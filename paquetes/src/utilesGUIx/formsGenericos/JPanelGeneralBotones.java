/*
 * JPanelGeneralBotones.java
 *
 * Created on 7 de julio de 2006, 9:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import java.util.HashMap;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;

public class JPanelGeneralBotones {
    public static final String mcsCancelar = "Cancelar";
    public static final String mcsAceptar = "Aceptar";
    public static final String mcsRefrescar = "Refrescar";
    public static final String mcsEditar = "Editar";
    public static final String mcsNuevo = "Nuevo";
    public static final String mcsBorrar = "Borrar";
    public static final String mcsFiltro = "Filtro";
    public static final String mcsCopiarTabla = "CopiarTabla";

    private final IBotonRelacionado moCancelar = new JBotonRelacionado(mcsCancelar, true);
    private final IBotonRelacionado moAceptar = new JBotonRelacionado(mcsAceptar, true);
    private final IBotonRelacionado moRefrescar = new JBotonRelacionado(mcsRefrescar, true);
    private final IBotonRelacionado moEditar = new JBotonRelacionado(mcsEditar, true);
    private final IBotonRelacionado moNuevo = new JBotonRelacionado(mcsNuevo, true);
    private final IBotonRelacionado moBorrar = new JBotonRelacionado(mcsBorrar, true);
    private final IBotonRelacionado moFiltro = new JBotonRelacionado(mcsFiltro);
    private final IBotonRelacionado moCopiarTabla = new JBotonRelacionado(mcsCopiarTabla);
    
    private JListaElementos moLista = new JListaElementos();
    private HashMap moListaPesos = new HashMap();

    /** Creates a new instance of JPanelGeneralBotones */
    JPanelGeneralBotones() {
        setVisibleModoEdicion();
    }
    
    public IBotonRelacionado getCancelar(){
        return moCancelar;
    }
    public IBotonRelacionado getAceptar(){
        return moAceptar;
    }
    public IBotonRelacionado getRefrescar(){
        return moRefrescar;
    }
    public IBotonRelacionado getEditar(){
        return moEditar;
    }
    public IBotonRelacionado getNuevo(){
        return moNuevo;
    }
    public IBotonRelacionado getBorrar(){
        return moBorrar;
    }
    public IBotonRelacionado getFiltro(){
        return moFiltro;
    }
    public IBotonRelacionado getCopiarTabla(){
        return moCopiarTabla;
    }
    
    public void setVisibleModoBusqueda(){
        moAceptar.setActivo(true);
        moCancelar.setActivo(true);
        moFiltro.setActivo(true);
        moRefrescar.setActivo(false);
        moEditar.setActivo(false);
        moNuevo.setActivo(false);
        moBorrar.setActivo(false);
    }
    public void setVisibleModoEdicion(){
        moCancelar.setActivo(false);
        moAceptar.setActivo(false);
        moRefrescar.setActivo(true);
        moEditar.setActivo(true);
        moNuevo.setActivo(true);
        moBorrar.setActivo(true);
        moFiltro.setActivo(true);
    }
    public void setVisibleTodo(){
        moCancelar.setActivo(true);
        moAceptar.setActivo(true);
        moRefrescar.setActivo(true);
        moEditar.setActivo(true);
        moNuevo.setActivo(true);
        moBorrar.setActivo(true);
        moFiltro.setActivo(true);
        moCopiarTabla.setActivo(true);
    }
    
    /**Add un boton principal al final*/
    public void addBotonPrincipal(final IBotonRelacionado poBoton){
        poBoton.setEsPrincipal(true);
        addBoton(poBoton);
    }
    /**Add un boton principal en la posicion plPosi con respecto a los botones principales*/
    public void addBotonPrincipal(final IBotonRelacionado poBoton, int plPosi){
        poBoton.setEsPrincipal(true);
        addBoton(poBoton, plPosi);
    }
    /**Add un boton*/
    public void addBoton(final IBotonRelacionado poBoton){
        moLista.add(poBoton);
    }
    /**Add un boton en la posicion plPosi*/
    public void addBoton(final IBotonRelacionado poBoton, int plPosi){
        moListaPesos.put(poBoton, new Integer(plPosi));
        addBoton(poBoton);
    }
