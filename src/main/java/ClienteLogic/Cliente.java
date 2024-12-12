/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ClienteLogic;
import com.mycompany.pl1paradigmas.InterfazRemota;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author Fer
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        try{
        InterfazRemota obj = (InterfazRemota) Naming.lookup("rmi://localhost:1099/objetoRMI");
        
         int respuesta = obj.ComprobarConexiones();
         System.out.println(respuesta);
        
        
        
        }catch (Exception e) {
         System.out.println("Excepción : " + e.getMessage());
         e.printStackTrace();
                }
        }
    
    
}
