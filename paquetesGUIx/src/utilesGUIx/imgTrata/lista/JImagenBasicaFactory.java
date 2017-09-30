/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.imgTrata.lista;


public class JImagenBasicaFactory implements IImagenFactory {
    protected final int mlImagen;
    protected final int mlDescrip;
    protected String msRutaBase;

    public JImagenBasicaFactory(int plImagen, int plDescrip){
        mlImagen = plImagen;
        mlDescrip = plDescrip;
    }
    public JImagenBasicaFactory(int plImagen, int plDescrip, String psRutaBase){
        this(plImagen, plDescrip);
        msRutaBase = psRutaBase;
    }

    public IImagen getImagenNueva() {
        return new JImagenBasica(mlImagen, mlDescrip, msRutaBase);
    }
}
