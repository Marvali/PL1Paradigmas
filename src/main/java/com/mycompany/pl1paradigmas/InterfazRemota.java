package com.mycompany.pl1paradigmas;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazRemota extends Remote {

    public int getGalletasDesperdiciadas() throws RemoteException;
    public int getGalletasGeneradas() throws RemoteException;
    public int getGalletasHorneadas() throws RemoteException;
    public int getEstadoHorno() throws RemoteException;
    public int getGalletasAlmacenadas() throws RemoteException;
    public int getGalletasComsumidas() throws RemoteException;
    public void pararRepostero(int idRepostero) throws RemoteException;

}

