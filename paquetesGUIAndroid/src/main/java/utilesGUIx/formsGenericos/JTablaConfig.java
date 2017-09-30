/*
 * JPersonalizacionTabla.java
 *
 * Created on 1 de diciembre de 2006, 14:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

public class JTablaConfig extends JTablaConfigAbstract {

    /** Creates a new instance of JPersonalizacionTabla */
    public JTablaConfig() {
        super();
    }
    
    public void borrarEventosTabla(){
    }


    public void setIndiceConfig(final String psNombre) {
        moTablaConfigConcreta = moTablaConfig.getConfig(psNombre);
        if (moTablaConfigConcreta == null) {
            moTablaConfigConcreta = new JTablaConfigTablaConfig();
            moTablaConfigConcreta.setNombre(psNombre);
            moTablaConfig.addConfig(moTablaConfigConcreta);
        }
        aplicar();
    }

    public void aplicar() {
        mbAnularEventos = true;
        try {
        } finally {
            mbAnularEventos = false;
        }

    }

    public static JTablaConfigTablaConfig getTablaConfigConfig(Object poTablaReal) {
        JTablaConfigTablaConfig loConfig = new JTablaConfigTablaConfig();
        return loConfig;
    }

    private void setPropiedadesDeTabla() {
        if (!mbAnularEventos && mbInicializado) {
        }

    }

}


