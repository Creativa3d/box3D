/*
* JTGUIXAVISOS.java
*
*/

package utilesGUIxAvisos.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTGUIXAVISOS extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCALENDARIO = 0;//CALENDARIO
      public static final int lPosiCODIGOEVENTO = 1;//EVENTO Q LO ORIGINA
      public static final int lPosiCODIGO = 2;//COIDGO AVISO
      public static final int lPosiFECHACONCRETA = 3;//FECHA/HORA EN LA Q SE REALIZA EL AVISO
      public static final int lPosiPANTALLASN = 4;//SI SE PRESENTA PANTALLA
      public static final int lPosiTELF = 5;//SI SE MANDA POR SMS
      public static final int lPosiSENDER = 6;//SENDER SMS
      public static final int lPosiEMAIL = 7;//SI SE MANDA POR EMAIL
      public static final int lPosiAVISADOSN = 8;//SI SE HA AVISADO
      public static final int lPosiFECHAMODIFICACION = 9;//Importantisimo, para eficiencia consultas, debe terner indice

      /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="GUIXAVISOS";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=10;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "CALENDARIO",
        "CODIGOEVENTO",
        "CODIGO",
        "FECHACONCRETA",
        "PANTALLASN",
        "TELF",
        "SENDER",
        "EMAIL",
        "AVISADOSN",
        "FECHAMODIFICACION"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoFecha,
        JListDatos.mclTipoBoolean,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoBoolean,
        JListDatos.mclTipoFecha,
    };
    public static final int[] malTamanos=    new int[] {
        255,
        10,
        10,
        23,
        1,
        255,
        255,
        255,
        0, 0
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCALENDARIO,
        lPosiCODIGOEVENTO,
        lPosiCODIGO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTGUIXAVISOS(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTGUIXAVISOS() {
      this(null);
    }
     public JFieldDef getCALENDARIO(){
          return moList.getFields().get(lPosiCALENDARIO);
     }
     public static String getCALENDARIONombre(){
          return  masNombres[lPosiCALENDARIO];
     }
    
     public JFieldDef getCODIGO(){
          return moList.getFields().get(lPosiCODIGO);
     }
     public static String getCODIGONombre(){
          return  masNombres[lPosiCODIGO];
     }
     public JFieldDef getCODIGOEVENTO(){
          return moList.getFields().get(lPosiCODIGOEVENTO);
     }
     public static String getCODIGOEVENTONombre(){
          return  masNombres[lPosiCODIGOEVENTO];
     }
     public JFieldDef getFECHACONCRETA(){
          return moList.getFields().get(lPosiFECHACONCRETA);
     }
     public static String getFECHACONCRETANombre(){
          return  masNombres[lPosiFECHACONCRETA];
     }
     public JFieldDef getPANTALLASN(){
          return moList.getFields().get(lPosiPANTALLASN);
     }
     public static String getPANTALLASNNombre(){
          return  masNombres[lPosiPANTALLASN];
     }
     public JFieldDef getTELF(){
          return moList.getFields().get(lPosiTELF);
     }
     public static String getTELFNombre(){
          return  masNombres[lPosiTELF];
     }
     public JFieldDef getSENDER(){
          return moList.getFields().get(lPosiSENDER);
     }
     public static String getSENDERNombre(){
          return  masNombres[lPosiSENDER];
     }
     public JFieldDef getEMAIL(){
          return moList.getFields().get(lPosiEMAIL);
     }
     public static String getEMAILNombre(){
          return  masNombres[lPosiEMAIL];
     }
     public JFieldDef getAVISADOSN(){
          return moList.getFields().get(lPosiAVISADOSN);
     }
     public static String getAVISADOSNNombre(){
          return  masNombres[lPosiAVISADOSN];
     }
    public JFieldDef getFECHAMODIFICACION() {
        return moList.getFields().get(lPosiFECHAMODIFICACION);
    }

    public static String getFECHAMODIFICACIONNombre() {
        return masNombres[lPosiFECHAMODIFICACION];
    }
     
    /**
    *recupera un objeto select segun la información actual
    *@return objeto select
    */
     public static JSelect getSelectStatico(){
         JSelect loSelect = new JSelect(msCTabla);
         for(int i = 0; i< masNombres.length; i++){
           loSelect.addCampo(msCTabla, masNombres[i]);
         }
         return loSelect;
     }
}
