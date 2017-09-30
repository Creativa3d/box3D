/*
 * JEstiloSerieBarras.java
 *
 * Created on 22 de abril de 2004, 10:42
 */

package utilesGUI.grafxy2.estilos;

import java.awt.*;
import java.util.Iterator;
import utiles.*;
import utilesGUI.grafxy2.util.*;

/**Estilo serie de barras*/
public class JEstiloSerieBarras  implements IEstiloSerie {
    /**color de la barra*/
    public Color moColor = Color.blue;
    /**Ancho de la barra*/
    public int mlAncho = 10;
    /**Desplazamiento con respecto al origen del valor, por defecto es -5 y como el ancho por defecto es 10 la barra se pinta justo en el centro*/
    public int mlDesplazamiento = -5;
    
    /** Creates a new instance of JEstiloSerieBarras */
    public JEstiloSerieBarras() {
    }
    /**
     * Contructor
     * @param poColor color Color
     * @param plAncho ancho Ancho
     * @param plDesplazamiento desplazamiento con respecto a la x(para incluir varias barras en la misma X(por ejemplo de series diferentes))
     */
    public JEstiloSerieBarras(Color poColor, int  plAncho, int plDesplazamiento) {
        moColor = poColor;
        mlAncho = plAncho;
        mlDesplazamiento = plDesplazamiento;
    }

    /**
     * Devuelve el ancho de la muestra(Este valor se usa en la leyenda)
     * @return ancho de la muestra
     */
    public int getAnchoMuestra() {
        return 30;
    }
    /**Pinta la muestra*/
    public void pintarMuestra(java.awt.Graphics g, int x, int y) {
        g.setColor(moColor);
        g.fillRect(x,y, 30, 8);
    }
    /**Pinta la serie*/
    public void pintarSerie(JEjeCoordenadas poEjeCoordenadas, double pdValorNulo, IListaElementos poX, IListaElementos poY, IListaElementos poEstilos) {
    double ldValory=0; 
//    double ldValorx=0;
    Object loValorX;
    Object loValorY;
    JEstiloSerieBarras loEstilo;

    Iterator enumX =poX.iterator();
    Iterator enumY =poY.iterator();
    //recorremos todos los datos
    for (int i=0; enumX.hasNext()&&enumY.hasNext() ;) {
        //valores
        loValorX=enumX.next();
        loValorY=enumY.next();
        ldValory=convertidorDouble.mddouble(loValorY);
//        ldValorx=convertidorDouble.mddouble(loValorX);

        //si es valor nulo reiniciar el i como si fuera la 1º vez
        if (pdValorNulo==ldValory) i=0;
        else{

            //vemos el estilo del punto
            if (poEstilos.size()!=0){
              loEstilo=(JEstiloSerieBarras)moEstilodeX(poEstilos, loValorX);
              if (loEstilo==null){
                loEstilo=this;
              }
            }else{
              loEstilo=this;
            }

            loEstilo.pintar(poEjeCoordenadas, loValorX, loValorY, mlDesplazamiento, mlAncho);

            i+=1;
        }
    }
    }
    //
    // Estilo del punto según el valor de la X
    // @param poValorX
    // @return
    //
    private Object moEstilodeX(IListaElementos poEstilosPuntos, Object poValorX){
        Iterator loEnum = poEstilosPuntos.iterator();
        EstiloPunto loEstiloPunto=null;
        Object loReturn=null;
        for(;loEnum.hasNext();){
          loEstiloPunto = (EstiloPunto)loEnum.next();
          if (loEstiloPunto.moX == poValorX){
            loReturn=loEstiloPunto.moEstiloSerie;
          }
        }
        return loReturn;
    }
    /**Pinta una barra*/
    void pintar(JEjeCoordenadas poEjeCoordenadas, Object x, Object y, int plDesplazamiento, int plAncho) {
        poEjeCoordenadas.setColor(moColor);
        poEjeCoordenadas.fillRectBarra(x, y, plDesplazamiento, plAncho);
    }
   
}
