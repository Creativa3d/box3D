/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml;

import impresionXML.impresion.estructura.ITextoLibre;
import impresionXML.impresion.estructura.JEstiloLinea;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import utiles.JConversiones;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMGuardar;

/**
 *
 * @author eduardo
 */
public class JxmlGuardarInforme {
    public static void guardarInforme(JxmlInforme poInforme, final String psRuta)  throws Exception{
        Element loRoot = new Element(JxmlInforme.mcsNombreXml);
        Document loDoc = new Document(loRoot);
        addInforme(loRoot, poInforme);
        for(int i = 0 ; i < poInforme.sizeFuente(); i++){
            addFuente(loRoot, poInforme.getFuente(i));
        }
        for(int i = 0 ; i < poInforme.sizeBanda(); i++){
            addBanda(loRoot, poInforme.getBanda(i));
        }
        JDOMGuardar.guardar(loDoc, psRuta);
    }
    
    private static void addInforme(Element loRoot, JxmlInforme poInforme){
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsCodigo, poInforme.getCodInforme());
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsName, poInforme.getNombre());
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsAlto, getPosicion(poInforme.getAlto()));
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsAncho, getPosicion(poInforme.getAncho()));
        switch(poInforme.getOrientacion()){
            case PageFormat.LANDSCAPE:
                loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsOrientacion, JxmlLectorInforme.mcsOrientacionhorizontal);
                break;
            default://portraid
                loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsOrientacion, JxmlLectorInforme.mcsOrientacionvertical);
        }
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsMargenDerecho, getPosicion(poInforme.getMargenDerecho()));
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsMargenInferior, getPosicion(poInforme.getMargenInferior()));
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsMargenIzquierdo, getPosicion(poInforme.getMargenIzquierdo()));
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsMargenSuperior, getPosicion(poInforme.getMargenSuperior()));
        
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsDisenoCamino, poInforme.getDiseno().getImagen());
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsDisenoListaCampos, poInforme.getDiseno().getListaCamposPredefinidos());
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsDisenoX, getPosicion(poInforme.getDiseno().getX()));
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsDisenoY, getPosicion(poInforme.getDiseno().getY()));
        loRoot.getAttributes().addAtributo(JxmlLectorInforme.mcsDisenoZOOM, getPosicion(poInforme.getDiseno().getZoom()));
    }

    private static void addFuente(Element loRoot, JxmlFuente pofuente) {
        Element loFuente = new Element(pofuente.mcsNombreXml);
        
        loFuente.getAttributes().addAtributo(JxmlLectorInforme.mcsName, pofuente.getNombre());
        loFuente.getAttributes().addAtributo(JxmlLectorInforme.mcsFuente, pofuente.getFuente());
        loFuente.getAttributes().addAtributo(JxmlLectorInforme.mcsSize, String.valueOf(pofuente.getSize()));
        loFuente.getAttributes().addAtributo(JxmlLectorInforme.mcsBold, getBoolean(pofuente.isBold()));
        loFuente.getAttributes().addAtributo(JxmlLectorInforme.mcsCursiva, getBoolean(pofuente.isCursiva()));
        loFuente.getAttributes().addAtributo(JxmlLectorInforme.mcsTachado, getBoolean(pofuente.isTachado()));
        loFuente.getAttributes().addAtributo(JxmlLectorInforme.mcsUnderline, getBoolean(pofuente.isUnderline()));
        
        loRoot.add(loFuente);
    }
    private static void addBanda(Element loRoot, JxmlBanda poBanda) {
        Element loBanda = new Element(poBanda.mcsNombreXml);
        
        loBanda.getAttributes().addAtributo(JxmlLectorInforme.mcsName, poBanda.getNombre());
        addPosicion(loBanda, poBanda.getPosicionDestino());
        loBanda.getAttributes().addAtributo(JxmlLectorInforme.mcsExtensible, poBanda.isMbExtensible()?"si":"no");
        
        loRoot.add(loBanda);
        for(int i = 0 ; i < poBanda.size(); i++){
            addObjeto(loBanda, poBanda.get(i));
        }
    }

    private static void addObjeto(Element loBanda, IxmlObjetos poObjeto) {
        Element loObjeto = new Element(poObjeto.getNombreXML());
        loBanda.add(loObjeto);
        if(poObjeto instanceof JxmlBanda){
            addBanda(loObjeto, (JxmlBanda) poObjeto);
        }
        if(poObjeto instanceof JxmlCuadrado){
            addCuadrado(loObjeto, (JxmlCuadrado) poObjeto);
        }
        if(poObjeto instanceof JxmlImagen){
            addImagen(loObjeto, (JxmlImagen) poObjeto);
        }
        if(poObjeto instanceof JxmlLinea){
            addLinea(loObjeto, (JxmlLinea) poObjeto);
        }
        if(poObjeto instanceof JxmlTexto){
            addTexto(loObjeto, (JxmlTexto) poObjeto);
        }
        
    }

    private static void addCuadrado(Element loObjeto, JxmlCuadrado jxmlCuadrado) {
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsName, jxmlCuadrado.getNombre());
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsX1, getPosicion(jxmlCuadrado.getPunto1().getX()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsY1, getPosicion(jxmlCuadrado.getPunto1().getY()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsX2, getPosicion(jxmlCuadrado.getPunto2().getX()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsY2, getPosicion(jxmlCuadrado.getPunto2().getY()));

        addEstilo(loObjeto ,jxmlCuadrado.getEstiloLinea());
        
    }


    private static void addLinea(Element loObjeto, JxmlLinea jxmlLinea) {
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsName, jxmlLinea.getNombre());
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsX1, getPosicion(jxmlLinea.getPunto1().getX()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsY1, getPosicion(jxmlLinea.getPunto1().getY()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsX2, getPosicion(jxmlLinea.getPunto2().getX()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsY2, getPosicion(jxmlLinea.getPunto2().getY()));

        addEstilo(loObjeto ,jxmlLinea.getEstiloLinea());
    }

    private static void addImagen(Element loObjeto, JxmlImagen jxmlImagen) {
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsName, jxmlImagen.getNombre());
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsCamino, jxmlImagen.getCamino());
        addPosicion(loObjeto, jxmlImagen.getPosicionDestino());
        switch(jxmlImagen.getRedimensionTipo()){
            case JxmlImagen.mclRedimensionNoProporcional:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsRedimension, JxmlLectorInforme.mcsRedimensionNoProporcional);
                break;
            case JxmlImagen.mclRedimensionProporcional:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsRedimension, JxmlLectorInforme.mcsRedimensionProporcional);
                break;
            default:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsRedimension, JxmlLectorInforme.mcsRedimensionTamanoRealOProporcinalSiNoCabe);
        }

        
    }

    private static void addTexto(Element loObjeto, JxmlTexto jxmlTexto) {
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsName, jxmlTexto.getNombre());
        addPosicion(loObjeto, jxmlTexto.getPosicionDestino());
        switch(jxmlTexto.getAlineacion()){
            case ITextoLibre.mclAlineacionCentro:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAlineacion, "center");
                break;
            case ITextoLibre.mclAlineacionDerecha:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAlineacion, "right");
                break;
            case ITextoLibre.mclAlineacionJustificada:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAlineacion, "justify");
                break;
            default:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAlineacion, "left");
        }
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsTexto, jxmlTexto.getTexto());
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsMultilinea, getBoolean(jxmlTexto.isMultilinea()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsColor, getColor(jxmlTexto.getColor()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsFuente, jxmlTexto.getFuente());
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAngulo, String.valueOf(jxmlTexto.getAngulo()));
        
        
    }
    private static String getColor(Color poColor){
        return String.valueOf(poColor.getRed()) + ":" 
                + String.valueOf(poColor.getGreen()) + ":" 
                + String.valueOf(poColor.getBlue()) ;
    }
    private static String getBoolean(boolean pbBoolean){
        return pbBoolean ? "si" : "no";
    }

    private static void addEstilo(Element loObjeto, JEstiloLinea poEstiloLinea) {
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAncho, String.valueOf(poEstiloLinea.mdGrosor));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsColor, getColor(poEstiloLinea.moColor));
        switch(poEstiloLinea.mlEstilo){
            case JEstiloLinea.mclPunteado:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsEstilo, "punteado");
                break;
            case JEstiloLinea.mclRayado:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsEstilo, "rayado");
                break;
            default:
                loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsEstilo, "solido");
                break;
        }
                
    }
    private static void addPosicion(Element loObjeto, Rectangle2D poRect ) {
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsX, getPosicion(poRect.getX()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsY, getPosicion(poRect.getY()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAncho, getPosicion(poRect.getWidth()));
        loObjeto.getAttributes().addAtributo(JxmlLectorInforme.mcsAlto, getPosicion(poRect.getHeight()));
    }
    private static String getPosicion(double pdPosi){
        return String.valueOf(JConversiones.numeroDecimales(pdPosi, 2));
    }
    

    
}
