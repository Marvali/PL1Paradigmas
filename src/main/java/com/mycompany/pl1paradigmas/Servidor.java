package com.mycompany.pl1paradigmas;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.crypto.spec.IvParameterSpec;

public class Servidor {

    public static void main(String[] args) {

        try {
            ObjetoRemoto obj = new ObjetoRemoto();  // INSTANCIAMOS EL OBJETO REMOTO
            Registry reg = LocateRegistry.createRegistry(1099); //ARRANCAMOS EL REGISTRO DE OBJETOS REMOTOS EN EL PUERTO 1099
            Naming.rebind("rmi://localhost:1099/objetoRMI", obj); // REGISTRAMOS EL OBJETO REMOTO EN EL REGISTRO


        } catch (Exception ex) { //IP REMOTE EXCEPTION
            ex.printStackTrace();
        }
    }




}