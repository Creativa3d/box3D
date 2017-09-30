/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

/**
 *
 * @author eduardo
 */
public class ColorCZ {
    
    public static final ColorCZ BLACK = new ColorCZ(0);
    public static final ColorCZ RED = new ColorCZ(-65536);
    public static final ColorCZ RED_DARK = new ColorCZ(-10223616);
    
    public static final ColorCZ YELLOW = new ColorCZ(-256);
    public static final ColorCZ YELLOW_DARK = new ColorCZ(-5147392);
    
    public static final ColorCZ GREEN = new ColorCZ(-16711936);
    public static final ColorCZ GREEN_DARK = new ColorCZ(-16751616);
    
    public static final ColorCZ CYAN = new ColorCZ(-16711681);
    public static final ColorCZ ORANGE = new ColorCZ(-14336);
    public static final ColorCZ WHITE = new ColorCZ(-1);
    public static final ColorCZ BLUE = new ColorCZ(-16776961);
    public static final ColorCZ BLUE_DARK = new ColorCZ(-16777116);
    
    
    private int mlColor;

    public ColorCZ(int plColor){
        mlColor=plColor;
    }
    /**
     * @return the mlColor
     */
    public int getColor() {
        return mlColor;
    }

    /**
     * @param mlColor the mlColor to set
     */
    public void setColor(int mlColor) {
        this.mlColor = mlColor;
    }
}
