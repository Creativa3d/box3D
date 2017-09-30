/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JRelacionesDef;
import ListDatos.estructuraBD.JTableDef;
import generadorClases.*;
import utiles.*;

public class JGeneradorPanelFormFX implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private boolean mbRelacionesExport; //Si tiene este tipo de relaciones, hay que generar pestanas
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorPanelFormFX(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        
        JTableDef loTabla = moConex.getTablaBD(moConex.getTablaActual());
        if(loTabla.getRelaciones().count() > 0) mbRelacionesExport = true;
        
        lsText.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import java.lang.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import java.util.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import javafx.scene.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import javafx.scene.control.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import javafx.scene.layout.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import utilesFX.panelesGenericos.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import utilesFX.formsGenericos.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import org.controlsfx.control.textfield.CustomTextField?>");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?scenebuilder-classpath-element ../../../../_NetBeansProyect/paquetes/paquetes/dist/paquetes.jar?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?scenebuilder-classpath-element ../../../../_NetBeansProyect/paquetes/paquetesGUIFX/dist/paquetesGUIFX.jar?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");
        lsText.append("<fx:root type=\"javafx.scene.layout.GridPane\" id=\"AnchorPane\" hgap=\"4.0\" prefHeight=\"400.0\" prefWidth=\"600.0\" vgap=\"4.0\" xmlns:fx=\"http://javafx.com/fxml/1\" xmlns=\"http://javafx.com/javafx/2.2\">");
        lsText.append("  <children>");lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("        <TabPane fx:id=\"jTabbedPane1\" prefHeight=\"200.0\" prefWidth=\"200.0\" tabClosingPolicy=\"UNAVAILABLE\" GridPane.columnIndex=\"0\" GridPane.rowIndex=\"0\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            <tabs>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                <Tab text=\"General\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    <content>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        <ScrollPane>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        <content>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        <GridPane id=\"AnchorPane\" hgap=\"4.0\" prefHeight=\"400.0\" prefWidth=\"600.0\" vgap=\"4.0\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        <children>              ");lsText.append(JUtiles.msRetornoCarro);
        }
        
        //Empieza la introduccion de controles **********************************************************************************
        //JTableDef loTabla = moConex.getTablaBD(moConex.getTablaActual());
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual());;
        //Introduce los paneles de busqueda en una lista inicial
        JListaElementos loPaneles = new JListaElementos();
        for(int i=0;i<loTabla.getRelaciones().count();i++) {
            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
                loPaneles.add(loRelacion.getCampoPropio(0));
            }
        }
        
        //Introduce el resto de controles 
        for(int i = 0; i < loCampos.count(); i++ ){
            if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                switch(loCampos.get(i).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                        lsText.append(addCheck(moUtiles.msSustituirRaros(loCampos.get(i).getNombre()),i,1));
                        break;
                    default:
                        //lsText.append("        txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".setTipoBD(mo"+ loTabla.getNombre()+".get" + loCampos.get(i).getNombre() + "().getTipo());");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append(addLabel(moUtiles.msSustituirRaros(loCampos.get(i).getNombre()),i,0));
                        lsText.append(addText(moUtiles.msSustituirRaros(loCampos.get(i).getNombre()),i,1,loCampos));
                        break;
                }
            }else {
                lsText.append(addPanelBusqueda(moUtiles.msSustituirRaros(loCampos.get(i).getNombre()),i,0));
            }
        }
        lsText.append("  </children>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <columnConstraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <ColumnConstraints hgrow=\"NEVER\" minWidth=\"10.0\" />");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <ColumnConstraints hgrow=\"SOMETIMES\" minWidth=\"10.0\" />");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </columnConstraints>");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("  <rowConstraints>");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("    <RowConstraints minHeight=\"10.0\" vgrow=\"SOMETIMES\" />");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    <RowConstraints minHeight=\"10.0\" vgrow=\"ALWAYS\" />");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </rowConstraints>");lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("                        </GridPane>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        </content>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        </ScrollPane>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    </content>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                </Tab>            ");lsText.append(JUtiles.msRetornoCarro);
            
            //Para cada relacion de exportacion
            if(mbRelacionesExport) {
                for(int i=0;i<loTabla.getRelaciones().count();i++) {
                    JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                    if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                        String lsNomTabla  = moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado);
                        lsText.append("                <Tab text=\""+lsNomTabla+"\">");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                    <content>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                        <BorderPane minHeight=\"0.0\" minWidth=\"0.0\" prefHeight=\"180.0\" prefWidth=\"200.0\">");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                            <center>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                                <JPanelGenerico  fx:id=\"jPanelGenerico"+lsNomTabla+"\"  maxHeight=\"1.7976931348623157E308\" maxWidth=\"1.7976931348623157E308\" BorderPane.alignment=\"CENTER\" />");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                            </center>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                        </BorderPane>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                    </content>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                </Tab>");lsText.append(JUtiles.msRetornoCarro);
                    }
                }
            }
            
            lsText.append("            </tabs>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        </TabPane>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    </children>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    <columnConstraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <ColumnConstraints hgrow=\"ALWAYS\" minWidth=\"10.0\" />");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    </columnConstraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    <rowConstraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <RowConstraints minHeight=\"10.0\" vgrow=\"ALWAYS\" />");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    </rowConstraints>");lsText.append(JUtiles.msRetornoCarro);
        }
        
        
        lsText.append("</fx:root>");        

        return lsText.toString();        
    }
    
    //Codigo de los distintos controles *************************************************************************
    
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
    
    //Check
    private String addCheck(String psNombre,int row,int col) {
        StringBuffer lsText = new StringBuffer(100);
        lsText.append("    <CheckBox fx:id=\"jCheck"+psNombre+"\" maxWidth=\"1.7976931348623157E308\" mnemonicParsing=\"false\" text=\""+psNombre+"\" GridPane.columnIndex=\""+String.valueOf(col)+"\" GridPane.rowIndex=\""+String.valueOf(row)+"\"  GridPane.columnSpan=\"2147483647\"  />");lsText.append(JUtiles.msRetornoCarro);
        return lsText.toString();
    }

    //Panel de busqueda
    private String addPanelBusqueda(String psNombre,int row,int col) {
        StringBuffer lsText = new StringBuffer(100);
        lsText.append("    <JPanelBusqueda fx:id=\"jPanel"+psNombre+"\" maxWidth=\"1.7976931348623157E308\" GridPane.columnIndex=\""+String.valueOf(col)+"\" GridPane.rowIndex=\""+String.valueOf(row)+"\" GridPane.columnSpan=\"2147483647\" />");lsText.append(JUtiles.msRetornoCarro);
        return lsText.toString();
    }

    //label
    private String addLabel(String psTexto,int row,int col) {
        StringBuffer lsText = new StringBuffer(100);
        lsText.append("    <Label fx:id=\"lbl"+psTexto+"\" maxWidth=\"1.7976931348623157E308\" mnemonicParsing=\"false\" text=\""+psTexto+"\" GridPane.columnIndex=\""+String.valueOf(col)+"\" GridPane.rowIndex=\""+String.valueOf(row)+"\" />");lsText.append(JUtiles.msRetornoCarro);
        return lsText.toString();        
    }
    
    //Text
    private String addText(String psNombre,int row,int col,JFieldDefs poCampos) {
        StringBuffer lsText = new StringBuffer(100);
        
        if(poCampos.get(row).getTamano() > 99999) { //En este caso es memo
            lsText.append("    <TextArea fx:id=\"txt"+psNombre+"\" maxWidth=\"1.7976931348623157E308\" GridPane.columnIndex=\""+String.valueOf(col)+"\" GridPane.rowIndex=\""+String.valueOf(row)+"\" />");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("    <CustomTextField fx:id=\"txt"+psNombre+"\" maxWidth=\"1.7976931348623157E308\" GridPane.columnIndex=\""+String.valueOf(col)+"\" GridPane.rowIndex=\""+String.valueOf(row)+"\" />");lsText.append(JUtiles.msRetornoCarro);
        }
        
        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return "forms";
    }

    public String getNombre() {
        return "JPanel"+ moUtiles.msSustituirRaros(moConex.getTablaActual())+".fxml";
    }

    public boolean isGeneral() {
        return false;
    }
    public String getNombreModulo() {
        return "JPanel.fxml";
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
