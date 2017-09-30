/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesChart.guardar;

import ListDatos.JFilaDatosDefecto;
import java.io.File;
import java.io.FileOutputStream;
import utiles.CadenaLarga;
import utiles.CadenaLargaOut;
import utiles.JArchivo;
import utiles.JCadenas;
import utilesChart.IGraficoXY;
import utilesChart.JPanelGraf2;
import utilesChart.JParamGraf2;
import utilesChart.JParamGraf2Y;
import utilesChart.util.JPanelGrafico;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.boton.IEjecutar;
import utilesGUIx.msgbox.JMsgBox;
import utilesx.JEjecutar;

/**
 *
 * @author eduardo
 */
public class JEjecutarGraf2Guardar implements IEjecutar{
    private final JPanelGraf2 moGraf2;
    private final JDatosGraf2Guardar moDatos;

    public JEjecutarGraf2Guardar(JPanelGraf2 poGraf2, JDatosGraf2Guardar poDatos){
        moGraf2=poGraf2;
        moDatos=poDatos;
    }
    //pixel
    public static int mlConvertir(final double pdCM){
        return (int)((pdCM * 72.0) / 2.54);
    }        
    public void actionPerformed(ActionEventCZ e, int plIndex) throws Exception {
        
        if(moDatos.mlProceso==JDatosGraf2Guardar.mclSimple){
            moGraf2.getGraficoXY().guardarGraficoPNG(moDatos.msRuta, mlConvertir(moDatos.mdAncho), mlConvertir(moDatos.mdAlto));
        }else{
            JParamGraf2 loParam = moGraf2.getParam();
            JParamGraf2Y loY = (JParamGraf2Y) loParam.moCollecEje1.get(moGraf2.getParametro1());
            JPanelGraf2 loPanelGraf = new JPanelGraf2();
            loPanelGraf.setDatos(loParam);
            if(loParam.moCollecEje2.size()>0){
                //PONEMOS EL EJE 2 Q ES INAMOVIBLE
                loPanelGraf.procesarParametro(
                            moGraf2.getParametro2()
                            , moGraf2.getFiltro2(), 1, false);
                loPanelGraf.aplicarEstilos(loParam.moCollecEje1, moGraf2.getParametro1(), loParam.moCollecEje2, moGraf2.getParametro2());
            }
            //para cada elemento del eje y procesamos
            File loDir;
            if(moDatos.mlDestino==JDatosGraf2Guardar.mclDirectorio){
                loDir = new File(moDatos.msRuta);
            }else{
                File loAux = new File(moDatos.msRuta);
                loDir = new File(loAux.getParentFile(), "tmp");
                loDir.mkdir();
            }
            StringBuilder lsVBImgs = new StringBuilder(500);

            for(int i = 0; i < loY.moColleciGrafXY.size(); i++){
                loPanelGraf.procesarParametro(
                        moGraf2.getParametro1()
                        , moGraf2.getFiltro1(), 0, false, i);
                loPanelGraf.aplicarEstilos(loParam.moCollecEje1, moGraf2.getParametro1(), loParam.moCollecEje2, moGraf2.getParametro2());
                IGraficoXY loGrafXY = (IGraficoXY) loY.moColleciGrafXY.get(i);

                if(loGrafXY.oSerie().moFunciones.moveFirst()){
                    String lsNombre = String.valueOf(i);
                    if(!JCadenas.isVacio(loGrafXY.oSerie().msSerieIdent)){
                        lsNombre=loGrafXY.oSerie().msSerieIdent.replaceAll(String.valueOf(JFilaDatosDefecto.mccSeparacion1), "");
                    }
                    File loFile = new File(loDir, lsNombre + ".png");
                    loPanelGraf.getGraficoXY().guardarGraficoPNG(
                            loFile.getAbsolutePath()
                            , mlConvertir(moDatos.mdAncho), mlConvertir(moDatos.mdAlto));
                    if(moDatos.mlDestino==JDatosGraf2Guardar.mclWord){
                        lsVBImgs.append("objWord.Selection.InlineShapes.AddPicture \""+loFile.getAbsolutePath()+"\", False, True");
                        lsVBImgs.append(((char) 13)); lsVBImgs.append(((char) 10));
                        lsVBImgs.append("objWord.Selection.MoveRight 1, 1");
                        lsVBImgs.append(((char) 13)); lsVBImgs.append(((char) 10));
                        lsVBImgs.append("objWord.Selection.TypeParagraph");
                        lsVBImgs.append(((char) 13)); lsVBImgs.append(((char) 10));
                    }
                }
            }
            if(moDatos.mlDestino==JDatosGraf2Guardar.mclWord){
                StringBuilder lsVB = new StringBuilder(500);
                lsVB.append("Set objWord = CreateObject(\"Word.Application\")");
                lsVB.append(((char) 13)); lsVB.append(((char) 10));
                lsVB.append(((char) 13)); lsVB.append(((char) 10));
                lsVB.append("objWord.Visible = True");
                lsVB.append(((char) 13)); lsVB.append(((char) 10));
                lsVB.append("objWord.Documents.Add()");
                lsVB.append(((char) 13)); lsVB.append(((char) 10));
                lsVB.append(lsVBImgs.toString());
                lsVB.append("objWord.ActiveDocument.SaveAs \"" + moDatos.msRuta + "\"");
                lsVB.append(((char) 13)); lsVB.append(((char) 10));
        
                ejecutarVBS(lsVB.toString(), loDir);


            }
            
        }
    }
    public void ejecutarVBS(String psVBS, File loDir) throws Exception {
        File loVBS = new File(loDir, "word.vbs");
        JArchivo.guardarArchivo(
                new CadenaLarga(psVBS)
                , new FileOutputStream(loVBS));
        
        JEjecutar loEje = new JEjecutar(new String[]{"cscript", loVBS.getAbsolutePath()});
        CadenaLargaOut loOut = new CadenaLargaOut();
        CadenaLargaOut loError = new CadenaLargaOut();
        loEje.setOutputProceso(loOut);
        loEje.setOuterrorProceso(loError);
        
        new Thread(loEje).start();
//                JArchivo.borrarDirectorio(loDir, false);
//        loEje.run();
//        JMsgBox.mensajeInformacion(null, loOut.moStringBuffer.toString() + "\n" + loError.moStringBuffer.toString());
//        if (loEje.getExitVal() != 0) {
//            throw new Exception(loOut.moStringBuffer.toString() + "\n" + loError.moStringBuffer.toString());
//        }
    }
    
}
