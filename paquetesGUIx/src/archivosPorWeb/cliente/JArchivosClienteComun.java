/*
 * JArchivosClienteComun.java
 *
 * Created on 5 de mayo de 2006, 8:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package archivosPorWeb.cliente;

import archivosPorWeb.comun.IServidorArchivos;
import archivosPorWeb.comun.JFichero;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import utilesGUIx.JGUIxConfigGlobal;

public class JArchivosClienteComun {
    public static JArchivosClienteAccion moAccion = null;
    
    /** Creates a new instance of JArchivosClienteComun */
    public JArchivosClienteComun() {
    }
    
    public static void setAccion(JArchivosClienteAccion poAccion){
        moAccion=poAccion;
    }
    public static JArchivosClienteAccion getAccion(){
        return moAccion;
    }
    private static String getMensajeconformacion(JFichero poOrigen, JFichero poDestino){
        String lsTipoArchivo = (poDestino.getEsDirectorio()? JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("directorio"):JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("archivo"));
        return 
            "<html>" +
            "<p>"+JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Esta carpeta ya contine un")+" "+ lsTipoArchivo +"  con el nombre <span style='font-size:12.0pt;color:blue'>" + poDestino.getNombre() +"</span></p><br>" +
            "<p>"+JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("¿Deseas reemplazarlo?")+"</p><br>" +
            " " +
            "</html>";
    }
    public static void pegar(IServidorArchivos poServidor, JFichero poPath) throws Exception {
        JFichero loDestino = new JFichero(poPath.getPath(), "", false, 0, null);
        for(JFichero loFichero: moAccion.moElementos ){
            loDestino.setNombre(loFichero.getNombre());
            switch(moAccion.mlAccion){
                case JArchivosClienteAccion.mclCopiar:
                    boolean lbContinuar = true;
                    JFichero loAux = poServidor.getFichero(loDestino);
                    if(loAux.isExiste()){
                        lbContinuar =  
                                JOptionPane.showConfirmDialog(
                                    new JLabel(), 
                                    getMensajeconformacion(poPath, loAux) , 
                                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Confirmar reemplazo de archivo"), 
                                    JOptionPane.YES_NO_OPTION, 
                                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
                    }
                    if(lbContinuar){
                        poServidor.copiarA(moAccion.moServidor, loFichero, loDestino);
                    }
                    break;
                case JArchivosClienteAccion.mclCortar:
                    lbContinuar = true;
                    loAux = poServidor.getFichero(loDestino);
                    if(loAux.isExiste()){
                        lbContinuar =  
                                JOptionPane.showConfirmDialog(
                                    new JLabel(), 
                                    getMensajeconformacion(poPath, loAux) , 
                                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Confirmar reemplazo de archivo"), 
                                    JOptionPane.YES_NO_OPTION, 
                                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
                    }
                    if(lbContinuar){
                        poServidor.mover(moAccion.moServidor, loFichero, loDestino);
                    }
                    break;
            }
            
        }
    }
    
}
