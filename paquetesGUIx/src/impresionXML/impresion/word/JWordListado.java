/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import ListDatos.JListDatos;
import org.jawin.win32.Ole32;

import utiles.JConversiones;
import utiles.JDepuracion;
import utiles.JFormat;


import PClaseWord2._clsWordClase;
import PClaseWord2.WdDireccion;

import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JListaElementos;
import utilesGUI.procesar.JProcesoAccionParam;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;

public class JWordListado extends JProcesoAccionAbstracX {
    protected static final int wdCharacter=1;
    protected static final int wdLine=5;
    protected static final int wdCell = 12;
    protected static final int wdAlignParagraphLeft=0;
    protected static final int wdAlignParagraphCenter=1;
    protected static final int wdAlignParagraphRight=2;
    protected static final int wdAlignParagraphJustify=3;
    
    protected static final char mcsAntesPalabra = (char)171;
    protected static final char mcsDespuesPalabra = (char)187;

    protected static final String mcsFormatoNumero = "###,###,##0.00";

    protected IListaElementos moCollec;

    public JListDatos moListado;

    protected _clsWordClase goWord;

    //BARRA PROCESO
    protected String msMensajePantalla="Imprimiendo Listado WORD";

    //PARAMETROS
    protected String msCaminoWord;
    private final String msRuta;

    /** Creates a new instance of JImpWordFicha */
    public JWordListado(String psRuta) {
        msRuta=psRuta;
        moCollec = new JListaElementos();
    }

    protected  void inicializar(){
        mlRegistroActual = 0;
        mbFin=false;
        mbCancelado=false;
    }
    public void setWord(String psCaminoWord) {
        msCaminoWord = psCaminoWord;
    }
    public void setListado(JListDatos poList){
        moListado = poList;
    }
    public void addParametro(String psNombre, JDateEdu poDate){
        addParametro(psNombre, poDate.toString(), JListDatos.mclTipoFecha);
    }
    public void addParametro(String psNombre, double pdValor ){
        addParametro(psNombre, JFormat.msFormatearDouble(pdValor, "###,###,###,##0.00"), JListDatos.mclTipoNumeroDoble);
    }
    public void addParametro(String psNombre, String psValor){
        addParametro(psNombre, psValor, JListDatos.mclTipoCadena);
    }
    public void addParametro(String psNombre, String psValor, int plTipo){
        moCollec.add(new JWordParametro(psNombre, psValor, plTipo));
    }
    public IListaElementos getCollec(){
        return moCollec;
    }

    public _clsWordClase getNuevoWord() throws Exception {
        _clsWordClase loWord;
        loWord = new _clsWordClase("PClaseWord2.clsWordClase");
        loWord.Inicializar(true);
        loWord.setmbSalirAlTerminar(false);
        return loWord;
    }
    protected void crearWord() throws Exception {
        goWord = getNuevoWord();
        goWord.setmbSalirAlTerminar(false);
        goWord.MostrarWord();
    }
    protected void copiar(String psFile, String psFileDestino) throws Exception {
        copiar(new File(psFile), new File(psFileDestino));
    }
    protected  void copiar(File poFile, File poFileDestino) throws Exception {
        if(!poFileDestino.getParentFile().exists()){
            poFileDestino.getParentFile().mkdirs();
        }
        //creamos los objetos de entrada y salida
        FileInputStream loFOrigen = new FileInputStream(poFile);
        FileOutputStream loFDestino = new FileOutputStream(poFileDestino);

        //pasamos byte a byte de un fichero al otro
        byte[] b1 = new byte[1024];
        int lLen = -1;
        while((lLen=loFOrigen.read(b1))!=-1){
            loFDestino.write(b1, 0, lLen);
        }

        //cerramos los objetos de entrada salida
        loFOrigen.close();
        loFDestino.close();
    }

