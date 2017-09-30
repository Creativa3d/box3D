/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelGraf.java
 *
 * Created on 30-abr-2009, 11:41:28
 */
package utilesChart;

import ListDatos.JListDatos;
import impresionJasper.JMotorInformes;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesChart.guardar.JDatosGraf2Guardar;
import utilesChart.guardar.JEjecutarGraf2Guardar;
import utilesChart.guardar.JPanelGraf2Guardar;
import utilesChart.util.JPanelGrafico;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JLabelCZ;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtension;

public class JPanelGraf2 extends javax.swing.JPanel implements ChartMouseListener {

//parametros
    private JParamGraf2 moParam;
//para no ejecutar ningun click de ningun control
    private boolean mbDesactivarClick;
//Ayuda
    private IListaElementos masAyuda = new JListaElementos();
    private IListaElementos masAyuda2 = new JListaElementos();
    private JPanelGrafico moPanelGrafico;

    public JPanelGraf2() throws Exception {
        initComponents();
    }
    public JPanelGraf2(final JParamGraf2 loParam) throws Exception {
        this();
        setDatos(loParam);
    }
    public void setVisibleBotones(boolean pbVisible){
        BtnGuardarImagen.setVisible(pbVisible);
        btnImprimir.setVisible(pbVisible);
        btnSalir.setVisible(pbVisible);
    }
    public void setDatos(final JParamGraf2 loParam) throws Exception {

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //paso de variables por parametro a variables modulares
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        moParam = loParam;
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //realizacion del grafico inicial
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        moPanelGrafico = new JPanelGrafico();
        moPanelGrafico.addChartMouseListener(this);
        moPanelGrafico.setTitulo(moParam.msTitulo);
        moPanelGrafico.setTituloX(moParam.msNombreEjeX);
        jPanelDibujo.setLayout(new BorderLayout());
        jPanelDibujo.add(moPanelGrafico, BorderLayout.CENTER);

        lblParam.setText(moParam.msCaption1);
        lblParam2.setText(moParam.msCaption2);
        mbDesactivarClick = true; //a?adimos los parametros

        anadirParam(moParam.moCollecEje1,
                cmbParam,
                cmbTipo,
                lblParam,
                lblTipo);
        anadirParam(moParam.moCollecEje2,
                cmbParam2,
                cmbTipo2,
                lblParam2,
                lblTipo2);
        //procesamos las colecciones

        if (moParam.moCollecEje1.size() > 0) {
            if (cmbParam.getSelectedIndex()<0 || cmbParam.getSelectedIndex()>=cmbParam.getItemCount()) {
                cmbParam.setSelectedIndex(0);
            }
            anadirFiltros(moParam.moCollecEje1,
                    0, cmbTipo,
                    lblTipo);

            if (cmbTipo.getItemCount() > 0) {
                cmbTipo.setSelectedIndex(0);
            }
            procesarParametro(cmbParam.getSelectedIndex(), 0,
                    JPanelGrafico.mclEjeY1, false);
        } else {
            inicializarGrafico(
                    "",
                    0, false, moParam.mlTipoEjeX);

        }
        //procesamos las colecciones
        if (moParam.moCollecEje2.size() > 0) {
            if (cmbParam2.getSelectedIndex()<0 || cmbParam2.getSelectedIndex()>=cmbParam2.getItemCount()) {
                cmbParam2.setSelectedIndex(0);
            }
            anadirFiltros(moParam.moCollecEje2,
                    0, cmbTipo2,
                    lblTipo2);

            if (cmbTipo2.getItemCount() > 0) {
                cmbTipo2.setSelectedIndex(0);
            }
            procesarParametro(cmbParam2.getSelectedIndex(), 0
                    , JPanelGrafico.mclEjeY2, false);
        }
        
        aplicarEstilos(moParam.moCollecEje1, 0, moParam.moCollecEje2, 0);

        mbDesactivarClick = false; //activamos el form
    }
    public int getParametro1(){
        return cmbParam.getSelectedIndex();
    }
    public int getFiltro1(){
        return cmbTipo.getSelectedIndex();
    }
    public int getParametro2(){
        return cmbParam2.getSelectedIndex();
    }
    public int getFiltro2(){
        return cmbTipo2.getSelectedIndex();
    }    
    public JParamGraf2 getParam(){
        return moParam;
    }
    public JPanelGrafico getGraficoXY(){
        return moPanelGrafico;
    }

