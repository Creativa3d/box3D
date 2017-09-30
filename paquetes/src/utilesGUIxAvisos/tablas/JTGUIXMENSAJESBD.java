/*
* JTGUIXMENSAJESSEND.java
*
*/

package utilesGUIxAvisos.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;

public class JTGUIXMENSAJESBD extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGO;
      public static final int lPosiIDMENSAJE;
      public static final int lPosiGRUPO;
      public static final int lPosiUSUARIO;
      public static final int lPosiFECHA;
      public static final int lPosiEMAILFROM;
      public static final int lPosiEMAILTO;
      public static final int lPosiEMAILCC;
      public static final int lPosiEMAILBCC;
      public static final int lPosiASUNTO;
      public static final int lPosiTEXTO;
      public static final int lPosiADJUNTOS;
      public static final int lPosiFOLDER;
      public static final int lPosiESTADO;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="GUIXMENSAJESBD";
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
        
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "CODIGO", "", true, 10));
        lPosiCODIGO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "IDMENSAJE", "", false, 255));
        lPosiIDMENSAJE = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "GRUPO", "", false, 255));
        lPosiGRUPO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "USUARIO", "", false, 255));
        lPosiUSUARIO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoFecha, "FECHA", "", false, 23));
        lPosiFECHA = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "EMAILFROM", "", false, 255));
        lPosiEMAILFROM = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "EMAILTO", "", false, 1073741823));
        lPosiEMAILTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "EMAILCC", "", false, 1073741823));
        lPosiEMAILCC = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "EMAILBCC", "", false, 1073741823));
        lPosiEMAILBCC = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "ASUNTO", "", false, 1073741823));
        lPosiASUNTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "TEXTO", "", false, 1073741823));
        lPosiTEXTO = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "ADJUNTOS", "", false, 1073741823));
        lPosiADJUNTOS = lPosi;
        lPosi++;
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoCadena, "FOLDER", "", false, 255));
        lPosiFOLDER = lPosi;
        lPosi++;
        
        moCamposEstaticos.addField(new JFieldDef(JListDatos.mclTipoNumero, "ESTADO", "", false, 255));
        lPosiESTADO = lPosi;
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
    public JTGUIXMENSAJESBD(IServerServidorDatos poServidorDatos) {
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
    public JTGUIXMENSAJESBD() {
      this(null);
    }
    public static JFieldDefs getCamposEstaticos(){
        return moCamposEstaticos;
    }
     public JFieldDef getCODIGO(){
          return moList.getFields().get(lPosiCODIGO);
     }
     public static String getCODIGONombre(){
          return moCamposEstaticos.get(lPosiCODIGO).getNombre();
     }
     public JFieldDef getGRUPO(){
          return moList.getFields().get(lPosiGRUPO);
     }
     public static String getGRUPONombre(){
          return moCamposEstaticos.get(lPosiGRUPO).getNombre();
     }
     public JFieldDef getIDMENSAJE(){
          return moList.getFields().get(lPosiIDMENSAJE);
     }
     public static String getIDMENSAJENombre(){
          return moCamposEstaticos.get(lPosiIDMENSAJE).getNombre();
     }
     public JFieldDef getUSUARIO(){
          return moList.getFields().get(lPosiUSUARIO);
     }
     public static String getUSUARIONombre(){
          return moCamposEstaticos.get(lPosiUSUARIO).getNombre();
     }
     public JFieldDef getFECHA(){
          return moList.getFields().get(lPosiFECHA);
     }
     public static String getFECHANombre(){
          return moCamposEstaticos.get(lPosiFECHA).getNombre();
     }
     public JFieldDef getEMAILFROM(){
          return moList.getFields().get(lPosiEMAILFROM);
     }
     public static String getEMAILFROMNombre(){
          return moCamposEstaticos.get(lPosiEMAILFROM).getNombre();
     }
     public JFieldDef getEMAILTO(){
          return moList.getFields().get(lPosiEMAILTO);
     }
     public static String getEMAILTONombre(){
          return moCamposEstaticos.get(lPosiEMAILTO).getNombre();
     }
     public JFieldDef getEMAILCC(){
          return moList.getFields().get(lPosiEMAILCC);
     }
     public static String getEMAILCCNombre(){
          return moCamposEstaticos.get(lPosiEMAILCC).getNombre();
     }
     public JFieldDef getEMAILBCC(){
          return moList.getFields().get(lPosiEMAILBCC);
     }
     public static String getEMAILBCCNombre(){
          return moCamposEstaticos.get(lPosiEMAILBCC).getNombre();
     }
     public JFieldDef getASUNTO(){
          return moList.getFields().get(lPosiASUNTO);
     }
     public static String getASUNTONombre(){
          return moCamposEstaticos.get(lPosiASUNTO).getNombre();
     }
     public JFieldDef getTEXTO(){
          return moList.getFields().get(lPosiTEXTO);
     }
     public static String getTEXTONombre(){
          return moCamposEstaticos.get(lPosiTEXTO).getNombre();
     }
     public JFieldDef getADJUNTOS(){
          return moList.getFields().get(lPosiADJUNTOS);
     }
     public static String getADJUNTOSNombre(){
          return moCamposEstaticos.get(lPosiADJUNTOS).getNombre();
     }
     public JFieldDef getFOLDER(){
          return moList.getFields().get(lPosiFOLDER);
     }
     public static String getFOLDERNombre(){
          return moCamposEstaticos.get(lPosiFOLDER).getNombre();
     }
     public JFieldDef getESTADO(){
          return moList.getFields().get(lPosiESTADO);
     }
     public static String getESTADONombre(){
          return moCamposEstaticos.get(lPosiESTADO).getNombre();
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
