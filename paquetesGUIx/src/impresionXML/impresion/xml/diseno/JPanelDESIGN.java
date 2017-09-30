/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno;

import impresionXML.impresion.xml.diseno.componentes.JPanelBASE;
import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.diseno.campos.JTCAMPOS;
import impresionXML.impresion.xml.diseno.operaciones.JOperacionMover;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JCopiarObjetos;
import utiles.JListaElementos;
import utilesGUIx.JPintaFondo;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.imgTrata.ImageInfo;
import utilesGUIx.imgTrata.JIMGTrata;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author eduardo
 */
public class JPanelDESIGN extends javax.swing.JPanel implements ComponentListener, MouseListener, ClipboardOwner {
    private JPanel moPanelPropiedades;
    private JPanelPreview moPreview;
    private int mlPopUpY;
    private int mlPopUpX;
    private JSeleccionObjetos moSeleccion = new JSeleccionObjetos(this);
    private IListaElementos moListaPaint = new JListaElementos();
    
    /**
     * Creates new form JPanelDESIGN
     */
    public JPanelDESIGN() {
        initComponents();
        this.addMouseListener(this);

        IListaElementos loLista = JFactoryDesignXML.getInstance().getListaDesignXML();
        for(int i = 0 ; i < loLista.size();i++){
            final JFactoryDesignXML.JFactoryDesignXMLElem loObj = (JFactoryDesignXML.JFactoryDesignXMLElem) loLista.get(i);
            JMenuItem loItem = new JMenuItem("Nuevo " + loObj.getCaption());
            loItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        addNuevo(loObj);
                    } catch (Throwable ex) {
                        JMsgBox.mensajeErrorYLog(JPanelDESIGN.this, ex);
                    }
                }
            });
            jPopupMenu1.add(loItem);
        }        
    }
    public void addPaintListener(PaintListener l){
        moListaPaint.add(l);
    }
    public void removePaintListener(PaintListener l){
        moListaPaint.remove(l);
    }
    
    public void addNuevo(final JFactoryDesignXML.JFactoryDesignXMLElem loObj) throws Throwable{
        if(loObj.isImpresion()){
            IxmlObjetos loXML= JFactoryDesignXML.getInstance().getXML(loObj.getTipo(), moPreview.getInforme());
            moPreview.getInforme().getBanda(0).add(loXML);
            loXML.visitar(new JOperacionMover(moPreview.getInforme(), mlPopUpX/getMultiCMaPixel(moPreview.getInforme().getDiseno().getZoom()), mlPopUpY/getMultiCMaPixel(moPreview.getInforme().getDiseno().getZoom()),0,0));
        }else{
            IxmlObjetos loFu= JFactoryDesignXML.getInstance().getXML(loObj.getTipo(), moPreview.getInforme());
            IXMLDesign loDesig = JFactoryDesignXML.getInstance().getDesignXML(loFu, moPreview.getInforme());
            loDesig.setDatos(loFu, moPreview.getInforme());
            JDatosGeneralesP.getDatosGenerales().mostrarEdicion(loDesig.getPropiedades(), (Component)loDesig.getPropiedades());

        }        
    }

    public JxmlInforme getInforme(){
        return moPreview.getInforme();
    }
    public static double getResolucionPantalla(double pdZoom){
        return 72*pdZoom;
    }
    public static double getMultiCMaPixel(double pdZoom){
        return getResolucionPantalla(pdZoom)/2.54;
    }

    void setDatos(JPanelPreview poPreview, JPanel poPanelPROPIEDADES) {
        moPreview = poPreview;
        moPanelPropiedades = poPanelPROPIEDADES;
    }

    public JSeleccionObjetos getSeleccion(){
        return moSeleccion;
    }
    
    public void pegar() throws Exception{
        getSeleccion().pegar();
    }
    public void copiar() {
        getSeleccion().copiar();
    }
    public void cambiarFondo(){
        try{
            
            final JFileChooser loFileM = new JFileChooser();
            if(!JCadenas.isVacio(moPreview.getInforme().getDiseno().getImagen())){
                loFileM.setSelectedFile(new File(moPreview.getInforme().getDiseno().getImagen()));
            }
            loFileM.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(loFileM.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                final JT2Parametros loParam = new JT2Parametros();
                loParam.x=moPreview.getInforme().getDiseno().getX();
                loParam.y=moPreview.getInforme().getDiseno().getY();
                loParam.mostrar(new CallBack<JMostrarPantallaParam>() {
                    public void callBack(JMostrarPantallaParam poControlador) {
                        if(!loParam.mbCancelado){
                            try {
                                moPreview.getInforme().getDiseno().setImagen(loFileM.getSelectedFile().getAbsolutePath());
                                moPreview.getInforme().getDiseno().setX(loParam.x);
                                moPreview.getInforme().getDiseno().setY(loParam.y);
                                ponerFondo();
                            } catch (Exception ex) {
                                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(JPanelDESIGN.this, ex);
                            }
                        }
                    }
                });
                
            }
            
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }        
    }
    
    public void add(IXMLDesign poDesign){
        final JPanelBASE loBase = poDesign.getVisualizacion();
        loBase.setPadre(this);
        loBase.addComponentListener(this);
        loBase.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
            }
            public void mouseMoved(MouseEvent e) {
//                MouseEvent loNuevo = new MouseEvent(
//                        JPanelDESIGN.this, e.getID(), e.getWhen()
//                        , e.getModifiers()
//                        , JPanelDESIGN.this.getX() + e.getX(), JPanelDESIGN.this.getY() + e.getY()
//                        , e.getClickCount(), e.isPopupTrigger());
////                JPanelDESIGN.this.processMouseMotionEvent(loNuevo);
//                MouseMotionListener[] loCon = JPanelDESIGN.this.getMouseMotionListeners();
//                for(int i = 0 ; i < loCon.length; i++){
//                    loCon[i].mouseMoved(loNuevo);
//                }
            }
        });
        this.add(loBase);
        loBase.setDatos(poDesign);
        revalidate();
        repaint();
    }
    public IXMLDesign get(IxmlObjetos poXML){
        IXMLDesign loResult = null;
        for(int i = 0 ; i < getComponentCount() && loResult == null ; i++){
            if(JPanelBASE.class.isAssignableFrom(getComponent(i).getClass())){
                JPanelBASE loB = (JPanelBASE) getComponent(i);
                if(loB.getXMLObjeto()==poXML){
                    loResult = loB.getXMLDesign();
                }
            }
        }
        return loResult;
    }

    public void setPropiedades(IFormEdicion propiedades) {
        
        moPanelPropiedades.removeAll();
        moPanelPropiedades.add((Component)propiedades, BorderLayout.CENTER);
        moPanelPropiedades.repaint();
        moPanelPropiedades.validate();
        moPanelPropiedades.updateUI();
    }
    private void ponerCamposPredefin(File loFile) throws Exception{
        moPreview.getInforme().getDiseno().setListaCamposPredefinidos(loFile.getAbsolutePath());
    }
    public void ponerFondo() throws Exception{
        
        //lectura imagen e informacion
        ImageIcon loIcon = JIMGTrata.getIMGTrata().getImagenCargada(moPreview.getInforme().getDiseno().getImagen());
        ImageInfo loInfo = JIMGTrata.getIMGTrata().getInfo(moPreview.getInforme().getDiseno().getImagen());

        //resolucion puntos por pulgada
        double DPIImg = loInfo.getPhysicalHeightDpi();
        double DPIPantalla = getResolucionPantalla(moPreview.getInforme().getDiseno().getZoom());

        //escalamos la imagen para q en pantalla sea tamaño real
        BufferedImage loImgAux = JIMGTrata.getIMGTrata().getImagenEscalada(loIcon.getImage()
                , loIcon.getIconWidth(), loIcon.getIconHeight()
                , (int)(((double)loIcon.getIconWidth()) * DPIPantalla/DPIImg), (int)(((double)loIcon.getIconHeight()) * DPIPantalla/DPIImg)
                );

        int lx = (int)(moPreview.getInforme().getDiseno().getX()*getMultiCMaPixel(moPreview.getInforme().getDiseno().getZoom()));
        int ly = (int)(moPreview.getInforme().getDiseno().getY()*getMultiCMaPixel(moPreview.getInforme().getDiseno().getZoom()));
        BufferedImage loImg = new BufferedImage(
                loImgAux.getWidth()+lx
                , loImgAux.getHeight()+ly
                , BufferedImage.TYPE_INT_RGB);
        loImg.getGraphics().setColor(Color.white);
        loImg.getGraphics().fillRect(0, 0, loImg.getWidth(), loImg.getHeight());
        loImg.getGraphics().drawImage(loImgAux
                , lx > 0 ? lx : 0 , ly > 0 ? ly : 0
                    , loImg.getWidth(), loImg.getHeight()
                , lx < 0 ? -lx : 0 , ly < 0 ? -ly : 0
                    , loImgAux.getWidth(), loImgAux.getHeight()
                , null);

        JPintaFondo f = new JPintaFondo(loImg);
        f.setTamanoOriginal(true);
        f.setAlineacionHorizontal(SwingConstants.LEFT);
        f.setAlineacionVertical(SwingConstants.TOP);
        this.setBorder(f);


    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0,0,this.getWidth(),this.getHeight());         

        super.paint(g);      
        for(int i = 0 ; i < moListaPaint.size(); i++){
            PaintListener l = (PaintListener) moListaPaint.get(i);
            l.paint((Graphics2D) g);
        }        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuFondo = new javax.swing.JMenuItem();
        jMenuListaCampos = new javax.swing.JMenuItem();

        jMenuFondo.setText("Imagen Fondo modo diseño");
        jMenuFondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFondoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuFondo);

        jMenuListaCampos.setText("Lista campos predefinidos");
        jMenuListaCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuListaCamposActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuListaCampos);

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(2000, 2000));
        setPreferredSize(new java.awt.Dimension(2000, 2000));
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuFondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFondoActionPerformed
        
        cambiarFondo();
    }//GEN-LAST:event_jMenuFondoActionPerformed

    private void jMenuListaCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuListaCamposActionPerformed

        try{
            File loFile = null;
            JFileChooser loFileM = new JFileChooser();
            if(!JCadenas.isVacio(moPreview.getInforme().getDiseno().getListaCamposPredefinidos())){
                loFileM.setSelectedFile(new File(moPreview.getInforme().getDiseno().getListaCamposPredefinidos()));
            }
            loFileM.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(loFileM.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                loFile = loFileM.getSelectedFile();
                ponerCamposPredefin(loFile);
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }
        
    }//GEN-LAST:event_jMenuListaCamposActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuFondo;
    private javax.swing.JMenuItem jMenuListaCampos;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables

    public void componentResized(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
//        int lWidth = 0;
//        int lHeight = 0;
//        for (int i = 0; i < this.getComponentCount(); i++) {
//            Component loComp = this.getComponent(i);
//            int lWidthAux = loComp.getWidth() + loComp.getX();
//            int lHeightAux = loComp.getHeight() + loComp.getY();
//            if (lWidth < lWidthAux) {
//                lWidth = lWidthAux;
//            }
//            if (lHeight < lHeightAux) {
//                lHeight = lHeightAux;
//            }
//        }
        if(e!=null){
            if (e.getComponent().getClass().isAssignableFrom(JPanelBASE.class)) {
                this.setComponentZOrder(e.getComponent(), 0);
            }
        }
//        setBounds(0, 0, lWidth, lHeight);    
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            jPopupMenu1.show(e.getComponent(),
                       e.getX(), e.getY());
            mlPopUpX = e.getX();
            mlPopUpY = e.getY();
            
        }
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

}
