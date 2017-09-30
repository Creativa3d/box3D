/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JSTabla;
import ListDatos.estructuraBD.JFieldDef;
import utilesGUIx.JGUIxConfigGlobalModelo;

/**
 *
 * @author eduardo
 */
public class JTFiltro extends JSTabla {
    public static final String mcsY = "Y";
    public static final String mcsO = "O";

    public static final String mcsComo = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Contenga");
    public static final String mcsIgual = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Igual");
    public static final String mcsDistinto = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Distinto");
    public static final String mcsMayor = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Mayor");
    public static final String mcsMayorIgual = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Mayor Igual");
    public static final String mcsMenor = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Menor");
    public static final String mcsMenorIgual = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Menor Igual");
    public static final String mcsMasOMenos = JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto("Mas o menos");

    public static final int lPosiCodigo = 0;
    public static final int lPosiNombre = 1;
    public static final int lPosiComparacion = 2;
    public static final int lPosiValor = 3;
    public static final int lPosiUnion = 4;
    public static final int lPosiDuplicadoSN = 5;
    public static final int lPosiVisibleSN = 6;

    private static final String mcsCodigo = "Código";
    private static final String mcsNombre = "Nombre";
    private static final String mcsComparacion = "Comparación";
    private static final String mcsValor = "Valor";
    private static final String mcsUnion = "Unión";
    private static final String mcsDuplicadoSN = "Es Duplicado?";
    private static final String mcsVisibleSN = "Es Visible?";

    public JTFiltro() {
        moList = new JListDatos(null,
                "",
                new String[]{mcsCodigo, mcsNombre, mcsComparacion, mcsValor, mcsUnion, mcsDuplicadoSN, mcsVisibleSN},
                new int[]{JListDatos.mclTipoNumero, JListDatos.mclTipoCadena, JListDatos.mclTipoCadena, JListDatos.mclTipoCadena, JListDatos.mclTipoCadena, JListDatos.mclTipoBoolean, JListDatos.mclTipoBoolean},
                new int[]{0},
                new String[]{
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(mcsCodigo),
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(mcsNombre),
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(mcsComparacion),
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(mcsValor),
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(mcsUnion),
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(mcsDuplicadoSN),
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getTexto(mcsVisibleSN)
                }
        );
        moList.getFields(lPosiCodigo).setEditable(false);
        moList.getFields(lPosiNombre).setEditable(false);
        moList.getFields(lPosiDuplicadoSN).setEditable(false);
        moList.getFields(lPosiVisibleSN).setEditable(false);
    }
    
    public JFieldDef getCodigo(){
        return moList.getFields(lPosiCodigo);
    }
    public JFieldDef getComparacion(){
        return moList.getFields(lPosiComparacion);
    }
    public JFieldDef getNombre(){
        return moList.getFields(lPosiNombre);
    }
    public JFieldDef getValor(){
        return moList.getFields(lPosiValor);
    }
    public JFieldDef getUnion(){
        return moList.getFields(lPosiUnion);
    }
    public JFieldDef getDuplicadoSN(){
        return moList.getFields(lPosiDuplicadoSN);
    }
    public JFieldDef getVisibleSN(){
        return moList.getFields(lPosiVisibleSN);
    }
    
    public void limpiar() throws ECampoError{
        if(moList.moveFirst()){
            do{
                getValor().setValue("");
                getComparacion().setValue(mcsComo);
                getUnion().setValue(mcsY);
                update(false);
            }while(moList.moveNext());
        }
        moList.getFiltro().addCondicionAND(JListDatos.mclTIgual, lPosiDuplicadoSN, JListDatos.mcsTrue);
        moList.filtrar();
        while(moList.moveFirst()){
            moList.borrar(false);
        }
        moList.getFiltro().Clear();
        moList.filtrarNulo();
        
    }

    public void ordenar() {
        moList.ordenar(lPosiDuplicadoSN);
        moList.ordenar(lPosiCodigo);
        
    }

    public JListDatosFiltroConj getFiltro() {
        String lsUnion = mcsY;
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        if (moveFirst()) {
            boolean lbPrim = true;
            do {

                //si el valor es distinto de nulo o la comparacion es igual o distinto (si tiene sentido por ejem. igual a nulo)
                if (!getValor().toString().equals("")
                        || getComparacion().toString().equals(mcsDistinto)
                        || getComparacion().toString().equals(mcsIgual)) {
                    //sacamos la union de la fila con datos anterior
                    int lUnion = JListDatosFiltroConj.mclAND;
                    if (!lbPrim) {
                        if (lsUnion.compareTo(mcsO) == 0) {
                            lUnion = JListDatosFiltroConj.mclOR;
                        }
                    }
                    lbPrim = false;
                    //sacamos la comparacion
                    int lComparacion = JListDatos.mclTLike;
                    String lsComp = getComparacion().toString();
                    if (lsComp.compareTo(mcsDistinto) == 0) {
                        lComparacion = JListDatos.mclTDistinto;
                    }
                    if (lsComp.compareTo(mcsIgual) == 0) {
                        lComparacion = JListDatos.mclTIgual;
                    }
                    if (lsComp.compareTo(mcsMayor) == 0) {
                        lComparacion = JListDatos.mclTMayor;
                    }
                    if (lsComp.compareTo(mcsMayorIgual) == 0) {
                        lComparacion = JListDatos.mclTMayorIgual;
                    }
                    if (lsComp.compareTo(mcsMenor) == 0) {
                        lComparacion = JListDatos.mclTMenor;
                    }
                    if (lsComp.compareTo(mcsMenorIgual) == 0) {
                        lComparacion = JListDatos.mclTMenorIgual;
                    }

                    //Añadimos la comparacion al filtro conjunto
                    if (lsComp.compareTo(mcsMasOMenos) == 0) {
                        loFiltro.addCondicion(
                                lUnion,
                                new utilesLogicaDifusa.JListDatosFiltroElemLD(
                                        80, true,
                                        getCodigo().getInteger(),
                                        getValor().getString()));
                    } else {
                        loFiltro.addCondicion(
                                lUnion, lComparacion,
                                new int[]{getCodigo().getInteger()},
                                new String[]{getValor().getString()});
                    }

                    //ponemos la union para la siguiente condicion
                    lsUnion = getUnion().getString();
                }
            } while (moveNext());
        }

        return loFiltro;
    }
    
}
