/*
 * JPersonalizacionTabla.java
 *
 * Created on 1 de diciembre de 2006, 14:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobal;

public class JTablaConfig extends JTablaConfigAbstract implements ComponentListener, FocusListener, TableColumnModelListener {

    private final JTable moTablaReal;

    /** Creates a new instance of JPersonalizacionTabla */
    public JTablaConfig(final JTable poTabla) {
        super();
        moTablaReal = poTabla;
        if(moTablaReal!=null){
            moTablaReal.addComponentListener(this);
            moTablaReal.addFocusListener(this);
            moTablaReal.getColumnModel().addColumnModelListener(this);
        }
    }
    
    public void borrarEventosTabla(){
        if(moTablaReal!=null){
            moTablaReal.removeComponentListener(this);
            moTablaReal.removeFocusListener(this);
            moTablaReal.getColumnModel().removeColumnModelListener(this);
        }
    }


    public static void setLongColumna(final TableColumn loColumn, final int plLong) {
        if (plLong == 0) {
            loColumn.setMinWidth(0);
            loColumn.setPreferredWidth(0);
            loColumn.setMaxWidth(0);
        } else {
            loColumn.setMinWidth(10);
            loColumn.setMaxWidth(10000);
            loColumn.setPreferredWidth(plLong);
        }
    }

    public String getNombreColumna(final int plColumna) {
        return getNombreColumna(moTablaReal, plColumna);
    }

    public static int getIndiceColumna(final JTable poTabla, final String psNombre) {
        int lResult = -1;
        int lColumnModel = Integer.valueOf(psNombre).intValue();
        for (int i = 0; i < poTabla.getColumnModel().getColumnCount() && lResult == -1; i++) {
            if (poTabla.getColumnModel().getColumn(i).getModelIndex() == lColumnModel) {
                lResult = i;
            }
        }
        return lResult;
    }

    public static String getNombreColumna(final JTable poTabla, final int plColumna) {
        return String.valueOf(poTabla.getColumnModel().getColumn(plColumna).getModelIndex());
    }

    public static String getCaptionColumna(final JTable poTabla, final int plColumna) {
        return poTabla.getColumnModel().getColumn(plColumna).getHeaderValue().toString();
    }

    public void setIndiceConfig(final String psNombre) {
        moTablaConfigConcreta = moTablaConfig.getConfig(psNombre);
        if (moTablaConfigConcreta == null) {
            moTablaConfigConcreta = new JTablaConfigTablaConfig();
            moTablaConfigConcreta.setNombre(psNombre);
            moTablaConfig.addConfig(moTablaConfigConcreta);
        }
        for (int i = 0; moTablaReal!=null && i < moTablaReal.getColumnModel().getColumnCount(); i++) {
            try{
                //nombre de la columna para recuperar la config
                String lsNombre = getNombreColumna(moTablaReal, i);
                //indice asociado al JListDatos, q es el que se usa en la config. defecto
                int lIndice = moTablaReal.getColumnModel().getColumn(i).getModelIndex();
                //por defeccto tamano 80
                int lLong = 80;
                //el orden se conserva
                int lOrden = i;
                //si no es nulo y la long. es mayor q el indice
                if (malLong != null && malLong.length > lIndice) {
                    lLong = malLong[lIndice];
                }
                //si no es nulo y la long. es mayor q el indice
                if (malOrden != null && malOrden.length > lIndice) {
                    if (malOrden[lIndice] >= 0) {
                        lOrden = malOrden[lIndice];
                    }
                }
                //se recupera la config. de la columna
                JTablaConfigColumna loC = moTablaConfigConcreta.getColumna(lsNombre);
                //si vacia, se añade con la config. por defecto
                if (loC == null) {
                    loC = new JTablaConfigColumna(lsNombre, lOrden, lLong, getCaptionColumna(moTablaReal, i));
                    moTablaConfigConcreta.addColumna(loC);
                }else{
                    loC.setCaption(moTablaReal.getModel().getColumnName(lIndice));
                }
                //visibilidad de los campos obligatoria, se ven o no pa siempre
                if (mabVisible != null && mabVisible.length > lIndice) {
                    if (!mabVisible[lIndice]) {
                        loC.setLong(0);
                    }
                }
            }catch(Throwable e){//ignoramos el error por si las moscas, por un fallo aqui no debe parar ejecucion
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        aplicar();
    }

    public void aplicar() {
        boolean lbAux = mbAnularEventos;
        mbAnularEventos = true;
        try {
            for (int i = 0; moTablaReal!=null && i < moTablaReal.getColumnModel().getColumnCount(); i++) {
                JTablaConfigColumna loColumna = moTablaConfigConcreta.getColumna(getNombreColumna(moTablaReal, i));
                if (loColumna != null) {
                    setLongColumna(moTablaReal.getColumnModel().getColumn(i), loColumna.getLong());
                }
            }
            for (int i = 0;moTablaReal!=null && i < moTablaReal.getColumnModel().getColumnCount(); i++) {
                JTablaConfigColumna loColumna = moTablaConfigConcreta.getColumnaPorOrden(i);
                if (loColumna != null) {
                    int lIndex = getIndiceColumna(moTablaReal, loColumna.getNombre());
                    if (lIndex >= 0) {
                        try {
                            moTablaReal.getColumnModel().moveColumn(lIndex, i);
                        } catch (Exception e) {
                            //no es un error grave y puede pasar cuando se añaden/quitan columnas de la consulta
                            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
                        }
                    }
                }
            }
        } finally {
            mbAnularEventos = lbAux;
        }

    }

    public void setAnularEventos(boolean b) {
        mbAnularEventos = b;
    }

    public static JTablaConfigTablaConfig getTablaConfigConfig(JTable poTablaReal) {
        JTablaConfigTablaConfig loConfig = new JTablaConfigTablaConfig();
        for (int i = 0; i < poTablaReal.getColumnModel().getColumnCount(); i++) {
            TableColumn loColumna = poTablaReal.getColumnModel().getColumn(i);
            String lsNombre = getNombreColumna(poTablaReal, i);
            int lLong = loColumna.getWidth();
            JTablaConfigColumna loC = loConfig.getColumna(lsNombre);
            if (loC == null) {
                loC = new JTablaConfigColumna(lsNombre, i, lLong, getCaptionColumna(poTablaReal, i));
                loConfig.addColumna(loC);
            }
            loC.setLong(lLong);
            loC.setOrden(i);

        }
        return loConfig;
    }

    private void setPropiedadesDeTabla() {
        if (!mbAnularEventos && mbInicializado) {
//            System.out.println("setPropiedadesDeTabla");
            for (int i = 0; moTablaReal!=null && i < moTablaReal.getColumnModel().getColumnCount(); i++) {
                TableColumn loColumna = moTablaReal.getColumnModel().getColumn(i);
                String lsNombre = getNombreColumna(moTablaReal, i);
                int lLong = loColumna.getWidth();
                JTablaConfigColumna loC = moTablaConfigConcreta.getColumna(lsNombre);
                if (loC == null) {
                    loC = new JTablaConfigColumna(lsNombre, i, lLong, getCaptionColumna(moTablaReal, i));
                    moTablaConfigConcreta.addColumna(loC);
                }
                loC.setLong(lLong);
                loC.setOrden(i);

            }
        }

    }

    public void componentResized(final ComponentEvent e) {
//        System.out.println("componentResized");
        setPropiedadesDeTabla();
    }

    public void componentMoved(final ComponentEvent e) {
//        System.out.println("componentMoved");
        setPropiedadesDeTabla();
    }

    public void componentShown(final ComponentEvent e) {
//        System.out.println("componentShown");
        setPropiedadesDeTabla();
    }

    public void componentHidden(final ComponentEvent e) {
//        System.out.println("componentHidden");
        setPropiedadesDeTabla();
    }

    public void focusGained(final FocusEvent e) {
//        System.out.println("focusGained");
        setPropiedadesDeTabla();
    }

    public void columnMarginChanged(ChangeEvent e) {
    }

    public void columnSelectionChanged(ListSelectionEvent e) {
    }

    public void columnAdded(TableColumnModelEvent e) {
//        System.out.println("columnAddes");
        setPropiedadesDeTabla();
    }

    public void columnMoved(TableColumnModelEvent e) {
//        System.out.println("columnMoved");
        setPropiedadesDeTabla();
    }

    public void columnRemoved(TableColumnModelEvent e) {
//        System.out.println("columnRemoved");
        setPropiedadesDeTabla();
    }

    public void focusLost(final FocusEvent e) {
        setPropiedadesDeTabla();
    }
//    protected void finalize () throws Throwable {
//        if(moConfigTablas!=null){
//            try {
//                guardarConfig();
//            } catch (FileNotFoundException ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            } catch (IOException ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
//        super.finalize();
//    }
}


