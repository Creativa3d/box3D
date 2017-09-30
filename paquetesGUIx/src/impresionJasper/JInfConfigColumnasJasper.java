/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionJasper;

import java.util.HashMap;
import java.util.Iterator;

public class JInfConfigColumnasJasper implements Cloneable {
    
    private HashMap moColumnas = new HashMap();
    
    /** Creates a new instance of JTablaConfigTabla */
    public JInfConfigColumnasJasper() {
    }
    
    public int size(){
        return moColumnas.size();
    }
    public boolean existColumna(final int plColumna){
        JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper) moColumnas.get(String.valueOf(plColumna));
        return (loColumn!=null);
        
    }
    public JInfConfigColumnaJasper getColumna(final int i){
        JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper) moColumnas.get(String.valueOf(i));
        if(loColumn==null){
            loColumn = new JInfConfigColumnaJasper(i); 
            moColumnas.put(String.valueOf(i), loColumn);
        }
        return loColumn;
    }
    public JInfConfigColumnaJasper getColumna(final String i){
        return (JInfConfigColumnaJasper) moColumnas.get(i);
    }

    public JInfConfigColumnaJasper getColumnaPorOrden(final int plOrden){
        Iterator loKeys = moColumnas.keySet().iterator();
        while(loKeys.hasNext()){
            Object loKey = loKeys.next();
            JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(loKey);
            if(loColumn.getOrden()==plOrden){
                return loColumn;
            }
        }
        return null;
    }
    public boolean isOrdenCorrecto(){
        int lUltColu = getUltColumn();
        int lOrdenAnt = -1;
        for(int i = 0 ; i < lUltColu;i++){
            JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(String.valueOf(i));
            if(loColumn != null){
                if (loColumn.getOrden() < lOrdenAnt){
                    return false;
                }
                lOrdenAnt=loColumn.getOrden();
            }
        }
        return true;
       
    }    
    public void eliminarOrdenYLong(){
        Iterator loKeys = moColumnas.keySet().iterator();
        int lMin=1000;
        while(loKeys.hasNext()){
            Object loKey = loKeys.next();
            JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(loKey);
            loColumn.setOrden(lMin);
            loColumn.setLong(0);
            lMin++;
        }
        
    }
    /**Los ordenes pueden tener huecos, este procedimiento elimina los huecos de los ordenes*/
    public void ponerOrdenesCorrectos(){
        int lMin;
        Object loColumnK;
        Iterator loKeys = moColumnas.keySet().iterator();
        while(loKeys.hasNext()){
            Object loKey = loKeys.next();
            JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(loKey);
            loColumn.setOrden(loColumn.getOrden()+1000);
        }
        
        int lMinValorSinOcupar=0;
        for(int i = 0 ; i < moColumnas.size(); i++){
            lMin = 100000;
            loColumnK = null;
            loKeys = moColumnas.keySet().iterator();
            while(loKeys.hasNext()){
                Object loKey = loKeys.next();
                JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(loKey);
                if(loColumn.getOrden()<lMin && 
                   loColumn.getOrden()>=lMinValorSinOcupar){
                    lMin=loColumn.getOrden();
                    loColumnK = loKey;
                }
            }
            ((JInfConfigColumnaJasper)moColumnas.get(loColumnK)).setOrden(lMinValorSinOcupar);
            lMinValorSinOcupar++;
        }
    }

    public void ordenarColumnas(){
        int lMin;
        Object loColumnK;
        HashMap loAux = new HashMap();
        
        while(moColumnas.size()>0){
            lMin = 100000;
            loColumnK = null;
            Iterator loKeys = moColumnas.keySet().iterator();
            while(loKeys.hasNext()){
                Object loKey = loKeys.next();
                JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(loKey);
                if(loColumn.getOrden()<lMin){
                    lMin=loColumn.getOrden();
                    loColumnK = loKey;
                }
            }
            loAux.put(loColumnK, moColumnas.get(loColumnK));
            moColumnas.remove(loColumnK);
        }
        moColumnas=loAux; 
    }

    public Object clone() throws CloneNotSupportedException{
        JInfConfigColumnasJasper lo = (JInfConfigColumnasJasper) super.clone();
        lo.moColumnas = new HashMap();
        Iterator loKeys = moColumnas.keySet().iterator();
        while(loKeys.hasNext()){
            Object loKey = loKeys.next();
            lo.moColumnas.put(loKey, ((JInfConfigColumnaJasper)moColumnas.get(loKey)).clone());
        }
        return lo;
    }

    public void addColumna(JInfConfigColumnaJasper poInfConfigColumnaJasper) {
        moColumnas.put(
                String.valueOf(poInfConfigColumnaJasper.getColumna()), 
                poInfConfigColumnaJasper);
    }
    public int getUltOrden(){
        int lMax=-1;
        Iterator loKeys = moColumnas.keySet().iterator();
        while(loKeys.hasNext()){
            Object loKey = loKeys.next();
            JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(loKey);
            if(loColumn.getOrden()>lMax){
                lMax=loColumn.getOrden();
            }
        }
        return lMax;
    }
    public int getUltColumn(){
        int lMax=-1;
        Iterator loKeys = moColumnas.keySet().iterator();
        while(loKeys.hasNext()){
            Object loKey = loKeys.next();
            JInfConfigColumnaJasper loColumn = (JInfConfigColumnaJasper)moColumnas.get(loKey);
            if(loColumn.getColumna()>lMax){
                lMax=loColumn.getColumna();
            }
        }
        return lMax;
    }
    
}
