package utilesGUI.grafxy;

/**
 * <p>Title: BDAInternet</p>
 * <p>Description: Eje Y de gráfico XY</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa-inarsa</p>
 * @author Eduardo Gonzalez Carpena
 * @version 1.0
 */

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;

import utiles.*;

/**Eje Y*/
public class JEjeY {
  /**
   * Estilo del eje
   */
  public JEstiloEje moEstiloY = new JEstiloEje();
  /**
   * Título del eje
   */
  public String msTitulo="";
  /**
   * Color del eje
   */
  public Color moColorEje=Color.black;
  /**
   * Valor(double) nulo para este eje
   */
  public double mdValorNulo=Double.MAX_VALUE;

  /**
   * Gráfico padre(importante por los objetos de X máx e X mín)
   */
  private JGrafXY moGrafXY;
  /**
   * Lista de series
   */
  private IListaElementos moSeries=new JListaElementos();
  /**
   * Indica si es el eje y1 (de la izq.) o no
   */
  private boolean mbesY1;
  /**
   * Espacio entre y=0 y lo primero que se pinta(para que no salga a mitad)
   */
  private final int mclEspacioSeguridad=10;
  /**
   * Valor máx. de la y de todas las series
   * Valor mín. de la y de todas las series
   */
  Double mdMaxY=null;
  Double mdMinY=null;

  /**
   * Despues de pintar la leyenda contien el ancho maximo
   */
  int mlAnchoLeyenda=0;
  /**
   * pintamos las lineas de los ejes
   */
  public boolean mbLineasDivisionY = false;

  /**
   * Constructor
   * @param poGrafxy Gráfico base
   * @param pbesY1 Indica si es el eje1 o eje2
   */
  public JEjeY(JGrafXY poGrafxy,boolean pbesY1){
    mbesY1=pbesY1;
    moGrafXY=poGrafxy;
  }
  /**
   * Constructor
   * @param poGrafxy Gráfico base
   * @param psTitulo Título del eje
   */
  public JEjeY(JGrafXY poGrafxy,String psTitulo) {
    moGrafXY=poGrafxy;
    msTitulo=psTitulo;
  }
  /**
   * Añade una serie al eje y
   * @return La serie creada
   */
  public JSerie addSerie(){
    JSerie loSerie = new JSerie(moGrafXY,this);
    moSeries.add(loSerie);
    return loSerie;
  }
  /**
   * Devuelve una serie del Eje
   * @param i Indice de la serie
   * @return Serie correspondiente con el indice
   */
  public JSerie getSerie(int i){
    return (JSerie)moSeries.get(i);
  }
  /**
   * Borra todas las series del eje Y
   */
  public void borrarSeries(){
    moSeries.clear();
    mdMaxY=null;
    mdMinY=null;
  }
  /**
   * Borra un serie concreta
   * @param i Indice de la serie a borrar
   */
  public void borrarSerie(int i){
    moSeries.remove(i);
    if (moSeries.size()==0){
      mdMaxY=null;
      mdMinY=null;
    }
  }

  /**
   * Borra un serie concreta
   * @param poObject serie
   */
  public void borrarSerie(Object poObject){
    moSeries.remove(poObject);
    if (moSeries.size()==0){
      mdMaxY=null;
      mdMinY=null;
    }
  }

