package com.mycompany.pl1paradigmas;

import java.util.concurrent.Semaphore;

public class Cafetera {

    private Semaphore emCafetera = new Semaphore(1);
    private Log log;

    //Constructor
    public Cafetera(Log log) {
        this.log = log;
        //semaforo para la cafetera
        emCafetera = new Semaphore(1);
    }

    //Metodos
// dovolver el semaforo

    public Semaphore getEmCafetera() {
        return emCafetera;
    }

}
