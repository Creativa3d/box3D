/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impresionXML.impresion.pdf;

import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlInformeConj;
import impresionXML.impresion.xml.JxmlLectorInforme;
import java.io.FileOutputStream;
import java.io.OutputStream;
import utiles.JDepuracion;


public class JPDFImprimirInforme {

    public static void imprimir(JxmlInforme poInforme, OutputStream poOut) throws Exception{

        JPDFPagina loPag = new JPDFPagina(
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
            try{
                loPag.close();
                poOut.close();
            }catch(Throwable e){
                
            }
        }
    }
    public static void imprimir(JxmlInformeConj poInformes, OutputStream poOut) throws Exception{
        if(poInformes.getListaElementos().size()>0){

            JxmlInforme loInf=(JxmlInforme) poInformes.getListaElementos().get(0);
            JPDFPagina loPag = new JPDFPagina(
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

    public static void main(String args[]) {
        try {
            JDepuracion.mbDepuracion = true;
            JDepuracion.mlNivelDepuracion = JDepuracion.mclINFORMACION;
            String lsInforme = "informe.xml";
            if (args.length > 0) {
                lsInforme = args[0];
            }
            JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), "imprimiendo " + lsInforme);
            JxmlInforme loInforme = JxmlLectorInforme.leerInforme(lsInforme);
//            loInforme.imprimir("pdf", true);
            JPDFImprimirInforme.imprimir(loInforme, new FileOutputStream("pdf.pdf"));

        } catch (Exception e) {
            JDepuracion.anadirTexto(JxmlLectorInforme.class.getName(), e);
        }
    }
}
