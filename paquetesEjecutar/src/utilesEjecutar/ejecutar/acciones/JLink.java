/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesEjecutar.ejecutar.acciones;

import java.io.File;
import utiles.IListaElementos;
import utiles.JArchivo;
import utiles.JDepuracion;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.ejecutar.IEjecutarInstruccion;
import utilesx.JEjecutar;

/**
 *
 * @author eduardo
 */
public class JLink  implements IEjecutarInstruccion {
    private static final String mcsNombre = "nombre";
    private static final String mcsEnable = "enable";

    private static final String mcsTargetPath="comando";
    private static final String mcsDirectorioTrabajo="dirTrabajo";
    private static final String mcsLinkPath="pathLink";
    private static final String mcsLinkName="nombreLink";
    private static final String mcsIconoLocalizacion="iconoLink";

    private String msTargetPath;
    private String msDirectorioTrabajo;
    private String msLinkPath;
    private String msLinkName;
    private String msIconoLocalizacion;
    private String msNombre = "";
    private JControladorCoordinadorEjecutar moCoordinador;
    private boolean mbLinkCreado=false;
    private boolean mbFicticio=false;
    private boolean mbHabilitada=true;

    public JLink(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }

    public boolean ejecutar() throws Throwable {
        mbLinkCreado = false;

        //si es una fuente en concreto se procesa solo esa fuente
        try{
            if(!mbFicticio){
                JEjecutar.crearAccesoDirecto(
                        new File(msTargetPath).getAbsolutePath()
                        , new File(msDirectorioTrabajo).getAbsolutePath()
                        , new File(msLinkPath).getAbsolutePath()
                        , msLinkName
                        , new File(msIconoLocalizacion).getAbsolutePath()
                        );
            }
            mbLinkCreado = true;
        }catch(Throwable e){
            mbLinkCreado = false;
            moCoordinador.addError(getClass().getName(), e);
        }

        return mbLinkCreado;
    }
    
    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEnable)){
                mbHabilitada = loAtrib.getValor().trim().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsDirectorioTrabajo)){
                msDirectorioTrabajo = loAtrib.getValor().trim();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsIconoLocalizacion)){
                msIconoLocalizacion = loAtrib.getValor().trim();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsLinkName)){
                msLinkName= loAtrib.getValor().trim();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsLinkPath)){
                msLinkPath = loAtrib.getValor().trim();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor().trim();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsTargetPath)){
                msTargetPath = loAtrib.getValor().trim();
            }

        }
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

    public boolean ejecutarFicticio() throws Throwable {
        mbFicticio=true;
        try{
            ejecutar();
        }finally{
            mbFicticio=false;
        }
        return mbFicticio;
    }

    public boolean isHabilitada() throws Throwable {
        return mbHabilitada;
    }

}
