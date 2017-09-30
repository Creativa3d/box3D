/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import java.io.File;
import java.util.HashMap;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionNavegador;
import utilesGUIx.formsGenericos.edicion.JActividadEdicion;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author eduardo
 */
public class JMostrarPantalla implements IMostrarPantalla {
    public static final String mcsNumeroForm = "NumeroForm";
    protected IListaElementos moListeners = new JListaElementos();

    private int mlNumeroform = 1;
    private HashMap moForms = new HashMap();
    private final Context moContext;
    private Activity moActividad;
    private Object moIcono;

    
    public JMostrarPantalla(Context poContext){
        moContext=poContext;
    }
    public void mostrarFormPrinci(IPanelControlador poControlador) {
        JMostrarPantallaParam loParam = new JMostrarPantallaParam(poControlador);
        mostrarForm(loParam);
    }

    public void mostrarForm(IMostrarPantallaCrear poPanel) {
       JMostrarPantallaParam loParam = new JMostrarPantallaParam(poPanel);
       mostrarForm(loParam);
    }

    public void mostrarForm(JMostrarPantallaParam poParam) {
        if(poParam.getControlador()!= null){
            if(poParam.getCrear()==null){
                poParam.setCrear(new IMostrarPantallaCrear() {
                    public Object getPanel(Object poActividad, JMostrarPantallaParam poParam) throws Throwable {
                        JPanelGenerico loPanel = new JPanelGenerico((Activity) poActividad);
                        loPanel.setControlador(
                                poParam.getControlador()
                                , ISalir.class.isAssignableFrom(poActividad.getClass()) ? (ISalir)poActividad: null);
                        return loPanel;
                    }

                    public Class getClase() {
                        return JPanelGenerico.class;
                    }
                });
            }
            String lsIden = addParam(poParam);
            Intent i = new Intent(JActividadDefecto.class.getName()+lsIden, Uri.EMPTY, moContext, JActividadDefecto.class);
            i.putExtra(mcsNumeroForm, lsIden);
            moContext.startActivity(i);    
        }else if(IFormEdicion.class.isAssignableFrom(poParam.getCrear().getClase())){
            String lsIden = addParam(poParam);
            Intent i = new Intent(JActividadEdicion.class.getName()+lsIden, Uri.EMPTY, moContext, JActividadEdicion.class);
            i.putExtra(mcsNumeroForm, lsIden);
            moContext.startActivity(i);    
            
        } else{
        	String lsIden = addParam(poParam);
            Intent i = new Intent(JActividadDefecto.class.getName()+lsIden, Uri.EMPTY, moContext, JActividadDefecto.class);
            i.putExtra(mcsNumeroForm, lsIden);
            moContext.startActivity(i);    
        }
    }
    public Object getContext(){
//    	if(moActividad!=null){
//    		return moActividad;
//    	}else{
    		return moContext;
//    	}
    }
    public String addParam(JMostrarPantallaParam poParam) {
        mlNumeroform++;
        moForms.put(String.valueOf(mlNumeroform), new JElementoForm(null, poParam));
        return String.valueOf(mlNumeroform);
    }
        
        

    public void cerrarForm(Object poForm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addMostrarListener(IMostrarPantallaListener poListener) {
        moListeners.add(poListener);
    }

    public void removeMostrarListener(IMostrarPantallaListener poListener) {
        moListeners.remove(poListener);
    }

    public void llamarListener(JMostrarPantallaEvent jMostrarPantallaEvent) {
        for(int i = 0 ; i < moListeners.size(); i++){
            IMostrarPantallaListener loList = (IMostrarPantallaListener) moListeners.get(i);
            loList.mostrarPantallaPerformed(jMostrarPantallaEvent);
        }
    }

    public JMostrarPantallaParam getParam(String psNumeroForm) {
        JElementoForm loElem = (JElementoForm) moForms.get(psNumeroForm);
        moForms.remove(psNumeroForm);
        return loElem.getParam();
    }

    public void setActividad(Object poAct) {
        moActividad = (Activity) poAct;
    }

    
    public static boolean openURLWithType(String url, String type, Context poContext) {
        Uri uri = Uri.parse(url);
        openURLWithType(uri, type, poContext);
        return true;
    }        
    public static boolean openFileWithType(File poFile, String type, Context poContext) {
        Uri uri = Uri.fromFile(poFile);
        openURLWithType(uri, type, poContext);
        return true;
    }        
    
    public static boolean openURLWithType(Uri uri, String type, Context poContext) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, type);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        poContext.startActivity(intent);
        return true;
    }        

    public void mostrarEdicion(IFormEdicion ife, IFormEdicionNavegador ifen, Object o, int i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarEdicion(IFormEdicion ife, Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarEdicion(IFormEdicionNavegador ifen, Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarFormPrinci(IPanelControlador ipc, int i, int i1) throws Exception {
    	mostrarFormPrinci(ipc);
    }

    public void mostrarFormPrinci(IPanelControlador ipc, int i, int i1, int i2, int i3) throws Exception {
    	mostrarFormPrinci(ipc);
    }

    public void mostrarFormPrinci(Object o, int i, int i1, int i2) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getActiveInternalFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void mostrarOpcion(Object o, String string, Runnable r, Runnable r1) {
        JMsgBox.mostrarOpcion((Context)(o==null?moActividad:o), string, r, r1);
    }

    public void mensajeErrorYLog(Object o, Throwable thrwbl, Runnable r) {
        JMsgBox.mensajeErrorYLog((Context)(o==null?moActividad:o), thrwbl, "");
    }

    public void mensaje(Object o, String string, int i, Runnable r) {
        JMsgBox.mensajeInformacion((Context)(o==null?moActividad:o), string);
    }

    public void mensajeFlotante(Object o, String string) {
        JMsgBox.mensajeFlotante((Context)(o==null?moActividad:o), string);
    }

    public Object getImagenIcono() {
        return moIcono;
    }

    public void setImagenIcono(Object o) {
        moIcono=o;
    }


    
}
