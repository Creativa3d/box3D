/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.controlProcesos;

import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUI.procesar.IProcesoAccion;

public class JProcesoManejador implements IProcesoThreadGroup {
    private final IListaElementos moLista = new JListaElementos();
    private final IListaElementos moListeners = new JListaElementos();
    private final IProcesoElementoFactoryMethod moFactoria;
    
    public JProcesoManejador(IProcesoElementoFactoryMethod poFactoria){
        moFactoria=poFactoria;
    }
//    public synchronized void addProcesoSwingYEjecutar( final IProcesoAccion poProceso ) {
//        addProcesoSwingYEjecutar(poProceso,true);
//    }
//    public synchronized void addProcesoSwingYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm) {
//        addProcesoYEjecutar(poProceso,pbConMostrarForm,true);
//    }
    public synchronized void addProcesoYEjecutar(final IProcesoAccion poProceso) {
        addProcesoYEjecutar(poProceso, true);
    }
    public synchronized void addProcesoYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm) {
        addProcesoYEjecutar(poProceso, pbConMostrarForm, false);
    }
    private synchronized void addProcesoYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm, boolean pbIsSwing) {
        IProcesoElemento loElem = moFactoria.getProcesoElemento(poProceso,pbConMostrarForm, this);
//        if( pbIsSwing )
//            loElem.arrancarSwing();
//        else
            loElem.arrancar();
        moLista.add(loElem);
        llamarListeners();
    }

    public synchronized void procesoFinalizado(IProcesoElemento poProcesoControl, Throwable moError){
        moLista.remove(poProcesoControl);
        llamarListeners();
        System.gc();
        JDepuracion.anadirTexto(
                JDepuracion.mclINFORMACION,
                getClass().getName(),
                "Memoria libre " + String.valueOf(Runtime.getRuntime().freeMemory())
                );

    }
    private void llamarListeners(){
        for(int i = 0 ; i < moListeners.size(); i++){
            IProcesoThreadGroupListener loLis = (IProcesoThreadGroupListener)moListeners.get(i);
            loLis.cambioProcesos(this);
        }
    }

    public IListaElementos getListaElementos() {
        return moLista;
    }
    public synchronized IListaElementos getListaProcesos() {
        IListaElementos loLista = new JListaElementos();
        for(int i = 0 ; i < moLista.size(); i++){
            IProcesoElemento loElem = (IProcesoElemento) moLista.get(i);
            loLista.add(loElem.getProceso());
        }
        return loLista;
    }
    public synchronized boolean isProcesosActivos(){
        return moLista.size()>0;
    }
    public synchronized int getProcesoTotal(){
        int lParte=0;
        int ltotal=0;
//        if(moLista.size()>0){
//            lParte = 90 / moLista.size();
//        }else{
//            lParte = 90;
//            ltotal=100;
//        }
        for(int i = 0; i < moLista.size(); i++){
            IProcesoElemento loControl = (IProcesoElemento)moLista.get(i);

            if(loControl.getProceso().getNumeroRegistros()<=0){
                ltotal+=10;
                lParte+=5;
            }else{
                lParte+=loControl.getProceso().getNumeroRegistro();
                ltotal+=loControl.getProceso().getNumeroRegistros();
            }
        }
        if(lParte>ltotal){
            lParte=ltotal;
        }
        if(moLista.size()==0){
            ltotal=100;
        }

        return  (int)((double)(lParte*100) / (double)ltotal);
    }

    public synchronized int getIndice(Object elemento){
        int lResult = -1;
        for(int i = 0; i < moLista.size() && lResult<0; i++){
            Object loControl = moLista.get(i);

            if(loControl==elemento){
                lResult=i;
            }
        }
        return lResult;
    }

    public void addListener(IProcesoThreadGroupListener poListener) {
        moListeners.add(poListener);
    }

    public void removeListener(IProcesoThreadGroupListener poListener) {
        moListeners.remove(poListener);
    }
    public static boolean isProcesosMostrarForm(IListaElementos poElementos){
        boolean lbMostrarForm = false;
        for(int i = 0; i < poElementos.size() && !lbMostrarForm; i++){
            IProcesoElemento loControl = (IProcesoElemento)poElementos.get(i);
            lbMostrarForm = loControl.isConMostrarForm();
        }
        return lbMostrarForm;
    }

    public synchronized String getProcesoTexto() {
        String lsResult = "";
        if(isProcesosActivos()){
            
            for(int i = 0 ; i <moLista.size(); i++){
                IProcesoElemento loControl = (IProcesoElemento)moLista.get(i);
                String lsTitulo = loControl.getProceso().getTitulo();
                String lsTituloReg = loControl.getProceso().getTituloRegistroActual();
                if(lsTitulo.equalsIgnoreCase(lsTituloReg)){
                    lsResult += lsTitulo + "; ";
                }else{
                    lsResult += lsTitulo + " - " + lsTituloReg + "; ";
                }
            }
            
        }

        return lsResult;

    }

}
