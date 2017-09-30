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

public class JGeneradorPanel implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private boolean mbRelacionesExport; //Si tiene este tipo de relaciones, hay que generar pestanas
    private JProyecto moProyecto;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorPanel(JProyecto poProyec) {
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
        
        //Compruebo el numero de relaciones de importacion para saber si tiene que llevar pestanas
        mbRelacionesExport = moConex.tieneRelacionesExport();

        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JPanel" + moUtiles.msSustituirRaros(loTabla.getNombre()) + ".java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + moConex.getDirPadre() + "." + "forms;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import java.awt.Rectangle;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.Rectangulo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.JDepuracion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.busqueda.*;");lsText.append(JUtiles.msRetornoCarro);
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
        lsText.append("public class JPanel"+ moUtiles.msSustituirRaros(loTabla.getNombre())+" extends JPanelGENERALBASE {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private "+moUtiles.getNombreTablaExtends(loTabla.getNombre())+" mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
//        //Controladores para las pestanas
//        if(mbRelacionesExport) {
//            for(int i=0;i<loTabla.getRelaciones().count();i++) {
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
//                    lsText.append("    private JT2"+ moConex.getNomTablaSimple(i) +" moCtrl"+ moConex.getNomTablaComplejo(i,JUtiles.mcnCamposTablaRelacionada) +" = null;");lsText.append(JUtiles.msRetornoCarro);
//                }
//            }
//            lsText.append(JUtiles.msRetornoCarro);
//        }
        
        lsText.append("    /** Creates new form JPanel"+ moUtiles.msSustituirRaros(loTabla.getNombre())+"*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JPanel"+ moUtiles.msSustituirRaros(loTabla.getNombre())+"() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        initComponents();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        if(moProyecto.getOpciones().getPKInvisibles()) {
            lsText.append("    public void setPKInvisible() {");lsText.append(JUtiles.msRetornoCarro);

//            //Introduce los paneles de busqueda en una lista inicial
//            loPaneles = new JListaElementos();
//            for(int i=0;i<loTabla.getRelaciones().count();i++) {
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
//                    loPaneles.add(loRelacion.getCampoPropio(0));
//                }
//            }
            
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
                                    lsText.append("        jScroll"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setVisible(false);");lsText.append(JUtiles.msRetornoCarro);
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
        
        lsText.append("    public void setDatos(final "+moUtiles.getNombreTablaExtends(loTabla.getNombre())+" po"+ moUtiles.msSustituirRaros(loTabla.getNombre())+", final IPanelControlador poPadre) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+" = po"+ moUtiles.msSustituirRaros(loTabla.getNombre())+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setDatos(poPadre);");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        moConsulta = poConsulta;");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        if(poConsulta!=null){");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("            clonar(poConsulta.getList());");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public String getTitulo() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        String lsResult;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+".moList.getModoTabla() == JListDatos.mclNuevo) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            lsResult= JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("+moUtiles.getNombreTablaExtends(loTabla.getNombre())+".msCTabla) + \" [Nuevo]\" ;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        if(loTabla.getIndices().getCountIndices()>0) {
            lsText.append("            lsResult=JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("+moUtiles.getNombreTablaExtends(loTabla.getNombre())+".msCTabla)  + ");lsText.append(JUtiles.msRetornoCarro);
        }else {
            lsText.append("            lsResult=JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("+moUtiles.getNombreTablaExtends(loTabla.getNombre())+".msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        }
        int k = 0;
        for(int i = 0; i < loCampos.count(); i++ ){
            if (loCampos.get(i).getPrincipalSN()) {
                if(k == (loCampos.masCamposPrincipales().length - 1)){
                    lsText.append("                mo" + moUtiles.msSustituirRaros(loTabla.getNombre())+ ".get" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "().getString();");lsText.append(JUtiles.msRetornoCarro);
                }else{
                    lsText.append("                mo" + moUtiles.msSustituirRaros(loTabla.getNombre())+ ".get" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "().getString() + \"-\" +");lsText.append(JUtiles.msRetornoCarro);
                }
                k++;
            }
        }
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return lsResult;");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JSTabla getTabla(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return mo" + moUtiles.msSustituirRaros(moConex.getTablaActual()) +  ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void rellenarPantalla() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionImport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                String lsNomCampo = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getCampoPropio(0));
                lsText.append("        jPanel" + lsNomCampo + ".setDatos(JDatosGeneralesP.getDatosGenerales().getParamPanelBusq(mo" + moUtiles.msSustituirRaros(moConex.getTablaActual()) + ".moList.moServidor, moPadre.getParametros().getMostrarPantalla(), JT"+moUtiles.msSustituirRaros(lsNomTabla)+".msCTabla));");
                lsText.append(JUtiles.msRetornoCarro);
            }
        }
        //
        //ponemos los caption a los controles
        //
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //ponemos los textos a los label");
        lsText.append(JUtiles.msRetornoCarro);

