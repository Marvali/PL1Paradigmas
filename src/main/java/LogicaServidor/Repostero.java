/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaServidor;

import InterfazServer.InterfazServidor;

import java.util.Random;
import java.util.concurrent.locks.Condition;
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
    private Log log = new Log();
    private int tandasProducidas ;                                           //entre 3 y 5 tandas de galletas
    private int galletasPorTanda = 0;                                           //entre 37 y 45 galletas por tanda
    private Random rand = new Random();
    private int tiempoProduccion = 0;                                           //entre 2 y 4 segundos
    private int tiempoDescanso = 0;                                            //entre 3 y 6 segundos
    private int totalGalletas = 0;                                              //total de galletas producidas
    private Cafetera cafetera;
    private InterfazServidor interfazServidor;
    private int tandasAProducir;
    private Fabrica fabrica;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pausarCondicion = lock.newCondition();
    private boolean pausado = false;



    //Constructor
    public Repostero(int idRespostero, Horno[] hornos, Cafetera cafetera, Fabrica fabrica) {
        this.idRespostero = idRespostero;
        this.hornos = hornos;
        this.cafetera = cafetera;
        this.interfazServidor = interfazServidor;
        this.fabrica = fabrica;
    }

    //Metodos
    @Override
    public void run() {
        try {
            while (true) {
                verificarPausa(); // Verificar si el hilo está pausado

                //El respostero produce galletas
                tandasAProducir = 3 + rand.nextInt(3); // Generate a random number between 3 and 5
                tandasProducidas = 0;
                fabrica.setTandasProducidas(idRespostero, tandasProducidas);
                fabrica.setTandasAProducir(idRespostero, tandasAProducir);

                for (int i = 0; i < tandasAProducir; i++) {
                    sleep((long) (2000 + 2000 * Math.random())); // Between 2 and 4 seconds
                    fabrica.setTandasProducidas(idRespostero, tandasProducidas);
                    log.escribirLog("El repostero " + idRespostero + " lleva " + tandasProducidas + " tandas producidas de " + tandasAProducir);

                    producirGalletas();
                    tandasProducidas++;

                    depositarGalletas();

                }




                //El respostero deposita las galletas en un horno
                //usar semaforo para acceder a los hornos





                // El repostero descansa



                if (tandasProducidas == tandasAProducir) {
                    tandasProducidas = 0;
                    fabrica.setTandasProducidas(idRespostero, tandasProducidas);
                    fabrica.setTandasAProducir(idRespostero, tandasAProducir);

                    hacerCafe();
                }

                log.escribirLog("El repostero " + idRespostero + " se va a tomar su descanso");
                sleep((long) (3000 + 3000 * Math.random())); //entre 3 y 6 segundos
                log.escribirLog("El repostero " + idRespostero + " ha terminado de descansar");
                System.out.println("El repostero " + idRespostero + " ha terminado de descansar");

            }
        } catch (InterruptedException e) {
            System.out.println(e);


            //El respostero descansa



        }

    }


    //funcion para hacer cafe, coger semaforo
    public void hacerCafe() throws InterruptedException {
        try {

            cafetera.getEmCafetera().acquire();
            log.escribirLog("El repostero " + idRespostero + " ha cogido la cafetera");
            System.out.println("El repostero " + idRespostero + " ha cogido la cafetera");
            fabrica.setReposteroEnCafetera1("repostero " + idRespostero);
            sleep((long) (2000)); // 2 seconds
            log.escribirLog("El repostero " + idRespostero + " ha preparado el cafe");
            System.out.println("El repostero " + idRespostero + " ha preparado el cafe");
            fabrica.setReposteroEnCafetera1(" No hay repostero en la cafetera");
            cafetera.getEmCafetera().release();
            log.escribirLog("El repostero " + idRespostero + " ha soltado la cafetera");
            System.out.println("El repostero " + idRespostero + " ha soltado la cafetera");



        } catch (InterruptedException e) {
            System.out.println(e);
            throw e; // Re-throw the InterruptedException
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
                                log.escribirLog("El repostero " + idRespostero + " deposita " + galletasPorTanda + " galletas en el horno " + horno.getId()) ;
                                System.out.println("El repostero " + idRespostero + " deposita " + galletasPorTanda + " galletas en el horno " + horno.getId());
                                fabrica.setGalletasGeneradas(idRespostero, galletasPorTanda);
                                galletasPorTanda = 0;

                                // Notifica a otros reposteros
                                horno.getVacio().signal();

                                // Notifica al horneador si el horno está lleno
                                if (horno.getCantidadGalletasHorno() == horno.getCapacidadMaxima()) {
                                    horno.getLleno().signal();
                                }
                            } else {
                                // Llena el horno con lo que puede y desperdicia el resto
                                horno.setCantidadGalletasHorno(horno.getCapacidadMaxima());
                                int galletasDepositadas = espacioVacio;
                                int galletasDesperdiciadas = galletasPorTanda - espacioVacio;
                                fabrica.setGalletasGeneradas(idRespostero, galletasDepositadas);
                                galletasPorTanda = 0; // Todas las galletas restantes se descartan

                                log.escribirLog("El repostero " + idRespostero + " deposita " + galletasDepositadas + " galletas en " + horno.getId() + " y desperdicia " + galletasDesperdiciadas + " galletas");
                                System.out.println("El repostero " + idRespostero + " deposita " + galletasDepositadas + " galletas en " + horno.getId() + " y desperdicia " + galletasDesperdiciadas + " galletas");
                                fabrica.setGallerasDesperdiciadas(idRespostero, galletasDesperdiciadas);

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

    // Método para pausar el hilo
    public void pausar() {
        lock.lock();
        try {
            pausado = true;



        } finally {
            lock.unlock();
        }
    }

    // Método para reanudar el hilo
    public void reanudar() {
        lock.lock();
        try {
            pausado = false;
            pausarCondicion.signal();

        } finally {
            lock.unlock();
        }
    }

    private void verificarPausa() throws InterruptedException {
        lock.lock();
        try {
            while (pausado) {
                System.out.println("--------------------");
                System.out.println("Repostero " + idRespostero + " pausado");
                System.out.println("--------------------");

                pausarCondicion.await();
            }
        } finally {
            lock.unlock();
        }
    }


    //Getters y Setters
    public int getTandasAProducir() {
        return tandasAProducir;
    }

    public int getTandasProducidas() {
        return tandasProducidas;
    }




}