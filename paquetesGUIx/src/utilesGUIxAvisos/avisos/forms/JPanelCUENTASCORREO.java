/*
 * JPanelCUENTASCORREO.java
 *
 * Creado el 29/03/2012
 */

package utilesGUIxAvisos.avisos.forms;

import java.awt.Rectangle;

import ListDatos.*;
import javax.swing.SwingUtilities;
import utilesGUIx.formsGenericos.*;

import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JConversiones;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.Rectangulo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreoEnviar;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreoLeer;
import utilesGUIxAvisos.avisos.JMensaje;
import utilesGUIxAvisos.avisos.consulta.JTFORMCUENTASCORREO;
import utilesGUIxAvisos.avisos.tablasControladoras.JT2CUENTASCORREO;

public class JPanelCUENTASCORREO extends JPanelGENERALBASE {

    private JGUIxAvisosCorreo moCUENTASCORREO;
    private String msTipoEntranteAux;
    private String msServidorEntranteAux;
    private String msUsuarioEntranteAux;
    private String msPassEntranteAux;
    private JTFORMCUENTASCORREOCarga moCarga;
    private JProcesoAccionAbstracX moEnvio;
    private String msPuertoEntranteAux;
    private String msSeguridadEntranteAux;

    /** Creates new form JPanelCUENTASCORREO*/
    public JPanelCUENTASCORREO() {
        super();
        initComponents();
    }

    public void setDatos(final JGUIxAvisosCorreo poCUENTASCORREO, final IPanelControlador poPadre) throws Exception {
        moCUENTASCORREO = poCUENTASCORREO;
        moPadre = poPadre;
        clonar(moPadre.getConsulta());
    }

