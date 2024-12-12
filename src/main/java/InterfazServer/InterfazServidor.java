package InterfazServer;

import com.mycompany.pl1paradigmas.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterfazServidor extends JFrame {

    private final Fabrica fabrica;
    // Componentes de la Cafetera
    private JTextField txtCafetera;

    // Reposteros
    private JPanel panelReposteros;
    private JTextField[] txtReposteros; // muestra estado de cada repostero
    private JLabel[] lblEstadoCafé;    // etiqueta para "Pausa para el café"

    // Hornos
    private JPanel panelHornos;
    private JTextField[] txtGalletasHorno;    // campo para indicar la cantidad de galletas
    private JProgressBar[] pbHorneado;        // barra de progreso del horneado
    private JLabel[] lblEstadoHorno;          // etiqueta para estado (Horneando, Esperando, etc.)

    // Empaquetadores
    private JPanel panelEmpaquetadores;
    private JTextField[] txtEstadoEmpaquetador;
    private JPanel[] panelCajasEmpaquetador;      // Panel para las cajas de 20 galletas
    private JLabel[][] lblCajasEmpaquetador;      // Representación gráfica de las cajas

    // Almacén
    private JPanel panelAlmacen;
    private JTextField txtAlmacen;
    private JButton btnComerGalletas;



    public InterfazServidor(Fabrica fabrica) {
        // Guardar referencia a la fábrica
        this.fabrica = fabrica;
        // Configuración general
        setTitle("Simulación Fábrica de Galletas - Servidor");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cambiar el esquema de colores
        Color fondoAzul = new Color(0x264653); // Azul oscuro (fondo principal)
        Color panelMarron = new Color(0x6b4026); // Marrón para los paneles 0x6b4026
        Color botonColor = new Color(0x875a34);  // Marrón claro
        Color textoClaro = Color.WHITE;         // Texto claro para contrastar

        // Fondo principal de la aplicación
        getContentPane().setBackground(fondoAzul);

        // Panel superior: Cafetera y Reposteros
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(fondoAzul);

        // Panel Cafetera
        JPanel panelCafetera = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Font titleFont = new Font("Arial", Font.BOLD, 24); // Create a Font object with the desired size
        panelCafetera.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Cafetera", 2, 2, titleFont, textoClaro));//poner font un poco mas grande

        panelCafetera.setBackground(fondoAzul);
        JLabel lblCafetera = new JLabel("Preparando café: ");
        lblCafetera.setForeground(textoClaro);
        txtCafetera = new JTextField(20);
        txtCafetera.setEditable(false);
        // Ejemplo de estado
        txtCafetera.setText("Repostero 2");
        txtCafetera.setBackground(Color.BLACK);
        txtCafetera.setForeground(textoClaro);
        panelCafetera.add(lblCafetera);
        panelCafetera.add(txtCafetera);


        // Panel Reposteros
        panelReposteros = new JPanel();
        panelReposteros.setLayout(new GridLayout(1, 5));
        panelReposteros.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Reposteros", 2, 2, null, textoClaro));
        panelReposteros.setBackground(panelMarron);

        txtReposteros = new JTextField[5];
        lblEstadoCafé = new JLabel[5];

        for (int i = 0; i < 5; i++) {
            JPanel p = new JPanel(new BorderLayout());
            p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Repostero " + (i + 1), 0, 0, null, textoClaro));
            p.setBackground(panelMarron);

            txtReposteros[i] = new JTextField("Produciendo (3/4)"); // Ejemplo de estado
            txtReposteros[i].setEditable(false);
            txtReposteros[i].setBackground(Color.BLACK);
            txtReposteros[i].setForeground(textoClaro);
            lblEstadoCafé[i] = new JLabel("Pausa para el café");
            lblEstadoCafé[i].setForeground(textoClaro);

            p.add(txtReposteros[i], BorderLayout.CENTER);
            p.add(lblEstadoCafé[i], BorderLayout.SOUTH);
            panelReposteros.add(p);
        }

        panelSuperior.add(panelCafetera, BorderLayout.NORTH);
        panelSuperior.add(panelReposteros, BorderLayout.CENTER);

        // Panel central: Hornos y Empaquetadores
        JPanel panelCentral = new JPanel(new GridLayout(1, 2));
        panelCentral.setBackground(fondoAzul);

        // Panel Hornos
        panelHornos = new JPanel(new GridLayout(3, 1));
        panelHornos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Hornos", 2, 2, null, textoClaro));
        panelHornos.setBackground(fondoAzul);

        txtGalletasHorno = new JTextField[3];
        pbHorneado = new JProgressBar[3];
        lblEstadoHorno = new JLabel[3];

        for (int i = 0; i < 3; i++) {
            JPanel ph = new JPanel(new GridLayout(3, 1));
            ph.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Horno " + (i + 1), 0, 0, null, textoClaro));
            ph.setBackground(panelMarron);

            JPanel phTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
            phTop.setBackground(panelMarron);
            JLabel lblGalletas = new JLabel("Número de galletas: ");
            lblGalletas.setForeground(textoClaro);
            phTop.add(lblGalletas);

            txtGalletasHorno[i] = new JTextField("0", 5);
            txtGalletasHorno[i].setEditable(false);
            txtGalletasHorno[i].setBackground(Color.BLACK);
            txtGalletasHorno[i].setForeground(textoClaro);
            phTop.add(txtGalletasHorno[i]);

            lblEstadoHorno[i] = new JLabel("Esperando");
            lblEstadoHorno[i].setForeground(textoClaro);

            pbHorneado[i] = new JProgressBar(0, 100);
            pbHorneado[i].setStringPainted(true);
            pbHorneado[i].setValue(0);
            pbHorneado[i].setBackground(Color.BLACK);
            pbHorneado[i].setForeground(textoClaro);

            // Asignar barra de progreso al horno correspondiente
            fabrica.getHorno(i).setBarraProgreso(pbHorneado[i]);

            ph.add(phTop);
            ph.add(lblEstadoHorno[i]);
            ph.add(pbHorneado[i]);

            panelHornos.add(ph);
        }

        // Panel Empaquetadores
        panelEmpaquetadores = new JPanel(new GridLayout(3, 1));
        panelEmpaquetadores.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Empaquetadores", 2, 2, null, textoClaro));
        panelEmpaquetadores.setBackground(fondoAzul);
        txtEstadoEmpaquetador = new JTextField[3];
        panelCajasEmpaquetador = new JPanel[3];
        lblCajasEmpaquetador = new JLabel[3][5]; // 5 cajas de 20 galletas cada una

        for (int i = 0; i < 3; i++) {
            JPanel pe = new JPanel(new BorderLayout());
            pe.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Empaquetador " + (i + 1), 0, 0, null, textoClaro));
            pe.setBackground(panelMarron);

            txtEstadoEmpaquetador[i] = new JTextField("Esperando");
            txtEstadoEmpaquetador[i].setEditable(false);
            txtEstadoEmpaquetador[i].setBackground(Color.BLACK);
            txtEstadoEmpaquetador[i].setForeground(textoClaro);
            pe.add(txtEstadoEmpaquetador[i], BorderLayout.NORTH);

            panelCajasEmpaquetador[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelCajasEmpaquetador[i].setBackground(panelMarron);
            // Representación de cajas llenas/vacías (por ahora vacías)
            for (int j = 0; j < 5; j++) {
                lblCajasEmpaquetador[i][j] = new JLabel();
                lblCajasEmpaquetador[i][j].setOpaque(true);
                lblCajasEmpaquetador[i][j].setBackground(Color.DARK_GRAY);
                lblCajasEmpaquetador[i][j].setPreferredSize(new Dimension(20, 20));
                panelCajasEmpaquetador[i].add(lblCajasEmpaquetador[i][j]);
            }

            pe.add(panelCajasEmpaquetador[i], BorderLayout.CENTER);
            panelEmpaquetadores.add(pe);
        }

        panelCentral.add(panelHornos);
        panelCentral.add(panelEmpaquetadores);

        // Panel inferior: Almacén
        panelAlmacen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAlmacen.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(textoClaro), "Almacén", 0, 0, null, textoClaro));
        panelAlmacen.setBackground(panelMarron);
        JLabel lblAlmacen = new JLabel("Galletas en almacén:");
        lblAlmacen.setForeground(textoClaro);
        panelAlmacen.add(lblAlmacen);
        txtAlmacen = new JTextField("500", 5);
        txtAlmacen.setEditable(false);
        txtAlmacen.setBackground(Color.BLACK);
        txtAlmacen.setForeground(textoClaro);
        panelAlmacen.add(txtAlmacen);

        btnComerGalletas = new JButton("Comer 100 galletas");
        btnComerGalletas.setBackground(botonColor);
        btnComerGalletas.setForeground(textoClaro);
        panelAlmacen.add(btnComerGalletas);

        btnComerGalletas.addActionListener(e -> {
            fabrica.comerGalletas();
        });

        // Añadimos todo al JFrame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelAlmacen, BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        iniciarHiloActualizacion();
    }
