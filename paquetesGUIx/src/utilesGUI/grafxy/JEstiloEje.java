package utilesGUI.grafxy;

/**
 * <p>Title: BDAInternet</p>
 * <p>Description: Estilo de un eje</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa-inarsa</p>
 * @author Eduardo Gonzalez Carpena
 * @version 1.0
 */
import java.awt.Font;
import java.awt.Color;

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
   * Constructor estilo eje
   */
  public JEstiloEje(){
    moEstiloTextoCaption = new JEstiloTexto(new Font("Courier New", Font.BOLD, 12),Color.gray);
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
}