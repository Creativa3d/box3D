/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.forms.util;

import java.awt.Color;
import utiles.JDateEdu;

public class JTableRenderEventoParam  {
    private String msCalendario;
    private String msCodigo;
    private Color moPrioridad;
    private String msNombre;
    private String msTexto;
    private JDateEdu moFechaDesde;
    private JDateEdu moFechaHasta;
    private boolean mbAviso;

    /**
     * @return the msCodigo
     */
    public String getCodigo() {
        return msCodigo;
    }

    /**
     * @param msCodigo the msCodigo to set
     */
    public void setCodigo(String msCodigo) {
        this.msCodigo = msCodigo;
    }

    /**
     * @return the moPrioridad
     */
    public Color getPrioridad() {
        return moPrioridad;
    }

    /**
     * @param moPrioridad the moPrioridad to set
     */
    public void setPrioridad(Color moPrioridad) {
        this.moPrioridad = moPrioridad;
    }

    /**
     * @return the msNopmbre
     */
    public String getNombre() {
        return msNombre;
    }

    /**
     * @param msNopmbre the msNopmbre to set
     */
    public void setNombre(String msNopmbre) {
        this.msNombre = msNopmbre;
    }

    /**
     * @return the msTexto
     */
    public String getTexto() {
        return msTexto;
    }

    /**
     * @param msTexto the msTexto to set
     */
    public void setTexto(String msTexto) {
        this.msTexto = msTexto;
    }

    /**
     * @return the moFechaDesde
     */
    public JDateEdu getFechaDesde() {
        return moFechaDesde;
    }

    /**
     * @param moFechaDesde the moFechaDesde to set
     */
    public void setFechaDesde(JDateEdu moFechaDesde) {
        this.moFechaDesde = moFechaDesde;
    }

    /**
     * @return the moFechaHasta
     */
    public JDateEdu getFechaHasta() {
        return moFechaHasta;
    }

    /**
     * @param moFechaHasta the moFechaHasta to set
     */
    public void setFechaHasta(JDateEdu moFechaHasta) {
        this.moFechaHasta = moFechaHasta;
    }

    @Override
    public String toString() {
        return "<html>"+msNombre +"<br>"+toStringFechas(false)+"</html>";
    }
    public String toStringFechas(boolean pbHTML){
        String lsHora = "";
        if(getFechaDesde()!=null && getFechaHasta()!=null){
            if(getFechaDesde().msFormatear("ddMMyyyy").equals(getFechaHasta().msFormatear("ddMMyyyy"))){
                lsHora = (pbHTML ? "<html>" + (isAviso()?"<strike>":""): "")
                        +"<font size=2> "
                        +getFechaDesde().msFormatear("dd 'de' MMM' de ' yyyy ',' ") 
                        +"</font>"
                        +"<font size=4> "
                        +getFechaDesde().msFormatear("HH:mm") 
                        + " - " +
                        getFechaHasta().msFormatear("HH:mm") 
                        +"</font>"
                        +(pbHTML ? (isAviso()?"</strike>":"")+"</html>": "");
            }else{
                lsHora = getFechaDesde().msFormatear("dd 'de' MMM' de ' yyyy ',' HH:mm") 
                        + " - " +
                        getFechaHasta().msFormatear("dd 'de' MMM',' HH:mm") ;
            }
        }
        return lsHora;
    }

    public String toStringFechas(){
        return toStringFechas(true);
        
    }
    /**
     * @return the mbAviso
     */
    public boolean isAviso() {
        return mbAviso;
    }

    /**
     * @param mbAviso the mbAviso to set
     */
    public void setAviso(boolean mbAviso) {
        this.mbAviso = mbAviso;
    }

    /**
     * @return the msCalendario
     */
    public String getCalendario() {
        return msCalendario;
    }

    /**
     * @param msCalendario the msCalendario to set
     */
    public void setCalendario(String msCalendario) {
        this.msCalendario = msCalendario;
    }
    
    
    
}
