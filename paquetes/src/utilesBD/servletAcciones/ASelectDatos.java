/*
 * ASelectDatos.java
 *
 * Created on 9 de septiembre de 2004, 8:57
 */

package utilesBD.servletAcciones;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;  
import java.util.zip.*;  
import java.sql.*;
//import javax.sql.*;

import ListDatos.*;
import utiles.*;
import utiles.config.JDatosGeneralesXML;

/**devuelve un select en funcion de la entrada que es un objeto serializado*/
public class ASelectDatos  extends JAccionAbstract  {
    private static final long serialVersionUID = 1L;
    /**constante del fichero de propiedades: Numero maximo de registros a devolver*/
    public static final String PARAMETRO_NumeroMaxRegistros="NumeroMaxRegistros";
    /**constante del fichero de propiedades: tablas en las que no se tiene en cuenta la constante PARAMETRO_NumeroMaxRegistros, conjunto de tablas entre corchetes []*/
    public static final String PARAMETRO_TablasAExcluirMaxRegistros="TablasAExcluirMaxRegistros";
    /**constante del fichero de propiedades: Si los espacios de la derecha de los valores de los campos siempre son eliminados antes de devolverlos al cliente*/
    public static final String PARAMETRO_EliminarEspaciosDerechaSiempre="EliminarEspaciosDerechaSiempre";
    public static final String mcsselectdatos="selectdatos";

    /**Numero maximo de registros a devolver*/
    private int mlNumeroMaxRegistros=0;
    /**tablas en las que no se tiene en cuenta la constante PARAMETRO_NumeroMaxRegistros*/
    private String msTablasAExcluirMaxRegistros="";
    /**Si los espacios de la derecha de los valores de los campos siempre son eliminados antes de devolverlos al cliente*/
    private boolean mbEliminarEspaciosDerechaSiempre=false;


    public String ejecutar(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.ServletContext poServletContext, ListDatos.IServerServidorDatos poServer)  throws Exception{
        JDatosGeneralesXML loDatosXML = (JDatosGeneralesXML) poServletContext.getAttribute(JDatosGeneralesXML.class.getName());
        
        boolean lbEntradaComprimida = AEntradaComprimida.getEntradaComprimida(request, loDatosXML);
        try{
            setNumeroMaxRegistros(Integer.valueOf(loDatosXML.getPropiedad(PARAMETRO_NumeroMaxRegistros)).intValue());
        }catch(Exception e){
            loDatosXML.setPropiedad(PARAMETRO_NumeroMaxRegistros, "0");
            loDatosXML.guardarFichero();
        }
        try{
            setEliminarEspaciosDerechaSiempre(loDatosXML.getPropiedad(PARAMETRO_EliminarEspaciosDerechaSiempre).equals("1"));
        }catch(Exception e){
            loDatosXML.setPropiedad(PARAMETRO_EliminarEspaciosDerechaSiempre, "0");
            loDatosXML.guardarFichero();
        }

        try{
            setTablasAExcluirMaxRegistros(loDatosXML.getPropiedad(PARAMETRO_TablasAExcluirMaxRegistros).toUpperCase());
        }catch(Exception e){
            loDatosXML.setPropiedad(PARAMETRO_TablasAExcluirMaxRegistros, "");
            loDatosXML.guardarFichero();
        }


        procesarEntrada(request, response, lbEntradaComprimida, poServer);
        return null;
    }

