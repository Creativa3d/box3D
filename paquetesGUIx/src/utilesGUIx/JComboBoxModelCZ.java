/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

import java.io.Serializable;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;
import utiles.IListaElementos;
import utiles.JListaElementos;

/**
 *
 * @author eduardo
 */
public class JComboBoxModelCZ extends AbstractListModel  implements MutableComboBoxModel, Serializable {
    private JListaElementos moList = new JListaElementos();
    private Object selectedObject;
    public int getSize() {
        return moList.size();
    }

    public Object getElementAt(int index) {
       if ( index >= 0 && index < moList.size() )
            return moList.get(index);
       else
            return null;
    }

    public void addElement(Object obj) {
        moList.add(obj);
        try{
        fireIntervalAdded(this,moList.size()-1, moList.size()-1);
        }catch(Throwable e){}
        if ( moList.size() == 1 && selectedObject == null && obj != null ) {
            setSelectedItem( obj );
        }
    }

    public void removeElement(Object obj) {
        int index = moList.indexOf(obj);
        if ( index != -1 ) {
            removeElementAt(index);
        }
    }

    public void insertElementAt(Object obj, int index) {
        moList.insertElementAt(obj,index);
        try{
        fireIntervalAdded(this, index, index);
        }catch(Throwable e){}
    }

    public void removeElementAt(int index) {
        if ( getElementAt( index ) == selectedObject ) {
            if ( index == 0 ) {
                setSelectedItem( getSize() == 1 ? null : getElementAt( index + 1 ) );
            }
            else {
                setSelectedItem( getElementAt( index - 1 ) );
            }
        }

        moList.remove(index);

        try{
        fireIntervalRemoved(this, index, index);
        }catch(Throwable e){}
    }

    public void setSelectedItem(Object anObject) {
        if ((selectedObject != null && !selectedObject.equals( anObject )) ||
	    selectedObject == null && anObject != null) {
	    selectedObject = anObject;
        try{
	    fireContentsChanged(this, -1, -1);
        }catch(Throwable e){}
        }
    }

    // implements javax.swing.ComboBoxModel
    public Object getSelectedItem() {
        return selectedObject;
    }

}
