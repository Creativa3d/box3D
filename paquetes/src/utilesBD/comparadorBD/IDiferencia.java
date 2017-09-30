/*
 * IDiferencia.java
 *
 * Created on 10 de agosto de 2005, 18:36
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesBD.comparadorBD;

public interface IDiferencia {
    public static final int mclTabla = 0;
    public static final int mclCampo = 1;
    public static final int mclIndice = 2;
    public static final int mclRelacion = 3;
    
    public static final int mclTipoModificacion = 0;
    public static final int mclTipoNoExiste = 1;
    public static final int mclTipoSobra = 2;
    
    public String getDiferencia();
    public String getEstructura();
    
    public int getTipoModificacion();
    public int getTipoEstructura();
    public void arreglarDiferencia() throws Exception;
   
}
