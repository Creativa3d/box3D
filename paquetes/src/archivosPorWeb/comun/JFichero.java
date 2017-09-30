/*
 * JFichero.java
 *
 * Created on 2 de mayo de 2006, 17:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.comun;

import java.io.File;
import java.io.Serializable;
import utiles.FechaMalException;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JDateEdu;

public class JFichero implements Serializable, Cloneable {
    private static final long serialVersionUID = 1110001L;
    
    private String msPathSolo;
    private boolean mbEsDirectorio;
    private transient IListaElementos moListaFicheros=null;
    private transient IServidorArchivos moServidor=null;
    private long mlLen=0;
    private String msNombreSolo;
    private String msPath;
    private boolean mbExiste;
    private JDateEdu moDateEdu;
    
    public JFichero(String psPathSolo, String psNombreSolo, boolean pbEsDirectorio, long plLen,JDateEdu poDateEdu) {
        msPathSolo = psPathSolo;
        if(msPathSolo == null || msPathSolo.equals("")){
            msPathSolo=".";
        }
        msPathSolo = msPathSolo.replace('\\','/');
        msNombreSolo = psNombreSolo;
        if(msNombreSolo==null){
            msNombreSolo="";
        }
        mbEsDirectorio = pbEsDirectorio;
        mlLen=plLen;
        if(!(msPathSolo.substring(msPathSolo.length()-1).compareTo("/")==0)) {
            msPathSolo=msPathSolo + "/";
        }
        msPath = msPathSolo + msNombreSolo;
        moDateEdu = poDateEdu;
    }

    public JFichero(JFichero poPathSolo, String psNombreSolo, boolean pbEsDirectorio) {
        this(poPathSolo, psNombreSolo, pbEsDirectorio, 0, new JDateEdu());
    }
    public JFichero(JFichero poPathSolo, String psNombreSolo, boolean pbEsDirectorio, long plLen,JDateEdu poDateEdu) {
        this(poPathSolo.getPath(), psNombreSolo, pbEsDirectorio, plLen, poDateEdu);
    }

    public JFichero(File poFile) throws FechaMalException {

        msPath = poFile.getPath().replace('\\','/');
        if(poFile.getParent()==null || poFile.getParent().equals("")){
            msPathSolo="";
        }else{
            msPathSolo = poFile.getParent().replace('\\','/');
        }
        if(msPathSolo==null){
            msPathSolo="";
        }
        msNombreSolo = poFile.getName();
        mbEsDirectorio = poFile.isDirectory();
        mlLen=poFile.length();
        if(msPathSolo.equals("")||
           !(msPathSolo.substring(msPathSolo.length()-1).compareTo("/")==0)) {
            msPathSolo=msPathSolo + "/";
        }
        moDateEdu = new JDateEdu("1/1/70 1:0:0");
        moDateEdu.add(moDateEdu.mclSegundos, (int)(poFile.lastModified()/1000));
    }

    public JDateEdu getDateEdu() {
        return moDateEdu;
    }
    
    public void setServidor(IServidorArchivos poServidor){
        moServidor=poServidor;
    }
    public long getLenght(){
        return mlLen;
    }
    public String getPath(){
        return msPath;
    }
    public void setPathRaiz(String psRaiz){
        if(!JCadenas.isVacio(psRaiz)){
            psRaiz = psRaiz.replace('\\','/');
            if(msPathSolo.equals("./")){
                msPathSolo="";
            }
            if(msPathSolo.length()<psRaiz.length()){
                msPathSolo =  psRaiz + msPathSolo;
            }else{
                if(!msPathSolo.substring(0, psRaiz.length()).equals(psRaiz)){
                    msPathSolo = psRaiz + msPathSolo;
                }
            }
            msPathSolo=msPathSolo.replace("//", "/");
            msPath = msPathSolo + msNombreSolo;
        }
    }
    public String getPathSinNombre(){
        return msPathSolo;
    }
    public String getNombre(){
        return msNombreSolo;
    }
    public void setNombre(String psNombre){
        msNombreSolo=psNombre;
        if(msNombreSolo==null){
            msNombreSolo="";
        }
        msPath = msPathSolo+msNombreSolo;
    }
    public boolean getEsDirectorio(){
        return mbEsDirectorio;
    }
    public IListaElementos getFicheros() throws Exception {
        if(mbEsDirectorio && moListaFicheros==null){
            moListaFicheros = moServidor.getListaFicheros(this);
        }
        return moListaFicheros;
    }
    public void refrescarListaFicheros(){
        moListaFicheros=null;
    }

    public void setExiste(boolean exists) {
        mbExiste = exists;
    }

    public boolean isExiste() {
        return mbExiste;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return getPath();
    }
}

