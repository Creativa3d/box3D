/*
 * JPanelBusqueda.java
 *
 * Created on 16 de septiembre de 2004, 10:38
 */
package utilesGUIx.panelesGenericos;



import ListDatos.*;
import ListDatos.estructuraBD.*;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.edicion.ITextBD;
import utilesGUIx.msgbox.JMsgBox;

/**Componete para presentar un codigo y su descripcion*/
public class JPanelBusqueda extends LinearLayout implements ITextBD {
    //indica si se bloquea o no el lostfocus del texto

    protected boolean mbBloq = false;
    private String msValorCodigo = "";
    private String msValorCodigoOriginal = "";
    protected JPanelBusquedaParametros moParam;
    private JFieldDef[] moCampos;

    private TextView lblDescripcion;
    private Button btnAnadir;
    
    /**Constructo*/
    public JPanelBusqueda(Context poContext) {
        super(poContext);
        lblDescripcion = new TextView(poContext);
        btnAnadir = new Button(poContext);
        addView(lblDescripcion);
//        addView(btnAnadir);
    }

    public void setDatos(JPanelBusquedaParametros poParam) {
        moParam = poParam;
        moParam.inicializarPlugIn();
        btnAnadir.setVisibility((moParam.moControlador != null ? Button.VISIBLE : Button.INVISIBLE));
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales Lista de posiciones de campos principales
     * @param palDescripciones Lista de posiciones de campos para la descripcin
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pasTextosDescripciones Lista de textos previos a las descripciones
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos, String[] pasTextosDescripciones) {
        JPanelBusquedaParametros loParam = new JPanelBusquedaParametros();

        loParam.moControlador = poControlador;
        loParam.moTabla = poTabla;
        loParam.mlCamposPrincipales = plCamposPrincipales;
        loParam.malDescripciones = palDescripciones;
        loParam.mbMensajeSiNoExiste = pbMensajeSiNoExiste;
        loParam.masTextosDescripciones = pasTextosDescripciones;
        loParam.mbConDatos = pbConDatos;

        setDatos(loParam);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales Lista de posiciones de campos principales
     * @param palDescripciones Lista de posiciones de campos para la descripcin
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
        setDatos(poControlador, poTabla, plCamposPrincipales, palDescripciones, pbMensajeSiNoExiste, false, null);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripcin
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     * @param pasTextosDescripciones Lista de textos previos a las descripciones
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos, String[] pasTextosDescripciones) {
        setDatos(poControlador, poTabla, new int[]{plCampoPrincipal}, palDescripciones, pbMensajeSiNoExiste, pbConDatos, pasTextosDescripciones);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripcin
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     * @param pasTextosDescripciones Lista de textos previos a las descripciones
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, String[] pasTextosDescripciones) {
        setDatos(poControlador, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false, pasTextosDescripciones);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripcin
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos) {
        setDatos(poControlador, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, pbConDatos, null);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripcin
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
        setDatos(poControlador, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales Lista posiciones de campos principales
     * @param palDescripciones Lista de posiciones de campos para la descripcin
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCamposPrincipales, palDescripciones, pbMensajeSiNoExiste);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param palDescripcion posicin de la descripcin
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales posicin del campo principal
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int[] plCamposPrincipales, int palDescripcion, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCamposPrincipales, new int[]{palDescripcion}, pbMensajeSiNoExiste);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones y una clave
     * @param plCampoPrincipal posicin del campo principal
     * @param palDescripciones Lista de posiciones de las descripciones
     * @param poTabla Tabla a mostrar
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false);
    }

    /**
     * establecemos una tabla relacionada con una sola descripcion y una clave
     * @param plCampoPrincipal posicin del campo principal
     * @param palDescripcion posicin de la descripcin
     * @param poTabla Tabla a mostrar
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int plCampoPrincipal, int palDescripcion, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCampoPrincipal, new int[]{palDescripcion}, pbMensajeSiNoExiste, false);
    }

//    /**
//     * Aade un listener aadir
//     * @param l oyente
//     */
//    public void addActionListenerBuscar(ActionListenerBuscar l) {
//        btnBuscar.addActionListener(new ActionListenerWrapper(null, l));
//    }
//
//    /**
//     * Borra un listener aadir
//     * @param l oyente
//     */
//    public void removeActionListenerBuscar(ActionListenerBuscar l) {
//        btnBuscar.removeActionListener(new ActionListenerWrapper(null, l));
//    }
//
//    /**
//     * Aade un listener aadir
//     * @param l oyente
//     */
//    public void addActionListenerAnadir(ActionListenerAnadir l) {
//        btnAnadir.addActionListener(new ActionListenerWrapper(l, null));
//    }
//
//    /**
//     * borra un listener aadir
//     * @param l oyente
//     */
//    public void removeActionListenerAnadir(ActionListenerAnadir l) {
//        btnAnadir.removeActionListener(new ActionListenerWrapper(l, null));
//    }

    public void setVisibleAnadir(boolean pbAnadir) {
        btnAnadir.setVisibility(pbAnadir ? Button.VISIBLE : Button.INVISIBLE);
    }

    public boolean isVisibleAnadir() {
        return btnAnadir.getVisibility()==Button.VISIBLE ;
    }

    /**
     * establece el valor en el campo cdigo y tambien busca la descripcion
     * si pbOriginal es true se pone como valor y al mismo tiempo como valor original y se vera en negro
     * si pbOriginal es false Si el valor es diferente al valor actual se pondra en rojo 
     * @param poValor valor
     */
    public void setValueConOriginal(Object poValor, boolean pbOriginal) {
        if (poValor == null) {
            msValorCodigo = "";
        } else {
            msValorCodigo = poValor.toString();
        }
        if (moParam != null
                && moParam.mlCamposPrincipales != null
                && moParam.mlCamposPrincipales.length == 1) {
            if (msValorCodigo.length() > 0
                    && msValorCodigo.charAt(msValorCodigo.length() - 1) == JFilaDatosDefecto.mccSeparacion1) {
                msValorCodigo = msValorCodigo.substring(0, msValorCodigo.length() - 1);
            }
            if(pbOriginal){
                msValorCodigoOriginal=msValorCodigo;
            }
        } else {
            if (msValorCodigo.indexOf(JFilaDatosDefecto.mccSeparacion1) >= 0) {
                if(pbOriginal){
                    msValorCodigoOriginal=msValorCodigo;
                }
            } else {
                if(pbOriginal){
                    msValorCodigoOriginal=msValorCodigo;
                }
            }
        }
        txtCodigoFocusLost();
    }
    /**
     * establece el valor en el campo cdigo y tambien busca la descripcion
     * @param poValor valor
     */
    public void setValueTabla(Object poValor) {
        setValueConOriginal(poValor, true);
    }
    /**
     * establece el valor en el campo cdigo y tambien busca la descripcion
     * Si el valor es diferente al valor actual se pondra en rojo
     * @param poValor valor
     */
    public void setText(Object poValor) {
        setValueConOriginal(poValor, false);
    }

    public void lanzarBusqueda() {
        buscar();
    }

    /**
     * Devuelve el valor
     * @return valor
     */
    public Object getValueTabla() {
        return getText();
    }

    /**
     * Devuelve el texto
     * @return texto
     */
    public String getText() {
        return msValorCodigo;
    }

    /**
     * Devuelve el texto del campo plIndex, este sirve para campos compuestos
     * @return texto
     * @param plIndex ndice del campo a devolver
     */
    public String getText(int plIndex) {
        return new JFilaDatosDefecto(msValorCodigo).msCampo(plIndex);
    }

    /**
     * Establece la descripcin, para hacer bsquedas manuales
     * @param psValor descripcin
     */
    public void setDescripcion(String psValor) {
        lblDescripcion.setText(psValor);
    }

    /**
     * Devuelve la descricin actual
     * @return descricin
     */
    public String getDescripcion() {
        return lblDescripcion.getText().toString();
    }




    /**Establece si esta habilitado*/
    @Override
    public void setEnabled(boolean pbValor) {
        super.setEnabled(pbValor);
        btnAnadir.setEnabled(pbValor);
    }

    private void anadir() {
        try {
            if (moParam.moControlador == null) {
                JMsgBox.mensajeInformacion(this.getContext(), "No se puede editar datos");
            } else {
                //probamos a llamar al metodo del controlador
                try {
                    if (moParam.mbEdicionLista) {
                        moParam.moControlador.mostrarFormPrinci();
                        busquedaIndice(moParam.moControlador.getIndex());
                    } else {
                        JListDatos loDatosEdicion = null;
                        loDatosEdicion = moParam.moControlador.getConsulta().getList();
                        try {
                            if (msValorCodigo.equals("")) {
                                moParam.moControlador.anadir();
                            } else {
                                IFilaDatos loFila = new JFilaDatosDefecto();
                                //buscamos el codigo
                                JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                                int lPrinci = 0;
                                for (int lIndex = 0; lIndex < loDatosEdicion.getFields().count(); lIndex++) {
                                    if (loDatosEdicion.getFields(lIndex).getPrincipalSN()) {
                                        String lsValor = "";
                                        if (moParam.mlCamposPrincipales.length == 1) {
                                            lsValor = msValorCodigo;
                                        } else {
                                            lsValor = (new JFilaDatosDefecto(msValorCodigo).msCampo(lPrinci));
                                            lPrinci++;
                                        }
                                        loFila.addCampo(lsValor);
                                        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,
                                                JListDatos.mclTIgual, lIndex,
                                                lsValor);
                                    } else {
                                        loFila.addCampo("");
                                    }
                                }

                                loDatosEdicion.getFiltro().Clear();
                                loDatosEdicion.filtrarNulo();
                                loDatosEdicion.getFiltro().addCondicion(
                                        JListDatosFiltroConj.mclAND, loFiltro);
                                loDatosEdicion.filtrar();
                                if (loDatosEdicion.moveFirst()) {
                                    JListDatosBookMark loBook = loDatosEdicion.getBookmark();
                                    loDatosEdicion.getFiltro().clear();
                                    loDatosEdicion.filtrarNulo();
                                    loDatosEdicion.setBookmark(loBook);
                                    moParam.moControlador.editar(loDatosEdicion.getIndex());
                                } else {
                                    loDatosEdicion.getFiltro().clear();
                                    loDatosEdicion.filtrarNulo();

                                    loFila.setTipoModif(JListDatos.mclNuevo);
                                    moParam.moControlador.getConsulta().addFilaPorClave(loFila);
                                    loDatosEdicion.moveLast();

                                    moParam.moControlador.editar(loDatosEdicion.getIndex());
                                }
                            }
                        } finally {
                            if (loDatosEdicion != null) {
                                loDatosEdicion.getFiltro().clear();
                                loDatosEdicion.filtrarNulo();
                            }
                        }


                    }
                } catch (Exception e) {
                    JDepuracion.anadirTexto(getClass().getName(), e);
                    JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Presentacin de form estandar");

                    JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(moParam.moControlador);
//
//                    JFormGenerico loForm = new JFormGenerico(moParam.moControlador);
//                    loForm.show();
                }
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
        }
    }

    protected void buscar() {
        if (moParam.moTabla != null) {
            JListDatosFiltroConj loFiltro = moParam.moTabla.moList.getFiltro().Clone();
            try {
                mbBloq = true;
                JBusqueda loBusq = new JBusqueda(new JConsulta(moParam.moTabla, moParam.mbConDatos), moParam.moTabla.moList.msTabla);
                loBusq.mlAlto = moParam.mlAlto;
                loBusq.mlAncho = moParam.mlAncho;
                loBusq.mbFiltro = moParam.mbFiltro;
                loBusq.getParametros().msTipoFiltroRapido = moParam.msTipoFiltroRapido;
                loBusq.getParametros().setColoresTabla(moParam.moColores);
                loBusq.mostrarBusq(new CallBack<IPanelControlador>() {

                    public void callBack(IPanelControlador poControlador) {
                        busquedaIndice(poControlador.getIndex());
                    }
                });


            } catch (Exception e) {
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
            } finally {
                if (loFiltro.mbAlgunaCond()) {
                    moParam.moTabla.moList.getFiltro().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
                }
                mbBloq = false;
            }
        }
    }

    protected void busquedaIndice(int plIndex) {

        if (plIndex >= 0) {
            moParam.moTabla.moList.setIndex(plIndex);
            if (moParam.mlCamposPrincipales.length == 1) {
                msValorCodigo = moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[0]).getString();
   
            } else {
                StringBuffer lsCad = new StringBuffer();
                for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                    lsCad.append(moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[i]).getString());
                    lsCad.append(JFilaDatosDefecto.mcsSeparacion1);
                }
                msValorCodigo = lsCad.toString();
            }
            mostrarDescripcion();
        }

    }

    public JPanelBusquedaParametros getParam() {
        return moParam;
    }

    //mostramos la descripcion
    public void mostrarDescripcion() {
        StringBuffer lsCadena = new StringBuffer();
        for (int i = 0; i < moParam.malDescripciones.length; i++) {
            if (moParam.masTextosDescripciones != null) {
                if (moParam.masTextosDescripciones[i] != null) {
                    lsCadena.append(moParam.masTextosDescripciones[i]);
                    lsCadena.append(' ');
                }
            }
            if (moParam.mbTrim) {
                lsCadena.append(moParam.moTabla.moList.getFields().get(moParam.malDescripciones[i]).getString().trim());
            } else {
                lsCadena.append(moParam.moTabla.moList.getFields().get(moParam.malDescripciones[i]).getString());
            }
            lsCadena.append(' ');
        }
        //establecemos la descripcion
        lblDescripcion.setText(lsCadena.toString());
    }


    private void txtCodigoFocusLost() {
        JListDatosFiltroConj loFiltroAux = null;
        //si existe una tabla asociada
        if (moParam != null && moParam.moTabla != null) {
            try {
                if (moParam.moTabla.getSelect() != null && moParam.moTabla.getSelect().getWhere().mbAlgunaCond()) {
                    loFiltroAux = moParam.moTabla.getSelect().getWhere().Clone();
                }

                if (!mbBloq) {
                    String lsCodigo = "";
                    for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                        lsCodigo += JFilaDatosDefecto.mcsSeparacion1;
                    }
                    //si el cdigo es nulo ponemos la descripcion a ""
                    if (msValorCodigo == null || (msValorCodigo.compareTo("") == 0) || (msValorCodigo.compareTo(lsCodigo) == 0)) {
                        lblDescripcion.setText("");
                    } else {
                        //buscamos el codigo
                        String[] loVAlores = new String[moParam.mlCamposPrincipales.length];

                        if (moParam.mlCamposPrincipales.length == 1) {
                            loVAlores[0] = msValorCodigo;
                        } else {
                            for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                                loVAlores[i] = (new JFilaDatosDefecto(msValorCodigo).msCampo(i));
                            }
                        }

                        if (!moParam.moTabla.moList.buscar(JListDatos.mclTIgual, moParam.mlCamposPrincipales, loVAlores)) {
                            if (!moParam.mbConDatos) {
//								if (moParam.moTabla.getSelect().getWhere().mbAlgunaCond()) {
//									loFiltroAux = moParam.moTabla.getSelect().getWhere().Clone();
//								}
                                JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                                loFiltro.addCondicion(JListDatosFiltroConj.mclAND,
                                        JListDatos.mclTIgual, moParam.mlCamposPrincipales,
                                        loVAlores);
                                moParam.moTabla.recuperarFiltradosNormal(loFiltro, false);
                                if (!moParam.moTabla.moList.moveFirst()) {
                                    //si no existre presentamos un mensaje en caso de que el prog. lo quiera
                                    if (moParam.mbMensajeSiNoExiste) {
                                        utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this.getContext(), "El codigo no existe en la tabla relacionada");
                                        if(moParam.mbRecuperarFocoSinNoExiste){
                                        }
                                    }
                                    //descripcion a vacio
                                    lblDescripcion.setText("");
                                } else {
                                    mostrarDescripcion();
                                }
                            } else {
                                //si no existre presentamos un mensaje en caso de que el prog. lo quiera
                                if (moParam.mbMensajeSiNoExiste) {
                                    utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this.getContext(), "El codigo no existe en la tabla relacionada");
                                    if(moParam.mbRecuperarFocoSinNoExiste){

                                    }
                                }
                                //descripcion a vacio
                                lblDescripcion.setText("");
                            }
                        } else {
                            //si existe un registro, creamos la descripcion
                            mostrarDescripcion();
                        }
                    }
                }
            } catch (Exception e) {
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
            } finally {
//                moParam.moTabla.moList.getFiltro().Clear();
//                moParam.moTabla.moList.filtrarNulo();
                if (moParam.moTabla.getSelect() != null) {
                    moParam.moTabla.getSelect().getWhere().Clear();
                    if (loFiltroAux != null) {
                        moParam.moTabla.getSelect().getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltroAux);
                    }
                }
            }
        }
    }
    /**Establecemos el campo de la BD*/
    public void setField(final JFieldDef poCampo) {
        setFields(new JFieldDef[]{poCampo});
    }

    /**Establecemos el campo de la BD*/
    public void setFields(final JFieldDef[] poCampos) {
        moCampos = poCampos;
    }

    /**Devolvemos el campo de la BD*/
    public JFieldDef[] getCampos() {
        return moCampos;
    }

    /**Mostramos los datos del campo de BD guardado*/
    public void mostrarDatosBD() {
        if (moCampos != null) {
            StringBuffer lasString = new StringBuffer();
            for (int i = 0; i < moCampos.length; i++) {
                lasString.append(moCampos[i].getString());
                lasString.append(JFilaDatosDefecto.mccSeparacion1);
            }
            setValueTabla(lasString.toString());
        }
    }

    /**Establecemos los datos de campo de BD guardado*/
    public void establecerDatosBD() throws ECampoError {
        if (moCampos != null) {
            for (int i = 0; i < moCampos.length; i++) {
                moCampos[i].setValue(getText(i));
            }
        }
    }
}
