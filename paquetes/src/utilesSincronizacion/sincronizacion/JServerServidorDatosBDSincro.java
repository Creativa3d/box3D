/*
 * JServerServidorDatosBD.java
 *
 * Created on 16 de enero de 2007, 11:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesSincronizacion.sincronizacion;

import ListDatos.IResultado;
import ListDatos.ISelectEjecutarSelect;
import ListDatos.IServerEjecutar;
import ListDatos.JActualizar;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JResultado;
import ListDatos.JServerServidorDatosBD;
import ListDatos.JXMLSelectMotor;
import ListDatos.estructuraBD.*;

import utiles.*;
import utilesSincronizacion.tablasExtend.JTEETABLASINCRONIZACIONBORRADOS;
import utilesSincronizacion.tablasExtend.JTEETABLASINCRONIZACIONGENERAL;

//con base de datos

public class JServerServidorDatosBDSincro extends JServerServidorDatosBD {
    private JTEETABLASINCRONIZACIONGENERAL moGeneral = new JTEETABLASINCRONIZACIONGENERAL(this);
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosBDSincro() {
        super();
    }
    public JServerServidorDatosBDSincro(String psDriver, String psURL, String psUsu, String psPassword) throws Throwable{
        super(psDriver, psURL, psUsu, psPassword);
    }
    public JServerServidorDatosBDSincro(final String psDriver, final String psURL, final String psUsu, final String psPassword, final String psTipoBDStandar) throws Throwable{
        super(psDriver, psURL, psUsu, psPassword, psTipoBDStandar);
    }
    public IResultado moActualizar(String psNombreServlet, JActualizar poActualizar, IServerEjecutar poEjecutar, ISelectEjecutarSelect poEstruc){
        IResultado loResult = null;
        try{
            //comenzamos la transaccion
            beginTrans();
            //ejecutamos el actualizador
            if(poActualizar==null){
                if(poEjecutar==null){
                    loResult = ejecutarSQLCompleta(poEstruc, getConec(), moSelect);
                }else{
                    loResult = poEjecutar.ejecutar(this);
                }
            }else{
                BooleanModif lbYaNumTransac=new BooleanModif();
                loResult = procesarSincronizar(poActualizar, lbYaNumTransac);
                
                if(loResult==null || loResult.getBien()){
                    loResult = ejecutarSQLCompleta(getConec(), moSelect,poActualizar);
                }
                
                if(loResult.getBien() && !lbYaNumTransac.booleanValue()){
                    IListaElementos loLista = loResult.getListDatos();
                    for(int i = 0 ; loLista!=null && i < loLista.size(); i++){
                        JListDatos loListAuxConDatos = (JListDatos)loLista.get(i);
                        if(!isTablaSincronizacion(loListAuxConDatos.msTabla)){
                            JListDatos loList = getTableDefs().get(loListAuxConDatos.msTabla).getListDatos();
                            if(loListAuxConDatos.moveFirst()){
                                do{

                                    int[] lalTiposCamposPrincipalesMasTransac = new int[loList.getFields().malCamposPrincipales().length+1];
                                    String[] lasNombresCamposPrincipalesMasTransac = new String[loList.getFields().malCamposPrincipales().length+1];
                                    int[] lalCamposPrincipales = new int[loList.getFields().malCamposPrincipales().length];
                                    int k=0;
                                    for(int lCampo = 0 ; lCampo < loList.getFields().size(); lCampo++){
                                        if(loList.getFields(lCampo).getPrincipalSN()) {
                                            lalTiposCamposPrincipalesMasTransac[k] = loList.getFields(lCampo).getTipo();
                                            lasNombresCamposPrincipalesMasTransac[k] = loList.getFields(lCampo).getNombre();
                                            lalCamposPrincipales[k] = lCampo;
                                            k++;
                                        }

                                    }
                                    lalTiposCamposPrincipalesMasTransac[lalTiposCamposPrincipalesMasTransac.length-1] = JListDatos.mclTipoNumero;
                                    lasNombresCamposPrincipalesMasTransac[lalTiposCamposPrincipalesMasTransac.length-1] = JSincronizar.mcsCampoNumeroTransaccion;

                                    JFieldDefs loCampos = new JFieldDefs(
                                            lasNombresCamposPrincipalesMasTransac, 
                                            lalCamposPrincipales, 
                                            lasNombresCamposPrincipalesMasTransac, 
                                            lalTiposCamposPrincipalesMasTransac, 
                                            null);
                                    k=0;
                                    for(int lCampo = 0 ; lCampo < loList.getFields().size(); lCampo++){
                                        if(loList.getFields(lCampo).getPrincipalSN()) {
                                            loCampos.get(k).setValue(loListAuxConDatos.moFila().msCampo(lCampo));
                                            k++;
                                        }
                                    }
                                    loCampos.get(lalTiposCamposPrincipalesMasTransac.length-1).setValue(
                                            getNumeroTransacGlobalMasUno()
                                            );

                                    JActualizar loActu = new JActualizar(
                                            loCampos, 
                                            loList.msTabla, 
                                            JListDatos.mclEditar, 
                                            msUsuario, msPassWord, msPermisos);
                                    IResultado loRes = ejecutarSQLCompleta(getConec(), moSelect,loActu);
                                    if(!loRes.getBien()) {
                                        loResult = new JResultado("Fallo de actualizacion del campo " + JSincronizar.mcsCampoNumeroTransaccion+ "(" + loRes.getMensaje()+")", false);
                                    }
                                }while(loListAuxConDatos.moveNext());
                            }
                        }
                    }
                }
            }
            
            
        }catch(Exception e1){
             loResult = new JResultado(new JFilaDatosDefecto(""), "", "En Servidor=" + e1.toString(), false, -1);
        }finally{
            if(getConec()!=null) {
                try{
                    //terminamos la trasaccion
                    if(loResult== null){
                        rollBackTrans();
                    }else{
                        if(loResult.getBien()){
                            commitTrans();
                        }else{
                            rollBackTrans();
                        }
                    }
                }catch(Exception e2){
                    JDepuracion.anadirTexto(this.getClass().getName(), e2);
                }
            }
        }

        return loResult;
    }
//    //preprocesamos el interfaz ISelectEjecutarUpdate, pbYaNumTransac sirve para comprobar si despues de actualizar real hay q postprocesar el resultado
//    private IResultado procesarSincronizar(ISelectEjecutarUpdate poActualizar, BooleanModif pbYaNumTransac) throws Exception {
//        IResultado loResult = null;
//        //si es asignable a un JActualizar preprocesamos
//        if(poActualizar.getClass().isAssignableFrom(JActualizar.class)){
//            pbYaNumTransac.mbValor &= true;
//            loResult = procesarSincronizar((JActualizar)poActualizar, pbYaNumTransac);
//        //si es asignable a un JActualizarConj preprocesamos cada una de sus filas
//        }else  if(poActualizar.getClass().isAssignableFrom(JActualizarConj.class)){
//            pbYaNumTransac.mbValor &= true;
//            loResult = procesarSincronizar((JActualizarConj)poActualizar, pbYaNumTransac);
//        } else {
//            pbYaNumTransac.mbValor &= false;
//        }
//        return loResult;
//    }
//    //procesamos el Conj. de actualizaciones
//    private IResultado procesarSincronizar(JActualizarConj poActualizarConj, BooleanModif pbYaNumTransac) throws Exception {
//        IResultado loResult=null;
//        //si tiene un JListDatos creamos los updates y lo establecemos a nulo
//        if(poActualizarConj.getListDatos()!=null){
//            poActualizarConj.crearUpdateAPartirList(poActualizarConj.getListDatos());
//            poActualizarConj.add((JListDatos)null);
//        }
//        //para cada SelectUpdate preprocesamos
//        for(int i = 0 ; i < poActualizarConj.getSize(); i++){
//            procesarSincronizar(poActualizarConj.getSelectUpdate(i), pbYaNumTransac);
//        }
//
//        return loResult;
//    }
    //preprocesamos el JActualizar
    private IResultado procesarSincronizar(JActualizar poActu, BooleanModif pbYaNumTransac) throws Exception{
        IResultado loResult=null;
        pbYaNumTransac.mbValor &= true;
        //si no es una tabla sincronizada
        if(!isTablaSincronizacion(poActu.getTabla())){
            //si es borrar la añadimos a borrados
            if(poActu.getTipoModif() == JListDatos.mclBorrar){
                JTEETABLASINCRONIZACIONBORRADOS loBorrados = new JTEETABLASINCRONIZACIONBORRADOS(this);
                loBorrados.moList.addNew();
                loBorrados.getTABLA().setValue(poActu.getTabla());
                loBorrados.getNUMEROTRANSACSINCRO().setValue(getNumeroTransacGlobalMasUno());
                loBorrados.getREGISTRO().setValue(
                        JXMLSelectMotor.msCamposEnXML(poActu.getFields(), true)
                        );
                loResult = loBorrados.moList.update(true);
            }else{
                //si es edit/add le añadimos el campo numerotrasac en el caso de q no lo tenga y
                //le ponemos el valor del ult.+1 (getNumeroTransacGlobalMasUno)
                JFieldDef loCampo = poActu.getFields().get(JSincronizar.mcsCampoNumeroTransaccion);
                if(loCampo==null){
                    loCampo = new JFieldDef(
                            JListDatos.mclTipoNumero,
                            JSincronizar.mcsCampoNumeroTransaccion, 
                            "",
                            false);

                    poActu.getFields().addField(loCampo);
                }
                loCampo.setValue(getNumeroTransacGlobalMasUno());
            }
        }
        return loResult;
    }
    
    private boolean isTablaSincronizacion(String psTabla){
        return psTabla.equals(JTEETABLASINCRONIZACIONBORRADOS.msCTabla) ||
               psTabla.equals(JTEETABLASINCRONIZACIONGENERAL.msCTabla);
        
    }
    private int getNumero(String psValor){
        return (int)JConversiones.cdbl(psValor);
    }
    private int getNumeroTransacGlobalMasUno() throws Exception{
        moGeneral.inicializar();
        return getNumero(moGeneral.getAtributo(JSincronizar.mcsCampoNumeroTransaccion)) + 1;
    }
}

class BooleanModif {
    public boolean mbValor = true;
    
    public boolean booleanValue(){
        return mbValor;
    }
}
