/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;

import java.util.Random;
import java.util.concurrent.Semaphore;

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
public class Respostero extends Thread {
    //Atributos
    private String idRespostero;
    private Horno[] hornos;
    private Semaphore emCafetera = new Semaphore(1);                     //este semaforo es porque solo hay 1 cafetera
    private Log log = new Log();
    private int tandasProducidas = 0;                                           //entre 3 y 5 tandas de galletas
    private int galletasPorTanda = 0;                                           //entre 37 y 45 galletas por tanda
    private Random rand = new Random();
    private int tiempoProduccion = 0;                                           //entre 2 y 4 segundos
    private int tiempoDescanso = 0;                                            //entre 3 y 6 segundos
    private int totalGalletas = 0;                                              //total de galletas producidas

    //Constructor
    public Respostero(String idRespostero, Horno[] hornos) {
        this.idRespostero = idRespostero;
        this.hornos = hornos;
    }

    //Metodos
    @Override
    public void run() {
        try {
            while (true) {
                //El respostero produce galletas
                sleep(2000 + 4000 * math.random()); //entre 2 y 4 segundos
                producirGalletas();


                //El respostero deposita las galletas en un horno
                //usar semaforo para acceder a los hornos


                depositarGalletas();



                //El respostero descansa
                tandasProducidas++;
                if (tandasProducidas >= 3 && tandasProducidas <= 5) {
                    //El respostero se prepara cafe
                    emCafetera.acquire();
                    sleep(2000); //2 segundos
                    emCafetera.release();
                    //El respostero descansa
                    sleep(3000 + 6000 * math.random()); //entre 3 y 6 segundos
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);


            //El respostero descansa


        }

    }

    //funcion para producir galletas
    public void producirGalletas() {
        //generamos un numero aleatorio entre 37 y 45
        galletasPorTanda = rand.nextInt(45 - 37 + 1) + 37;
        //guardamos las galletas en el total
        totalGalletas += galletasPorTanda;

    }

    //funcion para depositar galletas en un horno
    public void depositarGalletas() {
        // Seleccionamos un horno en orden; si llegamos al último, volvemos al primero
        do {
            for (int i = 0; i < hornos.length; i++) {
                Horno horno = hornos[i];
                horno.getLock().lock(); // Bloquea el acceso al horno actual
                try {
                    // Verifica si el horno no está horneando
                    if (!horno.getHorneo()) {
                        // Calcula el espacio disponible en el horno
                        int espacio = horno.getCapacidadMaxima() - horno.getCapacidadActual();

                        if (galletasPorTanda <= espacio) {
                            // Si caben todas las galletas, deposítalas
                            horno.setCapacidadActual(horno.getCapacidadActual() + galletasPorTanda);
                            log.escribirLog(idRepostero + " deposita " + galletasPorTanda + " galletas en " + horno.getId());
                            galletasPorTanda = 0; // Todas las galletas han sido depositadas
                            break;
                        } else {
                            // Si no caben todas las galletas, llena el horno y guarda las sobrantes
                            horno.setCapacidadActual(horno.getCapacidadMaxima());
                            log.escribirLog(idRepostero + " deposita " + espacio + " galletas en " + horno.getId());
                            galletasPorTanda -= espacio; // Reduce la cantidad de galletas restantes
                        }
                    }
                } finally {
                    horno.getLock().unlock(); // Libera el acceso al horno actual
                }
            }

            // Si todavía quedan galletas por depositar, espera un momento antes de reintentar
            if (galletasPorTanda > 0) {
                try {
                    Thread.sleep(500); // Espera 500 ms antes de reintentar
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } while (galletasPorTanda > 0);
    }


}


















}
