/*
 * LabelCZ.java
 *
 * Created on 10 de febrero de 2007, 10:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUI;

import ListDatos.estructuraBD.JFieldDef;
import java.awt.Color;
import java.awt.Label;

public class LabelCZ extends Label {
    public static Color moColorObligatorio = Color.red;
    
    public LabelCZ() {
	super();
    }
    public LabelCZ(String text) {
        super(text);
    }
    public LabelCZ(String text, int alignment) {
        super(text, alignment);
    }

    public void setField(final JFieldDef poCampo){
        setText(poCampo.getCaption());
        setForeground((poCampo.getNullable()?null:moColorObligatorio));
    }
    
    
}
