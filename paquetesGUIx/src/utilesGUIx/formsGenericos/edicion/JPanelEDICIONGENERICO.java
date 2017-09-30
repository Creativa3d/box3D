/*
 * JPanelACU.java
 *
 * Created on 4 de noviembre de 2004, 8:47
 */

package utilesGUIx.formsGenericos.edicion;

import java.awt.*;

import ListDatos.*;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.*;

public class JPanelEDICIONGENERICO extends JPanelEdicionAbstract {
    private JListDatos moDatos;
    private IPanelControlador moPadre;
    
    /** Creates new form JPanelZONAS */
    public JPanelEDICIONGENERICO() {
        super();
        initComponents();
//        setBackground(new Color(0f,0f,0f,0f));
//        setOpaque(false);
        
    }
    public void setDatos(JListDatos poDatos, IPanelControlador poPadre) throws Exception {
        moDatos = poDatos;
        moPadre = poPadre;
    }

    public String getTitulo() {
        String lsTitulo="";    
        if(moDatos.getModoTabla() == JListDatos.mclNuevo) {
            lsTitulo=moDatos.msTabla + " [Nuevo]" ;
        }else{
            lsTitulo=moDatos.msTabla + " [Edicion]";
        }
        return lsTitulo;
    }
    
    public void rellenarPantalla() throws Exception {
        for(int i=0;i<moDatos.getFields().count();i++ ){
            crearLabel(moDatos.getFields().get(i).getCaption());
            crearText(
                String.valueOf(i),
                moDatos.getFields().get(i).getTipo(),
                false, 1, true, "");
        }
    }
    private void crearLabel(String psCaption){
        javax.swing.JLabel loLabel = new javax.swing.JLabel(psCaption);
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel1.add(loLabel, gridBagConstraints);
    }
    private void crearText(String psCodigo, int plTipoBD, boolean pbCompuesto, int plFilas, boolean pbEsFinal, String psListaValores){
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        if(pbCompuesto){
            gridBagConstraints.gridwidth = 1;
        }else{
            gridBagConstraints.gridwidth = 2;
        }
        if(pbEsFinal){
            gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        }
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.gridheight = plFilas;
        if(plFilas>1){
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weighty = 0.1;
        }else{
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        }
        if(plTipoBD == JListDatos.mclTipoBoolean){
            utilesGUIx.JCheckBoxCZ loCheck =  new utilesGUIx.JCheckBoxCZ();
            loCheck.setName(psCodigo);
            jPanel1.add(loCheck, gridBagConstraints);
        }else{
            if(psListaValores.compareTo("")==0){
                if(plFilas==1){
                    utilesGUIx.JTextFieldCZ loText = new utilesGUIx.JTextFieldCZ();
                    loText.setName(psCodigo);
                    loText.setValueTabla("");
                    loText.setTipoBD(plTipoBD);
                    jPanel1.add(loText, gridBagConstraints);
                }else{
                    javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
                    utilesGUIx.JTextAreaCZ loArea = new utilesGUIx.JTextAreaCZ();
                    loArea.setTipoBD(plTipoBD);
                    loArea.setValueTabla("");
                    loArea.setName(psCodigo);
                    jScrollPane2.setName(psCodigo);
                    jScrollPane2.setViewportView(loArea);
                    jPanel1.add(jScrollPane2, gridBagConstraints);
                }
            }else{
                utilesGUIx.JComboBoxCZ loCombo = new utilesGUIx.JComboBoxCZ();
                loCombo.setName(psCodigo);
                jPanel1.add(loCombo, gridBagConstraints);
                psListaValores=psListaValores.replace('(','\u0000');
                psListaValores=psListaValores.replace(')',JFilaDatosDefecto.mccSeparacion1);
                JFilaDatosDefecto loFila = new JFilaDatosDefecto(psListaValores);
                for(int i = 0;i < loFila.mlNumeroCampos();i++){
                    String lsFila = loFila.msCampo(i) + ";";
                    loCombo.addLinea(loFila.gfsExtraerCampo(lsFila, 1, ';'),
                                     loFila.gfsExtraerCampo(lsFila, 0, ';'));
                }
                loCombo.setValueTabla("");
            }
        }
    }
    public void habilitarSegunEdicion() throws Exception {
//        int[] lalCamposPrincipales = moDatos.getFields().malCamposPrincipales();
        for(int i = 0;i<jPanel1.getComponentCount();i++){
            try{
//                utilesGUI.tabla.IComponentParaTabla loValor = (utilesGUI.tabla.IComponentParaTabla)jPanel1.getComponent(i);
                Component loComp = jPanel1.getComponent(i);
                if(moDatos.getFields().get(Integer.valueOf(loComp.getName()).intValue()).getPrincipalSN()){
                    loComp.setEnabled(moDatos.getModoTabla() == JListDatos.mclNuevo);
                }
            }catch(Exception e){
                
            }
        }
    }
    public void ponerTipoTextos() throws Exception {
        //vacio
    }
    public void mostrarDatos() throws Exception {
        for(int i = 0;i<jPanel1.getComponentCount();i++){
            try{
                utilesGUI.tabla.IComponentParaTabla loValor = (utilesGUI.tabla.IComponentParaTabla)jPanel1.getComponent(i);
                Component loComp = jPanel1.getComponent(i);
                loValor.setValueTabla(moDatos.getFields().get(Integer.valueOf(loComp.getName()).intValue()).getString());
            }catch(Throwable e){
                
            }
        }
    }
    public void establecerDatos() throws Exception {
        for(int i = 0;i<jPanel1.getComponentCount();i++){
            try{
                utilesGUI.tabla.IComponentParaTabla loValor = (utilesGUI.tabla.IComponentParaTabla)jPanel1.getComponent(i);
                Component loComp = jPanel1.getComponent(i);
                moDatos.getFields().get(Integer.valueOf(loComp.getName()).intValue()).setValue(loValor.getValueTabla());
                
            }catch(Throwable e){
                
            }
        }
    }
    
    public void aceptar() throws Exception {
        int lModo = moDatos.getModoTabla();
        IResultado loResult=moDatos.update(true);
        if(loResult.getBien()){
            //notificamos al padre
            IFilaDatos loFila = (IFilaDatos)moDatos.moFila().clone();
            loFila.setTipoModif (lModo);
            moPadre.datosactualizados(loFila);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }    
    
    public void cancelar() throws Exception {
        moDatos.cancel();
    }
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 600, 400);
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


//    public boolean getClaveDuplicada() throws Exception {
//        return false;
//    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}

