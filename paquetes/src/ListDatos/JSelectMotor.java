/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003-2005</p>
 * <p>Company: </p>
 * @author sin atribuir
 * @version 1.0
 */
package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Types;
import java.util.Iterator;
import utiles.*;

/**
 * Construye select de distintas bases de datos a partir del Objeto JSelect
 */
public final class JSelectMotor implements ISelectMotor, Serializable {

    private static final long serialVersionUID = 33333319L;
    /** Constante tipo de base de datos access */
    public static final int mclAccess = 0;
    /** Constante tipo de base de datos PostGreSQL */
    public static final int mclPostGreSQL = 1;
    /** Constante tipo de base de datos Oracle */
    public static final int mclOracle = 2;
    /** Constante tipo de base de datos Sql server */
    public static final int mclSqlServer = 3;
    /** Constante tipo de base de datos mySQL*/
    public static final int mclMySQL = 4;
    /** Constante tipo de base de datos firebird*/
    public static final int mclFireBird = 5;
    /** Constante tipo de base de datos derby*/
    public static final int mclDerby = 6;
    /** Constante tipo de base de datos SQLLite*/
    public static final int mclSQLLite = 7;
    /** tipo de base de datos por defectos access*/
    public final int mlTipo;

    /**
     *Contructor
     */
    public JSelectMotor() {
        super();
        mlTipo = mclAccess;
    }

    /**
     * Contructor
     * @param plTipo tipo de base de datos
     */
    public JSelectMotor(final int plTipo) {
        super();
        mlTipo = plTipo;
    }

    /**
     * Contructor
     * @param psTipo tipo de base de datos
     */
    public JSelectMotor(final String psTipo) {
        if(JConversiones.isNumeric(psTipo)){
            mlTipo = Integer.valueOf(psTipo).intValue();
        }else{
            mlTipo = mclAccess;
        }
    }

//    /**
//     * establece el tipo de base de datos
//     * @param plTipo tipo de base de datos
//     */
//    public void setTipo(final int plTipo) {
//        mlTipo = plTipo;
//    }
    public String msAnd(final String psCond1, final String psCond2) {
        return "(" + psCond1 + ") AND (" + psCond2 + ")";
    }

    public String msOr(final String psCond1, final String psCond2) {
        return "(" + psCond1 + ") OR (" + psCond2 + ")";
    }
    @Override
    public String msNot(String psCond1) {
        return "not (" + psCond1 + ")";
    }
    private String msAlmoadilla(final int plTipoCampo) {
        String lsAlmoadilla = "'";
        switch (plTipoCampo) {
            case JListDatos.mclTipoBoolean:
            case JListDatos.mclTipoNumero:
            case JListDatos.mclTipoNumeroDoble:
            case JListDatos.mclTipoMoneda3Decimales:
            case JListDatos.mclTipoMoneda:
            case JListDatos.mclTipoPorcentual3Decimales:
            case JListDatos.mclTipoPorcentual:
                lsAlmoadilla = "";
                break;
            case JListDatos.mclTipoCadena:
                lsAlmoadilla = "'";
                break;
            case JListDatos.mclTipoFecha:
                if (mlTipo == mclAccess) {
                    lsAlmoadilla = "#";
                    break;
                } else {
                    lsAlmoadilla = "'";
                    break;
                }
            default:
        }
        return lsAlmoadilla;
    }

    private String msTrue() {
        String lsTrue = "true";
        if ((mlTipo == mclSqlServer) || (mlTipo == mclOracle)
                || (mlTipo == mclMySQL) || (mlTipo == mclFireBird)
                || (mlTipo == mclDerby) || (mlTipo == mclSQLLite)) {
            lsTrue = "1";
        }
        return lsTrue;
    }

    private String msFalse() {
        String lsFalse = "false";
        if ((mlTipo == mclSqlServer) || (mlTipo == mclOracle)
                || (mlTipo == mclMySQL) || (mlTipo == mclFireBird)
                || (mlTipo == mclDerby) || (mlTipo == mclSQLLite)) {
            lsFalse = "0";
        }
        return lsFalse;
    }

