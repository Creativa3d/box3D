/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

/**
 *
 * @author eduardo
 */
public interface ITablaConfig {
    public JTablaConfigColumna getTablaConfigColumnaDeCampoReal(final int i);
    public abstract void setIndiceConfig(final String psNombre) ;
    public abstract void aplicar();
    public JTablaConfigTablaConfig getConfigTablaConcreta();
    public boolean[] getCamposVisibles();
    public JTablaConfigTabla getConfigTabla();
}
