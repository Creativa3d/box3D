/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import android.view.View;
import utilesGUI.tabla.ITabla;

/**
 *
 * @author eduardo
 */
public interface ITableCZ {

    public void addOnClickListenerCELL(View.OnClickListener onClickListener);

    public void addOnLongClickListenerCELL(View.OnLongClickListener onLongClickListener);

    public void setTableCZColores(ITableCZColores coloresTabla);

    public void setModel(ITabla moModeloDatos)throws Exception ;

    public void setColumnLong(int i, int i0);

    public void refrescarDatos()throws Exception ;

    public void limpiar();

    public int[] getSelectedRows() ;

    public int getSelectedRow() ;
    
}
