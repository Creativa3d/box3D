/*
 * JConsultaFiltro.java
 *
 * Created on 30 de octubre de 2007, 22:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.consultaPrincipal;

public class JPanelGenericoConsFiltro {
    
    public String msCaption;
    public int mlCampo;
    public int mlTipo;
    public boolean mbDesdeHasta = true;
    
    
    /** Creates a new instance of JConsultaFiltro
     * @param psCaption
     * @param plCampo
     * @param plTipo
     * @param pbDesdeHasta */
    public JPanelGenericoConsFiltro(final String psCaption, final int plCampo, final int plTipo, final boolean pbDesdeHasta) {
        super();
        msCaption = psCaption;
        mlCampo = plCampo;
        mlTipo = plTipo ;
        mbDesdeHasta = pbDesdeHasta;
    }
    
}