    /**
     * Procesamos la coleccion que llama al grafico
     * @param pbConservarGrafAnterior conserva el grafico anterior
     * @param plEjeY eje 0(izq.) o 1(der.)
     * @param plParametro indice de JParamGraf2Y a visualizar (cada eje un colecion de JParamGraf2Y)
     * @param plFiltro filtro a aplicar al parametro
     */
    public void procesarParametro(
            final int plParametro,
            final int plFiltro,
            final int plEjeY,
            final boolean pbConservarGrafAnterior) throws Exception {
        procesarParametro(plParametro, plFiltro, plEjeY, pbConservarGrafAnterior, -1);
    }
    /**
     * Procesamos la coleccion que llama al grafico
     * @param pbConservarGrafAnterior conserva el grafico anterior
     * @param plEjeY eje 0(izq.) o 1(der.)
     * @param plParametro indice de JParamGraf2Y a visualizar (cada eje un colecion de JParamGraf2Y)
     * @param plFiltro filtro a aplicar al parametro
     * @param plIndiceGrafXY indice dentro de la coleccion de JParamGraf2Y (IGraficoXY), -1 si son todos
     */
    public void procesarParametro(
            final int plParametro,
            final int plFiltro,
            final int plEjeY,
            final boolean pbConservarGrafAnterior,
            final int plIndiceGrafXY) throws Exception {
        
        JParamGraf2Y loParam;
        String lsParamFiltro = null;
        IGraficoXY loGrafXY;
        IListaElementos poCollec;
        //ponemos el campo actual
        if(plEjeY==JPanelGrafico.mclEjeY1){
            poCollec=moParam.moCollecEje1;
        }else{
            poCollec=moParam.moCollecEje2;
        }
        loParam = (JParamGraf2Y) poCollec.get(plParametro); //si hay algun filtro ponemos el del parametro

        if (loParam.moCollecFiltros.size() > 0) {
            lsParamFiltro = loParam.moCollecFiltros.get(plFiltro).toString();
        }
        //ponemos el grafico a vacio si no se conserva el graf anterior
        inicializarGrafico(loParam.msCampoDescripcion, plEjeY,
                pbConservarGrafAnterior, moParam.mlTipoEjeX);
        //establecemos los minimos y maximos, tiene preferencia el eje 0
        if(!pbConservarGrafAnterior && plEjeY == JPanelGrafico.mclEjeY1){
            moPanelGrafico.setXMin(loParam.msXInicio);
            moPanelGrafico.setXMax(loParam.msXFin);
            moPanelGrafico.setYMin(loParam.msYInicio);
            moPanelGrafico.setYMax(loParam.msYFin);
        }
        //recorremos todas las coleciones y las pintamos
        if(plIndiceGrafXY==-1){
            for (int i = 0; i < loParam.moColleciGrafXY.size(); i++) {
                loGrafXY = (IGraficoXY) loParam.moColleciGrafXY.get(i);
                mbCrearGrafico(loParam, lsParamFiltro, loGrafXY, plEjeY);
            }
        }else{
            loGrafXY = (IGraficoXY) loParam.moColleciGrafXY.get(plIndiceGrafXY);
            mbCrearGrafico(loParam, lsParamFiltro, loGrafXY, plEjeY);
        }
        
        //ponemos la imagen
        mPonerImagen(moParam.moImagenCabeza);
    }  
    public void aplicarEstilos(IListaElementos poCollec1, final int plParametro1, IListaElementos poCollec2 , final int plParametro2) throws Exception{
        JParamGraf2Y loParam1 = null; 
        JParamGraf2Y loParam2 = null; 
        if(poCollec1.size()>0){
            loParam1 = (JParamGraf2Y) poCollec1.get(plParametro1); 
        }
        if(poCollec2.size()>0){
            loParam2 = (JParamGraf2Y) poCollec2.get(plParametro2); 
        }
        if(loParam1!=null){
            IListaElementos loSeries = moPanelGrafico.getListaSeries();
            int lPosi=0;
            for(int i = 0 ; i < loSeries.size(); i++){
                JPanelGrafico.JPanelGraficoElemento loElem = (JPanelGrafico.JPanelGraficoElemento) loSeries.get(i);
                if(loElem.mlEjeY==0){
                    IGraficoXY loGrafXY = getIGraficoXY(poCollec1, loElem.moSerie);
                    //ponemos el estilo de la serie
                    loGrafXY.estiloSerie(
                            (XYLineAndShapeRenderer) moPanelGrafico.getPlotxy().getRenderer(0),
                             lPosi);   
                    lPosi++;
                }
            }
//            
//            int lSeries = moPanelGrafico.getSeriesSIZE(0);
//            int lInicio = lSeries-loParam1.moColleciGrafXY.size();
//            for (int i = lInicio; i < lSeries; i++) {
//                IGraficoXY loGrafXY = (IGraficoXY) loParam1.moColleciGrafXY.get(i-lInicio);
//                //ponemos el estilo de la serie
//                loGrafXY.estiloSerie(
//                        (XYLineAndShapeRenderer) moPanelGrafico.getPlotxy().getRenderer(0),
//                         i);
//            }        
        }
        if(loParam2!=null){
            IListaElementos loSeries = moPanelGrafico.getListaSeries();
            int lPosi=0;
            for(int i = 0 ; i < loSeries.size(); i++){
                JPanelGrafico.JPanelGraficoElemento loElem = (JPanelGrafico.JPanelGraficoElemento) loSeries.get(i);
                if(loElem.mlEjeY==1){
                    IGraficoXY loGrafXY = getIGraficoXY(poCollec2, loElem.moSerie);
                    //ponemos el estilo de la serie
                    loGrafXY.estiloSerie(
                            (XYLineAndShapeRenderer) moPanelGrafico.getPlotxy().getRenderer(1),
                             lPosi);
                    lPosi++;
                }
            }
//            int lSeries = moPanelGrafico.getSeriesSIZE(1);
//            int lInicio = lSeries-loParam2.moColleciGrafXY.size();
//            for (int i = lInicio; i < lSeries; i++) {
//                IGraficoXY loGrafXY = (IGraficoXY) loParam2.moColleciGrafXY.get(i-lInicio);
//                //ponemos el estilo de la serie
//                loGrafXY.estiloSerie(
//                        (XYLineAndShapeRenderer) moPanelGrafico.getPlotxy().getRenderer(1),
//                         i);
//            }        
        }
    }

