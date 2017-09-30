/*
 * JListDatosXSL.java
 *
 * Created on 23 de noviembre de 2004, 10:47
 */

package impresionXML.tools;

import ListDatos.*;
import java.awt.Font;


/**Formato de un JListDatos*/
public class JListDatosXSL {
    /**Sin lineas*/
    public static final int mclAutoformatoNada = 0;
    /**lineas verticales y hori.*/
    public static final int mclAutoformatoCuadricula = 1;
    /**lineas hori.*/
    public static final int mclAutoformatoFilas = 2;
    /**lineas verticales*/
    public static final int mclAutoformatoColumnas = 3;

    /**Alineacion izq.*/
    public static final int mclAlineacionIzq = 0;
    /**Alineacion der.*/
    public static final int mclAlineacionDer = 1;
    /**Alineacion Cen.*/
    public static final int mclAlineacionCen = 2;
    /**Alineacion Jus.*/
    public static final int mclAlineacionJus = 3;

    public static final int mclNumPaginaNinguno   = 0;
    public static final int mclNumPaginaArribaIzq = 1;
    public static final int mclNumPaginaArribaDer = 2;
    public static final int mclNumPaginaArribaCen = 3;
    public static final int mclNumPaginaAbajoIzq  = 4;
    public static final int mclNumPaginaAbajoDer  = 5;
    public static final int mclNumPaginaAbajoCen  = 6;
    
    private double mdPaginaAlto = 29.7;
    private double mdPaginaAncho = 21;
    private double mdPaginaMargenSuperior = 0.7;
    private double mdPaginaMargenInferior = 0.7;
    private double mdPaginaMargenDerecha = 1;
    private double mdPaginaMargenIzquierda = 1;
    boolean mbNuevoTipoPagina = true;
    boolean mbNuevaPagina = false;
    
    int mlAutoformato = mclAutoformatoNada;
    int mlFilasPie = 0;
    boolean mbEncabezado = true;

    JListDatosXSLColumna[] maoColumnas = null;
    
    java.awt.Font moFuenteEncabezado = new java.awt.Font(null, java.awt.Font.PLAIN, 10);
    java.awt.Font moFuenteCuerpo = new java.awt.Font(null, java.awt.Font.PLAIN, 10);
    java.awt.Font moFuentePie = new java.awt.Font(null, java.awt.Font.BOLD, 10);
    
    int mlNumeroPaginaVisible = mclNumPaginaAbajoCen;

    JListDatosXSLCabe moCabezera = new JListDatosXSLCabe();
    
    /**
     * Creates a new instance of JListDatosXSL
     * @param plNumeroCampos número de campos
     */
    public JListDatosXSL(final int plNumeroCampos) {
        maoColumnas = new JListDatosXSLColumna[plNumeroCampos];
        for(int i = 0; i< plNumeroCampos;i++){
            maoColumnas[i] = new JListDatosXSLColumna();
        }
    }

    public void setTamanoFuente(final int plTamano) {
        moFuenteEncabezado = new java.awt.Font(null, java.awt.Font.PLAIN, plTamano);
        moFuenteCuerpo = new java.awt.Font(null, java.awt.Font.PLAIN, plTamano);
        moFuentePie = new java.awt.Font(null, java.awt.Font.BOLD, plTamano);
    }

