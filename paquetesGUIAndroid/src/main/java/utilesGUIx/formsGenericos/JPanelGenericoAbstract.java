/*
 * JPanelConsultaPrincipal.java
 *
 * Created on 13 de septiembre de 2006, 18:29
 */
package utilesGUIx.formsGenericos;

import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ITableCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JTableCZ;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.msgbox.JMsgBox;
import ListDatos.JListDatos;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public abstract class JPanelGenericoAbstract extends LinearLayout implements IPanelGenerico, ActionListenerCZ {

    private static final long serialVersionUID = 1L;
    protected IPanelControlador moControlador;
    protected JTableModelDatosConFiltro moModeloDatos;
    protected JListDatos moDatos;
    protected final IListaElementos moBotones = new JListaElementos();
    protected boolean mbAnularEventos = true;
    private boolean mbEsBusqueda = false;
    //carga segundo plano
    protected Thread moThread;
    protected boolean mbEjecutando = false;
    private IListaElementos moListener = new JListaElementos();

    private JPanelGeneralFiltro moPanelGeneralFiltro1;
    private ISalir moSalir;

    private PopupMenu moMenu;
	protected JActividadDefecto moActividad;
    /**
     * Creates new form JPanelBusqueda
     */
    public JPanelGenericoAbstract(Context poCont) {
        super(poCont);

    }

    /**
     * Inicializa, se usa en el contructor de las clases hijas
     */
    public void inicializar() {

        
        moPanelGeneralFiltro1 = new JPanelGeneralFiltro();

        if(getBtnNuevo()!=null){
            getBtnNuevo().setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    accionNuevo();
                }
            });
        }
        
        getTabla().addOnClickListenerCELL(new OnClickListener() {
            public void onClick(View arg0) {
                actionPerformed(new ActionEventCZ(getTabla(), 0, ""));
            }
        });

        getPanelGeneralFiltroTodosCamp1().addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
                try{
                    moPanelGeneralFiltro1.setFiltroTodosCampos(arg0.toString());
                }catch(Exception e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                }finally{
                }      
            }
        });

        if(getBtnNuevo()!=null) {
            moMenu = new PopupMenu(getContext(), getBtnNuevo());
        }else{
            moMenu = new PopupMenu(getContext(), this);
        }
        moMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                actionPerformed(new ActionEventCZ(item, item.getItemId(), String.valueOf(item.getItemId())));
                return true;
            }
        });

        if(getPanelInformacion()!=null){
            ((ViewGroup)getPanelInformacion()).setVisibility(INVISIBLE);
        }
              

        getTabla().addOnLongClickListenerCELL(new OnLongClickListener() {

            public boolean onLongClick(View arg0) {
                menushow();
                return true;
            }
        });
    }

	public void setActividad(JActividadDefecto jActividadDefecto) {
		moActividad = jActividadDefecto;
        if(moControlador!=null) {
            moActividad.setTitle(moControlador.getParametros().getTitulo());
        }
	}    
    public boolean onCreateOptionsMenu(Menu menu) {

    	//moMenu = menu;
    	if(moControlador!=null){
	    	JPanelGeneralBotones loBotonesGenerales = moControlador.getParametros().getBotonesGenerales();
	    	moMenu.getMenu().clear();
	        establecerBotones(loBotonesGenerales, moMenu);
    	}
//        
//        // Inflate the currently selected menu XML resource.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.jformiformedicionenu, menu);
        
        return true;
    }    
    public boolean onOptionsItemSelected(MenuItem item) {
    	actionPerformed(new ActionEventCZ(item, item.getItemId(), String.valueOf(item.getItemId())));
        return true;
    }    
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if(moControlador!=null){
	    	JPanelGeneralBotones loBotonesGenerales = moControlador.getParametros().getBotonesGenerales();
	        establecerBotones(loBotonesGenerales, moMenu);
    	}
		
	}    
	public boolean onContextItemSelected(MenuItem item) {
    	actionPerformed(new ActionEventCZ(item, item.getItemId(), String.valueOf(item.getItemId())));
		return true;
	}

    public void menushow(){
        moMenu.show();
    }

    
    public int[] getSelectedRows() {
        return getTabla().getSelectedRows();               
    }

    public int getSelectedRow() {
        return getTabla().getSelectedRow();
    }
    
    /**
     * Devuelve el componente JPanelGeneralFiltro de la clase hija
     */
    public utilesGUIx.formsGenericos.JPanelGeneralFiltro getPanelGeneralFiltro1(){
        return moPanelGeneralFiltro1;
    }


    /**
     * Devuelve el componente JPanelGeneralFiltroTodosCamp de la clase hija
     */
    public abstract EditText getPanelGeneralFiltroTodosCamp1();


    /**
     * Devuelve el componente Nuevo de la clase hija
     */
    public abstract Button getBtnNuevo();

    /**
     * Devuelve el componente Tabla de la clase hija
     */
    public abstract ITableCZ getTabla();

    /**
     * Establece el total en la clase hija
     */
    public abstract void setTotal(String psValor);

    /**
     * Establece la visibilidad del config y filtro rapido
     */
    public abstract void setVisiblePanelConfigyFiltroRap(boolean pbVisible);



    /**
     * Devolvemos el controlador del panel
     */
    public IPanelControlador getControlador() {
        return moControlador;
    }

    /**
     * Establecemos el controlador del panel
     *
     * @param poControlador controlador
     * @throws Exception error
     */
    public void setControlador(final IPanelControlador poControlador) throws  Throwable {
        setControlador(poControlador, null);
    }

    /**
     * Establecemos el controlador del panel
     *
     * @param poControlador controlador
     * @param poSalir clase padre, puede ser un frame, internalframe,dialog,
     * pero que cumpla el interfaz salir
     * @throws Exception error
     */
    public void setControlador(final IPanelControlador poControlador, final ISalir poSalir) throws Exception, Throwable {
        moSalir = poSalir;
        //si ya existia un controlador previo borramos el listener
        if (moControlador != null && moControlador instanceof IPanelGenericoListener) {
            removeListenerIPanelGenerico((IPanelGenericoListener) moControlador);
        }
        //establecemos el controlador
        moControlador = poControlador;
        //si el controlador es de tipo IPanelGenericoListener, a?adimos el listener
        if (moControlador instanceof IPanelGenericoListener) {
            addListenerIPanelGenerico((IPanelGenericoListener) moControlador);
        }

        if (moControlador.getParametros().mbSegundoPlano) {
            synchronized (this) {
                while (mbEjecutando) {
                    try {
                        wait(1000);
                    } catch (Throwable e) {
                    }
                }
                EjecutarPanelGenericoA loEjecutar = new EjecutarPanelGenericoA(this);
                moThread = new Thread(loEjecutar);
                moThread.start();
            }
        } else {
            recuperarYmostrarDatos();
        }
    }
