/*
 * JTabControl.java
 *
 * Created on 10 de noviembre de 2003, 9:42
 */

package utilesGUI.TabControl;


import java.awt.*;

import java.beans.*;
import java.util.Iterator;
import java.util.Random;

import utiles.*;

/**Componente tab para presentar varias páginas*/
public class JTabControl extends java.awt.Panel implements java.io.Serializable, PropertyChangeListener {

    private final PropertyChangeSupport propertySupport;
    
    //nombre de los controles por diseño
    private static final String base = "JTabControl";
    private static int nameCounter = 0;
    
    //panel de los tab de arriba
    private final Panel moPanelTab = new Panel();
    //panel de las paginas
    private final Panel moPanelCentral = new Panel();
    
    //generador de numeros aleatorios para dar nombre a las paginas
    private final Random moRam= new Random();
    
    //listebers
    private final JListaElementos moListenerChange = new JListaElementos();
    
    /** Creates new JTabControl */
    public JTabControl() {
        super();
	setName(base + nameCounter++);
        propertySupport = new PropertyChangeSupport( this );
        this.setLayout(new BorderLayout());
        moPanelTab.setLayout(new GridLayout(1,0));
        moPanelCentral.setLayout(new CardLayout());
        this.add(moPanelCentral, BorderLayout.CENTER);
        this.add(moPanelTab, BorderLayout.NORTH);
    }
    
    /**
     * Añade una pagina
     * @return objeto página
     */
    public JPagina addPagina(){
        JPagina loPagina = moCrearPagina();
        moPanelCentral.add(loPagina.getKey(), loPagina);
        moPanelTab.add(loPagina.getKey(), loPagina.getLabel());
        return loPagina;
    }
    /**
     * Añade una pagina con el caption pasado por parametro
     * @return el objeto página creado
     * @param psCaption caption
     */
    public JPagina addPagina(String psCaption){
        JPagina loPagina = addPagina();
        loPagina.setCaption(psCaption);
        return loPagina;
    }
    /*
     *crea una pagina
     */
    private JPagina moCrearPagina(){
        JPagina loPagina = new JPagina();
        if (getPaginas()==0) {
            loPagina.setSeleccionada(true);
        }
        loPagina.addPropertyChangeListener(this);
        loPagina.setIndex(moPanelCentral.getComponentCount());
        loPagina.setKey((new Long(moRam.nextLong())).toString() + (new Integer(moPanelCentral.getComponentCount())).toString());
        return loPagina;
    }
    /**
     * borra la pagina de la posición i
     * @param plIndex ïndice
     * @return página borrada
     */
    public JPagina removePagina(int plIndex){
        JPagina loPagina = getPagina(plIndex);
        //reindexamos las paginas posteriores
        for(int i=(plIndex+1); i<moPanelCentral.getComponentCount();i++){
            getPagina(i).setIndex(i-1);
        }
        moPanelCentral.remove(plIndex);
        moPanelTab.remove(plIndex);
        return loPagina;
    }
    /**
     * devuelve la pagina de la posición i
     * @param i índice de la página
     * @return página
     */
    public JPagina getPagina(int i){
        return (JPagina)moPanelCentral.getComponent(i);
    }
//    public void setPagina(int i, JPagina poPagina){
//        moPanelCentral.remove(i);
//        moPanelCentral.add(poPagina,i);
//        poPagina.setIndex(i);
//        ((Label)moPanelTab.getComponent(i)).setText(poPagina.getCaption());
//    }
    /**
     * devuelve el numero de paginas
     * @return Número de páginas
     */
    public int getPaginas() {
        return moPanelCentral.getComponentCount();
    }
    /**
     * Establece el número de páginas
     * @param plPaginas Número de páginas
     */
    public void setPaginas(int plPaginas){
        int lPaginas = moPanelCentral.getComponentCount();
        if (lPaginas >= plPaginas){
            for(int i=lPaginas; i<plPaginas;i++){
                addPagina();
            }
        }else{
            for(int i=lPaginas; i==plPaginas;i--){
                removePagina(i-1);
            }
        }
    }
    /**
     * Establece la página activa
     * @param poPagina página
     */
    public void setPagina(JPagina poPagina){
        poPagina.setSeleccionada(true);
    }
    
    /**
     *Añade Listener de cambio de propiedades
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    /**
     *Borra listener de cambio de propiedades
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    /**
     *Eventos que disparan las paginas
     */
    public void propertyChange(PropertyChangeEvent evt) {
        JPagina loPagina = (JPagina)evt.getSource();
        if (evt.getPropertyName().compareTo(JPagina.mcsSeleccionada)==0){
            llamarListener(ponerPestana(loPagina));
        }
    }
    /**
     * Añade un oyente de cambio de página
     * @param poPagina oyente de los eventos de cambio de página
     */
    public void addChangePaginaListener(IChangePagina poPagina){
        moListenerChange.add(poPagina);
    }
    /**
     * Borra un oyente de cambio de página
     * @param poPagina oyente
     */
    public void removeChangePaginaListener(IChangePagina poPagina){
        moListenerChange.remove(poPagina);
    }
    /**Borra todos los oyentes de cambio de página*/
    public void removeAllChangePagina(){
        moListenerChange.clear();
    }
    
    private void llamarListener(JPagina poPagina){
        Iterator loEnum =moListenerChange.iterator(); 
        for(;loEnum.hasNext();){
            IChangePagina loChange = (IChangePagina)loEnum.next();
            loChange.cambioPagina(new ChangePaginaEvent(this, poPagina));
        }
    }
    /*
     *ponemos la pestaña seleccionada
     */
    private JPagina ponerPestana(JPagina poPagina){
        JPagina loPaginaReturn = poPagina;
        if(loPaginaReturn.getSeleccionada()){
            ((CardLayout)moPanelCentral.getLayout()).show(moPanelCentral, loPaginaReturn.getKey());
            for(int i = 0; i < moPanelCentral.getComponentCount(); i++ ){
                JPagina loPagina = (JPagina)moPanelCentral.getComponent(i);
                if (loPagina!=loPaginaReturn){
                    loPagina.getLabel().setPresionado(false);
                }
            }
        }
        int lCuantos = mlCuantosSeleccionada();
        if (lCuantos==0){
            loPaginaReturn = (JPagina)moPanelCentral.getComponent(0);
            loPaginaReturn.getLabel().setPresionado(true);
            ((CardLayout)moPanelCentral.getLayout()).show(moPanelCentral, loPaginaReturn.getKey());
        }
        return loPaginaReturn;
    }
    /*
     *cuantas pestañas seleccionadas
     */
    private int mlCuantosSeleccionada(){
        int lCuantos = 0;
        for(int i = 0; i < moPanelCentral.getComponentCount(); i++ ){
            JPagina loPagina = (JPagina)moPanelCentral.getComponent(i);
            if (loPagina.getSeleccionada()) {
                lCuantos++;
            }
        }
        return lCuantos;
    }
}
