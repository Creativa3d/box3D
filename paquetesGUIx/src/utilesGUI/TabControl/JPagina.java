/*
 * JPagina.java
 *
 * Created on 10 de noviembre de 2003, 9:55
 */

package utilesGUI.TabControl;

import java.beans.*;

import java.awt.*;

/**Objeto página*/
public class JPagina extends java.awt.Panel implements java.awt.event.ActionListener {
    /**CTE para eventos caption*/
    public static final String mcsCaption = "Caption";
    /**CTE para eventos seleccionado*/
    public static final String mcsSeleccionada = "Seleccionada";
    
    private ButtonPagina msCaption = new ButtonPagina("Página");
    
    private PropertyChangeSupport propertySupport;
    
    private int mlIndex=-1; 
    
    /** Creates a new instance of JPagina */
    public JPagina() {
        super();
        propertySupport = new PropertyChangeSupport( this );
        msCaption.addActionListener(this);
    }
    /**
     * Devuelve el label del componente
     * @return botón
     */
    public ButtonPagina getLabel(){
        return msCaption;
    }
    /**
     * devuelve el key de la pagina
     * @return key
     */
    public String getKey(){
        return msCaption.getName();
    }
    /**
     * Establece el key de la pagina
     * @param psValor key
     */
    public void setKey(String psValor ){
        msCaption.setName(psValor);
    }
    /**
     * Devuelve el index de la posicion en el page control
     * @return índice
     */
    public int getIndex(){
        return mlIndex;
    }
    /**
     * Establece el index de la posición en el pagecontrol
     * @param plIndex índice
     */
    protected void setIndex(int plIndex){
        mlIndex = plIndex;
    }
    /**
     * devuelve el caption de la página
     * @return captión
     */
    public String getCaption() {
        return msCaption.getLabel();
    }
    /**
     * establece el caption del la pagina
     * @param value caption
     */
    public void setCaption(String value) {
        String oldValue = msCaption.getLabel();
        msCaption.setLabel(value);
        propertySupport.firePropertyChange(mcsCaption, oldValue, msCaption.getLabel());
    }
    /**
     * Indica si es una pagina seleccionada
     * @return si esta seleccionada
     */
    public boolean getSeleccionada() {
        return msCaption.getPresionado();
    }
    /**
     * Establece si es seleccionada o no
     * @param value si es seleccionada
     */
    public void setSeleccionada(boolean value) {
        boolean oldValue = msCaption.getPresionado();
        msCaption.setPresionado(value);
        propertySupport.firePropertyChange(mcsSeleccionada, new Boolean(oldValue), new Boolean(msCaption.getPresionado()));
    }
    /**
     *Añade listener de cambio de propiedades
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
     *Cuando cliclamos en el label se selecciona esta pagina
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        propertySupport.firePropertyChange(mcsSeleccionada, new Boolean(!msCaption.getPresionado()), new Boolean(msCaption.getPresionado()));
    }
    
}
