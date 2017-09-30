/*
 * JColumna.java
 *
 * Created on 14 de septiembre de 2004, 11:02
 */

package impresionXML.impresion.estructura;

import java.awt.Color;
import java.io.Serializable;
/**Propiedades de una columna de una tabla*/
public class JTColumna implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**color de fondo*/
    public Color moBackColor = Color.white;
    /**color de letras*/
    public Color moForeColor = Color.black;
    /**Porcentaje de alineación izq.-der, por ejemplo 0.5 es alineación central*/
    public float mdAlineacionX = 0;
    /**Porcentaje de alineación arriba.-abajo, por ejemplo 0.5 es alineación central*/
    public float mdAlineacionY = 0;
    /**Estilo borde de arriba*/
    public JEstiloLinea moBordeArriba;
    /**Estilo borde de abajo*/
    public JEstiloLinea moBordeAbajo;
    /**Estilo borde de Izq.*/
    public JEstiloLinea moBordeIzquierda;
    /**Estilo borde de der.*/
    public JEstiloLinea moBordeDerecha;
    /**Ancho cm*/
    public float mdAncho;
    /**Alto cm*/
    public float mdAlto;
    
//    /** Creates a new instance of JColumna */
//    public JTColumna() {
//    }
    
}
