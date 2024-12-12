/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InterfazClient;

/**
 *
 * @author Fer
 */
import javax.swing.*;
import java.awt.*;

public class InterfazCliente {
    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Fábrica de Galletas - Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel para los reposteros
        JPanel reposterosPanel = new JPanel(new BorderLayout(10, 10));
        reposterosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Reposteros", 0, 0, new Font("Arial", Font.BOLD, 14)));

        // Encabezados
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        headerPanel.add(new JLabel("Repostero", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Galletas generadas", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Galletas desperdiciadas", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Acción", SwingConstants.CENTER));
        reposterosPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel reposterosGridPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        for (int i = 1; i <= 5; i++) {
            JLabel label = new JLabel("Repostero" + i, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            reposterosGridPanel.add(label);

            JTextField galletasGeneradas = new JTextField("0", 5);
            galletasGeneradas.setEditable(false);
            galletasGeneradas.setHorizontalAlignment(JTextField.CENTER);
            reposterosGridPanel.add(galletasGeneradas);

            JTextField galletasDesperdiciadas = new JTextField("0", 5);
            galletasDesperdiciadas.setEditable(false);
            galletasDesperdiciadas.setHorizontalAlignment(JTextField.CENTER);
            reposterosGridPanel.add(galletasDesperdiciadas);

            JButton actionButton = new JButton(i % 2 == 0 ? "Parar" : "Reanudar");
            actionButton.setForeground(Color.WHITE);
            actionButton.setBackground(i % 2 == 0 ? Color.RED : Color.GREEN);
            reposterosGridPanel.add(actionButton);
        }
        reposterosPanel.add(reposterosGridPanel, BorderLayout.CENTER);

        // Panel para los hornos
        JPanel hornosPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        hornosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Hornos", 0, 0, new Font("Arial", Font.BOLD, 14)));
        for (int i = 1; i <= 3; i++) {
            JLabel label = new JLabel("Horno" + i);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            hornosPanel.add(label);

            JPanel hornoDataPanel = new JPanel(new GridLayout(2, 1, 5, 5));

            JTextField galletasHorneadas = new JTextField("0", 5);
            galletasHorneadas.setEditable(false);
            galletasHorneadas.setHorizontalAlignment(JTextField.CENTER);
            hornoDataPanel.add(new JLabel("Galletas horneadas:"));
            hornoDataPanel.add(galletasHorneadas);

            JProgressBar horneandoBar = new JProgressBar(0, 100);
            horneandoBar.setValue(0);
            horneandoBar.setStringPainted(true);
            horneandoBar.setForeground(Color.RED);
            hornoDataPanel.add(new JLabel("Horneando:"));
            hornoDataPanel.add(horneandoBar);

            hornosPanel.add(hornoDataPanel);
        }

        // Panel para el almacén
        JPanel almacenPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        almacenPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Almacén", 0, 0, new Font("Arial", Font.BOLD, 14)));

        JPanel almacenInfoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel almacenLabel = new JLabel("Galletas almacenadas:");
        almacenLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        almacenInfoPanel.add(almacenLabel);

        JTextField galletasAlmacenadas = new JTextField("0", 5);
        galletasAlmacenadas.setEditable(false);
        galletasAlmacenadas.setHorizontalAlignment(JTextField.CENTER);
        almacenInfoPanel.add(galletasAlmacenadas);

        JLabel consumidasLabel = new JLabel("Galletas consumidas:");
        consumidasLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        almacenInfoPanel.add(consumidasLabel);

        JTextField galletasConsumidas = new JTextField("0", 5);
        galletasConsumidas.setEditable(false);
        galletasConsumidas.setHorizontalAlignment(JTextField.CENTER);
        almacenInfoPanel.add(galletasConsumidas);

        almacenPanel.add(almacenInfoPanel);

        // Añadir los paneles al panel principal
        mainPanel.add(reposterosPanel, BorderLayout.NORTH);
        mainPanel.add(hornosPanel, BorderLayout.CENTER);
        mainPanel.add(almacenPanel, BorderLayout.SOUTH);

        // Añadir el panel principal al marco
        frame.add(mainPanel);

        // Mostrar la interfaz
        frame.setVisible(true);
    }
}



