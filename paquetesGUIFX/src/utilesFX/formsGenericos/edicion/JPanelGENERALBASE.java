package utilesFX.formsGenericos.edicion;


import ListDatos.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**Los paneles de edición heredan de esta clase*/
public abstract class JPanelGENERALBASE extends JPanelEdicionNavegadorAbstract implements  IPlugInFrame, IContainer {
    private static final long serialVersionUID = 1L;

    public IPanelControlador moPadre;
    public JListDatos moListDatos;
    public IConsulta moConsulta;

    protected void setDatos(IPanelControlador poPadre) throws Exception{
        if(poPadre!=null){
            moPadre=poPadre;
            moConsulta=poPadre.getConsulta();
            if(moConsulta!=null){
                clonar(moConsulta);
            }
        }
    }
    public void clonar(IConsulta poTabla) throws Exception {
        if(poTabla!=null){
            clonar(poTabla.getList());
        }
    }
    public void clonar(JSTabla poTabla) throws Exception {
        if(poTabla!=null){
            clonar(poTabla.getList());
        }
    }
    public void clonar(JListDatos poListDatos) throws Exception {
        if(poListDatos!=null){
            JListDatosBookMark loMark = poListDatos.getBookmark();
            moListDatos= new JListDatos(poListDatos, true);
            moListDatos.setBookmark(loMark);
        }
    }
    
    @Override
    public abstract JSTabla getTabla();
    @Override
    public void ponerTipoTextos() throws Exception {}
    
    public void actualizarPadre(int lModo) throws Exception {
        //notificamos al padre
        if (getTabla().moList.moveFirst()) {
            do {
                IFilaDatos loFila = (IFilaDatos) getTabla().moList.moFila().clone();
                loFila.setTipoModif(lModo);
                if (moPadre != null) {
                    moPadre.datosactualizados(loFila);
                }
                if ((lModo == JListDatos.mclNuevo) && (moListDatos != null)) {
                    moListDatos.moveLast();
                }
            } while (getTabla().moList.moveNext());
        }
    }
    
    public int getModoTabla(){
        int lModo = getTabla().moList.getModoTabla();
        if(lModo != JListDatos.mclNuevo){
            if(getTabla().moList.moFila().getTipoModif()==JListDatos.mclNuevo){
                lModo = JListDatos.mclNuevo;
            }
        }
        return lModo;
    }
    @Override
    public void cancelar() throws Exception {
        getTabla().moList.cancel();
    }

    @Override
    public void borrar() throws Exception {
        //deshacemos el posible filtro para que coja todos los datos
        moConsulta.getList().filtrarNulo();
        //establecemos la posicion del moConsulta
        moConsulta.getList().setBookmark(moListDatos.getBookmark());
        //llamamos al borrar del padre
        moPadre.borrar(moConsulta.getList().getIndex());
        //una vez borrado establecemos el filtro que hubiera
        moConsulta.getList().filtrar();
        //es posible que el cursor este en una posicion no valida así que nos movemos
        //alante y atras para ponerlo bien
        moListDatos.movePrevious();
        moListDatos.moveNext();
    }

    @Override
    public void buscar() throws Exception {
        final JBusqueda loBusq = new JBusqueda(moConsulta, moConsulta.getList().msTabla);
        loBusq.mostrarBusq((IPanelControlador poControlador) -> {
            if(poControlador.getIndex()>=0){
                moListDatos.buscar(JListDatos.mclTIgual, 
                    moListDatos.getFields().malCamposPrincipales(),
                    moConsulta.getList().getFields().masCamposPrincipales()    
                );
            }            
        });
    }

    @Override
    public void editar() throws Exception {
        moPadre.valoresDefecto(getTabla());
    }

    @Override
    public void nuevo() throws Exception {
        getTabla().addNew();
        if(moPadre!=null){
            moPadre.valoresDefecto(getTabla());
        }
    }
    @Override
    public void recuperarDatos() throws Exception {
        getTabla().recuperarFiltradosNormal(new JListDatosFiltroElem(JListDatos.mclTIgual, 
                getTabla().moList.getFields().malCamposPrincipales(),
                moListDatos.getFields().masCamposPrincipales()
            ),
            false);
        getTabla().moList.moveFirst();
    }

    @Override
    public JListDatos getDatos() throws Exception {
        return moListDatos;
    }

    @Override
    public void refrescar() throws Exception {
        moPadre.refrescar();
        clonar(moConsulta.getList());
        if(moPadre!=null){
            moPadre.datosactualizados(null);
        }
    }
    
    @Override
    public String getIdentificador(){
        return this.getClass().getName();
    }
    public IContainer getContenedorI() {
        return this;
    }
    
    @Override
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    @Override
    public void aplicarListaComponentesAplicacion() {
    }

        
}
