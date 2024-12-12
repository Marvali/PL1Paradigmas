/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;

import static java.lang.Thread.sleep;

/**
 *
 * @author isaac
 * Cada empaquetador solo puede recoger galletas de un horno específico. El empaquetador 1
 * recoge galletas del horno 1, el empaquetador 2 del horno 2, y así sucesivamente.
 * Los empaquetadores son
 * responsables de llevar las galletas horneadas al almacén.
 * Los empaquetadores recogerán las galletas en lotes de 20, lo cual les toma entre 0,5 y 1
 * segundos por cada lote, y, cuando llegan a 100 galletas, las empaquetarán. No será hasta entonces cuando
 * las lleven al almacén, lo que les toma entre 2 y 4 segundos.
 */
public class Empaquetador extends Thread {
    //Atributos
    private int idEmpaquetador;
    private Horno horno;
    private Almacen almacen;
    private int galletasRecogidas = 0;
    private int galletasEmpaquetadas = 0;
    private int tiempoRecogida = 0;                                           //entre 0,5 y 1 segundos
    private int tiempoAlmacen = 0; ;//entre 2 y 4 segundos
    private Log log = new Log();

    //Constructor
    public Empaquetador(int idEmpaquetador, Horno horno, Almacen almacen) {
        this.idEmpaquetador = idEmpaquetador;
        this.horno = horno;
        this.almacen = almacen;
    }

    //Metodos
    @Override
    public void run() {
        while (true) {
            //El empaquetador recoge galletas

            recogerGalletas();


        }
    }


    private void recogerGalletas() {
        horno.getLock().lock();
        try {

            // Espera a que el horno esté listo para ser recogido
            while (horno.getCantidadGalletasHorno() == 0) {
                horno.getListoParaRecoger().await();
            }

            int galletasHorno = horno.getCantidadGalletasHorno();
            int galletasPorPaquete = 0; // Lleva un registro de cuántas galletas se recogen por tanda

            // Recoge galletas de 20 en 20 hasta completar un paquete de 100
            while (galletasHorno > 0 && galletasPorPaquete < 100) {
                sleep((long) (500 + 500 * Math.random())); //entre 0,5 y 1 segundos
                if (galletasHorno >= 20) {
                    galletasHorno -= 20;
                    galletasPorPaquete += 20;
                    log.escribirLog("El empaquetador " + idEmpaquetador + " recoge 20 galletas del horno " + horno.getId());
                    System.out.println("El empaquetador " + idEmpaquetador + " recoge 20 galletas del horno " + horno.getId());
                } else {
                    galletasPorPaquete += galletasHorno;
                    galletasHorno = 0;
                }
            }

            // Actualiza el estado del horno
            horno.setCantidadGalletasHorno(galletasHorno);

            log.escribirLog("El empaquetador " + idEmpaquetador + " ha recogido " + galletasPorPaquete + " galletas del horno " + horno.getId());
            System.out.println("El empaquetador " + idEmpaquetador + " ha recogido " + galletasPorPaquete + " galletas del horno " + horno.getId());

            // Si el horno está completamente vacío, notifica a los reposteros
            if (horno.getCantidadGalletasHorno() == 0) {
                horno.getVacio().signal(); // Indica que el horno está vacío al respostero
            }

            // Llevar al almacén
            galletasRecogidas = galletasPorPaquete;
            // Simula el tiempo de llevar las galletas al almacén

            llevarAlmacen();

        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            horno.getLock().unlock();
        }
    }

    private void llevarAlmacen() {
        try {
            sleep((long) (2000 + 2000 * Math.random())); //entre 2 y 4 segundos
            // Acquire the semaphore to access the almacen
            almacen.getEmSemaforo().acquire();

            // Wait until there is enough space in the almacen
            while (almacen.getCantidadGalletasAlmacen() + galletasRecogidas > almacen.getCapacidadMaxima()) {
                // Release the semaphore and wait a bit before trying again
                almacen.getEmSemaforo().release();
                System.out.println("El empaquetador " + idEmpaquetador + " está esperando a que haya espacio en el almacén");
                log.escribirLog("El empaquetador " + idEmpaquetador + " está esperando a que haya espacio en el almacén");
                Thread.sleep(1000); // Sleep for a short period
                almacen.getEmSemaforo().acquire();
            }

            // Deposit the cookies in the almacen
            almacen.setCantidadGalletasAlmacen(almacen.getCantidadGalletasAlmacen() + galletasRecogidas);
            log.escribirLog("El empaquetador " + idEmpaquetador + " ha llevado " + galletasRecogidas + " galletas al almacén");
            System.out.println("El empaquetador " + idEmpaquetador + " ha llevado " + galletasRecogidas + " galletas al almacén");

            // Reset the count of collected cookies
            galletasRecogidas = 0;
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            // Release the semaphore
            almacen.getEmSemaforo().release();
        }

        // Check if there are still cookies in the oven and call recogerGalletas again
        if (horno.getCantidadGalletasHorno() > 0) {
            recogerGalletas();
        }
    }
}