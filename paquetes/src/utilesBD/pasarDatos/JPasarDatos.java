/*
 * JPasarDatos.java
 *
 * Created on 10 de junio de 2006, 11:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesBD.pasarDatos;

import utilesBD.serverTrozos.IServerServidorDatosTrozos;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JActualizar;
import ListDatos.JListDatos;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;

public class JPasarDatos {
    public static final int mclNumRegDefecto = 50000;
    private int mlNumReg = mclNumRegDefecto;
    private JTableDefs moTablasBD1;
    private JTableDefs moTablasBD2;
    private int mlTransaccionCada=0;
    
    private IServerServidorDatosTrozos moServerBD1;
    private IServerServidorDatos moServerBD2;
    
    /** Creates a new instance of JPasarDatos */
    public JPasarDatos(JTableDefs poTablasBD1,JTableDefs poTablasBD2,IServerServidorDatosTrozos poServerBD1,IServerServidorDatos poServerBD2) {
        moTablasBD1=poTablasBD1;
        moTablasBD2=poTablasBD2;
        moServerBD1=poServerBD1;
        moServerBD2=poServerBD2;
    }
    public IServerServidorDatosTrozos getServerBD1(){
        return moServerBD1;
    }
    public IServerServidorDatos getServerBD2() {
        return moServerBD2;
    }
    


    public synchronized void borrarDatosDestino(String psTabla, boolean pbSecuencial) throws Exception {
        JListDatos loListDatos2 = new JListDatos(
                moServerBD2, psTabla, 
                getTable2(psTabla).getCampos().msNombres(), 
                getTable2(psTabla).getCampos().malTipos(),
                getTable2(psTabla).getCampos().malCamposPrincipales());
        if(pbSecuencial){

            loListDatos2.recuperarDatos(loListDatos2.getSelect(),false,loListDatos2.mclSelectNormal);

            if(loListDatos2.moveFirst()){
                do{
                    IResultado loResult =  loListDatos2.borrar(true);
                    if(!loResult.getBien()){
                        throw new Exception(loResult.getMensaje());
                    }
                }while(loListDatos2.moveFirst());
            }
            //return loListDatos2.size();
            System.gc();
        }else{

            JActualizar loAct = new JActualizar(null, psTabla, JListDatos.mclBorrar, "", "", "");
            IResultado loResult =  moServerBD2.ejecutarServer(loAct);
            if(!loResult.getBien()){
                throw new Exception(loResult.getMensaje());
            }            
            
        }
    }
    //por si no se ha podido leer la estruc. de una de las bases datos pero sabemos q es igual (microsoft access)
    public JTableDef getTable1(String psTabla){
        JTableDef loTabla1 = moTablasBD1.get(psTabla);
        JTableDef loTabla2 = moTablasBD2.get(psTabla);
        if(loTabla1==null){
            return loTabla2;
        }else{
            return loTabla1;
        }
        
    }    
    public JTableDef getTable2(String psTabla){
        JTableDef loTabla1 = moTablasBD2.get(psTabla);
        JTableDef loTabla2 = moTablasBD1.get(psTabla);
        if(loTabla1==null){
            return loTabla2;
        }else{
            return loTabla1;
        }
        
    }    
    public int getRegistrosTablaBD1(String psTabla)  throws Exception {
//        JSelect loSelect = new JSelect(psTabla);
//        loSelect.addCampo(JSelectCampo.mclFuncionCount, psTabla, moTablasBD1.get(psTabla).getCampos().get(0).getNombre());
//        
//        JListDatos loList = new JListDatos(moServerBD1, psTabla, new String[]{""}, new int[]{JListDatos.mclTipoNumero},new int[]{0} );
//        loList.recuperarDatos(loSelect,false,JListDatos.mclSelectNormal);
//        if(loList.moveFirst()){
//            return loList.getFields(0).getInteger();
//        }else{
//            return 0;
//        }
//        
        return getRegistrosTablaBDstatic(moServerBD1, getTable1(psTabla));
    }
    public static int getRegistrosTablaBDstatic(IServerServidorDatos poserver,JTableDef poTabla)  throws Exception {
        JSelect loSelect = new JSelect(poTabla.getNombre());
        loSelect.addCampo(JSelectCampo.mclFuncionCount, poTabla.getNombre(), poTabla.getCampos().get(0).getNombre());
        
        JListDatos loList = new JListDatos(poserver, poTabla.getNombre(), new String[]{""}, new int[]{JListDatos.mclTipoNumero},new int[]{0} );
        loList.recuperarDatos(loSelect,false,JListDatos.mclSelectNormal);
        if(loList.moveFirst()){
            return loList.getFields(0).getInteger();
        }else{
            return 0;
        }
    }
    public int getRegistrosTablaBD2(String psTabla)  throws Exception {
//        JSelect loSelect = new JSelect(psTabla);
//        loSelect.addCampo(JSelectCampo.mclFuncionCount, psTabla, getTable2(psTabla).getCampos().get(0).getNombre());
//        
//        JListDatos loList = new JListDatos(moServerBD2, psTabla, new String[]{""}, new int[]{JListDatos.mclTipoNumero},new int[]{0} );
//        loList.recuperarDatos(loSelect,false,JListDatos.mclSelectNormal);
//        if(loList.moveFirst()){
//            return loList.getFields(0).getInteger();
//        }else{
//            return 0;
//        }
        return getRegistrosTablaBDstatic(moServerBD2, getTable2(psTabla));
    }
    public JTableDefs getTablasBD1(){
        return moTablasBD1;
    }
    public void setTablasBD1(JTableDefs poTablasBD1){
        moTablasBD1=poTablasBD1;
    }
    public JTableDefs getTablasBD2(){
        return moTablasBD2;
    }
    public void setTablasBD2(JTableDefs poTablasBD2){
        moTablasBD2=poTablasBD2;
    }

    /**
     * @return the mlNumReg
     */
    public int getNumReg() {
        return mlNumReg;
    }

    /**
     * @param mlNumReg the mlNumReg to set
     */
    public void setNumReg(int mlNumReg) {
        this.mlNumReg = mlNumReg;
    }

    /**
     * @return the mlTransaccionCada
     */
    public int getTransaccionCada() {
        return mlTransaccionCada;
    }

    /**
     * @param mlTransaccionCada the mlTransaccionCada to set
     */
    public void setTransaccionCada(int mlTransaccionCada) {
        this.mlTransaccionCada = mlTransaccionCada;
    }

}
