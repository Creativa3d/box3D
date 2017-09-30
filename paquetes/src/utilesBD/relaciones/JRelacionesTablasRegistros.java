/*
 * JRelacionesTablasRegistros.java
 *
 * Created on 18 de noviembre de 2004, 14:01
 */

package utilesBD.relaciones;

import ListDatos.*;
import java.util.Iterator;
import utiles.*;

/**Devuelve las tablas relacionadas a una base*/
public class JRelacionesTablasRegistros implements IResultado {
    private static final long serialVersionUID = 33333344L;
    public String msMensaje;
    public boolean mbBien=true;
    /**Lista de tablas relacionadas*/
    public JListaElementos moTablas = new JListaElementos();
    
    /**
     * devuelve la primera tabla relacionda a la tabla psTabla
     * @param psTabla tabla
     * @return relacion 
     */
    public JRelacionTablaRegistros getTablaRelacionada(String psTabla){
        JRelacionTablaRegistros loTablaRelac = null;
        Iterator loEnum = moTablas.iterator();
        for(;(loEnum.hasNext())&&(loTablaRelac==null);){
            JRelacionTablaRegistros loTabla = (JRelacionTablaRegistros)loEnum.next();
            if(loTabla.isTabla(psTabla)){
                loTablaRelac=loTabla;
            }
        }
        return loTablaRelac;
    }

    /**
     * anade la tabla a la lista
     * @param psTabla tabla
     * @return almacion de registros de la relacion creada
     */
    public JRelacionTablaRegistros addTablaRelacionada(String psTabla){
        JRelacionTablaRegistros loRe = new JRelacionTablaRegistros(psTabla);
        moTablas.add(loRe);
        return loRe;
    }
    public boolean getBien() {
        return mbBien;
    }

    public String getMensaje() {
        return msMensaje;
    }

    public IListaElementos getListDatos() {
        return null;
    }

    public String getXML() {
        throw new InternalError("Todavia no implementado la parte XML");
    }
    
}
