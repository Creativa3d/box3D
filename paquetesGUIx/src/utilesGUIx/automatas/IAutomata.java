/*
 * IAutomata.java
 *
 * Created on 13 de agosto de 2005, 17:36
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesGUIx.automatas;

import utilesGUIx.JTextFieldCZ;

/**
 *
 * @author chema
 * Esta es una interface que permite implementar automatas para un JTextFieldsCZ
 */
public interface IAutomata extends java.awt.event.KeyListener {
//    public void accion(java.awt.event.KeyEvent evt);
    public int getCodigo();
    public String getNombre();
    public void setAccionesForm(IAccionesAutomata poForm);
    public void setTextPadre(JTextFieldCZ poText);
    public void setEstado(int plEstado);
    public void setTecla(int pnTeclaI, char pcTeclaC);
    public boolean getCtrl();
}
