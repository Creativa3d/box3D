/*
 * JServerCrearJResultado.java
 *
 * Created on 13 de enero de 2005, 9:08
 */

package ListDatos;

import ListDatos.estructuraBD.JFieldDef;
import utiles.CadenaLarga;
import utiles.JConversiones;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMException;
import utiles.xml.dom.SAXBuilder;
import utiles.xml.sax.*;

/**Crea el objeto resultado concreto*/
public class JXMLServerCrearJListDatos implements IXMLDevolverObjetoResultado {
    private JListDatos moResultado;
    private JFilaDatosDefecto moFila = new JFilaDatosDefecto();;
    
    /** Creates a new instance of JServerCrearJResultado */
    public JXMLServerCrearJListDatos() {
         moResultado = new JListDatos();
    }
    
    public void startDocument() throws Exception {
        //vacio a posta
    }
    public void endDocument() throws Exception {
        //vacio a posta
    }
    
    
    public void startElement(String name, JAtributos poAtributos) throws Exception {
        if(name.compareTo("registro")==0){
            moFila = new JFilaDatosDefecto();
        }
    }
    public void endElement(JEtiqueta poEtiqueta) throws Exception {
        if(poEtiqueta.getNombre().compareTo("fila")==0){
            moResultado.add(moFila);
            moFila = new JFilaDatosDefecto();
        }
        if(poEtiqueta.getNombre().compareTo("tabla")==0){
            moResultado.msTabla = poEtiqueta.getValor();
        }
        if(poEtiqueta.getNombre().compareTo("valor")==0){
            moFila.addCampo(poEtiqueta.getValor());
        }
        if(poEtiqueta.getNombre().compareTo("operacion")==0){
            if (poEtiqueta.getValor().compareTo("borrar")==0){
                moFila.setTipoModif(JListDatos.mclBorrar);
            }else{
                if (poEtiqueta.getValor().compareTo("nuevo")==0){
                    moFila.setTipoModif(JListDatos.mclNuevo);
                }else{
                    if (poEtiqueta.getValor().compareTo("editar")==0){
                        moFila.setTipoModif(JListDatos.mclEditar);
                    }else{
                        moFila.setTipoModif (JListDatos.mclNada);
                    }
                }
            }
        }
        
    }

    public Object getObjeto(){
        return moResultado;
    }
    
    public JListDatos getList(String psTexto) throws Exception{
        SAXBuilder loS = new  SAXBuilder();
        Document loDoc = loS.build(new CadenaLarga(psTexto));
        return getList(loDoc.getRootElement());
        
    }
    public JListDatos getList(Element loElem) throws Exception{
        JListDatos loResult=null;
        if(loElem.getName().equalsIgnoreCase("ListDatos")){
            loResult=new JListDatos();
            for(int i = 0 ; i < loElem.getChildren().size(); i++){
                Element loHijo = (Element) loElem.getChildren().get(i);
                if(loHijo.getName().equals("tabla")){
                    loResult.msTabla=loHijo.getValor();
                }
                if(loHijo.getName().equals("select")){
                    loResult.msSelect=loHijo.getValor();
                }
                if(loHijo.getName().equals("campos")){
                    crearCampos(loHijo, loResult);
                }
                if(loHijo.getName().equals("datos")){
                    rellenar(loHijo, loResult);
                }
            }
        }
        return loResult;
    }
    public void crearCampos(Element loCampos, JListDatos poList) throws Exception{
        for(int i = 0 ; i < loCampos.getChildren().size(); i++){
            Element loHijo = (Element) loCampos.getChildren().get(i);
            if(loHijo.getName().equals("campo")){
                JFieldDef loCampo = new JFieldDef("");
                crearCampo(loHijo, loCampo);
                poList.getFields().addField(loCampo);
            }
        }
    }
    public void crearCampo(Element loCampo, JFieldDef poCampo) throws Exception{
        for(int i = 0 ; i < loCampo.getChildren().size(); i++){
            Element loHijo = (Element) loCampo.getChildren().get(i);
            if(loHijo.getName().equals("nombre")){
                poCampo.setNombre(loHijo.getValor());
            }
            if(loHijo.getName().equals("tipo")){
                poCampo.setTipo(JXMLSelectMotor.mlTipo(loHijo.getValor()));
            }
            if(loHijo.getName().equals("tamano")){
                poCampo.setTamano((int)JConversiones.cdbl(loHijo.getValor()));
            }
            if(loHijo.getName().equals("esPrincipal")){
                poCampo.setPrincipalSN(Boolean.valueOf(loHijo.getValor()).booleanValue());
            }
            if(loHijo.getName().equals("valor")){
                poCampo.setValue(loHijo.getValor());
            }
            if(loHijo.getName().equals("actualizarValorSiNulo")){
                poCampo.setActualizarValorSiNulo((int)JConversiones.cdbl(loHijo.getValor()));
            }
            if(loHijo.getName().equals("valorPorDefecto")){
                poCampo.setValorPorDefecto(loHijo.getValor());
            }
            if(loHijo.getName().equals("caption")){
                poCampo.setCaption(loHijo.getValor());
            }
        }
    }
    public void rellenar(Element loDatos, JListDatos poList) throws Exception{
        for(int i = 0 ; i < loDatos.getChildren().size(); i++){
            Element loHijo = (Element) loDatos.getChildren().get(i);
            if(loHijo.getName().equals("fila")){
                JFilaDatosDefecto loFila = new JFilaDatosDefecto();
                for(int f = 0 ; f < loHijo.getChildren().size(); f++){
                    Element lof = (Element) loHijo.getChildren().get(f);
                    if(lof.getName().equals("valor")){
                        loFila.addCampo(lof.getValor());
                    }
                    if(lof.getName().equals("operacion")){
                        if(lof.getValor().equals("editar")){
                            loFila.setTipoModif(JListDatos.mclEditar);
                        }
                        if(lof.getValor().equals("borrar")){
                            loFila.setTipoModif(JListDatos.mclBorrar);
                        }
                        if(lof.getValor().equals("nuevo")){
                            loFila.setTipoModif(JListDatos.mclNuevo);
                        }
                    }
                }
                poList.add(loFila);
            }
        }
    }
    
}
