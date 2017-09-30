package utilesGUI.grafxy;

/**
 * <p>Title: BDAInternet</p>
 * <p>Description: Aplicación consulta BDA para la diputación de Alicante</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa-inarsa</p>
 * @author Eduardo Gonzalez Carpena
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Graphics;

/**Estilo linea*/
public class JEstiloLinea {
  /**
   * Color de la linea
   */
  public Color moColor=Color.blue;

  /**
   * Grosor de la linea
   */
  public int mlGrosor=1;

  /**
   * Construtor
   */
  public JEstiloLinea(){
  }

  /**
   * Construtor pasando el color y grosor por defecto
   * @param poColor Color
   * @param plGrosor grosor
   */
  public JEstiloLinea(Color poColor,int plGrosor) {
    moColor = poColor;
    mlGrosor = plGrosor;
  }
  /**
   * Establece el estilo al objeto gráfico
   * @param g Objeto gráfico
   */
  void setEstilo(Graphics g){
    g.setColor(moColor);
  }
  /**
   * Pinta en el objeto gráfico la linea
   * @param g Objeto gráfico
   * @param x x punto 1
   * @param y y punto 1
   * @param x2 x punto 2
   * @param y2 y punto 2
   */
  void pintar(Graphics g,int x,int y,int x2,int y2){
    setEstilo(g);
    for(int i = 0; i<mlGrosor;i++){
      g.drawLine(x,y+i,x2,y2+i);
    }
  }

}
