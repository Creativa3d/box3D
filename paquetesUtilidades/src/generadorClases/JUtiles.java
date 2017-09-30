/*
 * JUtiles.java
 *
 * Created on 8 de diciembre de 2005, 1:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorClases;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JTableDef;
import generadorClases.opciones.JOpcionesGlobal;
import utiles.JListaElementos;

/**
 *
 * @author Administrador
 */
public class JUtiles {
    
    public static final String msRetornoCarro = System.getProperty("line.separator");
    public static final int mcnCamposTablaRelacionada = 0;
    public static final int mcnCamposPropios = 1;
    private final JProyecto moProyecto;
    
    /** Creates a new instance of JUtiles */
    public JUtiles(JProyecto poProyecto) {
        moProyecto=poProyecto;
    }
    
    public String getDirPadre(String psPath) {
        int i;
        String sol="",a="",fin="";
        
        i = psPath.length()-1;
        do {
            a = psPath.substring(i,i+1);
            i--;
            if(a.compareTo(System.getProperty("file.separator")) != 0)
                sol = sol + a;
        } while(a.compareTo(System.getProperty("file.separator")) != 0);
        
        for(i=sol.length();i>0;i--) {
            a = sol.substring(i-1,i);
            fin = fin + a;
        }
        
        return fin;
    }
    
    public boolean mbLetraEne(char l){
        return l == '�' || l == '�' || l == '?' || l == '?' || ((int)l)==209;
    }
    public boolean mbLetraBuena(char l){
        if(l == ' ' || l == ',' || l == '.' || l == ':' || 
           l == '�' || l == '�' || l == '?' || l == '?' || ((int)l)==209 ||
           l == '�' || l == '�' || 
           l == '�' || l == '�' || 
           l == '�' || l == '�' || 
           l == '�' || l == '�' || l == ')' ||
           l == '�' || l == '�' || l == '(' ||
           l == '-' || l == '/' || l == '+' || l == ' ' || l == '_' ||
           l == '?' || l == '�' || l == '%' || l == '�'
          ){
            return false;
        }else{
            return true;
        }
    }
    
    public String msSustituirRaros(String psTexto){
        String lsTexto = psTexto.replace('�', 'a');
        lsTexto = lsTexto.replace('�', 'a');
        lsTexto = lsTexto.replace('�', 'e');
        lsTexto = lsTexto.replace('�', 'i');
        lsTexto = lsTexto.replace('�', 'o');
        lsTexto = lsTexto.replace('�', 'u');
        lsTexto = lsTexto.replace('�', 'A');
        lsTexto = lsTexto.replace('�', 'E');
        lsTexto = lsTexto.replace('�', 'I');
        lsTexto = lsTexto.replace('�', 'O');
        lsTexto = lsTexto.replace('�', 'U');
        lsTexto = lsTexto.replace('?', 'N');
        lsTexto = lsTexto.replace('?', 'n');
        lsTexto = lsTexto.replace('�', 'N');
        lsTexto = lsTexto.replace('�', 'n');
        if(moProyecto !=null &&  moProyecto.getOpciones().isMayusculas()){
            lsTexto = lsTexto.toUpperCase();
        }
//        lsTexto = lsTexto.replace('\%', '100');


        StringBuffer lsTextob = new StringBuffer(lsTexto.length());
        for(int i = 0; i < lsTexto.length(); i++ ){
            if(mbLetraBuena(lsTexto.charAt(i))){
                lsTextob.append(lsTexto.charAt(i));
            }else{
                if(mbLetraEne(lsTexto.charAt(i))){
                    lsTextob.append('N');
                }
            }
        }
        return lsTextob.toString().toUpperCase();
    }
    
    
    public String msTipo(int plTipo){
        String lsTipo="";
        switch(plTipo){
            case JListDatos.mclTipoBoolean:
                lsTipo="JListDatos.mclTipoBoolean";
                break;
            case JListDatos.mclTipoCadena:
                lsTipo="JListDatos.mclTipoCadena";
                break;
            case JListDatos.mclTipoFecha:
                lsTipo="JListDatos.mclTipoFecha";
                break;
            case JListDatos.mclTipoNumero:
                lsTipo="JListDatos.mclTipoNumero";
                break;
            case JListDatos.mclTipoNumeroDoble:
                lsTipo="JListDatos.mclTipoNumeroDoble";
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                lsTipo="JListDatos.mclTipoMoneda3Decimales";
                break;
            case JListDatos.mclTipoMoneda:
                lsTipo="JListDatos.mclTipoMoneda";
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                lsTipo="JListDatos.mclTipoPorcentual3Decimales";
                break;
            case JListDatos.mclTipoPorcentual:
                lsTipo="JListDatos.mclTipoPorcentual";
                break;
        }
        return lsTipo;
    }
    
    public String msTipoFlex(int plTipo){
        String lsTipo="";
        switch(plTipo){
            case JListDatos.mclTipoBoolean:
                lsTipo="formularios.mclTipoBoolean";
                break;
            case JListDatos.mclTipoCadena:
                lsTipo="formularios.mclTipoCadena";
                break;
            case JListDatos.mclTipoFecha:
                lsTipo="formularios.mclTipoFecha";
                break;
            case JListDatos.mclTipoNumero:
                lsTipo="formularios.mclTipoNumero";
                break;
            case JListDatos.mclTipoNumeroDoble:
                lsTipo="formularios.mclTipoNumeroDoble";
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                lsTipo="formularios.mclTipoMoneda3Decimales";
                break;
            case JListDatos.mclTipoMoneda:
                lsTipo="formularios.mclTipoMoneda";
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                lsTipo="formularios.mclTipoPorcentual3Decimales";
                break;
            case JListDatos.mclTipoPorcentual:
                lsTipo="formularios.mclTipoPorcentual";
                break;
        }
        return lsTipo;
    }
    
    
    public boolean perteneceA(String cad,JListaElementos lista) {
        boolean ok = false;
        
        for(int i=0;i<lista.size();i++) {
            if(lista.get(i).toString().equals(cad)) {
                ok = true;
            }
        }
        
        return ok;
    }
    

    public String getPaqueteExtend(){
        if(moProyecto.getOpciones().isUsarJTEE()){
            return "tablasExtend";
        }else{
            return "tablas2";
        }
    }
    public String getNombreTablaExtends(String psTabla){
        psTabla = msSustituirRaros(psTabla);
        if(moProyecto.getOpciones().isUsarJTEE()){
            return "JTEE"+ psTabla;
        }else{
            return "JT"+ psTabla+"2";
        }
    }    
}
