/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;
import ListDatos.estructuraBD.*;

public class JGeneradorJTable2 implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private boolean mbRelacionesExport;
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorJTable2(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        JTableDef loTabla = moConex.getTablaBD(moConex.getTablaActual());
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual());;
        boolean lbEsCamposMinimos = (loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos());
        String lsTablaClase = moUtiles.msSustituirRaros(loTabla.getNombre());

        //Compruebo el numero de relaciones de importacion para saber si tiene que llevar pestaNas
        mbRelacionesExport = moConex.tieneRelacionesExport();
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* "+moUtiles.getNombreTablaExtends(lsTablaClase) + ".java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION************************************
        lsText.append("package " + moConex.getDirPadre() + "." + moUtiles.getPaqueteExtend() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.util.HashMap;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.estructuraBD.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + "." + "tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + "." + "*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.busqueda.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.IGestionProyecto;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.panelesGenericos.JConsulta;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".consultas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".tablasControladoras.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".forms.*;");lsText.append(JUtiles.msRetornoCarro);
        

        lsText.append(JUtiles.msRetornoCarro);
        //***********************************************
        
        //CUERPO DE LA CLASE**************************************
        lsText.append("public class "+moUtiles.getNombreTablaExtends(lsTablaClase) + " extends JT" + lsTablaClase + (lbEsCamposMinimos?" implements IConsulta ":"") +" {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static final long serialVersionUID = 1L;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final String[] masCaption = JDatosGeneralesP.getDatosGenerales().getTextosForms().getCaptions(msCTabla, masNombres);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    protected transient HashMap moListaRelaciones = new HashMap();");lsText.append(JUtiles.msRetornoCarro);
