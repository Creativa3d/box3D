/*
 * JEstiloLinea.java
 *
 * Created on 13 de septiembre de 2004, 9:51
 */

package impresionXML.impresion.estructura;

import java.awt.Color;
import java.io.Serializable;

/**Estilo de una linea*/
public class JEstiloLinea implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    /**CTE. Si es solido*/
    public static final int mclSolido = 0;
    /**CTE. Si es punteado*/
    public static final int mclPunteado = 1;
    /**CTE. Si es rayado*/
    public static final int mclRayado = 2;
    /**Estilo*/
    public int mlEstilo = mclSolido;
    /**Grosor*/
    public float mdGrosor =(float)0.5;
    /**Color*/
    public Color moColor = Color.black;

    /**Constructor*/
    public JEstiloLinea(){
        super();
    }
    /**
     * Constructor
     * @param plEstilo CTE Estilo
     * @param plGrosor grosor
     * @param poColor Color
     */
    public JEstiloLinea(int plEstilo,float pdGrosor,Color poColor){
        mlEstilo = plEstilo;
        mdGrosor = pdGrosor;
        moColor = poColor;
    }
    /**
     * Constructor
     * @param plGrosor grosor
     * @param poColor color
     */
    public JEstiloLinea(float pdGrosor,Color poColor){
        mdGrosor = pdGrosor;
        moColor = poColor;
    }
    /**
     * Constructor
     * @param poColor color
     */
    public JEstiloLinea(Color poColor){
        moColor = poColor;
    }
    /**
     * Constructor
     * @param plGrosor grosor
     */
    public JEstiloLinea(float pdGrosor){
        mdGrosor = pdGrosor;
    }

    public Object clone() throws CloneNotSupportedException {
        Object retValue;
        
        retValue = super.clone();
        return retValue;
    }
    
}
