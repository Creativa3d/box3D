/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.cargaMasiva;

import ListDatos.IResultado;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.estructuraBD.JFieldDef;
import utiles.JCadenas;

import utiles.JDepuracion;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;
import utilesGUIx.panelesGenericos.JConsulta;


public class JCargaMasiva extends JProcesoAccionAbstracX {
    private final JListDatos moList;
    private final String msCarga;
    private int mlRegistros=-1;
    private String[] masDatos=null;
    private final JListDatos moErrores;
    private ActionListenerCZ moAccionTerminada;
    private boolean mbEditar;
    


    public JCargaMasiva(JListDatos poList, String psCarga) throws CloneNotSupportedException{
        this(poList, psCarga, false);
    }
    public JCargaMasiva(JListDatos poList, String psCarga, boolean pbEditar) throws CloneNotSupportedException{
        moList=poList;
        moErrores = new JListDatos(moList, false);
        moErrores.getFields().addField(new JFieldDef("Error"));
        msCarga=psCarga;
        mbEditar=pbEditar;
    }
    public void setAccionListenerTerminada(ActionListenerCZ poAccion){
        moAccionTerminada=poAccion;
    }

    public String getTitulo() {
        return "Carga masiva";
    }

    public int getNumeroRegistros() {
        return mlRegistros;
    }

    public void procesar() throws Throwable {

        masDatos = JFilaDatosDefecto.moArrayDatos(msCarga+'\n', '\n');
        mlRegistros = masDatos.length;
        for(mlRegistroActual=0; mlRegistroActual<masDatos.length; mlRegistroActual++){
            if(!JCadenas.isVacio(masDatos[mlRegistroActual])){
                String[] lasFila = JFilaDatosDefecto.moArrayDatos(masDatos[mlRegistroActual]+'\t', '\t');
                JFilaDatosDefecto loFila = new JFilaDatosDefecto(lasFila);
                moList.addNew();
                moList.getFields().cargar(loFila);
                IResultado loResult = moList.update(true);
                if(!loResult.getBien()){
                    if(mbEditar){
                        moList.clear();
                        loFila.setTipoModif(JListDatos.mclEditar);
                        moList.add(loFila);
                        loResult = moList.updateBatch();
                        if(!loResult.getBien()){
                            loFila.addCampo(loResult.getMensaje());
                            moErrores.add(loFila);
                        }
                    }else{
                        loFila.addCampo(loResult.getMensaje());
                        moErrores.add(loFila);
                    }
                }
            }
        }
        if(moAccionTerminada!=null){
            moAccionTerminada.actionPerformed(new ActionEventCZ(this, 0, msCarga));
        }
        mbFin=true;
    }

    public String getTituloRegistroActual() {
        if(mlRegistroActual>=0 && masDatos!=null && mlRegistroActual<masDatos.length){
            return masDatos[mlRegistroActual];
        }else{
            return "";
        }
    }

    public void mostrarMensaje(String psMensaje) {
        if(moErrores.size()>0){
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, "Hay errores", IMostrarPantalla.mclMensajeError, null);

            JSTabla loTabla = new JSTabla() {};
            loTabla.moList = moErrores;
            JBusqueda loBus = new JBusqueda(new JConsulta(loTabla, true), "Errores");
            
            try {
                loBus.mostrarFormPrinci();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }else{
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, psMensaje, IMostrarPantalla.mclMensajeInformacion, null);
        }

    }




}
