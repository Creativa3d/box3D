/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.imgTrata.lista;

import impresionXML.impresion.pdf.JPDFImprimirInforme;
import impresionXML.impresion.xml.JxmlImagen;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlLectorInforme;
import impresionXML.impresion.xml.JxmlTexto;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.imgTrata.JIMGTrata;

public class JImagenAccionImprimir implements IEjecutarExtend {
    public static final String mcsImprimir = "<html>Imprimir imagen</html>";
    private final IPanelControlador moControlador;

    public JImagenAccionImprimir (IPanelControlador poControlador){
        moControlador=poControlador;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        for(int i = 0 ; i < plIndex.length; i++){
            moControlador.getConsulta().getList().setIndex(plIndex[i]);
            if(e.getActionCommand().equalsIgnoreCase(mcsImprimir)){
                JPanelGenericoGaleria loGaleria = (JPanelGenericoGaleria) moControlador.getPanel();                    
                IImagen loImagen = loGaleria.getTabla().getImagen(moControlador.getConsulta().getList().moFila());
                if(loImagen!=null){
                    JxmlInforme loInforme = JxmlLectorInforme.leerInforme("/utilesGUIx/imgTrata/lista/informe.xml");
                    JxmlImagen loImg = (JxmlImagen) loInforme.getImagen("imagen1").get(0);
                    loImg.setImagen(JIMGTrata.getIMGTrata().getBufferedImage(((ImageIcon)loImagen.getImagen())));
                    JxmlTexto loText = (JxmlTexto) loInforme.getTexto("texto1").get(0);
                    loText.setTexto(loImagen.getDescripcion());
                    
                    JPDFImprimirInforme.imprimir(loInforme, new FileOutputStream("imagen"+String.valueOf(i)+".pdf"));
                    utilesx.JEjecutar.abrirDocumento("imagen"+String.valueOf(i)+".pdf");
                }
            }
            
        }

    }

}
