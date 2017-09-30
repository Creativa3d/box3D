/*
 * NodoArbol.java
 *
 * Created on 8 de julio de 2002, 21:09
 */
package utilesGUIx.plugin.seguridad.visual;

import ListDatos.*;

public class JNodoArbol {
    private IFilaDatos moDatos;
    private int[] malListaCaption;

    public JNodoArbol(final int[] palListaCaption, IFilaDatos poDatos) {
        super();
        malListaCaption = palListaCaption;
        moDatos = poDatos;
    }
    public String toString() {
        StringBuffer lsDatos = new StringBuffer(malListaCaption.length * 5);
        for(int i = 0 ; i < malListaCaption.length; i++){
            lsDatos.append(moDatos.msCampo(malListaCaption[i]));
            lsDatos.append(' ');
        }
        return lsDatos.toString();
    }
    public IFilaDatos getDatos() {
        return moDatos;
    }

    public void setDatos(final IFilaDatos moDatos) {
        this.moDatos = moDatos;
    }

}
