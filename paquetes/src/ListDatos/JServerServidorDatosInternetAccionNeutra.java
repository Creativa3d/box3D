/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos;

public class JServerServidorDatosInternetAccionNeutra extends JServerEjecutarAbstract {
    private static final long serialVersionUID = 1L;

    public IResultado ejecutar(IServerServidorDatos poServer) throws Exception {
        return new JResultado("", true);
    }

}
