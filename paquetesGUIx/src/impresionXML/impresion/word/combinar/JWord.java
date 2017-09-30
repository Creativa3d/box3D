/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import utiles.CadenaLarga;
import utiles.CadenaLargaOut;
import utiles.JArchivo;
import utiles.JDepuracion;
import utilesx.JEjecutar;

/**
 *
 * @author eduardo
 */
public class JWord implements IPlantillas {
    public static final String mcsTIPOWORD="TIPOWORD";
    public static final String mcsTIPOWORDHTML="TIPOWORDHTML";
    public static final String mcsPlantillaDir = "plantilla";
    public static final String mcsPlantilla = "plantilla.txt";
    public static final String mcsWORD = "word";
    
    protected static final char mcsAntesPalabra = (char)171;
    protected static final char mcsDespuesPalabra = (char)187;
    private String msGrupo;
    private final String msDirecTrabajo;

    public JWord(String psGrupo, String psDirecTrabajo) {
        msGrupo = psGrupo;
        msDirecTrabajo = psDirecTrabajo;
        File loFile = new File(msDirecTrabajo);
        loFile.mkdirs();
    }
    
    public void setGrupo(String psGrupo){
        msGrupo=psGrupo;
    }

    public static String getIdentWord(final String psTabla, final String psCampo) {
        String lsResult = "";
        if (psCampo == null || psCampo.equals("")) {
            lsResult = psTabla.trim();
        } else {
            if (psTabla == null || psTabla.equals("") || psCampo.trim().startsWith(psTabla)) {
                lsResult = psCampo.trim().replace(".", "_");
            } else {
                lsResult = psTabla.trim() + "_" + psCampo.trim().replace(".", "_");
            }
        }
        if (lsResult.length() > 41) {
            lsResult = lsResult.substring(0, 40);
        }
        return lsResult;
    }

    public File getRutaBase() throws Exception {
        File loFile = new File(msDirecTrabajo, mcsWORD);
        loFile.mkdirs();
        loFile = new File(loFile, msGrupo);
        loFile.mkdirs();
        return loFile;
    }

    public File getPlantilla() throws Exception {
        File loFile = getRutaBase();
        loFile = new File(loFile, mcsPlantillaDir);
        loFile.mkdirs();
        loFile = new File(loFile, mcsPlantilla);
        return loFile;
    }

    @Override
    public void generarPlantilla(JListDatos poList) throws Exception {
        generarPlantilla(poList, getPlantilla().getAbsolutePath());
    }

    public static void generarPlantilla(JListDatos poList, String psFile) throws Exception {
        FileOutputStream loOut = new FileOutputStream(psFile);
        PrintWriter loPrint = new PrintWriter(loOut);
        try {
            for (int i = 0; i < poList.getFields().size(); i++) {
                loPrint.write(getIdentWord(poList.getFields(i).getTabla(), poList.getFields(i).getNombre()) + '\t');
            }
            loPrint.write('\n');
            if (poList.moveFirst()) {
                do {
                    for (int i = 0; i < poList.getFields().size(); i++) {
                        JFieldDef loCampo = poList.getFields(i);
                        String lsAux = loCampo.getString().trim();
                        if(loCampo.getAtributos().get(mcsTIPOWORD)!=null
                                && loCampo.getAtributos().get(mcsTIPOWORD).toString().equalsIgnoreCase(mcsTIPOWORDHTML)){
                            File loFile = File.createTempFile(loCampo.getNombre(), ".html");
                            JArchivo.guardarArchivo(lsAux, loFile);
                            lsAux = loFile.getAbsolutePath().replace("/", "\\");
                            lsAux = lsAux.replace("\\", "\\\\");
                            loCampo.setValue(lsAux);
                            poList.update(false);
                            lsAux="";
                        }
                        loPrint.write(lsAux.replaceAll("\n", " ").replaceAll("\t", " ") + '\t');
                    }
                    if (poList.mlIndex != poList.size() - 1) {//la ultima fila sin intro
                        loPrint.write('\n');
                    }
                } while (poList.moveNext());
            }
        } finally {
            loPrint.close();
            loOut.close();
        }
    }

    @Override
    public void rellenarListaPlantillas(JListDatos loResult) throws Exception {
        loResult.clear();
        File loDir = getRutaBase();
        File[] loFiles = loDir.listFiles();
        for (File loFile : loFiles) {
            if (!loFile.isDirectory() && loFile.getName().contains("doc")) {
                loResult.addNew();
                loResult.getFields(0).setValue(loFile.getName());
                loResult.update(false);
            }
        }
    }

    @Override
    public JListDatos getListaPlantillas() throws Exception {
        JListDatos loResult = new JListDatos(null, "WORD", new String[]{"Nombre"}, new int[]{JListDatos.mclTipoCadena}, new int[]{0});
        rellenarListaPlantillas(loResult);
        return loResult;
    }

