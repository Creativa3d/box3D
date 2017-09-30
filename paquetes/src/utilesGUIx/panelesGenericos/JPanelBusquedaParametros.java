/*
 * JPanelBusquedaParametros.java
 *
 * Created on 19 de mayo de 2006, 16:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.panelesGenericos;

import ListDatos.JSTabla;
import utilesGUIx.ITableCZColores;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IPanelControlador;

public class JPanelBusquedaParametros {
    /**moControlador Controlador*/
    public IPanelControlador moControlador;
    /**moTabla Tabla a mostrar*/
    public JSTabla moTabla;
    /**mbConDatos Si moTabla viene con todos los datos y no hace falta recuperar del servidor*/
    public boolean mbConDatos;
    /**mlCamposPrincipales Lista de posiciones de campos principales*/
    public int[] mlCamposPrincipales;
    /**malDescripciones Lista de posiciones de campos para la descripción*/
    public int[] malDescripciones;
    /**mbMensajeSiNoExiste Si presenta un mensaje si no existe*/
    public boolean mbMensajeSiNoExiste;
    /**masTextosDescripciones Lista de textos previos a las descripciones*/
    public String[] masTextosDescripciones ;
    /**Tipo de filtro rapido, constantes de JPanelGeneralParametros*/
    public String msTipoFiltroRapido = JGUIxConfigGlobalModelo.getInstancia().getTipoFiltroRapidoDefecto();
    public int mlAlto = 400;
    public int mlAncho = 600;
    public boolean mbFiltro = true;
    public boolean mbEdicionLista = false;
    public boolean mbTrim = true;
    /**Si true: no deja salir del campo hasta q no se meta una valor valido de la lista o vacio*/
    public boolean mbRecuperarFocoSinNoExiste=false;

    /**Colores lineas*/
    public ITableCZColores moColores = null;

    public void inicializarPlugIn(){
        if(moControlador!=null && JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria()!=null){
            JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInManager().procesarControlador(
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto(),
                moControlador);
        }
    }
    
//    /** Creates a new instance of JPanelBusquedaParametros */
//    public JPanelBusquedaParametros() {
//        super();
//    }
    
}
