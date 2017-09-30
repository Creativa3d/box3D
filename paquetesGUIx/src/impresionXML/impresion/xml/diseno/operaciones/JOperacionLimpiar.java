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
 * Limpiamos todos los textos con nombre
 * @author eduardo
 */
public class JOperacionLimpiar implements IVisitorOperacion{
    private final JxmlInforme moInforme;
    
    public JOperacionLimpiar (JxmlInforme poInforme){
        moInforme = poInforme;
    }

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

    public void operar(JxmlTexto poTexto) throws Throwable {
        if(!JCadenas.isVacio(poTexto.getNombre())){
            poTexto.setTexto("");
        }
    }

    public void operar(JxmlInforme poBanda) throws Throwable {
    }
    
}
