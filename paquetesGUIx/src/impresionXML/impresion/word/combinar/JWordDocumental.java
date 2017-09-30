/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar;

import ListDatos.IResultado;
import ListDatos.JListDatos;
import java.io.File;
import java.io.FileOutputStream;
import utiles.CadenaLarga;
import utiles.CadenaLargaOut;
import utiles.JArchivo;
import utiles.JDepuracion;
import utilesDoc.JDocDatosGeneralesGUIx;
import utilesDoc.tablasExtend.JTEEDOCUMENTOS;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.msgbox.JMsgBox;
import utilesx.IEjecutarListener;
import utilesx.JEjecutar;

/**
 *
 * @author eduardo
 */
public class JWordDocumental implements IPlantillas {

    public static final String mcsPlantillaDir = JWord.mcsPlantillaDir;
    public static final String mcsPlantilla = JWord.mcsPlantilla;
    public static final String mcsWORD = JWord.mcsWORD;
    private String msGrupo;
    private final String msDirecTrabajo;
    
    private JFicheroObservable moFichObs;
    private Thread moThread;

    public JWordDocumental(String psGrupo) {
        msGrupo = psGrupo;
        msDirecTrabajo = ".";
    }
    
    private void observar(File poFile, JTEEDOCUMENTOS poDoc){
        if(moFichObs!=null){
            moFichObs.mbObservar=false;
        }
        moFichObs=new JFicheroObservable(poFile.lastModified(), poFile, poDoc);
        moThread = new Thread(moFichObs);
        moThread.start();
    }

    class JFicheroObservable implements Runnable {
        long mlTime;
        File moFile;
        boolean mbObservar=true;
        private final JTEEDOCUMENTOS moDoc;
        
        public JFicheroObservable(long plTime, File poFile, JTEEDOCUMENTOS poDoc){
            mlTime = plTime;
            moFile = poFile;
            moDoc=poDoc;
        }

        @Override
        public void run() {
            while(mbObservar){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {}
                File loFile = new File(moFile.getAbsolutePath());
                if((mlTime+1000) < loFile.lastModified()){
                    try {
                        mlTime = loFile.lastModified();
                        moDoc.setDatosDefecto(loFile);
                        moDoc.guardar(null);
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(JWordDocumental.class.getName(), ex);
                    }
                }
            }
        }
        
    }
    
    @Override
    public void setGrupo(String psGrupo) {
        msGrupo=psGrupo;
    }
    public static String getIdentWord(final String psTabla, final String psCampo) {
        String lsResult = "";
        if (psCampo == null || psCampo.equals("")) {
            lsResult = psTabla.trim();
        } else {
            if (psTabla == null || psTabla.equals("")) {
                lsResult = psCampo.trim();
            } else {
                lsResult = psTabla.trim() + "_" + psCampo.trim();
            }
        }
        if (lsResult.length() > 41) {
            lsResult = lsResult.substring(0, 40);
        }
        return lsResult;
    }
    
    private String getGrupo(){
        return mcsWORD;
    }
    
    private String getGrupoIdent(){
        return msGrupo;
    }

    public File getRutaBaseTEMP() throws Exception {
        File loFile = new File(msDirecTrabajo, mcsWORD);
        loFile.mkdirs();
        loFile = new File(loFile, msGrupo);
        loFile.mkdirs();
        return loFile;
    }

    public File getPlantillaTEMP() throws Exception {
        File loFile = getRutaBaseTEMP();
        loFile = new File(loFile, mcsPlantillaDir);
        loFile.mkdirs();
        loFile = new File(loFile, mcsPlantilla);
        return loFile;
    }

    @Override
    public void generarPlantilla(JListDatos poList) throws Exception {
        JWord.generarPlantilla(poList, getPlantillaTEMP().getAbsolutePath());
    }

