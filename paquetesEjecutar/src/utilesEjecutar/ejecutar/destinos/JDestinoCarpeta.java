

package utilesEjecutar.ejecutar.destinos;

import java.io.File;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.ejecutar.IEjecutarInstruccion;
import utilesEjecutar.fuentes.IFuente;
import utilesEjecutar.fuentes.archivos.IArchivo;
import utilesEjecutar.fuentes.archivos.JArchivoCarpetas;

/**
 * Copia las fuentes a una carpeta local
 */
public class JDestinoCarpeta implements IEjecutarInstruccion {
    private static final String mcsArbol = "arbol";
    private static final String mcsNombre = "nombre";
    private static final String mcsEstructura = "estructura";
    private static final String mcsFuentes = "fuentes";
    private static final String mcsCodeBase = "codebase";
    private static final String mcsTodas = "todas";
    private static final String mcsEnable = "enable";
    private static final String mcsForzar = "forzar";


    private String msCodeBase;
    private String msFuentes = "";
    private String msEstructura = mcsArbol;
    private String msNombre = "";
    private JControladorCoordinadorEjecutar moCoordinador;
    private boolean mbCopiadoAlgo=false;
    private boolean mbFicticio;
    private boolean mbHabilitada=true;
    private boolean mbForzar=false;

    public JDestinoCarpeta(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }

    public boolean ejecutar() throws Throwable {
        mbCopiadoAlgo = false;
        //si son todas las fuentes
        if(msFuentes.equalsIgnoreCase(mcsTodas) ||
           msFuentes.equalsIgnoreCase("")){
            //se hace un recorrido por cada fuente y se procesa
            for(int i = 0; i < moCoordinador.getFuentesControlador().getFuentes().size() && !moCoordinador.isCancelado(); i++ ){
                IFuente loFuente = (IFuente) moCoordinador.getFuentesControlador().getFuentes().get(i);
                try{
                    mbCopiadoAlgo |= copiarFuente(loFuente);
                }catch(Throwable e){
                    moCoordinador.addError(getClass().getName(), e);
                }
            }
        }else{
            //si es una fuente en concreto se procesa solo esa fuente
            IFuente loFuente = (IFuente) moCoordinador.getFuentesControlador().getFuente(msFuentes);
            try{
                mbCopiadoAlgo |= copiarFuente(loFuente);
            }catch(Throwable e){
                moCoordinador.addError(getClass().getName(), e);
            }
        }
        return mbCopiadoAlgo;
    }

    private boolean copiarFuente(IFuente poFuente) throws Throwable {
        boolean lbCopiadoAlgo = false;
        IListaElementos loElementos = poFuente.getLista();
        //por cada archivo de la fuente
        for(int i = 0 ; i < loElementos.size() && !moCoordinador.isCancelado(); i++){
            IArchivo loArchFuente = (IArchivo) loElementos.get(i);
            moCoordinador.addTextoGUI("Cargando " + loArchFuente.getRutaRelativa() + "-" +  loArchFuente.getNombre());

            File loFileDestino;
            //segun si es estruc. arbol o no
            if(msEstructura.equals(mcsArbol)  || msEstructura.equals("")){
//                //creamos los directorios en el destino
//                loFileDestino =new File(
//                            msCodeBase,
//                            loArchFuente.getRutaRelativa());
//                loFileDestino.mkdirs();
                //fichero a copiar en el destino
                loFileDestino =
                    new File(
                        new File(
                            msCodeBase,
                            loArchFuente.getRutaRelativa()).getAbsolutePath(),
                            loArchFuente.getNombre());
            }else{
//                //creamos los directorios en el destino
//                loFileDestino =new File(msCodeBase);
//                loFileDestino.mkdirs();
                //fichero a copiar en el destino
                loFileDestino = 
                    new File(msCodeBase, 
                             loArchFuente.getNombre());
            }
            //se encapsula el archivo destino
            IArchivo loArchDestino = new JArchivoCarpetas(loFileDestino, loArchFuente.getRutaRelativa());

            //si ha cambiado el tamaño o la fecha es menor q la de la fuente
            if(mbForzar || moCoordinador.isCompararCopiarSN(loArchDestino, loArchFuente)){
                lbCopiadoAlgo = true;
                if(!mbFicticio){
                    //si es copia de seguridad, se copia al archivo temporal
                    if(moCoordinador.isCopiaSeguridad() && loArchDestino.getTamano()>0){
                        File loFile = new File(
                                    new File(
                                        moCoordinador.getTmpCopiaSeguridad(),
                                        loArchDestino.getRutaRelativa()),
                                        loArchDestino.getNombre());
                        JDepuracion.anadirTexto(
                                JDepuracion.mclINFORMACION,
                                getClass().getName(),
                                "Se copiará el archivo " +
                                    loArchDestino.getRutaRelativa() + "-" +
                                    loArchDestino.getNombre() + " hasta " +
                                    loFile.getAbsolutePath());
                        loArchDestino.copiarFuenteA(
                                loFile.getAbsolutePath(),
                                false);
                    }
                    JDepuracion.anadirTexto(
                            JDepuracion.mclINFORMACION,
                            getClass().getName(),
                            "Se copiará el archivo " +
                                loArchFuente.getRutaRelativa() + "-" +
                                loArchFuente.getNombre() + " hasta " +
                                loFileDestino.getAbsolutePath());
                    loArchFuente.copiarFuenteA(
                            loFileDestino.getAbsolutePath(),
                            true);
                }
            }else{
                moCoordinador.addCompletadoGUI(loArchDestino.getTamano());
            }
        }
        return lbCopiadoAlgo;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsCodeBase)){
                msCodeBase = JControladorCoordinadorEjecutar.getDirLocalconSeparadorCorrecto(loAtrib.getValor());
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsFuentes)){
                msFuentes = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEstructura)){
                msEstructura = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsEnable)){
                mbHabilitada = loAtrib.getValor().trim().equals("1");
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsForzar)){
                mbForzar = loAtrib.getValor().trim().equals("1");
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
        return mbCopiadoAlgo;
    }

    public boolean isHabilitada() throws Throwable {
        return mbHabilitada;
    }

}
