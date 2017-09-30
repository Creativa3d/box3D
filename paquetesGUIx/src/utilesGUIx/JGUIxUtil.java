/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

import java.io.File;
import java.util.EventListener;
import javax.swing.ImageIcon;
import javax.swing.event.EventListenerList;
import utiles.JDepuracion;
import utilesGUIx.formsGenericos.boton.IEjecutar;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;

/**
 *
 * @author eduardo
 */
public class JGUIxUtil {
    private static int getListenerCount(Object[] list, Class t) {
        int count = 0;
	for (int i = 0; i < list.length; i+=2) {
	    if (t == (Class)list[i])
		count++;
	}
	return count;
    }    
    public static EventListener[] getListeners(EventListenerList listenerList, Class t) { 
	Object[] lList = listenerList.getListenerList(); 
	int n = getListenerCount(lList, t); 
        EventListener[] result = (EventListener[])java.lang.reflect.Array.newInstance(t, n); 
	int j = 0; 
	for (int i = lList.length-2; i>=0; i-=2) {
	    if (lList[i] == t) {
		result[j++] = (EventListener)lList[i+1];
	    }
	}
	return result;   
    }

    public static ImageIcon getIconoCargado(String psImageCamino, Object poEjecutar, Object poEjecutarExtend){
        ImageIcon loIcono=null;
        if(psImageCamino!=null && !psImageCamino.equals("")){
            try{
                File loFile = new File(psImageCamino);
                if(loFile.exists()){
                    loIcono=new ImageIcon(psImageCamino);
                }else{
                    throw new Exception("Fichero no existe");
                }
            }catch(Throwable e){
                try{
                    Class loClass=null;
                    if(poEjecutar!=null){
                        loClass = poEjecutar.getClass();
                    }
                    if(loClass==null && poEjecutarExtend!=null){
                        loClass = poEjecutarExtend.getClass();
                    }
                    if(loClass==null){
                        loClass = JGUIxUtil.class;
                    }
                    if(loClass!=null){
                        loIcono=new ImageIcon(loClass.getResource(psImageCamino));
                    }
                }catch(Throwable e1){
                    JDepuracion.anadirTexto(JGUIxUtil.class.getName(), "Error en imagen " + psImageCamino + " por:");                    
                    JDepuracion.anadirTexto(JGUIxUtil.class.getName(), e1);
                }
            }
        }
        return loIcono;
    }
}
