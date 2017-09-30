package utilesGUI;

import java.awt.*;

/**Componente barra de proceso*/
public class JBarraProceso extends Component {
  //nombre de los controles por diseño
  private static final String base = "BarraProceso";
  private static int nameCounter = 0;

  private double mdPorcentaje;
//  private double mdIncremento;
  private String msTexto="";
  private boolean mbPintarBorde = true;

  private Color moColorFondo = Color.blue;

  /**Constructor*/
  public JBarraProceso() {
    super();
    setPorcentaje(0);
    setTexto("");
    setName(base + nameCounter++);
  }
  /**
   * Color de fondo
   * @return color
   */
  public Color getColorFondo(){
      return moColorFondo;
  }
  /**
   * Porcentaje actual
   * @return porcentaje
   */
  public double getPorcentaje(){
      return mdPorcentaje;
  }
  /**
   * Texto actual
   * @return texto
   */
  public String getTexto(){
      return msTexto;
  }
  /**
   * Si pinta el borde
   * @return si lo pinta
   */
  public boolean getPintarBorde(){
      return mbPintarBorde;
  }
  /**
   * Establece si pinta el borde
   * @param pbPintarBorde si se pinta o no
   */
  public void setPintarBorde(boolean pbPintarBorde){
      mbPintarBorde=pbPintarBorde;
  }
  /**
   * Establece el color de fondo
   * @param poColorFondo color 
   */
  public void setColorFondo(Color poColorFondo){
    moColorFondo=poColorFondo;
    repaint();
  }
  /**
   * Establece el porcentaje actual
   * @param pdPorcentaje porcentaje
   */
  public void setPorcentaje(double pdPorcentaje){
    mdPorcentaje=pdPorcentaje;
    repaint();
  }
  /**
   * Establece el texto actual
   * @param psTexto texto
   */
  public void setTexto(String psTexto){
    msTexto = psTexto;
    repaint();
  }
  /**
   * Devuelve el tamaño preferido
   */
  public Dimension getPreferredSize() {
       return new Dimension(100, 50);
  }

  /**
   * Devuelve el mínimo tamaño
   */
  public Dimension getMinimumSize() {
      return new Dimension(100, 50);
  }
  /**Repinta el componente*/
  public void update( Graphics gc )
  {
      paint( gc );
  }
  /**Repinta el componente*/
  public void paint( Graphics g )
  {
    try{
    Rectangle r = getBounds();
    int lAncho = g.getFontMetrics(g.getFont()).stringWidth(msTexto);

    if(getBackground()!=null){
        g.setColor(getBackground());
        g.fill3DRect(0, 0, r.width, r.height, true);
        //g.draw3DRect(0, 0, r.width, r.height, true);
    }
        
    g.setColor( moColorFondo);
    g.fill3DRect(0, 1,(int)(r.width*mdPorcentaje)/100, r.height-2,true );

    g.setColor( getForeground() );
    g.setFont( getFont() );
    if (msTexto==null){
      g.drawString(String.valueOf((int)mdPorcentaje) + "%" , (r.width/2)-10 , (r.height/2) );
    }else{
      g.drawString(msTexto , (r.width/2)-(lAncho/2) , (r.height/2) );
    }
    if((getForeground()!=null)&&mbPintarBorde){
        g.setColor(getForeground());
        g.draw3DRect(0, 0, r.width, r.height, true);
    }
    }catch(Exception e){}
  }
}
//  public JBarraProceso(double pdSegundos,double pdIncremento) {
//      ponerAutomatico(pdSegundos,pdIncremento);
//  }
//  class RemindTask extends TimerTask {
//        public void run() {
//          setPorcentaje((int)((mdPorcentaje+mdIncremento) % 100) );
//        }
//  }
//  public void ponerAutomatico(double plSegundos,double pdIncremento ){
//    mdIncremento=pdIncremento;
//    moTimer=new Timer();
//    moTimer.schedule(new RemindTask(),0, (int)((plSegundos * 1000) + 1));
//  }
//  public void parar(){
//    moTimer.cancel();
//    moTimer=null;
//  }
//  private Color moColorLetra = Color.white;
//  private Font	moFuenteLetra = new Font( "Courier New", Font.PLAIN, 12 );
//  public Color getColorLetra(){return moColorLetra;}
//  public Font  getFuenteLetra(){return moFuenteLetra;}
//  public void setColorLetra(Color poColorLetra){
//    moColorLetra=poColorLetra;
//    repaint();
//  }
//  public void setFuenteLetra(Font poFuenteLetra){
//    moFuenteLetra=poFuenteLetra;
//    repaint();
//  }
