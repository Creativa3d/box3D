/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesChart.guardar;

import utiles.JCadenas;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.boton.IEjecutar;

/**
 *
 * @author eduardo
 */
public class JDatosGraf2Guardar {
    public static final int mclSimple = 0;
    public static final int mclSeries = 1;
    
    public static final int mclFichero = 0;
    public static final int mclDirectorio = 1;
    public static final int mclWord = 2;

    public int mlProceso = mclSeries;
    public int mlDestino = mclDirectorio;
    /**Ancho CM*/
    public double mdAncho=15;
    /**Alto CM*/
    public double mdAlto=10;
    
    public String msRuta="";
    
    public IEjecutar moEjecutar=null;
    

    public void ejecutar() throws Exception{
        moEjecutar.actionPerformed(new ActionEventCZ(this, 0, "Guardar"), -1);
    }

    public void validar() throws Exception {
        if(JCadenas.isVacio(msRuta)){
            throw new Exception("Ruta vacía");
        }
        if(mdAlto<=0){
            throw new Exception("El alto no es válido"); 
        }
        if(mdAncho<=0){
            throw new Exception("El ancho no es válido"); 
        }            
    }
}
