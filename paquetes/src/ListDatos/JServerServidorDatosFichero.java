/*
 * JServidorDatos.java
 *
 * Created on 16 de enero de 2007, 11:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos;


import ListDatos.estructuraBD.*;
//entrada por fichero y web
import java.io.*;
import java.util.HashMap;
import utiles.JCadenas;

public class JServerServidorDatosFichero extends JServidorDatosAbtrac {

    /**url base*/
    private String msURLBase1;

    private char mcCaracterSepacion = JFilaDatosDefecto.mccSeparacion1;
    private boolean mbCabezera = false;
    private HashMap moDelimitadores = new HashMap();
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosFichero() {
        super();
        msURLBase1="";
    }
    
    /**
     * Crea una instancia de servidor de datos con una URL fija
     * @param psURLBase url base
     * @param pcCaracterSeparacion Caracter d eseparacion
     */
    public JServerServidorDatosFichero(final String psRuta, final char pcCaracterSeparacion, final boolean pbCabezera) {
        super();
        msURLBase1=psRuta;
        mcCaracterSepacion = pcCaracterSeparacion;
        mbCabezera = pbCabezera;
        utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),"Inicializado");
    }
    public void setDelimitador(int plTipo, char psDelim){
        moDelimitadores.put(Integer.valueOf(plTipo), String.valueOf(psDelim));
    }
    public ISelectMotor getSelect() {
        return null;
    }
    
    /**
     * establece la url base
     * @param psURLBase url
     */
    public void setURLBase1(final String psURLBase){
      msURLBase1 = psURLBase;
    }
    /**
     * devuelve la url base
     * @return url
     */
    public String getURLBase1(){
        return msURLBase1;
    }
    /**
     * establece el usuario no se usa
     * @param psUsuario usuario
     * @param psPassword password
     * @param psPermisos permisos
     */
    public void setUsuario(final String psUsuario,final String psPassword,final String psPermisos){
      msUsuario=psUsuario;
      msPassWord=psPassword;
      msPermisos=psPermisos;
    }
    /**
     * devuelve el usuario no se usa
     * @return usuario
     */
    public String getUsuario(){
      return msUsuario;
    }

    ////////////////////////////////////////77
    ////Actualizaciones y sincronizacion fisica
    //////////////////////////////////////////
    public JTableDefs getTableDefs() throws Exception {
        return null;
    }
    public IResultado modificarEstructura(final ISelectEjecutarSelect poEstruc) {
        
        return new JResultado(new JFilaDatosDefecto(), "", "Servidor ficheros, No implementado", false, 0);
    }
    public IResultado ejecutarServer(final IServerEjecutar poEjecutar){
        return new JResultado(new JFilaDatosDefecto(), "", "Servidor ficheros, No implementado", false, 0);
    }
    
    protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        //introduccion de campos a la select de sistema
        poSelect.setPassWord(msPassWord);
        poSelect.setPermisos(msPermisos);
        poSelect.setUsuario(msUsuario);
        //recuperacion de datos fisica
        recuperarDatosFichero(v, poSelect ,psTabla);
    }
    public File getTablaFichero(String psTabla){
        if(msURLBase1 == null || msURLBase1.equals("")){
            return new File(psTabla);
        }else{
            return new File(msURLBase1, psTabla);
        }
    }
    public JListDatos recuperarDatosFichero(final JListDatos v,final InputStream miFStream, JListDatosFiltroConj loFiltro, final String psTabla) throws Exception  {
        BufferedReader miDStream=null;
	
        try{
            miDStream = new BufferedReader(new InputStreamReader(miFStream));

            //cuando sea el resultado vacio
            String inputLine= miDStream.readLine();
            //si hay cabezera nos la saltamos
            if(mbCabezera){
                inputLine= miDStream.readLine();
            }
            int lFila = 0;
            boolean lbContinuar = true;
            while (inputLine != null && lbContinuar){
                if(inputLine.length()>0){
                    if(inputLine.charAt(inputLine.length()-1) != mcCaracterSepacion){
                       inputLine += mcCaracterSepacion;
                    }
                }else{
                    inputLine += mcCaracterSepacion;
                }
                //destransparentamos los cambios de linea
                if((inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea10)>0)||
                   (inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea13)>0)){
                    inputLine = inputLine.replace(JFilaDatosDefecto.mccTransparentacionCambioLinea10, (char)10).replace(JFilaDatosDefecto.mccTransparentacionCambioLinea13,(char)13);
                }                
                IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                if(moDelimitadores.isEmpty()){
                    loFila.setArray(JFilaDatosDefecto.moArrayDatos(inputLine, mcCaracterSepacion));
                }else{
                    loFila.setArray(moArrayDatos(v, inputLine, mcCaracterSepacion));
                }
                if (loFiltro!=null || loFiltro.mbCumpleFiltro(loFila)) {
                    v.add(loFila);
                    lFila++;
                }
                if(lFila>getParametros().getNumeroMaximoRegistros() && getParametros().getNumeroMaximoRegistros()>0){
                    lbContinuar=false;
                }                       
                inputLine= miDStream.readLine();
            }

            return v;
        }finally{
            if(miDStream!=null){
                miDStream.close();
            }
        }
    }
    private String[] moArrayDatos(JListDatos v, final String psDatos, final char psSeparacion){
        int lCampos;
        lCampos=v.getFields().size();
        String[] aDatos=new String[lCampos];
      
        int lLen;
        int i;
        int lPosicion;
        boolean lbPrimero=false;
        boolean lbFin=false;
        StringBuilder lsDato;
        lLen=psDatos.length();
        lPosicion=0;
        lsDato=new StringBuilder(25);
        char lsSeparacion = psSeparacion;
        for(i = 0;i<lLen && lPosicion <= lCampos;i++){
            if(lPosicion < v.getFields().size() && lsSeparacion == psSeparacion && lsDato.length()==0){
                String lsValor;
                lsValor = (String) moDelimitadores.get(Integer.valueOf(v.getFields(lPosicion).getTipo()));
                if(JCadenas.isVacio(lsValor)){
                    lsSeparacion = psSeparacion;
                }else{
                    lsSeparacion = lsValor.charAt(0);
                    lbPrimero=true;
                    lbFin=true;
                }
            }else{
                if(!lbFin){
                    lsSeparacion = psSeparacion;
                }
            }
            if(psDatos.charAt(i)==lsSeparacion && !lbPrimero  ){
                aDatos[lPosicion]=lsDato.toString();
                lsDato.setLength(0);
                lPosicion++;
                //nos saltamos separacion normal
                if(lbFin){
                    i++;
                    lbFin=false;
                    lsSeparacion = psSeparacion;
                }
            } else {
                if(lbPrimero){
                    //comprobamos delimitador primero
                    if(psDatos.charAt(i)==lsSeparacion){
                        lbPrimero=false;
                    }
                }else{
                    lsDato.append(psDatos.charAt(i));
                }
            }
        }
        if(lsDato.length()>0 && lPosicion<aDatos.length){
            aDatos[lPosicion]=lsDato.toString();
        }

        return aDatos;
    }    
    private JListDatos recuperarDatosFichero(final JListDatos v,final JSelect poSelect,final String psTabla) throws Exception  {
        InputStream miFStream=null;
	
        try{
            File loFile = getTablaFichero(psTabla);
            if(loFile.exists()){
                miFStream = new FileInputStream(loFile);
            }else{
                 miFStream=getClass().getResourceAsStream(msURLBase1 + psTabla);
            }
            return recuperarDatosFichero(v, miFStream, poSelect.getWhere(), psTabla);
        }finally{
            if(miFStream!=null){
                miFStream.close();
            }
        }
    }

    public void guardar(final JListDatos v) throws Exception{

        //creamos el directorio
        File loFile = new File(msURLBase1);
        loFile.mkdir();
        loFile = new File(loFile.getAbsolutePath(), v.msTabla);
        if(loFile.exists()){
            loFile.delete();
        }
        //creamos el ficheor con los datros de la tabla
        PrintWriter out =
                new PrintWriter(
                    new FileOutputStream(loFile));
        try{
            StringBuilder lasBuffer;
            if(mbCabezera){
                lasBuffer = new StringBuilder(v.getFields().size()*8);
                for(int i = 0 ; i < v.getFields().size(); i++){
                    lasBuffer.append(v.getFields(i).getNombre());
                    lasBuffer.append(mcCaracterSepacion);
                }
                out.println(lasBuffer.toString());
            }
            if (v.moveFirst()){
                do{
                    lasBuffer = new StringBuilder(v.getFields().size()*8);
                    for(int i = 0 ; i < v.getFields().size(); i++){
                        lasBuffer.append(v.getFields(i).toString());
                        lasBuffer.append(mcCaracterSepacion);
                    }
                    //transparentamos los cambios de linea
                    if(indexOf(lasBuffer,(char)10)>0){
                        replace2(lasBuffer, (char)10, JFilaDatosDefecto.mccTransparentacionCambioLinea10);
                    }
                    if(indexOf(lasBuffer,(char)13)>0){
                        replace2(lasBuffer, (char)13, JFilaDatosDefecto.mccTransparentacionCambioLinea13);
                    }                
                    out.println(lasBuffer.toString());
                }while(v.moveNext());
            }
        }finally{
        out.close();
        }
    }
    protected int indexOf(StringBuilder psBuffer, char pcChar){
        for(int i = 0 ; i < psBuffer.length(); i++){
            if(psBuffer.charAt(i)==pcChar){
                return i;
            }
        }
        return 0;
        
    }
    protected void replace2(final StringBuilder psBuffer, final char pcChar, final char pcReplace){
        for(int i = 0 ; i < psBuffer.length(); i++){
            if(psBuffer.charAt(i)==pcChar){
                psBuffer.setCharAt(i, pcReplace);
            }
        }
    }    
    /**
     *
     * cerrar objeto, si es tipo base de datos es muy importante hacerlo
     * @throws Exception el error en caso de error
     */
    public void close() throws Exception{
    }

    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        throw new RuntimeException("Not supported yet.");
    }

}
