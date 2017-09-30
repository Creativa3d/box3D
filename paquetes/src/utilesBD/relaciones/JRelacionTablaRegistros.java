/*
 * JRelacionTablaRegistros.java
 *
 * Created on 18 de noviembre de 2004, 14:01
 */

package utilesBD.relaciones;
import ListDatos.estructuraBD.*;
import utiles.*;
import ListDatos.*;
/**lista de registros relacionados de una relacion concreta*/
public class JRelacionTablaRegistros implements java.io.Serializable{
    private static final long serialVersionUID = 33333341L;
    /**tabla*/
    public String msTabla = null;
    /**Lista de campos*/
    public JFieldDefs moFields = null;
    /**Lista de filas*/
    public JListaElementos moFilas = new JListaElementos();
    
    /**
     * Creates a new instance of JRelacionTablaRegistros
     * @param psTabla tabla base
     */
    public JRelacionTablaRegistros(String psTabla) {
        msTabla = psTabla;
    }
    /**
     * crea campos
     * @param pasNombres lista de nombres
     * @param palCamposPrincipales lista de indices de campos principales
     * @param palTipos lista de tipos
     */
    public void crearCampos(String pasNombres[], int[] palCamposPrincipales, int[] palTipos){
        crearCampos(pasNombres, palCamposPrincipales, palTipos, null);
    }
    /**
     * crea campos
     * @param palTamanos lista de tamanos
     * @param pasNombres lista de nombres
     * @param palCamposPrincipales lista de indices de campos principales
     * @param palTipos lista de tipos
     */
    public void crearCampos(String pasNombres[], int[] palCamposPrincipales, int[] palTipos, int[] palTamanos){
        moFields = new JFieldDefs(pasNombres, palCamposPrincipales, pasNombres, palTipos, palTamanos);
    }

    /**
     * Anade una fila de datos
     * @param poFila fila
     */
    public void addDatos(IFilaDatos poFila){
        moFilas.add(poFila);
    }
    /**
     * devuelve si la tabla pasada por parametro es la tabla base de esta relacioon
     * @param psTabla tabla
     * @return si es la tabla base
     */
    public boolean isTabla(String psTabla){
        return (msTabla.toLowerCase().compareTo(psTabla.toLowerCase())==0);
    }

    
}
