/*
 * JGrafDatosY.java
 *
 * Created on 19 de abril de 2004, 16:27
 */

package utilesGUI.grafxy2;

import java.awt.*;
import java.util.Iterator;
import utiles.*;
import utilesGUI.grafxy2.estilos.*;
import utilesGUI.grafxy2.util.*;

/**Datos del eje Y*/
public class JGrafDatosEjeY {
    /**
    * Gráfico padre(importante por los objetos de X máx e X mín)
    */
    private JGrafDatosEjeX moDatos;
    /**
    * Lista de series
    */
    private IListaElementos moSeries=new JListaElementos();
    /**
    * Estilo texto de la leyenda
    */
    public JEstiloTexto moEstiloLeyendaSerie = new JEstiloTexto();

    private JEjeCoordenadas moEjeCoordenadas;
        

    /**
     * Constructor
     * @param psTitulo título del eje Y
     * @param poGrafxy Gráfico base
     * @param pbesY1 Indica si es el eje1 o eje2
     */
    public JGrafDatosEjeY(JGrafDatosEjeX poGrafxy, boolean pbesY1, String psTitulo){
        moEjeCoordenadas = new JEjeCoordenadas(pbesY1);
        moEjeCoordenadas.msTituloY = psTitulo;
        moDatos=poGrafxy;
        
    }
    /**
    * Constructor
    * @param poGrafxy Gráfico base
    * @param psTitulo Título del eje
    */
    public JGrafDatosEjeY(JGrafDatosEjeX poGrafxy, String psTitulo) {
        this(poGrafxy, true, psTitulo);
    }
    /**
    * Añade una serie al eje y
    * @return La serie creada
    */
    public JGrafDatosSerie addSerie(){
        JGrafDatosSerie loSerie = new JGrafDatosSerie(moDatos, this);
        moSeries.add(loSerie);
        return loSerie;
    }
    /**
    * Devuelve una serie del Eje
    * @param i Indice de la serie
    * @return Serie correspondiente con el indice
    */
    public JGrafDatosSerie getSerie(int i){
        return (JGrafDatosSerie)moSeries.get(i);
    }
    /**
    * Borra todas las series del eje Y
    */
    public void borrarSeries(){
        moSeries.clear();
        moEjeCoordenadas.setYMax(null);
        moEjeCoordenadas.setYMin(null);
    }
    /**
    * Borra un serie concreta
    * @param i Indice de la serie a borrar
    */
    public void borrarSerie(int i){
        moSeries.remove(i);
        if (moSeries.size()==0){
            moEjeCoordenadas.setYMax(null);
            moEjeCoordenadas.setYMin(null);
        }
    }

    /**
     * Borra un serie concreta
     * @param poObject serie
     */
    public void borrarSerie(Object poObject){
        moSeries.remove(poObject);
        if (moSeries.size()==0){
            moEjeCoordenadas.setYMax(null);
            moEjeCoordenadas.setYMin(null);
        }
    }
  /**
   * pinta los datos (series)
   * @param gi Gráfico en donde pintar
   */
   public void pintar(Graphics g){
        //calculo estadistico
        if (moSeries.size()>0){
            moEjeCoordenadas.calculoEstadistico(moSeries);
        }
        //pintamos las coordenadas
        getEjeCoordenadas().setGraphics(g);
        getEjeCoordenadas().pintarEjeY(g);
        //si hay alguna serie pintamos las series
        if (moSeries.size()>0){
            
          //dibujamos en la imagen todas las series
          Iterator enum1 = moSeries.iterator();
          for (JGrafDatosSerie loSerie; enum1.hasNext() ;) {
              loSerie=(JGrafDatosSerie)enum1.next();
              loSerie.pintar(moEjeCoordenadas);
          }
        }
    }
    public void pintarLeyenda(Graphics gi, int x,int y, Rectangle r, boolean pbLeyendaSerieVertical){
        r.height=0;
        r.width=0;
        //si hay alguna serie pintamos las series
        if (moSeries.size()>0){
          int lAncho=0;
          //dibujamos en la leyenda todas las series
          Iterator enum1 = moSeries.iterator();
          for (JGrafDatosSerie loSerie; enum1.hasNext() ;) {
              loSerie=(JGrafDatosSerie)enum1.next();

              if(pbLeyendaSerieVertical){
                  //pintamos una muestra de la serie
                  loSerie.getEstiloSerie().pintarMuestra(gi,x,y+r.height);
                  lAncho  = loSerie.getEstiloSerie().getAnchoMuestra();

                  //pintamos el titulo de la serie
                  lAncho += moEstiloLeyendaSerie.pintar(gi,loSerie.msCaption, x+lAncho+5,y+r.height+5);
                  lAncho += 10;

                  //vemos el mayor ancho
                  if (lAncho>r.width) {
                      r.width=lAncho;
                  }
                  //incrementamos el alto
                  r.height += moEstiloLeyendaSerie.getAlto(gi)+2;
              }else{
                  //pintamos una muestra de la serie
                  loSerie.getEstiloSerie().pintarMuestra(gi,x+r.width,y);
                  lAncho  += loSerie.getEstiloSerie().getAnchoMuestra();

                  //pintamos el titulo de la serie
                  lAncho += moEstiloLeyendaSerie.pintar(gi,loSerie.msCaption, x+lAncho+5,y+5);
                  lAncho += 10;

                  r.width = lAncho;
                  //incrementamos el alto
                  r.height = moEstiloLeyendaSerie.getAlto(gi)+2;
              }
          }
        }
    }


    /**
     * Eje de coordenadas
     * @return eje
     */
    public JEjeCoordenadas getEjeCoordenadas(){
        return moEjeCoordenadas;
    }
}