//    /**Add un boton en la posicion plPosi*/
//    public void addBoton(final IBotonRelacionado poBoton, int plPosi){
//        try {
//            //clonamos la lista
//            JListaElementos loAux = (JListaElementos) moLista.clone();
//            //borramos la original
//            moLista.clear();
//            int i;
//            for(i = 0 ; i < loAux.size() && i < plPosi; i++){
//                IBotonRelacionado loBoton = (IBotonRelacionado) loAux.get(i);
//                //se Add ya q esta antes q el boton parametro
//                moLista.add(loBoton);
//            }
//            //adddboton param
//            moLista.add(poBoton);
//            //se Addn el resto
//            for(; i < loAux.size(); i++){
//                IBotonRelacionado loBoton = (IBotonRelacionado) loAux.get(i);
//                moLista.add(loBoton);
//            }
//
//
//        } catch (CloneNotSupportedException ex) {
//            JDepuracion.anadirTexto(getClass().getName(), ex);
//        }
//
//    }
//    /**Add un boton en la posicion plPosi con respecto al grupo boton q corresponda*/
//    public void addBotonRel(final IBotonRelacionado poBoton, int plPosi){
//        try {
//            //clonamos la lista
//            JListaElementos loAux = (JListaElementos) moLista.clone();
//            //borramos la original
//            moLista.clear();
//            //posicion relativa
//            int lPosiAux = 0;
//            int i;
//            boolean lbAdd = false;
//            for(i = 0 ; i < loAux.size(); i++){
//                IBotonRelacionado loBoton = (IBotonRelacionado) loAux.get(i);
//                //para cada principal/grupo coincidentes add 1 a la posicion
//                if((loBoton.isEsPrincipal()  && poBoton.isEsPrincipal() ) ||
//                   (loBoton.getGrupo() == poBoton.getGrupo()) ||
//                   (loBoton.getGrupo()!=null && poBoton.getGrupo()!=null &&
//                   loBoton.getGrupo().equals(poBoton.getGrupo()))
//                   ){
//                    if(lPosiAux>=plPosi && !lbAdd){
//                        //adddboton param
//                        moLista.add(poBoton);
//                        lbAdd=true;
//                    }
//                    //se Add ya q esta antes q el boton parametro
//                    moLista.add(loBoton);
//                    lPosiAux++;
//                }else{
//                    //se Add ya q esta antes q el boton parametro
//                    moLista.add(loBoton);
//                }
//            }
//            if(!lbAdd){
//                //adddboton param si no add previamente
//                moLista.add(poBoton);
//            }
//        } catch (CloneNotSupportedException ex) {
//            JDepuracion.anadirTexto(getClass().getName(), ex);
//        }
//
//    }

    public void removeBoton(final IBotonRelacionado poBoton){
        moLista.remove(poBoton);
    }
    private Object getMinimo(JListaElementos poLista){
        Object loResult = poLista.get(0);
        int lPosi = Integer.MAX_VALUE;
        for (int i = 0; i < poLista.size(); i++) {
            Object loB = poLista.get(i);
            Integer loPosi = ((Integer)moListaPesos.get(loB));
            if(loPosi!=null && loPosi.intValue()<lPosi){
                lPosi = loPosi.intValue();
                loResult = loB;
            }
        }

        return loResult;
    }
    public IListaElementos getListaBotones(){
        JListaElementos loLista = new JListaElementos();
        try {
            JListaElementos loListaAux = (JListaElementos) moLista.clone();
            while(loListaAux.size()>0){
                Object loB = getMinimo(loListaAux);
                loLista.add(loB);
                loListaAux.remove(loB);
            }
            moLista = loLista;
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        return moLista;
    }
    public void addBotones(IBotonRelacionado[] poBotones ){
        if(poBotones != null){
            for(int i = 0 ; i < poBotones.length; i++){
                moLista.add(poBotones[i]);
            }
        }
        
    }
}
