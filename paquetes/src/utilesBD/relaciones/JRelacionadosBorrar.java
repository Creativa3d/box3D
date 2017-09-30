/*
 * ABorrarCascada.java
 *
 * Created on 18 de noviembre de 2004, 11:22
 */

package utilesBD.relaciones;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.util.Iterator;
import utiles.*;
import utiles.config.*;

/**borra los registros relacionados*/
public class JRelacionadosBorrar extends JServerEjecutarAbstract {
    private static final long serialVersionUID = 33333342L;
    
    JActualizar moActu = null;
    private boolean mbComprimido=false;
    /**
     * Creates a new instance of ABorrarCascada
     * @param psDir directorio base
     */
    public JRelacionadosBorrar(JActualizar poActu) {
        moActu=poActu;
    }

    public IResultado ejecutar(IServerServidorDatos poServer) throws Exception {
        JResultado loResultado = new JResultado("", true);
        

        //recuperamos todos los registros relacionados
        JRelacionesTablasRegistros loRelaciones = new JRelacionesTablasRegistros();
        JRelacionadosRegistros loRelac = new JRelacionadosRegistros(moActu);
        loRelac.verRelaciones(loRelaciones, moActu, poServer);
        //creamos el consjunto de registros a borrar
        JActualizarConj loAct = new JActualizarConj(null,null,null);
        for(int i =(loRelaciones.moTablas.size()-1); i >= 0 ;i--){
            JRelacionTablaRegistros loTablaR=(JRelacionTablaRegistros)loRelaciones.moTablas.get(i);
            Iterator loEnum = loTablaR.moFilas.iterator();
            for(;loEnum.hasNext();){
                IFilaDatos loFila = (IFilaDatos)loEnum.next();
                JFieldDefs loFields = loTablaR.moFields.Clone();
                loFields.cargar(loFila);
                loAct.add(loFields, loTablaR.msTabla, JListDatos.mclBorrar);
            }
            loResultado.addResultado(poServer.ejecutarServer(loAct));
        }
        //anadimos el registro a borrar
        loResultado.addResultado(poServer.ejecutarServer(moActu));
        return loResultado;
    }

    public String getXML() {
        throw new InternalError("Todavia no implementado la parte XML");
    }

    public boolean getComprimido() {
        return mbComprimido;
    }

    public void setComprimido(boolean pbValor) {
        mbComprimido = pbValor;
    }
    
}
