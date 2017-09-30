package impresionJasper;

import ListDatos.ECampoError;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.IPanelGenerico;
import utilesGUIx.formsGenericos.JTablaConfigColumna;
import utilesGUIx.formsGenericos.JTablaConfigTablaConfig;
import utilesGUIx.msgbox.JMsgBox;

/**
 * Este informe se utiliza para imprimir directamente desde las pantallas de los buscadores
 */
public class JInfGeneralJasper {

    public static final int mclEstiloLineasNada = 0;
    public static final int mclEstiloLineasHorizontales = 1;
    public static final int mclEstiloLineasCuadrado = 2;
    public static final byte mclAlineacionCentro = ImageBanner.ALIGN_CENTER;
    public static final byte mclAlineacionIzquierda = ImageBanner.ALIGN_LEFT;
    public static final byte mclAlineacionDerecha = ImageBanner.ALIGN_RIGHT;
    public static final byte mclAlineacionJustificada = 100;
    public static final double mcnCoef = 0.0352777;
    public static final double mcnCoefPantallaImpreso = 0.75;
    public static final int mcsMargen = 16;
    private JListDatos moList;
    private JInfConfigColumnasJasper moConfig = new JInfConfigColumnasJasper();
    private String msTitulo;
    private String msSubTitulo;
    private JMotorInformes moMotor = new JMotorInformes();
    private int mlEstiloLineas;
    private JasperReport moJasperReportPregenerado;
    private JListDatos moListProcesadoPregenerado;
    private Style moEstiloPregenerado;
    private Style moEstiloEncabezado;
    private Style moEstiloTitulo;
    private Style moEstiloTituloSub;
    private Font moFuente;
    private Font moFuenteEncabezado;
    private Font moFuenteTitulo;
    private Font moFuenteTituloSub;
    
    private String msImagenHeader;
    private int mlImagenHeaderHeight = 60;
    private byte mlImagenHeaderAling = mclAlineacionIzquierda;

    private boolean mbSeleccionarA3 = false;
    private boolean mbPantallaSeleccion = false;
    private Page moPageManual = null;
//    private HashMap moListaColumnas = new HashMap();
    private FastReportBuilder modrb;
    private IListaElementos moGrupos = new JListaElementos();
    private String msTemplateFile;
//    private HashMap moPropiedades=new HashMap();

    public JInfGeneralJasper() {
        super();
        moMotor.setTipoListado(JMotorInformes.mclPrevisualizar);
        moEstiloPregenerado = new Style();
        moEstiloPregenerado.setBlankWhenNull(true);
        moEstiloPregenerado.setOverridesExistingStyle(true);
        moEstiloPregenerado.setVerticalAlign(VerticalAlign.TOP);
//        moEstiloPregenerado.setStretchWithOverflow(true);
        setEstiloLineas(mclEstiloLineasNada);
        setFuenteDetalle(new Font(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL, Font.PLAIN, 9));

        moEstiloEncabezado = new Style();
        moEstiloEncabezado.setBackgroundColor(Color.LIGHT_GRAY);
        moEstiloEncabezado.setTransparency(Transparency.OPAQUE);
        moEstiloEncabezado.setVerticalAlign(VerticalAlign.MIDDLE);
//        moEstiloEncabezado.setHorizontalAlign(HorizontalAlign.CENTER);
        setFuenteEncabezado(new Font(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL, Font.BOLD, 9));

        moEstiloTitulo = new Style();
        setFuenteTitulo(new Font(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL, Font.BOLD, 15));

        moEstiloTituloSub = new Style();
        setFuenteTituloSub(new Font(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL, Font.BOLD, 12));

    }

    public JListDatos getList() {
        return moList;
    }

    public JInfGeneralJasperGrupo addGrupo() {
        JInfGeneralJasperGrupo loG = new JInfGeneralJasperGrupo();
        moGrupos.add(loG);
        return loG;
    }

    public JInfGeneralJasperGrupo getGrupo(int plGrupo) {
        return (JInfGeneralJasperGrupo) moGrupos.get(plGrupo);
    }