    public boolean getNecesitaValidar(Usuario poUsuario) {
        return true;
    }
    /**
     * procesa la entrada
     * @param request peticion  servlet
     * @param response respuesta servlet
     * @param pbEntradaZIP si la entrada viene comprimida
     * @throws ServletException error
     * @throws IOException error
     */
    public void procesarEntrada(HttpServletRequest request, HttpServletResponse response, boolean pbEntradaZIP, ListDatos.IServerServidorDatos poServer) throws Exception {
        ISelectEjecutarSelect loSelect = null;
        boolean lbComprimido = false;
        GZIPOutputStream gzipout = null;
        PrintWriter out = null;
//        long lTime = new java.util.Date().getTime();
//        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(),
//                new JDateEdu().toString() + " entrada ASELECTDATOS-procesar entrada");
        try{
            //recogemos la select
            loSelect = getSelectWeb(request, pbEntradaZIP);
            lbComprimido = loSelect.getComprimido();
            
            //componemos la cabezera
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
//            response.setDateHeader("Expires",0);
            if (lbComprimido){
//                response.setHeader("Content-Encoding", "gzip");
                response.setContentType("application/gzip");
            }else{
                response.setContentType("text/plain; charset=iso-8859-1");        
            }

            //creamos salida (comprimida o no)
            if (lbComprimido){
                gzipout = new GZIPOutputStream(response.getOutputStream());
                out = new PrintWriter(new OutputStreamWriter( gzipout, "ISO-8859-1"));
            }else{
                out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "ISO-8859-1"));
            }
            
            //procesamos la select
            procesar(loSelect, out, lbComprimido, poServer);
         }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            if(out == null){
                if (lbComprimido){
                    gzipout = new GZIPOutputStream(response.getOutputStream());
                    out = new PrintWriter(new OutputStreamWriter( gzipout, "ISO-8859-1"));
                }else{
                    out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "ISO-8859-1"));
                }
            }
            //mandamos el error
            out.println("En Servidor="+e.toString());
         }finally{
            //vacioamos el buffer de salida
            if (out!=null) {
                out.flush();out.close();
            }
            if (gzipout!=null) {
                gzipout.close();
            }
        }
