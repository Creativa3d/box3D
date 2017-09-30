/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion.avisosGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author eduardo
 */
public class JVentanaAvisos extends JDialog {

    private final long mclMilisegundos = 20;
    private final int BARRA_DE_ESTADO = 30; // Tamaño de la barra de estado en windows 
    private JLabel textoPane;
    private Thread moThread;
    private int mlOpacidad = 0;
    public int maxX = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public int maxY = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - BARRA_DE_ESTADO;

    public JVentanaAvisos() {
        iniciarComponentes();
        ubicacionVentana();
    }

    public void ubicacionVentana() {
        int tamanioX = getWidth();
        int tamanioY = getHeight();

        // ubicacion de la ventana
        setLocation(maxX - tamanioX, maxY - tamanioY);
    }

    private void iniciarComponentes() {

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);                          // siempre arriba
        setPreferredSize(new java.awt.Dimension(280, 120));   // tamaño de la ventana
        setResizable(false);                             // no se puede modificar el tamaño
        setUndecorated(true);                           // no tiene los controles de estado
        setFocusable(false);
        setResizable(false);
//        setEnabled(false);
        setFocusableWindowState(false);
        
        //setAutoRequestFocus(false);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2d = (Graphics2D) g;
//                        final int R = 240;
//                        final int G = 240;
//                        final int B = 240;
//
//                        Paint p =
//                            new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0),
//                                0.0f, getHeight(), new Color(R, G, B, 255), true);
//                        g2d.setPaint(p);
                    g2d.setColor(new Color(255, 255, 255, mlOpacidad));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panel.setFocusable(false);
        panel.setRequestFocusEnabled(false);
//        panel.setEnabled(false);
        setContentPane(panel);
        
        panel.setLayout(new BorderLayout());
        textoPane = new JLabel();
        textoPane.setFocusable(false);
        textoPane.setRequestFocusEnabled(false);
        textoPane.setForeground(Color.BLACK);
//        textoPane.setEnabled(false);
        
        getContentPane().add(textoPane, BorderLayout.CENTER);
        

        
        
        pack();
    }

    public void setPanelAviso(JPanelAviso poPanel) {
        getContentPane().removeAll();
        getContentPane().add(poPanel, BorderLayout.CENTER);
        repaint();
    }

    public void hacerVisibleEInvisible(final int plSegundos) {
//        opacidad(0);
        setVisible(true);
        
        Runnable loRunnable = new Runnable() {

            public void run() {
                try {
                    Thread.sleep((plSegundos+2) * 1000);
                } catch (InterruptedException ex) {}
//
//                int ldOp = 0;
//                while (ldOp < 255) {
//                    opacidad(ldOp);
//
//                    try {
//                        Thread.sleep(mclMilisegundos);
//                    } catch (InterruptedException ex) {
//                    }
//                    ldOp += 5f;
//                }
//                try {
//                    Thread.sleep(plSegundos * 1000);
//                } catch (InterruptedException ex) {
//                }
//
//                ldOp = 255;
//                while (ldOp > 0) {
//                    opacidad(ldOp);
//
//                    try {
//                        Thread.sleep(mclMilisegundos);
//                    } catch (InterruptedException ex) {
//                    }
//                    ldOp -= 5f;
//                }
                JVentanaAvisos.this.setVisible(false);
                JVentanaAvisos.this.dispose();

            }
        };
        moThread = new Thread(loRunnable);
        moThread.start();
    }

    private void opacidad(final int ldOp) {
        try {
            mlOpacidad = ldOp;
            if(SwingUtilities.isEventDispatchThread()){
                repaint();
            }else{
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        repaint();
                    }
                });
            }

        } catch (Exception ex) {
        }

    }

    public static void main(String[] args) {
        JVentanaAvisos loFlo = new JVentanaAvisos();
        loFlo.setPanelAviso(new JPanelAviso());
        loFlo.hacerVisibleEInvisible(2);
//        loFlo.setVisible(true);

    }
}
