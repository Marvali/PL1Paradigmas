/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author isaac y fer
 *
 * Los reposteros tardan entre 2 y 4 segundos en producir una tanda de galletas, que puede ser entre 37 y
 * 45 galletas, y luego las depositan en un horno. Una vez que completan de 3 a 5 tandas de producción, realizan
 * una parada para el café. Durante este descanso, los reposteros se preparan café, pero solo hay una cafetera
 * disponible, por lo que solo un repostero puede prepararse café a la vez. En preparar café tardan 2 segundos,
 * y, una vez se han preparado el café, descansan entre 3 y 6 segundos.
 */
public class Repostero extends Thread {
    //Atributos
    private int idRespostero;
    private Horno[] hornos;
    private static Semaphore emCafetera = new Semaphore(1);
    private Log log = new Log();
    private int tandasProducidas = 0;                                           //entre 3 y 5 tandas de galletas
    private int galletasPorTanda = 0;                                           //entre 37 y 45 galletas por tanda
    private Random rand = new Random();
    private int tiempoProduccion = 0;                                           //entre 2 y 4 segundos
    private int tiempoDescanso = 0;                                            //entre 3 y 6 segundos
    private int totalGalletas = 0;                                              //total de galletas producidas

    //Constructor
    public Repostero(int idRespostero, Horno[] hornos) {
        this.idRespostero = idRespostero;
        this.hornos = hornos;
    }

    //Metodos
    @Override
    public void run() {
        try {
            while (true) {
                //El respostero produce galletas
                sleep((long) (2000 + 4000 * Math.random())); //entre 2 y 4 segundos
                producirGalletas();


                //El respostero deposita las galletas en un horno
                //usar semaforo para acceder a los hornos


                depositarGalletas();


                // El repostero descansa
                tandasProducidas++;
                if (tandasProducidas == 3 ) {

                hacerCafe();


                sleep((long) (3000 + 3000 * Math.random())); //entre 3 y 6 segundos
                log.escribirLog("El repostero " + idRespostero + " ha terminado de descansar");
                System.out.println("El repostero " + idRespostero + " ha terminado de descansar");
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);


            //El respostero descansa



        }

    }


    //funcion para hacer cafe, coger semaforo
    public void hacerCafe() {
        try {
            emCafetera.acquire(); // Intenta adquirir el permiso para usar la cafetera
            log.escribirLog("El repostero " + idRespostero + " está preparando café");
            System.out.println("El repostero " + idRespostero + " está preparando café");
            sleep(2000); // Simula el tiempo de preparación del café (2 segundos)
            log.escribirLog("El repostero " + idRespostero + " ha terminado de preparar café");
            System.out.println("El repostero " + idRespostero + " ha terminado de preparar café");
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            emCafetera.release(); // Libera el permiso para que otros puedan usar la cafetera
        }
    }


    //funcion para producir galletas
    public void producirGalletas() {
        //generamos un numero aleatorio entre 37 y 45

        galletasPorTanda = rand.nextInt(45 - 37 + 1) + 37;
        System.out.println("El repostero " + idRespostero + " ha producido " + galletasPorTanda + " galletas");
        log.escribirLog("El repostero " + idRespostero + " ha producido " + galletasPorTanda + " galletas");
        //guardamos las galletas en el total

        totalGalletas += galletasPorTanda;

    }

    //funcion para depositar galletas en un horno
    public void depositarGalletas() throws InterruptedException {
        //seguir intentando depositar galletas en los hornos hasta que se pueda
        do {
            for (int i = 0; i < hornos.length; i++) {
                Horno horno = hornos[i];
                if (horno.getLock().tryLock()) { // Intenta adquirir el lock del horno
                    try {
                        // Mientras el repostero tenga galletas para depositar
                        while (galletasPorTanda > 0) {
                            // Espera si el horno no está completamente vacío
                            while (horno.getCantidadGalletasHorno() == horno.getCapacidadMaxima()) {
                                horno.getVacio().await(); // Espera a que haya espacio en el horno
                            }

                            int espacioVacio = horno.getCapacidadMaxima() - horno.getCantidadGalletasHorno();

                            if (galletasPorTanda <= espacioVacio) {
                                // Todas las galletas caben
                                horno.setCantidadGalletasHorno(horno.getCantidadGalletasHorno() + galletasPorTanda);
                                log.escribirLog("El repostero " + idRespostero + " deposita " + galletasPorTanda + " galletas en " + horno.getId());
                                System.out.println("El repostero " + idRespostero + " deposita " + galletasPorTanda + " galletas en " + horno.getId());
                                galletasPorTanda = 0;

                                // Notifica a otros reposteros
                                horno.getVacio().signalAll();

                                // Notifica al horneador si el horno está lleno
                                if (horno.getCantidadGalletasHorno() == horno.getCapacidadMaxima()) {
                                    horno.getLleno().signal();
                                }
                            } else {
                                // Llena el horno con lo que puede y desperdicia el resto
                                horno.setCantidadGalletasHorno(horno.getCapacidadMaxima());
                                int galletasDepositadas = espacioVacio;
                                int galletasDesperdiciadas = galletasPorTanda - espacioVacio;
                                galletasPorTanda = 0; // Todas las galletas restantes se descartan

                                log.escribirLog("El repostero " + idRespostero + " deposita " + galletasDepositadas + " galletas en " + horno.getId() + " y desperdicia " + galletasDesperdiciadas + " galletas");
                                System.out.println("El repostero " + idRespostero + " deposita " + galletasDepositadas + " galletas en " + horno.getId() + " y desperdicia " + galletasDesperdiciadas + " galletas");

                                // Notifica a otros reposteros
                                horno.getVacio().signalAll();

                                // Notifica al horneador si el horno está lleno
                                if (horno.getCantidadGalletasHorno() == horno.getCapacidadMaxima()) {
                                    horno.getLleno().signal();
                                }
                            }
                        }
                    } finally {
                        horno.getLock().unlock(); // Libera el lock del horno
                    }
                }
            }
        } while (galletasPorTanda > 0);
    }


}