/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxSeguridad.util;

import java.awt.Frame;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import utiles.IListaElementos;
import utiles.JComunicacion;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIxSeguridad.ICertificados;
import utilesGUIxSeguridad.JCertificadosSUN;
import utilesGUIxSeguridad.JFrameSeleccionarCertificado;


public class JCertificadoUtil {
    public static final String mcsWindowsFirmaProfesional2="c:/windows/system32/bit4idpki.dll";
    public static final String mcsWindowsFirmaProfesional1="c:/windows/system32/aetpkss1.dll";
    public static final String mcsWindowsUM="c:/WINDOWS/system32/UMU_PKCS11_v1_02.dll";
    public static final String mcsWindowsEDNI="c:/WINDOWS/system32/UsrPkcs11.dll";
    public static final String mcsWindowsFNMT="c:/WINDOWS/system32/pkcsv2gk.dll";
    public static final String mcsLinuxEDNI="/usr/local/lib/pkcs11-spy.so";
    public static final String mcsLinuxEDNI2="/usr/lib/opensc-pkcs11.so";
    
    public static final String[] mcasListaFirmas = new String[]{
        mcsWindowsFirmaProfesional2,mcsWindowsFirmaProfesional1,
        mcsWindowsUM,mcsWindowsEDNI,mcsLinuxEDNI,mcsLinuxEDNI2,mcsWindowsFNMT
    };

    public static KeyStore createKeyStore_DLLyProp(final String psCompleto, final String psPassWord) throws Exception{
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JCertificadosSUN.class.getName(), psCompleto);
        InputStream loConf = new ByteArrayInputStream(psCompleto.getBytes());
        //Creamos el Provider con la clase SunPKCS11
        Provider nss = new sun.security.pkcs11.SunPKCS11(loConf);
        Security.addProvider(nss);
        KeyStore ks = KeyStore.getInstance("PKCS11", nss);
        ks.load(null,psPassWord.toCharArray());
        
        return ks;
        
    }
    public static KeyStore createKeyStore_DLLRuta(final String psName, final String psRutaDLL, final String psPassWord) throws Exception{
        String ls =
                "name = "+psName + "\r\n"
                + "library = " + psRutaDLL + "\r\n";
        return createKeyStore_DLLyProp(ls, psPassWord);
    }
    public static KeyStore createKeyStore_Linux_EDNI(final String psPassWord) throws Exception{
        //#library=/usr/local/lib/opensc-pkcs11.so
        String ls =
                "name = dnie" + "\n"
                + "library="+mcsLinuxEDNI+"\n";
        return createKeyStore_DLLyProp(ls, psPassWord);
    }

    public static KeyStore createKeyStore_EDNI(final String psPassWord) throws Exception{

        return createKeyStore_DLLRuta("edni",mcsWindowsEDNI, psPassWord);
    }

    public static KeyStore createKeyStore_UM(final String psPassWord) throws Exception{
        String ls =
                "name = UM" + "\n"
                + "library = "+mcsWindowsUM + "\n";
        return createKeyStore_DLLyProp(ls, psPassWord);
    }
    
    public static KeyStore createKeyStore_FirmaProfesional1(final String psPassWord) throws Exception{
        String ls =
                "name = FirmaProfesional1" + "\n" +
                "library = "+mcsWindowsFirmaProfesional1 + "\n";
        return createKeyStore_DLLyProp(ls, psPassWord);
    }

    public static KeyStore createKeyStore_FirmaProfesional2(final String psPassWord) throws Exception{
        String ls =
                "name = FirmaProfesional2" + "\n" +
                "library = "+mcsWindowsFirmaProfesional2 + "\n";
        return createKeyStore_DLLyProp(ls, psPassWord);
    }
    
    public static  void getCertificado(final String psDLL, final CallBack<ICertificados> poCallBack) throws Exception{
        
        final JComunicacion loComu = new JComunicacion();
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(new JPanelClave(loComu), 640, 480, JMostrarPantalla.mclEdicionFrame, "");
        loParam.setCallBack(new CallBack<JMostrarPantallaParam>() {
            public void callBack(JMostrarPantallaParam poControlador) {
                String lsClave="";
                if (loComu.moObjecto != null && !loComu.moObjecto.equals("")) {
                    lsClave=loComu.moObjecto.toString();
                }
                JCertificadosSUN loCertificado = new JCertificadosSUN();
                try{
                    loCertificado.setPassword(lsClave);
                    IListaElementos loLista;
                    KeyStore ks;
                    if(psDLL==null || psDLL.equals("")){
                        loLista = getListaDLLExistentes();
                        if(loLista.size()==0){
                            //TODO OJO: que pueda indicar la ruta
                            throw new Exception("No existe dispositivo de certificado digital");
                        }else if(loLista.size()==1){
                            ks = createKeyStore_DLLRuta("dnicert",loLista.get(0).toString(),lsClave);
                        }else{
                            //TODO OJO: que pueda seleccionar uno el usuario
                            ks = createKeyStore_DLLRuta("dnicert",loLista.get(0).toString(),lsClave);

                        }
                    }else{
                        ks = createKeyStore_DLLRuta("DNIe", psDLL, lsClave);
                    }
                    //establecemos el keystore
                    loCertificado.setKeyStore(ks);
                }catch(Throwable e){
                    JDepuracion.anadirTexto(JCertificadoUtil.class.getName(), e);
                }
                //seleccionamos un certificado
                new JFrameSeleccionarCertificado(new Frame(), loCertificado).setVisible(true);
                poCallBack.callBack(loCertificado);

            }
        });
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(new JPanelClave(loComu), 640, 480, JMostrarPantalla.mclEdicionDialog);

    }
    public static IListaElementos getListaDLLExistentes(){
        IListaElementos loLista = new JListaElementos();
        for(int i = 0 ; i < mcasListaFirmas.length; i++){
            if(new File(mcasListaFirmas[i]).exists()){
                loLista.add(mcasListaFirmas[i]);
            }
        }
        return loLista;
        
    }
}
