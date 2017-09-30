/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import utiles.JDepuracion;

/**
 *
 * @author cristian
 */
public class JGUIxAvisosSMSFactory implements ICreacionAvisoSMS {

    private final HashMap<String, String> availableClients;
    
    public JGUIxAvisosSMSFactory() {
        availableClients = new HashMap();
    }
    
    public Map<String, String> getAvailableClients() {
        return Collections.unmodifiableMap(availableClients);
    }
    
    @Override
    public void addClient(String clase, String psCaption) {
        availableClients.put(clase, psCaption);
    }

    @Override
    public JGUIxAvisosSMS createSender(String clase) {
        if (availableClients.get(clase)!=null) {
            try {
                return (JGUIxAvisosSMS) Class.forName(clase).newInstance();
            } catch (Throwable ex) {
                JDepuracion.anadirTexto(JGUIxAvisosSMSFactory.class.getName(), ex);
            }
        }
        return null;
    }
    
}
