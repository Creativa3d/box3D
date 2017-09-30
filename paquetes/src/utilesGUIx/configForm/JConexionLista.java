/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.configForm;

import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.configForm.JConexion;

public class JConexionLista {
    private IListaElementos moLista = new JListaElementos();

    public JConexionLista(){
    }

    public void add(final JConexion poConex){
        moLista.add(poConex);
    }
    public JConexion delete(final String psNombre){
        JConexion loResult = get(psNombre);
        moLista.remove(loResult);
        return loResult;
    }

    public JConexion get(String psNombre){
        JConexion loResult=null;
        for(int i = 0 ; i < moLista.size() && loResult==null; i++){
            JConexion loConex = (JConexion) moLista.get(i);
            if(loConex.getNombre().equalsIgnoreCase(psNombre)){
                loResult = loConex;
            }
        }
        return loResult;
    }

    public JConexion get(int i){
        return (JConexion) moLista.get(i);
    }

    public int size(){
        return moLista.size();
    }


}
