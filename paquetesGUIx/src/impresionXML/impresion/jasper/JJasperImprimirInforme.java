/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.jasper;

import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlInformeConj;
import java.io.OutputStream;


public class JJasperImprimirInforme {

    public static void imprimir(JxmlInforme poInforme, OutputStream poOut) throws Exception{

        JJasperPagina loPag = new JJasperPagina(
                poInforme.getAncho(),
                poInforme.getAlto(),
                poInforme.getMargenIzquierdo(),
                poInforme.getMargenSuperior(),
                poInforme.getMargenDerecho(),
                poInforme.getMargenInferior(),
                poOut);
        try{

            poInforme.imprimir(loPag);
        }finally{
            loPag.close();
        }
    }
    public static void imprimir(JxmlInformeConj poInformes, OutputStream poOut) throws Exception{
        if(poInformes.getListaElementos().size()>0){

            JxmlInforme loInf=(JxmlInforme) poInformes.getListaElementos().get(0);
            JJasperPagina loPag = new JJasperPagina(
                    loInf.getAncho(),
                    loInf.getAlto(),
                    loInf.getMargenIzquierdo(),
                    loInf.getMargenSuperior(),
                    loInf.getMargenDerecho(),
                    loInf.getMargenInferior(),
                    poOut);
            try{


                for(int i = 0; i < poInformes.getListaElementos().size() ;i++){
                    loInf = (JxmlInforme) poInformes.getListaElementos().get(i);
                    loInf.imprimir(loPag);
                    loPag.newPage();
                }
            }finally{
                loPag.close();
            }
            System.gc();
        }
    }
}
