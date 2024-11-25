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
 */
public class Respostero extends Thread{
    //Atributos
    String idRespostero;
    Horno [] hornos;
    Semaphore emCafetera = new Semaphore(1);                     //este semaforo es porque solo hay 1 cafetera
    Log log = new Log();
    int tandasProducidas = 0;                                           //entre 3 y 5 tandas de galletas
    int galletasPorTanda = 0;                                           //entre 37 y 45 galletas por tanda
    Random rand = new Random();
    int tiempoProduccion = 0;                                           //entre 2 y 4 segundos
    int tiempoDescanso = 0;                                             //entre 3 y 6 segundos

    //Constructor
    public Respostero(String idRespostero, Horno[] hornos) {
        this.idRespostero = idRespostero;
        this.hornos = hornos;  
    }

    //Metodos
    @Override
    public void run(){


                //El respostero produce galletas


                //El respostero descansa




        }


}
