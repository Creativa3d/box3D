/*
 * JPanelVistaPrevia.java
 *
 * Created on 20 de febrero de 2007, 9:56
 */
package utilesGUIx.imgTrata;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.EventObject;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JDepuracion;
import utiles.JListaElementos;

public class JPanelVistaPreviaMov extends javax.swing.JPanel implements Runnable {

    private transient Thread runner = null;
    private transient Image moImagen;
    private transient Image theCanvas;
    private double mdZoom = 1;
    private transient int mlHeight;
    private transient int mlWidth;
    private transient int mlHeightForm;
    private transient int mlWidthForm;
    private transient int xpos;
    private transient int ypos;
    private int mlZoomLevel = 5;
//    private int mlVelocidad = 6;
    private transient int xMove = 0;
    private transient int yMove = 0;
    private transient boolean mbFinalizar = false;
    private IListaElementos moZoomCambio = new JListaElementos();

    /** Creates new form JPanelVistaPrevia */
    public JPanelVistaPreviaMov() {
        super();
        initComponents();
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    public void setImagen(final Image poImagen) {
        moImagen=poImagen;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        mlWidth = moImagen.getWidth(this);
        mlHeight = moImagen.getHeight(this);

        mlHeightForm = (int) this.getBounds().height;
        mlWidthForm = (int) this.getBounds().width;
        theCanvas = this.createImage(mlWidthForm, mlHeightForm);
//        for (int z = 2; z <= getZoomLevel(); z++){
//            theCanvas.getGraphics().drawImage
//                (moImagen, xpos,
//                 ypos, mlWidth * z,
//                 mlHeight * z, this);
//        }
        xpos = 0;
        ypos = 0;
        this.repaint();

        start();
    }
    public void setImagen(final String psImagen) throws Exception {
        moImagen = JIMGTrata.getIMGTrata().getImagenCargada(psImagen).getImage();
        setImagen(moImagen);

    }

    public void addZoomCambioListener(final IZoomCambioListener l) {
        moZoomCambio.add(l);
    }

    private void llamarListenerZoom() {
        final EventObject loEvent = new EventObject(this);
        for (int i = 0; i < moZoomCambio.size(); i++) {
            ((IZoomCambioListener) moZoomCambio.get(i)).zoomCambio(loEvent);
        }
    }

    public int getZoomLevel() {
        return mlZoomLevel;
    }

    public void setZoomLevel(final int mlZoomLevel) {
        this.mlZoomLevel = mlZoomLevel;
    }

    private int getVelocidadRelativa(final int plX, final int plWidth) {
        int lResult = 5;
        final double ldPorcentaje = (double) plX * 100 / (double) plWidth;
        if (ldPorcentaje > 95 && ldPorcentaje <= 100) {
            lResult = 9;
        }
        if (ldPorcentaje > 90 && ldPorcentaje <= 95) {
            lResult = 7;
        }
        if (ldPorcentaje > 85 && ldPorcentaje <= 90) {
            lResult = 5;
        }
        if (ldPorcentaje > 80 && ldPorcentaje <= 85) {
            lResult = 3;
        }

        if (ldPorcentaje > 15 && ldPorcentaje <= 20) {
            lResult = 3;
        }
        if (ldPorcentaje > 10 && ldPorcentaje <= 15) {
            lResult = 5;
        }
        if (ldPorcentaje > 5 && ldPorcentaje <= 10) {
            lResult = 7;
        }
        if (ldPorcentaje >= 0 && ldPorcentaje <= 5) {
            lResult = 9;
        }

        return lResult;

    }
//    public int getVelocidad() {
//        return mlVelocidad;
//    }
//
//    public void setVelocidad(final int plVelocidad) {
//        this.mlVelocidad = plVelocidad;
//        if(mlVelocidad>10){
//            mlVelocidad=10;
//        }
//        if(mlVelocidad<1){
//            mlVelocidad=1;
//        }
//    }

    public double getZoom() {
        return mdZoom;
    }

    public void setZoom(final double zoom) {
        this.mdZoom = zoom;
        llamarListenerZoom();
    }

    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
        mbFinalizar = false;
    }

    public void stop() {
        mbFinalizar = true;
        runner = null;
    }