//
//    protected void setFormatosCampos(Format[] paoFormatosCampos) {
//        if (paoFormatosCampos != null) {
//            TableColumnModel m = getTabla().getColumnModel();
//            for (int i = 0; i < paoFormatosCampos.length; i++) {
//                if (paoFormatosCampos[i] != null) {
//                    m.getColumn(i).setCellRenderer(
//                            new JTableRenderConColor(
//                            moModeloDatos.getColumnClass(i),
//                            getTabla(),
//                            paoFormatosCampos[i]));
//                }
//            }
//        }
//    }

    protected void recuperarYmostrarDatos() throws Throwable {
        recuperarDatosBD();
        mostrarDatos();
    }

    protected void mostrarDatos() throws Throwable {
        if (moControlador.getParametros().isActivado()) {
            try {
                if (JGUIxConfigGlobal.getInstancia().getPlugInFactoria() != null) {
                    JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInManager().procesarControlador(
                            JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto(),
                            moControlador);
                }
                //            System.out.println("recuperarDatos");
                mbAnularEventos = true;
                synchronized (this) {
                    mbEjecutando = true;
                }
                setVisiblePanelConfigyFiltroRap(moControlador.getParametros().isVisibleConfigYFiltroRap());
                //creamos los botones relacionados
                JPanelGeneralBotones loBotonesGenerales = moControlador.getParametros().getBotonesGenerales();
                moBotones.clear();
                IListaElementos loBotonesRelac = loBotonesGenerales.getListaBotones();

                //vemos si hay algun boton relacionado, es decir, no principal
                //si el boton NO es activo se borra
                for (int i = 0; loBotonesRelac != null && i < loBotonesRelac.size(); i++) {
                    IBotonRelacionado loBoton = (IBotonRelacionado) loBotonesRelac.get(i);
                    if (loBoton.isActivo()) {
                        moBotones.add(loBoton);
                    }
                }

                if(moMenu!=null){
                    moMenu.getMenu().clear();
	                establecerBotones(loBotonesGenerales, moMenu);
                }
                getTabla().setTableCZColores(moControlador.getParametros().getColoresTabla());
                
                //len indicamos el panel que le visualiza los datos
                moControlador.setPanel(this);

                //creamos el filtro
                getPanelGeneralFiltro1().setDatos(getDatos(), null, this, true, getTablaConfig());

                //visualizamos los datos 
                visualizarDatos();
                moModeloDatos.mbEditable = false;


//                setFormatosCampos(moControlador.getParametros().getFormatosCampos());

            } finally {
                synchronized (this) {
                    mbEjecutando = false;
                    notifyAll();
                }
                mbAnularEventos = false;
            }
            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclFinInicializar, "");
        }
    }

    protected void recuperarDatosBD() throws Exception {
        if (moControlador.getParametros().isActivado()) {

            if (moControlador.getParametros() == null || moControlador.getParametros().isRefrescarListDatos()) {
                moDatos = moControlador.getDatos();
            } else {
                moDatos = moControlador.getConsulta().getList();
            }
        }
    }


    protected void establecerBotones(final JPanelGeneralBotones poBotonesGenerales, PopupMenu poMenu) {
        establecerValoresBotones(getBtnNuevo(), poBotonesGenerales.getNuevo());
        getBtnNuevo().setId(JPanelGenericoEvent.mclAccionNuevo);
        if(poBotonesGenerales.getNuevo().getIcono()!=null){
            getBtnNuevo().setBackgroundDrawable((Drawable)poBotonesGenerales.getNuevo().getIcono());
        }
        if(poBotonesGenerales.getEditar().isActivo()){
            poMenu.getMenu().add(Menu.NONE ,JPanelGenericoEvent.mclAccionEditar,Menu.NONE
                    , poBotonesGenerales.getEditar().getCaption() == null ? "Editar" : poBotonesGenerales.getEditar().getCaption());
        }
        if(poBotonesGenerales.getRefrescar().isActivo()){
            poMenu.getMenu().add(Menu.NONE ,JPanelGenericoEvent.mclAccionRefrescar,Menu.NONE
                    , poBotonesGenerales.getRefrescar().getCaption() == null ? "Refrescar" : poBotonesGenerales.getRefrescar().getCaption());
        }
        if(poBotonesGenerales.getBorrar().isActivo()){
            poMenu.getMenu().add(Menu.NONE ,JPanelGenericoEvent.mclAccionBorrar,Menu.NONE
                    , poBotonesGenerales.getBorrar().getCaption() == null ? "Borrar" : poBotonesGenerales.getBorrar().getCaption());
        }
        if(poBotonesGenerales.getAceptar().isActivo()){
            poMenu.getMenu().add(Menu.NONE ,JPanelGenericoEvent.mclAccionAceptar,Menu.NONE
                    , poBotonesGenerales.getAceptar().getCaption() == null ? "Aceptar" : poBotonesGenerales.getAceptar().getCaption());
        }
        if(poBotonesGenerales.getCancelar().isActivo()){
            poMenu.getMenu().add(Menu.NONE ,JPanelGenericoEvent.mclAccionCancelar,Menu.NONE
                    , poBotonesGenerales.getCancelar().getCaption() == null ? "Cancelar" : poBotonesGenerales.getCancelar().getCaption());
        }
        
        mbEsBusqueda = poBotonesGenerales.getAceptar().isActivo();

        if (moBotones != null && moBotones.size() > 0) {

            for (int i = 0; i < moBotones.size(); i++) {
                IBotonRelacionado loBoton = (IBotonRelacionado) moBotones.get(i);
                poMenu.getMenu().add(Menu.NONE ,i,Menu.NONE, loBoton.getCaption());
            }
        }
    }

    protected void establecerValoresBotones(final Button poBoton, final IBotonRelacionado poProp) {
        poBoton.setVisibility(poProp.isActivo() ? poBoton.VISIBLE : poBoton.INVISIBLE);
        if (poProp.getCaption() != null) {
            poBoton.setText(poProp.getCaption());
        }
    }

    protected void visualizarDatos() throws Throwable {
        getPanelGeneralFiltro1().limpiar();
        //creamos el table model a partir de los datps
        moModeloDatos = new JTableModelDatosConFiltro(getDatos(), getPanelGeneralFiltro1().getTableModelFiltro());

        getTabla().setModel(moModeloDatos);
        setTotal(String.valueOf(getDatos().size()));

        //long columns
        if(moControlador.getParametros().getLongitudCampos()!=null){
        	int[] lal = moControlador.getParametros().getLongitudCampos();
        	for(int i = 0 ; i < lal.length; i++){
        		getTabla().setColumnLong(i, lal[i]); 
        	}
        }

        
    }

    /**
     * ejecuta la accion del boton
     */
    public void actionPerformed(final ActionEventCZ evt) {
        if(isEnabled()){
            boolean lbContinuar = true;
            try {
                if (evt.getActionCommand().equals(String.valueOf(JPanelGenericoEvent.mclAccionRefrescar))) {
                    lbContinuar = false;
                    if (moControlador.getConsulta() != null && moControlador.getConsulta().getList() != null && moControlador.getConsulta().getList().moServidor != null) {
                        moControlador.getConsulta().getList().moServidor.clearCache();
                    }
                    moDatos = moControlador.getDatos();
                    getPanelGeneralFiltro1().setDatos(getDatos(), null, this, true, getTablaConfig());
                    refrescar();

                }
                if (evt.getActionCommand().equals(String.valueOf(JPanelGenericoEvent.mclAccionAceptar))) {
                        lbContinuar = false;
                        accionAceptar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionAceptar));
                }
                if (evt.getActionCommand().equals(String.valueOf(JPanelGenericoEvent.mclAccionCancelar))) {
                        lbContinuar = false;
                        accionCancelar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionCancelar));
                }
                if (evt.getActionCommand().equals(String.valueOf(JPanelGenericoEvent.mclAccionEditar))) {
                        lbContinuar = false;
                        accionEditar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionEditar));
                }
                if (evt.getActionCommand().equals(String.valueOf(JPanelGenericoEvent.mclAccionNuevo))) {
                        lbContinuar = false;
                        accionNuevo();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionNuevo));
                }
                if (evt.getActionCommand().equals(String.valueOf(JPanelGenericoEvent.mclAccionBorrar))) {
                        lbContinuar = false;
                        accionBorrar();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionBorrar));
                }
                if (lbContinuar) {
                    if (evt.getActionCommand().equals(JPanelGeneralFiltro.mcsCambioFiltro)) {
                        refrescarDatos();
                        llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionCambioFiltro));
                    } else {
                        if (evt.getSource() == getTabla()) {
                            if (mbEsBusqueda) {
                                accionAceptar();
                                llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionAceptar));
                            } else {
                                if (moControlador.getParametros().getBotonesGenerales().getEditar().isActivo()) {
                                    accionEditar();
                                    llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, String.valueOf(JPanelGenericoEvent.mclAccionEditar));
                                }
                            }
                        } else {
                            IBotonRelacionado loBoton = ((IBotonRelacionado) moBotones.get(Integer.valueOf(evt.getActionCommand()).intValue()));
                            loBoton.ejecutar(getSelectedRows());
                            llamarListenerIPanelGenericoList(JPanelGenericoEvent.mclEnter, loBoton.getNombre());
                        }
                    }
                }

            } catch (Throwable e) {
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
            }
        }
    }



    public void addListenerIPanelGenerico(IPanelGenericoListener poListener) {
        moListener.add(poListener);
    }

    public void removeListenerIPanelGenerico(IPanelGenericoListener poListener) {
        moListener.remove(poListener);
    }

    protected void llamarListenerIPanelGenericoList(int plTipo, String psComando) {
        JPanelGenericoEvent loEvent = new JPanelGenericoEvent(this, plTipo, new int[]{getSelectedRow()}, psComando);
        for (int i = 0; i < moListener.size(); i++) {
            IPanelGenericoListener loListener = (IPanelGenericoListener) moListener.get(i);
            loListener.eventPanelGenerico(loEvent);
        }

    }
    ////////////////
    ///Interfaz IPanelGenerico
    ////////////////

    public void refrescarDatos() throws Exception{
        getTabla().refrescarDatos();
        setTotal(String.valueOf(getDatos().size()));
        llamarListenerIPanelGenericoList(
                JPanelGenericoEvent.mclRefrescar
                , String.valueOf(JPanelGenericoEvent.mclAccionRefrescar));
    }
    public void refrescar() throws Exception {
        getTabla().refrescarDatos();
        setTotal(String.valueOf(getDatos().size()));
        llamarListenerIPanelGenericoList(
                JPanelGenericoEvent.mclRefrescar
                , String.valueOf(JPanelGenericoEvent.mclAccionRefrescar));
    }

    public void seleccionarTodo() {
//        getTabla().selectAll();
    }

    public void seleccionarFila(int plFila, boolean pbSeleccionar) {
//        if (pbSeleccionar) {
//            getTabla().getSelectionModel().addSelectionInterval(plFila, plFila);
//        } else {
//            getTabla().getSelectionModel().removeIndexInterval(plFila, plFila);
//        }
    }

    public IListaElementos getBotones() {
        return moBotones;
    }

    public void accionCancelar() {
        try {
            moControlador.setIndexs(null);
            if (moSalir != null) {
                moSalir.salir();
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
        }
    }

    public void accionAceptar() {
        try {
            moControlador.setIndexs(getSelectedRows());
            if (moSalir != null) {
                moSalir.salir();
            }
            if(moControlador.getParametros().getCallBack()!=null){
                moControlador.getParametros().getCallBack().callBack(moControlador);
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
        }
    }


    public void accionBorrar() {
        try {
            final int[] lIndexs = getSelectedRows();
            if (lIndexs.length > 0) {
                JMsgBox.mostrarOpcion(getContext()
                    , (lIndexs.length > 1
                        ? "¿Estas seguro de borrar los registros actuales?"
                        : "¿Estas seguro de borrar el registro actual?")
                    , new Runnable() {
                        public void run() {
                            try {
                                for (int i = lIndexs.length - 1; i >= 0 ; i--) {
                                    if (lIndexs[i] >= 0
                                            && lIndexs[i] < moDatos.size()) {
                                        moDatos.setIndex(lIndexs[i]);
                                        moControlador.borrar(lIndexs[i]);
                                    }
                                }                    
                            } catch (Throwable e) {
                                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(
                                        JPanelGenericoAbstract.this.getContext()
                                        , e, getClass().getName());
                            }
                        }
                    }, null);
            } else {
                utilesGUIx.msgbox.JMsgBox.mensajeFlotante(this.getContext(), "No existe una fila actual");
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
        }

    }

    public void accionEditar() {
        try {
            int lSelect = getSelectedRow();
            if (lSelect >= 0
                    && lSelect < getDatos().size()) {
                getDatos().setIndex(lSelect);
                moControlador.editar(lSelect);
//                refrescar();
            } else {
                utilesGUIx.msgbox.JMsgBox.mensajeFlotante(this.getContext(), "No existe una fila actual");
            }
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
        }
    }

    public void accionNuevo() {
        try {
            moControlador.anadir();
//            refrescar();
        } catch (Throwable e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this.getContext(), e, getClass().getName());
        }
    }

    /**
     * @return the moDatos
     */
    public JListDatos getDatos() {
        return moDatos;
    }

    public void liberar() {
            getTabla().limpiar();
    }





}
class JElementoBotonesA {

    public String msGrupo;
    public ViewGroup moToolBar;

    public JElementoBotonesA(String psGrupo, ViewGroup poToolBar) {
        msGrupo = psGrupo;
        moToolBar = poToolBar;
    }
}

class EjecutarPanelGenericoA implements Runnable {

    private JPanelGenericoAbstract moPanel;

    public EjecutarPanelGenericoA(final JPanelGenericoAbstract poPanel) {
        moPanel = poPanel;
    }

    public void run() {
        try {
            moPanel.recuperarYmostrarDatos();
        } catch (Throwable e) {
            JMsgBox.mensajeError(moPanel.getContext(), e);
        }
    }
}
