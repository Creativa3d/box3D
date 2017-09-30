/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelGrafico.java
 *
 * Created on 04-dic-2008, 9:10:49
 */

package utilesChart.util;

import utiles.FechaMalException;
import ListDatos.ECampoError;
import ListDatos.JListDatos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JConversiones;
import utiles.JDateEdu;
import utiles.JListaElementos;
import utilesChart.JGraficoXYSerie;



public class JPanelGrafico extends JPanel {
    //Tipos de graficas
    public static final int mclTipoBarras2D = 0;
    public static final int mclTipoXYValores = 1;
//    public static final int mclTipoCombinado = 2;
    public static final int mclTipoBarras3D = 3;
    public static final int mclTipoXYTiempo = 4;
    
    public static final int mclEjeY1=0;
    public static final int mclEjeY2=1;
    


    //Parametros de la grafica **************
    private String msTitulo = "";
    private String msTituloX = "";
    private String msTituloY[] = new String[2];
    private int mnTipoGrafica = mclTipoXYTiempo;

    private Dataset moDataSet[] = new Dataset[2];

    private ChartPanel moChartPanel = null;
    private boolean mbPrimeraVez=true;
    private JFreeChart moChart;
    private String msFormatEjeX;
    private double mdLabelXAngulo;
    private int mlSeriesSIZE[]=new int[2];
    private String msXMin;
    private String msXMax;
    private String msYMin;
    private String msYMax;

    private Double mdYMinCal[]=new Double[2];
    private Double mdYMaxCal[]=new Double[2];
    
    
    //propios del XY
    private XYPlot moPlotxy;
    //propios del Barras
    private CategoryPlot moPlotCategoria;

    private ChartMouseListener moListener;

    private IListaElementos moListaSeries = new JListaElementos();

    /** Creates new form JPanelGrafico */
    public JPanelGrafico() {
    }

    private boolean crearEje(int i) {
        boolean lbCreado=false;
        if(moDataSet[i]!=null){
            lbCreado=true;
            if(moPlotxy!=null && moPlotxy.getRangeAxis(i)==null){
                NumberAxis loEjeY = new NumberAxis(msTituloY[i]);
                loEjeY.setAutoRangeIncludesZero(false);  // override default
                moPlotxy.setRangeAxis(i, loEjeY);
                moPlotxy.setDataset(i,(XYDataset)moDataSet[i]);
                loEjeY.setPlot(moPlotxy);
                loEjeY.addChangeListener(moPlotxy);
                moPlotxy.mapDatasetToRangeAxis(i, i);
                moPlotxy.setRenderer(i, getRenderXY());
            }
            if(moPlotCategoria!=null && moPlotCategoria.getRangeAxis(i)==null){
                NumberAxis loEjeY = new NumberAxis(msTituloY[i]);
                loEjeY.setAutoRangeIncludesZero(false);  // override default
                moPlotCategoria.setRangeAxis(i, loEjeY);
                moPlotCategoria.setDataset(i,(CategoryDataset) moDataSet[i]);
                loEjeY.setPlot(moPlotCategoria);
                loEjeY.addChangeListener(moPlotCategoria);
                moPlotCategoria.mapDatasetToRangeAxis(i, i);
                moPlotCategoria.setRenderer(i, getRenderCategoria());
            }
        }
        //titulos del eje Y
        if(moPlotxy!=null){
            if(moPlotxy.getRangeAxis(i)!=null){
                moPlotxy.getRangeAxis(i).setLabel(msTituloY[i]);
            }
        }
        if(moPlotCategoria!=null){
            if(moPlotCategoria.getRangeAxis(i)!=null){
                moPlotCategoria.getRangeAxis(i).setLabel(msTituloY[i]);
            }
        }
        return lbCreado;

    }

    private XYLineAndShapeRenderer getRenderXY(){
        XYLineAndShapeRenderer loRenderXY = new XYLineAndShapeRenderer(true, true);
        loRenderXY.setBaseToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        //y azul claro el simbolo
        loRenderXY.setSeriesPaint(0, new Color(0,0,192) );
        loRenderXY.setSeriesFillPaint(0, new Color(0,0,255));
        return loRenderXY;
    }
    private BarRenderer getRenderCategoria(){
        BarRenderer loRenderCategoria = new BarRenderer();
        loRenderCategoria.setBaseToolTipGenerator(
                    new StandardCategoryToolTipGenerator());
        //y azul claro el simbolo
        loRenderCategoria.setSeriesPaint(0, new Color(0,0,192) );
        loRenderCategoria.setSeriesFillPaint(0, new Color(0,0,255));
        loRenderCategoria.setMaximumBarWidth(0.05);
        return loRenderCategoria;
    }


