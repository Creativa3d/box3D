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

import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.text.*;

/**
 * how to use: it is enabled adding the following code row into your own code:
 * Toolkit.getDefaultToolkit().getSystemEventQueue().push( new PopupMenuEventQueue());
 *<p>
 *Utilizzo: viene abilitato in automatico aggiungendo la seguente riga di codice:
 *Toolkit.getDefaultToolkit().getSystemEventQueue().push(new PopupMenuEventQueue());
 *
 */
public class PopupMenuEventQueue extends EventQueue {

    public JPopupMenu popup;
    public BasicAction cut, copy, paste, delete; //selectAll,
    private final String cutString;
    private final String CUT_ICON_PATH;
    private final ImageIcon cutIcon;
    private final String copyString;
    private final String COPY_ICON_PATH;
    private final ImageIcon copyIcon;
    private final String pasteString;
    private final String PASTE_ICON_PATH;
    private final ImageIcon pasteIcon;
    private final String deleteString;
    private final String DELETE_ICON_PATH;
    private final ImageIcon deleteIcon;
//    private final ResourceBundle bundle;
//    private final String selectAllString;

    public PopupMenuEventQueue() {
//      cutString = NbBundle.getMessage(org.openide.actions.CutAction.class, "Cut").replace("&", "");
        cutString = KeyEvent.getKeyText(KeyEvent.VK_CUT);
        CUT_ICON_PATH = "/utilesGUIx/images/Cut16.gif";
        cutIcon = new ImageIcon(CUT_ICON_PATH);

//      copyString = NbBundle.getMessage(org.openide.actions.CopyAction.class, "Copy").replace("&", "");
        copyString = KeyEvent.getKeyText(KeyEvent.VK_COPY);
        COPY_ICON_PATH = "/utilesGUIx/images/Copy16.gif";
        copyIcon = (new ImageIcon(COPY_ICON_PATH));

//      pasteString = NbBundle.getMessage(org.openide.actions.PasteAction.class, "Paste").replace("&", "");
        pasteString = KeyEvent.getKeyText(KeyEvent.VK_PASTE);
        PASTE_ICON_PATH = "/utilesGUIx/images/Paste16.gif";
        pasteIcon = (new ImageIcon(PASTE_ICON_PATH));

//      deleteString = NbBundle.getMessage(org.openide.actions.DeleteAction.class, "Delete").replace("&", "");
        deleteString = KeyEvent.getKeyText(KeyEvent.VK_DELETE);
        DELETE_ICON_PATH = "/utilesGUIx/images/Delete16.gif";
        deleteIcon = (new ImageIcon(DELETE_ICON_PATH));

//      selectAllString = KeyEvent.getKeyText(KeyEvent.VK_ALL_CANDIDATES);
//        bundle = ResourceBundle.getBundle("utilesGUIx/textPopupMenu/resources/resources/PopUpBundle");
//        selectAllString="Seleccionar todo";
    }

    public void createPopupMenu(JTextComponent textComponent) {

        cut = new PopCutAction(cutString, cutIcon);//(messages.getString("CutString"),null);
        copy = new PopCopyAction(copyString, copyIcon);//(messages.getString("CopyString"), null);
        paste = new PopPasteAction(pasteString, pasteIcon);//(messages.getString("PasteString"),null);
//        selectAll = new PopSelectAllAction(selectAllString, null);
        delete = new PopDeleteAction(deleteString, deleteIcon);//(messages.getString("DeleteString"),null);
        cut.setTextComponent(textComponent);
        copy.setTextComponent(textComponent);
        paste.setTextComponent(textComponent);
//        selectAll.setTextComponent(textComponent);
        delete.setTextComponent(textComponent);


        popup = new JPopupMenu();
        popup.add(cut);
        popup.add(copy);
        popup.add(paste);
        popup.add(delete);
//        popup.addSeparator();
//        popup.add(selectAll);
    }

    public void showPopup(Component parent, MouseEvent mouseEvt) {
        popup.validate();
        popup.show(parent, mouseEvt.getX(), mouseEvt.getY());
    }

    protected void dispatchEvent(AWTEvent event) {
        super.dispatchEvent(event);

        if (!(event instanceof MouseEvent)) {
            return;
        }
        MouseEvent me = (MouseEvent) event;
        if (!me.isPopupTrigger()) {
            return;
        }
        Component component = SwingUtilities.getDeepestComponentAt((Component) me.getSource(), me.getX(), me.getY());
        if (!(me.getSource() instanceof Component)) {
            return;
        }
        if (!(component instanceof JTextComponent)) {
            return;
        }
        if (MenuSelectionManager.defaultManager().getSelectedPath().length > 0) {
            return;
        }
        component.requestFocus(); //Thanks to wsc719@yahoo.com.cn for this contribution
        createPopupMenu((JTextComponent) component);
        showPopup((Component) me.getSource(), me);
    }
}
