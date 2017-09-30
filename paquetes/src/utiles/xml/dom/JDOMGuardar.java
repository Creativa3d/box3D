/*
 * JDOMGuardar.java
 *
 * Created on 23 de agosto de 2007, 13:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utiles.xml.dom;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import utiles.red.*;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JSaxParser;

public class JDOMGuardar {

    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();

    /**
     * Creates a new instance of JDOMGuardar
     */
    public JDOMGuardar() {
    }

    public static OutputStream getOutputStream(final String psFile) throws IOException {
        if (psFile.indexOf("file:") > -1
                || psFile.indexOf("http:") > -1
                || psFile.indexOf("ftp:") > -1) {
            try {
                return msoOpenConnection.getConnection(new URL(psFile)).getOutputStream();
//                return (new URL(psFile)).openConnection(ProxyConfig.getProxy()).getOutputStream();
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException(e.toString());
            }
        } else {
            return new FileOutputStream(new File(psFile));
        }

    }

    public static void guardar(final Document poDoc, final String psFile) throws Exception {
        //para evitar q 2 programas al mismo tiempo graven en el fichero (catastrofes)

        //1 creamos un archivo temporal unico donde guardar xml
        String lsFile = psFile + String.valueOf(Math.random()).substring(2);
        //2 guardamos el xml
        guardar(poDoc, getOutputStream(lsFile));
        //3 borramos el original
        File loFileO = new File(psFile);
        if (loFileO.exists()) {
            loFileO.delete();
        }
        //4 renombramos el nuevo como el original
        File loFileD = new File(lsFile);
        loFileD.renameTo(loFileO);
        //5 si todavia existe el archivo temporal lo borramos
        loFileD = new File(lsFile);
        if (loFileD.exists()) {
            loFileD.delete();
        }

    }

    public static void guardar(final Document poDoc, final OutputStream poOut) throws Exception {
        PrintStream loPrint = new PrintStream(poOut, false, poDoc.getEncoding());
        try {
            loPrint.println("<?xml version=\"1.0\" encoding=\"" + poDoc.getEncoding() + "\"?>");

            guardarElemento(loPrint, poDoc.getRootElement(), 0, poDoc);
        } finally {
            loPrint.flush();
            loPrint.close();
            poOut.close();
        }
    }

    public static void guardarElemento(final Element poElemento, final OutputStream poOut) throws IOException {
        PrintStream loPrint = new PrintStream(poOut);
        try {
            Document poDoc = new Document(poElemento);
            guardarElemento(loPrint, poDoc.getRootElement(), 0, poDoc);
        } finally {
            loPrint.flush();
            loPrint.close();
            poOut.close();
        }
    }

    private static void guardarElemento(final PrintStream loPrint, final Element poElemento, final int plNivel, final Document poDoc) {
        String lsTabs = "";
//        for(int i = 0 ; i < plNivel ; i++){
//            lsTabs += (char)KeyEvent.VK_TAB;
//        }
        loPrint.print(lsTabs + "<" + poElemento.getNombre());

        for (int i = 0; i < poElemento.getAttributes().size(); i++) {
            JAtributo loAtr = poElemento.getAttributes().getAtributo(i);
            loPrint.print(" " + loAtr.getNombre() + "=\"" + JSaxParser.reemplazarCaracRarosAEncode(loAtr.getValue(), poDoc.isReemplazarAcentosYEnes(), poDoc.isReemplazarMayorMenorYDemas()) + "\"");
        }

        if (poElemento.getChildren().size() == 0) {
            if (poElemento.getValor() == null || poElemento.getValor().equals("")) {
                loPrint.println("/>");
            } else {
                loPrint.print(">");
                loPrint.print(JSaxParser.reemplazarCaracRarosAEncode(poElemento.getValor(), poDoc.isReemplazarAcentosYEnes(), poDoc.isReemplazarMayorMenorYDemas()));
                loPrint.println("</" + poElemento.getNombre() + ">");
            }
        } else {

            loPrint.print(">");

            if (poElemento.getValor() != null && !poElemento.getValor().equals("")) {
                loPrint.print(JSaxParser.reemplazarCaracRarosAEncode(poElemento.getValor(), poDoc.isReemplazarAcentosYEnes(), poDoc.isReemplazarMayorMenorYDemas()));
            }

            for (int i = 0; i < poElemento.getChildren().size(); i++) {
                guardarElemento(
                        loPrint,
                        (Element) poElemento.getChildren().get(i),
                        plNivel + 1,
                        poDoc);
            }

            loPrint.println(lsTabs + "</" + poElemento.getNombre() + ">");
        }

    }

}
