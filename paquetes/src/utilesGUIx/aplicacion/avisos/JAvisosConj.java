/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.aplicacion.avisos;

import java.util.ArrayList;
import utiles.IListaElementos;
import utiles.JListaElementos;


public class JAvisosConj extends ArrayList<JAviso>{

    private IListaElementos<IAvisoListener> moListeners = new JListaElementos<IAvisoListener>();
    
    public void addListener(IAvisoListener poListener){
        moListeners.add(poListener);
    }
    public void removeListener(IAvisoListener poListener){
        moListeners.remove(poListener);
    }
    private void llamarListener(IAvisoListener.tiposAvisoListener plTipo, JAviso poAviso){
        for ( IAvisoListener loAviso : moListeners ){
            loAviso.avisoPerformed(plTipo, poAviso, this);
        }
    }
    @Override
    public boolean add(JAviso e) {
        boolean lb = super.add(e);
        if(lb){
            llamarListener(IAvisoListener.tiposAvisoListener.addAviso, e);
        }
        return lb;
    }

    @Override
    public boolean remove(Object o) {
        boolean lb = super.remove(o);
        if(lb){
            llamarListener(IAvisoListener.tiposAvisoListener.removeAviso, (JAviso)o);
        }
        return lb; 
    }

    @Override
    public void clear() {
        super.clear(); 
        llamarListener(IAvisoListener.tiposAvisoListener.removeAviso, null);
    }
    

}
