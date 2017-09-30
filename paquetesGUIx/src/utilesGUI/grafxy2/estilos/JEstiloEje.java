package utilesGUI.grafxy2.estilos;

/**
 * <p>Title: BDAInternet</p>
 * <p>Description: Estilo de un eje</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa-inarsa</p>
 * @author Eduardo Gonzalez Carpena
 * @version 1.0
 */
import java.awt.*;

/**Estilo eje*/
public class JEstiloEje {
    /**
     * Formato
     */
    public String msFormato="";
    /**
     * Objeto de estilo texto para las etiquetas del eje
     */
    public JEstiloTexto moEstiloTexto = new JEstiloTexto();
    /**
     * Objeto de estilo texto para el título del eje
     */
    public JEstiloTexto moEstiloTextoCaption;
    /**
     * Color eje x
     */
    public Color moColorEje = Color.black;
    /**
     * pintamos las lineas de los ejes
     */
    public boolean mbLineasDivision = false;
    /**
     * Color lineas division
     */
    public Color moColorLinea = Color.black;

  /**
   * Constructor estilo eje
   */
  public JEstiloEje(){
    moEstiloTextoCaption = new JEstiloTexto(new Font("Dialog", Font.BOLD, 12),Color.gray);
  }
  /**
   * Constructor estilo eje, pasando el formato y angulo por defecto
   * @param psFormato formato letras
   * @param pdAngulo angulo letras
   */
  public JEstiloEje( String psFormato,double pdAngulo) {
    moEstiloTextoCaption = new JEstiloTexto(new Font("Courier New", Font.BOLD, 12),Color.gray);
    msFormato=psFormato;
    moEstiloTexto.mdAngulo=pdAngulo;
  }
  /**
   * Pintamos el eje en el objeto gráfico
   * @param g objeto gráfico
   * @param x X
   * @param y Y
   * @param width ancho
   * @param height alto
   */
  public void pintarEje(Graphics g, int x, int y, int width, int height){
    g.setColor(moColorEje);
    g.drawLine(x, y, width, height);
  }
}
