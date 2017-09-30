/*
 * JPropiedadesGrafico.java
 *
 * Created on 3 de enero de 2006, 18:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorGraficosXY;

import java.awt.Color;

public class JGraficoPropiedades {
    
    public String msTituloPrincipio = "";
    public Color moColorSerie = Color.blue;
    
    public boolean mbYDivisiones = true;
    public boolean mbXDivisiones = false;
    
    public String msXEtiqEje = "Fecha";
    public String msYEtiqEje = "Valores";
    
    public boolean mbVisibleLeyenda=false;
    
    public String msXFormatoFecha = "MM/yyyy";
    public String msXFechaMin="";
    public String msXIntervalo=""; 
    
    public String msYValorMin="";
    public String msYIntervalo=""; 
    
    
    /** Creates a new instance of JPropiedadesGrafico */
    public JGraficoPropiedades() {
    }
    
    public JGraficoPropiedades Clone(){
        JGraficoPropiedades loProp = new JGraficoPropiedades();
        loProp.msTituloPrincipio = msTituloPrincipio;
        loProp.msXEtiqEje = msXEtiqEje;
        loProp.msXFechaMin = msXFechaMin;
        loProp.msXFormatoFecha = msXFormatoFecha;
        loProp.msXIntervalo = msXIntervalo;
        loProp.msYEtiqEje = msYEtiqEje;
        loProp.msYIntervalo = msYIntervalo;
        loProp.msYValorMin = msYValorMin;
        loProp.moColorSerie = new Color(moColorSerie.getRGB());

        loProp.mbVisibleLeyenda = mbVisibleLeyenda;
        loProp.mbXDivisiones = mbXDivisiones;
        loProp.mbYDivisiones = mbYDivisiones;
        
        return loProp;
    }
    
}
