/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

import java.util.EventObject;

public class JPanelGenericoEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public static final String mcsCambioFiltro = "CambioFiltro";
    public static final String mcsAccionRefrescar="Refrescar";
    public static final String mcsAccionConfig="Config";
    public static final String mcsAccionNuevo="Nuevo";
    public static final String mcsAccionEditar="Editar";
    public static final String mcsAccionBorrar="Borrar";
    public static final String mcsAccionAceptar="Aceptar";
    public static final String mcsAccionCancelar="Cancelar";
    public static final String mcsAccionPosicionarLinea="PosicionarLinea";
    public static final String mcsAccionFinInicializar="FinInicializar";
    public static final String mcsAccionMasFiltro="MasFiltros";
    public static final String mcsENTER = "ENTER";
    public static final String mcsESC = "ESC";
    
    
    public static final int mclAccionRefrescar=-1;
    public static final int mclAccionCambioFiltro = -2;
    public static final int mclAccionConfig=-3;
    public static final int mclAccionNuevo=-4;
    public static final int mclAccionEditar=-5;
    public static final int mclAccionBorrar=-6;
    public static final int mclAccionAceptar=-7;
    public static final int mclAccionCancelar=-8;
    public static final int mclAccionPosicionarLinea=-9;
    public static final int mclAccionFinInicializar=-10;
    

    public static final int mclRefrescar = 0;
    public static final int mclPosicionarLinea = 1;
    public static final int mclFinInicializar = 2;
    public static final int mclEnter = 3;
    public static final int mclError = 4;

    
    private IPanelGenerico moPanel;
    private int mlTipo;
    private int[] malRows;
    private String msComando;

    public JPanelGenericoEvent(final IPanelGenerico poPanel, final int plTipo,int[] palRows, String psComando){
        super(poPanel);
        moPanel = poPanel;
        mlTipo = plTipo;
        malRows = palRows;
        msComando = psComando;
        if((msComando==null || msComando.equals(""))){
            if(mlTipo==mclPosicionarLinea ){
                msComando = mcsAccionPosicionarLinea;
            }
            if(mlTipo==mclRefrescar){
                msComando = mcsAccionRefrescar;
            }
            if(mlTipo==mclFinInicializar){
                msComando = mcsAccionFinInicializar;
            }
        }
    }
    public JPanelGenericoEvent(final IPanelGenerico poPanel, final int plTipo,int[] palRows){
        this(poPanel, plTipo, palRows, "");
    }
    public IPanelGenerico getPanel(){
        return moPanel;
    }
    
    public int getTipoEvento(){
        return mlTipo;
    }
    public int[] getRow(){
        return malRows;
    }
    public String getActionCommand(){
        return msComando;
    }
}
