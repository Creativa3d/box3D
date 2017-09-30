/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xmlxsl.impl;

import impresionXML.impresion.xmlxsl.JXmlXslInforme;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author pvillar
 */
public class JXmlXslInforme620ResultValencia extends JXmlXslInforme{
    
    public JXmlXslInforme620ResultValencia (final String poPathXML, final String poPathXSLT){
        super(poPathXML, poPathXSLT);
    }
    
    protected String corregirEstilos(HTMLEditorKit poEditoKit,String poHtml){

          anhadirReglas(poEditoKit);
          
          return corregirHtml(poHtml);
    }
    
    private void anhadirReglas(HTMLEditorKit poEditoKit){
        
        poEditoKit.getStyleSheet().addRule("table { border-width: 0px;  margin: 0px; border-style: hidden;  border-collapse: collapse;}");
        poEditoKit.getStyleSheet().addRule("tr { border-width: 0px; margin: 0px; padding: 0px; border-style: hidden; border-collapse: collapse;}");
        poEditoKit.getStyleSheet().addRule("td { border-width: 1px;  margin: 0px; padding: 0px; border-style: solid; border-collapse: collapse;}");
        poEditoKit.getStyleSheet().addRule("#AutoNumber2 { border-width: 1px;  margin: 0px; padding: 0px; border-style: solid; border-collapse: collapse; }");
        
        
    }
    
    private String corregirHtml(String poHtml){
        //Cambios a realizar por no funcionar el border-collapse: border 
        //Quitamos el ancho de los bordes
        poHtml= poHtml.replaceAll("border=\"2\"", "");
        poHtml= poHtml.replaceAll("border=\"1\"", "");
        poHtml= poHtml.replaceAll("cellpadding=\"1\"", "cellpadding=\"0\"");
        poHtml= poHtml.replaceAll("cellspacing=\"1\"", "cellspacing=\"-1\"");
        poHtml= poHtml.replaceAll("cellspacing=\"0\"", "cellspacing=\"-1\"");
        
        //Corregimos error que se produce en la transformacion, ya que desaparece el tag </img>
        int liPosImg = poHtml.indexOf("<img");
        String lsPrimeraParte = poHtml.substring(0,liPosImg);
        String lsSegundaParte = poHtml.substring(liPosImg);
        
        lsSegundaParte = lsSegundaParte.replaceFirst(">", "></img>");
        
        poHtml = lsPrimeraParte + lsSegundaParte;
        
        
        
        return poHtml;
    }
    
}
