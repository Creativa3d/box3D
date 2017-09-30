package utilesGUI;
/*
 * @(#)OpenlookButton.java	1.2 97/01/14 Jeff Dinkins
 *
 * Copyright (c) 1995-1997 Sun Microsystems, Inc. All Rights Reserved.
 *
 */

import java.awt.*;
import java.awt.event.*;

/**
 * OpenlookButton - a class that produces a lightweight button.
 *
 * Lightweight components can have "transparent" areas, meaning that
 * you can see the background of the container behind them.
 *
 */
public class ButtonCZ extends Component {

    //nombre de los controles por diseño
  private static final String base = "ButtonCZ";
  private static int nameCounter = 0;
  private static int capWidth = 15;          // The width of the Button's endcap
  private String label;                      // The Button's text
  private boolean pressed = false; // true if the button is detented.
  private ActionListener actionListener;     // Post action events to listeners
  private FocusListener focusListener;
  /**Acción*/
  public String actionCommand="";
  private boolean mbRatonEncima=false;
  private Color moColorEncima = Color.blue;

  /**
   * Constructs an OpenlookButton with no label.
   */
  public ButtonCZ() {
      this("");
      setName(base + nameCounter++);
  }

  /**
   * Constructs an OpenlookButton with the specified label.
   * @param label the label of the button
   */
  public ButtonCZ(String label) {
      super();
      this.label = label;
      enableEvents(AWTEvent.MOUSE_EVENT_MASK);
      enableEvents(AWTEvent.FOCUS_EVENT_MASK);
      enableEvents(AWTEvent.KEY_EVENT_MASK);
      enableEvents(AWTEvent.ACTION_EVENT_MASK);
      enableEvents(AWTEvent.FOCUS_EVENT_MASK);
      setCursor( new Cursor( Cursor.HAND_CURSOR ) );
  }

  /**
   * gets the label
   * @see setLabel
   * @return cadena
   */
  public String getLabel() {
      return label;
  }
  /**
   * Color cuando ratón pasa por encima
   * @param poColor color
   */
  public void setForeColorEncima(Color poColor){
      moColorEncima = poColor;
  }
  /**
   * Color cuando ratón pasa por encima
   * @return color
   */
  public Color getForeColorEncima(){
      return moColorEncima;
  }

  /**
   * sets the label
   * @see getLabel
   * @param label label
   */
  public void setLabel(String label) {
      this.label = label;
      invalidate();
      repaint();
  }

  /**
   * paints the button
   */
  public void paint(Graphics g) {
      int width = getSize().width - 1;
      int height = getSize().height - 1;

      Color interior;
      Color highlight1;
      Color highlight2;

      interior = getBackground();

      // ***** determine what colors to use
      if(pressed) {
	  highlight1 = interior.darker();
	  highlight2 = interior.brighter();
      } else {
	  highlight1 = interior.brighter();
	  highlight2 = interior.darker();
      }

      // ***** paint the interior of the button
      g.setColor(interior);
      // left cap
      g.fillArc(0, 0, 			    // start
		capWidth, height,    	    // size
		90, 180);		    // angle

      // right cap
      g.fillArc(width - capWidth, 0,        // start
		capWidth, height,           // size
		270, 180);		    // angle

      // inner rectangle
      g.fillRect(capWidth/2, 0, width - capWidth, height);


      // ***** highlight the perimeter of the button
      // draw upper and lower highlight lines
      g.setColor(highlight1);
      g.drawLine(capWidth/2, 0, width - capWidth/2, 0);
      g.setColor(highlight2);
      g.drawLine(capWidth/2, height, width - capWidth/2, height);

      // upper arc left cap
      g.setColor(highlight1);
      g.drawArc(0, 0,                       // start
                capWidth, height,           // size
                90, 180-60                  // angle
      );

      // lower arc left cap
      g.setColor(highlight2);
      g.drawArc(0, 0,                       // start
                capWidth, height,           // size
                270-60, 60                  // angle
      );

      // upper arc right cap
      g.setColor(highlight1);
      g.drawArc(width - capWidth, 0,        // start
                capWidth, height,           // size
                90-60, 60                   // angle
      );

      // lower arc right cap
      g.setColor(highlight2);
      g.drawArc(width - capWidth, 0,        // start
                capWidth, height,           // size
                270, 180-40                 // angle
      );

      // ***** draw the label centered in the button
      Font f = getFont();
      if(f != null) {
	  FontMetrics fm = getFontMetrics(getFont());
          if (mbRatonEncima){
            g.setColor(moColorEncima);
          }else{
            g.setColor(getForeground());
          }

	  g.drawString(label,
		       width/2 - fm.stringWidth(label)/2,
		       height/2 + fm.getHeight()/2 - fm.getMaxDescent()
	  );
      }
  }