//        loPaneles = new JListaElementos();
//        for(int i=0;i<loTabla.getRelaciones().count();i++) {
//            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
//                loPaneles.add(loRelacion.getCampoPropio(0));
//            }
//        }
        
        
        for(int i = 0; i < loCampos.count(); i++ ){
            if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                switch(loCampos.get(i).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                        //check
                        lsText.append("        jCheck" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setField(mo"+moUtiles.msSustituirRaros(loTabla.getNombre())+".get"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"());");lsText.append(JUtiles.msRetornoCarro);
                        break;
                    default:
                        //textos
                        lsText.append("        lbl" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".setField(mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+".get" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "());");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".setField(mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+".get" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "());");lsText.append(JUtiles.msRetornoCarro);
                        break;
                }
            }else {
                lsText.append("        jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setLabel(mo"+moUtiles.msSustituirRaros(loTabla.getNombre())+".get"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"().getCaption());");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setField(mo"+moUtiles.msSustituirRaros(loTabla.getNombre())+".get"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"());");lsText.append(JUtiles.msRetornoCarro);
            }
        }        

        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void habilitarSegunEdicion() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+".moList.getModoTabla() == JListDatos.mclNuevo) {");lsText.append(JUtiles.msRetornoCarro);
        
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
                            lsText.append("            jCheck"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setEnabled(true);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                        default:
                            lsText.append("            txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".setEnabled(true);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                    }
                }else {
                    lsText.append("            jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setEnabled(true);");lsText.append(JUtiles.msRetornoCarro);
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
                            lsText.append("            jCheck"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setEnabled(false);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                        default:
                            lsText.append("            txt" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ".setEnabled(false);");lsText.append(JUtiles.msRetornoCarro);
                            break;
                    }
                }else {
                    lsText.append("            jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setEnabled(false);");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }



        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().getPKInvisibles()) {
            lsText.append("        setPKInvisible();");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void ponerTipoTextos() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        

        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void mostrarDatos() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IFilaDatos loFila;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);

        //Inicializar Controladores para las pestanas
        if(mbRelacionesExport) {
            lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jTabbedPane1StateChanged(null);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append(JUtiles.msRetornoCarro);
        }
        
        
//        //Introduce el resto de controles
//        for(int i = 0; i < loCampos.count(); i++ ){
//            if(comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacionDeCampo(loCampos.get(i).getNombre());
//                if(loRelacion.getCamposRelacionCount()==1) {
//                    lsText.append("        jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setValueTabla(mo"+moUtiles.msSustituirRaros(loTabla.getNombre())+".get"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"().getString());");lsText.append(JUtiles.msRetornoCarro);
//                }else {
//                    lsText.append("        loFila = new JFilaDatosDefecto(\"\");");lsText.append(JUtiles.msRetornoCarro);
//                    for(int j=0;j<loRelacion.getCamposRelacionCount();j++) { //Para cada campo de la relacion
//                        String lsCampo = moUtiles.msSustituirRaros(loRelacion.getCampoPropio(j));
//                        lsText.append("        loFila.addCampo(mo"+ moUtiles.msSustituirRaros(loTabla.getNombre()) +".get"+lsCampo+"().getString());");lsText.append(JUtiles.msRetornoCarro);
//                    }
//                    lsText.append("        jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setValueTabla(loFila);");lsText.append(JUtiles.msRetornoCarro);
//                    lsText.append(JUtiles.msRetornoCarro);
//                }
//            }
//        }
        
        lsText.append("        //Establecemos los valores de los paneles si hay");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //jPanelColectorEntrada.setValueTabla(moAlbaran.getCOLECTORENTRADA().getString());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void establecerDatos() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        
