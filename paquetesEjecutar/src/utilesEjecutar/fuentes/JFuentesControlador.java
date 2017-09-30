

package utilesEjecutar.fuentes;

import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.fuentes.archivos.IArchivo;

/**Mantiene una lista de las fuentes*/
public class JFuentesControlador {
    private IListaElementos moLista;
    private JControladorCoordinadorEjecutar moCoordinador;
    private JDateEdu moFechaMaximaArchivosFuentes;

    public JFuentesControlador(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador=poCoordinador;
        moLista = new JListaElementos();
    }

    /**
     * Devuelve la lista de IFuente
     */
    public IListaElementos getFuentes(){
        return moLista;
    }

    /**
     * Devuelve un IFuente q tenga el nombre psNombre
     */
    public IFuente getFuente(String psNombre) throws Throwable{
        IFuente loFuente = null;
        IFuente loAux = null;
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION ,getClass().getName(), "Se pide la fuente " + psNombre);
        for(int i = 0; i < moLista.size() && loFuente == null; i++ ){
            loAux = (IFuente) moLista.get(i);
            if(loAux.getNombre().equalsIgnoreCase(psNombre)){
                loFuente = loAux;
            }
        }
        if(loFuente!=null){
            long lTotal = 0;
            moCoordinador.addTextoGUI("Leyendo fuente " + loFuente.getNombre());
            IListaElementos loLista = loFuente.getLista();
            for(int ii = 0 ;ii < loLista.size() && !moCoordinador.isCancelado(); ii++){
                IArchivo loArchivo = (IArchivo) loLista.get(ii);
                lTotal+=loArchivo.getTamano();
            }
            moCoordinador.addTotal(lTotal);
        }
        return loFuente;
    }
    public JDateEdu getFechaMaximaArchivosFuentes() throws Throwable {
        if(moFechaMaximaArchivosFuentes==null){
            for(int i = 0 ; i < getFuentes().size(); i++){
                try{
                    IFuente loFuente = (IFuente) getFuentes().get(i);
                    IListaElementos loLista = loFuente.getLista();
                    for(int ii = 0 ;ii < loLista.size(); ii++){
                        IArchivo loArchivo = (IArchivo) loLista.get(ii);
                        if(moFechaMaximaArchivosFuentes==null || moFechaMaximaArchivosFuentes.compareTo(loArchivo.getFechaModificacion())<0){
                            moFechaMaximaArchivosFuentes = loArchivo.getFechaModificacion();
                        }
                    }
                }catch(Throwable e){
                    moCoordinador.addError(getClass().getName(), e);
                }
            }
        }
        return moFechaMaximaArchivosFuentes;
    }

    public void inicializar() {
        moFechaMaximaArchivosFuentes=null;
    }
    public void finalizar() {
        for(int i = 0 ; i < getFuentes().size(); i++){
            IFuente loFuente = (IFuente) getFuentes().get(i);
            try {
                loFuente.desconectar();
            }catch(Throwable ex){
                moCoordinador.addError(getClass().getName(), ex);
            }
        }
    }
    
}
