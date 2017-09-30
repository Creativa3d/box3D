/*
 * JTipoTextoBase.java
 *
 * Created on 8 de mayo de 2007, 19:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUI.tiposTextos;


public abstract class JTipoTextoBaseAbstract implements ITipoTexto {
    private boolean mbTipoCorrectoObligatorio=true;
    protected String msTexto = "";
    protected String msOriginal = null;
    protected int mlTipo;
    
    public void restaurarTexto(){
        msTexto = msOriginal;
    }
    public String getTextoError(final String psTexto){
        return null;
    }
    public boolean isTipoCorrecto(final String psValor){
        return true;
    }
    public String getTextoModificado(final String psTexto) {
        return psTexto;
    }
    public void lostFocus(final String psTexto){
        if(isTipoCorrecto(psTexto) || !isTipoCorrectoObligatorio()){
            msTexto = getTextoModificado(psTexto);
        }
    }
    public void getTecla(final String psTexto, final KeyEventCZ poEvent){
    }
    
    public boolean isTextoCambiado() {
        boolean lbResult = false;
        if(msOriginal!=null){
            lbResult = msTexto.compareTo(msOriginal)!=0;
        }
        return lbResult;
    }
    
    public int getTipo(){
        return mlTipo;
    }
    
    public String getText(){
        return msTexto;
    }
    public String getTextFormateado(){
        return msTexto;
    }
    public void setText(final String psText){
        msTexto = getTextoModificado(psText);
        if(msTexto == null){
            msTexto = "";
        }
    }
    public void setTextOriginal(final String psText){
        msOriginal = psText;
    }
    public String getTextOriginal() {
        return msOriginal;
    }
    
    public boolean isTipoCorrectoObligatorio(){
        return mbTipoCorrectoObligatorio;
    }
    
    public void setTipoCorrectoObligatorio(final boolean pbValor){
        mbTipoCorrectoObligatorio = pbValor;
    }
    
    
}
