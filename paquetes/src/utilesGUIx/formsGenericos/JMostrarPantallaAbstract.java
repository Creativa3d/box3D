/*
 * JMostrarPantalla.java
 *
 * Created on 6 de febrero de 2008, 11:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionNavegador;
import utilesGUIx.plugin.IPlugInFactoria;

public abstract class JMostrarPantallaAbstract implements IMostrarPantalla {

    /**Tipo edicion mclEdicionDialog, ...*/
    protected int mlTipoEdicion;
    /**Tipo form principal JPanelGeneral.mclTipo o JPanelGeneral2.mclTipo*/
    protected int mlTipoPrincipal;
    /**Tipo form principal mclEdicionDialog, ...*/
    protected int mlTipoPrincipalMostrar;
    protected IProcesoThreadGroup moGrupoThreads;
    protected IListaElementos moListeners = new JListaElementos();
    private Object moImagenIcono;

    public void setGrupoThreads(final IProcesoThreadGroup poGrupoThreads) {
        moGrupoThreads = poGrupoThreads;
    }

    public IPlugInFactoria getPlugInFactoria() {
        return JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria();
    }

    /**
     * 
     * @param poPanel 
     * @param poPanelNave 
     * @param poPanelMismo 
     * @param plTipoMostrar
     * @throws java.lang.Exception 
     */
    public void mostrarEdicion(final IFormEdicion poPanel, final IFormEdicionNavegador poPanelNave, final Object poPanelMismo, final int plTipoMostrar) throws Exception {

        JMostrarPantallaParam loParam = new JMostrarPantallaParam(poPanel, poPanelNave, poPanelMismo, plTipoMostrar);
        if (poPanelNave == null) {
            loParam.setTitulo(poPanel.getTitulo());
        } else {
            loParam.setTitulo(poPanelNave.getTitulo());
        }
        mostrarForm(loParam);


    }

    public void mostrarEdicion(IFormEdicion poPanel, Object poPanelMismo) throws Exception {
        mostrarEdicion(poPanel, null, poPanelMismo, mlTipoEdicion);
    }

    public void mostrarEdicion(IFormEdicionNavegador poPanel, Object poPanelMismo) throws Exception {
        mostrarEdicion(null, poPanel, poPanelMismo, mlTipoEdicion);
    }

    public void mostrarFormPrinci(IPanelControlador poControlador, int plAncho, int plAlto) throws Exception {
        mostrarFormPrinci(poControlador, plAncho, plAlto, mlTipoPrincipal, mlTipoPrincipalMostrar);
    }

    public void mostrarFormPrinci(IPanelControlador poControlador, int plAncho, int plAlto, int plTipoForm, int plTipoMostrar) throws Exception {

        JMostrarPantallaParam loParam = new JMostrarPantallaParam(poControlador, plAncho, plAlto, plTipoForm, plTipoMostrar);
        loParam.setTitulo(poControlador.getParametros().getTitulo());
        mostrarForm(loParam);
    }

    public void mostrarFormPrinci(Object poPanel, int plAncho, int plAlto, int plTipoMostrar) throws Exception {

        JMostrarPantallaParam loParam = new JMostrarPantallaParam(poPanel, plAncho, plAlto, plTipoMostrar, "");
        mostrarForm(loParam);
    }
    public void setTipoEdicion(final int plTipoEdicion) {
        mlTipoEdicion = plTipoEdicion;
    }

    public void setTipoPrincipalMostrar(final int plTipoPrincipalMostrar) {
        mlTipoPrincipalMostrar = plTipoPrincipalMostrar;
    }

    public int getTipoPrincipalMostrar() {
        return mlTipoPrincipalMostrar;
    }

    /**Tipo form principal JPanelGeneral.mclTipo o JPanelGeneral2.mclTipo*/
    public void setTipoPrincipal(final int plTipoPrincipal) {
        mlTipoPrincipal = plTipoPrincipal;
    }

    public int getTipoPrincipal() {
        return mlTipoPrincipal;
    }


    public int getTipoEdicion() {
        return mlTipoEdicion;
    }

    public void addMostrarListener(IMostrarPantallaListener poListener) {
        moListeners.add(poListener);
    }

    public void removeMostrarListener(IMostrarPantallaListener poListener) {
        moListeners.remove(poListener);
    }

    public void llamarListener(JMostrarPantallaEvent jMostrarPantallaEvent) {
        for(int i = 0 ; i < moListeners.size(); i++){
            IMostrarPantallaListener loList = (IMostrarPantallaListener) moListeners.get(i);
            loList.mostrarPantallaPerformed(jMostrarPantallaEvent);
        }
    }

    /**
     * @return the moImagenIcono
     */
    public Object getImagenIcono() {
        return moImagenIcono;
    }

    /**
     * @param moImagenIcono the moImagenIcono to set
     */
    public void setImagenIcono(Object moImagenIcono) {
        this.moImagenIcono = moImagenIcono;
    }

}
