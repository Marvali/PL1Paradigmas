package com.mycompany.pl1paradigmas;



public class Fabrica {
    private Repostero[] reposteros;
    private Horno[] hornos;
    private Empaquetador[] empaquetadores;
    private Almacen almacen;
    private Cafetera cafetera;
    //crear un array de 5 elementos para guardar las tandas producidas por cada repostero
    private int[] tandasProducidas ;
    private int[] tandasAProducir;
    private String reposteroEnCafetera;
    private int lotesEmpaquetados [];
    private String estadoEmpaquetador [];




    // Contadores globales
    private int galletasDesperdiciadas;
    private int galletasConsumidas;

    // Constructor que recibe las referencias a todas las entidades
    public Fabrica(Repostero[] reposteros, Horno[] hornos, Empaquetador[] empaquetadores, Almacen almacen, Cafetera cafetera) {
        this.reposteros = reposteros;
        this.hornos = hornos;
        this.empaquetadores = empaquetadores;
        this.almacen = almacen;
        this.cafetera = cafetera;
        this.galletasDesperdiciadas = 0;
        this.galletasConsumidas = 0;
        this.tandasProducidas = new int[5];
        this.tandasAProducir = new int[5];
        this.reposteroEnCafetera = "vacio";
        this.lotesEmpaquetados = new int[3];
        this.estadoEmpaquetador = new String[3];


    }

    // Getters y setters sincronizados para acceso seguro

    // ****** Galletas Desperdiciadas ******
    public synchronized void incrementarGalletasDesperdiciadas(int cantidad) {
        this.galletasDesperdiciadas += cantidad;
    }

    public synchronized int getGalletasDesperdiciadas() {
        return this.galletasDesperdiciadas;
    }

    // ****** Galletas Consumidas ******
    public synchronized void incrementarGalletasConsumidas(int cantidad) {
        this.galletasConsumidas += cantidad;
    }

    public synchronized int getGalletasConsumidas() {
        return this.galletasConsumidas;
    }

    // ****** Almacen ******
    public synchronized int getCantidadGalletasAlmacen() {
        return almacen.getCantidadGalletasAlmacen();
    }

    // Si se requiere modificar el almacén desde aquí
    public synchronized void setCantidadGalletasAlmacen(int cantidad) {
        almacen.setCantidadGalletasAlmacen(cantidad);
    }

    // ****** Reposteros ******
    public synchronized int getNumReposteros() {
        return reposteros.length;
    }

    public synchronized Repostero getRepostero(int id) {
        return reposteros[id];
    }
    public String getReposteroEnCafetera() {
        return reposteroEnCafetera;
    }

    // Metodos para pausar/reanudar reposteros si los implementas
    public synchronized void pausarRepostero(int idRepostero) {
        return;
    }


    // ****** Hornos ******
    public synchronized int getNumHornos() {
        return hornos.length;
    }

    public Horno getHorno(int index) {
        if (index < 0 || index >= hornos.length) {
            throw new IndexOutOfBoundsException("Índice de horno no válido");
        }
        return hornos[index];
    }

    public synchronized int getGalletasHorno(int idHorno) {
        return hornos[idHorno].getCantidadGalletasHorno();
    }

    public synchronized boolean isHornoEnHorneo(int idHorno) {

        return false;
    }

    // ****** Empaquetadores ******
    public synchronized int getNumEmpaquetadores() {
        return empaquetadores.length;
    }

    public synchronized Empaquetador getEmpaquetador(int idEmp) {
        return empaquetadores[idEmp];
    }

    // ****** Cafetera ******
    public synchronized Cafetera getCafetera() {
        return cafetera;
    }

    // Otros métodos que consideres necesarios:
    // Por ejemplo, para "comer galletas":
    public synchronized void consumirGalletas(int cantidad) {
        // Aquí podrías reutilizar el método de Almacen y llevar el control global
        int actuales = almacen.getCantidadGalletasAlmacen();
        if (actuales >= cantidad) {
            almacen.setCantidadGalletasAlmacen(actuales - cantidad);
            incrementarGalletasConsumidas(cantidad);
        }
    }

    // Métodos para settear arrays si en algún momento necesita recargarlos (opcional)
    public synchronized void setReposteros(Repostero[] reposteros) {
        this.reposteros = reposteros;
    }

    public synchronized void setHornos(Horno[] hornos) {
        this.hornos = hornos;
    }

    public synchronized void setEmpaquetadores(Empaquetador[] empaquetadores) {
        this.empaquetadores = empaquetadores;
    }

    public synchronized void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public synchronized void setCafetera(Cafetera cafetera) {
        this.cafetera = cafetera;
    }

    public synchronized int getTandasAProducir(int idRepostero) {
        return tandasAProducir[idRepostero];
    }

    public synchronized int getTandasProducidas(int idRepostero) {
        return tandasProducidas[idRepostero];
    }

   public synchronized void setTandasProducidas(int idRepostero, int tandasProducidas) {
        this.tandasProducidas[idRepostero] = tandasProducidas;
    }

    public synchronized  void setTandasAProducir(int idRepostero, int tandasAProducir) {
        this.tandasAProducir[idRepostero] = tandasAProducir;


   }

   public synchronized void setReposteroEnCafetera(String nombre) {
        cafetera.setReposteroActual(nombre);
    }

    public synchronized void setReposteroEnCafetera1(String repostero) {
        this.reposteroEnCafetera = repostero;

    }

    public synchronized void incrementarLotesEmpaquetados(int idEmpaquetador) {
        lotesEmpaquetados[idEmpaquetador]++;
    }

    public synchronized void setLotesEmpaquetados(int idEmpaquetador, int lotesEmpaquetados) {
        this.lotesEmpaquetados[idEmpaquetador] = lotesEmpaquetados;
    }

    public synchronized int getLotesEmpaquetados(int idEmpaquetador) {
        return lotesEmpaquetados[idEmpaquetador];
    }

    public synchronized void setEstadoEmpaquetador(int idEmpaquetador, String estado) {
        estadoEmpaquetador[idEmpaquetador] = estado;
    }

    public synchronized String getEstadoEmpaquetador(int idEmpaquetador) {
        return estadoEmpaquetador[idEmpaquetador];
    }

}


