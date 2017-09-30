package utilesGUI.grafxy;

/**
 * <p>Title: Grafico XY</p>
 * <p>Description: Interfaz que un objeto debe cumplir para ser compatible con el gráfico</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa-inarsa</p>
 * @author Eduardo Gonzalez
 * @version 1.0
 */

public interface IDouble {
    /**
     * Devolver el objeto convertido en double
     * @return valor double
     */
  public double convertirDouble();
  /**
   * Devolver el objeto en String
   * @param psFormat Formato concreto
   * @return cadena
   */
  public String toString(String psFormat);
  /**
   * Crear el objeto a partir de un double
   * @param pd Double a transformar
   * @return el objeto iDouble
   */
  public IDouble AsignarDouble(Double pd);
}