    public void run() {
        while (!mbFinalizar) {
            this.repaint();
            synchronized(this){
                try {
                    wait(50L);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    private void setXPos() {
        xpos = xpos + xMove;
        double ldProporcion = (getZoom() - 1.0);
        if (JConversiones.mlComparaDoubles(ldProporcion, 0, .0001) == 0) {
            ldProporcion = 1;
        }
        if (xpos > 0) {
            xpos = 0;
        } else {
            if (xpos < -Math.abs((double) mlWidth * ldProporcion)) {
                xpos = (int) -Math.abs((double) mlWidth * ldProporcion);
            }
        }
        if (mlWidth * getZoom() < mlWidthForm) {
            xpos = 0;
        }
        if (mlWidth * getZoom() >= mlWidthForm) {
            if ((xpos + mlWidth * getZoom()) < mlWidthForm) {
                xpos = (int) ((double) mlWidthForm - (double) mlWidth * getZoom());
            }
        }
    }

    private void setYPos() {
        ypos = ypos + yMove;
        double ldProporcion = (getZoom() - 1.0);
        if (JConversiones.mlComparaDoubles(ldProporcion, 0, .0001) == 0) {
            ldProporcion = 1;
        }
        if (ypos > 0) {
            ypos = 0;
        } else {
            if (ypos < -Math.abs((double) mlHeight * ldProporcion)) {
                ypos = (int) -Math.abs((double) mlHeight * ldProporcion);
            }
        }
        if (mlHeight * getZoom() < mlHeightForm) {
            ypos = 0;
        }
        if (mlHeight * getZoom() >= mlHeightForm) {
            if ((ypos + mlHeight * getZoom()) < mlHeightForm) {
                ypos = (int) ((double) mlHeightForm - (double) mlHeight * getZoom());
            }
        }
    }

    public void paint(final Graphics gg) {
        int lPosiXFinal = 0;
        int lPosiYFinal = 0;
        setXPos();
        setYPos();
        final Graphics g = theCanvas.getGraphics();

        if (mlWidth * getZoom() < mlWidthForm) {
            lPosiXFinal = (int) ((mlWidthForm - mlWidth * getZoom()) / 2);
        }
        if (mlHeight * getZoom() < mlHeightForm) {
            lPosiYFinal = (int) ((mlHeightForm - mlHeight * getZoom()) / 2);
        }
        g.setColor(Color.white);
        g.fillRect(0, 0, mlWidthForm, mlHeightForm);

        g.drawImage(moImagen,
                xpos, ypos,
                (int) (mlWidth * getZoom()), (int) (mlHeight * getZoom()),
                this);

        g.dispose();

        gg.setColor(Color.white);
        gg.fillRect(0, 0, mlWidthForm, mlHeightForm);

        gg.drawImage(theCanvas, lPosiXFinal, lPosiYFinal, this);
    }

    protected void processMouseEvent(final MouseEvent evt) {
        switch (evt.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                setZoom(getZoom() + 1);
                xpos = xpos - mlWidthForm / 2;
                ypos = ypos - mlHeightForm / 2;
                if (getZoom() > getZoomLevel()) {
                    setZoom(1);
                    xpos = 0;
                    ypos = 0;
                }
                break;
            case MouseEvent.MOUSE_EXITED:
                xMove = 0;
                yMove = 0;
                break;

            default:
        }

        super.processMouseEvent(evt);
    }

    protected void processMouseMotionEvent(final MouseEvent evt) {
        switch (evt.getID()) {
            case MouseEvent.MOUSE_MOVED:

                if (evt.getX() > 4 * mlWidthForm / 5) {
                    xMove = (-mlWidthForm / ((11 - getVelocidadRelativa(evt.getX(), mlWidthForm)) * 10));
                } else {
                    if (evt.getX() < mlWidthForm / 5) {
                        xMove = (mlWidthForm / ((11 - getVelocidadRelativa(evt.getX(), mlWidthForm)) * 10));
                    } else {
                        xMove = 0;
                    }
                }

                if (evt.getY() > 4 * mlHeightForm / 5) {
                    yMove = (-mlHeightForm / ((11 - getVelocidadRelativa(evt.getY(), mlHeightForm)) * 10));
                } else {
                    if (evt.getY() < mlHeightForm / 5) {
                        yMove = (mlHeightForm / ((11 - getVelocidadRelativa(evt.getY(), mlHeightForm)) * 10));
                    } else {
                        yMove = 0;
                    }
                }
                break;

            default:
        }

        super.processMouseMotionEvent(evt);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        mlHeightForm = (int) this.getBounds().height;
        mlWidthForm = (int) this.getBounds().width;
        theCanvas = this.createImage(mlWidthForm, mlHeightForm);

    }//GEN-LAST:event_formComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
