/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author isaac
 */


public class Horno extends Thread {
    private int id;                     // Identificador del horno
    private int capacidadMaxima;       // Capacidad máxima del horno
    private int cantidadGalletasHorno;       // Cantidad actual de galletas en el horno
    private Log log;                   // Objeto para registrar eventos
    private ReentrantLock lock;        // Bloqueo para exclusión mutua
    private Condition lleno;           // Condición para indicar que el horno está lleno
    private Condition listoParaRecoger;           // Condición para indicar que el horno está vacío
    private Condition vacio;           // Condición para indicar que el horno está vacío
    private boolean enHorneo;          // Indica si el horno está horneando actualmente
    private JProgressBar barraProgreso; // Barra de progreso asociada


    // Constructor
    public Horno(int id, int capacidadMaxima, Log log) {
        this.id = id;
        this.capacidadMaxima = capacidadMaxima;
        this.cantidadGalletasHorno = 0;
        this.log = log;
        this.lock = new ReentrantLock();
        this.lleno = lock.newCondition();
        this.vacio = lock.newCondition();
        this.listoParaRecoger = lock.newCondition();
        this.enHorneo = false;

        //signall vacio para que empiece el progama
        lock.lock();
        try {
            vacio.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Asignar la barra de progreso desde la interfaz gráfica
    public void setBarraProgreso(JProgressBar barraProgreso) {
        this.barraProgreso = barraProgreso;
    }

    // Depositar galletas en el horno




    // Hornear las galletas old version deprecated
    /*public void hornear() throws InterruptedException {
        lock.lock();
        try {
            while (cantidadGalletasHorno < capacidadMaxima) {
                lleno.await(); // Espera a que el horno esté lleno
            }

            log.escribirLog("El horno " + id + " comienza a hornear");
            System.out.println("El horno " + id + " comienza a hornear");
            Thread.sleep(8000); // Simula el tiempo de horneado (8 segundos)

            listoParaRecoger.signalAll(); // Indica que el horno está vacío
        } finally {
            lock.unlock();
            log.escribirLog("El horno " + id + " ha terminado de hornear");
            System.out.println("El horno " + id + " ha terminado de hornear");
        }
    }

     */

    // Hornear las galletas

    public void hornear() throws InterruptedException {
        lock.lock();
        try {
            while (cantidadGalletasHorno < capacidadMaxima) {
                lleno.await(); // Esperar a que el horno esté lleno
            }

            // Iniciar horneado y actualizar la barra de progreso
            for (int progreso = 0; progreso <= 100; progreso++) {
                Thread.sleep(80); // Simular progreso del horneado
                actualizarBarraProgreso(progreso); // Actualizar la barra
            }

            cantidadGalletasHorno = 0; // Resetear el horno después del horneado
            listoParaRecoger.signalAll(); // Notificar que el horno está listo
        } finally {
            lock.unlock();
        }
    }

    private void actualizarBarraProgreso(int progreso) {
        SwingUtilities.invokeLater(() -> {
            if (barraProgreso != null) {
                barraProgreso.setValue(progreso);
                barraProgreso.setString(progreso == 100 ? "Listo para recoger" : "Horneando...");
            }
        });
    }

    // Retirar las galletas del horno


    // Ciclo principal del horno
    @Override
    public void run() {
        try {
            while (true) {
                hornear(); // Ejecuta el proceso de horneado cuando esté lleno
            }
        } catch (
                InterruptedException e) {
            System.out.println(e);
        }
    }

    // Getters y setters

    public long getId() {
        return id;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public int getCantidadGalletasHorno() {

        return cantidadGalletasHorno;
    }


    public void setCantidadGalletasHorno(int cantidadGalletasHorno) {
        this.cantidadGalletasHorno = cantidadGalletasHorno;
    }

    public ReentrantLock getLock() {
        return lock;
    }


    public Condition getLleno() {
        return lleno;
    }

    public Condition getListoParaRecoger() {
        return listoParaRecoger;
    }

    public Condition getVacio() {
        return vacio;
    }


}


