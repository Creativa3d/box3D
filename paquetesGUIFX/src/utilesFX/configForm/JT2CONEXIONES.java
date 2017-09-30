/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package utilesFX.configForm;

import ListDatos.IFilaDatos;
import utiles.config.JDatosGeneralesXML;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesGUIx.configForm.ICONEXIONESMostrar;
import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.JT2CONEXIONESModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;

public class JT2CONEXIONES extends JT2CONEXIONESModelo {
    
    public JT2CONEXIONES(final JDatosGeneralesXML poDatosGeneralesXML, final IMostrarPantalla poMostrarPantalla) throws Exception {
        super(poDatosGeneralesXML, poMostrarPantalla, new ICONEXIONESMostrar() {
            public void mostrar(JConexion loConex, JT2CONEXIONESModelo poConexiones, IMostrarPantalla poMostrar) throws Exception {
                JPanelConexionesEDICION loPanel = new JPanelConexionesEDICION();
                loPanel.setDatos(loConex, poConexiones);
                loPanel.getParametros().setPlugInPasados(true);
                poMostrar.mostrarEdicion(loPanel,null, loPanel,JMostrarPantalla.mclEdicionDialog);
            }
        });
    }

    @Override
    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600, 1, IMostrarPantalla.mclEdicionFrame);
    }

    @Override
    public void datosactualizados(IFilaDatos poFila) throws Exception {
        super.datosactualizados(poFila);
        getIO().guardarListaConexiones(getListaConex());
    }
    
    
    
}
