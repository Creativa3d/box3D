package utilesEjecutar.fuentes;

import utiles.IListaElementos;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JAbstractFactoryEjecutar;
import utilesEjecutar.JControladorCoordinadorEjecutar;

/**
 * Fuente de un conj. de archivos individuales
 */
public class JFuenteConjArchivos implements IFuente {
    private static final String mcsCodeBase = "codebase";
    private static final String mcsNombre = "nombre";
    private static final String mcsTipo = "tipo";
    private static final String mcsArchivo = "archivo";
    private static final String mcs2Plano="segundoPlano";

    private JControladorCoordinadorEjecutar moCoordinador;

    private IListaElementos moLista;
    private String msNombre;
    private String msCodeBase;
    private String msTipo;
    private boolean mb2Plano=false;

    private IFuente moFuente;
    private IListaElementos moHijos;
    private boolean mbCalculado=false;

    public JFuenteConjArchivos(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }
    public void setLista(IListaElementos poLista){
        moFuente.setLista(poLista);
    }

    public IListaElementos getLista() throws Throwable {
        if(!mbCalculado){
            mbCalculado=true;
            //TODO OJO: error, nos saltamos la encapsulacion, pero no hay mas tiempo
            //reestruc para q se le pueden pasar a los JArchivos... una lista de parametros
            if(msTipo.equals(JAbstractFactoryEjecutar.mcshttp)){
                //se captura la Throwable para q estableca siempre la lista
                //y las veces q se use no vuelva a intentar cargar las lista
                try{
                    moFuente.setLista(
                            JFuenteHTTP.getListaPorXml(
                                moCoordinador,
                                msCodeBase,
                                moHijos,
                                mb2Plano));
                }catch(Throwable e){
                    moFuente.setLista(new JListaElementos());
                    throw e;
                }

            }
            //TODO OJO: faltan el resto de tipos
        }
        return moFuente.getLista();
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Throwable {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsCodeBase)){
                msCodeBase = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsTipo)){
                msTipo = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcs2Plano)){
                mb2Plano = loAtrib.getValor().equals("1");
            }
        }
        moFuente = moCoordinador.getAbstractFactory().getFuente(msTipo, moCoordinador);
        moFuente.setParametros(poLista, null);
        moHijos = poHijos;
    }
    public String getNombre() throws Throwable {
        return msNombre;
    }

    public void desconectar() throws Throwable {
    }

}