    public int getGruposSize() {
        return moGrupos.size();
    }

    public Font getFuenteTituloSub() {
        return moFuenteTituloSub;
    }

    public void setFuenteTituloSub(Font poFuente) {
        moFuenteTituloSub = poFuente;
        String lsNombre = poFuente.getFontName();
        if (!(lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COMIC_SANS)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COURIER_NEW)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_GEORGIA)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_TIMES_NEW_ROMAN)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_VERDANA))) {
            lsNombre = (ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL);
        }
        ar.com.fdvs.dj.domain.constants.Font loFuente = new ar.com.fdvs.dj.domain.constants.Font(
                poFuente.getSize(),
                lsNombre,
                (poFuente.getStyle() & poFuente.BOLD) == poFuente.BOLD,
                (poFuente.getStyle() & poFuente.ITALIC) == poFuente.ITALIC,
                false);
        moEstiloTituloSub.setFont(loFuente);
    }

    public Font getFuenteTitulo() {
        return moFuenteTitulo;
    }

    public void setFuenteTitulo(Font poFuente) {
        moFuenteTitulo = poFuente;
        String lsNombre = poFuente.getFontName();
        if (!(lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COMIC_SANS)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COURIER_NEW)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_GEORGIA)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_TIMES_NEW_ROMAN)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_VERDANA))) {
            lsNombre = (ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL);
        }
        ar.com.fdvs.dj.domain.constants.Font loFuente = new ar.com.fdvs.dj.domain.constants.Font(
                poFuente.getSize(),
                lsNombre,
                (poFuente.getStyle() & poFuente.BOLD) == poFuente.BOLD,
                (poFuente.getStyle() & poFuente.ITALIC) == poFuente.ITALIC,
                false);
        moEstiloTitulo.setFont(loFuente);
    }

    public Font getFuenteEncabezado() {
        return moFuenteEncabezado;
    }

    public void setFuenteEncabezado(Font poFuente) {
        moFuenteEncabezado = poFuente;
        String lsNombre = poFuente.getFontName();
        if (!(lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COMIC_SANS)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COURIER_NEW)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_GEORGIA)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_TIMES_NEW_ROMAN)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_VERDANA))) {
            lsNombre = (ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL);
        }
        ar.com.fdvs.dj.domain.constants.Font loFuente = new ar.com.fdvs.dj.domain.constants.Font(
                poFuente.getSize(),
                lsNombre,
                (poFuente.getStyle() & poFuente.BOLD) == poFuente.BOLD,
                (poFuente.getStyle() & poFuente.ITALIC) == poFuente.ITALIC,
                false);
        moEstiloEncabezado.setFont(loFuente);
    }

    public Font getFuenteDetalle() {
        return moFuente;
    }

    public void setFuenteDetalle(Font poFuente) {
        moFuente = poFuente;
        String lsNombre = poFuente.getFontName();
        if (!(lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COMIC_SANS)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_COURIER_NEW)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_GEORGIA)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_TIMES_NEW_ROMAN)
                || lsNombre.equals(ar.com.fdvs.dj.domain.constants.Font._FONT_VERDANA))) {
            lsNombre = (ar.com.fdvs.dj.domain.constants.Font._FONT_ARIAL);
        }
        ar.com.fdvs.dj.domain.constants.Font loFuente = new ar.com.fdvs.dj.domain.constants.Font(
                poFuente.getSize(),
                lsNombre,
                (poFuente.getStyle() & poFuente.BOLD) == poFuente.BOLD,
                (poFuente.getStyle() & poFuente.ITALIC) == poFuente.ITALIC,
                false);
        moEstiloPregenerado.setFont(loFuente);
    }

    public void setEstiloLineas(int plEstiloLineas) {
        mlEstiloLineas = plEstiloLineas;
        switch (mlEstiloLineas) {
            case mclEstiloLineasNada:
                moEstiloPregenerado.setBorder(Border.NO_BORDER);
                break;
            case mclEstiloLineasCuadrado:
//                moEstiloPregenerado.setBorder(Border.THIN);
                moEstiloPregenerado.setBorderBottom(Border.THIN);
                moEstiloPregenerado.setBorderTop(Border.THIN);
                moEstiloPregenerado.setBorderLeft(Border.THIN);
                moEstiloPregenerado.setBorderRight(Border.THIN);
                break;
            case mclEstiloLineasHorizontales:
                moEstiloPregenerado.setBorderBottom(Border.THIN);
                moEstiloPregenerado.setBorderTop(Border.THIN);
                moEstiloPregenerado.setBorderLeft(Border.NO_BORDER);
                moEstiloPregenerado.setBorderRight(Border.NO_BORDER);
                break;
        }

    }

    public void setVariasLineasSiNoCabe(boolean pbVriasLineas) {
        moEstiloPregenerado.setStretchWithOverflow(pbVriasLineas);
    }

    public void setAlineacionHorizontal(int plColumna, byte plAlineacion) {
        JInfConfigColumnaJasper loColumn = getConfig().getColumna(plColumna);
        loColumn.setAlineacion(plAlineacion);
    }

    private Style getEstilo(JInfConfigColumnaJasper loColumn) {
        Style loEstilo = new Style();
        loEstilo.setBlankWhenNull(moEstiloPregenerado.isBlankWhenNull());
        loEstilo.setOverridesExistingStyle(moEstiloPregenerado.isOverridesExistingStyle());
        loEstilo.setStretchWithOverflow(moEstiloPregenerado.isStretchWithOverflow());
        loEstilo.setVerticalAlign(moEstiloPregenerado.getVerticalAlign());

        loEstilo.setBorderBottom(moEstiloPregenerado.getBorderBottom());
        loEstilo.setBorderTop(moEstiloPregenerado.getBorderTop());
        loEstilo.setBorderLeft(moEstiloPregenerado.getBorderLeft());
        loEstilo.setBorderRight(moEstiloPregenerado.getBorderRight());

        loEstilo.setFont(moEstiloPregenerado.getFont());
        if (loColumn != null && loColumn.getAlineacion() >= 0) {
            loEstilo.setHorizontalAlign(loColumn.getAlineacionHorizontal());
        } else {
            if (moList != null) {
                switch (moList.getFields(loColumn.getColumna()).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                    case JListDatos.mclTipoFecha:
                        loEstilo.setHorizontalAlign(HorizontalAlign.CENTER);
                        break;
                    case JListDatos.mclTipoNumero:
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        loEstilo.setHorizontalAlign(HorizontalAlign.RIGHT);
                        break;
                    default:
                        loEstilo.setHorizontalAlign(HorizontalAlign.LEFT);
                }
            }
        }

        return loEstilo;

    }

    public void setImagenHeaderHeight(int i) {
        mlImagenHeaderHeight = i;
    }

    public void setImagenHeaderAling(byte i) {
        mlImagenHeaderAling = i;
    }

    public void setList(JListDatos moList) {
        try {
            this.moList = moList.Clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }

    public JInfConfigColumnasJasper getConfig() {
        if (moList != null) {
            int lLong = 80;
            if (moConfig.size() > 0) {
                lLong = 0;
            }
            synchronized (this) {
                for (int i = 0; i < moList.getFields().size(); i++) {
                    JFieldDef loCampo = moList.getFields(i);
                    if (!moConfig.existColumna(i)) {
                        moConfig.addColumna(
                                new JInfConfigColumnaJasper(
                                i,
                                String.valueOf(i),
                                moConfig.getUltOrden() + 1,
                                lLong,
                                loCampo.getCaption()));
                    }
                }
            }
        }
        return moConfig;
    }

    public JMotorInformes getMotor() {
        return moMotor;
    }

    public void setConfig(JTablaConfigTablaConfig poConfig) {

        //1º vaciamos las configuraciones previas
        moConfig.eliminarOrdenYLong();
        //transformamos JTablaConfigTablaConfig a JInfConfigColumnasJasper
        for (int i = 0; i < poConfig.size(); i++) {
            JTablaConfigColumna loColumnConfig = poConfig.getColumna(i);
            int lColumn = (int) JConversiones.cdbl(loColumnConfig.getNombre());
            if (!moConfig.existColumna(lColumn)) {
                moConfig.addColumna(
                        new JInfConfigColumnaJasper(
                        lColumn,
                        loColumnConfig.getNombre(),
                        loColumnConfig.getOrden(),
                        loColumnConfig.getLong(),
                        loColumnConfig.getCaption()));
            } else {
                moConfig.getColumna(lColumn).setCaption(loColumnConfig.getCaption());
                moConfig.getColumna(lColumn).setOrden(loColumnConfig.getOrden());
                moConfig.getColumna(lColumn).setLong(loColumnConfig.getLong());
                moConfig.getColumna(lColumn).setNombre(loColumnConfig.getNombre());
            }
        }
//        moConfig.ponerOrdenesCorrectos();
    }
    public void setConfig(JTablaConfigTablaConfig poConfig, JListDatos poList) {

        //1º vaciamos las configuraciones previas
        moConfig.eliminarOrdenYLong();
        //transformamos JTablaConfigTablaConfig a JInfConfigColumnasJasper
        for (int i = 0; i < poConfig.size(); i++) {
            JTablaConfigColumna loColumnConfig = poConfig.getColumna(i);
            int lColumn = (int) JConversiones.cdbl(loColumnConfig.getNombre());
            if (!moConfig.existColumna(lColumn)) {
                moConfig.addColumna(
                        new JInfConfigColumnaJasper(
                        lColumn,
                        loColumnConfig.getNombre(),
                        loColumnConfig.getOrden(),
                        loColumnConfig.getLong(),
                        poList.getFields(lColumn).getCaption()));
            } else {
                moConfig.getColumna(lColumn).setCaption(poList.getFields(lColumn).getCaption());
                moConfig.getColumna(lColumn).setOrden(loColumnConfig.getOrden());
                moConfig.getColumna(lColumn).setLong(loColumnConfig.getLong());
                moConfig.getColumna(lColumn).setNombre(loColumnConfig.getNombre());
            }
        }
//        moConfig.ponerOrdenesCorrectos();
    }

    public String getTitulo() {
        return msTitulo;
    }

    public void setTitulo(String msTitulo) {
        this.msTitulo = msTitulo;
    }

    public static JListDatos getListOrdenado(final JListDatos poList, final JInfConfigColumnasJasper poConfig) throws CloneNotSupportedException, ECampoError {
        return getListOrdenado(poList, poConfig, true);
    }

    public static JListDatos getListOrdenado(final JListDatos poList, final JInfConfigColumnasJasper poConfig, boolean pbCONColumnOcultas) throws CloneNotSupportedException, ECampoError {
        JListDatos loListNuevo = new JListDatos();

        for (int i = 0; i < poList.getFields().count(); i++) {
            JInfConfigColumnaJasper loColumn = poConfig.getColumnaPorOrden(i);
            if (loColumn != null && (loColumn.getLong() > 0 || pbCONColumnOcultas)) {
                loListNuevo.getFields().addField(poList.getFields(loColumn.getColumna()).Clone());
            }

        }

        if (poList.moveFirst()) {
            do {
                loListNuevo.addNew();
                int li = 0;
                for (int i = 0; i < poList.getFields().count(); i++) {
                    JInfConfigColumnaJasper loColumn = poConfig.getColumnaPorOrden(i);
                    if (loColumn != null && (loColumn.getLong() > 0 || pbCONColumnOcultas)) {
                        loListNuevo.getFields(li).setValue(
                                poList.getFields(loColumn.getColumna()).getString());
                        li++;
                    }

                }
                loListNuevo.update(false);

            } while (poList.moveNext());
        }

        return loListNuevo;
    }

    /**
     * Dado un listDatos pasado desde el controlador genero el listado
     */
    public void generarListado(final JListDatos poList, IPanelGenerico poPanel, String psTitulo) throws Exception {
        generarListado(poList, poPanel.getTablaConfig().getConfigTablaConcreta(), psTitulo);
    }

    /**
     * Dado un listDatos y la config de columnas genero el listado
     * @param poList
     * @param loConfig
     * @param psTitulo
     * @throws java.lang.Exception
     */
    public void generarListado(final JListDatos poList, JTablaConfigTablaConfig loConfig, String psTitulo) throws Exception {
        setList(poList);
        setConfig(loConfig, poList);
        setTitulo(psTitulo);

        generarListado();
    }

    public FastReportBuilder getFastReportBuilderPregenerado() {
        return modrb;
    }

    public JasperReport getJasperReportPregenerado() {
        return moJasperReportPregenerado;
    }

    public JListDatos getListProcesadoPregenerado() {
        return moListProcesadoPregenerado;
    }

    public static Page getPageA3() {
        return new Page(842, 1190);
    }

    public void setPageManual(Page poPage) {
        moPageManual = poPage;
    }

    public Page getPageManual() {
        return moPageManual;
    }

    public Page getPageAutomatico() {
        Page loPage;
        loPage = Page.Page_A4_Portrait();
        //calc las columnas
        double ldTotalLong = 0;
        for (int i = 0; i < getList().getFields().count(); i++) {
            JInfConfigColumnaJasper loColumn = moConfig.getColumnaPorOrden(i);
            if (loColumn != null && loColumn.getLong() > 0) {
                ldTotalLong += ((double) loColumn.getLong()) * mcnCoef;
            }
        }
        if (ldTotalLong > 29.0 && isSeleccionarA3()) {
            loPage = getPageA3();
        } else {
            if (ldTotalLong > 20.0) {
                loPage = Page.Page_A4_Landscape();
            }
        }
        return loPage;

    }

    private String getFormatoCadena(final int plTipo) {
        String retVal = "";
        if (plTipo == JListDatos.mclTipoNumero) {
            retVal = "###,###,###,##0";
        }
        if (plTipo == JListDatos.mclTipoNumeroDoble) {
            retVal = "###,###,###,##0.00";
        }
        if (plTipo == JListDatos.mclTipoMoneda3Decimales) {
            retVal = "###,###,###,##0.000 \u00A4";
        }
        if (plTipo == JListDatos.mclTipoMoneda) {
            retVal = "###,###,###,##0.00 \u00A4";
        }
        if (plTipo == JListDatos.mclTipoPorcentual3Decimales) {
            retVal = "#,##0.000 %";
        }
        if (plTipo == JListDatos.mclTipoPorcentual) {
            retVal = "#,##0.00 %";
        }
        if (plTipo == JListDatos.mclTipoFecha) {
            retVal = "dd/MM/yyyy";
        }
        return retVal;
    }

    public void pregenerarFastReportBuilderSolo() throws Exception {
        moListProcesadoPregenerado = getList();

        //si el orden es cambiado se regenera el list datos con TODOS los campos en el orden correcto
        getConfig().ponerOrdenesCorrectos();
        if (!moConfig.isOrdenCorrecto()) {
            moListProcesadoPregenerado = getListOrdenado(moListProcesadoPregenerado, getConfig());
        }

        modrb = new FastReportBuilder();
        boolean lbTemplate=false;
        if(getFileTemplateFile()!=null && !getFileTemplateFile().equals("") ){
            if(new File(getFileTemplateFile()).exists() ){
                modrb.setTemplateFile(getFileTemplateFile());
                lbTemplate=true;
            }else{
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "No existe la plantilla jrxml " + getFileTemplateFile() + " para el listado " + getTitulo());
            }
        }
        if(!lbTemplate){
            Page loPage = null;
            if (moPageManual != null) {
                loPage = moPageManual;
                //la pagina manual se debe poner en cada impresion
                moPageManual = null;
            } else {
                if (mbPantallaSeleccion) {
                    JInfGeneralJasperSeleccionPAGE loForm = new JInfGeneralJasperSeleccionPAGE(new Frame(), true);
                    loForm.setDatos(this);
                    loForm.setVisible(true);
                    loPage = moPageManual;
                    moPageManual = null;
                    if (loPage == null) {
                        throw new Exception("Cancelado por el usuario");
                    }
                } else {
                    loPage = getPageAutomatico();
                }
            }


            //tamaño de pagina y titulo
            modrb.setPageSizeAndOrientation(loPage);

            modrb.setLeftMargin(mcsMargen);
            modrb.setRightMargin(mcsMargen);
            modrb.setTopMargin(mcsMargen);
            modrb.setBottomMargin(mcsMargen);

            modrb.setDefaultStyles(
                    moEstiloTitulo, moEstiloTituloSub,
                    moEstiloEncabezado, moEstiloPregenerado);

            modrb.addAutoText(
                    new AutoText(
                    AutoText.AUTOTEXT_PAGE_X_SLASH_Y,
                    AutoText.POSITION_FOOTER,
                    HorizontalBandAlignment.CENTER));

            modrb.setTitle(getTitulo());
            modrb.setSubtitle(getSubTitulo());


            if (getImagenHeader() != null && !getImagenHeader().equals("")) {
                modrb.addImageBanner(getImagenHeader(), new Integer(loPage.getWidth() - mcsMargen * 2 - 5), new Integer(mlImagenHeaderHeight), mlImagenHeaderAling);
            }
        }

        IListaElementos loListaColumnas = new JListaElementos();
        //add las columnas
        for (int i = 0; i < getListProcesadoPregenerado().getFields().count(); i++) {
            JInfConfigColumnaJasper loColumn = moConfig.getColumnaPorOrden(i);//no hay huecos en el orden
            JFieldDef loCampo = getListProcesadoPregenerado().getFields(i);//el listado pregenerado ya esta ordenado
            if (loColumn != null && loColumn.getLong() > 0) {
                String lsFormato = loColumn.getFormato();
                if (lsFormato == null || lsFormato.equals("")) {
                    lsFormato = getFormatoCadena(loCampo.getTipo());
                }
                AbstractColumn loAbstColumn = ColumnBuilder.getInstance().setCommonProperties(
                        loColumn.getCaption(),
                        String.valueOf(i),
                        loCampo.getClaseStandar().getName(),
                        loColumn.getLong(),
                        true).setPattern(lsFormato).setStyle(getEstilo(loColumn)).build();
                modrb.addColumn(loAbstColumn);
                loListaColumnas.add(loAbstColumn);
            } else {
                loListaColumnas.add(null);
            }
        }
        //add grupos
        for (int i = 0; i < moGrupos.size(); i++) {
            JInfGeneralJasperGrupo loGrupo = (JInfGeneralJasperGrupo) moGrupos.get(i);

            int lColumn = getListProcesadoPregenerado().getFields().getIndiceDeCampo(loGrupo.getColumnaAAgrupar());
            if (lColumn >= 0 && loListaColumnas.get(lColumn) != null) {
                GroupBuilder gb1 = new GroupBuilder();
                gb1.setCriteriaColumn((PropertyColumn) loListaColumnas.get(lColumn));
                for (int ii = 0; ii < loGrupo.getOperaciones().size(); ii++) {
                    JInfGeneralJasperGrupoOperacion loOp = (JInfGeneralJasperGrupoOperacion) loGrupo.getOperaciones().get(ii);
                    int lColumnOP = getListProcesadoPregenerado().getFields().getIndiceDeCampo(loOp.msNombreColumna);
                    if (lColumnOP >= 0 && loListaColumnas.get(lColumnOP) != null) {
                        modrb.setGrandTotalLegend("TOTAL...");
                        AbstractColumn loColumnOP = (AbstractColumn) loListaColumnas.get(lColumnOP);
                        if (loOp.mbPie) {
                            modrb.addGlobalFooterVariable(loColumnOP, loOp.moOperacion);
                            gb1.addFooterVariable(loColumnOP, loOp.moOperacion);
                        } else {
                            modrb.addGlobalHeaderVariable(loColumnOP, loOp.moOperacion);
                            gb1.addHeaderVariable(loColumnOP, loOp.moOperacion);
                        }
                    }
                }
                modrb.addGroup(gb1.build());
            }
        }



    }

    public void pregenerar() throws Exception {

        if (modrb == null) {
            pregenerarFastReportBuilderSolo();
        }
        DynamicReport dr = modrb.build();


        HashMap loPropiedades = new HashMap();
        //        loPropiedades.put(
        //                    JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
        //                    JMotorInformes.getConfigurarImpresion(loPage.getWidth(), loPage.getHeight(), lOrientacion));


        moJasperReportPregenerado = DynamicJasperHelper.generateJasperReport(
                dr,
                new ClassicLayoutManager(), loPropiedades);

        
    }

    public void lanzarListadoPregenerado() throws Exception {
        moMotor.setList(getListProcesadoPregenerado());
        moMotor.lanzarInformeCompilado(getJasperReportPregenerado());
    }

    public void generarListado() throws Exception {
        pregenerar();
        lanzarListadoPregenerado();
    }

    /**
     * @return the msImagenHeader
     */
    public String getImagenHeader() {
        return msImagenHeader;
    }

    /**
     * @param msImagenHeader the msImagenHeader to set
     */
    public void setImagenHeader(String msImagenHeader) {
        this.msImagenHeader = msImagenHeader;
    }

    /**
     * @return the mbSeleccionarA3
     */
    public boolean isSeleccionarA3() {
        return mbSeleccionarA3;
    }

    /**
     * @param mbSeleccionarA3 the mbSeleccionarA3 to set
     */
    public void setSeleccionarA3(boolean mbSeleccionarA3) {
        this.mbSeleccionarA3 = mbSeleccionarA3;
    }

    /**
     * @return the mbPantallaSeleccion
     */
    public boolean isPantallaSeleccion() {
        return mbPantallaSeleccion;
    }

    /**
     * @param mbPantallaSeleccion the mbPantallaSeleccion to set
     */
    public void setPantallaSeleccion(boolean mbPantallaSeleccion) {
        this.mbPantallaSeleccion = mbPantallaSeleccion;
    }

    /**
     * @return the msSubTitulo
     */
    public String getSubTitulo() {
        return msSubTitulo;
    }

    /**
     * @param msSubTitulo the msSubTitulo to set
     */
    public void setSubTitulo(String msSubTitulo) {
        this.msSubTitulo = msSubTitulo;
    }

    /**
     * @return the msFileTemplateFile
     */
    public String getFileTemplateFile() {
        return msTemplateFile;
    }

    /**
     * @param msFileTemplateFile the msFileTemplateFile to set
     */
    public void setTemplateFile(String msFileTemplateFile) {
        this.msTemplateFile = msFileTemplateFile;
    }
