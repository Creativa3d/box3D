
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
public class JFuenteCarpetas implements IFuente {
    private static final String mcsNombre = "nombre";
    private static final String mcsCodeBase = "codebase";
    private String msCodeBase;
    private IListaElementos moLista;
    private String msNombre;
    private JControladorCoordinadorEjecutar moCoordinador;

    public JFuenteCarpetas(JControladorCoordinadorEjecutar poCoordinador){
        moCoordinador = poCoordinador;
    }
    public void setLista(IListaElementos poLista){
        moLista = poLista;
    }

    public IListaElementos getLista() throws Exception {
        if(moLista==null){
            moLista = new JListaElementos();
            File loFile = new File(msCodeBase);
            if(!loFile.exists()){
                throw new Exception("La carpeta " + msCodeBase + " no existe");
            }
            if(!loFile.isDirectory()){
                throw new Exception("La ruta " + msCodeBase + " no es un directorio");
            }

            recorrer(loFile, "");
        }

        return moLista;
    }

    private void recorrer(File poFile, String psRutaRelativa) throws Exception {
        String[] loFiles = poFile.list();
        for(int i = 0 ; i < loFiles.length ; i++){
            File loFile = new File(poFile.getAbsolutePath(),loFiles[i]);
            if(loFile.isDirectory()){
                recorrer(loFile, psRutaRelativa + JControladorCoordinadorEjecutar.mcsFileSeparator + loFile.getName() );
            }else{
                moLista.add(new JArchivoCarpetas(loFile, psRutaRelativa, moCoordinador));
            }
        }
    }

    public void setParametros(JAtributos poLista, IListaElementos poHijos) throws Exception {
        for(int i = 0 ; i < poLista.size(); i++){
            JAtributo loAtrib = poLista.getAtributo(i);
            if(loAtrib.getName().trim().equalsIgnoreCase(mcsCodeBase)){
                msCodeBase = loAtrib.getValor();
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
