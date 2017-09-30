/*
* JActualizarEstr.java
*
* Creado el 5/3/2009
*/

package utilesGUIx.aplicacion.actualizarEstruc;

import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosBD;
import ListDatos.estructuraBD.JTableDefs;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesBD.comparadorBD.JComparadorBD;
import utilesBD.comparadorBD.JDiferencia;
import utilesBD.estructuraBD.JConstructorEstructuraBDTablas;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;

public class JActualizarEstruc {
    private IActualizarEstruc moActEstruc;
    
    private boolean mbActualizador = false; //para eliminar cuando esté el actualizador automático
    private final IServerServidorDatos moServer;
    private boolean mbMensajes = true;
    private String msMensajesResultado = "";
    //por defecto NO usar recuperar tableDefs pq tarda mucho y no presenta beneficios, ya q solo se comparan las tablas desde el programa
    private boolean mbUsarGetTableDefs = false;
    private boolean mbIgnorarDiferenciaBooleanInteger=true;
    
    /** Creates a new instance of JActualizarEstr */
    public JActualizarEstruc(IActualizarEstruc poActEstruc, IServerServidorDatos poServer) {
        moActEstruc = poActEstruc;
        moServer=poServer;
    }
    
//    private IServerServidorDatos getServerOrigen() throws Exception{
//        JServerServidorDatos loServer = new JServerServidorDatos(
//                JServerServidorDatos.mclTipoInternetComprimido, 
//                JDatosGeneralesP.getDatosGenerales().getDireccionServerTPV(),
//                "selectDatos.ctrl", "guardarDatos.ctrl"
//                );
//        JDatosGeneralesP.getDatosGenerales().hacerLoginServidor(loServer, "eduardo", "");
//        JConstructorEstructuraBDInternet loConstr = new JConstructorEstructuraBDInternet(loServer);
//        loServer.setConstrucEstruc(loConstr);
//        return loServer;
//    }

    public static JTableDefs getTables(JTableDefs loTablasOrigen, IServerServidorDatos poServer) throws Exception{
        return getTables(loTablasOrigen, poServer, true);
    }
    public static JTableDefs getTables(JTableDefs loTablasOrigen, IServerServidorDatos poServer, boolean pbUsarGetTableDefs) throws Exception{
        JTableDefs loTablas=null;
        if(loTablasOrigen!=null){
            IServerServidorDatos loServer = poServer;
            //si se usa pbUsarGetTableDefs o no es asignable a una bd directa
            //es decir, la recuperacion de estruc a traves de bd directa puede ser costosa
            //por lo q las aplicaciones normalemente quieren comparar estruc a traves de JConstructorEstructuraBDTablas por lo q
            //pbUsarGetTableDefs sera false, esta variable(pbUsarGetTableDefs) se pasa de ella si no es una conexion de bd directa
            //por lo q el Servidor no es ni JServerServidorDatosBD ni JServerServidorDatos+tipo bd
            if(pbUsarGetTableDefs || 
              !(JServerServidorDatosBD.class.isAssignableFrom(loServer.getClass()) ||
               (JServerServidorDatos.class.isAssignableFrom(loServer.getClass()) && ((JServerServidorDatos) loServer).getTipo() == JServerServidorDatos.mclTipoBD )
               )){
                try{
                    loTablas = loServer.getTableDefs();
                }catch(Throwable e){

                }
            }
            //si no es capaz de leer laestruc normalmente, usamos JConstructorEstructuraBDTablas que va haciendo select de cada
            //tabla comprobando los campos y si existe la tabla
            if((loTablas == null || loTablas.getListaTablas().size()==0) &&
               (JServerServidorDatosBD.class.isAssignableFrom(loServer.getClass())||
                JServerServidorDatos.class.isAssignableFrom(loServer.getClass()))){
                JServerServidorDatosBD loServerBD;
                if(JServerServidorDatosBD.class.isAssignableFrom(loServer.getClass())){
                    loServerBD = (JServerServidorDatosBD) loServer;
                } else{
                    loServerBD = ((JServerServidorDatos) loServer).getServerBD();
                }
                String[] lasTablas = new String[loTablasOrigen.getListaTablas().size()];
                for(int i = 0 ; i < loTablasOrigen.getListaTablas().size(); i++){
                    lasTablas[i] = loTablasOrigen.get(i).getNombre();
                }

                JConstructorEstructuraBDTablas loConstruc =
                        new JConstructorEstructuraBDTablas(
                            loServerBD,
                            lasTablas);
                loTablas = loConstruc.getTableDefs();
            }
        }
        return loTablas;
    }
    
