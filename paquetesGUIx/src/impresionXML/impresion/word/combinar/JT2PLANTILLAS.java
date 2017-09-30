/*
* JT2ACUERDOS.java
*
* Creado el 20/8/2012
*/
package impresionXML.impresion.word.combinar;

import ListDatos.*;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtension;

public class JT2PLANTILLAS  extends JT2GENERALBASE2 {
    private static final String mcsImportar = "Importar";
    private static final String mcsRenombrar="Renombrar";
    private final JTFORMPLANTILLAS moConsulta;
    private String msDirActual="";
    private final JListDatos moListDatosFuente;
    private final IPlantillas moWord;


    /** Crea una nueva instancia de JT2WORD
     * @param poMostrarPantalla
     * @param poFuente
     * @param psRutaTrabajo
     * @throws java.lang.Exception */
    public JT2PLANTILLAS(final IMostrarPantalla poMostrarPantalla, JListDatos poFuente, IPlantillasFactoria poFacto) throws Exception {
        super();
        moListDatosFuente=poFuente;
        moWord = poFacto.getNuevaPlantilla(moListDatosFuente.msTabla);
        moConsulta = new JTFORMPLANTILLAS(moWord);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre("WORD");
        getParametros().setMostrarPantalla(poMostrarPantalla);
        getParametros().setPlugInPasados(true);
        moWord.generarPlantilla(moListDatosFuente);
    }

    @Override
    public IConsulta getConsulta() {
        return moConsulta;
    }

    @Override
    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }
    @Override
    public void anadir() throws Exception {
        String lsNombre = JOptionPane.showInputDialog("Nombre plantilla");
        if(lsNombre!=null && !lsNombre.equals("")){
            if(lsNombre.indexOf(".doc")<0){
                lsNombre = lsNombre + ".doc";
            }
            if(moConsulta.moList.buscar(JListDatos.mclTIgual, 0, lsNombre)){
                throw new Exception("El nombre de la plantilla " + lsNombre + " ya existe");
            }

            moWord.crearPlantilla(lsNombre);
            JFilaDatosDefecto loFila = new JFilaDatosDefecto(new String[]{lsNombre});
            loFila.setTipoModif(JListDatos.mclNuevo);
            datosactualizados(loFila);
        }
    }

    @Override
    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    @Override
    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        String lsNombre = moConsulta.moList.getFields(0).getString();
        
        moWord.borrar(lsNombre);
        
        JFilaDatosDefecto loFila = new JFilaDatosDefecto(new String[]{lsNombre});
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    @Override
    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        moWord.mostrarPlantilla(moConsulta.moList.getFields(0).getString());
    }

    public String getNombre() {
        return ("Word");
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[moConsulta.getFields().size()];

        loInt[JTFORMPLANTILLAS.lPosiNOMBRE]=200;
        return loInt;
    }

    @Override
    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if(e.getActionCommand().equals(mcsImportar)){

            JFileChooserFiltroPorExtension filtro = new JFileChooserFiltroPorExtension(
                    "Documentos (.doc, .docx)"
                    , new String[]{"doc", "docx"}
                    );

            JFileChooser loFileM = new JFileChooser();
            loFileM.setFileFilter(filtro);

            if (!msDirActual.equals("")) {
                loFileM.setCurrentDirectory(new File(msDirActual));
            }
            if (loFileM.showOpenDialog(new JLabel()) == JFileChooser.APPROVE_OPTION) {
                File loFile = loFileM.getSelectedFile();
                msDirActual = loFile.getParent();
                if (!loFile.exists()) {
                    throw new Exception("Fichero no existe " + loFile.getName());

                } else {
                    String lsNombre = moWord.procesar(loFile);
                    JFilaDatosDefecto loFila = new JFilaDatosDefecto(new String[]{lsNombre});
                    loFila.setTipoModif(JListDatos.mclNuevo);
                    datosactualizados(loFila);
                }
            }            
        }else{
            if(plIndex.length>0){
                for(int i = 0 ; i < plIndex.length; i++){
                    moConsulta.moList.setIndex(plIndex[i]);
                    if(e.getActionCommand().equals(mcsRenombrar)){
                        String lsAntig = moConsulta.moList.getFields(0).getString();
                        String lsExten = JWord.getExtesionFichero(lsAntig);
                        String lsNombre = JOptionPane.showInputDialog("Nuevo nombre para la plantilla " + lsAntig, JWord.getNombreFicheroSinExtension(lsAntig));
                        lsNombre=lsNombre+lsExten;
                        moWord.renombrar(lsAntig, lsNombre);
                        moConsulta.moList.setIndex(plIndex[i]);
                        moConsulta.moList.getFields(0).setValue(lsNombre);
                        moConsulta.moList.update(false);
                        getPanel().refrescar();
                        
            
                    }
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
        }
            
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsImportar, mcsImportar, "/utilesGUIx/images/Import24.gif", this));
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsRenombrar, mcsRenombrar, "/utilesGUIx/images/BePaint.gif", this));
         
    }


}