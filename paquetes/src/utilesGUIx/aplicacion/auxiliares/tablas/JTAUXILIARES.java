/*
* JTAUXILIARES.java
*
*/

package utilesGUIx.aplicacion.auxiliares.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTAUXILIARES extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOAUXILIAR;
      public static final int lPosiCODIGOGRUPOAUX;
      public static final int lPosiACRONIMO;
      public static final int lPosiDESCRIPCION;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="AUXILIARES";
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
        
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGOAUXILIAR", "", true, 10));
        lPosiCODIGOAUXILIAR = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGOGRUPOAUX", "", false, 10));
        lPosiCODIGOGRUPOAUX = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "ACRONIMO", "", false, 50));
        lPosiACRONIMO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "DESCRIPCION", "", false, 255));
        lPosiDESCRIPCION = lPosi;
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
    public JTAUXILIARES(IServerServidorDatos poServidorDatos) {
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
    public JTAUXILIARES() {
      this(null);
    }
    public static JFieldDefs getCamposEstaticos(){
        return moCamposEstaticos;
    }
     public JFieldDef getCODIGOAUXILIAR(){
          return moList.getFields().get(lPosiCODIGOAUXILIAR);
     }
     public static String getCODIGOAUXILIARNombre(){
          return moCamposEstaticos.get(lPosiCODIGOAUXILIAR).getNombre();
     }
     public JFieldDef getCODIGOGRUPOAUX(){
          return moList.getFields().get(lPosiCODIGOGRUPOAUX);
     }
     public static String getCODIGOGRUPOAUXNombre(){
          return moCamposEstaticos.get(lPosiCODIGOGRUPOAUX).getNombre();
     }
     public JFieldDef getACRONIMO(){
          return moList.getFields().get(lPosiACRONIMO);
     }
     public static String getACRONIMONombre(){
          return moCamposEstaticos.get(lPosiACRONIMO).getNombre();
     }
     public JFieldDef getDESCRIPCION(){
          return moList.getFields().get(lPosiDESCRIPCION);
     }
     public static String getDESCRIPCIONNombre(){
          return moCamposEstaticos.get(lPosiDESCRIPCION).getNombre();
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
