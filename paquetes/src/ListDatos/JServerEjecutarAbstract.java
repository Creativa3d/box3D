/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos;


public abstract  class JServerEjecutarAbstract implements IServerEjecutar {
    private static final long serialVersionUID = 7692332096583789386L;

    
    protected JServerEjecutarParametros moParametros = new JServerEjecutarParametros() ;
    protected boolean mbComprimido;

    public String getXML() {
        return null;
    }

    public JServerEjecutarParametros getParametros(){
        return moParametros;
    }
    public boolean getComprimido() {
        return mbComprimido;
    }

    public void setComprimido(boolean pbValor) {
        mbComprimido = pbValor;
    }
}