    private IGraficoXY getIGraficoXY(IListaElementos poListaY, JGraficoXYSerie poSerie) throws Exception{
        IGraficoXY loResult = null;
        for(int lY = 0; lY<poListaY.size(); lY++){
            IListaElementos loCollec=((JParamGraf2Y)poListaY.get(lY)).moColleciGrafXY;
            for(int i = 0 ; i < loCollec.size() && loResult==null; i++){
                IGraficoXY loXY = (IGraficoXY) loCollec.get(i);
                if(loXY.oSerie().msSerieIdent.equals(poSerie.msSerieIdent)){
                    loResult=loXY;
                }
            }
        }
        return loResult;
    }
          
    //crear grafico,devuelve si se ha pintado algun registro
    private boolean mbCrearGrafico(
            final JParamGraf2Y poParam,
            final String psParamFiltro,
            final IGraficoXY poGrafXY,
            final int plEjeY) throws Exception {
        boolean lbResult = false;
        //aplicamos el filtro
        if (!(psParamFiltro == null)) {
            poGrafXY.aplicarFiltro(psParamFiltro);
        } else {
            poGrafXY.aplicarFiltro("");
        }
        //indicamos si se ha pintado algun punto
        lbResult = true;
        JGraficoXYSerie loElem = poGrafXY.oSerie();
        moPanelGrafico.addListDatos(loElem, plEjeY);
        if (plEjeY == 0) {
            masAyuda.add(loElem.msSerieDescripcion);
        } else {
            masAyuda2.add(loElem.msSerieDescripcion);
        }


        return lbResult;
    }

//A?adimos a cmbParam los campos disponibles
    private void anadirParam(
            final IListaElementos poCollec,
            final JComboBoxCZ poCmbParam,
            final JComboBoxCZ poCmbTipo,
            final JLabelCZ lblParam,
            final JLabelCZ lblTipo) {
        JParamGraf2Y loParam;
        poCmbParam.borrarTodo();
        poCmbTipo.borrarTodo();
        if (poCollec.size() == 0) {
            poCmbParam.setVisible(false);
            poCmbTipo.setVisible(false);
            lblParam.setVisible(false);
            lblTipo.setVisible(false);
        } else {
            poCmbParam.setVisible(true);
            poCmbTipo.setVisible(true);
            lblParam.setVisible(true);
            lblTipo.setVisible(true);
            
            for (int i = 0; i < poCollec.size(); i++) {
                loParam = (JParamGraf2Y) poCollec.get(i);
                poCmbParam.addLinea(loParam.msCampoDescripcion, loParam.msCampoIdentificador);
                if(loParam.mbDefecto){
                    poCmbParam.mbSeleccionarClave(loParam.msCampoIdentificador);
                }
            }
            
        }
    }

//a?adimos los filtros segun el index seleccionado en el combo cmbParam
    private void anadirFiltros(
            final IListaElementos poCollec,
            final int plIndex,
            final JComboBoxCZ poCmbTipo,
            final JLabelCZ poLblTipo) {
        JParamGraf2Y loParam;
        String lsParamFiltro;
        int i;
        poCmbTipo.borrarTodo();
        loParam = (JParamGraf2Y) poCollec.get(plIndex);
        if (loParam.moCollecFiltros.size() == 0) {
            poCmbTipo.setVisible(false);
            poLblTipo.setVisible(false);
        } else {
            for (i = 0; i < loParam.moCollecFiltros.size(); i++) {
                lsParamFiltro = loParam.moCollecFiltros.get(i).toString();
                poCmbTipo.addLinea(lsParamFiltro, lsParamFiltro);
            }
        }
    }

