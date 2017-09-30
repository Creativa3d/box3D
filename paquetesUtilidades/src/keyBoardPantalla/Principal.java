/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keyBoardPantalla;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

public class Principal extends JFrame{

 JTextField txtusu=new JTextField(10);
 JPasswordField txtclave=new JPasswordField(10);
 JPopupMenu pop;
 JKeyboardPane teclado;
 
 public Principal(){
 
  colocarSkin();
 
  JPanel pafuera=new JPanel();
  JPanel p=new JPanel(new GridLayout(2,2,0,0));
  pafuera.setPreferredSize(new Dimension(200,30));
  p.add(new JLabel("Usuario: "));
  p.add(txtusu);
  p.add(new JLabel("Clave: "));
  p.add(txtclave);
 
  txtclave.addFocusListener(new FocusListener(){

   @Override
   public void focusGained(FocusEvent arg0) {
    pop = new JPopupMenu();
    teclado=new JKeyboardPane(txtclave);
    pop.add(teclado);
    pop.setVisible(true);
    pop.setLocation(txtclave.getLocationOnScreen().x+112, txtclave.getLocationOnScreen().y-1);
   }

   @Override
   public void focusLost(FocusEvent arg0) {
    pop.setVisible(false);
   }
   
  });
  txtusu.addFocusListener(new FocusListener(){

   @Override
   public void focusGained(FocusEvent arg0) {
    pop = new JPopupMenu();
    teclado=new JKeyboardPane(txtusu);
    pop.add(teclado);
    pop.setVisible(true);
    pop.setLocation(txtusu.getLocationOnScreen().x+112, txtusu.getLocationOnScreen().y-1);
   }

   @Override
   public void focusLost(FocusEvent arg0) {
    pop.setVisible(false);
   }
   
  });
 
  pafuera.add(p);
  add(pafuera);
 
 }
 
 public void colocarSkin(){
  try {
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
   } catch (ClassNotFoundException e) {
    e.printStackTrace();
   } catch (InstantiationException e) {
    e.printStackTrace();
   } catch (IllegalAccessException e) {
    e.printStackTrace();
   } catch (UnsupportedLookAndFeelException e) {
    e.printStackTrace();
   }
 }
 
 public static void main(String arg[]){
  Principal p=new Principal();
  p.setVisible(true);
  p.setBounds(0, 0, 300, 200);
  p.setLocationRelativeTo(null);
  p.setDefaultCloseOperation(EXIT_ON_CLOSE);
 }
 
}
