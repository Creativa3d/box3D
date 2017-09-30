package paquetesGeneradorInf.gui.util;

import ListDatos.*;
import ListDatos.estructuraBD.*;


import java.io.*;
import java.sql.PreparedStatement;
import java.util.Iterator;
import utiles.*;

/**
 * Construye select de distintas bases de datos a partir del Objeto JSelect
 */
public class JSelectMotorCODIGO implements ISelectMotor, Serializable {

    private static final long serialVersionUID = 33333319L;
    private final int mclCampoOrden = 1;
    private final int mclCampoGroup = 2;
    private final int mclCampoSelect = 0;
    

    private StringBuilder msJListDatos;

    /**
     *Contructor
     */
    public JSelectMotorCODIGO() {
        super();
    }


    public String msAnd(final String psCond1, final String psCond2) {
        return "new JListDatosFiltroConj().addCondicionAND("+psCond1+")\n\t.addCondicionAND("+psCond2+")\n";
    }

    public String msOr(final String psCond1, final String psCond2) {
        return "new JListDatosFiltroConj().addCondicionOR("+psCond1+")\n\t.addCondicionOR("+psCond2+")\n";
    }

    public String msNot(final String psCond1) {
        return "new JListDatosFiltroNOT("+psCond1+")\n\t\n";
    }

    private String msTrue() {
        String lsTrue = "JListDatos.mcsTrue";
        return lsTrue;
    }

    private String msFalse() {
        String lsFalse = "JListDatos.mcsFalse";
        return lsFalse;
    }

    public String msCondicion(final String psCampo, final int plCond, final String psValor, final String psCampo2, final int plTipo) {
        StringBuilder lsCadena = new StringBuilder();
        if (psCampo2 == null) {
            String lsTabla = psCampo.substring(2,psCampo.indexOf('.'));
            String lsCampo = psCampo.substring(psCampo.indexOf('.')+4,psCampo.indexOf("Nombre()"));
            lsCadena.append("\n\tnew JListDatosFiltroElem("+msOperador(plCond)
                    + ", new String[]{"+psCampo+"}"
                    + ", new String[]{\""+(psValor == null ? "" : psValor)+"\"}"
                    + ", new int[]{JT"+lsTabla+".malTipos[JT"+lsTabla+ ".lPosi" + lsCampo + "]}"
                    + ")"
                );
        } else {
            lsCadena.append("new JListDatosFiltroElemCampos("+msOperador(plCond)+", "+psCampo+", "+psCampo2+")");
        }
        return lsCadena.toString();
    }


    private String msOperador(final int plOperador) {
        String lsCadena = "";
        switch (plOperador) {
            case JListDatos.mclTDistinto:
                lsCadena = " JListDatos.mclTDistinto ";
                break;
            case JListDatos.mclTIgual:
                lsCadena = " JListDatos.mclTIgual ";
                break;
            case JListDatos.mclTMayor:
                lsCadena = " JListDatos.mclTMayor ";
                break;
            case JListDatos.mclTMayorIgual:
                lsCadena = " JListDatos.mclTMayorIgual ";
                break;
            case JListDatos.mclTMenor:
                lsCadena = " JListDatos.mclTMenor ";
                break;
            case JListDatos.mclTMenorIgual:
                lsCadena = " JListDatos.mclTMenorIgual ";
                break;
            case JListDatos.mclTLike:
                lsCadena = " JListDatos.mclTLike ";
                break;
            default:
                lsCadena = "";

        }
        return lsCadena;

    }
    private String getTabla(String psTabla, String psAlias){
        if(psAlias==null || psAlias.equals("")){
            return "JT"+msSustituirRaros(psTabla)+".msCTabla";
        }else{
            return "\""+psAlias+"\"";
        }

    }

