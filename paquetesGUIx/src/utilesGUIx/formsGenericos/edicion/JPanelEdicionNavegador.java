/*
 * JPanelEdicion.java
 *
 * Created on 10 de septiembre de 2004, 12:57
 */

package utilesGUIx.formsGenericos.edicion;

import ListDatos.JListDatos;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.navegador.JNavegador;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**Muestra un form. de edici?n en forma de panel*/
public class JPanelEdicionNavegador extends javax.swing.JPanel implements utilesGUIx.navegador.INavegador, ISalir, ActionListener, IPlugInFrame, IFormEdicionLista, IContainer {
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
    
    private IListaElementos moListaEdiciones = new JListaElementos();
    private JFormEdicionParametros moParam=new JFormEdicionParametros();
    
    
        
    /** Creates new form JPanelEdicion */
    public JPanelEdicionNavegador() {
        super();
        initComponents();
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, "JPanelEdicion");
        
    }
    public String getIdentificador() {
        return this.getClass().getName();
    }

    public IContainer getContenedorI() {
        return this;
    }

    public JFormEdicionParametros getParametros() {
        return moParam;
    }
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }
    

    /**
     * Establecemos el panel de los datos
     * @param poPanel Interfaz que debe cumplir el controlador de este panel
     * @param poPanelMismo componente a insertar en este panel, suele ser el mismo objeto que poPanel
     * @param poPadre el form. padre  de este panel, para que este panel pueda salir
     * @throws Exception error
     */
    public void setPanel(final IFormEdicionNavegador poPanel, final Component poPanelMismo, final ISalir poPadre) throws Exception {
        moPadre = poPadre;
        moListaEdiciones.add(poPanel);

        add(poPanelMismo, java.awt.BorderLayout.CENTER);
        setBounds(poPanel.getTanano().x, poPanel.getTanano().y, poPanel.getTanano().width, poPanel.getTanano().height);
        
        jLabelAvisos1.setAvisos(poPanel.getParametros().getAvisos());
        jLabelAvisos1.setVisible(poPanel.getParametros().getAvisos().size()>0);
                
        initEdicion(poPanel);
        
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
            poPanel.setBloqueoControles(true);
            mlModo = JListDatos.mclNada;
            if(Container.class.isAssignableFrom(poPanel.getClass())){
                JPanelEdicion.mostrarDatosBD((Container)poPanel);
            }
            poPanel.mostrarDatos();
        }else{
            jNavegador1.setModoEdicion();
            poPanel.setBloqueoControles(false);
            if(Container.class.isAssignableFrom(poPanel.getClass())){
                JPanelEdicion.mostrarDatosBD((Container)poPanel);
            }
            poPanel.mostrarDatos();
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
    public void addEdicion(final IFormEdicion poPanel) throws Exception{
        moListaEdiciones.add(poPanel);
        initEdicion((IFormEdicionNavegador)poPanel);
    }
    /**
     * @return devolvemos la lista de ediciones
     */
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
            if(Container.class.isAssignableFrom(loPanel.getClass())){
                JPanelEdicion.mostrarDatosBD((Container)loPanel);
            }        
            loPanel.mostrarDatos();
        }
        //el titulo solo el principal
        moPadre.setTitle(((IFormEdicionNavegador) moListaEdiciones.get(0)).getTitulo());
    }

    public void aceptar() throws Exception{
        comprobarEdicion();
        boolean lbContinuar = true;
        //para cada uno establecemos datos y  validamos
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            //no puede haber form de solo lectura
            if(Container.class.isAssignableFrom(loPanel.getClass())){
                JPanelEdicion.establecerDatosBD((Container)loPanel);
            }        
            loPanel.establecerDatos();
            lbContinuar &= loPanel.validarDatos();
        }        
        //para cada uno aceptamos
        for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.aceptar();
            loPanel.setBloqueoControles(true);
        }
        mlModo = JListDatos.mclNada;
        //recuperamos los datos de todos los paneles
        recuperarYMostrarDatos();
        
        //parche para windows, para q coja el foco este form
        try{
            Window loW = SwingUtilities.getWindowAncestor(this);
            boolean lbVisible = loW.isVisible();
            loW.setVisible(lbVisible);
        } catch(Throwable q){
            
        }
    }
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
            loPanel.setBloqueoControles(true);
        }

    }
    public void buscar() throws Exception{
        //busqueda solo por el primario
        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(0);
        loPanel.buscar();
        //recuperamos y mostramos todos
        recuperarYMostrarDatos();
    }
    
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
            throw new Exception(JGUIxConfigGlobal.getInstancia().getEdicionNavegadorMensajeSoloLectura());
        }
    }
    public void borrar() throws Exception {
        comprobarEdicion();
        //para cada uno borramos
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.borrar();
            //si no hay datos por defecto nuevo
            if(loPanel.getDatos().size()==0){
                jNavegador1.btnNuevo.doClick();
            }else{
                recuperarYMostrarDatos();
            }
        }
    }
    
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
                loPanel.setBloqueoControles(false);
                loPanel.habilitarSegunEdicion();
            }else{
                throw new Exception("Debe haber un registro seleccionado");
            }
        }
    }
    
    public void nuevo() throws Exception {
        comprobarEdicion();
        //para cada uno nuevo
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            loPanel.nuevo();
            mlModo = JListDatos.mclNuevo;
            JPanelEdicion.mostrarDatosBD(this);
            loPanel.mostrarDatos();
            moPadre.setTitle(loPanel.getTitulo());
            loPanel.setBloqueoControles(false);
            loPanel.habilitarSegunEdicion();
        }
    }
    
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
     */
    public boolean isEditable() {
        boolean lbSoloLectura = true;
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
            lbSoloLectura &= loPanel.getParametros() != null && loPanel.getParametros().isSoloLectura();
        }
        return !lbSoloLectura;
    }
    
    public boolean getBorrarSN() {
        return isEditable();
    }
    
    public boolean getBuscarSN() {
        return true;
    }
    
    public boolean getDentroFormEdicionSN() {
        return true;
    }
    
    public boolean getEditarSN() {
        return isEditable();
    }
    
    public boolean getNuevoSN() {
        return isEditable();
    }
    
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
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelBotones = new javax.swing.JPanel();
        jNavegador1 = new utilesGUIx.navegador.JNavegador();
        jLabelAvisos1 = new utilesGUIx.aplicacion.avisosGUI.JLabelAvisos();
        btnSalir = new utilesGUIx.JButtonCZ();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());

        jPanelBotones.setBackground(new java.awt.Color(175, 181, 186));
        jPanelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelBotones.setLayout(new java.awt.GridBagLayout());

        jNavegador1.setMinimumSize(new java.awt.Dimension(400, 26));
        jNavegador1.setPreferredSize(new java.awt.Dimension(400, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelBotones.add(jNavegador1, gridBagConstraints);

        jLabelAvisos1.setText(" ");
        jPanelBotones.add(jLabelAvisos1, new java.awt.GridBagConstraints());

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Stop16bis.gif"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelBotones.add(btnSalir, gridBagConstraints);

        add(jPanelBotones, java.awt.BorderLayout.SOUTH);
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == btnSalir) {
            JPanelEdicionNavegador.this.btnSalirActionPerformed(evt);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        
        if(mlModo == JListDatos.mclNada){
            moPadre.salir();
            //es posible q no se salga del form, simplemente lo haga invisible
//            moPadre=null;
//            loPanel=null;
        }else{
            switch(mlModoSalida){
                case mclSalidaCancelar:
                    if (JOptionPane.showConfirmDialog(this, "¿Estas seguro de cancelar la edición?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        moPadre.salir();

            //es posible q no se salga del form, simplemente lo haga invisible
//                        moPadre=null;
//                        loPanel=null;
                    }
                break;
                case mclSalidaGuardar:
                    if (JOptionPane.showConfirmDialog(this, "¿Deseas guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        try {
                            aceptar();
                            moPadre.salir();
            //es posible q no se salga del form, simplemente lo haga invisible
//                            moPadre=null;
//                            loPanel=null;
                        } catch (Throwable ex) {
                            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
                        }
                    }else{
                        moPadre.salir();
            //es posible q no se salga del form, simplemente lo haga invisible
//                        moPadre=null;
//                        loPanel=null;
                    }
                break;
                default:
                    moPadre.salir();
            //es posible q no se salga del form, simplemente lo haga invisible
//                    moPadre=null;
//                    loPanel=null;
            }
        }

    }//GEN-LAST:event_btnSalirActionPerformed

    public void salir() {
        btnSalirActionPerformed(null);
    }

    public void setTitle(String psTitulo) {
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnSalir;
    private utilesGUIx.aplicacion.avisosGUI.JLabelAvisos jLabelAvisos1;
    private utilesGUIx.navegador.JNavegador jNavegador1;
    private javax.swing.JPanel jPanelBotones;
    // End of variables declaration//GEN-END:variables

    
}
