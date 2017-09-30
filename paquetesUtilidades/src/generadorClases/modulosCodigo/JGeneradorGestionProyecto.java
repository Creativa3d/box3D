/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JTableDef;
import generadorClases.*;
import utiles.*;

public class JGeneradorGestionProyecto implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorGestionProyecto(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JGestionProyecto.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************

        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.io.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.awt.Component;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.awt.event.ActionEvent;        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.busqueda.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.panelesGenericos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".consultas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".tablasControladoras.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".forms.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + "."+moUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("public class JGestionProyecto implements IGestionProyecto {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of JGestionProyecto */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JGestionProyecto() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPanelBusquedaParametros loBusq = null;");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0 ; i <  moConex.getTablasBD().getListaTablas().size(); i++){
            JTableDef loTabla = (JTableDef) moConex.getTablasBD().getListaTablas().get(i);
            if(i==0){
                lsText.append("        if (psTabla.equals("+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        } else if (psTabla.equals("+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }
            lsText.append("            loBusq = "+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".getParamPanelBusq(poServer, poMostrar);");lsText.append(JUtiles.msRetornoCarro);
        }

        lsText.append("        } else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(\"Tabla no encontrada \" + psTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loBusq;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);


        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void mostrarEdicion(final IServerServidorDatos poServer, final IMostrarPantalla poMostrar, final String psTabla, final IFilaDatos poFila) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        final IPanelControlador loPanelControlador =  new JT2GENERALBASE2() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public JPanelGeneralParametros getParametros(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moParametros.setMostrarPantalla(poMostrar);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                return moParametros;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void datosactualizados(final IFilaDatos poFila) throws Exception {}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public IConsulta getConsulta() {return null;}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void anadir() throws Exception {}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void borrar(int plIndex) throws Exception {}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void editar(int plIndex) throws Exception {}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void mostrarFormPrinci() throws Exception {}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(ActionEvent e, int[] plIndex) throws Exception {}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        };");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IFormEdicion loEdicion = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0 ; i <  moConex.getTablasBD().getListaTablas().size(); i++){
            JTableDef loTabla = (JTableDef) moConex.getTablasBD().getListaTablas().get(i);
            if(i==0){
                lsText.append("        if (psTabla.equals("+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        } else if (psTabla.equals("+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }
            lsText.append("            "+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +" loObj = "+moUtiles.getNombreTablaExtends(loTabla.getNombre()) +".getTabla(poFila, poServer);");lsText.append(JUtiles.msRetornoCarro);
            if(loTabla.getCampos().size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
                lsText.append("            JPanelEDICIONGENERICO loPanel = new JPanelEDICIONGENERICO();");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            loPanel.setDatos(loObj.moList, loPanelControlador);");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("            JPanel"+moUtiles.msSustituirRaros(loTabla.getNombre()) +" loPanel = new JPanel"+moUtiles.msSustituirRaros(loTabla.getNombre()) +"();");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            loPanel.setDatos(loObj, loPanelControlador, null);");lsText.append(JUtiles.msRetornoCarro);
            }

            lsText.append("            loEdicion = loPanel;");lsText.append(JUtiles.msRetornoCarro);
        }

        lsText.append("        } else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(\"Tabla no encontrada \" + psTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        poMostrar.mostrarEdicion(loEdicion,(Component) loEdicion);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);




        lsText.append("    public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IConsulta loConsulta = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JSTabla loTabla = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        boolean lbCache = false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IFilaDatos loResult = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0 ; i <  moConex.getTablasBD().getListaTablas().size(); i++){
            JTableDef loTabla = (JTableDef) moConex.getTablasBD().getListaTablas().get(i);
            String lsTablaClase = moUtiles.msSustituirRaros(loTabla.getNombre());
            if(i==0){
                lsText.append("        if (psTabla.equals("+moUtiles.getNombreTablaExtends(lsTablaClase) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        } else if (psTabla.equals("+moUtiles.getNombreTablaExtends(lsTablaClase) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }
            lsText.append("            loTabla = new "+moUtiles.getNombreTablaExtends(lsTablaClase)+"(poServer);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            lbCache = "+moUtiles.getNombreTablaExtends(lsTablaClase)+".getPasarACache();");lsText.append(JUtiles.msRetornoCarro);
            if(loTabla.getCampos().size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
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
        }
        lsText.append("        } else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(\"Tabla no encontrada \" + psTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JBusqueda loBusq = new JBusqueda(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                loConsulta,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                loConsulta.getList().msTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loBusq.mostrarFormPrinci();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loBusq.getIndex()>=0){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loResult = loTabla.getList().moFila();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loTabla.recuperarFiltradosNormal(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    new JListDatosFiltroElem(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        JListDatos.mclTIgual,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        loTabla.getList().getFields().malCamposPrincipales(),");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        loConsulta.getList().getFields().masCamposPrincipales())");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    , lbCache);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loResult = loTabla.moList.moFila();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("    public JSTabla getTabla(final IServerServidorDatos poServer, final String psTabla) throws Exception{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JSTabla loResult = null;");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0 ; i <  moConex.getTablasBD().getListaTablas().size(); i++){
            JTableDef loTabla = (JTableDef) moConex.getTablasBD().getListaTablas().get(i);
            String lsTablaClase = moUtiles.msSustituirRaros(loTabla.getNombre());
            if(i==0){
                lsText.append("        if (psTabla.equals("+moUtiles.getNombreTablaExtends(lsTablaClase) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        } else if (psTabla.equals("+moUtiles.getNombreTablaExtends(lsTablaClase) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }

            lsText.append("            loResult = new "+moUtiles.getNombreTablaExtends(lsTablaClase) +"(poServer);");lsText.append(JUtiles.msRetornoCarro);
        }

        lsText.append("        } else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(\"Tabla no encontrada \" + psTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("    public IPanelControlador getControlador(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            IServerServidorDatos poServer, IMostrarPantalla poMostrar, ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            String psTabla, ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IPanelControlador loResult = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);


        for(int i = 0 ; i <  moConex.getTablasBD().getListaTablas().size(); i++){
            JTableDef loTabla = (JTableDef) moConex.getTablasBD().getListaTablas().get(i);
            String lsTablaClase = moUtiles.msSustituirRaros(loTabla.getNombre());
            if(i==0){
                lsText.append("        if (psTabla.equals("+moUtiles.getNombreTablaExtends(lsTablaClase) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("        } else if (psTabla.equals("+moUtiles.getNombreTablaExtends(lsTablaClase) +".msCTabla)){");lsText.append(JUtiles.msRetornoCarro);
            }

            if(loTabla.getCampos().size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
                lsText.append("            "+moUtiles.getNombreTablaExtends(lsTablaClase) +" lo"+lsTablaClase +" = new "+moUtiles.getNombreTablaExtends(lsTablaClase) +"(JDatosGeneralesP.getDatosGenerales().getServer());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            "+moUtiles.getNombreTablaExtends(lsTablaClase) +" lo"+lsTablaClase +"C = new "+moUtiles.getNombreTablaExtends(lsTablaClase) +"(JDatosGeneralesP.getDatosGenerales().getServer());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            loResult = new JT2GENERICO(");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    poMostrar,");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    lo"+lsTablaClase +",");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    lo"+lsTablaClase +"C,");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    lo"+lsTablaClase +"C.getPasarACache()");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    );");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("            loResult = new JT2"+lsTablaClase +"(poServer, poMostrar,psTablaRelac, poDatosRelac);");lsText.append(JUtiles.msRetornoCarro);
            }
        }

        lsText.append("        } else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(\"Tabla no encontrada \" + psTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);


        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
        
        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JGestionProyectoAntig.java";
    }

    public boolean isGeneral() {
        return true;
    }
    public String getNombreModulo() {
        return getNombre();
    }
    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }

}
