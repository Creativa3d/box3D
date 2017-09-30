/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import ListDatos.JUtilTabla;
import ListDatos.estructuraBD.JTableDef;
import generadorClases.*;
import utiles.*;

public class JGeneradorJPlugInPrincipalFX implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private JProyecto moProyecto;
    public JGeneradorJPlugInPrincipalFX(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * JPlugInPrincipal.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************

        //IMPORTACION***************************************
        lsText.append("package "+moConex.getDirPadre()+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.IFilaDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".tablasControladoras.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+"."+moUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".*;        ");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("import java.io.IOException;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.event.ActionEvent;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.event.EventHandler;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.fxml.FXML;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.fxml.FXMLLoader;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.Node;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.Menu;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.MenuBar;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.MenuItem;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.ToolBar;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.layout.BorderPane;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.formsGenericos.edicion.JT2GENERICO;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.plugin.JPlugInUtilidadesFX;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.IPanelControlador;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.IPlugIn;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.IPlugInConsulta;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.IPlugInContexto;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.IPlugInFrame;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.io.IOException;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.JDepuracion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.layout.Pane;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JPlugInPrincipalFX  extends BorderPane  implements IPlugIn {");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @FXML private Menu jMenuMantenimiento;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @FXML private ToolBar jToolBar1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @FXML private MenuBar jMenuBar1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @FXML private Pane paneCentro;");lsText.append(JUtiles.msRetornoCarro);
        //Tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            JTableDef loTabla = moConex.getTablasBD().get(i);
            lsText.append("    @FXML private MenuItem jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+";");lsText.append(JUtiles.msRetornoCarro);
        }

        
        lsText.append("    public void procesarInicial(IPlugInContexto poContexto) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        FXMLLoader loader = new FXMLLoader();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loader.setRoot(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loader.setLocation(this.getClass().getResource(\"JPlugInPrincipalFX.fxml\"));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loader.setController(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            final Node root = (Node)loader.load();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (IOException ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), ex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new IllegalStateException(ex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }                ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        establecerNombresMenus();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        addActionListeners();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPlugInUtilidadesFX.addBotones(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                poContexto.getFormPrincipal(),");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jToolBar1);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPlugInUtilidadesFX.addBotones(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                poContexto.getFormPrincipal(),");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                paneCentro);        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPlugInUtilidadesFX.addMenusFrame(poContexto.getFormPrincipal(), jMenuBar1);");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("                try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    JGUIxConfigGlobalModelo.getInstancia().setDatosGeneralesAvisos(JDatosGeneralesP.getDatosGenerales().getDatosCorreos());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    JGUIxConfigGlobalModelo.getInstancia().setDatosGeneralesCalendario(JDatosGeneralesP.getDatosGenerales().getTareasAvisos1());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                } catch (Exception ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                }");lsText.append(JUtiles.msRetornoCarro);
                
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void establecerNombresMenus(){");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) { //Para cada tabla
            lsText.append("        jMenuM"+ moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+".setText(JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JT"+ moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+".msCTabla) );");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void procesarFinal(IPlugInContexto poContexto) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append(" private void addActionListeners(){");lsText.append(JUtiles.msRetornoCarro);
        //aNadimos los menus de las tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) { //Para cada tabla 
            JTableDef loTabla = moConex.getTablasBD().get(i);
            lsText.append("        jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+".setText(\""+moUtiles.msSustituirRaros(loTabla.getNombre())+"\");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+".setOnAction(new EventHandler<ActionEvent>() {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            public void handle(ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+"ActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        //Eventos de los distintos mantenimientos
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            JTableDef loTabla = moConex.getTablasBD().get(i);
            String lsNombreBueno = moUtiles.msSustituirRaros(loTabla.getNombre());
            lsText.append("    private void jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+"ActionPerformed(ActionEvent t) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);

            if(loTabla.getCampos().size()<=moProyecto.getOpciones().getCamposMinimosTodosModulos()){
                lsText.append("            "+moUtiles.getNombreTablaExtends(lsNombreBueno)+" lo"+lsNombreBueno+" = new "+moUtiles.getNombreTablaExtends(lsNombreBueno)+"(JDatosGeneralesP.getDatosGenerales().getServer());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            "+moUtiles.getNombreTablaExtends(lsNombreBueno)+" lo"+lsNombreBueno+"C = new "+moUtiles.getNombreTablaExtends(lsNombreBueno)+"(JDatosGeneralesP.getDatosGenerales().getServer());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            JT2GENERICO loGenerico = new JT2GENERICO(");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    JDatosGeneralesP.getDatosGenerales(),");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    lo"+lsNombreBueno+",");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    lo"+lsNombreBueno+"C,");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    lo"+lsNombreBueno+"C.getPasarACache()");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("                    );");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            loGenerico.mostrarFormPrinci();");lsText.append(JUtiles.msRetornoCarro);
            }else{
                lsText.append("            JT2"+moUtiles.msSustituirRaros(loTabla.getNombre())+" lo"+moUtiles.msSustituirRaros(loTabla.getNombre())+" = new JT2"+moUtiles.msSustituirRaros(loTabla.getNombre())+"(JDatosGeneralesP.getDatosGenerales().getServer(), JDatosGeneralesP.getDatosGenerales());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("            lo"+moUtiles.msSustituirRaros(loTabla.getNombre())+".mostrarFormPrinci();");lsText.append(JUtiles.msRetornoCarro);
            }
            lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            JDatosGeneralesP.getDatosGenerales().getMostrarPantalla().mensajeErrorYLog(this, e, null);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
        
        

        return lsText.toString();
    }
    
    //invertir una cadena de texto
    private String invertir(String cad) {
        int i;
        String sol = "";
        
        for(i=0;i<=cad.length()-1;i++) {
            sol += cad.substring((cad.length()-1)-i,(cad.length())-i);
        }
        
        return sol;
    }    
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JPlugInPrincipalFX.java";
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