    public String msCondicion(final String psCampo, final int plCond, final String psValor, final String psCampo2, final int plTipo) {
        StringBuilder lsCadena = new StringBuilder();

        if (psCampo2 == null) {
            if ((psValor == null || psValor.equals(""))) {
                switch (plCond) {
                    case JListDatos.mclTIgual:
                        lsCadena.setLength(0);
                        lsCadena.append('(');
                        lsCadena.append(psCampo);
                        lsCadena.append(" is null");
                        if (plTipo == JListDatos.mclTipoCadena) {
                            lsCadena.append(" or ");
                            lsCadena.append(psCampo);
                            //el problema esta en que para memos/binarios la condicion igual no sirve
                            //por lo q se usa like
                            lsCadena.append(msOperador(JListDatos.mclTLike));
                            lsCadena.append(msAlmoadilla(plTipo));
                            lsCadena.append(msAlmoadilla(plTipo));
                        }
                        lsCadena.append(')');
                        break;
                    case JListDatos.mclTDistinto:
                        lsCadena.setLength(0);
                        lsCadena.append('(');
                        lsCadena.append("not ");
                        lsCadena.append(psCampo);
                        lsCadena.append(" is null");
                        if (plTipo == JListDatos.mclTipoCadena) {
                            lsCadena.append(" and not ");
                            lsCadena.append(psCampo);
                            //el problema esta en que para memos/binarios la condicion igual no sirve
                            //por lo q se usa like
                            lsCadena.append(msOperador(JListDatos.mclTLike));
                            lsCadena.append(msAlmoadilla(plTipo));
                            lsCadena.append(msAlmoadilla(plTipo));
                        }
                        lsCadena.append(')');
                        break;
                    default:
                        lsCadena.setLength(0);
                        lsCadena.append(psCampo);
                        lsCadena.append(msOperador(plCond));
                        lsCadena.append(msAlmoadilla(plTipo));
                        lsCadena.append(msAlmoadilla(plTipo));
                }
            } else {
                lsCadena.setLength(0);
                lsCadena.append(psCampo);
                lsCadena.append(msOperador(plCond));
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        if (psValor.compareTo(JListDatos.mcsTrue) == 0) {
                            lsCadena.append(msAlmoadilla(plTipo));
                            lsCadena.append(msTrue());
                            lsCadena.append(msAlmoadilla(plTipo));
                        } else {
                            lsCadena.append(msAlmoadilla(plTipo));
                            lsCadena.append(msFalse());
                            lsCadena.append(msAlmoadilla(plTipo));
                        }
                        break;
                    case JListDatos.mclTipoCadena:
                        lsCadena.append(msAlmoadilla(plTipo));
                        //nos aseguramos de que no nos hacen SQL injection reemplazando las almoadillas del valor
                        if(mlTipo==mclPostGreSQL){
                            lsCadena.append(replace2(psValor, msAlmoadilla(plTipo), "\'"));
                        }else{
                            lsCadena.append(replace2(psValor, msAlmoadilla(plTipo), msAlmoadilla(plTipo) + msAlmoadilla(plTipo)));
                        }
                        lsCadena.append(msAlmoadilla(plTipo));
                        break;
                    case JListDatos.mclTipoFecha:
                        String lsFormato = "";
                        try {
                            switch (mlTipo) {
                                case mclOracle:
                                    lsCadena.append("TO_DATE('"
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsddMMyyyyHHmmss)
                                            + "', 'DD/MM/YYYY HH24:MI:SS')");
                                    break;
                                case mclMySQL:
                                    lsCadena.append(
                                            msAlmoadilla(plTipo)
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsyyyyMMddHHmmssGuiones)
                                            + msAlmoadilla(plTipo));
                                    break;
                                case mclFireBird:
                                    lsCadena.append(msAlmoadilla(plTipo)
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsMMddyyyyHHmmss)
                                            + msAlmoadilla(plTipo));
                                    break;
                                case mclDerby:
                                    lsCadena.append(
                                            "TIMESTAMP('"
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsyyyyMMddHHmmssGuiones)
                                            + "')");
                                    break;
                                case mclSQLLite:
                                    lsCadena.append(
                                            "'"
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsyyyyMMddHHmmssGuiones)
                                            + "'");
                                    break;
                                case mclSqlServer:
                                    lsCadena.append(
                                            "CONVERT(DATETIME, '"
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsyyyyMMddHHmmssGuiones)
                                            + "', 102)");
                                    break;
                                case mclPostGreSQL:
                                    lsCadena.append(msAlmoadilla(plTipo)
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsyyyyMMddHHmmssGuiones)
                                            + msAlmoadilla(plTipo));
                                    break;
                                default:
                                    lsCadena.append(msAlmoadilla(plTipo)
                                            + (new JDateEdu(psValor)).msFormatear(JFormat.mcsMMddyyyyHHmmss)
                                            + msAlmoadilla(plTipo));
                            }
                        } catch (Exception e) {
                            lsCadena.setLength(0);
                            JDepuracion.anadirTexto(this.getClass().getName(), e);
                        }
                        break;
                    case JListDatos.mclTipoNumero:
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsCadena.append(msAlmoadilla(plTipo));
                        lsCadena.append(psValor.replace(',', '.'));
                        lsCadena.append(msAlmoadilla(plTipo));
                        break;
                    default:
                }
            }
        } else {
            lsCadena.setLength(0);
            lsCadena.append(psCampo);
            lsCadena.append(msOperador(plCond));
            lsCadena.append(psCampo2);
        }
        return "(" + lsCadena.toString() + ")";
    }

    private String replace2(final String psCadena, final String psCadena1, final String psCadena2) {
        int llPosicion;
        String lsSustituir = psCadena;
        llPosicion = lsSustituir.indexOf(psCadena1);
        for (; llPosicion >= 0;) {
            lsSustituir = lsSustituir.substring(0, llPosicion) + psCadena2 + lsSustituir.substring(llPosicion + psCadena1.length());
            llPosicion = lsSustituir.indexOf(psCadena1, llPosicion + psCadena2.length());
        }
        return lsSustituir;
    }

    private String msOperador(final int plOperador) {
        String lsCadena = "";
        switch (plOperador) {
            case JListDatos.mclTDistinto:
                lsCadena = "<>";
                break;
            case JListDatos.mclTIgual:
                lsCadena = "=";
                break;
            case JListDatos.mclTMayor:
                lsCadena = ">";
                break;
            case JListDatos.mclTMayorIgual:
                lsCadena = ">=";
                break;
            case JListDatos.mclTMenor:
                lsCadena = "<";
                break;
            case JListDatos.mclTMenorIgual:
                lsCadena = "<=";
                break;
            case JListDatos.mclTLike:
                lsCadena = " LIKE ";
                break;
            default:
                lsCadena = "";

        }
        return lsCadena;

    }

    private String getTabla(String psTabla, String psAlias) {
        if (psAlias == null || psAlias.equals("")) {
            return psTabla;
        } else {
            return psAlias;
        }

    }

    public JSelectFromParte msFromUnion(final JSelectFromParte psParte1, final JSelectUnionTablas poUnion) {
        StringBuilder lsCadena = new StringBuilder();
        StringBuilder lsWhere = new StringBuilder();
        JSelectFromParte loFromParte;
        if (mlTipo == mclFireBird) {
            lsCadena.append(psParte1.getFrom());
        } else {
            if (psParte1.getFrom().indexOf("(") >= 0) {
                lsCadena.append('(');
                lsCadena.append(psParte1.getFrom());
                lsCadena.append(')');
            } else {
                lsCadena.append(psParte1.getFrom());
            }
        }

        if (poUnion.getCampos1() == null || poUnion.getCampos1().length == 0) {
            lsCadena.insert(0, msTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()) + ',');
            loFromParte = new JSelectFromParte(lsCadena.toString(), null);
        } else {
            if (mlTipo == mclOracle) {
                if (psParte1.getFrom().compareTo("") == 0) {
                    lsCadena.append(msTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()));
                } else {
                    lsCadena.append(',');
                    lsCadena.append(msTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()));
                }
                if (psParte1.getWhere() == null || psParte1.getWhere().compareTo("") == 0) {
                    lsWhere.setLength(0);
                } else {
                    lsWhere.setLength(0);
                    lsWhere.append(psParte1.getWhere());
                    lsWhere.append(" and ");
                }

                for (int i = 0; i < poUnion.getCampos1().length; i++) {
                    lsWhere.append(msCampo(JSelectCampo.mclFuncionNada, poUnion.getTablaPrefijoCampos1(), poUnion.getCampos1()[i]));
                    if (poUnion.getTipo() == JSelectUnionTablas.mclRight) {
                        lsWhere.append("(+)");
                    }
                    lsWhere.append('=');
                    lsWhere.append(msCampo(JSelectCampo.mclFuncionNada, getTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()), poUnion.getCampos2()[i]));
                    if (poUnion.getTipo() == JSelectUnionTablas.mclLeft) {
                        lsWhere.append("(+)");
                    }
                    lsWhere.append(" and ");
                }
                lsWhere.setLength(lsWhere.length() - 4);
                loFromParte = new JSelectFromParte(lsCadena.toString(), lsWhere.toString());
            } else {
                String lsClave = "";
                switch (poUnion.getTipo()) {
                    case JSelectUnionTablas.mclInner:
                        lsClave = " inner join ";
                        break;
                    case JSelectUnionTablas.mclLeft:
                        lsClave = " left join ";
                        break;
                    case JSelectUnionTablas.mclRight:
                        lsClave = " right join ";
                        break;
                    default:
                        lsClave = "";
                }
                lsCadena.append(lsClave);
                lsCadena.append(msTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()));
                lsCadena.append(" on (");
                for (int i = 0; i < poUnion.getCampos1().length; i++) {
                    lsCadena.append(msCampo(JSelectCampo.mclFuncionNada, poUnion.getTablaPrefijoCampos1(), poUnion.getCampos1()[i]));
                    lsCadena.append('=');
                    lsCadena.append(msCampo(JSelectCampo.mclFuncionNada, getTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()), poUnion.getCampos2()[i]));
                    lsCadena.append(" and ");
                }
                lsCadena.setLength(lsCadena.length() - 4);
                lsCadena.append(')');

                loFromParte = new JSelectFromParte(lsCadena.toString(), null);
            }
        }
        return loFromParte;
    }

    private boolean isUsaComodinPostGRES(String psTabla){
            psTabla=psTabla.toLowerCase();
            String lsCaracteresPermitidos = "1234567890qwertyuiopasdfghjklzxcvbnm_";
            boolean lbComodin = false;
            for(int i = 0 ; i<psTabla.length() && !lbComodin;i++){
                if(lsCaracteresPermitidos.indexOf(psTabla.charAt(i))<0){
                    lbComodin = true;
                }
            }        
            return lbComodin;
    }
    public String msTabla(String psTabla, final String psTablaAlias) {
        String lsTabla = null;
        String lsComodinA = "";
        String lsComodinC = "";
        String lsSeparadorAlias = " ";
        if ((mlTipo == mclAccess) || (mlTipo == mclSqlServer)) {
            lsComodinA = "[";
            lsComodinC = "]";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclOracle) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclFireBird) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclDerby) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclSQLLite) {
            lsComodinA = "`";
            lsComodinC = "`";
            lsSeparadorAlias = " AS ";
        }
        
        if ((mlTipo == mclPostGreSQL)) {
            psTabla=psTabla.toLowerCase();
            if(isUsaComodinPostGRES(psTabla)){
                lsComodinA = "\"";
                lsComodinC = "\"";
            }else{
                lsComodinA = "";
                lsComodinC = "";
            }
        }
        if ((mlTipo == mclMySQL)) {
            lsComodinA = "`";
            lsComodinC = "`";
        }
        lsTabla = lsComodinA + psTabla + lsComodinC;
        if (psTablaAlias != null && !psTablaAlias.equals("")) {
            if ((mlTipo == mclPostGreSQL)) {
                if(isUsaComodinPostGRES(psTablaAlias)){
                    lsTabla = lsTabla + lsSeparadorAlias + lsComodinA + psTablaAlias + lsComodinC;
                }else{
                    lsTabla = lsTabla + lsSeparadorAlias + psTablaAlias;
                }
            }else{
                lsTabla = lsTabla + lsSeparadorAlias + lsComodinA + psTablaAlias + lsComodinC;
            }
        }
        return lsTabla;
    }

    public String msListaCampos(final int plTipo, final JListaElementos poCampos) {
        StringBuilder lsCampos = new StringBuilder();
        if (poCampos != null && poCampos.size() > 0) {
            if (plTipo == JSelect.mclCamposDistinct) {
                lsCampos.append(" Distinct ");
            }
            Iterator loEnum = poCampos.iterator();
            for (; loEnum.hasNext();) {
                JSelectCampo loCampo = (JSelectCampo) loEnum.next();
                lsCampos.append(loCampo.msSQL(this));
                lsCampos.append(',');
            }
            lsCampos.setLength(lsCampos.length() - 1);
        }
        return (lsCampos.length() == 0 ? null : lsCampos.toString());
    }

    public String msListaCamposGroup(final JListaElementos poCampos) {
        return msListaCampos(0, poCampos);
    }

    public String msListaCamposOrder(final JListaElementos poCampos) {
        if(mlTipo==mclPostGreSQL){
            StringBuilder lsCampos = new StringBuilder();
            if (poCampos != null && poCampos.size() > 0) {
                Iterator loEnum = poCampos.iterator();
                for (; loEnum.hasNext();) {
                    JSelectCampo loCampo = (JSelectCampo) loEnum.next();
                    lsCampos.append(loCampo.msSQL(this) + " NULLS FIRST");
                    lsCampos.append(',');
                }
                lsCampos.setLength(lsCampos.length() - 1);
            }
            return (lsCampos.length() == 0 ? null : lsCampos.toString());

        }else{
            return msListaCampos(0, poCampos);
        }
    }

    public String msActualizacion(final String psTabla, final JFieldDefs poCampos, final int plTipoModif) {
        //
        //creamos la SQL
        //
        String lsSQL = "";
        switch (plTipoModif) {
            case JListDatos.mclEditar:
                lsSQL = "update " + msTabla(psTabla, null) + " " + msCrearCampos(null, poCampos, plTipoModif) + " where " + msCrearWhere(psTabla, poCampos, plTipoModif);
                break;
            case JListDatos.mclNuevo:
                lsSQL = "insert into " + msTabla(psTabla, null) + " " + msCrearCampos(null, poCampos, plTipoModif);
                break;
            case JListDatos.mclBorrar:
                lsSQL = "delete from " + msTabla(psTabla, null) + (poCampos == null ? "" : " where " + msCrearWhere(psTabla, poCampos, plTipoModif));
                break;
            case JListDatos.mclComando://modo comando
                lsSQL = psTabla + msCrearCampos(psTabla, poCampos, plTipoModif);
                if (mlTipo == mclOracle) {
                    lsSQL = "begin " + lsSQL + "; end;";
                }
                if (mlTipo == mclFireBird) {
                    lsSQL = "execute procedure " + lsSQL + "";
                }
                break;
            case JListDatos.mclScript://modo script
                lsSQL = psTabla;
                break;                
            default:
                lsSQL = "";
        }
        return lsSQL;
    }

    private String msCrearCampos(final String psTabla, final JFieldDefs poCampos, final int plTipoModif) {
        //
        //Campos
        //

        StringBuilder lsCampos = new StringBuilder();
        StringBuilder lsValores = new StringBuilder();
        lsCampos.append(' ');
        lsValores.append(' ');
        for (int i = 0; i < poCampos.count(); i++) {
            JFieldDef loCampo = poCampos.get(i);
            if (loCampo.isEditable()) {
                switch (plTipoModif) {
                    case JListDatos.mclNuevo:
                    case JListDatos.mclComando:
                        lsCampos.append(msCampo(JSelectCampo.mclFuncionNada, psTabla, loCampo.getNombre()));
                        lsCampos.append(',');
                        lsValores.append("?,");
                        break;
                    case JListDatos.mclEditar:
                        if (!loCampo.getPrincipalSN()) {
                            lsCampos.append(msCampo(JSelectCampo.mclFuncionNada, psTabla, loCampo.getNombre()));
                            lsCampos.append(" = ? ,");
                        }
                        break;
                    default:
                }
            }
        }
        switch (plTipoModif) {
            case JListDatos.mclNuevo:
                lsCampos.setLength(lsCampos.length() - 1);
                lsCampos.insert(0, '(');
                lsCampos.append(')');

                lsValores.setLength(lsValores.length() - 1);
                lsValores.insert(0, '(');
                lsValores.append(')');

                lsCampos.append(" values ");
                lsCampos.append(lsValores);
                break;
            case JListDatos.mclEditar:
                lsCampos.setLength(lsCampos.length() - 1);
                lsCampos.insert(0, "SET ");
                break;
            case JListDatos.mclComando:
                lsCampos.setLength(0);
                lsCampos.append('(');
                lsValores.setLength(lsValores.length() - 1);//quitamos la coma
                lsCampos.append(lsValores.toString());
                lsCampos.append(')');
                break;
            default:
        }
        return lsCampos.toString();
    }

    private String msCrearWhere(final String psTabla, final JFieldDefs poCampos, final int plTipoModif) {
        //
        //where
        //
        int[] lalCamposPrincipales = poCampos.malCamposPrincipales();
        if(lalCamposPrincipales==null){
            return "";
        }else{
            StringBuilder lsWhere = new StringBuilder();
            lsWhere.append(' ');
            for (int i = 0; i < lalCamposPrincipales.length; i++) {
                if (plTipoModif != JListDatos.mclNuevo) {
                    lsWhere.append(msCampo(JSelectCampo.mclFuncionNada, psTabla, poCampos.get(lalCamposPrincipales[i]).getNombre()));
                    if (poCampos.get(lalCamposPrincipales[i]).isVacio()) {
                        lsWhere.append(" is null AND ");
                    } else {
                        lsWhere.append(" = ? AND ");
                    }
                }
            }
            if (plTipoModif != JListDatos.mclNuevo) {
                lsWhere.setLength(lsWhere.length() - 4);
            }
            lalCamposPrincipales = null;
            return lsWhere.toString();
        }
    }

    public void pasarParametros(final String psTabla, final JFieldDefs poCampos, final int plTipoModif, final PreparedStatement loSent) throws java.sql.SQLException {
        //Pasamos parametros
        int lParam = 1;
        int lParamOUT = 1;
        if (plTipoModif == JListDatos.mclNuevo
                || plTipoModif == JListDatos.mclEditar
                || plTipoModif == JListDatos.mclComando) {
            //pasamos los valores de los campos
            for (int i = 0; i < poCampos.count(); i++) {
                boolean lbOUT = JActualizar.isParamOut(poCampos.get(i));
                if (!(plTipoModif == JListDatos.mclEditar && poCampos.get(i).getPrincipalSN())
                        && !lbOUT
                        ) {
                    switch (poCampos.get(i).getTipoSQL()) {
                        case Types.TIME:
                        case Types.DATE:
                        case Types.TIMESTAMP:
                            if(mlTipo==mclSQLLite){
                                if (poCampos.get(i).isVacio()) {
                                    loSent.setObject(lParam++, poCampos.get(i).getStringConNull(), Types.VARCHAR);
                                }else{
                                    try {
                                        loSent.setObject(lParam++, poCampos.get(i).getDateEdu().msFormatear(JFormat.mcsyyyyMMddHHmmssGuiones), Types.VARCHAR);
                                    } catch (FechaMalException ex) {
                                        throw new java.sql.SQLException(ex.toString());
                                    }
                                }
                            }else{
                                java.util.Date lod = (java.util.Date) poCampos.get(i).getValueSQL();
                                if (lod instanceof Time) {
                                    loSent.setTime(lParam++, (java.sql.Time) poCampos.get(i).getValueSQL());
                                } else {
                                    loSent.setTimestamp(lParam++, (java.sql.Timestamp) poCampos.get(i).getValueSQL());
                                }
                            }
                            break;
                        case Types.LONGVARCHAR:
                            if (mlTipo == mclSqlServer) {
                                loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), Types.VARCHAR);
                            }else if (mlTipo == mclPostGreSQL) {
                                if(poCampos.get(i).isVacio()){
                                    loSent.setObject(lParam++, null, Types.VARCHAR);
                                }else{
                                    loSent.setObject(lParam++, poCampos.get(i).getString(), Types.VARCHAR, poCampos.get(i).getString().length());
                                }
//                            } if (mlTipo == mclPostGreSQL) {
//                                loSent.setObject(lParam++, poCampos.get(i).getValueSQL());
//                                loSent.setBinaryStream(lParam++
//                                        , loCadenaLarga
//                                        , loCadenaLarga.msCadena.length());
//                                new Reader() {
//
//                                    public int read(char[] cbuf, int off, int len) throws IOException {
//                                        return loCadenaLarga.read(cbuf, off, len);
//                                    }
//
//                                    public void close() throws IOException {
//                                        loCadenaLarga.close();
//                                    }
                            }else {
                                if (poCampos.get(i).isVacio()) {
                                    loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
                                } else {
                                    CadenaLarga loCadenaLaga = new CadenaLarga(poCampos.get(i).getString());
                                    loSent.setAsciiStream(lParam++, loCadenaLaga, loCadenaLaga.msCadena.length());
                                }
                            }
                            break;
                        case Types.BIT:
                            if (mlTipo == mclMySQL) {
                                if (poCampos.get(i).isVacio()) {
                                    loSent.setObject(lParam++, "0", poCampos.get(i).getTipoSQL());
                                } else {
                                    loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
                                }
                            } else if (mlTipo == mclSQLLite) {
                                if (poCampos.get(i).isVacio()) {
                                    loSent.setObject(lParam++, null, Types.INTEGER);
                                } else {
                                    loSent.setObject(lParam++, poCampos.get(i).getIntegerConNull(), Types.INTEGER);
                                }
                            } else {
                                loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
                            }
                            break;
                        default:
                            if(mlTipo == mclPostGreSQL && poCampos.get(i).getTipo()==JListDatos.mclTipoCadena){
                                if(poCampos.get(i).isVacio()){
                                    loSent.setObject(lParam++, null, poCampos.get(i).getTipoSQL());
                                }else{
                                    loSent.setObject(lParam++, poCampos.get(i).getString().replaceAll("'", "\'"), poCampos.get(i).getTipoSQL());
                                }
                            }else{
                                loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
                            }                    
                    }
                }else{
                    if(lbOUT){
                        ((CallableStatement)loSent).registerOutParameter(lParamOUT++, poCampos.get(i).getTipoSQL());
                    }
                }
            }
        }
