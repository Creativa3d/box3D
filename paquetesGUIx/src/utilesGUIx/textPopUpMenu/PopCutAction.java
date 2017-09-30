// <editor-fold defaultstate="collapsed" desc="license">
/*
 *  Copyright 2010 Rocco Casaburo.
 *  mail address: rcp.nbm.casaburo at gmail.com
 *  Visit projects homepage at http://kenai.com/people/8504-Rocco-Casaburo
 *
 *  Licensed under the GNU General Public License, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *       http://www.gnu.org/licenses/gpl-3.0.html
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
// </editor-fold>
package utilesGUIx.textPopUpMenu;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import utiles.Copiar;

 class PopCutAction extends BasicAction {

    public PopCutAction(String text, Icon icon) {
        super(text, icon);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
     }

    
    public void actionPerformed(ActionEvent e) {
        try{
            Copiar loCP = new Copiar();
            loCP.setClip(comp.getSelectedText());
            if (comp.isEditable() && comp.isEnabled()) {
                comp.setText(
                    comp.getText().substring(0, comp.getSelectionStart()  ) +
                    comp.getText().substring(comp.getSelectionEnd(), comp.getText().length() )
                    );
            }
        }catch(Throwable e1){

        }
    }

    
    public boolean isEnabled() {
        return comp != null && comp.isEditable() && comp.getSelectedText() != null;
    }
}
