/*
 * JT2ACU.java
 *
 * Created on 4 de noviembre de 2004, 9:12
 */

package utilesGUIx.formsGenericos.edicion;


import ListDatos.*;
import utilesGUIx.ActionEventCZ;

import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.panelesGenericos.JConsulta;

public class JT2GENERICO extends JT2GENERALBASE2 {
    
    private final IMostrarPantalla moMostrarPantalla;
    protected final IConsulta moConsulta;
    protected final JSTabla moTABLA;
    private int mlAncho;
    private int mlAlto;
    private boolean mbEnCache = false;
    

    public JT2GENERICO(IMostrarPantalla poMostrarPantalla, JSTabla poTabla, JSTabla poConsulta) throws Exception {
        this(poMostrarPantalla, poTabla, poConsulta, false);
    }
    public JT2GENERICO(IMostrarPantalla poMostrarPantalla, JSTabla poTabla, JSTabla poConsulta, boolean pbEncache) throws Exception {
        mbEnCache = pbEncache;
        moMostrarPantalla = poMostrarPantalla;
        if(mbEnCache){
            poConsulta.recuperarTodosNormalCache();
        }
        moConsulta = new JConsulta(poConsulta, pbEncache);
        moTABLA = poTabla;
        mlAncho = 600;
        mlAlto = 500;
        getParametros().setNombre(moTABLA.moList.msTabla);
    }
    public JT2GENERICO(IMostrarPantalla poMostrarPantalla, JListDatos poTabla) throws Exception {
        mbEnCache = false;
        moMostrarPantalla = poMostrarPantalla;
        
        moConsulta = new JConsulta(poTabla, true);
        moTABLA = new JSTablaModelo(poTabla);
        mlAncho = 600;
        mlAlto = 500;
        getParametros().setNombre(moTABLA.moList.msTabla);
        getParametros().getBotonesGenerales().getRefrescar().setActivo(false);
    }
    
    
    public JT2GENERICO(IMostrarPantalla poMostrarPantalla, JSTabla poTabla, JSTabla poConsulta, int plAncho, int plAlto, boolean pbEnCache) throws Exception {
        this(poMostrarPantalla, poTabla, poConsulta, pbEnCache);
        mlAncho = plAncho;
        mlAlto = plAlto;
    }
    public JT2GENERICO(IMostrarPantalla poMostrarPantalla, JSTabla poTabla, JSTabla poConsulta, int plAncho, int plAlto) throws Exception {
        this(poMostrarPantalla,poTabla,poConsulta,false);
        mlAncho = plAncho;
        mlAlto = plAlto;
    }
    
    
    public void mostrarFormPrinci() throws Exception {
        moMostrarPantalla.mostrarFormPrinci(this, mlAncho,mlAlto);
    }
    
    public void anadir() throws Exception {
        moTABLA.moList.addNew();
        
        JPanelEDICIONGENERICOJSTable loPanel = new JPanelEDICIONGENERICOJSTable();
        loPanel.setDatos(moTABLA, this);

        moMostrarPantalla.mostrarEdicion(loPanel, loPanel);
    }
    
    private boolean buscarEnTabla() throws Exception {

        String[] lsValores = new String[moTABLA.moList.getFields().malCamposPrincipales().length];
        for(int i = 0; i < lsValores.length; i++){
            lsValores[i] = moConsulta.getList().getFields().get(i).getString();
        }
        moTABLA.recuperarFiltradosNormal(new JListDatosFiltroElem(JListDatos.mclTIgual, moTABLA.moList.getFields().malCamposPrincipales(), lsValores), false);

        return moTABLA.moList.moveFirst();
    }
    
    public void borrar(int plIndex) throws Exception {
        moConsulta.getList().setIndex(plIndex);
        
        if(buscarEnTabla()){
            IFilaDatos loFila = moConsulta.getList().moFila();

            IResultado loResult = moTABLA.moList.borrar(true);
            if(!loResult.getBien()){
                throw new Exception("No se ha podído borrar el registro");
            }
        
            loFila.setTipoModif (JListDatos.mclBorrar);
            datosactualizados(loFila);
        }else{
            throw new Exception("No existe la fila seleccionada");
        }

    }

    public void editar(int plIndex) throws Exception {
        moConsulta.getList().setIndex(plIndex);

        if(buscarEnTabla()){
            JPanelEDICIONGENERICOJSTable loPanel = new JPanelEDICIONGENERICOJSTable();
            loPanel.setDatos(moTABLA, this);
        
            moMostrarPantalla.mostrarEdicion(loPanel, loPanel);
        }else{
            throw new Exception("No existe la fila seleccionada");
        }
    }
    
    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        //vacio
    }
    
}