    private void inicializarGrafico(
            final String lsTitY,
            final int plEjeY,
            final boolean pbConservarGrafAnterior,
            final int plTipoEjeX) throws Exception {
        //borramos todas las series
        if (!pbConservarGrafAnterior) {
            moPanelGrafico.limpiar(plEjeY);
        }
        //ponemos un titulo al eje y
        if (pbConservarGrafAnterior) {
            moPanelGrafico.setTituloY("", plEjeY);
        } else {
            moPanelGrafico.setTituloY(lsTitY, plEjeY);
        }
        moPanelGrafico.setFormatEjeX(moParam.msFormat);
        moPanelGrafico.setLabelXAngulo(moParam.mlAngulo);

    }

    private void visualizarLabel(final int plEjeY, final int plSerie) {
        if (plEjeY >= 0) {
            //vemos la ayuda
            if (plEjeY == 0) {
                if (masAyuda.size() > plSerie) {
                    lblAyuda.setText(masAyuda.get(plSerie).toString());
                }
            } else {
                if (masAyuda2.size() > plSerie) {
                    lblAyuda.setText(masAyuda2.get(plSerie).toString());
                }
            }
        } else {
            lblAyuda.setText("");
        }

    }

    public void imprimirGrafico(final String psLeyenda) throws Exception {
//        File loGrafico = new File(System.getProperty("java.io.tmpdir"), "grafico.png");
//        moPanelGrafico.guardarGraficoPNG( loGrafico.getAbsolutePath(), 776, 394);

        JMotorInformes loJasper = new JMotorInformes();
        loJasper.getParametros().put("logo", moParam.moImpRutaLogo);
        loJasper.getParametros().put("grafico", moPanelGrafico.getImagen(776, 394));
        loJasper.setParametro("titulo", moParam.msTitulo == null ? "" : moParam.msTitulo);
        loJasper.setParametro("logotexto", moParam.msImpTextoLogo == null ? "" : moParam.msImpTextoLogo);
        loJasper.setParametro("entidad", moParam.msImpEntidad == null ? "" : moParam.msImpEntidad);
        loJasper.setParametro("leyenda", psLeyenda == null ? "" : psLeyenda);
        JListDatos loList = new JListDatos(
                null, "",
                new String[]{"leyenda", "logotexto", "titulo", "entidad"},
                new int[]{JListDatos.mclTipoCadena, JListDatos.mclTipoCadena, JListDatos.mclTipoCadena, JListDatos.mclTipoCadena},
                new int[]{0});
        loList.addNew();
        loList.getFields(0).setValue(psLeyenda);
        loList.getFields(1).setValue(moParam.msImpTextoLogo);
        loList.getFields(2).setValue(moParam.msTitulo);
        loList.getFields(3).setValue(moParam.msImpEntidad);
        loList.update(false);
        loJasper.setList(loList);

        loJasper.lanzarInformeCompilado(
                (net.sf.jasperreports.engine.JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObject(
                getClass().getResourceAsStream("/utilesChart/informe.jasper")));
    }

    public void guardarGrafico() {
        JFileChooser loCh = new JFileChooser();

        loCh.setFileFilter(new JFileChooserFiltroPorExtension("png", "png"));
        if (loCh.showSaveDialog(this) == loCh.APPROVE_OPTION) {
            String lsFile = loCh.getSelectedFile().getAbsolutePath();
            if(lsFile.length()>4){
                int lIndex = lsFile.lastIndexOf('.');
                if(lIndex < 0){
                    lsFile=lsFile + "." + "png";
                }

            }
            moPanelGrafico.guardarGraficoPNG(lsFile, 800, 600);
        }
    }

    private void mPonerImagen(final Image psImagen1) {
//        if (psImagen1.equals("")) {
//            Chart2D1.Header.Interior.Image.FileName = "";
//            Chart2D1.Header.Border = oc2dBorderNone;
//            Chart2D1.Header.Text = "";
//        } else {
//            Chart2D1.Header.Interior.Image.FileName = psImagen1;
//            Chart2D1.Header.Border = oc2dBorder3DOut;
//            Chart2D1.Header.Text = "                                                                                             " + Chr(13) + " " + Chr(13) + " " + Chr(13) + " ";
//        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do !modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        txtValorPunto = new utilesGUIx.JLabelCZ();
        jPanel3 = new javax.swing.JPanel();
        lblTipo = new utilesGUIx.JLabelCZ();
        cmbTipo = new utilesGUIx.JComboBoxCZ();
        jPanel4 = new javax.swing.JPanel();
        lblTipo2 = new utilesGUIx.JLabelCZ();
        cmbTipo2 = new utilesGUIx.JComboBoxCZ();
        lblParam = new utilesGUIx.JLabelCZ();
        cmbParam = new utilesGUIx.JComboBoxCZ();
        lblParam2 = new utilesGUIx.JLabelCZ();
        cmbParam2 = new utilesGUIx.JComboBoxCZ();
        chkConservarGrafAnterior = new utilesGUIx.JCheckBoxCZ();
        jPanelDibujo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblAyuda = new utilesGUIx.JLabelCZ();
        BtnGuardarImagen = new utilesGUIx.JButtonCZ();
        btnImprimir = new utilesGUIx.JButtonCZ();
        btnSalir = new utilesGUIx.JButtonCZ();

        txtValorPunto.setText("jLabelCZ1");

        setLayout(new java.awt.GridBagLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(93, 24));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblTipo.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(lblTipo, gridBagConstraints);

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipoItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(cmbTipo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        lblTipo2.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel4.add(lblTipo2, gridBagConstraints);

        cmbTipo2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTipo2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTipo2ItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel4.add(cmbTipo2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel4, gridBagConstraints);

        lblParam.setText("Parámetros");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblParam, gridBagConstraints);

        cmbParam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbParam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbParamItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(cmbParam, gridBagConstraints);

        lblParam2.setText("Parámetros");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblParam2, gridBagConstraints);

        cmbParam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbParam2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbParam2ItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(cmbParam2, gridBagConstraints);

        chkConservarGrafAnterior.setText("Conservar gráfico");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(chkConservarGrafAnterior, gridBagConstraints);

        jPanelDibujo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDibujo.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jPanelDibujo, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        lblAyuda.setText(" ");
        lblAyuda.setMinimumSize(new java.awt.Dimension(100, 60));
        lblAyuda.setOpaque(true);
        lblAyuda.setPreferredSize(new java.awt.Dimension(100, 60));
        jPanel2.add(lblAyuda, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        BtnGuardarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesChart/images/Save16.gif"))); // NOI18N
        BtnGuardarImagen.setText("Guardar");
        BtnGuardarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarImagenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(BtnGuardarImagen, gridBagConstraints);

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesChart/images/Print16.gif"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(btnImprimir, gridBagConstraints);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesChart/images/Stop16.gif"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(btnSalir, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    public void chartMouseClicked(ChartMouseEvent event) {
    }

    public void chartMouseMoved(ChartMouseEvent event) {
        if (event.getEntity() != null) {
            if (XYItemEntity.class.isAssignableFrom(event.getEntity().getClass())) {
                XYItemEntity lo = (XYItemEntity) event.getEntity();
                visualizarLabel(0, lo.getSeriesIndex());
            }
        } else {
            visualizarLabel(-1, -1);
        }
////Update the Footer with the current location of the mouse.
//        int px;
//        int py;
//        int Series;
//        int Point;
//        int Distance;
//        int Region;
//        int lIndice;
//        String lsCadena;
//
//        px = x / Screen.TwipsPerPixelX;
//        py = y / Screen.TwipsPerPixelY;
//
////if(no buttons are being pressed,){ check and see if(the mouse is over a data point or
//// a series in the legend, and update the footer if(that is the case
//        if ((Button = 0)) {
////    molabel.IsShowing = false
//            txtValorPunto.setVisible(false);
//            ptLinea.setVisible(false);
//            lblAyuda.setVisible(false);
//            Region = Chart2D1.ChartGroups(1).CoordToDataIndex(px, py, oc2dFocusXY, Series, Point, Distance);
//            if ((Series > 0) && (Distance <= 5)) {
//                visualizarLabel(1, Series, Point, x, y);
//            }
//            Series = 0;
//            Distance = 0;
//            Region = Chart2D1.ChartGroups(2).CoordToDataIndex(px, py, oc2dFocusXY, Series, Point, Distance);
//            if ((Series > 0) && (Distance <= 5)) {
//                visualizarLabel(2, Series, Point, x, y);
//            }
//
//        }
    }

    private void BtnGuardarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarImagenActionPerformed
        try {
            //para la impresion se cambia el fondo a blanco
            mPonerImagen(moParam.moImpImagenCabeza);
            //guardamos el grafico en un BMP
            JDatosGraf2Guardar loGuardar = new JDatosGraf2Guardar();
            loGuardar.moEjecutar=new JEjecutarGraf2Guardar(this, loGuardar);
            JPanelGraf2Guardar loPanel = new JPanelGraf2Guardar();
            loPanel.setDatos(loGuardar);
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);

            //para la impresion se cambia el fondo a blanco
            mPonerImagen(moParam.moImagenCabeza);
        } catch (Exception e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }

    }//GEN-LAST:event_BtnGuardarImagenActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        try {
            String lsLeyenda;
            int i;
//pomemos la imgen de impresion
            mPonerImagen(moParam.moImpImagenCabeza);
//ponemos la leyenda
            lsLeyenda = "";
            for (i = 0; i < masAyuda.size(); i++) {
                if (!masAyuda.get(i).toString().equals("")) {
                    lsLeyenda = lsLeyenda + masAyuda.get(i).toString() + System.getProperty("line.separator");
                }
            }
            for (i = 0; i < masAyuda2.size(); i++) {
                if (!masAyuda2.get(i).toString().equals("")) {
                    lsLeyenda = lsLeyenda + masAyuda2.get(i).toString() + System.getProperty("line.separator");
                }
            }
//imprimimos el grafico
            imprimirGrafico(lsLeyenda);
//ponemos la imagen normal
            mPonerImagen(moParam.moImagenCabeza);
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
            JDepuracion.anadirTexto(getClass().getName(), e);

        }

    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed

