/*
 * AGuardarDatosXML.java
 *
 * Created on 5 de enero de 2005, 9:58
 */

package utilesBD.servletAcciones;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.io.*;
import java.util.zip.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**Ejecuta acciones guardar/otros en funcion de la entrada XML*/
public class AGuardarDatosXML extends AGuardarDatos {
    private static final long serialVersionUID = 1L;
    
    /**Devuelve un objeto guardar/otros en funcion de la entrada XML
     * @param request petion servelt
     * @param response respueta servelt
     * @param poServletContext contexto servlet
     * @param poServer servidor datos
     * @throws Exception Exception 
     * @return vista
     */
    public ISelectEjecutarComprimido getUpdateWeb(HttpServletRequest request, boolean pbEntradaZIP) throws Exception {
        
        //recogemos los datos a actualizar
        InputStream entrada = null;
        if(pbEntradaZIP){
            entrada = new GZIPInputStream(request.getInputStream());
        }else{
            entrada = request.getInputStream();
        }
        
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(entrada);
        
        return procesarDocumentoXML(document);
    }
    /**
     * procesa el documento XML
     * @param document documento a parsear
     * @throws Exception exception
     * @return Consulta actualizacion a ejecutar
     */
    public ISelectEjecutarComprimido procesarDocumentoXML(Document document) throws Exception {
        Node loElementosRaiz = document.getChildNodes().item(0);
        Node loElemento;
        boolean lbComprimido = false;
        JActualizarConj loUpdate = new JActualizarConj(null,null,null);
        for(int ini = 0;ini<loElementosRaiz.getChildNodes().getLength();ini++){
            loElemento = loElementosRaiz.getChildNodes().item(ini);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("vueltacomprimida")==0)){
                    lbComprimido = utiles.JConversiones.cBool(msDevolverCadena(loElemento));
                    loUpdate.setComprimido(lbComprimido);
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("accion")==0){
                    loUpdate.add(procesarAccion(loElemento));
                }
            }
        }
        return loUpdate;
    }
    private String msDevolverCadena(Node poElemento){
        String lsElem = "";
        Node loElem = poElemento.getFirstChild();
        if (loElem!=null){
            lsElem = poElemento.getFirstChild().getNodeValue();
            if (lsElem == null) {
                lsElem = "";
            }
        }
        return lsElem;
    }    
    private JActualizar procesarAccion(Node poAccion) throws Exception {
        Node loAtributos=null;
        String lsAccion="";
        Node loElemento;
        JActualizar loObjeto = null;
        for(int ini = 0;ini<poAccion.getChildNodes().getLength();ini++){
            loElemento = poAccion.getChildNodes().item(ini);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if(loElemento.getNodeName().toLowerCase().compareTo("atributos")==0){
                    loAtributos = loElemento;
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("nombre")==0){
                    lsAccion = msDevolverCadena(loElemento);
                }
            }
        }
        if(lsAccion.compareTo("actualizar")==0){
            loObjeto = procesarUpdate(loAtributos);
        }
        return loObjeto;
    }
    private JActualizar procesarUpdate(Node poAtributos) throws Exception {
        JActualizar loAct = new JActualizar(null,null,null);
        Node loElemento;
        for(int ini = 0;ini<poAtributos.getChildNodes().getLength();ini++){
            loElemento = poAtributos.getChildNodes().item(ini);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if(loElemento.getNodeName().toLowerCase().compareTo("tabla")==0){
                    loAct.setTabla(msDevolverCadena(loElemento));
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("tipo")==0){
                    String lsTipo = msDevolverCadena(loElemento);
                    if(lsTipo.compareTo("borrar")==0){
                        loAct.setTipoModif(JListDatos.mclBorrar);
                    }else{
                        if(lsTipo.compareTo("nuevo")==0){
                            loAct.setTipoModif(JListDatos.mclNuevo);
                        }else{
                            loAct.setTipoModif(JListDatos.mclEditar);
                        }
                    }
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("campos")==0){
                    loAct.setFields(moCampos(loElemento));
                }
            }
        }
        
        return loAct;
    }
    private JFieldDefs moCampos(Node poAtributos) throws Exception {
        JFieldDefs loCampos = new JFieldDefs();
        Node loElemento;
        for(int ini = 0;ini<poAtributos.getChildNodes().getLength();ini++){
            loElemento = poAtributos.getChildNodes().item(ini);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if(loElemento.getNodeName().toLowerCase().compareTo("campo")==0){
                    loCampos.addField(moCampo(loElemento));
                }
            }
        }
        return loCampos;
    }
    private JFieldDef moCampo(Node poAtributos) throws Exception {
        int lTipo = JListDatos.mclTipoCadena;
        String lsNombre = null;
        String lsCaption = null;
        String lsValor = null;
        String  lsValorPorDefecto = null;
        boolean lbEsPrincipal=false;
        int lTamano = -100;
        int  lActualizarValorSiNulo = JFieldDef.mclActualizarNada;

        Node loElemento;
        for(int ini = 0;ini<poAtributos.getChildNodes().getLength();ini++){
            loElemento = poAtributos.getChildNodes().item(ini);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if(loElemento.getNodeName().toLowerCase().compareTo("nombre")==0){
                    lsNombre = msDevolverCadena(loElemento);
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("tipo")==0){
                    String lsAux = msDevolverCadena(loElemento);
                    if(lsAux.compareTo("boolean")==0){
                        lTipo = JListDatos.mclTipoBoolean;
                    }
                    if(lsAux.compareTo("cadena")==0){
                        lTipo = JListDatos.mclTipoCadena;
                    }
                    if(lsAux.compareTo("fecha")==0){
                        lTipo = JListDatos.mclTipoFecha;
                    }
                    if(lsAux.compareTo("numero")==0){
                        lTipo = JListDatos.mclTipoNumero;
                    }
                    if(lsAux.compareTo("numerodoble")==0){
                        lTipo = JListDatos.mclTipoNumeroDoble;
                    }
                    if(lsAux.compareTo("moneda3D")==0){
                        lTipo = JListDatos.mclTipoMoneda3Decimales;
                    }
                    if(lsAux.compareTo("moneda")==0){
                        lTipo = JListDatos.mclTipoMoneda;
                    }
                    if(lsAux.compareTo("porciento3D")==0){
                        lTipo = JListDatos.mclTipoPorcentual3Decimales;
                    }
                    if(lsAux.compareTo("porciento")==0){
                        lTipo = JListDatos.mclTipoPorcentual;
                    }
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("tamano")==0){
                    String lsAux = msDevolverCadena(loElemento);
                    if(utiles.JConversiones.isNumeric(lsAux)){
                        lTamano = (int)utiles.JConversiones.cdbl(lsAux);
                    }
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("esprincipal")==0){
                    String lsAux = msDevolverCadena(loElemento);
                    if(utiles.JConversiones.isBool(lsAux)){
                        lbEsPrincipal = utiles.JConversiones.cBool(lsAux);
                    }
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("actualizarvalorsinulo")==0){
                    String lsAux = msDevolverCadena(loElemento);
                    if(utiles.JConversiones.isNumeric(lsAux)){
                        lActualizarValorSiNulo = (int)utiles.JConversiones.cdbl(lsAux);
                    }
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("valorpordefecto")==0){
                    lsValorPorDefecto = msDevolverCadena(loElemento);
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("caption")==0){
                    lsCaption = msDevolverCadena(loElemento);
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("valor")==0){
                    lsValor = msDevolverCadena(loElemento);
                }
            }
        }
        
        JFieldDef loCampo = new JFieldDef(lTipo,lsNombre,lsCaption,lbEsPrincipal,lTamano);
        loCampo.setValue(lsValor);
        loCampo.setValorPorDefecto(lsValorPorDefecto);
        loCampo.setActualizarValorSiNulo(lActualizarValorSiNulo);
        return loCampo;
    }
    /**Devuelve el resultado a la salida*/
    protected void devolverResultado(HttpServletResponse response, boolean pbEsComprimido, IResultado poResultado)  throws ServletException, IOException {
        response.setContentType("text/plain");
        GZIPOutputStream gzipout = null;
        try{
            if(pbEsComprimido){
                gzipout = new GZIPOutputStream(response.getOutputStream());
                gzipout.write(poResultado.getXML().getBytes());
                gzipout.flush(); 
            }else{
                response.getOutputStream().write(poResultado.getXML().getBytes());
            }
        }finally{
            if(gzipout != null) {
                gzipout.close(); 
            }
        }
    }

    public boolean getNecesitaConexionBD(){
        return true;
    }
    
}
