/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxSeguridad.util;

import es.gob.afirma.keystores.main.common.AOKeyStoreManager;
import es.gob.afirma.keystores.main.common.KeyStoreUtilities;
import javax.swing.JLabel;
import utilesGUIxSeguridad.ICertificados;
import utilesGUIxSeguridad.JCertificadosSUN;


public class JCertificadoUtilGOB {

    //firma con Firma_facil_con_firma_v1.0.jar
    //http://forja-ctt.administracionelectronica.gob.es/web/clienteafirma
    public static ICertificados getCertificado(boolean pbEDNI) throws Exception{
        JCertificadosSUN moCertificado = new JCertificadosSUN();
        AOKeyStoreManager ksm = SimpleKeyStoreManager.getKeyStore(pbEDNI, new JLabel());
        String alias = KeyStoreUtilities.showCertSelectionDialog(ksm.getAliases(), ksm,
                                                            new JLabel(),
                                                            true,
                                                            true,
                                                            false);
        moCertificado.setKeyStore(ksm.getKeyStores().get(0));
        moCertificado.setX509Certificate(alias);
        return moCertificado;

    }    
}
