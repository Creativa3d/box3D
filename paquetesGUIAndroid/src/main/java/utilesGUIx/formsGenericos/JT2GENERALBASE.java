/*
* JT2CLIENTES.java
*
* Creado el 21/1/2006
*/
package utilesGUIx.formsGenericos;


import utilesGUIx.Rectangulo;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.formsGenericos.boton.*;

public abstract class JT2GENERALBASE extends JT2GENERALBASE2 {
    public JT2GENERALBASE(){
        moParametros.getBotonesGenerales().addBotones(getBotones());
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(getNombre());
        getBotonesGenerales();
    }
    
    public IBotonRelacionado[] getBotones() {
        return null;
    }
    public int[] getLongitudCampos() {
        return null;
    }
    public String getNombre() {
        return "";
    }
    public JPanelGeneralBotones getBotonesGenerales() {
        return getParametros().getBotonesGenerales();
    }

}