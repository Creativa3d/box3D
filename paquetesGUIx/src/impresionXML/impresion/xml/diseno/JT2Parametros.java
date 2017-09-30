/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno;

import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;

/**
 *
 * @author eduardo
 */
public class JT2Parametros {
    public String mstitulo="";
    public double x;
    public double y;
    
    public boolean mbPresentarX = true;
    public boolean mbPresentarY = true;
    public boolean mbCancelado;
    
//    public boolean mbObligatorioX = true;
//    public boolean mbObligatorioY = true;
    
    public JT2Parametros(){
        
    }
    public void mostrar(CallBack<JMostrarPantallaParam> poCall) throws Exception {
        JPanelParametros loform = new JPanelParametros();
        loform.setDatos(this);
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(loform,null, loform, JMostrarPantalla.mclEdicionFrame);
        loParam.setCallBack(poCall);
        JDatosGeneralesP.getDatosGenerales().mostrarForm(loParam);
    }
    public void validar() throws Exception {
    }
    
}
