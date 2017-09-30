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
public class JSelectMotorNeutro implements ISelectMotor, Serializable {

    private static final long serialVersionUID = 33333319L;
    /** Constante tipo de base de datos access */
    public static final int mclNeutro = 100;
    /** tipo de base de datos por defectos access*/
    public int mlTipo = mclNeutro;
    public boolean mbCamposEnWhere=true;

    /**
     *Contructor
     */
    public JSelectMotorNeutro(boolean pbCamposEnWhere) {
        super();
        mbCamposEnWhere = pbCamposEnWhere;
    }

    /**
     * Contructor
     * @param plTipo tipo de base de datos
     */
    public JSelectMotorNeutro(final int plTipo, boolean pbCamposEnWhere) {
        super();
        mlTipo = plTipo;
    }

    /**
     * establece el tipo de base de datos
     * @param plTipo tipo de base de datos
     */
    public void setTipo(final int plTipo) {
        mlTipo = plTipo;
    }

    public String msAnd(final String psCond1, final String psCond2) {
        return "(" + psCond1 + ") Y (" + psCond2 + ")";
    }

    public String msOr(final String psCond1, final String psCond2) {
        return "(" + psCond1 + ") O (" + psCond2 + ")";
    }
    public String msNot(final String psCond1) {
        return "(" + psCond1 + ")";
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
                if (mlTipo == mclNeutro) {
                    lsAlmoadilla = "'";
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
        String lsTrue = "SI";
        return lsTrue;
    }

    private String msFalse() {
        String lsFalse = "NO";
        return lsFalse;
    }

    public String msCondicion(final String psCampo, final int plCond, final String psValor, final String psCampo2, final int plTipo) {
        StringBuilder lsCadena = new StringBuilder();

        if (psCampo2 == null) {
            if ((psValor == null || psValor.compareTo("") == 0)) {
                switch (plCond) {
                    case JListDatos.mclTIgual:
                        lsCadena.setLength(0);
                        if(mbCamposEnWhere){
                            lsCadena.append(psCampo);
                        }
                        lsCadena.append(" es vacio");
                        break;
                    case JListDatos.mclTDistinto:
                        lsCadena.setLength(0);
                        lsCadena.append("NO ");
                        if(mbCamposEnWhere){
                            lsCadena.append(psCampo);
                        }
                        lsCadena.append(" es vacio");
                        break;
                    default:
                        lsCadena.setLength(0);
                        if(mbCamposEnWhere){
                            lsCadena.append(psCampo);
                        }
                        lsCadena.append(msOperador(plCond));
                        lsCadena.append(msAlmoadilla(plTipo));
                        lsCadena.append(msAlmoadilla(plTipo));
                }
            } else {
                lsCadena.setLength(0);
                if(mbCamposEnWhere){
                    lsCadena.append(psCampo);
                }
                lsCadena.append(msOperador(plCond));
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        if (psValor.equals(JListDatos.mcsTrue) ||
                            psValor.equalsIgnoreCase(msTrue())) {
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
                        String lsFormato = "";
                        try {
                            switch (mlTipo) {
                                default:
                                    lsCadena.append(msAlmoadilla(plTipo) +
                                            (new JDateEdu(psValor)).msFormatear(JFormat.mcsddMMyyyyHHmmss) +
                                            msAlmoadilla(plTipo));
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
            if(mbCamposEnWhere){
                lsCadena.append(psCampo);
            }
            lsCadena.append(msOperador(plCond));
            lsCadena.append(psCampo2);
        }
        return lsCadena.toString();
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
                lsCadena = " <> ";
                break;
            case JListDatos.mclTIgual:
                lsCadena = " = ";
                break;
            case JListDatos.mclTMayor:
                lsCadena = " > ";
                break;
            case JListDatos.mclTMayorIgual:
                lsCadena = " >= ";
                break;
            case JListDatos.mclTMenor:
                lsCadena = " < ";
                break;
            case JListDatos.mclTMenorIgual:
                lsCadena = " <= ";
                break;
            case JListDatos.mclTLike:
                lsCadena = " COMO ";
                break;
            default:
                lsCadena = "";

        }
        return lsCadena;

    }
    private String getTabla(String psTabla, String psAlias){
        if(psAlias==null || psAlias.equals("")){
            return psTabla;
        }else{
            return psAlias;
        }

    }

    public JSelectFromParte msFromUnion(final JSelectFromParte psParte1, final JSelectUnionTablas poUnion) {
        StringBuilder lsCadena = new StringBuilder();
        StringBuilder lsWhere = new StringBuilder();
        JSelectFromParte loFromParte;
        if (psParte1.getFrom().indexOf("(") >= 0) {
            lsCadena.append('(');
            lsCadena.append(psParte1.getFrom());
            lsCadena.append(')');
        } else {
            lsCadena.append(psParte1.getFrom());
        }
        if(poUnion.getCampos1() == null || poUnion.getCampos1().length==0){
            lsCadena.insert(0,msTabla(poUnion.getTabla2(),poUnion.getTabla2Alias()) + ',');
            loFromParte = new JSelectFromParte(lsCadena.toString(), null);
        }else{
            String lsClave = "";
            switch (poUnion.getTipo()) {
                case JSelectUnionTablas.mclInner:
                    lsClave = " unido ";
                    break;
                case JSelectUnionTablas.mclLeft:
                    lsClave = " unido izquierda ";
                    break;
                case JSelectUnionTablas.mclRight:
                    lsClave = " unido derecha ";
                    break;
                default:
                    lsClave = "";
            }
            lsCadena.append(lsClave);
            lsCadena.append(msTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()));
            lsCadena.append(" por (");
            for (int i = 0; i < poUnion.getCampos1().length; i++) {
                lsCadena.append(msCampo(JSelectCampo.mclFuncionNada, poUnion.getTablaPrefijoCampos1(), poUnion.getCampos1()[i]));
                lsCadena.append(" = ");
                lsCadena.append(msCampo(JSelectCampo.mclFuncionNada, getTabla(poUnion.getTabla2(), poUnion.getTabla2Alias()), poUnion.getCampos2()[i]));
                lsCadena.append(" Y ");
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
        if (mlTipo == mclNeutro) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " COMO ";
        }
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
                lsCampos.append(" DISTINTO ");
            }
            Iterator loEnum = poCampos.iterator();
            for (; loEnum.hasNext();) {
                JSelectCampo loCampo = (JSelectCampo) loEnum.next();
                lsCampos.append(loCampo.msSQL(this));
                lsCampos.append(" , ");
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
                lsSQL = "ACTUALIZAR " + msTabla(psTabla, null) + " " + msCrearCampos(null, poCampos, plTipoModif) + " DONDE " + msCrearWhere(psTabla, poCampos, plTipoModif);
                break;
            case JListDatos.mclNuevo:
                lsSQL = "INSERTAR EN " + msTabla(psTabla, null) + " " + msCrearCampos(null, poCampos, plTipoModif);
                break;
            case JListDatos.mclBorrar:
                lsSQL = "BORRAR DE " + msTabla(psTabla, null) + (poCampos == null ? "" : " DONDE " + msCrearWhere(psTabla, poCampos, plTipoModif));
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

                lsCampos.append(" VALORES ");
                lsCampos.append(lsValores);
                break;
            case JListDatos.mclEditar:
                lsCampos.setLength(lsCampos.length() - 1);
                lsCampos.insert(0, "ESTABLECER ");
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
                if(mbCamposEnWhere){
                    lsWhere.append(msCampo(JSelectCampo.mclFuncionNada, psTabla, poCampos.get(lalCamposPrincipales[i]).getNombre()));
                }
                if (poCampos.get(lalCamposPrincipales[i]).isVacio()) {
                    lsWhere.append(" es Vacio Y ");
                } else {
                    lsWhere.append(" = ? Y ");
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
    }

    public String msSelect(final String psCampos, final String psFrom, final String psWhere, final String psGroup, final String psHaving, final String psOrder) {
//    moActualizar = null;
        int lLen = psFrom.length() + 40;
        if(psCampos!=null){
            lLen += psCampos.length();
        }
        StringBuilder loCadena = new StringBuilder(lLen);

        loCadena.append(" SELECCIONAR ");
        loCadena.append('\n');
        loCadena.append('\n');
        loCadena.append(psCampos);
        loCadena.append('\n');
        loCadena.append('\n');
        loCadena.append(" DE TABLAS ");
        loCadena.append('\n');
        loCadena.append('\n');
        loCadena.append(psFrom);
        loCadena.append('\n');
        loCadena.append('\n');
        loCadena.append((psWhere == null ? "" : " DONDE \n\n" + psWhere + "\n\n"));
        loCadena.append((psGroup == null ? "" : " AGRUPADOS POR " + psGroup + "\n"));
        loCadena.append((psHaving == null ? "" : " TENIENDO " + psHaving + "\n"));
        loCadena.append((psOrder == null ? "" : " ORDENADO POR " + psOrder + "\n"));

        return loCadena.toString();
    }

    public String msSelectUltMasUno(final String psTabla, final String psCampo, final int plActu) {
        return " SELECCIONAR MAXIMO(" + msCampo(JSelectCampo.mclFuncionNada, psTabla, psCampo) + ")+1" +
                " DE TABLAS " + msTabla(psTabla, null);
    }

    private String msCampo(String psCampo, String psCampoAlias){
        String lsResult;
        String lsComodinA = "";
        String lsComodinC = "";
        String lsSeparadorAlias = " AS ";
        if ((mlTipo == mclNeutro)) {
            lsComodinA = "";
            lsComodinC = "";
            lsSeparadorAlias = " COMO ";
        }
        lsResult = lsComodinA + psCampo + lsComodinC;
        if (psCampoAlias != null && !psCampoAlias.equals("")) {
            lsResult = lsResult + lsSeparadorAlias + lsComodinA + psCampoAlias + lsComodinC;
        }
        return lsResult;
    }

    public String msCampo(final int plFuncion, final String psTabla, final String psCampo) {
        String lsCampoResult = psCampo;
        String lsFuncion = "";
        switch (plFuncion) {
            case JSelectCampo.mclFuncionAvg:
                lsFuncion = "PROMEDIO";
                break;
            case JSelectCampo.mclFuncionCount:
                lsFuncion = "CUENTA";
                break;
            case JSelectCampo.mclFuncionMax:
                lsFuncion = "MAXIMO";
                break;
            case JSelectCampo.mclFuncionMin:
                lsFuncion = "MINIMO";
                break;
            case JSelectCampo.mclFuncionSum:
                lsFuncion = "SUMA";
                break;
            default:
                lsFuncion = "";
        }
        if (plFuncion != JSelectCampo.mclFuncionTalCual) {
            String lsCampo = null;
            if (psCampo.compareTo(JSelect.mcsTodosCampos) == 0) {
                lsCampo = (psTabla == null ? "" : msTabla(psTabla, null) + ".") + psCampo;
            } else {
                lsCampo = (psTabla == null ? "" : msTabla(psTabla, null) + ".") + msCampo(psCampo, null);
            }
            if (lsFuncion.compareTo("") != 0) {
                lsCampo = lsFuncion + "(" + lsCampo + ")";
            }
            lsCampoResult = lsCampo;
        }
        return lsCampoResult;

    }

    private String msTipo(final int plTipo, final int plTamano) {
        String lsTipo = "";
        switch (mlTipo) {
            case mclNeutro:
                switch (plTipo) {
                    case JListDatos.mclTipoBoolean:
                        lsTipo = "LOGICO";
                        break;
                    case JListDatos.mclTipoFecha:
                        lsTipo = "FECHA";
                        break;
//                    case JListDatos.mclTipoBinario:
//                        lsTipo = "BINARIO";
//                        break;
                    case JListDatos.mclTipoNumero:
                        lsTipo = "ENTERO";
                        break;
                    case JListDatos.mclTipoNumeroDoble:
                    case JListDatos.mclTipoMoneda3Decimales:
                    case JListDatos.mclTipoMoneda:
                    case JListDatos.mclTipoPorcentual3Decimales:
                    case JListDatos.mclTipoPorcentual:
                        lsTipo = "DECIMAL";
                        break;
                    default:
                        if ((plTamano > 255) || (plTamano <= 0)) {
                            lsTipo = "TEXTO_LARGO";
                        } else {
                            lsTipo = "TEXTO";
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
        if ((poCampo.getTamano() > 0) &&
                (poCampo.getTamano() < 4000) &&
                (poCampo.getTipo() == JListDatos.mclTipoCadena)) {

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
                lsCrear.append("CREAR TABLA ");
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
                    lsCrear.append(" CLAVES PRIMARIAS ( ");
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
                lsCrear.append(" BORRAR TABLA ");
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
        throw new ExceptionNoImplementado();
    }

    public String getRelacion(final int plTipo, final JRelacionesDef poRelacion, final JTableDef poTabla1, final JTableDef poTabla2) throws ExceptionNoImplementado {
        throw new ExceptionNoImplementado();
    }

    public String getCampo(final int plTipo, final JFieldDef poCampo, final JTableDef poTabla) throws ExceptionNoImplementado {
        StringBuilder lsSQL = new StringBuilder(40);
        switch (plTipo) {
            case JListDatos.mclNuevo:
                lsSQL.append(" MODIFICAR TABLA ");
                lsSQL.append(msTabla(poTabla.getNombre(), null));
                lsSQL.append(" AÑADIR ");
                lsSQL.append(msEstructuraCampo(poCampo));
                break;
            case JListDatos.mclBorrar:
                lsSQL.append(" MODIFICAR TABLE ");
                lsSQL.append(msTabla(poTabla.getNombre(), null));
                lsSQL.append(" BORRAR COLUMNA ");
                lsSQL.append(msCampo(JSelectCampo.mclFuncionNada, null, poCampo.getNombre()));
                break;
            case JListDatos.mclEditar:
                lsSQL.append(" MODIFICAR TABLA ");
                lsSQL.append(msTabla(poTabla.getNombre(), null));
                lsSQL.append(" MODIFICAR COLUMNA ");
                lsSQL.append(msEstructuraCampo(poCampo));
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
