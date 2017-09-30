/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos.edicion;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesFX.JFXConfigGlobal;
import utilesFX.aplicacion.avisosGUI.JLabelAvisos;
import utilesFX.formsGenericos.IPadreInterno;
import utilesFX.formsGenericos.JMostrarPantallaCargarForm;
import utilesFX.msgbox.JMsgBox;
import utilesFX.msgbox.JOptionPaneFX;
import utilesFX.navegador.JNavegador;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionLista;
import utilesGUIx.formsGenericos.edicion.IFormEdicionNavegador;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.navegador.INavegador;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**
 *
 * @author eduardo
 */
public class JPanelEdicionNavegador extends BorderPane implements INavegador, IPadreInterno, IPlugInFrame, IFormEdicionLista, IContainer, ISalir{
    private static final long serialVersionUID = 1L;
  
    public static final int mclSalidaCancelar = 0;
    public static final int mclSalidaGuardar = 1;
    public static final int mclSalidaNada = 2;

    public static final int mclINICIOEDICION = 0;
    public static final int mclINICIONAVEGAR = 1;
    
    public int mlINICIODEFECTO = mclINICIOEDICION;
  
    
    private ISalir moPadre;
//    private IFormEdicionNavegador moPanel;
    private int mlModo = JListDatos.mclNada;
    private int mlModoSalida = mclSalidaCancelar;
    
    private final IListaElementos moListaEdiciones = new JListaElementos();
    private final JFormEdicionParametros moParam=new JFormEdicionParametros();
    

    @FXML private JNavegador jNavegador1;
    @FXML private JLabelAvisos jLabelAvisos1;
    @FXML private Button lblInformacion;
    @FXML private Button btnSalir;
    @FXML private GridPane moAbajo;
        
    /** Creates new form JPanelEdicion */
    public JPanelEdicionNavegador() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFX/formsGenericos/edicion/JPanelEdicionNavegador.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }               
        
        
        
        btnSalir.setOnAction(
                (e)->btnSalirActionPerformed( e)
        );
        
        jLabelAvisos1 = new JLabelAvisos(lblInformacion, null);
        JFXConfigGlobal.getInstancia().setTextoTodosComponent(this, "JPanelEdicion");
        
    }
    @Override
    public String getIdentificador() {
        return this.getClass().getName();
    }

    public IContainer getContenedorI() {
        return this;
    }

    @Override
    public JFormEdicionParametros getParametros() {
        return moParam;
    }
    @Override
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    @Override
    public void aplicarListaComponentesAplicacion() {
    }
    

    /**
     * Establecemos el panel de los datos
     * @param poPanel Interfaz que debe cumplir el controlador de este panel
     * @param poPanelMismo componente a insertar en este panel, suele ser el mismo objeto que poPanel
     * @param poPadre el form. padre  de este panel, para que este panel pueda salir
     * @throws Exception error
     */
    public void setPanel(final IFormEdicionNavegador poPanel, final Node poPanelMismo) throws Exception {
        moListaEdiciones.add(poPanel);

        setCenter(poPanelMismo);
//        setPrefSize(poPanel.getTanano().width, poPanel.getTanano().height);
//        setLayoutX(poPanel.getTanano().x);
//        setLayoutY(poPanel.getTanano().y);
        
        jLabelAvisos1.setAvisos(poPanel.getParametros().getAvisos());
        lblInformacion.setVisible(poPanel.getParametros().getAvisos().size()>0);
                
        initEdicion(poPanel);
        
    }
    
    public void setPadre(ISalir poPadre){
        moPadre = poPadre;
    }
    //inicializamos un panel
    private void initEdicion(final IFormEdicionNavegador poPanel) throws Exception{
        poPanel.rellenarPantalla();
        poPanel.ponerTipoTextos();
        boolean lbNuevo = poPanel.getTabla()!=null && poPanel.getTabla().getList()!=null && poPanel.getTabla().getList().getModoTabla()==JListDatos.mclNuevo;
        jNavegador1.setDatos(this);
        if((poPanel.getParametros() != null && poPanel.getParametros().isSoloLectura())
                || (mlINICIODEFECTO==mclINICIONAVEGAR && !lbNuevo)
                ){
            jNavegador1.setModoNormal();
            setBloqueoControles(poPanel, true);
            mlModo = JListDatos.mclNada;

            mostrarDatos(poPanel);
        }else{
            jNavegador1.setModoEdicion();
            setBloqueoControles(poPanel, false);

            mostrarDatos(poPanel);
            poPanel.habilitarSegunEdicion();
            mlModo = JListDatos.mclEditar;
            
        }
        if(poPanel.getParametros() !=null){
            poPanel.getParametros().getBotones().add(btnSalir);
            poPanel.getParametros().getBotones().add(jNavegador1);
            poPanel.getParametros().getBotones().add(jLabelAvisos1);
        }        
    }
    /**
     * Añadimos una edición y la inicializamos
     */
    @Override
    public void addEdicion(final IFormEdicion poPanel) throws Exception{
        moListaEdiciones.add(poPanel);
        initEdicion((IFormEdicionNavegador)poPanel);
    }
    /**
     * @return devolvemos la lista de ediciones
     */
    @Override
    public IListaElementos getListaEdiciones() {
        return moListaEdiciones;
    }

