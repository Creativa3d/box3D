/*
 * JTablaConfigTabla.java
 *
 * Created on 12 de julio de 2007, 9:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import utiles.IListaElementos;
import utiles.JListaElementos;

public class JTablaConfigTablaConfig implements Cloneable {
    
    private IListaElementos moColumnas = new JListaElementos();
    private String msNombre="";
    
    /** Creates a new instance of JTablaConfigTabla */
    public JTablaConfigTablaConfig() {
    }
    
    public int size(){
        return moColumnas.size();
    }
    public JTablaConfigColumna getColumna(final int i){
        JTablaConfigColumna loColumn = null;
        if( i >= 0 ){
            loColumn = (JTablaConfigColumna)moColumnas.get(i);
        }
        return loColumn;
    }
    public JTablaConfigColumna getTablaConfigColumnaDeCampoReal(final int i) {
        return  getColumna(String.valueOf(i));
    }
    private int getIndiceColumna(final String psNombre){
        int lColumn = -1;
        for(int i = 0 ; i < moColumnas.size() && lColumn == -1;i++){
            if(getColumna(i).getNombre().toUpperCase().equals(psNombre.toUpperCase())){
                lColumn = i;
            }
        }
        return lColumn;
    }
    public JTablaConfigColumna getColumna(final String psNombre){
        return getColumna(getIndiceColumna(psNombre));
    }
    public JTablaConfigColumna getColumnaPorOrden(final int plOrden){
        int lColumn = -1;
        for(int i = 0 ; i < moColumnas.size() && lColumn == -1;i++){
            if(getColumna(i).getOrden() == plOrden){
                lColumn = i;
            }
        }
        return getColumna(lColumn);
    }

    public void addColumna(final JTablaConfigColumna poColumn){
        moColumnas.add(poColumn);
    }
    public void removeColumna(final int i){
        moColumnas.remove(i);
    }
    public void removeColumna(final String psNombre){
        moColumnas.remove(getIndiceColumna(psNombre));
    }
    public void ordenarColumnas(){
        int lMin;
        int lColumn;
        IListaElementos loAux = new JListaElementos();
        
        while(moColumnas.size()>0){
            lMin = 100000;
            lColumn = -1;
            for(int i = 0 ; i < moColumnas.size(); i++){
                JTablaConfigColumna loColumn = (JTablaConfigColumna)moColumnas.get(i);
                if(loColumn.getOrden()<lMin){
                    lMin=loColumn.getOrden();
                    lColumn = i;
                }
            }
            loAux.add(moColumnas.get(lColumn));
            moColumnas.remove(lColumn);
        }
        moColumnas=loAux; 
    }

    public String getNombre() {
        return msNombre;
    }

    public void setNombre(final String msNombre) {
        this.msNombre = msNombre;
    }

    public Object clone() throws CloneNotSupportedException{
        JTablaConfigTablaConfig lo = (JTablaConfigTablaConfig) super.clone();
        lo.moColumnas = new JListaElementos();
        for(int i = 0 ; i < moColumnas.size();i++){
            lo.moColumnas.add(getColumna(i).clone());
        }
        return lo;
    }
    
}