//          if (plTipoModif == JListDatos.mclEditar) {
//             //pasamos los valores de los campos
//             for(int i = 0; i< poCampos.count();i++)
//                if (!poCampos.get(i).getPrincipalSN())
//                    loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
//          }
        if ((plTipoModif == JListDatos.mclEditar)
                || (plTipoModif == JListDatos.mclBorrar && poCampos != null)) {
            //pasamos los valores del where
            int[] lalCamposPrincipales = poCampos.malCamposPrincipales();
            for (int i = 0; i < lalCamposPrincipales.length; i++) {
                if (!poCampos.get(lalCamposPrincipales[i]).isVacio()) {
                    loSent.setObject(lParam++, poCampos.get(lalCamposPrincipales[i]).getValueSQL(), poCampos.get(lalCamposPrincipales[i]).getTipoSQL());
                }
            }
            lalCamposPrincipales = null;
        }
    }

    public String msSelect(final String psCampos, final String psFrom, final String psWhere, final String psGroup, final String psHaving, final String psOrder) {
//    moActualizar = null;
        int lLen = psFrom.length() + 40;
        if (psCampos != null) {
            lLen += psCampos.length();
        }
        StringBuilder loCadena = new StringBuilder(lLen);

        loCadena.append(" Select ");
        loCadena.append(psCampos);
        loCadena.append(" from ");
        loCadena.append(psFrom);
        loCadena.append((psWhere == null ? "" : " where " + psWhere));
        loCadena.append((psGroup == null ? "" : " group by " + psGroup));
        loCadena.append((psHaving == null ? "" : " having " + psHaving));
        loCadena.append((psOrder == null ? "" : " order by " + psOrder));

        return loCadena.toString();
    }

    public String msSelectUltMasUno(final String psTabla, final String psCampo, final int plActu) {
        return " Select max(" + msCampo(JSelectCampo.mclFuncionNada, psTabla, psCampo) + ")+1"
                + " from " + msTabla(psTabla, null);
    }

    private String msCampo(String psCampo, String psCampoAlias) {
        String lsResult;
        String lsComodinA = "";
        String lsComodinC = "";
        String lsSeparadorAlias = " AS ";
        if ((mlTipo == mclAccess) || (mlTipo == mclSqlServer)) {
            lsComodinA = "[";
            lsComodinC = "]";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclOracle) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclFireBird) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclDerby) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " AS ";
        }
        if (mlTipo == mclSQLLite) {
            lsComodinA = "`";
            lsComodinC = "`";
            lsSeparadorAlias = " AS ";
        }
        if ((mlTipo == mclMySQL)) {
            lsComodinA = "`";
            lsComodinC = "`";
            lsSeparadorAlias = " AS ";
        }
        if ((mlTipo == mclPostGreSQL)) {
            psCampo=psCampo.toLowerCase();
            if(isUsaComodinPostGRES(psCampo)){
                lsComodinA = "\"";
                lsComodinC = "\"";
            }else{
                lsComodinA = "";
                lsComodinC = "";
            }
            lsSeparadorAlias = " AS ";
        }
        lsResult = lsComodinA + psCampo + lsComodinC;
        if (psCampoAlias != null && !psCampoAlias.equals("")) {
            lsResult = lsResult + lsSeparadorAlias + lsComodinA + psCampoAlias + lsComodinC;
        }
        return lsResult;
    }

    public String msCampo(final int plFuncion, final String psTabla, final String psCampo) {
        String lsCampoResult = psCampo;
        if (plFuncion != JSelectCampo.mclFuncionTalCual) {
            String lsFuncion = "";
            switch (plFuncion) {
                case JSelectCampo.mclFuncionAvg:
                    lsFuncion = "AVG";
                    break;
                case JSelectCampo.mclFuncionCount:
                    lsFuncion = "COUNT";
                    break;
                case JSelectCampo.mclFuncionMax:
                    lsFuncion = "MAX";
                    break;
                case JSelectCampo.mclFuncionMin:
                    lsFuncion = "MIN";
                    break;
                case JSelectCampo.mclFuncionSum:
                    lsFuncion = "SUM";
                    break;
                default:
                    lsFuncion = "";
            }
            String lsCampo = null;
            if (psCampo.compareTo(JSelect.mcsTodosCampos) == 0) {
                lsCampo = (psTabla == null ? "" : msTabla(psTabla, null) + ".") + psCampo;
            } else {
                lsCampo = (psTabla == null ? "" : msTabla(psTabla, null) + ".") + msCampo(psCampo, null);
            }
            if (lsFuncion.compareTo("") != 0) {
                lsCampo = lsFuncion + "(" + lsCampo + ")";
            }
            if (plFuncion == JSelectCampo.mclOrdenDesc) {
                lsCampo += " desc";
            }
            lsCampoResult = lsCampo;
        }
        return lsCampoResult;

    }

    private String msTipo(final int plTipo, final int plTamano) {
        String lsTipo = "";
        switch (mlTipo) {
            case mclSqlServer:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "BIT";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "DATETIME";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "INT";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "FLOAT";
                        break;
                    default:
                        if ((plTamano > 3999) || (plTamano <= 0)) {
                            lsTipo = "NTEXT";
                        } else {
                            lsTipo = "nvarchar";
                        }
                }
                break;
            case mclOracle:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "number(1)";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "date";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "integer";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "number";
                        break;
                    default:
                        if ((plTamano > 3999) || (plTamano <= 0)) {
                            lsTipo = "varchar(3000)";
                        } else {
                            lsTipo = "varchar";
                        }

                }
                break;
            case mclFireBird:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "number(1)";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "date";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "integer";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                        lsTipo = "number";
                        break;
                    case JListDatos.mclTipoMoneda3Decimales:
                        lsTipo = "number";
