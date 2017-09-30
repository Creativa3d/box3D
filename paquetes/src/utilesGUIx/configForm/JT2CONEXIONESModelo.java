/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package utilesGUIx.configForm;

import ListDatos.IFilaDatos;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JSelectMotor;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosInternetLogin;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utilesBD.estructuraBD.JConstructorEstructuraBDConnection;
import utilesBD.estructuraBD.JConstructorEstructuraBDInternet;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.JT2GENERALBASEModelo;
import utilesGUIx.formsGenericos.busqueda.IConsulta;

public class JT2CONEXIONESModelo extends JT2GENERALBASEModelo {
    private static final String mcsProbar = "Probar";

    private final JTFORMCONEXIONES moConsulta;
    private final IMostrarPantalla moMostrarPantalla;

    private JConexionLista moListaConex;
    private JConexionIO moIO;
    private final ICONEXIONESMostrar moConexMostrar;

    /** Crea una nueva instancia de JT2ATRIBUTOSDEF */
    public JT2CONEXIONESModelo(final JDatosGeneralesXML poDatosGeneralesXML, final IMostrarPantalla poMostrarPantalla, final ICONEXIONESMostrar poConexMostrar) throws Exception {
        super();
        moMostrarPantalla = poMostrarPantalla;
        moIO = new JConexionIO();
        moIO.setLector(poDatosGeneralesXML);
        moListaConex = moIO.leerListaConexiones();
        moConsulta = new JTFORMCONEXIONES(moListaConex);
        addBotones();
        getParametros().setLongitudCampos(getLongitudCampos());
        getParametros().setPlugInPasados(true);
        getParametros().setMostrarPantalla(moMostrarPantalla);
        moConexMostrar=poConexMostrar;
    }

    public IConsulta getConsulta() {
        return moConsulta;
    }
    public JConexionIO getIO() {
        return moIO;
    }
    public JConexionLista getListaConex(){
        return moListaConex;
    }
    public void mostrarFormPrinci() throws Exception {
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(this, 800,600, 1, IMostrarPantalla.mclEdicionFrame);
//        loParam.setCallBack(new CallBack<JMostrarPantallaParam>() {
//            public void callBack(JMostrarPantallaParam poControlador) {
//                try {
//                    moIO.guardarListaConexiones(moListaConex);
//                } catch (Exception ex) {
//                    moMostrarPantalla.mensajeErrorYLog(null, ex, null);
//                }
//            }
//        });
        moMostrarPantalla.mostrarForm(loParam);
    }
    public void mostrarFormPrinci(final CallBack<IPanelControlador> poCall) throws Exception {
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(this, 800,600, 1, IMostrarPantalla.mclEdicionFrame);
        loParam.setCallBack(new CallBack() {
            public void callBack(Object poControlador) {
                try {
//                    moIO.guardarListaConexiones(moListaConex);
                    poCall.callBack(JT2CONEXIONESModelo.this);
                } catch (Exception ex) {
                    moMostrarPantalla.mensajeErrorYLog(null, ex, null);
                }
            }
        });
        moMostrarPantalla.mostrarForm(loParam);
    }

    public void anadir() throws Exception {
        JConexion loConex = new JConexion();
        
        moConexMostrar.mostrar(loConex, this, moMostrarPantalla);
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        moListaConex.delete(moConsulta.getList().getFields(moConsulta.lPosiNombre).getString());

        IFilaDatos loFila = (IFilaDatos) moConsulta.moList.moFila().clone();
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JConexion loConex = moListaConex.get(moConsulta.getList().getFields(moConsulta.lPosiNombre).getString());
        loConex.leerConfig();
        moConexMostrar.mostrar(loConex, this, moMostrarPantalla);
    }
    public void addConexion(JConexion poConexion) throws Exception {
        //transformamos en ifila
        IFilaDatos loFila = JTFORMCONEXIONES.getFilaDeConex(poConexion);
        //conexigomos la conexion previa
        JConexion loAux = moListaConex.get(poConexion.getNombre());

        if(loAux==null){
            //si no existe es nuevo
            loFila.setTipoModif(JListDatos.mclNuevo);
        }else{
            //si existe es editar y se borra
            loFila.setTipoModif(JListDatos.mclEditar);
            moListaConex.delete(poConexion.getNombre());
        }
        //se añade la conexion
        moListaConex.add(poConexion);
        //se guarda las conexiones
        moIO.guardarListaConexiones(moListaConex);
        //actualizamos datos de la tabla
        refrescar();
        
    }

    public String getNombre() {
        return "Conexiones";
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMCONEXIONES.mclNumeroCampos];
        loInt[JTFORMCONEXIONES.lPosiNombre ]=80;
        loInt[JTFORMCONEXIONES.lPosiTipo]=80;
        return loInt;
    }

    public void actionPerformed(utilesGUIx.ActionEventCZ e, int[] plIndex) throws Exception {
            if(plIndex.length>0){
                if(e.getActionCommand().equals(mcsProbar)){
                    for(int i = 0 ; i < plIndex.length; i++){
                        moConsulta.moList.setIndex(plIndex[i]);
                        JConexion loConex = moListaConex.get(moConsulta.getList().getFields(moConsulta.lPosiNombre).getString());
                        loConex.probar();
                    }
                    moMostrarPantalla.mensaje(null, "Conexiones probadas correctamente", IMostrarPantalla.mclMensajeInformacion, null);
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;

        retValue = getParametros().getBotonesGenerales();

//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsProbar, mcsProbar, "", this));

    }

    public void datosactualizados(IFilaDatos poFila) throws Exception {
        super.datosactualizados(poFila);
    }
    public static IServerServidorDatos getServer(JConexion poConex) throws Throwable{
        IServerServidorDatos loResult = null;        
        if(poConex.getConexion().getTipoConexion()==JServerServidorDatos.mclTipoBD 
                && poConex.getConexion().getTipoBD() == JSelectMotor.mclAccess){
            JServerServidorDatos loServer = null;        
            poConex.leerConfig();
            try{
                loServer = new JServerServidorDatos(poConex.getConexion());
            }catch(RuntimeException e1){
                //en linux no funciona
                JDepuracion.anadirTexto(JT2CONEXIONESModelo.class.getName(), e1);
                loServer = new JServerServidorDatos();
            }catch(Throwable e){
                //en linux no funciona
                JDepuracion.anadirTexto(JT2CONEXIONESModelo.class.getName(), e);
                loServer = new JServerServidorDatos();
            }
            
            loServer.setTipo(JServerServidorDatos.mclTipoBD);
            loResult = loServer;

        }else{

            JServerServidorDatos loServer;

            if(poConex.getConexion().getTipoConexion()==JServerServidorDatos.mclTipoBD){
                loServer = new JServerServidorDatos(poConex.getConexion());
                JConstructorEstructuraBDConnection loEstr = new JConstructorEstructuraBDConnection(loServer.getServerBD(), true, true);
                loServer.setConstrucEstruc(loEstr);
            } else {
                loServer = new JServerServidorDatos(poConex.getConexion());
                JServerServidorDatosInternetLogin loLogin = new JServerServidorDatosInternetLogin(
                        poConex.getConexion().getUSUARIO(), poConex.getConexion().getPASSWORD());
                loServer.setLogin(loLogin);
                if(!loLogin.autentificar()){
                    throw new Exception("usuario incorrecto");
                }
                JConstructorEstructuraBDInternet loEstr = new JConstructorEstructuraBDInternet(loServer);
                loServer.setConstrucEstruc(loEstr);
            }
            loResult = loServer;
            
        }
        return loResult;
    }

}
