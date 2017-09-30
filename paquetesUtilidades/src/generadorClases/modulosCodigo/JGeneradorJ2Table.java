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
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JGeneradorJ2Table implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private boolean mbRelacionesImport; //Si tiene este tipo de relaciones, hay que generar pestañas
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorJ2Table(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        JTableDef loTabla = moConex.getTablaBD(moConex.getTablaActual());
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );
        String lsTablaClase = moUtiles.msSustituirRaros(loTabla.getNombre());
        
        //Compruebo el numero de relaciones de importacion
        mbRelacionesImport = moConex.tieneRelacionesImport();
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JT2" + lsTablaClase + ".java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + "." + "tablasControladoras;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.ActionEventCZ;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.boton.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.busqueda.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.cargaMasiva.ICargaMasiva;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + "."+moUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".forms.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".consultas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.IGestionProyecto;");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.panelesGenericos.JConsulta;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.IFormEdicion;");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append(JUtiles.msRetornoCarro);
        
        //**************************************************
        
        //CUERPO DE LA CLASE*******************************************
        lsText.append("public class JT2" + lsTablaClase + "  extends "
                + (moProyecto.getOpciones().isEdicionFX() ? "JT2GENERALBASEModelo " : " JT2GENERALBASE2 ")
                + "  implements ICargaMasiva {");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("    private static final String mcsImpListado = \"Listado\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private final JTFORM"+lsTablaClase +" moConsulta;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private final IServerServidorDatos moServer;");lsText.append(JUtiles.msRetornoCarro);

        //Se crea una lista de los distintos tipos de constructores que se deben implementar
        
//        if(mbRelacionesImport) {
        lsText.append("    private IFilaDatos moFilaDatosRelac;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private String msNomTabRelac = \"\";");lsText.append(JUtiles.msRetornoCarro);
