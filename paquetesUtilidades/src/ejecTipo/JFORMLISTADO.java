/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecTipo;

import ListDatos.IFilaDatos;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JListDatosFiltroElem;
import ListDatos.JSTabla;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.JSelectUnionTablas;
import ListDatos.JUtilTabla;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import java.io.File;
import utiles.JConversiones;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtension;

/**
 *
 * @author eduardo
 */
public class JFORMLISTADO  extends JSTabla implements IConsulta {
    private static final long serialVersionUID = 1L;

     /**
      * Variables para las posiciones de los campos
      */
    public static final int lPosiARCHIVO = 0;
    public static final int lPosiNOMBRE = 1;
    public static final int lPosiDESCRIPCION = 2;




     /**
      * Variable nombre de tabla
      */
    public static String msCTabla="listado";

     /**
      * Número de campos de la tabla
      */
    public static int mclNumeroCampos=3;
     /**
      * Nombres de la tabla
      */
    public static String[] masNombres=    new String[] {
        "Archivo",
        "Nombre",
        "Descripción"
    };

    public static int[] malTipos=    new int[] {
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena,

    };
          
    public static int[] malCamposPrincipales=
            new int[]   {lPosiARCHIVO};
    private String msCamino;
    private int mlZ=1;
    private String[] masExtensiones;
    private JFileChooserFiltroPorExtension moFiltro;
    private final JDatosGeneralesXML moDatosXML;

     /**
      * Crea una instancia de la clase intermedia para la tabla CFORMIPA1 incluyendole el servidor de datos
      */
    public JFORMLISTADO(JDatosGeneralesXML poDatosXML) {
        super();
        moDatosXML=poDatosXML;
        moList = new JListDatos(null,msCTabla, masNombres, malTipos, malCamposPrincipales);
        moList.addListener(this);
    }

     public JFieldDef getARCHIVO(){
               return moList.getFields().get(lPosiARCHIVO);
     }
     public JFieldDef getDESCRIPCION(){
               return moList.getFields().get(lPosiDESCRIPCION);
     }
     public JFieldDef getNOMBRE(){
               return moList.getFields().get(lPosiNOMBRE);
     }
     public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
         moList.clear();
         setCamino(moDatosXML.getPropiedad(JDatosGenerales.mcsRuta, "."));
         setZ((int)JConversiones.cdbl(moDatosXML.getPropiedad(JDatosGenerales.mcsZ, "1")));
         setExtensiones( moDatosXML.getPropiedad(JDatosGenerales.mcsExtensiones, "rom").split(";"));
         
        repasar(new File(msCamino), 0);
        System.gc();
     }
     
     public void repasar(File poFile, int plZ) throws Exception {
        File[] files = poFile.listFiles();
        int i = 0;
        File loFile;
        for (; i<files.length;i++){
            loFile = files[i];
            if(moFiltro==null
                    || moFiltro.accept(loFile) ){
                if(loFile.isDirectory()){
                    if(plZ<mlZ){
                        repasar(loFile, plZ+1);
                    }
                }else{
                    moList.addNew();
                    getARCHIVO().setValue(loFile.getAbsolutePath());
                    getNOMBRE().setValue(loFile.getName());
                    getDESCRIPCION().setValue(loFile.getName());
                    moList.update(false);
                }
            }
        }
        System.gc();
     }
     
     public void setCamino(String psCamino){
         msCamino=psCamino;
     }
     public void setZ(int plZ){
         mlZ=plZ;
     }
     public void setExtensiones(String[] pasExtensiones){
         masExtensiones=pasExtensiones;
         if(masExtensiones==null 
            || (masExtensiones.length==1 && masExtensiones[0].equals(""))){
             moFiltro = null;
         }else{
            moFiltro = new JFileChooserFiltroPorExtension("", pasExtensiones);
            moFiltro.setDirectoriosValidos(true);
         }
         
     }
     

     public void addFilaPorClave(IFilaDatos poFila) throws Exception {
        switch(poFila.getTipoModif()){
            case JListDatos.mclBorrar:
                moList.borrar(false);
                break;
            case JListDatos.mclEditar:
                moList.getFields().cargar(poFila);
                moList.update(false);
                break;
            case JListDatos.mclNuevo:
                moList.addNew();
                moList.getFields().cargar(poFila);
                moList.update(false);
                break;
            default:
                throw new Exception("Tipo modificación incorrecto");
        }
     }

    public boolean getPasarCache() {
        return false;
    }
     

}
