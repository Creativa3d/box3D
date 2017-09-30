/*
 * JTableModelSimple.java
 *
 * Created on 22 de noviembre de 2004, 12:43
 */

package utilesGUIx;


import javax.swing.table.AbstractTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


import utiles.*;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUI.tabla.*;

/**Para tablas simples y sin edición*/
public class JTableModelSimple  extends AbstractTableModel implements TableModelListener, ITabla {
  /**Lista de def. de campos*/  
  public JFieldDefs moCampos;
  /**Lista de datos*/
  public JListaElementos moLista;
  private int mlUltimaFilaCargada=-1;
   /**
    * Constructor
    * @param poCampos Lista de def. de campos
    * @param poLista Lista de datos
    */
  public JTableModelSimple(JFieldDefs poCampos, JListaElementos poLista){
      super();
    moLista = poLista;
    moCampos = poCampos;
  }

  public int getColumnCount() {
      return moCampos.count();
  }

  public int getRowCount() {
      return moLista.size();
  }

  public String getColumnName(int col) {
      return moCampos.get(col).getCaption();
  }

  public Object getValueAt(int row, int col) {
    Object loValor = null;  
    try{
        if(mlUltimaFilaCargada!=row){
            mlUltimaFilaCargada=row;
            moCampos.cargar((IFilaDatos)moLista.get(row));
        }
        loValor = moCampos.get(col).getValue();
    }catch(Exception e){
        JDepuracion.anadirTexto(this.getClass().getName(), e);
        loValor = null;
    }
    return loValor;
  }

  public Class getColumnClass(int c) {
    return moCampos.get(c).getClase();
  }
  public void tableChanged(final TableModelEvent e) {
    fireTableChanged(e);
  }
  public void sortByColumn(final int plColumn, final boolean pbAscendente){
//    moLista.ordenar(new int[]{plColumn}, pbAscendente);
//    tableChanged(new TableModelEvent(this));
  }
//  public void addMouseListenerToHeaderInTable(JTable table) {
//      final JTableModel sorter = this;
//      final JTable tableView = table;
//      tableView.setColumnSelectionAllowed(false);
//      MouseAdapter listMouseListener = new MouseAdapter() {
//          public void mouseClicked(MouseEvent e) {
//              TableColumnModel columnModel = tableView.getColumnModel();
//              int viewColumn = columnModel.getColumnIndexAtX(e.getX());
//              int column = tableView.convertColumnIndexToModel(viewColumn);
//              if (e.getClickCount() == 1 && column != -1) {
//                  int shiftPressed = e.getModifiers()&InputEvent.SHIFT_MASK;
//                  boolean ascending = (shiftPressed == 0);
//
//                  sorter.sortByColumn(column, ascending);
//              }
//          }
//      };
//      JTableHeader th = tableView.getTableHeader();
//      th.addMouseListener(listMouseListener);
//  }

  /*
   * indica si la celda es editble
   */
   public boolean isCellEditable(int row, int col) {
      return false;
  }

  /*
   * Establece el valor de la celda
   */
    public void setValueAt(Object value, int row, int col) {
//      try{
//        moLista.setIndex(row);
//        moCampos.get(col).setValue(value);
//        IResultado loResul = moList.update(mbActualizarServidor);
//        if (!loResul.mbBien) utilesGUI.msgbox.JDialogo.showDialog(null, loResul.toString());
//        fireTableCellUpdated(row, col);
//      }catch(Exception e){
//        utilesGUI.msgbox.JDialogo.showDialog(null, e.toString());
//      }
  }

    public java.awt.Component getComponent(int row, int col) {
        return null;
    }
    
}
