/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.IListDatosEdicion;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class JPanelGeneralFiltroModelo {

    public static final String mcsCambioFiltro = JPanelGenericoEvent.mcsCambioFiltro;
    public static final String mcsCambioFiltroCombo = JPanelGenericoEvent.mcsCambioFiltro+"combo";


    private JListDatos moDatos;
    private boolean[] mabVisibles;
    private JTFiltro moTablaFiltro = new JTFiltro();
    private ActionListenerCZ moAccion;

    private boolean mbConOperadorComo = true;

    private JListDatos moListComparaciones = new JListDatos(null, "", new String[]{""}, new int[]{0}, new int[]{0});
    private JListDatos moListUniones = new JListDatos(null, "", new String[]{""}, new int[]{0}, new int[]{0});

    private ITablaConfig moTablaConfig;
    private boolean mbAnularEvento = false;

    public JPanelGeneralFiltroModelo() {
        moTablaFiltro.getList().addListenerEdicion(new IListDatosEdicion() {

            @Override
            public void edicionDatos(int plModo, int plIndex, IFilaDatos poDatos) {
                actionPerformed();
            }

            @Override
            public void edicionDatosAntes(int plModo, int plIndex) throws Exception {
            }
        });
    }
//
//    public void setDatos(final JListDatos poDatos, final boolean[] pabVisibles, final ActionListenerCZ poAccion) {
//        setDatos(poDatos, pabVisibles, poAccion, true, null);
//    }
//
//    public void setDatos(final JListDatos poDatos, final boolean[] pabVisibles, final ActionListenerCZ poAccion, final boolean pbConOperadorComo) {
//        setDatos(poDatos, pabVisibles, poAccion, pbConOperadorComo, null);
//    }

    public void setDatos(final JListDatos poDatos, final ActionListenerCZ poAccion, final boolean pbConOperadorComo, final ITablaConfig poTablaConfig) throws Exception {
        moDatos = poDatos;
        moAccion = poAccion;
        mbConOperadorComo = pbConOperadorComo;
        mabVisibles = new boolean[getDatos().getFields().count()];
        moTablaConfig = poTablaConfig;
        moTablaFiltro.moList.clear();

        crearCombos();

        setVisibles(null);
        
        mbAnularEvento=(true);
        try{
            setFiltro(JTablaConfigTabla.mcsSinFiltro);
        }finally{
            mbAnularEvento=(false);
        }
        
    }
    
    public void setFiltro(String psFiltro) throws Exception{
        if(JCadenas.isVacio(psFiltro)){
            psFiltro=JTablaConfigTabla.mcsSinFiltro;
        }
        JTFiltro loFiltro = moTablaConfig.getConfigTabla().getFiltro(psFiltro);
        if(loFiltro == null){
            psFiltro=JTablaConfigTabla.mcsSinFiltro;
            loFiltro = moTablaConfig.getConfigTabla().getFiltro(psFiltro);
        }        
        boolean lbAnular = isAnularEvento();
        if(!lbAnular){
            anularCambios();
        }
        try{
            pasarDatos(loFiltro, moTablaFiltro);
            mostrarTabla();
        }finally{
            if(!lbAnular){
                activarCambios();
            }
        }
        //llamamos al panel para que refresque
        if (getAccion() != null) {
            getAccion().actionPerformed(new ActionEventCZ(this, 0, mcsCambioFiltroCombo));
        }
    }
    public void pasarDatosAModelo() throws Exception {
        JTFiltro loFiltro = moTablaConfig.getConfigTabla().getFiltro(moTablaFiltro.moList.msTabla);
        pasarDatos(moTablaFiltro, loFiltro);
        
    }
    public void pasarDatos(JTFiltro poOrigen, JTFiltro poDestino) throws Exception {
        poDestino.limpiar();
        poDestino.moList.msTabla=poOrigen.moList.msTabla;
        if(poOrigen.moveFirst()){
            do{
                if(poDestino.moList.buscar(JListDatos.mclTIgual, poDestino.lPosiCodigo, poOrigen.getCodigo().getString())){
                    if(poOrigen.getDuplicadoSN().getBoolean()){
                        poDestino.addNew();
                        poDestino.getFields().cargar(poOrigen.moList.moFila());
                    }
                    poDestino.getValor().setValue(poOrigen.getValor().getString());
                    poDestino.getComparacion().setValue(poOrigen.getComparacion().getString());
                    poDestino.getUnion().setValue(poOrigen.getUnion().getString());
                    poDestino.getDuplicadoSN().setValue(poOrigen.getDuplicadoSN().getString());
                    poDestino.update(false);
                }else{
                    poDestino.moList.add((IFilaDatos) poOrigen.moList.moFila().clone());
                }
            }while(poOrigen.moveNext());
            poDestino.ordenar();
        }
    }


    public JListDatos getFiltrosDisponibles() throws ECampoError{
        JListDatos loList = new JListDatos(null, "", new String[]{""}, new int[]{JListDatos.mclTipoCadena}, new int[]{0});
        for(String lsI : moTablaConfig.getConfigTabla().getFiltros().keySet()){
            loList.addNew();
            loList.getFields(0).setValue(lsI);
            loList.update(false);
        }
        return loList;
    }
    
    public JListDatos getFiltroPorCampo() throws CloneNotSupportedException {

        JListDatos loFiltroRapido = new JListDatos();
        for (int i = 0; i < moDatos.getFields().size(); i++) {
            loFiltroRapido.getFields().addField(moDatos.getFields(i).Clone());
            loFiltroRapido.getFields(i).setTipo(JListDatos.mclTipoCadena);
        }
        loFiltroRapido.addNew();
        loFiltroRapido.update(false);

        loFiltroRapido.addListenerEdicion(new IListDatosEdicion() {
            @Override
            public void edicionDatos(int plModo, int plIndex, IFilaDatos poDatos) {
                try {
                    if(!isAnularEvento()){
                        anularCambios();
                        JTFiltro loModel = getTablaFiltro();
                        loModel.moList.msTabla=JTablaConfigTabla.mcsSinFiltro;
                        //llamamos al panel para que refresque
                        if (getAccion() != null) {
                            getAccion().actionPerformed(new ActionEventCZ(this, 0, mcsCambioFiltroCombo));
                        }
                        if (loModel.moveFirst()) {
                            do {
                                loModel.getUnion().setValue(JTFiltro.mcsY);
                                loModel.getValor().setValue(
                                        poDatos.msCampo(loModel.getCodigo().getInteger())
                                );
                                loModel.getComparacion().setValue(JTFiltro.mcsComo);
                                loModel.update(false);
                            } while (loModel.moveNext());
                        }
                    }
                } catch (Exception e) {
                    JDepuracion.anadirTexto(getClass().getName(), e);
                } finally {
                    activarCambios();
                }
            }

            @Override
            public void edicionDatosAntes(int plModo, int plIndex) throws Exception {
            }
        });
        return loFiltroRapido;
    }
    public void buscarTodosCampos(String psValor) {
        try {
            anularCambios();

            JTFiltro loModel = getTablaFiltro();
            loModel.moList.msTabla=JTablaConfigTabla.mcsSinFiltro;
            //llamamos al panel para que refresque
            if (getAccion() != null) {
                getAccion().actionPerformed(new ActionEventCZ(this, 0, mcsCambioFiltroCombo));
            }
            loModel.limpiar();
            if (loModel.moveFirst()) {
                do {
                    if (loModel.getVisibleSN().getBoolean()) {
                        loModel.getValor().setValue(psValor);

                        if (!loModel.getComparacion().toString().equals(JTFiltro.mcsComo)) {
                            loModel.getComparacion().setValue(JTFiltro.mcsComo);
                        }
                        if (psValor.equals("")) {
                            loModel.getUnion().setValue(JTFiltro.mcsY);
                        } else {
                            loModel.getUnion().setValue(JTFiltro.mcsO);
                        }
                        loModel.update(false);
                    }
                } while (loModel.moveNext());
            }

        } catch (Exception e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
        } finally {
            activarCambios();
        }
    }
    private void crearCombos() {
        getListComparaciones().clear();
        getListUniones().clear();

        if (isConOperadorComo()) {
            getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsComo}));
        }
        getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsIgual}));
        getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsDistinto}));
        getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsMayor}));
        getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsMayorIgual}));
        getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsMenor}));
        getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsMenorIgual}));
        getListComparaciones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsMasOMenos}));

        getListUniones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsY}));
        getListUniones().add(new JFilaDatosDefecto(new String[]{JTFiltro.mcsO}));

    }

    public void setVisibles(final boolean[] pabVisibles) {
        for (int i = 0; i < getVisibles().length; i++) {
            if (pabVisibles != null) {
                mabVisibles[i] = pabVisibles[i];
            } else {
                mabVisibles[i] = true;
            }
        }
        mostrarTabla();
    }

    private void mostrarTabla() {
        setAnularEvento(true);
        try {
            boolean lbVisible;
            for (int i = 0; i < getDatos().getFields().count(); i++) {
                lbVisible = true;
                if (getVisibles() != null) {
                    if (getVisibles().length > i) {
                        lbVisible = getVisibles()[i];
                    } else {
                        lbVisible = false;
                    }
                }
                if (getTablaFiltro().getList().buscar(JListDatos.mclTIgual, JTFiltro.lPosiCodigo, String.valueOf(i))) {
                    getTablaFiltro().getVisibleSN().setValue(lbVisible);
                    getTablaFiltro().update(false);
                } else {
                    String lsOperador = JTFiltro.mcsComo;
                    if (!isConOperadorComo()) {
                        lsOperador = JTFiltro.mcsIgual;
                    }

                    getTablaFiltro().addNew();
                    getTablaFiltro().getCodigo().setValue(i);
                    getTablaFiltro().getNombre().setValue(getDatos().getFields().get(i).getCaption());
                    getTablaFiltro().getComparacion().setValue(lsOperador);
                    getTablaFiltro().getValor().setValue("");
                    getTablaFiltro().getUnion().setValue(JTFiltro.mcsY);
                    getTablaFiltro().getDuplicadoSN().setValue(false);
                    getTablaFiltro().getVisibleSN().setValue(lbVisible);
                    getTablaFiltro().update(false);
                }
            }
        } catch (Exception e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
        } finally {
            setAnularEvento(false);
        }
    }


    /**
     * Limpia la tabla y el filtro y lo ejecuta
     */
    public void limpiar() throws ECampoError {
        if (getTablaFiltro() != null) {
            try {
                anularCambios();
                if (getTablaFiltro().moveFirst()) {
                    do {
                        getTablaFiltro().getValor().setValue("");
                        getTablaFiltro().getComparacion().setValue(JTFiltro.mcsComo);
                        getTablaFiltro().getUnion().setValue(JTFiltro.mcsY);
                        getTablaFiltro().update(false);
                    } while (getTablaFiltro().moveNext());
                }
            } finally {
                activarCambios();
            }
        }
    }

    public void anularCambios() {
        setAnularEvento(true);
    }

    public void activarCambios() {
        setAnularEvento(false);
        actionPerformed();
    }

    /**
     * crea el filtro y lo ejecuta
     */
    public void buscar() {

        boolean lbRefrescar = false;
        //aplicamos el filtro
        if (getDatos().getFiltro().mbAlgunaCond()) {
            getDatos().getFiltro().Clear();
            getDatos().filtrarNulo();
            lbRefrescar = true;
        }
        if (getFiltro() != null && getFiltro().mbAlgunaCond()) {
            getDatos().getFiltro().addCondicion(JListDatosFiltroConj.mclAND, getFiltro());
            getDatos().filtrar();
            lbRefrescar = true;
            if(!JTablaConfigTabla.mcsSinFiltro.equalsIgnoreCase(moTablaFiltro.moList.msTabla)){
                try {
                    pasarDatosAModelo();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
            }            
        }
        //llamamos al panel para que refresque
        if (getAccion() != null && lbRefrescar) {
            getAccion().actionPerformed(new ActionEventCZ(this, 0, mcsCambioFiltro));
        }
    }

    //cada vez que cambia una fila se llama a este evento y se ejecuta el filtrar
    public void actionPerformed() {
        if (!isAnularEvento()) {
            buscar();
        }
    }

    public void duplicar(final int row) throws Exception {
        IFilaDatos loFila = getTablaFiltro().getList().get(row);
        getTablaFiltro().addNew();
        getTablaFiltro().getFields().cargar(loFila);
        getTablaFiltro().getDuplicadoSN().setValue(true);
        getTablaFiltro().update(false);
        getTablaFiltro().ordenar();
    }

    public JListDatosFiltroConj getFiltro() {
        return getTablaFiltro().getFiltro();
    }

    public int getFilaOrigenSinDuplicados(final int plFila) {
        int lResult = -1;
        for (int i = 0; i < getTablaFiltro().getList().size() && lResult == -1; i++) {
            getTablaFiltro().getList().setIndex(i);
            if (getTablaFiltro().getCodigo().toString().equals(String.valueOf(plFila))) {
                lResult = i;
            }
        }
        return lResult;
    }

    /**
     * @return the moDatos
     */
    public JListDatos getDatos() {
        return moDatos;
    }

    /**
     * @return the mabVisibles
     */
    public boolean[] getVisibles() {
        return mabVisibles;
    }

    /**
     * @return the moTableModel
     */
    public JTFiltro getTablaFiltro() {
        return moTablaFiltro;
    }

    /**
     * @return the moAccion
     */
    public ActionListenerCZ getAccion() {
        return moAccion;
    }

    /**
     * @return the mbConOperadorComo
     */
    public boolean isConOperadorComo() {
        return mbConOperadorComo;
    }

    /**
     * @return the moListComparaciones
     */
    public JListDatos getListComparaciones() {
        return moListComparaciones;
    }

    /**
     * @return the moListUniones
     */
    public JListDatos getListUniones() {
        return moListUniones;
    }

    /**
     * @return the moTablaConfig
     */
    public ITablaConfig getTablaConfig() {
        return moTablaConfig;
    }

    /**
     * @return the mbAnularEvento
     */
    public boolean isAnularEvento() {
        return mbAnularEvento;
    }

    /**
     * @param mbAnularEvento the mbAnularEvento to set
     */
    public void setAnularEvento(boolean mbAnularEvento) {
        this.mbAnularEvento = mbAnularEvento;
    }

    public JTFiltro addFiltro(String lsNombre) throws Exception {
        JTFiltro loFiltro = new JTFiltro();
        loFiltro.moList.msTabla=lsNombre;
        getTablaConfig().getConfigTabla().addFiltro(loFiltro);
        //llamamos al panel para que refresque
        if (getAccion() != null && !isAnularEvento()) {
            getAccion().actionPerformed(new ActionEventCZ(this, 0, mcsCambioFiltroCombo));
        }        
        return loFiltro;
    }

    public void borrarFiltro(String psNombre) {
        getTablaConfig().getConfigTabla().getFiltros().remove(psNombre);
        //llamamos al panel para que refresque
        if (getAccion() != null && !isAnularEvento()) {
            getAccion().actionPerformed(new ActionEventCZ(this, 0, mcsCambioFiltroCombo));
        }        
    }

}
