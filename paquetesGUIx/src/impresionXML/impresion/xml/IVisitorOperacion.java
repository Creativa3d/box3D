/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml;

/**
 *
 * @author eduardo
 */
public interface IVisitorOperacion {
    public void operar(JxmlBanda poBanda) throws Throwable;
    public void operar(JxmlCuadrado poCuadrado) throws Throwable;
    public void operar(JxmlFuente poFuente) throws Throwable;
    public void operar(JxmlImagen poImagen) throws Throwable;
    public void operar(JxmlLinea poLinea) throws Throwable;
    public void operar(JxmlTexto potexto) throws Throwable;
    public void operar(JxmlInforme poInforme) throws Throwable;
}
