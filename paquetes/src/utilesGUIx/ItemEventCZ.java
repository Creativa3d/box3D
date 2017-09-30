/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

/**
 *
 * @author eduardo
 */
public class ItemEventCZ {
    private Object source;
    private int id;
    private Object item;
    private int stateChange;
    
    public ItemEventCZ (Object source, int id, Object item, int stateChange    ){
        this.source=source;
        this.id = id;
        this.item = item;
        this.stateChange = stateChange;
    
    }
    
    public Object getItem() {
        return item;
    }
    public int getStateChange() {
        return stateChange;
    }
    public int getID() {
        return id;
    }
    /**
     * @return the source
     */
    public Object getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Object source) {
        this.source = source;
    }
    
    
}
