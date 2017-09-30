/*
* JTCUENTASCORREO.java
*
* Creado el 27/07/2011
*/

package utilesGUIxAvisos.tablas;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTCUENTASCORREO extends JSTabla {
    private static final long serialVersionUID = 1L;
    
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiCODIGOCUENTACORREO = 0;
      public static final int lPosiNOMBRE = 1;
      public static final int lPosiDIRECCION = 2;
      public static final int lPosiUSUARIOENTRANTE = 3;
      public static final int lPosiPASSENTRANTE = 4;
      public static final int lPosiSERVIDORENTRANTE = 5;
      public static final int lPosiTIPOENTRANTE = 6;
      public static final int lPosiUSUARIOSALIENTE = 7;
      public static final int lPosiPASSSALIENTE = 8;
      public static final int lPosiSERVIDORSALIENTE = 9;
      public static final int lPosiFECHAULTMODIF = 10;
      public static final int lPosiUSUCODIGOUSUARIO = 11;
      public static final int lPosiCARPETAENTRADA = 12;
      public static final int lPosiCARPETASALIDA = 13;

      public static final int lPosiPUERTOSALIDA = 14;
      public static final int lPosiPUERTOENTRADA = 15;
      public static final int lPosiSEGURIDADSALIDA = 16;
      public static final int lPosiSEGURIDADENTRADA = 17;

      
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="CUENTASCORREO";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=18;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "CODIGOCUENTACORREO",
        "NOMBRE",
        "DIRECCION",
        "USUARIOENTRANTE",
        "PASSENTRANTE",
        "SERVIDORENTRANTE",
        "TIPOENTRANTE",
        "USUARIOSALIENTE",
        "PASSSALIENTE",
        "SERVIDORSALIENTE",
        "FECHAULTMODIF",
        "USUCODIGOUSUARIO",
        "CARPETAENTRADA",
        "CARPETASALIDA"

      ,"PUERTOSALIDA"
      ,"PUERTOENTRADA"
      ,"SEGURIDADSALIDA"
      ,"SEGURIDADENTRADA"
        
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoNumero,

        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena, //5
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,//10
        JListDatos.mclTipoCadena,

        JListDatos.mclTipoCadena,

        JListDatos.mclTipoFecha,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoNumero,
        JListDatos.mclTipoNumero,
        

    };
    public static final int[] malTamanos=    new int[] {
        10,
        18,
        50,
        100,
        100,
        50,
        50,
        100,
        100,
        50,
        23,
        10,
        50,
        50,
        10,
        10,
        10,
        10,
        
        
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiCODIGOCUENTACORREO
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTCUENTASCORREO(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTCUENTASCORREO() {
      this(null);
    }
     public JFieldDef getCODIGOCUENTACORREO(){
          return moList.getFields().get(lPosiCODIGOCUENTACORREO);
     }
     public static String getCODIGOCUENTACORREONombre(){
          return  masNombres[lPosiCODIGOCUENTACORREO];
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public static String getNOMBRENombre(){
          return  masNombres[lPosiNOMBRE];
     }
     public JFieldDef getDIRECCION(){
          return moList.getFields().get(lPosiDIRECCION);
     }
     public static String getDIRECCIONNombre(){
          return  masNombres[lPosiDIRECCION];
     }
     public JFieldDef getUSUARIOENTRANTE(){
          return moList.getFields().get(lPosiUSUARIOENTRANTE);
     }
     public static String getUSUARIOENTRANTENombre(){
          return  masNombres[lPosiUSUARIOENTRANTE];
     }
     public JFieldDef getPASSENTRANTE(){
          return moList.getFields().get(lPosiPASSENTRANTE);
     }
     public static String getPASSENTRANTENombre(){
          return  masNombres[lPosiPASSENTRANTE];
     }
     public JFieldDef getSERVIDORENTRANTE(){
          return moList.getFields().get(lPosiSERVIDORENTRANTE);
     }
     public static String getSERVIDORENTRANTENombre(){
          return  masNombres[lPosiSERVIDORENTRANTE];
     }
     public JFieldDef getTIPOENTRANTE(){
          return moList.getFields().get(lPosiTIPOENTRANTE);
     }
     public static String getTIPOENTRANTENombre(){
          return  masNombres[lPosiTIPOENTRANTE];
     }
     public JFieldDef getSERVIDORSALIENTE(){
          return moList.getFields().get(lPosiSERVIDORSALIENTE);
     }
     public static String getSERVIDORSALIENTENombre(){
          return  masNombres[lPosiSERVIDORSALIENTE];
     }
     public JFieldDef getUSUARIOSALIENTE(){
          return moList.getFields().get(lPosiUSUARIOSALIENTE);
     }
     public static String getUSUARIOSALIENTENombre(){
          return  masNombres[lPosiUSUARIOSALIENTE];
     }
     public JFieldDef getPASSSALIENTE(){
          return moList.getFields().get(lPosiPASSSALIENTE);
     }
     public static String getPASSSALIENTENombre(){
          return  masNombres[lPosiPASSSALIENTE];
     }
     public JFieldDef getFECHAULTMODIF(){
          return moList.getFields().get(lPosiFECHAULTMODIF);
     }
     public static String getFECHAULTMODIFNombre(){
          return  masNombres[lPosiFECHAULTMODIF];
     }
     public JFieldDef getUSUCODIGOUSUARIO(){
          return moList.getFields().get(lPosiUSUCODIGOUSUARIO);
     }
     public static String getUSUCODIGOUSUARIONombre(){
          return  masNombres[lPosiUSUCODIGOUSUARIO];
    }

    public JFieldDef getCARPETAENTRADA() {
        return moList.getFields().get(lPosiCARPETAENTRADA);
    }

    public static String getCARPETAENTRADANombre() {
        return masNombres[lPosiCARPETAENTRADA];
    }

    public JFieldDef getCARPETASALIDA() {
        return moList.getFields().get(lPosiCARPETASALIDA);
    }

    public static String getCARPETASALIDANombre() {
        return masNombres[lPosiCARPETASALIDA];
    }

    public JFieldDef getPUERTOSALIDA() {
        return moList.getFields().get(lPosiPUERTOSALIDA);
    }

    public static String getPUERTOSALIDANombre() {
        return masNombres[lPosiPUERTOSALIDA];
    }

    public JFieldDef getPUERTOENTRADA() {
        return moList.getFields().get(lPosiPUERTOENTRADA);
    }

    public static String getPUERTOENTRADANombre() {
        return masNombres[lPosiPUERTOENTRADA];
    }

    public JFieldDef getSEGURIDADSALIDA() {
        return moList.getFields().get(lPosiSEGURIDADSALIDA);
    }

    public static String getSEGURIDADSALIDANombre() {
        return masNombres[lPosiSEGURIDADSALIDA];
    }

    public JFieldDef getSEGURIDADENTRADA() {
        return moList.getFields().get(lPosiSEGURIDADENTRADA);
    }

    public static String getSEGURIDADENTRADANombre() {
        return masNombres[lPosiSEGURIDADENTRADA];
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
