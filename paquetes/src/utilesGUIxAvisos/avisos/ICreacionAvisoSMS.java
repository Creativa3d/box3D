/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

/**
 *
 * @author cristian
 */
public interface ICreacionAvisoSMS {
    
    public void addClient(String clase, String psCaption);
    public JGUIxAvisosSMS createSender(String clase);
    
}
