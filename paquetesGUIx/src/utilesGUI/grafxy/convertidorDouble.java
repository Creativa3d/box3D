package utilesGUI.grafxy;
/**
 * <p>Title: Grafico XY</p>
 * <p>Description: Generador de gráficos XY</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa-inarsa</p>
 * @author Eduardo Gonzalez
 * @version 1.0
 */
import utiles.JDateEdu;
import java.awt.Graphics; 
import java.text.NumberFormat;

class convertidorDouble{
  /**
   * Convierte a cadena el double pasado por parametro según el tipo
   * @param poObjeto Objeto double a convertir a cadena
   * @param tipo Tipo original del Double a pasar a String
   * @return Devuelve el Objeto en String
   */
  private static final int mclPixelDiferencia= 40;
  public static int mlPixelDiferenciaMax = mclPixelDiferencia;

  static String toString(Double poObjeto,Class tipo){
    String lsReturn="";
    if (tipo==java.lang.Long.class) {
      Long l = new Long(poObjeto.longValue());
      lsReturn=l.toString();

    }else if (tipo==java.lang.Integer.class) {
      Integer l = new Integer(poObjeto.intValue());
      lsReturn=l.toString();

    }else if (tipo==java.lang.Double.class) {
      NumberFormat ll = NumberFormat.getNumberInstance();
      lsReturn=ll.format(poObjeto.doubleValue());

    }else if (tipo==utiles.JDateEdu.class) {
      JDateEdu ld=JDateEdu.CDate(poObjeto.doubleValue());
      lsReturn=ld.toString();
    }else{
      try{
        IDouble l = (IDouble)tipo.newInstance();
        l.AsignarDouble(poObjeto);
        lsReturn=l.toString();
      }catch( Exception e ) {
        e.printStackTrace();
        lsReturn="";
      }
    }
    return lsReturn;

  }
  /**
   * Convierte a double el objeto pasado por parámetro
   * @param poObjeto Objeto a convertir a double
   * @return Devuelve el double
   */
  static double mddouble(Object poObjeto){
    double ldValor=0.0;
    Class tipo = poObjeto.getClass();

    if (tipo==java.lang.Long.class) {
      Long l = (Long)poObjeto;
      ldValor=l.doubleValue();

    }else if (tipo==java.lang.Integer.class) {
      Integer l = (Integer)poObjeto;
      ldValor=l.doubleValue();

    }else if (tipo==java.lang.Double.class) {
      Double ld=(Double)poObjeto;
      ldValor=ld.doubleValue();

    }else if (tipo==utiles.JDateEdu.class) {
      JDateEdu ld=(JDateEdu)poObjeto;
      ldValor=ld.getFechaEnNumero();

    }else{
      IDouble l = (IDouble)poObjeto;
      ldValor=l.convertirDouble();
    }
    return ldValor;
  }
  /**
   * Devuelve la separacion devalores entre rayas
   * @param g En donde se dibujara el gráfico
   * @param poEstiloTexto Estilo del texto
   * @param poMax Valor máximo
   * @param poMin Valor mínimo
   * @param piheight Alto del gráfico
   * @return Valor de separación entre rayas
   */
  static double mdValorEntreRallas(Graphics g,JEstiloTexto poEstiloTexto, Object poMax, Object poMin,int piheight,boolean pbEsY){
    double ldMax;
    double ldMin;
    double ldAnchoGrafico;
    double ldAnchoValores=1;
    int lNumero = 0;
    double ldNumero=0;
      try{
        String lsDato;
        if (pbEsY)
          mlPixelDiferenciaMax = mclPixelDiferencia;
        else{
          if (poMax.getClass()==utiles.JDateEdu.class){
            mlPixelDiferenciaMax = 80;
          }else{
            int mlPixelDiferenciaMax = poEstiloTexto.mlAncho(g,poMax.toString())+10;
            if (mlPixelDiferenciaMax < mclPixelDiferencia ) {
                mlPixelDiferenciaMax = mclPixelDiferencia;
            }
          }
        }
        ldMax = mddouble(poMax);
        ldMin = mddouble(poMin);
        ldAnchoGrafico=(piheight-mlPixelDiferenciaMax)/(mlPixelDiferenciaMax + 2);
        ldAnchoValores = (ldMax - ldMin)/ldAnchoGrafico;
        lsDato= Double.toString(ldAnchoValores);

        int i=0;
        boolean lbComa=false;
        int li=0;
        for (;lsDato.length()>i;i++){
          if (!(lsDato.charAt(i) == '0' || lsDato.charAt(i) == '.' || lsDato.charAt(i) == ',')){
            if(lNumero==0){
              lNumero = Integer.valueOf(lsDato.substring(i,i+1)).intValue()+1;
              li=i;
              if(lbComa){
                  break;
              }
            }
          }else {
              if (lsDato.charAt(i) == '.' || lsDato.charAt(i) == ','){
                if (lNumero==0){
                  lbComa =true;
                }else {
                  li=i;
                  break;
                }
              }
          }
        }

        if (lsDato.length()<=li) {
            li=0;
        }

        ldNumero=lNumero;

        if (!lbComa){
          for(;li>1;li--){
            ldNumero *= 10;
          }
        }else{
          for(;li>1;li--){
            ldNumero /= 10;
          }
        }


      } catch(Exception e){
            e.printStackTrace();
            ldNumero=(ldAnchoValores/mlPixelDiferenciaMax);
      }
      return ldNumero;
  }
  /**
   * Convierte un double a double sin decimales
   * @param pdDouble Valor double
   * @return Valor double sin decimales
   */
  static double abs(double pdDouble ){
      return (pdDouble < 0 ?-pdDouble:pdDouble);
  }

}