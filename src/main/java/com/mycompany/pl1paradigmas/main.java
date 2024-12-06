package com.mycompany.pl1paradigmas;

public class main {

    public static void main(String[] args) {
        Log log = new Log();
        //crear los hornos con for y llearnr un array de hornos
        Horno[] hornos = new Horno[0];
        for (int i = 0; i < 4; i++) {
            Horno horno = new Horno(i, 45, log);
            hornos[i] = horno;
            horno.start();
        }
        
        //crear 4 reposteros con for
        for (int i = 0; i < 4; i++) {
            Repostero repostero = new Repostero(i, hornos);
            repostero.start();
        }

       



    }
}


