/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizarDiferenciasBD;

import utilesBD.serverTrozos.IServerServidorDatosTrozos;
import ListDatos.ECampoError;
import ListDatos.IResultado;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JTableDefs;
import utiles.JDepuracion;
import utilesBD.pasarDatos.JPasarDatos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IMostrarPantalla;

/**
 *
 * @author eduardo
 */
public class JRecorrerBaseDatosYQuitarRaros extends JProcesoAccionAbstracX {
    private int mlTotal=-1;
    private String msTablaActual="";
    private IServerServidorDatosTrozos moServer;
    private JTableDefs moTablas;
    private JListDatos moPatrones = new JListDatos(null, "", new String[]{"patron", "sustituto"}, new int[]{JListDatos.mclTipoCadena, JListDatos.mclTipoCadena}, new int[]{0});
    private String msBuenos="abcdefghijklmnÒopqrstuvwxyz·ÈÌÛ˙"
            + "ABCDEFGHIJKLMN—OPQRSTUVWXYZ¡…Õ”⁄"
            + "0123456789"
            + " .,;:-?ø°!()=+-*/\\_#%∫™<>'@\n";
    
    private StringBuffer msCaracteresPosibles; 
    private StringBuffer moErrores; 
    private int mlSustituciones;
    private boolean mbREAL = true;
    public JRecorrerBaseDatosYQuitarRaros(IServerServidorDatosTrozos poServer) throws ECampoError{
        mlRegistroActual=0;
        moServer=poServer;
        addPatron("M—XIMO","M¡XIMO");
        addPatron("EP—GRAFE","EPÕGRAFE");
        addPatron("EP—GRAFE","EPÕGRAFE");
        addPatron("M—X.","M—X.");
        addPatron("AGR—COLA","AGR—COLA");
        addPatron("ANTONI¬?O","ANTONI¬?O");
        addPatron("DENOMINACI√N","DENOMINACI”N");
        addPatron("VAC—O","VACÕO");
        addPatron("V—LIDO","V¡LIDO");
        addPatron("VEH—CULO","VEHÕCULO");
        addPatron("TELEM—TICAMENTE","TELEM¡TICAMENTE");
        addPatron("S—BADO","S¡BADO");
        addPatron("GESTOR—A","GESTORÕA");
        addPatron("PROV—NCIA","PROVÕNCIA");
        addPatron("JUR—DICA","JURÕDICA");
        addPatron("F—SICA","FÕSICA");
        addPatron("M—XIMA","M¡XIMA");
        addPatron("L—MITE","LÕMITE");
        addPatron("…ST¡","EST¡");
        addPatron("LIMITACI—N","LIMITACI”N");
        addPatron("DISPOSICI—N","DISPOSICI”N");
        addPatron("TELEM—TICO","TELEM¡TICO");
        addPatron("MERCANC—AS","MERCANCÕAS");
        
        addPatron("CONDUCCI√?N", "CONDUCCI”N");
        addPatron("CEM VAC√çO", "CEM VACÕO");
        addPatron("A√?N", "A⁄N");
        addPatron("N√?MERO", "N⁄MERO");
        addPatron("EXENCI√?N", "EXENCI”N");
        addPatron("C√?DIGO", "C”DIGO");
        addPatron("SUJECI√?N", "SUJECI”N");
        addPatron("T√?CNICO", "T…CNICO");
        addPatron("MATRICULACI√?N", "MATRICULACI”N");
        addPatron("CIRCULACI√?N", "CIRCULACI”N");
        addPatron("VALIDACI√?N", "VALIDACI”N");
        addPatron("CREACI√?N", "CREACI”N");
        addPatron("PR√?LOGO", "PR”LOGO");
        addPatron("AUTOB√?S", "AUTOB⁄S");
        addPatron("RELACI√?N", "RELACI”N");
        addPatron("EL√?CTRICOS", "EL…CTRICOS");
        addPatron("IMPORTACI√?N", "IMPORTACI”N");
        addPatron("EXPORTACI√?N", "EXPORTACI”N");
        addPatron("TRANSMISI√?N", "TRANSMISI”N");
        addPatron("√?STE", "…STE");
        addPatron("EST√?", "…ST¡");
        addPatron("EL√?CTRICOS", "EL…CTRICOS");
        addPatron("INSPECCI√?N", "INSPECCI”N");
        addPatron("T√?CNICA", "T…CNICA");
        addPatron("FERN√?NDEZ", "FERN¡NDEZ");
        addPatron("FACTURACI√?N", "FACTURACI”N");
        addPatron("VEH√çCULOS", "VEHÕCULOS");
        addPatron("√?LTIMOS", "⁄LTIMOS");
        addPatron("EL√?CTRICO", "EL…CTRICO");
        addPatron("PROV√çNCIAS", "PROVÕNCIAS");
        addPatron("ANOTACI√?N", "ANOTACI”N");
        addPatron("PETICI√?N", "PETICI”N");
        addPatron("INT√?NTELO", "INT…NTELO");
        addPatron("NOTIFICACI√?N", "NOTIFICACI”N");
        addPatron("SEG√?N", "SEG⁄N");
        addPatron("KIL√?METROS", "KIL”METROS");
        addPatron("GUARE¬•A", "GUARE—A");
        addPatron("PE¬•ALSORDO", "PE—ALSORDO");
        addPatron("√ç", "Õ");
        addPatron("", "¡");
        addPatron("¬™", "™");
        addPatron("√±", "Ò");
        addPatron("√≥", "Û");
        addPatron("¬∫", "∫");
        addPatron("√°", "·");
        addPatron("√Å", "¡");
        addPatron("√≠", "Ì");
        addPatron("√\u008d", "Õ");
        addPatron("√?", "—");

        
        
    }
    
