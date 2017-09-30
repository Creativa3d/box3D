/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.controlProcesos;

import utilesGUI.procesar.IProcesoAccion;
import utilesGUIx.controlProcesos.IProcesoElemento;
import utilesGUIx.controlProcesos.IProcesoElementoFactoryMethod;
import utilesGUIx.controlProcesos.JProcesoManejador;


/**
 *
 * @author eduardo
 */
public class JProcesoElementoFactoryMethod implements IProcesoElementoFactoryMethod{

    public IProcesoElemento getProcesoElemento(IProcesoAccion poProceso, boolean pbConMostrarForm, JProcesoManejador poManejador) {
        return new JProcesoElemento(poProceso, pbConMostrarForm, poManejador);
    }
    
}
