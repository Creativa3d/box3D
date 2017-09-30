/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xmlxsl;

import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

/**
 *
 * @author pvillar
 */
public class JXmlXslInformeParam {
    
    private MediaSizeName moMediaName;
    private OrientationRequested moOrientation;
    
    public JXmlXslInformeParam(){
        this(MediaSizeName.ISO_A4,OrientationRequested.PORTRAIT);
    }

    public JXmlXslInformeParam(MediaSizeName poMediaName, OrientationRequested poOrientation) {
        this.moMediaName = poMediaName;
        this.moOrientation = poOrientation;
    }

    public MediaSizeName getMoMediaName() {
        return moMediaName;
    }

    public void setMoMediaName(MediaSizeName moMediaName) {
        this.moMediaName = moMediaName;
    }

    public OrientationRequested getMoOrientation() {
        return moOrientation;
    }

    public void setMoOrientation(OrientationRequested moOrientation) {
        this.moOrientation = moOrientation;
    }
    
    
    
    
}
