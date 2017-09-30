/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.operaciones;

import impresionXML.impresion.xml.IVisitorOperacion;
import impresionXML.impresion.xml.JxmlBanda;
import impresionXML.impresion.xml.JxmlCuadrado;
import impresionXML.impresion.xml.JxmlFuente;
import impresionXML.impresion.xml.JxmlImagen;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlLinea;
import impresionXML.impresion.xml.JxmlTexto;
import utiles.JCadenas;

/**
 *
 * @author eduardo
 */
public class JOperacionRellenar implements IVisitorOperacion {
    private int mli = 1;
    public void operar(JxmlBanda poBanda) throws Throwable {
    }

    public void operar(JxmlCuadrado poCuadrado) throws Throwable {
    }

    public void operar(JxmlFuente poFuente) throws Throwable {
    }

    public void operar(JxmlImagen poImagen) throws Throwable {
    }

    public void operar(JxmlLinea poLinea) throws Throwable {
    }

    public void operar(JxmlTexto potexto) throws Throwable {
        if(!JCadenas.isVacio(potexto.getNombre())){
            StringBuilder lsBuffer = new StringBuilder();
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            lsBuffer.append(String.valueOf(mli));
            potexto.setTexto(potexto.getNombre());
            mli++;
        }
    }

    public void operar(JxmlInforme poInforme) throws Throwable {
    }
    
}
