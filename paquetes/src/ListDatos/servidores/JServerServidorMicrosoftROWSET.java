/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos.servidores;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import utiles.CadenaLarga;
import utiles.IListaElementos;
import utiles.JArchivo;
import utiles.JConversiones;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.SAXBuilder;
import utiles.xml.sax.JAtributo;

/**
 *
 * @author eduardo
 */
public class JServerServidorMicrosoftROWSET {
    public JListDatos getList(String psFile) throws Exception{
        JListDatos loList = new JListDatos();
        SAXBuilder loSax = new SAXBuilder();
//        Document loCab = loSax.build(psFile);
        Document loCab = loSax.build(new CadenaLarga(new String(JArchivo.getArchivoEnBytes(psFile), "UTF-8")));

        IListaElementos loEsquema = loCab.getRootElement().getChild("s:Schema").getChild("s:ElementType").getChildren("s:AttributeType");
        for(int i = 0 ; i < loEsquema.size(); i++) {
            Element loXml = (Element) loEsquema.get(i);
            String lsNombre = loXml.getAttribute("name").getValor();
            
            int lTipo = JListDatos.mclTipoCadena;
            int lTamano = -100;
            boolean lbNull = true;
            String lsTipo="";
            
            
            IListaElementos loHijos = loXml.getChildren("s:datatype");
            if(loHijos!=null && loHijos.size()>0){
                loXml = (Element) loHijos.get(0);
                if(loXml.getAttribute("rs:dbtype")!=null){
                    lsTipo = loXml.getAttribute("rs:dbtype").getValor();
                    if(lsTipo.equalsIgnoreCase("timestamp")){
                        lTipo = JListDatos.mclTipoFecha;
                    }
                    if(lsTipo.equalsIgnoreCase("int")){
                        lTipo = JListDatos.mclTipoNumero;
                    }
                    if(lsTipo.equalsIgnoreCase("number")){
                        lTipo = JListDatos.mclTipoNumeroDoble;
                    }
                }
                if(loXml.getAttribute("dt:maxLength")!=null){
                    lTamano = (int)JConversiones.cdbl(loXml.getAttribute("dt:maxLength").getValor());            
                }
                if(loXml.getAttribute("rs:maybenull")!=null){
                    lbNull = loXml.getAttribute("rs:maybenull").getValor().equalsIgnoreCase("true");            
                }
            }
            
            JFieldDef loCampo = new JFieldDef(lTipo
                    , lsNombre , lsNombre
                    , false, lTamano, lsTipo, lbNull, "");
            
            
            loList.getFields().addField(loCampo);
        }
        Element loDatos = loCab.getRootElement().getChild("rs:data");
        for(int i = 0 ; i < loDatos.size(); i++) {
            Element loXml = (Element) loDatos.getChildren().get(i);
            loXml = getZROW(loXml);
            if(loXml!=null){
                addFila(loList, loXml);
            }
        }
        return loList;
    }
    private Element getZROW(Element loXml){
        
        if(loXml.getName().equalsIgnoreCase("z:row")){
            return loXml;
        } else{
            if(loXml.getChildren().size()>0){
                return getZROW((Element) loXml.getChildren().get(0));
            }else{
                return null;
            }
        }
        
    }
    private void addFila(JListDatos loList, Element loXml) throws Exception {
        loList.addNew();
        for(int i = 0 ; i < loXml.getAttributes().size(); i++) {
            JAtributo loAtrib = (JAtributo) loXml.getAttributes().get(i);
            loList.getFields(loAtrib.getName()).setValue(loAtrib.getValor());
        }
        loList.update(false);
    }
    
}
