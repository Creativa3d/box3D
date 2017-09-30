/*
 * JConstruirXSL.java
 *
 * Created on 24 de noviembre de 2004, 11:54
 */

package impresionXML.tools;

import java.awt.Color;
import java.awt.Font;
import org.xml.sax.InputSource;

import ListDatos.*;
import utiles.CadenaLarga;

/**Construir fuente en xsl-fo directo*/
public class JConstruirXSLFO {

    private static final String mcsleft = "left";
    private static final String mcsbefore="before";
    private static final String mcsafter="after";
    private static final String mcsright="right";
    private static final String mcsthick="thick";
    private static final String mcsmedium="medium";
    
    private static final String mcsBlockIni="<fo:block ";
    private static final String mcsBlockIniMayor="<fo:block>";
    private static final String mcsBlockFin="</fo:block>";
    private static final String mcstablecell="<fo:table-cell>";
    private static final String mcstablecellFin="</fo:table-cell>";
    
    private final StringBuilder moBufferPaginas;
    private StringBuilder moBufferPageSecuenceA;
    private StringBuilder moBufferPageSecuenceD;
    private final StringBuilder moBufferXSLTemplate;
    public static final String msRetornoCarro = System.getProperty("line.separator");
    
    public JConstruirXSLFO(){
        super();
        moBufferPaginas = new StringBuilder(300);
        moBufferPageSecuenceA = new StringBuilder(1100);
        moBufferPageSecuenceD = new StringBuilder(300);
        moBufferXSLTemplate = new StringBuilder(300);
    }
    /**
     * Añadimos un estilo de pagina
     * @param plIndex índice de la tabla
     * @param poFormato formato
     */
    public void addXSLPagina(final int plIndex, final JListDatosXSL poFormato){
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
        
        moBufferPaginas.append("<fo:region-after extent=\"1cm\"/>");moBufferPaginas.append(msRetornoCarro);
        
        if(poFormato.getCabezera().masLogotipoTextos.length==0 &&
           poFormato.getCabezera().msLogotipoImagen==null &&
           poFormato.getCabezera().msLogotipoImagen2==null){
            moBufferPaginas.append("<fo:region-before extent=\"1cm\"/>");moBufferPaginas.append(msRetornoCarro);
            moBufferPaginas.append("<fo:region-body margin-top=\"1cm\" margin-bottom=\"1cm\" />");moBufferPaginas.append(msRetornoCarro);
        }else{
            moBufferPaginas.append("<fo:region-before extent=\"2.5cm\"/>");moBufferPaginas.append(msRetornoCarro);
            moBufferPaginas.append("<fo:region-body margin-top=\"2.5cm\" margin-bottom=\"1cm\"/>");moBufferPaginas.append(msRetornoCarro);
        }
        moBufferPaginas.append("</fo:simple-page-master>");moBufferPaginas.append(msRetornoCarro);
        
        
        moBufferPageSecuenceA.delete(0, moBufferPageSecuenceA.length());
        moBufferPageSecuenceD.delete(0, moBufferPageSecuenceD.length());
        
        //nueva pagina
        moBufferPageSecuenceA.append("<fo:page-sequence hyphenate=\"true\" language=\"es\" initial-page-number=\"1\" master-reference=\"simpleA4" + String.valueOf(plIndex) + "\">");moBufferPageSecuenceA.append(msRetornoCarro);
        
        //ENCABEZADO
            moBufferPageSecuenceA.append("<fo:static-content flow-name=\"xsl-region-before\">");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append(mcsBlockIni);
                moBufferPageSecuenceA.append(getFuente(poFormato.moFuenteEncabezado));
                moBufferPageSecuenceA.append('>');moBufferPageSecuenceA.append(msRetornoCarro);
            //tabla para la cabezera
            moBufferPageSecuenceA.append("<fo:table table-layout=\"fixed\">");
            
            double ldLogoTexto = (poFormato.getPaginaAncho()-poFormato.getPaginaMargenIzquierda()-poFormato.getPaginaMargenDerecha()-8)/2;
            
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"3cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"");
                moBufferPageSecuenceA.append(ldLogoTexto);
                moBufferPageSecuenceA.append("cm\"/>");
                moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"1.5cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"");
                moBufferPageSecuenceA.append(ldLogoTexto);
                moBufferPageSecuenceA.append("cm\"/>");
                moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"3cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            
            moBufferPageSecuenceA.append("<fo:table-body><fo:table-row>");moBufferPageSecuenceA.append(msRetornoCarro);
            //celda del grafico del logotipo
            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().msLogotipoImagen!=null){
                moBufferPageSecuenceA.append("<fo:external-graphic content-height=\"1.5cm\" content-width=\"2.5cm\" scaling=\"uniform\"  src=\""+poFormato.getCabezera().msLogotipoImagen+"\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaArribaIzq){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);
            //celda del texto del logotipo
            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().masLogotipoTextos.length>0){
                    for(int i = 0; i< poFormato.getCabezera().masLogotipoTextos.length;i++){
                        moBufferPageSecuenceA.append(mcsBlockIniMayor);
                        moBufferPageSecuenceA.append(JXMLSelectMotor.msReemplazarCaracNoValidos(poFormato.getCabezera().masLogotipoTextos[i]));
                        moBufferPageSecuenceA.append(mcsBlockFin);
                        moBufferPageSecuenceA.append(msRetornoCarro);
                    }
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);
            //numero de pagina
            moBufferPageSecuenceA.append("<fo:table-cell><fo:block text-align=\"right\" font-size=\"12pt\">");moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaArribaCen){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);

