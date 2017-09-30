/*
 * JBarraDesplazamiento.java
 *
 * Created on 2 de abril de 2004, 13:02
 */

package utilesGUI.tabla;

import java.awt.event.*;
import java.awt.*;
import java.io.Serializable;
/**
*clase barra de desplazamiento
*/
class JBarraDesplazamiento implements Serializable {
    //ancho de la barra
    public static final int mclAncho = 17;
    //tamaño
    private Dimension moSize=null;
    //indica si la barra es vertical, si no horizontal
    private final boolean mbEsVertical;
    //numero de registros que desplaza de una vez cuando pulsas en la barra 
    //y no en elñ circulo
    int mlBloque = 10;
    //posicion de la primera linea de la rejilla visible
    int mlIndex =0;
    //número maximo de registros
    private int mlMax = 100;
    //colores
    private Color moColorExtremos = Color.lightGray;
    private Color moColorCentro = Color.white;
    private Color moColorLineas = Color.black;
    //indica si este objeto se usa en la tbla o permaneze invisible
    private boolean mbSeUsa = false;
    //indica si se tiene que refrescar la estructrua de datos
    private boolean mbRefrescar = true;
//    /**
//    * Imagen para el buffer en donde pintar
//    */
//    private Image moImg=null;
   
    /**
     *Rectangulo extremo menos
     */
    private final Rectangle moRecExtremoMenos = new Rectangle();
    /**
     *Rectangulo extremo mas
     */
    private final Rectangle moRecExtremoMas = new Rectangle();
    /**
     *Rectangulo de enmedio(todo)
     */
    private final Rectangle moRecCentro = new Rectangle();
    /**
     *Variable que representa el centro en funcion de la posicion
     */
    private int mlCentro=0;
    
    
    //contructor
    public JBarraDesplazamiento(boolean pbEsVertical){
        mbEsVertical=pbEsVertical;
    }
    //dimensiones del rectangulo en donde se pinta
    public void setSize(Dimension poSize){
        if(moSize==null){
            mbRefrescar=true;
        } else {
            if((poSize.height!=moSize.height)||
               (poSize.width !=moSize.width)) {
                mbRefrescar=true;
            }
        }
        moSize = poSize;
    }
    //establece el max de la barra
    public void setMax(int plMax){
        mlMax = plMax;
    }
    //establece el index de la barra
    public void setIndex(int plIndex){
        mlIndex = plIndex;
    }
    //colores de la barra
    public void setColores(Color poColorExtremos, Color poColorCentro, Color poColorLineas){
        moColorExtremos=poColorExtremos;
        moColorCentro=poColorCentro;
        moColorLineas=poColorLineas;
    }
    //indica si se dibuja la barra o no
    public void setSeUsa(boolean pbSeUsa){
        mbSeUsa=pbSeUsa;
    }
    public boolean getSeUsa(){
        return mbSeUsa;
    }
    //dibuja la barra 
    public void paint(Graphics g2, Component poComp){
        if (mbSeUsa){
            if((mbRefrescar)){
                crearDimesiones();
            }
            pintar(g2);
            mbRefrescar = false;
        }
    }
    private void crearDimesiones(){
        if (mbEsVertical){
            moRecExtremoMenos.x = moSize.width-mclAncho;
            moRecExtremoMenos.y = 0;
            moRecExtremoMenos.width = mclAncho;
            moRecExtremoMenos.height=mclAncho;
            
            moRecExtremoMas.x = moSize.width-mclAncho;
            moRecExtremoMas.y = moSize.height-mclAncho*1;
            moRecExtremoMas.width = mclAncho;
            moRecExtremoMas.height= mclAncho;
            
            moRecCentro.x = moSize.width-mclAncho;
            moRecCentro.y = mclAncho;
            moRecCentro.width = mclAncho;
            moRecCentro.height= moSize.height-mclAncho*2-2;
        }else{
            moRecExtremoMenos.x = 0;
            moRecExtremoMenos.y = moSize.height-mclAncho;
            moRecExtremoMenos.width = mclAncho;
            moRecExtremoMenos.height=mclAncho;
            
            moRecExtremoMas.x = moSize.width-mclAncho*2;
            moRecExtremoMas.y = moSize.height-mclAncho*1;
            moRecExtremoMas.width = mclAncho;
            moRecExtremoMas.height= mclAncho;
            
            moRecCentro.x = mclAncho;
            moRecCentro.y = moSize.height-mclAncho;
            moRecCentro.width = moSize.width-mclAncho*3-2;
            moRecCentro.height= mclAncho;
        }
    }
    private void pintar(Graphics g){
        //extremos
        g.setColor(moColorExtremos);
        g.fillRect(moRecExtremoMenos.x, moRecExtremoMenos.y, moRecExtremoMenos.width, moRecExtremoMenos.height);
        g.fillRect(moRecExtremoMas.x, moRecExtremoMas.y, moRecExtremoMas.width, moRecExtremoMas.height);
        //centro
        g.setColor(moColorCentro);
        g.fillRect(moRecCentro.x, moRecCentro.y, moRecCentro.width, moRecCentro.height);
        //lineas
        g.setColor(moColorLineas);
        g.drawRect(moRecCentro.x, moRecCentro.y, moRecCentro.width, moRecCentro.height);
        g.drawRect(moRecExtremoMenos.x, moRecExtremoMenos.y, moRecExtremoMenos.width+(mbEsVertical?-1:0), moRecExtremoMenos.height+(mbEsVertical?0:-1));
        g.drawRect(moRecExtremoMas.x, moRecExtremoMas.y, moRecExtremoMas.width+(mbEsVertical?-1:0), moRecExtremoMas.height-1);
        
        g.setColor(moColorExtremos);
        
        if (mbEsVertical){
            mlCentro = moRecCentro.y + (int)(((double)mlIndex/(double)(mlMax-1)) * (double)moRecCentro.height);
            g.drawRect(moRecCentro.x,mlCentro , moRecCentro.width-1, 2);
        }else{
            mlCentro = moRecCentro.x+(int)(((double)mlIndex/(double)(mlMax-1)) * (double)moRecCentro.width);
            g.drawRect(mlCentro, moRecCentro.y , 2, moRecCentro.height -1);
        }
        
        //circulos de los extremos
        g.setColor(moColorLineas);
        g.drawOval(moRecExtremoMenos.x+1, moRecExtremoMenos.y+1, moRecExtremoMenos.width-3, moRecExtremoMenos.height-3);
        g.drawOval(moRecExtremoMas.x+1, moRecExtremoMas.y+1, moRecExtremoMas.width-3, moRecExtremoMas.height-3);
        
    }
    
