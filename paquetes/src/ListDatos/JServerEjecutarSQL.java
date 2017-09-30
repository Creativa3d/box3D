/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;

import utiles.IListaElementos;
import utiles.JListaElementos;

/**
 *
 * @author eduardo
 */
public class JServerEjecutarSQL extends JServerEjecutarAbstract implements IResultado {
    private boolean mbBien=true;
    private String msMensaje="";
    private JListDatos moResult;
    private final JSelect moSelect;
    
    public JServerEjecutarSQL(JSelect poSelect){
        moSelect = poSelect;
    }
    public JServerEjecutarSQL(String psSelect){
        moSelect = new JSelect();
        moSelect.msSelectAPelo=psSelect;
    }
    
    

    public IResultado ejecutar(IServerServidorDatos poServer) throws Exception {
        JServerServidorDatosBD loServer=null;
        if(poServer instanceof JServerServidorDatosBD){
            loServer = (JServerServidorDatosBD) poServer;
        } else if(poServer instanceof JServerServidorDatosBD){
            loServer = ((JServerServidorDatos)poServer).getServerBD();
        }
        if(loServer!=null){
            moResult = loServer.getListDatos(moSelect, "tabla");
            JServerEjecutarSQL lo = new JServerEjecutarSQL(moSelect);
            lo.moResult=moResult;
            return lo;
        }else{
            return poServer.ejecutarServer(this);
        }
    }

    public boolean getBien() {
        return mbBien;
    }

    public String getMensaje() {
        return msMensaje;
    }

    public IListaElementos getListDatos() {
        JListaElementos loLista = new JListaElementos();
        loLista.add(moResult);
        return loLista;
    }
    
    public JListDatos getListDatosResult(){
        return moResult;
    }
    
}
