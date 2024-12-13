package com.mycompany.pl1paradigmas;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;


public class ObjetoRemoto extends UnicastRemoteObject implements InterfazRemota {
    private final Fabrica fabrica; // Referencia a la fábrica

    public ObjetoRemoto(Fabrica fabrica) throws RemoteException {
        this.fabrica = fabrica;
    }

    @Override
    public int getGalletasDesperdiciadas(int idRepostero) throws RemoteException {
        return fabrica.getGalletasDesperdiciadas(idRepostero);
    }

    @Override
    public int getGalletasGeneradas(int idRepostero) throws RemoteException {
        return fabrica.getGalletasGeneradas(idRepostero);
    }

    @Override
    public int getGalletasHorneadas() throws RemoteException {
        return 0;
    }

    @Override
    public int getEstadoHorno() throws RemoteException {
        return 0;
    }

    @Override
    public int getGalletasAlmacenadas() throws RemoteException {
        return fabrica.getCantidadGalletasAlmacen();
    }

    @Override
    public int getGalletasComsumidas() throws RemoteException {
        return fabrica.getGalletasComidas();
    }

    @Override
    public void pararRepostero(int idRepostero) throws RemoteException {

    }

    @Override
    public int ComprobarConexiones() throws RemoteException {
        return 1;


    }

    @Override
    public void pausarRepostero(int idRepostero) throws RemoteException {
        fabrica.getRepostero(idRepostero).pausar();
        System.out.println("Repostero " + idRepostero + " pausado.");

    }

    @Override
    public void reanudarRepostero(int idRepostero) throws RemoteException {
        fabrica.getRepostero(idRepostero).reanudar();
        System.out.println("Repostero " + idRepostero + " reanudado.");
    }
}
