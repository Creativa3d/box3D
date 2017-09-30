
package ListDatos;

public class JElementoFiltro {


    public final String msTabla;
    public final IListDatosFiltro moFiltro;
    public final int mlTipoEdicion;
    public final String msNombre;


    public JElementoFiltro(String psTabla, IListDatosFiltro poFiltro) {
        msTabla = psTabla;
        moFiltro=poFiltro;
        mlTipoEdicion = JServidorDatosAbtrac.mclFiltroTipoSelect;
        msNombre="";
    }
    public JElementoFiltro(int plTipo,String psNombre, String psTabla, IListDatosFiltro poFiltro) {
        msTabla = psTabla;
        moFiltro = poFiltro;
        mlTipoEdicion = plTipo;
        msNombre = psNombre;
    }

}
