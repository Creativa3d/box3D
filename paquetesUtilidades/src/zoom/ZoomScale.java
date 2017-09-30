/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zoom;

// ZoomScale.java
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ZoomScale {
    public static void main(String args[]) {
        JPanel cp = new JPanel(new BorderLayout());

        ZoomPanel zp = new ZoomPanel(1.0);
        JButton zoomIn = new JButton("Zoom In");
        JButton zoomOut = new JButton("Zoom Out");
        zoomIn.addActionListener(new ZoomAction(zp, 0.4));
        zoomOut.addActionListener(new ZoomAction(zp, -0.4));
        zp.setBorder
           (BorderFactory.createTitledBorder("This stuff zooms"));
        zp.add(zoomIn);
        zp.add(zoomOut);
        
        cp.add(new JScrollPane(zp), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        zoomIn = new JButton("Zoom In");
        zoomOut = new JButton("Zoom Out");
        zoomIn.addActionListener(new ZoomAction(zp, 0.4));
        zoomOut.addActionListener(new ZoomAction(zp, -0.4));
        buttonPanel.add(zoomIn);
        buttonPanel.add(zoomOut);

        JLabel readoutLabel = new JLabel("Zoom Factor 1.0");
        new ReadoutUpdater(zp, readoutLabel);
        southPanel.add(readoutLabel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        cp.add(southPanel, BorderLayout.SOUTH);

        final JFrame frame = new JFrame("Zoom example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(cp);
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    frame.pack();
                    frame.setVisible(true);
                }
            });
    }

    public static class ZoomPanel extends JPanel {
        protected double zoom;

        public ZoomPanel(double initialZoom) {
            super(new FlowLayout());
            setName("Zoom Panel");
            zoom = initialZoom;
        }

        public void paint(Graphics g) {
            super.paintComponent(g); // clears background
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform backup = g2.getTransform();
            g2.scale(zoom, zoom);
            super.paint(g);
            g2.setTransform(backup);
        }

        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void paintAll(Graphics g) {
            super.paintAll(g); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void paintComponents(Graphics g) {
            super.paintComponents(g); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void paintImmediately(Rectangle r) {
            super.paintImmediately(r); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void paintImmediately(int x, int y, int w, int h) {
            super.paintImmediately(x, y, w, h); //To change body of generated methods, choose Tools | Templates.
        }
        

        public boolean isOptimizedDrawingEnabled() {
            return false;
        }

        public Dimension getPreferredSize() {
            Dimension unzoomed
              = getLayout().preferredLayoutSize(this);
            Dimension zoomed
              = new Dimension((int) ((double) unzoomed.width*zoom),
                              (int) ((double) unzoomed.height*zoom));
            System.out.println("PreferredSize: Unzoomed "+unzoomed);
            System.out.println("PreferredSize: Zoomed "+zoomed);
            return zoomed;
        }
        
        public void setZoom(double newZoom)
            throws PropertyVetoException {
            if (newZoom <= 0.0) {
                throw new PropertyVetoException
                    ("Zoom must be positive-valued",
                     new PropertyChangeEvent(this,
                                             "zoom",
                                             new Double(zoom),
                                             new Double(newZoom)));
            }
            double oldZoom = zoom;
            if (newZoom != oldZoom) {
                Dimension oldSize = getPreferredSize();
                zoom = newZoom;
                Dimension newSize = getPreferredSize();
                firePropertyChange("zoom", oldZoom, newZoom);
                firePropertyChange("preferredSize",
                                   oldSize, newSize);
                revalidate();
                repaint();
            }
        }

        public double getZoom() {
            return zoom;
        }
    }

    public static class ZoomAction implements ActionListener {

        protected double amount;
        protected ZoomPanel zp;

        public ZoomAction(ZoomPanel zp, double amount) {
            this.amount = amount;
            this.zp = zp;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                zp.setZoom(zp.getZoom() + amount);
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog
                    ((Component) e.getSource(),
                     "Couldn't change zoom: "+ex.getMessage());
            }
        }
    }

    public static class ReadoutUpdater
        implements PropertyChangeListener {

        protected ZoomPanel zp;
        protected JLabel label;

        public ReadoutUpdater(ZoomPanel zp, JLabel label) {
            this.zp = zp;
            this.label = label;
            zp.addPropertyChangeListener(this);
        }

        public void propertyChange(PropertyChangeEvent e) {
            if ("zoom".equals(e.getPropertyName())) {
                label.setText("Zoom Factor "+e.getNewValue());
            }
        }
    }
} 