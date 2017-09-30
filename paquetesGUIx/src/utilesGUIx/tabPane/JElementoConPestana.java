/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.tabPane;

import java.awt.Color;
import javax.swing.JComponent;

public class JElementoConPestana {
    private JComponent moPesta;
    private JComponent moComp;
    private String msClave;
    private boolean mbActivado = false;
    private Color moColor;
    private Color moColorDesActivo;


    public JElementoConPestana(final JComponent poPesta, final JComponent poComp, final int mlClave){
        moPesta = poPesta;
        moComp = poComp;
        msClave = String.valueOf(mlClave);
        moColorDesActivo = poPesta.getBackground();
        moColor = moColorDesActivo.brighter();
        setActivado(mbActivado);
    }

    public JComponent getPesta() {
        return moPesta;
    }

    public JComponent getComp() {
        return moComp;
    }

    public String getClave() {
        return msClave;
    }

    public boolean isActivado() {
        return mbActivado;
    }

    void setActivado(boolean mbActivado) {
        this.mbActivado = mbActivado;
        moPesta.setEnabled(mbActivado);
    }

    public Color getColor() {
        return moColor;
    }

    public void setColor(Color moColor) {
        this.moColor = moColor;
    }

    public Color getColorDesActivo() {
        return moColorDesActivo;
    }

    public void setColorDesActivo(Color moColorDesActivo) {
        this.moColorDesActivo = moColorDesActivo;
    }
}