    //ejecutamos los click y vemos si se incrementa o decrementa
    public boolean mbClick(MouseEvent e){
        boolean lbResul=false;
        if (mbSeUsa){
            if (moRecExtremoMenos.contains(e.getX(),e.getY())) {
                lbResul=incrementar(-1);
            }
            if (moRecExtremoMas.contains(e.getX(),e.getY())) {
                lbResul=incrementar(1);
            }
            if (moRecCentro.contains(e.getX(),e.getY())) {
                if (mbEsVertical) {
                    if (e.getY()>mlCentro){
                        lbResul=incrementar(mlBloque );
                    }else {
                        lbResul=incrementar(-mlBloque );
                    }
                }else{
                    if (e.getX()>mlCentro){
                        lbResul=incrementar(mlBloque );
                    }else {
                        lbResul=incrementar(-mlBloque );
                    }
                }
            }
                
        }
        return lbResul;
    }
    //incrementamos la variable
    public boolean incrementar(int plNumero){
        boolean lbResul=false;
        if (plNumero<0){
            if (mlIndex>0){
                mlIndex += plNumero;//ya que el numero es negativo
                if (mlIndex<0) {
                    mlIndex = 0;
                }
                lbResul=true;
            }
        }else{
            if (mlIndex<(mlMax-1)){
                mlIndex += plNumero;
                if (mlIndex>=mlMax) {
                    mlIndex = mlMax-1;
                }
                lbResul=true;
            }
        }
        return lbResul;
    }
}