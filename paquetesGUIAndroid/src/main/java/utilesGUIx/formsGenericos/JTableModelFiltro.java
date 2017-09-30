/*
 * JTableModelFiltro.java
 *
 * Created on 5 de septiembre de 2005, 14:07
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilesGUIx.formsGenericos;


import ListDatos.*;
import android.view.View;
import utilesGUI.tabla.ITabla;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;

public class JTableModelFiltro implements ITabla {
    
    public static final String mcsCodigo = "Código";
    public static final String mcsNombre = "Nombre";
    public static final String mcsComparacion = "Comparación";
    public static final String mcsValor = "Valor";
    public static final String mcsUnion = "Unión";
    public static final String mcsDuplicadoSN = "Es Duplicado?";
    
    public static final int mclCodigo = 0;
    public static final int mclNombre = 1;
    public static final int mclComparacion = 2;
    public static final int mclValor = 3;
    public static final int mclUnion = 4;
    public static final int mclDuplicadoSN = 5;
    
    public static final String mcsY = "Y";
    public static final String mcsO = "O";
    
    public static final String mcsComo = "Contenga";
    public static final String mcsIgual = "Igual";
    public static final String mcsDistinto = "Distinto";
    public static final String mcsMayor = "Mayor";
    public static final String mcsMayorIgual = "Mayor Igual";
    public static final String mcsMenor = "Menor";
    public static final String mcsMenorIgual = "Menor Igual";
    public static final String mcsMasOMenos = "Mas o menos";

    
    
    private final String[] lsNames = { mcsCodigo, mcsNombre, mcsComparacion, mcsValor, mcsUnion,mcsDuplicadoSN}; 
    private Object[][] moData;
    private final ActionListenerCZ moEscucha;
    private boolean mbCambiosActivos = true;
    
    public JTableModelFiltro(final Object[][] poData, final ActionListenerCZ poEscucha){
        super();
        moData = poData;
        moEscucha = poEscucha;
    }
    public void setDatos(final Object[][] poData){
        moData = poData;
    }
    public int getColumnCount() { 
        return lsNames.length; 
    } 
    public int getRowCount() { 
        return moData.length;
    } 
    public Object getValueAt(final int row, final int col) {
        return moData[row][col];
    } 
    public String getColumnName(final int column) {
        return lsNames[column];
    } 
    public Class getColumnClass(final int c) {
        return getValueAt(0, c).getClass();
    } 
    public boolean isCellEditable(final int row, final int col) {
        return !((col == mclCodigo)||(col == mclNombre)||(col == mclDuplicadoSN) );
    } 
    public void setValueAt(final Object aValue, final int row, final int column) { 
        moData[row][column] = aValue; 
        if(mbCambiosActivos){
            moEscucha.actionPerformed(new ActionEventCZ(this,row, JPanelGeneralFiltro.mcsCambioFiltro));
        }
    }
    
    public void duplicar(final int row) throws Exception {
        Object[][] loData = new Object[moData.length+1][moData[0].length];
	int i1 = 0;
	int i2 = 0;
        for(; i1 < loData.length;i1++,i2++){
            if(i2 == row){
                loData[i1] = moData[i2];
                i1++;
                loData[i1] = (Object[])moData[i2].clone();
                loData[i1][mclDuplicadoSN] = Boolean.TRUE;
            }else{
                loData[i1] = moData[i2];
            }
        }
        
        moData = loData;
    }
    public JListDatosFiltroConj getFiltro(){
        String lsUnion = mcsY;
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        for(int i = 0; i < getRowCount(); i++ ){
            if(getValueAt(i, mclValor)!=null){
                //si el valor es distinto de nulo o la comparacion es igual o distinto (si tiene sentido por ejem. igual a nulo)
                if(!getValueAt(i, mclValor).toString().equals("") ||
                    getValueAt(i, mclComparacion).toString().equals(mcsDistinto) ||
                    getValueAt(i, mclComparacion).toString().equals(mcsIgual)
                   ){
                    //sacamos la union de la fila con datos anterior
                    int lUnion = JListDatosFiltroConj.mclAND;
                    if(i != 0){
                        if(lsUnion.compareTo(mcsO)==0){
                            lUnion = JListDatosFiltroConj.mclOR;
                        }
                    }
                    //sacamos la comparacion
                    int lComparacion = JListDatos.mclTLike;
                    String lsComp = getValueAt(i, mclComparacion).toString();
                    if(lsComp.compareTo(mcsDistinto)==0 ){
                        lComparacion = JListDatos.mclTDistinto;
                    }
                    if(lsComp.compareTo(mcsIgual)==0 ){
                        lComparacion = JListDatos.mclTIgual;
                    }
                    if(lsComp.compareTo(mcsMayor)==0 ){
                        lComparacion = JListDatos.mclTMayor;
                    }
                    if(lsComp.compareTo(mcsMayorIgual)==0 ){
                        lComparacion = JListDatos.mclTMayorIgual;
                    }
                    if(lsComp.compareTo(mcsMenor)==0 ){
                        lComparacion = JListDatos.mclTMenor;
                    }
                    if(lsComp.compareTo(mcsMenorIgual)==0 ){
                        lComparacion = JListDatos.mclTMenorIgual;
                    }

                    //Anadimos la comparacion al filtro conjunto
                    if(lsComp.compareTo(mcsMasOMenos)==0 ){
                        loFiltro.addCondicion(
                            lUnion, 
                            new utilesLogicaDifusa.JListDatosFiltroElemLD(
                                80, true,
                                ((Integer)getValueAt(i, mclCodigo)).intValue(),
                                getValueAt(i, mclValor).toString()));
                    }else{
                        loFiltro.addCondicion(
                            lUnion, lComparacion,
                            new int[]{((Integer)getValueAt(i, mclCodigo)).intValue()},
                            new String[]{getValueAt(i, mclValor).toString()});
                    }
                    
                    //ponemos la union para la siguiente condicion
                    lsUnion = getValueAt(i, mclUnion).toString();
                }
            }
        }
        return loFiltro;
    }
    
    public int getFilaOrigenSinDuplicados(final int plFila){
        int lResult = -1;
        for(int i = 0 ; i < getRowCount()  && lResult == -1; i++){
            if(getValueAt(i, mclCodigo).toString().equals(String.valueOf(plFila))){
                lResult = i;
            }
        }
        return lResult; 
    }

    public void anularCambios() {
        mbCambiosActivos = false;
    }

    public void activarCambios() {
        mbCambiosActivos = true;
        moEscucha.actionPerformed(new ActionEventCZ(this,0, JPanelGeneralFiltro.mcsCambioFiltro));
    }

    public void sortByColumn(int plColumn, boolean pbAscendente) {
    }

    public View getComponent(int row, int col) {
        return null;
    }
    
}
