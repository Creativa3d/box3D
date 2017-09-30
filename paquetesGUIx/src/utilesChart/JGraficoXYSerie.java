/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesChart;

import ListDatos.JListDatos;

/**Una serie del grafico XY, DATOS*/
public class JGraficoXYSerie {
    /**caption de la serie actual*/
    public String msSerieCaption;

    /**descripción de la serie que luego puede ser imprimida*/
    public String msSerieDescripcion;

    /**Identificador de la serie actual*/
    public String msSerieIdent;

    /**Clase ofunciones que contiene la coleccion(Recordset)*/
    public JListDatos moFunciones;

    /**Posicion en JListDatos del campo que representa el eje X*/
    public int mlCampoX;
    /**Posicion en JListDatos del campo que representa el eje Y*/
    public int mlCampoY;
    /**Si hay duplicados los elimina, si es no esta a true cuando haya duplicados casca*/
    public boolean mbEliminarDuplicados=false;
    
    public JGraficoXYSerie(){
        
    }
    public JGraficoXYSerie (final JListDatos poList, final int plPosiValorX, final int plPosiValorY, final String psSerieCaption, boolean pbEliminarDuplicados){
        moFunciones=poList;
        mlCampoX=plPosiValorX;
        mlCampoY=plPosiValorY;
        msSerieCaption=psSerieCaption;
        mbEliminarDuplicados=pbEliminarDuplicados;
    }
}
