/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.pasarDatos;

import ListDatos.IResultado;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IMostrarPantalla;

public class JProcesoPasarDatos extends JProcesoAccionAbstracX {
    private final String[] masTablas;
    private int mlTotal;
    private int mlTransaccionCada=0;
    private final JPasarDatos moPasar;
    private String msTablaActual="";
    private final int mlAPartirDe;
    private final boolean mbIgnoraErrores;
    private StringBuffer msErrores = new StringBuffer();
    private JDateEdu moDateInicio;
    private IListaElementos<JListDatos> moListaJListDatos = new JListaElementos<JListDatos>();
    private boolean mbCerrarBD2=false;
    
    public JProcesoPasarDatos(JPasarDatos poPasar,String[] pasTablas, int plAPartirDe, boolean pbIgnoraErrores){
        masTablas=pasTablas;
        mlRegistroActual=0;
        moPasar=poPasar;
        mlAPartirDe=plAPartirDe;
        mbIgnoraErrores=pbIgnoraErrores;
    }
    public void add(JListDatos poList){
        moListaJListDatos.add(poList);
    }
    public void setTransaccionCada(int plTransaccionCada){
        mlTransaccionCada=plTransaccionCada;
    }
    public void setCerrarBD2(boolean pbCerrarB2){
        mbCerrarBD2=pbCerrarB2;
    }

    public String getTitulo() {
        return "Pasar datos";
    }

    public int getNumeroRegistros() {
        return mlTotal;
    }

    public void procesar() throws Throwable {
        moDateInicio = new JDateEdu();
        mlTotal=0;
        mlRegistroActual=mlAPartirDe;
        for(int i = 0 ; i < masTablas.length && !mbCancelado;i++){
            String lsTabla = masTablas[i];
            mlTotal += moPasar.getRegistrosTablaBD1(lsTabla);
        }
        for(JListDatos loAux : moListaJListDatos){
            mlTotal += loAux.size();
        }
        if(mlTotal>5000 && JDepuracion.mlNivelDepuracion == JDepuracion.mclINFORMACION){
            JDepuracion.mlNivelDepuracion = JDepuracion.mclWARNING;
        }
        for(int i = 0 ; i < masTablas.length && !mbCancelado;i++){
            igualar(masTablas[i]);
        }
        for(JListDatos loAux : moListaJListDatos){
            if(!mbCancelado){
                JListDatos loListDatos2 = new JListDatos(loAux, false);
                loListDatos2.moServidor = moPasar.getServerBD2();
                loListDatos2.ensureCapacity(loAux.size() + 1);
                igualar(loAux, loListDatos2);
            }
        }
        if(mbCerrarBD2){
            moPasar.getServerBD2().close();
        }
        mbFin=true;
    }

