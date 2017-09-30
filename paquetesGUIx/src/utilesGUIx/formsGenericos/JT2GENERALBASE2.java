/*
 * JT2CLIENTES.java
 *
 * Creado el 21/1/2006
 */
package utilesGUIx.formsGenericos;

import ListDatos.*;
import java.awt.Cursor;
import javax.swing.JPanel;

public abstract class JT2GENERALBASE2 extends JT2GENERALBASEModelo {

    public JT2GENERALBASE2() {
    }

    @Override
    public ListDatos.JListDatos getDatos() throws Exception {
        JListDatos loList=null;
        try {
            if (getPanel() != null && getPanel() instanceof JPanel) {
                ((JPanel) getPanel()).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            loList=super.getDatos();

        } finally {
            if (getPanel() != null && getPanel() instanceof JPanel) {
                ((JPanel) getPanel()).setCursor(Cursor.getDefaultCursor());
            }
        }
        return loList;
    }

    @Override
    public void refrescar() throws Exception {
        try {
            if (getPanel() != null) {
                ((JPanel) getPanel()).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            super.refrescar();
        } finally {
            if (getPanel() != null) {
                ((JPanel) getPanel()).setCursor(Cursor.getDefaultCursor());
            }
        }
    }
    
}
