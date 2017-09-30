/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author eduardo
 */
public class JDepuracionFACTORY extends  LogFactory {
    protected Hashtable attributes = new Hashtable();
/**
     * Return the configuration attribute with the specified name (if any),
     * or <code>null</code> if there is no such attribute.
     *
     * @param name Name of the attribute to return
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Return an array containing the names of all currently defined
     * configuration attributes.  If there are no such attributes, a zero
     * length array is returned.
     */
    public String[] getAttributeNames() {
        return (String[]) attributes.keySet().toArray(new String[attributes.size()]);
    }
    @Override
    public Log getInstance(Class type) throws LogConfigurationException {
        return JDepuracion.moIMPL;
    }

    @Override
    public Log getInstance(String string) throws LogConfigurationException {
        return JDepuracion.moIMPL;
    }

    @Override
    public void release() {
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        

        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }
    }

    
}
