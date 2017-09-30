package utilesGUI.grafxy;

/**
 * <p>Title: GrafXY</p>
 * <p>Description: Generador de gráficos XY</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa</p>
 * @author Eduardo Gonzalez
 * @version 1.0
 */
import java.awt.Graphics; 

/**Estilo serie*/
public class JEstiloSerie {
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
  public static final int mclAnchoMuestra=30;
  /**
   * Contructor
   */
  public JEstiloSerie() {
    moEstiloLinea = new JEstiloLinea();
    moEstiloPunto = new JEstiloPunto();
  }

  /**
   * pinta una muestra del estilo de la serie actual
   */
  void pintarMuestra(Graphics g,int x,int y){
    moEstiloLinea.pintar(g,x,y,x+mclAnchoMuestra,y);
    moEstiloPunto.pintar(g,x+(mclAnchoMuestra/2)-(moEstiloPunto.mlTamano/2),y);
    //return mlAnchoMuestra;
  }
}