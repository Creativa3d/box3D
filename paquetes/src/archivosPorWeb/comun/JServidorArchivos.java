/*
 * JServidorArchivos.java
 *
 * Created on 3 de mayo de 2006, 16:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.comun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JServidorArchivos extends JServidorArchivosAbstract {

    private String msRaiz;
    
    /** Creates a new instance of JServidorArchivos */
    public JServidorArchivos() {
    }
    public JServidorArchivos(String psRaiz) {
        msRaiz = psRaiz;
    }

    @Override
    public JFichero getFichero(JFichero poFichero) throws Exception {
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        File loFile = new File(poFichero.getPath());
        JFichero loFichero = new JFichero(loFile);
        loFichero.setExiste(loFile.exists());
        loFichero.setServidor(this);
        return loFichero;
    }
    @Override
    public IListaElementos<JFichero> getListaFicheros(JFichero poFichero) throws Exception {
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        File loFile = new File(poFichero.getPath());
        IListaElementos<JFichero> loLista = new JListaElementos<JFichero>();
        if(loFile.isDirectory()){
            //lista de ficheros
            File[] files = loFile.listFiles();
            int i = 0;
            File loFileAux;
            for (; i<files.length;i++){
                loFileAux = files[i];
                JFichero loFichero = new JFichero(loFileAux);
                loFichero.setServidor(this);
                loLista.add(loFichero);
            }
        }
        return loLista;
    }
    
    @Override
    public void copiarFichero(IServidorArchivos poServidorOrigen , JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
        poFicherodestino=(JFichero) poFicherodestino.clone();
        poFicherodestino.setPathRaiz(msRaiz);
        File loFile = new File(poFicherodestino.getPath());
        loFile.createNewFile();
        FileOutputStream loOut = null;
        InputStream loIn=null;
        try{
            loIn = poServidorOrigen.getFlujoEntrada(poFicheroOrigen);
            loOut = new FileOutputStream(loFile);
            byte[] loBuffer = new byte[2000];
            int len=0;
            while((len=loIn.read(loBuffer))!=-1){
                loOut.write(loBuffer, 0, len);
            }
        }finally{
            if(loOut!=null){
                loOut.close();
            }
            if(loIn!=null){
                loIn.close();
            }
        }
    }
    
    @Override
    public void borrar(JFichero poFichero) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        File loFile = new File(poFichero.getPath());
        if(!loFile.delete()){
            throw new Exception("Fichero no borrado");
        }
    }
    @Override
    public void moverFichero(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
        copiarA(poServidorOrigen, poFicheroOrigen, poFicherodestino);
        poServidorOrigen.borrar(poFicheroOrigen);
    }
    @Override
    public InputStream getFlujoEntrada(JFichero poFichero) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        return new FileInputStream(new File(poFichero.getPath()));
    }
    @Override
    public OutputStream getFlujoSalida(JFichero poFichero, boolean pbAppend) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        return new FileOutputStream(new File(poFichero.getPath()), pbAppend);
    }
    @Override
    public void crearCarpeta(JFichero poFichero) throws Exception{
        poFichero=(JFichero) poFichero.clone();
        poFichero.setPathRaiz(msRaiz);
        File loFile = new File(poFichero.getPath());
        loFile.mkdir();
    }
}
