/*
 * JConstruirXSL.java
 *
 * Created on 24 de noviembre de 2004, 11:54
 */

package impresionXML.tools;

import java.awt.Font;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;





import ListDatos.*;
import utiles.CadenaLarga;

/**construye el formateador xsl para transformar de xml a xsl-fo*/
public class JConstruirXSL {
    
    private final StringBuilder moBufferPaginas = new StringBuilder(300);
    private final StringBuilder moBufferPageSecuence = new StringBuilder();
    private final StringBuilder moBufferPageSecuenceA = new StringBuilder(1200);
    private final StringBuilder moBufferPageSecuenceD = new StringBuilder(100);
    private final StringBuilder moBufferPageSecuenceI = new StringBuilder(60);
    private final StringBuilder moBufferXSLTemplate = new StringBuilder(700);
    private final String msRetornoCarro = System.getProperty("line.separator");
    

    /**
     * Añadimos un estilo de pagina
     * @param plIndex índice de la tabla
     * @param poFormato formato
     */
    public void addXSLPagina(int plIndex, JListDatosXSL poFormato){
        moBufferPaginas.append("<fo:simple-page-master master-name=\"simpleA4");
            moBufferPaginas.append(plIndex);
            moBufferPaginas.append("\" page-height=\"");
            moBufferPaginas.append(poFormato.getPaginaAlto());
            moBufferPaginas.append("cm\" page-width=\"");
            moBufferPaginas.append(poFormato.getPaginaAncho());
            moBufferPaginas.append("cm\" margin-top=\"");
            moBufferPaginas.append(poFormato.getPaginaMargenSuperior());
            moBufferPaginas.append("cm\" margin-bottom=\"");
            moBufferPaginas.append(poFormato.getPaginaMargenInferior());
            moBufferPaginas.append("cm\" margin-left=\"");
            moBufferPaginas.append(poFormato.getPaginaMargenIzquierda());
            moBufferPaginas.append("cm\" margin-right=\"");
            moBufferPaginas.append(poFormato.getPaginaMargenDerecha());
            moBufferPaginas.append("cm\">");
            moBufferPaginas.append(msRetornoCarro);
        
        moBufferPaginas.append("<fo:region-before extent=\"2.5cm\"/>");moBufferPaginas.append(msRetornoCarro);
        
        if(poFormato.getCabezera().masLogotipoTextos.length==0 &&
           poFormato.getCabezera().msLogotipoImagen==null &&
           poFormato.getCabezera().msLogotipoImagen2==null){
            moBufferPaginas.append("<fo:region-body margin-top=\"2.5cm\"/>");moBufferPaginas.append(msRetornoCarro);
        }else{
            moBufferPaginas.append("<fo:region-body/>");moBufferPaginas.append(msRetornoCarro);
        }
        moBufferPaginas.append("</fo:simple-page-master>");moBufferPaginas.append(msRetornoCarro);
        
        moBufferPageSecuence.append(moBufferPageSecuenceA.toString());
        moBufferPageSecuence.append(moBufferPageSecuenceI.toString());
        moBufferPageSecuence.append(moBufferPageSecuenceD.toString());
        
        moBufferPageSecuenceA.delete(0, moBufferPageSecuenceA.length());
        moBufferPageSecuenceD.delete(0, moBufferPageSecuenceD.length());
        moBufferPageSecuenceI.delete(0, moBufferPageSecuenceI.length());
        
        //nueva pagina
        moBufferPageSecuenceA.append("<fo:page-sequence hyphenate=\"true\" language=\"es\" initial-page-number=\"1\" master-reference=\"simpleA4" + String.valueOf(plIndex) + "\">");moBufferPageSecuenceA.append(msRetornoCarro);
        //encabezado
        moBufferPageSecuenceA.append("<fo:static-content flow-name=\"xsl-region-before\">");moBufferPageSecuenceA.append(msRetornoCarro);
        moBufferPageSecuenceA.append("<fo:block ");
            moBufferPageSecuenceA.append(JConstruirXSLFO.getFuente(poFormato.moFuenteEncabezado));
            moBufferPageSecuenceA.append('>');moBufferPageSecuenceA.append(msRetornoCarro);
            //tabla para la cabezera
            moBufferPageSecuenceA.append("<fo:table table-layout=\"fixed\">");
            
            double ldLogoTexto = (poFormato.getPaginaAncho()-poFormato.getPaginaMargenIzquierda()-poFormato.getPaginaMargenDerecha()-8)/2;
            
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"3cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\""+String.valueOf(ldLogoTexto)+"cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"1.5cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\""+String.valueOf(ldLogoTexto)+"cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"3cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            
            moBufferPageSecuenceA.append("<fo:table-body><fo:table-row>");moBufferPageSecuenceA.append(msRetornoCarro);
            //celda del grafico del logotipo
            moBufferPageSecuenceA.append("<fo:table-cell><fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().msLogotipoImagen!=null){
                moBufferPageSecuenceA.append("<fo:external-graphic content-height=\"1.5cm\" content-width=\"2.5cm\" scaling=\"uniform\"  src=\""+poFormato.getCabezera().msLogotipoImagen+"\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append("</fo:block></fo:table-cell>");moBufferPageSecuenceA.append(msRetornoCarro);
            //celda del texto del logotipo
            moBufferPageSecuenceA.append("<fo:table-cell><fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().masLogotipoTextos.length>0){
                    for(int i = 0; i< poFormato.getCabezera().masLogotipoTextos.length;i++){
                        moBufferPageSecuenceA.append("<fo:block>"+JXMLSelectMotor.msReemplazarCaracNoValidos(poFormato.getCabezera().masLogotipoTextos[i])+"</fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
                    }
            }
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaArribaIzq){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append("</fo:block></fo:table-cell>");moBufferPageSecuenceA.append(msRetornoCarro);
            //numero de pagina
            moBufferPageSecuenceA.append("<fo:table-cell><fo:block text-align=\"right\" font-size=\"12pt\">");moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaArribaCen){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append("</fo:block></fo:table-cell>");moBufferPageSecuenceA.append(msRetornoCarro);

            //celda del texto del logotipo
            moBufferPageSecuenceA.append("<fo:table-cell><fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().masLogotipoTextos2.length>0){
                    for(int i = 0; i< poFormato.getCabezera().masLogotipoTextos2.length;i++){
                        moBufferPageSecuenceA.append("<fo:block text-align=\"right\">"+JXMLSelectMotor.msReemplazarCaracNoValidos(poFormato.getCabezera().masLogotipoTextos2[i])+"</fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
                    }
            }
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaArribaDer){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append("</fo:block></fo:table-cell>");moBufferPageSecuenceA.append(msRetornoCarro);
            
            //celda del grafico del logotipo
            moBufferPageSecuenceA.append("<fo:table-cell><fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().msLogotipoImagen2!=null){
                moBufferPageSecuenceA.append("<fo:external-graphic src=\""+poFormato.getCabezera().msLogotipoImagen2+"\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append("</fo:block></fo:table-cell>");moBufferPageSecuenceA.append(msRetornoCarro);

            
            
            moBufferPageSecuenceA.append("</fo:table-row></fo:table-body></fo:table>");moBufferPageSecuenceA.append(msRetornoCarro);
            
            
            
        moBufferPageSecuenceA.append("          </fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
        
        moBufferPageSecuenceA.append("        </fo:static-content>");moBufferPageSecuenceA.append(msRetornoCarro);
        //Empieza contenido dinamico
        moBufferPageSecuenceA.append("        <fo:flow flow-name=\"xsl-region-body\">");moBufferPageSecuenceA.append(msRetornoCarro);
        moBufferPageSecuenceA.append("          <fo:block "+JConstruirXSLFO.getFuente(poFormato.moFuenteCuerpo)+">");moBufferPageSecuenceA.append(msRetornoCarro);
        //Fin contenido dinamico
        moBufferPageSecuenceD.append("          </fo:block>");moBufferPageSecuenceA.append(msRetornoCarro);
        moBufferPageSecuenceD.append("        </fo:flow>");moBufferPageSecuenceA.append(msRetornoCarro);
        moBufferPageSecuenceD.append("      </fo:page-sequence>");moBufferPageSecuenceA.append(msRetornoCarro);
    }
    /**
     * Añadimos una tabla
     * @param plIndex índice de la tabla
     * @param poFormato formato
     */
    public void addXSLBody(int plIndex, JListDatosXSL poFormato){
        JConstruirXSLFO.imprimirCabezera(moBufferXSLTemplate, poFormato);

        //
        //Listado
        //
        moBufferXSLTemplate.append("  <xsl:template match=\"ListDatos"+  String.valueOf(plIndex) +"\">");moBufferXSLTemplate.append(msRetornoCarro);
        
        moBufferXSLTemplate.append("            <fo:table table-layout=\"fixed\">");moBufferXSLTemplate.append(msRetornoCarro);
        for(int i = 0; i < poFormato.getNumeroColumnas();i++){
            if(poFormato.getColumna(i).getLong()>0){
                moBufferXSLTemplate.append("              <fo:table-column column-width=\"" + String.valueOf(poFormato.getColumna(i).getLong()) + "cm\"/>");moBufferXSLTemplate.append(msRetornoCarro);
            }
        }

        moBufferXSLTemplate.append("              <fo:table-header>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("                <xsl:apply-templates select=\"cabezera"+  String.valueOf(plIndex) +"\"/>/>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("              </fo:table-header>" );moBufferXSLTemplate.append(msRetornoCarro);
        
        moBufferXSLTemplate.append("              <fo:table-body>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("                <xsl:apply-templates select=\"datos"+  String.valueOf(plIndex) +"\"/>/>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("              </fo:table-body>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("            </fo:table>");moBufferXSLTemplate.append(msRetornoCarro);

        moBufferXSLTemplate.append("  </xsl:template>");moBufferXSLTemplate.append(msRetornoCarro);
        //
        //Bordes de la tabla
        //
        String lsAutoformatoCabe = "";
        String lsAutoformato = "";
        switch(poFormato.mlAutoformato){
            case JListDatosXSL.mclAutoformatoColumnas:
                 lsAutoformato += JConstruirXSLFO.getBorder("left", "medium");
                 lsAutoformatoCabe += JConstruirXSLFO.getBorder("left", "medium");
                 lsAutoformato += JConstruirXSLFO.getBorder("right", "medium");
                 lsAutoformatoCabe += JConstruirXSLFO.getBorder("right", "medium");
                break;
            case JListDatosXSL.mclAutoformatoCuadricula:
                 lsAutoformato += JConstruirXSLFO.getBorder("before", "medium");
                 lsAutoformatoCabe += JConstruirXSLFO.getBorder("before", "thick");
                 lsAutoformato += JConstruirXSLFO.getBorder("after", "medium");
                 lsAutoformatoCabe += JConstruirXSLFO.getBorder("after", "thick");
                 lsAutoformato += JConstruirXSLFO.getBorder("left", "medium");
                 lsAutoformatoCabe += JConstruirXSLFO.getBorder("left", "medium");
                 lsAutoformato += JConstruirXSLFO.getBorder("right", "medium");
                 lsAutoformatoCabe += JConstruirXSLFO.getBorder("right", "medium");
                break;
            case JListDatosXSL.mclAutoformatoFilas:
                 lsAutoformato += JConstruirXSLFO.getBorder("after", "medium");
                 lsAutoformatoCabe += JConstruirXSLFO.getBorder("after", "thick");
                break;
            case JListDatosXSL.mclAutoformatoNada:
                lsAutoformatoCabe = JConstruirXSLFO.getBorder("after", "thick");
                break;
            default:
        }
        //
        //Cabezera
        //
        if(poFormato.mbEncabezado){
            moBufferXSLTemplate.append("  <xsl:template match=\"cabezera"+  String.valueOf(plIndex) +"\">");moBufferXSLTemplate.append(msRetornoCarro);
            moBufferXSLTemplate.append("    <fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
            moBufferXSLTemplate.append("      <xsl:attribute name=\"font-weight\">bold</xsl:attribute>");moBufferXSLTemplate.append(msRetornoCarro);
            for(int i = 0; i < poFormato.getNumeroColumnas();i++){
                if(poFormato.getColumna(i).getLong()>0){
                    moBufferXSLTemplate.append("      <fo:table-cell "+lsAutoformatoCabe+">");moBufferXSLTemplate.append(msRetornoCarro);
                    moBufferXSLTemplate.append("        <fo:block margin-left=\"2pt\" margin-right=\"2pt\" padding-after=\"2pt\" padding-before=\"2pt\">");moBufferXSLTemplate.append(msRetornoCarro);
                    moBufferXSLTemplate.append("          <xsl:value-of select=\"name" + String.valueOf(i) + "\"/>");moBufferXSLTemplate.append(msRetornoCarro);
                    moBufferXSLTemplate.append("        </fo:block>");moBufferXSLTemplate.append(msRetornoCarro);
                    moBufferXSLTemplate.append("      </fo:table-cell>");moBufferXSLTemplate.append(msRetornoCarro);
                }
            }

            moBufferXSLTemplate.append("    </fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
            moBufferXSLTemplate.append("  </xsl:template>");moBufferXSLTemplate.append(msRetornoCarro);
        }

        //
        //Datos
        //
        moBufferXSLTemplate.append("  <xsl:template match=\"datos"+  String.valueOf(plIndex) +"\">");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("        <xsl:apply-templates select=\"fila"+String.valueOf(plIndex)+"\"/>/>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("  </xsl:template>");moBufferXSLTemplate.append(msRetornoCarro);
        
        moBufferXSLTemplate.append("  <xsl:template match=\"fila"+String.valueOf(plIndex)+"\">");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("    <fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
        String lsAlineacion = "";
        for(int i = 0; i < poFormato.getNumeroColumnas();i++){
            if(poFormato.getColumna(i).getLong()>0){
                lsAlineacion = JConstruirXSLFO.getAlineacion(poFormato.getColumna(i).getAlineacion());
                moBufferXSLTemplate.append("      <fo:table-cell "+lsAutoformato+">");moBufferXSLTemplate.append(msRetornoCarro);
                moBufferXSLTemplate.append("        <fo:block "+lsAlineacion+" " + JConstruirXSLFO.getFuente(poFormato.getColumna(i).getFuente()) + "margin-left=\"2pt\" margin-right=\"2pt\" padding-end=\"2pt\" padding-before=\"2pt\">");moBufferXSLTemplate.append(msRetornoCarro);
                moBufferXSLTemplate.append("          <xsl:value-of select=\"valor" + String.valueOf(i) + "\"/>");moBufferXSLTemplate.append(msRetornoCarro);
                moBufferXSLTemplate.append("        </fo:block>");moBufferXSLTemplate.append(msRetornoCarro);
                moBufferXSLTemplate.append("      </fo:table-cell>");moBufferXSLTemplate.append(msRetornoCarro);
            }
        }

        moBufferXSLTemplate.append("    </fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("  </xsl:template>");moBufferXSLTemplate.append(msRetornoCarro);
        
    }
    /**
     * Devuelve la cadena del xsl que transforma de xml a xsl-fo
     * @return cadena del xsl
     */
    public String getXSLString(){
        StringBuilder loBuffer = new StringBuilder(
                moBufferPaginas.length() +
                moBufferPageSecuence.length() +
                moBufferPageSecuenceA.length() +
                moBufferPageSecuenceD.length() +
                moBufferPageSecuenceI.length() +
                moBufferXSLTemplate.length() + 142
                );

        loBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");loBuffer.append(msRetornoCarro);loBuffer.append(msRetornoCarro);
        
        loBuffer.append("<xsl:stylesheet version=\"1.1\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:fo=\"http://www.w3.org/1999/XSL/Format\" exclude-result-prefixes=\"fo\">" );loBuffer.append(msRetornoCarro);
        loBuffer.append("  <xsl:output method=\"xml\" version=\"1.0\" omit-xml-declaration=\"no\" indent=\"yes\"/>" );loBuffer.append(msRetornoCarro);


            loBuffer.append("  <xsl:template match=\"root\">" );loBuffer.append(msRetornoCarro);
            loBuffer.append("    <fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">");loBuffer.append(msRetornoCarro);

                loBuffer.append("      <fo:layout-master-set>");loBuffer.append(msRetornoCarro);
                loBuffer.append(moBufferPaginas.toString());
                loBuffer.append("      </fo:layout-master-set>");loBuffer.append(msRetornoCarro);

                //3 lineas es necesario para realizar la ultima pagina
                moBufferPageSecuence.append(moBufferPageSecuenceA.toString());
                moBufferPageSecuence.append(moBufferPageSecuenceI.toString());
                moBufferPageSecuence.append(moBufferPageSecuenceD.toString());
                loBuffer.append(moBufferPageSecuence.toString());

            loBuffer.append("    </fo:root>");loBuffer.append(msRetornoCarro);
            loBuffer.append("  </xsl:template>");loBuffer.append(msRetornoCarro);
        
            loBuffer.append(moBufferXSLTemplate.toString());

        loBuffer.append("</xsl:stylesheet>");loBuffer.append(msRetornoCarro);
        
//        System.out.println(loBuffer.toString());
        
        return loBuffer.toString();
    }
    /**
     * Devuelve el source del xsl que transforma de xml a xsl-fo
     * @return source
     */
    public Source getXSL() {
        return new StreamSource(new CadenaLarga(getXSLString()));
    }    
}

