/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.imgTrata.seleccionar;



import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import utiles.FechaMalException;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.msgbox.JMsgBox;

public class JAccionesSeleccionarFotoPanel  {
    public static final String mcsSeleccionarRuta = "fotosExpediente/SeleccionarRuta";
    public static final String mcsLimpiarDirectorio = "fotosExpediente/LimpiarDirectorio";


    protected File moArchivo;
    protected boolean mbCancelado=false;
    protected JDatosGeneralesXML moDatosXML;

    private IListaElementos<String> moExtensiones = new JListaElementos<String>();
    
    public JAccionesSeleccionarFotoPanel(JDatosGeneralesXML poDatosXML){
        moDatosXML=poDatosXML;
        moExtensiones.add("JPG");
        moExtensiones.add("PNG");
        moExtensiones.add("GIF");
        moExtensiones.add("TIF");
        moExtensiones.add("TIFF");
        moExtensiones.add("JPEG");
        moExtensiones.add("PDF");
    }

    
    public void setDatosGeneralesXML(JDatosGeneralesXML poDatosXML){
        moDatosXML=poDatosXML;
    }
    
    public IListaElementos<String> getExtensiones(){
        return moExtensiones;
    }
            

    public boolean borrarImagenes(File poFile){
        boolean lbBorrado=false;
        String[] loFiles = poFile.list();
        for(int i = 0 ; i < loFiles.length; i++){
            File loAux = new File(poFile.getAbsolutePath(), loFiles[i]);
            if(isImage(loAux.getName())){
                loAux.delete();
                lbBorrado=true;
            }else if (loAux.isDirectory()){
                //limitamos el borrado de imagenes a un solo directorio
                if(!lbBorrado){
                    lbBorrado=borrarImagenes(loAux);
                }
            }
        }
        return lbBorrado;
    }

    public void limpiarDirectorio() throws Exception {
        File loDir = new File(getRutaDefecto());
        borrarImagenes(loDir);
    }
    public void seleccionarImagen() throws Exception{
        //1º comprobamos q la ruta defecto existe, de lo contrario selecccionamos una
        if(getRutaDefecto()==null || getRutaDefecto().equals("")) {
        }else{
            //2º conseguimos la imagen autom.
            moArchivo = getImagenAutomatica();
        }
        //3º llamamos al form. para q el usuario compruebe
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(
                new JPanelSeleccionarFoto(this),
                800,
                600,
                JMostrarPantalla.mclEdicionDialog,
                "Seleccionar imagen");
        loParam.setCallBack(new CallBack<JMostrarPantallaParam>() {
            public void callBack(JMostrarPantallaParam poControlador) {
                if(moArchivo==null && !mbCancelado){
                    JMsgBox.mensajeError(null, "Imagen vacía");
                }
            }
        });
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarForm(loParam);
    }
    public void seleccionarRutaDefecto() throws Exception {
        JFileChooser loSelec = new JFileChooser(getRutaDefecto());
        loSelec.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(loSelec.showOpenDialog(new JLabel())==JFileChooser.APPROVE_OPTION){
            setRutaDefecto(loSelec.getSelectedFile().getAbsolutePath());
        }
    }

    public boolean isLimpiarDirectorio() throws Exception {
        return moDatosXML.getPropiedad(mcsLimpiarDirectorio, "1").equals("1");
    }
    public void setLimpiarDirectorio(boolean selected) throws Exception {
        moDatosXML.setPropiedad(mcsLimpiarDirectorio, (selected?"1":"0"));
        moDatosXML.guardarFichero();
    }
    public void setRutaDefecto(String psRuta) throws Exception{
        moDatosXML.setPropiedad(mcsSeleccionarRuta, psRuta);
        moDatosXML.guardarFichero();
    }
    public String getRutaDefecto() {
        return moDatosXML.getPropiedad(mcsSeleccionarRuta, "");
    }

    private File getImagen(File poFile) throws Exception{
        return getImagen(poFile, 0);
    }    
    private boolean isImage(final String psName){
        boolean lbResult = false;
        String lsName = psName.trim().toUpperCase();
        
        for( String lsExt : moExtensiones){
            lsExt = lsExt.toUpperCase();
            if(lsName.length()>lsExt.length()){
                lbResult |= lsName.substring(psName.length()-lsExt.length()).equals(lsExt);
            }
        }
        
        return lbResult;
    }
    public JDateEdu getFechaFichero(long pdtime) throws FechaMalException{
        JDateEdu loDate = new JDateEdu("1/1/70 1:0:0");
        loDate.add(loDate.mclSegundos, (int)(pdtime/1000));
        return loDate;
    }

    public boolean isMasReciente(final File poFile1, final File poFile2){
        return poFile1==null || 
               (poFile1.lastModified()<poFile2.lastModified());
    }    
    public File getImagen(File poFile, int plProf) throws Exception{
        File loResulFoto = null;
        JDateEdu loDateHoy = new JDateEdu();
        String[] loFiles = poFile.list();
        int i = 0;
        if(loFiles!=null){
            i = loFiles.length-1;
        }
        
        for(; loFiles!=null && i >=0 && plProf < 2; i--){
            File loAux = new File(
                                poFile.getAbsolutePath(),
                                loFiles[i]);
            //la foto no puede ser mas antigua de 15 minutos
            JDateEdu loDate = getFechaFichero(loAux.lastModified());
            double ldMinutos = Math.abs(loDate.diff(loDate.mclMinutos, loDate, loDateHoy));
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,getClass().getName(), loAux.getName() + "=" + loDate.toString() + " hoy " + loDateHoy.toString()  + " minutos " + String.valueOf(ldMinutos));
            //en algunos pda me calcula una hora mal, asi q ponemos 75 en vez de 15
            if(isImage(loAux.getName()) && (ldMinutos<=75)){
                if(isMasReciente(loResulFoto, loAux)){
                    loResulFoto = loAux;
                }
            }else if (loAux.isDirectory()){
                loAux = getImagen(loAux, plProf+1);
                if(loAux!=null){
                    if(isMasReciente(loResulFoto, loAux)){
                        loResulFoto = loAux;
                    }
                }
            }
        }
        return loResulFoto;
    }    
    public File getImagenAutomatica() throws Exception {
        File loDir = new File(getRutaDefecto());
        return getImagen(loDir);
    }
    public void setArchivo(File poArch){
        moArchivo = poArch;
    }
    public File getArchivo(){
        return moArchivo;
    }
    public void setCancelado (boolean pbCancelado){
        mbCancelado=pbCancelado;
    }

    public boolean isCancelado() {
        return mbCancelado;
    }

}
