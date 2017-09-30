package utilesGUIx.formsGenericos.edicion;

import utilesGUIx.Rectangulo;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import ListDatos.*;
import android.content.Context;
import android.view.ViewGroup;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;


/**Los paneles de edicion heredan de esta clase*/
public abstract class JPanelGENERALBASE extends JPanelEdicionNavegadorAbstract implements  IPlugInFrame, IContainer {
    private static final long serialVersionUID = 1L;

    public IPanelControlador moPadre;
    public JListDatos moListDatos;
    public IConsulta moConsulta;

    public JPanelGENERALBASE (Context context) {
        super(context);
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
    public abstract JSTabla getTabla();
    public void ponerTipoTextos() throws Exception {

    }
    
    public void actualizarPadre(int lModo) throws Exception {
        //notificamos al padre
        IFilaDatos loFila = (IFilaDatos)getTabla().moList.moFila().clone();
        loFila.setTipoModif (lModo);
        if(moPadre!=null){
            moPadre.datosactualizados(loFila);
        }
        if((lModo == JListDatos.mclNuevo)&&(moListDatos!=null)){
            moListDatos.moveLast();
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
    public void cancelar() throws Exception {
        if(getTabla()!=null){
            getTabla().moList.cancel();
        }
    }

    public void borrar() throws Exception {
        //deshacemos el posible filtro para que coja todos los datos
        moConsulta.getList().filtrarNulo();
        //establecemos la posicion del moConsulta
        moConsulta.getList().setBookmark(moListDatos.getBookmark());
        //llamamos al borrar del padre
        moPadre.borrar(moConsulta.getList().getIndex());
        //una vez borrado establecemos el filtro que hubiera
        moConsulta.getList().filtrar();
        //es posible que el cursor este en una posicion no valida asi que nos movemos
        //alante y atras para ponerlo bien
        moListDatos.movePrevious();
        moListDatos.moveNext();
    }

    public void buscar() throws Exception {
        JBusqueda loBusq = new JBusqueda(moConsulta, moConsulta.getList().msTabla);
        loBusq.mostrarFormPrinci();
        if(loBusq.getIndex()>=0){
            moListDatos.buscar(JListDatos.mclTIgual, 
                moListDatos.getFields().malCamposPrincipales(),
                moConsulta.getList().getFields().masCamposPrincipales()    
            );
        }
    }

    public void editar() throws Exception {
        moPadre.valoresDefecto(getTabla());
    }

    public void nuevo() throws Exception {
        getTabla().addNew();
        if(moPadre!=null){
            moPadre.valoresDefecto(getTabla());
        }
    }
    public void recuperarDatos() throws Exception {
        getTabla().recuperarFiltradosNormal(new JListDatosFiltroElem(JListDatos.mclTIgual, 
                getTabla().moList.getFields().malCamposPrincipales(),
                moListDatos.getFields().masCamposPrincipales()
            ),
            false);
        getTabla().moList.moveFirst();
    }

    public JListDatos getDatos() throws Exception {
        return moListDatos;
    }

    public void refrescar() throws Exception {
        moPadre.refrescar();
        clonar(moConsulta.getList());
        if(moPadre!=null){
            moPadre.datosactualizados(null);
        }
    }

    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        setBloqueoControlesContainer(this, pbBloqueo);
    }

    public String getIdentificador(){
        return this.getClass().getName();
    }

    private void setBloqueoControlesContainer(ViewGroup aThis, boolean pbBloqueo) {
    }
    public Rectangulo getTanano(){
        return new Rectangulo(320, 240);
    }
    public IContainer getContenedorI() {
        return this;
    }
    
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }
    
}
