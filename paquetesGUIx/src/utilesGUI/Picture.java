/*
 * Picture.java
 *
 * Created on 9 de abril de 2004, 11:23
 */

package utilesGUI;
import java.awt.*;

/**Componente para ver una imagen*/
public class Picture extends Canvas{
    private MediaTracker moCargador;
    private Image moImage=null;
    /** Creates a new instance of Picture */
    public Picture() {
        super();
        moCargador = new MediaTracker(this);
        setVisible(true);
        setBackground(Color.white);
    }
    /**
     * Devuelve la imagen visualizada
     * @return imagen
     */
    public Image getImage(){
        return moImage;
    }
    /**
     * Establece la imagen del componente
     * @param poImage Imagen
     */
    public void setImagen(Image poImage){
        //asiganamos la image
        moImage = poImage;
        //la añadimos al cargador
        moCargador = new MediaTracker(this);
        moCargador.addImage(moImage,0);
        //iniciamos la carga
        moCargador.checkAll(true);
    }
    /**
     * Establece el camino de la imagen del componente
     * @param psImagen camino de la imagen
     * @throws Exception error
     */
    public void setImagenCamino(final String psImagen) throws Exception {
        //creamos la image
        moImage = getToolkit().getImage(new java.net.URL(psImagen));
        //la añadimos al cargador
        moCargador = new MediaTracker(this);
        moCargador.addImage(moImage,0);
        //iniciamos la carga
        moCargador.checkAll(true);
        //esperamos a que termine
        try{
            moCargador.waitForAll();
        }catch(Exception e){
            //vacio
        }
        //repintamos
        repaint();
    }
    /**
     * Establece el camino de la imagen del componente, y no espera a que se termine de cargar
     * @param psImagen camino de la imagen
     */
    public void setImagenCaminoSinEsperar(String psImagen){
        //creamos la image
        moImage = getToolkit().getImage(psImagen);
        //la añadimos al cargador
        moCargador = new MediaTracker(this);
        moCargador.addImage(moImage,0);
        //iniciamos la carga
        moCargador.checkAll(true);
        //repintamos al segundo
        repaint(1000);
    }
    
    public void paint(Graphics g) {
        Rectangle loRect =  this.getBounds();
        if((loRect.width>=2)&&(loRect.height>=2)){
            g.draw3DRect(0, 0, loRect.width-2, loRect.height-2,false);
            if(moImage!=null){
                if(moCargador.checkAll()){
                    g.drawImage(moImage, 
                        1, 1, loRect.width-2, loRect.height-2, 
                        0, 0, loRect.width-3, loRect.height-3,
                        this);
                }else{
                    repaint(1000);
                }
            }
        }
    }
    
    
}