    public static final String getExtesionFichero(String psNombre) {
        String lsResult;
        int lPunto = psNombre.lastIndexOf('.');
        if (lPunto > 0) {
            lsResult = psNombre.substring(lPunto);
        } else {
            lsResult = "";
        }
        return lsResult;
    }

    public static final String getNombreFicheroSinExtension(String psNombre) {
        String lsResult;
        int lPunto = psNombre.lastIndexOf('.');
        if (lPunto > 0) {
            lsResult = psNombre.substring(0, lPunto);
        } else {
            lsResult = psNombre;
        }
        return lsResult;
    }

    @Override
    public void borrar(String lsNombre) throws Exception {
        File loFile = new File(getRutaBase(), lsNombre);
        if (loFile.exists()) {
            if (!loFile.delete()) {
                throw new Exception("No se ha podido borrar la plantilla " + lsNombre);
            }
        }
    }

    @Override
    public void renombrar(String lsAntig, String lsNombre) throws Exception {
        File loFile = new File(getRutaBase(), lsAntig);
        if (loFile.exists()) {
            File loFileAux = new File(getRutaBase(), lsNombre);
            if (!loFile.renameTo(loFileAux)) {
                throw new Exception("No se ha podido renombrar la plantilla " + lsAntig + " a " + lsNombre);
            }
        }
    }

    @Override
    public String procesar(File loFile) throws Exception {
        String lsNombre = loFile.getName();
        if (loFile.exists()) {
            //entonces es un externo, pq si no existe es que es ruta relativa
            File loAux = new File(getRutaBase(), loFile.getName());
            //comprobamos q no existe en el direc. word, si existe le cambiamos nombre
            int i = 0;
            while (loAux.exists()) {
                i++;
                int lPunto = loFile.getName().lastIndexOf('.');
                if (lPunto > 0) {
                    loAux = new File(getRutaBase(), loFile.getName().substring(0, lPunto) + String.valueOf(i) + loFile.getName().substring(lPunto));
                } else {
                    loAux = new File(getRutaBase(), String.valueOf(i) + loFile.getName());
                }

            }
            //guardamos en el direc. de word
            JArchivo.guardarArchivo(loFile, loAux);
            lsNombre = loAux.getName();
        } else {
            throw new Exception("Fichero " + loFile.getAbsolutePath() + " no existe");
        }

        loFile = new File(getRutaBase(), lsNombre);
        if (loFile.exists()) {
            File loVBS = new File(getRutaBase(), "enlace.vbs");
            JArchivo.guardarArchivo(
                    new CadenaLarga(getVBScripEnlace(loFile, getPlantilla())), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath());
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }

        return lsNombre;
    }

