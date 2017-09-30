/*
 * JDatosSerie.java
 *
 * Created on 19 de abril de 2004, 16:09
 */

package utilesGUI.grafxy2;

import java.awt.*;
import utilesGUI.grafxy2.estilos.*;
import utilesGUI.grafxy2.util.*;
import utiles.*;

/**Serie de datos*/
public class JGrafDatosSerie {
    private IEstiloSerie moEstilo;//estilo de la serie generico
    /**nombre*/
    public String msNombre;
    /**Captión*/
    public String msCaption;
    
    private IListaElementos moX;//donde se almacenan los valores de la x
    private IListaElementos moY;//donde se almacenan los valores de la y
    private IListaElementos moEstilosPuntos;//estilo punto en tabla hash
    
    private JGrafDatosEjeX moDatos;
    private JGrafDatosEjeY moEjeY;
    
    private double mdValorNulo=Double.MAX_VALUE;

//    private boolean mbHayEstiloPer=false;

    /**
     * Creates a new instance of JDatosSerie
     * @param poDatos Eje X
     * @param poEjeY Eje Y
     */
    public JGrafDatosSerie(JGrafDatosEjeX poDatos, JGrafDatosEjeY poEjeY){
        moDatos = poDatos;
        moEjeY = poEjeY;
        msNombre="";
        msCaption="";
        moX = new JListaElementos();
        moY = new JListaElementos();
        moEstilosPuntos = new JListaElementos();
        moEstilo = new JEstiloSerieLineaPunto();
    }
    
    /**
     * Establece Valor(double) nulo para esta serie
     * @param pdValor valor
     */
    public void setValorNulo(double pdValor){
        mdValorNulo = pdValor;
    }
    /**
     * Devuelve Valor(double) nulo para esta serie
     * @return valor nulo
     */
    public double getValorNulo(){
        return mdValorNulo;
    }
    /**
     * Estilo serie
     * @param poEstiloSerie estilo
     */
    public void setEstiloSerie(IEstiloSerie poEstiloSerie){
        moEstilo = poEstiloSerie;
    }
    /**
     * Estilo serie
     * @return estilo
     */
    public IEstiloSerie getEstiloSerie(){
        return moEstilo;
    }
    /**
    * añade un punto
    * @param x valor x
    * @param y valor y
    */
    public void addXY(Object x,Double y){
        moX.add(x);
        if(y == null) {
            y = new Double(mdValorNulo);
        }
        moY.add(y);

        double ldx=convertidorDouble.mddouble(x);
        double ldy=convertidorDouble.mddouble(y);

        if (moDatos.getXMax()==null) {
          moDatos.setXMax(x);
          moDatos.setXMin(x);
        }
        else{
          double l2 = convertidorDouble.mddouble(moDatos.getXMax());
          if (ldx>l2) {
              moDatos.setXMax(x);
          }
          l2 = convertidorDouble.mddouble(moDatos.getXMin());
          if (ldx<l2) {
              moDatos.setXMin(x);
          }
        }

        if (mdValorNulo!=ldy){
          if (moEjeY.getEjeCoordenadas().getYMax()==null) {
            moEjeY.getEjeCoordenadas().setYMax(y);
            moEjeY.getEjeCoordenadas().setYMin(y);
          }
          else{
            double l2 = convertidorDouble.mddouble(moEjeY.getEjeCoordenadas().getYMax());
            if (ldy>l2) {
                moEjeY.getEjeCoordenadas().setYMax(y);
            }
            l2 = convertidorDouble.mddouble(moEjeY.getEjeCoordenadas().getYMin());
            if (ldy<l2) {
                moEjeY.getEjeCoordenadas().setYMin(y);
            }
          }
        }
  }
    /**
    * Añade un punto con un estilo personalizado
    * @param x valor x
    * @param y valor y
    * @param poEstilo estilo personalizado
    */
    public void addXY(Object x, Double y, Object poEstilo){
        addXY(x,y);
//        mbHayEstiloPer=true;
        moEstilosPuntos.add(new EstiloPunto(x, poEstilo));
    }

   /**
   * para pintar la serie dentro de una imagen
   * @param g
   * @param poEjeCorrdenadas
   */
  void pintar(JEjeCoordenadas poEjeCoordenadas) {
      moEstilo.pintarSerie(poEjeCoordenadas, mdValorNulo, moX, moY, moEstilosPuntos);
  }

}

