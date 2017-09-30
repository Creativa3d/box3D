package JavaZoneEspiaWeb;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

public class HiloFoto extends Thread{
	
	PanelCamara pc;
	
	public HiloFoto(PanelCamara pc){
		this.pc=pc;
	}
	
	public void run(){
		try {
			Thread.sleep(500);
			while(true){
				pc.img.flush();
				pc.img=new ImageIcon(new URL(pc.url)).getImage();
				pc.repaint();
				pc.updateUI();
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
