package JavaZoneEspiaWeb;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Principal extends JFrame {

    public JPanel panelPrincipal;
    public ArrayList<String> urls = new ArrayList<String>();
    public ArrayList<PanelCamara> camaras = new ArrayList<PanelCamara>();

    public Principal() {

        urls.add("http://146.186.123.229/axis-cgi/jpg/image.cgi?resolution=352x240");
        urls.add("http://131.111.133.11/axis-cgi/jpg/image.cgi?resolution=480x360&dummy=1267804722739");
        urls.add("http://fotogermanoviseu.dyndns.info/axis-cgi/jpg/image.cgi?resolution=640x480&compression=10&color=1&clock=1&date=1");
        urls.add("http://198.82.159.134/axis-cgi/jpg/image.cgi?resolution=640x480&dummy=1152818432828");
        urls.add("http://98.238.252.97/axis-cgi/jpg/image.cgi?resolution=800x600&dummy=1280477002758");
        urls.add("http://80.24.195.19/axis-cgi/jpg/image.cgi?resolution=480x360");
    	urls.add("http://216.8.159.21/axis-cgi/jpg/image.cgi?resolution=640x480");
        urls.add("http://hncam1.hn.psu.edu/axis-cgi/jpg/image.cgi?resolution=320x240");

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(2, 4, 5, 5));
        for (int i = 0; i < urls.size(); i++) {
                PanelCamara pc = new PanelCamara(i,urls.get(i),this);                
                panelPrincipal.add(pc);
                camaras.add(pc);
        }
        add(panelPrincipal);
    }
    
    public void DesactivarHilos(){
    	for(int i=0;i<camaras.size();i++){
			camaras.get(i).hf.stop();
			camaras.get(i).PararHilo();
    	}
    }
    
    public void ActivarHilos(){
    	for(int i=0;i<camaras.size();i++){
    		camaras.get(i).IniciarHilo();
    	}
    }
    
    public void AgregarPaneles(){
    	for(int i=0;i<camaras.size();i++){
    		panelPrincipal.add(camaras.get(i));
    	}
    	panelPrincipal.updateUI();
    	repaint();
    }

    public static void main(String arg[]) {

        Principal p = new Principal();
        p.setVisible(true);
        p.setBounds(0, 0, 600, 250);
        p.setLocationRelativeTo(null);
        p.setTitle("Java Zone Espia Web");
        p.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}