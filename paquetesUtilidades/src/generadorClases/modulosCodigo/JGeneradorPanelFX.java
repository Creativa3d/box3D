/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.*;
import generadorClases.*;
import utiles.*;
import ListDatos.*;

public class JGeneradorPanelFX implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private boolean mbRelacionesExport; //Si tiene este tipo de relaciones, hay que generar pestanas
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorPanelFX(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        JListaElementos loPaneles;
        JTableDef loTabla;
        JFieldDefs loCampos;
        
        loTabla = moConex.getTablaBD(moConex.getTablaActual());
        loCampos = moConex.getCamposBD(moConex.getTablaActual());
        
        //Introduce los paneles de busqueda en una lista inicial
        loPaneles = new JListaElementos();
        for(int i=0;i<loTabla.getRelaciones().count();i++) {
            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
                loPaneles.add(loRelacion.getCampoPropio(0));
            }
        }
        String lsTablaSinRaros = moUtiles.msSustituirRaros(loTabla.getNombre());
        
        //Compruebo el numero de relaciones de importacion para saber si tiene que llevar pestanas
        mbRelacionesExport = moConex.tieneRelacionesExport();

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JPanel" + lsTablaSinRaros + ".java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + "." + "forms;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.io.IOException;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.fxml.FXML;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.fxml.FXMLLoader;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.Node;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.CheckBox;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.Label;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.TextField;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.TextArea;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.scene.control.TabPane;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.beans.value.ChangeListener;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javafx.beans.value.ObservableValue;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.formsGenericos.edicion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.panelesGenericos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesFX.formsGenericos.JPanelGenerico;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.IPanelControlador;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.JDepuracion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import org.controlsfx.control.textfield.CustomTextField;");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + "."+moUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + moConex.getDirPadre() + ".JDatosGeneralesP;");lsText.append(JUtiles.msRetornoCarro);
        if(mbRelacionesExport) {
            lsText.append("import " + moConex.getDirPadre() + ".tablasControladoras.*;");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append(JUtiles.msRetornoCarro);
        //**************************************************
        
        //CUERPO DE LA CLASE*******************************************
        lsText.append("public class JPanel"+ lsTablaSinRaros+" extends JPanelGENERALBASE {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private "+moUtiles.getNombreTablaExtends(lsTablaSinRaros)+" mo"+ lsTablaSinRaros+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        for(int i = 0; i < loCampos.count(); i++ ){
            if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                switch(loCampos.get(i).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                        lsText.append("    @FXML private CheckBox jCheck"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+";");lsText.append(JUtiles.msRetornoCarro);
                        break;
                    default:
                        //labels
                        lsText.append("    @FXML private Label lbl"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
                        //textos
                        if(loCampos.get(i).getTamano() > 99999) { //En este caso es memo
                            lsText.append("    @FXML private TextArea txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
                        } else {
                            lsText.append("    @FXML private CustomTextField txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
                        }
                        break;
                }
            }else {
                //Introduce paneles de busqueda
                lsText.append("    @FXML private JPanelBusqueda jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
            }
        }        
        
                
        //Introduce el resto de controles 
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("    @FXML private TabPane jTabbedPane1;");lsText.append(JUtiles.msRetornoCarro);
        }
        
        //Declaracion de panelesGenericos
        if(mbRelacionesExport) {
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());                        
                    String lsNomControlador = lsNomTabla;
                    for(int j=0;j < loTabla.getRelaciones().getRelacion(i).getCamposRelacionCount();j++) {
                        String lsNomCampo = moUtiles.msSustituirRaros(loRelacion.getCampoRelacion(j));
                        lsNomTabla += "_" + lsNomCampo;
                    }
                    
                    lsText.append("    @FXML private JPanelGenerico jPanelGenerico" + moUtiles.msSustituirRaros(lsNomTabla) + ";");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }

        
        lsText.append("    /** Creates new form JPanel"+ lsTablaSinRaros+"*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JPanel"+ lsTablaSinRaros+"() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        FXMLLoader loader = new FXMLLoader();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loader.setRoot(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loader.setLocation(this.getClass().getResource(\"JPanel"+ lsTablaSinRaros + ".fxml\"));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loader.setController(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JFXConfigGlobal.getInstancia().inicializarFX();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            final Node root = (Node)loader.load();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (IOException ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new IllegalStateException(ex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }             ");lsText.append(JUtiles.msRetornoCarro);

        
        if(mbRelacionesExport) {
            lsText.append("        jTabbedPane1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            @Override");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                jTabbedPane1StateChanged();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        }
        
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        if(moProyecto.getOpciones().getPKInvisibles()) {
            lsText.append("    public void setPKInvisible() {");lsText.append(JUtiles.msRetornoCarro);

            
            for(int i = 0; i < loCampos.count(); i++ ){
                if (loCampos.get(i).getPrincipalSN()) {
                    if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                        switch(loCampos.get(i).getTipo()) {
                            case JListDatos.mclTipoBoolean:
                                lsText.append("        jCheck"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+".setVisible(false);");lsText.append(JUtiles.msRetornoCarro);
                                break;
                            default:
                                //labels
                                lsText.append("        lbl"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setVisible(false);");lsText.append(JUtiles.msRetornoCarro);
                                //textos
                                if(loCampos.get(i).getTamano() > 99999) { //En este caso es memo
                                    lsText.append("        txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setVisible(false);");lsText.append(JUtiles.msRetornoCarro);
                                } else {
                                    lsText.append("        txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setVisible(false);");lsText.append(JUtiles.msRetornoCarro);
                                }
                                break;
                        }
                    }else {
                        //Introduce paneles de busqueda
                        lsText.append("        jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setVisible(false);");lsText.append(JUtiles.msRetornoCarro);
                    }
                }
            }
            
            lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("    public void setDatos(final "+moUtiles.getNombreTablaExtends(lsTablaSinRaros)+" po"+ lsTablaSinRaros+", final IPanelControlador poPadre) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        mo"+ lsTablaSinRaros+" = po"+ lsTablaSinRaros+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setDatos(poPadre);");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String getTitulo() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        String lsResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(mo"+ lsTablaSinRaros+".moList.getModoTabla() == JListDatos.mclNuevo) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            lsResult= JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("+moUtiles.getNombreTablaExtends(lsTablaSinRaros)+".msCTabla) + \" [Nuevo]\" ;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        if(loTabla.getIndices().getCountIndices()>0) {
            lsText.append("            lsResult=JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("+moUtiles.getNombreTablaExtends(lsTablaSinRaros)+".msCTabla)  + ");lsText.append(JUtiles.msRetornoCarro);
        }else {
            lsText.append("            lsResult=JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("+moUtiles.getNombreTablaExtends(lsTablaSinRaros)+".msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        }
        int k = 0;
        for(int i = 0; i < loCampos.count(); i++ ){
            if (loCampos.get(i).getPrincipalSN()) {
                if(k == (loCampos.masCamposPrincipales().length - 1)){
                    lsText.append("                mo" + lsTablaSinRaros+ ".get" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "().getString();");lsText.append(JUtiles.msRetornoCarro);
                }else{
                    lsText.append("                mo" + lsTablaSinRaros+ ".get" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "().getString() + \"-\" +");lsText.append(JUtiles.msRetornoCarro);
                }
                k++;
            }
        }
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return lsResult;");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JSTabla getTabla(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return mo" + moUtiles.msSustituirRaros(moConex.getTablaActual()) +  ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void rellenarPantalla() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionImport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                String lsNomCampo = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getCampoPropio(0));
                lsText.append("        jPanel" + lsNomCampo + ".setDatos(JDatosGeneralesP.getDatosGenerales().getParamPanelBusq(mo" + moUtiles.msSustituirRaros(moConex.getTablaActual()) + ".moList.moServidor, moPadre.getParametros().getMostrarPantalla(), JT"+moUtiles.msSustituirRaros(lsNomTabla)+".msCTabla));");lsText.append(JUtiles.msRetornoCarro);
                lsText.append(JUtiles.msRetornoCarro);
            }
        }
        //
        //ponemos los caption a los controles
        //
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //ponemos los textos a los label");
        lsText.append(JUtiles.msRetornoCarro);


        
        
        for(int i = 0; i < loCampos.count(); i++ ){
            String lsNomCampo = moUtiles.msSustituirRaros(moUtiles.msSustituirRaros(loCampos.get(i).getNombre()));
                
            if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                switch(loCampos.get(i).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                        //check
                        lsText.append("        addFieldControl(new JFieldControl(jCheck" + lsNomCampo +",mo"+lsTablaSinRaros+".get"+lsNomCampo+"()));");lsText.append(JUtiles.msRetornoCarro);
                        break;
                    default:
                        //textos
                        lsText.append("        lbl" + lsNomCampo + ".setText(mo"+ lsTablaSinRaros+".get" + lsNomCampo + "().getCaption());");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        addFieldControl(new JFieldControl(txt" + lsNomCampo + ",mo"+ lsTablaSinRaros+".get" + lsNomCampo + "()));");lsText.append(JUtiles.msRetornoCarro);
                        break;
                }
            }else {
                lsText.append("        jPanel"+ lsNomCampo +".setLabel(mo"+lsTablaSinRaros+".get"+lsNomCampo+"().getCaption());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        jPanel"+ lsNomCampo +".setField(mo"+lsTablaSinRaros+".get"+lsNomCampo+"());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        addFieldControl(jPanel"+ lsNomCampo +");");lsText.append(JUtiles.msRetornoCarro);
            }
        }        

        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void habilitarSegunEdicion() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(mo"+ lsTablaSinRaros+".moList.getModoTabla() == JListDatos.mclNuevo) {");lsText.append(JUtiles.msRetornoCarro);
        
        //Introduce los paneles de busqueda en una lista inicial
        
//        loPaneles = new JListaElementos();
//        for(int i=0;i<loTabla.getRelaciones().count();i++) {
//            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
//                loPaneles.add(loRelacion.getCampoPropio(0));
//            }
//        }
        
        //Introduce el resto de controles 
        for(int i = 0; i < loCampos.count(); i++ ){
            if(loCampos.get(i).getPrincipalSN()) {
                if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                    switch(loCampos.get(i).getTipo()) {
                        case JListDatos.mclTipoBoolean:
                            lsText.append("            jCheck"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setDisable(false);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                        default:
                            lsText.append("            txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".setDisable(false);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                    }
                }else {
                    lsText.append("            jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setDisable(false);");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }


        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        
        //Introduce el resto de controles 
        for(int i = 0; i < loCampos.count(); i++ ){
            if(loCampos.get(i).getPrincipalSN()) {
                if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                    switch(loCampos.get(i).getTipo()) {
                        case JListDatos.mclTipoBoolean:
                            lsText.append("            jCheck"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setDisable(true);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                        default:
                            lsText.append("            txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".setDisable(true);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                    }
                }else {
                    lsText.append("            jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setDisable(true);");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }



        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().getPKInvisibles()) {
            lsText.append("        setPKInvisible();");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void ponerTipoTextos() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        

        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void mostrarDatos() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super.mostrarDatos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);

        //Inicializar Controladores para las pestanas
        if(mbRelacionesExport) {
            lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jTabbedPane1StateChanged();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append(JUtiles.msRetornoCarro);
        }
        
        
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void establecerDatos() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super.establecerDatos();");lsText.append(JUtiles.msRetornoCarro);
        

        lsText.append("        mo"+ lsTablaSinRaros+".validarCampos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void aceptar() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        int lModo = getModoTabla();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IResultado loResult=mo"+ lsTablaSinRaros+".guardar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loResult.getBien()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("             actualizarPadre(lModo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(loResult.getMensaje());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public Rectangulo getTanano(){");lsText.append(JUtiles.msRetornoCarro);
        String lsAlto;
        if(loTabla.getCampos().count() * 47 >= 400) {
            lsAlto = new Integer(loTabla.getCampos().count() * 47).toString();
        } else {
            lsAlto = "400";
        }
        lsText.append("        return new Rectangulo(0,0, 740, " + lsAlto + ");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        if(mbRelacionesExport) {
            lsText.append("    private void compruebaPK() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        if(");lsText.append(JUtiles.msRetornoCarro);
            
            k = 0;
            for(int i = 0; i < loCampos.count(); i++ ){
                if (loCampos.get(i).getPrincipalSN()) {
                    if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                        switch(loCampos.get(i).getTipo()) {
                            case JListDatos.mclTipoBoolean:
                                //check
                                break;
                            default:
                                //textos
                                if(k == (loCampos.masCamposPrincipales().length - 1)){
                                    lsText.append("            txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".getText().compareTo(\"\") == 0");lsText.append(JUtiles.msRetornoCarro);
                                }else{
                                    lsText.append("            txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".getText().compareTo(\"\") == 0 ||");lsText.append(JUtiles.msRetornoCarro);
                                }
                                k++;
                                break;
                        }
                    }else {
                        //JPanelesBusqueda
                        if(k == (loCampos.masCamposPrincipales().length - 1)){
                            lsText.append("            jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".getText().compareTo(\"\") == 0");lsText.append(JUtiles.msRetornoCarro);
                        }else{
                            lsText.append("            jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".getText().compareTo(\"\") == 0 ||");lsText.append(JUtiles.msRetornoCarro);
                        }
                        k++;
                    }
                }
            }        
            
            lsText.append("          ) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            jTabbedPane1.getSelectionModel().select(0);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            throw new Exception(\"Es necesario guardar datos antes de continuar\");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
            
            lsText.append("    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        super.setBloqueoControles(pbBloqueo);");lsText.append(JUtiles.msRetornoCarro);
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());                        
                    String lsNomControlador = lsNomTabla;
                    for(int j=0;j < loTabla.getRelaciones().getRelacion(i).getCamposRelacionCount();j++) {
                        String lsNomCampo = moUtiles.msSustituirRaros(loRelacion.getCampoRelacion(j));
                        lsNomTabla += "_" + lsNomCampo;
                    }                    
                    lsText.append("        setBloqueoControlesContainer(jPanelGenerico"+  moUtiles.msSustituirRaros(lsNomTabla)+",false);");lsText.append(JUtiles.msRetornoCarro);
                }
            }
            lsText.append("   }");lsText.append(JUtiles.msRetornoCarro);
            
            lsText.append("    private void jTabbedPane1StateChanged() {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            if(jTabbedPane1.getSelectionModel().getSelectedIndex()>=0){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                switch(jTabbedPane1.getSelectionModel().getSelectedIndex()){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    case 0://General");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        break;");lsText.append(JUtiles.msRetornoCarro);
            
            int cont = 1;
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    lsText.append("                    case " + cont + "://"+ moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado)+"");lsText.append(JUtiles.msRetornoCarro);
                    cont++;
                    lsText.append("                        compruebaPK();");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("                        jPanelGenerico"+ moConex.getNomTablaComplejo(i,JUtiles.mcnCamposTablaRelacionada) +".setControlador(mo"+lsTablaSinRaros + ".getControlador(JT"+ moConex.getNomTablaSimple(i)+".msCTabla, moPadre.getParametros().getMostrarPantalla()));");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("                        break;");lsText.append(JUtiles.msRetornoCarro);
                }
            }
            
            lsText.append("                }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            JDatosGeneralesP.getDatosGenerales().mensajeErrorYLog(this, e, null);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        }
        
        


        
        //***********************************************************************************************************
        
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
    
    //Comprueba si el panel esta en la lista
    private boolean comprueboPanel(String psNombre,JListaElementos poPaneles) {
        boolean lbOk;
        
        lbOk = false;
        
        for(int i=0;i<poPaneles.size();i++) {
            if(poPaneles.get(i).toString().compareTo(psNombre) == 0) {
                lbOk =true;
                break;
            }
        }
        
        return lbOk;
    }
    public String getRutaRelativa() {
        return "forms";
    }

    public String getNombre() {
        return "JPanel"+ moUtiles.msSustituirRaros(moConex.getTablaActual())+".java";
    }

    public boolean isGeneral() {
        return false;
    }
    public String getNombreModulo() {
        return "JPanelFX.java";
    }
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );
        loParam.mbGenerar=moProyecto.getOpciones().isEdicionFX();
        if(loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
            loParam.mbGenerar=false;
        }
        
        return loParam;
    }

}
