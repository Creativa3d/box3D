package utilesGUI.grafxy2.estilos;

/**
 * <p>Title: Grafico XY</p>
 * <p>Description: Generador de gr�ficos XY</p>
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
  public Font moFuente = new Font("Dialog", Font.PLAIN, 10);
  /**
   * Color
   */
  public Color moColor = Color.black;
  /**
   * Angulo de impresi�n
   */
  public double mdAngulo=0;
  /**
   * Constante de alineaci�n izq.
   */
  public static final int mclAlinIzq =0;
  /**
   * Constante de alineaci�n der.
   */
  public static final int mclAlinDer =1;
  /**
   * Constante de alineaci�n centro
   */
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
   * Establece el estilo al objeto gr�fico
   * @param g objeto en donde poner el estilo
   */
  public void setEstilo(Graphics g){
    g.setColor(moColor);
    g.setFont(moFuente);
  }
  /**
   * Pinta el texto en el objeto gr�fico
   * @param g Objeto gr�fico
   * @param psTexto Texto a pintar
   * @param r Rectangulo en donde pintar el texto
   * @return Alto del texto
   */
  public int pintar(Graphics g,String psTexto,Rectangle r){
    setEstilo(g);
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    int lAncho = currentMetrics.stringWidth(psTexto);
    g.drawString(psTexto,r.x + r.width/2 - lAncho/2,r.y);
    return currentMetrics.getMaxDescent();
  }
  /**
   * Pinta el texto en el objeto gr�fico
   * @param g Objeto gr�fico
   * @param psTexto Texto a pintar
   * @param r Rectangulo en donde pintar el texto
   * @param plAlineacion Alineaci�n en rectangulo
   * @return Alto del texto
   */
  public int pintar(Graphics g,String psTexto,Rectangle r, int plAlineacion){
    int lAlto;
    switch(plAlineacion){
      case mclAlinIzq:
          lAlto = pintar(g,psTexto,r.x,r.y);
          break;
      case mclAlinDer:
          setEstilo(g);
          FontMetrics currentMetrics = g.getFontMetrics(moFuente);
          int lAncho = currentMetrics.stringWidth(psTexto);
          g.drawString(psTexto,r.x + r.width - lAncho -1,r.y);
          lAlto = currentMetrics.getMaxDescent();
          break;
      case mclAlinCent:
          lAlto = pintar(g,psTexto,r);
          break;
      default:
          lAlto = -1;
    }
    if(lAlto ==-1){
        setEstilo(g);
        FontMetrics currentMetrics = g.getFontMetrics(moFuente);
        int lAncho = currentMetrics.stringWidth(psTexto);
        g.drawString(psTexto,r.x + r.width/2 - lAncho/2,r.y);
        lAlto=currentMetrics.getMaxDescent();
    }
    return lAlto;
  }
  /**
   * Pinta el texto en el objeto gr�fico
   * @param g Objeto gr�fico
   * @param psTexto Texto a pintar
   * @param x x punto
   * @param y y punto
   * @return Ancho del texto
   */
  public int pintar(Graphics g,String psTexto,int x,int y){
    setEstilo(g);
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    g.drawString(psTexto,x,y);
    return currentMetrics.stringWidth(psTexto);
  }
  /**
   * Devuelve el alto de la fuente del objeto Gr�fico
   * @param g Objeto Gr�fico
   * @return Alto
   */
  public int getAlto(Graphics g){
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    return currentMetrics.getHeight();
  }
  /**
   * Devuelve el ancho de del texto seg�n la fuente del objeto Gr�fico
   * @param g Objeto Gr�fico
   * @param psTexto Texto
   * @return Ancho
   */
  public int getAncho(Graphics g,String psTexto){
    FontMetrics currentMetrics = g.getFontMetrics(moFuente);
    return currentMetrics.stringWidth(psTexto);
  }
}