//        }

        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Crea una nueva instancia de JT2" + lsTablaClase + " */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JT2" + lsTablaClase + "(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moServer = poServidorDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moConsulta = new JTFORM" + lsTablaClase + "(poServidorDatos);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moConsulta.crearSelectSimple();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        addBotones();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moParametros.setLongitudCampos(getLongitudCampos());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moParametros.setNombre("+moUtiles.getNombreTablaExtends(lsTablaClase)+".msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moParametros.setTitulo(JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("+moUtiles.getNombreTablaExtends(lsTablaClase)+".msCTabla));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        getParametros().setMostrarPantalla(poMostrarPantalla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        inicializarPlugIn(JDatosGeneralesP.getDatosGeneralesPlugIn());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//EJEMPLOS de coloreo        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//        JListDatosFiltroConj loElem = new JListDatosFiltroConj();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//        loElem.addCondicion(JListDatosFiltroConj.mclAND,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                JListDatos.mclTMayorIgual, JTEEARTICULOS.lPosiDESCRIPCION, \"*\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//        loElem.addCondicion(JListDatosFiltroConj.mclAND,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                JListDatos.mclTMenorIgual, JTEEARTICULOS.lPosiDESCRIPCION, \"*ZZZZZZZZZ\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//        loElem.inicializar(JTEEARTICULOS.msCTabla, JTEEARTICULOS.malTipos, JTEEARTICULOS.masNombres);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                loElem,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                null,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                new ColorCZ(Color.red));");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    public JT2" + lsTablaClase + "(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            IServerServidorDatos poServer,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            IMostrarPantalla poMostrar,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            String psNomTabRelac,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            IFilaDatos poDatosRelac) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        this(poServer, poMostrar);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setTablaRelacionada(psNomTabRelac, poDatosRelac);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void setTablaRelacionada(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            String psNomTabRelac,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            IFilaDatos poDatosRelac){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        msNomTabRelac = psNomTabRelac;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moFilaDatosRelac = poDatosRelac;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moConsulta.crearSelect(msNomTabRelac, moFilaDatosRelac);");lsText.append(JUtiles.msRetornoCarro);

//        lsText.append("        if(msNomTabRelac!=null && !msNomTabRelac.equals(\"\")){");lsText.append(JUtiles.msRetornoCarro);
//        if(mbRelacionesImport) {
//            for(int j=0;j<loTabla.getRelaciones().count();j++) {
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(j);
//                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importación
//                    int numCamposRel = loRelacion.getCamposRelacionCount();
//                    lsText.append("        if(msNomTabRelac.equals(JT" + moConex.getNomTablaSimple(j) + ".msCTabla)) {");lsText.append(JUtiles.msRetornoCarro);
//                    lsText.append("            moConsulta.crearSelect" + moConex.getNomTablaSimple(j) + "(");lsText.append(JUtiles.msRetornoCarro);
//                    //Añadimos los parametros
//                    for(int k=0;k<numCamposRel;k++) {
//                        lsText.append("                    moFilaDatosRelac.msCampo(JT" + moConex.getNomTablaSimple(j) + ".lPosi" + moUtiles.msSustituirRaros(loRelacion.getCampoRelacion(k)) + ")");
//                        if(k!=(numCamposRel-1)) {
//                            lsText.append(",");
//                        }
//                        lsText.append(JUtiles.msRetornoCarro);
//                    }
//                    lsText.append("            );");lsText.append(JUtiles.msRetornoCarro);
//                    lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
//                    lsText.append(JUtiles.msRetornoCarro);
//                }
//            }
//        }
//        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    public IConsulta getConsulta() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moConsulta;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void mostrarFormPrinci() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void anadir() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        "+moUtiles.getNombreTablaExtends(lsTablaClase) + " lo" + lsTablaClase + " = new "+moUtiles.getNombreTablaExtends(lsTablaClase) + "(moServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        lo" + lsTablaClase + ".moList.addNew();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        valoresDefecto(lo" + lsTablaClase + ");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPanel" + lsTablaClase + " loPanel = new JPanel" + lsTablaClase + "();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loPanel.setDatos(lo" + lsTablaClase + ", this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().isEdicionNavegador()){
            lsText.append("        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        
        
        
        lsText.append("    public void valoresDefecto(final JSTabla poTabla) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(poTabla.moList.getModoTabla() == JListDatos.mclNuevo) {");lsText.append(JUtiles.msRetornoCarro);

        if(mbRelacionesImport) {
            lsText.append("            if(msNomTabRelac!=null && !msNomTabRelac.equals(\"\")){");lsText.append(JUtiles.msRetornoCarro);
            for(int j=0;j<loTabla.getRelaciones().count();j++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(j);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importación
                    int numCamposRel = loRelacion.getCamposRelacionCount();
                    lsText.append("            if(msNomTabRelac.equals(JT" + moConex.getNomTablaSimple(j) + ".msCTabla)) {");lsText.append(JUtiles.msRetornoCarro);
                    //Añadimos un parametro por cada clave primaria de la tabla relacionada
                    for(int l=0;l<loRelacion.getCamposRelacionCount();l++) {
                        lsText.append("                poTabla.moList.getFields(JT" + lsTablaClase + ".lPosi" + moUtiles.msSustituirRaros(loRelacion.getCampoPropio(l)) + ").setValue(");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                    moFilaDatosRelac.msCampo(JT" + moConex.getNomTablaSimple(j) + ".lPosi" + moUtiles.msSustituirRaros(loRelacion.getCampoRelacion(l)) + ")");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                    );");lsText.append(JUtiles.msRetornoCarro);
                    }

                    lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append(JUtiles.msRetornoCarro);
                }
            }
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("            (("+moUtiles.getNombreTablaExtends(lsTablaClase)+")poTabla).valoresDefecto();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);        
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    public void borrar(final int plIndex) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        moConsulta.moList.setIndex(plIndex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        "+moUtiles.getNombreTablaExtends(lsTablaClase) + " lo" + lsTablaClase + " = new "+moUtiles.getNombreTablaExtends(lsTablaClase) + "(moServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        lo" + lsTablaClase + ".moList.addNew();");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            if(loCampos.get(i).getPrincipalSN()) {
                lsText.append("        lo" + lsTablaClase + ".get" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "().setValue(moConsulta.moList.getFields(moConsulta.lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ").getValue());");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("        lo" + lsTablaClase + ".moList.update(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().getBorrarEnCascada()) {
            lsText.append("        IFilaDatos loFila = lo" + lsTablaClase + ".moList.moFila();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        loFila.setTipoModif (JListDatos.mclBorrar);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        JDatosGeneralesP.getDatosGenerales().borrarTodo(lo" + lsTablaClase + ".moList.getFields(), lo" + lsTablaClase + ".msCTabla);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        datosactualizados(loFila);");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("        lo" + lsTablaClase + ".moList.moFila().setTipoModif(JListDatos.mclNada);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        IFilaDatos loFila = (IFilaDatos)lo" + lsTablaClase + ".moList.moFila().clone();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        IResultado loResult = lo" + lsTablaClase + ".borrar();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        if(!loResult.getBien()){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            throw new Exception(loResult.getMensaje());");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        loFila.setTipoModif (JListDatos.mclBorrar);");lsText.append(JUtiles.msRetornoCarro);

            lsText.append("        datosactualizados(loFila);");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void editar(final int plIndex) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moConsulta.moList.setIndex(plIndex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        "+moUtiles.getNombreTablaExtends(lsTablaClase) + " lo"+ lsTablaClase 
                + " = "+moUtiles.getNombreTablaExtends(lsTablaClase) + ".getTabla(");
        lsText.append(JUtiles.msRetornoCarro);
        if(loTabla.getIndices().getCountIndices()>0) {        
            for(int i = 0; i < loCampos.count(); i++ ){
                if(loCampos.get(i).getPrincipalSN()) {
                    lsText.append("                moConsulta.moList.getFields(moConsulta.lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ").getString(),");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        
        lsText.append("                moServer);");

        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        valoresDefecto(lo" + lsTablaClase + ");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPanel" + lsTablaClase + " loPanel = new JPanel" + lsTablaClase + "();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loPanel.setDatos(lo" + lsTablaClase + ", this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().isEdicionNavegador()){
            lsText.append("        if(moConsulta.moList.size()>1){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }            ");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public int[] getLongitudCampos() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//        int[] loInt = new int[JTFORM" + lsTablaClase + ".mclNumeroCampos];");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("//        loInt[JTFORM" + lsTablaClase + ".lPosi" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "]=80;");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("//        return loInt;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        if(e.getActionCommand().compareTo(mcsImpListado)==0){");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("            JInfGeneral loinf = new JInfGeneral();");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("            loinf.generarListado(moConsulta.moList, moPanel, getNombre());");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(plIndex.length>0){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                for(int i = 0 ; i < plIndex.length; i++){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    moConsulta.moList.setIndex(plIndex[i]);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                throw new Exception(\"No existe una fila actual\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void addBotones() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPanelGeneralBotones retValue;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        retValue = getParametros().getBotonesGenerales();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsImpListado, mcsImpListado, \"/images/Print16.gif\", this));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append(getProyectoTabla(loTabla, moProyecto));
        
        

        lsText.append("    public JListDatos getTablaBase() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return new "+moUtiles.getNombreTablaExtends(lsTablaClase)+"(moServer).getList();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        
        
        lsText.append("}");
        
        return lsText.toString();        
    }
    
    public static String getProyectoTabla(JTableDef loTabla, JProyecto poProyecto){
        StringBuffer lsText = new StringBuffer();
        JUtiles loUtiles=new JUtiles(poProyecto);
        String lsTablaClase = loUtiles.msSustituirRaros(loTabla.getNombre());
        
        lsText.append("    public static class GestionProyectoTabla implements IGestionProyecto {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return new "+loUtiles.getNombreTablaExtends(lsTablaClase) + "(poServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IPanelControlador loResult = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        if(loTabla.getCampos().size()<poProyecto.getOpciones().getCamposMinimosTodosModulos()){
            lsText.append("            "+loUtiles.getNombreTablaExtends(lsTablaClase) +" lo"+lsTablaClase +" = new "+loUtiles.getNombreTablaExtends(lsTablaClase) +"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            "+loUtiles.getNombreTablaExtends(lsTablaClase) +" lo"+lsTablaClase +"C = new "+loUtiles.getNombreTablaExtends(lsTablaClase) +"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            loResult = new JT2GENERICO(");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    poMostrar,");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    lo"+lsTablaClase +",");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    lo"+lsTablaClase +"C,");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    lo"+lsTablaClase +"C.getPasarACache()");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    );");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("            loResult = new JT2"+lsTablaClase +"(poServer, poMostrar,psTablaRelac, poDatosRelac);");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("            return loResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return "+loUtiles.getNombreTablaExtends(lsTablaClase) + ".getParamPanelBusq(poServer, poMostrar);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            "+loUtiles.getNombreTablaExtends(lsTablaClase) + " loObj = "+loUtiles.getNombreTablaExtends(lsTablaClase) + ".getTabla(poFila, poServer);");lsText.append(JUtiles.msRetornoCarro);
        if(loTabla.getCampos().size()<poProyecto.getOpciones().getCamposMinimosTodosModulos()){
            lsText.append("            JPanelEDICIONGENERICO loPanel = new JPanelEDICIONGENERICO();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            loPanel.setDatos(loObj.moList, null);");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("            JPanel" + lsTablaClase + " loPanel = new JPanel" + lsTablaClase + "();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            loPanel.setDatos(loObj, null);");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("            poMostrar.mostrarEdicion(loPanel, loPanel);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            IConsulta loConsulta = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JSTabla loTabla = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            boolean lbCache = false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loTabla = new "+loUtiles.getNombreTablaExtends(lsTablaClase) + "(poServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            lbCache = "+loUtiles.getNombreTablaExtends(lsTablaClase) + ".getPasarACache();");lsText.append(JUtiles.msRetornoCarro);
        if(loTabla.getCampos().size()<poProyecto.getOpciones().getCamposMinimosTodosModulos()){
            lsText.append("            loTabla.recuperarTodosNormal(lbCache);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            loConsulta = new JConsulta(loTabla, true);");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("            if(lbCache){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                loTabla.recuperarTodosNormal(lbCache);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                loConsulta = new JConsulta(loTabla, true);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }else{");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                JTFORM"+lsTablaClase+" loConsulta1 = new JTFORM"+lsTablaClase+"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                loConsulta1.crearSelectSimple();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                loConsulta = loConsulta1;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("            return JDatosGeneralesP.getDatosGenerales().mostrarBusqueda(loConsulta, loTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("       }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);   
        return lsText.toString();
    }
    
    private int addTipoConstructores(JRelacionesDef poRelacion,int pnMaxParam,JListaElementos poConstruc) {
        int lnMaxParam;
        
        lnMaxParam = pnMaxParam;
        //Para cada campo de cada relación
        if(poRelacion.getCamposRelacionCount() > lnMaxParam) lnMaxParam = poRelacion.getCamposRelacionCount();
        for(int i=0;i<poRelacion.getCamposRelacionCount();i++) {
            String lsNomCampo = moUtiles.msSustituirRaros(poRelacion.getCampoRelacion(i));
            if(!moUtiles.perteneceA(new Integer(poRelacion.getCamposRelacionCount()).toString(),poConstruc)) {
                poConstruc.add(new Integer(poRelacion.getCamposRelacionCount()).toString());
            }
        }
        
        return lnMaxParam;
    }
    public String getRutaRelativa() {
        return "tablasControladoras";
    }

    public String getNombre() {
        return "JT2" + moUtiles.msSustituirRaros(moConex.getTablaActual()) + ".java";
    }

    public boolean isGeneral() {
        return false;
    }
    public String getNombreModulo() {
        return "TablaControladora";
    }
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );;
        if(loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
            loParam.mbGenerar=false;
        }
        return loParam;
    }

}
