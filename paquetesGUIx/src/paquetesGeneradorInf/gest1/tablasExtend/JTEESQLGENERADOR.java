/*
* JTEESQLGENERADOR.java
*
* Creado el 9/9/2013
*/

package paquetesGeneradorInf.gest1.tablasExtend;

import java.util.HashMap;
import ListDatos.*;
import ListDatos.estructuraBD.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import paquetesGeneradorInf.gest1.tablas.*;
import paquetesGeneradorInf.gest1.*;
import utilesGUIx.formsGenericos.*;
import paquetesGeneradorInf.gest1.consultas.*;
import paquetesGeneradorInf.gest1.tablasControladoras.*;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import utiles.CadenaLarga;

public class JTEESQLGENERADOR extends JTSQLGENERADOR {
    private static final long serialVersionUID = 1L;
    public static final String[] masCaption = JDatosGeneralesP.getDatosGenerales().getTextosForms().getCaptions(msCTabla, masNombres);
    protected transient HashMap moListaRelaciones = new HashMap();

    /**
     * Crea una instancia de la clase intermedia para la tabla SQLGENERADOR incluyendole el servidor de datos
     */
    public JTEESQLGENERADOR(IServerServidorDatos poServidorDatos) {
        super(poServidorDatos);
        moList.getFields().setCaptions(masCaption);
        moList.getFields().get(lPosiCODIGOSQLGENERADOR).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);
    }

    public static boolean getPasarACache(){
        return false;
    }
    public static JTEESQLGENERADOR getTabla(final String psCODIGOSQLGENERADOR,final IServerServidorDatos poServer) throws Exception {
        JTEESQLGENERADOR loTabla = new JTEESQLGENERADOR(poServer);
        if(getPasarACache()){
            loTabla.recuperarTodosNormalCache();
            loTabla.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND, 
                    JListDatos.mclTIgual, 
                    malCamposPrincipales, 
                    new String[]{psCODIGOSQLGENERADOR});
            loTabla.moList.filtrar();
        }else{
            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(
                JListDatos.mclTIgual, malCamposPrincipales, new String[]{psCODIGOSQLGENERADOR}) ,false);
        }
        return loTabla;
    }


    public static JTEESQLGENERADOR getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {
        return getTabla(
                 poFila.msCampo(lPosiCODIGOSQLGENERADOR),
                poServer);
    }
    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {
        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();
        loParam.mlCamposPrincipales = JTFORMSQLGENERADOR.getFieldsEstaticos().malCamposPrincipales();
        loParam.masTextosDescripciones = masCaption;
        loParam.mbConDatos=false;
        loParam.mbMensajeSiNoExiste=true;

        loParam.malDescripciones = new int[]{
            JTFORMSQLGENERADOR.lPosiNOMBRE,
            JTFORMSQLGENERADOR.lPosiPALABRASCLAVE,
            JTFORMSQLGENERADOR.lPosiPADRE,
            JTFORMSQLGENERADOR.lPosiTABLAPRINCIPAL,
            JTFORMSQLGENERADOR.lPosiSQL,
            JTFORMSQLGENERADOR.lPosiOBSERVACIONES
            };
        loParam.masTextosDescripciones = new String[]{
             JTFORMSQLGENERADOR.getFieldEstatico(JTFORMSQLGENERADOR.lPosiNOMBRE).getCaption(),
             JTFORMSQLGENERADOR.getFieldEstatico(JTFORMSQLGENERADOR.lPosiPALABRASCLAVE).getCaption(),
             JTFORMSQLGENERADOR.getFieldEstatico(JTFORMSQLGENERADOR.lPosiPADRE).getCaption(),
             JTFORMSQLGENERADOR.getFieldEstatico(JTFORMSQLGENERADOR.lPosiTABLAPRINCIPAL).getCaption(),
             JTFORMSQLGENERADOR.getFieldEstatico(JTFORMSQLGENERADOR.lPosiSQL).getCaption(),
             JTFORMSQLGENERADOR.getFieldEstatico(JTFORMSQLGENERADOR.lPosiOBSERVACIONES).getCaption()
            };

        loParam.moControlador = new JT2SQLGENERADOR(poServer, poMostrar);

        JTFORMSQLGENERADOR loConsulta = new JTFORMSQLGENERADOR(poServer);
        loConsulta.crearSelectSimple();
        
        loParam.moTabla = loConsulta;
        
        return loParam;
    }
   public void valoresDefecto() throws Exception {   
   }
    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{
            if(!pbEsMismaclave){
                moListaRelaciones = null;
                moListaRelaciones = new HashMap();
            }
    }
    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{
            comprobarClaveCargar(isMismaClave());
            if(!isMismaClave()){
                msCodigoRelacionado = getClave();
            }
            
        
    }
    public IResultado guardar() throws Exception{
        //se comprueba antes de guardar la clave pq despues de 
        //guardar puede cambiar (por ejem. si estaba en modo nuevo )
        boolean lbIsMismaClave =  isMismaClave();
        //comprobamos las tablas relacionadas
        comprobarClaveCargar(lbIsMismaClave);
        JResultado loResult = new JResultado("", true);
        loResult.addResultado(moList.update(true));
        
        if(loResult.getBien() && 
           lbIsMismaClave) {
//            JTEESUBFAMILIAS loSub = JTEESUBFAMILIAS.getTabla(getCODIGOFAMILIA().getString(),moList.moServidor);
//            if(loSub.moList.moveFirst()){
//                do{
//                    loSub.getUSALOTESSN().setValue(getUSALOTESSN().getBoolean()); 
//                    loSub.moList.update(false);
//                }while(loSub.moList.moveNext());
//                JActualizarConj loAct = new JActualizarConj("","","");
//                loAct.crearUpdateAPartirList(loSub.moList);
//                loResult.addResultado(loSub.moList.moServidor.actualizarConj(loAct));
//                
//            }
            
        }
        //si estaba en modo nuevo, si todo bien se queda en modo Nada/editar, por lo q hay
        //q actualizar el codigo relacionado
        if(loResult.getBien()){
            if(lbIsMismaClave){
                msCodigoRelacionado = getClave();
            }
        }        
        return loResult;
    }
    public IResultado borrar() throws Exception {
        JTEESQLGENERADORATRIB loAtrib = new JTEESQLGENERADORATRIB(null);
        loAtrib.moList.addNew();
        loAtrib.getCODIGOSQLGENERADOR().setValue(getCODIGOSQLGENERADOR().getString());
        loAtrib.moList.update(false);
        loAtrib.moList.moFila().setTipoModif(JListDatos.mclNada);
        loAtrib.getATRIBUTODEF().setPrincipalSN(false);
        loAtrib.moList.borrar(false);
        JActualizarConj loBorrar = new JActualizarConj("", "", "");
        loBorrar.add(loAtrib.moList);
        loBorrar.add(moList.getFields(), msCTabla, JListDatos.mclBorrar);
        IServerServidorDatos loServer = moList.moServidor;
        IResultado loREsult = loServer.ejecutarServer(loBorrar);
        if(loREsult.getBien()){
            moList.borrar(false);
        }
        return loREsult;
    }
    
    public JSelect getSQLSelect() throws Exception{
//        JAXBContext context = JAXBContext.newInstance(JSelect.class);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        JSelect provincaAux = (JSelect) unmarshaller.unmarshal(new CadenaLarga(getSQL().getString()));
//        return provincaAux;
        XStream xstream = new XStream(new DomDriver());
        return (JSelect) xstream.fromXML(new StringReader(getSQL().getString()));
                
    }
    public void setSQLSelect(JSelect poSelect) throws Exception{
//        JAXBContext context = JAXBContext.newInstance(JSelect.class);
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        //Mostramos el documento XML generado por la salida estandar
//        CadenaLargaOut loOut = new CadenaLargaOut();
//        marshaller.marshal(poSelect, loOut);
//        getSQL().setValue(loOut.toString());
        XStream xstream = new XStream(new DomDriver());
        StringWriter loW = new StringWriter();
        xstream.toXML(poSelect, loW);
        getSQL().setValue(loW.toString());
        getTABLAPRINCIPAL().setValue(poSelect.getFrom().getTablaUnion(0).getTabla2());

    }
    
    public void setDatos(JGuiConsultaDatos poDatos) throws Exception{
        setSQLSelect(poDatos.getSelect());
        getNOMBRE().setValue(poDatos.getNombre());
        getOBSERVACIONES().setValue(poDatos.getObservaciones());
    }
    public JGuiConsultaDatos getDatos() throws Exception{
        JGuiConsultaDatos loDatos = new JGuiConsultaDatos(moList.moServidor);
        loDatos.setSelect(getSQLSelect());
        loDatos.setNombre(getNOMBRE().getString());
        loDatos.setObservaciones(getOBSERVACIONES().getString());
        return loDatos;
    }
    
    
}
