/*
* JT2GUIXMENSAJESSEND.java
*
* Creado el 8/9/2012
*/
package utilesGUIxAvisos.tablasControladoras;

import utilesGUIxAvisos.consultas.JTFORMGUIXMENSAJESBD;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;
import utilesGUIx.ActionEventCZ;

import ListDatos.*;
import utiles.JDepuracion;
import utilesGUI.procesar.JProcesoAccionAbstrac;
import utilesGUIx.ColorCZ;
import utilesGUIx.JGUIxConfigGlobalModelo;


import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;
import utilesGUIxAvisos.avisos.JGUIxAvisosCorreo;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;



public abstract class JT2GUIXMENSAJESBDModelo  extends JT2GENERALBASEModelo {
    public static final String mcsRecibirMensajes = "Recibir";

    protected final JTFORMGUIXMENSAJESBD moConsulta;
    protected final IServerServidorDatos moServer;
    protected IFilaDatos moFilaDatosRelac;
    protected String msNomTabRelac = "";
    protected final JGUIxAvisosDatosGenerales moDatosGenerales;
    protected JGUIxAvisosCorreo moCorreo;
    protected String msCarpeta;

    /** Crea una nueva instancia de JT2GUIXMENSAJESSEND
     * @param poDatosGenerales */
    public JT2GUIXMENSAJESBDModelo(JGUIxAvisosDatosGenerales poDatosGenerales) {
        super();
        moDatosGenerales=poDatosGenerales;
        
        moServer = poDatosGenerales.getServer();
        moConsulta = new JTFORMGUIXMENSAJESBD(moServer);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
//        Format[] loFormat = new Format[moConsulta.mclNumeroCampos];
//        loFormat[moConsulta.lPosiTEXTO] = new FormatHTML();
//        getParametros().setFormatosCampos(loFormat);        
        moParametros.setNombre(JTEEGUIXMENSAJESBD.msCTabla);
        moParametros.setTitulo(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(JTEEGUIXMENSAJESBD.msCTabla));
        getParametros().setMostrarPantalla(JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla());
        colorear();
    }
    public JT2GUIXMENSAJESBDModelo(JGUIxAvisosDatosGenerales poDatosGenerales, JGUIxAvisosCorreo poCorreo, String psCarpeta) {
        super();
        moDatosGenerales=poDatosGenerales;
        moCorreo = poCorreo;
        msCarpeta = psCarpeta;
        moServer = poDatosGenerales.getServer();
        moConsulta = new JTFORMGUIXMENSAJESBD(moServer);
        moConsulta.crearSelect(poCorreo, psCarpeta);
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
//        Format[] loFormat = new Format[moConsulta.mclNumeroCampos];
//        loFormat[moConsulta.lPosiTEXTO] = new FormatHTML();
//        getParametros().setFormatosCampos(loFormat);        
        moParametros.setNombre(JTEEGUIXMENSAJESBD.msCTabla+psCarpeta);
        moParametros.setTitulo(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(JTEEGUIXMENSAJESBD.msCTabla)+ "/"+psCarpeta);
        getParametros().setMostrarPantalla(JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla());

        if(!JTEEGUIXMENSAJESBD.mcsENVIADOS.equalsIgnoreCase(psCarpeta)){
            colorear();
        }
    }

