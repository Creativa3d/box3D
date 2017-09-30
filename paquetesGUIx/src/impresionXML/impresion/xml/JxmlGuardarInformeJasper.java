/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml;

import impresionXML.impresion.estructura.ITextoLibre;
import impresionXML.impresion.estructura.JEstiloLinea;
import impresionXML.impresion.motorImpresion.JPagina;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.util.HashMap;
import java.util.Set;
import utiles.JCadenas;
import utiles.JConversiones;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMGuardar;
import utiles.xml.sax.JAtributo;

/**
 *
 * @author eduardo
 */
public class JxmlGuardarInformeJasper {
    private final JxmlInforme moInforme;
    private final HashMap<String, String> moFields = new HashMap<String, String>(); 
    
    public JxmlGuardarInformeJasper (JxmlInforme poInforme){
        moInforme=poInforme;
    }
    
    public void guardarInforme(final String psRuta)  throws Exception{
        
        Element loRoot = new Element("jasperReport");
        Document loDoc = new Document(loRoot);
        loDoc.setEnconding("UTF-8");
        loDoc.setReemplazarAcentosYEnes(false);
        loDoc.setReemplazarMayorMenorYDemas(false);

        
        addInforme(loRoot, moInforme);
        for(int i = 0 ; i < moInforme.sizeBanda(); i++){
            addFields(loRoot, moInforme.getBanda(i));
        }
        Set<String> loCampos = moFields.keySet();
        for(String lsCampo : loCampos){
            loRoot.addContent(newElement("field", new JAtributo[]{new JAtributo("name", lsCampo), new JAtributo("class", "java.lang.String")}) );
        }
        
        Element loElem = new Element("background");
        loElem.addContent(newElement("band", new JAtributo[]{new JAtributo("splitType", "Stretch")}));
        loRoot.addContent(loElem);
        loElem = new Element("title");
        loElem.addContent(newElement("band", new JAtributo[]{new JAtributo("height", "0"), new JAtributo("splitType", "Stretch")}));
        loRoot.addContent(loElem);
        loElem = new Element("pageHeader");
        loElem.addContent(newElement("band", new JAtributo[]{new JAtributo("height", "0"), new JAtributo("splitType", "Stretch")}));
        loRoot.addContent(loElem);
        loElem = new Element("columnHeader");
        loElem.addContent(newElement("band", new JAtributo[]{new JAtributo("height", "0"), new JAtributo("splitType", "Stretch")}));
        loRoot.addContent(loElem);

        loElem = new Element("detail");
        loRoot.addContent(loElem);

        
        for(int i = 0 ; i < moInforme.sizeBanda(); i++){
            addBanda(loElem, moInforme.getBanda(i));
        }
        
        
        loElem = new Element("columnFooter");
        loElem.addContent(newElement("band", new JAtributo[]{new JAtributo("height", "0"), new JAtributo("splitType", "Stretch")}));
        loRoot.addContent(loElem);
        loElem = new Element("pageFooter");
        loElem.addContent(newElement("band", new JAtributo[]{new JAtributo("height", "0"), new JAtributo("splitType", "Stretch")}));
        loRoot.addContent(loElem);
        loElem = new Element("summary");
        loElem.addContent(newElement("band", new JAtributo[]{new JAtributo("height", "0"), new JAtributo("splitType", "Stretch")}));
        loRoot.addContent(loElem);
        
        JDOMGuardar.guardar(loDoc, psRuta);
    }
    
