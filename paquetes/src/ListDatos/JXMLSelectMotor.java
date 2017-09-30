
/*
 * JSelectMotorXML.java
 *
 * Created on 4 de enero de 2005, 11:24
 */

package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.Serializable;
import java.util.Iterator;


import utiles.*;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.SAXBuilder;

/**
 * Construye XML a partir del Objeto JSelect 
 */
public class JXMLSelectMotor implements ISelectMotor, Serializable{
    private static final long serialVersionUID = 33333324L;
    private static String msRetornoCarro = System.getProperty("line.separator");
    public static final String mcsAccionI = "<accion>";
    public static final String mcsAccionF = "</accion>";
    public static final String mcsTablaI = "<tabla>";
    public static final String mcsTablaF = "</tabla>";
    public static final String mcsAtributosI = "<atributos>";
    public static final String mcsAtributosF = "</atributos>";
    
//    public JXMLSelectMotor(){
//        super();
//    }
    /**
     * Pasar un JResultado a XML
     * @return XML del objeto JResultado
     * @param poResultado objeto a pasar a xml
     */

    public static String getXMLResultado(final IResultado poResultado) {
        StringBuilder lsXML = new StringBuilder(100);
        lsXML.append(mcsAccionI);lsXML.append(msRetornoCarro);
        lsXML.append("<nombre>JResultado</nombre>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosI);lsXML.append(msRetornoCarro);
        IListaElementos loListaListDatos = poResultado.getListDatos();
        for(int i = 0; i < loListaListDatos.size(); i++){
            JListDatos loList = (JListDatos) loListaListDatos.get(i);
            if(loList.moveFirst()){
                do{
                    lsXML.append("<registro>");lsXML.append(msRetornoCarro);
                    lsXML.append(mcsTablaI + msReemplazarCaracNoValidos(loList.msTabla) + mcsTablaF );lsXML.append(msRetornoCarro);
                    lsXML.append(getXMLFilaDatos(loList.moFila(), true) );lsXML.append(msRetornoCarro);
                    lsXML.append("</registro>" );lsXML.append(msRetornoCarro);
                }while(loList.moveNext());
            }

        }
        lsXML.append("<bien>"+String.valueOf(poResultado.getBien())+"</bien>" );lsXML.append(msRetornoCarro);
        lsXML.append("<mensaje>"+msReemplazarCaracNoValidos(poResultado.getMensaje())+"</mensaje>" );lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosF );lsXML.append(msRetornoCarro);
        lsXML.append(mcsAccionF);lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }

