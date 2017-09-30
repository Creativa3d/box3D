/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesEjecutar.ejecutar.controlflujo;

import java.io.File;
import utiles.IListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.ejecutar.IEjecutarInstruccion;

//<sinoexistedir dir="/intecsa/canon/fuenteUltima" ejecutar="original"/>

public class JCondicion implements IEjecutarInstruccion{
    private static final String mcsTipo = "tipo";
    private static final String mcsDir = "dir";
    private static final String mcsEjecutar = "ejecutar";
    private static final String mcsNombre = "nombre";
    private static final String mcsEnable = "enable";

    private static final String mcsSiNoExisteDir = "sinoexistedir";
    private static final String mcsSiExisteDir = "siexistedir";


    private String msEjecutar;
    private String msNombre;
    private String msDir;
    private boolean mbHabilitada=true;
    private String msTipo;

    private JControladorCoordinadorEjecutar moCoordinador;

    public JCondicion(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEjecutar)){
                msEjecutar = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsDir)){
                msDir = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsTipo)){
                msTipo = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEnable)){
                mbHabilitada = loAtrib.getValor().trim().equals("1");
            }

        }
    }

    private boolean ejecutarR(boolean pbFicticio) throws Throwable {
        boolean lbResult = false;
        if(msTipo.equalsIgnoreCase(mcsSiNoExisteDir)){
            File loFile = new File(msDir);
            if(!loFile.exists()){
                IEjecutarInstruccion loEje = moCoordinador.getEjecucionesControlador().getEjecutar(msEjecutar);
                if(pbFicticio){
                    lbResult = loEje.ejecutarFicticio();
                }else{
                    lbResult = loEje.ejecutar();
                }
            }
        }
        if(msTipo.equalsIgnoreCase(mcsSiExisteDir)){
            File loFile = new File(msDir);
            if(loFile.exists()){
                IEjecutarInstruccion loEje = moCoordinador.getEjecucionesControlador().getEjecutar(msEjecutar);
                if(pbFicticio){
                    lbResult = loEje.ejecutarFicticio();
                }else{
                    lbResult = loEje.ejecutar();
                }
            }
        }
        return lbResult;
    }
    public boolean ejecutar() throws Throwable {
        return ejecutarR(false);
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

    public boolean ejecutarFicticio() throws Throwable {
        return ejecutarR(true);
    }
    public boolean isHabilitada() throws Throwable {
        return mbHabilitada;
    }

}
