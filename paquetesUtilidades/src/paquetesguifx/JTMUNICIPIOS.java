/*
* JTMUNICIPIOS.java
*
*/

package paquetesguifx;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTMUNICIPIOS extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODPROV;
      public static final int lPosiCODHACIENDA;
      public static final int lPosiMUNICIPIO;
      public static final int lPosiCPM;
      public static final int lPosiESCAPITALSN;
      public static final int lPosiFECHAACT;
      public static final int lPosiCODIGOINE;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="MUNICIPIOS";
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
        
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODPROV", "", true, 2));
        lPosiCODPROV = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODHACIENDA", "", true, 10));
        lPosiCODHACIENDA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "MUNICIPIO", "", false, 26));
        lPosiMUNICIPIO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CPM", "", false, 3));
        lPosiCPM = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoBoolean, "ESCAPITALSN", "", false, 1));
        lPosiESCAPITALSN = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoFecha, "FECHAACT", "", false, 29));
        lPosiFECHAACT = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "CODIGOINE", "", false, 3));
        lPosiCODIGOINE = lPosi;
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
    public JTMUNICIPIOS(IServerServidorDatos poServidorDatos) {
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
    public JTMUNICIPIOS() {
      this(null);
    }
    public static JFieldDefs getCamposEstaticos(){
        return moCamposEstaticos;
    }
     public JFieldDef getCODPROV(){
          return moList.getFields().get(lPosiCODPROV);
     }
     public static String getCODPROVNombre(){
          return moCamposEstaticos.get(lPosiCODPROV).getNombre();
     }
     public JFieldDef getCODHACIENDA(){
          return moList.getFields().get(lPosiCODHACIENDA);
     }
     public static String getCODHACIENDANombre(){
          return moCamposEstaticos.get(lPosiCODHACIENDA).getNombre();
     }
     public JFieldDef getMUNICIPIO(){
          return moList.getFields().get(lPosiMUNICIPIO);
     }
     public static String getMUNICIPIONombre(){
          return moCamposEstaticos.get(lPosiMUNICIPIO).getNombre();
     }
     public JFieldDef getCPM(){
          return moList.getFields().get(lPosiCPM);
     }
     public static String getCPMNombre(){
          return moCamposEstaticos.get(lPosiCPM).getNombre();
     }
     public JFieldDef getESCAPITALSN(){
          return moList.getFields().get(lPosiESCAPITALSN);
     }
     public static String getESCAPITALSNNombre(){
          return moCamposEstaticos.get(lPosiESCAPITALSN).getNombre();
     }
     public JFieldDef getFECHAACT(){
          return moList.getFields().get(lPosiFECHAACT);
     }
     public static String getFECHAACTNombre(){
          return moCamposEstaticos.get(lPosiFECHAACT).getNombre();
     }
     public JFieldDef getCODIGOINE(){
          return moList.getFields().get(lPosiCODIGOINE);
     }
     public static String getCODIGOINENombre(){
          return moCamposEstaticos.get(lPosiCODIGOINE).getNombre();
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
