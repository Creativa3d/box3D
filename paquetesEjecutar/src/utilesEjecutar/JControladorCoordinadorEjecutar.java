

package utilesEjecutar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Properties;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JDepuracionLOG4J;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.ejecutar.JEjecucionesControlador;
import utilesEjecutar.fuentes.JFuentesControlador;
import utilesEjecutar.fuentes.archivos.IArchivo;


public class JControladorCoordinadorEjecutar {
    public static final char mcsFileSeparator = System.getProperty("file.separator").charAt(0);

    private static final String mcsCopiaseguridad = "copiaseguridad";
    private static final String mcsTmp = "tmp";
    private static final String mcsLogo = "logo";
    private static final String mcsSalirAlfinalizar = "SalirAlfinalizar";
    private static final String mcsFechaEnFichero = "fechaEnFichero";
    private static final String mcsMostrarErrores = "MostrarErrores";
    private static final String mcsLog="log";

    private boolean mbCopiaSeguridad = true;
    private String msTmp = "tmp";

    private JFuentesControlador moFuentes;
    private JEjecucionesControlador moEjecuciones;
    private String msTmpCopiaSeguridad;
    private String msTmpFuentes;

    private boolean mbCancelado = false;
    private String msLogo;
    private long mlTotal=0;
    private String msTexto;
    private long mlCompletado=0;
    private boolean mbFin=false;
    private boolean mbSalirAlFinalizar = true;
    private boolean mbFechaEnFichero = false;
    private boolean mbMostrarErrores = true;
    private JDateEdu moFechaUltActualizacion = null;
    private IAbstractFactoryEjecutar moFabricaClases;

    private IListaElementos moErrores = new JListaElementos();
    private JAtributos moVariables = new JAtributos();


    public JControladorCoordinadorEjecutar(){
        moFuentes = new JFuentesControlador(this);
        moEjecuciones = new JEjecucionesControlador(this);
        inicializarVariables();
    }

    public void inicializarVariables(){
        moVariables.clear();
        //añadimos las variables del sistema
        Properties loProp = System.getProperties();
        Enumeration loEnum = loProp.keys();
        for(; loEnum.hasMoreElements(); ){
            String lsKey = loEnum.nextElement().toString();
            String lsValor = loProp.getProperty(lsKey);
            addVariable(lsKey, lsValor);
        }

    }

    /**
     * Añade un error
     * Visualiza en consola
     * Da el texto para verlo en pantalla
     * Añade a la lista de errores
     */
    public void addError(String psClase, Throwable e) {
        addError(psClase, e, false);
    }
    /**
     * Añade un error
     * Visualiza en consola
     * Da el texto para verlo en pantalla
     * Añade a la lista de errores
     */
    public void addError(String psClase, Throwable e, boolean pbTrazaCompleta) {
        if(pbTrazaCompleta){
            JDepuracion.anadirTexto(psClase, e);
        } else {
            JDepuracion.anadirTexto(psClase, e.toString());
        }
        moErrores.add(e);
        addTextoGUI(e.toString());
    }

    public void addTotal(long lTotal) {
        mlTotal += lTotal;
    }

    /**
     * Devuelve la fabrica de creacion de fuentes y ejecuciones
     */
    public IAbstractFactoryEjecutar getAbstractFactory(){
        return moFabricaClases;
    }

    /**Devuelve la lista de errores*/
    public IListaElementos getErrores(){
        return moErrores;
    }

