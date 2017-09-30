/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

/**
 *
 * @author eduardo
 */
public class ActionEventCZ {
    private String actionCommand;
    private Object source;
    private int algo;
    
    public ActionEventCZ(Object poSource, int plalgo, String psAccion){
        source = poSource;
        actionCommand=psAccion;
        algo=plalgo;
    }
    /**
     * @return the actionCommand
     */
    public String getActionCommand() {
        return actionCommand;
    }

    /**
     * @param actionCommand the actionCommand to set
     */
    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
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