    public synchronized void igualar(JListDatos loListDatos, JListDatos loListDatos2) throws Exception {
        msTablaActual=loListDatos.msTabla;
        System.gc();
        
        int lReg = 0;
        if (loListDatos.moveFirst() && !mbCancelado) {
            do {
                mlRegistroActual++;
                lReg++;
                loListDatos2.addNew();

                for (int i = 0; i < loListDatos.getFields().count(); i++) {
                    JFieldDef loCampo = loListDatos2.getFields().get(loListDatos.getFields(i).getNombre());
                    if(loCampo!=null){
                        try{
                            loCampo.setValue(loListDatos.getFields(i).getString());
                        }catch(Exception e){
                            JDepuracion.anadirTexto(getClass().getName(), e);
                            if(!mbIgnoraErrores){
                                throw e;
                            }
                        }
                    }
                }
                if (mlTransaccionCada > 1) {
                    loListDatos2.update(false);
                    if (lReg >= mlTransaccionCada) {
                        lReg = 0;
                        IResultado loResult = loListDatos2.updateBatch();
                        if (!loResult.getBien()) {
                            if (mbIgnoraErrores) {
                                msErrores.append(loResult.getMensaje());
                                msErrores.append('\n');
                            } else {
                                throw new Exception(loResult.getMensaje());
                            }
                        }
                        loListDatos2.clear();
                        loListDatos2.ensureCapacity(mlTransaccionCada);
                    }
                } else {
                    lReg = 0;
                    IResultado loResult = loListDatos2.update(true);
                    if (!loResult.getBien()) {
                        if (mbIgnoraErrores) {
                            msErrores.append(loResult.getMensaje());
                            msErrores.append('\n');
                        } else {
                            throw new Exception(loResult.getMensaje());
                        }
                    }
                    loListDatos2.clear();
                       
                }
            } while (loListDatos.moveNext() && !mbCancelado);
        }
        if (mlTransaccionCada > 1 && lReg > 0) {
            loListDatos2.update(false);
            lReg = 0;
            IResultado loResult = loListDatos2.updateBatch();
            if (!loResult.getBien()) {
                if (mbIgnoraErrores) {
                    msErrores.append(loResult.getMensaje());
                    msErrores.append('\n');
                } else {
                    throw new Exception(loResult.getMensaje());
                }
            }
            loListDatos2.clear();
        }
        System.gc();
    }
    public synchronized void igualar(String psTabla) throws Exception {
        msTablaActual=psTabla;
        try{
        boolean lbContinuar = true;
        int lMin = mlAPartirDe;
        int lMax = mlAPartirDe+moPasar.getNumReg();
        while(lbContinuar && !mbCancelado){
            moPasar.getServerBD1().setIntervaloDatos(lMin, lMax);
            JListDatos loListDatos = new JListDatos(
                moPasar.getServerBD1(), psTabla,
                moPasar.getTable1(psTabla).getCampos().msNombres(),
                moPasar.getTable1(psTabla).getCampos().malTipos(),
                moPasar.getTable1(psTabla).getCampos().malCamposPrincipales(),
                moPasar.getTable1(psTabla).getCampos().malTamanos()
                );
            loListDatos.ensureCapacity(moPasar.getNumReg()+1);
            loListDatos.recuperarDatos(loListDatos.getSelect(),false,loListDatos.mclSelectNormal);
            System.gc();
            JListDatos loListDatos2 = new JListDatos(
                moPasar.getServerBD2(), psTabla,
                moPasar.getTable2(psTabla).getCampos().msNombres(),
                moPasar.getTable2(psTabla).getCampos().malTipos(),
                moPasar.getTable2(psTabla).getCampos().malCamposPrincipales(),
                moPasar.getTable2(psTabla).getCampos().malTamanos());
            loListDatos2.ensureCapacity(loListDatos.size()+1);
            
            igualar(loListDatos, loListDatos2);

                    
            System.gc();
            //si se ha recuperado menos registros de lo normal es q ya no hay mas
            if(loListDatos.size()<(lMax-lMin)){
                lbContinuar=false;
            }else{
                lMax+=moPasar.getNumReg();
                lMin+=moPasar.getNumReg();
            }
        }
        }finally{
            moPasar.getServerBD1().setIntervaloDatos(0, 0);
        }
    }

    public String getTituloRegistroActual() {
        return msTablaActual;
    }

    public void mostrarMensaje(String psMensaje) {
        try {
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, 
                    "Tiempo transcurrido " + String.valueOf(JDateEdu.diff(JDateEdu.mclMinutos, new JDateEdu(), moDateInicio)) +"\n"
                    +(msErrores.length()> 0 
                        ? msErrores.substring(0, msErrores.length()>5000?5000:msErrores.length())
                        : psMensaje)
                    , IMostrarPantalla.mclMensajeInformacion
                    , null);
        } catch (Exception ex) {
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, 
                    (msErrores.length()> 0 
                        ? msErrores.substring(0, msErrores.length()>5000?5000:msErrores.length())
                        : psMensaje)
                    , IMostrarPantalla.mclMensajeInformacion
                    , null);
        }
    }


}