            //celda del texto del logotipo
            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().masLogotipoTextos2.length>0){
                    for(int i = 0; i< poFormato.getCabezera().masLogotipoTextos2.length;i++){
                        moBufferPageSecuenceA.append("<fo:block text-align=\"right\">");
                            moBufferPageSecuenceA.append(JXMLSelectMotor.msReemplazarCaracNoValidos(poFormato.getCabezera().masLogotipoTextos2[i]));
                            moBufferPageSecuenceA.append(mcsBlockFin);
                            moBufferPageSecuenceA.append(msRetornoCarro);
                    }
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);
            
            //celda del grafico del logotipo
            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.getCabezera().msLogotipoImagen2!=null){
                moBufferPageSecuenceA.append("<fo:external-graphic src=\""+poFormato.getCabezera().msLogotipoImagen2+"\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaArribaDer){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);

            
            
            moBufferPageSecuenceA.append("</fo:table-row></fo:table-body></fo:table>");moBufferPageSecuenceA.append(msRetornoCarro);
            
            
            moBufferPageSecuenceA.append(mcsBlockFin);moBufferPageSecuenceA.append(msRetornoCarro);
        
            moBufferPageSecuenceA.append("</fo:static-content>");moBufferPageSecuenceA.append(msRetornoCarro);
        //FIN ENCABEZADO

        
        //PIE
            moBufferPageSecuenceA.append("<fo:static-content flow-name=\"xsl-region-after\">");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append(mcsBlockIni+getFuente(poFormato.moFuenteEncabezado)+">");moBufferPageSecuenceA.append(msRetornoCarro);
            //tabla 
            moBufferPageSecuenceA.append("<fo:table table-layout=\"fixed\">");
            
            ldLogoTexto = (poFormato.getPaginaAncho()-poFormato.getPaginaMargenIzquierda()-poFormato.getPaginaMargenDerecha()-8)/2;
            
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"3cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\""+String.valueOf(ldLogoTexto)+"cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"1.5cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\""+String.valueOf(ldLogoTexto)+"cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            moBufferPageSecuenceA.append("<fo:table-column column-width=\"3cm\"/>");moBufferPageSecuenceA.append(msRetornoCarro);
            
            moBufferPageSecuenceA.append("<fo:table-body><fo:table-row>");moBufferPageSecuenceA.append(msRetornoCarro);
            //celda del grafico del logotipo
            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaAbajoIzq){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);
            //celda del texto del logotipo
            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
                moBufferPageSecuenceA.append(mcsBlockFin);
            moBufferPageSecuenceA.append(mcstablecellFin);moBufferPageSecuenceA.append(msRetornoCarro);
            //numero de pagina
            moBufferPageSecuenceA.append("<fo:table-cell><fo:block text-align=\"right\" font-size=\"12pt\">");moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaAbajoCen){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);

            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
                moBufferPageSecuenceA.append(mcsBlockFin);
            moBufferPageSecuenceA.append(mcstablecellFin);moBufferPageSecuenceA.append(msRetornoCarro);
            
            moBufferPageSecuenceA.append(mcstablecell);
                moBufferPageSecuenceA.append(mcsBlockIniMayor);
                moBufferPageSecuenceA.append(msRetornoCarro);
            if(poFormato.mlNumeroPaginaVisible == poFormato.mclNumPaginaAbajoDer){
                moBufferPageSecuenceA.append("<fo:page-number/>");moBufferPageSecuenceA.append(msRetornoCarro);
            }
            moBufferPageSecuenceA.append(mcsBlockFin);
                moBufferPageSecuenceA.append(mcstablecellFin);
                moBufferPageSecuenceA.append(msRetornoCarro);

            
            
            moBufferPageSecuenceA.append("</fo:table-row></fo:table-body></fo:table>");moBufferPageSecuenceA.append(msRetornoCarro);
            
            
            moBufferPageSecuenceA.append(mcsBlockFin);moBufferPageSecuenceA.append(msRetornoCarro);
        
            moBufferPageSecuenceA.append("</fo:static-content>");moBufferPageSecuenceA.append(msRetornoCarro);
        //FIN PIE
        //Empieza contenido dinamico
        moBufferPageSecuenceA.append("<fo:flow flow-name=\"xsl-region-body\">");moBufferPageSecuenceA.append(msRetornoCarro);
        moBufferPageSecuenceA.append(mcsBlockIni);
            moBufferPageSecuenceA.append(getFuente(poFormato.moFuenteCuerpo));
            moBufferPageSecuenceA.append('>');
            moBufferPageSecuenceA.append(msRetornoCarro);
        //Fin contenido dinamico
        moBufferPageSecuenceD.append(mcsBlockFin);moBufferPageSecuenceD.append(msRetornoCarro);
        moBufferPageSecuenceD.append("</fo:flow>");moBufferPageSecuenceD.append(msRetornoCarro);
        moBufferPageSecuenceD.append("</fo:page-sequence>");moBufferPageSecuenceD.append(msRetornoCarro);
    }
    
    static void imprimirCabezera(final StringBuilder moBufferXSLTemplate,final JListDatosXSL poFormato){
        JListDatosXSLCabe loCabezera = poFormato.getCabezera();
        String lsBreak = "";
        if(poFormato.mbNuevaPagina){
            lsBreak = " break-before=\"page\" ";
        }
        if(loCabezera.msTitulo.compareTo("")==0){
            if(lsBreak.compareTo("")!=0){
                moBufferXSLTemplate.append(mcsBlockIni);
                moBufferXSLTemplate.append(lsBreak);
                moBufferXSLTemplate.append(" space-after=\"2mm\">");
                moBufferXSLTemplate.append(mcsBlockFin);moBufferXSLTemplate.append(msRetornoCarro);
            }
        }else{
            moBufferXSLTemplate.append(mcsBlockIni);
            moBufferXSLTemplate.append(getAlineacion(loCabezera.mlAlineacionCabe));
            moBufferXSLTemplate.append(' ');
            moBufferXSLTemplate.append(lsBreak);
            moBufferXSLTemplate.append(' ');
            moBufferXSLTemplate.append(getFuente(loCabezera.moFuenteTitulo));
            moBufferXSLTemplate.append(" space-after=\"5mm\" space-before=\"5mm\">");
            moBufferXSLTemplate.append(JXMLSelectMotor.msReemplazarCaracNoValidos(loCabezera.msTitulo));
            moBufferXSLTemplate.append(mcsBlockFin);moBufferXSLTemplate.append(msRetornoCarro);
        }

        if(loCabezera.getFilas()>0){
            moBufferXSLTemplate.append("<fo:table space-after=\"5mm\" table-layout=\"fixed\">");moBufferXSLTemplate.append(msRetornoCarro);
            for(int i = 0 ; i < loCabezera.getColumnas(0);i++){
                moBufferXSLTemplate.append("<fo:table-column column-width=\"" + String.valueOf(loCabezera.getColumna(0, i).getLong()) + "cm\"/>");moBufferXSLTemplate.append(msRetornoCarro);
            }
            moBufferXSLTemplate.append("<fo:table-body>");moBufferXSLTemplate.append(msRetornoCarro);
        }
        
        for(int i = 0 ; i < loCabezera.getFilas();i++){
            moBufferXSLTemplate.append("<fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
            for(int ii = 0; ii < loCabezera.getColumnas(i);ii++){
                moBufferXSLTemplate.append(
                        "<fo:table-cell "+
                            getBorder(loCabezera.getColumna(i,ii))+
                            ">");moBufferXSLTemplate.append(msRetornoCarro);
                moBufferXSLTemplate.append(
                        mcsBlockIni+
                            getAlineacion(loCabezera.getColumna(i,ii).getAlineacion())+" " + 
                            getFuente(loCabezera.getColumna(i,ii).getFuente()) +  
                            " margin-left=\"2pt\" margin-right=\"2pt\" padding-end=\"2pt\" padding-before=\"2pt\"  white-space-collapse=\"false\">");
                moBufferXSLTemplate.append(JXMLSelectMotor.msReemplazarCaracNoValidos(loCabezera.getColumna(i,ii).getTexto()));
                moBufferXSLTemplate.append(mcsBlockFin);moBufferXSLTemplate.append(msRetornoCarro);
                moBufferXSLTemplate.append(mcstablecellFin);moBufferXSLTemplate.append(msRetornoCarro);
            }

            moBufferXSLTemplate.append("</fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
        }
        
        if(loCabezera.getFilas()>0){
            moBufferXSLTemplate.append("</fo:table-body>");moBufferXSLTemplate.append(msRetornoCarro);
            moBufferXSLTemplate.append("</fo:table>");moBufferXSLTemplate.append(msRetornoCarro);
        }
    }    
    /**
     * Añadimos una tabla
     * @param plIndex índice de la tabla
     * @param poFormato formato
     * @param poList datos
     */
    public void addXSLBody(final int plIndex, final JListDatosXSL poFormato, final JListDatos poList){
        String lsFuenteCabezera = getFuente(poFormato.getCabezera().moFuenteCabezera);
        String lsFuentePie = getFuente(poFormato.moFuentePie);
        String lsFuenteCuerpo = getFuente(poFormato.moFuenteCuerpo);

        imprimirCabezera(moBufferXSLTemplate, poFormato);
        //
        //Listado
        //
        
        moBufferXSLTemplate.append("<fo:table table-layout=\"fixed\">");moBufferXSLTemplate.append(msRetornoCarro);
        
        for(int i = 0; i < poFormato.getNumeroColumnas();i++){
            if(poFormato.getColumna(i).getLong()>0){
                moBufferXSLTemplate.append("<fo:table-column column-width=\"");
                    moBufferXSLTemplate.append(poFormato.getColumna(i).getLong());
                    moBufferXSLTemplate.append("cm\" />");
                    moBufferXSLTemplate.append(msRetornoCarro);
            }
        }

        //
        //Bordes de la tabla
        //
        String lsAutoformatoCabe = "";
        String lsAutoformato = "";
        String lsAutoformatoPie = "";
        StringBuilder lsAux;
        switch(poFormato.mlAutoformato){
            case JListDatosXSL.mclAutoformatoColumnas:
                 lsAux = new StringBuilder(100 * 2);
                 lsAux.append(getBorder(mcsleft, mcsmedium));
                 lsAux.append(getBorder(mcsright, mcsmedium));
                 lsAutoformato = lsAux.toString();
                 lsAux.delete(0, lsAux.length());
                 lsAux.append(getBorder(mcsleft, mcsmedium));
                 lsAux.append(getBorder(mcsright, mcsmedium));
                 lsAutoformatoCabe = lsAux.toString();
                break;
            case JListDatosXSL.mclAutoformatoCuadricula:
                 lsAux = new StringBuilder(100 * 4);
                 lsAux.append(getBorder(mcsbefore, mcsmedium));
                 lsAux.append(getBorder(mcsafter, mcsmedium));
                 lsAux.append(getBorder(mcsleft, mcsmedium));
                 lsAux.append(getBorder(mcsright, mcsmedium));
                 lsAutoformato = lsAux.toString();
                 lsAux.delete(0, lsAux.length());
                 lsAux.append(getBorder(mcsbefore, mcsthick));
                 lsAux.append(getBorder(mcsafter, mcsthick));
                 lsAux.append(getBorder(mcsleft, mcsmedium));
                 lsAux.append(getBorder(mcsright, mcsmedium));
                 lsAutoformatoCabe = lsAux.toString();
                break;
            case JListDatosXSL.mclAutoformatoFilas:
                 lsAutoformato = getBorder(mcsafter, mcsmedium);
                 lsAutoformatoCabe = getBorder(mcsafter, mcsthick);
                break;
            case JListDatosXSL.mclAutoformatoNada:
                lsAutoformatoCabe = getBorder(mcsafter, mcsthick);
                lsAutoformatoPie = getBorder(mcsbefore, mcsthick);
                break;
            default:
        }
        //
        //Cabezera
        //
        if(poFormato.mbEncabezado){
            moBufferXSLTemplate.append("<fo:table-header>" );moBufferXSLTemplate.append(msRetornoCarro);
            moBufferXSLTemplate.append("<fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
            for(int i = 0; i < poFormato.getNumeroColumnas();i++){
                if(poFormato.getColumna(i).getLong()>0){
                    moBufferXSLTemplate.append("<fo:table-cell "+lsAutoformatoCabe+">");moBufferXSLTemplate.append(msRetornoCarro);
                    moBufferXSLTemplate.append(mcsBlockIni + lsFuenteCabezera + " "+getAlineacion(poFormato.getColumna(i).getAlineacion())+" margin-left=\"2pt\" margin-right=\"2pt\" padding-after=\"2pt\" padding-before=\"2pt\"  white-space-collapse=\"false\" >");
                    moBufferXSLTemplate.append(JXMLSelectMotor.msReemplazarCaracNoValidos(poList.getFields().get(i).getCaption()));
                    moBufferXSLTemplate.append(mcsBlockFin);moBufferXSLTemplate.append(msRetornoCarro);
                    moBufferXSLTemplate.append(mcstablecellFin);moBufferXSLTemplate.append(msRetornoCarro);
                }
            }

            moBufferXSLTemplate.append("</fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
            moBufferXSLTemplate.append("</fo:table-header>" );moBufferXSLTemplate.append(msRetornoCarro);
        }

        
        moBufferXSLTemplate.append("<fo:table-body>");moBufferXSLTemplate.append(msRetornoCarro);
        //
        //Datos
        //
        if(poList.moveFirst()){
            do{
                moBufferXSLTemplate.append("<fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
                String lsAutoDatos;
                String lsFuenteDatos;
                if(poList.getIndex() >= (poList.size() - poFormato.mlFilasPie)){
                    lsAutoDatos = lsAutoformatoPie;
                    lsFuenteDatos = lsFuentePie;
                }else{
                    lsAutoDatos = lsAutoformato;
                    lsFuenteDatos = lsFuenteCuerpo;
                }
                for(int i = 0; i < poFormato.getNumeroColumnas();i++){
                    if(poFormato.getColumna(i).getLong()>0){
                        moBufferXSLTemplate.append("<fo:table-cell ");
                            moBufferXSLTemplate.append(lsAutoDatos);
                            moBufferXSLTemplate.append('>');
                            moBufferXSLTemplate.append(msRetornoCarro);
                        moBufferXSLTemplate.append(mcsBlockIni);
                            moBufferXSLTemplate.append(lsFuenteDatos);
                            moBufferXSLTemplate.append(' ');
                            moBufferXSLTemplate.append(getAlineacion(poFormato.getColumna(i).getAlineacion()));
                            moBufferXSLTemplate.append(' ');
                            moBufferXSLTemplate.append(getFuente(poFormato.getColumna(i).getFuente()));
                            moBufferXSLTemplate.append(" margin-left=\"2pt\" margin-right=\"2pt\" padding-end=\"2pt\" padding-before=\"2pt\"  white-space-collapse=\"false\">");
                        moBufferXSLTemplate.append(JXMLSelectMotor.msReemplazarCaracNoValidos(poList.getFields(i).toString()));
                        moBufferXSLTemplate.append(mcsBlockFin);moBufferXSLTemplate.append(msRetornoCarro);
                        moBufferXSLTemplate.append(mcstablecellFin);moBufferXSLTemplate.append(msRetornoCarro);
                    }
                }

                moBufferXSLTemplate.append("</fo:table-row>");moBufferXSLTemplate.append(msRetornoCarro);
            }while(poList.moveNext());
        }
        moBufferXSLTemplate.append("</fo:table-body>");moBufferXSLTemplate.append(msRetornoCarro);
        moBufferXSLTemplate.append("</fo:table>");moBufferXSLTemplate.append(msRetornoCarro);
    }
    
    /**
     * Devuelve el cuerpo de las tablas
     * @return cuerpo de tablas
     */
    public String getBufferXSLTemplate(){
        return moBufferXSLTemplate.toString();
    }
    static String getFuente(final Font poFuente){
        StringBuilder lsCadena=new StringBuilder(25);
        if(poFuente!=null){
            lsCadena.append(" font-size=\"");
            lsCadena.append(String.valueOf(poFuente.getSize()));
            lsCadena.append("pt\" ");
            if(poFuente.isBold()){
                lsCadena.append(" font-weight=\"bold\" ");
            }else{
                lsCadena.append(" font-weight=\"normal\" ");
            }
        }
        return lsCadena.toString();
    }
    static String getBorder(final String psDirec, final String psGrosor, final Color poColor, final String psEstilo){
        StringBuilder loAux = new StringBuilder(66);
        loAux.append(" border-");
        loAux.append(psDirec);
        loAux.append("-width=\"");
        loAux.append(psGrosor);
        loAux.append("\" border-");
        loAux.append(psDirec);
        loAux.append("-style=\"");
        loAux.append(psEstilo);
        loAux.append("\"  border-");
        loAux.append(psDirec);
        loAux.append("-color=\"rgb(");
        loAux.append(poColor.getRed());
        loAux.append(',');
        loAux.append(poColor.getGreen());
        loAux.append(',');
        loAux.append(poColor.getBlue());
        loAux.append(")\" ");
        return loAux.toString();
    }
    static String getBorder(final String psDirec, final String psGrosor, final Color poColor){
        return getBorder(psDirec,psGrosor,poColor,"solid");
    }
    static String getBorder(final String psDirec, final String psGrosor){
        return getBorder(psDirec, psGrosor,  Color.BLACK);
    }
    static String getAlineacion(final int plAlineacion){
        String lsAlineacion ="";
        switch(plAlineacion){
            case JListDatosXSL.mclAlineacionCen:
                lsAlineacion = "text-align=\"center\"";
                break;
            case JListDatosXSL.mclAlineacionDer:
                lsAlineacion = "text-align=\"right\"";
                break;
            case JListDatosXSL.mclAlineacionJus:
                lsAlineacion = "text-align=\"left\"";
                break;
            default:
                lsAlineacion = "text-align=\"left\"";
                break;
        }
        return lsAlineacion;
        
    }
    static String getBorder(final JListDatosXSLColumna poColumna){
        StringBuilder lsCadena = new StringBuilder(36);
        String lsEstilo;
        switch (poColumna.getBordeEstilo()){
            case JListDatosXSLColumna.mclBordeDashed:
                lsEstilo = "dashed";
                break;
            case JListDatosXSLColumna.mclBordeDotted:
                lsEstilo = "dotted";
                break;
            case JListDatosXSLColumna.mclBordeDouble:
                lsEstilo = "double";
                break;
            case JListDatosXSLColumna.mclBordeGroove:
                lsEstilo = "groove";
                break;
            case JListDatosXSLColumna.mclBordeHidden:
                lsEstilo = "hidden";
                break;
            case JListDatosXSLColumna.mclBordeInset:
                lsEstilo = "inset";
                break;
            case JListDatosXSLColumna.mclBordeOutset:
                lsEstilo = "outset";
                break;
            case JListDatosXSLColumna.mclBordeRidge:
                lsEstilo = "ridge";
                break;
            default:
                lsEstilo = "solid";
        }
        if(poColumna.getBordeAbajo()>0){
            lsCadena.append(
                getBorder(mcsafter, String.valueOf(poColumna.getBordeAbajo())+"pt",poColumna.getBordeColor() ,lsEstilo)
                );
        }
        if(poColumna.getBordeArriba()>0){
            lsCadena.append(
                getBorder(mcsbefore, String.valueOf(poColumna.getBordeArriba())+"pt",poColumna.getBordeColor() ,lsEstilo)
                );
        }
        if(poColumna.getBordeDerecha()>0){
            lsCadena.append(
                getBorder(mcsright, String.valueOf(poColumna.getBordeDerecha())+"pt",poColumna.getBordeColor() ,lsEstilo)
                );
        }
        if(poColumna.getBordeIzquierda()>0){
            lsCadena.append(
                getBorder(mcsleft, String.valueOf(poColumna.getBordeIzquierda())+"pt",poColumna.getBordeColor() ,lsEstilo)
                );
        }
        lsCadena.append(" number-columns-spanned=\"" + String.valueOf(poColumna.getColSpan()) + "\"");
        return lsCadena.toString();
    }
    
    /**
     * Devuelve la cadena del xsl-fo
     * @return cadena
     */
    public String getXSLFOString(){
        int lLen = moBufferPaginas.length() + moBufferPageSecuenceA.length() +
                moBufferXSLTemplate.length() + moBufferPageSecuenceD.length() +
                178;
        StringBuilder loBuffer = new StringBuilder(lLen);

        loBuffer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" );loBuffer.append(msRetornoCarro);
        
            loBuffer.append("    <fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">");loBuffer.append(msRetornoCarro);

                loBuffer.append("      <fo:layout-master-set>");loBuffer.append(msRetornoCarro);
                loBuffer.append(moBufferPaginas.toString());
                loBuffer.append("      </fo:layout-master-set>");loBuffer.append(msRetornoCarro);

                //3 lineas es necesario para realizar la ultima pagina
                loBuffer.append(moBufferPageSecuenceA.toString());
                loBuffer.append(moBufferXSLTemplate.toString());
                loBuffer.append(moBufferPageSecuenceD.toString());

            loBuffer.append("    </fo:root>");loBuffer.append(msRetornoCarro);
        
        return loBuffer.toString();
    }
    /**
     * Devuelve el InputSource del  xsl-fo
     * @return InputSource 
     */
    public InputSource getXSLFO() {
        return new InputSource(new CadenaLarga(getXSLFOString()));
    }    
}

