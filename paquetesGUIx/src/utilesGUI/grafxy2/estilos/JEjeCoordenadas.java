/*
 * JEjeCoordenadas.java
 *
 * Created on 21 de abril de 2004, 8:27
 */

package utilesGUI.grafxy2.estilos;

import java.awt.*;

import utilesGUI.grafxy2.util.*;
import utiles.*;

/**Objeto eje de coordeandas*/
public class JEjeCoordenadas {
    
    /**
     * rectangulo del area de datos
     */
    public Rectangle moAreaDatos=null;
    
    //valores minimos y maximos de los datos 
    private Object mdXMin=null;
    private Object mdXMax=null;
    private Object mdYMin=null;
    private Object mdYMax=null;
    
    private boolean mbYMinFijo=false;
    private boolean mbYMaxFijo=false;
    
    //pixeles por unidad
    private double mdUnidadX=0.0;
    private double mdUnidadY=0.0;

    //valores de separacion entre rayas de los ejes
    private double mdValorSeparaEjeX;
    private boolean mbValorSeparaEjeXManual=false;
    private double mdValorSeparaEjeY;
    private boolean mbValorSeparaEjeYManual=false;
    
    //Estilos de los ejes
    private JEstiloEje moEstiloY = new JEstiloEje();
    private JEstiloEje moEstiloX = new JEstiloEje();

    //Margen para que los datos en el area de dibujado no llegen a los limites
    private int mclMargenDatosX = 10;
    private int mclMargenDatosY = 3;
    
    //Indica si es el eje y1 (de la izq.) o no
    private boolean mbesY1 = true;

    /**
    * Título del eje x
    */
    public String msTituloX="";
    /**
    * Título del eje Y
    */
    public String msTituloY="";
    
    private Graphics moGraphics = null;
    
    
    /**
     * Creates a new instance of JEjeCoordenadas
     * @param pbEsY1 si es y1 si no y2
     */
    public JEjeCoordenadas(boolean pbEsY1) {
        mbesY1 = pbEsY1;
    }
    
    /**
     * Estilo del ejeY
     * @return estilo
     */
    public JEstiloEje getEstiloY(){
        return moEstiloY;
    }
    /**
     * Establecemos el objeto en donde pintar
     * @param poGrap objeto gráfico
     */
    public void setGraphics(Graphics poGrap){
        moGraphics = poGrap;
    }
    /**
     * Estilo del ejeX
     * @return estilo
     */
    public JEstiloEje getEstiloX(){
        return moEstiloX;
    }
    
    /**
     * valor de separación manual entre valores del eje X
     * @param pdValor valor
     */
    public void setValorSeparaEjeX(double pdValor){
        mdValorSeparaEjeX = pdValor;
        mbValorSeparaEjeXManual = true;
    }
    /**
     * valor de separación manual entre valores del eje Y
     * @param pdValor valor
     */
    public void setValorSeparaEjeY(double pdValor){
        mdValorSeparaEjeY = pdValor;
        mbValorSeparaEjeYManual = true;
    }
    /**
     * Establece min de X
     * @param pdValor valor
     */
    public void setXMin(Object pdValor){
        mdXMin = pdValor;
    }
    /**
     * Establece max de X
     * @param pdValor valor
     */
    public void setXMax(Object pdValor){
        mdXMax = pdValor;
    }
    /**
     * Establece min de Y
     * @param pdValor valor
     */
    public void setYMin(Object pdValor){
        mdYMin = pdValor;
        mbYMinFijo=false;
    }
    public void setYMinFijo(Object pdValor){
        mdYMin = pdValor;
        mbYMinFijo=true;
    }
    /**
     * Establece max de Y
     * @param pdValor valor
     */
    public void setYMax(Object pdValor){
        mdYMax = pdValor;
        mbYMaxFijo=false;
    }
    public void setYMaxFijo(Object pdValor){
        mdYMax = pdValor;
        mbYMaxFijo=true;
    }

    private void calcularUnidadX(){
        if((mdXMax!=null)&&(mdXMin!=null)&&(moAreaDatos!=null)){
            double ldXMax = convertidorDouble.mddouble(mdXMax);
            double ldXMin = convertidorDouble.mddouble(mdXMin);
            if ((ldXMax-ldXMin) == 0 ) {
                mdUnidadX = 0;
            } else {
                mdUnidadX = ((moAreaDatos.width-mclMargenDatosX)/(ldXMax-ldXMin));
            }
        }else{
            mdUnidadX = 0;
        }
    }