    /**
     * Constructor
     * @param psTitulo título
     * @param pdLong lista de long.
     */
    public JListDatosXSL(final String psTitulo, final double[] pdLong) {
        this(pdLong.length);
        moCabezera.msTitulo = psTitulo;
        for(int i = 0; i< pdLong.length;i++){
            maoColumnas[i].setLong(pdLong[i]);
        }
    }
    public JListDatosXSLColumna getColumna(final int i){
        return maoColumnas[i];
    }
    public int getNumeroColumnas(){
        return maoColumnas.length;
    }
    public JListDatosXSLCabe getCabezera(){
        return moCabezera;
    }
    public void setFuenteCabezera(final Font poFuente){
        moCabezera.moFuenteCabezera = poFuente;
    }
    public void setFuenteCuerpo(final Font poFuente){
        moFuenteCuerpo = poFuente;
    }
    public void setFuentePie(final Font poFuente){
        moFuentePie = poFuente;
    }
    public void setFuenteTitulo(final Font poFuente){
        moCabezera.moFuenteTitulo = poFuente;
    }
    public void setNumeroPaginaVisible(final boolean pbNumeroPaginaVisible){
        mlNumeroPaginaVisible = (pbNumeroPaginaVisible ? mclNumPaginaAbajoCen : mclNumPaginaNinguno);
    }
    public void setNumeroPaginaPosicion(final int plNumeroPaginaVisible){
        mlNumeroPaginaVisible = plNumeroPaginaVisible;
    }
    /**
     * inserta la imagen del logotipo
     * @param psImagen camino de la imagen del logotipo
     */
    public void setLogotipoImagen(final String psImagen){
        moCabezera.msLogotipoImagen=psImagen;
    }
    /**
     * inserta la imagen del logotipo
     * @param psImagen camino de la imagen del logotipo
     */
    public void setLogotipoImagen2(final String psImagen){
        moCabezera.msLogotipoImagen2=psImagen;
    }
    /**
     * inserta un texto del logotipo
     * @param psTexto texto
     */
    public void addLogotipoTexto(final String psTexto){
        String[] lasLogos = new String[moCabezera.masLogotipoTextos.length+1];
        for(int i = 0; i< moCabezera.masLogotipoTextos.length;i++){
            lasLogos[i] = moCabezera.masLogotipoTextos[i];
        }
        lasLogos[lasLogos.length-1] = psTexto;
        moCabezera.masLogotipoTextos = lasLogos;
    }
    /**
     * inserta un texto del logotipo
     * @param psTexto texto
     */
    public void addLogotipoTexto2(final String psTexto){
        String[] lasLogos = new String[moCabezera.masLogotipoTextos2.length+1];
        for(int i = 0; i< moCabezera.masLogotipoTextos2.length;i++){
            lasLogos[i] = moCabezera.masLogotipoTextos2[i];
        }
        lasLogos[lasLogos.length-1] = psTexto;
        moCabezera.masLogotipoTextos2 = lasLogos;
    }
    /**
     * establece el título
     * @param psTitulo título
     */
    public void setTitulo(final String psTitulo){
        moCabezera.msTitulo = psTitulo;
    }
    /**
     * establece las filas del pie
     * @param plValor número de filas del pie
     */
    public void setFilasPie(final int plValor){
        mlFilasPie = plValor;
    }
    /**
     * establece el autoformaot
     * @param plValor valor
     */
    public void setAutoformato(final int plValor){
        mlAutoformato  = plValor;
    }
    /**
     * establece la long. de la columna(CM)
     * @param plIndex columna
     * @param pdLong long. en CM
     */
    public void setLong(final int plIndex, final double pdLong){
        maoColumnas[plIndex].setLong(pdLong);
    }
    /**
     * establece la alineación de la columna
     * @param plIndex columna
     * @param plAlin Alineación
     */
    public void setAlineacion(final int plIndex, final int plAlin){
        maoColumnas[plIndex].setAlineacion(plAlin);
    }
    /**
     * establece la alineación de la columna en función del tipo de la base de datos
     * @param plIndex Columna
     * @param plTipoBD tipo de bd
     */
    public void setAlineacionBD(final int plIndex, final int plTipoBD){
        switch(plTipoBD){
            case JListDatos.mclTipoBoolean:
                maoColumnas[plIndex].setAlineacion(mclAlineacionCen);
                break;
            case JListDatos.mclTipoCadena:
                maoColumnas[plIndex].setAlineacion(mclAlineacionIzq);
                break;
            case JListDatos.mclTipoFecha:
                maoColumnas[plIndex].setAlineacion(mclAlineacionCen);
                break;
            case JListDatos.mclTipoNumero:
                maoColumnas[plIndex].setAlineacion(mclAlineacionDer);
                break;
            case JListDatos.mclTipoNumeroDoble:
                maoColumnas[plIndex].setAlineacion(mclAlineacionDer);
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                maoColumnas[plIndex].setAlineacion(mclAlineacionDer);
                break;
            case JListDatos.mclTipoMoneda:
                maoColumnas[plIndex].setAlineacion(mclAlineacionDer);
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                maoColumnas[plIndex].setAlineacion(mclAlineacionDer);
                break;
            case JListDatos.mclTipoPorcentual:
                maoColumnas[plIndex].setAlineacion(mclAlineacionDer);
                break;
            default:
                maoColumnas[plIndex].setAlineacion(mclAlineacionIzq);
        }
    }
    /**
     * Establece si tendrá encabezado
     * @param pbValor valor
     */
    public void setEncabezadoTabla(final boolean pbValor){
        mbEncabezado = pbValor;
    }
    /**
     * Devuelve el título
     * @return titulo
     */
    public String getTitulo(){
        return moCabezera.msTitulo;
    }
    /**
     * Índica si se pone una nueva página, indep. de tamaño que la anterior(generalmente para la primera tabla se pone a true y para el resto a false)
     * @param pbValor valor
     */
    public void setNuevoTipoPagina(final boolean pbValor){
        mbNuevoTipoPagina = pbValor;
    }
    /**
     * Índica si antes de imprimir esta tabla se hace un salto de página
     * @param pbNuevaPagina valor
     */
    public void setNuevaPagina(final boolean pbNuevaPagina){
        mbNuevaPagina = pbNuevaPagina;
    }
    /**
     * Establece el tamaño de la página de impresión, con sus márgenes
     * @param pdPaginaAlto alto de página
     * @param pdPaginaAncho ancho de página
     * @param pdPaginaMargenSuperior margen superior
     * @param pdPaginaMargenInferior margen inferior
     * @param pdPaginaMargenDerecha margen derecha
     * @param pdPaginaMargenIzquierda margen izquierda
     */
    public void setPagina(final double pdPaginaAlto,final double pdPaginaAncho, final double pdPaginaMargenSuperior, final double pdPaginaMargenInferior, final double pdPaginaMargenDerecha, final double pdPaginaMargenIzquierda){
        mdPaginaAlto = pdPaginaAlto;
        mdPaginaAncho = pdPaginaAncho;
        mdPaginaMargenSuperior = pdPaginaMargenSuperior;
        mdPaginaMargenInferior = pdPaginaMargenInferior;
        mdPaginaMargenDerecha = pdPaginaMargenDerecha;
        mdPaginaMargenIzquierda = pdPaginaMargenIzquierda;
    }
    /**
     * Genera el xsl del formato actual
     * @param plIndex índice
     * @param poContruirXSL constructor
     */
    public void rellenarXSLParcial(final int plIndex, final JConstruirXSL poContruirXSL){
        if(mbNuevoTipoPagina){
            poContruirXSL.addXSLPagina(plIndex, this);
        }
        poContruirXSL.addXSLBody(plIndex, this);
    }
    /**
     * Genera el xsl-fo directamente del formato actual+JListDatos
     * @param plIndex índice
     * @param poContruirXSLFO constructor
     * @param poList datos
     */
    public void rellenarXSLParcialFO(final int plIndex, final JConstruirXSLFO poContruirXSLFO, final JListDatos poList){
        if(mbNuevoTipoPagina){
            poContruirXSLFO.addXSLPagina(plIndex, this);
        }
        poContruirXSLFO.addXSLBody(plIndex, this, poList);
    }

    public double getPaginaAlto() {
        return mdPaginaAlto;
    }

    public double getPaginaAncho() {
        return mdPaginaAncho;
    }

    public double getPaginaMargenSuperior() {
        return mdPaginaMargenSuperior;
    }

    public double getPaginaMargenInferior() {
        return mdPaginaMargenInferior;
    }

    public double getPaginaMargenDerecha() {
        return mdPaginaMargenDerecha;
    }

    public double getPaginaMargenIzquierda() {
        return mdPaginaMargenIzquierda;
    }
    
}

