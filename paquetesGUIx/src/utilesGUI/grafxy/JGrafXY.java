package utilesGUI.grafxy;

import java.awt.*;

/**
 * <p>Title: GrafXY</p>
 * <p>Description: Generador de gráficos XY</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Intecsa</p>
 * @author Eduardo Gonzalez
 * @version 1.0
 */

public class JGrafXY extends Canvas {

  //nombre de los controles por diseño
  private static final String base = "JGrafXY";
  private static int nameCounter = 0;
  /**
   * Título
   */
  public String msTitulo="";
  /**
   * Estilo texto del título
   */
  public JEstiloTexto moEstiloTitulo = new JEstiloTexto(new Font("Courier New", Font.BOLD, 14),Color.black);
  /**
   * Estilo texto de la leyenda
   */
  public JEstiloTexto moEstiloLeyendaSerie = new JEstiloTexto();
  /**
   * Color de la linea del cuadro de las series
   */
  public Color moColorLineaLeyenda = Color.gray;

  /**
   * eje Y1
   */
  public JEjeY moY1=new JEjeY(this,true);
  /**
   * eje Y2
   */
  public JEjeY moY2=new JEjeY(this,false);

  /**
   * Color eje x
   */
  public Color moColorEjeX = Color.black;
  /**
   * Estilo del texto del eje x
   */
  public JEstiloEje moEstiloX = new JEstiloEje();
  /**
   * Título del eje x
   */
  public String msTituloEjeX="";

  /**
   * Tipo de clase del ejeX
   */
  Class tipoX;

  /**
   * Valor máximo de todas las series de todos los ejes
   */
  Object mdMaxX=null;
  /**
   * Valor mímimo de todas las series de todos los ejes
   */
  Object mdMinX=null;

  /**
   * margen izq
   */
  public int mlBordeIzq=60;
  /**
   * margen der
   */
  public int mlBordeDer=60;
  /**
   * margen arriba
   */
  public int mlBordeArriba=20;
  /**
   * margen Abajo
   */
  public int mlBordeAbajo=40;
  /**
   * pintamos las lineas de los ejes
   */
  public boolean mbLineasDivisionX = false;

  /**
   * control de refresco
   */
  private boolean mbRefrescar = false;
  /**
   * Imagen para el buffer en donde pintar
   */
  private Image moImg=null;

