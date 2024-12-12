/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;

import java.sql.SQLOutput;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Almacen de galletas
 * El almacen tiene una capacidad máxima de 1000 galletas
 * Los empaquetadores llevan las galletas al almacen
 * Los empaquetadores recogerán las galletas en lotes de 20, lo cual les toma entre 0,5 y 1
 * segundos por cada lote, y, cuando llegan a 100 galletas, las empaquetarán. No será hasta entonces cuando
 * las lleven al almacén, lo que les toma entre 2 y 4 segundos.
 * El almacén tiene una capacidad máxima de 1000
 * galletas y contará con un botón que el usuario podrá pulsar para “comer” galletas. Cada pulsación del ratón
 * debe consumir 100 galletas. Mientras el almacén esté lleno, los empaquetadores no podrán depositar más
 * galletas, por lo que el usuario deberá vaciar el almacén regularmente para permitir nuevas entregas.
 */

/**
 *
 * @author isaac
 */
public class Almacen {
    private int capacidadMaxima;       // Capacidad máxima del almacen
    private int cantidadGalletasAlmacen;       // Cantidad actual de galletas en el almacen
    private Semaphore emSemaforo;        // Semáforo para controlar el acceso


    // Constructor
    public Almacen(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.cantidadGalletasAlmacen = 0;
        this.emSemaforo = new Semaphore(1);

    }

    //Metodos

    //getters y setters
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getCantidadGalletasAlmacen() {
        return cantidadGalletasAlmacen;
    }


    public Semaphore getEmSemaforo() {
        return emSemaforo;
    }



    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }




    //Metodo para comer galletas
    public void comerGalletas() {
        //usando el semaforo
        try {
            emSemaforo.acquire();
            if (cantidadGalletasAlmacen >= 100) {
                cantidadGalletasAlmacen -= 100;
            } else {
                System.out.println("No hay suficientes galletas en el almacen");
            }
            emSemaforo.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Metodo para depositar galletas
    public void setCantidadGalletasAlmacen(int cantidad) {
        //usando el semaforo


            if (cantidadGalletasAlmacen + cantidad <= capacidadMaxima) {
                cantidadGalletasAlmacen += cantidad;
                System.out.println("+-----------------------------------+");
                System.out.println("El empaquetador ha depositado " + cantidad + " galletas en el almacen");
                System.out.println("El almacen tiene " + cantidadGalletasAlmacen + " galletas");
                System.out.println("+-----------------------------------+");

            } else {
                System.out.println("+-----------------------------------+");
                System.out.println("NO HAY ESPACIO EN EL ALMACEN");
                System.out.println(" el almacen esta a " + cantidadGalletasAlmacen + " de " + capacidadMaxima);
                System.out.println("+-----------------------------------+");
            }


    }

}