/*
    private void iniciarHiloActualizacion() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> actualizarInterfaz());
        }, 0, 100, TimeUnit.MILLISECONDS); // Actualizar cada 100 milisegundos
    }

 */
private void iniciarHiloActualizacion() {
    Timer timer = new Timer(100, e -> actualizarInterfaz()); // Update every 100 milliseconds
    timer.start();
}

    private void actualizarInterfaz() {

        // Actualizar estado de los hornos
        for (int i = 0; i < txtGalletasHorno.length; i++) {

            // Actualizar estado de las galletas en el horno
            int galletas = fabrica.getGalletasHorno(i);
            txtGalletasHorno[i].setText(String.valueOf(galletas));

            // Actualizar estado de la barra de progreso
            int progreso = fabrica.getBarraProgresoHorno(i);
            pbHorneado[i].setValue(progreso);

            // Actualizar estado del horno
            if (progreso == 100) {
                lblEstadoHorno[i].setText("Acabado");
            } else if (progreso > 0) {
                lblEstadoHorno[i].setText("Horneando");
            } else {
                lblEstadoHorno[i].setText("Esperando");
            }
        }
        // Actualizar estado de la cafetera
        txtCafetera.setText(fabrica.getReposteroEnCafetera());

            // Actualizar estado de los reposteros
            for (int j = 0; j < 5; j++) {
                int tandasProducidas = fabrica.getRepostero(j).getTandasProducidas();
                int tandasAProducir = fabrica.getRepostero(j).getTandasAProducir();
                txtReposteros[j].setText("Produciendo (" + tandasProducidas + "/" + tandasAProducir + ")");

                if (fabrica.getReposteroEnCafetera().equals("repostero " + (j + 1))) {

                    lblEstadoCafé[j].setForeground(Color.RED);
                } else {
                    lblEstadoCafé[j].setForeground(Color.WHITE);
                }
            }



// Actualizar estado de los empaquetadores
        for (int j = 0; j < 3; j++) {
            txtEstadoEmpaquetador[j].setText(fabrica.getEstadoEmpaquetador(j));
            int cajasLlenas = fabrica.getLotesEmpaquetados(j);
            for (int k = 0; k < 5; k++) {
                if (k < cajasLlenas) {
                    lblCajasEmpaquetador[j][k].setBackground(Color.GREEN);
                } else {
                    lblCajasEmpaquetador[j][k].setBackground(Color.DARK_GRAY);
                }
            }
        }

        //almacen
         //actualizar las galletas del almacen
        txtAlmacen.setText(String.valueOf(fabrica.getCantidadGalletasAlmacen()));
        //actualizar el boton de comer galletas
        if (fabrica.getCantidadGalletasAlmacen() < 100) {
            btnComerGalletas.setEnabled(false);
        } else {
            btnComerGalletas.setEnabled(true);
        }











    }
    //




}
