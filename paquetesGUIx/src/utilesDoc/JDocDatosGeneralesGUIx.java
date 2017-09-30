/*
* JDatosGenerales.java
*
* Creado el 3/11/2011
*/

package utilesDoc;

import utilesDoc.tablasExtend.IImagenFactoryDoc;
import utilesDoc.tablasExtend.JDocImagenBasicaFactory;


public class JDocDatosGeneralesGUIx extends JDocDatosGeneralesModelo {

    /** Creates a new instance of JUsuarioActual
     * @throws java.lang.Exception */
    public JDocDatosGeneralesGUIx() throws Exception {
        super();
    }    

    /**
     * @return the moImagenBasicaFactory
     */
    @Override
    public synchronized IImagenFactoryDoc getImagenBasicaFactory() {
        if(moImagenBasicaFactory==null){
            moImagenBasicaFactory = new JDocImagenBasicaFactory(
                        getServidorArchivos(), getServer());
        }
        moImagenBasicaFactory.setServer(getServer());
        return moImagenBasicaFactory;
    }
    
    
}
