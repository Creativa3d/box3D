/*
 *
 */
package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.Iterator;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;

/**
 *Lista de datos final, es la que usan los clientes, tiene funciones de ordenacion, filtrar, actualizar, ?
 */
public final class JListDatos extends AbstractList<IFilaDatos>
        implements IListaElementos<IFilaDatos>, Cloneable, IBusquedaListener, IListDatosGest, Serializable {

    private static final long serialVersionUID = 3333335L;
    /** Constante tipo campo cadena */
    public static final int mclTipoCadena = 0;
    /** Constante tipo campo numero */
    public static final int mclTipoNumero = 1;
    /** Constante tipo campo fecha */
    public static final int mclTipoFecha = 2;
    /** Constante tipo campo booleano*/
    public static final int mclTipoBoolean = 3;
    /** Constante tipo campo numero doble */
    public static final int mclTipoNumeroDoble = 4;
    /** Constante tipo campo Moneda */
    public static final int mclTipoMoneda3Decimales = 5;
    public static final int mclTipoMoneda = 6;
    /** Constante tipo campo Porcentual */
    public static final int mclTipoPorcentual3Decimales = 7;
    public static final int mclTipoPorcentual = 8;
    public static final int mclTipoInputStream = 9;
    /**Constante valor true */
    public static final String mcsTrue = "1";
    /**Constante valor false  */
    public static final String mcsFalse = "0";
    /**Constante de comparacion Igual*/
    public static final int mclTIgual = 0;
    /**Constante de comparacion Mayor*/
    public static final int mclTMayor = 1;
    /**Constante de comparacion Menor*/
    public static final int mclTMenor = -1;
    /**Constante de comparacion Mayor Igual*/
    public static final int mclTMayorIgual = 2;
    /**Constante de comparacion Menor Igual*/
    public static final int mclTMenorIgual = -2;
    /**Constante de comparacion Distinto*/
    public static final int mclTDistinto = -3;
    /**Constante de comparacion like/como*/
    public static final int mclTLike = 4;
    /**Constante Sin actualizacion */
    public static final int mclNada = 0;
    /**Constante actualizacion editar */
    public static final int mclEditar = 1;
    /**Constante actualizacion nuevo*/
    public static final int mclNuevo = 2;
    /**Constante actualizacion borrar*/
    public static final int mclBorrar = 3;
    /**Constante actualizacion ejecutar un comando*/
    public static final int mclComando = 4;
    /**Constante para ejecutar un script de base datos especifica*/
    public static final int mclScript = 5;
    /**Constante actualizacion crear tabla*/
//    public static final int mclCrearTabla = 4;
    /** Constante de recuperacion de datos normal*/
    public static final int mclSelectNormal = 0;
    /** Constante de recuperacion de datos asincrono/segundo plano*/
    public static final int mclSelectSegundoPlano = 1;
    /**Posicion del cursor en la coleccion*/
    public int mlIndex = 0;
    private int[] malPunteros = new int[0];
    /**Definicion de los campos, contiene los valores actuales*/
    private JFieldDefs moCampos;
    /**Tipo de edicion*/
    private int mlModo = mclNada;
    /**Tabla base*/
    public String msTabla = "";
    public String msTablaAlias = "";
    /**Servidor de datos, Esto no es serializable*/
    public transient IServerServidorDatos moServidor;
    /**Listeners de recuperacion de datos, Esto no es serializable*/
    private transient JListaElementos moListenersRecuperarDatos = null;
    /**Listeners de anadido/borrado/actualizado de datos, Esto no es serializable*/
    private transient JListaElementos moListenersEdicion = null;
    /**Listeners de anadido/borrado/actualizado de datos, Esto no es serializable*/
    private transient JListaElementos moListenersMover = null;
    /**Coleccion de datos*/
    private JList moList = null;
    /**Coleccion de datos borrados*/
    private JListaElementos moListBorrados = null;
    /**Buscar*/
    private transient JListDatosBuscar moBuscar = null;
    /**Select que ha recuperado*/
    public String msSelect = "";
    /**Indica si se ha cargado la select completamente*/
    public boolean mbCargado = false;
    /**Indica si se estan cargando los datos*/
    public boolean mbRecuperandoDatos = false;
    private transient JListDatosCarga moCarga = null;
    /**Ordenacion*/
    private Comparator<IFilaDatos> moOrdenacion = null;
    /**Condiciones de filtro*/
    private JListDatosFiltroConj moFiltro = new JListDatosFiltroConj();
    private JListDatosFiltroConj moFiltroCache = null;//no usar, no se quita por compatibilidad
    private boolean mbFiltrado = false;
    private transient JSelect moSelectDatosRecuperados;
    private transient IListDatosOrdenacionHacer moOrdenacionHacer;

    /**Constructor*/
    public JListDatos() {
        super();
        setList(new JList(), null, null, "", null);
        moCampos = new JFieldDefs();
    }

    /**
     * Constructor
     * @param poListDatos JListDatos original a clonar
     * @param pbConDatos si lo clona con datos o vacio
     */
    public JListDatos(final JListDatos poListDatos, final boolean pbConDatos) throws CloneNotSupportedException {
        super();
        replicar(poListDatos, pbConDatos);
    }

    /**
     * Constructor
     * @param poServidor servidor de datos
     * @param psTabla tabla base
     * @param psNombres lista de nombres 
     * @param palTipos lista de tipos
     * @param palCamposPrincipales lista de campos principales
     */
    public JListDatos(
            final IServerServidorDatos poServidor, final String psTabla,
            final String[] psNombres, final int[] palTipos, final int[] palCamposPrincipales) {
        super();
        inicializacion(poServidor, psTabla, psNombres, null, palTipos,
                palCamposPrincipales, null);
    }

    /**
     * Constructor
     * @param psNombresCaption lista de caption
     * @param poServidor servidor de datos
     * @param psTabla tabla base
     * @param psNombres lista de nombres
     * @param palTipos lista de tipos
     * @param palCamposPrincipales lista de campos principales
     */
    public JListDatos(
            final IServerServidorDatos poServidor, final String psTabla,
            final String[] psNombres, final int[] palTipos, final int[] palCamposPrincipales,
            final String[] psNombresCaption) {
        super();
        inicializacion(poServidor, psTabla, psNombres, psNombresCaption, palTipos,
                palCamposPrincipales, null);
    }

    /**
     * Constructor
     * @param palTamanos lista de tamanos
     * @param poServidor servidor de datos
     * @param psTabla tabla base
     * @param psNombres lista de nombres
     * @param palTipos lista de tipos
     * @param palCamposPrincipales lista de campos principales
     */
    public JListDatos(
            final IServerServidorDatos poServidor, final String psTabla,
            final String[] psNombres, final int[] palTipos,
            final int[] palCamposPrincipales, final int[] palTamanos) {
        super();
        inicializacion(poServidor, psTabla, psNombres, null, palTipos,
                palCamposPrincipales, palTamanos);
    }

    /**
     * Constructor
     * @param psNombresCaption lista de caption
     * @param palTamanos lista de tamanos
     * @param poServidor servidor de datos
     * @param psTabla tabla base
     * @param psNombres lista de nombres
     * @param palTipos lista de tipos
     * @param palCamposPrincipales lista de campos principales
     */
    public JListDatos(
            final IServerServidorDatos poServidor, final String psTabla,
            final String[] psNombres, final int[] palTipos, final int[] palCamposPrincipales,
            final String[] psNombresCaption, final int[] palTamanos) {
        super();
        inicializacion(poServidor, psTabla, psNombres, psNombresCaption, palTipos,
                palCamposPrincipales, palTamanos);
    }

    private void inicializacion(
            final IServerServidorDatos poServidor, final String psTabla,
            final String[] psNombres, final String[] psNombresCaption,
            final int[] palTipos, final int[] palCamposPrincipales,
            final int[] palTamanos) {
        msTabla = psTabla;
        msTablaAlias="";
        moServidor = poServidor;
        moListBorrados = null;
        String[] lsNombresCaption = null;
        if (psNombresCaption == null) {
            lsNombresCaption = new String[psNombres.length];
            for (int i = 0; i < psNombres.length; i++) {
                lsNombresCaption[i] = psNombres[i];
            }
        } else {
            lsNombresCaption = psNombresCaption;
        }
        mlIndex = 0;
        moCampos = new JFieldDefs(psNombres, palCamposPrincipales, lsNombresCaption, palTipos, palTamanos);
        moCampos.setTabla(psTabla);
        setList(new JList(), null, null, "", null);
    }

    /**
     * Devuelve el objeto clonado
     * @return Devuelve objeto clonado
     */
    public Object clone() throws CloneNotSupportedException {
        JListDatos loList;
        loList = new JListDatos(this, true);
        loList.mbCargado = true;
        return loList;
    }
    /**
     * Cerramos el JListDatos, y liberamos conexion con el JList modular
     */
    public void close() {
        if(moListenersEdicion!=null){
            moListenersEdicion.clear();
        }
        if(moListenersMover!=null){
            moListenersMover.clear();
        }
        if(moListenersRecuperarDatos!=null){
            moListenersRecuperarDatos.clear();
        }
        moServidor=null;
        if(moList!=null){
            moList.removeListener(this);
        }
        moList=null;
    }
    /**
     * Clonacion del objeto con un tipo dado
     * @return  objeto clonado
     */
    public JListDatos Clone() throws CloneNotSupportedException {
        return (JListDatos) clone();
    }

    void replicar(final JListDatos poListDatos, final boolean pbConDatos) throws CloneNotSupportedException {
        msTabla = poListDatos.msTabla;
        moServidor = poListDatos.moServidor;
        mlIndex = 0;
        //solo duplicampos los campos si la estruc. es diferente
        if(moCampos==null || !moCampos.isEstructuraIgual(poListDatos.getFields())){
            moCampos = poListDatos.getFields().Clone();
        }
        if (pbConDatos) {
            setList(poListDatos.moList, null, null, poListDatos.msSelect, poListDatos.moSelectDatosRecuperados);
            malPunteros=poListDatos.malPunteros.clone();            
        } else {
            setList(new JList(), null, null, "", null);
        }

    }

    private void setList(final JList poList, final Comparator<IFilaDatos> poOrdenacion, final JListDatosFiltroConj poFiltro, final String psSelect, final JSelect poSelectDatosRecuperados) {
        if(moList!=null){
            moList.removeListener(this);
            moList=null;
        }
        moList = poList;
        msSelect = psSelect;
        if (poSelectDatosRecuperados != null) {
            try {
                moSelectDatosRecuperados = (JSelect) poSelectDatosRecuperados.clone();
            } catch (CloneNotSupportedException w) {
                JDepuracion.anadirTexto(getClass().getName(), w);
            }
        } else {
            moSelectDatosRecuperados = null;
        }
        moList.addListener(this);
        rehacerTodosIndices();
        if (poFiltro != null) {
            moFiltro = (JListDatosFiltroConj) poFiltro.clone();
            if (moFiltro.mbAlgunaCond()) {
                filtrar();
            }
        }
//        if (poFiltroCache != null) {
//            moFiltroCache = (JListDatosFiltroConj) poFiltroCache.clone();
//        }
        ordenar(poOrdenacion);
    }

    /**
     *Devuelve el conj. de filas real
     */
    JList getJList() {
        return moList;
    }

    public void ensureCapacity(int plTamano) {
        moList.ensureCapacity(plTamano);
    }
    /**
     * Devuelve si el conj. de filas esta en cache
     */
    public boolean getEnCache() {
        boolean lbCache = false;
        if (moServidor != null) {
            lbCache = moServidor.getEnCache(this) != null;
        }
        return lbCache;
    }

    /**
     * Nos movemos al primero por el conjunto de datos
     * @return si el movimiento ha tenido exito
     */
    public boolean moveFirst() {
        boolean lbExito = (size() != 0);
        if (lbExito) {
            setIndex(0);
        } else {
            try {
                getFields().limpiar();
            } catch (ECampoError ex) {
            }
        }
        return lbExito;
    }

    /**
     * Nos movemos al ultimo por el conjunto de datos
     * @return si el movimiento ha tenido exito
     */
    public boolean moveLast() {
        boolean lbExito = (size() != 0);
        if (lbExito) {
            setIndex(size() - 1);
        } else {
            try {
                getFields().limpiar();
            } catch (ECampoError ex) {
            }
        }
        return lbExito;
    }

    /**
     * Nos movemos al anterior por el conjunto de datos
     * @return si el movimiento ha tenido exito
     */
    public boolean movePrevious() {
        boolean lbExito = false;
        if ((mlIndex - 1) < 0) {
            moveFirst();
            lbExito = false;
        } else {
            setIndex(mlIndex - 1);
            lbExito = true;
        }
        return lbExito;
    }

    /**
     * Nos movemos al siguiente por el conjunto de datos
     * @return si el movimiento ha tenido exito
     */
    public boolean moveNext() {
        boolean lbExito = false;
        if ((mlIndex + 1) >= size()) {
            moveLast();
            lbExito = false;
        } else {
            setIndex(mlIndex + 1);
            lbExito = true;
        }
        return lbExito;
    }

    /**
     * Nos indica si es el ultimo elemento de la tabla
     * @return si es el ultimo
     */
    public boolean mbEsElUltimo() {
        return (mlIndex == (size() - 1));
    }

    /**
     * Devuelve la fila actual
     * @return la fila
     */
    public IFilaDatos moFila() {
        return (IFilaDatos) get(mlIndex);
    }

    /**
     * Devuelve la lista de campos 
     * @return lista de campos
     */
    public JFieldDefs getFields() {
        return moCampos;
    }

    /**
     * Devuelve un campo
     * @return JFieldDef del campo
     * @param i Posicion del campo
     */
    public JFieldDef getFields(final int i) {
        return moCampos.get(i);
    }

    /**
     * Devuelve un campo
     * @return JFieldDef del campo
     * @param psNombre Nombre del campo
     */
    public JFieldDef getFields(final String psNombre) {
        return moCampos.get(psNombre);
    }

    /**
     * Carga los campos de definicion con los valores actuales
     */
    public void cargarFields() {
        cargarFields(moFila());
    }

    void cargarFields(IFilaDatos poFila) {
        try {
            moCampos.cargar(poFila);
        } catch (Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), e);
        }
    }
    
    public int getIndex(){
        return mlIndex;
    }

    /**
     * Cada vez que cambiamos el indice se cargan los datos en JFieldDefs
     * @param plIndex indice del JListDatos
     */
    public void setIndex(final int plIndex) {
        try {
            llamarListenersMover(plIndex, 0);
            mlIndex = plIndex;
            if ((mlIndex >= 0) && (mlIndex < size())) {
                cargarFields();
            } else {
                 moCampos.limpiar();
            }
            mlModo = mclNada;
            llamarListenersMover(plIndex, 1);
        } catch (Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), e);
        }
    }

    /**
     * Devuelve el modo de la fila actual 
     * @return modo(edicion, ...)
     */
    public int getModoTabla() {
        return mlModo;
    }

    /**
     * Establece el modo de la fila actual 
     */
    public void setModoTabla(final int plModo) {
        mlModo = plModo;
    }

    /**
     * Actualizacion de los datos
     */
    public void addNew() {
        try {
            moCampos.limpiar();
        } catch (Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), e);
        }
        mlModo = mclNuevo;
    }

    /**
     * Cancela la edicion
     */
    public void cancel() {
        setIndex(mlIndex);
    }

    /**
     * Devuelve los punteros borrados 
     * @return Lista de punteros
     */
    public IListaElementos getListBorrados() {
        if (moListBorrados == null) {
            moListBorrados = new JListaElementos();
        }

        return moListBorrados;
    }

    /**
     * Devuelve si esta en borrados la fila pasado por parametro, 
     * a traves de la claves principales
     */
    private int mlIndiceDeBorrados(final IFilaDatos poFila) {
        boolean lbEncontrado = false;
        int lIndex = -1;
        int i;
        IFilaDatos loFilaDatos;
        IFilaDatos loFilaDatosCom;
        //
        //creamos el objeto que nos comparara filas, en funcion de la clave primaria
        //
        int[] lalCampos = getFields().malCamposPrincipales();
        if (lalCampos != null) {
            int[] lalTiposCampos = new int[lalCampos.length];
            String[] lasCadenas = new String[lalCampos.length];
            for (i = 0; i < lalCampos.length; i++) {
                lalTiposCampos[i] = getFields().get(lalCampos[i]).getTipo();
                lasCadenas[i] = poFila.msCampo(lalCampos[i]);
            }

            JOrdenacion loOrden = new JOrdenacion(lalCampos, lalTiposCampos);

            lalTiposCampos = null;

            //
            //creamos la fila a comparar
            //
            loFilaDatosCom = JListDatosBuscar.moFilaAComparar(lalCampos, lasCadenas);

            lasCadenas = null;
            //
            //recorremos la lista de borrados y vemos si la fila existe
            //
            i = 0;
            for (; (i < getListBorrados().size()) && (!lbEncontrado); i++) {
                loFilaDatos = (IFilaDatos) getListBorrados().get(i);
                lbEncontrado = false;
                lbEncontrado = (loOrden.compare(loFilaDatos, loFilaDatosCom)
                        == JOrdenacion.mclIgual);
                if (lbEncontrado) {
                    lIndex = i;
                }
            }
        }
        loFilaDatos = null;
        loFilaDatosCom = null;
        return lIndex;
    }

    /**
     * Limpia todas las actualizaciones pendientes
     */
    public void limpiarActualizaciones() {
        getListBorrados().clear();
        Iterator loEnum = iterator();
        while (loEnum.hasNext()) {
            IFilaDatos loFila = (IFilaDatos) loEnum.next();
            loFila.setTipoModif(mclNada);
        }
        loEnum = null;
    }

    /**
     * Borrar en el servidor y actualiza la coleccion local 
     * @return el objeto resutlado
     * @param pbActualizarServidor1 si actualiza en el servidor
     */
    public IResultado borrar(final boolean pbActualizarServidor1) {
        IResultado loReturn;
        int lTipoModifPrevio = mclNada;
        boolean lbActualizarServidor = pbActualizarServidor1;
        if ((lbActualizarServidor) && (moServidor != null)) {
            setIndex(mlIndex);
            loReturn = moServidor.borrar(msSelect, msTabla, moCampos);
        } else {
            lbActualizarServidor = false;
            lTipoModifPrevio = moFila().getTipoModif();
            loReturn = new JResultado((IFilaDatos) moFila().clone(), msTabla, "", true, this.mclBorrar);
        }
        if (loReturn.getBien()) {

            //si no se ha actualizado en el servidor se anade la fila
            //a la lista de borrados
            if (!lbActualizarServidor) {
                if (lTipoModifPrevio != mclNuevo) {
                    getListBorrados().add(moFila());
                }
            }

            int lIndex = mlIndex;
            //se borra la fila del conj. actual
            remove(mlIndex);

            mlIndex = lIndex;

            //nos movemos si no estamos en una posicion valida
            if (mlIndex >= size()) {
                moveLast();
            } else {
                setIndex(mlIndex);
            }
        }
        return loReturn;
    }

    /**Actualiza en el servidor los cambios hechos con update(false) o borrar(false)*/
    public IResultado updateBatch() throws Exception{
        JActualizarConj loAct = new JActualizarConj(null, null, null);
        loAct.crearUpdateAPartirList(this);
        return moServidor.ejecutarServer(loAct);
    }

    /**
     * Guarda en el servidor y actualiza la coleccion local cuando editamos, si no ha cambiado nada en la fila no se llama al servidor
     * @param pbActualizarServidor si es true se actualiza en el servidor
     * @return objeto resultado de la operacion
     */
    public IResultado update(final boolean pbActualizarServidor) {
        return update(pbActualizarServidor, true);
    }
    
    /**
     * Guarda en el servidor y actualiza la coleccion local cuando editamos, si no ha cambiado nada en la fila no se llama al servidor
     * @param pbActualizarServidor1  si actualiza en el servidor
     * @param pbForzar si es true aunque no cambie nada se guarda en el servidor
     * @return objeto resultado de la operacion
     */
    public IResultado update(final boolean pbActualizarServidor1, final boolean pbForzar) {
        return update(pbActualizarServidor1, pbForzar, moCampos);
    }
    
    /**
     * Guarda en el servidor y actualiza la coleccion local cuando editamos, si no ha cambiado nada en la fila no se llama al servidor
     * @param poCampos lista de campos actualizar, incluye la clave primaria, los campos deben ser referencias a JFieldDef de getFields()
     * @param pbActualizarServidor1  si actualiza en el servidor
     * @param pbForzar si es true aunque no cambie nada se guarda en el servidor
     * @return objeto resultado de la operacion
     */
    public IResultado update(final boolean pbActualizarServidor1, final boolean pbForzar, final JFieldDefs poCampos) {
        IResultado loReturn = null;
        boolean lbActualizarServidor = pbActualizarServidor1;
        try {
            //llamamos a los listener antes de que se haya modificado/anadido un registro
            IFilaDatos loFila;
            loFila = moCampos.moFilaDatos();//los campos de poCampos son referencias a los campos de moCampos
            llamarListenersEdicion(mlModo, mlIndex, loFila, false);
            if (mlModo == mclNuevo) {
                if ((lbActualizarServidor) && (moServidor != null)) {
                    if(moCampos!=poCampos){
                        throw new Exception("El JListDatos de tabla "+msTabla+" solo se pueden añadir filas con todos los campos");
                    }
                    loReturn = moServidor.anadir(msSelect, msTabla, poCampos);
                } else {
                    loReturn = new JResultado(poCampos.moFilaDatos(), msTabla, "", true, mclNuevo);
                    lbActualizarServidor = false;
                }
                if (loReturn.getBien()) {
                    //sabemos que solo hay un resultado(Un JListDatos y 
                    //  dentro de este una sola fila)
                    //se hace por resultado por que el servidor puede haber
                    //anadido datos
                    if(moCampos==poCampos){
                        loFila = (IFilaDatos) ((IListaElementos) loReturn.getListDatos().get(0)).get(0);
                    }else{
                        IFilaDatos loAux = (IFilaDatos) ((IListaElementos) loReturn.getListDatos().get(0)).get(0);
                        loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(msTabla);
                        String[] lasDatos = new String[getFields().size()];
                        for (int i = 0; i < getFields().size(); i++) {
                            lasDatos[i] = ((JFieldDef) getFields().get(i)).getString();
                            int lindice = poCampos.getIndiceDeCampo(getFields().get(i).getNombre());
                            if(lindice>=0){
                                lasDatos[i] = loAux.msCampo(lindice);
                            }
                        }
                        loFila.setArray(lasDatos);
                    }
                    
                    if (lbActualizarServidor) {
                        loFila.setTipoModif(mclNada);
                    } else {
                        //si existe en la lista de borrados se debe poner 
                        //estado editar no anadir
                        int lIndex = mlIndiceDeBorrados(loFila);
                        if (lIndex == -1) {
                            loFila.setTipoModif(mclNuevo);
                        } else {
                            //comprobamos si todos los campos son principales, en este caso
                            //no editamos
                            boolean lbTodosPrincipales = true;
                            for (int i = 0; i < getFields().size(); i++) {
                                lbTodosPrincipales &= getFields(i).getPrincipalSN();
                            }
                            if (lbTodosPrincipales) {
                                loFila.setTipoModif(mclNada);
                            } else {
                                loFila.setTipoModif(mclEditar);
                            }
                            getListBorrados().remove(lIndex);
                        }
                    }

                    add(loFila);

                    setIndex(size() - 1);
                }
            } else {
                if (pbForzar || !(loFila.toString().equals(moFila().toString()))) {
                    if ((lbActualizarServidor) && (moServidor != null)) {
                        if(moCampos!=poCampos && moServidor.getEnCache(this)!=null){
                            throw new Exception("El JListDatos de tabla "+msTabla+" esta en cache y no se pueden actualizar campos concretos");
                        }
                        loReturn = moServidor.modificar(msSelect, msTabla, poCampos);
                    } else {
                        loReturn = new JResultado(poCampos.moFilaDatos(), msTabla, "", true, mclEditar);
                        lbActualizarServidor = false;
                    }
                    if (loReturn.getBien()) {
                        //sabemos que solo hay un resultado(Un JListDatos y 
                        //  dentro de este una sola fila)
                        //se hace por resultado por que el servidor puede haber
                        //anadido datos
                        if(moCampos==poCampos){
                            moFila().setArray(((IFilaDatos) ((IListaElementos) loReturn.getListDatos().get(0)).get(0)).moArrayDatos());
                        }else{
                            IFilaDatos loAux = (IFilaDatos) ((IListaElementos) loReturn.getListDatos().get(0)).get(0);
                            String[] lasDatos = new String[getFields().size()];
                            for (int i = 0; i < getFields().size(); i++) {
                                lasDatos[i] = ((JFieldDef) getFields().get(i)).getString();
                                int lindice = poCampos.getIndiceDeCampo(getFields().get(i).getNombre());
                                if(lindice>=0){
                                    lasDatos[i] = loAux.msCampo(lindice);
                                }
                            }
                            moFila().setArray(lasDatos);
                        }
                        
                        if (lbActualizarServidor) {
                            moFila().setTipoModif(mclNada);
                        } else {
                            if (moFila().getTipoModif() != mclNuevo) {
                                moFila().setTipoModif(mclEditar);
                            }
                        }
                    }
                } else {
                    loReturn = new JResultado((IFilaDatos) moFila().clone(), msTabla, "No en edicion", true,
                            JListDatos.mclEditar);
                }
                //llamamos a los listener de que se ha modificado un registro
                llamarListenersEdicion(mclEditar, mlIndex, moFila(), true);
            }
            if (loReturn.getBien()) {
                mlModo = mclNada;
            }
            loFila = null;
        } catch (Exception e) {
            loReturn = new JResultado(poCampos.moFilaDatos(), msTabla, e.toString(), false, mlModo);
        }
        return loReturn;
    }

    /**
     * Recuperacion de los datos
     */
    public synchronized void esperarATerminar() {
        while (mbRecuperandoDatos) {
            try {
                wait(1000);
            } catch (Exception e) {
//              JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), "esperarATerminar->" + e.toString());
            }
        }
    }

    /**
     * Recupera un objeto select segun la informacion actual
     *@return objeto select
     */
    public JSelect getSelect() {
        JSelect loSelect;
        String lsAlias = msTablaAlias;

        if(lsAlias==null || lsAlias.equals("")){
            lsAlias = msTabla;
            loSelect = new JSelect(msTabla);
        }else{
            loSelect = new JSelect(msTabla, lsAlias);
        }
        for (int i = 0; i < getFields().count(); i++) {
            loSelect.addCampo(lsAlias, getFields().get(i).getNombre());
            if(getFields().get(i).getPrincipalSN()){
                loSelect.addCampoOrder(lsAlias, getFields().get(i).getNombre());
            }
        }
        return loSelect;
    }

    public String getTablaOAlias(){
        String lsResult;
        if(msTablaAlias==null || msTablaAlias.equals("")){
            lsResult = msTabla;
        }else{
            lsResult = msTablaAlias;
        }
        return lsResult;
    }

    public JSelect getSelectDatosRecuperados() {
        return moSelectDatosRecuperados;
    }
    public void setSelectDatosRecuperados(JSelect poSelect) {
        moSelectDatosRecuperados=poSelect;
    }

    /**
     * Recupera los datos del objeto select, SIN cache y NO asincrono
     * @param poSelect objeto select
     * @throws Exception exception
     */
    public void recuperarDatosNoCacheNormal(final JSelect poSelect) throws Exception {
        recuperarDatos(poSelect, false, JListDatos.mclSelectNormal);
    }

    /**
     * Recupera los datos del objeto select, CON cache y SI asincrono
     * @param poSelect objeto select
     * @throws Exception exception
     */
    public void recuperarDatosCacheAsin(final JSelect poSelect) throws Exception {
        recuperarDatos(poSelect, true, JListDatos.mclSelectSegundoPlano);
    }

    /**
     * Recuperar datos de la select 
     * @param poSelect objeto select
     * @param pbPasarACache si pasa a cache
     * @param plOpciones opciones, si es sincrono o asincrono
     * @throws Exception excepcion
     */
    public void recuperarDatos(
            final JSelect poSelect, final boolean pbPasarACache,
            final int plOpciones) throws Exception {
        recuperarDatos(poSelect, pbPasarACache, plOpciones, false);
    }

    /**
     * Recuperar datos de la select 
     * @param pbRefrescarCache si refresca la cache
     * @param poSelect objeto select
     * @param pbPasarACache si pasa a cache
     * @param plOpciones opciones, si es sincrono o asincrono
     * @throws Exception excepcion
     */
    public void recuperarDatos(
            final JSelect poSelect, final boolean pbPasarACache,
            final int plOpciones, final boolean pbRefrescarCache) throws Exception {
        boolean lbCargar = true;
        synchronized (this) {
            if (mbRecuperandoDatos) {
                esperarATerminar();
                lbCargar = false;
            } else {
                clear();
                mbRecuperandoDatos = true;
                mbCargado = false;
            }
        }
        moOrdenacion=null;
        if(msTabla==null || msTabla.equals("") )  {
            if(poSelect.getFrom().getTablasUnion().size()>0){
                msTabla = poSelect.getFrom().getTablaUnion(0).getTabla2();
            } else{
                if(poSelect.msSelectAPelo!=null && !poSelect.msSelectAPelo.equals("")){
                    msTabla = poSelect.msSelectAPelo;
                }
            }
        }
        msSelect = poSelect.toString();
        moSelectDatosRecuperados = poSelect;
        if (lbCargar) {
            if (plOpciones == JListDatos.mclSelectSegundoPlano) {
                moCarga = new JListDatosCarga("Carga " + msTabla,
                        moServidor, this, poSelect, msTabla, pbPasarACache, plOpciones, pbRefrescarCache);
                moCarga.start();
            } else {
                try {
                    moServidor.recuperarDatos(this, poSelect, msTabla, pbPasarACache, pbRefrescarCache, plOpciones);
                } catch (Exception e) {
                    //si hay algun error de conex. indicamos que no se estan recuperando datos
                    mbRecuperandoDatos = false;
                    mbCargado = false;
                    throw e;
                }
            }
        }
    }

    /////////////////////////////////777
    //Implementacion del interfaz IBusquedaListener
    ///////////////////////////////////7
    /**
     * Evento que se produce cuando la recuperacion de datos se ha terminado
     * @param e Objeto evento
     */
    public void recuperacionDatosTerminada(final BusquedaEvent e) {
        moCarga = null;
        if (e.mbError) {
            synchronized (this) {
                mbRecuperandoDatos = false;
                mbCargado = false;
                notifyAll();
            }
        } else {
            rehacerTodosIndices();
            ordenar(moOrdenacion);
            synchronized (this) {
                mbRecuperandoDatos = false;
                mbCargado = true;
                notifyAll();
            }
        }
        moveFirst();
        moListBorrados = null;
        Iterator loEnum = getListenersRecuperarDatos().iterator();
        IBusquedaListener loListener;
        for (; loEnum.hasNext();) {
            loListener = (IBusquedaListener) loEnum.next();
            loListener.recuperacionDatosTerminada(e);
        }
        loListener = null;
        loEnum = null;
    }

    /**
     * Establece el algoritmo de ordenacion
     * @param poOrdenacionHacer Algoritmo de ordenacion
     */
    public void setOrdenacionHacer(final IListDatosOrdenacionHacer poOrdenacionHacer){
        moOrdenacionHacer = poOrdenacionHacer;
    }
    /**
     * Rehace el array de indices segun un orden
     * @param poOrdenacion Objeto ordenacion
     */
    public void ordenar(final Comparator<IFilaDatos> poOrdenacion) {
        moOrdenacion = poOrdenacion;
        if (poOrdenacion != null) {
            if(moOrdenacionHacer==null){
                JOrdenacionHacer loOrdenarHacer =
                        new JOrdenacionHacer(JOrdenacionHacer.mclMergesort);
                ordenar(poOrdenacion, loOrdenarHacer);
                loOrdenarHacer = null;
            }else{
                ordenar(poOrdenacion, moOrdenacionHacer);
            }
        }
    }
    /**
     * Rehace el array de indices segun un orden
     * @param poOrdenacion Objeto ordenacion
     * @param poOrdenacionHacer Objeto que implementa el algoritmo de ordenacion
     */
    public void ordenar(final Comparator<IFilaDatos> poOrdenacion, final IListDatosOrdenacionHacer poOrdenacionHacer) {
        poOrdenacionHacer.ordenar(malPunteros, poOrdenacion, moList);
        setIndex(mlIndex);
    }

    /**
     * ordena segun el objeto ordenacion modular
     */
    public void ordenar() {
        ordenar(moOrdenacion);
    }

    /**
     * Crea un objeto de ordenacion/comparacion
     * @param palCampos lista de campos
     * @param pbAscendente si es ascendente
     * @return Objeto Ordenacion
     */
    public Comparator<IFilaDatos> crearOrdenacion(final int[] palCampos, final boolean pbAscendente) {
        int[] lalTiposCampos = new int[palCampos.length];
        for (int i = 0; i < palCampos.length; i++) {
            lalTiposCampos[i] = getFields().get(palCampos[i]).getTipo();
        }
        JOrdenacion loOrdenacion = new JOrdenacion(palCampos, lalTiposCampos,
                pbAscendente, true);
        lalTiposCampos = null;
        return loOrdenacion;
    }

    /**
     * Campos por los que ordenar 
     * @param palCampos lista de campos
     * @param pbAscendente si es ascendente
     */
    public void ordenar(final int[] palCampos, final boolean pbAscendente) {
        if (palCampos.length != 0) {
            ordenar(crearOrdenacion(palCampos, pbAscendente));
        }
    }

    /**
     * Campos por los que ordenar
     * @param palCampos lista de campos por los que ordenar
     */
    public void ordenar(final int[] palCampos) {
        ordenar(palCampos, true);
    }

    /**
     * Campo por el que ordenar
     * @param plCampo posicion del campo
     */
    public void ordenar(final int plCampo) {
        ordenar(new int[]{plCampo}, true);
    }

    /**
     * Devuelve la ordenacion actual
     * @return Devuelve el objeto ordenacion actual
     */
    public Comparator<IFilaDatos> getOrdenacion() {
        return moOrdenacion;
    }

    /**
     * Devuelve el filtro actual 
     * @return el flitro
     */
    public JListDatosFiltroConj getFiltro() {
        return moFiltro;
    }

    /**
     * Devuelve si tiene un filtro
     * @return Indica si esta filtrado el JListDatos
     */
    public boolean getEsFiltrado() {
        return mbFiltrado;
    }

