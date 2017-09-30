package utilesGUI.grafxy;

/**
 * <p>Title: GrafXY</p>
 * <p>Description: Generador de gráficos XY</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa</p>
 * @author Eduardo Gonzalez
 * @version 1.0
 */
import java.awt.Color; 
import java.awt.Graphics;

/**Estilo punto*/
public class JEstiloPunto {
  /** constante tipo circulo hueco */
  public static final int mclCirculoHueco=1;
  /** constante tipo circulo relleno */
  public static final int mclCirculo=2;
  /** constante tipo cuadrado hueco */
  public static final int mclCuadradoHueco=3;
  /** constante tipo circulo relleno */
  public static final int mclCuadrado=4;

  /**
   * Estilo del punto (usar constantes)
   */
  public int mlEstilo=mclCirculo;
  /**
   * Color del punto
   */
  public Color moColor=Color.blue;
  /**
   * Tamaño del punto
   */
  public int mlTamano=7;

  Object moX=null;
  /**Constructor */
  public JEstiloPunto() {
  }
  /**
   * Contructor estilo punto
   * @param plEstilo estilo del punto
   * @param poColor Color del punto
   * @param plTamano tamaño del punto
   */
  public JEstiloPunto(int plEstilo,Color poColor,int plTamano ) {
    mlEstilo=plEstilo;
    moColor=poColor;
    mlTamano=plTamano;
  }
  /**
   * Pinta el punto en el objeto gráfico
   * @param g Objeto gráfico
   * @param x x del punto
   * @param y y del punto
   */
  void pintar(Graphics g,int x, int y){
    g.setColor( moColor);
    if (mlEstilo==mclCirculo){
      g.fillOval(x-(mlTamano/2) ,y-(mlTamano/2),mlTamano,mlTamano);
    }else if (mlEstilo==mclCirculoHueco){
      g.drawOval(x-(mlTamano/2) ,y-(mlTamano/2),mlTamano,mlTamano);
    }else if (mlEstilo==mclCuadrado){
      g.fillRect(x-(mlTamano/2) ,y-(mlTamano/2),mlTamano,mlTamano);
    }else if (mlEstilo==mclCuadradoHueco){
      g.drawRect(x-(mlTamano/2) ,y-(mlTamano/2),mlTamano,mlTamano);
    }
  }
}