        try{
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(this);
        }catch(Exception e){
            SwingUtilities.getWindowAncestor(this).dispose();
        }    

    }//GEN-LAST:event_btnSalirActionPerformed

    private void cmbTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipoItemStateChanged
        try {
            if (!mbDesactivarClick && evt.getStateChange() == evt.SELECTED) {
                //procesamos las colecciones
                if (moParam.moCollecEje1.size() > 0) {
                    procesarParametro(
                            cmbParam.getSelectedIndex(),
                            cmbTipo.getSelectedIndex(),
                            0,
                            chkConservarGrafAnterior.isSelected());
                    aplicarEstilos(moParam.moCollecEje1, cmbParam.getSelectedIndex(), moParam.moCollecEje2, cmbParam2.getSelectedIndex());
                }
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
            JDepuracion.anadirTexto(getClass().getName(), e);
        }

    }//GEN-LAST:event_cmbTipoItemStateChanged

    private void cmbParamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbParamItemStateChanged
        try {
            if (!mbDesactivarClick && evt.getStateChange() == evt.SELECTED) {
                try {
                    mbDesactivarClick = true;
                    //procesamos las colecciones
                    if (moParam.moCollecEje1.size() > 0) {
                        anadirFiltros(
                                moParam.moCollecEje1,
                                cmbParam.getSelectedIndex(),
                                cmbTipo,
                                lblTipo);
                        if (cmbTipo.getItemCount() > 0) {
                            cmbTipo.setSelectedIndex(0);
                        }
                        procesarParametro(
                                cmbParam.getSelectedIndex(), 0,
                                0,
                                chkConservarGrafAnterior.isSelected());
                        aplicarEstilos(moParam.moCollecEje1, cmbParam.getSelectedIndex(), moParam.moCollecEje2, cmbParam2.getSelectedIndex());
                        
                    }
                } finally {
                    mbDesactivarClick = false;
                }
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
            JDepuracion.anadirTexto(getClass().getName(), e);
        }

    }//GEN-LAST:event_cmbParamItemStateChanged

    private void cmbTipo2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTipo2ItemStateChanged
        try {
            if (!mbDesactivarClick && evt.getStateChange() == evt.SELECTED) {
                //procesamos las colecciones
                if (moParam.moCollecEje2.size() > 0) {
                    procesarParametro(
                            cmbParam2.getSelectedIndex(),
                            cmbTipo2.getSelectedIndex(),
                            1,
                            chkConservarGrafAnterior.isSelected());
                    aplicarEstilos(moParam.moCollecEje1, cmbParam.getSelectedIndex(), moParam.moCollecEje2, cmbParam2.getSelectedIndex());
                    
                }
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
            JDepuracion.anadirTexto(getClass().getName(), e);
        }

    }//GEN-LAST:event_cmbTipo2ItemStateChanged

    private void cmbParam2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbParam2ItemStateChanged
        try {
            if (!mbDesactivarClick && evt.getStateChange() == evt.SELECTED) {
                try {
                    mbDesactivarClick = true;
                    //procesamos las colecciones
                    if (moParam.moCollecEje2.size() > 0) {
                        anadirFiltros(
                                moParam.moCollecEje2,
                                cmbParam2.getSelectedIndex(),
                                cmbTipo2, lblTipo2);
                        if (cmbTipo2.getItemCount() > 0) {
                            cmbTipo2.setSelectedIndex(0);
                        }
                        procesarParametro(
                                cmbParam2.getSelectedIndex(), 0,
                                1,
                                chkConservarGrafAnterior.isSelected());
                        aplicarEstilos(moParam.moCollecEje1, cmbParam.getSelectedIndex(), moParam.moCollecEje2, cmbParam2.getSelectedIndex());                    
                    }
                } finally {
                    mbDesactivarClick = false;
                }
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
            JDepuracion.anadirTexto(getClass().getName(), e);
        }


    }//GEN-LAST:event_cmbParam2ItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ BtnGuardarImagen;
    private utilesGUIx.JButtonCZ btnImprimir;
    private utilesGUIx.JButtonCZ btnSalir;
    private utilesGUIx.JCheckBoxCZ chkConservarGrafAnterior;
    private utilesGUIx.JComboBoxCZ cmbParam;
    private utilesGUIx.JComboBoxCZ cmbParam2;
    private utilesGUIx.JComboBoxCZ cmbTipo;
    private utilesGUIx.JComboBoxCZ cmbTipo2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelDibujo;
    private utilesGUIx.JLabelCZ lblAyuda;
    private utilesGUIx.JLabelCZ lblParam;
    private utilesGUIx.JLabelCZ lblParam2;
    private utilesGUIx.JLabelCZ lblTipo;
    private utilesGUIx.JLabelCZ lblTipo2;
    private utilesGUIx.JLabelCZ txtValorPunto;
    // End of variables declaration//GEN-END:variables
}
