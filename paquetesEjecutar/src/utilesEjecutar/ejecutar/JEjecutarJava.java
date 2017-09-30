/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesEjecutar.ejecutar;

import com.sun.JApplication;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;

/**
 * Ejecuta una instruccion tipo java en la misma jvm
 */
public class JEjecutarJava implements IEjecutarInstruccion {
    private static final String mcsParametro = "parametro";
    private static final String mcsMain = "main";
    private static final String mcsNombre = "nombre";
    private static final String mcsEsperarATerminar = "EsperarATerminar";
    private static final String mcsClassPath = "classpath";
    private static final String mcsCP = "cp";
    private static final String mcsRutaBase="RutaBase";
    private static final String mcsJVMCompartida="JVMCompartida";
    private static final String mcsenable = "enable";
    private static final String mcsrepetirEjecutarSeg = "repetirEjecutarSeg";

    private JControladorCoordinadorEjecutar moCoordinador;

    private String msMain;
    private String msNombre;
    private IListaElementos moParametros = new JListaElementos();
    private boolean mbEsperarATerminar=false;
    private String msClassPath;
    private String msRutaBase;
    private boolean mbJVMCompartida=false;
    private boolean mbHabilitada=true;
    private int mnRepetirEjecutarSeg = -1; //Este es el caso por defecto y no se repite nunca

    private ClassLoader moClassLoader;
    private String msClassPathCompleto;

    public JEjecutarJava(JControladorCoordinadorEjecutar poCoordinador) {
        moCoordinador = poCoordinador;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsMain)){
                msMain = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsParametro)){
                moParametros.add(loAtrib.getValor());
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsClassPath) ||
               loAtrib.getName().trim().equalsIgnoreCase(mcsCP)){
                msClassPath = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsRutaBase)){
                msRutaBase = JControladorCoordinadorEjecutar.getDirLocalconSeparadorCorrecto(loAtrib.getValor());
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEsperarATerminar)){
                mbEsperarATerminar = loAtrib.getValor().trim().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsJVMCompartida)){
                mbJVMCompartida = loAtrib.getValor().trim().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsenable)){
                mbHabilitada = loAtrib.getValor().trim().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsrepetirEjecutarSeg)){
                mnRepetirEjecutarSeg = new Integer(loAtrib.getValor()).intValue();
            }

        }
    }
    public String getNombre() throws Throwable {
        return (msNombre == null  || msNombre.equals("") ? msMain : msNombre);
    }

    public boolean ejecutar() throws Throwable {
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Comienzo Ejecutando aplicación " + getNombre());

        if(msRutaBase!=null && !msRutaBase.equals("")){
            crearclassLoader(new File(msRutaBase));
        }else{
            crearclassLoader(null);
        }
        final String[] finalArgs = new String[moParametros.size()];
        for(int i = 0 ; i < moParametros.size(); i++){
            finalArgs[i] = (String) moParametros.get(i);
        }
        if(!moCoordinador.isCancelado()){
            moCoordinador.addTextoGUI("Ejecutando aplicación " + getNombre());

            //no funciona, en doc. de internet dicen q es imposible
//            System.setProperty("user.dir", msRutaBase);
            if(mbJVMCompartida){

                String[] lsArg = new String[moParametros.size() + 3];
                lsArg[0]="-classpath";
                lsArg[1]=msClassPathCompleto;
                lsArg[2]=msMain;
                for(int i = 0 ; i < moParametros.size(); i++){
                    lsArg[i+3] = (String) moParametros.get(i);
                }
                JApplication.comunicar(lsArg);
            }else{
                System.setSecurityManager(new SecurityManager(){
                    public void checkPermission(java.security.Permission p) {
                    }
                    public void checkPermission(java.security.Permission perm, Object context) {
                        
                    }
                });
                if(mbEsperarATerminar){
                    Method main = moClassLoader.loadClass(msMain).getMethod("main", new Class[]{String[].class});
                    main.invoke(null, new Object[] {finalArgs});
                }else{
                    new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Method main = moClassLoader.loadClass(msMain).getMethod("main", new Class[]{String[].class});
                                    main.invoke(null, new Object[] {finalArgs});
                                } catch (Throwable e) {
                                    moCoordinador.addError(JEjecutarJava.class.getName(),e,true);
                                    JOptionPane.showMessageDialog(null, e.toString());
                                }
                            }
                        }).start();
                }
            }
        }
        return false;
    }

    private URL[] toURLs(File[] files) throws Throwable {
        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                urls[i] = new URL("jar:file:" + files[i].getPath() + "!/");
            } catch (Throwable e) {
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        return urls;
    }

    private Collection findFilesRecursively(File directory) {
        Collection files = new ArrayList();
        String[] lasFiles = directory.list();
        for (int i = 0 ; i < lasFiles.length; i++) {
            File file = new File(directory.getAbsolutePath(),lasFiles[i]);
            if (file.isDirectory() && file.getName().equalsIgnoreCase("lib")) {//solo cojemos los jar dentro del lib
                files.addAll(findFilesRecursively(file));
            }
            if(file.isFile() &&
               (file.getName().toLowerCase().lastIndexOf(".jar")>0 ||
                file.getName().toLowerCase().lastIndexOf(".zip")>0)){
                files.add(file);
            }
        }
        return files;
    }

    private void crearclassLoader(File poFileBase) throws Throwable {
        URL[] loURLs = null;
        URL[] loURLs2 = null;
        //urls recursivas a partir del diorectorio base
        if(poFileBase!=null && poFileBase.isDirectory()){
            loURLs = toURLs(
                        (File[])findFilesRecursively(poFileBase)
                            .toArray(new File[] {}));
        }
        //urls del classpath
        if(msClassPath!=null && !msClassPath.equals("")){
            StringTokenizer izer = new StringTokenizer(msClassPath, ";");
            List urls = new LinkedList();
            while (izer.hasMoreTokens()) {

                String urlString = izer.nextToken();
                URL u;
                if (!urlString.startsWith("http:") &&
                    !urlString.startsWith("file:") &&
                    !urlString.startsWith("jar:")) {
                    File f = new File(urlString).getCanonicalFile();
                    u = f.toURI().toURL();
                } else {
                    u = new URL(urlString);
                }
                urls.add(u);
            }
            loURLs2 = new URL[urls.size()];
        }
        //se juntan las 2 urls
        if(loURLs==null){
            loURLs = loURLs2;
        }else if (loURLs2!=null){
            URL[] loURLsAux = new URL[loURLs.length+loURLs2.length];
            int i = 0;
            for(i = 0 ; i < loURLs.length; i++){
                loURLsAux[i] = loURLs[i];
            }
            int lMax = i;
            for(i = 0 ; i < loURLs2.length; i++){
                loURLsAux[lMax+i] = loURLs2[i];
            }
            loURLs = loURLsAux;
        }
        StringBuffer loBuffer = new StringBuffer(100);
        for(int i = 0 ; i < loURLs.length; i++ ){
            loBuffer.append(loURLs[i].toString());
            loBuffer.append(';');
        }
        msClassPathCompleto = loBuffer.toString();
        moClassLoader = new URLClassLoader(loURLs);

    }

    public boolean ejecutarFicticio() throws Throwable {
        return false;
    }
    public boolean isHabilitada() throws Throwable {
        return mbHabilitada;
    }


}
