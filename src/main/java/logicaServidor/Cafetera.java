package logicaServidor;

import java.util.concurrent.Semaphore;

public class Cafetera {

    private Semaphore emCafetera = new Semaphore(1);
    private Log log;
    private String reposteroActual;
    //Constructor
    public Cafetera(Log log) {
        this.log = log;
        //semaforo para la cafetera
        emCafetera = new Semaphore(1);
        this.reposteroActual = "vacio";
    }

    //Metodos
// dovolver el semaforo

    public Semaphore getEmCafetera() {
        return emCafetera;
    }

    public synchronized String getReposteroActual() {
        return reposteroActual;
    }

    public synchronized void setReposteroActual(String reposteroActual) {
        this.reposteroActual = reposteroActual;
    }


}
