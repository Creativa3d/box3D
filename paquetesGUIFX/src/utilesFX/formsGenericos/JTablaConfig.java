/*
 * JPersonalizacionTabla.java
 *
 * Created on 1 de diciembre de 2006, 14:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;


import ListDatos.ECampoError;
import ListDatos.JListDatos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import utiles.JConversiones;
import utiles.JDepuracion;
import utilesFX.JTableViewCZ;
import utilesGUIx.formsGenericos.JTablaConfigAbstract;
import utilesGUIx.formsGenericos.JTablaConfigColumna;
import utilesGUIx.formsGenericos.JTablaConfigTablaConfig;

public class JTablaConfig extends JTablaConfigAbstract implements ChangeListener<Boolean>, ListChangeListener, EventHandler<MouseEvent> {

    private final JTableViewCZ moTablaReal;

    /** Creates a new instance of JPersonalizacionTabla */
    public JTablaConfig(final JTableViewCZ poTabla) {
        super();
        moTablaReal = poTabla;
        if(moTablaReal!=null){
            moTablaReal.focusedProperty().addListener(this);
            moTablaReal.getColumns().addListener(this);
            moTablaReal.setOnMouseDragged(this);
        }
    }
    
    public void borrarEventosTabla(){
        if(moTablaReal!=null){
            moTablaReal.focusedProperty().removeListener(this);
            moTablaReal.getColumns().remove(this);
            moTablaReal.setOnMouseDragged(null);
        }
    }


    public static void setLongColumna(final TableColumn loColumn, final double plLong) {
        if (plLong == 0) {
            loColumn.setMinWidth(0);
            loColumn.setVisible(false);
            loColumn.setMaxWidth(0);
            loColumn.setPrefWidth(0);
        } else {
            loColumn.setMinWidth(10);
            loColumn.setMaxWidth(10000);
            loColumn.setPrefWidth(plLong);
            loColumn.setVisible(true);
        }
    }

    public String getNombreColumna(final int plColumna) {
        return getNombreColumna(moTablaReal, plColumna);
    }

    public static TableColumn getTableColumn(final JTableViewCZ poTabla, final String psNombre) {
        int lIndex = getIndiceColumna(poTabla, psNombre);
        if(lIndex>=0){
            return (TableColumn) poTabla.getColumns().get ( lIndex);
        }else{
            return null;
        }
    }
    public static int getIndiceColumna(final JTableViewCZ poTabla, final String psNombre) {
        int lResult = -1;
        int lColumnModel = Integer.valueOf(psNombre).intValue();
        for (int i = 0; i < poTabla.getColumns().size()&& lResult == -1; i++) {
            TableColumn loColumna = (TableColumn) poTabla.getColumns().get(i);
            if (JConversiones.cdbl(loColumna.getId()) == lColumnModel) {
                lResult = i;
            }
        }
        return lResult;
    }

    public static String getNombreColumna(final JTableViewCZ poTabla, final int plColumna) {
        return ((TableColumn)poTabla.getColumns().get(plColumna)).getId();
    }

    public static String getCaptionColumna(final JTableViewCZ poTabla, final int plColumna) {
        return ((TableColumn)poTabla.getColumns().get(plColumna)).getText();
    }

    public void setIndiceConfig(final String psNombre) {
        moTablaConfigConcreta = moTablaConfig.getConfig(psNombre);
        if (moTablaConfigConcreta == null) {
            moTablaConfigConcreta = new JTablaConfigTablaConfig();
            moTablaConfigConcreta.setNombre(psNombre);
            moTablaConfig.addConfig(moTablaConfigConcreta);
        }
        for (int i = 0; moTablaReal!=null && i < moTablaReal.getColumns().size(); i++) {
            try{
                TableColumn loColumna = (TableColumn) moTablaReal.getColumns().get(i);
                //nombre de la columna para recuperar la config
                String lsNombre = getNombreColumna(moTablaReal, i);
                //indice asociado al JListDatos, q es el que se usa en la config. defecto
                int lIndice = (int)JConversiones.cdbl(loColumna.getId());
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
                    loC.setCaption(loColumna.getText());
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
//
    public void aplicar() {
        mbAnularEventos = true;
        try {
            for (int i = 0; moTablaReal!=null && i < moTablaReal.getColumns().size(); i++) {
                TableColumn loColumnaTablaFisica = (TableColumn) moTablaReal.getColumns().get(i);
                JTablaConfigColumna loColumna = moTablaConfigConcreta.getColumna(getNombreColumna(moTablaReal, i));
                if (loColumna != null) {
                    setLongColumna(loColumnaTablaFisica, loColumna.getLong());
                }
            }
            for (int i = 0;moTablaReal!=null && i < moTablaReal.getColumns().size(); i++) {
                JTablaConfigColumna loColumna = moTablaConfigConcreta.getColumnaPorOrden(i);
                if (loColumna != null) {
                    int lIndex = getIndiceColumna(moTablaReal, loColumna.getNombre());
                    if (lIndex >= 0 && lIndex!=i) {
                        try {
                            Object loo = moTablaReal.getColumns().remove(lIndex);
                            moTablaReal.getColumns().add(i, loo);
                        } catch (Exception e) {
                            //no es un error grave y puede pasar cuando se añaden/quitan columnas de la consulta
                            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), e);
                        }
                    }
                }
            }
        } finally {
            mbAnularEventos = false;
        }

    }

    public void setAnularEventos(boolean b) {
        mbAnularEventos = b;
    }

    public static JTablaConfigTablaConfig getTablaConfigConfig(JTableViewCZ poTablaReal) {
        JTablaConfigTablaConfig loConfig = new JTablaConfigTablaConfig();
        for (int i = 0; i < poTablaReal.getColumns().size(); i++) {
            TableColumn loColumna = (TableColumn) poTablaReal.getColumns().get(i);
            String lsNombre = getNombreColumna(poTablaReal, i);
            int lLong = (int) loColumna.getWidth();
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
        if (!mbAnularEventos && mbInicializado && moTablaConfigConcreta!=null) {
//            System.out.println("setPropiedadesDeTabla");
            for (int i = 0; moTablaReal!=null && i < moTablaReal.getColumns().size(); i++) {
                TableColumn loColumna = (TableColumn) moTablaReal.getColumns().get(i);
                String lsNombre = getNombreColumna(moTablaReal, i);
                int lLong = (int) loColumna.getWidth();
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

    @Override
    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
        setPropiedadesDeTabla();
    }

    @Override
    public void onChanged(Change change) {
        setPropiedadesDeTabla();
    }

    @Override
    public void handle(MouseEvent t) {
        setPropiedadesDeTabla();
    }    
}