    public void procesar() throws Throwable {
        inicializar();
        try{
            Ole32.CoInitialize();
            File loFile = new File(msRuta );
            try{
                loFile.delete();
            }catch(Exception e){}
            try{
                loFile.mkdirs();
            }catch(Exception e){}

            String lsCaminoWordAux = msRuta + "/tmp" + String.valueOf(Math.random() * 100 + mlRegistroActual) + ".doc";
            //copiamos el archivo a un temporal
            copiar(msCaminoWord, lsCaminoWordAux);

            msMensajePantalla = "Generador documentos word";
            msMensajePantalla = "Abriendo word";
            mlRegistroActual=1;
            crearWord();
            msMensajePantalla = "Creando página en word";
            goWord.inicializarVariables();
            goWord.Archivo_AbrirDocumento(lsCaminoWordAux);
            goWord.MostrarWord();

            mlRegistroActual=2;
            msMensajePantalla = "mandando parámetros";
            for(int i = 0 ; i < moCollec.size() && !mbCancelado; i++){
                JWordParametro loParam = (JWordParametro) moCollec.get(i);

                buscarYReemplazar(
                        loParam.getNombre(),
                        loParam.getValor(),
                        loParam.getTipo()
                        );
            }
            if(!mbCancelado){
                rellenarListado();
            }


        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            throw e;
        }finally{
            mbFin=true;
//            if(loWord!=null){
//                loWord.Archivo_Salir(false, "");
//            }
            if(goWord!=null){
                goWord.MostrarWord();
            }
            try{
                Ole32.CoUninitialize();
            }catch(Exception e){

            }

        }
    }
    public void rellenarListado() throws Exception{
        rellenarListado(moListado, goWord);
    }
    public static void rellenarListado(JListDatos poListado, _clsWordClase poWord) throws Exception{
        //insertamos las filas
        boolean lbInsertadoFilas = false;
        for(int i = 0 ; i < poListado.getFields().size() && !lbInsertadoFilas; i++){
            if(!lbInsertadoFilas){
                if(poWord.Edicion_Buscar(mcsAntesPalabra + poListado.getFields(i).getNombre() + mcsDespuesPalabra) == -1){
                    poWord.Tabla_InsertarFilas(poListado.size()-1);
                    lbInsertadoFilas=true;
                }
            }
        }
        //una vez insertadas las filas en la tabla las rellenamos
        for(int i = 0 ; i < poListado.getFields().size() && lbInsertadoFilas; i++){
            if(poWord.Edicion_Buscar(mcsAntesPalabra + poListado.getFields(i).getNombre() + mcsDespuesPalabra) == -1){
                poWord.Edicion_Suprimir(1, wdCharacter);
                poWord.Insertar("");
                poWord.Moverse(WdDireccion.WdArriba, wdLine, poListado.size()-1);
                if(poListado.moveFirst()){
                    do{
                        String lsValor=poListado.getFields(i).getString();
                        switch(poListado.getFields(i).getTipo()){
                            case JListDatos.mclTipoBoolean:
                                if( lsValor.compareTo(JListDatos.mcsTrue) == 0){
                                    lsValor = "Sí";
                                }else{
                                    lsValor = "No";
                                }
                                break;
                            case JListDatos.mclTipoFecha:
                                lsValor = lsValor.replaceAll("00:00:00", "");
                                break;
                            case JListDatos.mclTipoNumeroDoble:
                                if(JConversiones.isNumeric(lsValor)){
                                    lsValor = JFormat.msFormatearDouble(Double.valueOf(lsValor), mcsFormatoNumero);
                                }
                                break;
                            default:
                        }

                        poWord.Insertar(lsValor);
                        poWord.Moverse(WdDireccion.WdAbajo, wdLine, 1);
                    }while(poListado.moveNext());
                }
            }
        }
    }
//    private boolean buscarYReemplazar(final String psNombre, final JFieldDef poCampo) throws Exception {
//        boolean lbResult;
//        if(poCampo==null){
//            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,getClass().getName(),"Campo NO existe: " + poCampo.getNombre());
//            lbResult = false;
//        }else{
//            if(poCampo.getTipo() == JListDatos.mclTipoFecha && !poCampo.isVacio()){
//                lbResult = buscarYReemplazar(psNombre, poCampo.getDateEdu().toString(), poCampo.getTipo(), "");
//            }else{
//                lbResult = buscarYReemplazar(psNombre, poCampo.getString(), poCampo.getTipo(), "");
//            }
//        }
//        return lbResult;
//    }
    protected  boolean buscarYReemplazar(final String psNombre, final String psValor , final int plTipo) throws Exception {
        return buscarYReemplazar(psNombre, psValor , plTipo, "");
    }
    protected  boolean buscarYReemplazar(final String psNombre, final String psValor , final int plTipo, final String psFormato) throws Exception {
        String lsValor=psValor ;
        boolean lbResul = false;
        if( psFormato.compareTo("")==0){
            switch(plTipo){
                case JListDatos.mclTipoBoolean:
                    if( lsValor.compareTo(JListDatos.mcsTrue) == 0){
                        lsValor = "Sí";
                    }else{
                        lsValor = "No";
                    }
                    break;
                case JListDatos.mclTipoFecha:
                    lsValor = lsValor.replaceAll("00:00:00", "");
                    break;
                case JListDatos.mclTipoNumeroDoble:
                case JListDatos.mclTipoNumero:
                    if(JConversiones.isNumeric(lsValor)){
                        lsValor = JFormat.msFormatearDouble(Double.valueOf(lsValor), mcsFormatoNumero);
                    }
                    break;
                default:
            }
        }else{
            if(JConversiones.isNumeric(lsValor)){
                lsValor = JFormat.msFormatearDouble(Double.valueOf(lsValor), psFormato);
            }
        }
//        lbResul = poWord.setVariable(psNombre, lsValor);
//        lbResul = goWord.Edicion_Reemplazar(
//                mcsAntesPalabra + psNombre + mcsDespuesPalabra,
//                lsValor,
//                true,
//                false)==-1;
        while (goWord.Edicion_Buscar(mcsAntesPalabra + psNombre + mcsDespuesPalabra) == -1){
            goWord.Edicion_Suprimir(1, wdCharacter);
            goWord.Insertar(lsValor);
            lbResul = true;
        }
        return lbResul;
    }
    public JProcesoAccionParam getParametros() {
        return moParametros;
    }

    public String getTitulo() {
        return "Listados WORD";
    }

    public int getNumeroRegistros() {
        return moListado.getFields().size()+3;
    }


    public int getNumeroRegistro() {
        return mlRegistroActual;
    }

    public String getTituloRegistroActual() {
        return msMensajePantalla;
    }

    public boolean isFin() {
        return mbFin;
    }

    public void setCancelado(boolean pbCancelado) {
        mbCancelado = pbCancelado;
    }

    public void mostrarMensaje(String psMensaje) {

    }

    public void mostrarError(Throwable e) {
        utilesGUIx.msgbox.JMsgBox.mensajeError(null, e);
    }

    public void finalizar() {
    }



}
