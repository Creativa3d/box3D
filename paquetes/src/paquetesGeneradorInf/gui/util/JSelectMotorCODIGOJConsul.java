package paquetesGeneradorInf.gui.util;

import ListDatos.*;
import ListDatos.estructuraBD.*;


import java.util.HashMap;
import java.util.Iterator;
import utiles.*;

/**
 * Construye select de distintas bases de datos a partir del Objeto JSelect
 */
public class JSelectMotorCODIGOJConsul extends JSelectMotorCODIGO {

    private static final long serialVersionUID = 33333319L;

    private StringBuilder msJListDatos;
    private JListaElementos moCampos;
    private String msTabla;

    /**
     *Contructor
     */
    public JSelectMotorCODIGOJConsul() {
        super();
    }

    private String getTabla(String psTabla, String psAlias){
        if(psAlias==null || psAlias.equals("")){
            return "JT"+msSustituirRaros(psTabla)+".msCTabla";
        }else{
            return "\""+psAlias+"\"";
        }

    }

    public String msTabla(final String psTabla, final String psTablaAlias) {
        msTabla = psTabla;
        return super.msTabla(psTabla, psTablaAlias);
    }

    public String msListaCampos(final int plTipo, final JListaElementos poCampos) {

        moCampos = poCampos;
        return super.msListaCampos(plTipo, poCampos);
        
    }


    public String msSelect(final String psCampos, final String psFrom, final String psWhere, final String psGroup, final String psHaving, final String psOrder) {
        JTableDef loDefs = new JTableDef(msTabla);
        Iterator loEnum = moCampos.iterator();
        for (; loEnum.hasNext();) {
            JSelectCampo loCampo = (JSelectCampo) loEnum.next();
            JFieldDef loCampoDef = new JFieldDef(loCampo.getNombre());
            loCampoDef.setTabla(loCampo.getTabla());
            loDefs.getCampos().addField(loCampoDef);
        }
        
        return getCodigo(loDefs, "");
    }