    public JSelectFromParte msFromUnion(final JSelectFromParte psParte1, final JSelectUnionTablas poUnion) {

        StringBuilder lsCadena = new StringBuilder();
        lsCadena.append("loSelect.getFrom().addTabla(new JSelectUnionTablas(\n");
        
        
        if(poUnion.getCampos1() == null || poUnion.getCampos1().length==0){
            lsCadena.append(getTabla(poUnion.getTabla2(),null)+",  \""+(JCadenas.isVacio( poUnion.getTabla2Alias()) ? "" : poUnion.getTabla2Alias())+" \" ))\n");
        }else{
            String lsClave = "";
            switch (poUnion.getTipo()) {
                case JSelectUnionTablas.mclInner:
                    lsClave = " JSelectUnionTablas.mclInner ";
                    break;
                case JSelectUnionTablas.mclLeft:
                    lsClave = " JSelectUnionTablas.mclLeft ";
                    break;
                case JSelectUnionTablas.mclRight:
                    lsClave = " JSelectUnionTablas.mclRight ";
                    break;
                default:
                    lsClave = "";
            }
            
            lsCadena.append(lsClave+""
                    + ", "+getTabla(poUnion.getTablaPrefijoCampos1(),null)
                    + ", "+getTabla(poUnion.getTabla2(),null)
                    + ", \""+(JCadenas.isVacio( poUnion.getTabla2Alias()) ? "" : poUnion.getTabla2Alias())+"\"\n"
                );
            
            StringBuilder lsCampos1= new StringBuilder(", new String[]{");
            StringBuilder lsCampos2= new StringBuilder(", new String[]{");
            
            for (int i = 0; i < poUnion.getCampos1().length; i++) {
                
                lsCampos1.append(getCampoJava(poUnion.getTablaPrefijoCampos1(), poUnion.getCampos1()[i]));
                lsCampos1.append(",");
                lsCampos2.append(getCampoJava(poUnion.getTabla2(), poUnion.getCampos2()[i]));
                lsCampos2.append(",");
            }
            lsCampos1.deleteCharAt(lsCampos1.length()-1);
            lsCampos2.deleteCharAt(lsCampos2.length()-1);
            lsCampos1.append("}\n");
            lsCampos2.append("}\n");
            lsCadena.append(lsCampos1.toString());
            lsCadena.append(lsCampos2.toString());
            lsCadena.append("));\n");
        }

        return new JSelectFromParte(psParte1.getFrom() + lsCadena.toString(), null);
    }

    public String msTabla(final String psTabla, final String psTablaAlias) {
        String lsTabla = null;
        lsTabla = "JSelect loSelect = new JSelect("+getTabla(psTabla, "")+", \""+(psTablaAlias==null?"":psTablaAlias)+"\");\n";
        msJListDatos=new StringBuilder();
        return lsTabla;
    }

    public String msListaCampos(final int plTipo, final JListaElementos poCampos) {

        StringBuilder lsCampos = new StringBuilder();
        if (poCampos != null && poCampos.size() > 0) {
            msJListDatos.append("JListDatos loList = new JListDatos();\n");
            msJListDatos.append("loList.moServidor=JDatosGeneralesP.getDatosGenerales().getServer();\n");
            if (plTipo == JSelect.mclCamposDistinct) {
                lsCampos.append("loSelect.setCamposTipo(JSelect.mclCamposDistinct);\n");
            }
            Iterator loEnum = poCampos.iterator();
            for (; loEnum.hasNext();) {
                JSelectCampo loCampo = (JSelectCampo) loEnum.next();
                lsCampos.append(msCampo(mclCampoSelect, loCampo.getFuncion(), loCampo.getTabla(), loCampo.getNombre()));
                lsCampos.append("\n");
                msJListDatos.append("loList.getFields().addField(new JFieldDef("+getCampoJava(loCampo.getTabla(),loCampo.getNombre()) +"));");
                msJListDatos.append("\n");
            }
            lsCampos.setLength(lsCampos.length() - 1);
            msJListDatos.append("loList.recuperarDatosNoCacheNormal(loSelect);\n");
        }
        return (lsCampos.length() == 0 ? null : lsCampos.toString());
    }

    public String msListaCamposGroup(final JListaElementos poCampos) {

        StringBuilder lsCampos = new StringBuilder();
        if (poCampos != null && poCampos.size() > 0) {
            Iterator loEnum = poCampos.iterator();
            for (; loEnum.hasNext();) {
                JSelectCampo loCampo = (JSelectCampo) loEnum.next();
                lsCampos.append(msCampo(mclCampoGroup, JSelectCampo.mclFuncionNada, loCampo.getTabla(), loCampo.getNombre()));
                lsCampos.append("\n");
            }
            lsCampos.setLength(lsCampos.length() - 1);
        }
        return (lsCampos.length() == 0 ? null : lsCampos.toString());
    }

