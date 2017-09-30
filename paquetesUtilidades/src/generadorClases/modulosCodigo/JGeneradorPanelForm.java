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

public class JGeneradorPanelForm implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private boolean mbRelacionesExport; //Si tiene este tipo de relaciones, hay que generar pestanas
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorPanelForm(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        
        JTableDef loTabla = moConex.getTablaBD(moConex.getTablaActual());
        if(loTabla.getRelaciones().count() > 0) mbRelacionesExport = true;
        
        lsText.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<Form version=\"1.3\" maxVersion=\"1.7\" type=\"org.netbeans.modules.form.forminfo.JPanelFormInfo\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <AuxValues>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_generateMnemonicsCode\" type=\"java.lang.Boolean\" value=\"false\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_listenerGenerationStyle\" type=\"java.lang.Integer\" value=\"0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_variablesLocal\" type=\"java.lang.Boolean\" value=\"false\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"FormSettings_variablesModifier\" type=\"java.lang.Integer\" value=\"2\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    <AuxValue name=\"designerSize\" type=\"java.awt.Dimension\" value=\"-84,-19,0,5,115,114,0,18,106,97,118,97,46,97,119,116,46,68,105,109,101,110,115,105,111,110,65,-114,-39,-41,-84,95,68,20,2,0,2,73,0,6,104,101,105,103,104,116,73,0,5,119,105,100,116,104,120,112,0,0,1,44,0,0,1,-112\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  </AuxValues>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("  <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("    <Container class=\"javax.swing.JTabbedPane\" name=\"jTabbedPane1\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"0\" insetsLeft=\"0\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.1\" weightY=\"0.1\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.support.JTabbedPaneSupportLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <Container class=\"javax.swing.JPanel\" name=\"jPanelGENERAL\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.support.JTabbedPaneSupportLayout\" value=\"org.netbeans.modules.form.compat2.layouts.support.JTabbedPaneSupportLayout$JTabbedPaneConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              <JTabbedPaneConstraints tabName=\"General\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                <Property name=\"tabTitle\" type=\"java.lang.String\" value=\"General\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              </JTabbedPaneConstraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            </Constraint>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          </Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
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
        
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("            <Container class=\"javax.swing.JPanel\" name=\"jPanelEspaciador\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                  <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"0\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"0\" insetsLeft=\"0\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"1.0\" weightY=\"1.0\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("              <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignFlowLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            </Container>");lsText.append(JUtiles.msRetornoCarro);

            lsText.append("          </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        </Container>");lsText.append(JUtiles.msRetornoCarro);

            //Para cada relacion de exportacion
            if(mbRelacionesExport) {
                for(int i=0;i<loTabla.getRelaciones().count();i++) {
                    JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                    if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
//                        String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
//                        String lsNomControlador = lsNomTabla;
//                        for(int j=0;j < loTabla.getRelaciones().getRelacion(i).getCamposRelacionCount();j++) {
//                            String lsNomCampo = moUtiles.msSustituirRaros(loRelacion.getCampoRelacion(j));
//                            lsNomTabla += "_" + lsNomCampo;
//                        }
                        String lsNomTabla  = moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado);
                        lsText.append("        <Container class=\"javax.swing.JPanel\" name=\"jPanel" + lsNomTabla + "\">");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("          <Constraints>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("            <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.support.JTabbedPaneSupportLayout\" value=\"org.netbeans.modules.form.compat2.layouts.support.JTabbedPaneSupportLayout$JTabbedPaneConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("              <JTabbedPaneConstraints tabName=\"" + lsNomTabla + "\">");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                <Property name=\"tabTitle\" type=\"java.lang.String\" value=\"" + lsNomTabla + "\"/>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("              </JTabbedPaneConstraints>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("            </Constraint>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("          </Constraints>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("          <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("          <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("            <Component class=\"utilesGUIx.formsGenericos.JPanelGenerico\" name=\"jPanelGenerico" + lsNomTabla + "\">");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("              <Constraints>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                  <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"0\" insetsLeft=\"0\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.1\" weightY=\"0.1\"/>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("                </Constraint>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("              </Constraints>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("            </Component>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("          </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        </Container>");lsText.append(JUtiles.msRetornoCarro);
                    }
                }
            }
            
            lsText.append("      </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("    <Container class=\"javax.swing.JPanel\" name=\"jPanelEspaciador\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"0\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"0\" insetsLeft=\"0\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"1.0\" weightY=\"1.0\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.DesignFlowLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        }
        
        //***********************************************************************************************************************
        lsText.append("  </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("  </Container>");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("  </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("  </Container>");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("  </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("</Form>");lsText.append(JUtiles.msRetornoCarro);
          
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
        
        lsText.append("    <Component class=\"utilesGUIx.JCheckBoxCZ\" name=\"jCheck" + psNombre + "\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Property name=\"border\" type=\"javax.swing.border.Border\" editor=\"org.netbeans.modules.form.editors2.BorderEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Border info=\"org.netbeans.modules.form.compat2.border.TitledBorderInfo\">");lsText.append(JUtiles.msRetornoCarro);        
        lsText.append("            <TitledBorder/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          </Border>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Property name=\"text\" type=\"java.lang.String\" value=\""+ psNombre +"\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Property name=\"margin\" type=\"java.awt.Insets\" editor=\"org.netbeans.beaninfo.editors.InsetsEditor\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <Insets value=\"[0, 0, 0, 0]\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Property>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"0\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.1\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Component>");lsText.append(JUtiles.msRetornoCarro);
        
        return lsText.toString();
    }

    //Panel de busqueda
    private String addPanelBusqueda(String psNombre,int row,int col) {
        StringBuffer lsText = new StringBuffer(100);
        
        lsText.append("    <Component class=\"utilesGUIx.panelesGenericos.JPanelBusqueda\" name=\"jPanel" + psNombre + "\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Property name=\"label\" type=\"java.lang.String\" value=\"" + psNombre + "\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"0\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.1\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Component>");lsText.append(JUtiles.msRetornoCarro);
        
        return lsText.toString();
    }

    //label
    private String addLabel(String psTexto,int row,int col) {
        StringBuffer lsText = new StringBuffer(100);
        
        lsText.append("    <Component class=\"utilesGUIx.JLabelCZ\" name=\"lbl" + psTexto + "\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Property name=\"text\" type=\"java.lang.String\" value=\""+ psTexto +"\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Properties>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("          <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    </Component>");lsText.append(JUtiles.msRetornoCarro);
        
        return lsText.toString();
    }
    
    //Text
    private String addText(String psNombre,int row,int col,JFieldDefs poCampos) {
        StringBuffer lsText = new StringBuffer(100);
        
        if(poCampos.get(row).getTamano() > 99999) { //En este caso es memo
            lsText.append("    <Container class=\"javax.swing.JScrollPane\" name=\"jScroll"+ psNombre +"\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <AuxValues>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <AuxValue name=\"autoScrollPane\" type=\"java.lang.Boolean\" value=\"true\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      </AuxValues>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"1\" gridHeight=\"1\" fill=\"1\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.0\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);

            lsText.append("      <Layout class=\"org.netbeans.modules.form.compat2.layouts.support.JScrollPaneSupportLayout\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <SubComponents>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <Component class=\"utilesGUIx.JTextAreaCZ\" name=\"txt"+ psNombre +"\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <Properties>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            <Property name=\"columns\" type=\"int\" value=\"20\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            <Property name=\"rows\" type=\"int\" value=\"5\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          </Properties>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        </Component>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      </SubComponents>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    </Container>");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("    <Component class=\"utilesGUIx.JTextFieldCZ\" name=\"txt"+ psNombre +"\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      <Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        <Constraint layoutClass=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout\" value=\"org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout$GridBagConstraintsDescription\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("          <GridBagConstraints gridX=\"-1\" gridY=\"-1\" gridWidth=\"0\" gridHeight=\"1\" fill=\"2\" ipadX=\"0\" ipadY=\"0\" insetsTop=\"2\" insetsLeft=\"2\" insetsBottom=\"0\" insetsRight=\"0\" anchor=\"10\" weightX=\"0.1\" weightY=\"0.0\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        </Constraint>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("      </Constraints>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    </Component>");lsText.append(JUtiles.msRetornoCarro);
        }
        
        return lsText.toString();
    }
    public String getRutaRelativa() {
        return "forms";
    }

    public String getNombre() {
        return "JPanel"+ moUtiles.msSustituirRaros(moConex.getTablaActual())+".form";
    }

    public boolean isGeneral() {
        return false;
    }
    public String getNombreModulo() {
        return "JPanel.form";
    }
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );;
        loParam.mbGenerar=!moProyecto.getOpciones().isEdicionFX();
        if(loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
            loParam.mbGenerar=false;
        }
        return loParam;
    }

}
