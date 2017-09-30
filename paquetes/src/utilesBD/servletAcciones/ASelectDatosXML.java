/*
 * ASelectDatosXML.java
 *
 * Created on 5 de enero de 2005, 9:39
 */

package utilesBD.servletAcciones;

import java.io.*;

import javax.servlet.http.*;  
import java.util.zip.*;  
//import javax.sql.*;

import javax.xml.parsers.DocumentBuilderFactory;  
 
import javax.xml.parsers.DocumentBuilder;  

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import ListDatos.*;
import utiles.*;

/**devuelve una select recogiendo los datos en formato XML*/
public class ASelectDatosXML extends ASelectDatos {
    private static final long serialVersionUID = 1L;
    

    /**Devuelve un ISelectEjecutarSelect en funcion de la entrada*/
    public ISelectEjecutarSelect getSelectWeb(HttpServletRequest request, boolean pbEntradaZIP) throws Exception {

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
     * procesa el documento xml
     * @param document documento
     * @return interfaz Select a ejecutar
     */
    public ISelectEjecutarSelect procesarDocumentoXML(Document document){
        Node loElementosRaiz = document.getChildNodes().item(0);
        Node loElemento;
        boolean lbComprimido = false;
        JSelect loSelect = null;
        for(int ini = 0;ini<loElementosRaiz.getChildNodes().getLength();ini++){
            loElemento = loElementosRaiz.getChildNodes().item(ini);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("vueltacomprimida")==0)){
                    lbComprimido = utiles.JConversiones.cBool(msDevolverCadena(loElemento));
                }
                if(loElemento.getNodeName().toLowerCase().compareTo("accion")==0){
                    loSelect = (JSelect)procesarAccion(loElemento);
                    loSelect.setComprimido(lbComprimido);
                }
            }
        }
        return loSelect;
    }
    
    private Object procesarAccion(Node poAccion){
        Node loAtributos=null;
        String lsAccion="";
        Node loElemento;
        Object loObjeto = null;
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
        if(lsAccion.compareTo("select")==0){
            loObjeto = procesarSelect(loAtributos);
        }
        return loObjeto;
    }
    private JSelect procesarSelect(Node poAtributos){
        JSelect loSelect = new JSelect();
        Node loElemento;
        for(int i = 0;i<poAtributos.getChildNodes().getLength();i++){
            //relacion individual
            loElemento = poAtributos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("selectcampos")==0)){
                    procesarCampos(loElemento, loSelect, 0);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("selectfrom")==0)){
                    procesarFrom(loElemento, loSelect);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("selectwhere")==0)){
                    procesarWhere(loElemento, loSelect);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("selectorder")==0)){
                    procesarOrder(loElemento, loSelect);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("selectgroup")==0)){
                    procesarGroup(loElemento, loSelect);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("selecthaving")==0)){
                    procesarHaving(loElemento, loSelect);
                }
            }
        }
        return loSelect;
    }
    private void procesarCampos(Node poElementos, JSelect poSelect, int plTipo){
        Node loElemento;
        for(int i = 0;i<poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("tipo")==0)){
                    String lsTipo = msDevolverCadena(loElemento);
                    if(lsTipo.compareTo("distinct")==0){
                        poSelect.setCamposTipo(poSelect.mclCamposDistinct);
                    }
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("camposelect")==0)){
                    JSelectCampo loCampo = procesarCampo(loElemento);
                    switch(plTipo){
                        case 0:
                            poSelect.addCampo(loCampo);
                            break;
                        case 1:
                            poSelect.addCampoOrder(loCampo);
                            break;
                        case 2:
                            poSelect.addCampoGroup(loCampo);
                            break;
                        default:
                            
                            
                    }
                }
            }
        }
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
    private JSelectCampo procesarCampo(Node poElementos){
        JSelectCampo loCampo = new JSelectCampo("");
        Node loElemento;
        for(int i = 0;i<poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("funcion")==0)){
                    String lsFuncion = msDevolverCadena(loElemento);
                    if(lsFuncion.compareTo("AVG")==0) { 
                        loCampo.setFuncion(JSelectCampo.mclFuncionAvg);
                    }
                    if(lsFuncion.compareTo("COUNT")==0) {
                        loCampo.setFuncion(JSelectCampo.mclFuncionCount);
                    }
                    if(lsFuncion.compareTo("MAX")==0) {
                        loCampo.setFuncion(JSelectCampo.mclFuncionMax);
                    }
                    if(lsFuncion.compareTo("MIN")==0) {
                        loCampo.setFuncion(JSelectCampo.mclFuncionMin);
                    }
                    if(lsFuncion.compareTo("SUM")==0) {
                        loCampo.setFuncion(JSelectCampo.mclFuncionSum);
                    }
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("tabla")==0)){
                    loCampo.setTabla(msDevolverCadena(loElemento));
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("nombre")==0)){
                    loCampo.setNombre(msDevolverCadena(loElemento));
                }
            }
        }
        return loCampo;
    }
    
    private void procesarFrom(Node poElementos, JSelect poSelect){
        Node loElemento;
        for(int i = 0;i<poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("tabla")==0)){
                    poSelect.setTabla(msDevolverCadena(loElemento));
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("union")==0)){
                    JSelectUnionTablas loUnion = procesarFromUnion(loElemento);
                    poSelect.getFrom().addTabla(loUnion);
                }
            }
        }
    }
    private JSelectUnionTablas procesarFromUnion(Node poElementos){
        //variables
        int lTipo = JSelectUnionTablas.mclLeft;
        String lsTabla1=null;
        String lsTabla2=null;
        String[] lasCampos1=null;
        String[] lasCampos2=null;
        JListaElementos loCampos1 = new JListaElementos();
        JListaElementos loCampos2 = new JListaElementos();
        //procesamos
        Node loElemento;
        Node loElemento2;
        for(int i = 0;i<poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("tipo")==0)){
                    String lsTipo = msDevolverCadena(loElemento);
                    if(lsTipo.compareTo("inner join")==0){
                        lTipo = JSelectUnionTablas.mclInner;
                    }
                    if(lsTipo.compareTo("left join")==0){
                        lTipo = JSelectUnionTablas.mclLeft;
                    }
                    if(lsTipo.compareTo("right join")==0){
                        lTipo = JSelectUnionTablas.mclRight;
                    }
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("tabla")==0)){
                    lsTabla1 = msDevolverCadena(loElemento);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("tabla2")==0)){
                    lsTabla2 = msDevolverCadena(loElemento);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("listacampos")==0)){
                    for(int ii = 0; ii < loElemento.getChildNodes().getLength();ii++){
                        loElemento2 = loElemento.getChildNodes().item(ii);
                        if (loElemento2.getNodeType() == Node.ELEMENT_NODE){
                            if((loElemento2.getNodeName().toLowerCase().compareTo("campos")==0)){
                                procesarFromUnionCampo1Y2(loElemento2, loCampos1, loCampos2);
                            }
                        }
                    }
                }
            }
        }
        //pasamos a array los campos
        lasCampos1=new String[loCampos1.size()];
        lasCampos2=new String[loCampos2.size()];
        for(int i = 0; i<loCampos1.size();i++ ){
            lasCampos1[i]=((JSelectCampo)loCampos1.get(i)).getNombre();
            lasCampos2[i]=((JSelectCampo)loCampos2.get(i)).getNombre();
        }
        return new JSelectUnionTablas(lTipo, lsTabla1, lsTabla2, lasCampos1 , lasCampos2);
    }	
    private void procesarFromUnionCampo1Y2(Node poElementos, JListaElementos poCampos1, JListaElementos poCampos2){
        Node loElemento;
        Node loElemento2;
        for(int i = 0; i < poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("campo1")==0)||
                   (loElemento.getNodeName().toLowerCase().compareTo("campo2")==0)){
                       for(int ii = 0; ii < loElemento.getChildNodes().getLength();ii++){
                           loElemento2 = loElemento.getChildNodes().item(ii);
                           if (loElemento2.getNodeType() == Node.ELEMENT_NODE){
                                //campo
                                JSelectCampo loCampo = procesarCampo(loElemento2);
                                if(loElemento.getNodeName().toLowerCase().compareTo("campo1")==0){
                                    poCampos1.add(loCampo);
                                }else{
                                    poCampos2.add(loCampo);
                                }
                           }
                       }
                }
            }
        }        
    }
    private void procesarWhere(Node poElementos, JSelect poSelect){
        IListDatosFiltro loAux = procesarWhereUnionYCond(poElementos);
        if(loAux!=null) {
            poSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loAux);
        }
    }
    private JListDatosFiltroConj procesarWhereUnionYCond(Node poElementos){
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        boolean lbAlguno = false;
        Node loElemento;
        for(int i = 0; i < poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("condicion")==0)){
                    IListDatosFiltro loAux = procesarWhereCond(loElemento);
                    if(loAux!=null) {
                        loFiltro.addCondicion(loFiltro.mclAND, loAux);
                        lbAlguno=true;
                    }
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("unioncondiciones")==0)){
                    IListDatosFiltro loAux = procesarWhereUnion(loElemento);
                    if(loAux!=null) {
                        loFiltro.addCondicion(loFiltro.mclAND, loAux);
                        lbAlguno=true;
                    }
                }
            }
        }
        if(!lbAlguno){
            loFiltro=null;
        }
        return loFiltro;
    }
    private JListDatosFiltroConj procesarWhereUnion(Node poElementos){
        int lOperador = JListDatosFiltroConj.mclAND;
        JListDatosFiltroConj loFiltro=null;
        JListDatosFiltroConj loFiltro2=null;
        Node loElemento;
        for(int i = 0; i < poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("operador")==0)){
                    String lsOperador = msDevolverCadena(loElemento);
                    if(lsOperador.toLowerCase().compareTo("or")==0){
                        lOperador = JListDatosFiltroConj.mclOR;
                    }
                    if(lsOperador.toLowerCase().compareTo("and")==0){
                        lOperador = JListDatosFiltroConj.mclAND;
                    }
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("operando1")==0)){
                    loFiltro = procesarWhereUnionYCond(loElemento);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("operando2")==0)){
                    loFiltro2 = procesarWhereUnionYCond(loElemento);
                }
            }
        }
        loFiltro.addCondicion(lOperador, loFiltro2);
        return loFiltro;
    }
    private JListDatosFiltroElem procesarWhereCond(Node poElementos){
        JSelectCampo loCampo = null;
        //JSelectCampo loCampo2 = null;
        int lCond=JListDatos.mclTIgual;
        String lsValor="";
        int lTipoCampo=JListDatos.mclTipoCadena;
        
        Node loElemento;
        Node loElemento2;
        for(int i = 0; i < poElementos.getChildNodes().getLength();i++){
            loElemento = poElementos.getChildNodes().item(i);
            if (loElemento.getNodeType() == Node.ELEMENT_NODE){
                if((loElemento.getNodeName().toLowerCase().compareTo("cond")==0)){
                    String lsCond = msDevolverCadena(loElemento);
                    if(lsCond.compareTo("igual")==0){
                        lCond = JListDatos.mclTIgual;
                    }
                    if(lsCond.compareTo("distinto")==0){
                        lCond = JListDatos.mclTDistinto;
                    }
                    if(lsCond.compareTo("mayor")==0){
                        lCond = JListDatos.mclTMayor;
                    }
                    if(lsCond.compareTo("mayorigual")==0){
                        lCond = JListDatos.mclTMayorIgual;
                    }
                    if(lsCond.compareTo("menor")==0){
                        lCond = JListDatos.mclTMenor;
                    }
                    if(lsCond.compareTo("menorigual")==0){
                        lCond = JListDatos.mclTMenorIgual;
                    }
                    if(lsCond.toLowerCase().compareTo("like")==0){
                        lCond = JListDatos.mclTLike;
                    }
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("valor")==0)){
                    lsValor = msDevolverCadena(loElemento);
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("tipo")==0)){
                    String lsTipo = msDevolverCadena(loElemento);
                    if(lsTipo.toLowerCase().compareTo("boolean")==0){
                        lTipoCampo = JListDatos.mclTipoBoolean;
                    }
                    if(lsTipo.toLowerCase().compareTo("cadena")==0){
                        lTipoCampo = JListDatos.mclTipoCadena;
                    }
                    if(lsTipo.toLowerCase().compareTo("fecha")==0){
                        lTipoCampo = JListDatos.mclTipoFecha;
                    }
                    if(lsTipo.toLowerCase().compareTo("numero")==0){
                        lTipoCampo = JListDatos.mclTipoNumero;
                    }
                    if(lsTipo.toLowerCase().compareTo("numerodoble")==0){
                        lTipoCampo = JListDatos.mclTipoNumeroDoble;
                    }
                    if(lsTipo.toLowerCase().compareTo("moneda3D")==0){
                        lTipoCampo = JListDatos.mclTipoMoneda3Decimales;
                    }
                    if(lsTipo.toLowerCase().compareTo("moneda")==0){
                        lTipoCampo = JListDatos.mclTipoMoneda;
                    }
                    if(lsTipo.toLowerCase().compareTo("porciento3D")==0){
                        lTipoCampo = JListDatos.mclTipoPorcentual3Decimales;
                    }
                    if(lsTipo.toLowerCase().compareTo("porciento")==0){
                        lTipoCampo = JListDatos.mclTipoPorcentual;
                    }
                }
                if((loElemento.getNodeName().toLowerCase().compareTo("campo")==0)||
                   (loElemento.getNodeName().toLowerCase().compareTo("campo2")==0)){
                   for(int ii = 0; ii < loElemento.getChildNodes().getLength();ii++){
                       loElemento2 = loElemento.getChildNodes().item(ii);
                       if (loElemento2.getNodeType() == Node.ELEMENT_NODE){
                            //campo
                            if(loElemento.getNodeName().toLowerCase().compareTo("campo")==0){
                                loCampo = procesarCampo(loElemento2);
                            }
//                            else{
//                                loCampo2 = procesarCampo(loElemento2);
//                            }
                       }
                   }
                }
            }
        }
        return new JListDatosFiltroElem(
                        lCond,
                        loCampo.getTabla(),
                        new String[]{loCampo.getNombre()},
                        new String[]{lsValor},
                        new int[]{lTipoCampo}
                );
    }
    
    private void procesarOrder(Node poElemento, JSelect poSelect){
        procesarCampos(poElemento, poSelect, 1);
    }
    private void procesarGroup(Node poElemento, JSelect poSelect){
        procesarCampos(poElemento, poSelect, 2);
    }
    private void procesarHaving(Node poElemento, JSelect poSelect){
        //vacio
    }

    public boolean getNecesitaConexionBD(){
        return true;
    }
}