    @Override
    public String crearPlantilla(String lsNombre) throws Exception {
        File loFile = new File(getRutaBase(), lsNombre);
        if (!loFile.exists()) {
            File loVBS = new File(getRutaBase(), "nueva.vbs");
            JArchivo.guardarArchivo(
                    new CadenaLarga(getVBScripEnlaceNueva(loFile, getPlantilla())), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath());
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
        return lsNombre;
    }

    @Override
    public void mostrarPlantilla(String lsNombre) throws Exception {
        File loFile = new File(getRutaBase(), lsNombre);
        if (loFile.exists()) {
            File loVBS = new File(getRutaBase(), "enlace.vbs");
            JArchivo.guardarArchivo(
                    new CadenaLarga(getVBScripEnlace(loFile, getPlantilla())), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath());
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
    }

    @Override
    public void combinarPlantilla(String lsNombre, JListDatos poList) throws Exception {
        File loFile = new File(getRutaBase(), lsNombre);
        if (loFile.exists()) {
            generarPlantilla(poList);
            File loVBS = new File(getRutaBase(), "combinar.vbs");
            JArchivo.guardarArchivo(
                    new CadenaLarga(getVBScripCombinarPlantilla(loFile, getPlantilla(), null, poList)), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath());
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
    }
    @Override
    public void combinarPlantilla(String lsNombre, JListDatos poList, File poFileDestino) throws Exception {
        File loFile = new File(getRutaBase(), lsNombre);
        if (loFile.exists()) {
            generarPlantilla(poList);
            File loVBS = new File(getRutaBase(), "combinar.vbs");
            JArchivo.guardarArchivo(
                    new CadenaLarga(getVBScripCombinarPlantilla(loFile, getPlantilla(),poFileDestino, poList)), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath());
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
    }


    public void ejecutarVBS(String psVBS) throws Exception {
        JEjecutar loEje = new JEjecutar(new String[]{"cscript", psVBS}, getRutaBase());
        CadenaLargaOut loOut = new CadenaLargaOut();
        CadenaLargaOut loError = new CadenaLargaOut();
        loEje.setOutputProceso(loOut);
        loEje.setOuterrorProceso(loError);
        loEje.run();
        if (loEje.getExitVal() != 0) {
            throw new Exception(loOut.moStringBuffer.toString() + "\n" + loError.moStringBuffer.toString());
        }
    }

    public static String getVBScripCombinarPlantilla(File poDoc, File poTXT, File poTMP, JListDatos poList) {
        StringBuilder lsVB = new StringBuilder(500);

        lsVB.append("Set objWord = CreateObject(\"Word.Application\")");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.Documents.Open \"" + poDoc.getAbsolutePath() + "\", False,False, False, \"\", \"\", False, \"\", \"\", 0");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        if(poList!=null){
            for (int i = 0; i < poList.getFields().size(); i++) {
                JFieldDef loCampo = poList.getFields(i);
                if(loCampo.getAtributos().get(mcsTIPOWORD)!=null
                        && loCampo.getAtributos().get(mcsTIPOWORD).toString().equalsIgnoreCase(mcsTIPOWORDHTML)
                        ){

                    lsVB.append("objWord.Selection.Find.ClearFormatting");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("With objWord.Selection.Find");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append(" .Text = \""+mcsAntesPalabra + getIdentWord(loCampo.getTabla(), loCampo.getNombre()) + mcsDespuesPalabra+"\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .Replacement.Text = \"\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .Forward = True");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .Wrap = wdFindContinue");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .Format = False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .MatchCase = False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .MatchWholeWord = False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .MatchWildcards = False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .MatchSoundsLike = False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    .MatchAllWordForms = False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("End With");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("If objWord.Selection.Find.Execute Then");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    objWord.Selection.Delete 1, 1");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    lsVB.append("    objWord.Selection.TypeText \" \"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    if(!loCampo.isVacio()){
//                        lsVB.append("    objWord.Selection.InsertFile FileName:=\""+loCampo.getString()+"\", Range:=\"\", ConfirmConversions:=False, Link:=False, Attachment:=False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                        lsVB.append("    objWord.Selection.InsertFile \""+loCampo.getString()+"\", \"\", False, False, False");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                    }
                    lsVB.append("End If");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                }
            }
        }
        
        
        lsVB.append("objWord.ActiveDocument.MailMerge.OpenDataSource  \"" + poTXT.getAbsolutePath() + "\", 0, False, False, True,  False, \"\", \"\", False, \"\", \"\",  \"\", \"\", \"\", False, 0");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("With objWord.ActiveDocument.MailMerge");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append(".Destination = 0");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append(".SuppressBlankLines = True");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("With .DataSource");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append(".FirstRecord = 1");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append(".LastRecord = -16");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("End With");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append(".Execute False");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("End With");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        
        if(poTMP==null){
            try {
                File loFile = File.createTempFile("Word", ".doc");
                lsVB.append("objWord.ActiveDocument.SaveAs \"" + loFile.getAbsolutePath() + "\"");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                lsVB.append("objWord.Application.Quit False ");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                
                
                lsVB.append("Set objWord = CreateObject(\"Word.Application\")");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                lsVB.append("objWord.Visible=true");lsVB.append(((char) 13)); lsVB.append(((char) 10));
                lsVB.append("objWord.Documents.Open \"" + loFile.getAbsolutePath() + "\", False,False, False, \"\", \"\", False, \"\", \"\", 0");lsVB.append(((char) 13)); lsVB.append(((char) 10));
        
            } catch (IOException ex) {
                JDepuracion.anadirTexto(JWord.class.getName(), ex);
            }
            
        } else {
            lsVB.append("objWord.ActiveDocument.SaveAs \"" + poTMP.getAbsolutePath() + "\"");
            lsVB.append(((char) 13)); lsVB.append(((char) 10));
            lsVB.append("objWord.Application.Quit False ");
            lsVB.append(((char) 13)); lsVB.append(((char) 10));
        }
        return lsVB.toString();
    }
    public static String getVBScripEnlace(File poDoc, File poTXT) {
        StringBuilder lsVB = new StringBuilder(500);

        lsVB.append("Set objWord = CreateObject(\"Word.Application\")");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.Visible = True");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.Documents.Open \"" + poDoc.getAbsolutePath() + "\", False,False, False, \"\", \"\", False, \"\", \"\", 0");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.ActiveDocument.MailMerge.OpenDataSource  \"" + poTXT.getAbsolutePath() + "\", 0, False, False, True,  False, \"\", \"\", False, \"\", \"\",  \"\", \"\", \"\", False, 0");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.Windows(\"" + poDoc.getName() + "\").Activate");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.ActiveDocument.Save");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        return lsVB.toString();
    }

    public static String getVBScripEnlaceNueva(File poDoc, File poTXT) {
        StringBuilder lsVB = new StringBuilder(500);

        lsVB.append("Set objWord = CreateObject(\"Word.Application\")");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.Visible = True");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.Documents.Add()");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.ActiveDocument.MailMerge.OpenDataSource  \"" + poTXT.getAbsolutePath() + "\", 0, False, False, True,  False, \"\", \"\", False, \"\", \"\",  \"\", \"\", \"\", False, 0");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        lsVB.append("objWord.ActiveDocument.SaveAs \"" + poDoc.getAbsolutePath() + "\"");
        lsVB.append(((char) 13)); lsVB.append(((char) 10));
        return lsVB.toString();
    }
}
