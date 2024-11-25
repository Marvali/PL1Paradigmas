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
    private String id;                 // Identificador único del horno
    private int capacidadMaxima;       // Capacidad máxima del horno
    private int capacidadActual;       // Cantidad actual de galletas en el horno
    private Log log;                   // Objeto para registrar eventos
    private ReentrantLock lock;        // Bloqueo para exclusión mutua
    private Condition lleno;           // Condición para indicar que el horno está lleno
    private Condition vacio;           // Condición para indicar que el horno está vacío
    private boolean enHorneo;          // Indica si el horno está horneando actualmente

    // Constructor
    public Horno(String id, int capacidadMaxima, Log log) {
        this.id = id;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = 0;
        this.log = log;
        this.lock = new ReentrantLock();
        this.lleno = lock.newCondition();
        this.vacio = lock.newCondition();
        this.enHorneo = false;
    }

    // Agregar galletas al horno
    public boolean agregarGalletas(int cantidad, String reposteroId) {
        lock.lock();
        try {
            if (capacidadActual + cantidad > capacidadMaxima) {
                int espacioDisponible = capacidadMaxima - capacidadActual;
                capacidadActual += espacioDisponible; // Llena el horno
                log.escribirLog(reposteroId + " deja " + espacioDisponible + " galletas en " + id);
                log.escribirLog(reposteroId + " desecha " + (cantidad - espacioDisponible) + " galletas");
                if (capacidadActual == capacidadMaxima) {
                    lleno.signal(); // Notifica que el horno está lleno
                }
                return false; // Parte de las galletas se desperdicia
            } else {
                capacidadActual += cantidad;
                log.escribirLog(reposteroId + " deja " + cantidad + " galletas en " + id);
                if (capacidadActual == capacidadMaxima) {
                    lleno.signal(); // Notifica que el horno está lleno
                }
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    // Hornear las galletas
    public void hornear() throws InterruptedException {
        lock.lock();
        try {
            while (capacidadActual < capacidadMaxima) {
                lleno.await(); // Espera a que el horno esté lleno
            }
            enHorneo = true;
            log.escribirLog(id + " comienza a hornear");
            Thread.sleep(8000); // Simula el tiempo de horneado (8 segundos)
            log.escribirLog(id + " termina de hornear");
            capacidadActual = 0; // Vacía el horno
            enHorneo = false;
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

    // Verificar si el horno está horneando
    public boolean getHorneo() {
        return enHorneo;
    }


}


