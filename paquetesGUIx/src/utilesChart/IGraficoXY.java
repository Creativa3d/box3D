/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesChart;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

/**Una serie del grafico XY, datos + estilos y filtro*/
public interface IGraficoXY {
    /**estilo de la serie, por si se quiere cambiar en funcion de la serie*/
    public void estiloSerie(final XYLineAndShapeRenderer poEstiloSerie, int plSerie) throws Exception;
    /**estilo de la serie, por si se quiere cambiar en funcion de la serie*/
    public void estiloSerie(final BarRenderer poEstiloSerie, int plSerie) throws Exception;
    /**para aplicar un filtro a la coleccion existente de oseries*/
    public void aplicarFiltro(final String psFiltro) throws Exception;

    /**JGraficoXYSerie*/
    public JGraficoXYSerie oSerie() throws Exception;
}
