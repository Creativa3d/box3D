package JavaZoneEspiaWeb;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelCamara extends JPanel {

    String url = "";
    int id=0;
    Image img;
    Principal prin;
    HiloFoto hf;
    boolean seleccionado=false;

    public PanelCamara(int id,String url, Principal p) {
        this.url = url;
        this.prin=p;
        this.id=id;
        addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(seleccionado){
					seleccionado=false;
				}else{
					seleccionado=true;
				}
				if(seleccionado){
					prin.DesactivarHilos();
					hf=new HiloFoto(PanelCamara.this);
			        hf.start();
					prin.panelPrincipal.removeAll();
					prin.panelPrincipal.setLayout(new BorderLayout());
					prin.panelPrincipal.add(prin.camaras.get(PanelCamara.this.id));
					prin.setBounds(0, 0, 500, 400);
					prin.setLocationRelativeTo(null);
				}else{
					hf.stop();
					hf=null;
					prin.panelPrincipal.removeAll();
					prin.panelPrincipal.setLayout(new GridLayout(2, 4, 5, 5));
					prin.AgregarPaneles();
					prin.ActivarHilos();
					prin.setBounds(0, 0, 600, 250);
					prin.setLocationRelativeTo(null);
				}
			}
		});
        img = new ImageIcon(this.getClass().getResource("/JavaZoneEspiaWeb/webcam.png")).getImage();
        this.setDoubleBuffered(true);
        IniciarHilo();
    }
    
    public void PararHilo(){
    	hf=null;
    }
    
    public void IniciarHilo(){
    	hf=new HiloFoto(this);
        hf.start();
    }

    @Override
    public void paintComponent(Graphics g) {
            super.paintComponents(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}