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

public class JGeneradorJPlugInPrincipal implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private JProyecto moProyecto;
    public JGeneradorJPlugInPrincipal(JProyecto poProyec) {
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
        lsText.append("import java.io.IOException;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.JT2GENERICO;");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("import utiles.JDepuracion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.IPanelControlador;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.navegadorWeb.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javax.swing.JButton;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javax.swing.JComponent;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javax.swing.JLabel;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.IListaElementos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.JListaElementos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.JTablaConfig;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.toolBar.JCompCMBElemento;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.toolBar.JComponenteAplicacion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JPlugInPrincipal extends javax.swing.JFrame implements IPlugIn {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void procesarInicial(IPlugInContexto poContexto) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        initComponents();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        establecerNombresMenus();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPlugInUtilidades.addBotones(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                poContexto.getFormPrincipal(),");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jToolBar1,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPlugInUtilidades.addMenusFrame(poContexto.getFormPrincipal(), jMenuBar1);");lsText.append(JUtiles.msRetornoCarro);
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
        lsText.append("    public void procesarFinal(IPlugInContexto poContexto) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** This method is called from within the constructor to");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * initialize the form.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * WARNING: Do NOT modify this code. The content of this method is");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * always regenerated by the Form Editor.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // <editor-fold defaultstate=\"collapsed\" desc=\" Generated Code \">//"+invertir("NIGEB-NEG")+":initComponents");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void initComponents() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        java.awt.GridBagConstraints gridBagConstraints;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jToolBar1 = new javax.swing.JToolBar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuBar1 = new javax.swing.JMenuBar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuMantenimiento = new javax.swing.JMenu();");lsText.append(JUtiles.msRetornoCarro);
        
        //ANadimos los mantenimientos de todas las tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) { //Para cada tabla 
            JTableDef loTabla = moConex.getTablasBD().get(i);
            lsText.append("        jMenuM"+ moUtiles.msSustituirRaros(loTabla.getNombre())+" = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        }
        
        
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuMantenimiento.setText(\"Mantenimiento\");");lsText.append(JUtiles.msRetornoCarro);
        //aNadimos los menus de las tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) { //Para cada tabla 
            JTableDef loTabla = moConex.getTablasBD().get(i);
            lsText.append("        jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+".setText(\""+moUtiles.msSustituirRaros(loTabla.getNombre())+"\");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+".addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+"ActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jMenuMantenimiento.add(jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuBar1.add(jMenuMantenimiento);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setJMenuBar(jMenuBar1);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        pack();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // </editor-fold>//"+invertir("DNE-NEG")+":initComponents");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        //Eventos de los distintos mantenimientos
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            JTableDef loTabla = moConex.getTablasBD().get(i);
            String lsNombreBueno = moUtiles.msSustituirRaros(loTabla.getNombre());
            lsText.append("    private void jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+"ActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+"ActionPerformed");lsText.append(JUtiles.msRetornoCarro);
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
            lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+"ActionPerformed");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // Variables declaration - do not modify//"+invertir("NIGEB-NEG")+":variables");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuBar jMenuBar1;");lsText.append(JUtiles.msRetornoCarro);
        
        //Tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            JTableDef loTabla = moConex.getTablasBD().get(i);
            lsText.append("    private javax.swing.JMenuItem jMenuM"+moUtiles.msSustituirRaros(loTabla.getNombre())+";");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    private javax.swing.JMenu jMenuMantenimiento;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JToolBar jToolBar1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // End of variables declaration//"+invertir("DNE-NEG")+":variables");lsText.append(JUtiles.msRetornoCarro);
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
        return "JPlugInPrincipal.java";
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