//                      lsTipo = "numeric(15,3)";
                        break;
                    case JListDatos.mclTipoMoneda:
                        lsTipo = "number";
//                      lsTipo = "numeric(15,3)";
                        break;
                    case JListDatos.mclTipoPorcentual3Decimales:
                        lsTipo = "number";
//                      lsTipo = "numeric(15,3)";
                        break;
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "number";
//                      lsTipo = "numeric(15,3)";
                        break;
                    default:
                        if ((plTamano > 3999) || (plTamano <= 0)) {
                            lsTipo = "varchar(3000)";
                        } else {
                            lsTipo = "varchar";
                        }

                }
                break;
            case mclDerby:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "BOOLEAN";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "TIMESTAMP";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "INTEGER";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "DOUBLE";
                        break;
                    default:
                        if ((plTamano > 3999) || (plTamano <= 0)) {
                            lsTipo = "LONGVARCHAR";
                        } else {
                            lsTipo = "VARCHAR";
                        }

                }
                break;
            case mclSQLLite:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "INTEGER";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "TEXT";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "INTEGER";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "REAL";
                        break;
                    default:
                        if ((plTamano > 3999) || (plTamano <= 0)) {
                            lsTipo = "BLOB";
                        } else {
                            lsTipo = "TEXT";
                        }

                }
                break;
            case mclAccess:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "BIT";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "DATETIME";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "LONG";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "DOUBLE";
                        break;
                    default:
                        if ((plTamano > 255) || (plTamano <= 0)) {
                            lsTipo = "LONGTEXT";
                        } else {
                            lsTipo = "TEXT";
                        }
                }
                break;
            case mclPostGreSQL:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "bool";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "timestamp";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "int8";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "float8";
                        break;
                    default:
                        if ((plTamano > 3999) || (plTamano <= 0)) {
                            lsTipo = "text";
                        } else {
                            lsTipo = "varchar";
                        }
                }
                break;
            case mclMySQL:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "tinyint";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "datetime";
                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "int";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "double";
                        break;
                    default:
                        if ((plTamano > 3999) || (plTamano <= 0)) {
                            lsTipo = "longtext";
                        } else {
                            lsTipo = "varchar";
                        }
                }
                break;
            default:

        }
        return lsTipo;
    }

    private String msEstructuraCampo(final JFieldDef poCampo) {
        StringBuilder lsSQL = new StringBuilder(40);

        lsSQL.append(msCampo(JSelectCampo.mclFuncionNada, null, poCampo.getNombre()));
        lsSQL.append(' ');
        lsSQL.append(msTipo(poCampo.getTipo(), poCampo.getTamano()));
        lsSQL.append(' ');
        if ((poCampo.getTamano() > 0)
                && (poCampo.getTamano() < 4000 || (mlTipo == mclAccess && poCampo.getTamano() <= 255 ))
                && (poCampo.getTipo() == JListDatos.mclTipoCadena)) {

            lsSQL.append('(');
            lsSQL.append(poCampo.getTamano());
            lsSQL.append(')');

        }
        if (mlTipo == mclSQLLite 
                && (poCampo.getTipo() == JListDatos.mclTipoFecha)) {
            lsSQL.append("(20)");//yyyy-MM-dd hh:mm:ss
        }
        
        if (mlTipo == mclDerby) {
            lsSQL.append((poCampo.getNullable() && (!poCampo.getPrincipalSN())) ? " " : " NOT NULL ");
        } else {
            lsSQL.append((poCampo.getNullable() && (!poCampo.getPrincipalSN())) ? " NULL " : " NOT NULL ");
        }
        return lsSQL.toString();
    }

    public String getTabla(final int plTipo, final JTableDef poTabla) throws ExceptionNoImplementado {
        StringBuilder lsCrear = null;
        switch (plTipo) {
            case JListDatos.mclNuevo:
                lsCrear = new StringBuilder(100);
                lsCrear.append("create table ");
                lsCrear.append(msTabla(poTabla.getNombre(), null));
                lsCrear.append('(');
                StringBuilder lsPrincipal = new StringBuilder();
                //creamos los campos y sus tipos
                for (int i = 0; i < poTabla.getCampos().count(); i++) {
                    //creamos el campo
                    lsCrear.append(msEstructuraCampo(poTabla.getCampos().get(i)));
                    lsCrear.append(',');
                    //componemos la clave principal
                    if (poTabla.getCampos().get(i).getPrincipalSN()) {
                        lsPrincipal.append(msCampo(JSelectCampo.mclFuncionNada, null, poTabla.getCampos().get(i).getNombre()));
                        lsPrincipal.append(',');
                    }

                }
                //sorprendentemente si quito el ult. coma q sobra casca
//                if(lsCrear.charAt(lsCrear.length()-1) == ','){
//                    lsCrear.setCharAt(lsCrear.length()-1, ' ');
//                }
                //creamos la primary key
                if (lsPrincipal.length() > 0) {
                    lsCrear.append(" PRIMARY KEY ( ");
                    lsCrear.append(lsPrincipal.toString().substring(0, lsPrincipal.length() - 1));
                    lsCrear.append("))");
                } else {
                    if (lsCrear.charAt(lsCrear.length() - 1) == ',') {
                        lsCrear.setCharAt(lsCrear.length() - 1, ' ');
                    }
                    lsCrear.append(")");
                }
                break;
            case JListDatos.mclBorrar:
                lsCrear = new StringBuilder(20);
                lsCrear.append(" DROP TABLE ");
                lsCrear.append(msTabla(poTabla.getNombre(), null));
                break;
            case JListDatos.mclEditar:
                throw new ExceptionNoImplementado();
            default:
                throw new ExceptionNoImplementado();
        }
        return (lsCrear.length() == 0 ? null : lsCrear.toString());
    }

    public String getIndice(final int plTipo, final JIndiceDef poIndice, final JTableDef poTabla) throws ExceptionNoImplementado {
        StringBuilder lsResult = new StringBuilder();

        switch (plTipo) {
            case JListDatos.mclNuevo:
                if (poIndice.getEsPrimario()) {
                    lsResult.append(" ALTER TABLE ");
                    lsResult.append(msTabla(poTabla.getNombre(), null));
                    lsResult.append(" ADD CONSTRAINT ");
                    lsResult.append(msTabla(poIndice.getNombreIndice(), null));
                    lsResult.append(" PRIMARY KEY ");
                    lsResult.append("(");
                    for (int i = 0; i < poIndice.getCountCamposIndice(); i++) {
                        lsResult.append(msCampo(JSelectCampo.mclFuncionNada, null, poIndice.getCampoIndice(i)));
                        lsResult.append(',');
                    }
                    lsResult.setLength(lsResult.length() - 1);
                    lsResult.append(")");
                } else {
                    lsResult.append(" CREATE ");
                    if (poIndice.getEsUnico()) {
                        lsResult.append(" UNIQUE ");
                    }
                    lsResult.append(" INDEX ");
                    lsResult.append(msTabla(poIndice.getNombreIndice(), null));
                    lsResult.append(" ON ");
                    lsResult.append(msTabla(poTabla.getNombre(), null));
                    lsResult.append("(");
                    for (int i = 0; i < poIndice.getCountCamposIndice(); i++) {
                        lsResult.append(msCampo(JSelectCampo.mclFuncionNada, null, poIndice.getCampoIndice(i)));
                        lsResult.append(',');
                    }
                    lsResult.setLength(lsResult.length() - 1);
                    lsResult.append(")");
                }

                break;
            case JListDatos.mclBorrar:
                if (poIndice.getEsPrimario()) {
                    lsResult.append(" ALTER TABLE ");
                    lsResult.append(msTabla(poTabla.getNombre(), null));
                    lsResult.append(" DROP CONSTRAINT ");
                    lsResult.append(msTabla(poIndice.getNombreIndice(), null));
                } else {
                    lsResult.append(" DROP INDEX ");
                    lsResult.append(msTabla(poIndice.getNombreIndice(), null));
                    if(mlTipo!=mclPostGreSQL){//en posgresql no hay que hacer referencia a la tabla
                        lsResult.append(" ON ");
                        lsResult.append(msTabla(poTabla.getNombre(), null));
                    }
                }
                break;
//            case JListDatos.mclEditar:
//                lsResult.append(getIndice(mclTipoBorrar, poIndice, poTabla));
//                lsResult.append("\n");
//                lsResult.append(" GO ");
//                lsResult.append("\n");
//                lsResult.append(getIndice(mclTipoADD, poIndice, poTabla));
//                lsResult.append("\n");
//                lsResult.append(" GO ");
//                lsResult.append("\n");
//                break;
//no funciona
            default:
                throw new ExceptionNoImplementado();
        }
        return (lsResult.length() == 0 ? null : lsResult.toString());
    }

    public String getRelacion(final int plTipo, final JRelacionesDef poRelacion, final JTableDef poTabla1, final JTableDef poTabla2) throws ExceptionNoImplementado {
        StringBuilder lsResult = new StringBuilder();

        if (poRelacion.getTipoRelacion() == JRelacionesDef.mclRelacionImport) {
            switch (plTipo) {
                case JListDatos.mclNuevo:

                    lsResult.append(" ALTER TABLE ");
                    lsResult.append(msTabla(poTabla1.getNombre(), null));
                    lsResult.append(" ADD CONSTRAINT ");
                    //nombre relac
                    lsResult.append(msTabla(poRelacion.getNombreRelacion(), null));
                    lsResult.append(" FOREIGN KEY ");
                    //campos tabla
                    lsResult.append("(");
                    for (int i = 0; i < poRelacion.getCamposRelacionCount(); i++) {
                        lsResult.append(msCampo(JSelectCampo.mclFuncionNada, null, poRelacion.getCampoPropio(i)));
                        lsResult.append(',');
                    }
                    lsResult.setLength(lsResult.length() - 1);
                    lsResult.append(")");

                    lsResult.append(" REFERENCES ");
                    //tabla externa
                    lsResult.append(msTabla(poRelacion.getTablaRelacionada(), null));
                    //campos externos
                    lsResult.append("(");
                    for (int i = 0; i < poRelacion.getCamposRelacionCount(); i++) {
                        lsResult.append(msCampo(JSelectCampo.mclFuncionNada, null, poRelacion.getCampoRelacion(i)));
                        lsResult.append(',');
                    }
                    lsResult.setLength(lsResult.length() - 1);
                    lsResult.append(")");
                    //atributos
                    if (mlTipo == mclSqlServer || mlTipo == mclPostGreSQL) {
                        if (poRelacion.getUpdate() == poRelacion.mclimportedKeyCascade) {
                            lsResult.append(" ON UPDATE CASCADE ");
                        }
                        if (poRelacion.getDelete() == poRelacion.mclimportedKeyCascade) {
                            lsResult.append(" ON DELETE CASCADE ");
                        }
                    }
                    break;
                case JListDatos.mclBorrar:
                    lsResult.append(" ALTER TABLE ");
                    lsResult.append(msTabla(poTabla1.getNombre(), null));
                    lsResult.append(" DROP CONSTRAINT ");
                    lsResult.append(msTabla(poRelacion.getNombreRelacion(), null));
                    break;
//            case JListDatos.mclEditar:
//                lsResult.append(getIndice(mclTipoBorrar, poIndice, poTabla));
//                lsResult.append("\n");
//                lsResult.append(" GO ");
//                lsResult.append("\n");
//                lsResult.append(getIndice(mclTipoADD, poIndice, poTabla));
//                lsResult.append("\n");
//                lsResult.append(" GO ");
//                lsResult.append("\n");
//                break;
//no funciona
                default:
                    throw new ExceptionNoImplementado();
            }
        } else {
            throw new ExceptionNoImplementado();
        }
        
        return (lsResult.length() == 0 ? null : lsResult.toString());
    }

    public String getCampo(final int plTipo, final JFieldDef poCampo, final JTableDef poTabla) throws ExceptionNoImplementado {
        StringBuilder lsSQL = new StringBuilder(40);
        switch (plTipo) {
            case JListDatos.mclNuevo:
                lsSQL.append(" ALTER TABLE ");
                lsSQL.append(msTabla(poTabla.getNombre(), null));
                lsSQL.append(" ADD ");
                lsSQL.append(msEstructuraCampo(poCampo));
                break;
            case JListDatos.mclBorrar:
                lsSQL.append(" ALTER TABLE ");
                lsSQL.append(msTabla(poTabla.getNombre(), null));
                lsSQL.append(" DROP COLUMN ");
                lsSQL.append(msCampo(JSelectCampo.mclFuncionNada, null, poCampo.getNombre()));
                break;
            case JListDatos.mclEditar:
                lsSQL.append(" ALTER TABLE ");
                lsSQL.append(msTabla(poTabla.getNombre(), null));
                if (mlTipo == mclMySQL) {
                    lsSQL.append(" MODIFY ");
                } else {
                    lsSQL.append(" ALTER COLUMN ");
                }
                if (mlTipo == mclPostGreSQL) {
                    lsSQL.append(msCampo(JSelectCampo.mclFuncionNada, null, poCampo.getNombre()));
                    lsSQL.append(" TYPE ");
                    lsSQL.append(msTipo(poCampo.getTipo(), poCampo.getTamano()));
                    lsSQL.append(' ');
                    if ((poCampo.getTamano() > 0)
                            && (poCampo.getTamano() < 4000)
                            && (poCampo.getTipo() == JListDatos.mclTipoCadena)) {

                        lsSQL.append('(');
                        lsSQL.append(poCampo.getTamano());
                        lsSQL.append(')');

                    }
                }else{
                    lsSQL.append(msEstructuraCampo(poCampo));
                }
                break;
            default:
                throw new ExceptionNoImplementado();
        }
        return lsSQL.toString();
    }

    public String msSelectPaginado(String psCampos, String psFrom, String psWhere, String psGroup, String psHaving, String psOrder, String pagina_actual, String elem_per_page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