    public void setActualizador(boolean pbActualizador){
        mbActualizador = pbActualizador;
    }
    public void setMensajes(boolean pbMensajes){
        mbMensajes = pbMensajes;
    }
    
    public void actualizar() throws Exception{
        
        JTableDefs loTablasOrigen =  moActEstruc.getTablasOrigen();
        JTableDefs loTablas = getTables(loTablasOrigen, moServer, mbUsarGetTableDefs);

        corregirdiferencias(loTablasOrigen,loTablas);
        
        try{
            moActEstruc.postProceso(moServer);
            if(mbMensajes){
                //mientras no se usa el actualizador automático
                if (mbActualizador){
                    JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeFlotante(null, "Se ha actualizado la estructura correctamente");
                } else {
                    JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, "Se ha actualizado la estructura correctamente", IMostrarPantalla.mclMensajeInformacion, null);
                }
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            msMensajesResultado="Se ha actualizado la estructura correctamente pero no se han podido actualizar los datos ("+e.toString()+") ";
            if (mbActualizador){
                JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeFlotante(null,msMensajesResultado);
            } else {
                JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, msMensajesResultado, IMostrarPantalla.mclMensajeInformacion, null);
            }
        }
    }
    private void corregirdiferencias(JTableDefs loTablasOrigen , JTableDefs loTablas) throws Exception{
        if(loTablasOrigen!=null){
            JComparadorBD loComp = new JComparadorBD(
                    loTablasOrigen,
                    loTablas,
                    null,
                    moServer);
            loComp.setIgnorarDiferenciaBooleanInteger(mbIgnorarDiferenciaBooleanInteger);
            loComp.ejecutar();
            IListaElementos loListaDif = loComp.getDiferencias();
            for(int i = 0 ; i < loListaDif.size() ; i++ ){
                JDiferencia loDif = (JDiferencia)loListaDif.get(i);
                if((loDif.getTipoModificacion()==loDif.mclTipoNoExiste ||
                    loDif.getTipoModificacion()==JDiferencia.mclTipoModificacion) &&
                   (loDif.getTipoEstructura()==loDif.mclCampo 
                    || loDif.getTipoEstructura()==loDif.mclTabla 
                    || loDif.getTipoEstructura()==loDif.mclIndice
                    || loDif.getTipoEstructura()==loDif.mclRelacion
                        )){
                    try{
                        JDepuracion.anadirTexto(
                                JDepuracion.mclINFORMACION,
                                getClass().getName(),
                                loDif.getEstructura()+ " -> " + loDif.getDiferencia());
                        loDif.arreglarDiferencia();

                    }catch(Exception e){
                        JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), e.toString());
    //                    utilesGUIx.msgbox.JMsgBox.mensajeError(new JLabel(), e.toString());
                    }
                }
            }
        }
    }
    
    public static void actualizarDatosTabla(final JSTabla poOrigen, final JSTabla poDestino) throws Exception{
        poOrigen.recuperarTodosNormal(false);
        poDestino.recuperarTodosNormal(false);
        if(poOrigen.moList.moveFirst()){
            do{
                if(!poDestino.moList.buscar(
                        JListDatos.mclTIgual,
                        poOrigen.moList.getFields().malCamposPrincipales(),
                        poOrigen.moList.getFields().masCamposPrincipales()
                        )){
                    poDestino.moList.addNew();
                }
                poDestino.moList.getFields().cargar(poOrigen.moList.moFila());
                poDestino.moList.update(true);
            }while(poOrigen.moList.moveNext());
        }
    }
    public String getMensajesResultado(){
        return msMensajesResultado;
    }

    /**
     * @return the mbUsarGetTableDefs
     */
    public boolean isUsarGetTableDefs() {
        return mbUsarGetTableDefs;
    }

    /**
     * @param mbUsarGetTableDefs the mbUsarGetTableDefs to set
     */
    public void setUsarGetTableDefs(boolean mbUsarGetTableDefs) {
        this.mbUsarGetTableDefs = mbUsarGetTableDefs;
    }
    
    
}
