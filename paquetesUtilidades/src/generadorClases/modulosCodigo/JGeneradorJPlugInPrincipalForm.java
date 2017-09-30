/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;

public class JGeneradorJPlugInPrincipalForm implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;

    public JGeneradorJPlugInPrincipalForm(JProyecto poProyec) {
        moProyecto = poProyec;
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        lsText.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<Form version=\"1.0\" type=\"org.netbeans.modules.form.forminfo.JFrameFormInfo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <NonVisualComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Menu class=\"javax.swing.JMenuBar\" name=\"jMenuBar1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Menu class=\"javax.swing.JMenu\" name=\"jMenuMantenimiento\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Property name=\"text\" type=\"java.lang.String\" value=\"Mantenimiento\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        //Para cada tabla
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            lsText.append("            <MenuItem class=\"javax.swing.JMenuItem\" name=\"jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\""+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"ActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            </MenuItem>");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("          </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Menu>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Menu>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </NonVisualComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Property name=\"title\" type=\"java.lang.String\" value=\""+moConex.getDirPadre()+"\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Property name=\"name\" type=\"java.lang.String\" value=\""+moConex.getDirPadre()+"\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <SyntheticProperties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"menuBar\" type=\"java.lang.String\" value=\"jMenuBar1\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"formSizePolicy\" type=\"int\" value=\"1\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </SyntheticProperties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <AuxValues>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_generateMnemonicsCode\" type=\"java.lang.Boolean\" value=\"false\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_listenerGenerationStyle\" type=\"java.lang.Integer\" value=\"0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_variablesLocal\" type=\"java.lang.Boolean\" value=\"false\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_variablesModifier\" type=\"java.lang.Integer\" value=\"2\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"designerSize\" type=\"java.awt.Dimension\" value=\"-84,-19,0,5,115,114,0,18,106,97,118,97,46,97,119,116,46,68,105,109,101,110,115,105,111,110,65,-114,-39,-41,-84,95,68,20,2,0,2,73,0,6,104,101,105,103,104,116,73,0,5,119,105,100,116,104,120,112,0,0,1,116,0,0,2,33\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </AuxValues>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Container class=\"javax.swing.JToolBar\" name=\"jToolBar1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <BorderConstraints direction=\"North\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignBoxLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("</Form>");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JPlugInPrincipal.form";
    }

    public boolean isGeneral() {
        return true;
    }
    public String getNombreModulo() {
        return getNombre();
    }
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        loParam.mbGenerar=!moProyecto.getOpciones().isEdicionFX();
        return loParam;
    }

}
