package utilesGUIx.formsGenericos.edicion;

import utilesGUIx.formsGenericos.IPanelGenerico;
import java.awt.Container;
import javax.swing.*;

import ListDatos.*;
import utilesGUIx.JTextHTML;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupo;

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
    public abstract JSTabla getTabla();
    public void ponerTipoTextos() throws Exception {

    }
    
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
    public void cancelar() throws Exception {
        getTabla().moList.cancel();
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
        //es posible que el cursor este en una posicion no valida así que nos movemos
        //alante y atras para ponerlo bien
        moListDatos.movePrevious();
        moListDatos.moveNext();
    }

    public void buscar() throws Exception {
        final JBusqueda loBusq = new JBusqueda(moConsulta, moConsulta.getList().msTabla);
        loBusq.mostrarBusq(new CallBack<IPanelControlador>() {
            public void callBack(IPanelControlador poControlador) {
                if(poControlador.getIndex()>=0){
                    moListDatos.buscar(JListDatos.mclTIgual, 
                        moListDatos.getFields().malCamposPrincipales(),
                        moConsulta.getList().getFields().masCamposPrincipales()    
                    );
                }
            }
        });
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

    @Override
    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        setBloqueoControlesContainer(this, pbBloqueo);
    }

    public static void setBloqueoControlesContainer(final java.awt.Container poComponente, final boolean pbBloqueo) throws Exception {
        for(int i = 0; i < poComponente.getComponentCount(); i++){
            //establecemos el bloque al componente concreto
            if (((poComponente.getComponent(i) instanceof javax.swing.JButton)
                    || (poComponente.getComponent(i) instanceof javax.swing.JTextField)
                    || (poComponente.getComponent(i) instanceof javax.swing.JTextArea)
                    || (poComponente.getComponent(i) instanceof javax.swing.JComboBox)
                    || (poComponente.getComponent(i) instanceof javax.swing.JCheckBox)
                    || (poComponente.getComponent(i) instanceof javax.swing.JList)
                    || (poComponente.getComponent(i) instanceof java.awt.TextField)
                    || (poComponente.getComponent(i) instanceof java.awt.TextArea)
                    || (poComponente.getComponent(i) instanceof java.awt.Choice)
                    || (poComponente.getComponent(i) instanceof java.awt.Checkbox)
                    || (poComponente.getComponent(i) instanceof java.awt.List)
                    || (poComponente.getComponent(i) instanceof IPanelGenerico)
                    || (poComponente.getComponent(i) instanceof IPlugInConsulta)
                    || (poComponente.getComponent(i) instanceof JTextHTML)
                    )
                    && (poComponente != poComponente.getComponent(i))) {
                
                if(JTextField.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                    ((JTextField)poComponente.getComponent(i)).setEnabled(!pbBloqueo);
                }else if(JTextArea.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                    ((JTextArea)poComponente.getComponent(i)).setEnabled(!pbBloqueo);
                } else{
                    poComponente.getComponent(i).setEnabled(!pbBloqueo);
                }
                
            } else if (((poComponente.getComponent(i) instanceof java.awt.Container))
                    && (poComponente != poComponente.getComponent(i))){
                setBloqueoControlesContainer((java.awt.Container)poComponente.getComponent(i), pbBloqueo);
            }
//            //siempre habilitamos las tablas, arboles, redimensionadores y paneles 
//            if ((poComponente.getComponent(i) instanceof javax.swing.JTree)
//                    || (poComponente.getComponent(i) instanceof javax.swing.JTable)
//                    || (poComponente.getComponent(i) instanceof javax.swing.JPanel)
//                    || (poComponente.getComponent(i) instanceof javax.swing.JSplitPane)
//                    || (poComponente.getComponent(i) instanceof javax.swing.JScrollPane)                    ){
//                 poComponente.getComponent(i).setEnabled(true);
//            }
        }
    }
    public static void setEdicionControlesContainer(final java.awt.Container poComponente, final boolean pbEdicion) throws Exception {
        setEdicionControlesContainer(poComponente, pbEdicion, false);
    }
    
    public static void setEdicionControlesContainer(final java.awt.Container poComponente, final boolean pbEdicion, final boolean pbConBotones) throws Exception {
        for(int i = 0; i < poComponente.getComponentCount(); i++){
            //establecemos el bloque al componente concreto
            if((poComponente.getComponent(i).getClass() != javax.swing.JLabel.class)&&
               (poComponente.getComponent(i).getClass() != utilesGUIx.JLabelCZ.class)&&
               (poComponente.getComponent(i).getClass() != javax.swing.JTabbedPane.class)){
                if(JTextField.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                    ((JTextField)poComponente.getComponent(i)).setEditable(pbEdicion);
                }else if(JTextArea.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                    ((JTextArea)poComponente.getComponent(i)).setEditable(pbEdicion);
                }else if(JButton.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                    poComponente.getComponent(i).setEnabled(pbEdicion || !pbConBotones);
                }else {
                    poComponente.getComponent(i).setEnabled(pbEdicion);
                }
            }
            //llamada recursiva para los contenedores
            if(((poComponente.getComponent(i).getClass().getSuperclass() == java.awt.Container.class)||
                (poComponente.getComponent(i).getClass().getSuperclass().getSuperclass() == java.awt.Container.class)||
                (poComponente.getComponent(i).getClass().getSuperclass().getSuperclass().getSuperclass() == java.awt.Container.class)
               )&&
               (poComponente != poComponente.getComponent(i))){
                setEdicionControlesContainer((java.awt.Container)poComponente.getComponent(i), pbEdicion);
            }
            //siempre habilitamos las tablas, arboles, redimensionadores y paneles
            if((poComponente.getComponent(i).getClass() == javax.swing.JTree.class)||
                (poComponente.getComponent(i).getClass() == javax.swing.JTable.class)||
                (poComponente.getComponent(i).getClass() == javax.swing.JPanel.class)||
                (poComponente.getComponent(i).getClass() == javax.swing.JSplitPane.class)){
                 poComponente.getComponent(i).setEnabled(true);
            }
        }
    }

    public JMenuBar getMenu() {
        return null;
    }
    public String getIdentificador(){
        return this.getClass().getName();
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
