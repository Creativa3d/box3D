/*
 * JListaElementosConflictos.java
 *
 * Created on 7 de octubre de 2008, 18:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesSincronizacion.sincronizacion.conflictos;

import utiles.JListaElementos;

public class JListaElementosConflictos extends JListaElementos {
    
    /** Creates a new instance of JListaElementosConflictos */
    public JListaElementosConflictos() {
    }

    public boolean add(Object poFilaDatos) {
        //comprobamos si el conflicto existia previamente
        //si existia se borra el anterior y se añade el nuevo
        JConflicto loConflicto = (JConflicto)poFilaDatos;
        String[] lasCamposPrinci;
        if(loConflicto.moCamposCliente!=null){
            lasCamposPrinci = loConflicto.moCamposCliente.masCamposPrincipales();
        }else{
            lasCamposPrinci = loConflicto.moCamposServidor.masCamposPrincipales();
        }
        boolean lbIgual = false;
        for(int i = 0 ; i < size() && !lbIgual; i++){
            JConflicto loAux = (JConflicto)get(i);
            lbIgual = false;
            if(loAux.msTabla.equals(loConflicto.msTabla)){
                String[] lasCamposPrinciAux;
                if(loAux.moCamposCliente!=null){
                    lasCamposPrinciAux =loAux.moCamposCliente.masCamposPrincipales();
                }else{
                    lasCamposPrinciAux =loAux.moCamposServidor.masCamposPrincipales();
                }
                lbIgual=true;
                for(int ii = 0 ; ii < lasCamposPrinci.length && lbIgual; ii++){
                    lbIgual &=  lasCamposPrinci[ii].equals(lasCamposPrinciAux[ii]);
                }
                if(lbIgual){
                    remove(i);
                }
            }
        }
        
        boolean retValue;
        retValue = super.add(poFilaDatos);
        return retValue;
    }
    public void actualizarTransacion(int plTransacion){
        
    }
    public boolean hayPendientes() {
        boolean lbResult = false;
        for(int i = size()-1; i>=0;i--){
            JConflicto loCon = (JConflicto)get(i);
            lbResult |= loCon.mbSinHacer;
        }
        return lbResult;
    }

    public void borrarHechos() {
        for(int i = size()-1; i>=0;i--){
            JConflicto loCon = (JConflicto)get(i);
            if(!loCon.mbSinHacer){
                remove(i);
            }
        }
    }
    
}
