package com.mycompany.pl1paradigmas;

import InterfazServer.InterfazServidor;

import javax.swing.SwingUtilities;

public class main {

    public static void main(String[] args) {
        // Crear el log
        Log log = new Log();
        Fabrica fabrica = new Fabrica(null, null, null, null, null);

        // Crear un array de 3 hornos
        Horno[] arrayHornos = new Horno[3];
        for (int i = 0; i < arrayHornos.length; i++) {
            Horno horno = new Horno(i, 200, log, fabrica);
            arrayHornos[i] = horno;
            horno.start();
        }

        // Crear una cafetera
        Cafetera cafetera = new Cafetera(log);

        // Crear un almacén
        Almacen almacen = new Almacen(1000);

        // Crear un array de 5 reposteros
        Repostero[] arrayReposteros = new Repostero[5];

        // Crear 4 reposteros y pasarles la interfaz
        for (int i = 0; i < arrayReposteros.length; i++) {
            Repostero repostero = new Repostero(i, arrayHornos, cafetera,fabrica);
            arrayReposteros[i] = repostero;
            repostero.start();
        }

        // Crear un array de 3 empaquetadores
        Empaquetador[] arrayEmpaquetadores = new Empaquetador[3];
        // Crear 3 empaquetadores y asignarles su horno único
        for (int i = 0; i < arrayEmpaquetadores.length; i++) {
            Empaquetador empaquetador = new Empaquetador(i, arrayHornos[i], almacen,fabrica );
            arrayEmpaquetadores[i] = empaquetador;
            empaquetador.start();
        }

        // llenar la fabrica con los objetos creados
        fabrica.setReposteros(arrayReposteros);
        fabrica.setHornos(arrayHornos);
        fabrica.setEmpaquetadores(arrayEmpaquetadores);
        fabrica.setAlmacen(almacen);
        fabrica.setCafetera(cafetera);



        // Crear la interfaz
        SwingUtilities.invokeLater(() -> {
            InterfazServidor gui = new InterfazServidor(fabrica);
            gui.setVisible(true);
        });



    }
}