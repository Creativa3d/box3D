package utilesGUIxAvisos.forms.util;

import java.awt.Color;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import utiles.FechaMalException;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesGUI.tabla.ITabla;
import utilesGUIxAvisos.consultas.JTFORMGUIXEVENTOS;

/**
 * Datos para una tabla
 */
public class JTableModelEVENTOSDIA extends AbstractTableModel implements TableModelListener, ITabla {

    /**
     * Objeto fuente de datos
     */
    public JTFORMGUIXEVENTOS moEventos;
    private final JDateEdu moDate;
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
     *
     * @param poList Datos
     */
    public JTableModelEVENTOSDIA(JTFORMGUIXEVENTOS poEventos, JDateEdu poDate) throws CloneNotSupportedException {
        super();
        moEventos = new JTFORMGUIXEVENTOS(poEventos.moList.moServidor);
        moEventos.moList = poEventos.moList.Clone();
        moDate = (JDateEdu) poDate.clone();
        moDate.setHora(0);
        moDate.setMinuto(0);
        moDate.setSegundo(0);
    }

    /**
     * Devuelve el numero de columnas
     *
     * @return Número columnas
     */
    public int getColumnCount() {
        return 1;
    }
    
    private boolean isIntervaloCorrecto() throws FechaMalException{
        JDateEdu loDesde = moEventos.getField(JTFORMGUIXEVENTOS.lPosiFECHADESDE).getDateEdu();
        loDesde.setHora(0);
        loDesde.setMinuto(0);
        loDesde.setSegundo(0);
        JDateEdu loHasta = moEventos.getField(JTFORMGUIXEVENTOS.lPosiFECHAHASTA).getDateEdu();
        loHasta.setHora(0);
        loHasta.setMinuto(0);
        loHasta.setSegundo(0);

        //fecha desde
        boolean lbResult = loDesde.compareTo(moDate)==0;
        //intervalo
        if(!lbResult){
            lbResult = loDesde.compareTo(moDate)<0 &&
                        loHasta.compareTo(moDate)>=0;
        }
        //final
        if(!lbResult){
            lbResult = loHasta.compareTo(moDate)==0;
        }
        
        return lbResult ;

    }

    /**
     * Devuelve el numero de filas
     *
     * @return número de filas
     */
    @Override
    public int getRowCount() {
        int lSize = 0;
        if (moEventos.moveFirst()) {
            try {
//                loDateHasta.add(JDateEdu.mclDia, +1);
                do {
                    if (isIntervaloCorrecto()) {
                        lSize++;
                    }
                } while (moEventos.moveNext());
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }

        return lSize;

    }

    /**
     * Devuelve el nombre de la columna
     *
     * @return nombre
     * @param col columna
     */
    @Override
    public String getColumnName(int col) {
        return "";
    }

    /**
     * devuelve el valor de la celda
     *
     * @param row fila
     * @param col columna
     * @return valor de la celda
     */
    @Override
    public Object getValueAt(int row, int col) {
        int lSize = -1;
        if (moEventos.moveFirst()) {
            try {
                do {
                    if (isIntervaloCorrecto()) {
                        lSize++;
                    }
                    if (lSize == row) {
                        return getEventoParam();
                    }
                } while (moEventos.moveNext() && lSize!=row);
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
        return null;
    }

    /**
     * devuelve la clase de la columna
     *
     * @return clase de los datos de la columna
     * @param c índice de la columna
     */
    @Override
    public Class getColumnClass(int c) {
        return JTableRenderEventoParam.class;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        fireTableChanged(e);
    }

    /**
     * indica si la celda es editble
     *
     * @param row fila
     * @param col columna
     * @return si es editable
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Establece el valor de la celda
     *
     * @param value valor
     * @param row fila
     * @param col columna
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
    }

    /**
     * Devolvemos el componente
     *
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
