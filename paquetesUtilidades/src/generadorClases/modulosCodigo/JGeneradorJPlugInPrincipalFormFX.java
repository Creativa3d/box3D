/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;

public class JGeneradorJPlugInPrincipalFormFX implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;

    public JGeneradorJPlugInPrincipalFormFX(JProyecto poProyec) {
        moProyecto = poProyec;
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        
        lsText.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import java.lang.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import javafx.scene.control.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<?import javafx.scene.layout.*?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<fx:root id=\"AnchorPane\" prefHeight=\"400.0\" prefWidth=\"600.0\" type=\"javafx.scene.layout.BorderPane\" xmlns=\"http://javafx.com/javafx/8\" xmlns:fx=\"http://javafx.com/fxml/1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <top>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <VBox minHeight=\"-1.0\" prefHeight=\"-1.0\" prefWidth=\"600.0\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <children>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <MenuBar fx:id=\"jMenuBar1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <menus>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Menu fx:id=\"jMenuMantenimiento\" mnemonicParsing=\"false\" text=\"Mantenimiento\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <items>");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            lsText.append("                <MenuItem fx:id=\"jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"\" mnemonicParsing=\"false\" text=\""+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"\" />");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("              </items>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Menu>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </menus>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </MenuBar>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <ToolBar fx:id=\"jToolBar1\" />");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </children>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </VBox>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </top>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <center>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Pane fx:id=\"paneCentro\" prefHeight=\"200.0\" prefWidth=\"200.0\" BorderPane.alignment=\"CENTER\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Pane>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("   </center>        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("</fx:root>");lsText.append(JUtiles.msRetornoCarro);
        
    
        return lsText.toString();
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JPlugInPrincipalFX.fxml";
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
