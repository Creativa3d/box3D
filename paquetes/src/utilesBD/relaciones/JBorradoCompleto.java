/*
 * JBorradoCompleto.java
 *
 * Created on 22 de noviembre de 2004, 9:30
 */

package utilesBD.relaciones;
import java.net.*;
import java.io.*;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.*;

/**Borrado completo de datos relaciondos*/
public class JBorradoCompleto {
    private final IServerServidorDatos moServer;
    private final IFormBorrado moFormBorrado;
    /**
     * Creates a new instance of JBorradoCompleto
     * @param poServer servidor de datos
     * @param psRelacionadosRegistros servicio para ver los registros relaciondos
     * @param psRelacionadosBorrar servicio para borrar los registros relaciondos
     * @param poFormBorrado formulario para presentar los registros relaciondos
     */
    public JBorradoCompleto(IServerServidorDatos poServer, IFormBorrado poFormBorrado) {
        moServer = poServer;
        moFormBorrado = poFormBorrado;
    }

    /**
     * Borra todos los registros relacionados
     * @return Resultado
     * @param poCampos lista de campos
     * @param psTabla tabla
     * @param pbConMensaje si presenta mensaje
     * @throws Exception error
     */
    public IResultado borrarTodo(JFieldDefs poCampos, String psTabla, boolean pbConMensaje) throws Exception {
        IResultado loResul = new JResultado("", true);
        JActualizar loActualizar;
        loActualizar = new JActualizar(poCampos, psTabla, JListDatos.mclBorrar,null,null,null);
        boolean lbContinuar = true;
        if(pbConMensaje){
            JRelacionadosRegistros loRegistros = new JRelacionadosRegistros(loActualizar);
            loResul = (IResultado)moServer.ejecutarServer(loRegistros);
            if(loResul.getBien()){
                JRelacionesTablasRegistros loRelac = (JRelacionesTablasRegistros)loResul;
                for(int i =0; (i<loRelac.moTablas.size())&&(lbContinuar);i++){
                    JRelacionTablaRegistros loTabla = (JRelacionTablaRegistros)loRelac.moTablas.get(i);
                    if(loTabla.moFilas.size()>0){
                        JComunicacion loComu = new JComunicacion();
                        loComu.moObjecto = new Boolean(false);
                        moFormBorrado.setTabla(loTabla, loComu);
                        moFormBorrado.show();
                        lbContinuar = ((Boolean)loComu.moObjecto).booleanValue();
                    }
                }
            }
        }
        if(loResul.getBien()){
            if(lbContinuar){
                JRelacionadosBorrar loBorrado = new JRelacionadosBorrar(loActualizar);
                loResul = moServer.ejecutarServer(loBorrado);
            }else{
                loResul = new JResultado("Borrado cancelado", false);
            }
        }
        return loResul;        
    }
}
