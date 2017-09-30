
package utilesEjecutar.fuentes;

import java.io.File;
import utiles.FechaMalException;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JListaElementos;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.JControladorCoordinadorEjecutar;
import utilesEjecutar.fuentes.archivos.JArchivoCarpetas;

/**
 * La fuente es un directorio local
 */
public class JFuenteArchivo implements IFuente {
    private static final String mcsNombre = "nombre";
    private static final String mcsArchivo = "Archivo";
    private String msArchivo;
    private IListaElementos moLista;
    private String msNombre;
    private JControladorCoordinadorEjecutar moCoordinador;

    public JFuenteArchivo(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }
    public void setLista(IListaElementos poLista){
        moLista = poLista;
    }

    public IListaElementos getLista() throws Exception {
        if(moLista==null){
            moLista = new JListaElementos();
            File loFile = new File(msArchivo);
            if(!loFile.exists()){
                throw new Exception("El archivo " + msArchivo + " no existe");
            }

            moLista.add(new JArchivoCarpetas(loFile, "", moCoordinador));
        }

        return moLista;
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Exception {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsArchivo)){
                msArchivo = loAtrib.getValor();
            }
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsNombre)){
                msNombre = loAtrib.getValor();
            }
        }
    }
    public String getNombre() throws Exception {
        return msNombre;
    }

    public void desconectar() throws Exception {
    }

}
