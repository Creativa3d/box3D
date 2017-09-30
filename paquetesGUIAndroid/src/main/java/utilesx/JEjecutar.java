package utilesx;


import java.io.*;
import java.net.URLConnection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import utiles.CadenaLargaOut;
import utiles.IListaElementos;
import utiles.JListaElementos;


public class JEjecutar implements Runnable {
    public static final int mclExitNula = -1010101;

    private String moCMD[];
    private java.io.File moDirOriginal;

    private int exitVal=mclExitNula;
    
//    private StreamGobbler errorGobbler;
//    private StreamGobbler outputGobbler;
    
    private OutputStream moOutput=null;
    private OutputStream moOuterror=null;
            
    private java.lang.Process proc;
    private Throwable moError=null;
    private IListaElementos moListener = new JListaElementos();
            
    public JEjecutar(String cmd[]){
        setCMD(cmd);
    }
    public JEjecutar(String cmd){
        setCMD(new String[]{cmd});
    }
    public JEjecutar(String cmd[], java.io.File poDir){
        setCMD(cmd);
        setDirOriginal(poDir);
    }
    public JEjecutar(String cmd, java.io.File poDir){
        setCMD(new String[]{cmd});
        setDirOriginal(poDir);
    }
    public void addListener(IEjecutarListener poListener){
        moListener.add(poListener);
    }
    public void removeListener(IEjecutarListener poListener){
        moListener.remove(poListener);
    }
    private void llamarListener(){
        for(int i = 0; i < moListener.size(); i++){
            IEjecutarListener loListerner = (IEjecutarListener) moListener.get(i);
            loListerner.terminado(this);
        }
    }

    public String[] getCMD() {
        return moCMD;
    }

    public void setCMD(String[] poCMD) {
        moCMD = poCMD;
    }

    /**devuelve el directorio Base en donde se ejecutara la apliacacion*/
    public File getDirOriginal() {
        return moDirOriginal;
    }

    public void setDirOriginal(File poDirOriginal) {
        moDirOriginal = poDirOriginal;
    }

    /**Devuelve el proceso*/
    public java.lang.Process getProc() {
        return proc;
    }

    /**Devuelve la salida del proceso a la q es redirigida*/
    public OutputStream getOutputProceso() {
        return moOutput;
    }

//    public void pp(){
//
//    }
    /**Establece la salida del proceso a la q es redirigida*/
    public void setOutputProceso(OutputStream poOutput) {
        moOutput = poOutput;
    }

    /**Devuelve la salida de errores del proceso a la q es redirigida*/
    public OutputStream getOuterrorProceso() {
        return moOuterror;
    }

    /**Establece la salida de errores del proceso a la q es redirigida*/
    public void setOuterrorProceso(OutputStream poOuterror) {
        moOuterror = poOuterror;
    }

    public Throwable getError(){
        return moError;
    }
    
