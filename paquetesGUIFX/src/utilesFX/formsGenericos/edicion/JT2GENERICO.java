/*
 * JT2ACU.java
 *
 * Created on 4 de noviembre de 2004, 9:12
 */

package utilesFX.formsGenericos.edicion;


import ListDatos.*;
import utilesFX.formsGenericos.JT2GENERALBASE2;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.cargaMasiva.ICargaMasiva;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
//import utilesGUIx.formsGenericos.edicion.JPanelEDICIONGENERICO;
import utilesGUIx.panelesGenericos.JConsulta;

public class JT2GENERICO extends JT2GENERALBASE2 implements ICargaMasiva {
    
    private final IMostrarPantalla moMostrarPantalla;
    private final IConsulta moConsulta;
    private final JSTabla moTABLA;
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
        JListDatos loList = moTABLA.moList.Clone();
        loList.addNew();
        
//        JPanelEDICIONGENERICO loPanel = new JPanelEDICIONGENERICO();
//        loPanel.setDatos(loList, this);
//
//        moMostrarPantalla.mostrarEdicion(loPanel, loPanel);
    }
    
    private boolean buscarEnTabla() throws Exception {
        if(mbEnCache){
            moTABLA.recuperarTodosNormalCache();
            moTABLA.moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND,
                    JListDatos.mclTIgual,
                    moTABLA.moList.getFields().malCamposPrincipales(),
                    moConsulta.getList().getFields().masCamposPrincipales());
            moTABLA.moList.filtrar();
        }else{
            String[] lsValores = new String[moTABLA.moList.getFields().malCamposPrincipales().length];
            for(int i = 0; i < lsValores.length; i++){
                lsValores[i] = moConsulta.getList().getFields().get(i).getString();
            }
            moTABLA.recuperarFiltradosNormal(new JListDatosFiltroElem(JListDatos.mclTIgual, moTABLA.moList.getFields().malCamposPrincipales(), lsValores), false);
        }

        return moTABLA.moList.moveFirst();
    }
    
    public void borrar(int plIndex) throws Exception {
        moConsulta.getList().setIndex(plIndex);
        
        if(buscarEnTabla()){
            IFilaDatos loFila = moConsulta.getList().moFila();

            IResultado loResult = moTABLA.moList.borrar(true);
            if(!loResult.getBien()){
                throw new Exception("No se ha pod�do borrar el registro");
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
            JListDatos loList = moTABLA.moList.Clone();
            
//            JPanelEDICIONGENERICO loPanel = new JPanelEDICIONGENERICO();
//            loPanel.setDatos(loList, this);
//        
//            moMostrarPantalla.mostrarEdicion(loPanel, loPanel);
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

    @Override
    public JListDatos getTablaBase() {
        try {
            return JUtilTabla.clonarFilasListDatos(moTABLA.getList(), false);
        } catch (Exception ex) {
            return null;
        }
    }
    
}
