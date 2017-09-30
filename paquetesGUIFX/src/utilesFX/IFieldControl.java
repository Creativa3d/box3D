/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX;

import ListDatos.estructuraBD.JFieldDef;
import utilesGUIx.formsGenericos.edicion.ITextBD;

/**
 *
 * @author eduardo
 */
public interface IFieldControl  extends utilesGUI.tabla.IComponentParaTabla, ITextBD{
    public JFieldDef getCampo();
}
