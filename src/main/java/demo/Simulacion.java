package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulacion extends JFrame {

    private JTextField campoCliente;
    private JTextField campoMonto;
    private JButton botonPagar;
    private JLabel etiquetaResultado;

    public Simulacion() {
        crearInterfaz();
        configurarVentana();
    }

    private void crearInterfaz() {
        campoCliente = new JTextField(20);
        campoMonto = new JTextField(10);
        botonPagar = new JButton("Pagar");
        etiquetaResultado = new JLabel(" ");

        setLayout(new GridLayout(4, 2));

        add(new JLabel("Cliente:"));
        add(campoCliente);
        add(new JLabel("Monto:"));
        add(campoMonto);
        add(botonPagar);
        add(etiquetaResultado);

        botonPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarPago();
            }
        });
    }

    private void configurarVentana() {
        setTitle("Simulación de Pago");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void procesarPago() {
        String cliente = campoCliente.getText();
        String montoStr = campoMonto.getText();
        double monto;

        try {
            if (cliente == null || cliente.isEmpty()) {
                throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
            }

            monto = Double.parseDouble(montoStr);

            // Simulación de pago
            boolean exito = procesarPagoSimulado(cliente, monto);

            if (exito) {
                etiquetaResultado.setText("Pago realizado con éxito");
            } else {
                etiquetaResultado.setText("Error en el pago");//cambia el mensaje de error
            }

        } catch (NumberFormatException e) {
            etiquetaResultado.setText("Monto inválido");
        } catch (IllegalArgumentException e) {
            etiquetaResultado.setText(e.getMessage());
        } catch (RuntimeException e) {
            etiquetaResultado.setText("Ocurrió un error inesperado");
        }
        
        etiquetaResultado.revalidate();
        repaint();
    }

    private boolean procesarPagoSimulado(String cliente, double monto) {
        // Aquí podrías implementar la lógica real de simulación de pago
        return Math.random() > 0.5; // Simulación aleatoria de éxito o fallo en el pago
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Simulacion().setVisible(true);
            }
        });
    }
}
