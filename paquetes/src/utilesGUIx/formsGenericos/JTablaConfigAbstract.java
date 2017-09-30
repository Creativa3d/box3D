/*
 * JPersonalizacionTabla.java
 *
 * Created on 1 de diciembre de 2006, 14:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import utiles.JArchivo;
import utiles.JConversiones;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobalModelo;

public abstract class JTablaConfigAbstract implements ITablaConfig{
    public static final String mcsNombreDefecto = "Defecto";
    
    protected static JTablaConfigTablas moConfigTablas = null;
    protected JTablaConfigTabla moTablaConfig;
    protected JTablaConfigTablaConfig moTablaConfigConcreta;
    protected int[] malLong = null;
    protected int[] malOrden = null;
    protected boolean[] mabVisible = null;
    protected boolean mbAnularEventos = false;
    protected boolean mbInicializado = false;

    /** Creates a new instance of JPersonalizacionTabla */
    public JTablaConfigAbstract() {
        super();
    }
    
    public void setInicializado(boolean pbIni) {
        mbInicializado = pbIni;
    }

    /**  
     * @param psNombre
     * @param palLong
     * @param palOrden
     * @param pabVisible 
     */
    public void setDatos(
            final String psNombre, final int[] palLong, final int[] palOrden,
            boolean[] pabVisible) {
        mbInicializado = true;
        malLong = palLong;
        malOrden = palOrden;
        mabVisible = pabVisible;
        moTablaConfig = getConfigTablas().getTabla(psNombre);
    }

    public static synchronized void setConfigTablas(JTablaConfigTablas poConfig) {
        moConfigTablas = poConfig;
    }

    public static synchronized JTablaConfigTablas getConfigTablas() {
        if (moConfigTablas == null) {
            try {
                moConfigTablas = JTablaConfigLector.getTablaConfigTablas(JGUIxConfigGlobalModelo.getInstancia().getFicheroLongTablas());
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JTablaConfigAbstract.class.getName(), ex);
                try {
                    moConfigTablas = (
                        JTablaConfigLector.getTablaConfigTablas(JGUIxConfigGlobalModelo.getInstancia().getFicheroLongTablas()+".cs")
                        );
                } catch (Exception ex1) {    
                    
                    JDepuracion.anadirTexto(JTablaConfigAbstract.class.getName(), ex1);
                    moConfigTablas = new JTablaConfigTablas();
                }                
            }
        }
        return moConfigTablas;
    }

    @Override
    public boolean[] getCamposVisibles() {
        if (mbInicializado) {
            boolean[] labVisibles = new boolean[moTablaConfigConcreta.size()];
            for (int i = 0; i < moTablaConfigConcreta.size(); i++) {
                labVisibles[i] = moTablaConfigConcreta.getColumna(i).getLong() > 0;
            }
            return labVisibles;
        }
        return new boolean[0];
    }

    @Override
    public JTablaConfigTablaConfig getConfigTablaConcreta() {
        return moTablaConfigConcreta;
    }

    public JTablaConfigTabla getConfigTabla() {
        return moTablaConfig;
    }

    public String getIndiceConfig() {
        return moTablaConfigConcreta.getNombre();
    }

    @Override
    public JTablaConfigColumna getTablaConfigColumnaDeCampoReal(final int i) {

        return moTablaConfigConcreta.getColumna(String.valueOf(i));
    }


    public void borrar() throws Exception {
        if (moTablaConfigConcreta.getNombre().equals(mcsNombreDefecto)) {
            throw new Exception("No se puede borrar la config. por defecto");
        } else {
            moTablaConfig.removeConfig(moTablaConfigConcreta.getNombre());
            moTablaConfigConcreta = moTablaConfig.getConfig(0);
//            guardarConfig();
        }
    }

    public synchronized static void guardarConfig() throws FileNotFoundException, IOException {
        if (moConfigTablas != null) {
            try {
                JArchivo.guardarArchivo(new File(JGUIxConfigGlobalModelo.getInstancia().getFicheroLongTablas()), new File(JGUIxConfigGlobalModelo.getInstancia().getFicheroLongTablas()+".cs"));
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JTablaConfigAbstract.class.getName(), ex);
            }
            
            JTablaConfigLector.guardar(getConfigTablas(), JGUIxConfigGlobalModelo.getInstancia().getFicheroLongTablas());
        }
    }

    public void setAnularEventos(boolean b) {
        mbAnularEventos = b;
    }

    
    public static JListDatos getListOrdenado(final JListDatos poList, final JTablaConfigTablaConfig poConfig) throws CloneNotSupportedException, ECampoError {
        return getListOrdenado(poList, poConfig, false);
    }

    public static JListDatos getListOrdenado(final JListDatos poList, final JTablaConfigTablaConfig poConfig, final boolean pbCONColumnOcultas) throws CloneNotSupportedException, ECampoError {
        JListDatos loListNuevo = new JListDatos();

        for (int i = 0; i < poList.getFields().count(); i++) {
            JTablaConfigColumna loColumn = poConfig.getColumnaPorOrden(i);
            if (loColumn != null && loColumn.getLong() > 0 || pbCONColumnOcultas) {
                loListNuevo.getFields().addField(poList.getFields((int) JConversiones.cdbl(loColumn.getNombre())).Clone());
            }

        }

        if (poList.moveFirst()) {
            do {
                loListNuevo.addNew();
                int li = 0;
                for (int i = 0; i < poList.getFields().count(); i++) {
                    JTablaConfigColumna loColumn = poConfig.getColumnaPorOrden(i);
                    if (loColumn != null && loColumn.getLong() > 0 || pbCONColumnOcultas) {
                        loListNuevo.getFields(li).setValue(
                                poList.getFields((int) JConversiones.cdbl(loColumn.getNombre())).getString());
                        li++;
                    }

                }
                loListNuevo.update(false);

            } while (poList.moveNext());
        }

        return loListNuevo;
    }
}


