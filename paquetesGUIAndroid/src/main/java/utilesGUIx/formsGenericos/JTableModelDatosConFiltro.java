/*
 * JTableModelDatosConFiltro.java
 *
 * Created on 5 de septiembre de 2005, 13:52
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesGUIx.formsGenericos;


import utilesGUIx.*;
import ListDatos.*;
import android.content.Context;

/**Datos para una tabla*/
public class JTableModelDatosConFiltro  extends JTableModel {
    public JTableModelFiltro moFiltro;
    private boolean mbEnabled = true;
    
  /**
   * Contructor
   * @param poList Datos
   */
    public JTableModelDatosConFiltro (JListDatos poList, JTableModelFiltro poFiltro){
        super(poList);
        moFiltro = poFiltro;
        mbEditable  =true;
        mbActualizarServidor=false;
    }

   /**
     * Establece el valor de la celda
     * @param value valor
     * @param row fila
     * @param col columna
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
      try{
          if(isEnabled()){
                moList.setIndex(row);
                if(value.toString().compareTo(moList.getFields().get(col).getString())!=0){
                    boolean lbEncon = false;
                    for(int i = 0; (i < moFiltro.getRowCount())&&(!lbEncon) ; i++){
                        String lsNombre = moFiltro.getValueAt(i, JTableModelFiltro.mclNombre).toString().toUpperCase();
                        if(getColumnName(col).toUpperCase().equals(lsNombre)){
                          moFiltro.setValueAt(value, i, JTableModelFiltro.mclValor);
                          lbEncon=true;
                        }
                    }
                }
          }
      }catch(Exception e){
          utilesGUIx.msgbox.JMsgBox.mensajeError(
                  (Context)JGUIxConfigGlobal.getInstancia().getMostrarPantalla().getContext()
                  , e);
      }
  }

    /**
     * @return the mbEnabled
     */
    public boolean isEnabled() {
        return mbEnabled;
    }

    /**
     * @param mbEnabled the mbEnabled to set
     */
    public void setEnabled(boolean mbEnabled) {
        this.mbEnabled = mbEnabled;
    }

    
}