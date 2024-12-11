package com.mycompany.pl1paradigmas;

public class main {

    public static void main(String[] args) {
        Log log = new Log();
        //crear los hornos con for y llearnr un array de hornos
        //array de 4 hornos
        Horno[] hornos = new Horno[3];
        for (int i = 0; i < hornos.length; i++) {
            Horno horno = new Horno(i, 200, log);
            hornos[i] = horno;
            horno.start();
        }
        //crear una cafetera
        Cafetera cafetera = new Cafetera(log);

        //crear 5 reposteros con for
        for (int i = 0; i < 5; i++) {
            Repostero repostero = new Repostero(i, hornos, cafetera);
            repostero.start();
        }
        //crear un almacen
        Almacen almacen = new Almacen(1000);
        //crear 3 empaquetadores con for
        //cada empaquetador tiene su unico horno
        for (int i = 0; i < 3; i++) {
            Empaquetador empaquetador = new Empaquetador(i, hornos[i], almacen);
            empaquetador.start();
        }




       



    }
}


