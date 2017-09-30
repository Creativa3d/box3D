/*

 * JRelojDigital1.java

 *

 * Created on 5 de junio de 2005, 15:49

 */



package utilesGUIx.reloj;



/**

 *

 * @author  chema

 */

import java.awt.*;

import java.awt.event.*;

import java.lang.*;

import java.util.*;

import java.applet.*;

import javax.swing.*;

import java.beans.*;

import utiles.timer.*;



public class Prueba extends javax.swing.JFrame implements BeanInfo, ITemporizador {



    private JTimer loTimer;

    

    //inicializar la hora

    public void init() { 

        loTimer = new JTimer(this);         

        loTimer.start();

    }   

    

    /** Creates new form JRelojDigital1 */

    public Prueba() {

        initComponents();

        init();

    }

    

    /** This method is called from within the constructor to

     * initialize the form.

     * WARNING: Do NOT modify this code. The content of this method is

     * always regenerated by the Form Editor.

     */

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        horaActual = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        getContentPane().add(horaActual, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-91)/2, (screenSize.height-46)/2, 91, 46);
    }// </editor-fold>//GEN-END:initComponents

    

    /** Exit the Application */

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm

        System.exit(0);

    }//GEN-LAST:event_exitForm

    

    /**

     * @param args the command line arguments

     */

    public static void main(String args[]) {

        new Prueba().show();

    }

    

    public BeanInfo[] getAdditionalBeanInfo() {

        return null;

    }

    

    public BeanDescriptor getBeanDescriptor() {

        return null;

    }

    

    public int getDefaultEventIndex() {

        return -1;

    }

    

    public int getDefaultPropertyIndex() {

        return -1;

    }

    

    public EventSetDescriptor[] getEventSetDescriptors() {

        return null;

    }

    

    public java.awt.Image getIcon(int iconKind) {

        return null;

    }

    

    public MethodDescriptor[] getMethodDescriptors() {

        return null;

    }

    

    public PropertyDescriptor[] getPropertyDescriptors() {

        return null;

    }

    

    public void timerArrancado(JTimer JTimer) {

    }

    

    public void timerIntervalo(JTimer JTimer) {

        Date loActual = new Date();

        int horas = loActual.getHours();

        int minutos = loActual.getMinutes();

        int segundos = loActual.getSeconds();

        int hora;

        String tiempo = "";



        if( horas > 12 ) 

            hora = horas - 12;

        else

            hora = horas;

        if( hora < 10 )

            tiempo += "0";

        tiempo += hora;

        tiempo += ":";



        if( minutos < 10 )

            tiempo += "0";

        tiempo += minutos + ":";



        if( segundos < 10 )

            tiempo += "0";

        tiempo += segundos;



        if( horas > 12 )

            tiempo += " pm";

        else

            tiempo += " am";



        horaActual.setText(tiempo);

    }

    

    public void timerMuerto(JTimer JTimer) {

    }

    

    public void timerParado(JTimer JTimer) {

    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel horaActual;
    // End of variables declaration//GEN-END:variables

    

}