//    /**
//     * Devuelve el filtro actual para la actualizacion de la cache 
//     * @return el filtro
//     */
//    public JListDatosFiltroConj getFiltroCache() {
//        return moFiltroCache;
//    }

    /**
     * Establece la lista de campos 
     * @param poFields lista de campos
     */
    public void setFields(final JFieldDefs poFields) {
        moCampos = poFields;
    }

    /**
     * Filtra el array de punteros a la condiciones de filtro de la cabecera
     */
    public void filtrar() {
        int[] lalPunteros = new int[size()];
        int lIndex = -1;
        //inicializamos el filtro
        moFiltro.inicializar(msTabla, moCampos.malTipos(), moCampos.msNombres());
        //para cada elemento comprobamos la cond.
        for (int i = 0; i < size(); i++) {
            if (moFiltro.mbCumpleFiltro((IFilaDatos) get(i))) {
                lIndex++;
                lalPunteros[lIndex] = malPunteros[i];
            }
        }
        //rehacemos el array de punteros al nuevo subconjunto
        malPunteros = new int[lIndex + 1];
        for (int i = 0; i < malPunteros.length; i++) {
            malPunteros[i] = lalPunteros[i];
        }
        //lo dejamos en la primera posicion
        moveFirst();
        //indicamos que esta filtrado
        mbFiltrado = true;
        lalPunteros = null;
        try {
            llamarListenersMover(mlIndex, 2);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }        
    }

    /**
     * Deshacemos el filtro
     */
    public void filtrarNulo() {
        rehacerTodosIndices();
        ordenar(moOrdenacion);
        //indicamos que no esta filtrado
        mbFiltrado = false;
        try {
            llamarListenersMover(mlIndex, 2);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }        
    }

    private void rehacerTodosIndices() {
        malPunteros = new int[moList.size()];
        for (int i = 0; i < moList.size(); i++) {
            malPunteros[i] = i;
        }
        try {
            llamarListenersMover(mlIndex, 2);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

    private int mlBuscarPuntero(final int plPuntero) {
        int i = 0;
        for (i = 0; (i < (size() - 1)) && (malPunteros[i] != plPuntero); i++) {
            ;
        }
        if (i >= size()) {
            i = -1;
        }
        return i;
    }

    
    public JTableDef getTableDef() throws Exception {
        JTableDef loTabla = null;
        loTabla = new JTableDef(msTabla);
        loTabla.setTipo(loTabla.mclTipoTabla);
        loTabla.getCampos().setTabla(msTabla);

        int numberOfColumns = getFields().size();
        for(int i = 0; i<numberOfColumns;i++){
            JFieldDef loCampo = getFields().get(i).Clone();
            loTabla.getCampos().addField(loCampo);
        }
        return loTabla;
    }

    /**
     * Guarda el estado actual de la fila
     * @return El objeto memorizado
     */
    public JListDatosBookMark getMemento() {
        return getBookmark();
    }

    /**
     * Establece el estado pasado por parametro
     * @param poMemento El objeto memorizado
     */
    public void setMemento(final JListDatosBookMark poMemento) {
        try {
            setBookmark(poMemento);
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, getClass().getName(), ex);
        }
    }    
    /**
     * Devuelve el puntero interno a la fuente de datos
     * @return objeto puntero
     */
    public JListDatosBookMark getBookmark() {
        if (mlIndex >= 0 
                && mlIndex < malPunteros.length 
                && mlIndex < moList.size() 
                && malPunteros[mlIndex] < moList.size()) {
            return new JListDatosBookMark(moList, malPunteros[mlIndex], mlModo, moCampos, moFila());
        } else {
            return new JListDatosBookMark(moList, -1, mlModo, moCampos, null);
        }
    }

    /**
     * Establece el puntero interno a la fuente de datos
     * @param poBookmark puntero a establecer
     * @throws EBookmarkIncorrecto error
     */
    public void setBookmark(final JListDatosBookMark poBookmark) throws EBookmarkIncorrecto {
        if (poBookmark.getModo() == mclNuevo) {
            addNew();
        } else {
            if (poBookmark.getIndexInterno() != -1) {
                int lIndex = mlBuscarPuntero(poBookmark.getIndexInterno());
                if (lIndex != -1) {
                    setIndex(lIndex);
                }
                if(lIndex != -1 
                    && moFila() != poBookmark.getFila() 
                    && moFila().toString().equalsIgnoreCase(poBookmark.getFila().toString())
                  ){
                    lIndex=-1;
                }
                if (lIndex == -1) {
                    for(int i = 0 ; i < size(); i++){
                        Object loFila = get(i);
                        if(loFila == poBookmark.getFila()){
                            setIndex(i);
                            lIndex = i;
                        }
                    }
                }
                if (lIndex == -1) {
                    throw new EBookmarkIncorrecto();
                }
                
            }
        }
        try {
            moCampos.cargar(poBookmark.getFilaFieldDefs());
        } catch (ECampoError ex) {
            throw new EBookmarkIncorrecto(ex);
        }
    }

    /**
     * Busqueda Binomial, exige que en los campos a buscar esten ordenados
     * @return si se ha encontrado
     * @param psValores valores a buscar
     * @param poCampos Lista de los campos
     */
    public boolean buscarBinomial(final int[] poCampos, final String[] psValores) {
        return buscarBinomial(poCampos, psValores, true);
    }

    /**
     * Busqueda Binomial, exige que en los campos a buscar esten ordenados
     * @return si se ha encontrado
     * @param psValores Array de valores a buscar
     * @param pbIgnoreCASE ignora mayus. y minus.
     * @param poCampos Lista de los campos
     */
    public boolean buscarBinomial(final int[] poCampos, final String[] psValores, final boolean pbIgnoreCASE) {
        if (moBuscar == null) {
            moBuscar = new JListDatosBuscar();
        }
        moBuscar.setTipo(moBuscar.mclBuscarBinomial);
        return moBuscar.Buscar(this, JListDatos.mclTIgual, poCampos, psValores, false, pbIgnoreCASE);
    }

    /**
     * Busca un registro que coincida con el criterio
     * @return si se ha encontrado
     * @param poFiltro filtro q debe cumplir
     */
    public boolean buscar(final IListDatosFiltro poFiltro) {
        return buscar(poFiltro, false);
    }
    /**
     * Busca un registro que coincida con el criterio
     * @param pbAPartirActual, si busca a partir del registro actual
     * @return si se ha encontrado
     * @param poFiltro filtro q debe cumplir
     */
    public boolean buscar(final IListDatosFiltro poFiltro, final boolean pbAPartirActual) {
        boolean lbEncontrado = false;

        int i = 0;
        //indice inicial e busqueda
        if (pbAPartirActual) {
            i = mlIndex;
        } else {
            i = 0;
        }
        //inicializamos el filtro
        poFiltro.inicializar(msTabla, moCampos.malTipos(), moCampos.msNombres());
        //para cada elemento comprobamos la cond.
        for (; i < size() && (!lbEncontrado); i++) {
            if (poFiltro.mbCumpleFiltro((IFilaDatos) get(i))) {
                lbEncontrado = true;
                setIndex(i);
            }

        }
        return lbEncontrado;
    }

    /**
     * Busca un registro que coincida con los criterios campo - valor
     * @return si se ha encontrado
     * @param psCadena Cadena a buscar
     * @param plCompara Tipo comparacion
     * @param plCampo indice del campo
     */
    public boolean buscar(final int plCompara, final int plCampo, final String psCadena) {
        return buscar(plCompara, new int[]{plCampo}, new String[]{psCadena}, false, true);
    }

    /**
     * Busca un registro que coincida con los criterios campos - valores
     * @param plCompara Tipo comparacion
     * @param poCampos indice de los campos
     * @param poCadenas Valores
     * @return si se ha encontrado
     */
    public boolean buscar(final int plCompara, final int[] poCampos, final String[] poCadenas) {
        return buscar(plCompara, poCampos, poCadenas, false, true);
    }

    /**
     * Busca un registro que coincida con los criterios campos - valores
     * @param plCompara Tipo comparacion
     * @param poCampos Indice de los campos
     * @param poCadenas Valores
     * @param pbAPartirActual A partir del registro actual
     * @return Si se ha encontrado
     */
    public boolean buscar(final int plCompara, final int[] poCampos, final String[] poCadenas, final boolean pbAPartirActual) {
        return buscar(plCompara, poCampos, poCadenas, pbAPartirActual, true);
    }

    /**
     * Busca un registro que coincida con los criterios campos - valores
     * @param plCompara Tipo comparacion
     * @param poCampos Indice de los campos
     * @param poCadenas Valores
     * @param pbAPartirActual A partir del registro actual
     * @param pbIgnorarCASE No distinge entre mayusculas y minusculas
     * @return Si se ha encontrado
     */
    public boolean buscar(final int plCompara, final int[] poCampos, final String[] poCadenas, final boolean pbAPartirActual, final boolean pbIgnorarCASE) {
        if (moBuscar == null) {
            moBuscar = new JListDatosBuscar();
        }
        moBuscar.setTipo(moBuscar.mclBuscarNormal);
        return moBuscar.Buscar(this, plCompara, poCampos, poCadenas, pbAPartirActual, pbIgnorarCASE);
    }

    /**
     * Gestor de borrado/anadir un conjunto de datos el servidor, al actualizar datos busca en toda la cache JListDatos a actualizar(misma tabla), y llama a este metodo con todas las Filas ha actualizar
     * @param poDatos Filas a actualizar
     */
    public void actualizarDatos(final JListDatos poDatos) throws CloneNotSupportedException {
        int[] lalCamposPrincipales = moCampos.malCamposPrincipales();
        poDatos.setFields(moCampos.Clone());
        if (lalCamposPrincipales == null) {
            //si es anadir comprobamos si cumple el filtro cache
//            if (getFiltroCache().mbCumpleFiltro(poDatos.moFila())) {
                //si la accion no es borrar anadimos la fila
                if (poDatos.moFila().getTipoModif() == mclBorrar) {
                    IFilaDatos loDatos = (IFilaDatos) poDatos.moFila().clone();
                    loDatos.setTipoModif(mclNada);
                    add(loDatos);
                }
//            }
        } else {
            if (poDatos.moveFirst()) {
                //Guardamos el estado
                JListDatosBookMark loMemento = getMemento();
                //rehacemos los indices para poder buscar por todos los datos
                String[] lasCampos = new String[lalCamposPrincipales.length];
                rehacerTodosIndices();
                do {
                    //creamos el array de datos a buscar
                    for (int i = 0; i < lasCampos.length; i++) {
                        lasCampos[i] = poDatos.moCampos.get(lalCamposPrincipales[i]).getString();
                    }
                    //buscamos, si lo encuentra modificamos los datos
                    //si no los anadimos
                    if (buscar(mclTIgual, lalCamposPrincipales, lasCampos, false)) {
                        if (poDatos.moFila().getTipoModif() == mclBorrar) {
                            remove(mlIndex);
                        } else {
                            moFila().setArray(poDatos.moFila().moArrayDatos());
                            moFila().setTipoModif(mclNada);
                        }
                    } else {
                        //si es anadir comprobamos si cumple el filtro cache
//                        if (getFiltroCache().mbCumpleFiltro(poDatos.moFila())) {
                            //si la accion no es borrar anadimos la fila
                            if (poDatos.moFila().getTipoModif() != mclBorrar) {
                                IFilaDatos loDatos = (IFilaDatos) poDatos.moFila().clone();
                                loDatos.setTipoModif(mclNada);
                                add(loDatos);
                            }
//                        }
                    }
                } while (poDatos.moveNext());
                //restauramos los punteros
                rehacerOrdenYFiltro();
                    //restauramos el estado
                setMemento(loMemento);

                loMemento = null;
                lasCampos = null;
            }
        }
        lalCamposPrincipales = null;
    }



    /**
     * Anula los eventos del JList, de anadir/borrar fila importantes para hacer un JServidorDatos sin usar JServidorDatosAbstrac
     */
    public void eventosGestAnular() {
        moList.mbAnularListeners = true;
    }

    /**
     * Activa los eventos del JList, de anadir/borrar fila importantes para hacer un JServidorDatos sin usar JServidorDatosAbstrac
     */
    public void eventosGestActivar() {
        moList.mbAnularListeners = false;
    }

    ////////////////////////////////////
    //interfaz IListDatosGest
    ///////////////////////////////////77
    @Override
    public void removeFilaDatos(final int plIndex, final IFilaDatos poFila) throws Exception {
        //Guardamos el estado
        JListDatosBookMark loMemento = getMemento();
        //rehacemos los punteros, no se borra simplemente el indice pq todos los punteros
        //a partir de ese indice borrado se corren
        rehacerOrdenYFiltro();
        //restauramos el estado
        setMemento(loMemento);
        //llamamos a los listener de que se ha borrado un registro
        llamarListenersEdicion(mclBorrar, plIndex, poFila, true);
        loMemento = null;
    }

    @Override
    public void addFilaDatos(final int plIndex, final IFilaDatos poFila) throws Exception {
        boolean lbContinuar = true;
        //si ese indice ya existe nose anade
        for (int i = 1; i < 5 && i < size() && lbContinuar; i++) {
            if (plIndex == malPunteros[size() - i]) {
                lbContinuar = false;
            }
        }
        if (lbContinuar) {
            //creamos una nueva lista de punteros + 1
            //y al ultimo elemento le asignamos el indice nuevo
            int lalPunteros[] = new int[size() + 1];
            System.arraycopy(malPunteros, 0, lalPunteros, 0, size());
            lalPunteros[lalPunteros.length - 1] = plIndex;
            malPunteros = lalPunteros;
            //llamamos a los listener de que se ha anadido un registro
            llamarListenersEdicion(mclNuevo, lalPunteros.length - 1, poFila, true);
            lalPunteros = null;
        }
    }

    /**
     * Rehace el orden y el filtro
     */
    public void rehacerOrdenYFiltro() {
        rehacerTodosIndices();
        if (mbFiltrado) {
            filtrar();
        }
        ordenar(moOrdenacion);
    }


    /////////////////////////////////777
    //Gestor de listeners
    ///////////////////////////////////7
    private IListaElementos getListenersRecuperarDatos() {
        if (moListenersRecuperarDatos == null) {
            moListenersRecuperarDatos = new JListaElementos();
        }
        return moListenersRecuperarDatos;
    }

    private IListaElementos getListenersEdicion() {
        if (moListenersEdicion == null) {
            moListenersEdicion = new JListaElementos();
        }
        return moListenersEdicion;
    }
    private IListaElementos getListenersMover() {
        if (moListenersMover == null) {
            moListenersMover = new JListaElementos();
        }
        return moListenersMover;
    }
    

    /**
     * Anade un listener para recuperacion de datos terminada
     * @param poListener Listener
     */
    public void addListener(final IBusquedaListener poListener) {
        getListenersRecuperarDatos().add(poListener);
    }

    /**
     * Borra un listener para recuperacion de datos terminada
     * @param poListener Listener
     */
    public void removeListener(final IBusquedaListener poListener) {
        getListenersRecuperarDatos().remove(poListener);
    }

    /**
     * Borra todos los listeners para recuperacion de datos terminada
     */
    public void removeAllListener() {
        getListenersRecuperarDatos().clear();
    }

    /**
     * Anade un listener para edicion de datos terminada
     * @param poListener Listener
     */
    public void addListenerEdicion(final IListDatosEdicion poListener) {
        getListenersEdicion().add(poListener);
    }

    /**
     * Borra un listener para edicion de datos terminada
     * @param poListener Listener
     */
    public void removeListenerEdicion(final IListDatosEdicion poListener) {
        getListenersEdicion().remove(poListener);
    }

    /**
     * Borra todos los listeners para edicion de datos terminada
     */
    public void removeAllListenerEdicion() {
        getListenersEdicion().clear();
    }

    private void llamarListenersEdicion(final int plModo, final int plIndex, final IFilaDatos poFila, final boolean pbDespues) throws Exception {
        if (getListenersEdicion().size() > 0) {
            Iterator loEnum = getListenersEdicion().iterator();
            IListDatosEdicion loListener;
            while (loEnum.hasNext()) {
                loListener = (IListDatosEdicion) loEnum.next();
                if (pbDespues) {
                    loListener.edicionDatos(plModo, plIndex, poFila);
                } else {
                    loListener.edicionDatosAntes(plModo, plIndex);
                }
            }
            loEnum = null;
            loListener = null;
        }

    }
    /**
     * Anade un listener para edicion de datos terminada
     * @param poListener Listener
     */
    public void addListenerMover(final IListDatosEdicion poListener) {
        getListenersMover().add(poListener);
    }

    /**
     * Borra un listener para edicion de datos terminada
     * @param poListener Listener
     */
    public void removeListenerMover(final IListDatosEdicion poListener) {
        getListenersMover().remove(poListener);
    }

    /**
     * Borra todos los listeners para edicion de datos terminada
     */
    public void removeAllListenerMover() {
        getListenersMover().clear();
    }

    private void llamarListenersMover(final int plIndex, final int plTipo) throws Exception {
        if (moListenersMover!=null && getListenersMover().size() > 0) {
            Iterator loEnum = getListenersMover().iterator();
            IListDatosMoverListener loListener;
            while (loEnum.hasNext()) {
                loListener = (IListDatosMoverListener) loEnum.next();
                switch(plTipo){
                    case 0:
                        loListener.moverDatos(plIndex, this);
                        break;
                    case 1:
                        loListener.moverDatosAntes(mlIndex, plIndex, this);
                        break;
                    case 2:
                        loListener.filtrado(this);
                        break;                        
                }
            }
            loEnum = null;
            loListener = null;
        }

    }

    /////////////////////////////////777
    //Implementacion del interfaz List
    ///////////////////////////////////7

    @Override
    public int size() {
        return malPunteros.length;
    }

    @Override
    public boolean isEmpty() {
        return (malPunteros.length == 0);
    }

    @Override
    public boolean add(final IFilaDatos o) {
        return moList.add(o);
    }
    
    //ESTE EQUAL SE DEJA, NO ES REDUNDANTE
    @Override
    public boolean equals(final Object o) {
        return o == this;
    }

    @Override
    public void clear() {
        setIndex(-1);
        malPunteros = new int[0];
        //NO VALEN OPTIMIZACIONES, SE TIENE QUE CREAR UN NEW JLIST
        setList(new JList(), null, null, "", null);

    }

    @Override
    public IFilaDatos get(final int index) {
        IFilaDatos loFila = (IFilaDatos) moList.get(malPunteros[index]);
        moCampos.cargarCamposCalculados(loFila);
        return loFila;
    }

    @Override
    public IFilaDatos remove(final int index) {
        return (IFilaDatos) moList.remove(malPunteros[index]);
    }

    /**
     * Borrar la fila que coincide con el objeto poObj
     * @param poObj
     * @return 
     */
    public boolean remove(final IFilaDatos poObj) {
        return moList.remove(poObj);
    }
    @Override
    public boolean remove(final Object poObj) {
        return moList.remove(poObj);
    }
}
