/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package formateo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author GONZE2
 */
public class QuitarEspaciosBlanco {
    public void guardarEnFichero(OutputStream loOut, String lsValor) throws Exception{
        //guardamos
        BufferedWriter out = (new BufferedWriter(new OutputStreamWriter(loOut)));

        out.write(lsValor, 0, lsValor.length());

        out.close();

    }
    public String quitarBlancos(InputStream loIn) throws Exception{
        //leemos y quitamos espacios en blanco
        StringBuffer lasValores = new StringBuffer(100);
        BufferedReader in = (new BufferedReader(new InputStreamReader(loIn)));

        String inputLine = in.readLine();
        while ( inputLine != null) {
            if(!inputLine.equals("")){
                lasValores.append(inputLine);
                lasValores.append('\n');

            }
            inputLine = in.readLine();
        }
        in.close();

        return lasValores.toString();

    }
    
    public void quitarLineasBlanco(File loFile) throws Exception{
        //leemos
        String lsValor = null;
        FileInputStream loIn = new FileInputStream(loFile);
        try{
            lsValor = quitarBlancos(loIn);
        }finally{
            loIn.close();
        }
        
        //guardamos
        FileOutputStream loOut = new FileOutputStream(loFile);
        try{
            guardarEnFichero(loOut, lsValor);
        }finally{
            loOut.close();
        }
        
    }

    public static void main(String[] pas){
        JFileChooser loFileC = new JFileChooser();
        loFileC.setMultiSelectionEnabled(true);
        if(loFileC.showOpenDialog(null)==loFileC.APPROVE_OPTION){
            QuitarEspaciosBlanco loQ = new QuitarEspaciosBlanco();
            File[] loFiles = loFileC.getSelectedFiles();
            for(int i = 0 ; i < loFiles.length; i++){
                File loFile = loFiles[i];
                try {
                    loQ.quitarLineasBlanco(loFile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JMsgBox.mensajeError(null, ex);
                }
            }

        }

    }

}
