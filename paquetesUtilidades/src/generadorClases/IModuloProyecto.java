/*
 * IModuloProyecto.java
 *
 * Created on 8 de diciembre de 2005, 1:23
 *
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorClases;


public interface IModuloProyecto {
    public String getCodigo();
    public String getRutaRelativa();
    public String getNombre();
    public boolean isGeneral();
    public String getNombreModulo();
    public JModuloProyectoParametros getParametros();
}
