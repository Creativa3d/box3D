     /*
 * JPlugInListados.java
 *
 * Created on 9 de febrero de 2008, 12:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.plugin; 

import utiles.IListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

public class JPlugInListados implements IPlugIn {
    /** Creates a new instance of JPlugInImporExport */
    public JPlugInListados() {
    }

    public void procesarInicial(IPlugInContexto poContexto) {
//        IPlugInContextoGest loContexto = (IPlugInContextoGest)poContexto;
//        //prueba de menu
//        JMenu loMenuPrueba = new JMenu("prueba");
//        JMenuItem loItem = new JMenuItem("prueba item");
//        loItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                utilesGUIx.msgbox.JMsgBox.mensajeInformacion(new Label(), "Prueba exitosa");
//            }
//        });
//        loMenuPrueba.add(loItem);
//        loContexto.getFormPrincipal().getMenu().add(loMenuPrueba);
//        
//        //prueba informes
//        JInfINFORME loInf = new JInfINFORME("prueba", 
//                 new JInfMandarBAJAS(JTBAJAS2.msCTabla, JT2INF.mcsBaja),
//                 JTBAJAS2.msCTabla, false
//                 );
//        loContexto.getInformes().add(loInf);
        
    }


    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        IListaElementos loElem = poControlador.getParametros().getBotonesGenerales().getListaBotones();
        JAccionesListados loAcciones = new JAccionesListados(poControlador);
        JBotonRelacionado loBoton = new JBotonRelacionado(
                JAccionesListados.mcsListado, JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  JAccionesListados.mcsListado),"/images/Print16.gif"
                ,null, (IEjecutarExtend)loAcciones, null,JAccionesListados.mcsGrupoInformes);
        loBoton.setEsPrincipal(true);
        loElem.add(loBoton);
    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
    }
    public void procesarFinal(IPlugInContexto poContexto){
        
    }

    
}