    public static String getCodigo(JTableDef poTabla, String psPaqueteRaiz) {

        StringBuilder lsText = new StringBuilder(100);
        boolean mbRelacionesExport = false;
        JFieldDefs loCampos = poTabla.getCampos();
        String lsNombreTablaSinRaros = msSustituirRaros(poTabla.getNombre());
        if(poTabla.getRelaciones().count() > 0) {
            mbRelacionesExport = true;
        }
        
        //CABECERA*********************************
        JDateEdu loDate = new JDateEdu();
        lsText.append("/*");lsText.append('\n');
        lsText.append("* JC" + lsNombreTablaSinRaros + ".java");lsText.append('\n');
        lsText.append("*");lsText.append('\n');
        lsText.append("* Creado el " + loDate.getDia() + "/" + loDate.getMes() + "/" + loDate.getAno());lsText.append('\n');
        lsText.append("*/");lsText.append('\n');
        //*****************************************
        
        //IMPORTACION***************************************
        lsText.append("package " + psPaqueteRaiz + "." + "consultas;");lsText.append('\n');
        lsText.append('\n');
        lsText.append("import ListDatos.*;");lsText.append('\n');
        lsText.append("import ListDatos.estructuraBD.*;");lsText.append('\n');
        lsText.append("import utilesGUIx.formsGenericos.busqueda.IConsulta;");lsText.append('\n');
        lsText.append("import " + psPaqueteRaiz + ".tablas.*;");lsText.append('\n');
        lsText.append("import " + psPaqueteRaiz + ".tablasExtend.*;");lsText.append('\n');
        lsText.append('\n');
        //**************************************************
        
        //CUERPO DE LA CLASE*******************************************
        lsText.append("public class JTFORM"+ lsNombreTablaSinRaros+" extends JSTabla  implements IConsulta {");lsText.append('\n');
        lsText.append("    private static final long serialVersionUID = 1L;");lsText.append('\n');
        lsText.append("    private JSelect moSelect;");lsText.append('\n');
        lsText.append("    private final static JSelect moSelectEstatica;");lsText.append('\n');
        lsText.append("    private final static JListDatos moListDatosEstatico;");lsText.append('\n');
        lsText.append("    private static final String msTablaPrincipal;");lsText.append('\n');
        lsText.append("    ");lsText.append('\n');
        lsText.append("    /**");lsText.append('\n');
        lsText.append("     * Variables para las posiciones de los campos");lsText.append('\n');
        lsText.append("     */");lsText.append('\n');
        
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("    public static final int lPosi" + msSustituirRaros(loCampos.get(i).getNombre()) + ";");lsText.append('\n');
        }
        lsText.append('\n');
        lsText.append("    public static final int mclNumeroCampos;");lsText.append('\n');
        lsText.append('\n');
        lsText.append('\n');
        lsText.append("    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo){");lsText.append('\n');
        lsText.append("        if (poCampo.getTabla().equals(msTablaPrincipal)) {");lsText.append('\n');
        lsText.append("            return addCampo(psNombreTabla, poCampo, poCampo.getPrincipalSN());");lsText.append('\n');
        lsText.append("        } else {");lsText.append('\n');
        lsText.append("            return addCampo(psNombreTabla, poCampo, false);");lsText.append('\n');
        lsText.append("        }");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("");lsText.append('\n');
        lsText.append("    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal){");lsText.append('\n');
        lsText.append("        return addCampo(psNombreTabla, poCampo, pbEsPrincipal, JSelectCampo.mclFuncionNada, false);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("");lsText.append('\n');
        lsText.append("    private static JFieldDef addCampo(final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal, final int plFuncion, final boolean pbAddAgrupado){");lsText.append('\n');
        lsText.append("        return JUtilTabla.addCampo(moSelectEstatica,moListDatosEstatico,psNombreTabla, poCampo, pbEsPrincipal, plFuncion, pbAddAgrupado);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("");lsText.append('\n');
        lsText.append("    private static JFieldDef addCampoFuncion(final String psNombreTabla, final JFieldDef poCampo, final int plFuncion){");lsText.append('\n');
        lsText.append("        return addCampo(psNombreTabla, poCampo, false, plFuncion, false);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("    private static JFieldDef addCampoYGrupo(final String psNombreTabla, final JFieldDef poCampo){");lsText.append('\n');
        lsText.append("        return addCampo(psNombreTabla, poCampo, false, JSelectCampo.mclFuncionNada, true);");lsText.append('\n');
        lsText.append("    }        ");lsText.append('\n');
        lsText.append("    private static JFieldDef addCampoLibre(final String psNombreCampo, final int pnTipoCampo) {");lsText.append('\n');
        lsText.append("        JFieldDef loCampo = new JFieldDef(psNombreCampo);");lsText.append('\n');
        lsText.append("        loCampo.setTipo(pnTipoCampo);");lsText.append('\n');
        lsText.append("        loCampo.setPrincipalSN(false);");lsText.append('\n');
        lsText.append("        moListDatosEstatico.getFields().addField(loCampo);");lsText.append('\n');
        lsText.append("        //moSelectEstatica.addCampo(psNombreCampo);");lsText.append('\n');
        lsText.append("        return loCampo;");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("    static{");lsText.append('\n');
       
        String lsPrefijo;
        String lsSufijo;
        lsPrefijo = "JTEE";
        lsSufijo = "";
        lsText.append("        msTablaPrincipal = "+lsPrefijo+lsNombreTablaSinRaros+lsSufijo +".msCTabla;");lsText.append('\n');
        lsText.append("        moListDatosEstatico = new JListDatos();");lsText.append('\n');
        lsText.append("        moListDatosEstatico.msTabla = msTablaPrincipal;");lsText.append('\n');
        lsText.append("        moSelectEstatica = new JSelect(msTablaPrincipal);");lsText.append('\n');
        lsText.append('\n');
        lsText.append("        "+lsPrefijo+lsNombreTablaSinRaros+lsSufijo +" lo"+lsNombreTablaSinRaros+" = new "+lsPrefijo+lsNombreTablaSinRaros+lsSufijo+"(null);");lsText.append('\n');
        HashMap loMap = new HashMap();
        loMap.put(poTabla.getNombre(), "1");
        //lista tablas diferentes
        for(int i = 0; i < loCampos.count(); i++ ){
            if(loMap.get(loCampos.get(i).getTabla())==null && !JCadenas.isVacio(loCampos.get(i).getTabla()) ){
                loMap.put(loCampos.get(i).getTabla(), "1");
                String lsTablaAuxSinRaros = msSustituirRaros(loCampos.get(i).getTabla());
                lsText.append("        "+lsPrefijo+lsTablaAuxSinRaros+lsSufijo +" lo"+lsTablaAuxSinRaros+" = new "+lsPrefijo+lsTablaAuxSinRaros+lsSufijo+"(null);");lsText.append('\n');
            }
        }
        
        lsText.append("        int lPosi = 0;");lsText.append('\n');
        lsText.append('\n');
        for(int i = 0; i < loCampos.count(); i++ ){
            String lsTablaAuxSinRaros;
            if(JCadenas.isVacio(loCampos.get(i).getTabla())){
                lsTablaAuxSinRaros = lsNombreTablaSinRaros;
            }else{
                lsTablaAuxSinRaros = msSustituirRaros(loCampos.get(i).getTabla());
            }
            lsText.append("        addCampo(lo"+ lsTablaAuxSinRaros +lsSufijo+".msCTabla, lo"+lsTablaAuxSinRaros+".moList.getFields(lo"+lsTablaAuxSinRaros+lsSufijo+".lPosi"+msSustituirRaros(loCampos.get(i).getNombre())+"));");lsText.append('\n');
            lsText.append("        lPosi"+msSustituirRaros(loCampos.get(i).getNombre())+" = lPosi;");lsText.append('\n');
            lsText.append("        lPosi++;");lsText.append('\n');
            lsText.append('\n');
        }
        lsText.append("        mclNumeroCampos=lPosi;");
        lsText.append('\n');
        
        lsText.append("    };");lsText.append('\n');
        lsText.append('\n');
        lsText.append("    public static JFieldDef getFieldEstatico(final int plPosi){");lsText.append('\n');
        lsText.append("        return moListDatosEstatico.getFields(plPosi);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        
        lsText.append("    public static JFieldDefs getFieldsEstaticos(){");lsText.append('\n');
        lsText.append("        return moListDatosEstatico.getFields();");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("    public static String getNombreTabla(){");lsText.append('\n');
        lsText.append("        return msTablaPrincipal;");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        
        lsText.append('\n');
        lsText.append("     /**");lsText.append('\n');
        lsText.append("      * Crea una instancia de la clase intermedia para la tabla " + poTabla.getNombre() + "incluyendole el servidor de datos");lsText.append('\n');
        lsText.append("      */");lsText.append('\n');
        lsText.append("    public JTFORM"+ lsNombreTablaSinRaros+"(IServerServidorDatos poServidorDatos) {");lsText.append('\n');
        lsText.append("        super();");lsText.append('\n');
        lsText.append("        try {");lsText.append('\n');
        lsText.append("            moList = JUtilTabla.clonarFilasListDatos(moListDatosEstatico, true);");lsText.append('\n');
        lsText.append("        } catch (Exception ex) {");lsText.append('\n');
        lsText.append("            utiles.JDepuracion.anadirTexto(getClass().getName(), ex);");lsText.append('\n');
        lsText.append("        }");lsText.append('\n');
        lsText.append("        moList.moServidor = poServidorDatos;");lsText.append('\n');
        lsText.append("        moList.addListener(this);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("     public boolean getPasarCache(){");lsText.append('\n');
        lsText.append("        return (moList.moServidor.getEnCache(moSelect.toString())!=null);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("");lsText.append('\n');
        lsText.append("    public JFieldDef getField(final int plPosi){");lsText.append('\n');
        lsText.append("        return moList.getFields(plPosi);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("    public JSelect getSelect(){");lsText.append('\n');
        lsText.append("        return moSelect;");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');

        lsText.append("    private void cargar(final IFilaDatos poFila) throws Exception{");lsText.append('\n');
        lsText.append("        //creamos el filtro por los campo principales");lsText.append('\n');
        lsText.append("        String[] lasValores = new String[JTEE"+lsNombreTablaSinRaros+".malCamposPrincipales.length];");lsText.append('\n');
        lsText.append("        for(int i = 0 ; i < JTEE"+lsNombreTablaSinRaros+".malCamposPrincipales.length; i++){");lsText.append('\n');
        lsText.append("            lasValores[i] = poFila.msCampo(JTEE"+lsNombreTablaSinRaros+".malCamposPrincipales[i]);");lsText.append('\n');
        lsText.append("        }");lsText.append('\n');
        lsText.append("        JListDatosFiltroElem loFiltro = ");lsText.append('\n');
        lsText.append("            new JListDatosFiltroElem(");lsText.append('\n');
        lsText.append("                  JListDatos.mclTIgual, ");lsText.append('\n');
        lsText.append("                  JTEE"+lsNombreTablaSinRaros+".malCamposPrincipales,");lsText.append('\n');
        lsText.append("                  lasValores");lsText.append('\n');
        lsText.append("            );");lsText.append('\n');
        lsText.append("        loFiltro.inicializar(JTEE"+lsNombreTablaSinRaros+".msCTabla, JTEE"+lsNombreTablaSinRaros+".malTipos, JTEE"+lsNombreTablaSinRaros+".masNombres);");lsText.append('\n');
        lsText.append("        //creamos un objeto consulta con la select simple");lsText.append('\n');
        lsText.append("        JTFORM"+ lsNombreTablaSinRaros+" loCons = new JTFORM"+ lsNombreTablaSinRaros+"(moList.moServidor);");lsText.append('\n');
        lsText.append("        loCons.crearSelectSimple();");lsText.append('\n');
        lsText.append("        //aNadimos la condicion de los campos principales");lsText.append('\n');
        lsText.append("        loCons.moSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);");lsText.append('\n');
        lsText.append("        //refrescamos");lsText.append('\n');
        lsText.append("        loCons.refrescar(false, false);");lsText.append('\n');
        lsText.append("        //cargamos los datos ");lsText.append('\n');
        lsText.append("        if(loCons.moList.moveFirst()){");lsText.append('\n');
        lsText.append("            moList.getFields().cargar(loCons.moList.moFila());");lsText.append('\n');
        lsText.append("        }else{");lsText.append('\n');
        lsText.append("            throw new Exception(JTFORM"+ lsNombreTablaSinRaros+".class.getName() + \"->cargar = No existe el registro de la tabla \" + JTEE"+ lsNombreTablaSinRaros+ ".msCTabla);");lsText.append('\n');
        lsText.append("        }");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("");lsText.append('\n');
        lsText.append("    public void addFilaPorClave(final IFilaDatos poFila) throws Exception {");lsText.append('\n');
        lsText.append("        switch(poFila.getTipoModif()){");lsText.append('\n');
        lsText.append("            case JListDatos.mclBorrar:");lsText.append('\n');
        lsText.append("                moList.borrar(false);");lsText.append('\n');
        lsText.append("                break;");lsText.append('\n');
        lsText.append("            case JListDatos.mclEditar:");lsText.append('\n');
        lsText.append("                cargar(poFila);");lsText.append('\n');
        lsText.append("                moList.update(false);");lsText.append('\n');
        lsText.append("                break;");lsText.append('\n');
        lsText.append("            case JListDatos.mclNuevo:");lsText.append('\n');
        lsText.append("                moList.addNew();");lsText.append('\n');
        lsText.append("                cargar(poFila);");lsText.append('\n');
        lsText.append("                moList.update(false);");lsText.append('\n');
        lsText.append("                break;");lsText.append('\n');
        lsText.append("            default:");lsText.append('\n');
        lsText.append("                throw new Exception(\"Tipo modificaciOn incorrecto\");");lsText.append('\n');
        lsText.append("        }");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("");lsText.append('\n');
        lsText.append("    public void refrescar(final boolean pbPasarACache, final boolean pbLimpiarCache) throws Exception {");lsText.append('\n');
        lsText.append("        moList.recuperarDatos(moSelect, getPasarCache(), JListDatos.mclSelectNormal, pbLimpiarCache);");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');
        lsText.append("");lsText.append('\n');
//        lsText.append("    public JListDatos getList() {");lsText.append('\n');
//        lsText.append("        return moList;");lsText.append('\n');
//        lsText.append("    }");lsText.append('\n');
        
        
        lsText.append("    public void crearSelectSimple(){");lsText.append('\n');
        lsText.append("        try {");lsText.append('\n');
        lsText.append("            moSelect = (JSelect)moSelectEstatica.clone();");lsText.append('\n');
        lsText.append("        } catch (CloneNotSupportedException ex) {");lsText.append('\n');
        lsText.append("            ex.printStackTrace();");lsText.append('\n');
        lsText.append("        }");lsText.append('\n');
        lsText.append("    }");lsText.append('\n');


        StringBuilder lsAux = new StringBuilder();
        lsAux.append("    public void crearSelect(String psTabla, IFilaDatos poFilaDatosRelac){");lsAux.append('\n');
        lsAux.append("        crearSelectSimple();");lsAux.append('\n');
        lsAux.append("        if(psTabla!=null){");lsAux.append('\n');
        lsAux.append("        }");lsAux.append('\n');
        lsAux.append("    }");lsAux.append('\n');
        lsText.append(lsAux);
        lsText.append("}");lsText.append('\n');


        return lsText.toString();        
    }

}
