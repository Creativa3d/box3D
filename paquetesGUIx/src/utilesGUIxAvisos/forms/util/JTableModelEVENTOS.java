package utilesGUIxAvisos.forms.util;

import java.awt.Color;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import utiles.FechaMalException;
import utiles.JDepuracion;
import utilesGUI.tabla.ITabla;
import utilesGUIxAvisos.consultas.JTFORMGUIXEVENTOS;

/**Datos para una tabla*/
public class JTableModelEVENTOS extends AbstractTableModel implements TableModelListener, ITabla {

    /**
     *Objeto fuente de datos
     */
    public JTFORMGUIXEVENTOS moEventos;
    private JTableRenderEventoParam moRender=new JTableRenderEventoParam();

    public JTableRenderEventoParam getEventoParam() {
        try {
            moRender.setCodigo(moEventos.getField(moEventos.lPosiCODIGO).getString());
            moRender.setCalendario(moEventos.getField(moEventos.lPosiCALENDARIO).getString());
            moRender.setAviso(moEventos.getField(moEventos.lPosiEVENTOSN).getBoolean());
            moRender.setFechaDesde(moEventos.getField(moEventos.lPosiFECHADESDE).getDateEdu());
            moRender.setFechaHasta(moEventos.getField(moEventos.lPosiFECHAHASTA).getDateEdu());
            moRender.setNombre(moEventos.getField(moEventos.lPosiNOMBRE).getString());
            moRender.setTexto(moEventos.getField(moEventos.lPosiTEXTO).getString());
            moRender.setPrioridad(new Color(moEventos.getField(moEventos.lPosiPRIORIDADCOLOR).getInteger()));
            
            return moRender;
        } catch (FechaMalException ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        return null;
    }

    /**
     * Contructor
     * @param poList Datos
     */
    public JTableModelEVENTOS(JTFORMGUIXEVENTOS poList) {
        super();
        moEventos = poList;
    }

    /**
     * Devuelve el numero de columnas
     * @return Número columnas
     */
    public int getColumnCount() {
        return 1;
    }

    /**
     * Devuelve el numero de filas
     * @return número de filas
     */
    public int getRowCount() {

            return moEventos.moList.size();
        
    }

    /**
     * Devuelve el nombre de la columna
     * @return nombre
     * @param col columna
     */
    public String getColumnName(int col) {
        return "";
    }

    /**
     * devuelve el valor de la celda
     * @param row fila
     * @param col columna
     * @return valor de la celda
     */
    public Object getValueAt(int row, int col) {
        if(row >= moEventos.moList.size()){
            return null;
        }else{
            if (moEventos.moList.getIndex() != row) {
                moEventos.moList.setIndex(row);
            }
            return getEventoParam();
        }
    }

    /**
     * devuelve la clase de la columna
     * @return clase de los datos de la columna
     * @param c índice de la columna
     */
    public Class getColumnClass(int c) {
        return JTableRenderEventoParam.class;
    }

    public void tableChanged(TableModelEvent e) {
        fireTableChanged(e);
    }


    /**
     * indica si la celda es editble
     * @param row fila
     * @param col columna
     * @return si es editable
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Establece el valor de la celda
     * @param value valor
     * @param row fila
     * @param col columna
     */
    public void setValueAt(Object value, int row, int col) {
       
    }

    /**
     * Devolvemos el componente
     * @return Componente
     * @param row fila
     * @param col columna
     */
    public java.awt.Component getComponent(int row, int col) {
        return null;
    }

    @Override
    public void sortByColumn(int plColumn, boolean pbAscendente) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
