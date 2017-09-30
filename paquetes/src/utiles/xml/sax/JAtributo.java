/*
 * JAtributo.java
 *
 * Created on 12 de enero de 2005, 10:34
 */

package utiles.xml.sax;

/**Atributo concreto de una etiqueta*/
public class JAtributo {
    /**Nombre del atributo*/
    private String msNombre;
    /**valor del atributo*/
    private String msValor;
    
    /**
     * Constructor
     * @param psNombre nombre
     * @param psValor valor
     */
    public JAtributo(String psNombre,String psValor) {
        msNombre = JSaxParser.reemplazarCaracRaros(psNombre);
        msValor = JSaxParser.reemplazarCaracRaros(psValor);
    }
    /**
     * Nombre de la atributo
     * @return nombre
     */
    public String getNombre(){
        return msNombre;
    }
    /**
     * Nombre de la atributo
     * @return nombre
     */
    public String getName(){
        return msNombre;
    }
    /**
     * Nombre de la atributo
     * @param psNombre el nombre
     */
    public void setNombre(String psNombre){
        msNombre = JSaxParser.reemplazarCaracRaros(psNombre);
    }
    /**
     * Valor de la atributo
     * @return valor
     */
    public String getValor(){
        return msValor;
    }
    /**
     * Valor de la atributo
     * @return valor
     */
    public String getValue(){
        return msValor;
    }
    /**
     * Valor de la atributo
     * @param psValor establece el valor
     */
    public void setValor(String psValor){
        msValor=JSaxParser.reemplazarCaracRaros(psValor);
    }
    
}
