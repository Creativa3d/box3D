/*
 * JGeneral.java
 *
 * Created on 14 de octubre de 2004, 12:50
 */

package CopiaSeguridad.file;

import java.io.*;
import utiles.*;

public class JGeneral {
    private static PrintWriter moFichero;
    private static FileOutputStream moFile;
    /** Creates a new instance of JGeneral */
    private JGeneral() {
    }
    
    public static void inicializar() throws Exception {
        JDateEdu loDate = new JDateEdu();
        moFile = new FileOutputStream("LogCS" + loDate.msFormatear("yyyyMMdd_HHmmss") + ".txt");
        moFichero = new PrintWriter(moFile);
    }
    
    public static void print(String psTexto){
        try{
            System.out.println(psTexto);
            moFichero.println(psTexto);
        }catch(Exception e){
            
        }
    }
    public static void finalizar() throws Exception {
        moFichero.close();
        moFile.close();
    }
    public static int getBuffer(){
        return 256;
    }
    public static boolean getEsPorSistemaLinux(){
        return false;
    }
}