    public void run() {
        moError=null;
        try {
            Runtime rt = Runtime.getRuntime();
            if(moDirOriginal==null){
                proc = rt.exec(getCMD());
            }else{
                proc = rt.exec(getCMD(),null,moDirOriginal);
            }
            // any error message?
            StreamGobbler errorGobbler = new 
                StreamGobbler(getProc().getErrorStream(), "ERROR", moOuterror);            
            
            // any output?
            StreamGobbler outputGobbler = new 
                StreamGobbler(getProc().getInputStream(), "OUTPUT", moOutput);
                
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
                                    
            // any error???
            exitVal = getProc().waitFor();
//            if(moOutput!=null){
//                moOutput.write( ("Salida: " + String.valueOf(getExitVal())).getBytes() );
//            }
        } catch (Throwable t) {
            moError=t;
            try {
                if(moOuterror!=null){
                    moOuterror.write(t.toString().getBytes());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            t.printStackTrace();
        }
        llamarListener();
    }
    public static void main(String cmd[]){
        try {
            //        JEjecutar loE =  new JEjecutar(new String[]{"cmd.exe", "/C","C:\\Documents and Settings\\GONZE2\\Mis documentos\\Java Printing.pdf"});
            //        
            //        moThread.start();
            //        moThread.start();

                    JEjecutar.crearAccesoDirectoDESKTOPLinux("/home/d/PIPRE/ejecutar.sh", "/home/d/PIPRE", "/home/d/PIPRE", "piprep", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**Devuelve la salida del proceso*/
    public int getExitVal() {
        return exitVal;
    }
    public static void abrirDocumento(Context moContext, String psDoc) {
        File file = new File(psDoc);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-excel");
        intent.setDataAndType(Uri.fromFile(file),URLConnection.guessContentTypeFromName(file.getAbsolutePath()));
        moContext.startActivity(intent);        
    }
    public static String ejecutar(String[] psVBS) throws Exception {
        JEjecutar loEje = new JEjecutar(psVBS);
        CadenaLargaOut loOut = new CadenaLargaOut();
        CadenaLargaOut loError = new CadenaLargaOut();
        loEje.setOutputProceso(loOut);
        loEje.setOuterrorProceso(loError);
        loEje.run();
        if (loEje.getExitVal() != 0) {
            throw new Exception(loOut.moStringBuffer.toString() + "\n" + loError.moStringBuffer.toString());
        }
        return loOut.toString();
    }
    /** 
     * CScript para crear accesos directos en window, usarlo con ejecutarVBSCadena
     */
    private static String getCrearAccesoDirecto(String psTargetPath, String psDirectorioTrabajo, String psLinkPath, String psLinkName, String psIconoLocalizacion){
        StringBuffer lsVB = new StringBuffer(500);

        lsVB.append("'Creacion del objeto Shell");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("Set WshShell = WScript.CreateObject(\"WScript.Shell\")");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("'Recuperamos los argumentos de la llamada a este script");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("'Definimos la localizacion del fichero LNK");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LinkFilename = \"" + new File( psLinkPath ,psLinkName + ".lnk").getAbsolutePath() +  "\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("'Creamos el objeto LNKFile");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("Set LNKFile = WshShell.CreateShortcut(LinkFilename)");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("'Establecemos los contenidos del fichero LNK");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.TargetPath = \"" + psTargetPath + "\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.Arguments = \"\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.Description = \"Acceso directo a "+psTargetPath+"\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.HotKey = \"\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.WindowStyle = \"1\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.WorkingDirectory = \""+psDirectorioTrabajo+"\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.IconLocation=\""+ psIconoLocalizacion +  "\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("'Guardamos el fichero LNK en disco");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("LNKFile.Save");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        
        return lsVB.toString();
    }
    /**
     * Crea acceso directo solo windows
     */
    public static void crearAccesoDirectoWindows(String psTargetPath, String psDirectorioTrabajo, String psLinkPath, String psLinkName, String psIconoLocalizacion) throws Exception{
        ejecutarVBSCadena(getCrearAccesoDirecto(psTargetPath, psDirectorioTrabajo, psLinkPath, psLinkName, psIconoLocalizacion));
    }
    /**
     * Crea acceso directo(.desktop) solo linux
     */
    public static void crearAccesoDirectoDESKTOPLinux(String psTargetPath, String psDirectorioTrabajo, String psLinkPath, String psLinkName, String psIconoLocalizacion) throws Exception{
        PrintStream loOut = new PrintStream(new File(psLinkPath, psLinkName+".desktop"));
        try{
            loOut.println("[Desktop Entry]");
            loOut.println("Encoding=UTF-8");
            loOut.println("Name="+psLinkName);
            loOut.println("Comment=Acceso directo a " + psTargetPath);
            loOut.println("Exec=\"" + psTargetPath + "\"");
            loOut.println("Icon="+psIconoLocalizacion);
            loOut.println("Categories=Application;Development;Java;IDE");
            loOut.println("Version=1.0");
            loOut.println("Type=Application");
            loOut.println("Path="+psDirectorioTrabajo);
            loOut.println("Terminal=0        ");
        }finally{
            loOut.close();
        }
    }
    /**
     * Crea acceso directo
     */
    public static void crearAccesoDirecto(String psTargetPath, String psDirectorioTrabajo, String psLinkPath, String psLinkName, String psIconoLocalizacion) throws Exception{
        String sSistemaOperativo = System.getProperty("os.name").toLowerCase();

        if ("linux".equals(sSistemaOperativo) || "unix".equals(sSistemaOperativo)) {
            JEjecutar.crearAccesoDirectoDESKTOPLinux(psTargetPath, psDirectorioTrabajo, psLinkPath, psLinkName, psIconoLocalizacion);
        } else if ("mac".equals(sSistemaOperativo)) {
        }else {
            JEjecutar.crearAccesoDirectoWindows(psTargetPath, psDirectorioTrabajo, psLinkPath, psLinkName, psIconoLocalizacion);
        }
    }
    /**
     * Crea acceso directo(link) solo linux
     */
    public static void crearAccesoDirectoLinux(String psTargetPath, String psLinkPath, String psLinkName) throws Exception{
        ejecutar(
                new String[]{
                    "ln", "--symbolic", 
                    psTargetPath, 
                    new File( psLinkPath ,psLinkName + ".lnk").getAbsolutePath()}
                );
        
    }
    public static String ejecutarVBS(String psFileVBS) throws Exception {
        return ejecutar(new String[]{"cscript", "//NoLogo", psFileVBS});
    }    
    public static String ejecutarVBSCadena(String psComandosVBS) throws Exception {
        File file = File.createTempFile("realhowto", ".vbs");
        file.deleteOnExit();
//        File file = new File("realhowtoPlacabase.vbs");
        FileWriter fw = new java.io.FileWriter(file);
        try{
            fw.write(psComandosVBS);
        }finally {
            fw.close();            
        }
        return ejecutarVBS(file.getAbsolutePath());
    }    
}
class StreamGobbler extends Thread  {

    private InputStream is;
    private String type;
    private OutputStream os;
    
    StreamGobbler(InputStream is, String type) {
        this(is, type, null);
    }
    StreamGobbler(InputStream is, String type, OutputStream redirect) {
        this.is = is;
        this.type = type;
        this.os = redirect;
    }
    
    public void run() {
        try
        {
            PrintWriter pw = null;
            if (os != null)
                pw = new PrintWriter(os);
                
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
            {
                if (pw != null)
                    pw.println(line);
                System.out.println(type + ">" + line);    
            }
            if (pw != null)
                pw.flush();
        } catch (IOException ioe)
            {
            ioe.printStackTrace();  
            }
    }

 }