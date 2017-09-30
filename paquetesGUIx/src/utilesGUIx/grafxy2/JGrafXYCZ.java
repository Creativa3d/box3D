/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.grafxy2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import javax.swing.JPanel;
import utilesGUI.grafxy2.estilos.*;
import utilesGUI.grafxy2.*;

public class JGrafXYCZ extends JPanel    {
    //nombre de los controles por diseño
    private static final String base = "JGraf2";
    private static int nameCounter = 0;
    
    /**
    * Título
    */
    public String msTitulo="";
    /**
    * Estilo texto del título
    */
    public JEstiloTexto moEstiloTitulo = new JEstiloTexto(new Font("Dialog", Font.BOLD, 14),Color.black);
    /**
    * Estilo texto de la leyenda
    */
    public JEstiloTexto moEstiloLeyendaSerie = new JEstiloTexto(new Font("Dialog", Font.BOLD, 12),Color.black);
    /**
     * Las series de la leyenda se pintan en vertical/horizontal
     */
    public boolean mbLeyendaSerieVertical = true;
    /**
     * es la leyenda visible
     */
    public boolean mbLeyendaVisible = true;
    /**
    * Color de la linea del cuadro de las series
    */
    public Color moColorLineaLeyenda = Color.gray;
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

    /** control de refresco*/
    public boolean mbRefrescar = false;
    /**si tiene doble buffer para evitar parpadeos*/
    public boolean mbDobleBuffer = true;
    // Imagen para el buffer en donde pintar
    private Image moImg=null;
    //Eje X
    private JGrafDatosEjeX moDatosX1;
    private java.awt.Image moImage = null; 

    
    /**
    * Contructor
    */
    public JGrafXYCZ() {
        this(Double.class,"");
    }
    /**
     * Constructor
     * @param pcClaseX tipo de objeto del eje X
     */
    public JGrafXYCZ(Class pcClaseX) {
        this(pcClaseX, "");
    }
    /**
     * Contructor
     * @param pcClaseX clase de los valores del eje X
     * @param psTitulo Título
     */
    public JGrafXYCZ(Class pcClaseX, String psTitulo) {
        moDatosX1 = new JGrafDatosEjeX(pcClaseX);
        msTitulo=psTitulo;
        setOpaque(true);
        setBackground(Color.white);
        setName(base + nameCounter++);
    }
    /**
     * Establece la imagen de fondo
     * @param poImage Imagen
     */
    public void setImageBack(java.awt.Image poImage){
        moImage = poImage;
        try{
            MediaTracker loCargar = new MediaTracker(this);
            loCargar.addImage(moImage,1);
            loCargar.waitForAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    /**
    * Repinta el gráfico
    */
    public void Refrescar(){
        mbRefrescar=true;
        repaint();
    }
    
    /**
     * Devuelve el objeto Datos del eje X1
     * @return datos eje X
     */
    public JGrafDatosEjeX getDatosX1(){
        return moDatosX1;
    }


  /**
   * Evento de cada vez que se pinta el componente, aqui se hace el proceso de pintar en la imagen
   * si es refrescar y luego pinta la imagen en el Objeto Gráfico
   * @param g2
   */
  public void paint(Graphics g2) {
    if (mbRefrescar){
      Rectangle r = bounds();
      Graphics g;
      //creamos una imagen para dibujar en ella, se le suma 10 para que el ult. punto no se pintaria a mitad
      if(mbDobleBuffer){
          moImg = createImage( r.width, r.height);
          g = moImg.getGraphics();
      }else{
          g = g2;
      }
      
      //ponemos el fondo
      if(moImage!=null) {
          int lImageWidth = moImage.getWidth(this);
          int lImageHeight = moImage.getHeight(this);
          for(int i = 0 ; i < ((r.width / lImageWidth )+1); i++){
              for(int ii=0; ii< ((r.height / lImageHeight)+1); ii++){
                  g.drawImage(moImage, i*lImageWidth, ii*lImageHeight,this);
              }
          }
      }


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

      int lAlto =5+moEstiloTitulo.pintar(g,msTitulo,r);

      //establecemos la coord. y el alto restante despues de pintar la leyenda y los titulos
      r.y += lAlto;
      r.height -= lAlto;
      
      ////////////////////////
      //pintamos las leyendas
      ///////////////////////
      //pintamos las leyendas
      Rectangle rLeyen = new Rectangle(0,0);
      moDatosX1.pintarLeyenda(
              g,
              mbLeyendaVisible,
              moEstiloLeyendaSerie,
              r.x, r.y, 
              rLeyen, 
              mbLeyendaSerieVertical
              );

      //establecemos la coord. y el alto restante despues de pintar la leyenda y los titulos
      r.y += rLeyen.height;
      r.height -= rLeyen.height;

      ////////////////////
      //pintamos los datos
      ////////////////////

      moDatosX1.pintarDatos(g, r);
    }
    if(mbDobleBuffer){
        if (moImg!=null){
          g2.drawImage(moImg, 0, 0, this);
          mbRefrescar=false;
        }
    }
  }

}
