package utilesGUI.grafxy;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import utiles.*;

/**Objeto serie*/
public class JSerie {
  /**nombre*/
  public String msNombre;
  /**caption*/
  public String msCaption;
  /**estilo de la serie generico*/
  public JEstiloSerie moEstilo;

  private IListaElementos moX;//donde se almacenan los valores de la x
  private IListaElementos moY;//donde se almacenan los valores de la y
  private IListaElementos moEstilosPuntos;//estilo punto en tabla hash
  private JGrafXY moGrafXY;//grafico padre
  private JEjeY moEjeY;//eje al que pertenece
  private boolean mbHayEstiloPer=false;

  /**
   * contructor
   * @param poGrafXY objeto padre
   * @param poEjeY eje Y al que pertenece
   */
  public JSerie(JGrafXY poGrafXY,JEjeY poEjeY) {
    moGrafXY=poGrafXY;
    moEjeY=poEjeY;
    moEstilo=new JEstiloSerie();
    msNombre="";
    msCaption="";
    moEstilosPuntos = new JListaElementos();
    moX = new JListaElementos();
    moY = new JListaElementos();
  }

  /**
   * añade un punto
   * @param x valor x
   * @param y valor y
   */
  public void addXY(Object x,Double y){

    moX.add(x);
    moY.add((Object)y);

    double ldx=convertidorDouble.mddouble(x);
    double ldy=convertidorDouble.mddouble(y);

    if (moGrafXY.mdMaxX==null) {
      moGrafXY.mdMaxX=x;
      moGrafXY.mdMinX=x;
    }
    else{
      double l2 = convertidorDouble.mddouble(moGrafXY.mdMaxX);
      if (ldx>l2) {
          moGrafXY.mdMaxX = x;
      }
      l2 = convertidorDouble.mddouble(moGrafXY.mdMinX);
      if (ldx<l2) {
          moGrafXY.mdMinX = x;
      }
    }

    if (moEjeY.mdValorNulo!=ldy){
      if (moEjeY.mdMaxY==null) {
        moEjeY.mdMaxY=y;
        moEjeY.mdMinY=y;
      }
      else{
        double l2 = convertidorDouble.mddouble(moEjeY.mdMaxY);
        if (ldy>l2){
            moEjeY.mdMaxY = y;
        }
        l2 = convertidorDouble.mddouble(moEjeY.mdMinY);
        if (ldy<l2) {
            moEjeY.mdMinY = y;
        }
      }
    }
  }

  /**
   * Añade un punto con un estilo personalizado
   * @param x Valor x
   * @param y Valor y
   * @param poEstilo estilo
   */
  public void addXY(Object x,Double y,JEstiloPunto poEstilo){
    addXY(x,y);
    mbHayEstiloPer=true;
    poEstilo.moX=x;
    moEstilosPuntos.add(poEstilo);
  }

  /**
   * para pintar la serie dentro de una imagen
   * @param g
   * @param poRectangulo
   * @param pdUnidadX
   * @param pdUnidadY
   * @param pdxMin
   * @param pdyMin
   */
  void pintar(Graphics g, Rectangle poRectangulo,
    double pdUnidadX, double pdUnidadY,
    double pdxMin,double pdyMin) {
    int lx; int ly;
    double ldValory=0; double ldValorx=0;
    Object loValorX;
    JEstiloPunto loEstiloPunto;

    Iterator enumX =moX.iterator();
    Iterator enumY =moY.iterator();
    //recorremos todos los datos
    for (int i=0,lxUlt=0,lyUlt=0; enumX.hasNext()&&enumY.hasNext() ;) {
      //valores
      loValorX=enumX.next();
      ldValory=convertidorDouble.mddouble(enumY.next());
      ldValorx=convertidorDouble.mddouble(loValorX);

      //si es valor nulo reiniciar el i como si fuera la 1º vez
      if (moEjeY.mdValorNulo==ldValory)
        i=0;
      else
      {
        //sacamos las coordenadas
        lx=(int)((-pdxMin+ldValorx)*pdUnidadX);
        ly=(int)((-pdyMin+ldValory)*pdUnidadY);

        //pintamos la linea si no es el primer elemento
        if (i!=0){
          moEstilo.moEstiloLinea.pintar(g,
            (poRectangulo.x + lxUlt),
            (poRectangulo.y + poRectangulo.height - lyUlt),
            (poRectangulo.x + lx),
            (poRectangulo.y + poRectangulo.height- ly));
        }
        //vemos el estilo del punto
        if (mbHayEstiloPer){
          loEstiloPunto=moEstilodeX(loValorX);
          if (loEstiloPunto==null){
            loEstiloPunto=moEstilo.moEstiloPunto;
          }
        }else{
          loEstiloPunto=moEstilo.moEstiloPunto;
        }

        //pintamos el punto
        loEstiloPunto.pintar(g,
          (poRectangulo.x + lx),
          (poRectangulo.y + poRectangulo.height- ly));

        lxUlt=lx;
        lyUlt=ly;
        i+=1;
      }
    }
  }
  /**
   * Estilo del punto según el valor de la X
   * @param poValorX
   * @return
   */
  private JEstiloPunto moEstilodeX(Object poValorX){
    JEstiloPunto loEstilo = null;  
    Iterator loEnum = moEstilosPuntos.iterator();
    JEstiloPunto loEstiloPunto=null;
    for(;loEnum.hasNext();){
      loEstiloPunto = (JEstiloPunto)loEnum.next();
      if (loEstiloPunto.moX == poValorX){
        loEstilo=loEstiloPunto;
      }
    }
    return loEstilo;
  }
}
