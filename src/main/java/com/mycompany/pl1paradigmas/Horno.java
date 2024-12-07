/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl1paradigmas;
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



    // Hornear las galletas
    // OJO, HAY QUE QUITAR QUE VACIE LAS GALLETAS POR AQUI
    public void hornear() throws InterruptedException {
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


