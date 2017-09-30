/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad;


import android.util.Base64;

import java.io.IOException;
import java.util.Vector;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.security.*;

import utiles.JDepuracion;
/**
 *
 * @author eduardo
 */
public abstract class JCertificadosAbstract implements ICertificados{
    protected transient String msPass;
    protected X509Certificate moX509 = null;

    public String getTransFormarABASE64(byte[] labFirma) {
        return Base64.encodeToString(labFirma, Base64.DEFAULT);
    }

    public byte[] getDesTransFormarABASE64(String lsFirmaBase64) throws IOException {
        return Base64.decode(lsFirmaBase64, Base64.DEFAULT);
    }

    public void setPassword(String psPass) throws Exception {
        msPass = psPass;
    }
    public String getPassword(){
        return msPass;
    }
    public X509Certificate[] getCertificateChain(String psAlias){
        try {
            
            X509Certificate[] certificados = new X509Certificate[]{getX509Certificate()};
            return certificados;
//            return ks.getCertificateChain(psAlias);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        return null;
}
    public X509Certificate getX509Certificate()  throws Exception {
        return moX509;
    }

    /**Devuelve el alias*/
    public String getAlias() throws Exception{
        if(moX509!=null){
            return getCertificateAlias(moX509);
        }else{
            return null;
        }
    }
    
    public String toString(){
        try {
            return getAlias();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    /**
     * Metodo que muestra por consola la informacion sobre los certificados pasados
     * @param listCertificates
     */
    public void mostrarInformacionCertificados(Vector listCertificates) {

        for (int a = 0; a < listCertificates.size(); a++) {
            X509Certificate certTemp = (X509Certificate) listCertificates.get(a);

            System.out.println("-----------------------------");
            
            //Emitido para
            System.out.println("Subject --> "
                    + certTemp.getSubjectDN().toString());

            //Emitido por
            System.out.println("Issuer -->"
                    + certTemp.getIssuerDN().toString());

            //Fecha de emision y de caducidad
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            String emision = formateador.format(certTemp.getNotBefore());
            System.out.println("Not Before -->" + emision);
            String caducidad = formateador.format(certTemp.getNotAfter());
            System.out.println("Not After -->" + caducidad);

            /*Usos permitidos del certificado
            Recogemos el keyUsage

            KeyUsage ::= BIT STRING {
            digitalSignature        (0),
            nonRepudiation          (1),
            keyEncipherment         (2),
            dataEncipherment        (3),
            keyAgreement            (4),
            keyCertSign             (5),
            cRLSign                 (6),
            encipherOnly            (7),
            decipherOnly            (8) }

            Usos del certificado:
            F Firma digital,N no repudio, Cc cifrado de claves,
            Cd cifrado de datos, Ac Acuerdo de claves, Fc Firma de certificados,
            Fcrl Firma de CRL, Sc Solo cifrado, Sf solo firma

             */

            String claveUsoString = "";
            String[] claveUsoArray = {"F", "N", "Cc", "Cd", "Ac", "Fc",
                "Fcrl", "Sc", "Sf"};
            boolean[] claveUso = certTemp.getKeyUsage();
            if (claveUso != null) {
                for (int z = 0; z < claveUso.length; z++) {
                    if (claveUso[z]) {
                        claveUsoString = claveUsoString + claveUsoArray[z] + ",";
                    }
                }
            } else {
                claveUsoString = "No definido ";
            }
            System.out.println("Key Usage -->"
                    + claveUsoString.substring(0, claveUsoString.length() - 1));
            System.out.println("-----------------------------");
        }
    }
    /**Verifica datos y firma*/
    public boolean verificar(byte[] pabDatos, byte[] pabFirma) throws Exception {
        Signature rsa_vfy = Signature.getInstance("SHA1withRSA");
        rsa_vfy.initVerify(getX509Certificate().getPublicKey());
        rsa_vfy.update(pabDatos);

        boolean lbResult = rsa_vfy.verify(pabFirma);

        return lbResult;
    }




}
