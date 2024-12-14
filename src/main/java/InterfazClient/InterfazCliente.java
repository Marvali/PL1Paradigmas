package InterfazClient;

import LogicaServidor.InterfazRemota;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class InterfazCliente extends JFrame {
    private InterfazRemota servidorRMI; // Objeto remoto
    private Map<String, JTextField> galletasGeneradasCampos = new HashMap<>();
    private Map<String, JTextField> galletasDesperdiciadasCampos = new HashMap<>();
    private Map<String, JTextField> galletasHorneadasCampos = new HashMap<>();
    private JTextField galletasAlmacenadasCampo;
    private JTextField galletasConsumidasCampo;
    private Map<String, JProgressBar> barrasProgresoHornos = new HashMap<>();

    public InterfazCliente(InterfazRemota servidorRMI) {
        this.servidorRMI = servidorRMI;
        inicializarUI();
        iniciarActualizador();
    }

    private void inicializarUI() {
        // Configuración básica de la ventana
        setTitle("Fábrica de Galletas - Cliente");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Colores personalizados
        Color fondoAzul = new Color(0x264653);
        Color panelMarron = new Color(0x6b4026);
        Color botonColor = new Color(0x875a34);
        Color textoClaro = Color.WHITE;

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondoAzul);

        // Panel para los reposteros
        JPanel reposterosPanel = new JPanel(new BorderLayout(10, 10));
        reposterosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Reposteros", 0, 0, new Font("Arial", Font.BOLD, 14)));
        reposterosPanel.setBackground(panelMarron);

        // Encabezados
        JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        headerPanel.setBackground(panelMarron);
        headerPanel.add(crearEtiqueta("Repostero", textoClaro));
        headerPanel.add(crearEtiqueta("Galletas generadas", textoClaro));
        headerPanel.add(crearEtiqueta("Galletas desperdiciadas", textoClaro));
        headerPanel.add(crearEtiqueta("Acción", textoClaro));
        reposterosPanel.add(headerPanel, BorderLayout.NORTH);

        // Grid para los reposteros
        JPanel reposterosGridPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        reposterosGridPanel.setBackground(panelMarron);
        for (int i = 1; i <= 5; i++) {
            JLabel label = crearEtiqueta("Repostero" + i, textoClaro);
            reposterosGridPanel.add(label);

            JTextField galletasGeneradas = new JTextField("0", 5);
            galletasGeneradas.setEditable(false);
            galletasGeneradas.setHorizontalAlignment(JTextField.CENTER);
            reposterosGridPanel.add(galletasGeneradas);
            galletasGeneradasCampos.put("Repostero" + i, galletasGeneradas);

            JTextField galletasDesperdiciadas = new JTextField("0", 5);
            galletasDesperdiciadas.setEditable(false);
            galletasDesperdiciadas.setHorizontalAlignment(JTextField.CENTER);
            reposterosGridPanel.add(galletasDesperdiciadas);
            galletasDesperdiciadasCampos.put("Repostero" + i, galletasDesperdiciadas);

            // Botón para pausar/reanudar el repostero
            JButton actionButton = new JButton("Parar");
            actionButton.setForeground(textoClaro);
            actionButton.setBackground(botonColor);
            int idRepostero = i - 1; // Ajustar índice para el servidor
            actionButton.addActionListener(e -> {
                try {
                    if (actionButton.getText().equals("Parar")) {
                        servidorRMI.pausarRepostero(idRepostero);
                        actionButton.setText("Reanudar");
                        actionButton.setBackground(Color.GREEN);
                    } else {
                        servidorRMI.reanudarRepostero(idRepostero);
                        actionButton.setText("Parar");
                        actionButton.setBackground(botonColor);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            });
            reposterosGridPanel.add(actionButton);
        }
        reposterosPanel.add(reposterosGridPanel, BorderLayout.CENTER);

        // Panel para los hornos
        JPanel hornosPanel = new JPanel(new BorderLayout(10, 10));
        hornosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Hornos", 0, 0, new Font("Arial", Font.BOLD, 14)));
        hornosPanel.setBackground(panelMarron);

        JPanel hornosGridPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        hornosGridPanel.setBackground(panelMarron);
        for (int i = 1; i <= 3; i++) {
            JLabel label = crearEtiqueta("Horno" + i, textoClaro);
            hornosGridPanel.add(label);

            JTextField galletasHorneadas = new JTextField("0", 5);
            galletasHorneadas.setEditable(false);
            galletasHorneadas.setHorizontalAlignment(JTextField.CENTER);
            hornosGridPanel.add(galletasHorneadas);
            galletasHorneadasCampos.put("Horno" + i, galletasHorneadas);

            JProgressBar barraProgreso = new JProgressBar(0, 100);
            barraProgreso.setValue(0); // Inicialmente, 0%
            barraProgreso.setStringPainted(true);
            barraProgreso.setForeground(Color.WHITE);
            barraProgreso.setBackground(Color.DARK_GRAY);
            barraProgreso.setPreferredSize(new Dimension(300, 15)); // Más fina y alargada
            hornosGridPanel.add(barraProgreso);
            barrasProgresoHornos.put("Horno" + i, barraProgreso);
        }
        hornosPanel.add(hornosGridPanel, BorderLayout.CENTER);

        // Panel para el almacén
        JPanel almacenPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        almacenPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Almacén", 0, 0, new Font("Arial", Font.BOLD, 14)));
        almacenPanel.setBackground(panelMarron);

        almacenPanel.add(crearEtiqueta("Galletas almacenadas:", textoClaro));

        galletasAlmacenadasCampo = new JTextField("0", 5);
        galletasAlmacenadasCampo.setEditable(false);
        galletasAlmacenadasCampo.setHorizontalAlignment(JTextField.CENTER);
        almacenPanel.add(galletasAlmacenadasCampo);

        almacenPanel.add(crearEtiqueta("Galletas consumidas:", textoClaro));

        galletasConsumidasCampo = new JTextField("0", 5);
        galletasConsumidasCampo.setEditable(false);
        galletasConsumidasCampo.setHorizontalAlignment(JTextField.CENTER);
        almacenPanel.add(galletasConsumidasCampo);

        // Añadir los paneles al panel principal
        mainPanel.add(reposterosPanel, BorderLayout.NORTH);
        mainPanel.add(hornosPanel, BorderLayout.CENTER);
        mainPanel.add(almacenPanel, BorderLayout.SOUTH);

        // Añadir el panel principal al marco
        add(mainPanel);
    }
    // Crear una etiqueta con el texto y color especificados
    private JLabel crearEtiqueta(String texto, Color colorTexto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 12));
        etiqueta.setForeground(colorTexto);
        return etiqueta;
    }

    private void iniciarActualizador() {
        Timer timer = new Timer(1000, e -> {
            try {
                // Actualiza datos del servidor
                // Reposteros, galleras generadas y desperdiciadas
                for (int i = 1; i <= 5; i++) {
                    galletasGeneradasCampos.get("Repostero" + i).setText(String.valueOf(servidorRMI.getGalletasGeneradas(i - 1)));
                    galletasDesperdiciadasCampos.get("Repostero" + i).setText(String.valueOf(servidorRMI.getGalletasDesperdiciadas(i - 1)));
                }

                // Hornos, galletas horneadas y barras de progreso
                for (int i = 1; i <= 3; i++) {
                    galletasHorneadasCampos.get("Horno" + i).setText(String.valueOf(servidorRMI.getGalletasHorneadas(i - 1)));
                    int progreso = servidorRMI.getEstadoHorno(i - 1);
                    barrasProgresoHornos.get("Horno" + i).setValue(progreso);
                }
                // Almacén, galletas almacenadas y consumidas
                galletasAlmacenadasCampo.setText(String.valueOf(servidorRMI.getGalletasAlmacenadas()));
                galletasConsumidasCampo.setText(String.valueOf(servidorRMI.getGalletasComsumidas()));
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        timer.start();
    }
}
