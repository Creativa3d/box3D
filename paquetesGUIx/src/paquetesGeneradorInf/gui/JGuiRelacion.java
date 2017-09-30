/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui;

import paquetesGeneradorInf.gui.util.IPaint;
import ListDatos.JSelectUnionTablas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import utiles.IListaElementos;
import utiles.JListaElementos;


public class JGuiRelacion implements MouseListener, IPaint {
    JSelectUnionTablas moUnion;
    JGuiTabla moTablaPrinci;
    JGuiTabla moTablaRelac;
    JGuiSelectTablas moSelectTablas;

    private JRectasRelacionConj moRectas = new JRectasRelacionConj(this);


    public JGuiRelacion(JGuiSelectTablas poSelectTablas){
        super();
        moSelectTablas = poSelectTablas;
        moSelectTablas.addMouseListener(this);
    }

    public void liberar(){
        moSelectTablas.removeMouseListener(this);
        moRectas.liberar();
        moTablaPrinci = null;
        moTablaRelac = null;
        moUnion = null;
        moSelectTablas.repaint();
        moSelectTablas = null;
    }
    public void setRelacion(
            final JSelectUnionTablas poRelacion,
            final JGuiTabla poTablaPrinci,
            final JGuiTabla poTablaRelac
            ){
        moUnion=poRelacion;
        moTablaPrinci=poTablaPrinci;
        moTablaRelac=poTablaRelac;
        moRectas.crear();
    }


    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()>1){
            moRectas.mouseClicked(e);
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
    public JSelectUnionTablas getRelacion() {
        return moUnion;
    }
    public JGuiTabla getTablaPrinci() {
        return moTablaPrinci;
    }
    public JGuiTabla getTablaRelac() {
        return moTablaRelac;
    }

    public void paint(Graphics g) {
        moRectas.paint(g);
    }

}
class JRectasRelacionConj {
    JGuiRelacion moRelac;
    private IListaElementos moLista =  new JListaElementos();

    JRectasRelacionConj(JGuiRelacion poRelac) {
        moRelac = poRelac;
    }
    public void crear(){
        if(moRelac!=null && moLista!=null){
            moLista.clear();
            for(int i = 0 ; moRelac.moUnion.getCampos1()!=null && i < moRelac.moUnion.getCampos1().length; i++){
                moLista.add(
                        new JRectasRelacion(
                            this,
                            moRelac.moUnion.getCampos1()[i],
                            moRelac.moUnion.getCampos2()[i]));
            }
        }
    }
    public void mouseClicked(MouseEvent e) {
        for(int i = 0 ; i < moLista.size(); i++){
            JRectasRelacion loRect = (JRectasRelacion) moLista.get(i);
            loRect.mouseClicked(e);
        }
    }
    public void paint(Graphics g) {
        for(int i = 0 ; i < moLista.size(); i++){
            JRectasRelacion loRect = (JRectasRelacion) moLista.get(i);
            loRect.paint(g);
        }

    }

    public void liberar() {
        moLista.clear();
        moRelac=null;
    }

}
class JRectasRelacion {
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private boolean mbGrueso = false;
    
    private String msCampo0;
    private String msCampo1;
    private JRectasRelacionConj moConj;
    
    JRectasRelacion(JRectasRelacionConj poConj, String psCampo0, String psCampo1){
        moConj = poConj;
        msCampo0=psCampo0;
        msCampo1=psCampo1;
    }
    
    private void setBounds() {
        
        x1=moConj.moRelac.moTablaPrinci.getX() + moConj.moRelac.moTablaPrinci.getWidth();
        y1 = moConj.moRelac.moTablaPrinci.getY() +
            moConj.moRelac.moTablaPrinci.getPosicionCampo(msCampo0);
        x2=moConj.moRelac.moTablaRelac.getX();
        y2=moConj.moRelac.moTablaRelac.getY() +
            moConj.moRelac.moTablaRelac.getPosicionCampo(msCampo1);

        if(x1>x2){
            x1 -= moConj.moRelac.moTablaPrinci.getWidth();
            x2 += moConj.moRelac.moTablaRelac.getWidth();
        }

    }
    
    public void paint(Graphics g) {
        Graphics2D log2 = (Graphics2D) g;
        setBounds();
        log2.drawLine(
                (int)x1,(int)y1,
                (int)x2,(int)y2
                );
        //dibujamos la flecha en funcion de si es left o right
        if(moConj.moRelac.moUnion.getTipo() != JSelectUnionTablas.mclInner){
            Polygon p = new Polygon();
            if(moConj.moRelac.moUnion.getTipo() == JSelectUnionTablas.mclRight){
                if(x1<x2){
                    p.addPoint((int)x1, (int)y1);
                    p.addPoint((int)x1+5, (int)y1-5);
                    p.addPoint((int)x1+5, (int)y1+5);
                }else{
                    p.addPoint((int)x1, (int)y1);
                    p.addPoint((int)x1-5, (int)y1-5);
                    p.addPoint((int)x1-5, (int)y1+5);
                }
            }else{
                log2.fillRect(
                        (int)x1-2,(int)y1-2,
                        4,4
                        );
            }
            if(moConj.moRelac.moUnion.getTipo() == JSelectUnionTablas.mclLeft){
                if(x2<x1){
                    p.addPoint((int)x2, (int)y2);
                    p.addPoint((int)x2+5, (int)y2-5);
                    p.addPoint((int)x2+5, (int)y2+5);
                }else{
                    p.addPoint((int)x2, (int)y2);
                    p.addPoint((int)x2-5, (int)y2-5);
                    p.addPoint((int)x2-5, (int)y2+5);
                }
            }else{
                log2.fillRect(
                        (int)x2-2,(int)y2-2,
                        4,4
                        );
            }

            log2.fillPolygon(p);
        }else{
            log2.fillRect(
                    (int)x2-2,(int)y2-2,
                    4,4
                    );
            log2.fillRect(
                    (int)x1-2,(int)y1-2,
                    4,4
                    );

        }

        if(mbGrueso){
            log2.drawLine(
                (int)x1+1,(int)y1+1,
                (int)x2+1,(int)y2+1
                );
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()>1){
            double ldXO = e.getX();
            double ldYO = e.getY();
            double ldP = (y2-y1)/(x2-x1);//pendiente
            double ldC = ((y2-y1)/(x2-x1)) * -x1 + y1;//constante
            double ldDistancia =
                    Math.abs(ldP * ldXO -ldYO + ldC) /
                    Math.sqrt(Math.pow(ldP, 2) + 1);
            
            if(ldDistancia < 5 &&
               ((ldXO >= x1 && ldXO <= x2)  ||
                (ldXO >= x2 && ldXO <= x1)  )   ){
                mbGrueso=true;
                try{
                    moConj.moRelac.moSelectTablas.repaint();
                    
                    try {
                        moConj.moRelac.moSelectTablas.mostrarRelacion(moConj.moRelac.moUnion, moConj.moRelac.moTablaPrinci.getTabla().getNombre());
                        moConj.crear();
                    } catch (Exception ex) {
                        utilesGUIx.msgbox.JMsgBox.mensajeError(new JLabel(), ex);
                    }
                }finally{
                    mbGrueso=false;
                    if(moConj!=null && moConj.moRelac!=null && moConj.moRelac.moSelectTablas!=null){
                        moConj.moRelac.moSelectTablas.repaint();
                    }
                }
            }
        }
    }
}
