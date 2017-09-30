
package utilesEjecutar.ejecutar;

import java.io.File;
import utiles.IListaElementos;
import utilesx.JEjecutar;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;

/**
 * Ejecuta una instruccion del sistema operativo
 */
public class JEjecutarInstruccion implements IEjecutarInstruccion{
    private static final String mcsParametro = "parametro";
    private static final String mcsPrograma = "programa";
    private static final String mcsNombre = "nombre";
    private static final String mcsEsperarATerminar = "EsperarATerminar";
    private static final String mcsRutaBase="RutaBase";
    private static final String mcsEnable = "enable";

    private boolean mbEsperarATerminar=false;
    private String msPrograma;
    private IListaElementos moParametros = new JListaElementos();
    private String msNombre;
    private String msRutaBase;
    private boolean mbHabilitada=true;

    private JControladorCoordinadorEjecutar moCoordinador;
    private Thread moThread;
    
    public JEjecutarInstruccion(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsPrograma)){
                msPrograma = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsParametro)){
                moParametros.add(loAtrib.getValor());
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsRutaBase)){
                msRutaBase = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEsperarATerminar)){
                mbEsperarATerminar = loAtrib.getValor().trim().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEnable)){
                mbHabilitada = loAtrib.getValor().trim().equals("1");
            }


        }
    }

    public boolean ejecutar() throws Throwable {
        JEjecutar loEjecutar;
        if(!moCoordinador.isCancelado()){
            moCoordinador.addTextoGUI("Ejecutando aplicación " + msPrograma);
            File loDirRutaBase=null;
            if(msRutaBase!=null && !msRutaBase.trim().equals("")){
                loDirRutaBase=new File(msRutaBase);
            }
            if(moParametros.size()>0){
                String[] lasEje = new String[moParametros.size()+1];
                lasEje[0]=msPrograma;
                for(int i = 0 ; i < moParametros.size();i++ ){
                    lasEje[i+1] = (String) moParametros.get(i);
                }
                loEjecutar = new JEjecutar(lasEje, loDirRutaBase);
            }else{
                loEjecutar = new JEjecutar(msPrograma, loDirRutaBase);
            }
            if(mbEsperarATerminar){
                loEjecutar.run();
            }else{
                moThread = new Thread(loEjecutar);
                moThread.start();
            }
        }
        return false;
    }

    public String getNombre() throws Throwable {
        return msNombre;
    }

    public boolean ejecutarFicticio() throws Throwable {
        return false;
    }

    public boolean isHabilitada() throws Throwable {
        return mbHabilitada;
    }

}
