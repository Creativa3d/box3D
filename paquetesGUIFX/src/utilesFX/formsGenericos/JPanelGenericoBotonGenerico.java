/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import javafx.scene.control.Button;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;

class JPanelGenericoBotonGenerico {
        public IBotonRelacionado moBotonRelac;
        public Button moButton;
        
        JPanelGenericoBotonGenerico(IBotonRelacionado poBotonRelac, Button poButton){
            moButton=poButton;
            moBotonRelac=poBotonRelac;
        }
    }