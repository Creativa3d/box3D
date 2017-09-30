/*
 * JTablaConfigTabla.java
 *
 * Created on 12 de julio de 2007, 9:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import java.util.HashMap;
import java.util.LinkedHashMap;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JListaElementos;

public class JTablaConfigTabla {
    
    public static final String mcsSinFiltro = "Filtros";
    
    private IListaElementos moConfigs = new JListaElementos();
    private String msNombre="";
    private String msCampoBusqueda="";
    private String msFiltroDefecto=mcsSinFiltro;
    
    
    private LinkedHashMap<String, JTFiltro> moFiltros = new LinkedHashMap<String, JTFiltro>();
    
    /** Creates a new instance of JTablaConfigTabla */
    public JTablaConfigTabla() {
    }
    
    public int size(){
        return moConfigs.size();
    }
    public JTablaConfigTablaConfig getConfig(final int i){
        JTablaConfigTablaConfig loConfig = null;
        if(i>=0){
            loConfig = (JTablaConfigTablaConfig)moConfigs.get(i);
        }
        return loConfig;
    }
    private int getIndiceConfig(final String psNombre){
        int lColumn = -1;
        for(int i = 0 ; i < moConfigs.size() && lColumn == -1;i++){
            if(getConfig(i).getNombre().toUpperCase().equals(psNombre.toUpperCase())){
                lColumn = i;
            }
        }
        return lColumn;
    }
    public JTablaConfigTablaConfig getConfig(final String psNombre){
        return getConfig(getIndiceConfig(psNombre));
    }
    public void addConfig(final JTablaConfigTablaConfig poColumn){
        moConfigs.add(poColumn);
    }
    public void removeConfig(final int i){
        moConfigs.remove(i);
    }
    public void removeConfig(final String psNombre){
        moConfigs.remove(getIndiceConfig(psNombre));
    }
    public String getNombre() {
        return msNombre;
    }

    public void setNombre(final String msNombre) {
        this.msNombre = msNombre;
    }
    public String getCampoBusqueda(){
        return msCampoBusqueda;
    }
    public void setCampoBusqueda(final String psCampoBusq){
        msCampoBusqueda = psCampoBusq;
    }   
    public JTFiltro getFiltroDefectoJT(){
        String lsFiltro=getFiltroDefecto();
        if(JCadenas.isVacio(lsFiltro)){
            lsFiltro=mcsSinFiltro;
        }
        return getFiltros().get(lsFiltro);
    }
    public JTFiltro getFiltro(String psFiltro){
        if(JCadenas.isVacio(psFiltro)){
            psFiltro=mcsSinFiltro;
        }        
        JTFiltro loFiltro=getFiltros().get(psFiltro);
        if(loFiltro==null){
            loFiltro=getFiltroDefectoJT();
        }
        
        return loFiltro;
    }
    public HashMap<String, JTFiltro> getFiltros(){
        JTFiltro loFiltro = moFiltros.get(mcsSinFiltro);
        if(loFiltro==null){
            loFiltro = new JTFiltro();
            loFiltro.moList.msTabla=mcsSinFiltro;
            moFiltros.put(mcsSinFiltro, loFiltro);
        }
        return moFiltros;
    }

    /**
     * @return the msFiltroDefecto
     */
    public String getFiltroDefecto() {
        return msFiltroDefecto;
    }

    /**
     * @param msFiltroDefecto the msFiltroDefecto to set
     */
    public void setFiltroDefecto(String msFiltroDefecto) {
        this.msFiltroDefecto = msFiltroDefecto;
    }

    public void addFiltro(JTFiltro poFiltro) throws Exception {
        if(getFiltros().get(poFiltro.moList.msTabla)!=null){
            throw new Exception("Filtro ya existe");
        }
        getFiltros().put(poFiltro.moList.msTabla, poFiltro);
    }
}
