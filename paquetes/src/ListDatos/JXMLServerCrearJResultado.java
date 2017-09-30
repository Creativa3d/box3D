/*
 * JServerCrearJResultado.java
 *
 * Created on 13 de enero de 2005, 9:08
 */

package ListDatos;

import utiles.xml.sax.*;
import utiles.*;

/**Crea el objeto resultado concreto*/
public class JXMLServerCrearJResultado implements IXMLDevolverObjetoResultado {
    private final JResultado moResultado;
//    private boolean mbComienzoRegistro=false;
    private String msTabla;
    private JFilaDatosDefecto moFila = new JFilaDatosDefecto();;
    
    /** Creates a new instance of JServerCrearJResultado */
    public JXMLServerCrearJResultado() {
         moResultado = new JResultado("", true);
    }
    
    public void startDocument() throws Exception {
        //vacio a posta
    }
    public void endDocument() throws Exception {
        //vacio a posta
    }
    
    
    public void startElement(String name, JAtributos poAtributos) throws Exception {
        if(name.compareTo("registro")==0){
//            mbComienzoRegistro = true;
            msTabla = "";
            moFila = new JFilaDatosDefecto();
        }
    }
    public void endElement(JEtiqueta poEtiqueta) throws Exception {
        if(poEtiqueta.getNombre().compareTo("fila")==0){
            moResultado.addDatos(moFila, msTabla, moFila.getTipoModif());
            msTabla = "";
            moFila = new JFilaDatosDefecto();
        }
        if(poEtiqueta.getNombre().compareTo("tabla")==0){
            msTabla = poEtiqueta.getValor();
        }
        if(poEtiqueta.getNombre().compareTo("bien")==0){
            if(JConversiones.isBool(poEtiqueta.getValor())){
                moResultado.setBien(JConversiones.cBool(poEtiqueta.getValor()));
            }
        }
        if(poEtiqueta.getNombre().compareTo("mensaje")==0){
            moResultado.setMensaje(poEtiqueta.getValor());
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
    
}
