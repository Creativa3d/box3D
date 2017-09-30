/*
 * IEstiloSerie.java
 *
 * Created on 19 de abril de 2004, 17:01
 */

package utilesGUI.grafxy2.estilos;

import java.awt.Graphics;
import utiles.*;

/**Estilo de la serie*/
public interface IEstiloSerie {
    /**
     * Pinta la serie
     * @param poEjeCoordenadas eje de coor.
     * @param pdValorNulo el valor nulo
     * @param poX X
     * @param poY Y
     * @param poEstilos Lista de estilos
     */
    public void pintarSerie(JEjeCoordenadas poEjeCoordenadas, double pdValorNulo, IListaElementos poX, IListaElementos poY, IListaElementos poEstilos );
    /**
     * Pinta la muestra
     * @param g Objeto gráfico en donde pintar
     * @param x X 
     * @param y Y
     */
    public void pintarMuestra(Graphics g,int x,int y);
    /**
     * Devuelve el ancho de la muestra
     * @return ancho
     */
    public int getAnchoMuestra();
}