  /**
   * pinta los ejes
   * @param g Gráfico en donde pintar
   * @param r Rectangulo en donde pintar
   * @param rReal Rectangulo real
   */
  void pintarEjes(Graphics g, Rectangle r,Rectangle rReal){
    //si hay series
    if (moSeries.size()>0){
      int lInicioX=0;
      int lRayaIzq;
      int lRayaDer;
      int lyminima=r.height + r.y;
      int lymaxima=r.y;
      int lxEjeTexto;

      //
      //inicializacion de variables segun eje 1 o 2
      //

      if (mbesY1)  {
        lInicioX=r.x;
        lRayaIzq=5;
        lRayaDer=0;
        lxEjeTexto = rReal.x;
      }else {
        lInicioX=r.x + r.width;
        lRayaDer=5;
        lRayaIzq=0;
        lxEjeTexto = lInicioX + 5;
      }

      //
      //pintamos el eje
      //

      //establecemos el color del eje
      g.setColor(moColorEje);

      //pintamos la linea del eje
      g.drawLine(lInicioX,lyminima,lInicioX,lymaxima);

      //
      //calculamos la y minima y las unidades graficas para la y
      //

      double ldyMin=convertidorDouble.mddouble(mdMinY);
      double ldyMax=convertidorDouble.mddouble(mdMaxY);
      double ldyDiferMaxMin = (ldyMax-ldyMin);
      double ldUnidadY = (r.height-mclEspacioSeguridad)/(ldyMax-ldyMin);
      int lyDiferMaxMin=lyminima-(new Double(ldyDiferMaxMin * ldUnidadY).intValue());
      double ldSeparValores;
      ldSeparValores=convertidorDouble.mdValorEntreRallas(g,moEstiloY.moEstiloTexto,mdMaxY,mdMinY,r.height,true);

      //
      //pintamos las rayas de los ejes
      //

      int lValorMax=(new Double((ldyMax-ldyMin)/ldSeparValores)).intValue();
      double ldValorActual = ldyMin;
      int i =0;
      //siempre pintamos la y minima y la y maxima, la y-1 de los valores normales se pinta
      //si hay una diferencia de mas de 20 px con la y max.
      for(;i<=lValorMax ;i++){
        int ly=lyminima-(new Double(ldSeparValores * i * ldUnidadY).intValue());
        if ((ly-lyDiferMaxMin)>convertidorDouble.mlPixelDiferenciaMax) {
          g.drawLine(lInicioX-lRayaIzq,ly,lInicioX + lRayaDer,ly);
          moEstiloY.moEstiloTexto.pintar(g,convertidorDouble.toString(new Double(ldValorActual),Double.class),lxEjeTexto,ly );
        }
        ldValorActual+=ldSeparValores;
      }
      //se pinta la y maxima
      g.drawLine(lInicioX-lRayaIzq,lyDiferMaxMin,lInicioX + lRayaDer,lyDiferMaxMin);
      moEstiloY.moEstiloTexto.pintar(g,convertidorDouble.toString(new Double(ldyMax),Double.class),lxEjeTexto,lyDiferMaxMin );
    }
  }

  /**
   * pinta los datos (series)
   * @param gi Gráfico en donde pintar
   * @param poRectangulo Rectangulo en donde pintar
   * @param pdxMin Valor x mínimo
   * @param pdUnidadX Unidades entre los valores X
   */
  void pintar(Graphics gi, Rectangle poRectangulo,double pdxMin,double pdUnidadX){
    //si hay alguna serie pintamos las series
    if (moSeries.size()>0){

      //calculamos la y minima y las unidades graficas para la y
      double ldyMin=convertidorDouble.mddouble(mdMinY);
      double ldyMax=convertidorDouble.mddouble(mdMaxY);
      double ldUnidadY;

      if ((ldyMax-ldyMin) ==0) {
          ldUnidadY = 0;
      } else {
          ldUnidadY = (poRectangulo.height-mclEspacioSeguridad)/(ldyMax-ldyMin);
      }

      //dibujamos en la imagen todas las series
      Iterator enum1 = moSeries.iterator();
      for (JSerie loSerie; enum1.hasNext() ;) {
          loSerie=(JSerie)enum1.next();
          loSerie.pintar(gi,poRectangulo,pdUnidadX,ldUnidadY,pdxMin,ldyMin);
      }
    }
  }
  int pintarLeyenda(Graphics gi, JEstiloTexto poEstiloTexto, int x,int y,int plAncho){
    int lAlto=0;
    mlAnchoLeyenda=0;
    //si hay alguna serie pintamos las series
    if (moSeries.size()>0){
      int lAncho;
      //dibujamos en la leyenda todas las series
      Iterator enum1 = moSeries.iterator();
      for (JSerie loSerie; enum1.hasNext() ;) {
          loSerie=(JSerie)enum1.next();

          loSerie.moEstilo.pintarMuestra(gi,x,y+lAlto);
          lAncho=poEstiloTexto.pintar(gi,loSerie.msCaption,
                    x+loSerie.moEstilo.mclAnchoMuestra+5,y+lAlto+5);

          if (lAncho>mlAnchoLeyenda) {
              mlAnchoLeyenda=lAncho;
          }
          lAlto =lAlto + poEstiloTexto.mlAlto(gi)+2;
      }
    }
    mlAnchoLeyenda += (JEstiloSerie.mclAnchoMuestra +10);
    return lAlto;
  }
  /**
   * Pintamos el título del eje
   * @param gi Gráfico en donde pintar
   * @param r Rectangulo en donde pintar
   * @return Alto total de lo pintado
   */
  int pintarTitulo(Graphics gi, Rectangle r){
    //pintamos el titulo segun el eje
    if (mbesY1) {
        moEstiloY.moEstiloTextoCaption.pintar(gi,msTitulo,r,JEstiloTexto.mclAlinIzq);
    }else {
        moEstiloY.moEstiloTextoCaption.pintar(gi,msTitulo,r,JEstiloTexto.mclAlinDer);
    }
    //devolvemos el alto
    return moEstiloY.moEstiloTextoCaption.mlAlto(gi)+2;
  };
}