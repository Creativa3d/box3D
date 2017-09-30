/*
* JTAUXILIARES.java
*
*/

package impresionXML.impresion.xml.diseno.campos;

import ListDatos.*;
import ListDatos.estructuraBD.*;

public class JTCAMPOS extends JSTabla {
    private static final long serialVersionUID = 1L;
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiTIPO = 0;
      public static final int lPosiNOMBRE = 1;
      public static final int lPosiEXPLICACION = 2;
      public static final int lPosiVALORPOSIBLE = 3;
     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="CAMPOS";
     /**
      * NUmero de campos de la tabla
      */
    public static final int mclNumeroCampos=4;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
        "TIPO",
        "NOMBRE",
        "EXPLICACION",
        "VALORPOSIBLE"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena
    };
    public static final int[] malTamanos=    new int[] {
        0,
        0,
        0,
        0
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiTIPO
        , lPosiNOMBRE    
    };
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JTCAMPOS(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales,malTamanos);
        moList.getFields().setTabla(msCTabla);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JTCAMPOS() {
      this(null);
    }
     public JFieldDef getTIPO(){
          return moList.getFields().get(lPosiTIPO);
     }
     public JFieldDef getNOMBRE(){
          return moList.getFields().get(lPosiNOMBRE);
     }
     public JFieldDef getEXPLICACION(){
          return moList.getFields().get(lPosiEXPLICACION);
     }
     public JFieldDef getVALORPOSIBLE(){
          return moList.getFields().get(lPosiVALORPOSIBLE);
     }
    /**
    *recupera un objeto select segun la informaciOn actual
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