    public String msListaCamposOrder(final JListaElementos poCampos) {
        StringBuilder lsCampos = new StringBuilder();
        if (poCampos != null && poCampos.size() > 0) {
            Iterator loEnum = poCampos.iterator();
            for (; loEnum.hasNext();) {
                JSelectCampo loCampo = (JSelectCampo) loEnum.next();
                lsCampos.append(msCampo(mclCampoOrden, JSelectCampo.mclFuncionNada, loCampo.getTabla(), loCampo.getNombre()));
                lsCampos.append("\n");
            }
            lsCampos.setLength(lsCampos.length() - 1);
        }
        return (lsCampos.length() == 0 ? null : lsCampos.toString());
    }

    public String msActualizacion(final String psTabla, final JFieldDefs poCampos, final int plTipoModif) {
        return "";
    }
    public void pasarParametros(final String psTabla, final JFieldDefs poCampos, final int plTipoModif, final PreparedStatement loSent) throws java.sql.SQLException {
    }

    public String msSelect(final String psCampos, final String psFrom, final String psWhere, final String psGroup, final String psHaving, final String psOrder) {
//    moActualizar = null;
        int lLen = psFrom.length() + 40;
        if(psCampos!=null){
            lLen += psCampos.length();
        }
        StringBuilder loCadena = new StringBuilder(lLen);

        loCadena.append(psFrom);
        loCadena.append('\n');
        loCadena.append(psCampos);
        loCadena.append('\n');
        loCadena.append((psGroup == null ? "" : psGroup + "\n"));
        loCadena.append((psOrder == null ? "" : psOrder + "\n"));
        loCadena.append((psWhere == null ? "" : "loSelect.getWhere().addCondicionAND("+psWhere+");\n"));
        loCadena.append((psHaving == null ? "" : psHaving + "\n"));

        loCadena.append("\n");loCadena.append("\n");
        loCadena.append(msJListDatos.toString());
        
        return loCadena.toString();
    }

    public String msSelectUltMasUno(final String psTabla, final String psCampo, final int plActu) {
        return "JSelect loSelect = new JSelect(psTabla);\n"+
                "loSelect.addCampo(JSelectCampo.mclFuncionMax, psTabla, psCampo);\n";
    }


    public String msCampo(final int plFuncion, final String psTabla, final String psCampo) {
        return getCampoJava(psTabla,psCampo);

    }
    private String msCampo(final int plTipo, final int plFuncion, final String psTabla, final String psCampo) {
        String lsCampoResult;
        String lsFuncion;

        switch (plFuncion) {
            case JSelectCampo.mclFuncionAvg:
                lsFuncion = "JSelectCampo.mclFuncionAvg";
                break;
            case JSelectCampo.mclFuncionCount:
                lsFuncion = "JSelectCampo.mclFuncionCount";
                break;
            case JSelectCampo.mclFuncionMax:
                lsFuncion = "JSelectCampo.mclFuncionMax";
                break;
            case JSelectCampo.mclFuncionMin:
                lsFuncion = "JSelectCampo.mclFuncionMin";
                break;
            case JSelectCampo.mclFuncionSum:
                lsFuncion = "JSelectCampo.mclFuncionSum";
                break;
            case JSelectCampo.mclFuncionTalCual:
                lsFuncion = "JSelectCampo.mclFuncionTalCual";
                break;                
            default:
                lsFuncion = "JSelectCampo.mclFuncionNada";
        }
        switch(plTipo){
            case mclCampoOrden:
                lsCampoResult ="loSelect.addCampoOrder(JT"+msSustituirRaros(psTabla)+".msCTabla, "+getCampoJava(psTabla,psCampo) +");";
                break;
            case mclCampoGroup:
                lsCampoResult ="loSelect.addCampoGroup(JT"+msSustituirRaros(psTabla)+".msCTabla, "+getCampoJava(psTabla,psCampo) +");";
                break;
            default:
                if(plFuncion==JSelectCampo.mclFuncionNada){
                    lsCampoResult ="loSelect.addCampo(JT"+msSustituirRaros(psTabla)+".msCTabla, "+getCampoJava(psTabla,psCampo) +");";
                }else{
                    lsCampoResult ="loSelect.addCampo("+lsFuncion+",JT"+msSustituirRaros(psTabla)+".msCTabla, "+getCampoJava(psTabla,psCampo) +");";
                }
        }
        
        return lsCampoResult;

    }    

