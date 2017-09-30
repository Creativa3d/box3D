/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecTipo;

import ListDatos.JSTabla;
import javax.swing.ImageIcon;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.JT2GENERALBASE2;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesx.JEjecutar;

/**
 * ejecutar un directorio de ficheros , como rom de juegos
 * @author eduardo
 */
public class JT2Listado extends JT2GENERALBASE2 {
    private static String mcsEjecutar = "Ejecutar";
    private static String mcsOpciones = "Opciones";
    
    private JFORMLISTADO moConsulta;
    private final JDatosGeneralesXML moDatosGene;

    /** Crea una nueva instancia de JT2PROV */
    public JT2Listado() throws Exception {
        super();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre("Listado");
        
        moDatosGene = new JDatosGeneralesXML("opciones");
        moDatosGene.leer();
        moConsulta = new JFORMLISTADO(moDatosGene);
        
    }

    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void anadir() throws Exception {
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        utilesx.JEjecutar loEje = new JEjecutar(new String[]{
            moDatosGene.getPropiedad(JDatosGenerales.mcsRutaEje)
                , moConsulta.getARCHIVO().getString()
            });
        Thread loThread = new Thread(loEje);
        loThread.start();
    }

    public String getNombre() {
        return "Listado";
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[JFORMLISTADO.mclNumeroCampos];

        loInt[JFORMLISTADO.lPosiARCHIVO]=0;
        loInt[JFORMLISTADO.lPosiDESCRIPCION]=0;
        loInt[JFORMLISTADO.lPosiNOMBRE]=200;
        return loInt;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if(e.getActionCommand().equals(mcsOpciones)){
            JFormOpciones loSelect = new JFormOpciones();
            loSelect.setDatos(this, moDatosGene);
            loSelect.setVisible(true);
        }else{
            if(plIndex.length>0){
                for(int i = 0 ; i < plIndex.length; i++){
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
        }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        
        retValue.getEditar().setActivo(true);
        retValue.getNuevo().setActivo(false);
        retValue.getBorrar().setActivo(false);
        
        retValue.getEditar().setCaption(mcsEjecutar);
        retValue.getEditar().setIcono(new ImageIcon(getClass().getResource("/ejecTipo/Play24.gif")));
        
        
        
        
//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsEjecutar, mcsEjecutar, "/ejecTipo/Play24.gif", this));
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsOpciones, mcsOpciones, "/ejecTipo/Preferences24.gif", this));
        
    }

    public void mostrarFormPrinci() throws Exception {
    }

}
