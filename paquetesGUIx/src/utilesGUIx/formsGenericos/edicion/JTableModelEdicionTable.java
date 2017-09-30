/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos.edicion;

import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import java.awt.event.ActionListener;
import utilesGUI.tabla.ElementoActualizado;
import utilesGUIx.JTableModel;

/**
 *
 * @author eduardo
 */
public class JTableModelEdicionTable  extends JTableModel {
    private ActionListener moNotificar;
    private final JSTabla moLineas;
    
    /** Creates a new instance of JTableModelTickets */
    public JTableModelEdicionTable(JSTabla poLineas) {
        super(poLineas.moList);
        moLineas=poLineas;
    }
    public void setNotificarCambios(ActionListener poNotificar){
        moNotificar = poNotificar;
    }
    public boolean isCellEditable(int row, int col) {
      return mbEditable;
             
    }
    public void setValueAt(Object value, int row, int col) {
      try{
        moList.setIndex(row);
        if(row>=moList.size()){
            moList.addNew();
            moLineas.valoresDefecto();
        }
        moList.getFields().get(col).setValue(value);
        IResultado loResul = moList.update(mbActualizarServidor);
        if (loResul.getBien()) {
            if (mbLlevarCeldasActualizadas) {
                moListaActualizados.add(new ElementoActualizado(row, col, value));
            }
        } else {
            throw new Exception(loResul.getMensaje());
        }
        fireTableCellUpdated(row, col);
        if(moNotificar!=null){
            moNotificar.actionPerformed(null);
        }
      }catch(Exception e){
        utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(new utilesGUIx.JLabelCZ(), e, this.getClass().getName());
      }
      
  }
    public int getRowCount() {
      return moList.size() + 1;
    }
    public Object getValueAt(int row, int col) {
        if(row >= moList.size()){
            return null;
        }else{
            moList.setIndex(row);
            Object loValor=null;
            loValor=moList.getFields().get(col).getValue();
            return loValor;
        }
    }
}

