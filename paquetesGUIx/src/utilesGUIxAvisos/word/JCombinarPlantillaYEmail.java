/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.word;

import ListDatos.JListDatos;
import impresionXML.impresion.word.combinar.IPlantillas;
import impresionXML.impresion.word.combinar.JWord;
import java.io.File;
import java.io.FileOutputStream;
import utiles.CadenaLarga;
import utiles.JArchivo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.avisos.JMensaje;

/**
 *
 * @author eduardo
 */
public class JCombinarPlantillaYEmail extends JProcesoAccionAbstracX {

    private final String msNombreDoc;
    private final JListDatos moList;
    private final int mlPosiEMAIL;
    private final int mlPosiIDENTIFICADOR;
    private final JGUIxAvisosDatosGenerales moDa;
    private final IPlantillas moWord;
    private final JMensaje moMensaje;

    public JCombinarPlantillaYEmail(IPlantillas poWord, String lsNombreDoc, JListDatos poList, int lPosiEMAIL, int lPosiIDENTIFICADOR, JGUIxAvisosDatosGenerales loDa, JMensaje poMensaje) {
        msNombreDoc = lsNombreDoc;
        moList = poList;
        mlPosiEMAIL = lPosiEMAIL;
        mlPosiIDENTIFICADOR = lPosiIDENTIFICADOR;
        moDa = loDa;
        moMensaje = poMensaje;
        moWord = poWord;
    }

    public String getTitulo() {
        return "Combinar y eMail";
    }

    public int getNumeroRegistros() {
        return moList.size();
    }

    @Override
    public void procesar() throws Throwable {
        moDa.inicializarEmail();
        if (moList.moveFirst()) {
            mlRegistroActual = moList.mlIndex;
            do {
                if (moList.getFields(mlPosiEMAIL).isVacio()) {
                    JMsgBox.mensajeInformacion(null, "No tiene eMail" + moList.getFields(mlPosiIDENTIFICADOR).toString());
                } else {
                    JListDatos loListAux = new JListDatos(moList, false);
                    loListAux.add(moList.moFila());
                    moWord.generarPlantilla(loListAux);
                    File loFileTMP = new File(System.getProperty("java.io.tmpdir"), moList.getFields(mlPosiIDENTIFICADOR).toString() + ".doc");
                    try {

                        moWord.combinarPlantilla(msNombreDoc, moList, loFileTMP);
                        
                        JMensaje loMensajeAux = (JMensaje) moMensaje.clone();
                        loMensajeAux.addEmailTO(moList.getFields(mlPosiEMAIL).getString());
                        loMensajeAux.addFicheroAdjunto(loFileTMP.getAbsolutePath());

                        moDa.enviarEmail(loMensajeAux);
                    } finally {
                        loFileTMP.delete();
                    }
                }

            } while (moList.moveNext());

        }

    }

    public String getTituloRegistroActual() {
        return moList.getFields(mlPosiIDENTIFICADOR).getString();
    }

    public void mostrarMensaje(String psMensaje) {
        JMsgBox.mensajeInformacion(null, psMensaje);
    }
}
