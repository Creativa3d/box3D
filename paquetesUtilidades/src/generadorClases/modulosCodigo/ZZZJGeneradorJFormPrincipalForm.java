/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;

public class ZZZJGeneradorJFormPrincipalForm implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;

    public ZZZJGeneradorJFormPrincipalForm(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        lsText.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<Form version=\"1.0\" type=\"org.netbeans.modules.form.forminfo.JFrameFormInfo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <NonVisualComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Component class=\"javax.swing.ButtonGroup\" name=\"buttonGroup1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Menu class=\"javax.swing.JMenuBar\" name=\"jMenuBar1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Menu class=\"javax.swing.JMenu\" name=\"jMenuArchivo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Property name=\"text\" type=\"java.lang.String\" value=\"Archivo\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <MenuItem class=\"javax.swing.JMenuItem\" name=\"jMenuASalir\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Salir\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jMenuASalirActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </MenuItem>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <MenuItem class=\"javax.swing.JMenuItem\" name=\"jMenuAPropiedades\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Propiedades\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jMenuAPropiedadesActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </MenuItem>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <MenuItem class=\"javax.swing.JMenuItem\" name=\"jMenuAEstilo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Estilo visual\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jMenuAEstiloActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </MenuItem>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <MenuItem class=\"javax.swing.JMenuItem\" name=\"jMenuConexion\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Config. Conexi&#xf3;n BD\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jMenuConexionActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </MenuItem>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <MenuItem class=\"javax.swing.JMenuItem\" name=\"jMenuActEstruc\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Actualizar Estructura\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jMenuActEstrucActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </MenuItem>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Menu>");lsText.append(JUtiles.msRetornoCarro);
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
        lsText.append("        <Menu class=\"javax.swing.JMenu\" name=\"jMenuLogin\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Property name=\"text\" type=\"java.lang.String\" value=\"Login\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <MenuItem class=\"javax.swing.JMenuItem\" name=\"jMenuLoginNuevo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Nuevo login\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jMenuLoginNuevoActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </MenuItem>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Menu>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Menu>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </NonVisualComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Property name=\"title\" type=\"java.lang.String\" value=\"Gestión "+moConex.getDirPadre()+"\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Property name=\"name\" type=\"java.lang.String\" value=\"Gestión "+moConex.getDirPadre()+"\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <SyntheticProperties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"menuBar\" type=\"java.lang.String\" value=\"jMenuBar1\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"formSizePolicy\" type=\"int\" value=\"1\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </SyntheticProperties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <EventHandler event=\"windowClosing\" listener=\"java.awt.event.WindowListener\" parameters=\"java.awt.event.WindowEvent\" handler=\"exitForm\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </Events>");lsText.append(JUtiles.msRetornoCarro);
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
        lsText.append("    <Container class=\"javax.swing.JDesktopPane\" name=\"jDesktopPane1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Property name=\"autoscrolls\" type=\"boolean\" value=\"true\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <BorderConstraints direction=\"Center\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.support.JLayeredPaneSupportLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Container class=\"javax.swing.JPanel\" name=\"jPanel1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <BorderConstraints direction=\"South\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Component class=\"javax.swing.JLabel\" name=\"jLabel1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Property name=\"text\" type=\"java.lang.String\" value=\"   \"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"0\" insetsLeft=\"0\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"1.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Component class=\"utilesGUIx.controlProcesos.JProcesoThreadGroup\" name=\"jProcesoThreadGroup1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Property name=\"preferredSize\" type=\"java.awt.Dimension\" editor=\"org.netbeans.beaninfo.editors.DimensionEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Dimension value=\"[150, 20]\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"0\" insetsLeft=\"0\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"13\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("  </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("</Form>");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }
    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "JFormPrincipal.form";
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