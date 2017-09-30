/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUI.procesar;

public abstract class JProcesoAccionAbstrac implements IProcesoAccion {
    protected JProcesoAccionParam moParametros=new JProcesoAccionParam();
    protected int mlRegistroActual = -1;
    protected boolean mbFin = false;
    protected boolean mbCancelado = false;
    protected String msTituloRegistroActual="";

    public JProcesoAccionAbstrac(){
    }
    public JProcesoAccionAbstrac(Object poTAG){
        moParametros.setTag(poTAG);
    }
    
    
    public JProcesoAccionParam getParametros(){
        return moParametros;
    }

    public int getNumeroRegistro(){
        return mlRegistroActual;
    }

    public boolean isFin(){
        return mbFin;
    }

    public void setCancelado(boolean pbCancelado){
        mbCancelado = pbCancelado;
    }

    public void finalizar(){
        mbFin=true;
    }
    public String getTituloRegistroActual() {
        return msTituloRegistroActual;
    }
    

}
