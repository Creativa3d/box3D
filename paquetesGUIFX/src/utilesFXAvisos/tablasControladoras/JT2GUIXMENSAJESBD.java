/*
* JT2GUIXMENSAJESSEND.java
*
* Creado el 8/9/2012
*/
package utilesFXAvisos.tablasControladoras;

import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;

import ListDatos.*;
import utiles.IListaElementos;
import utilesFX.JFXConfigGlobal;
import utilesFXAvisos.forms.JPanelMensajeFX;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;




import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.tablasControladoras.JT2GUIXMENSAJESBDModelo;

public class JT2GUIXMENSAJESBD  extends JT2GUIXMENSAJESBDModelo {
    public static final String mcsResponder = "Responder";
    public static final String mcsReenviar = "Reenviar";    
    /** Crea una nueva instancia de JT2GUIXMENSAJESSEND */
    public JT2GUIXMENSAJESBD(JGUIxAvisosDatosGenerales poDatosGenerales) {
        super(poDatosGenerales);
    }

    public JT2GUIXMENSAJESBD(
            JGUIxAvisosDatosGenerales poDatosGenerales,
            String psNomTabRelac,
            IFilaDatos poDatosRelac) {
        super(poDatosGenerales, psNomTabRelac, poDatosRelac);
    }
    public JT2GUIXMENSAJESBD(JGUIxAvisosDatosGenerales poDatosGenerales, JGUIxAvisosCorreo poCorreo, String psCarpeta) {
        super(poDatosGenerales, poCorreo, psCarpeta);
        JPanelGeneralBotones retValue = getParametros().getBotonesGenerales();
        IListaElementos loBotones = retValue.getListaBotones();
        for(int i = 0 ; i < loBotones.size(); i++){
            JBotonRelacionado loboton = (JBotonRelacionado) loBotones.get(i);
            if(loboton.getNombre().equalsIgnoreCase(mcsRecibirMensajes)){
                loboton.setIcono(JFXConfigGlobal.getImagenCargada("/utilesFX/images/mail_get16.png"));
            }
        }
        retValue.getNuevo().setIcono(JFXConfigGlobal.getImagenCargada("/utilesFX/images/mail-message-new16.png"));
        retValue.getEditar().setIcono(JFXConfigGlobal.getImagenCargada("/utilesFX/images/mail-queue16.png"));
        retValue.getEditar().setCaption("Ver");
        retValue.getBorrar().setIcono(JFXConfigGlobal.getImagenCargada("/utilesFX/images/Delete16.gif"));
        
        JBotonRelacionado loBoton = new JBotonRelacionado(mcsResponder, this);
        loBoton.setIcono(JFXConfigGlobal.getImagenCargada("/utilesFX/images/mail-reply-sender16.png"));
        retValue.addBotonPrincipal(loBoton);
        
        loBoton = new JBotonRelacionado(mcsReenviar, this);
        loBoton.setIcono(JFXConfigGlobal.getImagenCargada("/utilesFX/images/mail-forward16.png"));
        retValue.addBotonPrincipal(loBoton);
        
    }
    
    @Override
    public void anadir() throws Exception {
        JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = new JTEEGUIXMENSAJESBD(moServer);
        loGUIXMENSAJESSEND.moList.addNew();
        valoresDefecto(loGUIXMENSAJESSEND);

        JPanelMensajeFX loPanel = new JPanelMensajeFX();
        loPanel.setDatos(loGUIXMENSAJESSEND.getMensaje(), null, JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().getPathPlantilla()
                , null, null, true
        );

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    @Override
    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        super.valoresDefecto(poTabla);
        
    }

    @Override
    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = new JTEEGUIXMENSAJESBD(moServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,JListDatos.mclTIgual, 
            new int[]{
                JTEEGUIXMENSAJESBD.lPosiCODIGO
            },
            new String[]{
                moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getString()
            });
        loGUIXMENSAJESSEND.recuperarFiltrados(loFiltro,false,false);
        if(loGUIXMENSAJESSEND.getESTADO().getInteger() != JTEEGUIXMENSAJESBD.mclLEIDOYCLASIFICADO){
            if(loGUIXMENSAJESSEND.getESTADO().getInteger() == JTEEGUIXMENSAJESBD.mclNOLEIDOYCLASIFICADO
                    || !loGUIXMENSAJESSEND.getGRUPO().isVacio()){
                loGUIXMENSAJESSEND.getESTADO().setValue(JTEEGUIXMENSAJESBD.mclLEIDOYCLASIFICADO);
            } else {
                loGUIXMENSAJESSEND.getESTADO().setValue(JTEEGUIXMENSAJESBD.mclLEIDO);
            }
            loGUIXMENSAJESSEND.update(true);
            datosactualizados(loGUIXMENSAJESSEND.moList.moFila());
        }
        valoresDefecto(loGUIXMENSAJESSEND);
        JPanelMensajeFX loPanel = new JPanelMensajeFX();
        loPanel.setDatos(loGUIXMENSAJESSEND.getMensaje(), null, null, null, null, false);
        loPanel.getParametros().setSoloLectura(true);
        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    @Override
    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        super.actionPerformed(e, plIndex); //To change body of generated methods, choose Tools | Templates.
        if(plIndex.length>0){
            for(int i = 0 ; i < plIndex.length; i++){
                moConsulta.moList.setIndex(plIndex[i]);
                if(mcsReenviar.equalsIgnoreCase(e.getActionCommand())){

                    JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = JTEEGUIXMENSAJESBD.getTabla(
                            moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getString()
                            , moServer);
                    JPanelMensajeFX loPanel = new JPanelMensajeFX();
                    loPanel.setDatos(loGUIXMENSAJESSEND.getMensaje().getReenviar(), null, null, null, null, true);
                    getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);

                }
                if(mcsResponder.equalsIgnoreCase(e.getActionCommand())){

                    JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = JTEEGUIXMENSAJESBD.getTabla(
                            moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getString()
                            , moServer);
                    JPanelMensajeFX loPanel = new JPanelMensajeFX();
                    loPanel.setDatos(loGUIXMENSAJESSEND.getMensaje().getResponder(), null, null, null, null, true);
                    getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);

                }
            }
        }
        
    }
    

}