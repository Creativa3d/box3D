/*
 * JPanelConsultaPrincipal.java
 *
 * Created on 13 de septiembre de 2006, 18:29
 */

package utilesGUIx.formsGenericos;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.*;

import java.awt.Dimension;
import utilesGUIx.JButtonCZ;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JTableCZ;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;



public class JPanelGenerico2 extends JPanelGenericoAbstract {
    private static final long serialVersionUID = 1L;

    public static final int mclTipo = 1;
    public static Dimension moDimensionDefecto = new Dimension(99, 55);
    public static boolean mbSeleccionarTodoVisible = true;
    private boolean mbVisibleSplash=false;
    /** Creates new form JPanelBusqueda */
    public JPanelGenerico2() {
        super();
        initComponents();
        jBtnAceptar.setMnemonic('a');
        jBtnCancelar.setMnemonic('c');
        jBtnBorrar.setMnemonic('b');
        jBtnNuevo.setMnemonic('n');
        jBtnEditar.setMnemonic('e');
        jBtnRefrescar.setMnemonic('r');
        btnSelect.setVisible(mbSeleccionarTodoVisible);
        inicializar();
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, "JPanelGenerico");
    }

    public JPanelGeneralFiltro getPanelGeneralFiltro1() {
        return jPanelGeneralFiltro1;
    }

    public JPanelGeneralFiltroLinea getPanelGeneralFiltroLinea1() {
        return jPanelGeneralFiltroLinea1;
    }

    public JPanelGeneralFiltroTodosCamp getPanelGeneralFiltroTodosCamp1() {
        return jPanelGeneralFiltroTodosCamp1;
    }

    public JButtonCZ getBtnAceptar() {
        return jBtnAceptar;
    }

    public JButtonCZ getBtnBorrar() {
        return jBtnBorrar;
    }

    public JButtonCZ getBtnCancelar() {
        return jBtnCancelar;
    }

    public JButtonCZ getBtnEditar() {
        return jBtnEditar;
    }

    public JButtonCZ getBtnNuevo() {
        return jBtnNuevo;
    }

    public JButtonCZ getBtnRefrescar() {
        return jBtnRefrescar;
    }

    public JButtonCZ getbtnConfig() {
        return btnConfig;
    }

    public JComboBoxCZ getcmbConfig() {
        return cmbConfig;
    }
    @Override
    public JComboBoxCZ getcmbFiltros() {
       return cmbFiltros;
    }
    public JComboBoxCZ getcmbTipoFiltroRapido() {
        return cmbTipoFiltroRapido;
    }

    public JTableCZ getTabla() {
        return jTableDatos;
    }

    public void setTotal(String psValor) {
        lblTotal.setText(psValor);
        lblTotal.setToolTipText(psValor);
    }

    public void setPosicion(String psValor) {
        lblPosicion.setText(psValor);
        lblPosicion.setToolTipText(psValor);
    }

    public Container crearContenedorBotones(String psGrupo) {
        JToolBar loPanel = new JToolBar();
        loPanel.setFloatable(false);
//        loPanel.setMaximumSize(new java.awt.Dimension(1000, 1000));
//        loPanel.setMinimumSize(new java.awt.Dimension(4, 59));
//        loPanel.setPreferredSize(new java.awt.Dimension(4, 59));
        return loPanel;
    }

    public JScrollPane getScrollPaneTablaDatos() {
        return jScrollPaneTablaDatos;
    }

    public JPanel getPanelRelacionadoGen() {
        return jPanelRelacionadoGen;
    }
    public void propiedadesBotonRecienCreado(JButtonCZ poBoton) {
        poBoton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        poBoton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        poBoton.setPreferredSize(new Dimension(poBoton.getPreferredSize().width, moDimensionDefecto.height));
        poBoton.setMinimumSize(new Dimension(poBoton.getPreferredSize().width, moDimensionDefecto.height));
        poBoton.setMaximumSize(new Dimension(poBoton.getPreferredSize().width, moDimensionDefecto.height));
        
    }

    public void setVisiblePanelConfigyFiltroRap(boolean pbVisible) {
        jPanelConfigyFiltroRap.setVisible(pbVisible);
    }

    public void setVisiblePanelTareasFiltro(boolean pbVisible) {
        btnMasFiltros.setVisible(pbVisible);
    }

    public Container getPanelEditar() {
        return jPanelEditar;
    }

    public Dimension getDimensionDefecto(final IBotonRelacionado poBoton) {
        return moDimensionDefecto;
    }

    public Object getPanelInformacion() {
        return jPanelInformacion;
    }
    public void setVisibleSplash(boolean pbVisible, String psTexto) {
        if(pbVisible){
            if(!"Procesando....".equalsIgnoreCase(psTexto)){
                lblSplashLabel.setText(psTexto);
            } else {
                lblSplashLabel.setText("");
            }
            
            if(!mbVisibleSplash){
                mbVisibleSplash = true;
                remove(jPanelCentral);
                java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.weightx=1;
                gridBagConstraints.weighty=1;
                gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
                gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                add(jPanelSplash, gridBagConstraints,0 );
                this.updateUI();
            }
        }else{
            mbVisibleSplash = false;
            remove(jPanelSplash);
            java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx=1;
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            add(jPanelCentral, gridBagConstraints,0 );
            
            this.updateUI();
        }
    }
    private void mostrarCabezera() {
        ((CardLayout)(jPanelCabezera.getLayout())).show(
                jPanelCabezera,
                "filtro");
        jPanelConfigyFiltroRap.setVisible(true);
        btnMostrarCabezera.setVisible(false);
        jPanelCabezera.setPreferredSize(new Dimension(502, 50));
        jPanelCabezera.setMinimumSize(new Dimension(502, 50));
        updateUI();
    }
    private void ocultarCabezera() {
        ((CardLayout)(jPanelCabezera.getLayout())).show(
                jPanelCabezera,
                "filtromostrar");
        jPanelConfigyFiltroRap.setVisible(false);
        btnMostrarCabezera.setVisible(true);
        jPanelCabezera.setPreferredSize(new Dimension(50, 10));
        jPanelCabezera.setMinimumSize(new Dimension(50, 10));
        updateUI();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelGeneralFiltro1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltro();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuNuevo = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenuItem();
        jMenuBorrar = new javax.swing.JMenuItem();
        jMenuRefrescar = new javax.swing.JMenuItem();
        jMenuAceptar = new javax.swing.JMenuItem();
        jMenuCancelar = new javax.swing.JMenuItem();
        jMenuCopiarTabla = new javax.swing.JMenuItem();
        jMenuCampos = new javax.swing.JMenu();
        jMenuOtrasAcciones = new javax.swing.JMenu();
        jPanelSplash = new javax.swing.JPanel();
        lblSplashLabel = new javax.swing.JLabel();
        jPanelCentral = new javax.swing.JPanel();
        jPanelCabezera = new javax.swing.JPanel();
        jPanelConfigyFiltroRap = new javax.swing.JPanel();
        btnConfig = new utilesGUIx.JButtonCZ();
        cmbConfig = new utilesGUIx.JComboBoxCZ();
        cmbTipoFiltroRapido = new utilesGUIx.JComboBoxCZ();
        cmbFiltros = new utilesGUIx.JComboBoxCZ();
        btnMasFiltros = new javax.swing.JButton();
        btnOcultarCabezera = new javax.swing.JButton();
        btnSelect = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblPosicion = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        JPanelTipoFiltroRapido = new javax.swing.JPanel();
        jPanelGeneralFiltroTodosCamp1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltroTodosCamp();
        jPanelGeneralFiltroLinea1 = new utilesGUIx.formsGenericos.JPanelGeneralFiltroLinea();
        btnMostrarCabezera = new javax.swing.JButton();
        jScrollPaneTablaDatos = new javax.swing.JScrollPane();
        jTableDatos = new utilesGUIx.JTableCZ();
        jPanel4 = new javax.swing.JPanel();
        jPanelInformacion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelBotones = new javax.swing.JPanel();
        jPanelEditar = new javax.swing.JToolBar();
        jBtnNuevo = new utilesGUIx.JButtonCZ();
        jBtnEditar = new utilesGUIx.JButtonCZ();
        jBtnBorrar = new utilesGUIx.JButtonCZ();
        jBtnRefrescar = new utilesGUIx.JButtonCZ();
        jBtnAceptar = new utilesGUIx.JButtonCZ();
        jBtnCancelar = new utilesGUIx.JButtonCZ();
        jPanelRelacionadoGen = new javax.swing.JPanel();

        jPanelGeneralFiltro1.setMinimumSize(new java.awt.Dimension(214, 90));
        jPanelGeneralFiltro1.setPreferredSize(new java.awt.Dimension(214, 90));

        jMenuNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Add16.gif"))); // NOI18N
        jMenuNuevo.setText("Nuevo"); // NOI18N
        jPopupMenu1.add(jMenuNuevo);

        jMenuEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Edit16.gif"))); // NOI18N
        jMenuEditar.setText("Editar"); // NOI18N
        jPopupMenu1.add(jMenuEditar);

        jMenuBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif"))); // NOI18N
        jMenuBorrar.setText("Borrar"); // NOI18N
        jPopupMenu1.add(jMenuBorrar);

        jMenuRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Refresh16.gif"))); // NOI18N
        jMenuRefrescar.setText("Refrescar"); // NOI18N
        jPopupMenu1.add(jMenuRefrescar);

        jMenuAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        jMenuAceptar.setText("Aceptar"); // NOI18N
        jPopupMenu1.add(jMenuAceptar);

        jMenuCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        jMenuCancelar.setText("Cancelar"); // NOI18N
        jPopupMenu1.add(jMenuCancelar);

        jMenuCopiarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Copy16.gif"))); // NOI18N
        jMenuCopiarTabla.setText("Copiar tabla"); // NOI18N
        jPopupMenu1.add(jMenuCopiarTabla);

        jMenuCampos.setText("Campos"); // NOI18N
        jPopupMenu1.add(jMenuCampos);

        jMenuOtrasAcciones.setText("Otras acciones"); // NOI18N
        jPopupMenu1.add(jMenuOtrasAcciones);

        jPanelSplash.setLayout(new java.awt.BorderLayout());

        lblSplashLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblSplashLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSplashLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/espera.gif"))); // NOI18N
        jPanelSplash.add(lblSplashLabel, java.awt.BorderLayout.CENTER);

        setMinimumSize(new java.awt.Dimension(100, 25));
        setPreferredSize(new java.awt.Dimension(600, 200));
        setLayout(new java.awt.GridBagLayout());

        jPanelCentral.setBackground(new java.awt.Color(237, 234, 229));
        jPanelCentral.setOpaque(false);
        jPanelCentral.setLayout(new java.awt.BorderLayout());

        jPanelCabezera.setLayout(new java.awt.CardLayout());

        jPanelConfigyFiltroRap.setOpaque(false);
        jPanelConfigyFiltroRap.setLayout(new java.awt.GridBagLayout());

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/ColumnInsertAfter16.gif"))); // NOI18N
        btnConfig.setToolTipText("Configurar columnas"); // NOI18N
        btnConfig.setFocusable(false);
        btnConfig.setMinimumSize(new java.awt.Dimension(20, 26));
        btnConfig.setPreferredSize(new java.awt.Dimension(20, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(btnConfig, gridBagConstraints);

        cmbConfig.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbConfig.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(cmbConfig, gridBagConstraints);

        cmbTipoFiltroRapido.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTipoFiltroRapido.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(cmbTipoFiltroRapido, gridBagConstraints);

        cmbFiltros.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltros.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(cmbFiltros, gridBagConstraints);

        btnMasFiltros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/base_arrow_up.png"))); // NOI18N
        btnMasFiltros.setText("Más filtros"); // NOI18N
        btnMasFiltros.setFocusable(false);
        btnMasFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasFiltrosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(btnMasFiltros, gridBagConstraints);

        btnOcultarCabezera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/crossed_circle.png"))); // NOI18N
        btnOcultarCabezera.setText("Ocultar"); // NOI18N
        btnOcultarCabezera.setToolTipText("Ocultar botones y filtros"); // NOI18N
        btnOcultarCabezera.setFocusable(false);
        btnOcultarCabezera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOcultarCabezeraActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(btnOcultarCabezera, gridBagConstraints);

        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/RowInsertAfter16.gif"))); // NOI18N
        btnSelect.setText("Selec."); // NOI18N
        btnSelect.setToolTipText("Seleccionar todas las filas"); // NOI18N
        btnSelect.setFocusable(false);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelConfigyFiltroRap.add(btnSelect, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        jPanelConfigyFiltroRap.add(jLabel3, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblPosicion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblPosicion.setForeground(new java.awt.Color(153, 51, 255));
        lblPosicion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPosicion.setText("1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(lblPosicion, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 51, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(" de "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jLabel7, gridBagConstraints);

        lblTotal.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(153, 51, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("9000"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(lblTotal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanelConfigyFiltroRap.add(jPanel2, gridBagConstraints);

        JPanelTipoFiltroRapido.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        JPanelTipoFiltroRapido.add(jPanelGeneralFiltroTodosCamp1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        JPanelTipoFiltroRapido.add(jPanelGeneralFiltroLinea1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelConfigyFiltroRap.add(JPanelTipoFiltroRapido, gridBagConstraints);

        jPanelCabezera.add(jPanelConfigyFiltroRap, "filtro");

        btnMostrarCabezera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/up16.png"))); // NOI18N
        btnMostrarCabezera.setToolTipText("Mostrar botones y filtros"); // NOI18N
        btnMostrarCabezera.setBorder(null);
        btnMostrarCabezera.setFocusable(false);
        btnMostrarCabezera.setMaximumSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.setMinimumSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.setPreferredSize(new java.awt.Dimension(50, 10));
        btnMostrarCabezera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCabezeraActionPerformed(evt);
            }
        });
        jPanelCabezera.add(btnMostrarCabezera, "filtromostrar");

        jPanelCentral.add(jPanelCabezera, java.awt.BorderLayout.NORTH);

        jScrollPaneTablaDatos.setBackground(new java.awt.Color(237, 234, 229));
        jScrollPaneTablaDatos.setBorder(null);

        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableDatos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableDatos.setEditingColumn(0);
        jTableDatos.setEditingRow(0);
        jScrollPaneTablaDatos.setViewportView(jTableDatos);

        jPanelCentral.add(jScrollPaneTablaDatos, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanelInformacion.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jPanelInformacion, gridBagConstraints);

        jPanelBotones.setOpaque(false);
        jPanelBotones.setLayout(new java.awt.GridBagLayout());

        jPanelEditar.setFloatable(false);

        jBtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Add24.gif"))); // NOI18N
        jBtnNuevo.setText("Nuevo"); // NOI18N
        jBtnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnNuevo.setMaximumSize(new java.awt.Dimension(99, 55));
        jBtnNuevo.setMinimumSize(new java.awt.Dimension(99, 55));
        jBtnNuevo.setPreferredSize(new java.awt.Dimension(99, 55));
        jBtnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelEditar.add(jBtnNuevo);

        jBtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Edit24.gif"))); // NOI18N
        jBtnEditar.setText("Editar"); // NOI18N
        jBtnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnEditar.setMaximumSize(new java.awt.Dimension(99, 55));
        jBtnEditar.setMinimumSize(new java.awt.Dimension(99, 55));
        jBtnEditar.setPreferredSize(new java.awt.Dimension(99, 55));
        jBtnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelEditar.add(jBtnEditar);

        jBtnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete24.gif"))); // NOI18N
        jBtnBorrar.setText("Borrar"); // NOI18N
        jBtnBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnBorrar.setMaximumSize(new java.awt.Dimension(99, 55));
        jBtnBorrar.setMinimumSize(new java.awt.Dimension(99, 55));
        jBtnBorrar.setPreferredSize(new java.awt.Dimension(99, 55));
        jBtnBorrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelEditar.add(jBtnBorrar);

        jBtnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Refresh24.gif"))); // NOI18N
        jBtnRefrescar.setText("Refrescar"); // NOI18N
        jBtnRefrescar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnRefrescar.setMaximumSize(new java.awt.Dimension(99, 55));
        jBtnRefrescar.setMinimumSize(new java.awt.Dimension(99, 55));
        jBtnRefrescar.setPreferredSize(new java.awt.Dimension(99, 55));
        jBtnRefrescar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelEditar.add(jBtnRefrescar);

        jBtnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        jBtnAceptar.setText("Aceptar"); // NOI18N
        jBtnAceptar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnAceptar.setMaximumSize(new java.awt.Dimension(99, 55));
        jBtnAceptar.setMinimumSize(new java.awt.Dimension(99, 55));
        jBtnAceptar.setPreferredSize(new java.awt.Dimension(99, 55));
        jBtnAceptar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelEditar.add(jBtnAceptar);

        jBtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        jBtnCancelar.setText("Cancelar"); // NOI18N
        jBtnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnCancelar.setMaximumSize(new java.awt.Dimension(99, 55));
        jBtnCancelar.setMinimumSize(new java.awt.Dimension(99, 55));
        jBtnCancelar.setPreferredSize(new java.awt.Dimension(99, 55));
        jBtnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanelEditar.add(jBtnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanelBotones.add(jPanelEditar, gridBagConstraints);

        jPanelRelacionadoGen.setLayout(new java.awt.GridLayout(0, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelBotones.add(jPanelRelacionadoGen, gridBagConstraints);

        jScrollPane1.setViewportView(jPanelBotones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        jPanelCentral.add(jPanel4, java.awt.BorderLayout.SOUTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanelCentral, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasFiltrosActionPerformed
        try{
            jPanelGeneralFiltro1.antesmostrar();
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(
                    jPanelGeneralFiltro1, 700, 500, getMostrarTipo());
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_btnMasFiltrosActionPerformed

    private void btnOcultarCabezeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcultarCabezeraActionPerformed
        ocultarCabezera();
}//GEN-LAST:event_btnOcultarCabezeraActionPerformed

    private void btnMostrarCabezeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCabezeraActionPerformed
        mostrarCabezera();
}//GEN-LAST:event_btnMostrarCabezeraActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        seleccionarTodo();
    }//GEN-LAST:event_btnSelectActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelTipoFiltroRapido;
    private utilesGUIx.JButtonCZ btnConfig;
    private javax.swing.JButton btnMasFiltros;
    private javax.swing.JButton btnMostrarCabezera;
    private javax.swing.JButton btnOcultarCabezera;
    private javax.swing.JButton btnSelect;
    private utilesGUIx.JComboBoxCZ cmbConfig;
    private utilesGUIx.JComboBoxCZ cmbFiltros;
    private utilesGUIx.JComboBoxCZ cmbTipoFiltroRapido;
    private utilesGUIx.JButtonCZ jBtnAceptar;
    private utilesGUIx.JButtonCZ jBtnBorrar;
    private utilesGUIx.JButtonCZ jBtnCancelar;
    private utilesGUIx.JButtonCZ jBtnEditar;
    private utilesGUIx.JButtonCZ jBtnNuevo;
    private utilesGUIx.JButtonCZ jBtnRefrescar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuAceptar;
    private javax.swing.JMenuItem jMenuBorrar;
    private javax.swing.JMenu jMenuCampos;
    private javax.swing.JMenuItem jMenuCancelar;
    private javax.swing.JMenuItem jMenuCopiarTabla;
    private javax.swing.JMenuItem jMenuEditar;
    private javax.swing.JMenuItem jMenuNuevo;
    private javax.swing.JMenu jMenuOtrasAcciones;
    private javax.swing.JMenuItem jMenuRefrescar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelBotones;
    private javax.swing.JPanel jPanelCabezera;
    private javax.swing.JPanel jPanelCentral;
    private javax.swing.JPanel jPanelConfigyFiltroRap;
    private javax.swing.JToolBar jPanelEditar;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltro jPanelGeneralFiltro1;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltroLinea jPanelGeneralFiltroLinea1;
    private utilesGUIx.formsGenericos.JPanelGeneralFiltroTodosCamp jPanelGeneralFiltroTodosCamp1;
    private javax.swing.JPanel jPanelInformacion;
    private javax.swing.JPanel jPanelRelacionadoGen;
    private javax.swing.JPanel jPanelSplash;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneTablaDatos;
    private utilesGUIx.JTableCZ jTableDatos;
    private javax.swing.JLabel lblPosicion;
    private javax.swing.JLabel lblSplashLabel;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables



   
}
