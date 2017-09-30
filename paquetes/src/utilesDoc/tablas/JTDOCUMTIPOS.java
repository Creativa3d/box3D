/*
* JTDOCUMTIPOS.java
*
*/

package utilesDoc.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTDOCUMTIPOS extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOTIPODOC;
      public static final int lPosiEXTENSION;
      public static final int lPosiIMAGENSN;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="DOCUMTIPOS";
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
        
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGOTIPODOC", "", true, 10));
        lPosiCODIGOTIPODOC = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "EXTENSION", "", false, 200));
        lPosiEXTENSION = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoBoolean, "IMAGENSN", "", false, 3));
        lPosiIMAGENSN = lPosi;
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
    public JTDOCUMTIPOS(IServerServidorDatos poServidorDatos) {
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
    public JTDOCUMTIPOS() {
      this(null);
    }
    public static JFieldDefs getCamposEstaticos(){
        return moCamposEstaticos;
    }
     public JFieldDef getCODIGOTIPODOC(){
          return moList.getFields().get(lPosiCODIGOTIPODOC);
     }
     public static String getCODIGOTIPODOCNombre(){
          return moCamposEstaticos.get(lPosiCODIGOTIPODOC).getNombre();
     }
     public JFieldDef getEXTENSION(){
          return moList.getFields().get(lPosiEXTENSION);
     }
     public static String getEXTENSIONNombre(){
          return moCamposEstaticos.get(lPosiEXTENSION).getNombre();
     }
     public JFieldDef getIMAGENSN(){
          return moList.getFields().get(lPosiIMAGENSN);
     }
     public static String getIMAGENSNNombre(){
          return moCamposEstaticos.get(lPosiIMAGENSN).getNombre();
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