//        //Introduce el resto de controles
//        for(int i = 0; i < loCampos.count(); i++ ){
//            if(comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
//                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacionDeCampo(loCampos.get(i).getNombre());
//                if(loRelacion.getCamposRelacionCount()==1) {
//                    lsText.append("        mo"+moUtiles.msSustituirRaros(loTabla.getNombre())+
//                            ".get"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"().setValue("+
//                                "jPanel"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+".getText());");
//                    lsText.append(JUtiles.msRetornoCarro);
//                }else {
//                    JFieldDefs loCamposRelacionados = moConex.getTablaBD(loRelacion.getTablaRelacionada()).getCampos();
//                    for(int j = 0; j < loCamposRelacionados.count(); j++ ){
//                        if (loCamposRelacionados.get(j).getPrincipalSN()) {
//                            lsText.append("mo"+moUtiles.msSustituirRaros(loTabla.getNombre())+".get"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"().setValue(jPanel"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+".getText("+j+"));");lsText.append(JUtiles.msRetornoCarro);
//                        }
//                    }
//                }
//            }
//        }

        lsText.append("        mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+".validarCampos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void aceptar() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        int lModo = getModoTabla();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        IResultado loResult=mo"+ moUtiles.msSustituirRaros(loTabla.getNombre())+".guardar();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loResult.getBien()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("             actualizarPadre(lModo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(loResult.getMensaje());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
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
            lsText.append("            jTabbedPane1.setSelectedIndex(0);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            throw new Exception(\"Es necesario guardar datos antes de continuar\");");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
            
            lsText.append("    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        super.setBloqueoControles(pbBloqueo);");lsText.append(JUtiles.msRetornoCarro);
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    lsText.append("        setBloqueoControlesContainer(jPanel"+ moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado)+",false);");lsText.append(JUtiles.msRetornoCarro);
                }
            }
            lsText.append("   }");lsText.append(JUtiles.msRetornoCarro);
            
            lsText.append("    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//"+ invertir("TSRIF-NEG") + ":event_jTabbedPane1StateChanged");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        try{");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            if(jTabbedPane1.getSelectedIndex()>=0){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                switch(jTabbedPane1.getSelectedIndex()){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                    case 0://General");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                        break;");lsText.append(JUtiles.msRetornoCarro);
            
            int cont = 1;
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    lsText.append("                    case " + cont + "://"+ moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado)+"");lsText.append(JUtiles.msRetornoCarro);
                    cont++;
                    lsText.append("                        compruebaPK();");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("                        jPanelGenerico"+ moConex.getNomTablaComplejo(i,JUtiles.mcnCamposTablaRelacionada) +".setControlador(mo"+moUtiles.msSustituirRaros(loTabla.getNombre()) + ".getControlador(JT"+ moConex.getNomTablaSimple(i)+".msCTabla, moPadre.getParametros().getMostrarPantalla()));");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("                        break;");lsText.append(JUtiles.msRetornoCarro);
                }
            }
            
            lsText.append("                }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }catch(Exception e){");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            JDepuracion.anadirTexto(getClass().getName(), e);");lsText.append(JUtiles.msRetornoCarro);    
            lsText.append("            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    }//" + invertir("TSAL-NEG") + ":event_jTabbedPane1StateChanged");lsText.append(JUtiles.msRetornoCarro);
        }
        
        
        //CODIGO JAVA DE GESTION DE CONTROLES DEL FORMULARIO *******************************************************************
        
        lsText.append("    /** Este metodo es llamado desde el constructor para");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     *  inicializar el formulario.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     *  AVISO: No modificar este codigo. El contenido de este metodo es");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     *  siempre regenerado por el editor de formularios.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//"+ invertir("NIGEB-NEG") +":initComponents");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private void initComponents() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        java.awt.GridBagConstraints gridBagConstraints;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        //Insertamos todos los controles
        
        //Introduce los paneles de busqueda en una lista inicial
        
