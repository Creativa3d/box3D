/*
 * JConflicto.java
 *
 * Created on 3 de octubre de 2008, 13:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesSincronizacion.sincronizacion.conflictos;

import ListDatos.ECampoError;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDefs;

public class JConflicto {
    public static final int mclModificado = 0;
    public static final int mclBorradoServidorYModificadoCliente=1;
    public static final int mclBorradoClienteYModificadoServidor=2;


    public String msTabla;
    
    public JFieldDefs moCamposServidor;
    public JFieldDefs moCamposCliente;
    
    public IServerServidorDatos moServerServidor;
    public IServerServidorDatos moServerCliente;

    public boolean mbSinHacer = true;
    
    /** Creates a new instance of JConflicto */
    public JConflicto(
        final String psTabla,   
        final JFieldDefs poCamposServidor,
        final JFieldDefs poCamposCliente,
        final IServerServidorDatos poServerServidor,
        final IServerServidorDatos poServerCliente) {
        
        msTabla = psTabla;

        moCamposServidor = poCamposServidor;
        moCamposCliente = poCamposCliente;

        moServerServidor = poServerServidor;
        moServerCliente = poServerCliente;
        
    }

    public int getTipo() {
        int lResult = mclModificado;
        if(moCamposCliente== null){
            lResult = mclBorradoClienteYModificadoServidor;
        }
        if(moCamposServidor== null){
            lResult = mclBorradoServidorYModificadoCliente;
        }
        return lResult;
    }
    
    public void ejecutar(final boolean pbGanaServidor) throws Exception {
        JListDatos loList;
        IResultado loResult=null;
        switch(getTipo()){
            case mclModificado:
                loList = new JListDatos();
                loList.setFields(moCamposCliente.Clone());
                loList.add(new JFilaDatosDefecto(new String[loList.getFields().size()]));
                loList.setIndex(0);
                loList.msTabla = msTabla;
                if(pbGanaServidor){
                    loList.moServidor = moServerCliente;
                    for(int i = 0 ; i < moCamposCliente.size(); i++){
                        loList.getFields().get(i).setValue(moCamposServidor.get(moCamposCliente.get(i).getNombre()).getString());
                    }
                }else{
                    loList.moServidor = moServerServidor;
                    for(int i = 0 ; i < moCamposCliente.size(); i++){
                        loList.getFields().get(i).setValue(moCamposCliente.get(moCamposCliente.get(i).getNombre()).getString());
                    }
                }
                loResult = loList.update(true);
                
                break;
            case mclBorradoClienteYModificadoServidor:
                loResult = borradoClienteYModificadoServidor(
                        pbGanaServidor, 
                        moCamposServidor, moCamposCliente,
                        moServerServidor, moServerCliente);
                break;
            case mclBorradoServidorYModificadoCliente:
                loResult = borradoClienteYModificadoServidor(
                        !pbGanaServidor, 
                        moCamposCliente, moCamposServidor,
                        moServerCliente, moServerServidor);
                break;
        }
        if(loResult!=null && !loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
    }
    
    private IResultado borradoClienteYModificadoServidor(
            final boolean pbGanaServidor,
            final JFieldDefs poCamposServidor,
            final JFieldDefs poCamposCliente,
            final IServerServidorDatos poServerServidor,
            final IServerServidorDatos poServerCliente) throws CloneNotSupportedException, ECampoError{
        IResultado loResult = null;
        JListDatos loList = new JListDatos();
        loList.msTabla = msTabla;
        if(pbGanaServidor){
            loList.moServidor=poServerCliente;
            loList.setFields(poCamposServidor.Clone());
            loList.addNew();
            for(int i = 0 ; i < poCamposServidor.size(); i++){
                loList.getFields().get(i).setValue(poCamposServidor.get(i).getString());
            }
            loResult = loList.update(true);

        }else{
            loList.moServidor=poServerServidor;
            loList.setFields(poCamposServidor.Clone());
            loList.add(new JFilaDatosDefecto(new String[loList.getFields().size()]));
            loList.setIndex(0);
            for(int i = 0 ; i < poCamposServidor.size(); i++){
                loList.getFields().get(i).setValue(poCamposServidor.get(i).getString());
            }
            loList.update(false);
            loResult = loList.borrar(true);
        }
        return loResult;
    }
    
    public void actualizarTransacion(final int plTransacion){
        
    }
    
}
