/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author eduardo
 */
public class JVentanaFlotante extends JDialog {

    public static final int mclIzq = -1;
    public static final int mclCentro = 0;
    public static final int mclDere = 1;

    public static final int mclArriba = -1;
    public static final int mclAbajo = 1;
    
    
    private long mclMilisegundos=20;
    private final int BARRA_DE_ESTADO = 30; // Tamaño de la barra de estado en windows 
    private JLabel textoPane;
    private Thread moThread;
    private int mlOpacidad=0;
    private int mlPosiH=mclDere;
    private int mlPosiV=mclAbajo;

    public JVentanaFlotante() {
        iniciarComponentes();
        ubicacionVentana();
    }

    public void setPosicionH(int plPosi){
        mlPosiH = plPosi;
    }
    public void setPosicionV(int plPosi){
        mlPosiV = plPosi;
    }
    private void ubicacionVentana() {
        int tamanioX = getWidth();
        int tamanioY = getHeight();
        int maxX = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int maxY = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        int lX=0;
        int lY=0;
        
        if(mlPosiH==mclCentro){
            lX=(maxX - tamanioX)/2;
        }        
        if(mlPosiH==mclDere){
            lX=(maxX - tamanioX);
        }
        if(mlPosiV==mclCentro){
            lY=(maxY - tamanioY)/2;
        }        
        if(mlPosiV==mclAbajo){
            lY=(maxY - tamanioY);
        }
        // ubicacion de la ventana
        setLocation(lX, lY - BARRA_DE_ESTADO);
    }

    private void iniciarComponentes() {

            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    if (g instanceof Graphics2D) {
                        Graphics2D g2d = (Graphics2D)g;
                        g2d.setColor(new Color(255,255,255,mlOpacidad));
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                    }
                }
            };
            setContentPane(panel);
            panel.setLayout(new BorderLayout());

            textoPane = new JLabel();
            
            setAlwaysOnTop(true);                          // siempre arriba
            setPreferredSize(new java.awt.Dimension(280, 120));           // tamaño de la ventana
            setResizable(false);                             // no se puede modificar el tamaño
            setUndecorated(true);                           // no tiene los controles de estado
            
            
            getContentPane().add(textoPane, BorderLayout.CENTER);
            textoPane.setForeground(Color.BLACK);
            
            pack();    
    }

    public void setTexto(String msj) {
        textoPane.setText(msj);
    }
    public void hacerVisibleEInvisible(final int plSegundos) {
        opacidad(0);
        setVisible(true);
        Runnable loRunnable = new Runnable() {
            
            public void run() {
                int ldOp = 0;
                while (ldOp < 255) {
                    opacidad(ldOp);

                    try {
                        Thread.sleep(mclMilisegundos);
                    } catch (InterruptedException ex) {
                    }
                    ldOp += 5f;
                }
                try {
                    Thread.sleep(plSegundos * 1000);
                } catch (InterruptedException ex) {
                }

                ldOp = 255;
                while (ldOp > 0) {
                    opacidad(ldOp);

                    try {
                        Thread.sleep(mclMilisegundos);
                    } catch (InterruptedException ex) {
                    }
                    ldOp -= 5f;
                }
                JVentanaFlotante.this.dispose();
                
            }
        };
        moThread = new Thread(loRunnable);
        moThread.start();        
    }
    
    public void hacerVisible(final int plSegundos) {
        Runnable loRunnable = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(plSegundos * 1000);
                } catch (InterruptedException ex) {
                }

                int ldOp = 0;
                while (ldOp < 255) {
                    opacidad(ldOp);

                    try {
                        Thread.sleep(mclMilisegundos);
                    } catch (InterruptedException ex) {
                    }
                    ldOp += 5f;
                }
            }
        };
        moThread = new Thread(loRunnable);
        moThread.start();
    }
    public void desvanecer(final int plSegundos) {
        Runnable loRunnable = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(plSegundos * 1000);
                } catch (InterruptedException ex) {
                }

                int ldOp = 255;
                while (ldOp > 0) {
                    opacidad(ldOp);

                    try {
                        Thread.sleep(mclMilisegundos);
                    } catch (InterruptedException ex) {
                    }
                    ldOp -= 5f;
                }
                JVentanaFlotante.this.dispose();
            }
        };
        moThread = new Thread(loRunnable);
        moThread.start();
    }

    private void opacidad(final int ldOp) {
        try {
            mlOpacidad=ldOp;

            if(SwingUtilities.isEventDispatchThread()){
                repaint();
            }else{
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
    //                    JVentanaFlotante.this.setBackground(new Color(0, 0, 0, ldOp));
                        repaint();
                    }
                });
            }
        } catch (Exception ex) {
        }

    }

    public static void main(String[] args) {
        JVentanaFlotante loFlo = new JVentanaFlotante();
        loFlo.setTexto("sdlkfnsdl fjsdlk jfslkdjf sldk jfs");
        loFlo.hacerVisibleEInvisible(2);
//        loFlo.setVisible(true);

    }
}