//        loPaneles = new JListaElementos();
//        for(int i=0;i<loTabla.getRelaciones().count();i++) {
//            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
//                loPaneles.add(loRelacion.getCampoPropio(0));
//            }
//        }
        
        //Anade el tabulador y la pestana general
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("        jTabbedPane1 = new javax.swing.JTabbedPane();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        jPanelGENERAL = new javax.swing.JPanel();");lsText.append(JUtiles.msRetornoCarro);
        }
        
        //Anade las pestanas y dentro de estas los paneles genericos
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
                    
                    lsText.append("        jPanel" + moUtiles.msSustituirRaros(lsNomTabla) + " = new javax.swing.JPanel();");lsText.append(JUtiles.msRetornoCarro);
                }
            }
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                    String lsNomControlador = lsNomTabla;
                    for(int j=0;j < loTabla.getRelaciones().getRelacion(i).getCamposRelacionCount();j++) {
                        String lsNomCampo = moUtiles.msSustituirRaros(loRelacion.getCampoRelacion(j));
                        lsNomTabla += "_" + lsNomCampo;
                    }
                    
                    lsText.append("        jPanelGenerico" + moUtiles.msSustituirRaros(lsNomTabla) + " = new utilesGUIx.formsGenericos.JPanelGenerico();");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        
        //Introduce el resto de controles 
        for(int i = 0; i < loCampos.count(); i++ ){
            if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                switch(loCampos.get(i).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                        //check
                        lsText.append("        jCheck" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + " = new utilesGUIx.JCheckBoxCZ();");lsText.append(JUtiles.msRetornoCarro);
                        break;
                    default:
                        //labels
                        lsText.append("        lbl"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +" = new utilesGUIx.JLabelCZ();");lsText.append(JUtiles.msRetornoCarro);
                        //textos
                        if(loCampos.get(i).getTamano() > 99999) { //En este caso es memo
                            lsText.append("        jScroll"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +" = new javax.swing.JScrollPane();");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +" = new utilesGUIx.JTextAreaCZ();");lsText.append(JUtiles.msRetornoCarro);
                        } else {
                            lsText.append("        txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +" = new utilesGUIx.JTextFieldCZ();");lsText.append(JUtiles.msRetornoCarro);
                        }
                        break;
                }
            }else {
                //Introduce paneles de busqueda
                lsText.append("        jPanel"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+" = new utilesGUIx.panelesGenericos.JPanelBusqueda();");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        
        lsText.append("        jPanelEspaciador = new javax.swing.JPanel();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        setLayout(new java.awt.GridBagLayout());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        
        if(mbRelacionesExport) {
            lsText.append("        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            public void stateChanged(javax.swing.event.ChangeEvent evt) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                jTabbedPane1StateChanged(evt);");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        });");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append(JUtiles.msRetornoCarro);

        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());");lsText.append(JUtiles.msRetornoCarro);
        }
        
        //Establece Layout por cada relacion de exportacion
        if(mbRelacionesExport) {
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    lsText.append("        jPanel" + moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado) + ".setLayout(new java.awt.GridBagLayout());");lsText.append(JUtiles.msRetornoCarro);
                }
            }
            lsText.append(JUtiles.msRetornoCarro);
        }
        
        //Implementamos las propiedades de los controles

