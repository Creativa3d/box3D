/*
 * JGrafDatosX.java
 *
 * Created on 19 de abril de 2004, 16:07
 */

package utilesGUI.grafxy2;

import java.awt.*;
import utilesGUI.grafxy2.estilos.*;
import utilesGUI.grafxy2.util.*;
/**Datos asociados al eje X*/
public class JGrafDatosEjeX {
    /**
    * eje Y1
    */
    private JGrafDatosEjeY moY1=new JGrafDatosEjeY(this,true,"");
    /**
    * eje Y2
    */
    private JGrafDatosEjeY moY2=new JGrafDatosEjeY(this,false,"");
    /**
    * Tipo de clase del ejeX
    */
    private Class tipoX;
    /**
    * Color de la linea del cuadro de las series
    */
    public Color moColorLineaLeyenda = Color.gray;

    /**
     * Creates a new instance of JGrafDatosEjeX
     * @param pcClaseX tipo de datos del eje X
     */
    public JGrafDatosEjeX(Class pcClaseX) {
        tipoX = pcClaseX;
    }
    /**
     * Devuelve el eje Y1
     * @return eje Y 1
     */
    public JGrafDatosEjeY getY1(){
        return moY1;
    }
    /**
     * Devuelve el eje Y2
     * @return eje y2
     */
    public JGrafDatosEjeY getY2(){
        return moY2;
    }
    /**
     * Establece el estilo del eje x
     * @param pcClaseX clase de los datos del eje X
     */
    public void setClaseX(Class pcClaseX){
        tipoX = pcClaseX;
    }
    /**
     * Establece la X mínima
     * @param pdValor valor
     */
    public void setXMin(Object pdValor){
        moY1.getEjeCoordenadas().setXMin(pdValor);
        moY2.getEjeCoordenadas().setXMin(pdValor);
    }
    /**
     * Establece la X máxima
     * @param pdValor valor
     */
    public void setXMax(Object pdValor){
        moY1.getEjeCoordenadas().setXMax(pdValor);
        moY2.getEjeCoordenadas().setXMax(pdValor);
    }
    /**
     * Devuelve la X mínima
     * @return valor
     */
    public Object getXMin(){
        return moY1.getEjeCoordenadas().getXMin();
    }
    /**
     * Devuelve la X máxima
     * @return valor
     */
    public Object getXMax(){
        return moY1.getEjeCoordenadas().getXMax();
    }
    /**
    * Borra todas las series de todos los ejes
    */
    public void borrarSeries(){
        moY1.borrarSeries();
        moY2.borrarSeries();
        setXMax(null);
        setXMin(null);
    }
    /**
    * Pinta las series introducidas en los ejes
    * @param g
    * @param r
    */
    public void pintarDatos(Graphics g, Rectangle r){
        //establecemos el area de pintado
        moY1.getEjeCoordenadas().setArea(r);
        moY2.getEjeCoordenadas().setArea(r);

        //solo es valido el eje de corr. X de la moY1
        moY1.getEjeCoordenadas().pintarEjeX(g, tipoX);
        moY1.getEjeCoordenadas().pintarTituloX(g);

        //pintamos las series de cada eje
        moY1.pintar(g);
        moY2.pintar(g);
    }
    
    public void pintarLeyenda(Graphics g, boolean pbLeyenda, JEstiloTexto poEstiloTexto, int x,int y, Rectangle rDevuelta, boolean pbLeyendaSerieVertical){
        
        if(pbLeyenda){
            Rectangle r2 = new Rectangle(0 ,0);
            //escribimos titulos de la leyenda
            moY1.pintarLeyenda(g, x+4, y+15, rDevuelta, pbLeyendaSerieVertical);
            moY2.pintarLeyenda(g, x+4, rDevuelta.height + y+15, r2, pbLeyendaSerieVertical);

            //rectangulo total de la leyenda escrita
            rDevuelta.height += r2.height;
            if(r2.width > rDevuelta.width) {
                rDevuelta.width = r2.width;
            }

            //pintamos un rectangulo alrededor de la leyenda
            g.setColor(moColorLineaLeyenda);
            g.drawRoundRect(x, y,
                rDevuelta.width + 6,
                rDevuelta.height + y-14, 10, 10);
            g.drawRoundRect(x, y,
                rDevuelta.width + 7,
                rDevuelta.height + y-15, 10, 10);
        }
        rDevuelta.height+=25;

        //pintamos los titulos de los ejes
        int lAltoTitEje1, lAltoTitEje2;

        lAltoTitEje1= moY1.getEjeCoordenadas().pintarTituloY(g,new Rectangle(x,y+rDevuelta.height+3,400,100));
        lAltoTitEje2= moY2.getEjeCoordenadas().pintarTituloY(g,new Rectangle(x,y+rDevuelta.height+3,400,100));

        rDevuelta.height += (lAltoTitEje1>lAltoTitEje2) ? lAltoTitEje1 : lAltoTitEje2;
    }
    /**
     * Establece el título del Eje
     * @param psTituloX título
     */
    public void setTituloX(String psTituloX){
        moY1.getEjeCoordenadas().msTituloX = psTituloX;
        moY2.getEjeCoordenadas().msTituloX = psTituloX;
    }

}