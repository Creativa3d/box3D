/*
* clase generada con metaGenerador 
*
* Creado el 5/2/2006
*/

package generadorClases.modulosCodigo;

import ListDatos.JUtilTabla;
import generadorClases.*;
import utiles.*;

public class ZZZJGeneradorJFormPrincipal implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;

    public ZZZJGeneradorJFormPrincipal(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * JFormPrincipal.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************

        //IMPORTACION***************************************
        lsText.append("package "+moConex.getDirPadre()+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import javax.swing.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.io.FileNotFoundException;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.io.IOException;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.awt.Container;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.config.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.JDepuracion;        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.lookAndFeel.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.JTablaConfig;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.IPlugInFrame;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".forms.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".tablasControladoras.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+"."+moUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JFormPrincipal extends javax.swing.JFrame implements IPlugInFrame {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private JLookAndFeel moLook;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private JFormEdicionParametros moParametros = new JFormEdicionParametros();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    static {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JGUIxConfigGlobal.getInstancia().setFicheroLongTablas(\"ConfigurationLong"+moConex.getDirPadre()+".xml\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // Add your handling code here:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates new form JFormPrincipal */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JFormPrincipal() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("        //look and feel");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        rellenarLookAndFeel();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moLook = new JLookAndFeel();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moLook.setComponenteBase(this);        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ponerLookAndFeel(JDatosGeneralesP.getDatosGenerales().getLookAndFeel(), \"\");");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        initComponents();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        establecerNombresMenus();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setExtendedState(JFrame.MAXIMIZED_BOTH);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JDatosGeneralesP.getDatosGenerales().setDesktopPane1(jDesktopPane1, this, jProcesoThreadGroup1);");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        jMenuLoginNuevoActionPerformed(null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JDatosGeneralesP.getDatosGeneralesPlugIn().getPlugInManager().procesarInicial(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                JDatosGeneralesP.getDatosGeneralesPlugIn().getPlugInContexto()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                );");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JMenuBar getMenu() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return jMenuBar1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String getIdentificador() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return getClass().getName();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public Container getContenedor() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return getContentPane();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JFormEdicionParametros getParametros() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moParametros;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void establecerNombresMenus(){");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) { //Para cada tabla 
            lsText.append("        jMenuM"+ moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+".setText(JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JT"+ moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+".msCTabla) );");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void ponerLookAndFeel(String psLookAndFeel, String psTema) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(psLookAndFeel.compareTo(\"\")!=0){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moLook.setEstilo(psLookAndFeel);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moLook.setTema(psTema);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void rellenarLookAndFeel(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            UIManager.installLookAndFeel(\"Plastic 3d\", \"com.jgoodies.looks.plastic.Plastic3DLookAndFeel\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            UIManager.installLookAndFeel(\"Liquido\", \"com.birosoft.liquid.LiquidLookAndFeel\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            UIManager.installLookAndFeel(\"NimROD\", \"com.nilo.plaf.nimrod.NimRODLookAndFeel\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            UIManager.installLookAndFeel(\"Defecto SO\", UIManager.getCrossPlatformLookAndFeelClassName());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            UIManager.installLookAndFeel(\"Defecto\", UIManager.getSystemLookAndFeelClassName());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
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
        lsText.append("        buttonGroup1 = new javax.swing.ButtonGroup();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jToolBar1 = new javax.swing.JToolBar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jDesktopPane1 = new javax.swing.JDesktopPane();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuBar1 = new javax.swing.JMenuBar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuArchivo = new javax.swing.JMenu();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuASalir = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuAPropiedades = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuActEstruc = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuMantenimiento = new javax.swing.JMenu();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuConexion = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuAEstilo = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jProcesoThreadGroup1 = new utilesGUIx.controlProcesos.JProcesoThreadGroup();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jPanel1 = new javax.swing.JPanel();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jLabel1 = new javax.swing.JLabel();");lsText.append(JUtiles.msRetornoCarro);
        
        //Anadimos los mantenimientos de todas las tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) { //Para cada tabla 
            lsText.append("        jMenuM"+ moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+" = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        }
        
        
        lsText.append("        jMenuLogin = new javax.swing.JMenu();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuLoginNuevo = new javax.swing.JMenuItem();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setTitle(\"Gesti\u00f3n "+ moConex.getDirPadre()+"\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setName(\"Gesti\u00f3n "+ moConex.getDirPadre()+"\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        addWindowListener(new java.awt.event.WindowAdapter() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void windowClosing(java.awt.event.WindowEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                exitForm(evt);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jPanel1.setLayout(new java.awt.GridBagLayout());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jLabel1.setText(\"   \");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.weightx = 1.0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jPanel1.add(jLabel1, gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jProcesoThreadGroup1.setPreferredSize(new java.awt.Dimension(150, 20));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jPanel1.add(jProcesoThreadGroup1, gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jDesktopPane1.setAutoscrolls(true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuArchivo.setText(\"Archivo\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuASalir.setText(\"Salir\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuASalir.addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jMenuASalirActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuArchivo.add(jMenuASalir);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuAPropiedades.setText(\"Propiedades\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuAPropiedades.addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jMenuAPropiedadesActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuArchivo.add(jMenuAPropiedades);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuAEstilo.setText(\"Estilo visual\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuAEstilo.addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jMenuAEstiloActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuArchivo.add(jMenuAEstilo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        jMenuConexion.setText(\"Config. Conexi\u00f3n BD\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuConexion.addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jMenuConexionActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuArchivo.add(jMenuConexion);");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        jMenuActEstruc.setText(\"Actualizar Estructura\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuActEstruc.addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jMenuActEstrucActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("        jMenuArchivo.add(jMenuActEstruc);");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuBar1.add(jMenuArchivo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuMantenimiento.setText(\"Mantenimiento\");");lsText.append(JUtiles.msRetornoCarro);
        //anadimos los menus de las tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) { //Para cada tabla 
            lsText.append("        jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+".setText(\""+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"\");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+".addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"ActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jMenuMantenimiento.add(jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuBar1.add(jMenuMantenimiento);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuLogin.setText(\"Login\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuLoginNuevo.setText(\"Nuevo login\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuLoginNuevo.addActionListener(new java.awt.event.ActionListener() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(java.awt.event.ActionEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                jMenuLoginNuevoActionPerformed(evt);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuLogin.add(jMenuLoginNuevo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        jMenuBar1.add(jMenuLogin);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setJMenuBar(jMenuBar1);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        pack();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // </editor-fold>//"+invertir("DNE-NEG")+":initComponents");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        //Eventos de los distintos mantenimientos
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            lsText.append("    private void jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"ActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"ActionPerformed");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            JT2"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+" lo"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+" = new JT2"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"(JDatosGeneralesP.getDatosGenerales().moServer, JDatosGeneralesP.getDatosGenerales());");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            lo"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+".mostrarFormPrinci();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+"ActionPerformed");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    private void jMenuActEstrucActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuActEstrucActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JActualizarEstruc loEstru = new  JActualizarEstruc();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loEstru.actualizar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this, \"Proceso terminado\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuActEstrucActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    private void jMenuAEstiloActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuAEstiloActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moLook.mostrar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(JDatosGenerales.mcsLookAndFeel, moLook.getEstilo().msEstilo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            IFilaDatos loFila = moLook.getTema();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            if(loFila==null){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(JDatosGenerales.mcsLookAndFeelTema, \"\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(JDatosGenerales.mcsLookAndFeelTema, loFila.msCampo(JLookAndFeelEstilo.mclTema));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuAEstiloActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    private void jMenuConexionActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuConexionActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDatosGeneralesP.getDatosGenerales().mostrarPropiedadesConexionBD();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuConexionActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void jMenuAPropiedadesActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuAPropiedadesActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDatosGeneralesP.getDatosGenerales().mostrarPropiedades();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuAPropiedadesActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void jMenuLoginNuevoActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuLoginNuevoActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JFormLogin loLogin = new JFormLogin(this, true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loLogin.show();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuLoginNuevoActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void jMenuASalirActionPerformed(java.awt.event.ActionEvent evt) {//"+invertir("TSRIF-NEG")+":event_jMenuASalirActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        exitForm(null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }//"+invertir("TSAL-NEG")+":event_jMenuASalirActionPerformed");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Exit the Application */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void exitForm(java.awt.event.WindowEvent evt) {//"+invertir("TSRIF-NEG")+":event_exitForm");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JTablaConfig.guardarConfig();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (FileNotFoundException ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            ex.printStackTrace();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (IOException ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            ex.printStackTrace();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        dispose();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        System.exit(0);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }//"+invertir("TSAL-NEG")+":event_exitForm");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * @param args the command line arguments");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static void main(String args[]) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        new JFormPrincipal().show();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // Variables declaration - do not modify//"+invertir("NIGEB-NEG")+":variables");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.ButtonGroup buttonGroup1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JDesktopPane jDesktopPane1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuItem jMenuAPropiedades;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuItem jMenuActEstruc;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuItem jMenuASalir;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuItem jMenuAEstilo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenu jMenuArchivo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuBar jMenuBar1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenu jMenuLogin;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuItem jMenuLoginNuevo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JMenuItem jMenuConexion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JPanel jPanel1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private utilesGUIx.controlProcesos.JProcesoThreadGroup jProcesoThreadGroup1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private javax.swing.JLabel jLabel1;");lsText.append(JUtiles.msRetornoCarro);
        
        //Tablas
        for(int i = 0; i < moConex.getTablasBD().getListaTablas().size();i++) {
            lsText.append("    private javax.swing.JMenuItem jMenuM"+moUtiles.msSustituirRaros(moConex.getTablasBD().get(i).getNombre())+";");lsText.append(JUtiles.msRetornoCarro);
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
        return "JFormPrincipal.java";
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
