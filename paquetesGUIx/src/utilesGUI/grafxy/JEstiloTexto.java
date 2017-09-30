package utilesGUI.grafxy;

/**
 * <p>Title: Grafico XY</p>
 * <p>Description: Generador de gráficos XY</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa-inarsa</p>
 * @author Eduardo Gonzalez
 * @version 1.0
 */
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.FontMetrics;

/**Estilo texto*/
public class JEstiloTexto {
  /**
   * Fuente
   */
  public Font moFuente = new Font("Courier New", Font.PLAIN, 10);
  /**
   * Color
   */
  public Color moColor = Color.black;
  /**
   * Angulo de impresión
   */
  public double mdAngulo=0;
  /** cte alineación Izq. */
  public static final int mclAlinIzq =0;
  /** cte alineación Der. */
  public static final int mclAlinDer =1;
  /** cte alineación centro */
  public static final int mclAlinCent =2;

  /**
   * Contructor
   */
  public JEstiloTexto() {
  }

  /**
   * Contructor
   * @param poFuente fuente
   * @param poColor color
   */
  public JEstiloTexto(Font poFuente,Color poColor) {
    moFuente=poFuente;
    moColor=poColor;
  }
  /**
   * Establece el estilo al objeto gráfico
   */
  void setEstilo(Graphics g){
    g.setColor(moColor);
    g.setFont(moFuente);
  }
  /**
   * Pinta el texto en el objeto gráfico
   * @param g Objeto gráfico
   * @param psTexto Texto a pintar
   * @param r Rectangulo en donde pintar el texto
   * @return Alto del texto
   */
  int pintar(Graphics g,String psTexto,Rectangle r){
    setEstilo(g);
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    int lAncho = currentMetrics.stringWidth(psTexto);
    g.drawString(psTexto,r.x + r.width/2 - lAncho/2,r.y);
    return currentMetrics.getMaxDescent();
  }
  /**
   * Pinta el texto en el objeto gráfico
   * @param g Objeto gráfico
   * @param psTexto Texto a pintar
   * @param r Rectangulo en donde pintar el texto
   * @param plAlineacion Alineación en rectangulo
   * @return Alto del texto
   */
  int pintar(Graphics g,String psTexto,Rectangle r, int plAlineacion){
    int lAlto;  
    switch(plAlineacion){
      case mclAlinIzq:
          lAlto=pintar(g,psTexto,r.x,r.y);
          break;
      case mclAlinDer:
          setEstilo(g);
          FontMetrics currentMetrics = g.getFontMetrics(moFuente);
          int lAncho = currentMetrics.stringWidth(psTexto);
          g.drawString(psTexto,r.x + r.width - lAncho -1,r.y);
          lAlto=currentMetrics.getMaxDescent();
          break;
      case mclAlinCent:
          lAlto=pintar(g,psTexto,r);
          break;
      default:
          lAlto=-1;
    }
    if(lAlto==-1){
        setEstilo(g);
        FontMetrics currentMetrics = g.getFontMetrics(moFuente);
        int lAncho = currentMetrics.stringWidth(psTexto);
        g.drawString(psTexto,r.x + r.width/2 - lAncho/2,r.y);
        lAlto = currentMetrics.getMaxDescent();
    }
    return lAlto;
  }
  /**
   * Pinta el texto en el objeto gráfico
   * @param g Objeto gráfico
   * @param psTexto Texto a pintar
   * @param x x punto
   * @param y y punto
   * @return Ancho del texto
   */
  int pintar(Graphics g,String psTexto,int x,int y){
    setEstilo(g);
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    g.drawString(psTexto,x,y);
    return currentMetrics.stringWidth(psTexto);
  }
  /**
   * Devuelve el alto de la fuente del objeto Gráfico
   * @param g Objeto Gráfico
   * @return Alto
   */
  int mlAlto(Graphics g){
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    return currentMetrics.getHeight();
  }
  /**
   * Devuelve el ancho de del texto según la fuente del objeto Gráfico
   * @param g Objeto Gráfico
   * @param psTexto Texto
   * @return Ancho
   */
  int mlAncho(Graphics g,String psTexto){
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    return currentMetrics.stringWidth(psTexto);
  }
}