  /**
   * Contructor
   */
  public JGrafXY() {
      this(Double.class,"");
  }
  /**
   * Constructor
   * @param pcEstiloX clase de los datos del Eje X
   */
  public JGrafXY(Class pcEstiloX) {
    this(pcEstiloX, "");
  }
  /**
   * Contructor
   * @param pcEstiloX  clase de los datos del Eje X 
   * @param psTitulo título 
   */
  public JGrafXY(Class pcEstiloX,String psTitulo) {
    tipoX=pcEstiloX;
    msTitulo=psTitulo;
    this.setBackground(Color.white);
    setName(base + nameCounter++);
 }
  /**
   * Establece el estilo del eje x
   * @param pcEstiloX clase del eje X
   */
  public void setEstiloX(Class pcEstiloX){
    tipoX=pcEstiloX;
  }
  /**
   * Borra todas las series de todos los ejes
   */
  public void borrarSeries(){
    moY1.borrarSeries();
    moY2.borrarSeries();
    mdMaxX=null;
    mdMinX=null;
  }
  /**
   * Repinta el gráfico
   */
  public void Refrescar(){
    mbRefrescar=true;
    repaint();
  }
  /**
   * Evento de cada vez que se pinta el componente, aqui se hace el proceso de pintar en la imagen
   * si es refrescar y luego pinta la imagen en el Objeto Gráfico
   * @param g2
   */
  public void paint(Graphics g2) {
    if (mbRefrescar){
      Rectangle r = bounds();

      //creamos una imagen para dibujar en ella, se le suma 10 para que el ult. punto no se pintaria a mitad
      moImg = createImage( r.width, r.height);
      Graphics g = moImg.getGraphics();


      /* el r.x y r.y de bounds son relativos al espacio del padre asi que los
         establecemos a 0
      */
      r.x = 0;  r.y = 0;
      r.x      = r.x + mlBordeIzq;
      r.y      = r.y + mlBordeArriba;
      r.width  = r.width  - (mlBordeIzq+mlBordeDer);
      r.height = r.height - (mlBordeAbajo+mlBordeArriba);

      ////////////////////////
      //pintamos el titulo
      ////////////////////////

      int lAlto =20+moEstiloTitulo.pintar(g,msTitulo,r);

      ////////////////////////
      //pintamos las leyendas
      ///////////////////////
      int lAltoOrigen=lAlto;
      //pintamos las leyendas
      lAlto+=moY1.pintarLeyenda(g,moEstiloLeyendaSerie,r.x,lAlto + r.y,100);
      lAlto+=moY2.pintarLeyenda(g,moEstiloLeyendaSerie,r.x,lAlto + r.y,100);

      //pintamos un rectangulo alrededor de la leyenda
      g.setColor(moColorLineaLeyenda);
      g.drawRoundRect(r.x-4,r.y+lAltoOrigen-15,
        (moY1.mlAnchoLeyenda>moY2.mlAnchoLeyenda) ? (moY1.mlAnchoLeyenda + 6) : (moY2.mlAnchoLeyenda + 6) ,
        lAlto+15-lAltoOrigen,10,10);
      g.drawRoundRect(r.x-4,r.y+lAltoOrigen-15,
        (moY1.mlAnchoLeyenda>moY2.mlAnchoLeyenda) ? (moY1.mlAnchoLeyenda + 7) : (moY2.mlAnchoLeyenda + 7) ,
        lAlto+14-lAltoOrigen,10,10);

      lAlto+=20;

      //pintamos los titulos de los ejes
      int lAltoTitEje1, lAltoTitEje2;

      lAltoTitEje1= moY1.pintarTitulo(g,new Rectangle(r.x,r.y+lAlto+3,r.width,r.height));
      lAltoTitEje2= moY2.pintarTitulo(g,new Rectangle(r.x,r.y+lAlto+3,r.width,r.height));

      lAlto += (lAltoTitEje1>lAltoTitEje2) ? lAltoTitEje1 : lAltoTitEje2;


      //establecemos la coord. y el alto restante despues de pintar la leyenda y los titulos
      r.y += lAlto;
      r.height -= lAlto;

      ////////////////////
      //pintamos los datos
      ////////////////////

      pintarEjes(g,r);
      pintarDatos(g,r);
    }
    if (moImg!=null){
      g2.drawImage(moImg, 0, 0, this);
      mbRefrescar=false;
    }
  }
  /**
   * Pinta los ejes
   * @param g
   * @param r
   */
  private void pintarEjes(Graphics g, Rectangle r){
    //pintamos ejes Y
    moY1.pintarEjes(g,r,new Rectangle(1,r.y,r.width+(mlBordeIzq+mlBordeDer)-2,r.height));
    moY2.pintarEjes(g,r,new Rectangle(1,r.y,r.width+(mlBordeIzq+mlBordeDer)-2,r.height));
    //pintamos eje X
    g.setColor(moColorEjeX);
    g.drawLine(r.x,r.height + r.y,r.x+r.width,r.height + r.y );

    pintarTextos(g, r);

    moEstiloX.moEstiloTextoCaption.pintar(g,msTituloEjeX,
          new Rectangle(r.x,r.y+r.height+30 ,r.width,r.height ),JEstiloTexto.mclAlinCent);
  }
  /**
   * Pinta los textos y lineas de los ejes y títulos
   * @param g
   * @param r
   */
  private void pintarTextos(Graphics g, Rectangle r){
    int lInicioY=r.height + r.y;
    int lRayaArr;
    int lxminima=r.x;
//    int lxmaxima=r.x+r.width;
    int lyEjeTexto;

    //
    //inicializacion de variables segun eje 1 o 2
    //

    if (true)  {
      lRayaArr=5;
      lyEjeTexto = lInicioY + 15;
    }else {
      lRayaArr=-5;
      lyEjeTexto = lInicioY - 15;
    }

    //
    //calculamos la x minima y las unidades graficas para la x
    //


    double ldxMin=convertidorDouble.mddouble(mdMinX);
    double ldxMax=convertidorDouble.mddouble(mdMaxX);
    double ldxDiferMaxMin = (ldxMax-ldxMin);
    double ldUnidadX = (r.width)/(ldxMax-ldxMin);
    int lxDiferMaxMin=lxminima+(new Double(ldxDiferMaxMin * ldUnidadX).intValue());
    double ldSeparValores;
    ldSeparValores=convertidorDouble.mdValorEntreRallas(g,moEstiloX.moEstiloTexto,mdMaxX,mdMinX,r.width,false);

    //
    //pintamos las rayas de los ejes
    //

    int lValorMax=(new Double((ldxMax-ldxMin)/ldSeparValores)).intValue();
    double ldValorActual = ldxMin;
    int i =0;
    //siempre pintamos la y minima y la y maxima, la y-1 de los valores normales se pinta
    //si hay una diferencia de mas de 20 px con la y max.
    for(;i<=lValorMax ;i++){
      int lx=lxminima+(new Double(ldSeparValores * i * ldUnidadX).intValue());
      if ((lxDiferMaxMin-lx)>convertidorDouble.mlPixelDiferenciaMax) {
        g.drawLine(lx,lInicioY,lx,lInicioY + lRayaArr);
        moEstiloX.moEstiloTexto.pintar(g,convertidorDouble.toString(new Double(ldValorActual),tipoX),lx-15,lyEjeTexto);
      }
      ldValorActual+=ldSeparValores;
    }
    //se pinta la y maxima
    g.drawLine(lxDiferMaxMin,lInicioY,lxDiferMaxMin,lInicioY + lRayaArr);
    moEstiloX.moEstiloTexto.pintar(g,convertidorDouble.toString(new Double(ldxMax),tipoX),lxDiferMaxMin-15,lyEjeTexto );
  }
  /**
   * Pinta las series introducidas en los ejes
   * @param g
   * @param r
   */
  private void pintarDatos(Graphics g, Rectangle r){
//    JSerie loSerie;
//    int lx;

    //x minima
    double ldxMin=convertidorDouble.mddouble(mdMinX);
    double ldxMax=convertidorDouble.mddouble(mdMaxX);


    //unidad grafica X
    double ldUnidadX;
    if ((ldxMax-ldxMin) == 0 ) {
        ldUnidadX = 0;
    }else {
        ldUnidadX = ((r.width)/(ldxMax-ldxMin));
    }

    //pintamos las series de cada eje
    moY1.pintar(g, r,ldxMin,ldUnidadX);
    moY2.pintar(g, r,ldxMin,ldUnidadX);
  }
}
