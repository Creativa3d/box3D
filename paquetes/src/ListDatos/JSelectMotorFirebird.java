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


import utiles.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Types;
import java.util.Iterator;

/**
 * Construye select de distintas bases de datos a partir del Objeto JSelect
 */
public final class JSelectMotorFirebird implements ISelectMotor, Serializable {

    private static final long serialVersionUID = 33333319L;
    /** Constante tipo de base de datos access */
    /**
     *Contructor
     */
    public JSelectMotorFirebird() {
        super();
    }

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
                lsAlmoadilla = "'";
                break;
            default:
        }
        return lsAlmoadilla;
    }

    private String msTrue() {
        return "1";
    }

    private String msFalse() {
        return "0";
    }

    public String msCondicion(final String psCampo, final int plCond, final String psValor, final String psCampo2, final int plTipo) {
        StringBuilder lsCadena = new StringBuilder();

        if (psCampo2 == null) {
            if ((psValor == null || psValor.compareTo("") == 0)) {
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
                        lsCadena.append(replace2(psValor, msAlmoadilla(plTipo), msAlmoadilla(plTipo) + msAlmoadilla(plTipo)));
                        lsCadena.append(msAlmoadilla(plTipo));
                        break;
                    case JListDatos.mclTipoFecha:
                        try {
                            lsCadena.append(msAlmoadilla(plTipo));
                            lsCadena.append((new JDateEdu(psValor)).msFormatear(JFormat.mcsMMddyyyyHHmmss));
                            lsCadena.append(msAlmoadilla(plTipo));

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
        lsCadena.append(psParte1.getFrom());

        if (poUnion.getCampos1() == null || poUnion.getCampos1().length == 0) {
            lsCadena.insert(0, msTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()) + ',');
            loFromParte = new JSelectFromParte(lsCadena.toString(), null);
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
        return loFromParte;
    }

    public String msTabla(final String psTabla, final String psTablaAlias) {
        String lsTabla = null;
        String lsComodinA = "";
        String lsComodinC = "";
        String lsSeparadorAlias = " ";
        lsComodinA = "";
        lsComodinC = "";
        lsSeparadorAlias = " AS ";
        lsTabla = lsComodinA + psTabla + lsComodinC;
        if (psTablaAlias != null && !psTablaAlias.equals("")) {
            lsTabla = lsTabla + lsSeparadorAlias + lsComodinA + psTablaAlias + lsComodinC;
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
        return msListaCampos(0, poCampos);
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
                lsSQL = "execute procedure " + lsSQL + "";
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

    public void pasarParametros(final String psTabla, final JFieldDefs poCampos, final int plTipoModif, final PreparedStatement loSent) throws java.sql.SQLException {
        //Pasamos parametros
        int lParam = 1;
        if (plTipoModif == JListDatos.mclNuevo
                || plTipoModif == JListDatos.mclEditar
                || plTipoModif == JListDatos.mclComando) {
            //pasamos los valores de los campos
            for (int i = 0; i < poCampos.count(); i++) {
                if (!(plTipoModif == JListDatos.mclEditar && poCampos.get(i).getPrincipalSN())) {
                    switch (poCampos.get(i).getTipoSQL()) {
                        case Types.TIME:
                        case Types.DATE:
                        case Types.TIMESTAMP:
                            java.util.Date lod = (java.util.Date) poCampos.get(i).getValueSQL();
                            if (lod instanceof Time) {
                                loSent.setTime(lParam++, (java.sql.Time) poCampos.get(i).getValueSQL());
                            } else {
                                loSent.setTimestamp(lParam++, (java.sql.Timestamp) poCampos.get(i).getValueSQL());
                            }
                            break;
                        case Types.LONGVARCHAR:
                            if (poCampos.get(i).isVacio()) {
                                loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
                            } else {
                                CadenaLarga loCadenaLaga = new CadenaLarga(poCampos.get(i).getString());
                                loSent.setAsciiStream(lParam++, loCadenaLaga, loCadenaLaga.msCadena.length());
                            }
                            break;
                        case Types.BIT:
                            loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
                            break;
                        default:
                            loSent.setObject(lParam++, poCampos.get(i).getValueSQL(), poCampos.get(i).getTipoSQL());
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

    public String msSelectPaginado(String psCampos, String psFrom, String psWhere, String psGroup, String psHaving, String psOrder,
            String pagina_actual, String elem_per_page) {


        if (pagina_actual==null || "".equals(pagina_actual)) {
            return msSelect(psCampos, psFrom, psWhere, psGroup, psHaving, psOrder);
        }
//        if (pagina_actual==null || "".equals(pagina_actual)) {
//            pagina_actual = "0";
//            elem_per_page = "100";
//
//        }

        int l_pagina_actual = Integer.parseInt(pagina_actual);
        int l_elem_per_page = Integer.parseInt(elem_per_page == null ? "1000" : elem_per_page);

        int lLen = psFrom.length() + 40;
        if (psCampos != null) {
            lLen += psCampos.length();
        }
        StringBuilder loCadena = new StringBuilder(lLen);

        loCadena.append(" Select ");
        loCadena.append(" first ").append(l_elem_per_page+1).append(" skip ").append( l_pagina_actual*l_elem_per_page ).append(" ");
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
        lsComodinA = "";
        lsComodinC = "";
        lsSeparadorAlias = " AS ";
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
//                lsTipo = "numeric(15,3)";
                break;
            case JListDatos.mclTipoMoneda:
                lsTipo = "number";
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                lsTipo = "number";
//                lsTipo = "numeric(15,3)";
                break;
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
        return lsTipo;
    }

    private String msEstructuraCampo(final JFieldDef poCampo) {
        StringBuilder lsSQL = new StringBuilder(40);

        lsSQL.append(msCampo(JSelectCampo.mclFuncionNada, null, poCampo.getNombre()));
        lsSQL.append(' ');
        lsSQL.append(msTipo(poCampo.getTipo(), poCampo.getTamano()));
        lsSQL.append(' ');
        if ((poCampo.getTamano() > 0)
                && (poCampo.getTamano() < 4000)
                && (poCampo.getTipo() == JListDatos.mclTipoCadena)) {

            lsSQL.append('(');
            lsSQL.append(poCampo.getTamano());
            lsSQL.append(')');

        }
        lsSQL.append((poCampo.getNullable() && (!poCampo.getPrincipalSN())) ? " NULL " : " NOT NULL ");
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
                    lsResult.append(" ON ");
                    lsResult.append(msTabla(poTabla.getNombre(), null));
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
        throw new ExceptionNoImplementado();
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
                lsSQL.append(" ALTER COLUMN ");
                lsSQL.append(msEstructuraCampo(poCampo));
                break;
            default:
                throw new ExceptionNoImplementado();
        }
        return lsSQL.toString();
    }
}
