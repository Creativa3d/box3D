/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlBanda;
import impresionXML.impresion.xml.JxmlInforme;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JMenuBar;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.swing.JPlugInFrameAbstract;
/**
 *
 * @author eduardo
 */
public class JPanelPreview extends JPlugInFrameAbstract implements PropertyChangeListener {
    private JxmlInforme moInforme;

    /**
     * Creates new form JPanelPreview
     */
    public JPanelPreview() {
        initComponents();
        jPanelDESIGN1.setDatos(this, jPanelPROPIEDADES);
        jReglaHoriz.setOrientacion(jReglaHoriz.mclHorizontal);
        jReglaVErt.setOrientacion(jReglaVErt.mclVertical);
        
    }
    
    public void setDatos(JxmlInforme poInforme) throws Exception{
        moInforme=poInforme;
        propertyChange(null);
        jPanelArbolElementos1.setDatos(poInforme, jPanelDESIGN1);
        moInforme.addPropertyChangeListener(this);
        poInforme.getDiseno().addPropertyChangeListener(this);
        for(int i = 0 ; i < poInforme.sizeBanda(); i++){
            JxmlBanda loBanda = poInforme.getBanda(i);
            loBanda.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if(evt.getPropertyName().equalsIgnoreCase("remove")){
                        IXMLDesign loDe = jPanelDESIGN1.get((IxmlObjetos) evt.getOldValue());
                        if(loDe!=null){
                            jPanelDESIGN1.remove(loDe.getVisualizacion());
                            jPanelDESIGN1.repaint();
                        }
                    }
                    if(evt.getPropertyName().equalsIgnoreCase("add")){
                        IxmlObjetos loXML = (IxmlObjetos) evt.getNewValue();
                        try {
                            IXMLDesign loDesig = JFactoryDesignXML.getInstance().getDesignXML(loXML, getInforme());
                            jPanelDESIGN1.add(loDesig);
                        } catch (Exception ex) {
                            JDepuracion.anadirTexto(getClass().getName(), ex);
                            throw new InternalError(ex.toString());
                        }
                    }
                    
                }
            });
            for(int ii = 0 ; ii < loBanda.size(); ii++){
                IxmlObjetos loObj = loBanda.get(ii);
                IXMLDesign loDesig = JFactoryDesignXML.getInstance().getDesignXML(loObj, moInforme);
                if(loDesig!=null){
                    jPanelDESIGN1.add(loDesig);
                }
            }
            revalidate();
            repaint();
        }
        jReglaHoriz.setPanelDesign(jPanelDESIGN1);
        jReglaVErt.setPanelDesign(jPanelDESIGN1);
//        if(!JCadenas.isVacio(getInforme().getDiseno().getListaCamposPredefinidos())){
//            try{
//                jPanelDESIGN1.ponerCamposPredefin(new File(getInforme().getDiseno().getListaCamposPredefinidos()));
//            }catch(Throwable e){
//                JMsgBox.mensajeErrorYLog(jPanel1, e);
//            }
//        }
        
    }
    public void propertyChange(PropertyChangeEvent evt) {
        Dimension loD = new Dimension(
                (int)(moInforme.getAnchoTotalReal()* JPanelDESIGN.getMultiCMaPixel(getInforme().getDiseno().getZoom())
                
                    )
                , (int)(moInforme.getAltoTotalReal()* JPanelDESIGN.getMultiCMaPixel(getInforme().getDiseno().getZoom())
                   
                    )
                );
        jPanelDESIGN1.setPreferredSize(loD);
        jPanelDESIGN1.setMinimumSize(loD);
        jPanelDESIGN1.setMaximumSize(loD);

        if(!JCadenas.isVacio(getInforme().getDiseno().getImagen())){
            try{
                jPanelDESIGN1.ponerFondo();
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
                JMsgBox.mensajeInformacion(jPanel1, "No se ha podido cargar el fondo");
            }
        }
    }
    public JPanelDESIGN getPanelDesign(){
        return jPanelDESIGN1;
    }
    public JxmlInforme getInforme() {
        return moInforme;
    }
    
    public JMenuBar getMenu() {
        return null;
    }

    public String getIdentificador() {
        return moInforme.getNombre();
    }

    public Container getContenedor() {
        return this;
    }
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            jPopupMenu1.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jReglaHoriz = new impresionXML.impresion.xml.diseno.JRegla();
        jReglaVErt = new impresionXML.impresion.xml.diseno.JRegla();
        jPanelDESIGN1 = new impresionXML.impresion.xml.diseno.JPanelDESIGN();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelPROPIEDADES = new javax.swing.JPanel();
        jPanelArbolElementos1 = new impresionXML.impresion.xml.diseno.JPanelArbolElementos();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(800);
        jSplitPane1.setOneTouchExpandable(true);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setFocusable(false);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 600));
        jScrollPane1.setRequestFocusEnabled(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setMaximumSize(new java.awt.Dimension(15, 15));
        jPanel2.setMinimumSize(new java.awt.Dimension(15, 15));
        jPanel2.setPreferredSize(new java.awt.Dimension(15, 15));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(" ");
        jLabel1.setMaximumSize(new java.awt.Dimension(15, 15));
        jLabel1.setMinimumSize(new java.awt.Dimension(15, 15));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(15, 15));
        jPanel2.add(jLabel1, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jReglaHoriz, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jReglaVErt.setMinimumSize(new java.awt.Dimension(15, 15));
        jReglaVErt.setPreferredSize(new java.awt.Dimension(15, 15));
        jPanel1.add(jReglaVErt, java.awt.BorderLayout.WEST);
        jPanel1.add(jPanelDESIGN1, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanelPROPIEDADES.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Propiedades", jPanelPROPIEDADES);
        jTabbedPane1.addTab("Elementos", jPanelArbolElementos1);

        jSplitPane1.setRightComponent(jTabbedPane1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private impresionXML.impresion.xml.diseno.JPanelArbolElementos jPanelArbolElementos1;
    private impresionXML.impresion.xml.diseno.JPanelDESIGN jPanelDESIGN1;
    private javax.swing.JPanel jPanelPROPIEDADES;
    private javax.swing.JPopupMenu jPopupMenu1;
    private impresionXML.impresion.xml.diseno.JRegla jReglaHoriz;
    private impresionXML.impresion.xml.diseno.JRegla jReglaVErt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables





}
