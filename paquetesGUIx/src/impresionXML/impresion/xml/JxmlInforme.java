/*
 * JxmlInforme.java
 *
 * Created on 25 de enero de 2007, 8:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import impresionXML.impresion.estructura.ILienzo;
import impresionXML.impresion.motorImpresion.JPagina;
import impresionXML.impresion.xml.diseno.campos.JTCAMPOS;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JListaElementos;

public class JxmlInforme  extends JxmlAbstract  implements Printable {
    private static final long serialVersionUID = 1L;
    public static final String mcsNombreXml = "informe";

    private String msInformeFisico;
    private String mlCodInforme;
    private String msNombre;
    
    private float mdAncho = (float)21;
    private float mdAlto = (float)29.7;
    private int   mlOrientacion = PageFormat.PORTRAIT;
    private float mdMargenSuperior;
    private float mdMargenInferior;
    private float mdMargenDerecho;
    private float mdMargenIzquierdo;
    
    private IListaElementos moBandas = new JListaElementos();
    private IListaElementos moFuentes = new JListaElementos();

    private JxmlInformeDiseno moDiseno = new JxmlInformeDiseno();
    
    /**
     * Creates a new instance of JxmlInforme
     */
    public JxmlInforme(String psInformeFisico) {
        super();
        setInformeFisico(psInformeFisico);
    }
    
    void crearColecciones(){
        moBandas = new JListaElementos();
        moFuentes = new JListaElementos();
    }
    
    public String toString() {
        return getNombre();
    }

    public IListaElementos getBandas() {
        return moBandas;
    }
    public void add(JxmlBanda poBanda){
        moBandas.add(poBanda);
        firePropertyChange("banda.add", null, poBanda);
        
    }
    public void borrar(JxmlBanda poBanda){
        moBandas.remove(poBanda);
        firePropertyChange("banda.remove", poBanda, null);
    }
    public void borrarBanda(int plIndex){
        Object lo = moBandas.remove(plIndex);
        firePropertyChange("banda.remove", lo, null);
    }
    public int sizeBanda(){
        return moBandas.size();
    }
    public JxmlBanda getBanda(int plIndex){
        return (JxmlBanda) moBandas.get(plIndex);
    }

    public IListaElementos getFuentes() {
        return moFuentes;
    }
    public void add(JxmlFuente poFuente){
        moFuentes.add(poFuente);
        firePropertyChange("fuente.add", null, poFuente);
    }
    public void borrar(JxmlFuente poFuente){
        moFuentes.remove(poFuente);
        firePropertyChange("fuente.remove", poFuente, null);
    }
    public void borrarFuente(int plIndex){
        Object lo = moFuentes.remove(plIndex);
        firePropertyChange("fuente.remove", lo, null);
    }
    public int sizeFuente(){
        return moFuentes.size();
    }
    public JxmlFuente getFuente(int plIndex){
        return (JxmlFuente) moFuentes.get(plIndex);
    }
    
    
    public JxmlBanda getBanda(final String psNombre) {
        JxmlBanda loBanda=null;
        for(int i = 0; i < moBandas.size() && loBanda == null;i++){
            if(((JxmlBanda)moBandas.get(i)).getNombre().compareToIgnoreCase(psNombre)==0){
                loBanda = (JxmlBanda)moBandas.get(i);
            }
        }
        return loBanda;
    }

    public JxmlFuente getFuente(final String psNombre) {
        JxmlFuente loFuente=null;
        for(int i = 0; i < moFuentes.size() && loFuente == null && psNombre!=null;i++){
            if(((JxmlFuente)moFuentes.get(i)).getNombre().compareToIgnoreCase(psNombre)==0){
                loFuente = (JxmlFuente)moFuentes.get(i);
            }
        }
        return loFuente;
    }
    public IListaElementos getCuadrado(final String psNombre) {
        return getObjetoNombre(psNombre, 0);
    }
    public IListaElementos getImagen(final String psNombre) {
        return getObjetoNombre(psNombre, 1);
    }
    public IListaElementos getLinea(final String psNombre) {
        return getObjetoNombre(psNombre, 2);
    }
    public IListaElementos getTexto(final String psNombre) {
        return getObjetoNombre(psNombre, 3);
    }
    private IListaElementos getObjetoNombre(final String psNombre, final int plTipo) {
        JxmlBanda loBanda=null;
        IListaElementos loLista = new JListaElementos();
        for(int i = 0; i < moBandas.size();i++){
            loBanda=(JxmlBanda)moBandas.get(i);
            IListaElementos loListaAux=null;
            switch(plTipo){
                case 0:
                    moDiseno.addCampo(JxmlCuadrado.mcsNombreXml, psNombre, "", "");
                    loListaAux = loBanda.getCuadradoNombre(psNombre);
                    break;
                case 1:
                    moDiseno.addCampo(JxmlImagen.mcsNombreXml, psNombre, "", "");
                    loListaAux = loBanda.getImagenNombre(psNombre);
                    break;
                case 2:
                    moDiseno.addCampo(JxmlLinea.mcsNombreXml, psNombre, "", "");
                    loListaAux = loBanda.getLineaNombre(psNombre);
                    break;
                case 3:
                    moDiseno.addCampo(JxmlTexto.mcsNombreXml, psNombre, "", "");
                    loListaAux = loBanda.getTextoNombre(psNombre);
                    break;
                default:
                    moDiseno.addCampo(JxmlTexto.mcsNombreXml, psNombre, "", "");
                    loListaAux = loBanda.getTextoNombre(psNombre);
            }
            for(int iAux = 0; iAux < loListaAux.size();iAux++){
                loLista.add(loListaAux.get(iAux));
            }
            
        }
        return loLista;
    }
    void inicializarImpresion(){
         for(int i = 0; i < moBandas.size() ;i++){
            JxmlBanda loBanda = (JxmlBanda)moBandas.get(i);
            loBanda.inicializarImpresion();
            loBanda.imprimir(this);
        }       
    }
    
    private static boolean isValor(float pdCMActualW, float pdCMActualH, float pdCMW, float pdCMH){
        boolean lbResul = false;

//        //ponemos siempre el mayo en el alto
//        if(pdCMW > pdCMH){
//            float ldAux = pdCMH;
//            pdCMH = pdCMW;
//            pdCMW = ldAux;
//        }
//        if(pdCMActualW > pdCMActualH){
//            float ldAux = pdCMActualH;
//            pdCMActualH = pdCMActualW;
//            pdCMActualW = ldAux;
//        }

        lbResul=JConversiones.mlComparaDoubles(pdCMW, pdCMActualW, 0.15)==0 &&
                JConversiones.mlComparaDoubles(pdCMH, pdCMActualH, 0.15)==0;


        return lbResul;
    }
    public static PrintRequestAttributeSet getConfigurarImpresion(float pdAnchoCM, float pdAltoCM, int plOrien){
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

        // Busco el tamaño de papel de la impresora más parecido
        MediaSizeName loMediaName = null;
        if(isValor(pdAnchoCM, pdAltoCM,(float) 21,(float) 29.7))
            loMediaName = MediaSizeName.ISO_A4;
        else if(isValor(pdAnchoCM, pdAltoCM, (float) 14.8,(float) 21))
            loMediaName = MediaSizeName.ISO_A5;
        else if(isValor(pdAnchoCM, pdAltoCM, (float) 29.7,(float) 42))
            loMediaName = MediaSizeName.ISO_A3;
        else if(isValor(pdAltoCM, pdAnchoCM,(float) 21,(float) 29.7)){
            loMediaName = MediaSizeName.ISO_A4;
            plOrien=PageFormat.LANDSCAPE;
        }else if(isValor(pdAltoCM, pdAnchoCM, (float) 14.8,(float) 21)){
            loMediaName = MediaSizeName.ISO_A5;
            plOrien=PageFormat.LANDSCAPE;
        }else if(isValor(pdAltoCM, pdAnchoCM, (float) 29.7,(float) 42)){
            loMediaName = MediaSizeName.ISO_A3;
            plOrien=PageFormat.LANDSCAPE;
        }else
            loMediaName = MediaSize.findMedia(pdAnchoCM*10, pdAltoCM*10, MediaSize.MM);

        if(loMediaName!=null) {
            printRequestAttributeSet.add(loMediaName);
        }

        // Orientación
        OrientationRequested orientation;
        if(plOrien==PageFormat.LANDSCAPE){
            orientation = OrientationRequested.LANDSCAPE;
            // Configuro el area de impresión
            //es posible q el ancho/alto ya venga al reves
            if(pdAnchoCM> pdAltoCM){
                printRequestAttributeSet.add(new MediaPrintableArea(0, 0, pdAnchoCM*10, pdAltoCM*10, MediaPrintableArea.MM));
            }else{
                printRequestAttributeSet.add(new MediaPrintableArea(0, 0, pdAltoCM*10, pdAnchoCM*10, MediaPrintableArea.MM));
            }
        }else{
            orientation = OrientationRequested.PORTRAIT;
            // Configuro el area de impresión
            printRequestAttributeSet.add(new MediaPrintableArea(0, 0, pdAnchoCM*10, pdAltoCM*10, MediaPrintableArea.MM));
        }
        printRequestAttributeSet.add(orientation);
//        printRequestAttributeSet.add(new PrinterResolution(300,300,PrinterResolution.DPI));
        return printRequestAttributeSet;
    }
    public PrintRequestAttributeSet getConfigurarImpresion() {
        return getConfigurarImpresion(getAncho(), getAlto(), getOrientacion());
    }
    public void imprimir(final ILienzo poLienzo) throws Exception{
        inicializarImpresion();
        for(int i = 0; i < moBandas.size() ;i++){
            JxmlBanda loBanda = (JxmlBanda)moBandas.get(i);
            loBanda.getBanda().imprimir(poLienzo, null);
        }
//        System.gc();
    }
    
    public static PrinterJob getPrintJob(String psImpresora) throws PrinterException{
        PrinterJob pj=PrinterJob.getPrinterJob();
        String lsImpresora = psImpresora;
        if(lsImpresora!=null && !lsImpresora.equals("")){
            PrintService[] loImpresoras = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService loImpresora=null;
            for(int i = 0; i < loImpresoras.length  ; i++){
                if(loImpresoras[i].getName().equalsIgnoreCase(lsImpresora)){
                    loImpresora = loImpresoras[i];
                }
            }
            if(loImpresora!=null){
                pj.setPrintService(loImpresora);
            }
        }
        return pj;

    }
    
    public void imprimir(final String psImpresora, final boolean pbPresentarDialogoImpresion) throws PrinterException{

        inicializarImpresion();
        PrinterJob pj = getPrintJob(psImpresora);
        pj.setPrintable(this);
        
        if(pbPresentarDialogoImpresion){
            if(pj.printDialog(getConfigurarImpresion())){
                pj.print();
            }
        }else{
            pj.print(getConfigurarImpresion());
        }
        
        pj=null;
//        System.gc();
        
        
    }
    public int print(java.awt.Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
     	Graphics2D  g2 = (Graphics2D) g;

        JPagina loPag = new JPagina(getMargenIzquierdo(), getMargenSuperior(), getAnchoTotalReal(), getAltoTotalReal(), pageFormat, g2);

        for(int i = 0; i < moBandas.size() ;i++){
            JxmlBanda loBanda = (JxmlBanda)moBandas.get(i);
            loBanda.getBanda().imprimir(loPag, null);
        }
        int lResult = 0;
        if(pageIndex>0) {
            lResult = NO_SUCH_PAGE;
        }else {
            lResult = PAGE_EXISTS;
        }
        return lResult;
    }    
    
    public String getNombre() {
        return msNombre;
    }

    public Object clone() throws CloneNotSupportedException {
        JxmlInforme retValue;
        
        retValue = (JxmlInforme)super.clone();
        
        retValue.crearColecciones();
        
        for(int i = 0 ; i < moBandas.size();i++){
            retValue.getBandas().add(((JxmlBanda)moBandas.get(i)).clone());
        }

        for(int i = 0 ; i < moFuentes.size();i++){
            retValue.getFuentes().add(((JxmlFuente)moFuentes.get(i)).clone());
        }
        
        return retValue;
    }

    public String getInformeFisico() {
        return msInformeFisico;
    }

    public void setInformeFisico(final String msInformeFisico) {
        this.msInformeFisico = msInformeFisico;
    }

    public String getCodInforme() {
        return mlCodInforme;
    }

    public void setCodInforme(final String mlCodInforme) {
        this.mlCodInforme = mlCodInforme;
    }

    public void setNombre(final String msNombre) {
        this.msNombre = msNombre;
    }

    public float getAnchoTotalReal(){
        float ldResult = 0;
        if(mlOrientacion==PageFormat.LANDSCAPE){
            if(getAncho() > getAlto()){
                ldResult= getAncho();
            }else{
                ldResult= getAlto();
            }
        }else{
            ldResult= getAncho();
        }
        return ldResult;
    }
    public float getAltoTotalReal(){
        float ldResult = 0;
        if(mlOrientacion==PageFormat.LANDSCAPE){
            if(getAncho() > getAlto()){
                ldResult= getAlto();
            }else{
                ldResult= getAncho();
            }
        }else{
            ldResult= getAlto();
        }
        return ldResult;
    }

    /**Ancho en cm*/
    public float getAncho() {
        return mdAncho;
    }

    /**Ancho en cm*/
    public void setAncho(final float mdAncho) {
        double old = this.mdAncho;
        this.mdAncho = mdAncho;
        firePropertyChange("ancho", old, mdAncho);
    }
    
    /**Alto en cm*/
    public float getAlto() {
        return mdAlto;
    }

    /**Alto en cm*/
    public void setAlto(final float mdAlto) {
        double old = this.mdAlto;
        this.mdAlto = mdAlto;
        firePropertyChange("alto", old, mdAlto);
    }

    /**orientacion: PageFormat.PORTRAIT, PageFormat.LANDSCAPE*/
    public int getOrientacion() {
        return mlOrientacion;
    }

    /**orientacion: PageFormat.PORTRAIT, PageFormat.LANDSCAPE*/
    public void setOrientacion(final int mlOrientacion) {
        int old = this.mlOrientacion;
        this.mlOrientacion = mlOrientacion;
        firePropertyChange("orientacion", old, mlOrientacion);
    }

    /**Margen superior en CM*/
    public float getMargenSuperior() {
        return mdMargenSuperior;
    }

    /**Margen superior en CM*/
    public void setMargenSuperior(final float mdMargenSuperior) {
        double old = this.mdMargenSuperior;
        this.mdMargenSuperior = mdMargenSuperior;
        firePropertyChange("margenSuperior", old, mdMargenSuperior);
    }

    /**Margen inferior en CM*/
    public float getMargenInferior() {
        return mdMargenInferior;
    }

    /**Margen inferior en CM*/
    public void setMargenInferior(final float mdMargenInferior) {
        double old = this.mdMargenInferior;
        this.mdMargenInferior = mdMargenInferior;
        firePropertyChange("margenInferior", old, mdMargenInferior);
    }

    public float getMargenDerecho() {
        return mdMargenDerecho;
    }

    public void setMargenDerecho(final float mdMargenDerecho) {
        double old = this.mdMargenDerecho;
        this.mdMargenDerecho = mdMargenDerecho;
        firePropertyChange("margenDerecho", old, mdMargenDerecho);
    }

    public float getMargenIzquierdo() {
        return mdMargenIzquierdo;
    }

    public void setMargenIzquierdo(final float mdMargenIzquierdo) {
        double old = this.mdMargenIzquierdo;
        this.mdMargenIzquierdo = mdMargenIzquierdo;
        firePropertyChange("margenIzquierdo", old, mdMargenIzquierdo);
    }
    
    public JxmlInformeDiseno getDiseno(){
        return moDiseno;
    }
    
    public void visitar(IVisitorOperacion poOperador) throws Throwable{
        poOperador.operar(this);
        for(int i = 0 ; i < moFuentes.size();i++){
            JxmlFuente loObj = (JxmlFuente) moFuentes.get(i);
            loObj.visitar(poOperador);
        }
        for(int i = 0 ; i < moBandas.size();i++){
            JxmlBanda loObj = (JxmlBanda) moBandas.get(i);
            loObj.visitar(poOperador);
        }
    }
}
