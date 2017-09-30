/*
* JPanelGUIXEVENTOS.java
*
* Creado el 3/11/2011
*/

package utilesGUIxAvisos.forms;

import ListDatos.*;
import java.awt.Color;
import utiles.JComunicacion;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.seguridad.JTPlugInUsuarios;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.tablasControladoras.JT2GUIXAVISOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOSPRIORIDAD;

public class JPanelGUIXEVENTOS extends JPanelGENERALBASE {

    private JTEEGUIXEVENTOS moGUIXEVENTOS;
    private String msFechas;
    private String msTipoRepeOriginal;
    private JDatosGenerales moDatosGenerales;

    /** Creates new form JPanelGUIXEVENTOS*/
    public JPanelGUIXEVENTOS() {
        super();
        initComponents();
        
    }

    public void setDatos(JDatosGenerales poDatosGenerales, final JTEEGUIXEVENTOS poGUIXEVENTOS, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moGUIXEVENTOS = poGUIXEVENTOS;
        moPadre = poPadre;
        moConsulta = poConsulta;
        moDatosGenerales=poDatosGenerales;
        
        if(poConsulta!=null){
            clonar(poConsulta.getList());
        }
    }

    @Override
    public String getTitulo() {
        String lsResult;
        if(moGUIXEVENTOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOS.msCTabla) + " " + 
                moGUIXEVENTOS.getCODIGO().getString() + " " + moGUIXEVENTOS.getNOMBRE().getString();
        }
        return lsResult;
    }

    @Override
    public JSTabla getTabla(){
        return moGUIXEVENTOS;
    }

    @Override
    public void rellenarPantalla() throws Exception {

        jPanelBusquedaPrioridad.setDatos(JTEEGUIXEVENTOSPRIORIDAD.getParamPanelBusq(moDatosGenerales));
        jPanelBusquedaCalendario.setDatos(JTEEGUIXCALENDARIO.getParamPanelBusq(moDatosGenerales));
        
        if(JGUIxConfigGlobalModelo.getInstancia().getPlugInSeguridad()==null){
            jPanelBusquedaUsuarioAsignado.setVisible(false);
        } else {
            JTPlugInUsuarios loUsu = JGUIxConfigGlobalModelo.getInstancia().getPlugInSeguridad().getUsuarios();
            utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
            loParam.mlCamposPrincipales = loUsu.moList.getFields().malCamposPrincipales();
            loParam.masTextosDescripciones = null;
            loParam.mbConDatos=true;
            loParam.mbMensajeSiNoExiste=true;
            loParam.moTabla=loUsu;
            loParam.malDescripciones = new int[]{
                loUsu.lPosiNOMBRE, loUsu.lPosiNOMBRECOMPLETO
            };
            jPanelBusquedaUsuarioAsignado.setDatos(loParam);
            jPanelBusquedaUsuario.setDatos(loParam);
        }
        
        cmbREPE_TIPO.borrarTodo();
        cmbREPE_TIPO.addLinea("Evento único", JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPO.addLinea("Diario", JTEEGUIXEVENTOS.mcsRepeticionesTipoDIA + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPO.addLinea("Semanal", JTEEGUIXEVENTOS.mcsRepeticionesTipoSEMANAS + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPO.addLinea("Mesual", JTEEGUIXEVENTOS.mcsRepeticionesTipoMESES + JFilaDatosDefecto.mcsSeparacion1);
        cmbREPE_TIPO.addLinea("Anual", JTEEGUIXEVENTOS.mcsRepeticionesTipoANYO + JFilaDatosDefecto.mcsSeparacion1);
        
        //ponemos los textos a los label
        lblFECHADESDE.setField(moGUIXEVENTOS.getFECHADESDE());
        txtFECHADESDE.setField(moGUIXEVENTOS.getFECHADESDE());
        lblFECHAHASTA.setField(moGUIXEVENTOS.getFECHAHASTA());
        txtFECHAHASTA.setField(moGUIXEVENTOS.getFECHAHASTA());
        lblNOMBRE.setField(moGUIXEVENTOS.getNOMBRE());
        txtNOMBRE.setField(moGUIXEVENTOS.getNOMBRE());
        lblTEXTO.setField(moGUIXEVENTOS.getTEXTO());
        txtTEXTO.setField(moGUIXEVENTOS.getTEXTO());
        lblREPETICION.setField(moGUIXEVENTOS.getREPETICION());
        lblGRUPO.setField(moGUIXEVENTOS.getGRUPO());
        txtGRUPO.setField(moGUIXEVENTOS.getGRUPO());
        
        txtNUMERO.setLongitudTextoMaxima(2);
        
        jPanelBusquedaPrioridad.setField(moGUIXEVENTOS.getPRIORIDAD());
        jPanelBusquedaPrioridad.setLabel(moGUIXEVENTOS.getPRIORIDAD().getCaption());
        
        jPanelBusquedaCalendario.setField(moGUIXEVENTOS.getCALENDARIO());
        jPanelBusquedaCalendario.setLabel(moGUIXEVENTOS.getCALENDARIO().getCaption());
        
        jPanelBusquedaUsuarioAsignado.setField(moGUIXEVENTOS.getUSUARIOASIGNADO());
        jPanelBusquedaUsuario.setField(moGUIXEVENTOS.getUSUARIO());
        
        
        chkEVENTOSN.setField(moGUIXEVENTOS.getEVENTOSN());
        chkEVENTOSN.setForeground(Color.WHITE);
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
//        if(!moGUIXEVENTOS.getGRUPO().isVacio()){
            txtGRUPO.setVisible(false);
            lblGRUPO.setVisible(false);
//        }
            btnDatosRelacionados.setVisible(
                    moDatosGenerales.isDatosRelacionados()
                    && !moGUIXEVENTOS.getGRUPO().isVacio());
            chkEVENTOSN.setVisible(moGUIXEVENTOS.getList().getModoTabla()!=JListDatos.mclNuevo);
            jPanelBusquedaUsuario.setEnabled(false);
    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    @Override
    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;
        msFechas = moGUIXEVENTOS.getFECHADESDE().getString()+moGUIXEVENTOS.getFECHAHASTA().getString();
        msTipoRepeOriginal = moGUIXEVENTOS.getRepeticionesTipo();
        cmbREPE_TIPO.setValueTabla(msTipoRepeOriginal+JFilaDatosDefecto.mcsSeparacion1);
        txtNUMERO.setValueTabla(String.valueOf(moGUIXEVENTOS.getRepeticionesNumero()));
        chkEVENTOSN.setForeground(Color.WHITE);
        
        jTabbedPane1StateChanged(null);
    }

    @Override
    public void establecerDatos() throws Exception {
        String lsFechas = moGUIXEVENTOS.getFECHADESDE().getString()+moGUIXEVENTOS.getFECHAHASTA().getString();
        if(!lsFechas.equals(msFechas)) {
            moGUIXEVENTOS.getEVENTOSN().setValue(false);
        }
        moGUIXEVENTOS.setRepeticiones(cmbREPE_TIPO.getFilaActual().msCampo(0), txtNUMERO.getText());
        moGUIXEVENTOS.validarCampos();
    }

    @Override
    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        if(!moGUIXEVENTOS.getRepeticionesTipo().equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO)
             || (!msTipoRepeOriginal.equals("") && !msTipoRepeOriginal.equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO) )   ){
            int lTipo = JTEEGUIXEVENTOS.mclTodos;
            if(lModo!=JListDatos.mclNuevo){
                JPanelOpcionesRepe loPanel = new JPanelOpcionesRepe();
                JComunicacion loComu = new JComunicacion();
                loComu.moObjecto = String.valueOf(lTipo);
                loPanel.setDatos(moDatosGenerales, loComu);
                moDatosGenerales.getMostrarPantalla().mostrarForm(new JMostrarPantallaParam(loPanel, 400, 400, JMostrarPantalla.mclEdicionDialog, "Opciones"));
                lTipo = Integer.valueOf(loComu.moObjecto.toString());
            }
            IResultado loResult=JTEEGUIXEVENTOS.procesarRepeticiones(moDatosGenerales, moGUIXEVENTOS, lTipo, moGUIXEVENTOS.moList.moServidor);
            if(loResult.getBien()){
                moPadre.refrescar();
            }else{
                throw new Exception(loResult.getMensaje());
            }
        }else{
            IResultado loResult=moGUIXEVENTOS.guardar(moDatosGenerales);
            if(loResult.getBien()){
                 actualizarPadre(lModo);
            }else{
                throw new Exception(loResult.getMensaje());
            }
        }
    }
    

    @Override
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 400);
    }
    @Override
    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        super.setBloqueoControles(pbBloqueo);
        setBloqueoControlesContainer(jPanelGenericoAvisos,false);
   }
    
    private void compruebaPK() throws Exception {
        if(
            moGUIXEVENTOS.getCODIGO().isVacio()
          ) {
            jTabbedPane1.setSelectedIndex(0);
            throw new Exception("Es necesario guardar datos antes de continuar");
        }
    }
    /** Este metodo es llamado desde el constructor para
     *  inicializar el formulario.
     *  AVISO: No modificar este codigo. El contenido de este metodo es
     *  siempre regenerado por el editor de formularios.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelGENERAL = new javax.swing.JPanel();
        chkEVENTOSN = new utilesGUIx.JCheckBoxCZ();
        jPanelBusquedaCalendario = new utilesGUIx.panelesGenericos.JPanelBusquedaCombo();
        lblFECHADESDE = new utilesGUIx.JLabelCZ();
        txtFECHADESDE = new utilesGUIx.JTextFieldCZ();
        lblFECHAHASTA = new utilesGUIx.JLabelCZ();
        txtFECHAHASTA = new utilesGUIx.JTextFieldCZ();
        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        jPanel1 = new javax.swing.JPanel();
        jPanelBusquedaPrioridad = new utilesGUIx.panelesGenericos.JPanelBusquedaCombo();
        lblColor = new javax.swing.JLabel();
        lblREPETICION = new utilesGUIx.JLabelCZ();
        cmbREPE_TIPO = new utilesGUIx.JComboBoxCZ();
        lblNUMERO = new utilesGUIx.JLabelCZ();
        txtNUMERO = new utilesGUIx.JTextFieldCZ();
        jPanelBusquedaUsuario = new utilesGUIx.panelesGenericos.JPanelBusquedaCombo();
        jPanelBusquedaUsuarioAsignado = new utilesGUIx.panelesGenericos.JPanelBusquedaCombo();
        btnDatosRelacionados = new utilesGUIx.JButtonCZ();
        lblGRUPO = new utilesGUIx.JLabelCZ();
        txtGRUPO = new utilesGUIx.JTextFieldCZ();
        lblTEXTO = new utilesGUIx.JLabelCZ();
        jScrollTEXTO = new javax.swing.JScrollPane();
        txtTEXTO = new utilesGUIx.JTextAreaCZ();
        jPanelGenericoAvisos = new utilesGUIx.formsGenericos.JPanelGenerico2();

        setLayout(new java.awt.GridBagLayout());

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

        chkEVENTOSN.setBackground(new java.awt.Color(0, 102, 51));
        chkEVENTOSN.setForeground(new java.awt.Color(255, 255, 255));
        chkEVENTOSN.setText("Tarea terminada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelGENERAL.add(chkEVENTOSN, gridBagConstraints);

        jPanelBusquedaCalendario.setLabel("calendario");
        jPanelBusquedaCalendario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jPanelBusquedaCalendarioItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelGENERAL.add(jPanelBusquedaCalendario, gridBagConstraints);

        lblFECHADESDE.setText("FECHADESDE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblFECHADESDE, gridBagConstraints);

        txtFECHADESDE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFECHADESDEFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtFECHADESDE, gridBagConstraints);

        lblFECHAHASTA.setText("FECHAHASTA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblFECHAHASTA, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtFECHAHASTA, gridBagConstraints);

        lblNOMBRE.setText("NOMBRE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblNOMBRE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtNOMBRE, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanelBusquedaPrioridad.setLabel("prioridad");
        jPanelBusquedaPrioridad.addFocusListenerText(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanelBusquedaPrioridadFocusGained(evt);
            }
        });
        jPanelBusquedaPrioridad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jPanelBusquedaPrioridadItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanelBusquedaPrioridad, gridBagConstraints);

        lblColor.setText("        ");
        lblColor.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(lblColor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelGENERAL.add(jPanel1, gridBagConstraints);

        lblREPETICION.setText("REPETICION");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblREPETICION, gridBagConstraints);

        cmbREPE_TIPO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbREPE_TIPOItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(cmbREPE_TIPO, gridBagConstraints);

        lblNUMERO.setText("Nº");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblNUMERO, gridBagConstraints);

        txtNUMERO.setMinimumSize(new java.awt.Dimension(90, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtNUMERO, gridBagConstraints);

        jPanelBusquedaUsuario.setLabel("Creador tarea");
        jPanelBusquedaUsuario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jPanelBusquedaUsuarioItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelGENERAL.add(jPanelBusquedaUsuario, gridBagConstraints);

        jPanelBusquedaUsuarioAsignado.setLabel("Asignar A:");
        jPanelBusquedaUsuarioAsignado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jPanelBusquedaUsuarioAsignadoItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelGENERAL.add(jPanelBusquedaUsuarioAsignado, gridBagConstraints);

        btnDatosRelacionados.setText("Datos relacionados");
        btnDatosRelacionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDatosRelacionadosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(btnDatosRelacionados, gridBagConstraints);

        lblGRUPO.setText("GRUPO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblGRUPO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtGRUPO, gridBagConstraints);

        lblTEXTO.setText("TEXTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblTEXTO, gridBagConstraints);

        txtTEXTO.setColumns(20);
        txtTEXTO.setRows(5);
        jScrollTEXTO.setViewportView(txtTEXTO);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(jScrollTEXTO, gridBagConstraints);

        jTabbedPane1.addTab("General", jPanelGENERAL);
        jTabbedPane1.addTab("Avisos", jPanelGenericoAvisos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jTabbedPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanelBusquedaPrioridadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jPanelBusquedaPrioridadItemStateChanged
        try{
            JTEEGUIXEVENTOSPRIORIDAD loPri = JTEEGUIXEVENTOSPRIORIDAD.getTabla(jPanelBusquedaPrioridad.getText(), moGUIXEVENTOS.moList.moServidor);
            if(loPri.moveFirst()){
                lblColor.setBackground(new Color(loPri.getCOLOR().getInteger()));
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_jPanelBusquedaPrioridadItemStateChanged

    private void jPanelBusquedaCalendarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jPanelBusquedaCalendarioItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelBusquedaCalendarioItemStateChanged

    private void txtFECHADESDEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFECHADESDEFocusLost
        try{
            if(!JDateEdu.isDate(txtFECHAHASTA.getText() )
                    || new JDateEdu(txtFECHADESDE.getText()).compareTo(new JDateEdu(txtFECHAHASTA.getText()))>0
                    ){
               JDateEdu loDate = new JDateEdu(txtFECHADESDE.getText());
               loDate.add(loDate.mclHoras, 1);
               txtFECHAHASTA.setText(loDate.msFormatear("dd/MM/yyyy HH:mm"));                
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_txtFECHADESDEFocusLost

    private void jPanelBusquedaPrioridadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanelBusquedaPrioridadFocusGained
        jPanelBusquedaPrioridadItemStateChanged(null);
    }//GEN-LAST:event_jPanelBusquedaPrioridadFocusGained

    private void btnDatosRelacionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatosRelacionadosActionPerformed
        try{
            moDatosGenerales.mostrarDatosRelacionados(moGUIXEVENTOS);
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }
    }//GEN-LAST:event_btnDatosRelacionadosActionPerformed

    private void cmbREPE_TIPOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbREPE_TIPOItemStateChanged
        try{
            txtNUMERO.setEnabled(
                    !cmbREPE_TIPO.getFilaActual().msCampo(0).equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO)
                    );
            if(txtNUMERO.isEnabled() && (txtNUMERO.getText().equals("") || txtNUMERO.getText().equals("1"))){
                txtNUMERO.setText(JTEEGUIXEVENTOS.mcsREPE_NUMERO_DEFECTO);
            }
            if(cmbREPE_TIPO.getFilaActual().msCampo(0).equalsIgnoreCase(JTEEGUIXEVENTOS.mcsRepeticionesTipoUNICO)){
                txtNUMERO.setText("1");
            }
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_cmbREPE_TIPOItemStateChanged

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        try{
            if(jTabbedPane1.getSelectedIndex()>=0){
                switch(jTabbedPane1.getSelectedIndex()){
                    case 0://General
                        break;
                    case 1://TABLA3CODIGODIRECTIVACEE
                        compruebaPK();
                        jPanelGenericoAvisos.setControlador(new JT2GUIXAVISOS(moDatosGenerales, moGUIXEVENTOS.msCTabla, moGUIXEVENTOS.moList.moFila()));

                        break;
                    
                }
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jPanelBusquedaUsuarioAsignadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jPanelBusquedaUsuarioAsignadoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelBusquedaUsuarioAsignadoItemStateChanged

    private void jPanelBusquedaUsuarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jPanelBusquedaUsuarioItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelBusquedaUsuarioItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnDatosRelacionados;
    private utilesGUIx.JCheckBoxCZ chkEVENTOSN;
    private utilesGUIx.JComboBoxCZ cmbREPE_TIPO;
    private javax.swing.JPanel jPanel1;
    private utilesGUIx.panelesGenericos.JPanelBusquedaCombo jPanelBusquedaCalendario;
    private utilesGUIx.panelesGenericos.JPanelBusquedaCombo jPanelBusquedaPrioridad;
    private utilesGUIx.panelesGenericos.JPanelBusquedaCombo jPanelBusquedaUsuario;
    private utilesGUIx.panelesGenericos.JPanelBusquedaCombo jPanelBusquedaUsuarioAsignado;
    private javax.swing.JPanel jPanelGENERAL;
    private utilesGUIx.formsGenericos.JPanelGenerico2 jPanelGenericoAvisos;
    private javax.swing.JScrollPane jScrollTEXTO;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblColor;
    private utilesGUIx.JLabelCZ lblFECHADESDE;
    private utilesGUIx.JLabelCZ lblFECHAHASTA;
    private utilesGUIx.JLabelCZ lblGRUPO;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JLabelCZ lblNUMERO;
    private utilesGUIx.JLabelCZ lblREPETICION;
    private utilesGUIx.JLabelCZ lblTEXTO;
    private utilesGUIx.JTextFieldCZ txtFECHADESDE;
    private utilesGUIx.JTextFieldCZ txtFECHAHASTA;
    private utilesGUIx.JTextFieldCZ txtGRUPO;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    private utilesGUIx.JTextFieldCZ txtNUMERO;
    private utilesGUIx.JTextAreaCZ txtTEXTO;
    // End of variables declaration//GEN-END:variables
}
