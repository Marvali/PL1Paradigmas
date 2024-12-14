/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ClienteLogic;

//
import InterfazClient.InterfazCliente;
import LogicaServidor.InterfazRemota;
import javax.swing.SwingUtilities;
        import java.rmi.Naming;

/**
 *
 * @author Fer
 */
public class Cliente {

    public static void main(String[] args) {
        try {
            InterfazRemota obj = (InterfazRemota) Naming.lookup("rmi://localhost:1099/objetoRMI");
            int respuesta = obj.ComprobarConexiones();
            System.out.println("Respuesta del servidor: " + respuesta);

            // Lanza la interfaz gráfica
            SwingUtilities.invokeLater(() -> {
                InterfazCliente gui = new InterfazCliente(obj);
                gui.setVisible(true);
            });
        } catch (Exception e) {
            System.err.println("Excepción: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
