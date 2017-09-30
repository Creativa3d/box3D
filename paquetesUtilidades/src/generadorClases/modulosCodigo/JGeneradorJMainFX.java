/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;

public class JGeneradorJMainFX implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;

    public JGeneradorJMainFX(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto=poProyec;
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * Login.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("import utilesFX.aplicacion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.msgbox.JMsgBox;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.config.JDevolverTextos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.JGUIxConfigGlobal;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.util.ResourceBundle;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.application.Application;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.stage.Stage;        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.JFXConfigGlobal;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.IUsuario;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JMainFX  extends Application{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of JMain */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JMainFX() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static JParametrosAplicacion crearParametrosAplicacion(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JParametrosAplicacion loParam =");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("           new JParametrosAplicacion(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                \""+moConex.getDirPadre()+"\",");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                new IUsuario() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    private JTUSUARIOS moUsu;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    public String getCodUsuario() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        return moUsu.getCODIGOUSUARIO().getString();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    public String getNombre() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        return moUsu.getLOGIN().getString();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    public String getPassWord() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        return moUsu.getPASSWORD().getString();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    public int getPermisos() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        return moUsu.getPERMISO().getInteger();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    public boolean recuperarUsuario(IServerServidorDatos poServer, String psUsuario) throws Throwable {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        moUsu = new JTUSUARIOS(poServer);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        moUsu.recuperarFiltradosNormal(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                new JListDatosFiltroElem(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                JListDatos.mclTIgual, moUsu.lPosiLOGIN, psUsuario),");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                false");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                );");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                        return moUsu.moList.moveFirst();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    public void aplicarFiltrosPorUsuario(IServerServidorDatos poServer, String psUsuario, String psCodUsuario) throws Throwable {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                }, ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                new String[]{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    \""+moConex.getDirPadre()+".JPlugInPrincipalFX\",");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    \"impresionJasper.plugin.JPlugInListadosJasper\",");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    \"utilesGUIx.cargaMasiva.JPlugInCargaMasiva\" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                },");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                new JPlugInSeguridadRW(),");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                new "+moConex.getDirPadre()+"."+moUtiles.getPaqueteExtend()+".JActualizarEstruc());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loParam.setImagenLogin(\"/images/logoDef.jpg\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loParam;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static void main(String[] args) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        launch(args);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * @param args the command line arguments");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void start(Stage primaryStage)  throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        System.setProperty(\"org.apache.commons.logging.LogFactory\", \"utiles.JDepuracionFACTORY\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JParametrosAplicacion loParam = crearParametrosAplicacion();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JDatosGeneralesP.getDatosGenerales().inicializar(loParam);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JDatosGeneralesP.getDatosGenerales().getGestionProyecto().getListaProyectos().add(new JGestionProyecto());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JFXConfigGlobal.getInstancia().setToolTipTextLabels(new JDevolverTextos(ResourceBundle.getBundle(\"ToolTipTextTablas\"+loParam.getNombreProyecto())));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JFXConfigGlobal.getInstancia().setAyudaURLLabels(new JDevolverTextos(ResourceBundle.getBundle(\"AyudaURLTablas\"+loParam.getNombreProyecto())));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JDatosGeneralesP.getDatosGenerales().mostrarLogin();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (Exception ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JMsgBox.mensajeErrorYLog(null, ex, \"JMain\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }

    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JMainFX.java";
    }

    public boolean isGeneral() {
        return true;
    }

    public String getNombreModulo() {
        return getNombre();
    }
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        loParam.mbGenerar=moProyecto.getOpciones().isEdicionFX();
        return loParam;
    }
}
