/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.Vector;
import utilesGUIxSeguridad.Rectangulo;

/**
 *
 * @author eduardo
 */
public interface ICertificados {
  /**Devuelve un vector de X509Certificate*/
    public Vector getListaCertificados() throws Exception ;
    /**Devuelve el alias*/
    public String getAlias() throws Exception;
    /**Establece el certificado a traves del alias*/
    public void setX509Certificate(String psAlias) throws Exception ;
    /**Establece el certificado X509Certificate*/
    public void setX509Certificate(X509Certificate poParam)  throws Exception ;

    /**Devuelve el certificado X509*/
    public X509Certificate getX509Certificate()  throws Exception;
    
    /**Devuelve cadena de certificados*/
    public X509Certificate[] getCertificateChain(String psAlias);

    /**Transforma la entrada array de bytes en cadena */
    public String getTransFormarABASE64(byte[] labFirma) ;

    /**Transforma la cadena de entrada array de bytes origianl*/
    public byte[] getDesTransFormarABASE64(String lsFirmaBase64) throws IOException ;

    /**Devuelve la firma de los datos pasados por parametro*/
    public byte[] sign(byte[] labDatos) throws Exception ;

    /**Verifica datos y firma*/
    public boolean verificar(byte[] labDatos, byte[] b) throws Exception ;

    /**
     * Método que muestra por consola la informacion sobre los certificados pasados
     * @param listCertificates
     */
    public void mostrarInformacionCertificados(Vector listCertificates);

    /**Devuelve el privatekey*/
    public PrivateKey getPrivateKey() throws Exception;

    /**Devuelve el alias del certificado*/
    public String getCertificateAlias(X509Certificate poCert) throws Exception;

    /**Establecemos el password para recuperar el PrivateKey*/
    public void setPassword(String psPass) throws Exception;
    public String getPassword();

    public Provider getProvider();
    /**
     * Firmar un PDF
     * @param poPDFOriginal Flujo de lectura del PDF a firmar
     * @param poPDFSalida Flujo de escritura en donde se guardara el PDF firmado
     * @param psRazon Razon de la firma (opcional)
     * @param psLocalizacion Localizacion en donde se firmo (opcional)
     * @param poRect Rectangulo que indica la localizacion visual en el PDF firmado (Opcional), si no se indica la firma es invisible
     * @param plNumeroPagina pagina en donde sepone el rectangulo de la firma
     * @throws java.lang.Exception
     *
     */
    public void fimarPDF(InputStream poPDFOriginal, OutputStream poPDFSalida, String psRazon, String psLocalizacion, Rectangulo poRect, int plNumeroPagina) throws Exception;
    /**
     * Verificar un PDF
     * @param poPDFFirmado Flujo de lectura del PDF a verificar firma
     * @param poPDFRevision Flujo de escritura en donde se guardara el PDF revisado/original sin firmar (Opcional)
     * @throws java.lang.Exception
     */
    public void verificarPDF(InputStream poPDFFirmado, OutputStream poPDFRevision) throws Exception;
}