    public static boolean mbLetraEne(char l){
        return l == 'ñ' || l == 'Ñ' || l == '?' || l == '?' || ((int)l)==209;
    }
    public static boolean mbLetraBuena(char l){
        if(l == ' ' || l == ',' || l == '.' || l == ':' || 
           l == 'ñ' || l == 'Ñ' || l == '?' || l == '?' || ((int)l)==209 ||
           l == 'á' || l == 'Á' || 
           l == 'é' || l == 'É' || 
           l == 'í' || l == 'Í' || 
           l == 'ó' || l == 'Ó' || l == ')' ||
           l == 'ú' || l == 'Ú' || l == '(' ||
           l == '-' || l == '/' || l == '+' || l == ' ' || l == '_' ||
           l == '?' || l == '¿' || l == '%' || l == 'º'
          ){
            return false;
        }else{
            return true;
        }
    }
    
    public static String msSustituirRaros(String psTexto){
        String lsTexto = psTexto.replace('á', 'a');
        lsTexto = lsTexto.replace('á', 'a');
        lsTexto = lsTexto.replace('é', 'e');
        lsTexto = lsTexto.replace('í', 'i');
        lsTexto = lsTexto.replace('ó', 'o');
        lsTexto = lsTexto.replace('ú', 'u');
        lsTexto = lsTexto.replace('Á', 'A');
        lsTexto = lsTexto.replace('É', 'E');
        lsTexto = lsTexto.replace('Í', 'I');
        lsTexto = lsTexto.replace('Ó', 'O');
        lsTexto = lsTexto.replace('Ú', 'U');
        lsTexto = lsTexto.replace('?', 'N');
        lsTexto = lsTexto.replace('?', 'n');
        lsTexto = lsTexto.replace('Ñ', 'N');
        lsTexto = lsTexto.replace('ñ', 'n');
//        lsTexto = lsTexto.replace('\%', '100');


        StringBuilder lsTextob = new StringBuilder(lsTexto.length());
        for(int i = 0; i < lsTexto.length(); i++ ){
            if(mbLetraBuena(lsTexto.charAt(i))){
                lsTextob.append(lsTexto.charAt(i));
            }else{
                if(mbLetraEne(lsTexto.charAt(i))){
                    lsTextob.append('N');
                }
            }
        }
        return lsTextob.toString().toUpperCase();
    }
    
    
    public String msTipo(int plTipo, int plTama){
        String lsTipo="";
        switch(plTipo){
            case JListDatos.mclTipoBoolean:
                lsTipo="JListDatos.mclTipoBoolean";
                break;
            case JListDatos.mclTipoCadena:
                lsTipo="JListDatos.mclTipoCadena";
                break;
            case JListDatos.mclTipoFecha:
                lsTipo="JListDatos.mclTipoFecha";
                break;
            case JListDatos.mclTipoNumero:
                lsTipo="JListDatos.mclTipoNumero";
                break;
            case JListDatos.mclTipoNumeroDoble:
                lsTipo="JListDatos.mclTipoNumeroDoble";
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                lsTipo="JListDatos.mclTipoMoneda3Decimales";
                break;
            case JListDatos.mclTipoMoneda:
                lsTipo="JListDatos.mclTipoMoneda";
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                lsTipo="JListDatos.mclTipoPorcentual3Decimales";
                break;
            case JListDatos.mclTipoPorcentual:
                lsTipo="JListDatos.mclTipoPorcentual";
                break;
        }
        return lsTipo;
    }    
    private String getCampoJava(String psTabla, String psCampo){
        return "JT"+msSustituirRaros(psTabla)+".get"+ msSustituirRaros(psCampo) +"Nombre()";
    }


    public String getTabla(final int plTipo, final JTableDef poTabla) throws ExceptionNoImplementado {
        throw new ExceptionNoImplementado();
    }

    public String getIndice(final int plTipo, final JIndiceDef poIndice, final JTableDef poTabla) throws ExceptionNoImplementado {
        throw new ExceptionNoImplementado();
    }

    public String getRelacion(final int plTipo, final JRelacionesDef poRelacion, final JTableDef poTabla1, final JTableDef poTabla2) throws ExceptionNoImplementado {
        throw new ExceptionNoImplementado();
    }

    public String getCampo(final int plTipo, final JFieldDef poCampo, final JTableDef poTabla) throws ExceptionNoImplementado {
        throw new ExceptionNoImplementado();
    }

    public String msSelectPaginado(String psCampos, String psFrom, String psWhere, String psGroup, String psHaving, String psOrder, String pagina_actual, String elem_per_page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
