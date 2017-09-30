package utilesGUI.grafxy2.estilos;

/**
 * <p>Title: GrafXY</p>
 * <p>Description: Generador de gráficos XY</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa</p>
 * @author Eduardo Gonzalez
 * @version 1.0
 */
import java.awt.*; 
import java.util.*;
import utilesGUI.grafxy2.*;
import utilesGUI.grafxy2.util.*;
import utiles.*;

/**Estilo serie de lineas y punto*/
public class JEstiloSerieLineaPunto implements IEstiloSerie {
  /**
   * Estilo de la linea
   */
  public JEstiloLinea moEstiloLinea;
  /**
   * Estilo del punto
   */
  public JEstiloPunto moEstiloPunto;
  /**
   * Ancho de la muestra a pintar
   */
  private static final int mclAnchoMuestra=30;
  /**
   * Contructor
   */
  public JEstiloSerieLineaPunto() {
    moEstiloLinea = new JEstiloLinea();
    moEstiloPunto = new JEstiloPunto();
  }

  /**
   * pinta una muestra del estilo de la serie actual
   */
  public void pintarMuestra(Graphics g,int x,int y){
    moEstiloLinea.pintar(g,x,y,x+mclAnchoMuestra,y);
    moEstiloPunto.pintar(g,x+(mclAnchoMuestra/2)-(moEstiloPunto.mlTamano/2),y);
    //return mlAnchoMuestra;
  }
  /**Pinta la serie*/
  public void pintarSerie(JEjeCoordenadas poEjeCoordenadas, double pdValorNulo, IListaElementos poX, IListaElementos poY, IListaElementos poEstilos ){
    double ldValorY=0; 
//    double ldValorX=0;
    Object loValorYAnt=null;
    Object loValorXAnt=null;
    Object loValorX=null;
    Object loValorY=null;
    JEstiloSerieLineaPunto loEstilo;

    Iterator enumX =poX.iterator();
    Iterator enumY =poY.iterator();
    //recorremos todos los datos
    for (int i=0; enumX.hasNext()&&enumY.hasNext() ;) {
        //valores
        loValorX=enumX.next();
        loValorY=enumY.next();
        ldValorY=convertidorDouble.mddouble(loValorY);
//        ldValorX=convertidorDouble.mddouble(loValorX);

        //si es valor nulo reiniciar el i como si fuera la 1º vez
        if (pdValorNulo==ldValorY) {
            i=0;
        }else{

            //vemos el estilo del punto
            if (poEstilos.size()!=0){
              loEstilo=(JEstiloSerieLineaPunto)moEstilodeX(poEstilos, loValorX);
              if (loEstilo==null){
                loEstilo=this;
              }
            }else{
              loEstilo=this;
            }


            if (i!=0){
                //pintamos el punto
                loEstilo.pintar(poEjeCoordenadas, loValorXAnt, loValorYAnt, loValorX, loValorY);
            }else{
                //pintamos el punto
                loEstilo.pintar(poEjeCoordenadas, null, null, loValorX, loValorY);
            }
            loValorYAnt = loValorY;
            loValorXAnt = loValorX;
            i+=1;
        }
    }
      
  }
    /**
    * Estilo del punto según el valor de la X
    * @param poValorX valor X
    * @return estilo de ese valor
    */
    private Object moEstilodeX(IListaElementos poEstilosPuntos, Object poValorX){
        Iterator loEnum = poEstilosPuntos.iterator();
        EstiloPunto loEstiloPunto=null;
        Object loReturn = null; 
        for(;loEnum.hasNext();){
          loEstiloPunto = (EstiloPunto)loEnum.next();
          if (loEstiloPunto.moX == poValorX){
            loReturn = loEstiloPunto.moEstiloSerie;
          }
        }
        return loReturn;
    }
    /**Pinta un elemento*/
    void pintar(JEjeCoordenadas poEjeCoordenadas, Object xAnt, Object yAnt, Object x, Object y) {
        //pintamos la linea si no es el primer elemento
        if (xAnt != null){
          moEstiloLinea.pintar(poEjeCoordenadas, xAnt, yAnt, x, y);
        }

        //pintamos el punto
        moEstiloPunto.pintar(poEjeCoordenadas, x, y);
    }
    /**Ancho de la muestra*/
    public int getAnchoMuestra(){
      return mclAnchoMuestra;
    }
}
