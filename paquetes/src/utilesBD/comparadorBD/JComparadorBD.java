/*
 * JComparadorBD.java
 *
 * Created on 10 de agosto de 2005, 18:09
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesBD.comparadorBD;

import utiles.IListaElementos;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JListaElementos;


public class JComparadorBD {
    private final JTableDefs moTablas1;
    private final JTableDefs moTablas2;
    
    private final IServerServidorDatos moServer1;
    private final IServerServidorDatos moServer2;
    
    private IListaElementos<JDiferencia> moDiferencias=null;
    
    private final boolean mbIgnorarDescripciones = true;

    private boolean mbSoloTablasNormales = true;
    private boolean mbIgnorarDiferenciaBooleanInteger = false;
    
    /** Creates a new instance of JComparadorBD */
    public JComparadorBD(JTableDefs poTablas1, JTableDefs poTablas2, IServerServidorDatos poServer1, IServerServidorDatos poServer2) {
        moTablas1 = poTablas1;
        moTablas2 = poTablas2;
        moServer1 = poServer1;
        moServer2 = poServer2;
    }
    public IListaElementos<JDiferencia> getDiferencias(){
        return moDiferencias;
    }
    
    public void setSoloTablasNormales(boolean pbSoloTablasNormales){
        mbSoloTablasNormales = pbSoloTablasNormales;
    }
    
    public void setIgnorarDiferenciaBooleanInteger(boolean pbIg){
        mbIgnorarDiferenciaBooleanInteger = pbIg;
    }
    
    public void ejecutar() throws Exception {
        moDiferencias = new JListaElementos<JDiferencia>();
        diferenciasTablas();
    }
    private void diferenciasRelaciones(final JTableDef poTabla1, final JTableDef poTabla2){
        JDiferencia loDif = null;
        StringBuilder lsDescripcion = new StringBuilder(20);
        //comprobamos las Campos que no existen
        for(int i = 0 ; i < poTabla1.getRelaciones().count(); i++){
            JRelacionesDef loRelaciones1 = poTabla1.getRelaciones().getRelacion(i);
            JRelacionesDef loRelaciones2 = poTabla2.getRelaciones().getRelacion(loRelaciones1.getNombreRelacion());
            if(loRelaciones1.getTipoRelacion()==loRelaciones1.mclRelacionImport){
                if(loRelaciones2 == null){
                    loDif = new JDiferencia(
                            "No existe la Relacion " + poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion() ,
                            poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion(),
                            JDiferencia.mclTipoNoExiste, JDiferencia.mclRelacion,
                            new JActualizarEstructura(loRelaciones1, poTabla1, poTabla2, JListDatos.mclNuevo, "","",""),
                            moServer2);
                }else{
                    lsDescripcion.setLength(0);
                    boolean lbPosible = false;
                    if(loRelaciones1.getTipoRelacion() != loRelaciones2.getTipoRelacion() ){
                        lsDescripcion.append("Diferencia en tipo relacion " + poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion());
                        lbPosible = true;
                    }
                    if(!loRelaciones1.getTablaRelacionada().equalsIgnoreCase(loRelaciones2.getTablaRelacionada()) ){
                        lsDescripcion.append("Diferencia en TablaRelacionada " + poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion());
                        lbPosible = true;
                    }
                    if(loRelaciones1.getListaCamposRelacion().size() != loRelaciones2.getListaCamposRelacion().size() ){
                        lsDescripcion.append("Diferencia en el numero de campos " + poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion());
                        lbPosible = true;
                    }else{
                        for(int lCampo = 0 ; lCampo < loRelaciones1.getCamposRelacionCount(); lCampo++){
                            if(!loRelaciones1.getCampoRelacion(lCampo).equalsIgnoreCase(loRelaciones2.getCampoRelacion(lCampo))){
                                lbPosible = true;
                                lsDescripcion.append("Diferencia en " + poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion() + " por el campo " + loRelaciones1.getCampoRelacion(lCampo) +"!="+loRelaciones2.getCampoRelacion(lCampo));
                            }
                        }
                    }
                    if(lsDescripcion.length()!=0){
                        if(lbPosible){
                            loDif = new JDiferencia(lsDescripcion.toString(),
                                poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion(),
                                JDiferencia.mclTipoModificacion, JDiferencia.mclRelacion,
                                new JActualizarEstructura(loRelaciones1, poTabla1, poTabla2, JListDatos.mclEditar, "","",""),
                                moServer2);
                        }else{
                            loDif = new JDiferencia( lsDescripcion.toString(),
                                            poTabla1.getNombre() + "."  + loRelaciones1.getNombreRelacion(),
                                            JDiferencia.mclTipoModificacion, JDiferencia.mclRelacion,
                                            null, null);
                        }
                    }
                }
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
            loDif=null;
        }
        //comprobamos las Relacions que sobran
        for(int i = 0 ; i < poTabla2.getRelaciones().count(); i++){
            JRelacionesDef loRelacions2 = poTabla2.getRelaciones().getRelacion(i);
            JRelacionesDef loRelacions1 = poTabla1.getRelaciones().getRelacion(loRelacions2.getNombreRelacion());
            if(loRelacions2.getTipoRelacion()==loRelacions2.mclRelacionImport){            
                if(loRelacions1 == null){
                    loDif = new JDiferencia(
                            "Sobra el Relacion " + poTabla2.getNombre() + "."  + loRelacions2.getNombreRelacion() ,
                            poTabla2.getNombre() + "."  + loRelacions2.getNombreRelacion() ,
                            JDiferencia.mclTipoSobra, JDiferencia.mclRelacion,
                            new JActualizarEstructura(loRelacions2,poTabla1, poTabla2, JListDatos.mclBorrar, "","",""),
                            moServer2);
                }
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
            loDif=null;
        }
    }
    private void diferenciasIndices(final JTableDef poTabla1, final JTableDef poTabla2){
        JDiferencia loDif = null;
        StringBuilder lsDescripcion = new StringBuilder(20);
        //comprobamos las Campos que no existen
        for(int i = 0 ; i < poTabla1.getIndices().getCountIndices(); i++){
            JIndiceDef loIndices1 = poTabla1.getIndices().getIndice(i);
            JIndiceDef loIndices2 = poTabla2.getIndices().getIndice(loIndices1.getNombreIndice());
            if(loIndices2 == null){
                loDif = new JDiferencia(
                        "No existe el indice " + poTabla1.getNombre() + "."  + loIndices1.getNombreIndice() ,
                        poTabla1.getNombre() + "."  + loIndices1.getNombreIndice(),
                        loDif.mclTipoNoExiste, loDif.mclIndice,
                        new JActualizarEstructura(loIndices1, poTabla1, JListDatos.mclNuevo, "","",""),
                        moServer2);
            }else{
                lsDescripcion.setLength(0);
                boolean lbPosible = false;
                if(loIndices1.getEsPrimario() != loIndices2.getEsPrimario() ){
                    lsDescripcion.append("Diferencia en ,es primario? " + poTabla1.getNombre() + "."  + loIndices1.getNombreIndice());
                    lbPosible = true;
                }
                if(loIndices1.getEsUnico() != loIndices2.getEsUnico() ){
                    lsDescripcion.append("Diferencia en ,es unico? " + poTabla1.getNombre() + "."  + loIndices1.getNombreIndice());
                    lbPosible = true;
                }
                if(loIndices1.getCountCamposIndice() != loIndices2.getCountCamposIndice() ){
                    lsDescripcion.append("Diferencia en el numero de campos " + poTabla1.getNombre() + "."  + loIndices1.getNombreIndice());
                    lbPosible = true;
                }else{
                    for(int lCampo = 0 ; lCampo < loIndices1.getCountCamposIndice(); lCampo++){
                        if(!loIndices1.getCampoIndice(lCampo).equalsIgnoreCase(loIndices2.getCampoIndice(lCampo))){
                            lbPosible = true;
                            lsDescripcion.append("Diferencia en " + poTabla1.getNombre() + "."  + loIndices1.getNombreIndice() + " por el campo " + loIndices1.getCampoIndice(lCampo) +"!="+loIndices2.getCampoIndice(lCampo));
                        }
                    }
                }
                if(lsDescripcion.length()!=0){
                    if(lbPosible){
                        loDif = new JDiferencia(lsDescripcion.toString(),
                            poTabla1.getNombre() + "."  + loIndices1.getNombreIndice(),
                            loDif.mclTipoModificacion, loDif.mclIndice,
                            new JActualizarEstructura(loIndices1, poTabla1, JListDatos.mclEditar, "","",""),
                            moServer2);
                    }else{
                        loDif = new JDiferencia( lsDescripcion.toString(),
                                        poTabla1.getNombre() + "."  + loIndices1.getNombreIndice(),
                                        loDif.mclTipoModificacion, loDif.mclIndice,
                                        null, null);
                    }
                }
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
            loDif=null;
        }
        //comprobamos las indices que sobran
        for(int i = 0 ; i < poTabla2.getIndices().getCountIndices(); i++){
            JIndiceDef loIndices2 = poTabla2.getIndices().getIndice(i);
            JIndiceDef loIndices1 = poTabla1.getIndices().getIndice(loIndices2.getNombreIndice());
            if(loIndices1 == null){
                loDif = new JDiferencia(
                        "Sobra el indice " + poTabla2.getNombre() + "."  + loIndices2.getNombreIndice() ,
                        poTabla2.getNombre() + "."  + loIndices2.getNombreIndice() ,
                        loDif.mclTipoSobra, loDif.mclIndice,
                        new JActualizarEstructura(loIndices2, poTabla2, JListDatos.mclBorrar, "","",""),
                        moServer2);
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
            loDif=null;
        }
    }
    private void diferenciasCampos(final JTableDef poTabla1, final JTableDef poTabla2) {
        JDiferencia loDif = null;
        StringBuilder lsDescripcion = new StringBuilder(20);
        //comprobamos las Campos que no existen
        for(int i = 0 ; i < poTabla1.getCampos().count(); i++){
            JFieldDef loCampo1 = poTabla1.getCampos().get(i);
            JFieldDef loCampo2 = poTabla2.getCampos().get(loCampo1.getNombre());
            if(loCampo2 == null){
                try {
                    loCampo1 = loCampo1.Clone();
                } catch (CloneNotSupportedException ex) {}
                loCampo1.setNullable(true);
                loCampo1.setPrincipalSN(false);
                loDif = new JDiferencia(
                        "No existe el campo " + poTabla1.getNombre() + "."  + loCampo1.getNombre() , 
                        poTabla1.getNombre() + "."  + loCampo1.getNombre(), 
                        loDif.mclTipoNoExiste, loDif.mclCampo, 
                        new JActualizarEstructura(loCampo1, poTabla1, JListDatos.mclNuevo, "","",""),
                        moServer2);
            }else{
                lsDescripcion.setLength(0);
                boolean lbPosible = false;
                if((loCampo1.getDescripcion()!=null) && (!loCampo1.getDescripcion().equals(loCampo2.getDescripcion())&&(!mbIgnorarDescripciones))){
                    lsDescripcion.append("Diferencia en descripcion del campo ").append(poTabla1.getNombre()).append(".").append(loCampo1.getNombre());
                }
                if(loCampo1.getTipo() != loCampo2.getTipo() ){
                    if(!(mbIgnorarDiferenciaBooleanInteger 
                            && loCampo1.getTipo() == JListDatos.mclTipoBoolean 
                            && loCampo2.getTipo() == JListDatos.mclTipoNumero )){
                        lsDescripcion.append("Diferencia en el tipo ").append(poTabla1.getNombre()).append(".").append(loCampo1.getNombre());
                        lbPosible = true;
                    }
                }else{
                    if(loCampo1.getTamano() != loCampo2.getTamano() && 
                        (loCampo1.getTipo() == JListDatos.mclTipoCadena
                        )){ //loCampo1.getTipo() == JListDatos.mclTipoFecha no incluimos la fecha ya q en bd distintas las long. son diferentes
                        //las longitudes de los campos memo pueden cambiar entre distintas base datos
                        boolean lbEsMemo1 = false;
                        boolean lbEsMemo2 = false;
                        if ((loCampo1.getTamano() > 3999) || (loCampo1.getTamano()<= 0)) {
                            lbEsMemo1=true;
                        }
                        if ((loCampo2.getTamano() > 3999) || (loCampo2.getTamano()<= 0)) {
                            lbEsMemo2=true;
                        }

                        if(lbEsMemo1 && lbEsMemo2){
                            //si son los 2 memo no hacer nada
                        }else{
                            lsDescripcion.append("Diferencia Tamano " + poTabla1.getNombre() + "."  + loCampo1.getNombre());
                            lbPosible = true;
                        }
                    }
                }
                if(loCampo1.getNullable() != loCampo2.getNullable() && !mbIgnorarDiferenciaBooleanInteger){
                    lsDescripcion.append("Diferencia Nullable " + poTabla1.getNombre() + "."  + loCampo1.getNombre());
                    lbPosible = true;
                }
                if(lsDescripcion.length()!=0){
                    if(lbPosible){
                        loDif = new JDiferencia(lsDescripcion.toString(),  
                            poTabla1.getNombre() + "."  + loCampo1.getNombre(), 
                            loDif.mclTipoModificacion, loDif.mclCampo, 
                            new JActualizarEstructura(loCampo1, poTabla1, JListDatos.mclEditar, "","",""),
                            moServer2);
                    }else{
                        loDif = new JDiferencia( lsDescripcion.toString(),  
                                        poTabla1.getNombre() + "."  + loCampo1.getNombre(), 
                                        loDif.mclTipoModificacion, loDif.mclCampo, 
                                        null, null);
                    }
                }
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
            loDif=null;
        }
        //comprobamos las campos que sobran
        for(int i = 0 ; i < poTabla2.getCampos().count(); i++){
            JFieldDef loCampo2 = poTabla2.getCampos().get(i);
            JFieldDef loCampo1 = poTabla1.getCampos().get(loCampo2.getNombre());
            if(loCampo1 == null){
                loDif = new JDiferencia(
                        "Sobra el campo " + poTabla2.getNombre() + "."  + loCampo2.getNombre() , 
                        poTabla2.getNombre() + "."  + loCampo2.getNombre() , 
                        loDif.mclTipoSobra, loDif.mclCampo, 
                        new JActualizarEstructura(loCampo2, poTabla2, JListDatos.mclBorrar, "","",""),
                        moServer2);
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
            loDif=null;
        }
    }
    private void diferenciasTablas(){
        JDiferencia loDif = null;
        //comprobamos las tablas que no existen
        for(int i = 0 ; i < moTablas1.getListaTablas().size(); i++){
            boolean lbcontinuar = true;
            loDif = null;
            JTableDef loTabla1 = moTablas1.get(i);
            JTableDef loTabla2 = moTablas2.get(loTabla1.getNombre());
            if(mbSoloTablasNormales){
                lbcontinuar = (loTabla1.getTipo() == loTabla1.mclTipoTabla);
            }
            if(lbcontinuar){
                if(loTabla2 == null){
                    loDif = new JDiferencia(
                            "No existe la tabla " + loTabla1.getNombre(),
                            loTabla1.getNombre(),
                            loDif.mclTipoNoExiste, loDif.mclTabla, 
                            new JActualizarEstructura(loTabla1, JListDatos.mclNuevo, "","",""),
                            moServer2);
                }else{
                    diferenciasCampos(loTabla1, loTabla2);
                    diferenciasIndices(loTabla1, loTabla2);
                    diferenciasRelaciones(loTabla1, loTabla2);
                }
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
        }
        //comprobamos las tablas que sobran
        for(int i = 0 ; i < moTablas2.getListaTablas().size(); i++){
            boolean lbcontinuar = true;
            loDif=null;
            JTableDef loTabla2 = moTablas2.get(i);
            JTableDef loTabla1 = moTablas1.get(loTabla2.getNombre());
            if(mbSoloTablasNormales){
                lbcontinuar = (loTabla2.getTipo() == loTabla1.mclTipoTabla);
            }
            if(lbcontinuar){
                if(loTabla1 == null){
                    loDif = new JDiferencia("Sobra la tabla " + loTabla2.getNombre(), 
                            loTabla2.getNombre(),
                            loDif.mclTipoSobra, loDif.mclTabla, 
                            new JActualizarEstructura(loTabla2, JListDatos.mclBorrar, "","",""),
                            moServer2);
                }
            }
            if(loDif!=null){
                moDiferencias.add(loDif);
            }
        }
        
    }
    
}