    private JFreeChart createChart() throws FechaMalException {
        JFreeChart result=null;
        //Crea la imagen segun el tipo de grafica
        switch(mnTipoGrafica) {
            case mclTipoXYTiempo:
                result = ChartFactory.createTimeSeriesChart(
                    msTitulo,      // chart title
                    msTituloX,               // domain axis label
                    msTituloY[0],                  // range axis label
                    (XYDataset)moDataSet[0],                  // data
                    true,                     // include legend
                    true,                     // tooltips
                    false                     // urls
                );
                result.getTitle().setFont(
                        new Font("SansSerif", Font.BOLD, 14)
                        );
                moPlotxy = (XYPlot)result.getPlot();
                DateAxis loX = (DateAxis)moPlotxy.getDomainAxis();
                if(msFormatEjeX != null){
                    loX.setDateFormatOverride(new SimpleDateFormat(msFormatEjeX));
                }
                if(getLabelXAngulo() != 0.0){
                    loX.setLabelAngle(getLabelXAngulo());
                }
                if(!JCadenas.isVacio(msXMin)){
                    loX.setMinimumDate(new JDateEdu(msXMin).getDate());
                }
                if(!JCadenas.isVacio(msXMax)){
                    loX.setMaximumDate(new JDateEdu(msXMax).getDate());
                }
                if(!JCadenas.isVacio(msYMin) && !JCadenas.isVacio(msYMax)){
                    moPlotxy.getRangeAxis().setRange(JConversiones.cdbl(msYMin), JConversiones.cdbl(msYMax));
                }else{
                    //correccion del BUG de JFreeChar , cuando tiene 3 series (param calidad PH + limite superior y limite inferior) 
                    //no tiene en cuenta la 3 serie en el rango del eje Y
                    if(mdYMinCal[0]!=null && mdYMaxCal[0]!=null && mdYMaxCal[0].doubleValue() != mdYMinCal[0].doubleValue()
                            && mlSeriesSIZE[0]>1){
                        //min - 10%, max +10%
                        double ldMargen = (mdYMaxCal[0].doubleValue()-mdYMinCal[0].doubleValue()) * 0.1;
                        moPlotxy.getRangeAxis().setRange(mdYMinCal[0].doubleValue()-ldMargen, mdYMaxCal[0].doubleValue()+ldMargen);
                    }
                }
                moPlotxy.setRenderer(0, getRenderXY());
                break;
            case mclTipoXYValores:
                result = ChartFactory.createXYLineChart(
                    msTitulo,      // chart title
                    msTituloX,               // domain axis label
                    msTituloY[0],                  // range axis label
                    (XYDataset)moDataSet[0],                  // data
                    PlotOrientation.VERTICAL, // orientation
                    true,                     // include legend
                    true,                     // tooltips
                    false                     // urls
                );
                result.getTitle().setFont(
                        new Font("SansSerif", Font.BOLD, 14)
                        );
                moPlotxy = (XYPlot)result.getPlot();
                NumberAxis loXN = (NumberAxis)moPlotxy.getDomainAxis();
                if(msFormatEjeX != null){
                    loXN.setNumberFormatOverride( new DecimalFormat(msFormatEjeX));
                }
                moPlotxy.setRenderer(0, getRenderXY());
                break;
            case mclTipoBarras2D:
                result = ChartFactory.createBarChart(
                    msTitulo,      // chart title
                    msTituloX,               // domain axis label
                    msTituloY[0],                  // range axis label
                    (CategoryDataset)moDataSet[0],                  // data
                    PlotOrientation.VERTICAL, // orientation
                    true,                     // include legend
                    true,                     // tooltips
                    false                     // urls
                );
                result.getTitle().setFont(
                        new Font("SansSerif", Font.BOLD, 14)
                        );
                moPlotCategoria = result.getCategoryPlot();
                moPlotCategoria.setRenderer(0, getRenderCategoria());
                break;
            case mclTipoBarras3D:
                result = ChartFactory.createBarChart3D(
                    msTitulo,      // chart title
                    msTituloX,               // domain axis label
                    msTituloY[0],                  // range axis label
                    (CategoryDataset)moDataSet[0],                  // data
                    PlotOrientation.VERTICAL, // orientation
                    true,                     // include legend
                    true,                     // tooltips
                    false                     // urls
                );
                moPlotCategoria = result.getCategoryPlot();

                
                
                result.getTitle().setFont(
                        new Font("SansSerif", Font.BOLD, 14)
                        );

                break;
//            case mclTipoCombinado:
//                final NumberAxis rangeAxis1 = new NumberAxis(msTituloY);
//                rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//                final LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
//                renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
////                //Establecer color del grafico y mostrar label
////                for(int i=0;i<dataset.getRowCount();i++) {
////                    renderer1.setSeriesPaint(i, new Color(30, 100, 175));
////                }
//
//                final CategoryPlot subplot1 = new CategoryPlot(getDefaultCategoryDataset(), null, rangeAxis1, renderer1);
//                subplot1.setDomainGridlinesVisible(true);
//
//                final NumberAxis rangeAxis2 = new NumberAxis(msTituloY);
//                rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//                final BarRenderer renderer2 = new BarRenderer();
//                renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
////                //Establecer color del grafico y mostrar label
////                for(int i=0;i<dataset.getRowCount();i++) {
////                    renderer2.setSeriesPaint(i, new Color(30, 100, 175));
////                }
//                final CategoryPlot subplot2 = new CategoryPlot(getDefaultCategoryDataset(), null, rangeAxis2, renderer2);
//                subplot2.setDomainGridlinesVisible(true);
//
//                final CategoryAxis domainAxis = new CategoryAxis(msTituloX);
//                final CombinedDomainCategoryPlot plotCombinado;
//                plotCombinado = new CombinedDomainCategoryPlot(domainAxis);
//                plotCombinado.add(subplot1, 2);
//                plotCombinado.add(subplot2, 1);
//
//                result = new JFreeChart(
//                    msTitulo,
//                    new Font("SansSerif", Font.BOLD, 12),
//                    plotCombinado,
//                    true
//                );
//                break;
        }
        
        if(moPlotCategoria!=null){
                moPlotCategoria.getDomainAxis().setCategoryLabelPositions(
                    CategoryLabelPositions.createUpRotationLabelPositions((Math.PI * mdLabelXAngulo)/180)
                );
        }
        if(moPlotxy!=null){
            if(getLabelXAngulo() != 0.0){
                moPlotxy.getDomainAxis().setLabelAngle(
                        (Math.PI * mdLabelXAngulo)/180
                        );
            }
        }


        return result;
    }
    public IListaElementos getListaSeries(){
        return moListaSeries;
    }
    
