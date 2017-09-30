/*
 * JConstructorEstructuraBDInternet.java
 *
 * Created on 29 de mayo de 2006, 9:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesBD.estructuraBD;

import ListDatos.IResultado;
import ListDatos.IServerEjecutar;
import ListDatos.IServerServidorDatos;
import ListDatos.JServerEjecutarParametros;
import ListDatos.estructuraBD.IConstructorEstructuraBD;
import ListDatos.estructuraBD.JTableDefs;
import utiles.IListaElementos;

public class JConstructorEstructuraBDInternet implements IConstructorEstructuraBD, IResultado, IServerEjecutar {
    private static final long serialVersionUID = 33333351L;
    
    private transient final IServerServidorDatos moServer;
    private String msMensaje = "";
    private boolean mbBien=true;

    private boolean mbComprimido;
    private JTableDefs moTableDefs=null;
    private JServerEjecutarParametros moParametros = new JServerEjecutarParametros();
    
    /** Creates a new instance of JConstructorEstructuraBDInternet */
    public JConstructorEstructuraBDInternet(IServerServidorDatos poServer) {
        moServer = poServer;
    }

    public JTableDefs getTableDefs() throws Exception {
        if(moTableDefs==null){
            IResultado loResult = moServer.ejecutarServer(this);
            if(loResult.getBien()){
                moTableDefs = ((IConstructorEstructuraBD)loResult).getTableDefs();
            }else{
                msMensaje = loResult.toString();
                mbBien=false;
                throw new Exception(loResult.toString());
            }
        }
        return moTableDefs;
        
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
        return "";
    }

    public String getUsuario() {
        return "";
    }

    public String getPassWord() {
        return "";
    }

    public String getPermisos() {
        return "";
    }

    public boolean getComprimido() {
        return mbComprimido;
    }

    public void setComprimido(final boolean pbValor) {
        mbComprimido = pbValor;
    }

    public IResultado ejecutar(IServerServidorDatos poServer) throws Exception {
        moTableDefs = poServer.getTableDefs();
        return this;
    }

    public JServerEjecutarParametros getParametros() {
        return moParametros;
    }
    
}
