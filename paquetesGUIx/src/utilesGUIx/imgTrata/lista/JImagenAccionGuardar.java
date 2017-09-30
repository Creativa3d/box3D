/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.imgTrata.lista;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.imgTrata.JIMGTrata;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtension;

public class JImagenAccionGuardar implements IEjecutarExtend {
    public static final String mcsGuardarImagen = "<html>Guardar imagen</html>";
    private final IPanelControlador moControlador;

    public JImagenAccionGuardar (IPanelControlador poControlador){
        moControlador=poControlador;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        for(int i = 0 ; i < plIndex.length; i++){
            moControlador.getConsulta().getList().setIndex(plIndex[i]);
            if(e.getActionCommand().equalsIgnoreCase(mcsGuardarImagen)){
                JFileChooser loSelec = new JFileChooser();
                loSelec.setFileFilter(
                        new JFileChooserFiltroPorExtension("jpg",new String[]{"JPG"}));
                loSelec.setFileSelectionMode(JFileChooser.FILES_ONLY);
                loSelec.setMultiSelectionEnabled(false);
                if(loSelec.showSaveDialog((Component) moControlador.getPanel())==JFileChooser.APPROVE_OPTION){
                    JPanelGenericoGaleria loGaleria = (JPanelGenericoGaleria) moControlador.getPanel();                    
                    IImagen loImagen = loGaleria.getTabla().getImagen(moControlador.getConsulta().getList().moFila());
                    if(loImagen!=null){
                        String ls = loSelec.getSelectedFile().getAbsolutePath();
                        if(!ls.substring(ls.length()-3).equals("jpg")){
                            ls+=".jpg";
                        }
                        JIMGTrata.getIMGTrata().guardarImagenJPG(
                                JIMGTrata.getIMGTrata().getBufferedImage(((ImageIcon)loImagen.getImagen())),
                                ls, 9);
                    }
                }
            }
            
        }

    }

}
