/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author isaac
 */


public class Horno extends Thread {
    private int id;                     // Identificador del horno
    private int capacidadMaxima;       // Capacidad máxima del horno
    private int capacidadActual;       // Cantidad actual de galletas en el horno
    private Log log;                   // Objeto para registrar eventos
    private ReentrantLock lock;        // Bloqueo para exclusión mutua
    private Condition lleno;           // Condición para indicar que el horno está lleno
    private Condition vacio;           // Condición para indicar que el horno está vacío
    private boolean enHorneo;          // Indica si el horno está horneando actualmente

    // Constructor
    public Horno(int id, int capacidadMaxima, Log log) {
        this.id = id;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = 0;
        this.log = log;
        this.lock = new ReentrantLock();
        this.lleno = lock.newCondition();
        this.vacio = lock.newCondition();
        this.enHorneo = false;
    }



    // Hornear las galletas
    public void hornear() throws InterruptedException {
        lock.lock();
        try {
            while (capacidadActual < capacidadMaxima) {
                lleno.await(); // Espera a que el horno esté lleno
            }
            log.escribirLog(id + " comienza a hornear");
            Thread.sleep(8000); // Simula el tiempo de horneado (8 segundos)
            log.escribirLog(id + " termina de hornear");
            capacidadActual = 0; // Vacía el horno
            vacio.signalAll(); // Notifica que el horno está vacío
        } finally {
            lock.unlock();
        }
    }

    // Retirar las galletas del horno
    public int retirarGalletas(String empaquetadorId) throws InterruptedException {
        lock.lock();
        try {
            while (capacidadActual == 0 && !enHorneo) {
                vacio.await(); // Espera a que haya galletas horneadas
            }
            int galletasRetiradas = capacidadActual;
            capacidadActual = 0;
            log.escribirLog(empaquetadorId + " retira " + galletasRetiradas + " galletas de " + id);
            return galletasRetiradas;
        } finally {
            lock.unlock();
        }
    }

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

    public int getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(int capacidadActual) {
        this.capacidadActual = capacidadActual;
    }

    public ReentrantLock getLock() {
        return lock;
    }


    public Condition getLleno() {
        return lleno;
    }
}