    private void calcularUnidadY(){
        if((mdYMax!=null)&&(mdYMin!=null)&&(moAreaDatos!=null)){
            double ldYMax = convertidorDouble.mddouble(mdYMax);
            double ldYMin = convertidorDouble.mddouble(mdYMin);
            
            if(!mbValorSeparaEjeYManual){
                mdValorSeparaEjeY = mdValorEntreRallas(mdYMax, mdYMin, moAreaDatos.height, true);
                if(mdValorSeparaEjeY < 0.00001){
                    mdValorSeparaEjeY = 0.5;
                }
            }
 
            double ldValorMax = ldYMin-1;
            double ldValorMin = ldYMin-1;
            if(mdValorSeparaEjeY > 0.0){
                if(!mbYMinFijo){
                    if(ldYMin<0){
                        ldValorMin=ldValorMin+2;
                        for(int i = 0; ldValorMin > ldYMin; i++){
                            ldValorMin = -mdValorSeparaEjeY * (double)i;
                        }
                       
                    }else{
                        for(int i = 0; ldValorMin < ldYMin; i++){
                            ldValorMin = mdValorSeparaEjeY * (double)i;
                        }
                        if(ldValorMin !=ldYMin){
                            ldValorMin -= mdValorSeparaEjeY;
                        }
                    }
                }else{
                    ldValorMin = convertidorDouble.mddouble(mdYMin);
                }
                if(!mbYMaxFijo){
                    if(ldYMax<0){
                        ldValorMax=0;
                        for(int i = 0; ldValorMax>ldYMax; i++){
                            ldValorMax = -mdValorSeparaEjeY * (double)i;
                        }
                        if(ldValorMax !=ldYMin){
                            ldValorMax += mdValorSeparaEjeY;
                        }
                    }else{
                        for(int i = 0; ldValorMax<ldYMax; i++){
                            ldValorMax = mdValorSeparaEjeY * (double)i-(ldValorMin>=0?0:ldValorMin);
                        }
                    }
                }else{
                    ldValorMax = convertidorDouble.mddouble(mdYMax);
                }
            }else{
                ldValorMax = convertidorDouble.mddouble(mdYMax);
                ldValorMin = convertidorDouble.mddouble(mdYMin);
            }
            mdYMax = new Double(ldValorMax);
            mdYMin = new Double(ldValorMin);
            if ((ldValorMax-ldValorMin) == 0.0 ) {
                mdUnidadY = 0;
            }else {
                mdUnidadY = ((moAreaDatos.height-mclMargenDatosY)/(ldValorMax-ldValorMin));
            }
        }else
            mdUnidadY = 0;
    }
    
    /**
     * El valor mínimo X
     * @return valor
     */
    public Object getXMin(){
        return mdXMin;
    }
    /**
     * El valor mínimo Y
     * @return valor
     */
    public Object getYMin(){
        return mdYMin;
    }
    /**
     * El valor máximo X
     * @return valor
     */
    public Object getXMax(){
        return mdXMax;
    }
    /**
     * El valor máximo Y
     * @return valor
     */
    public Object getYMax(){
        return mdYMax;
    }
    

    /**
     * Devuelve el punto que corresponde con el grafico segun el valor x, y
     * @return Punto
     * @param pdValorX valor x
     * @param pdValorY valor y
     */
    public Point getPunto(double pdValorX, double pdValorY){
        double ldYMin = convertidorDouble.mddouble(mdYMin);
        double ldXMin = convertidorDouble.mddouble(mdXMin);
        
        Point loP = new Point();
        loP.x = +(int)((-ldXMin+pdValorX)*mdUnidadX) + moAreaDatos.x;
        loP.y = -(int)((-ldYMin+pdValorY)*mdUnidadY) + moAreaDatos.y + moAreaDatos.height;
        return loP;
    }
    /**
     * Establece el área de los datos
     * @param r área
     */
    public void setArea(Rectangle r){
        moAreaDatos = r;
        calcularUnidadX();
    }
    /**
     * Calculo estadisticos segun series y valores actuales
     * @param poSeries lista de series
     */
    public void calculoEstadistico(IListaElementos poSeries){
        calcularUnidadX();
        calcularUnidadY();
    }
    