    public boolean ejecutarCompleto(String psConfig, IAbstractFactoryEjecutar poFactoria) {
        return ejecutarCompleto(psConfig, poFactoria, false);
    }
    private void inicializar(){
        moFuentes.getFuentes().clear();
        moFuentes.inicializar();
        moEjecuciones.getEjecuciones().clear();
        moEjecuciones.inicializar();
        msTmpCopiaSeguridad=null;
        msTmpFuentes=null;
        mlTotal=0;
        mlCompletado=0;
        moFechaUltActualizacion = null;
        mbFin=false;
    }
    /** Ejecuta todos los pasos de la actualizacion-lectura de fuentes-copia de archivos-ejecuciones*/
    public boolean ejecutarCompleto(String psConfig, IAbstractFactoryEjecutar poFactoria, boolean pbFicticio) {
        boolean lbActualizadoAlgo = false;
        moFabricaClases=poFactoria;
        inicializar();
        try{
            //creamos el lector
            JLectorXMLEjecutar loLector;
            loLector = new JLectorXMLEjecutar(psConfig);
            try {
                //leemos el archivo de configuracion
                loLector.leer(this);
                //procesamos las fuentes
                procesarPrevio();
            } catch (Throwable ex) {
                addError(getClass().getName(), ex, true);
            }
            try {
                //ejecutamos
                if(pbFicticio){
                    JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Antes Comienzo ejecutar ficticio");
                    lbActualizadoAlgo = ejecutarFicticio();
                }else{
                    JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Antes Comienzo ejecutar");
                    lbActualizadoAlgo = ejecutar();
                }
            } catch (Throwable ex) {
                addError(getClass().getName(), ex, true);
            }
        }finally{
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Antes finalizar");
            if(mbFechaEnFichero && !isCancelado() && !pbFicticio && moErrores.size()==0){
                try {
                    guardarFechaEnFichero();
                } catch (Throwable ex) {
                    addError(getClass().getName(), ex);
                }
            }
            finalizar();
            mbFin=true;
        }
        return lbActualizadoAlgo;
    }
    /**Devuelve el controlador de las fuentes*/
    public JFuentesControlador getFuentesControlador(){
        return moFuentes;
    }
    /**Devuelve el controlador de las ejecuciones*/
    public JEjecucionesControlador getEjecucionesControlador(){
        return moEjecuciones;
    }

    /**Devuelve el logo q debe tener la pantalla de proceso*/
    public String getLogo() {
        return msLogo;
    }

    /**Devuelve si se tienen q mostrar los errores despues de actualizar*/
    public boolean isMostrarErrores() {
        return mbMostrarErrores;
    }

    /**Establece los parametros xml*/
    public void setParametros(JAtributos poLista) {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsTmp)){
                msTmp = getDirLocalconSeparadorCorrecto(loAtrib.getValor());
                msTmpCopiaSeguridad = new File(msTmp, "copiaSeguridad" + new JDateEdu().msFormatear("yyyyMMddHHmmss")).getAbsolutePath();
                msTmpFuentes = new File(msTmp, "fuentes" + new JDateEdu().msFormatear("yyyyMMddHHmmss")).getAbsolutePath();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsCopiaseguridad)){
                mbCopiaSeguridad = loAtrib.getValor().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsLogo)){
                msLogo = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsSalirAlfinalizar)){
                mbSalirAlFinalizar = loAtrib.getValor().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsFechaEnFichero)){
                mbFechaEnFichero = loAtrib.getValor().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsMostrarErrores)){
                mbMostrarErrores = loAtrib.getValor().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsLog)){
                if(!loAtrib.getValor().equals("")){
                    JDepuracion.moIMPL = new JDepuracionLOG4J(loAtrib.getValor());
                }
            }
        }
    }

    /**
     * @return si se hace copia de seguridad antes de copiar
     */
    public boolean isCopiaSeguridad() {
        return mbCopiaSeguridad;
    }

    /**
     * @return el directorio temporal de copia de seguridad del programa local
     */
    public String getTmpCopiaSeguridad() {
        return msTmpCopiaSeguridad;
    }
    /**
     * @return el directorio temporal de los fuentes, por si tienen q copiar algo
     */
    public String getTmpFuentes() {
        return msTmpFuentes;
    }

    /**Ejecuta el proceso previo a la actualizacion de archivos, esto sirve, entre otras cosas, para saber q tamaño tiene la descarga*/
    public void procesarPrevio() throws Throwable {
        msTexto = "Leyendo fuentes";
        mlTotal = 0;
        if(mbFechaEnFichero){
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Antes leer fecha fichero");

            try{
                leerFechaEnFichero();
            }catch(Throwable e){
                JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), e.toString());
            }
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Despues leer fecha fichero");
        }
