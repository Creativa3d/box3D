/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesChart;

import utiles.IListaElementos;
import utiles.JListaElementos;

/**Un elemento del desplegable de grafico XY, puede tener varias series en el grafico y una coleccion de filtros*/
public class JParamGraf2Y {

    /**Campo*/
    public String msCampoIdentificador;
    /**Nombre del campo a presentar en el combo*/
    public String msCampoDescripcion;
    /**Inicio eje X*/
    public String msXInicio;
    /**Inicio eje Y*/
    public String msYInicio;
    /**Inicio eje X*/
    public String msXFin;
    /**Inicio eje Y*/
    public String msYFin;    
    /**Coleccion de Interfaces IGraficoXY a presentar al seleccionar este campo*/
    public IListaElementos<IGraficoXY> moColleciGrafXY;
    /**Coleccion de filtros por nombre disponibles para este campo*/
    public IListaElementos<String> moCollecFiltros;
    /**Indicar q este parametro es el que primer ose presenta en el grafico*/
    public boolean mbDefecto;
    
    /**inicializamos memoria*/
    public JParamGraf2Y() {
        moColleciGrafXY = new JListaElementos();
        moCollecFiltros = new JListaElementos();
    }
}
