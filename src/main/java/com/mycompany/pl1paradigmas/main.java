package com.mycompany.pl1paradigmas;

import Interfaz.InterfazServidor;

import javax.swing.SwingUtilities;

public class main {

    public static void main(String[] args) {
        // Crear el log
        Log log = new Log();

        // Crear un array de 3 hornos
        Horno[] arrayHornos = new Horno[3];
        for (int i = 0; i < arrayHornos.length; i++) {
            Horno horno = new Horno(i, 200, log);
            arrayHornos[i] = horno;
            horno.start();
        }

        // Crear una cafetera
        Cafetera cafetera = new Cafetera(log);

        // Crear un almacén
        Almacen almacen = new Almacen(1000);

        // Crear un array de 4 reposteros
        Repostero[] arrayReposteros = new Repostero[4];

        // Crear 4 reposteros y pasarles la interfaz
        for (int i = 0; i < arrayReposteros.length; i++) {
            Repostero repostero = new Repostero(i, arrayHornos, cafetera);
            arrayReposteros[i] = repostero;
            repostero.start();
        }

        // Crear un array de 3 empaquetadores
        Empaquetador[] arrayEmpaquetadores = new Empaquetador[3];
        // Crear 3 empaquetadores y asignarles su horno único
        for (int i = 0; i < arrayEmpaquetadores.length; i++) {
            Empaquetador empaquetador = new Empaquetador(i, arrayHornos[i], almacen);
            arrayEmpaquetadores[i] = empaquetador;
            empaquetador.start();
        }

        // Crear la fábrica
        Fabrica fabrica = new Fabrica(arrayReposteros, arrayHornos, arrayEmpaquetadores, almacen, cafetera);

        // Crear la interfaz
        SwingUtilities.invokeLater(() -> {
            InterfazServidor gui = new InterfazServidor(fabrica);
            gui.setVisible(true);
        });



    }
}