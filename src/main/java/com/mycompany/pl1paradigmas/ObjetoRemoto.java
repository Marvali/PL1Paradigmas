package com.mycompany.pl1paradigmas;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfazRemota {
    ObjetoRemoto() throws RemoteException {

    }

    @Override
    public int getGalletasDesperdiciadas() throws RemoteException {
        return 0;
    }

    @Override
    public int getGalletasGeneradas() throws RemoteException {
        return 0;
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
        return 0;
    }

    @Override
    public int getGalletasComsumidas() throws RemoteException {
        return 0;
    }

    @Override
    public void pararRepostero(int idRepostero) throws RemoteException {

    }

    @Override
    public int ComprobarConexiones() throws RemoteException {
        return 1;


    }
}