  /**
   * The preferred size of the button.
   */
  public Dimension getPreferredSize() {
        Font f = getFont();
        Dimension loDimension = new Dimension(20, 25);
        if(f != null) {
          FontMetrics fm = getFontMetrics(getFont());
          loDimension = new Dimension(fm.stringWidth(label) + capWidth*2,
                               fm.getHeight() + 10);
        }
        return loDimension;
  }

  /**
   * The minimum size of the button.
   */
  public Dimension getMinimumSize() {
      return getPreferredSize();
  }

  /**
   * Adds the specified action listener to receive action events
   * from this button.
   * @param listener the action listener
   */
   public void addActionListener(ActionListener listener) {
       actionListener = AWTEventMulticaster.add(actionListener, listener);
       enableEvents(AWTEvent.MOUSE_EVENT_MASK);
       enableEvents(AWTEvent.ACTION_EVENT_MASK);
   }
   public void addFocusListener(FocusListener listener) {
       focusListener = AWTEventMulticaster.add(focusListener, listener);
       enableEvents(AWTEvent.FOCUS_EVENT_MASK);
   }

   /**
    * Removes the specified action listener so it no longer receives
    * action events from this button.
    * @param listener the action listener
    */
   public void removeActionListener(ActionListener listener) {
       actionListener = AWTEventMulticaster.remove(actionListener, listener);
   }
   public void removeFocusListener(FocusListener listener) {
       focusListener = AWTEventMulticaster.remove(focusListener, listener);
   }

   /**
    * Paints the button and sends an action event to all listeners.
    */
   public void processMouseEvent(MouseEvent e) {
       switch(e.getID()) {
          case MouseEvent.MOUSE_PRESSED:
            // render myself inverted....
            pressed = true;
            this.requestFocus();
	    // Repaint might flicker a bit. To avoid this, you can use
	    // double buffering (see the Gauge example).
	    repaint();
            break;
          case MouseEvent.MOUSE_RELEASED:
            if(actionListener != null) {
               actionListener.actionPerformed(new ActionEvent(
                   this, ActionEvent.ACTION_PERFORMED, (actionCommand.compareTo("")==0) ? label :actionCommand ));
            }
            // render myself normal again
            if(pressed ) {
                pressed = false;

	        // Repaint might flicker a bit. To avoid this, you can use
	        // double buffering (see the Gauge example).
		repaint();
            }
            break;
          case MouseEvent.MOUSE_ENTERED:
            if (!mbRatonEncima){
              mbRatonEncima=true;
              repaint();
            }
            break;
          case MouseEvent.MOUSE_EXITED:
            mbRatonEncima=false;
            // Cancel! Don't send action event.
            pressed = false;

            // Repaint might flicker a bit. To avoid this, you can use
            // double buffering (see the Gauge example).
            repaint();

            // Note: for a more complete button implementation,
            // you wouldn't want to cancel at this point, but
            // rather detect when the mouse re-entered, and
            // re-highlight the button. There are a few state
            // issues that that you need to handle, which we leave
            // this an an excercise for the reader (I always
            // wanted to say that!)
            break;
           default:
       }
       super.processMouseEvent(e);
   }
//   public void processFocusEvent(FocusEvent e){
//        int id = e.getID();
//        switch(id) {
//          case FocusEvent.FOCUS_GAINED:
////              if (listener != null) {
////              }
//            break;
//          case FocusEvent.FOCUS_LOST:
////              if (listener != null) {
////              }
//            break;
//           
//        }
//        super.processFocusEvent(e);
//   }

   public void processKeyEvent(KeyEvent e){
        if((e.getKeyCode() == '\n')||(e.getKeyCode() == '\b')||(e.getKeyCode() == ' ')){
            int id = e.getID();
            switch(id) {
              case KeyEvent.KEY_PRESSED:
                // render myself inverted....
                pressed = true;

                // Repaint might flicker a bit. To avoid this, you can use
                // double buffering (see the Gauge example).
                repaint();
                break;
              case KeyEvent.KEY_RELEASED:
                if(actionListener != null) {
                   actionListener.actionPerformed(new ActionEvent(
                       this, ActionEvent.ACTION_PERFORMED, (actionCommand.compareTo("") == 0) ? label :actionCommand ));
                }
                // render myself normal again
                if(pressed) {
                    pressed = false;

                    // Repaint might flicker a bit. To avoid this, you can use
                    // double buffering (see the Gauge example).
                    repaint();
                }
                break;
              default:
            }
       }
        super.processKeyEvent(e);
   }

}