    public void addListDatos(JGraficoXYSerie poSerie, int plEjeY) throws Exception {
        JPanelGraficoElemento loElem = new JPanelGraficoElemento(poSerie, plEjeY);
        //Crea la imagen segun el tipo de grafica
        switch(mnTipoGrafica) {
            case mclTipoBarras2D:
                addDatasetCategory(loElem);
                break;
            case mclTipoXYTiempo:
                addDatasetTime(loElem);
                break;
            case mclTipoXYValores:
                addDataSetXY(loElem);
                break;
            case mclTipoBarras3D:
                addDatasetCategory(loElem);
                break;
            default:
                throw new Exception("Tipo de gráfico incorrecto");
        }

        
        generarGrafico();

        getChartPanel().updateUI();
        this.updateUI();
        loElem.mlIndice = mlSeriesSIZE[plEjeY];
        moListaSeries.add(loElem);
        mlSeriesSIZE[plEjeY]++;
    }
    public void addListDatos(JListDatos poList, int plPosiValorX, int plPosiValorY, String psSerie,int plEjeY, boolean pbEliminarDuplicados) throws Exception {
        addListDatos(new JGraficoXYSerie(poList, plPosiValorX, plPosiValorY, psSerie, pbEliminarDuplicados), plEjeY);
    }
    public void addListDatos(JListDatos poList, int plPosiValorX, int plPosiValorY, String psSerie,int plEjeY) throws Exception {
        addListDatos(poList, plPosiValorX, plPosiValorY, psSerie, plEjeY, false);
    }
    public void addListDatos(JPanelGraficoListDatos poDefecto, String psSerie) throws Exception {
        addListDatos(poDefecto.moList, poDefecto.lPosiX, poDefecto.lPosiY, psSerie, 0, false);
    }
    public void addListDatos(JPanelGraficoListDatos poDefecto, String psSerie, int plEjeY) throws Exception {
        addListDatos(poDefecto.moList, poDefecto.lPosiX, poDefecto.lPosiY, psSerie, plEjeY, false);
    }

