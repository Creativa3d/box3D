/*
 * JEtiqueta.java
 *
 * Created on 12 de enero de 2005, 13:17
 */

package utiles.xml.sax;

/**representa la informacion de una etiqueta completa*/
public class JEtiqueta {
    private String msNombre;
    private String msValor;
    private JAtributos moAtributos;
  
    public JEtiqueta(){
        moAtributos = new JAtributos();
    }
    /**
     * Nombre de la etiqueta
     * @return nombre
     */
    public String getNombre(){
        return msNombre;
    }
    /**
     * Nombre de la etiqueta
     * @param psNombre nuevo nombre
     */
    public void setNombre(String psNombre){
        msNombre = JSaxParser.reemplazarCaracRaros(psNombre);
    }
    /**
     * Valor de la etiqueta
     * @return valor 
     */
    public String getValor(){
        return msValor;
    }
    /**
     * Valor de la etiqueta
     * @param psValor establece el valor
     */
    public void setValor(String psValor){
        msValor=JSaxParser.reemplazarCaracRaros(psValor);
    }
    /**
     * Lista de Atributos
     * @return lista de atributos
     */
    public JAtributos getAtributos(){
        return moAtributos;
    }
}
