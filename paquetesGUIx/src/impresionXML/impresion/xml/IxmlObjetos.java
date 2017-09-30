/*
 * IxmlObjetos.java
 *
 * Created on 25 de enero de 2007, 15:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import java.beans.PropertyChangeListener;
import java.io.Serializable;


public interface IxmlObjetos  extends Cloneable, Serializable {
    public void imprimir(final JxmlBanda poBanda, final JxmlInforme poInforme);
    public String getNombre();
    public String getNombreXML();
    public Object clone() throws CloneNotSupportedException;
    public void visitar(IVisitorOperacion poOperador) throws Throwable;
    public void addPropertyChangeListener(PropertyChangeListener listener);
    public void removePropertyChangeListener(PropertyChangeListener listener);
    public PropertyChangeListener[] getPropertyChangeListeners();
}
