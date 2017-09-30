/*
 * Document.java
 *
 * Created on 12 de septiembre de 2006, 8:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utiles.xml.dom;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import utiles.IListaElementos;
import utiles.JListaElementos;
import static utiles.xml.dom.JDOMGuardar.guardar;

public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    private Element moRoot;
    private boolean mbReemplazarAcentosYEnes;
    private boolean mbReemplazarMayorMenorYDemas=true;
    private String msEncoding = "ISO-8859-1";

    /**
     * Creates a new instance of Document
     */
    public Document() {
    }

    public Document(final Element poRoot) {
        moRoot = poRoot;
    }

    public Element getRootElement() {
        return moRoot;
    }

    public String getEncoding() {
        return msEncoding;
    }

    public void setEnconding(String psEncoding) {
        msEncoding = psEncoding;
    }

    public boolean isReemplazarAcentosYEnes() {
        return mbReemplazarAcentosYEnes;
    }

    public void setReemplazarAcentosYEnes(boolean pbValor) {
        mbReemplazarAcentosYEnes = pbValor;
    }

    public String getPropiedad(final String psNombre, final String psValorDefecto) {
        String lsResult = null;
        Element loAux = Element.simpleXPath(getRootElement(), getSinRarosConBarra(getRootElement().getName() + "/" + getSinRarosConBarra(psNombre)));
        if (loAux != null) {
            lsResult = loAux.getValue();
        } else {
            lsResult = psValorDefecto;
        }
        if (lsResult == null) {
            lsResult = "";
        }
        return lsResult;
    }

    public static String getSinRarosConBarra(String psCadena) {
        StringBuilder loBuffer = new StringBuilder(psCadena.length());
        for (int i = 0; i < psCadena.length(); i++) {
            char lc = psCadena.charAt(i);
            if ((lc >= '0' && lc <= '9')
                    || (lc >= 'a' && lc <= 'z')
                    || (lc >= 'A' && lc <= 'Z')
                    || lc == '/') {
                loBuffer.append(lc);
            } else {
                loBuffer.append('_');
            }
        }
        return loBuffer.toString();

    }
    
    //Recupera XML como String
    public String getString() throws Exception {
        OutputStream out = new OutputStream() {
            private StringBuilder string = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                this.string.append((char) b);
            }

            public String toString() {
                return this.string.toString();
            }
        };
        guardar(this, out);
        return out.toString();
    }

    /**
     * Establece el valor en la propiedad psnombre simpleXpath
     */
    public void setPropiedad(final String psNombre, final String psValor) {
        Element loAux = Element.simpleXPath(getRootElement(), getRootElement().getName() + "/" + getSinRarosConBarra(psNombre));
        if (loAux == null) {
            loAux = crearElementosYDevolverUlt(psNombre);
        }
        loAux.setValor(psValor);
    }

    private Element crearElementosYDevolverUlt(final String psNombre) {
        Element loResult = null;
        String lsNombre = psNombre;
        if (lsNombre.charAt(0) == '/') {
            lsNombre = lsNombre.substring(1);
        }
        IListaElementos loElementos = new JListaElementos();
        StringBuilder lsAux = new StringBuilder();
        for (int i = 0; i < lsNombre.length(); i++) {
            if (lsNombre.charAt(i) == '/') {
                loElementos.add(lsAux.toString());
                lsAux.setLength(0);
            } else {
                lsAux.append(lsNombre.charAt(i));
            }
        }
        if (lsAux.length() > 0) {
            loElementos.add(lsAux.toString());
        }
        loResult = getRootElement();
        for (int i = 0; i < loElementos.size(); i++) {
            String lsKey = (String) loElementos.get(i);
            Element loAux = loResult.getChild(lsKey);
            if (loAux == null) {
                loAux = new Element(lsKey);
                loResult.addContent(loAux);
            }
            loResult = loAux;
        }
        return loResult;
    }

    /**
     * @return the mbReemplazarMayorMenorYDemas
     */
    public boolean isReemplazarMayorMenorYDemas() {
        return mbReemplazarMayorMenorYDemas;
    }

    /**
     * @param mbReemplazarMayorMenorYDemas the mbReemplazarMayorMenorYDemas to set
     */
    public void setReemplazarMayorMenorYDemas(boolean mbReemplazarMayorMenorYDemas) {
        this.mbReemplazarMayorMenorYDemas = mbReemplazarMayorMenorYDemas;
    }

}
