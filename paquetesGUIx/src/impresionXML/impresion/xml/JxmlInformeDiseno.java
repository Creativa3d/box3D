/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml;

import ListDatos.JServerServidorDatosFichero;
import impresionXML.impresion.xml.diseno.campos.JTCAMPOS;
import java.io.File;

/**
 *
 * @author eduardo
 */
public class JxmlInformeDiseno  extends JxmlAbstract  {
    private String msImagen="";
    private String msListaCamposPredefinidos="";
    private double x;
    private double y;
    private JTCAMPOS moCampos;
    private double mdZOOM=1.5;
    /**
     * @return the msImagen
     */
    public String getImagen() {
        return msImagen;
    }

    /**
     * @param msImagen the msImagen to set
     */
    public void setImagen(String msImagen) {
        String lsOld = this.msImagen;
        this.msImagen = msImagen;
        firePropertyChange("imagen", lsOld, msImagen);
        
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        double Old = this.x;
        this.x = x;
        firePropertyChange("x", Old, x);
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        double Old = this.y;
        this.y = y;
        firePropertyChange("y", Old, y);
    }
    public void crearCamposPredefinidosDebug(){
        moCampos=new JTCAMPOS(new JServerServidorDatosFichero(".", '\t', true));
        
    }
    public void addCampo(String psTipo, String psNombre, String psExplicacion, String psValorDefecto) {
        try {
            if(moCampos!=null){
                moCampos.addNew();
                moCampos.getTIPO().setValue(psTipo);
                moCampos.getNOMBRE().setValue(psNombre);
                moCampos.getEXPLICACION().setValue(psExplicacion);
                moCampos.getVALORPOSIBLE().setValue(psValorDefecto);
                moCampos.update(false);
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void guardarCamposPredefinidosDebug() {
        try {
        if(moCampos!=null){
            ((JServerServidorDatosFichero)moCampos.moList.moServidor).guardar(moCampos.moList);
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public JTCAMPOS getTablaCamposPredefinidos() throws Exception{
        File loFile = new File(msListaCamposPredefinidos);
        JServerServidorDatosFichero loServer = new JServerServidorDatosFichero(loFile.getParent(), '\t', true);
        moCampos = new JTCAMPOS(loServer);
        moCampos.moList.msTabla = loFile.getName();
        moCampos.recuperarTodosNormalSinCache();
        return moCampos;
    }

    /**
     * @return the msListaCamposPredefinidos
     */
    public String getListaCamposPredefinidos() {
        return msListaCamposPredefinidos;
    }

    /**
     * @param msListaCamposPredefinidos the msListaCamposPredefinidos to set
     */
    public void setListaCamposPredefinidos(String msListaCamposPredefinidos) {
        this.msListaCamposPredefinidos = msListaCamposPredefinidos;
    }

    public void setZoom(double pdValor) {
        double Old = mdZOOM;
        mdZOOM = pdValor;
        firePropertyChange("zoom", Old, mdZOOM);
    }
    public double getZoom(){
        return mdZOOM;
    }
}
