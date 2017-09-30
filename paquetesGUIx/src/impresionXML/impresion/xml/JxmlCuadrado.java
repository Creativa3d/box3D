/*
 * JxmlCuadrado.java
 *
 * Created on 25 de enero de 2007, 9:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import impresionXML.impresion.estructura.JEstiloLinea;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class JxmlCuadrado extends JxmlAbstract  implements IxmlObjetos {
    public static final String mcsNombreXml = "cuadrado";
    private static final long serialVersionUID = 1L;

    private Point2D moPunto1 = new Point2D.Double(0,0);
    private Point2D moPunto2 = new Point2D.Double(2,1);
    private JEstiloLinea moEstiloLinea = new JEstiloLinea();
    private String msNombre;

    /** Creates a new instance of JxmlCuadrado */
    public JxmlCuadrado() {
        super();
    }
    
    public String toString() {
        return getNombre();
    }

    public void imprimir(final JxmlBanda poBanda, final JxmlInforme poInforme) {
        Point2D loPunto1x2 = new Point2D.Float();
        Point2D loPunto1y2 = new Point2D.Float();
        loPunto1x2.setLocation(getPunto2().getX(), getPunto1().getY());
        loPunto1y2.setLocation(getPunto1().getX(), getPunto2().getY());
        
        poBanda.getLinea().insertarLinea(getPunto1(), loPunto1x2, getEstiloLinea());
        poBanda.getLinea().insertarLinea(getPunto1(), loPunto1y2, getEstiloLinea());

        poBanda.getLinea().insertarLinea(loPunto1x2, getPunto2(), getEstiloLinea());
        poBanda.getLinea().insertarLinea(loPunto1y2, getPunto2(), getEstiloLinea());

    }

    public String getNombre() {
        return msNombre;
    }
    public Object clone() throws CloneNotSupportedException {
        JxmlCuadrado retValue;
        
        retValue = (JxmlCuadrado)super.clone();
        
        retValue.setPunto1((Point2D) getPunto1().clone());
        retValue.setPunto2((Point2D) getPunto2().clone());
        retValue.setEstiloLinea((JEstiloLinea) getEstiloLinea().clone());
        
        
        return retValue;
    }

    public Point2D getPunto1() {
        return moPunto1;
    }

    public void setPunto1(final Point2D poPunto1) {
        Point2D loOld = this.moPunto1;
        this.moPunto1 = poPunto1;
        firePropertyChange("punto1", loOld, this.moPunto1);
    }

    public Rectangle2D.Double getRectangulo(){
        Rectangle2D.Double lo = new Rectangle2D.Double(moPunto1.getX(), moPunto1.getY(), 0 , 0);
        
        lo.add(moPunto2);
        
        return lo;
              
    }
    
    public void setPunto1y2(final Point2D poPunto1, final Point2D poPunto2) {
        
        Rectangle2D.Double loOld = getRectangulo();
        
        setPunto1(poPunto1);
        setPunto2(poPunto2);
        
        
        firePropertyChange("rectangulo", loOld, getRectangulo());
    }

    public Point2D getPunto2() {
        return moPunto2;
    }

    public void setPunto2(final Point2D poPunto) {
        Point2D loOld = this.moPunto2;
        this.moPunto2 = poPunto;
        firePropertyChange("punto2", loOld, this.moPunto2);
    }

    public JEstiloLinea getEstiloLinea() {
        return moEstiloLinea;
    }

    public void setEstiloLinea(final JEstiloLinea moEstiloLinea) {
        this.moEstiloLinea = moEstiloLinea;
    }

    public void setNombre(final String msNombre) {
        String lsOld = this.msNombre;
        this.msNombre = msNombre;
        firePropertyChange("nombre", lsOld, msNombre);
    }
    public String getNombreXML(){
        return mcsNombreXml;
    }

    public void visitar(IVisitorOperacion poOperador) throws Throwable {
        poOperador.operar(this);
    }
    
    
}