    @Override
    public void rellenarListaPlantillas(JListDatos loResult) throws Exception {
        loResult.clear();
        JTEEDOCUMENTOS loDoc = JTEEDOCUMENTOS.getTabla(getGrupo(), getGrupoIdent(), JGUIxConfigGlobalModelo.getInstancia().getServer());
        if(loDoc.moveFirst()){
            do{
                if (loDoc.getNOMBRE().getString().contains("doc")) {
                    loResult.addNew();
                    loResult.getFields(0).setValue(loDoc.getNOMBRE().getString());
                    loResult.update(false);
                }
            }while(loDoc.moveNext());
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
        JTEEDOCUMENTOS loDoc = JTEEDOCUMENTOS.getTablaNombre(
                getGrupo(), getGrupoIdent()
                , lsNombre, JGUIxConfigGlobalModelo.getInstancia().getServer());
        IResultado loResult = loDoc.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
    }

    @Override
    public void renombrar(String lsAntig, String lsNombre) throws Exception {
        JTEEDOCUMENTOS loDoc = JTEEDOCUMENTOS.getTablaNombre(
                getGrupo(), getGrupoIdent()
                , lsAntig, JGUIxConfigGlobalModelo.getInstancia().getServer());
        
        loDoc.getNOMBRE().setValue(lsNombre);
        loDoc.guardar(null);
        
    }

    @Override
    public String procesar(File poFileO) throws Exception {
        final JTEEDOCUMENTOS loDoc;
        String lsNombre = poFileO.getName();
        if (!poFileO.exists()) {
            throw new Exception("Fichero " + poFileO.getAbsolutePath() + " no existe");
        } else {
            loDoc = JTEEDOCUMENTOS.getTabla(getGrupo(), getGrupoIdent(), JGUIxConfigGlobalModelo.getInstancia().getServer());
            int i = 0;
            while(loDoc.moList.buscar(JListDatos.mclTIgual, loDoc.lPosiNOMBRE, lsNombre)){
                i++;
                int lPunto = lsNombre.lastIndexOf('.');
                if (lPunto > 0) {
                    lsNombre = lsNombre.substring(0, lPunto) + String.valueOf(i) + lsNombre.substring(lPunto);
                } else {
                    lsNombre = String.valueOf(i) + poFileO.getName();
                }
            }
            loDoc.moList.clear();
            loDoc.addNew();
            loDoc.setDatosDefecto(poFileO);
            loDoc.getGRUPO().setValue(getGrupo());
            loDoc.getGRUPOIDENT().setValue(getGrupoIdent());
            IResultado loResult = loDoc.guardar(null);
            if(!loResult.getBien()){
                throw new Exception(loResult.getMensaje());
            }                    
        }

        final File loFile = new File(getRutaBaseTEMP(), lsNombre);
        JArchivo.copy(poFileO, loFile);
        
        if (loFile.exists()) {
            File loVBS = new File(getRutaBaseTEMP(), "enlace.vbs");
            JArchivo.guardarArchivo(new CadenaLarga(JWord.getVBScripEnlace(loFile, getPlantillaTEMP())), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath(), null);
            observar(loFile, loDoc);
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }

        return lsNombre;
    }
    
    @Override
    public String crearPlantilla(String lsNombre) throws Exception {
        final File loFile = new File(getRutaBaseTEMP(), lsNombre);
        if (!loFile.exists()) {
            File loVBS = new File(getRutaBaseTEMP(), "nueva.vbs");
            JArchivo.guardarArchivo(new CadenaLarga(JWord.getVBScripEnlaceNueva(loFile, getPlantillaTEMP())), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath(), new IEjecutarListener() {
                @Override
                public void terminado(JEjecutar poEjecucion) {
                    try {
                        JTEEDOCUMENTOS loDoc = new JTEEDOCUMENTOS(JGUIxConfigGlobalModelo.getInstancia().getServer());
                        loDoc.addNew();
                        loDoc.setDatosDefecto(loFile);
                        loDoc.getGRUPO().setValue(getGrupo());
                        loDoc.getGRUPOIDENT().setValue(getGrupoIdent());
                        IResultado loResult = loDoc.guardar(null);
                        if(!loResult.getBien()){
                            throw new Exception(loResult.getMensaje());                    
                        }
                        observar(loFile, loDoc);
                    } catch (Exception ex) {
                        JMsgBox.mensajeErrorYLog(null, ex);
                    }
                }
            });            
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
        return lsNombre;
    }

    @Override
    public void mostrarPlantilla(String lsNombre) throws Exception {
        final JTEEDOCUMENTOS loDoc = JTEEDOCUMENTOS.getTablaNombre(getGrupo(), getGrupoIdent(), lsNombre, JGUIxConfigGlobalModelo.getInstancia().getServer());
        final File loFile = new File(getRutaBaseTEMP(), lsNombre);
        JArchivo.copy(loDoc.getFicheroLocal(), loFile);
        if (loFile.exists()) {
            File loVBS = new File(getRutaBaseTEMP(), "enlace.vbs");
            JArchivo.guardarArchivo(new CadenaLarga(JWord.getVBScripEnlace(loFile, getPlantillaTEMP())), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath(), null);
            observar(loFile, loDoc);
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
    }

    @Override
    public void combinarPlantilla(String lsNombre, JListDatos poList) throws Exception {
        final JTEEDOCUMENTOS loDoc = JTEEDOCUMENTOS.getTablaNombre(getGrupo(), getGrupoIdent(), lsNombre, JGUIxConfigGlobalModelo.getInstancia().getServer());
        final File loFile = new File(getRutaBaseTEMP(), lsNombre);
        JArchivo.copy(loDoc.getFicheroLocal(), loFile);
        if (loFile.exists()) {
            generarPlantilla(poList);
            File loVBS = new File(getRutaBaseTEMP(), "combinar.vbs");
            JArchivo.guardarArchivo(new CadenaLarga(JWord.getVBScripCombinarPlantilla(loFile, getPlantillaTEMP(), null, poList)), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath(), null);
            observar(loFile, loDoc);
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
    }

    @Override
    public void combinarPlantilla(String lsNombre, JListDatos poList, File poFileDestino) throws Exception {
        final JTEEDOCUMENTOS loDoc = JTEEDOCUMENTOS.getTablaNombre(getGrupo(), getGrupoIdent(), lsNombre, JGUIxConfigGlobalModelo.getInstancia().getServer());
        final File loFile = new File(getRutaBaseTEMP(), lsNombre);
        JArchivo.copy(loDoc.getFicheroLocal(), loFile);
        if (loFile.exists()) {
            generarPlantilla(poList);
            File loVBS = new File(getRutaBaseTEMP(), "combinar.vbs");
            JArchivo.guardarArchivo(new CadenaLarga(JWord.getVBScripCombinarPlantilla(loFile, getPlantillaTEMP(), poFileDestino, poList)), new FileOutputStream(loVBS));
            ejecutarVBS(loVBS.getAbsolutePath(), null);
            observar(loFile, loDoc);
        } else {
            throw new Exception("Fichero no existe " + lsNombre);
        }
    }
    

    public void ejecutarVBS(String psVBS, IEjecutarListener poCallBack) throws Exception {
        JEjecutar loEje = new JEjecutar(new String[]{"cscript", psVBS}, getRutaBaseTEMP());
        CadenaLargaOut loOut = new CadenaLargaOut();
        CadenaLargaOut loError = new CadenaLargaOut();
        if(poCallBack!=null){
            loEje.addListener(poCallBack);
        }
        loEje.setOutputProceso(loOut);
        loEje.setOuterrorProceso(loError);
        loEje.run();
        if (loEje.getExitVal() != 0) {
            throw new Exception(loOut.moStringBuffer.toString() + "\n" + loError.moStringBuffer.toString());
        }
    }

}
