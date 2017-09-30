/*
 * JDevolverTextos.java
 *
 * Created on 19 de mayo de 2006, 12:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos.config;

import ListDatos.JSTabla;
import java.util.ResourceBundle;
import utiles.config.JLectorFicherosParametros;

public class JDevolverTextos {
    
    private ITextosFuente moTextos=null;
    
    public JDevolverTextos() {
    }
    /** Creates a new instance of JDevolverTextos */
    public JDevolverTextos(ResourceBundle poTextos) {
        this(new JTextosFuente(poTextos));
    }
    public JDevolverTextos(JLectorFicherosParametros poTextos2) {
        this(new JTextosFuente(poTextos2));
    }
    public JDevolverTextos(ITextosFuente poTextos) {
        moTextos = poTextos;
    }
    public void setFuente(ITextosFuente poTextos ){
        moTextos = poTextos;
    }
    public String getString(String psKey){
        return getTexto(psKey);
    }
    public String getTexto(String psKey){
        String lsValor="";
        try{
            String lsKey = getKeySinRaros(psKey);
            lsValor = moTextos.getString(lsKey);
            if(lsValor==null || lsValor.equals("") || lsValor.equals(lsKey)){
                lsValor = psKey;
            }
//            System.out.println(lsKey + "=" + lsValor);
        }catch(Exception e){
            lsValor=psKey;
        }
        return lsValor;
    }
    public static String getKeySinRaros(String psKey){
        if(psKey==null){
            return "";
        }else{
            String lsCaractIncluir = "abcdefghijklmnopqrstuvwxyz0123456789-ABCDEFGHIJKLMNOPQRSTUVWXYZ_·ÈÌÛ˙¡…Õ”⁄";
            StringBuilder ls = new StringBuilder(psKey.length());
            for(int i = 0; i < psKey.length(); i++){
                if(lsCaractIncluir.indexOf(psKey.charAt(i))>=0){
                    ls.append(psKey.charAt(i));
                }else if(psKey.charAt(i) == 'Ò'){
                    ls.append('n');
                }else if(psKey.charAt(i) == '—'){
                    ls.append('N');
                }else{
                    ls.append('-');
                }
            }
            String lsKey = ls.toString();
            return lsKey;
        }
    }
    public String getTextoVacio(String psKey){
        String lsValor="";
        try{
           lsValor = moTextos.getString(getKeySinRaros(psKey));
        }catch(Exception e){
        }
        return lsValor;
    }

    public String getCaption(String psTabla, String psNombre){
        String lsResult;
        String lsCampo = psTabla + "_" + psNombre;
        lsResult = getTextoVacio(lsCampo);
        if(lsResult.equals("")){
             lsResult = psNombre;
        }
//        System.out.println(getKeySinRaros(lsCampo)   "=" + lsResult);
        return lsResult;
    }
    public String getCaptionReducido(String psTabla, String psNombre){
        String lsResult;
        String lsCampo = psTabla + "_" + psNombre;
        String lsCampoRed = psTabla + "_" + psNombre + "_Reducido";
        lsResult = getTextoVacio(lsCampoRed);
        if(lsResult.equals("")){
             lsResult = getTextoVacio(lsCampo);
             if(lsResult.compareTo("")==0){
                  lsResult = psNombre;
             }
        }
        return lsResult;
    }

    public String[] getCaptions(String psTabla, String[] pasNombres){
        String lasTextos[] = new String[pasNombres.length];
        for(int i = 0; i< lasTextos.length; i++){
            lasTextos[i] = getCaption(psTabla, pasNombres[i]);
        }
        return lasTextos;
    }
    public String[] getCaptionsReducidos(String psTabla, String[] pasNombres){
        String lasTextos[] = new String[pasNombres.length];
        for(int i = 0; i< lasTextos.length; i++){
            lasTextos[i] = getCaptionReducido(psTabla, pasNombres[i]);
        }
        return lasTextos;
    }

    public String[] getCaptions(JSTabla poTabla){
        return getCaptions(poTabla.moList.msTabla, poTabla.moList.getFields().msNombres());
    }

    public String[] getCaptionsReducidos(JSTabla poTabla){
        return getCaptionsReducidos(poTabla.moList.msTabla, poTabla.moList.getFields().msNombres());
    }
    public void setCaptions(JSTabla poTabla){
        for(int i = 0; i< poTabla.moList.getFields().count(); i++){
            poTabla.moList.getFields(i).setCaption(
                    getCaption(poTabla.moList.msTabla, poTabla.moList.getFields(i).getNombre())
                    );
        }
    }
    
    
}
