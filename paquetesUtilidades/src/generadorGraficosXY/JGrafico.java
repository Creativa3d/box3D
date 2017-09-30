/*
 * JGrafico.java
 *
 * Created on 3 de enero de 2006, 18:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorGraficosXY;

import utiles.*;
import utilesGUI.grafxy2.JGraf2;
import utilesGUI.grafxy2.JGrafDatosSerie;

public class JGrafico {
    
    public JGraficoPropiedades moPropiedades=null;
    public IListaElementos moSeries = new JListaElementos();
    public String msCodigo="";

    /** Creates a new instance of JGrafico */
    public JGrafico() {
    }
    
    public void refrescarGraf(JGraficos poDatos, JGraf2 poGraf) throws Exception {
        //borramos series
        poGraf.getDatosX1().borrarSeries();
        
        //seleccionamos las propiedades del grafico
        JGraficoPropiedades loPropiedades = moPropiedades;
        if(loPropiedades==null){
            loPropiedades = poDatos.moPropiedades;
        }
        
        //Estilo lineas
        utilesGUI.grafxy2.estilos.JEstiloSerieLineaPunto loEstilo2 = new utilesGUI.grafxy2.estilos.JEstiloSerieLineaPunto();
        loEstilo2.moEstiloLinea.moColor = loPropiedades.moColorSerie;
        loEstilo2.moEstiloPunto.moColor = loPropiedades.moColorSerie;
        
        //rellenamos las series del grafico
        JDateEdu loDateMin = null;
        JDateEdu loDateMax = null;
        double ldYMin = 10000000;
        double ldYMax = -10000000;
        for(int i = 0; i < moSeries.size();i++){
            JGraficoSerie loSerie = (JGraficoSerie)moSeries.get(i);
            JGrafDatosSerie loGrafSerie = poGraf.getDatosX1().getY1().addSerie();
            loGrafSerie.setEstiloSerie(loEstilo2);
            
            loGrafSerie.msCaption = loSerie.msGrupo;
            
            if(loSerie.moDatos.moList.moveFirst()){
                if(loDateMin==null){
                    loDateMin = loSerie.moDatos.getFecha().getDateEdu();
                }
                if(loDateMax==null){
                    loDateMax = loSerie.moDatos.getFecha().getDateEdu();
                }
                JDateEdu loDate = null;
                double ldValor;
                do{
                    loDate = loSerie.moDatos.getFecha().getDateEdu();
                    ldValor = loSerie.moDatos.getValor().getDouble();
                    if(loDate.compareTo(loDateMin)==loDate.mclFechaMenor){
                        loDateMin = loDate;
                    }
                    if(loDate.compareTo(loDateMax)==loDate.mclFechaMayor){
                        loDateMax = loDate;
                    }
                    if(ldValor<ldYMin){
                        ldYMin = ldValor;
                    }
                    if(ldValor>ldYMax){
                        ldYMax = ldValor;
                    }
                    loGrafSerie.addXY(loDate, loSerie.moDatos.getValor().getDoubleConNull());
                }while(loSerie.moDatos.moList.moveNext());
            }
        }
        
        //ponemos propiedades generales
        poGraf.msTitulo = loPropiedades.msTituloPrincipio + msCodigo;
        poGraf.mbLeyendaVisible = loPropiedades.mbVisibleLeyenda;
        poGraf.getDatosX1().getY1().getEjeCoordenadas().getEstiloX().moEstiloTexto.mdAngulo=90;
        poGraf.getDatosX1().getY1().getEjeCoordenadas().getEstiloY().moEstiloTexto.mdAngulo=90;
        poGraf.getDatosX1().setClaseX(JGraficoFecha.class);
        if(loPropiedades.msXFormatoFecha.compareTo("")!=0){
            JGraficoFecha.msFormato = loPropiedades.msXFormatoFecha;
        }else{
            JGraficoFecha.msFormato = "MM/yyyy";
        }
        
        poGraf.getDatosX1().setTituloX(loPropiedades.msXEtiqEje);
        poGraf.getDatosX1().getY1().getEjeCoordenadas().msTituloY = loPropiedades.msYEtiqEje;
        
        if(loPropiedades.msYValorMin.compareTo("")!=0){
            poGraf.getDatosX1().getY1().getEjeCoordenadas().setYMin(Double.valueOf(loPropiedades.msYValorMin));
        }
        if(loPropiedades.msXFechaMin.compareTo("")!=0){
            poGraf.getDatosX1().setXMin(new JDateEdu(loPropiedades.msXFechaMin));
        }else{
            if(loDateMin!=null){
                loDateMin.setDia(1);
                loDateMin.setMes(1);
                poGraf.getDatosX1().setXMin(loDateMin);
            }
            
        }

        if(loPropiedades.msXIntervalo.compareTo("")!=0){
            poGraf.getDatosX1().getY1().getEjeCoordenadas().setValorSeparaEjeX(Double.valueOf(loPropiedades.msXIntervalo).doubleValue());
        }else{
            poGraf.getDatosX1().getY1().getEjeCoordenadas().setValorSeparaEjeX((loDateMax.getFechaEnNumero() - loDateMin.getFechaEnNumero())/5);
        }
        if(loPropiedades.msYIntervalo.compareTo("")!=0){
            poGraf.getDatosX1().getY1().getEjeCoordenadas().setValorSeparaEjeY(Double.valueOf(loPropiedades.msYIntervalo).doubleValue());
        }else{
            if(ldYMax > ldYMin){
                int lValor = (int)(ldYMax - ldYMin)/5;
                if(lValor<=0){
                    lValor = (int)(ldYMax - ldYMin);
                }
                poGraf.getDatosX1().getY1().getEjeCoordenadas().setValorSeparaEjeY(lValor);
            }
        }
        
        poGraf.getDatosX1().getY1().getEjeCoordenadas().getEstiloY().mbLineasDivision = loPropiedades.mbYDivisiones;
        poGraf.getDatosX1().getY1().getEjeCoordenadas().getEstiloX().mbLineasDivision = loPropiedades.mbXDivisiones;
        
        poGraf.Refrescar();
    }
}