    public void addListDatos(JListDatos poList, int plPosiValorX, int plPosiValorY, String psSerie) throws Exception {
        addListDatos(poList, plPosiValorX, plPosiValorY, psSerie, 0, false);
    }
    public static JPanelGraficoListDatos getNewJPanelGraficoListDatos(){
        return new JPanelGraficoListDatos(null);
    }
    private DefaultCategoryDataset addDatasetCategory(JPanelGraficoElemento loElem){
        if(moDataSet[loElem.mlEjeY]==null){
            moDataSet[loElem.mlEjeY] = new DefaultCategoryDataset();
        }
        DefaultCategoryDataset loDataSet = (DefaultCategoryDataset) moDataSet[loElem.mlEjeY];
        if(loElem.moSerie.moFunciones.moveFirst()){
            do{
                Double ldValor = loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoY).getDoubleConNull();
                loDataSet.addValue(
                        loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoY).getDouble(),
                        loElem.moSerie.msSerieCaption ,
                        loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoX).getString());
                if(ldValor!=null){
                    if(mdYMinCal[loElem.mlEjeY] ==null || ldValor < mdYMinCal[loElem.mlEjeY]){
                        mdYMinCal[loElem.mlEjeY]=ldValor;
                    }
                    if(mdYMaxCal[loElem.mlEjeY] ==null || ldValor > mdYMaxCal[loElem.mlEjeY]){
                        mdYMaxCal[loElem.mlEjeY]=ldValor;
                    }
                }                
            }while(loElem.moSerie.moFunciones.moveNext());
        }
        return loDataSet;
    }
    private XYDataset addDatasetTime(JPanelGraficoElemento loElem) throws FechaMalException {
        if(moDataSet[loElem.mlEjeY]==null){
            moDataSet[loElem.mlEjeY] = new TimeSeriesCollection();
        }
        TimeSeriesCollection loDataSetCoy = (TimeSeriesCollection) moDataSet[loElem.mlEjeY];
        TimeSeries loDataSet;
        try{
            loDataSet = new TimeSeries((String)loElem.moSerie.msSerieCaption, Second.class);
        }catch(Throwable e){
            loDataSet = new TimeSeries((Comparable)loElem.moSerie.msSerieCaption, Second.class);
        }
        if(loElem.moSerie.moFunciones.moveFirst()){
            do{
                Double ldValor = loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoY).getDoubleConNull();
                if(loElem.moSerie.mbEliminarDuplicados){
                    loDataSet.addOrUpdate(
                        new Second(loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoX).getDateEdu().moDate()),
                        ldValor);
                }else{
                    loDataSet.add(
                        new Second(loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoX).getDateEdu().moDate()),
                        ldValor);
                }
                if(ldValor!=null){
                    if(mdYMinCal[loElem.mlEjeY] ==null || ldValor < mdYMinCal[loElem.mlEjeY]){
                        mdYMinCal[loElem.mlEjeY]=ldValor;
                    }
                    if(mdYMaxCal[loElem.mlEjeY] ==null || ldValor > mdYMaxCal[loElem.mlEjeY]){
                        mdYMaxCal[loElem.mlEjeY]=ldValor;
                    }
                }
            }while(loElem.moSerie.moFunciones.moveNext());
        }
        loDataSetCoy.addSeries(loDataSet);
        return loDataSetCoy;
    }

    private XYDataset addDataSetXY(JPanelGraficoElemento loElem){
        if(moDataSet[loElem.mlEjeY]==null){
            moDataSet[loElem.mlEjeY] = new XYSeriesCollection();
        }
        XYSeriesCollection loDataSetCoy = (XYSeriesCollection) moDataSet[loElem.mlEjeY];
        XYSeries loDataSet = new XYSeries(loElem.moSerie.msSerieCaption);
        if(loElem.moSerie.moFunciones.moveFirst()){
            do{
                Double ldValor = loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoY).getDoubleConNull();
                
                if(loElem.moSerie.mbEliminarDuplicados){
                    loDataSet.addOrUpdate(
                        loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoX).getDoubleConNull(),
                        ldValor);
                }else{
                    loDataSet.add(
                        loElem.moSerie.moFunciones.getFields(loElem.moSerie.mlCampoX).getDoubleConNull(),
                        ldValor);
                }
                if(ldValor!=null){
                    if(mdYMinCal[loElem.mlEjeY] ==null || ldValor < mdYMinCal[loElem.mlEjeY]){
                        mdYMinCal[loElem.mlEjeY]=ldValor;
                    }
                    if(mdYMaxCal[loElem.mlEjeY] ==null || ldValor > mdYMaxCal[loElem.mlEjeY]){
                        mdYMaxCal[loElem.mlEjeY]=ldValor;
                    }
                }                
            }while(loElem.moSerie.moFunciones.moveNext());
        }
        loDataSetCoy.addSeries(loDataSet);
        return loDataSetCoy;
    }

    public void setTitulo(String psTitulo) {
        msTitulo = psTitulo;
    }

    public void setTituloX(String psTituloX) {
        msTituloX = psTituloX;
    }

    public void setTituloY(String psTituloY) {
        msTituloY[0] = psTituloY;
    }
    public void setTituloY(String psTituloY, int plEjeY) {
        msTituloY[plEjeY] = psTituloY;
    }

    public void setTipoGrafica(int plTipo) {
        mnTipoGrafica = plTipo;
    }

    public void generarGrafico() throws Exception{
        if(mbPrimeraVez){
            initComponents();
            mbPrimeraVez = false;
        }
        if(getChartPanel()!=null){
            this.remove(getChartPanel());
        }
//        if(getChartPanel()==null){
            setChart(createChart());
            setChartPanel(new ChartPanel(getChart()));
            this.add(getChartPanel(), BorderLayout.CENTER);
            getChartPanel().updateUI();
            this.updateUI();
//        }
        //en caso de q exista se crea eje
        crearEje(0);
        //en caso de q exista se crea eje
        crearEje(1);
    }
    public void limpiarTodo() throws Exception{
        if(getChartPanel()!=null){
            this.remove(getChartPanel());
        }
        setChartPanel(null);
        setChart(null);
        moDataSet[0] = null;
        moDataSet[1] = null;
        mlSeriesSIZE[0]=0;
        mlSeriesSIZE[1]=0;
        moListaSeries.clear();
        mdYMaxCal[0]=null;
        mdYMinCal[0]=null;        
        mdYMaxCal[1]=null;
        mdYMinCal[1]=null;        
    }
    public void limpiar(int plEjeY) throws Exception{
        mdYMaxCal[plEjeY]=null;
        mdYMinCal[plEjeY]=null;   
        
        //eje 2 en caso de q exista se crea
        if(moDataSet[plEjeY]!=null){
            if(moPlotxy!=null){
                moPlotxy.setRangeAxis(plEjeY, null);
                moPlotxy.setDataset(plEjeY, null);
            }
            if(moPlotCategoria!=null){
                moPlotCategoria.setRangeAxis(plEjeY, null);
                moPlotCategoria.setDataset(plEjeY, null);
            }
        }
        moDataSet[plEjeY] = null;
        mlSeriesSIZE[plEjeY]=0;
        
        for(int i = 0; i < moListaSeries.size(); i++){
            JPanelGraficoElemento loElem =  (JPanelGraficoElemento) moListaSeries.get(i);
            if(loElem!=null && loElem.mlEjeY==plEjeY){
                moListaSeries.remove(loElem);
                i--;
            }
        }
        
        
    }
    public void guardarGraficoPNG(String psFichero, int plWidth, int plHeight) {
        try {
            if(!psFichero.substring(psFichero.length()-4, psFichero.length()).equals(".png")){
                psFichero=psFichero+".png";
            }
            File loFile = new File(psFichero);
            ChartUtilities.writeChartAsPNG(new FileOutputStream(loFile),getChart(), plWidth, plHeight);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public BufferedImage getImagen(int plWidth, int plHeight){
        BufferedImage image = getChart().createBufferedImage(plWidth, plHeight,
                BufferedImage.TYPE_INT_RGB, null);
        return image;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * @return the moChartPanel
     */
    public ChartPanel getChartPanel() {
        return moChartPanel;
    }

    /**
     * @param moChartPanel the moChartPanel to set
     */
    public void setChartPanel(ChartPanel moChartPanel) {
        this.moChartPanel = moChartPanel;
        if(moChartPanel!=null && moListener != null){
            moChartPanel.addChartMouseListener(moListener);
        }
    }
    public void addChartMouseListener(ChartMouseListener poListener){
        moListener=poListener;
    }

    /**
     * @return the chart
     */
    public JFreeChart getChart() {
        return moChart;
    }

    /**
     * @param chart the chart to set
     */
    public void setChart(JFreeChart chart) {
        this.moChart = chart;
    }

    public void setFormatEjeX(String psFormatEjeX){
        msFormatEjeX = psFormatEjeX;
    }

    /**
     * @return the mdLabelXAngulo
     */
    public double getLabelXAngulo() {
        return mdLabelXAngulo;
    }

    /**
     * @param mdLabelXAngulo the mdLabelXAngulo to set
     */
    public void setLabelXAngulo(double mdLabelXAngulo) {
        this.mdLabelXAngulo = mdLabelXAngulo;
    }

    
    /**
     * @return the moPlotxy
     */
    public XYPlot getPlotxy() {
        return moPlotxy;
    }

    /**
     * @return the moPlotCategoria
     */
    public CategoryPlot getPlotCategoria() {
        return moPlotCategoria;
    }

    /**
     * @return the mlSeriesSIZE
     * 
     */
    public int getSeriesSIZE(int plEjeY) {
        return mlSeriesSIZE[plEjeY];
    }


    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {
        try {
            JListDatos dataset = new JListDatos(
                    null,
                    "Serie1",
                    new String[]{"x", "y"},
                    new int[]{JListDatos.mclTipoNumeroDoble, JListDatos.mclTipoNumeroDoble},
                    new int[]{0});
            addValor(dataset, "1", "1");
            addValor(dataset, "2", "2");
            addValor(dataset, "3", "3");
            JListDatos dataset2 = new JListDatos(
                    null,
                    "Serie2",
                    new String[]{"", ""},
                    new int[]{JListDatos.mclTipoNumeroDoble, JListDatos.mclTipoNumeroDoble},
                    new int[]{0});
            addValor(dataset2, "1", "1");
            addValor(dataset2, "2", "2");
            addValor(dataset2, "3", "3");
            JPanelGrafico jPanelGrafico1 = new JPanelGrafico();
            jPanelGrafico1.addListDatos(dataset,0,1,"Serie1");
            jPanelGrafico1.addListDatos(dataset2,0,1,"Serie2");
            jPanelGrafico1.setTitulo("Titulo");
            jPanelGrafico1.setTituloX("TituloX");
            jPanelGrafico1.setTituloY("TituloY");
            jPanelGrafico1.setTipoGrafica(jPanelGrafico1.mclTipoBarras2D);
            jPanelGrafico1.generarGrafico();

            JFrame loFrame = new JFrame();
            loFrame.getContentPane().setLayout(new BorderLayout());
            loFrame.getContentPane().add(jPanelGrafico1, BorderLayout.CENTER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void addValor(JListDatos poData, String x, String y) throws ECampoError{
        poData.addNew();
        poData.getFields(0).setValue(x);
        poData.getFields(1).setValue(y);
        poData.update(false);

    }

    /**
     * @return the msXMin
     */
    public String getXMin() {
        return msXMin;
    }

    /**
     * @param msXMin the msXMin to set
     */
    public void setXMin(String msXMin) {
        this.msXMin = msXMin;
    }

    /**
     * @return the msXMax
     */
    public String getXMax() {
        return msXMax;
    }

    /**
     * @param msXMax the msXMax to set
     */
    public void setXMax(String msXMax) {
        this.msXMax = msXMax;
    }

    /**
     * @return the msYMin
     */
    public String getYMin() {
        return msYMin;
    }

    /**
     * @param msYMin the msYMin to set
     */
    public void setYMin(String msYMin) {
        this.msYMin = msYMin;
    }

    /**
     * @return the msYMax
     */
    public String getYMax() {
        return msYMax;
    }

    /**
     * @param msYMax the msYMax to set
     */
    public void setYMax(String msYMax) {
        this.msYMax = msYMax;
    }

    public class JPanelGraficoElemento {

        public JGraficoXYSerie moSerie;
        public int mlEjeY=0;
        public int mlIndice;

        public JPanelGraficoElemento (final JGraficoXYSerie poSerie, int plEjeY){
            moSerie=poSerie;
            mlEjeY=plEjeY;
        }
        public JPanelGraficoElemento (final JListDatos poList, final int plPosiValorX, final int plPosiValorY, final String psSerie, int plEjeY, boolean pbEliminarDuplicados){
            this(new JGraficoXYSerie(poList, plPosiValorX, plPosiValorY, psSerie, pbEliminarDuplicados), plEjeY);
        }
    }

}

