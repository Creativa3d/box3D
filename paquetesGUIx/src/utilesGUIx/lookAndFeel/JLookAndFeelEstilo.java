/*
 * JLookAndFeelEstilo.java
 *
 * Created on 3 de enero de 2006, 12:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.lookAndFeel;

import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import utiles.*;

public class JLookAndFeelEstilo {
    public static final int mclCaption = 0;
    public static final int mclTema = 1;
    public static final int mclMetodo = 2;
    
    public String msEstilo;
    public String msCaption;
    IListaElementos moTemas = new JListaElementos();
    
    /** Creates a new instance of JLookAndFeelEstilo */
    public JLookAndFeelEstilo(String psEstilo, String psCaption) {
        super();
        msEstilo = psEstilo;
        msCaption = psCaption;
    }
    
    public void addTema(String psCaption, String msTema){
        IFilaDatos loFila = new JFilaDatosDefecto("");
        loFila.addCampo(psCaption);
        loFila.addCampo(msTema);
        loFila.addCampo("setCurrentTheme");
        moTemas.add(loFila);
    }
    public void addTema(String psCaption, String msTema, String psMetodo){
        IFilaDatos loFila = new JFilaDatosDefecto("");
        loFila.addCampo(psCaption);
        loFila.addCampo(msTema);
        loFila.addCampo(psMetodo);
        moTemas.add(loFila);
    }
    
}
