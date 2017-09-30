/*
 * JLabelCZ.java
 *
 * Created on 10 de febrero de 2007, 10:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx;

import ListDatos.estructuraBD.JFieldDef;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JLabel;
import utiles.JDepuracion;
import utilesGUIx.navegadorWeb.JFormNavegador;

public class JLabelCZ extends JLabel implements MouseListener{
    private static final long serialVersionUID = 1L;
    
    private boolean mbLabelHTMLDefecto;
    private JFieldDef moCampo;
    private String msAyuda;
    
    public JLabelCZ(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        try{
        mbLabelHTMLDefecto = JGUIxConfigGlobal.getInstancia().isLabelHTMLDefecto();
        }catch(Throwable e){}
    }
    public JLabelCZ(String text, int horizontalAlignment) {
        super(text, null, horizontalAlignment);
        try{
        mbLabelHTMLDefecto = JGUIxConfigGlobal.getInstancia().isLabelHTMLDefecto();
        }catch(Throwable e){}
    }
    public JLabelCZ(String text) {
        super(text);
        try{
        mbLabelHTMLDefecto = JGUIxConfigGlobal.getInstancia().isLabelHTMLDefecto();
        }catch(Throwable e){}
    }

    public JLabelCZ(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        try{
        mbLabelHTMLDefecto = JGUIxConfigGlobal.getInstancia().isLabelHTMLDefecto();
        }catch(Throwable e){}
    }
    public JLabelCZ(Icon image) {
        super(image);
        try{
        mbLabelHTMLDefecto = JGUIxConfigGlobal.getInstancia().isLabelHTMLDefecto();
        }catch(Throwable e){}
    }
    public JLabelCZ() {
        try{
        mbLabelHTMLDefecto = JGUIxConfigGlobal.getInstancia().isLabelHTMLDefecto();
        }catch(Throwable e){}
    }
    public JFieldDef getField(){
        return moCampo;
    }
    
    public void setField(final JFieldDef poCampo){
        moCampo = poCampo;
        //ayuda o no
        msAyuda = null;
        if(JGUIxConfigGlobal.getInstancia().getAyudaURLLabels()!=null) {
            msAyuda =JGUIxConfigGlobal.getInstancia().getAyudaURLLabels().getCaption(poCampo.getTabla(), poCampo.getNombre());
            if(msAyuda!=null && !msAyuda.equals("") && !msAyuda.equalsIgnoreCase(poCampo.getNombre())){
            }else{
                msAyuda=null;
            }
        }
        if(msAyuda!=null){
            if(JGUIxConfigGlobal.getInstancia().getAyudaURLLabels()!=null) {
                removeMouseListener(this);
                addMouseListener(this);
            }
            setText("<html><p>"+poCampo.getCaption()+" "
                    + "<span style=\"text-decoration: underline; font-weight: bold; color: rgb(40, 55, 253);\">?</span>"
                    + "</p>"
                    + "</html>");
        }else{
            if(mbLabelHTMLDefecto){
                setText("<html><p>"+poCampo.getCaption()+"</p></html>");
            }else{
                setText(poCampo.getCaption());
            }
        }
        //tool tip
        boolean lbPuesto = false;
        if(JGUIxConfigGlobal.getInstancia().getToolTipTextLabels()!=null) {
            String lsToolTip =JGUIxConfigGlobal.getInstancia().getToolTipTextLabels().getCaption(poCampo.getTabla(), poCampo.getNombre());
            if(lsToolTip!=null && !lsToolTip.equals("") && !lsToolTip.equalsIgnoreCase(poCampo.getNombre())){
                setToolTipText(lsToolTip);
                lbPuesto = true;
            }
        }
        if(!lbPuesto){
            if(mbLabelHTMLDefecto){
                setToolTipText("<html><p>"+poCampo.getCaption()+"</p></html>");
            }else{
                setToolTipText(poCampo.getCaption());
            }
        }
        setForeground((poCampo.getNullable()?null:JGUIxConfigGlobal.getInstancia().getLabelColorObligatorio()));
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getPoint().getX()> (this.getWidth()-5)){
            try {
                JFormNavegador loNavegador = new JFormNavegador(msAyuda);
                loNavegador.setVisible(true);
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JLabelCZ.class.getName(), ex);
            }
                    
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    
}
