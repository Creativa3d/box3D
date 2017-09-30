/*
* JTDOCUMENTOS.java
*
*/

package utilesDoc.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTDOCUMENTOS extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiGRUPO;
      public static final int lPosiGRUPOIDENT;
      public static final int lPosiCODIGODOCUMENTO;
      public static final int lPosiNOMBRE;
      public static final int lPosiDESCRIPCION;
      public static final int lPosiAUTOR;
      public static final int lPosiFECHA;
      public static final int lPosiUSUARIO;
      public static final int lPosiFECHAMODIF;
      public static final int lPosiCODTIPODOCUMENTO;
      public static final int lPosiCODCLASIF;
      public static final int lPosiRUTA;
      public static final int lPosiIDENTIFICADOREXTERNO;
      public static final int lPosiIDENTIFICADOROTRO;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="DOCUMENTOS";
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
        
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "GRUPO", "", true, 255));
        lPosiGRUPO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "GRUPOIDENT", "", true, 255));
        lPosiGRUPOIDENT = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGODOCUMENTO", "", true, 10));
        lPosiCODIGODOCUMENTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "NOMBRE", "", false, 100));
        lPosiNOMBRE = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "DESCRIPCION", "", false, 2147483647));
        lPosiDESCRIPCION = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "AUTOR", "", false, 50));
        lPosiAUTOR = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoFecha, "FECHA", "", false, 19));
        lPosiFECHA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "USUARIO", "", false, 255));
        lPosiUSUARIO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoFecha, "FECHAMODIF", "", false, 19));
        lPosiFECHAMODIF = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODTIPODOCUMENTO", "", false, 10));
        lPosiCODTIPODOCUMENTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODCLASIF", "", false, 10));
        lPosiCODCLASIF = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "RUTA", "", false, 255));
        lPosiRUTA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "IDENTIFICADOREXTERNO", "", false, 255));
        lPosiIDENTIFICADOREXTERNO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "IDENTIFICADOROTRO", "", false, 255));
        lPosiIDENTIFICADOROTRO = lPosi;
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
    public JTDOCUMENTOS(IServerServidorDatos poServidorDatos) {
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
    public JTDOCUMENTOS() {
      this(null);
    }
    public static JFieldDefs getCamposEstaticos(){
        return moCamposEstaticos;
    }
     public JFieldDef getGRUPO(){
          return moList.getFields().get(lPosiGRUPO);
     }
     public static String getGRUPONombre(){
          return moCamposEstaticos.get(lPosiGRUPO).getNombre();
     }
     public JFieldDef getGRUPOIDENT(){
          return moList.getFields().get(lPosiGRUPOIDENT);
     }
     public static String getGRUPOIDENTNombre(){
          return moCamposEstaticos.get(lPosiGRUPOIDENT).getNombre();
     }
     public JFieldDef getCODIGODOCUMENTO(){
          return moList.getFields().get(lPosiCODIGODOCUMENTO);
     }
     public static String getCODIGODOCUMENTONombre(){
          return moCamposEstaticos.get(lPosiCODIGODOCUMENTO).getNombre();
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return moCamposEstaticos.get(lPosiNOMBRE).getNombre();
     }
     public JFieldDef getDESCRIPCION(){
          return moList.getFields().get(lPosiDESCRIPCION);
     }
     public static String getDESCRIPCIONNombre(){
          return moCamposEstaticos.get(lPosiDESCRIPCION).getNombre();
     }
     public JFieldDef getAUTOR(){
          return moList.getFields().get(lPosiAUTOR);
     }
     public static String getAUTORNombre(){
          return moCamposEstaticos.get(lPosiAUTOR).getNombre();
     }
     public JFieldDef getFECHA(){
          return moList.getFields().get(lPosiFECHA);
     }
     public static String getFECHANombre(){
          return moCamposEstaticos.get(lPosiFECHA).getNombre();
     }
     public JFieldDef getUSUARIO(){
          return moList.getFields().get(lPosiUSUARIO);
     }
     public static String getUSUARIONombre(){
          return moCamposEstaticos.get(lPosiUSUARIO).getNombre();
     }
     public JFieldDef getFECHAMODIF(){
          return moList.getFields().get(lPosiFECHAMODIF);
     }
     public static String getFECHAMODIFNombre(){
          return moCamposEstaticos.get(lPosiFECHAMODIF).getNombre();
     }
     public JFieldDef getCODTIPODOCUMENTO(){
          return moList.getFields().get(lPosiCODTIPODOCUMENTO);
     }
     public static String getCODTIPODOCUMENTONombre(){
          return moCamposEstaticos.get(lPosiCODTIPODOCUMENTO).getNombre();
     }
     public JFieldDef getCODCLASIF(){
          return moList.getFields().get(lPosiCODCLASIF);
     }
     public static String getCODCLASIFNombre(){
          return moCamposEstaticos.get(lPosiCODCLASIF).getNombre();
     }
     public JFieldDef getRUTA(){
          return moList.getFields().get(lPosiRUTA);
     }
     public static String getRUTANombre(){
          return moCamposEstaticos.get(lPosiRUTA).getNombre();
     }
     public JFieldDef getIDENTIFICADOREXTERNO(){
          return moList.getFields().get(lPosiIDENTIFICADOREXTERNO);
     }
     public static String getIDENTIFICADOREXTERNONombre(){
          return moCamposEstaticos.get(lPosiIDENTIFICADOREXTERNO).getNombre();
     }
     public JFieldDef getIDENTIFICADOROTRO(){
          return moList.getFields().get(lPosiIDENTIFICADOROTRO);
     }
     public static String getIDENTIFICADOROTRONombre(){
          return moCamposEstaticos.get(lPosiIDENTIFICADOROTRO).getNombre();
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