//        //Controladores para las pestaNas
//        if(mbRelacionesExport) {
//            for(int i=0;i<loTabla.getRelaciones().count();i++) {
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
//                    JTableDef loTablaRelac = moConex.getTablaBD(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
//                    if(loTablaRelac.getCampos().size()>=moProyecto.getOpciones().getCamposMinimosTodosModulos()){
//                        lsText.append("    protected JT2"+ moConex.getNomTablaSimple(i) +" moCtrl"+ moConex.getNomTablaComplejo(i,JUtiles.mcnCamposTablaRelacionada) +" = null;");lsText.append(JUtiles.msRetornoCarro);
//                    }
//                }
//            }
//            lsText.append(JUtiles.msRetornoCarro);
//        }
        
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * Crea una instancia de la clase intermedia para la tabla " + lsTablaClase + " incluyendole el servidor de datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public "+moUtiles.getNombreTablaExtends(lsTablaClase) + "(IServerServidorDatos poServidorDatos) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super(poServidorDatos);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList.getFields().setCaptions(masCaption);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList.getFields().get(0).setActualizarValorSiNulo(JFieldDef.mclActualizarUltMasUno);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static boolean getPasarACache(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        if(lbEsCamposMinimos){
            lsText.append("    public boolean getPasarCache(){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        return getPasarACache();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    } ");lsText.append(JUtiles.msRetornoCarro);
            lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        super.refrescar(pbPasarACache, pbLimpiarCache);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    public void addFilaPorClave(IFilaDatos poFila) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        switch(poFila.getTipoModif()){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            case JListDatos.mclBorrar:");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                moList.borrar(false);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                break;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            case JListDatos.mclEditar:");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                moList.getFields().cargar(poFila);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                moList.update(false);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                break;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            case JListDatos.mclNuevo:");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                moList.addNew();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                moList.getFields().cargar(poFila);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                moList.update(false);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                break;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            default:");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                throw new Exception(\"Tipo modificacion incorrecto\");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("    public static "+moUtiles.getNombreTablaExtends(lsTablaClase) + " getTabla(");
        String lsCamposPrincipales="";
        String lsCamposPrincipalesFila="";
        int[] lalCamposP = loCampos.malCamposPrincipales();
        if(lalCamposP!=null){
            for(int i = 0; i < lalCamposP.length; i++ ){
                String lsCampo = moUtiles.msSustituirRaros(loCampos.get(lalCamposP[i]).getNombre());
                if(i == (lalCamposP.length-1)){
                    lsCamposPrincipales += "ps" + lsCampo;
                    lsText.append("final String ps" + lsCampo + "");
                    lsCamposPrincipalesFila += "poFila.msCampo(lPosi" + lsCampo + "),";
                }else{
                    lsCamposPrincipales += "ps" + lsCampo + ",";
                    lsText.append("final String ps" + lsCampo + ",");
                    lsCamposPrincipalesFila += "poFila.msCampo(lPosi" + lsCampo + "),";
                }
            }
        }

        lsText.append(",final IServerServidorDatos poServer) throws Exception {");
        lsText.append(JUtiles.msRetornoCarro); 
        lsText.append("        "+moUtiles.getNombreTablaExtends(lsTablaClase) + " loTabla = new "+moUtiles.getNombreTablaExtends(lsTablaClase) + "(poServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(getPasarACache()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loTabla.recuperarTodosNormalCache();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loTabla.moList.getFiltro().addCondicion(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    JListDatosFiltroConj.mclAND, ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    JListDatos.mclTIgual, ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    malCamposPrincipales, ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    new String[]{"+lsCamposPrincipales+"});");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loTabla.moList.filtrar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loTabla.recuperarFiltradosNormal(new JListDatosFiltroElem(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                JListDatos.mclTIgual, malCamposPrincipales, new String[]{"+lsCamposPrincipales+"}) ,false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loTabla;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static "+moUtiles.getNombreTablaExtends(lsTablaClase) + " getTabla(IFilaDatos poFila, IServerServidorDatos poServer) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return getTabla(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                 " + lsCamposPrincipalesFila);lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                poServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("    public static utilesGUIx.panelesGenericos.JPanelBusquedaParametros getParamPanelBusq(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        utilesGUIx.panelesGenericos.JPanelBusquedaParametros loParam = new utilesGUIx.panelesGenericos.JPanelBusquedaParametros();");lsText.append(JUtiles.msRetornoCarro);
        if(lbEsCamposMinimos){
            lsText.append("        loParam.mlCamposPrincipales = malCamposPrincipales;");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("        loParam.mlCamposPrincipales = "+"JTFORM"+lsTablaClase+".getFieldsEstaticos().malCamposPrincipales();");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("        loParam.masTextosDescripciones = masCaption;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loParam.mbConDatos=false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loParam.mbMensajeSiNoExiste=true;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        loParam.malDescripciones = new int[]{");lsText.append(JUtiles.msRetornoCarro);
        if(loCampos.malCamposPrincipales()!=null){
            for(int i = loCampos.malCamposPrincipales().length; i < loCampos.count(); i++ ){
                if(lbEsCamposMinimos){
                    lsText.append("            lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + 
                        (i==(loCampos.count()-1)? "" : ",")
                           );lsText.append(JUtiles.msRetornoCarro);
                }else{
                    lsText.append("            JTFORM"+lsTablaClase+".lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +
                        (i==(loCampos.count()-1)? "" : ",")
                           );lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        lsText.append("            };" + JUtiles.msRetornoCarro);
        lsText.append("        loParam.masTextosDescripciones = new String[]{");lsText.append(JUtiles.msRetornoCarro);
        if(loCampos.malCamposPrincipales()!=null){
            for(int i = loCampos.malCamposPrincipales().length; i < loCampos.count(); i++ ){
                if(lbEsCamposMinimos){
                    lsText.append("             "+
                            "masCaption[lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +"]"
                       );
                    if(i!=(loCampos.count()-1)){
                        lsText.append(",");
                    }
                    lsText.append(JUtiles.msRetornoCarro);
                }else{
                    lsText.append("             "+
                            "JTFORM"+lsTablaClase+".getFieldEstatico("+
                            "JTFORM"+lsTablaClase+".lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +
                            (i==(loCampos.count()-1)? ").getCaption()" : ").getCaption(),")
                       );lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        lsText.append("            };" + JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        if(lbEsCamposMinimos){
            lsText.append("        "+moUtiles.getNombreTablaExtends(lsTablaClase)+" loObj = new "+moUtiles.getNombreTablaExtends(lsTablaClase)+"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        "+moUtiles.getNombreTablaExtends(lsTablaClase)+" loObjC = new "+moUtiles.getNombreTablaExtends(lsTablaClase)+"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        JT2GENERICO loControlador = new JT2GENERICO(");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                poMostrar,");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                loObj,");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                loObjC,");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                loObj.getPasarACache()");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                );");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        loParam.moControlador = loControlador;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        loObj = new "+moUtiles.getNombreTablaExtends(lsTablaClase)+"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        loParam.moTabla = loObj;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            ");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("        loParam.moControlador = new JT2"+lsTablaClase+"(poServer, poMostrar);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        JTFORM"+lsTablaClase+" loConsulta = new JTFORM"+lsTablaClase+"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        loConsulta.crearSelectSimple();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        loParam.moTabla = loConsulta;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("        return loParam;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("   public void valoresDefecto() throws Exception {   ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("   }");lsText.append(JUtiles.msRetornoCarro);
   
        lsText.append("    protected void comprobarClaveCargar(boolean pbEsMismaclave) throws Exception{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(!pbEsMismaclave){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moListaRelaciones = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moListaRelaciones = new HashMap();");lsText.append(JUtiles.msRetornoCarro);
//        //Controladores para las pestanas
//        if(mbRelacionesExport) {
//            for(int i=0;i<loTabla.getRelaciones().count();i++) {
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
//                    JTableDef loTablaRelac = moConex.getTablaBD(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
//                    if(loTablaRelac.getCampos().size()>=moProyecto.getOpciones().getCamposMinimosTodosModulos()){
//                        lsText.append("                moCtrl"+ moConex.getNomTablaComplejo(i,JUtiles.mcnCamposTablaRelacionada) +" = null;");lsText.append(JUtiles.msRetornoCarro);
//                    }
//
//                }
//            }
//            lsText.append(JUtiles.msRetornoCarro);
//        }
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
    
        lsText.append("    protected void cargarControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IPanelControlador loAux =");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        JDatosGeneralesP.getDatosGenerales().getControlador(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            moList.moServidor,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            poMostrar,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            psTabla,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            moList.msTabla,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            moList.getFields().moFilaDatos()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            );");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moListaRelaciones.put(psTabla, loAux);");lsText.append(JUtiles.msRetornoCarro);
//        //Controladores para las pestanas
//        if(mbRelacionesExport) {
//            for(int i=0;i<loTabla.getRelaciones().count();i++) {
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
//                    JTableDef loTablaRelac = moConex.getTablaBD(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
//                    if(loTablaRelac.getCampos().size()>=moProyecto.getOpciones().getCamposMinimosTodosModulos()){
//
//
//                        String lsControlador = "moCtrl"+ moConex.getNomTablaComplejo(i,JUtiles.mcnCamposTablaRelacionada);
//                        String lsTabla  = moConex.getNomTablaSimple(i);
//                        String lsControladorClase  = "JT2"+lsTabla;
//
//
//                        lsText.append("            if(psTabla.equalsIgnoreCase(JTEE" + lsTabla + ".msCTabla) && ");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("               ("+lsControlador+"==null)   ");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("              ){");lsText.append(JUtiles.msRetornoCarro);
//
//
//                        lsText.append("                "+lsControlador+" = new "+lsControladorClase +"(");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("                            moList.moServidor, ");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("                            poMostrar, ");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("                            moList.msTabla, ");lsText.append(JUtiles.msRetornoCarro);
//
//
//                        for(int j = 0; j < loCampos.count(); j++ ){
//                            if (loCampos.get(j).getPrincipalSN()) {
//                                lsText.append("                            get" + moUtiles.msSustituirRaros(loCampos.get(j).getNombre()) + "().getString() , ");lsText.append(JUtiles.msRetornoCarro);
//                            }
//                        }
//                        //borramos la coma del final + retornmo carro ,mas espacio
//                        lsText.delete(lsText.length()-5, lsText.length()-1);
//
//                        lsText.append("                            );");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
//                    }
//                }
//            }
//            lsText.append(JUtiles.msRetornoCarro);
//        }
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        
        
        lsText.append("    protected void cargar(String psTabla, IMostrarPantalla poMostrar) throws Exception{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            comprobarClaveCargar(isMismaClave());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(!isMismaClave()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                msCodigoRelacionado = getClave();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            ");lsText.append(JUtiles.msRetornoCarro);
        
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionImport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                String lsNomCampo = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getCampoPropio(0));
                lsText.append("            if(("+moUtiles.getNombreTablaExtends(lsNomTabla)+".msCTabla+get"+lsNomCampo+"Nombre()).equalsIgnoreCase(psTabla)){");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                "+moUtiles.getNombreTablaExtends(lsNomTabla)+" loTabla = ("+moUtiles.getNombreTablaExtends(lsNomTabla)+")moListaRelaciones.get(psTabla);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                if(loTabla==null){");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    loTabla = "+moUtiles.getNombreTablaExtends(lsNomTabla)+".getTabla(");
                for(int l = 0 ; l < loTabla.getRelaciones().getRelacion(i).getCamposRelacionCount(); l++ ){
                    lsNomCampo = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getCampoPropio(l));
                    lsText.append("get"+lsNomCampo + "().getString(), ");
                }
                lsText.append(" moList.moServidor);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    moListaRelaciones.put(psTabla, loTabla);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                }");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            } else ");lsText.append(JUtiles.msRetornoCarro);
                lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("            cargarControlador(psTabla, poMostrar);");lsText.append(JUtiles.msRetornoCarro);
            
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public IPanelControlador getControlador(String psTabla, IMostrarPantalla poMostrar) throws Exception{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        cargar(psTabla, poMostrar);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return (IPanelControlador) moListaRelaciones.get(psTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionImport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                String lsNomCampo = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getCampoPropio(0));
                lsText.append("    public "+moUtiles.getNombreTablaExtends(lsNomTabla)+" get"+lsNomTabla+lsNomCampo+"() throws Exception{");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        cargar("+moUtiles.getNombreTablaExtends(lsNomTabla)+".msCTabla+ get" + lsNomCampo  + "Nombre(), null);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        return ("+moUtiles.getNombreTablaExtends(lsNomTabla)+") moListaRelaciones.get("+moUtiles.getNombreTablaExtends(lsNomTabla)+".msCTabla + get" + lsNomCampo  + "Nombre());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("    }        ");lsText.append(JUtiles.msRetornoCarro);
                lsText.append(JUtiles.msRetornoCarro);
            }
        }
        

//        if(mbRelacionesExport) {
//            for(int i=0;i<loTabla.getRelaciones().count();i++) {
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
//                    JTableDef loTablaRelac = moConex.getTablaBD(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
//                    if(loTablaRelac.getCampos().size()>=moProyecto.getOpciones().getCamposMinimosTodosModulos()){
//                        String lsNombreCompuesto = moConex.getNomTablaComplejo(i,JUtiles.mcnCamposTablaRelacionada);
//                        String lsTabla  = moConex.getNomTablaSimple(i);
//                        lsText.append("    public JT2"+lsTabla+" getCtrl"+lsNombreCompuesto+"(IMostrarPantalla poMostrar) throws Exception{");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("        cargar(JTEE"+lsTabla+".msCTabla, poMostrar);");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("        return moCtrl"+lsNombreCompuesto+";");lsText.append(JUtiles.msRetornoCarro);
//                        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
//                    }
//                }
//            }
//            lsText.append(JUtiles.msRetornoCarro);
//        }
//
        lsText.append("    public IResultado guardar() throws Exception{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //se comprueba antes de guardar la clave pq despues de ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //guardar puede cambiar (por ejem. si estaba en modo nuevo )");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        boolean lbIsMismaClave =  isMismaClave();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //comprobamos las tablas relacionadas");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        comprobarClaveCargar(lbIsMismaClave);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JResultado loResult = new JResultado(\"\", true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loResult.addResultado(moList.update(true));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loResult.getBien() && ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("           lbIsMismaClave) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//            JTEESUBFAMILIAS loSub = JTEESUBFAMILIAS.getTabla(getCODIGOFAMILIA().getString(),moList.moServidor);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//            if(loSub.moList.moveFirst()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                do{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                    loSub.getUSALOTESSN().setValue(getUSALOTESSN().getBoolean()); ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                    loSub.moList.update(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                }while(loSub.moList.moveNext());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                JActualizarConj loAct = new JActualizarConj(\"\",\"\",\"\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                loAct.crearUpdateAPartirList(loSub.moList);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                loResult.addResultado(loSub.moList.moServidor.actualizarConj(loAct));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("        //si estaba en modo nuevo, si todo bien se queda en modo Nada/editar, por lo q hay");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //q actualizar el codigo relacionado");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loResult.getBien()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(lbIsMismaClave){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                msCodigoRelacionado = getClave();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }        ");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        return loResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public IResultado borrar() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IResultado loResult = moList.borrar(true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        if(loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
           lsText.append(JGeneradorJ2Table.getProyectoTabla(loTabla, moProyecto));
        }        
        
        
        lsText.append("}");
        
        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return moUtiles.getPaqueteExtend();
    }

    public String getNombre() {
        return moUtiles.getNombreTablaExtends(moConex.getTablaActual()) + ".java";
    }

    public boolean isGeneral() {
        return false;
    }
    public String getNombreModulo() {
        return "JTEETable";
    }
    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }
}
