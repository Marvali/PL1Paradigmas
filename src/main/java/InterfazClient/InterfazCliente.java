package InterfazClient;

import com.mycompany.pl1paradigmas.InterfazRemota;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class InterfazCliente extends JFrame {
    private InterfazRemota servidorRMI; // Objeto remoto
    private Map<String, JTextField> galletasGeneradasCampos = new HashMap<>();
    private Map<String, JTextField> galletasDesperdiciadasCampos = new HashMap<>();
    private JTextField galletasAlmacenadasCampo;
    private JTextField galletasConsumidasCampo;

    public InterfazCliente(InterfazRemota servidorRMI) {
        this.servidorRMI = servidorRMI;
        inicializarUI();
        iniciarActualizador();
    }

    private void inicializarUI() {
        // Configuración básica de la ventana
        setTitle("Fábrica de Galletas - Cliente");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        // Grid para los reposteros
        JPanel reposterosGridPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        for (int i = 1; i <= 5; i++) {
            JLabel label = new JLabel("Repostero" + i, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
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

            JButton actionButton = new JButton("Parar");
            actionButton.setForeground(Color.WHITE);
            actionButton.setBackground(Color.RED);
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
                        actionButton.setBackground(Color.RED);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            });
            reposterosGridPanel.add(actionButton);
        }
        reposterosPanel.add(reposterosGridPanel, BorderLayout.CENTER);

        // Panel para el almacén
        JPanel almacenPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        almacenPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Almacén", 0, 0, new Font("Arial", Font.BOLD, 14)));

        JLabel almacenLabel = new JLabel("Galletas almacenadas:");
        almacenLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        almacenPanel.add(almacenLabel);

        galletasAlmacenadasCampo = new JTextField("0", 5);
        galletasAlmacenadasCampo.setEditable(false);
        galletasAlmacenadasCampo.setHorizontalAlignment(JTextField.CENTER);
        almacenPanel.add(galletasAlmacenadasCampo);

        JLabel consumidasLabel = new JLabel("Galletas consumidas:");
        consumidasLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        almacenPanel.add(consumidasLabel);

        galletasConsumidasCampo = new JTextField("0", 5);
        galletasConsumidasCampo.setEditable(false);
        galletasConsumidasCampo.setHorizontalAlignment(JTextField.CENTER);
        almacenPanel.add(galletasConsumidasCampo);

        // Añadir los paneles al panel principal
        mainPanel.add(reposterosPanel, BorderLayout.NORTH);
        mainPanel.add(almacenPanel, BorderLayout.SOUTH);

        // Añadir el panel principal al marco
        add(mainPanel);
    }

    private void iniciarActualizador() {
        Timer timer = new Timer(2000, e -> {
            try {
                // Actualiza datos del servidor
                for (int i = 1; i <= 5; i++) {
                    galletasGeneradasCampos.get("Repostero" + i).setText(String.valueOf(servidorRMI.getGalletasGeneradas(i - 1)));
                    galletasDesperdiciadasCampos.get("Repostero" + i).setText(String.valueOf(servidorRMI.getGalletasDesperdiciadas(i - 1)));
                }
                galletasAlmacenadasCampo.setText(String.valueOf(servidorRMI.getGalletasAlmacenadas()));
                galletasConsumidasCampo.setText(String.valueOf(servidorRMI.getGalletasComsumidas()));
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        timer.start();
    }
}