//        long lTime2 = new java.util.Date().getTime();
//        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(),
//                    "(Tiempo milisegundos: "+String.valueOf(lTime2 - lTime) +")"+" salida ASELECTDATOS-procesar entrada");

    }
    /**
     * Devuelve un ISelectEjecutarSelect en funcion de la entrada
     * @return Consulta a ejecutar
     * @param request petion servlet
     * @param pbEntradaZIP si la entrada esta en zip
     * @throws Exception exception
     */
    public ISelectEjecutarSelect getSelectWeb(HttpServletRequest request, boolean pbEntradaZIP) throws Exception {
        ISelectEjecutarSelect loSelect = null;

        //comprobamos si es una select a pelo y comprimida
        String lsSelect = request.getParameter("select");
        String lsComprimido = request.getParameter("comprimido");
        if(lsSelect == null){
            lsSelect = (String)request.getAttribute("select");
        }
        if (lsSelect != null){
            if(lsSelect.compareTo("")!=0){
                JSelect loSelectAux = new JSelect();
                loSelectAux.msSelectAPelo = "select " + lsSelect;
                if(lsComprimido !=null) {
                    if(lsComprimido.compareTo("1")==0){
                        loSelectAux.setComprimido(true);
                    }
                }
                loSelect = loSelectAux;
            }
        }
        if(loSelect ==null){
            //recogemos los datos a actualizar
            ObjectInputStream entrada = null;
            if(pbEntradaZIP){
                entrada = new ObjectInputStream(new GZIPInputStream(request.getInputStream()));
            }else{
                entrada = new ObjectInputStream(request.getInputStream());
            }
            Object loObject = entrada.readObject();
            loSelect = (ISelectEjecutarSelect)loObject;
        }
        return loSelect;
    }
    private String getSelectTexto(ISelectEjecutarSelect loSelect, IServerServidorDatos poServer){
        String lsSQL = "";
        if(JServerServidorDatos.class.isAssignableFrom(poServer.getClass())){
            lsSQL = loSelect.msSQL(((JServerServidorDatos)poServer).getSelect());
        } else if(JServerServidorDatosBD.class.isAssignableFrom(poServer.getClass())){
            lsSQL = loSelect.msSQL(((JServerServidorDatosBD)poServer).getSelect());
        } else {
            lsSQL = loSelect.toString();
        }
        return lsSQL;
    }
    /**
     * procesa la consulta
     * @param loSelect consulta
     * @param out en donde se envia el resultado de la consulta
     * @param lbComprimido si se devuelve comprimido
     * @throws Exception error
     */
    public void procesar(ISelectEjecutarSelect loSelect, PrintWriter out,boolean lbComprimido, ListDatos.IServerServidorDatos poServer) throws Exception {
        //abrimos la select por paramertro y la enviamos a la salida
        Statement stmt = null;
        Connection loConex = null;
        ResultSet rs = null;
        boolean lbSinLimite = false;
        int lSizePagina = -1;
        int lPagina = -1;
        
        if(JSelect.class.isAssignableFrom(loSelect.getClass())){
            JSelect loSelectAux = (JSelect) loSelect;
            loSelect=JServidorDatosAbtrac.aplicarFiltros(poServer.getFiltros(), loSelectAux);
            if(getTablasAExcluirMaxRegistros()!=null && !msTablasAExcluirMaxRegistros.equals("")){
                lbSinLimite = getTablasAExcluirMaxRegistros().indexOf(loSelectAux.getFrom().msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto()).getFrom().toUpperCase())>=0;
            }       
            if(loSelectAux.getMETAPagina_actual()!=null && loSelectAux.getMETAPagina_size()!=null){
                lPagina = loSelectAux.getMETAPagina_actual().getValueAsInteger();
                lSizePagina = loSelectAux.getMETAPagina_size().getValueAsInteger();
            }
        }
        
        String lsSQL = getSelectTexto(loSelect, poServer);
        try{
            long lTime = new java.util.Date().getTime();
            if(JServerServidorDatos.class.isAssignableFrom(poServer.getClass())){
                loConex = ((JServerServidorDatos)poServer).getConec();
                stmt = loConex.createStatement();
                rs = stmt.executeQuery(lsSQL);
            }else if(JServerServidorDatosBD.class.isAssignableFrom(poServer.getClass())){
                loConex = ((JServerServidorDatosBD)poServer).getConec();
                stmt = loConex.createStatement();
                rs = stmt.executeQuery(lsSQL);
            } else{
                throw new Exception("El IServerServidorDatos del servidor TOMCAT no es compatible ni con JServerServidorDatos ni JServerServidorDatosBD");
            }
            long lTime2 = new java.util.Date().getTime();
            
            out.println("");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            StringBuffer lsLinea = new StringBuffer(numberOfColumns * 6);
            int lindex = 0;
            int lCount = 0;
            JDateEdu loDateEdu = new JDateEdu();
            boolean lbContinuar = true;
            boolean lbTroceo = lSizePagina>0 && lPagina>=0;
            int lMin = lSizePagina * lPagina;
            int lMax = lMin + lSizePagina;
            int lFilasReales = 0;
            while(rs.next() && lbContinuar && (!lbTroceo || (lCount<lMax))) {
                if(!lbTroceo || (lCount>=lMin && lCount<lMax) ){
                    lFilasReales++;
                    //se anade la linea a la salida
                    ListDatos.JServerServidorDatosBD.rellenarLinea(lsLinea, rs, numberOfColumns, loDateEdu, rsmd, isEliminarEspaciosDerechaSiempre());

                    //transparentamos los cambios de linea
                    if(indexOf(lsLinea,(char)10)>0){
                        replace2(lsLinea, (char)10, JFilaDatosDefecto.mccTransparentacionCambioLinea10);
                    }
                    if(indexOf(lsLinea,(char)13)>0){
                        replace2(lsLinea, (char)13, JFilaDatosDefecto.mccTransparentacionCambioLinea13);
                    }
                    out.println(lsLinea.toString());
                    //cada 500 registros vaciamos el buffer de salida
                    //si es comprimido se espera hasta el final pq si la otra
                    //aplicacion lo pilla a medio casca
                    if ((lindex>500)&&(!lbComprimido)){
                        out.flush();
                        lindex = 0;
                    }
                    //si el numero de registros es > q el limite se corta, a no ser q sea una tabla
                    //que no tiene limite
                    if(lFilasReales>=getNumeroMaxRegistros() && getNumeroMaxRegistros()>0 && !lbSinLimite){
                        lbContinuar=false;
                    }
                    lindex++;
                }
                lCount ++;
            }
            long lTime3=new java.util.Date().getTime();
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(),
                    "SELECT"+
                    ":Tiempo milisegundos" +
                    ":Despues ejecutar select y antes de recorrela:"+String.valueOf(lTime2 - lTime) +
                    ":Despues recorrer select:" + String.valueOf(lTime3 - lTime2) +
                    ":filas afectadas:"+String.valueOf(lCount)+
                    ":sql:" +lsSQL.replace(':', '.'),
                    (poServer.getParametros() == null ? "" : poServer.getParametros().getTAG()));
         } catch(Throwable e){
            throw new AException("ERROR SQL " + lsSQL ,e);
         } finally {
             //liberamos memoria
            if(rs!=null) {
                try{
                    rs.close();
                    rs=null;
                }catch(Exception e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }
            }
            if(stmt!=null) {
                try{
                    stmt.close();
                    stmt=null;
                }catch(Exception e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }
            }
            if(loConex!=null) {
                try{
//                    loConex.close();
                    loConex=null;
                }catch(Exception e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }
            }
        }
    }
    protected int indexOf(StringBuffer psBuffer, char pcChar){
        for(int i = 0 ; i < psBuffer.length(); i++){
            if(psBuffer.charAt(i)==pcChar){
                return i;
            }
        }
        return 0;
        
    }
    protected void replace2(final StringBuffer psBuffer, final char pcChar, final char pcReplace){
        for(int i = 0 ; i < psBuffer.length(); i++){
            if(psBuffer.charAt(i)==pcChar){
                psBuffer.setCharAt(i, pcReplace);
            }
        }
    }

    public boolean getConexionEdicion() {
        return false;
    }
    
    public boolean getNecesitaConexionBD(){
        return true;
    }

    /**
     * @return the mlNumeroMaxRegistros
     */
    public int getNumeroMaxRegistros() {
        return mlNumeroMaxRegistros;
    }

    /**
     * @param mlNumeroMaxRegistros the mlNumeroMaxRegistros to set
     */
    public void setNumeroMaxRegistros(int mlNumeroMaxRegistros) {
        this.mlNumeroMaxRegistros = mlNumeroMaxRegistros;
    }

    /**
     * @return the msTablasAExcluirMaxRegistros
     */
    public String getTablasAExcluirMaxRegistros() {
        return msTablasAExcluirMaxRegistros;
    }

    /**
     * @param msTablasAExcluirMaxRegistros the msTablasAExcluirMaxRegistros to set
     */
    public void setTablasAExcluirMaxRegistros(String msTablasAExcluirMaxRegistros) {
        this.msTablasAExcluirMaxRegistros = msTablasAExcluirMaxRegistros;
    }

    /**
     * @return the mbEliminarEspaciosDerechaSiempre
     */
    public boolean isEliminarEspaciosDerechaSiempre() {
        return mbEliminarEspaciosDerechaSiempre;
    }

    /**
     * @param mbEliminarEspaciosDerechaSiempre the mbEliminarEspaciosDerechaSiempre to set
     */
    public void setEliminarEspaciosDerechaSiempre(boolean mbEliminarEspaciosDerechaSiempre) {
        this.mbEliminarEspaciosDerechaSiempre = mbEliminarEspaciosDerechaSiempre;
    }
}
