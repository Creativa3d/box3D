/*
* JActualizarEstr.java
*
* Creado el 29/8/2007
*/

package utilesGUIxAvisos.tablasExtend;

import ListDatos.IServerServidorDatos;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import utiles.JDepuracion;
import utilesBD.estructuraBD.JEstructuraBDUtil;
import utilesGUIx.ColorCZ;
import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;
import utilesGUIxAvisos.tablas.JTCUENTASCORREO;
import utilesGUIxAvisos.tablas.JTGUIXAVISOS;
import utilesGUIxAvisos.tablas.JTGUIXCALENDARIO;
import utilesGUIxAvisos.tablas.JTGUIXEVENTOS;
import utilesGUIxAvisos.tablas.JTGUIXEVENTOSPRIORIDAD;
import utilesGUIxAvisos.tablas.JTGUIXMENSAJESBD;

public class JActualizarEstrucGUIXAvisos implements IActualizarEstruc {
    private static final long serialVersionUID = 110005L;


    /** Creates a new instance of JActualizarEstr */
    public JActualizarEstrucGUIXAvisos() {
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


    @Override
    public JTableDefs getTablasOrigen() throws Exception {


        JTableDefs loTablasOrigen = new JTableDefs();
        loTablasOrigen.add(new JTableDef(
                JTGUIXAVISOS.msCTabla, JTableDef.mclTipoTabla,
                JTGUIXAVISOS.masNombres, JTGUIXAVISOS.malCamposPrincipales,
                JTGUIXAVISOS.malTipos, JTGUIXAVISOS.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTGUIXEVENTOS.msCTabla, JTableDef.mclTipoTabla,
                JTGUIXEVENTOS.masNombres, JTGUIXEVENTOS.malCamposPrincipales,
                JTGUIXEVENTOS.malTipos, JTGUIXEVENTOS.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTGUIXEVENTOSPRIORIDAD.msCTabla, JTableDef.mclTipoTabla,
                JTGUIXEVENTOSPRIORIDAD.masNombres, JTGUIXEVENTOSPRIORIDAD.malCamposPrincipales,
                JTGUIXEVENTOSPRIORIDAD.malTipos, JTGUIXEVENTOSPRIORIDAD.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTGUIXCALENDARIO.msCTabla, JTableDef.mclTipoTabla,
                JTGUIXCALENDARIO.masNombres, JTGUIXCALENDARIO.malCamposPrincipales,
                JTGUIXCALENDARIO.malTipos, JTGUIXCALENDARIO.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTGUIXMENSAJESBD.msCTabla, JTableDef.mclTipoTabla,
                JTGUIXMENSAJESBD.masNombres, JTGUIXMENSAJESBD.malCamposPrincipales,
                JTGUIXMENSAJESBD.malTipos, JTGUIXMENSAJESBD.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTCUENTASCORREO.msCTabla, JTableDef.mclTipoTabla, 
                JTCUENTASCORREO.masNombres, JTCUENTASCORREO.malCamposPrincipales, 
                JTCUENTASCORREO.malTipos, JTCUENTASCORREO.malTamanos)
                );
        return loTablasOrigen;
    }

    @Override
    public void postProceso(IServerServidorDatos poServer) throws Exception {

        addIndices(poServer);
        
        //añadimos un calendario si no existe ninguno
        JTGUIXCALENDARIO loCalendarios = new JTGUIXCALENDARIO(poServer);
        loCalendarios.recuperarTodosNormalSinCache();
        if(!loCalendarios.moveFirst()){
            loCalendarios.addNew();
            loCalendarios.getCALENDARIO().setValue("1");
            loCalendarios.getNOMBRE().setValue("Calendario general");
            loCalendarios.update(true);
        }

        
        //añadimos prioridades basicas si no existe ninguna
        JTEEGUIXEVENTOSPRIORIDAD loPri = new JTEEGUIXEVENTOSPRIORIDAD(poServer);
        loPri.recuperarTodosNormalSinCache();
        if(!loPri.moveFirst()){
            utilesGUIxAvisos.tablasExtend.JActualizarEstrucGUIXAvisos.addPrioridad(loPri, "1", "Baja", ColorCZ.BLUE.getColor());
            utilesGUIxAvisos.tablasExtend.JActualizarEstrucGUIXAvisos.addPrioridad(loPri, "50", "Media", ColorCZ.ORANGE.getColor());
            utilesGUIxAvisos.tablasExtend.JActualizarEstrucGUIXAvisos.addPrioridad(loPri, "100", "Alta", ColorCZ.RED.getColor());
        }                      
    }
    

    public static void addIndices(IServerServidorDatos poServer){
        try {
            JTableDef loAvisos = new JTableDef(
                        JTGUIXAVISOS.msCTabla, JTableDef.mclTipoTabla,
                        JTGUIXAVISOS.masNombres, JTGUIXAVISOS.malCamposPrincipales,
                        JTGUIXAVISOS.malTipos, JTGUIXAVISOS.malTamanos);
            JEstructuraBDUtil.addIndice(loAvisos, JTGUIXAVISOS.getFECHAMODIFICACIONNombre(), poServer);
            JEstructuraBDUtil.addIndice(loAvisos, JTGUIXAVISOS.getAVISADOSNNombre(), poServer);
            
            JTableDef loMensajes = new JTableDef(
                        JTGUIXMENSAJESBD.msCTabla, JTableDef.mclTipoTabla,
                        JTGUIXMENSAJESBD.masNombres, JTGUIXMENSAJESBD.malCamposPrincipales,
                        JTGUIXMENSAJESBD.malTipos, JTGUIXMENSAJESBD.malTamanos);
            JEstructuraBDUtil.addIndice(loMensajes, JTGUIXMENSAJESBD.getGRUPONombre(), poServer);
            JEstructuraBDUtil.addIndice(loMensajes, JTGUIXMENSAJESBD.getFOLDERNombre(), poServer);
            JEstructuraBDUtil.addIndice(loMensajes, JTGUIXMENSAJESBD.getIDMENSAJENombre(), poServer);
            JEstructuraBDUtil.addIndice(loMensajes, JTGUIXMENSAJESBD.getFECHANombre(), poServer);

            JTableDef loEventos = new JTableDef(
                        JTGUIXEVENTOS.msCTabla, JTableDef.mclTipoTabla,
                        JTGUIXEVENTOS.masNombres, JTGUIXEVENTOS.malCamposPrincipales,
                        JTGUIXEVENTOS.malTipos, JTGUIXEVENTOS.malTamanos);
            
            JEstructuraBDUtil.addIndice(loEventos, JTGUIXEVENTOS.getFECHAMODIFICACIONNombre(), poServer);
            JEstructuraBDUtil.addIndice(loEventos, JTGUIXEVENTOS.getGRUPONombre(), poServer);
            JEstructuraBDUtil.addIndice(loEventos, JTGUIXEVENTOS.getIDENTIFICADORSERIENombre(), poServer);
            JEstructuraBDUtil.addIndice(loEventos, JTGUIXEVENTOS.getIDENTIFICADOREXTERNONombre(), poServer);
            JEstructuraBDUtil.addIndice(loEventos, JTGUIXEVENTOS.getFECHADESDENombre(), poServer);
            JEstructuraBDUtil.addIndice(loEventos, JTGUIXEVENTOS.getEVENTOSNNombre(), poServer);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(JActualizarEstrucGUIXAvisos.class.getName(), ex);
        }

        
    }
    public static void addPrioridad(JTEEGUIXEVENTOSPRIORIDAD loPrioridad, String psCod, String psNombre, int plRGB) throws Exception{
        loPrioridad.addNew();
        loPrioridad.getGUIXPRIORIDAD().setValue(psCod);
        loPrioridad.getCOLOR().setValue(plRGB);
        loPrioridad.getNOMBRE().setValue(psNombre);
        loPrioridad.update(true);
    }
}
