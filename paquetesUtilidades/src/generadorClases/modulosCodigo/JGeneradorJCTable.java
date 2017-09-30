/*
 * JGeneradorJTable.java
 *
 * Created on 5 de agosto de 2005, 17:38
 *
 */

package generadorClases.modulosCodigo;

import generadorClases.*;
import utiles.*;
import ListDatos.estructuraBD.*;

public class JGeneradorJCTable implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    public static int mclTipo = 0;
    private JProyecto moProyecto;
    private final JUtiles moUtiles;
    /**
     * Creates a new instance of JGeneradorJTable
     */
    public JGeneradorJCTable(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    
    public String getCodigo() {
        return getCodigo(moConex.getTablaBD(moConex.getTablaActual()), moConex.getDirPadre(), moConex, moProyecto);
    }
    public static String getCodigo(JTableDef poTabla, String psPaqueteRaiz, JConexionGeneradorClass poConexSiRelac, JProyecto poProyec) {
        JUtiles loUtiles = new JUtiles(poProyec);

        StringBuffer lsText = new StringBuffer(100);
        boolean mbRelacionesExport = false;
        JFieldDefs loCampos = poTabla.getCampos();
        String lsNombreTablaSinRaros = loUtiles.msSustituirRaros(poTabla.getNombre());
        if(poTabla.getRelaciones().count() > 0) {
            mbRelacionesExport = true;
        }
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JC" + lsNombreTablaSinRaros + ".java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + psPaqueteRaiz + "." + "consultas;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.estructuraBD.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.busqueda.IConsulta;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import " + psPaqueteRaiz + ".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        if(mclTipo==0){
            lsText.append("import " + psPaqueteRaiz + "."+loUtiles.getPaqueteExtend()+".*;");lsText.append(JUtiles.msRetornoCarro);
        }else{
            lsText.append("import " + psPaqueteRaiz + ".tablas2.*;");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append(JUtiles.msRetornoCarro);
        //**************************************************
        
        //CUERPO DE LA CLASE*******************************************
        lsText.append("public class JTFORM"+ lsNombreTablaSinRaros+" extends JSTabla  implements IConsulta {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static final long serialVersionUID = 1L;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private JSelect moSelect;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private final static JSelect moSelectEstatica;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private final static JListDatos moListDatosEstatico;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static final String msTablaPrincipal;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     * Variables para las posiciones de los campos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     */");lsText.append(JUtiles.msRetornoCarro);
        
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("    public static final int lPosi" + loUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + ";");lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static final int mclNumeroCampos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if (poCampo.getTabla().equals(msTablaPrincipal)) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } else {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            return addCampo(psNombreTabla, poCampo, false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return addCampo(psNombreTabla, poCampo, pbEsPrincipal, JSelectCampo.mclFuncionNada, false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal, final int plFuncion, final boolean pbAddAgrupado){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return JUtilTabla.addCampo(moSelectEstatica,moListDatosEstatico,psNombreTabla, poCampo, pbEsPrincipal, plFuncion, pbAddAgrupado);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static JFieldDef addCampoFuncion(final String psNombreTabla, final JFieldDef poCampo, final int plFuncion){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return addCampo(psNombreTabla, poCampo, false, plFuncion, false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static JFieldDef addCampoYGrupo(final String psNombreTabla, final JFieldDef poCampo){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return addCampo(psNombreTabla, poCampo, false, JSelectCampo.mclFuncionNada, true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    private static JFieldDef addCampoLibre(final String psNombreCampo, final int pnTipoCampo) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JFieldDef loCampo = new JFieldDef(psNombreCampo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loCampo.setTipo(pnTipoCampo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loCampo.setPrincipalSN(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moListDatosEstatico.getFields().addField(loCampo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //moSelectEstatica.addCampo(psNombreCampo);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loCampo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    static{");lsText.append(JUtiles.msRetornoCarro);
       
        
        lsText.append("        msTablaPrincipal = "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros) +".msCTabla;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moListDatosEstatico = new JListDatos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moListDatosEstatico.msTabla = msTablaPrincipal;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moSelectEstatica = new JSelect(msTablaPrincipal);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros) +" lo"+lsNombreTablaSinRaros+" = new "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+"(null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        int lPosi = 0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("        addCampo("+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros) +".msCTabla, lo"+lsNombreTablaSinRaros+".moList.getFields("+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".lPosi"+loUtiles.msSustituirRaros(loCampos.get(i).getNombre())+"));");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        lPosi"+loUtiles.msSustituirRaros(loCampos.get(i).getNombre())+" = lPosi;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("        lPosi++;");lsText.append(JUtiles.msRetornoCarro);
            lsText.append(JUtiles.msRetornoCarro);
        }
        lsText.append("        mclNumeroCampos=lPosi;");
        lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    };");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static JFieldDef getFieldEstatico(final int plPosi){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moListDatosEstatico.getFields(plPosi);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("    public static JFieldDefs getFieldsEstaticos(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moListDatosEstatico.getFields();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public static String getNombreTabla(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return msTablaPrincipal;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     /**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      * Crea una instancia de la clase intermedia para la tabla " + poTabla.getNombre() + "incluyendole el servidor de datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("      */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JTFORM"+ lsNombreTablaSinRaros+"(IServerServidorDatos poServidorDatos) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moList = JUtilTabla.clonarFilasListDatos(moListDatosEstatico, true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (Exception ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            utiles.JDepuracion.anadirTexto(getClass().getName(), ex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList.moServidor = poServidorDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList.addListener(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("     public boolean getPasarCache(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return (moList.moServidor.getEnCache(moSelect.toString())!=null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JFieldDef getField(final int plPosi){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moList.getFields(plPosi);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JSelect getSelect(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return moSelect;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("    private void cargar(final IFilaDatos poFila) throws Exception{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //creamos el filtro por los campo principales");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        String[] lasValores = new String["+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".malCamposPrincipales.length];");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        for(int i = 0 ; i < "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".malCamposPrincipales.length; i++){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            lasValores[i] = poFila.msCampo("+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".malCamposPrincipales[i]);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JListDatosFiltroElem loFiltro = ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            new JListDatosFiltroElem(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  JListDatos.mclTIgual, ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".malCamposPrincipales,");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  lasValores");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            );");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loFiltro.inicializar("+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".msCTabla, "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".malTipos, "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+".masNombres);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //creamos un objeto consulta con la select simple");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JTFORM"+ lsNombreTablaSinRaros+" loCons = new JTFORM"+ lsNombreTablaSinRaros+"(moList.moServidor);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loCons.crearSelectSimple();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //aNadimos la condicion de los campos principales");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //refrescamos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loCons.refrescar(false, false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        //cargamos los datos ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loCons.moList.moveFirst()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moList.getFields().cargar(loCons.moList.moFila());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }else{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            throw new Exception(JTFORM"+ lsNombreTablaSinRaros+".class.getName() + \"->cargar = No existe el registro de la tabla \" + "+loUtiles.getNombreTablaExtends(lsNombreTablaSinRaros)+ ".msCTabla);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void addFilaPorClave(final IFilaDatos poFila) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        switch(poFila.getTipoModif()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            case JListDatos.mclBorrar:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moList.borrar(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                break;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            case JListDatos.mclEditar:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                cargar(poFila);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moList.update(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                break;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            case JListDatos.mclNuevo:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moList.addNew();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                cargar(poFila);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                moList.update(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                break;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            default:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                throw new Exception(\"Tipo modificaciOn incorrecto\");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void refrescar(final boolean pbPasarACache, final boolean pbLimpiarCache) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        moList.recuperarDatos(moSelect, getPasarCache(), JListDatos.mclSelectNormal, pbLimpiarCache);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("    public JListDatos getList() {");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("        return moList;");lsText.append(JUtiles.msRetornoCarro);
//        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        
        
        lsText.append("    public void crearSelectSimple(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            moSelect = (JSelect)moSelectEstatica.clone();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        } catch (CloneNotSupportedException ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            ex.printStackTrace();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);


        StringBuffer lsAux = new StringBuffer();
        lsAux.append("    public void crearSelect(String psTabla, IFilaDatos poFilaDatosRelac){");lsAux.append(JUtiles.msRetornoCarro);
        lsAux.append("        crearSelectSimple();");lsAux.append(JUtiles.msRetornoCarro);
        lsAux.append("        if(psTabla!=null){");lsAux.append(JUtiles.msRetornoCarro);
        if(mbRelacionesExport) {
            for(int j=0;j<poTabla.getRelaciones().count();j++) {
                JRelacionesDef loRelacion = poTabla.getRelaciones().getRelacion(j);
                if(loRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) { //Si es una relacion de importaciOn
                    String lsCampo = loUtiles.msSustituirRaros(poTabla.getRelaciones().getRelacion(j).getCampoPropio(0));
                    String lsTabla = loUtiles.msSustituirRaros(poConexSiRelac.getNomTablaSimple(j));
                    
                    lsAux.append("        if(psTabla.equalsIgnoreCase(JT"+ lsTabla +".msCTabla)){");lsAux.append(JUtiles.msRetornoCarro);
                    lsAux.append("            crearSelect"+lsTabla +"_"+lsCampo+"(");lsAux.append(JUtiles.msRetornoCarro);

                    //Para cada tabla creamos una select
                    lsText.append("    public void crearSelect"+ lsTabla +"_"+lsCampo + "(");lsText.append(JUtiles.msRetornoCarro);
                    //Para cada campo de la relacion aNadimos un parametro
                    int i=0;
                    for(int k=0;k < poTabla.getRelaciones().getRelacion(j).getCamposRelacionCount();k++) {
                        String lsNomCampo = loUtiles.msSustituirRaros(loRelacion.getCampoPropio(k));
                        String lsNomCampoExt = loUtiles.msSustituirRaros(loRelacion.getCampoRelacion(k));
                        if(k == poTabla.getRelaciones().getRelacion(j).getCamposRelacionCount() -1 ) {
                            lsText.append("            String ps" + lsNomCampo);lsText.append(JUtiles.msRetornoCarro);
                            lsAux.append("              poFilaDatosRelac.msCampo(JT"+poConexSiRelac.getNomTablaSimple(j)+".lPosi"+lsNomCampoExt+"));");lsAux.append(JUtiles.msRetornoCarro);
                        }else {
                            lsText.append("            String ps" + lsNomCampo + ",");lsText.append(JUtiles.msRetornoCarro);
                            lsAux.append("              poFilaDatosRelac.msCampo(JT"+poConexSiRelac.getNomTablaSimple(j)+".lPosi"+lsNomCampoExt+"),");lsAux.append(JUtiles.msRetornoCarro);
                        }
                    }

                    lsText.append("        ) {");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        crearSelectSimple();");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();");lsText.append(JUtiles.msRetornoCarro);
                    for(int k=0;k < poTabla.getRelaciones().getRelacion(j).getCamposRelacionCount();k++) {
                        String lsNomCampo = loUtiles.msSustituirRaros(loRelacion.getCampoPropio(k));
                        lsText.append("        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual,JT"+ lsNombreTablaSinRaros+".lPosi" + lsNomCampo + ", ps" + lsNomCampo + ");");lsText.append(JUtiles.msRetornoCarro);
                    }

                    lsText.append("        loFiltro.inicializar(JT"+ lsNombreTablaSinRaros+".msCTabla, JT"+ lsNombreTablaSinRaros+".malTipos, JT"+ lsNombreTablaSinRaros+".masNombres);");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("        moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("");lsText.append(JUtiles.msRetornoCarro);

                    lsAux.append("        }");lsAux.append(JUtiles.msRetornoCarro);
                }
            }
        }
        lsAux.append("        }");lsAux.append(JUtiles.msRetornoCarro);
        lsAux.append("    }");lsAux.append(JUtiles.msRetornoCarro);
        lsText.append(lsAux);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);


        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return "consultas";
    }

    public String getNombre() {
        return "JTFORM"+ moUtiles.msSustituirRaros(moConex.getTablaActual())+".java";
    }

    public boolean isGeneral() {
        return false;
    }
    public String getNombreModulo() {
        return "JConsulta.java";
    }
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );;
        if(loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
            loParam.mbGenerar=false;
        }
        return loParam;
    }

}