//    /**
//     * @return the moPropiedades
//     */
//    public HashMap getPropiedades() {
//        return moPropiedades;
//    }
//
//    /**
//     * @param moPropiedades the moPropiedades to set
//     */
//    public void setPropiedades(HashMap moPropiedades) {
//        this.moPropiedades = moPropiedades;
//    }    
    public static void main(String [] arg ){
        try {
            JListDatos loList = new JListDatos(null, "prueba", new String[]{"Codigo","Descripcion"}, new int[]{JListDatos.mclTipoNumero, JListDatos.mclTipoCadena}, new int[]{0});
            loList.addNew();
            loList.getFields(0).setValue("1");
            loList.getFields(1).setValue("Descrip 1");
            loList.update(false);
            for(int i = 2 ; i < 100 ; i++){
                loList.add(new JFilaDatosDefecto(new String[]{String.valueOf(i),"descrip "+String.valueOf(i)}));
            }
            
            JInfGeneralJasper loInf = new JInfGeneralJasper();
            loInf.setList(loList);
            
            loInf.getMotor().getParametros().put("leftHeader","pepe");
            loInf.getMotor().getParametros().put("rightHeader","juan");

            loInf.setTemplateFile("TemplateReportTest.jrxml");

            loInf.generarListado();
        } catch (Exception ex) {
            JMsgBox.mensajeErrorYLog(null, ex);
        }
    }


}
