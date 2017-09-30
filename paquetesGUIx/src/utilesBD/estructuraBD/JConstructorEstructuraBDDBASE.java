/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.estructuraBD;

import ListDatos.estructuraBD.IConstructorEstructuraBD;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import utiles.JDepuracion;
import utilesBD.servidoresDatos.JServerServidorDatosDBASE;

/**
 *
 * @author eduardo
 */
public class JConstructorEstructuraBDDBASE  implements IConstructorEstructuraBD {

    private String msRuta;
    private JTableDefs moEstructura=null;
    private File[] moBD;

    /** Creates a new instance of JConstructorMetaDatosBD */
    public JConstructorEstructuraBDDBASE(String psRuta) {
        setRuta(psRuta);
    }
    public JConstructorEstructuraBDDBASE(String psRuta, boolean pbOptimizado) {
        this(psRuta);
    }
    public JConstructorEstructuraBDDBASE(String psRuta, boolean pbconRelacionesExport, boolean pbconRelacionesInport) {
        this(psRuta);
    }

    public synchronized  void setRuta(String psBD) {
        msRuta=psBD;
        
    }
    public synchronized String getRuta(){
        return msRuta;
    }
    //recuperamos la estructura de la base de datos
    public synchronized JTableDefs getTableDefs() throws Exception {
        if(moEstructura==null){
            moBD = new File(msRuta).listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith("dbf");
                }
            });
            moEstructura = new JTableDefs();

            try {
                //Recuperar Tablas
                for(int i = 0 ; i < moBD.length; i++) {
                    File loFile = moBD[i];
                    String lsTabla = loFile.getName();
                    moEstructura.add(JServerServidorDatosDBASE.getTableDefDeDBF(new FileInputStream(loFile), lsTabla.substring(0, lsTabla.length()-4)));  //Anadimos la tabla a la estructura
                }
            }catch(Exception e) {
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), e);
            } finally{
                moBD=null;
            }
        }
        return moEstructura;
    }


    
    public static void main(String[] pas){
        try {
            JConstructorEstructuraBDDBASE loAc = new JConstructorEstructuraBDDBASE("/home/eduardo/d/bda/intecsa/chs/Ipasub97.mdb");
            loAc.getTableDefs();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
