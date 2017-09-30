/*
 * JTableModelSimple.java
 *
 * Created on 22 de noviembre de 2004, 12:43
 */

package archivosPorWeb.cliente;


import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;


import utiles.*;
import archivosPorWeb.comun.JFichero;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utilesGUIx.JTableModel;

/**Para tablas simples y sin edición*/
public class JTableModelArchivos  extends AbstractTableModel implements TableModelListener{
  private static final int mclNombre = 0;
  private static final int mclTamano = 1;
  private static final int mclesDirectorio = 2;
  /**Lista de def. de campos*/  
  public JFieldDefs moCampos;
  /**Lista de datos*/
  public IListaElementos moLista;
   /**
    * Constructor
    * @param poCampos Lista de def. de campos
    * @param poLista Lista de datos
    */
  public JTableModelArchivos(IListaElementos poLista){
        moCampos = new JFieldDefs();
        moCampos.addField(new JFieldDef(JListDatos.mclTipoCadena, "Nombre", "Nombre", true));
        moCampos.addField(new JFieldDef(JListDatos.mclTipoNumero, "Tamaño (kb)", "Tamaño (kb)", false));
        moCampos.addField(new JFieldDef(JListDatos.mclTipoBoolean, "Es directorio", "Es directorio", false));
        setDatos(poLista);
  }
  
  public void setDatos(IListaElementos poLista){
        moLista = poLista;
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
        JFichero loFichero = (JFichero)moLista.get(row);
        switch(col){
            case mclNombre:
                loValor = loFichero.getNombre();
                break;
            case mclTamano:
                loValor = new Long(loFichero.getLenght() / (long)1024 );
                break;
            case mclesDirectorio:
                loValor = new Boolean(loFichero.getEsDirectorio());
                break;
        }
    }catch(Exception e){
        e.printStackTrace();
        loValor = null;
    }
    return loValor;
  }

  public Class getColumnClass(int c) {
    return moCampos.get(c).getClase();
  }
  public void tableChanged(TableModelEvent e) {
    fireTableChanged(e);
  }
  public void sortByColumn(int plColumn, boolean pbAscendente){
      try{
          JListDatos loDatos = new JListDatos();
          loDatos.setFields(new JFieldDefs());
          loDatos.getFields().addField(moCampos.get(0));
          loDatos.getFields().addField(moCampos.get(1));
          loDatos.getFields().addField(moCampos.get(2));
          loDatos.getFields().addField(new JFieldDef("numero"));
          for(int i =0; i<moLista.size();i++ ){
              JFichero loFichero = (JFichero)moLista.get(i);
              loDatos.addNew();
              loDatos.getFields(mclNombre).setValue(loFichero.getNombre());
              loDatos.getFields(mclTamano).setValue(loFichero.getLenght() / (long)1024);
              loDatos.getFields(mclesDirectorio).setValue(loFichero.getEsDirectorio());
              loDatos.getFields(3).setValue(i);
              loDatos.update(false);
          }
          loDatos.ordenar(plColumn);
          
          IListaElementos loLista = new JListaElementos(moLista.size());
          if(loDatos.moveFirst()){
              do{
                  loLista.add(moLista.get(loDatos.getFields(3).getInteger()));
              }while(loDatos.moveNext());
          }
          moLista = loLista;
          
      }catch(Exception e){
          e.printStackTrace();
      }
//    moLista.ordenar(new int[]{plColumn}, pbAscendente);
    tableChanged(new TableModelEvent(this));
  }
  public void addMouseListenerToHeaderInTable(JTable table) {
      final JTableModelArchivos sorter = this;
      final JTable tableView = table;
      tableView.setColumnSelectionAllowed(false);
      MouseAdapter listMouseListener = new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
              TableColumnModel columnModel = tableView.getColumnModel();
              int viewColumn = columnModel.getColumnIndexAtX(e.getX());
              int column = tableView.convertColumnIndexToModel(viewColumn);
              if (e.getClickCount() == 1 && column != -1) {
                  int shiftPressed = e.getModifiers()&InputEvent.SHIFT_MASK;
                  boolean ascending = (shiftPressed == 0);

                  sorter.sortByColumn(column, ascending);
              }
          }
      };
      JTableHeader th = tableView.getTableHeader();
      th.addMouseListener(listMouseListener);
  }

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