    /**
     * Pasar un JFilaDatos a XML
     * @return XML del objeto JFilaDatos
     * @param poFila objeto a pasar a xml
     * @param pbConTipoModif indica si la variable tipo modificacion se 
     *  anade de JFilaDatos se anade al xml
     */
    public static String getXMLFilaDatos(final IFilaDatos poFila, final boolean pbConTipoModif){
        int lLen = poFila.mlNumeroCampos() * 8 * 18 + 15;
        StringBuilder lsXML = new StringBuilder(lLen);
        lsXML.append("<f>");
        lLen = poFila.mlNumeroCampos();
        for(int i = 0; i < lLen;i++){
            lsXML.append("<v>");
                lsXML.append(msReemplazarCaracNoValidos(poFila.msCampo(i)));
            lsXML.append("</v>");
        }
        lsXML.append(msRetornoCarro);
        if(pbConTipoModif){
            lsXML.append("<o>");
            switch(poFila.getTipoModif()){
                case JListDatos.mclBorrar:
                    lsXML.append("borrar");
                    break;
                case JListDatos.mclNuevo:
                    lsXML.append("nuevo");
                    break;
                case JListDatos.mclEditar:
                    lsXML.append("editar");
                    break;
                default:
                    lsXML.append("");
                    break;
            }
            lsXML.append("</o>" ); lsXML.append(msRetornoCarro);
        }
        
        lsXML.append("</f>" ); lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
    /**
     * Pasar un JListDatos a XML
     * @return XML del objeto
     * @param poList objeto a pasar a xml
     */
    public static String getXMLListDatos(final JListDatos poList){
        return getXMLListDatos(poList, false);
    }    
    /**
     * Pasar un JListDatos a XML
     * @param pbReducido si es reducido no se añade la select ni el numero de campos y solo se añade de cada campo el nombre
     * @return XML del objeto
     * @param poList objeto a pasar a xml
     */
    public static String getXMLListDatos(final JListDatos poList, boolean pbReducido){
        JFieldDefs loFields = poList.getFields();
        int lLen = poList.size() * loFields.count() * 18  + 30;
        StringBuilder lsXML = new StringBuilder(lLen);
        lsXML.append("<ListDatos>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsTablaI);lsXML.append(msRetornoCarro);
            lsXML.append(msReemplazarCaracNoValidos(poList.msTabla));
        lsXML.append(mcsTablaF);lsXML.append(msRetornoCarro);
        if(!pbReducido){
            lsXML.append("<select>");lsXML.append(msRetornoCarro);
                lsXML.append(msReemplazarCaracNoValidos(poList.msSelect));
            lsXML.append("</select>");lsXML.append(msRetornoCarro);
            lsXML.append("<numeroCampos>");lsXML.append(msRetornoCarro);
                lsXML.append(loFields.count());
            lsXML.append("</numeroCampos>");lsXML.append(msRetornoCarro);
        }
        lsXML.append(msCamposEnXML(loFields, true, pbReducido));lsXML.append(msRetornoCarro);


        lsXML.append("<datos>");lsXML.append(msRetornoCarro);
        JListDatosBookMark loMen = poList.getMemento();
        if(poList.moveFirst()){
            do{
                lsXML.append(getXMLFilaDatos(poList.getFields().moFilaDatos(),true));
            }while(poList.moveNext());
        }
        poList.setMemento(loMen);
        lsXML.append("</datos>");lsXML.append(msRetornoCarro);

        lsXML.append("</ListDatos>");lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }    
//    private static String msCampoEnXML(final JFieldDef poCampo){
//        return msCampoEnXML(poCampo, true);
//    }
    /**
     * Pasar un JFieldDef a XML
     * @return XML del objeto
     * @param poCampo objeto a pasar a xml
     * @param pbConValor indica si se anade el valor del campo al xml
     */
    public static String msCampoEnXML(final JFieldDef poCampo, final boolean pbConValor){
        return msCampoEnXML(poCampo, pbConValor, false);
    }
    /**
     * Pasar un JFieldDef a XML
     * @return XML del objeto
     * @param poCampo objeto a pasar a xml
     * @param pbConValor indica si se anade el valor del campo al xml
     */
    public static String msCampoEnXML(final JFieldDef poCampo, final boolean pbConValor, final boolean pbReducido){
        StringBuilder lsXML = new StringBuilder(210);
        lsXML.append("<campo>");lsXML.append(msRetornoCarro);
        lsXML.append("<nombre>" + msReemplazarCaracNoValidos(poCampo.getNombre()) + "</nombre>");lsXML.append(msRetornoCarro);
        if(!pbReducido){
            lsXML.append("<tipo>" + msTipo(poCampo.getTipo()) + "</tipo>");lsXML.append(msRetornoCarro);
            lsXML.append("<tamano>" + String.valueOf(poCampo.getTamano()) + "</tamano>");lsXML.append(msRetornoCarro);
            lsXML.append("<esPrincipal>" + String.valueOf(poCampo.getPrincipalSN()) + "</esPrincipal>");lsXML.append(msRetornoCarro);
            lsXML.append("<actualizarValorSiNulo>" + String.valueOf(poCampo.getActualizarValorSiNulo()) + "</actualizarValorSiNulo>");lsXML.append(msRetornoCarro);
            lsXML.append("<valorPorDefecto>" + msReemplazarCaracNoValidos(poCampo.getValorPorDefecto()) + "</valorPorDefecto>");lsXML.append(msRetornoCarro);
            lsXML.append("<caption>" + msReemplazarCaracNoValidos(poCampo.getCaption()) + "</caption>");lsXML.append(msRetornoCarro);
        }
        lsXML.append((!pbConValor ? "":"<valor>" + msReemplazarCaracNoValidos(poCampo.getString()) + "</valor>"));lsXML.append(msRetornoCarro);
        lsXML.append("</campo>");lsXML.append(msRetornoCarro);
        
        return lsXML.toString();
    }
    
    public static JFieldDefs getCamposDesdeXML(String psDatos) throws Exception {
        JFieldDefs loCampos = new JFieldDefs();
        SAXBuilder loSax = new SAXBuilder();
        Document loDoc = loSax.build(new CadenaLarga(psDatos));
        Element loRaiz = loDoc.getRootElement();
        Element loElemento;
        for(int ini = 0;ini<loRaiz.getChildren().size();ini++){
            loElemento = (Element) loRaiz.getChildren().get(ini);
            if(loElemento.getName().equalsIgnoreCase("campo")){
                loCampos.addField(getCampoDesdeXML(loElemento));
            }
        }
        return loCampos;
    }
    public static JFieldDef getCampoDesdeXML(String psDatos) throws Exception {
        SAXBuilder loSax = new SAXBuilder();
        Document loDoc = loSax.build(new CadenaLarga(psDatos));
        Element loRaiz = loDoc.getRootElement();
        return getCampoDesdeXML(loRaiz);
    }
    public static JFieldDef getCampoDesdeXML(Element loRaiz) throws Exception {
                
        int lTipo = JListDatos.mclTipoCadena;
        String lsNombre = null;
        String lsCaption = null;
        String lsValor = null;
        String  lsValorPorDefecto = null;
        boolean lbEsPrincipal=false;
        int lTamano = -100;
        int  lActualizarValorSiNulo = JFieldDef.mclActualizarNada;

        Element loElemento;
        for(int ini = 0;ini<loRaiz.getChildren().size();ini++){
            loElemento = (Element) loRaiz.getChildren().get(ini);
            if(loElemento.getNombre().equalsIgnoreCase("nombre")){
                lsNombre = loElemento.getValor().trim();
            }
            if(loElemento.getNombre().equalsIgnoreCase("tipo")){
                String lsAux = loElemento.getValor().trim();
                if(lsAux.equalsIgnoreCase("boolean")){
                    lTipo = JListDatos.mclTipoBoolean;
                }
                if(lsAux.equalsIgnoreCase("cadena")){
                    lTipo = JListDatos.mclTipoCadena;
                }
                if(lsAux.equalsIgnoreCase("fecha")){
                    lTipo = JListDatos.mclTipoFecha;
                }
                if(lsAux.equalsIgnoreCase("numero")){
                    lTipo = JListDatos.mclTipoNumero;
                }
                if(lsAux.equalsIgnoreCase("numerodoble")){
                    lTipo = JListDatos.mclTipoNumeroDoble;
                }
                if(lsAux.equalsIgnoreCase("moneda3D")){
                    lTipo = JListDatos.mclTipoMoneda3Decimales;
                }
                if(lsAux.equalsIgnoreCase("moneda")){
                    lTipo = JListDatos.mclTipoMoneda;
                }
                if(lsAux.equalsIgnoreCase("porciento3D")){
                    lTipo = JListDatos.mclTipoPorcentual3Decimales;
                }
                if(lsAux.equalsIgnoreCase("porciento")){
                    lTipo = JListDatos.mclTipoPorcentual;
                }
            }
            if(loElemento.getNombre().equalsIgnoreCase("tamano")){
                String lsAux = loElemento.getValor().trim();
                if(utiles.JConversiones.isNumeric(lsAux)){
                    lTamano = (int)utiles.JConversiones.cdbl(lsAux);
                }
            }
            if(loElemento.getNombre().equalsIgnoreCase("esprincipal")){
                String lsAux = loElemento.getValor().trim();
                if(utiles.JConversiones.isBool(lsAux)){
                    lbEsPrincipal = utiles.JConversiones.cBool(lsAux);
                }
            }
            if(loElemento.getNombre().equalsIgnoreCase("actualizarvalorsinulo")){
                String lsAux = loElemento.getValor().trim();
                if(utiles.JConversiones.isNumeric(lsAux)){
                    lActualizarValorSiNulo = (int)utiles.JConversiones.cdbl(lsAux);
                }
            }
            if(loElemento.getNombre().equalsIgnoreCase("valorpordefecto")){
                lsValorPorDefecto = loElemento.getValor().trim();
            }
            if(loElemento.getNombre().equalsIgnoreCase("caption")){
                lsCaption = loElemento.getValor().trim();
            }
            if(loElemento.getNombre().equalsIgnoreCase("valor")){
                lsValor = loElemento.getValor().trim();
            }
            
        }
        
        JFieldDef loCampo = new JFieldDef(lTipo,lsNombre,lsCaption,lbEsPrincipal,lTamano);
        loCampo.setValue(lsValor);
        loCampo.setValorPorDefecto(lsValorPorDefecto);
        loCampo.setActualizarValorSiNulo(lActualizarValorSiNulo);
        return loCampo;
    }

    
    private static String msCamposEnXML(final JFieldDefs poFields){
        return msCamposEnXML(poFields, true);
    }
    /**
     * Pasar un JFieldDefs a XML
     * @return XML del objeto
     * @param poFields lista de campos
     * @param pbConValor indica si se anade el valor de los campos al xml
     */
    public static String msCamposEnXML(final JFieldDefs poFields, final boolean pbConValor){
        return msCamposEnXML(poFields, pbConValor, false);
    }
    /**
     * Pasar un JFieldDefs a XML
     * @return XML del objeto
     * @param poFields lista de campos
     * @param pbConValor indica si se anade el valor de los campos al xml
     */
    public static String msCamposEnXML(final JFieldDefs poFields, final boolean pbConValor, final boolean pbReducido){
        StringBuilder lsXML = new StringBuilder(50);
        lsXML.append("<campos>" );lsXML.append(msRetornoCarro);
        for(int i = 0 ; i < poFields.count() ; i++){
            lsXML.append(msCampoEnXML(poFields.get(i), pbConValor, pbReducido));
        }
        lsXML.append("</campos>" );lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
     
    /**
     * xml de creacion de tabla
     * @return XML de creacion de tabla
     * @param psTabla tabla
     * @param poFields estructura de campos
     */
    public String crearTabla(final String psTabla, final JFieldDefs poFields) {
        StringBuilder lsXML = new StringBuilder(50);
        lsXML.append(mcsAccionI);lsXML.append(msRetornoCarro);
        lsXML.append("<nombre>CrearTabla</nombre>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosI );lsXML.append(msRetornoCarro);
        lsXML.append(mcsTablaI+msReemplazarCaracNoValidos(psTabla)+mcsTablaF );lsXML.append(msRetornoCarro);
        lsXML.append(msCamposEnXML(poFields) );lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosF );lsXML.append(msRetornoCarro);
        lsXML.append(mcsAccionF );lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
    
    public String msActualizacion(final String psTabla, final JFieldDefs poCampos, final int plTipoModif) {
        StringBuilder lsXML = new StringBuilder(50);
        lsXML.append(mcsAccionI );lsXML.append(msRetornoCarro);
        lsXML.append("<nombre>actualizar</nombre>" );lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosI );lsXML.append(msRetornoCarro);
        lsXML.append(mcsTablaI+msReemplazarCaracNoValidos(psTabla)+mcsTablaF );lsXML.append(msRetornoCarro);
        lsXML.append("<tipo>");
        switch(plTipoModif){
            case JListDatos.mclBorrar:
                lsXML.append("borrar");
                break;
            case JListDatos.mclNuevo:
                lsXML.append("nuevo");
                break;
            default:
                lsXML.append("editar");
                break;
        }
        lsXML.append("</tipo>" );lsXML.append(msRetornoCarro);
        lsXML.append(msCamposEnXML(poCampos));
        lsXML.append(mcsAtributosF );lsXML.append(msRetornoCarro);
        lsXML.append(mcsAccionF );lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
    
    public String msOr(final String psCond1, final String psCond2) {
        StringBuilder lsXML = new StringBuilder(110);
        lsXML.append("<unioncondiciones>");lsXML.append(msRetornoCarro);
        lsXML.append("<operador>OR</operador>");lsXML.append(msRetornoCarro);
        lsXML.append("<operando1>"+psCond1+"</operando1>");lsXML.append(msRetornoCarro);
        lsXML.append("<operando2>"+psCond2+"</operando2>");lsXML.append(msRetornoCarro);
        lsXML.append("</unioncondiciones>");lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
    
    public String msAnd(final String psCond1, final String psCond2) {
        StringBuilder lsXML = new StringBuilder(110);
        lsXML.append("<unioncondiciones>");lsXML.append(msRetornoCarro);
        lsXML.append("<operador>AND</operador>");lsXML.append(msRetornoCarro);
        lsXML.append("<operando1>"+psCond1+"</operando1>");lsXML.append(msRetornoCarro);
        lsXML.append("<operando2>"+psCond2+"</operando2>");lsXML.append(msRetornoCarro);
        lsXML.append("</unioncondiciones>");lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
    @Override
    public String msNot(String psCond1) {
        StringBuffer lsXML = new StringBuffer(110);
        lsXML.append("<unioncondiciones>");lsXML.append(msRetornoCarro);
        lsXML.append("<operador>NOT</operador>");lsXML.append(msRetornoCarro);
        lsXML.append("<operando1>"+psCond1+"</operando1>");lsXML.append(msRetornoCarro);
        lsXML.append("</unioncondiciones>");lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
    public String msCampo(final int plFuncion, final String psTabla, final String psCampo) {
        String lsFuncion="";
        switch(plFuncion){
          case JSelectCampo.mclFuncionAvg:
              lsFuncion="AVG";
              break;
          case JSelectCampo.mclFuncionCount:
              lsFuncion="COUNT";
              break;
          case JSelectCampo.mclFuncionMax:
              lsFuncion="MAX";
              break;
          case JSelectCampo.mclFuncionMin:
              lsFuncion="MIN";
              break;
          case JSelectCampo.mclFuncionSum:
              lsFuncion="SUM";
              break;
          default:
              lsFuncion="";
        }
        StringBuilder lsXML = new StringBuilder(70);
        lsXML.append("<campoSelect>");lsXML.append(msRetornoCarro);
        lsXML.append("<funcion>"+lsFuncion+"</funcion>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsTablaI+msReemplazarCaracNoValidos(psTabla)+mcsTablaF);lsXML.append(msRetornoCarro);
        lsXML.append("<nombre>"+msReemplazarCaracNoValidos(psCampo)+"</nombre>");lsXML.append(msRetornoCarro);
        lsXML.append("</campoSelect>");lsXML.append(msRetornoCarro);
        return lsXML.toString();
    }
    private String msOperador(final int plOperador){
        String lsCadena="";
        switch(plOperador){
            case JListDatos.mclTDistinto:
              lsCadena = "distinto";
              break;
            case JListDatos.mclTIgual:
              lsCadena = "igual";
              break;
            case JListDatos.mclTMayor:
              lsCadena = "mayor";
              break;
            case JListDatos.mclTMayorIgual:
              lsCadena = "mayorigual";
              break;
            case JListDatos.mclTMenor:
              lsCadena = "menor";
              break;
            case JListDatos.mclTMenorIgual:
              lsCadena = "menorigual";
              break;
            case JListDatos.mclTLike:
              lsCadena = "like";
              break;
            default:
              lsCadena = "";
         }
         return lsCadena;
    }
    /**
     * delvuelve el tipo en cadena xml
     * @param plTipo tipo a pasar a xml
     * @return tipo en xml
     */
    public static String msTipo(final int plTipo){
        String lsTipo=null;
        switch (plTipo) {
          case JListDatos.mclTipoBoolean:
              lsTipo="boolean";
              break;
          case JListDatos.mclTipoCadena:
              lsTipo="cadena";
              break;
          case JListDatos.mclTipoFecha:
              lsTipo="fecha";
              break;
          case JListDatos.mclTipoNumero:
              lsTipo="numero";
              break;
          case JListDatos.mclTipoNumeroDoble:
              lsTipo="numerodoble";
              break;
          case JListDatos.mclTipoMoneda3Decimales:
              lsTipo="moneda3D";
              break;
          case JListDatos.mclTipoMoneda:
              lsTipo="moneda";
              break;
          case JListDatos.mclTipoPorcentual3Decimales:
              lsTipo="porciento3D";
              break;
          case JListDatos.mclTipoPorcentual:
              lsTipo="porciento";
              break;
          default:
              lsTipo="cadena"; 
        }
        return lsTipo;
    }
    public static int mlTipo(final String psTipo){
        int lTipo=JListDatos.mclTipoCadena;
        if(psTipo.equals("boolean")){
            lTipo=JListDatos.mclTipoBoolean;
        }
        if(psTipo.equals("fecha")){
            lTipo=JListDatos.mclTipoFecha;
        }
        if(psTipo.equals("numero")){
            lTipo=JListDatos.mclTipoNumero;
        }
        if(psTipo.equals("numerodoble")){
            lTipo=JListDatos.mclTipoNumeroDoble;
        }
        if(psTipo.equals("moneda3D")){
            lTipo=JListDatos.mclTipoMoneda3Decimales;
        }
        if(psTipo.equals("moneda")){
            lTipo=JListDatos.mclTipoMoneda;
        }
        if(psTipo.equals("porciento3D")){
            lTipo=JListDatos.mclTipoPorcentual3Decimales;
        }
        if(psTipo.equals("porciento")){
            lTipo=JListDatos.mclTipoPorcentual;
        }
        return lTipo;
    }
    
    public String msCondicion(final String psCampo, final int plCond, final String psValor, final String psCampo2, final int plTipo) {
        StringBuilder lsXML = new StringBuilder(100);
        lsXML.append("<condicion>");lsXML.append(msRetornoCarro);
        lsXML.append("<campo>"+psCampo+"</campo>");lsXML.append(msRetornoCarro);
        lsXML.append("<cond>"+msOperador(plCond)+"</cond>");lsXML.append(msRetornoCarro);
        lsXML.append("<valor>"+msReemplazarCaracNoValidos(psValor)+"</valor>");lsXML.append(msRetornoCarro);
        lsXML.append("<campo2>"+(psCampo2==null ? "": msReemplazarCaracNoValidos(psCampo2))+"</campo2>");lsXML.append(msRetornoCarro);
        lsXML.append("<tipo>"+msTipo(plTipo)+"</tipo>");lsXML.append(msRetornoCarro);
        lsXML.append("</condicion>");lsXML.append(msRetornoCarro);
        
        return  lsXML.toString();
    }
    
    public JSelectFromParte msFromUnion(final JSelectFromParte poParte1, final JSelectUnionTablas poUnion) {
        StringBuilder lsXML = new StringBuilder(120);
        lsXML.append(poParte1.getFrom());
        lsXML.append("<union>");lsXML.append(msRetornoCarro);
        lsXML.append("<tipo>");
        switch(poUnion.getTipo()){
          case JSelectUnionTablas.mclInner:
            lsXML.append("inner join");
            break;
          case JSelectUnionTablas.mclLeft:
            lsXML.append("left join");
            break;
          case JSelectUnionTablas.mclRight:
            lsXML.append("right join");
            break;
          default:
            lsXML.append(',');
        }
        lsXML.append("</tipo>");lsXML.append(msRetornoCarro);
        lsXML.append(msTabla(poUnion.getTablaPrefijoCampos1(), null));lsXML.append(msRetornoCarro);
        lsXML.append(msTabla2(poUnion.getTabla2(), poUnion.getTabla2Alias()));lsXML.append(msRetornoCarro);
        lsXML.append("<listaCampos>");lsXML.append(msRetornoCarro);
        for(int i=0;i<poUnion.getCampos1().length;i++){
          lsXML.append(
                "<campos><campo1>"+ msCampo(JSelectCampo.mclFuncionNada,poUnion.getTablaPrefijoCampos1(),poUnion.getCampos1()[i]) +"</campo1>"+
                "<campo2>"+ msCampo(JSelectCampo.mclFuncionNada,poUnion.getTabla2(),poUnion.getCampos2()[i]) +"</campo2></campos>");lsXML.append(msRetornoCarro);
        }
        lsXML.append("</listaCampos>" );lsXML.append(msRetornoCarro);
        lsXML.append("</union>" );lsXML.append(msRetornoCarro);

        return new JSelectFromParte(lsXML.toString(), null);        
    }
    
    public String msListaCampos(final int plTipo, final JListaElementos poCampos) {
        String lsResult = null;
        if(poCampos!=null) {
            StringBuilder lsXML = new StringBuilder(20);
            if (plTipo == JSelect.mclCamposDistinct) {
                lsXML.append("<tipo>distinct</tipo>");
            }else{
                lsXML.append("<tipo></tipo>");
            }
            Iterator loEnum = poCampos.iterator();
            for (; loEnum.hasNext(); ) {
              JSelectCampo loCampo =  (JSelectCampo)loEnum.next();
              lsXML.append(loCampo.msSQL(this));
            }
            lsResult = lsXML.toString();
        }        
        return lsResult;
    }
    
    public String msListaCamposGroup(final JListaElementos poCampos) {
        return msListaCampos(0, poCampos);
    }
    
    public String msListaCamposOrder(final JListaElementos poCampos) {
        return msListaCampos(0, poCampos);
    }
    
    
    public String msSelect(final String psCampos, final String psFrom, final String psWhere, final String psGroup, final String psHaving, final String psOrder) {
        StringBuilder lsXML = new StringBuilder(200);
        lsXML.append(mcsAccionI);lsXML.append(msRetornoCarro);
        lsXML.append("<nombre>select</nombre>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosI);lsXML.append(msRetornoCarro);
        lsXML.append("<selectCampos>"+psCampos+"</selectCampos>");lsXML.append(msRetornoCarro);
        lsXML.append("<selectFrom>"+psFrom+"</selectFrom>");lsXML.append(msRetornoCarro);
        lsXML.append("<selectWhere>"+(psWhere==null ? "": psWhere)+"</selectWhere>");lsXML.append(msRetornoCarro);
        lsXML.append("<selectOrder>"+(psOrder==null ? "": psOrder)+"</selectOrder>");lsXML.append(msRetornoCarro);
        lsXML.append("<selectGroup>"+(psGroup==null ? "": psGroup)+"</selectGroup>");lsXML.append(msRetornoCarro);
        lsXML.append("<selectHaving>"+(psHaving==null ? "": psHaving)+"</selectHaving>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosF);lsXML.append(msRetornoCarro);
        lsXML.append(mcsAccionF);lsXML.append(msRetornoCarro);
        
        return lsXML.toString();
    }
    
    public String msSelectUltMasUno(final String psTabla, final String psCampo, final int plActu) {
        StringBuilder lsXML = new StringBuilder(120);
        
        lsXML.append(mcsAccionI);lsXML.append(msRetornoCarro);
        lsXML.append("<nombre>selectUltMasUno</nombre>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosI);lsXML.append(msRetornoCarro);
        lsXML.append("<selectTabla>"+psTabla+"</selectTabla>");lsXML.append(msRetornoCarro);
        lsXML.append("<selectCampo>"+psCampo+"</selectCampo>");lsXML.append(msRetornoCarro);
        lsXML.append("<selectActu>"+String.valueOf(plActu)+"</selectActu>");lsXML.append(msRetornoCarro);
        lsXML.append(mcsAtributosF);lsXML.append(msRetornoCarro);
        lsXML.append(mcsAccionF);lsXML.append(msRetornoCarro);
        
        return lsXML.toString();
    }
    /**
     * reemplaza los caracteres no validos por sus correspondientes validos
     * @param psXml cadena a sustituir los caracteres no validos
     * @return xml con caraact. sustituidos
     */
    public static String msReemplazarCaracNoValidos(final String psXml){
        StringBuilder lsXMLReemp=null;
        if(psXml!=null){
            lsXMLReemp = new StringBuilder(psXml.length());
            for(int i = 0; i<psXml.length(); i++ ){
                if(psXml.charAt(i) == '<'){
                    lsXMLReemp.append("&lt;");
                }else if (psXml.charAt(i) == '>'){
                    lsXMLReemp.append("&gt;");
                }else if (psXml.charAt(i) == '&'){
                    lsXMLReemp.append("&amp;");
                }else if (psXml.charAt(i) == '"'){
                    lsXMLReemp.append("&quot;");
                }else if (psXml.charAt(i) == '\''){
                    lsXMLReemp.append("&apos;");
                }else{
                    if(psXml.charAt(i)==0){
                        lsXMLReemp.append(' ');
                    }else{
                        lsXMLReemp.append(psXml.charAt(i));
                    }
                }
            }
        }
        return ( lsXMLReemp==null ? "" : lsXMLReemp.toString());
    }
    /**
     *  devuelve la tabla en xml
     * @param psTabla nombre de tabla a pasar a xml
     */
    public String msTabla(final String psTabla,final String psTablaAlias) {
        return mcsTablaI+msReemplazarCaracNoValidos(psTabla)+mcsTablaF;
    }
    /**
     * devuelve la tabla en xml con marcas tabla2
     * @param psTabla nombre de tabla a pasar a xml
     * @return la tabla en xml en tabla2
     */
    public String msTabla2(final String psTabla,final String psTablaAlias) {
        return "<tabla2>"+msReemplazarCaracNoValidos(psTabla)+"</tabla2>";
    }
    
    /**
     * no se usa
     */
    public void pasarParametros(final String psTabla, final JFieldDefs poCampos, final int plTipoModif, final java.sql.PreparedStatement loSent) throws java.sql.SQLException {
    }
    
    public String getTabla(final int plTipo, final JTableDef poTabla)  throws ExceptionNoImplementado{
        throw new ExceptionNoImplementado();
    }
    public String getIndice(final int plTipo, final JIndiceDef poIndice, final JTableDef poTabla) throws ExceptionNoImplementado{
        throw new ExceptionNoImplementado();
    }

    public String getRelacion(final int plTipo, final JRelacionesDef poRelacion, final JTableDef poTabla1, final JTableDef poTabla2) throws ExceptionNoImplementado {
        throw new ExceptionNoImplementado();
    }

    public String getCampo(final int plTipo, final JFieldDef poCampo, final JTableDef poTabla) throws ExceptionNoImplementado{
        throw new ExceptionNoImplementado();
    }

    public String msSelectPaginado(String psCampos, String psFrom, String psWhere, String psGroup, String psHaving, String psOrder, String pagina_actual, String elem_per_page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
