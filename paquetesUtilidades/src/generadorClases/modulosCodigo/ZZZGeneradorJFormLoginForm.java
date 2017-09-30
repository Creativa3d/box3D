/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;

public class ZZZGeneradorJFormLoginForm implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades

    public ZZZGeneradorJFormLoginForm(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        lsText.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<Form version=\"1.0\" type=\"org.netbeans.modules.form.forminfo.JDialogFormInfo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Property name=\"defaultCloseOperation\" type=\"int\" value=\"2\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Property name=\"title\" type=\"java.lang.String\" value=\"Login\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <SyntheticProperties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"formSize\" type=\"java.awt.Dimension\" value=\"-84,-19,0,5,115,114,0,18,106,97,118,97,46,97,119,116,46,68,105,109,101,110,115,105,111,110,65,-114,-39,-41,-84,95,68,20,2,0,2,73,0,6,104,101,105,103,104,116,73,0,5,119,105,100,116,104,120,112,0,0,0,-94,0,0,3,39\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"formSizePolicy\" type=\"int\" value=\"0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"generateSize\" type=\"boolean\" value=\"true\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <SyntheticProperty name=\"generateCenter\" type=\"boolean\" value=\"true\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </SyntheticProperties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <AuxValues>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"designerSize\" type=\"java.awt.Dimension\" value=\"-84,-19,0,5,115,114,0,18,106,97,118,97,46,97,119,116,46,68,105,109,101,110,115,105,111,110,65,-114,-39,-41,-84,95,68,20,2,0,2,73,0,6,104,101,105,103,104,116,73,0,5,119,105,100,116,104,120,112,0,0,0,-124,0,0,3,29\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </AuxValues>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Container class=\"javax.swing.JPanel\" name=\"jPanel1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <BorderConstraints direction=\"North\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <Container class=\"javax.swing.JPanel\" name=\"jPanel3\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Property name=\"border\" type=\"javax.swing.border.Border\" editor=\"org.netbeans.modules.form.editors2.BorderEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Border info=\"org.netbeans.modules.form.compat2.border.BevelBorderInfo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <BevelBorder/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Border>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <BorderConstraints direction=\"Center\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Container class=\"javax.swing.JPanel\" name=\"jPanel2\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <BorderConstraints direction=\"Center\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"javax.swing.JLabel\" name=\"jLabel1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Login\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <GridBagConstraints gridX=\"0\" gridY=\"0\" gridWidth=\"1\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"2\" insetsRight=\"2\" anchor=\"10\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"utilesGUIx.JTextFieldCZ\" name=\"jTextLogin\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <GridBagConstraints gridX=\"1\" gridY=\"0\" gridWidth=\"0\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"2\" insetsRight=\"2\" anchor=\"10\" weightX=\"0.3\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"javax.swing.JLabel\" name=\"jLabel2\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"PassWord\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <GridBagConstraints gridX=\"-1\" gridY=\"1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"2\" insetsRight=\"2\" anchor=\"10\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"javax.swing.JPasswordField\" name=\"jTextPassWord\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <GridBagConstraints gridX=\"1\" gridY=\"1\" gridWidth=\"0\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"2\" insetsRight=\"2\" anchor=\"10\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"javax.swing.JLabel\" name=\"jLabel5\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Servidor\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"2\" insetsRight=\"2\" anchor=\"10\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"utilesGUIx.JComboBoxCZ\" name=\"jComboBoxCZ1\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"2\" insetsRight=\"2\" anchor=\"10\" weightX=\"1.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"utilesGUIx.JButtonCZ\" name=\"jBtnConexionesBD\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"...\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jBtnConexionesBDActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"0\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"0\" insetsLeft=\"0\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("          </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Container>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Container class=\"javax.swing.JPanel\" name=\"jPanelBotones\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Property name=\"background\" type=\"java.awt.Color\" editor=\"org.netbeans.beaninfo.editors.ColorEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Color blue=\"ba\" green=\"b5\" red=\"af\" type=\"rgb\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Property name=\"border\" type=\"javax.swing.border.Border\" editor=\"org.netbeans.modules.form.editors2.BorderEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Border info=\"org.netbeans.modules.form.compat2.border.EtchedBorderInfo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EtchetBorder/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Border>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <BorderConstraints direction=\"South\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignFlowLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"javax.swing.JButton\" name=\"jButtonAceptar\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"icon\" type=\"javax.swing.Icon\" editor=\"org.openide.explorer.propertysheet.editors.IconEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <Image iconType=\"3\" name=\"/images/Import16.gif\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Login  \"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jButtonAceptarActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"javax.swing.JLabel\" name=\"jLabel4\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"        \"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            <Component class=\"javax.swing.JButton\" name=\"jButtonCancelar\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"icon\" type=\"javax.swing.Icon\" editor=\"org.openide.explorer.propertysheet.editors.IconEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  <Image iconType=\"3\" name=\"/images/Stop16bis.gif\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <Property name=\"text\" type=\"java.lang.String\" value=\"Cancelar\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              <Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                <EventHandler event=\"actionPerformed\" listener=\"java.awt.event.ActionListener\" parameters=\"java.awt.event.ActionEvent\" handler=\"jButtonCancelarActionPerformed\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("              </Events>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Container>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("</Form>");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }
    public String getRutaRelativa() {
        return "forms";
    }

    public String getNombre() {
        return "JFormLogin.form";
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
