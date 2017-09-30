/*
 * JCopiar.java
 *
 * Created on 7 de octubre de 2004, 15:54
 */

package CopiaSeguridad.file;

import java.io.*;
import java.util.zip.*;  
import java.util.*;  
import utiles.*;

public class JFicheroDirec {
    public static final int mclSinSobreescribir = 0;
    public static final int mclSobreescribir = 1;
    public static final int mclSegunFicheroFecha = 2;
    
    private File moFile;
    /** Creates a new instance of JCopiar */
    public JFicheroDirec() {
    }
    public boolean isDirectory(){
        return moFile.isDirectory();
    }
    public void setFichero(File poFile){
        moFile = poFile;
    }
    public String getFecha(){
        return "";
    }
    public void crearDirectorios(){
        moFile.mkdirs();
    }
    private String msQuitarRaritos(String psNombreOriginal, String psPatron){
        String lsNombre = psNombreOriginal;
        for(int i = 0;i < psPatron.length(); i=i+2){
            lsNombre = lsNombre.replace(psPatron.charAt(i) ,psPatron.charAt(i+1));
        }
        return lsNombre;
    }
    public void renombrarquitarRaros(String psPatron){
        String lsNombre;
        if(moFile.isDirectory()){
            lsNombre = msQuitarRaritos(moFile.getPath(), psPatron);
            if(lsNombre.compareTo(moFile.getPath())!=0){
                JGeneral.print(moFile.getPath() + "->" + lsNombre);
                File loFileAux = new File(lsNombre);
                moFile.renameTo(loFileAux);
                moFile = loFileAux;
            }
            JGeneral.print("Directorio " +  moFile.getPath());
            //lista de ficheros
            File[] files = moFile.listFiles();
            int i = 0;
            int lNumero = 0;
            File loFile;
            JFicheroDirec loFicheDir = new JFicheroDirec();
            for (; i<files.length;i++){
                loFile = files[i];
                loFicheDir.setFichero(loFile);
                loFicheDir.renombrarquitarRaros(psPatron);
            }
            System.gc();
        }else{
            lsNombre = msQuitarRaritos(moFile.getName(),psPatron);
            if(lsNombre.compareTo(moFile.getName())!=0){
                JGeneral.print(moFile.getName() + "->" + lsNombre);
                moFile.renameTo(new File(moFile.getParentFile().getPath(), lsNombre));
            }
        }
        
    }
    public void copiar(JFicheroDirec poDir, int plTipo) {
        copiar(poDir, plTipo, JFicheroFiltro.getFiltroNulo(), poDir) ;
    }
    public void mover(JFicheroDirec poDir, FilenameFilter poPatron) {
        //creamos los directorios hasta aqui
        poDir.crearDirectorios();

        if(moFile.isDirectory()){
            //subimos en un directorio
            JFicheroDirec loDirDestino = new JFicheroDirec();
            loDirDestino.setFichero(new File(poDir.getPath() + "/" + moFile.getName()));
            //lista de ficheros
            File[] files = moFile.listFiles(poPatron);
            int i = 0;
            int lNumero = 0;
            File loFile;
            JFicheroDirec loFicheDir = new JFicheroDirec();
            for (; i<files.length;i++){
                loFile = files[i];
                loFicheDir.setFichero(loFile);
                loFicheDir.mover(loDirDestino, poPatron);
            }
            System.gc();
        }else{
            boolean lbContinuar = true;
            //comprobamos si existe el fichero en el destino si es asi lo borramos previamente
            File loFileDestino  = new File(poDir.getPath(), moFile.getName() + ".gz");
            if(loFileDestino.exists()) {
                if (!loFileDestino.delete()){
                    lbContinuar=false;
                    JGeneral.print("No se a podido borrar el fichero " + loFileDestino.getName());
                }
            }
            if(lbContinuar){
                try{
                    copiar(moFile, loFileDestino, null);
                    moFile.delete();//borramos el fichero una vez copiado
                }catch(Exception e){
                    e.printStackTrace();
                    JGeneral.print("No se a podido copiar el fichero " + moFile.getName() + "("+e.toString()+")");
                }
            }
        }
    }
    public void copiar(JFicheroDirec poDir, int plTipo, FilenameFilter poPatron, JFicheroDirec poDirRef) {
        //creamos los directorios hasta aqui
        poDir.crearDirectorios();

        if(moFile.isDirectory()){
            //subimos en un directorio
            JFicheroDirec loDirDestino = new JFicheroDirec();
            JFicheroDirec loDirRef = new JFicheroDirec();
            loDirDestino.setFichero(new File(poDir.getPath() + "/" + moFile.getName()));
            loDirRef.setFichero(new File(poDirRef.getPath() + "/" + moFile.getName()));
            //lista de ficheros
            File[] files = moFile.listFiles(poPatron);
            int i = 0;
            int lNumero = 0;
            File loFile;
            JFicheroDirec loFicheDir = new JFicheroDirec();
            for (; i<files.length;i++){
                loFile = files[i];
                loFicheDir.setFichero(loFile);
                loFicheDir.copiar(loDirDestino, plTipo, poPatron, loDirRef);
            }
            System.gc();
        }else{
            boolean lbContinuar = true;
            //comprobamos si existe el fichero en el destino si es asi lo borramos previamente
            File loFileDestino  = new File(poDir.getPath(), moFile.getName() + ".gz");
            switch(plTipo){
                case mclSinSobreescribir:
                case mclSobreescribir:
                    if(loFileDestino.exists()) {
                        if(plTipo==mclSobreescribir) {
                            if (!loFileDestino.delete()){
                                lbContinuar=false;
                                JGeneral.print("No se a podido borrar el fichero " + loFileDestino.getName());
                            }
                        }else{
                            lbContinuar=false;
                            JGeneral.print("No se a podido borrar el fichero " + loFileDestino.getName());                    
                        }
                    }
                    if(lbContinuar){
                        try{
                            copiar(moFile, loFileDestino, null);
                        }catch(Exception e){
                            e.printStackTrace();
                            JGeneral.print("No se a podido copiar el fichero " + moFile.getName() + "("+e.toString()+")");
                        }
                    }
                    break;
                case mclSegunFicheroFecha:
                    File loFileDestinoTXT = new File(poDirRef.getPath(), moFile.getName() + ".txt");
                    try{
                        DataInputStream in = new DataInputStream(new FileInputStream(loFileDestinoTXT));
                        long lTime = in.readLong();
                        in.readChar();
                        long lLen = in.readLong();
                        lbContinuar = (lLen != moFile.length())||
                                      (lTime != moFile.lastModified());
                        
                    }catch(Exception e1){
                        lbContinuar = true;
                    }
                    loFileDestinoTXT = new File(poDir.getPath(), moFile.getName() + ".txt");
                    if(lbContinuar) {
                        try{
                            copiar(moFile, loFileDestino, loFileDestinoTXT);
                        }catch(Exception e){
                            e.printStackTrace();
                            JGeneral.print("No se a podido copiar el fichero " + moFile.getName() + "("+e.toString()+")");
                        }
                    } else {
                        JGeneral.print("No necesario copiar " + moFile.getName());
                    }
                    break;
            }

        }
    }
    public String getPath(){
        return moFile.getAbsolutePath();
    }
    private void copiar(File poFile, File poFileDestino, File poTXT) throws Exception {
        if(JGeneral.getEsPorSistemaLinux()){
            java.lang.Runtime loRun = java.lang.Runtime.getRuntime();
            
            Process loProceso;
            loProceso = loRun.exec(new String[]{
                    "zip", poFileDestino.getPath(), poFile.getPath()
                    });
            try {
                loProceso.waitFor();
            }catch (InterruptedException error) {
                error.printStackTrace(); 
            }
        }else{
            //creamos los objetos de entrada y salida
            FileInputStream loFOrigen = new FileInputStream(poFile);
            FileOutputStream loFDestino = new FileOutputStream(poFileDestino);
            GZIPOutputStream loGDestino = new GZIPOutputStream(loFDestino);

            //pasamos byte a byte de un fichero al otro
            long lLongTamano = 0;
            byte[] b1 = new byte[JGeneral.getBuffer()];
            int lLen = -1;
            while((lLen=loFOrigen.read(b1))!=-1){
                loGDestino.write(b1, 0, lLen);
            }

            //cerramos los objetos de entrada salida
            loFOrigen.close();
            loGDestino.close();
            loFDestino.close();
            //imprimirmos que se ha copìado
            JGeneral.print(poFile.getAbsoluteFile().toString());
            
            if(poTXT != null){
                //borramos el fichero de texto si no existe
                if(poTXT.exists())
                    poTXT.delete();
                
                //guardamos milisegundos, long. del fichero, fecha formato dd/mm/yyyy hh,mm,ss
                FileOutputStream loFTXT = new FileOutputStream(poTXT);
                DataOutputStream out = new DataOutputStream(loFTXT);
                Date loDate = new Date();
                loDate.setTime(poFile.lastModified());
                JDateEdu loDateE = new JDateEdu();
                loDateE.setDate(loDate);
                out.writeLong(poFile.lastModified());
                out.writeChar('\n');
                out.writeLong(poFile.length());
                out.writeChar('\n');
                out.writeBytes(loDateE.toString());
                
                //cerramos los ficheros
                out.close();
                loFTXT.close();
            }

        }
    }
}
