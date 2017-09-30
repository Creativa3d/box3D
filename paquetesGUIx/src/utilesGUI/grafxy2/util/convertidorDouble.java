package utilesGUI.grafxy2.util;

import utiles.JDateEdu;
import java.text.NumberFormat;

/**Clase para convertir objetos de todo tipo a double*/
public class convertidorDouble{

  /**
   * Convierte a cadena el double pasado por parametro según el tipo pasado por parámetro
   * @return Devuelve el Objeto en String
   * @param poObjeto Objeto double a convertir a cadena
   * @param tipo Tipo original del Double a pasar a String
   */
  public static String toString(Double poObjeto,Class tipo){
    String lsResult = ""; 
    if (tipo==java.lang.Long.class) {
      Long l = new Long(poObjeto.longValue());
      lsResult = l.toString();

    }else if (tipo==java.lang.Integer.class) {
      Integer l = new Integer(poObjeto.intValue());
      lsResult = l.toString();

    }else if (tipo==java.lang.Double.class) {
      NumberFormat ll = NumberFormat.getNumberInstance();
      lsResult = ll.format(poObjeto.doubleValue());

    }else if (tipo==utiles.JDateEdu.class) {
      JDateEdu ld=JDateEdu.CDate(poObjeto.doubleValue());
      lsResult = ld.toString();
    }else{
      try{
        IDouble l = (IDouble)tipo.newInstance();
        l.AsignarDouble(poObjeto);
        lsResult = l.toString();
      }catch( Exception e ) {
        e.printStackTrace();
        lsResult = "";
      }
    }
    return lsResult;

  }
  /**
   * Convierte a double el objeto pasado por parámetro
   * @param poObjeto Objeto a convertir a double
   * @return Devuelve el double
   */
  public static double mddouble(Object poObjeto){
    double ldValor = 0.0;
    Class tipo = poObjeto.getClass();

    if (tipo==java.lang.Long.class) {
      Long l = (Long)poObjeto;
      ldValor = l.doubleValue();

    }else if (tipo==java.lang.Integer.class) {
      Integer l = (Integer)poObjeto;
      ldValor = l.doubleValue();

    }else if (tipo==java.lang.Double.class) {
      Double ld=(Double)poObjeto;
      ldValor = ld.doubleValue();

    }else if (tipo==utiles.JDateEdu.class) {
      JDateEdu ld=(JDateEdu)poObjeto;
      ldValor = ld.getFechaEnNumero();

    }else{
      IDouble l = (IDouble)poObjeto;
      ldValor = l.convertirDouble();
    }
    return ldValor;
  }
  /**
   * Convierte un double a double sin decimales
   * @param pdDouble Valor double
   * @return Valor double sin decimales
   */
  public static double abs(double pdDouble ){
    double ldValor=0.0;  
    if (pdDouble < 0){
        ldValor=-pdDouble;
    }else {
        ldValor=pdDouble;
    }
    return ldValor;
  }

}