    private void colorear(){
        JListDatosFiltroConj loElem = new JListDatosFiltroConj();
        loElem.addCondicionAND(
                JListDatos.mclTIgual, JTFORMGUIXMENSAJESBD.lPosiESTADO, "");
        loElem.addCondicionOR(
                JListDatos.mclTIgual, JTFORMGUIXMENSAJESBD.lPosiESTADO, String.valueOf(JTEEGUIXMENSAJESBD.mclNOLEIDOYCLASIFICADO));
        loElem.inicializar(JTFORMGUIXMENSAJESBD.getNombreTabla(), JTFORMGUIXMENSAJESBD.getFieldsEstaticos().malTipos(), JTFORMGUIXMENSAJESBD.getFieldsEstaticos().msNombres());
        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
                loElem,
                ColorCZ.ORANGE,
                null); 
        loElem = new JListDatosFiltroConj();
        loElem.addCondicion(JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual, JTFORMGUIXMENSAJESBD.lPosiESTADO, String.valueOf(JTEEGUIXMENSAJESBD.mclLEIDOYCLASIFICADO));
        loElem.inicializar(JTFORMGUIXMENSAJESBD.getNombreTabla(), JTFORMGUIXMENSAJESBD.getFieldsEstaticos().malTipos(), JTFORMGUIXMENSAJESBD.getFieldsEstaticos().msNombres());
        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
                loElem,
                null,
                ColorCZ.BLUE);        
    }
    public JT2GUIXMENSAJESBDModelo(
            JGUIxAvisosDatosGenerales poDatosGenerales,
            String psNomTabRelac,
            IFilaDatos poDatosRelac) {
        this(poDatosGenerales);
        setTablaRelacionada(psNomTabRelac, poDatosRelac);
    }

    public void setTablaRelacionada(
            String psNomTabRelac,
            IFilaDatos poDatosRelac){
        msNomTabRelac = psNomTabRelac;
        moFilaDatosRelac = poDatosRelac;
        moConsulta.crearSelect(msNomTabRelac, moFilaDatosRelac);
    }
    @Override
    public IConsulta getConsulta() {
        return moConsulta;
    }

    public JTFORMGUIXMENSAJESBD getConsultaO() {
        return moConsulta;
    }

    @Override
    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }

    @Override
    public abstract void anadir() throws Exception;

    @Override
    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.getList().getModoTabla()==JListDatos.mclNuevo){
            ((JTEEGUIXMENSAJESBD)poTabla).getEMAILFROM().setValue(moCorreo.getEnviar().getCorreo());
        }
    }

    @Override
    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = new JTEEGUIXMENSAJESBD(moServer);
        loGUIXMENSAJESSEND.moList.addNew();
        loGUIXMENSAJESSEND.getCODIGO().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getValue());
        loGUIXMENSAJESSEND.moList.update(false);

        loGUIXMENSAJESSEND.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loGUIXMENSAJESSEND.moList.moFila().clone();
        IResultado loResult = loGUIXMENSAJESSEND.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    @Override
    public abstract void editar(final int plIndex) throws Exception;

    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMGUIXMENSAJESBD.mclNumeroCampos];

        loInt[JTFORMGUIXMENSAJESBD.lPosiCODIGO]=0;
        loInt[JTFORMGUIXMENSAJESBD.lPosiGRUPO]=0;
        loInt[JTFORMGUIXMENSAJESBD.lPosiUSUARIO]=0;
        loInt[JTFORMGUIXMENSAJESBD.lPosiFECHA]=160;
        loInt[JTFORMGUIXMENSAJESBD.lPosiEMAILTO]=250;
        loInt[JTFORMGUIXMENSAJESBD.lPosiASUNTO]=358;
//        loInt[JTFORMGUIXMENSAJESBD.lPosiTEXTO]=0;
        loInt[JTFORMGUIXMENSAJESBD.lPosiADJUNTOS]=80;
        return loInt;
    }

    @Override
    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if(e.getActionCommand().equalsIgnoreCase(mcsRecibirMensajes)){
            JGUIxConfigGlobalModelo.getInstancia().getThreadGroup()
                    .addProcesoYEjecutar(new JProcesoAccionAbstrac() {
                @Override
                public String getTitulo() {
                    return mcsRecibirMensajes + " " + moCorreo.getLeer().getServidor();
                }

                @Override
                public int getNumeroRegistros() {
                    return -1;
                }

                @Override
                public void procesar() throws Throwable {
                    
                    moCorreo.recibirYGuardar();
                }

                @Override
                public void mostrarMensaje(String psMensaje) {
                    try {
                        refrescar();
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(JT2GUIXMENSAJESBDModelo.class.getName(), ex);
                    }
                }

                @Override
                public void mostrarError(Throwable e) {
                    JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(JT2GUIXMENSAJESBDModelo.this, e, null);
                }
            }, false);
        } else {
            
        }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
//        retValue.getNuevo().setActivo(false);
//        retValue.getBorrar().setActivo(false);
        retValue.getRefrescar().setActivo(false);
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsRecibirMensajes, this));

        
    }


}