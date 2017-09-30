/*
 * JPanelGeneralColores.java
 *
 * Created on 26 de marzo de 2007, 20:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.colores;

import ListDatos.IListDatosFiltro;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import utiles.FechaMalException;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JListaElementos;
import utilesGUIx.ColorCZ;
import utilesGUIx.ITableCZColores;

public class JPanelGenericoColores implements ITableCZColores {
    private IListaElementos moListaElementos = null;
    private JListDatos moDatos;
    private JCacheColor moCacheColor = new JCacheColor();
    
    /** Creates a new instance of JPanelGeneralColores */
    public JPanelGenericoColores(final JListDatos poDatos) {
        super();
        moDatos = poDatos;
    }
    public void setDatos(final JListDatos poDatos) {
        moDatos = poDatos;
    }
    
    private IListaElementos getElementos(){
        if(moListaElementos == null){
            moListaElementos = new JListaElementos();
        }
        return moListaElementos;
    }
    public JElementoColor addCondicion(int plComparacion,int plCampo1, int plCampo2, final ColorCZ poBack, final ColorCZ poFore){
        JElementoColor loResult = new JElementoColor(plComparacion,plCampo1, plCampo2, poBack, poFore);
        getElementos().add(loResult);
        return loResult;
    }
    
    public JElementoColor addCondicion(final IListDatosFiltro poCond, final ColorCZ poBack, final ColorCZ poFore){
        JElementoColor loResult = new JElementoColor(poCond, poBack, poFore);
        getElementos().add(loResult);
        return loResult;
    }

    private JElementoColor getElemento(final boolean isSelected, final int row, final int col){
        JElementoColor loResult = null;
        if(moListaElementos != null && !isSelected){
            JElementoColor loElem = moCacheColor.getElementoColor(row, col, getClave());
            if(loElem !=null){
                loResult = loElem;
            }else{
                if(row >=0 && row<moDatos.size()){
                    moDatos.setIndex(row);
                    boolean lbAlgunaEnColumna = false;
                    for(int i = 0; i < moListaElementos.size() && loResult == null; i++){
                        loElem = (JElementoColor)moListaElementos.get(i);
                        lbAlgunaEnColumna |= loElem.mlColumna!=-1;
                        if((loElem.mlColumna==-1 || loElem.mlColumna==col)){
                            if(loElem.moFiltro==null){
                                int lResult;
                                switch(moDatos.getFields().get(loElem.mlCampo1).getTipo()){
                                    case JListDatos.mclTipoBoolean:
                                    case JListDatos.mclTipoNumero:
                                    case JListDatos.mclTipoNumeroDoble:
                                    case JListDatos.mclTipoMoneda3Decimales:
                                    case JListDatos.mclTipoMoneda:
                                    case JListDatos.mclTipoPorcentual3Decimales:
                                    case JListDatos.mclTipoPorcentual:
                                        lResult =
                                                JConversiones.mlComparaDoubles(
                                                moDatos.getFields().get(loElem.mlCampo1).getDouble(),
                                                moDatos.getFields().get(loElem.mlCampo2).getDouble(),
                                                0.00001
                                                );
                                        break;
                                    case JListDatos.mclTipoCadena:
                                        lResult = moDatos.getFields().get(loElem.mlCampo1).toString().compareTo(moDatos.getFields().get(loElem.mlCampo2).toString());
                                        break;
                                    case JListDatos.mclTipoFecha:
                                        try {
                                            lResult = moDatos.getFields().get(loElem.mlCampo1).getDateEdu().compareTo(moDatos.getFields().get(loElem.mlCampo2).getDateEdu());
                                        } catch (FechaMalException ex) {
                                            lResult = 0;
                                        }
                                        break;
                                    default:
                                        lResult = moDatos.getFields().get(loElem.mlCampo1).toString().compareTo(moDatos.getFields().get(loElem.mlCampo2).toString());
                                }
                                boolean lbCorrecto = false;
                                switch(loElem.mlComparacion){
                                    case JListDatos.mclTIgual:
                                        lbCorrecto = lResult == 0;
                                        break;
                                    case JListDatos.mclTDistinto:
                                        lbCorrecto = lResult != 0;
                                        break;
                                    case JListDatos.mclTMayor:
                                        lbCorrecto = lResult > 0;
                                        break;
                                    case JListDatos.mclTMayorIgual:
                                        lbCorrecto = lResult >= 0;
                                        break;
                                    case JListDatos.mclTMenor:
                                        lbCorrecto = lResult < 0;
                                        break;
                                    case JListDatos.mclTMenorIgual:
                                        lbCorrecto = lResult <= 0;
                                        break;
                                }
                                if(lbCorrecto){
                                    loResult = loElem;
                                }
                            }else{
                                try{
                                    if(loElem.moFiltro.mbCumpleFiltro(moDatos.moFila())){
                                        loResult = loElem;
                                    }
                                }catch(Throwable e){
                                    loElem.moFiltro.inicializar(moDatos.msTabla, moDatos.getFields().malTipos(), moDatos.getFields().msNombres());
                                    if(loElem.moFiltro.mbCumpleFiltro(moDatos.moFila())){
                                        loResult = loElem;
                                    }
                                }
                            }
                        }
                    }
                    //por ahora si alguna condicion por columnas desactivamos la cache de colores
                    //ya q por defecto va por filas y en el momento q metes una fila siempre coje esa
                    if(!lbAlgunaEnColumna){
                        if(loResult==null){
                            moCacheColor.add(row, -1, getClave(), loResult);
                        }else{
                            moCacheColor.add(row, loResult.mlColumna, getClave(), loResult);
                        }
                    }
                }
            }
        }
        return loResult;
    }

    public ColorCZ getColorBackground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        JElementoColor loResult =getElemento(isSelected, row, column);
        if(loResult!=null){
            return loResult.moColorBackGround;
        }else{
            return null;
        }
    }
    private String getClave(){
        StringBuilder lsResult= new StringBuilder();
        for(int i = 0 ; i < moDatos.getFields().size();i++){
            lsResult.append(moDatos.getFields(i).getString());
            lsResult.append(JFilaDatosDefecto.mcsSeparacion1);
        }
        lsResult.append(moDatos.msSelect);
        return lsResult.toString();

    }

    public ColorCZ getColorForeground(final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        JElementoColor loResult =getElemento(false, row, column);
        if(loResult!=null){
            return loResult.moColorForeground;
        }else{
            return null;
        }
    }
    
}