    private void addInforme(Element loRoot, JxmlInforme poInforme){
        loRoot.getAttributes().addAtributo("xmlns", "http://jasperreports.sourceforge.net/jasperreports");
        loRoot.getAttributes().addAtributo("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        loRoot.getAttributes().addAtributo("xsi:schemaLocation", "http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd");
        loRoot.getAttributes().addAtributo("name", "report name");
        loRoot.getAttributes().addAtributo("pageWidth", String.valueOf((int)JPagina.mdConvertir(poInforme.getAncho())) );
        loRoot.getAttributes().addAtributo("pageHeight", String.valueOf((int)JPagina.mdConvertir(poInforme.getAlto())) );

        if(poInforme.getOrientacion()==PageFormat.LANDSCAPE){
            loRoot.getAttributes().addAtributo("orientation", "Landscape");
        }
                
                        
        loRoot.getAttributes().addAtributo("columnWidth", 
                String.valueOf((int)JPagina.mdConvertir(poInforme.getAnchoTotalReal()) 
                        - (int)JPagina.mdConvertir(poInforme.getMargenDerecho()) 
                        - (int)JPagina.mdConvertir(poInforme.getMargenIzquierdo()) )
        );
        loRoot.getAttributes().addAtributo("leftMargin", String.valueOf((int)JPagina.mdConvertir(poInforme.getMargenIzquierdo())));
        loRoot.getAttributes().addAtributo("rightMargin", String.valueOf((int)JPagina.mdConvertir(poInforme.getMargenDerecho())));
        loRoot.getAttributes().addAtributo("topMargin", String.valueOf((int)JPagina.mdConvertir(poInforme.getMargenSuperior())));
        loRoot.getAttributes().addAtributo("bottomMargin", String.valueOf((int)JPagina.mdConvertir(poInforme.getMargenInferior())));
        
        loRoot.addContent(newElement("property", new JAtributo[]{new JAtributo("name", "ireport.zoom"), new JAtributo("value", "1.0")}) )  ;
        loRoot.addContent(newElement("property", new JAtributo[]{new JAtributo("name", "ireport.x"), new JAtributo("value", "0")}) );
        loRoot.addContent(newElement("property", new JAtributo[]{new JAtributo("name", "ireport.y"), new JAtributo("value", "0")}) );
    }
    private Element newElement(String psNombre, JAtributo[] poAtributos){
        Element loElem = new Element(psNombre);
        for(int i = 0; i < poAtributos.length; i++){
            loElem.getAttributes().add(poAtributos[i]);
        }
        return loElem;
        
    }
    private void addFields(Element loRoot, JxmlBanda poObjeto) {
        for(int i = 0 ; i < poObjeto.size(); i++){
            IxmlObjetos loAux = poObjeto.get(i);
            if(loAux instanceof JxmlBanda){
                addFields(loRoot, (JxmlBanda) loAux);
            }
            if(loAux instanceof JxmlTexto){
                JxmlTexto loT = (JxmlTexto) loAux;
                if(!JCadenas.isVacio(loT.getNombre())){
                    if(moFields.get(loT.getNombre())==null){
                        moFields.put(loT.getNombre(), loT.getNombre());
                    }
                }
            }
            if(loAux instanceof JxmlImagen){
                JxmlImagen loT = (JxmlImagen) loAux;
                if(!JCadenas.isVacio(loT.getNombre())){
                    if(moFields.get(loT.getNombre())==null){
                        moFields.put(loT.getNombre(), loT.getNombre());
                    }
                }
            }            
        }
        
    }

    private void addBanda(Element loRoot, JxmlBanda poBanda) {
        Element loBandaXML = newElement("band", new JAtributo[]{
            new JAtributo("height", String.valueOf((int)JPagina.mdConvertir(moInforme.getAltoTotalReal() - moInforme.getMargenSuperior() - moInforme.getMargenInferior())))
            , new JAtributo("splitType", "Stretch")
        });
        loRoot.add(loBandaXML);
        for(int i = 0 ; i < poBanda.size(); i++){
            addObjeto(loBandaXML, poBanda.get(i));
        }
    }

    private void addObjeto(Element loBanda, IxmlObjetos poObjeto) {
        if(poObjeto instanceof JxmlBanda){
            addBanda(loBanda, (JxmlBanda) poObjeto);
        }
        if(poObjeto instanceof JxmlCuadrado){
            addCuadrado(loBanda, (JxmlCuadrado) poObjeto);
        }
        if(poObjeto instanceof JxmlImagen){
            addImagen(loBanda, (JxmlImagen) poObjeto);
        }
        if(poObjeto instanceof JxmlLinea){
            addLinea(loBanda, (JxmlLinea) poObjeto);
        }
        if(poObjeto instanceof JxmlTexto){
            addTexto(loBanda, (JxmlTexto) poObjeto);
        }
        
    }

    private Element getReportElement(Rectangle2D poRect, Color poFore){
        Element loPosi = new Element("reportElement");
        loPosi.getAttributes().addAtributo("x", String.valueOf((int)JPagina.mdConvertir(poRect.getX())));
        loPosi.getAttributes().addAtributo("y", String.valueOf((int)JPagina.mdConvertir(poRect.getY())));
        loPosi.getAttributes().addAtributo("width", String.valueOf((int)JPagina.mdConvertir(poRect.getWidth())));
        loPosi.getAttributes().addAtributo("height", String.valueOf((int)JPagina.mdConvertir(poRect.getHeight())));
        if(poFore!=null){
            String rgb = Integer.toHexString(poFore.getRGB());
            rgb = rgb.substring(2, rgb.length());
            loPosi.getAttributes().addAtributo("forecolor", "#"+rgb);
        }
        return loPosi;
        
    }
    private void addCuadrado(Element poBanda, JxmlCuadrado jxmlCuadrado) {
        Element loXml = new Element("rectangle");
        poBanda.addContent(loXml);
        loXml.addContent(getReportElement(jxmlCuadrado.getRectangulo(), jxmlCuadrado.getEstiloLinea().moColor));
        Element loGrap = new Element("graphicElement");
        loXml.addContent(loGrap);
        
        loGrap.addContent(
                newElement("pen", new JAtributo[]{
                    new JAtributo("lineWidth", String.valueOf(jxmlCuadrado.getEstiloLinea().mdGrosor))
                    }
                )
        );
        
    }


    private void addLinea(Element poBanda, JxmlLinea jxmlLinea) {
        Element loXml = new Element("line");
        poBanda.addContent(loXml);
        loXml.addContent(getReportElement(jxmlLinea.getRectangulo(), jxmlLinea.getEstiloLinea().moColor));
        Element loGrap = new Element("graphicElement");
        loXml.addContent(loGrap);
        
        
        loGrap.addContent(
                newElement("pen", new JAtributo[]{
                    new JAtributo("lineWidth", String.valueOf(jxmlLinea.getEstiloLinea().mdGrosor))
                    }
                )
        );
        
    }

    private void addImagen(Element poBanda, JxmlImagen jxmlImagen) {
        Element loXml = new Element("image");
        poBanda.addContent(loXml);
        loXml.addContent(getReportElement(jxmlImagen.getPosicionDestino(), null));
        
        loXml.addContent(
                new Element("imageExpression", getCDATA(jxmlImagen.getNombre(), jxmlImagen.getCamino()))
        );        
    }

    private String getCDATA(String psNombre, String psValor){
        return "<![CDATA[" + (JCadenas.isVacio(psNombre) ? "\""+psValor+"\"" :"$F{"+psNombre+"}")+"]]>";
    }
    
    private void addTexto(Element poBanda, JxmlTexto jxmlTexto) {
        
        Element loRect = new Element("textField");
        poBanda.addContent(loRect);
        Element loPosi = getReportElement(jxmlTexto.getPosicionDestino(), jxmlTexto.getColor());
        loRect.addContent(loPosi);
        
        Element lotextElement = new Element("textElement");
        loRect.addContent(lotextElement);
        
        //alineacion y fuente
        switch(jxmlTexto.getAlineacion()){
            case ITextoLibre.mclAlineacionCentro:
                lotextElement.getAttributes().addAtributo("textAlignment", "Center");
                break;
            case ITextoLibre.mclAlineacionDerecha:
                lotextElement.getAttributes().addAtributo("textAlignment", "Right");
                break;
            case ITextoLibre.mclAlineacionJustificada:
                lotextElement.getAttributes().addAtributo("textAlignment", "Justified");
                break;
            default:
                lotextElement.getAttributes().addAtributo("textAlignment", "Left");
        }
        if(jxmlTexto.getAngulo()==90){
            lotextElement.getAttributes().addAtributo("rotation","Left");
        } else if(jxmlTexto.getAngulo()==180){
            lotextElement.getAttributes().addAtributo("rotation","Right");
        }
        Element lotextFont = new Element("font");
        lotextElement.addContent(lotextFont);
        JxmlFuente loFuente = moInforme.getFuente(jxmlTexto.getFuente());
        lotextFont.getAttributes().addAtributo("fontName", loFuente.getFuente());
        lotextFont.getAttributes().addAtributo("size", String.valueOf((int)loFuente.getSize()));
        lotextFont.getAttributes().addAtributo("isBold", String.valueOf(loFuente.isBold()) );
        lotextFont.getAttributes().addAtributo("isItalic", String.valueOf(loFuente.isCursiva()));
        lotextFont.getAttributes().addAtributo("isUnderline", String.valueOf(loFuente.isUnderline()));
        lotextFont.getAttributes().addAtributo("isStrikeThrough", String.valueOf(loFuente.isTachado()));
        
        Element loExpre = new Element("textFieldExpression", getCDATA(jxmlTexto.getNombre(), jxmlTexto.getTexto()));
        loRect.addContent(loExpre);
        
    }

    private String getPosicion(double pdPosi){
        return String.valueOf(JConversiones.numeroDecimales(pdPosi, 2));
    }
    

    
}
