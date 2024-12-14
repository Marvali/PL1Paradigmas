package logicaServidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazRemota extends Remote {



    public int getGalletasDesperdiciadas(int idRepostero) throws RemoteException;
    public int getGalletasGeneradas(int idRepostero) throws RemoteException;
    public int getGalletasHorneadas(int idRepostero) throws RemoteException;
    public int getEstadoHorno(int idHorno) throws RemoteException;
    public int getGalletasAlmacenadas() throws RemoteException;
    public int getGalletasComsumidas() throws RemoteException;
    public int ComprobarConexiones() throws RemoteException;
    public void pausarRepostero(int idRepostero) throws RemoteException;
    public void reanudarRepostero(int idRepostero) throws RemoteException;
}