    /**
    * Pinta ejeX
    * @param g En donde pintar
    * @param poTipo tipo de datos del eje x
    */
    public void pintarEjeX(Graphics g, Class poTipo){
        if ((mdXMin!=null)&&(mdXMax!=null)){
            
            if(!mbValorSeparaEjeXManual){
              mdValorSeparaEjeX = mdValorEntreRallas(mdXMax, mdXMin, moAreaDatos.width, false);
            }
            
            pintarEje(g, false, mdValorSeparaEjeX, mdXMin, mdXMax, moEstiloX, poTipo);
        }
    }
    /**
     * Pinta ejeY
     * @param g Objeto en donde pintar
     */
    public void pintarEjeY(Graphics g){
        if ((mdYMin!=null)&&(mdYMax!=null)){
            
            if(!mbValorSeparaEjeYManual){
              mdValorSeparaEjeY = mdValorEntreRallas(mdYMax, mdYMin, moAreaDatos.height, true);
            }
            
            pintarEje(g, true, mdValorSeparaEjeY, mdYMin, mdYMax, moEstiloY, Double.class);
        }
    }
    /**
     * Pinta el título en el objeto gráfico
     * @param g objeto gráfico
     */
    public void pintarTituloX(Graphics g){
        Rectangle r = moAreaDatos;
        moEstiloX.moEstiloTextoCaption.pintar(g,msTituloX,
              new Rectangle(r.x,r.y+r.height+30 ,r.width,r.height ),JEstiloTexto.mclAlinCent);
    }
    /**
     * Pintamos el título del eje
     * @return Alto total de lo pintado
     * @param g objeto gráfico
     * @param r Rectangulo en donde pintar
     */
    public int pintarTituloY(Graphics g, Rectangle r){
        //pintamos el titulo segun el eje
        if (mbesY1) {
            moEstiloY.moEstiloTextoCaption.pintar(g,msTituloY,r,JEstiloTexto.mclAlinIzq);
        }else {
            moEstiloY.moEstiloTextoCaption.pintar(g,msTituloY,r,JEstiloTexto.mclAlinDer);
        }

        //devolvemos el alto
        return moEstiloY.moEstiloTextoCaption.getAlto(g)+2;
    }
  /**
   * Devuelve la separacion devalores entre rayas
   * @param poMax Valor máximo
   * @param poMin Valor mínimo
   * @param plAnchoTotal Alto del gráfico
   * @param pbEsY indica si pintamos un eje Y
   * @return Valor de separación entre rayas
   */
    private double mdValorEntreRallas(Object poMax, Object poMin, int plAnchoTotal, boolean pbEsY){
        double ldMax;
        double ldMin;
        double ldDif;
        int lAnchoGrafico;
        double ldAnchoValores=1;
        double ldRango=0.0;
        int lPixelDiferenciaMax=40;
        if ((!pbEsY)&&(poMax.getClass()==utiles.JDateEdu.class)) {
            JDateEdu loMin = (JDateEdu)poMin;
            loMin.setHora(0);
            loMin.setMinuto(0);
            loMin.setSegundo(0);
            ldMax = convertidorDouble.mddouble(poMax);
            ldMin = convertidorDouble.mddouble(poMin);
            ldDif = ldMax - ldMin;
            
            if(plAnchoTotal < 100)
                ldRango = (int)(ldDif / 2);
            else if(plAnchoTotal < 200)
                ldRango = (int)(ldDif / 3);
            else if(plAnchoTotal < 400)
                    ldRango = (int)(ldDif / 4);
            else if(plAnchoTotal < 600)
                    ldRango = (int)(ldDif / 6);
            else if(plAnchoTotal < 800)
                    ldRango = (int)(ldDif / 8);
            else 
                ldRango = (int)(ldDif / 12);
            
            
            return ldRango;
        }else{

            ldMax = convertidorDouble.mddouble(poMax);
            ldMin = convertidorDouble.mddouble(poMin);
            ldDif = ldMax - ldMin;


            lAnchoGrafico = (plAnchoTotal)/(lPixelDiferenciaMax);

            if(lAnchoGrafico>0){
                ldAnchoValores = java.lang.Math.abs(ldDif / lAnchoGrafico);
            }else{
                ldAnchoValores = java.lang.Math.abs(ldDif);
            }

            if(ldDif>0.0){
                if(ldAnchoValores > 1){
                    ldRango = 5.0;
                    ldAnchoValores/=10;
                    for(;ldAnchoValores > 1;){
                        ldRango*=10;
                        ldAnchoValores/=10;
                    }
                    if(ldRango>ldMax){
                        ldRango/=5.0;
                    }
                }else{
                    ldRango = 5.0;
                    ldAnchoValores*=10;
                    for(;ldAnchoValores < 1;){
                        ldRango/=10;
                        ldAnchoValores*=10;
                    }
                }
            }
            return ldRango;
        }
    }
    /**
     * Pinta ejeY
     * @param pbEsY si es eje Y sino eje X
     * @param pdValorSeparaEje valor de sepración de los valores del eje
     * @param pdValorMin Valor mínimo de los valores del eje
     * @param pdValorMax valor máximo de los valores del eje
     * @param poEstilo estilo del Eje
     * @param poTipo tipo de los datos del eje
     * @param g objeto gráfico en donde pintar
     */
    public void pintarEje(Graphics g, boolean pbEsY, double pdValorSeparaEje, Object pdValorMin, Object pdValorMax, JEstiloEje poEstilo, Class poTipo){
//        Rectangle r = moAreaDatos;

        //si hay series
        int lxMinima;
        int lxMaxima;
        int lRayaIzq;
        int lRayaDer;
        int lRayaArr;
        int lRayaAba;
        int lyMinima;
        int lyMaxima;
        int lxCorreccionTexto;
        int lyCorreccionTexto;

        //
        //inicializacion de variables segun eje 1 o 2
        //

        if(pbEsY){
            lyMinima=moAreaDatos.height + moAreaDatos.y;
            lyMaxima=moAreaDatos.y;
            if (mbesY1){
                lxMinima=moAreaDatos.x;
                lxMaxima=moAreaDatos.x;
                lRayaArr=0;
                lRayaAba=0;
                lRayaIzq=5;
                if(moEstiloY.mbLineasDivision){
                    lRayaDer=moAreaDatos.width;
                }else{
                    lRayaDer=0;
                }
                lxCorreccionTexto=-lxMinima;
                lyCorreccionTexto=0;
            }else{
                lxMinima=moAreaDatos.x + moAreaDatos.width;
                lxMaxima=moAreaDatos.x + moAreaDatos.width;
                lRayaArr=0;
                lRayaAba=0;
                lRayaDer=5;
                lRayaIzq=0;
                if(moEstiloY.mbLineasDivision){
                    lRayaIzq=moAreaDatos.width;
                }else{
                    lRayaIzq=0;
                }
                lxCorreccionTexto=5;
                lyCorreccionTexto=0;
            }
        }else{
            if(moEstiloX.mbLineasDivision){
                lRayaArr=moAreaDatos.height;
            }else{
                lRayaArr=0;
            }
            lRayaAba=5;
            lRayaIzq=0;
            lRayaDer=0;
            lxMinima=moAreaDatos.x;
            lxMaxima=moAreaDatos.x + moAreaDatos.width;
            lyMinima=moAreaDatos.height + moAreaDatos.y;
            lyMaxima=lyMinima;
            lxCorreccionTexto=-5;
            lyCorreccionTexto=15;
        }

        //
        //pintamos el eje
        //

        g.setColor(poEstilo.moColorEje);
        g.drawLine(lxMinima,lyMinima,lxMaxima,lyMaxima);

        //
        //calculamos la y minima y las unidades graficas para la y
        //pintamos las rayas de los ejes
        //
        double ldMin=convertidorDouble.mddouble(pdValorMin);
        double ldMax=convertidorDouble.mddouble(pdValorMax);

        //siempre pintamos la y minima, la y-1 de los valores normales se pinta
        //si hay una diferencia de mas de 20 px con la y max.
        double ldValorActual = ldMin;
        int lValorMax=(new Double((ldMax-ldMin)/pdValorSeparaEje)).intValue()+2;

        int ly=0;
        int lx=0;
        for(int i =1;
            ((((lyMinima-ly)>=lyMaxima)&&(pbEsY))||
             (((lxMinima+lx)<=lxMaxima)&&(!pbEsY)))&&
            (i<=lValorMax) 
            ;i++){

            //dibujamos la rayita
            g.drawLine(lxMinima - lRayaIzq + lx, lyMinima -ly + lRayaAba, 
                       lxMinima + lRayaDer + lx, lyMinima -ly - lRayaArr);

            //dibujamos el texto
            poEstilo.moEstiloTexto.pintar(g,
                convertidorDouble.toString(new Double(ldValorActual), poTipo), 
                lxMinima + lxCorreccionTexto + lx, lyMinima + lyCorreccionTexto - ly);

            //incrementamos el valor actual
            ldValorActual+=pdValorSeparaEje;

            //incrementamos las coordenadas segun el eje
            if(pbEsY){
                ly=(new Double(pdValorSeparaEje * i * mdUnidadY).intValue());
            }else{
                lx=(new Double(pdValorSeparaEje * i * mdUnidadX).intValue());
            }
        }
    }
    /** 
     ************************************************************
     * ATENCION LOS ESTILOS DEBEN USAR ESTOS METODOS PARA DIBUJAR
     ************************************************************
     */
    /**
     * Pinta una linea entre los valores dados u con el grosor dado
     * @param poValor1X valor x 1
     * @param poValor1Y valor y 1
     * @param poValor2X valor x 2
     * @param poValor2Y valor y 2
     * @param plGrosor grosor de la línea
     */
    public void drawLineLinea(Object poValor1X, Object poValor1Y, Object poValor2X, Object poValor2Y, int plGrosor){
        Point loValor1 = getPunto(convertidorDouble.mddouble(poValor1X),convertidorDouble.mddouble(poValor1Y));
        Point loValor2 = getPunto(convertidorDouble.mddouble(poValor2X),convertidorDouble.mddouble(poValor2Y));
        
        for(int i = 0; i<plGrosor;i++){
          moGraphics.drawLine(loValor1.x+i,loValor1.y+i,loValor2.x+i,loValor2.y+i);
        }
    }
    /**
     * Pinta una ovalo relleno en el valor dado
     * @param poValor1X valor x 1
     * @param poValor1Y valor y 1
     * @param plAncho ancho
     * @param plAlto alto
     */
    public void fillOvalPunto(Object poValor1X, Object poValor1Y, int plAncho, int plAlto){
        Point loValor1 = getPunto(convertidorDouble.mddouble(poValor1X),convertidorDouble.mddouble(poValor1Y));
        moGraphics.fillOval(loValor1.x - (plAncho/2), loValor1.y - (plAlto/2), plAncho, plAlto);
    }
    /**
     * Pinta una ovalo hueco en el valor dado
     * @param poValor1X valor x 1
     * @param poValor1Y valor y 1
     * @param plAncho ancho
     * @param plAlto alto
     */
    public void drawOvalPunto(Object poValor1X, Object poValor1Y, int plAncho, int plAlto){
        Point loValor1 = getPunto(convertidorDouble.mddouble(poValor1X),convertidorDouble.mddouble(poValor1Y));
        moGraphics.drawOval(loValor1.x - (plAncho/2), loValor1.y - (plAlto/2), plAncho, plAlto);
    }
    /**
     * Pinta una rectangulo relleno en el valor dado
     * @param poValor1X valor x 1
     * @param poValor1Y valor y 1
     * @param plAncho ancho
     * @param plAlto alto
     */
    public void fillRectPunto(Object poValor1X, Object poValor1Y, int plAncho, int plAlto){
        Point loValor1 = getPunto(convertidorDouble.mddouble(poValor1X),convertidorDouble.mddouble(poValor1Y));
        moGraphics.fillRect(loValor1.x - (plAncho/2), loValor1.y - (plAlto/2), plAncho, plAlto);
    }
    /**
     * Pinta una rectangulo hueco en el valor dado
     * @param poValor1X valor x 1
     * @param poValor1Y valor y 1
     * @param plAncho ancho
     * @param plAlto alto
     */
    public void drawRectPunto(Object poValor1X, Object poValor1Y, int plAncho, int plAlto){
        Point loValor1 = getPunto(convertidorDouble.mddouble(poValor1X),convertidorDouble.mddouble(poValor1Y));
        moGraphics.drawRect(loValor1.x - (plAncho/2), loValor1.y - (plAlto/2), plAncho, plAlto);
    }
    /**
     * Pinta una rectangulo relleno en el valor dado desde origen x hasta el valor dado
     * @param poValor1X valor x 1
     * @param poValor1Y valor y 1
     * @param plAncho ancho
     * @param plDesplazamiento desplazamiento con respecto al eje X
     */
    public void fillRectBarra(Object poValor1X, Object poValor1Y, int plDesplazamiento, int plAncho){
        Point loP = getPunto(convertidorDouble.mddouble(poValor1X),convertidorDouble.mddouble(poValor1Y));
        moGraphics.fillRect(loP.x + plDesplazamiento, loP.y, plAncho, (moAreaDatos.y + moAreaDatos.height + 1)-loP.y);
    }
    /**
     * Establece el color del Graphics
     * @param poColor color
     */
    public void setColor(Color poColor){
        moGraphics.setColor(poColor);
    }
}
