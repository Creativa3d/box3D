/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFXAvisos.forms;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;
import utilesFX.formsGenericos.JPanelGenerico2;
import utilesFX.msgbox.JMsgBox;
import utilesFXAvisos.tablasControladoras.JT2GUIXMENSAJESBD;
import utilesGUI.procesar.JProcesoAccionAbstrac;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;

/**
 *
 * @author eduardo
 */
public class JGestorCorreo extends GridPane {

    @FXML
    private JPanelGenerico2 jPanelGenerico1;

    @FXML
    private TreeView<?> jTreeView1;
    
    private JT2GUIXMENSAJESBD moControladorCorreos;
    
    public JGestorCorreo() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFXAvisos/forms/JGestorCorreo.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
            jPanelGenerico1.setBotoneraARRIBA();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    class JCarpeta {

        private final String msCarpeta;
        private final JGUIxAvisosCorreo moCorreo;
        private final String msTitulo;
        
        public JCarpeta(String psCarpeta, String psTitulo, JGUIxAvisosCorreo poCorreo){
            msCarpeta = psCarpeta;
            moCorreo = poCorreo;
            msTitulo=psTitulo;
        }
        
        
        @Override
        public String toString(){
            if(JCadenas.isVacio(msTitulo)){
                return msCarpeta;
            }else {
                return msTitulo;
            }
        }

        /**
         * @return the moCorreo
         */
        public JGUIxAvisosCorreo getCorreo() {
            return moCorreo;
        }

    }
    
    public void mostrarDatos() throws Exception{
        jPanelGenerico1.setVisible(false);
        moControladorCorreos = new JT2GUIXMENSAJESBD(JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos());
        moControladorCorreos.getConsultaO().crearSelectNula();
        
        jPanelGenerico1.setControlador(moControladorCorreos);
        
        List<JGUIxAvisosCorreo> loCorreos = JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().getListaCorreos();
        
        JGUIxConfigGlobalModelo.getInstancia().getThreadGroup().addProcesoYEjecutar(new JProcesoAccionAbstrac() {

            @Override
            public String getTitulo() {
                return "recuperando correos";
            }

            @Override
            public int getNumeroRegistros() {
                return -1;
            }

            @Override
            public void procesar() throws Throwable {
                JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().recibirYGuardar();
            }

            @Override
            public void mostrarMensaje(String psMensaje) {
                try {
                    moControladorCorreos.refrescar();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(JGestorCorreo.class.getName(), ex);
                }
            }

            @Override
            public void mostrarError(Throwable e) {
                JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(JGestorCorreo.this, e, null);
            }
        }, false);
        
        TreeItem loRoot = new TreeItem("Correos");
        TreeItem loPrimeraCarpeta = null;
        for(JGUIxAvisosCorreo loCorreo : loCorreos){
            Node loViewBandejaentrada = new ImageView(
                new Image(getClass().getResourceAsStream("/utilesFX/images/mail-queue16.png"))
            );
            Node loViewConfigCorreo = new ImageView(
                new Image(getClass().getResourceAsStream("/utilesFX/images/mail-receive16.png"))
            );
            Node loViewEnviados = new ImageView(
                new Image(getClass().getResourceAsStream("/utilesFX/images/SendMail16.gif"))
            );
            TreeItem loCorreoItem = new TreeItem(loCorreo.getEnviar().getCorreoNombre(), loViewConfigCorreo);
            loRoot.getChildren().add(loCorreoItem);
            loCorreoItem.expandedProperty().set(true);
            if(!JCadenas.isVacio(loCorreo.getLeer().getCarpetaCorreo())){
                TreeItem loCorreoCarpetaItem = new TreeItem(new JCarpeta(loCorreo.getLeer().getCarpetaCorreo() , "Bandeja entrada", loCorreo), loViewBandejaentrada);
                loCorreoItem.getChildren().add(loCorreoCarpetaItem);
                if(loPrimeraCarpeta==null){
                    loPrimeraCarpeta=loCorreoCarpetaItem;
                }
            }
            TreeItem loCorreoCarpetaItem = new TreeItem(new JCarpeta(JTEEGUIXMENSAJESBD.mcsENVIADOS, JTEEGUIXMENSAJESBD.mcsENVIADOS, loCorreo), loViewEnviados);
            loCorreoItem.getChildren().add(loCorreoCarpetaItem);
            if(loPrimeraCarpeta==null){
                loPrimeraCarpeta=loCorreoCarpetaItem;
            }
        }


        jTreeView1.setRoot(loRoot);
        jTreeView1.showRootProperty().set(false);
        jTreeView1.getRoot().expandedProperty().set(true);
        
        jTreeView1.selectionModelProperty().get().setSelectionMode(SelectionMode.SINGLE);
        
        jTreeView1.selectionModelProperty().get().getSelectedItems().addListener(new ListChangeListener<TreeItem<?>>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends TreeItem<?>> c) {
                TreeItem loItem = jTreeView1.selectionModelProperty().get().getSelectedItem();
                Object loObj = loItem.getValue();
                if(loObj instanceof JCarpeta){
                    jPanelGenerico1.setVisible(true);
                    try {
                        JCarpeta loCarpeta = (JCarpeta) loObj;
                        moControladorCorreos=new JT2GUIXMENSAJESBD(JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos(),loCarpeta.moCorreo, loCarpeta.msCarpeta);
                        moControladorCorreos.getParametros().mbSegundoPlano=true;
                        jPanelGenerico1.setControlador(moControladorCorreos);
                    } catch (Exception ex) {
                        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(JGestorCorreo.this, ex, null);
                    }
                } else {
                    jPanelGenerico1.setVisible(false);
                }
            }
        });
        
        if(loPrimeraCarpeta!=null){
            jTreeView1.getSelectionModel().clearSelection();
            jTreeView1.getSelectionModel().clearAndSelect(jTreeView1.getRow(loPrimeraCarpeta));
        }

    }
    
    
    
    
}
