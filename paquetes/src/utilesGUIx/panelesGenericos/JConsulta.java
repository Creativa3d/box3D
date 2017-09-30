/*
 * JConsulta.java
 *
 * Created on 27 de octubre de 2004, 9:14
 */

package utilesGUIx.panelesGenericos;

import utilesGUIx.formsGenericos.busqueda.*;
import ListDatos.*;

/**Consulta para buscar*/
public class JConsulta implements IConsulta {
    private final JSTabla moTabla;
    private final JListDatos moListDatos;
    private final boolean mbConDatos;
    /**
     * Creates a new instance of JConsulta
     * @param poTabla datos
     */
    public JConsulta(JSTabla poTabla, boolean pbConDatos) {
        moTabla=poTabla;
        moListDatos=poTabla.moList;
        mbConDatos = pbConDatos;
    }
    public JConsulta(JListDatos poTabla, boolean pbConDatos) {
        moTabla=null;
        moListDatos=poTabla;
        mbConDatos = pbConDatos;
    }

    public void addFilaPorClave(IFilaDatos poFila) throws Exception {
        switch(poFila.getTipoModif()){
            case JListDatos.mclBorrar:
                moListDatos.borrar(false);
                break;
            case JListDatos.mclEditar:
                moListDatos.getFields().cargar(poFila);
                moListDatos.update(false);
                break;
            case JListDatos.mclNuevo:
                moListDatos.addNew();
                moListDatos.getFields().cargar(poFila);
                moListDatos.update(false);
                break;
        }
    }

    public ListDatos.JListDatos getList() {
        return moListDatos;
    }

    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
        if(!mbConDatos || pbLimpiarCache) {
            if(moTabla!=null){
                moTabla.recuperarTodos(pbPasarACache, false, pbLimpiarCache);
            }else{
                JSelect loSelect = moListDatos.getSelect();
                moListDatos.recuperarDatos(loSelect, pbPasarACache, JListDatos.mclSelectNormal, pbLimpiarCache);
            }
        }
    }

    public boolean getPasarCache() {
        return false;
    }
    
}