//    /**
//     * @param moListaEdiciones Establecemos la lista de ediciones
//     */
//    public void setListaEdiciones(IListaElementos moListaEdiciones) {
//        this.moListaEdiciones = moListaEdiciones;
//    }
    public JNavegador getNavegador(){
        return jNavegador1;
    }
    public void recuperarYMostrarDatos() throws Exception{
        //para cada uno mostramos
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.recuperarDatos();
     
            mostrarDatos(loPanel);
        }
        //el titulo solo el principal
        moPadre.setTitle(((IFormEdicionNavegador) moListaEdiciones.get(0)).getTitulo());
    }

    @Override
    public void aceptar() throws Exception{
        comprobarEdicion();
        boolean lbContinuar = true;
        //para cada uno establecemos datos y  validamos
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            //no puede haber form de solo lectura
      
            establecerDatos(loPanel);
            lbContinuar &= loPanel.validarDatos();
        }        
        //para cada uno aceptamos
        for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.aceptar();
            setBloqueoControles(loPanel, true);
        }
        mlModo = JListDatos.mclNada;
        //recuperamos los datos de todos los paneles
        recuperarYMostrarDatos();
        

    }
    @Override
    public void cancelar() throws Exception{
        //para cada uno cancelamos
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.cancelar();
        }
        mlModo = JListDatos.mclNada;
        //recuperamos y mostramos todos
        recuperarYMostrarDatos();
        //para cada uno bloqueamos, se debe de hacer aqui pq al mostrar datos se crean componenetes nuevos
        for (int i = 0; i < moListaEdiciones.size(); i++) {
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            setBloqueoControles(loPanel, true);
        }

    }
    @Override
    public void buscar() throws Exception{
        //busqueda solo por el primario
        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(0);
        loPanel.buscar();
        //recuperamos y mostramos todos
        recuperarYMostrarDatos();
    }
    
    @Override
    public void refrescar() throws Exception{
        //refrescamos todos los paneles
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.refrescar();
        }
        //recuperamos y mostramos todos
        recuperarYMostrarDatos();
    }
    private void comprobarEdicion() throws Exception{
        if(!isEditable()){
            throw new Exception(JFXConfigGlobal.getInstancia().getEdicionNavegadorMensajeSoloLectura());
        }
    }
    @Override
    public void borrar() throws Exception {
        comprobarEdicion();
        //para cada uno borramos
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.borrar();
            //si no hay datos por defecto nuevo
            if(loPanel.getDatos().isEmpty()){
                jNavegador1.btnNuevo.fire();
            }else{
                recuperarYMostrarDatos();
            }
        }
    }
    
    @Override
    public void editar() throws Exception {
        comprobarEdicion();
        //para cada uno editamos
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            if(loPanel.getDatos().size()>0){
                loPanel.editar();
                mlModo = JListDatos.mclEditar;
                //no recuperamos datos pq ya estan en la pantalla(optimizacion)
                //recuperarYMostrarDatos();
                setBloqueoControles(loPanel, false);
                loPanel.habilitarSegunEdicion();
            }else{
                throw new Exception("Debe haber un registro seleccionado");
            }
        }
    }
    
    @Override
    public void nuevo() throws Exception {
        comprobarEdicion();
        //para cada uno nuevo
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.nuevo();
            mlModo = JListDatos.mclNuevo;
            mostrarDatos(loPanel);
            moPadre.setTitle(loPanel.getTitulo());
            setBloqueoControles(loPanel, false);
            loPanel.habilitarSegunEdicion();
        }
    }
    
    @Override
    public boolean anterior() throws Exception {
        boolean lbExito = false;
        //para cada uno anterior
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            lbExito |= loPanel.getDatos().movePrevious();
        }
        //si alguno se ha movido bien recuperamos todos
        if(lbExito){
            recuperarYMostrarDatos();
        }
        return lbExito;
    }    
    @Override
    public boolean primero() throws Exception {
        boolean lbExito = false;
        //para cada uno primero
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            lbExito |= loPanel.getDatos().moveFirst();
        }
        //si alguno se ha movido bien recuperamos todos
        if(lbExito){
            recuperarYMostrarDatos();
        }
        return lbExito;
    }
    
    @Override
    public boolean siguiente() throws Exception {
        boolean lbExito = false;
        //para cada uno siguiente
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            lbExito |= loPanel.getDatos().moveNext();
        }
        //si alguno se ha movido bien recuperamos todos
        if(lbExito){
            recuperarYMostrarDatos();
        }
        return lbExito;
    }
    
    @Override
    public boolean ultimo() throws Exception {
        boolean lbExito = false;
        //para cada uno ultimo
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            lbExito |= loPanel.getDatos().moveLast();
        }
        //si alguno se ha movido bien recuperamos todos
        if(lbExito){
            recuperarYMostrarDatos();
        }
        return lbExito;    
    }
    /**
     * Indica si todos los IFormEdicion son editables
     * @return 
     */
    public boolean isEditable() {
        boolean lbSoloLectura = true;
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            lbSoloLectura &= loPanel.getParametros() != null && loPanel.getParametros().isSoloLectura();
        }
        return !lbSoloLectura;
    }
    
    @Override
    public boolean getBorrarSN() {
        return isEditable();
    }
    
    @Override
    public boolean getBuscarSN() {
        return true;
    }
    
    @Override
    public boolean getDentroFormEdicionSN() {
        return true;
    }
    
    @Override
    public boolean getEditarSN() {
        return isEditable();
    }
    
    @Override
    public boolean getNuevoSN() {
        return isEditable();
    }
    
    @Override
    public boolean getRefrescarSN() {
        return true;
    }
    
    public void setModoSalida(final int plModoSalida){
        mlModoSalida = plModoSalida;
    }
    public int getModoSalida(){
        return mlModoSalida;
    }
    public void setINICIODEFECTO(final int plINICIODEFECTO){
        mlINICIODEFECTO = plINICIODEFECTO;
    }
    public int getINICIODEFECTO(){
        return mlINICIODEFECTO;
    }

    private void btnSalirActionPerformed(ActionEvent e) {                                         
        
        if(mlModo == JListDatos.mclNada){
            moPadre.salir();
            //es posible q no se salga del form, simplemente lo haga invisible
//            moPadre=null;
//            loPanel=null;
        }else{
            switch(mlModoSalida){
                case mclSalidaCancelar:
                    JOptionPaneFX.showConfirmDialog(
                            this
                            , "¿Estas seguro de cancelar la edición?"
                            , ()->{moPadre.salir();}
                            , ()->{}
                    );
                break;
                case mclSalidaGuardar:
                    JOptionPaneFX.showConfirmDialog(
                            this
                            , "¿Deseas guardar los cambios?"
                            , ()->{
                                try {
                                    aceptar();
                                    moPadre.salir();
                    //es posible q no se salga del form, simplemente lo haga invisible
        //                            moPadre=null;
        //                            loPanel=null;
                                } catch (Throwable ex) {
                                    JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
                                }
                            }, ()->{
                                    moPadre.salir();
                        //es posible q no se salga del form, simplemente lo haga invisible
            //                        moPadre=null;
            //                        loPanel=null;
                            }
                    );
                break;
                default:
                    moPadre.salir();
            //es posible q no se salga del form, simplemente lo haga invisible
//                    moPadre=null;
//                    loPanel=null;
            }
        }

    }                                        

    @Override
    public void salir() {
        btnSalirActionPerformed(null);
    }
    @Override
    public void setTitle(String psTitulo) {
    }

    private void mostrarDatos(IFormEdicion loPanel) throws Exception {
        JMostrarPantallaCargarForm.mostrarSiSwing(loPanel);
        loPanel.mostrarDatos();
    }
    private void establecerDatos(IFormEdicion loPanel) throws ECampoError, Exception {
        JMostrarPantallaCargarForm.establecerSiSwing(loPanel);
        loPanel.establecerDatos();
    }
    private void setBloqueoControles(IFormEdicionNavegador poPanel, boolean pbBloqueo) throws Exception {
        poPanel.setBloqueoControles(pbBloqueo);
        JMostrarPantallaCargarForm.setBloqueoControlesSiSwing(poPanel, pbBloqueo);
    }
        
}
