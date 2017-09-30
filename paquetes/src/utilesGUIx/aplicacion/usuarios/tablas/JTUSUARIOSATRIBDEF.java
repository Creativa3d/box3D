/*
* JTUSUARIOSATRIBDEF.java
*
*/

package utilesGUIx.aplicacion.usuarios.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTUSUARIOSATRIBDEF extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOUSUARIOATRIBDEF;
      public static final int lPosiNOMBRE;
      public static final int lPosiTITULO;
      public static final int lPosiTIPO;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="USUARIOSATRIBDEF";
     /**
      * NUmero de campos de la tabla
      */
    public static final int mclNumeroCampos;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres;
    public static final int[] malTipos;
    public static final int[] malTamanos;
    public static final int[] malCamposPrincipales;
    private static final JFieldDefs moCamposEstaticos;
    static {
        moCamposEstaticos = new JFieldDefs();
        moCamposEstaticos.setTabla(msCTabla);
        int lPosi = 0;
        
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGOUSUARIOATRIBDEF", "", true, 10));
        lPosiCODIGOUSUARIOATRIBDEF = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "NOMBRE", "", false, 50));
        lPosiNOMBRE = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "TITULO", "", false, 50));
        lPosiTITULO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "TIPO", "", false, 10));
        lPosiTIPO = lPosi;
        lPosi++;
        
        
        mclNumeroCampos = moCamposEstaticos.size();
        malCamposPrincipales = moCamposEstaticos.malCamposPrincipales();
        masNombres = moCamposEstaticos.msNombres();
        malTamanos = moCamposEstaticos.malTamanos();
        malTipos = moCamposEstaticos.malTipos();
    }        
     /**
      * Crea una instancia de la clase intermedia para la tabla incluyendole el servidor de datos
      */
    public JTUSUARIOSATRIBDEF(IServerServidorDatos poServidorDatos) {
        super();
        try {
            moList = new JListDatos();
            moList.setFields(moCamposEstaticos.Clone());
            moList.msTabla = msCTabla;
            moList.moServidor=poServidorDatos;
            moList.addListener(this);
        } catch (CloneNotSupportedException ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }        
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla
      */
    public JTUSUARIOSATRIBDEF() {
      this(null);
    }
    public static JFieldDefs getCamposEstaticos(){
        return moCamposEstaticos;
    }
     public JFieldDef getCODIGOUSUARIOATRIBDEF(){
          return moList.getFields().get(lPosiCODIGOUSUARIOATRIBDEF);
     }
     public static String getCODIGOUSUARIOATRIBDEFNombre(){
          return moCamposEstaticos.get(lPosiCODIGOUSUARIOATRIBDEF).getNombre();
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return moCamposEstaticos.get(lPosiNOMBRE).getNombre();
     }
     public JFieldDef getTITULO(){
          return moList.getFields().get(lPosiTITULO);
     }
     public static String getTITULONombre(){
          return moCamposEstaticos.get(lPosiTITULO).getNombre();
     }
     public JFieldDef getTIPO(){
          return moList.getFields().get(lPosiTIPO);
     }
     public static String getTIPONombre(){
          return moCamposEstaticos.get(lPosiTIPO).getNombre();
     }
    /**
    *recupera un objeto select segun la informaciOn actual
    *@return objeto select
    */
     public static JSelect getSelectStatico(){
         JSelect loSelect = new JSelect(msCTabla);
         for(int i = 0; i< moCamposEstaticos.size(); i++){
           loSelect.addCampo(msCTabla, moCamposEstaticos.get(i).getNombre());
         }
         return loSelect;
     }
}