//        //Introduce los paneles de busqueda en una lista inicial
//        loPaneles = new JListaElementos();
//        for(int i=0;i<loTabla.getRelaciones().count();i++) {
//            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
//                loPaneles.add(loRelacion.getCampoPropio(0));
//            }
//        }
        
        //Introduce el resto de controles 
        for(int i = 0; i < loCampos.count(); i++ ){
            if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                switch(loCampos.get(i).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                        //check
                        lsText.append("        jCheck"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+".setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        jCheck"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+".setText(\""+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"\");");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        gridBagConstraints.weightx = 0.1;");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);");lsText.append(JUtiles.msRetornoCarro);
                        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
                            lsText.append("        jPanelGENERAL.add(jCheck"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                        }else {
                            lsText.append("        add(jCheck"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                        }
                        break;
                    default:
                        //labels
                        lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);");lsText.append(JUtiles.msRetornoCarro);
                        lsText.append("        lbl"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setText(\""+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "\");");lsText.append(JUtiles.msRetornoCarro);
                        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
                            lsText.append("        jPanelGENERAL.add(lbl"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +", new java.awt.GridBagConstraints());");lsText.append(JUtiles.msRetornoCarro);
                        } else {
                            lsText.append("        add(lbl"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +", new java.awt.GridBagConstraints());");lsText.append(JUtiles.msRetornoCarro);
                        }
                        //textos
                        if(loCampos.get(i).getTamano() > 99999) { //En este caso es memo
                            lsText.append("        jScroll"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +".setViewportView(txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +");");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.weightx = 0.1;");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.weighty = 0.1;");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);");lsText.append(JUtiles.msRetornoCarro);
                        } else {
                            lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.weightx = 0.1;");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);");lsText.append(JUtiles.msRetornoCarro);
                        }
                        

                        if(loCampos.get(i).getTamano() > 99999) { //En este caso es memo
                            if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
                                lsText.append("        jPanelGENERAL.add(jScroll"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                            } else {
                                lsText.append("        add(jScroll"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                            }
                        } else {
                            if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
                                lsText.append("        jPanelGENERAL.add(txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                            } else {
                                lsText.append("        add(txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                            }
                        }
                        break;
                }
            }else {
                //Introduce paneles de busqueda
                lsText.append("        jPanel"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+".setLabel(\""+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"\");");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        gridBagConstraints.weightx = 0.1;");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);");lsText.append(JUtiles.msRetornoCarro);
                if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
                    lsText.append("        jPanelGENERAL.add(jPanel"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                } else {
                    lsText.append("        add(jPanel"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        
        lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.weightx = 0.1;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        gridBagConstraints.weighty = 0.1;");lsText.append(JUtiles.msRetornoCarro);
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("        jPanelGENERAL.add(jPanelEspaciador, gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
        } else {
            lsText.append("        add(jPanelEspaciador, gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
        }

        
        lsText.append(JUtiles.msRetornoCarro);
        
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("        jTabbedPane1.addTab(\"GENERAL\", jPanelGENERAL);");lsText.append(JUtiles.msRetornoCarro);
        }
        
        //Para cada relacion de exportacion
        if(mbRelacionesExport) {
            for(int i=0;i<loTabla.getRelaciones().count();i++) {
                JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionExport) { //Si es una relacion de exportacion
                    lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        gridBagConstraints.weightx = 0.1;");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        gridBagConstraints.weighty = 0.1;");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        jPanel" + moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado) + ".add(jPanelGenerico" + moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado) + ", gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        jTabbedPane1.addTab(\"" + moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado) + "\", jPanel" + moConex.getNomTablaComplejo(i,JConexionGeneradorClass.mcnTipoRelacionado) + ");");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        
        lsText.append(JUtiles.msRetornoCarro);
        
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("        gridBagConstraints = new java.awt.GridBagConstraints();");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        gridBagConstraints.weightx = 0.1;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        gridBagConstraints.weighty = 0.1;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        add(jTabbedPane1, gridBagConstraints);");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//"+ invertir("DNE-NEG") +":initComponents");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//"+ invertir("NIGEB-NEG") +":variables");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    // Declaracion de controles No modificar!!");lsText.append(JUtiles.msRetornoCarro);
        
        //Introduce los paneles de busqueda en una lista inicial
        
//        loPaneles = new JListaElementos();
//        for(int i=0;i<loTabla.getRelaciones().count();i++) {
//            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(i);
//            if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importacion
//                loPaneles.add(loRelacion.getCampoPropio(0));
//            }
//        }
        
        //Introduce el resto de controles 
        
        if(moProyecto.getOpciones().getIncluirTabForm() || mbRelacionesExport) {
            lsText.append("    private javax.swing.JTabbedPane jTabbedPane1;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("    private javax.swing.JPanel jPanelGENERAL;");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("    private javax.swing.JPanel jPanelEspaciador;");lsText.append(JUtiles.msRetornoCarro);
        
        //Anade una pestana por cada relacion de exportacion
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
                    lsText.append("    private javax.swing.JPanel jPanel" + moUtiles.msSustituirRaros(lsNomTabla) + ";");lsText.append(JUtiles.msRetornoCarro);
                }
            }
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
                    
                    lsText.append("    private utilesGUIx.formsGenericos.JPanelGenerico jPanelGenerico" + moUtiles.msSustituirRaros(lsNomTabla) + ";");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }
        
        for(int i = 0; i < loCampos.count(); i++ ){
            if(!comprueboPanel(loCampos.get(i).getNombre(),loPaneles)) { //Si no es un panel
                switch(loCampos.get(i).getTipo()) {
                    case JListDatos.mclTipoBoolean:
                        lsText.append("    private utilesGUIx.JCheckBoxCZ jCheck"+moUtiles.msSustituirRaros(loCampos.get(i).getNombre())+";");lsText.append(JUtiles.msRetornoCarro);
                        break;
                    default:
                        //labels
                        lsText.append("    private utilesGUIx.JLabelCZ lbl"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
                        //textos
                        if(loCampos.get(i).getTamano() > 99999) { //En este caso es memo
                            lsText.append("    private javax.swing.JScrollPane jScroll"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
                            lsText.append("    private utilesGUIx.JTextAreaCZ txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
                        } else {
                            lsText.append("    private utilesGUIx.JTextFieldCZ txt"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
                        }
                        break;
                }
            }else {
                //Introduce paneles de busqueda
                lsText.append("    private utilesGUIx.panelesGenericos.JPanelBusqueda jPanel"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +";");lsText.append(JUtiles.msRetornoCarro);
            }
        }

        lsText.append("    // Fin de la declaracion de controles");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//"+ invertir("DNE-NEG") +":variables");lsText.append(JUtiles.msRetornoCarro);
        
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
        return "JPanel.java";
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
