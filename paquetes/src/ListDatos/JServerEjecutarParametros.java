/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos;

import java.io.Serializable;
import utiles.IListaElementos;
import utiles.JListaElementos;


public class JServerEjecutarParametros implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String mcsSIN_EDICION = "SIN_EDICION";
    private IListaElementos moParametros = new JListaElementos();

    public JServerEjecutarParametros(){
    }

    /**Add parametro*/
    public synchronized void addParametro(String psParam, Object psValor){
        boolean lbencon = false;
        //no permitimos duplicados
        for(int i = 0 ; i < moParametros.size(); i++){
            JServerEjecutarParametrosElem loElem = (JServerEjecutarParametrosElem) moParametros.get(i);
            if(psParam.equalsIgnoreCase(loElem.getParam())){
                loElem.msValor=psValor;
                lbencon = true;
            }
        }
        if(!lbencon){
            moParametros.add(new JServerEjecutarParametrosElem(psParam, psValor));
        }
    }
    /**Numero de parametros*/
    public int size(){
        return moParametros.size();
    }
    /**Devuelve el valor de la posicion i*/
    public Object getValor(int i){
        return ((JServerEjecutarParametrosElem)moParametros.get(i)).getValor();
    }
    /**Devuelve la clave de la posicion i*/
    public String getClave(int i){
        return ((JServerEjecutarParametrosElem)moParametros.get(i)).getParam();
    }
    /**Devuelve el valor de la clave psClave*/
    public Object getValor(String psClave){
        Object lsResult = null;
        for(int i = 0 ; i < moParametros.size() && lsResult==null; i++){
            JServerEjecutarParametrosElem loElem = (JServerEjecutarParametrosElem) moParametros.get(i);
            if(psClave.equalsIgnoreCase(loElem.getParam())){
                lsResult = loElem.getValor();
            }
        }
        return lsResult;
    }
    public boolean getValorAsBoolean(String psClave){
        Boolean lbResul = (Boolean) getValor(psClave);
        if(lbResul!=null && lbResul.booleanValue()){
            return true;
        }else{
            return false;
        }
    }
    public static class JServerEjecutarParametrosElem implements Serializable {
        private static final long serialVersionUID = 1L;
        private String msParam;
        private Object msValor;

        public JServerEjecutarParametrosElem(String psParam, Object psValor) {
            msParam = psParam;
            msValor = psValor;
        }
        public String getParam(){
            return msParam;
        }
        public Object getValor(){
            return msValor;
        }

    }
}