    public String getTitulo() {
        String lsResult;
        if(moCUENTASCORREO.getTipoModif() == JListDatos.mclNuevo) {
            lsResult= "Cuenta correo [Nuevo]" ;
        }else{
            lsResult="Cuenta correo" + " " +
                moCUENTASCORREO.getEnviar().getCorreoNombre();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return null;
    }

    public void rellenarPantalla() throws Exception {


//        ponemos los textos a los label

        cmbTIPOENTRANTE.borrarTodo();
        cmbTIPOENTRANTE.addLinea("POP3", JGUIxAvisosCorreoLeer.mcsServidorPOP3+JFilaDatosDefecto.mcsSeparacion1);
        cmbTIPOENTRANTE.addLinea("IMAP", JGUIxAvisosCorreoLeer.mcsServidorIMAP+JFilaDatosDefecto.mcsSeparacion1);
        
        cmbSeguridadLeer.borrarTodo();
        cmbSeguridadLeer.addLinea("", String.valueOf(JGUIxAvisosCorreoEnviar.mclSeguridadNada)+JFilaDatosDefecto.mcsSeparacion1);
        cmbSeguridadLeer.addLinea("SSL/TLS", String.valueOf(JGUIxAvisosCorreoEnviar.mclSeguridadSSL)+JFilaDatosDefecto.mcsSeparacion1);
        cmbSeguridadLeer.addLinea("startTLS", String.valueOf(JGUIxAvisosCorreoEnviar.mclSeguridadstarttls)+JFilaDatosDefecto.mcsSeparacion1);
        
        cmbSeguridadEnviar.borrarTodo();
        cmbSeguridadEnviar.addLinea("", String.valueOf(JGUIxAvisosCorreoEnviar.mclSeguridadNada)+JFilaDatosDefecto.mcsSeparacion1);
        cmbSeguridadEnviar.addLinea("SSL/TLS", String.valueOf(JGUIxAvisosCorreoEnviar.mclSeguridadSSL)+JFilaDatosDefecto.mcsSeparacion1);
        cmbSeguridadEnviar.addLinea("startTLS", String.valueOf(JGUIxAvisosCorreoEnviar.mclSeguridadstarttls)+JFilaDatosDefecto.mcsSeparacion1);
                
        
        lblEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/proceso.gif")));
        lblSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/proceso.gif")));
        lblPrueba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/proceso.gif")));
        
        
        lblEntrada.setVisible(false);
        lblSalida.setVisible(false);
        lblPrueba.setVisible(false);     

    }

    public void habilitarSegunEdicion() throws Exception {
        if(moCUENTASCORREO.getTipoModif()== JListDatos.mclNuevo) {
        }else{
        }
    }

    @Override
    public void ponerTipoTextos() throws Exception {

        txtNOMBRE.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtDIRECCION.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtSERVIDORENTRANTE.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtUSUARIOENTRANTE.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtSERVIDORSALIENTE.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtUSUARIOSALIENTE.setTipo(JTipoTextoEstandar.mclTextCadena);
        txtCORREO_PUERTOLEER.setTipo(JTipoTextoEstandar.mclTextNumeroEntero);
        txtCORREO_PUERTOENVIAR.setTipo(JTipoTextoEstandar.mclTextNumeroEntero);
        
    }

    public void mostrarDatos() throws Exception {
        
        txtNOMBRE.setValueTabla(moCUENTASCORREO.getEnviar().getCorreoNombre());
        txtDIRECCION.setValueTabla(moCUENTASCORREO.getEnviar().getCorreo());
        
        cmbTIPOENTRANTE.setValueTabla(moCUENTASCORREO.getLeer().getTipoEntrante()+ JFilaDatosDefecto.mcsSeparacion1);
        pssPASSENTRANTE.setText(moCUENTASCORREO.getLeer().getPassword());
        txtSERVIDORENTRANTE.setValueTabla(moCUENTASCORREO.getLeer().getServidor());
        cmbSeguridadLeer.setValueTabla(moCUENTASCORREO.getLeer().getSeguridad()+ JFilaDatosDefecto.mcsSeparacion1);
        txtCORREO_PUERTOLEER.setText(String.valueOf(moCUENTASCORREO.getLeer().getPuerto()));
        txtUSUARIOENTRANTE.setValueTabla(moCUENTASCORREO.getLeer().getUsuario());
        
        pssPASSSALIENTE.setText(moCUENTASCORREO.getEnviar().getPassword());
        txtSERVIDORSALIENTE.setText(moCUENTASCORREO.getEnviar().getServidor());
        cmbSeguridadEnviar.setValueTabla(moCUENTASCORREO.getEnviar().getSeguridad()+ JFilaDatosDefecto.mcsSeparacion1);
        txtCORREO_PUERTOENVIAR.setText(String.valueOf(moCUENTASCORREO.getEnviar().getPuerto()));
        txtUSUARIOSALIENTE.setValueTabla(moCUENTASCORREO.getEnviar().getUsuario());
        
        cmbCARPETAENTRADA.borrarTodo();
        cmbCARPETASALIDA.borrarTodo();
        
        cmbCARPETAENTRADA.addLinea("", "");
        cmbCARPETASALIDA.addLinea("", "");

        if(!JCadenas.isVacio(moCUENTASCORREO.getLeer().getCarpetaCorreo())){
            cmbCARPETAENTRADA.addLinea(moCUENTASCORREO.getLeer().getCarpetaCorreo(), moCUENTASCORREO.getLeer().getCarpetaCorreo() + JFilaDatosDefecto.mcsSeparacion1);
        }
        if(!JCadenas.isVacio(moCUENTASCORREO.getEnviar().getCarpetaCorreo())){
            cmbCARPETASALIDA.addLinea(moCUENTASCORREO.getEnviar().getCarpetaCorreo(), moCUENTASCORREO.getEnviar().getCarpetaCorreo() + JFilaDatosDefecto.mcsSeparacion1);
        }
        cmbCARPETAENTRADA.mbSeleccionarClave(moCUENTASCORREO.getLeer().getCarpetaCorreo() + JFilaDatosDefecto.mcsSeparacion1);
        cmbCARPETASALIDA.mbSeleccionarClave(moCUENTASCORREO.getEnviar().getCarpetaCorreo() + JFilaDatosDefecto.mcsSeparacion1);
        
        getCarpetasCorreo();
        
    }

    public void establecerDatos() throws Exception {
        establecerDatos(moCUENTASCORREO);
    }
        
    public void establecerDatos(JGUIxAvisosCorreo poCUENTASCORREO) throws Exception {
        poCUENTASCORREO.getEnviar().setCorreoNombre(txtNOMBRE.getText());
        poCUENTASCORREO.getEnviar().setCorreo(txtDIRECCION.getText());
        
        poCUENTASCORREO.getLeer().setTipoEntrante(cmbTIPOENTRANTE.getFilaActual().msCampo(0));
        poCUENTASCORREO.getLeer().setPassword(pssPASSENTRANTE.getText());
        poCUENTASCORREO.getLeer().setServidor(txtSERVIDORENTRANTE.getText());
        poCUENTASCORREO.getLeer().setSeguridad((int)JConversiones.cdbl(cmbSeguridadLeer.getFilaActual().msCampo(0)));
        poCUENTASCORREO.getLeer().setPuerto((int)JConversiones.cdbl(txtCORREO_PUERTOLEER.getText()));
        poCUENTASCORREO.getLeer().setUsuario(txtUSUARIOENTRANTE.getText());
        
        poCUENTASCORREO.getEnviar().setPassword(pssPASSSALIENTE.getText());
        poCUENTASCORREO.getEnviar().setServidor(txtSERVIDORSALIENTE.getText());
        poCUENTASCORREO.getEnviar().setSeguridad((int)JConversiones.cdbl(cmbSeguridadEnviar.getFilaActual().msCampo(0)));
        poCUENTASCORREO.getEnviar().setPuerto((int)JConversiones.cdbl(txtCORREO_PUERTOENVIAR.getText()));
        poCUENTASCORREO.getEnviar().setUsuario(txtUSUARIOSALIENTE.getText());
        
        
        poCUENTASCORREO.getLeer().setCarpetaCorreo(cmbCARPETAENTRADA.getFilaActual().msCampo(0));
        poCUENTASCORREO.getEnviar().setCarpetaCorreo(cmbCARPETASALIDA.getFilaActual().msCampo(0));
        
        
    }

    public void aceptar() throws Exception {
        IResultado loResult=new JResultado("", true);
        if(loResult.getBien()){
            if(moPadre!=null){
                ((JT2CUENTASCORREO)moPadre).datosactualizados(moCUENTASCORREO);
            }
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    @Override
    public void cancelar() throws Exception {
        
    }
    
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 640, 350);
    }

    private void getCarpetasCorreo() {
        try {


            String lsTipoEntrante = (String) cmbTIPOENTRANTE.getFilaActual().msCampo(0);
            String lsServidorEntrante = (String) txtSERVIDORENTRANTE.getText();
            String lsUsuarioEntrante = (String) txtUSUARIOENTRANTE.getText();
            String lsPassEntrante = new String(pssPASSENTRANTE.getPassword());
            String lsPuertoEntrante = txtCORREO_PUERTOLEER.getText();
            String lsSeguridadEntrante = cmbSeguridadLeer.getFilaActual().msCampo(0);

            // En caso de que algun campo haya cambiado
            if (!lsTipoEntrante.equals(msTipoEntranteAux) 
                    || !lsServidorEntrante.equals(msServidorEntranteAux) 
                    || !lsUsuarioEntrante.equals(msUsuarioEntranteAux)
                    || !lsPassEntrante.equals(msPassEntranteAux)
                    || !lsPuertoEntrante.equals(msPuertoEntranteAux)
                    || !lsSeguridadEntrante.equals(msSeguridadEntranteAux)
                    ) {
                msTipoEntranteAux = lsTipoEntrante;
                msServidorEntranteAux = lsServidorEntrante;
                msUsuarioEntranteAux = lsUsuarioEntrante;
                msPassEntranteAux = lsPassEntrante;
                msPuertoEntranteAux = lsPuertoEntrante;
                msSeguridadEntranteAux = lsSeguridadEntrante;
                

                if (moCarga != null) {
                    moCarga.setCancelado(true);
                }
                moCarga = new JTFORMCUENTASCORREOCarga();
                JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup().addProcesoYEjecutar(moCarga, false);
            }
     
        } catch (Exception e) {
            JMsgBox.mensajeInformacion(this, e.getMessage());
        }

    }
    

    class JTFORMCUENTASCORREOCarga extends JProcesoAccionAbstracX {


        JTFORMCUENTASCORREOCarga() throws CloneNotSupportedException {
        }

        @Override
        public String getTitulo() {
            return "Carga carpetas";
        }

        @Override
        public int getNumeroRegistros() {
            return -1;
        }

        @Override
        public void procesar() throws Throwable {

            System.gc();   

            // Si todos los campos estan cubiertos
            if (!msTipoEntranteAux.equals("")
                    && !msServidorEntranteAux.equals("")
                    && !msUsuarioEntranteAux.equals("")
                    && !msPassEntranteAux.equals("")) {

                try{
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            lblEntrada.setVisible(true);
                            lblSalida.setVisible(true);
                        }
                    });                
                    JGUIxAvisosCorreo loCorreo = (JGUIxAvisosCorreo) moCUENTASCORREO.clone();
                    loCorreo.inicializar();
                    establecerDatos(loCorreo);
                    final IListaElementos loListaCarpetas;
                    if(!mbCancelado){
                        loListaCarpetas = loCorreo.getLeer().getListaCarpetas();
                    } else {
                        loListaCarpetas = null;
                    }
                    if(!mbCancelado){
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                final String lsCarpetaEntrada = cmbCARPETAENTRADA.getFilaActual().msCampo(0);
                                final String lsCarpetaSalida = cmbCARPETASALIDA.getFilaActual().msCampo(0);
                                cmbCARPETAENTRADA.borrarTodo();
                                cmbCARPETASALIDA.borrarTodo();

                                cmbCARPETAENTRADA.addLinea("", "");
                                cmbCARPETASALIDA.addLinea("", "");

                                for (int i = 0; i < loListaCarpetas.size() && !mbCancelado; i++) {
                                    String lsNombreCarpeta = (String) loListaCarpetas.get(i);
                                    cmbCARPETAENTRADA.addLinea(lsNombreCarpeta, lsNombreCarpeta+JFilaDatosDefecto.mcsSeparacion1);
                                    cmbCARPETASALIDA.addLinea(lsNombreCarpeta, lsNombreCarpeta+JFilaDatosDefecto.mcsSeparacion1);
                                }
                                cmbCARPETAENTRADA.mbSeleccionarClave(lsCarpetaEntrada+JFilaDatosDefecto.mcsSeparacion1);
                                cmbCARPETASALIDA.mbSeleccionarClave(lsCarpetaSalida+JFilaDatosDefecto.mcsSeparacion1);
                            }
                        });
                    }
                }finally{
                    if(!mbCancelado){
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                lblEntrada.setVisible(false);
                                lblSalida.setVisible(false);
                            }
                        });
                    }
                }
            } else{
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        lblEntrada.setVisible(false);
                        lblSalida.setVisible(false);
                    }
                });
            }

            mbFin = true;
        }

        @Override
        public String getTituloRegistroActual() {
            return "";
        }

        @Override
        public void mostrarMensaje(String psMensaje) {
        }

        @Override
        public void mostrarError(Throwable e) {
            if(!mbCancelado){
                super.mostrarError(e); 
            }
        }


    }
    
    /**
     * Este metodo es llamado desde el constructor para inicializar el
     * formulario. AVISO: No modificar este codigo. El contenido de este metodo
     * es siempre regenerado por el editor de formularios.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelGENERAL = new javax.swing.JPanel();
        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        lblDIRECCION = new utilesGUIx.JLabelCZ();
        txtDIRECCION = new utilesGUIx.JTextFieldCZ();
        jPanel2 = new javax.swing.JPanel();
        lblTIPOENTRANTE = new utilesGUIx.JLabelCZ();
        cmbTIPOENTRANTE = new utilesGUIx.JComboBoxCZ();
        lblSERVIDORENTRANTE = new utilesGUIx.JLabelCZ();
        txtSERVIDORENTRANTE = new utilesGUIx.JTextFieldCZ();
        jLabelCZ11 = new utilesGUIx.JLabelCZ();
        cmbSeguridadLeer = new utilesGUIx.JComboBoxCZ();
        jLabelCZ12 = new utilesGUIx.JLabelCZ();
        txtCORREO_PUERTOLEER = new utilesGUIx.JTextFieldCZ();
        lblUSUARIOENTRANTE = new utilesGUIx.JLabelCZ();
        txtUSUARIOENTRANTE = new utilesGUIx.JTextFieldCZ();
        lblPASSENTRANTE = new utilesGUIx.JLabelCZ();
        pssPASSENTRANTE = new javax.swing.JPasswordField();
        lblCARPETAENTRADA = new utilesGUIx.JLabelCZ();
        jPanel4 = new javax.swing.JPanel();
        cmbCARPETAENTRADA = new utilesGUIx.JComboBoxCZ();
        lblEntrada = new javax.swing.JLabel();
        lblCARPETASALIDA = new utilesGUIx.JLabelCZ();
        jPanel5 = new javax.swing.JPanel();
        cmbCARPETASALIDA = new utilesGUIx.JComboBoxCZ();
        lblSalida = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblSERVIDORSALIENTE = new utilesGUIx.JLabelCZ();
        txtSERVIDORSALIENTE = new utilesGUIx.JTextFieldCZ();
        jLabelCZ13 = new utilesGUIx.JLabelCZ();
        cmbSeguridadEnviar = new utilesGUIx.JComboBoxCZ();
        jLabelCZ14 = new utilesGUIx.JLabelCZ();
        txtCORREO_PUERTOENVIAR = new utilesGUIx.JTextFieldCZ();
        lblUSUARIOSALIENTE = new utilesGUIx.JLabelCZ();
        txtUSUARIOSALIENTE = new utilesGUIx.JTextFieldCZ();
        lblPASSSALIENTE = new utilesGUIx.JLabelCZ();
        pssPASSSALIENTE = new javax.swing.JPasswordField();
        btnProbar = new utilesGUIx.JButtonCZ();
        lblPrueba = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

        lblNOMBRE.setText("Nombre");
        lblNOMBRE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblNOMBRE, gridBagConstraints);

        txtNOMBRE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtNOMBRE.setMinimumSize(new java.awt.Dimension(6, 17));
        txtNOMBRE.setPreferredSize(new java.awt.Dimension(6, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtNOMBRE, gridBagConstraints);

        lblDIRECCION.setText("Dirección");
        lblDIRECCION.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblDIRECCION, gridBagConstraints);

        txtDIRECCION.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtDIRECCION.setMinimumSize(new java.awt.Dimension(6, 17));
        txtDIRECCION.setPreferredSize(new java.awt.Dimension(6, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtDIRECCION, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del correo entrante"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblTIPOENTRANTE.setText("Tipo entrada");
        lblTIPOENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(lblTIPOENTRANTE, gridBagConstraints);

        cmbTIPOENTRANTE.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTIPOENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cmbTIPOENTRANTE.setMinimumSize(new java.awt.Dimension(4, 17));
        cmbTIPOENTRANTE.setPreferredSize(new java.awt.Dimension(4, 17));
        cmbTIPOENTRANTE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbTIPOENTRANTEFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(cmbTIPOENTRANTE, gridBagConstraints);

        lblSERVIDORENTRANTE.setText("Servidor entrada");
        lblSERVIDORENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(lblSERVIDORENTRANTE, gridBagConstraints);

        txtSERVIDORENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtSERVIDORENTRANTE.setMinimumSize(new java.awt.Dimension(4, 17));
        txtSERVIDORENTRANTE.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(txtSERVIDORENTRANTE, gridBagConstraints);

        jLabelCZ11.setText("Seguridad entrada");
        jLabelCZ11.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(jLabelCZ11, gridBagConstraints);

        cmbSeguridadLeer.setMinimumSize(new java.awt.Dimension(4, 17));
        cmbSeguridadLeer.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(cmbSeguridadLeer, gridBagConstraints);

        jLabelCZ12.setText("Puerto entrada");
        jLabelCZ12.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(jLabelCZ12, gridBagConstraints);

        txtCORREO_PUERTOLEER.setMinimumSize(new java.awt.Dimension(4, 17));
        txtCORREO_PUERTOLEER.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(txtCORREO_PUERTOLEER, gridBagConstraints);

        lblUSUARIOENTRANTE.setText("Usuario entrada");
        lblUSUARIOENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(lblUSUARIOENTRANTE, gridBagConstraints);

        txtUSUARIOENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtUSUARIOENTRANTE.setMinimumSize(new java.awt.Dimension(4, 17));
        txtUSUARIOENTRANTE.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(txtUSUARIOENTRANTE, gridBagConstraints);

        lblPASSENTRANTE.setText("Contraseña entrada");
        lblPASSENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(lblPASSENTRANTE, gridBagConstraints);

        pssPASSENTRANTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        pssPASSENTRANTE.setText("jPasswordField1");
        pssPASSENTRANTE.setMinimumSize(new java.awt.Dimension(4, 17));
        pssPASSENTRANTE.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(pssPASSENTRANTE, gridBagConstraints);

        lblCARPETAENTRADA.setText("Carpeta entrada");
        lblCARPETAENTRADA.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(lblCARPETAENTRADA, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        cmbCARPETAENTRADA.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cmbCARPETAENTRADA.setMinimumSize(new java.awt.Dimension(4, 17));
        cmbCARPETAENTRADA.setPreferredSize(new java.awt.Dimension(4, 17));
        cmbCARPETAENTRADA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbCARPETAENTRADAFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel4.add(cmbCARPETAENTRADA, gridBagConstraints);

        lblEntrada.setText(" ");
        jPanel4.add(lblEntrada, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel4, gridBagConstraints);

        lblCARPETASALIDA.setText("Carpeta salida");
        lblCARPETASALIDA.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel2.add(lblCARPETASALIDA, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        cmbCARPETASALIDA.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        cmbCARPETASALIDA.setMinimumSize(new java.awt.Dimension(4, 17));
        cmbCARPETASALIDA.setPreferredSize(new java.awt.Dimension(4, 17));
        cmbCARPETASALIDA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbCARPETASALIDAFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel5.add(cmbCARPETASALIDA, gridBagConstraints);

        lblSalida.setText(" ");
        jPanel5.add(lblSalida, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelGENERAL.add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del correo saliente"));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblSERVIDORSALIENTE.setText("Servidor salida");
        lblSERVIDORSALIENTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(lblSERVIDORSALIENTE, gridBagConstraints);

        txtSERVIDORSALIENTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtSERVIDORSALIENTE.setMinimumSize(new java.awt.Dimension(6, 17));
        txtSERVIDORSALIENTE.setPreferredSize(new java.awt.Dimension(6, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(txtSERVIDORSALIENTE, gridBagConstraints);

        jLabelCZ13.setText("Seguridad salida");
        jLabelCZ13.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(jLabelCZ13, gridBagConstraints);

        cmbSeguridadEnviar.setMinimumSize(new java.awt.Dimension(4, 17));
        cmbSeguridadEnviar.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(cmbSeguridadEnviar, gridBagConstraints);

        jLabelCZ14.setText("Puerto salida");
        jLabelCZ14.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(jLabelCZ14, gridBagConstraints);

        txtCORREO_PUERTOENVIAR.setMinimumSize(new java.awt.Dimension(4, 17));
        txtCORREO_PUERTOENVIAR.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(txtCORREO_PUERTOENVIAR, gridBagConstraints);

        lblUSUARIOSALIENTE.setText("Usuario salida");
        lblUSUARIOSALIENTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(lblUSUARIOSALIENTE, gridBagConstraints);

        txtUSUARIOSALIENTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        txtUSUARIOSALIENTE.setMinimumSize(new java.awt.Dimension(4, 17));
        txtUSUARIOSALIENTE.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(txtUSUARIOSALIENTE, gridBagConstraints);

        lblPASSSALIENTE.setText("Contraseña salida");
        lblPASSSALIENTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(lblPASSSALIENTE, gridBagConstraints);

        pssPASSSALIENTE.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        pssPASSSALIENTE.setText("jPasswordField2");
        pssPASSSALIENTE.setMinimumSize(new java.awt.Dimension(4, 17));
        pssPASSSALIENTE.setPreferredSize(new java.awt.Dimension(4, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(pssPASSSALIENTE, gridBagConstraints);

        btnProbar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIxAvisos/images/SendMail24.gif"))); // NOI18N
        btnProbar.setText("Enviar correo de prueba");
        btnProbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProbarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(btnProbar, gridBagConstraints);

        lblPrueba.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel3.add(lblPrueba, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        jPanelGENERAL.add(jPanel3, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanelGENERAL.add(jPanel1, gridBagConstraints);

        jTabbedPane1.addTab("General", jPanelGENERAL);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jTabbedPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCARPETAENTRADAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbCARPETAENTRADAFocusGained
        getCarpetasCorreo();
    }//GEN-LAST:event_cmbCARPETAENTRADAFocusGained

    private void cmbCARPETASALIDAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbCARPETASALIDAFocusGained
        getCarpetasCorreo();
    }//GEN-LAST:event_cmbCARPETASALIDAFocusGained

    private void cmbTIPOENTRANTEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbTIPOENTRANTEFocusLost
        getCarpetasCorreo();
    }//GEN-LAST:event_cmbTIPOENTRANTEFocusLost

    private void btnProbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProbarActionPerformed
        try{
            lblPrueba.setVisible(true);
            btnProbar.setEnabled(false);
            if(moEnvio!=null){
                moEnvio.setCancelado(true);
            }
            moEnvio = new JProcesoAccionAbstracX() {
                public String getTitulo() {
                    msTituloRegistroActual="";
                    return "Mensaje de prueba";
                }

                public int getNumeroRegistros() {
                    return -1;
                }

                public void procesar() throws Throwable {
                    try{
                        msTituloRegistroActual="";
                        JMensaje loMensaje = new JMensaje(new String[]{txtDIRECCION.getText()}, "Prueba", "prueba", null);
                        JGUIxAvisosCorreo loCorreo = (JGUIxAvisosCorreo) moCUENTASCORREO.clone();
                        loCorreo.inicializar();
                        establecerDatos(loCorreo);
                        loCorreo.getEnviar().enviarEmail(loMensaje);
                    }finally{
                        if(!mbCancelado){
                            SwingUtilities.invokeAndWait(new Runnable() {
                                public void run() {
                                    lblPrueba.setVisible(false);
                                }
                            });
                        }
                    }
                }

                @Override
                public void setCancelado(boolean pbCancelado) {
                    super.setCancelado(pbCancelado); 
                }
                
                @Override
                public void mostrarError(Throwable e) {
                    if(!mbCancelado){
                        super.mostrarError(e);
                    }
                }
                
                public void mostrarMensaje(String string) {
                    if(!mbCancelado){
                        JMsgBox.mensajeInformacion(JPanelCUENTASCORREO.this, "Prueba correcta");
                    }
                }
            };
            JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto().getThreadGroup().addProcesoYEjecutar(moEnvio, false);
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        } finally{
            btnProbar.setEnabled(true);
        }
    }//GEN-LAST:event_btnProbarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnProbar;
    private utilesGUIx.JComboBoxCZ cmbCARPETAENTRADA;
    private utilesGUIx.JComboBoxCZ cmbCARPETASALIDA;
    private utilesGUIx.JComboBoxCZ cmbSeguridadEnviar;
    private utilesGUIx.JComboBoxCZ cmbSeguridadLeer;
    private utilesGUIx.JComboBoxCZ cmbTIPOENTRANTE;
    private utilesGUIx.JLabelCZ jLabelCZ11;
    private utilesGUIx.JLabelCZ jLabelCZ12;
    private utilesGUIx.JLabelCZ jLabelCZ13;
    private utilesGUIx.JLabelCZ jLabelCZ14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelGENERAL;
    private javax.swing.JTabbedPane jTabbedPane1;
    private utilesGUIx.JLabelCZ lblCARPETAENTRADA;
    private utilesGUIx.JLabelCZ lblCARPETASALIDA;
    private utilesGUIx.JLabelCZ lblDIRECCION;
    private javax.swing.JLabel lblEntrada;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JLabelCZ lblPASSENTRANTE;
    private utilesGUIx.JLabelCZ lblPASSSALIENTE;
    private javax.swing.JLabel lblPrueba;
    private utilesGUIx.JLabelCZ lblSERVIDORENTRANTE;
    private utilesGUIx.JLabelCZ lblSERVIDORSALIENTE;
    private javax.swing.JLabel lblSalida;
    private utilesGUIx.JLabelCZ lblTIPOENTRANTE;
    private utilesGUIx.JLabelCZ lblUSUARIOENTRANTE;
    private utilesGUIx.JLabelCZ lblUSUARIOSALIENTE;
    private javax.swing.JPasswordField pssPASSENTRANTE;
    private javax.swing.JPasswordField pssPASSSALIENTE;
    private utilesGUIx.JTextFieldCZ txtCORREO_PUERTOENVIAR;
    private utilesGUIx.JTextFieldCZ txtCORREO_PUERTOLEER;
    private utilesGUIx.JTextFieldCZ txtDIRECCION;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    private utilesGUIx.JTextFieldCZ txtSERVIDORENTRANTE;
    private utilesGUIx.JTextFieldCZ txtSERVIDORSALIENTE;
    private utilesGUIx.JTextFieldCZ txtUSUARIOENTRANTE;
    private utilesGUIx.JTextFieldCZ txtUSUARIOSALIENTE;
    // End of variables declaration//GEN-END:variables
}