    private void addPatron(String psPatron, String psSustituto) throws ECampoError{
        moPatrones.addNew();
        moPatrones.getFields(0).setValue(psPatron);
        moPatrones.getFields(1).setValue(psSustituto);
        moPatrones.update(false);
    }

    public String getTitulo() {
        return "Quitar raros";
    }

    public int getNumeroRegistros() {
        return mlTotal;
    }

    public void procesar() throws Throwable {
        mlSustituciones=0;
        moErrores=new StringBuffer();
        msCaracteresPosibles=new StringBuffer();
        moTablas = moServer.getTableDefs();
        mlTotal=moTablas.size();
        for(mlRegistroActual = 0 ; mlRegistroActual < moTablas.size() && !mbCancelado;mlRegistroActual++){
            String lsTabla = moTablas.get(mlRegistroActual).getNombre();
            try {
                quitarraros(lsTabla);
            }catch(Exception e){
                JDepuracion.anadirTexto(getClass().getName(), e);
                JDepuracion.anadirTexto(getClass().getName(), lsTabla+" " + e.toString() );
            }
        }
        mbFin=true;
    }
    public synchronized void quitarraros(String psTabla) throws Exception {
        msTablaActual=psTabla;
        int[] lalCamposPrincipales = moTablas.get(psTabla).getCampos().malCamposPrincipales();
        if(lalCamposPrincipales==null){
            lalCamposPrincipales=moTablas.get(psTabla).getCampos().malCamposNOT_NULL();
        }
        JListDatos loListDatos = new JListDatos(
                moServer, psTabla,
                moTablas.get(psTabla).getCampos().msNombres(),
                moTablas.get(psTabla).getCampos().malTipos(),
                lalCamposPrincipales);


        boolean lbContinuar = true;
        int lMin = 0;
        int lMax = JPasarDatos.mclNumRegDefecto;
        try{
        while(lbContinuar){
            moServer.setIntervaloDatos(lMin, lMax);
            loListDatos.recuperarDatos(loListDatos.getSelect(),false,loListDatos.mclSelectNormal);
            System.gc();

            if(loListDatos.moveFirst()){
                do{
                    boolean lbCambio = false;
                    for(int i =0; i<loListDatos.getFields().count(); i++){
                        String lsValor = loListDatos.getFields(i).getString(); 
                        String lsNueva = procesarCadena(lsValor);
                        if(lsNueva!=null && !lsNueva.equals(lsValor)){
                            lbCambio=true;
                            loListDatos.getFields(i).setValue(lsNueva);
                        }
                    }
                    if(lbCambio){
                        IResultado loResult =  loListDatos.update(mbREAL);
                        if(!loResult.getBien()){
                            throw new Exception(loListDatos.msTabla+"  "+loResult.getMensaje() + " (" + loListDatos.getFields().toString() + ")");
                        }
                        mlSustituciones++;
                    }
                }while(loListDatos.moveNext() && !mbCancelado);
                
            }
            lbContinuar=false;
            //si se ha recuperado menos registros de lo normal es q ya no hay mas
            if(loListDatos.size()<(lMax-lMin)){
                lbContinuar=false;
            }else{
                lMax+=JPasarDatos.mclNumRegDefecto;
                lMin+=JPasarDatos.mclNumRegDefecto;
            }
        }
        }finally{
             moServer.setIntervaloDatos(0, 0);
        }
    }

    public String getTituloRegistroActual() {
        return msTablaActual;
    }

    public void mostrarMensaje(String psMensaje) {
        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, ""
                + "Sustituciones: " + String.valueOf(mlSustituciones)
                + "posibles palabras mal \n"
                + msCaracteresPosibles.toString()
        , IMostrarPantalla.mclMensajeInformacion
        , null);
    }

    @Override
    public void mostrarError(Throwable e) {
        super.mostrarError(e);
        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, ""
                + "Sustituciones: " + String.valueOf(mlSustituciones)
                + "posibles palabras mal \n"
                + msCaracteresPosibles.toString()
                , IMostrarPantalla.mclMensajeError
                , null);
    }

    
    private String procesarCadena(String psValor) {
        String lsResult = psValor;
//        if(lsResult.indexOf("N√?MERO")>=0 ){
//            System.out.println(lsResult);
//        }
        boolean lbPatron2 = false;
        if(moPatrones.moveFirst()){
            do{
                int lPosi = lsResult.indexOf(moPatrones.getFields(0).getString());
                if(lPosi>=0){
                    lsResult = lsResult.replace(moPatrones.getFields(0).getString()
                            , moPatrones.getFields(1).getString());
                    lbPatron2=moPatrones.getFields(0).getString().length()<=2;
                }
            }while(moPatrones.moveNext());
        }
        
        if(!lsResult.equals(psValor) && lbPatron2){
            System.out.println(psValor+"-->"+lsResult);
        }else{
            //posibles
            for(int i = 0 ; i < lsResult.length(); i++){
                char lcC = lsResult.charAt(i);
                if(msBuenos.indexOf(lcC)<0){
                    System.out.println("posible: "+lsResult);
                    msCaracteresPosibles.append(lsResult);
                    msCaracteresPosibles.append('\n');
                }
            }
        }
        return lsResult;
    }


}
