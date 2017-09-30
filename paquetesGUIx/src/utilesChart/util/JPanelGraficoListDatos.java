/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesChart.util;

import ListDatos.ECampoError;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.estructuraBD.JFieldDef;

public class JPanelGraficoListDatos extends JSTabla  {
     /**
      * Variables para las posiciones de los campos
      */
      public static final int lPosiX = 0;
      public static final int lPosiY = 1;

     /**
      * Variable nombre de tabla
      */
    public static final String msCTabla="";
     /**
      * Número de campos de la tabla
      */
    public static final int mclNumeroCampos=2;
     /**
      * Nombres de la tabla
      */
    public static final String[] masNombres=    new String[] {
      "X",
      "Y"
    };
    public static final int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoNumeroDoble
    };
    public static final int[] malTamanos=    new int[] {
        0,0
    };
    public static final int[] malCamposPrincipales=    new int[] {
        lPosiX
    };

     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS incluyendole el servidor de datos
      */
    public JPanelGraficoListDatos(IServerServidorDatos poServidorDatos) {
        super();
        moList = new JListDatos(poServidorDatos,msCTabla, masNombres, malTipos, malCamposPrincipales, malTamanos);
        moList.addListener(this);
    }
     /**
      * Crea una instancia de la clase intermedia para la tabla FAMILIAS
      */
    public JPanelGraficoListDatos() {
      this(null);
    }

    public JFieldDef getX (){
        return moList.getFields(lPosiX);
    }
    public JFieldDef getY (){
        return moList.getFields(lPosiY);
    }

    public void addValor(double pdX, double pdY) throws ECampoError{
        moList.addNew();
        getX().setValue(pdX);
        getY().setValue(pdY);
        moList.update(false);
    }
    public void addValor(String psX, double pdY) throws ECampoError{
        moList.addNew();
        getX().setValue(psX);
        getY().setValue(pdY);
        moList.update(false);
    }

}