//        for(int i = 0 ; i < moFuentes.getFuentes().size() && !isCancelado(); i++){
//            try{
//                IFuente loFuente = (IFuente) moFuentes.getFuentes().get(i);
//                msTexto = "Leyendo fuente " + loFuente.getNombre();
//                IListaElementos loLista = loFuente.getLista();
//                for(int ii = 0 ;ii < loLista.size() && !isCancelado(); ii++){
//                    IArchivo loArchivo = (IArchivo) loLista.get(ii);
//                    mlTotal+=loArchivo.getTamano();
//                }
//            }catch(Throwable e){
//                addError(getClass().getName(), e);
//            }
//        }
    }

    public JDateEdu getFechaMaximaArchivosFuentes() throws Throwable {
        return moFuentes.getFechaMaximaArchivosFuentes();
    }

    public boolean ejecutarFicticio() throws Throwable {
        return moEjecuciones.ejecutarFicticio();
    }
    /**Ejecuta la lista de IEjecutarInstruccion*/
    public boolean ejecutar() throws Throwable {
        return moEjecuciones.ejecutar();
    }
    /**Finaliza el proceso, desconecta fuentes, borra tmp, ...*/
    public void finalizar() {
        moFuentes.finalizar();
        moEjecuciones.finalizar();
        //borramos el temporal de las fuentes
        File loFileTmpfuente = new File(msTmpFuentes);
        if(loFileTmpfuente.exists()){
            borrarDir(loFileTmpfuente);
        }


//no borramos el de copiaseguridad
//        File loFileTmpCP = new File(msTmpCopiaSeguridad);
//        if(loFileTmpCP.exists()){
//            borrarDir(loFileTmpCP);
//        }
    }
    private void borrarDir(File poDir){
        String[] loFiles = poDir.list();
        for(int i = 0 ; i < loFiles.length; i++){
            File loFile = new File(poDir.getAbsolutePath(), loFiles[i]);
            if(loFile.isDirectory()){
                borrarDir(loFile);
            }else{
                loFile.delete();
            }

        }
        poDir.delete();

    }

    /**Devuelve el total de kbytes a descargar*/
    public int getTotal() {
        return (int)(mlTotal / 1024);
    }
    /**Devuelve los kbytes comp`letados*/
    public int getCompletado(){
        return (int)(mlCompletado / 1024);
    }
    /**Devuelve el texto actual q debe poner el cargador*/
    public String getTextoGUI(){
        return msTexto;
    }

    /**Establece el texto actual q debe poner el cargador*/
    public void addTextoGUI(String psTexto){
        msTexto = psTexto;
    }

    /**Añade lo bytes completados*/
    public void addCompletadoGUI(long plCompl){
        mlCompletado+=plCompl;
    }

    /**Cancela el proceso*/
    public synchronized void cancelar() {
        mbCancelado = true;
    }

    /**Devuelve si se ha Cancelado el proceso*/
    public synchronized boolean isCancelado() {
        return mbCancelado;
    }

    /**Devuelve si ha terminado de actualizar/ejecutar*/
    public boolean isFin(){
        return mbFin;
    }

    /**
     * @return Si cuando acaba la actualizacion se sale de la jvm actual
     * Cuando es un programa java NO debe salir
     */
    public boolean isSalirAlFinalizar() {
        return mbSalirAlFinalizar;
    }

    /**
     * @return La actualizacion es por fecha en fichero,
     * es decir, q cuando lee las fuentes lee la ultima
     * fecha de actualizacion de las fuentes y esta es
     * guardada en el fichero para posteriores comprobaciones
     * de las fuentes
     */
    public boolean isFechaEnFichero() {
        return mbFechaEnFichero;
    }

    private void guardarFechaEnFichero() throws Throwable {
        File loDir = new File(msTmp);
        loDir.mkdirs();
        FileOutputStream loOut = new FileOutputStream(
                new File(msTmp, "fechaActualizacion.txt")
                );
        try{
            char[] lachar = getFechaMaximaArchivosFuentes().toString().toCharArray();
            for(int i = 0 ; i < lachar.length; i++){
                loOut.write((byte)lachar[i]);
            }
        }finally{
            loOut.close();
        }

    }

    private void leerFechaEnFichero() throws Throwable  {
        FileInputStream loIn = null;
        try{
            loIn = new FileInputStream(
                new File(msTmp, "fechaActualizacion.txt")
                );
            byte[] lab = new byte[100];
            int lLen = loIn.read(lab);
            String lsFecha = new String(lab, 0, lLen);
            moFechaUltActualizacion = new JDateEdu(lsFecha);
        }finally{
            if(loIn!=null){
                loIn.close();
            }
        }

    }

    /**Compara un archivo fuente con un archivo destino y devuelve si se tiene q actualizar*/
    public boolean isCompararCopiarSN(IArchivo poArchDestino, IArchivo poArchFuente) throws Throwable{
        boolean lbResult = false;

        //si se compara por fecha guardada en fichero
        if(mbFechaEnFichero ){
            if(moFechaUltActualizacion == null || moFechaUltActualizacion.compareTo(poArchFuente.getFechaModificacion())<0){
                lbResult=true;
                String lsFecha = "";
                if(moFechaUltActualizacion != null ){
                    lsFecha = moFechaUltActualizacion.toString();
                }
                JDepuracion.anadirTexto(getClass().getName(), poArchFuente.getNombre() +  "->Fecha ult. actualizacion " + lsFecha + " Fecha archivo fuente " + poArchFuente.getFechaModificacion().toString());
            }
        }else{
            //si la fecha es menor
            //si el tamaño es distinto Y el tamaño de la fuente es > 0 (es posible q la fuente no pueda leer el tamaño)
            if(poArchDestino.getFechaModificacion().compareTo(poArchFuente.getFechaModificacion())<0
//los tamaños del servidor y del cliente siempre difieren                    
//                    ||
//               ((Math.max(poArchDestino.getTamano(), poArchFuente.getTamano())
//                    - Math.min(poArchDestino.getTamano(), poArchFuente.getTamano()))>512 &&
//                poArchFuente.getTamano()>0 && poArchDestino.getTamano() >0
//                )
              ){
                lbResult=true;
                JDepuracion.anadirTexto(getClass().getName()
                        , poArchFuente.getNombre() +  "->Arch. destino " 
                                + "Fecha:" + poArchDestino.getFechaModificacion().toString() 
                                + "Tamaño:" + String.valueOf(poArchDestino.getTamano()) 
                                + "  Archivo fuente " 
                                + "Fecha:" + poArchFuente.getFechaModificacion().toString()
                                + "Tamaño:" + String.valueOf(poArchFuente.getTamano())
                        );
            }
        }


        return lbResult;
    }

    public void setVariableYReemp(String psNombre, String psValor){
        JAtributo loAtrib = moVariables.getAtributo(psNombre);
        while(loAtrib!=null){
            moVariables.remove(loAtrib);
            loAtrib = moVariables.getAtributo(psNombre);
        }
        moVariables.add(new JAtributo(psNombre, psValor));

    }
    public void addVariable(String psNombre, String psValor){
        JAtributo loAtrib = moVariables.getAtributo(psNombre);
        if(loAtrib==null){
            moVariables.add(new JAtributo(psNombre, psValor));
        }
    }
    
    public String getVariable(String psNombre){
        return moVariables.getAtributo(psNombre).getValor();
    }
    public JAtributos getVariables(){
        return moVariables;
    }

    public static String getDirLocalconSeparadorCorrecto(String psPath) {
        String lsResult = "";
        if(psPath!=null){
            lsResult = psPath.replace('/', mcsFileSeparator).replace('\\', mcsFileSeparator);
        }
        return lsResult;
    }
    public static void crearDirPadres(File loFileTMPFuente) {
        String lsPadre = loFileTMPFuente.getParent();
        File loFilePadre = new File(lsPadre);
        loFilePadre.mkdirs();
        loFilePadre.mkdir();
        if(!loFilePadre.isDirectory()){
            JDepuracion.anadirTexto(
                    JDepuracion.mclWARNING,
                    JControladorCoordinadorEjecutar.class.getName(),
                    lsPadre + " no se ha creado el directorio");
        }


    }
    public static String getRutaRelativaFichero(String psRutaCompleta) {
        String lsNombre = getNombreFichero(psRutaCompleta);
        String lsResult = psRutaCompleta.substring(
                0,
                psRutaCompleta.length() - lsNombre.length());

        if(lsResult.length()>0){
            char lc = lsResult.charAt(lsResult.length()-1);
            if(lc == '/' || lc == '\\'){
                lsResult = lsResult.substring(0, lsResult.length()-1);
            }
        }
        return lsResult;
    }
    public static String getNombreFichero(String psRutaCompleta) {
        StringBuffer lasBuffer = new StringBuffer();
        int lIndex = psRutaCompleta.length()-1;
        char lc = psRutaCompleta.charAt(lIndex);
        while(lIndex>=0 && lc != '/' && lc != '\\'){
            lasBuffer.insert(0, lc);
            lIndex--;
            if(lIndex>=0){
                lc = psRutaCompleta.charAt(lIndex);
            }
        }
        return lasBuffer.toString();
    }

    public void setFin(boolean b) {
        mbFin = b;
    }

}
