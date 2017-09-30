/*
 * JxmlLectorInforme.java
 *
 * Created on 25 de enero de 2007, 8:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package impresionXML.impresion.xml;

import ListDatos.JFilaDatosDefecto;
import impresionXML.impresion.estructura.ITextoLibre;
import impresionXML.impresion.estructura.JEstiloLinea;
import impresionXML.impresion.pdf.JPDFImprimirInforme;
import java.awt.Color;
import java.awt.print.PageFormat;
import java.io.FileOutputStream;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMException;
import utiles.xml.dom.SAXBuilder;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;

public class JxmlLectorInforme {

    public static final String mcsCodigo = "codigo";
    public static final String mcsName = "name";
    public static final String mcsCamino = "camino";
    public static final String mcsX = "x";
    public static final String mcsY = "y";
    public static final String mcsDisenoCamino = "Disenocamino";
    public static final String mcsDisenoListaCampos = "ListaCampos";
    public static final String mcsDisenoX = "Disenox";
    public static final String mcsDisenoY = "Disenoy";
    public static final String mcsDisenoZOOM = "DisenoZOOM";
    public static final String mcsAncho = "ancho";
    public static final String mcsAlto = "alto";
    public static final String mcsAngulo = "angulo";
    public static final String mcsAjustar = "ajustar";
    public static final String mcsOrientacion = "orientacion";
    public static final String mcsMargenSuperior = "margenSuperior";
    public static final String mcsMargenInferior = "margenInferior";
    public static final String mcsMargenDerecho = "margenDerecho";
    public static final String mcsMargenIzquierdo = "margenIzquierdo";
    public static final String mcsFuente = "fuente";
    public static final String mcsSize = "size";
    public static final String mcsUnderline = "underline";
    public static final String mcsBold = "bold";
    public static final String mcsCursiva = "cursiva";
    public static final String mcsTachado = "tachado";
    public static final String mcsExtensible = "extensible";
    public static final String mcsX1 = "x1";
    public static final String mcsY1 = "y1";
    public static final String mcsX2 = "x2";
    public static final String mcsY2 = "y2";
    public static final String mcsEstilo = "estilo";
    public static final String mcsColor = "color";
    public static final String mcsAlineacion = "alineacion";
    public static final String mcsTexto = "texto";
    public static final String mcsMultilinea = "multilinea";
    public static final String mcsRedimension = "Redimension";
    public static final String mcsRedimensionProporcional = "proporcional";
    public static final String mcsRedimensionNoProporcional = "NOproporcional";
    public static final String mcsRedimensionTamanoRealOProporcinalSiNoCabe = "TamanoRealOProporcinalSiNoCabe";
    public static final String mcsOrientacionhorizontal = "horizontal";
    public static final String mcsOrientacionvertical = "vertical";
    public static boolean mcbOrigenRelativo = true;

    /**
     * Creates a new instance of JxmlLectorInforme
     */
    private JxmlLectorInforme() {
        super();
    }

    public static JxmlInforme leerInforme(final String psInforme) throws JDOMException {
        JxmlInforme loInforme = new JxmlInforme(psInforme);

        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            doc = builder.build(psInforme);
        } catch (Exception e) {
            if (mcbOrigenRelativo) {
                try {
                    doc = builder.build("." + psInforme);
                } catch (Exception e1) {
                    doc = builder.build(JxmlLectorInforme.class.getResourceAsStream(psInforme));
                }
            } else {
                doc = builder.build(JxmlLectorInforme.class.getResourceAsStream(psInforme));
            }
        }


        Element itr = doc.getRootElement();
        leerInforme(itr, loInforme);

        return loInforme;
    }

    private static void leerInforme(final Element poitr, final JxmlInforme poInforme) {
        JAtributos loAtributos = poitr.getAttributes();
        if (loAtributos != null) {
            for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                JAtributo loAtributo = (JAtributo) loAtributos.get(lAtrib);
                
                if (loAtributo.getName().compareToIgnoreCase(mcsDisenoCamino) == 0) {
                    poInforme.getDiseno().setImagen(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsDisenoX) == 0) {
                    poInforme.getDiseno().setX((float) getDouble(loAtributo.getValue(), "Fichero Diseno X erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsDisenoY) == 0) {
                    poInforme.getDiseno().setY((float) getDouble(loAtributo.getValue(), "Fichero Diseno Y erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsDisenoZOOM) == 0) {
                    poInforme.getDiseno().setZoom(getDouble(loAtributo.getValue(), "Fichero Diseno ZOOM erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsDisenoListaCampos) == 0) {
                    poInforme.getDiseno().setListaCamposPredefinidos(loAtributo.getValue());
                }
                
                if (loAtributo.getName().compareToIgnoreCase(mcsName) == 0) {
                    poInforme.setNombre(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsCodigo) == 0) {
                    poInforme.setCodInforme(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAlto) == 0) {
                    poInforme.setAlto((float) getDouble(loAtributo.getValue(), "Fichero Pagina Alto erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAncho) == 0) {
                    poInforme.setAncho((float) getDouble(loAtributo.getValue(), "Fichero Pagina Alto erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsMargenDerecho) == 0) {
                    poInforme.setMargenDerecho((float) getDouble(loAtributo.getValue(), "Fichero Pagina Margen derecho erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsMargenInferior) == 0) {
                    poInforme.setMargenInferior((float) getDouble(loAtributo.getValue(), "Fichero Pagina Margen Inferior erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsMargenIzquierdo) == 0) {
                    poInforme.setMargenIzquierdo((float) getDouble(loAtributo.getValue(), "Fichero Pagina Margen Izquierdoerroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsMargenSuperior) == 0) {
                    poInforme.setMargenSuperior((float) getDouble(loAtributo.getValue(), "Fichero Pagina Margen Superior erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsOrientacion) == 0) {
                    try {
                        if (loAtributo.getValue().compareToIgnoreCase(mcsOrientacionvertical) == 0) {
                            poInforme.setOrientacion(PageFormat.PORTRAIT);
                        }
                        if (loAtributo.getValue().compareToIgnoreCase(mcsOrientacionhorizontal) == 0) {
                            poInforme.setOrientacion(PageFormat.LANDSCAPE);
                        }
                    } catch (Exception e) {
                        JDepuracion.anadirTexto(JDepuracion.mclWARNING, JxmlLectorInforme.class.getName(), "Fichero Pagina orientacion erroneo: " + loAtributo.getValue());
                    }
                }
            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), poInforme.toString());
        Element itr;
        IListaElementos loHijos = poitr.getChildren();
        JxmlBanda loBanda = null;
        for (int i = 0; i < loHijos.size(); i++) {
            itr = (Element) loHijos.get(i);
            if (itr.getName().compareToIgnoreCase(JxmlBanda.mcsNombreXml) == 0) {
                loBanda = new JxmlBanda();
                poInforme.add(loBanda);
                leerBanda(itr, loBanda);
            }
            if (itr.getName().compareToIgnoreCase(JxmlFuente.mcsNombreXml) == 0) {
                JxmlFuente loFuente = new JxmlFuente();
                poInforme.add(loFuente);
                leerFuente(itr, loFuente);
            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), "fin informe " + poInforme.toString());
    }

    private static void leerFuente(final Element itr, final JxmlFuente poFuente) {
        JAtributos loAtributos = itr.getAttributes();
        if (loAtributos != null) {
            for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                JAtributo loAtributo = (JAtributo) loAtributos.get(lAtrib);
                if (loAtributo.getName().compareToIgnoreCase(mcsName) == 0) {
                    poFuente.setNombre(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsFuente) == 0) {
                    poFuente.setFuente(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsSize) == 0) {
                    try {
                        poFuente.setSize(Float.valueOf(loAtributo.getValue()).floatValue());
                    } catch (Exception e) {
                        JDepuracion.anadirTexto(JDepuracion.mclWARNING, JxmlLectorInforme.class.getName(), "Fichero Pagina Margen erroneo: " + loAtributo.getValue());
                    }
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsBold) == 0) {
                    poFuente.setBold(getBoolean(loAtributo.getValue()));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsCursiva) == 0) {
                    poFuente.setCursiva(getBoolean(loAtributo.getValue()));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsTachado) == 0) {
                    poFuente.setTachado(getBoolean(loAtributo.getValue()));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsUnderline) == 0) {
                    poFuente.setUnderline(getBoolean(loAtributo.getValue()));
                }
            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), poFuente.toString());
    }

    private static void leerBanda(final Element poitr, final JxmlBanda poBanda) {
        JAtributos loAtributos = poitr.getAttributes();
        if (loAtributos != null) {
            for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                JAtributo loAtributo = (JAtributo) loAtributos.get(lAtrib);
                if (loAtributo.getName().compareToIgnoreCase(mcsName) == 0) {
                    poBanda.setNombre(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAlto) == 0) {
                    poBanda.getPosicionDestino().setRect(
                            poBanda.getPosicionDestino().getX(),
                            poBanda.getPosicionDestino().getY(),
                            poBanda.getPosicionDestino().getWidth(),
                            getDouble(loAtributo.getValue(), "Fichero BANDA Alto erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAncho) == 0) {
                    poBanda.getPosicionDestino().setRect(
                            poBanda.getPosicionDestino().getX(),
                            poBanda.getPosicionDestino().getY(),
                            getDouble(loAtributo.getValue(), "Fichero BANDA Ancho erroneo: "),
                            poBanda.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsX) == 0) {
                    poBanda.getPosicionDestino().setRect(
                            getDouble(loAtributo.getValue(), "Fichero BANDA x erroneo: "),
                            poBanda.getPosicionDestino().getY(),
                            poBanda.getPosicionDestino().getWidth(),
                            poBanda.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsY) == 0) {
                    poBanda.getPosicionDestino().setRect(
                            poBanda.getPosicionDestino().getX(),
                            getDouble(loAtributo.getValue(), "Fichero BANDA y erroneo: "),
                            poBanda.getPosicionDestino().getWidth(),
                            poBanda.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsExtensible) == 0) {
                    poBanda.setExtensible(getBoolean(loAtributo.getValue()));
                }
            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), poBanda.toString());
        IListaElementos loHijos = poitr.getChildren();
        Element itr;
        for (int i = 0; i < loHijos.size(); i++) {
            itr = (Element) loHijos.get(i);
            if (itr.getName().compareToIgnoreCase(JxmlBanda.mcsNombreXml) == 0) {
                JxmlBanda loBanda;
                loBanda = new JxmlBanda();
                leerBanda(itr, loBanda);
            }
            if (itr.getName().compareToIgnoreCase(JxmlImagen.mcsNombreXml) == 0) {
                JxmlImagen loImagen = new JxmlImagen();
                poBanda.add(loImagen);
                leerImagen(itr, loImagen);
            }
            if (itr.getName().compareToIgnoreCase(JxmlCuadrado.mcsNombreXml) == 0) {
                JxmlCuadrado loCuadrado = new JxmlCuadrado();
                poBanda.add(loCuadrado);
                leerCuadrado(itr, loCuadrado);
            }
            if (itr.getName().compareToIgnoreCase(JxmlLinea.mcsNombreXml) == 0) {
                JxmlLinea loLinea = new JxmlLinea();
                poBanda.add(loLinea);
                leerLinea(itr, loLinea);
            }
            if (itr.getName().compareToIgnoreCase(JxmlTexto.mcsNombreXml) == 0) {
                JxmlTexto loTexto = new JxmlTexto();
                poBanda.add(loTexto);
                leerTexto(itr, loTexto);
            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), "fin banda " + poBanda.toString());
    }

    private static void leerLinea(final Element itr, final JxmlLinea poLinea) {
        JAtributos loAtributos = itr.getAttributes();
        if (loAtributos != null) {
            for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                JAtributo loAtributo = (JAtributo) loAtributos.get(lAtrib);
                if (loAtributo.getName().compareToIgnoreCase(mcsName) == 0) {
                    poLinea.setNombre(loAtributo.getValue());
                }

                if (loAtributo.getName().compareToIgnoreCase(mcsEstilo) == 0) {
                    if (loAtributo.getValue().compareToIgnoreCase("solido") == 0) {
                        poLinea.getEstiloLinea().mlEstilo = JEstiloLinea.mclSolido;
                    }
                    if (loAtributo.getValue().compareToIgnoreCase("punteado") == 0) {
                        poLinea.getEstiloLinea().mlEstilo = JEstiloLinea.mclPunteado;
                    }
                    if (loAtributo.getValue().compareToIgnoreCase("rayado") == 0) {
                        poLinea.getEstiloLinea().mlEstilo = JEstiloLinea.mclRayado;
                    }
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAncho) == 0) {
                    poLinea.getEstiloLinea().mdGrosor =
                            (float) getDouble(loAtributo.getValue(), "Fichero Linea Ancho erroneo: ");
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsX1) == 0) {
                    poLinea.getPunto1().setLocation(
                            getDouble(loAtributo.getValue(), "Fichero Linea X1 erroneo: "),
                            poLinea.getPunto1().getY());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsY1) == 0) {
                    poLinea.getPunto1().setLocation(
                            poLinea.getPunto1().getX(),
                            getDouble(loAtributo.getValue(), "Fichero Linea Y1 erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsX2) == 0) {
                    poLinea.getPunto2().setLocation(
                            getDouble(loAtributo.getValue(), "Fichero Linea X2 erroneo: "),
                            poLinea.getPunto2().getY());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsY2) == 0) {
                    poLinea.getPunto2().setLocation(
                            poLinea.getPunto2().getX(),
                            getDouble(loAtributo.getValue(), "Fichero Linea Y2 erroneo: "));
                }
            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), poLinea.toString());
    }

    private static void leerCuadrado(final Element itr, final JxmlCuadrado poCuadrado) {
        JAtributos loAtributos = itr.getAttributes();
        if (loAtributos != null) {
            for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                JAtributo loAtributo = (JAtributo) loAtributos.get(lAtrib);
                if (loAtributo.getName().compareToIgnoreCase(mcsName) == 0) {
                    poCuadrado.setNombre(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsEstilo) == 0) {
                    if (loAtributo.getValue().compareToIgnoreCase("solido") == 0) {
                        poCuadrado.getEstiloLinea().mlEstilo = JEstiloLinea.mclSolido;
                    }
                    if (loAtributo.getValue().compareToIgnoreCase("punteado") == 0) {
                        poCuadrado.getEstiloLinea().mlEstilo = JEstiloLinea.mclPunteado;
                    }
                    if (loAtributo.getValue().compareToIgnoreCase("rayado") == 0) {
                        poCuadrado.getEstiloLinea().mlEstilo = JEstiloLinea.mclRayado;
                    }
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAncho) == 0) {
                    poCuadrado.getEstiloLinea().mdGrosor =
                            (float) getDouble(loAtributo.getValue(), "Fichero Linea Ancho erroneo: ");
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsX1) == 0) {
                    poCuadrado.getPunto1().setLocation(
                            getDouble(loAtributo.getValue(), "Fichero Linea X1 erroneo: "),
                            poCuadrado.getPunto1().getY());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsY1) == 0) {
                    poCuadrado.getPunto1().setLocation(
                            poCuadrado.getPunto1().getX(),
                            getDouble(loAtributo.getValue(), "Fichero Linea Y1 erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsX2) == 0) {
                    poCuadrado.getPunto2().setLocation(
                            getDouble(loAtributo.getValue(), "Fichero Linea X2 erroneo: "),
                            poCuadrado.getPunto2().getY());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsY2) == 0) {
                    poCuadrado.getPunto2().setLocation(
                            poCuadrado.getPunto2().getX(),
                            getDouble(loAtributo.getValue(), "Fichero Linea Y2 erroneo: "));
                }

            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), poCuadrado.toString());
    }

    private static void leerImagen(final Element itr, final JxmlImagen poImagen) {
        JAtributos loAtributos = itr.getAttributes();
        if (loAtributos != null) {
            for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                JAtributo loAtributo = (JAtributo) loAtributos.get(lAtrib);
                if (loAtributo.getName().compareToIgnoreCase(mcsCamino) == 0) {
                    poImagen.setCamino(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsName) == 0) {
                    poImagen.setNombre(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAlto) == 0) {
                    poImagen.getPosicionDestino().setRect(
                            poImagen.getPosicionDestino().getX(),
                            poImagen.getPosicionDestino().getY(),
                            poImagen.getPosicionDestino().getWidth(),
                            getDouble(loAtributo.getValue(), "Fichero IMAGEN Alto erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAncho) == 0) {
                    poImagen.getPosicionDestino().setRect(
                            poImagen.getPosicionDestino().getX(),
                            poImagen.getPosicionDestino().getY(),
                            getDouble(loAtributo.getValue(), "Fichero IMAGEN Ancho erroneo: "),
                            poImagen.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsX) == 0) {
                    poImagen.getPosicionDestino().setRect(
                            getDouble(loAtributo.getValue(), "Fichero IMAGEN x erroneo: "),
                            poImagen.getPosicionDestino().getY(),
                            poImagen.getPosicionDestino().getWidth(),
                            poImagen.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsY) == 0) {
                    poImagen.getPosicionDestino().setRect(
                            poImagen.getPosicionDestino().getX(),
                            getDouble(loAtributo.getValue(), "Fichero IMAGEN y erroneo: "),
                            poImagen.getPosicionDestino().getWidth(),
                            poImagen.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().equalsIgnoreCase(mcsAjustar)) {
                    if (getBoolean(loAtributo.getValor().trim())) {
                        poImagen.setRedimensionTipo(poImagen.mclRedimensionProporcional);
                    }else{
                        poImagen.setRedimensionTipo(poImagen.mclRedimensionTamanoRealOProporcinalSiNoCabe);
                    }
                }
                if (loAtributo.getName().equalsIgnoreCase(mcsRedimension)) {
                    if (loAtributo.getValor().trim().equalsIgnoreCase(mcsRedimensionProporcional)) {
                        poImagen.setRedimensionTipo(poImagen.mclRedimensionProporcional);
                    }
                    if (loAtributo.getValor().trim().equalsIgnoreCase(mcsRedimensionNoProporcional)) {
                        poImagen.setRedimensionTipo(poImagen.mclRedimensionNoProporcional);
                    }
                    if (loAtributo.getValor().trim().equalsIgnoreCase(mcsRedimensionTamanoRealOProporcinalSiNoCabe)) {
                        poImagen.setRedimensionTipo(poImagen.mclRedimensionTamanoRealOProporcinalSiNoCabe);
                    }

                }

            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), poImagen.toString());
    }

    private static void leerTexto(final Element itr, final JxmlTexto poTexto) {
        JAtributos loAtributos = itr.getAttributes();
        if (loAtributos != null) {
            for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                JAtributo loAtributo = (JAtributo) loAtributos.get(lAtrib);
                if (loAtributo.getName().compareToIgnoreCase(mcsName) == 0) {
                    poTexto.setNombre(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAngulo) == 0) {
                    poTexto.setAngulo(getDouble(loAtributo.getValue(), "Fichero ANGULO erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAlto) == 0) {
                    poTexto.getPosicionDestino().setRect(
                            poTexto.getPosicionDestino().getX(),
                            poTexto.getPosicionDestino().getY(),
                            poTexto.getPosicionDestino().getWidth(),
                            getDouble(loAtributo.getValue(), "Fichero TEXTO Alto erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAncho) == 0) {
                    poTexto.getPosicionDestino().setRect(
                            poTexto.getPosicionDestino().getX(),
                            poTexto.getPosicionDestino().getY(),
                            getDouble(loAtributo.getValue(), "Fichero TEXTO Ancho erroneo: "),
                            poTexto.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsX) == 0) {
                    poTexto.getPosicionDestino().setRect(
                            getDouble(loAtributo.getValue(), "Fichero TEXTO x erroneo: "),
                            poTexto.getPosicionDestino().getY(),
                            poTexto.getPosicionDestino().getWidth(),
                            poTexto.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsY) == 0) {
                    poTexto.getPosicionDestino().setRect(
                            poTexto.getPosicionDestino().getX(),
                            getDouble(loAtributo.getValue(), "Fichero TEXTO y erroneo: "),
                            poTexto.getPosicionDestino().getWidth(),
                            poTexto.getPosicionDestino().getHeight());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsColor) == 0) {
                    poTexto.setColor(getColor(loAtributo.getValue(), "Fichero Texto Color erroneo: "));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsTexto) == 0) {
                    poTexto.setTexto(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsMultilinea) == 0) {
                    poTexto.setMultilinea(getBoolean(loAtributo.getValue()));
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsFuente) == 0) {
                    poTexto.setFuente(loAtributo.getValue());
                }
                if (loAtributo.getName().compareToIgnoreCase(mcsAlineacion) == 0) {
                    if (loAtributo.getValue().compareToIgnoreCase("left") == 0) {
                        poTexto.setAlineacion(ITextoLibre.mclAlineacionIzquierda);
                    }
                    if (loAtributo.getValue().compareToIgnoreCase("right") == 0) {
                        poTexto.setAlineacion(ITextoLibre.mclAlineacionDerecha);
                    }
                    if (loAtributo.getValue().compareToIgnoreCase("center") == 0) {
                        poTexto.setAlineacion(ITextoLibre.mclAlineacionCentro);
                    }
                    if (loAtributo.getValue().compareToIgnoreCase("justify") == 0) {
                        poTexto.setAlineacion(ITextoLibre.mclAlineacionJustificada);
                    }
                }
            }
        }
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION, JxmlLectorInforme.class.getName(), poTexto.toString());
    }

    private static Color getColor(final String psTexto, final String psEnCasoError) {
        Color loResult = Color.black;
        if (psTexto.indexOf(':') > 0) {
            String lsTexto = psTexto + ":";
            int r = Integer.valueOf(JFilaDatosDefecto.gfsExtraerCampo(lsTexto, 0, ':')).intValue();
            int g = Integer.valueOf(JFilaDatosDefecto.gfsExtraerCampo(lsTexto, 1, ':')).intValue();
            int b = Integer.valueOf(JFilaDatosDefecto.gfsExtraerCampo(lsTexto, 2, ':')).intValue();
            int a = -1;
            try {
                a = Integer.valueOf(JFilaDatosDefecto.gfsExtraerCampo(lsTexto, 3, ':')).intValue();
            } catch (Exception e) {
                //nada
            }
            try {
                if (a >= 0) {
                    loResult = new Color(r, g, b, a);
                } else {
                    loResult = new Color(r, g, b);
                }
            } catch (Exception e) {
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, JxmlLectorInforme.class.getName(), psEnCasoError + "  r=" + String.valueOf(r) + "g=" + String.valueOf(g) + "b=" + String.valueOf(b) + "a=" + String.valueOf(a));
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, JxmlLectorInforme.class.getName(), "Ponemos color negro");
            }
        } else {
            loResult = new Color(Integer.valueOf(psTexto).intValue());
        }
        return loResult;
    }
//
//    /**
//     * Devuelve el valor de la posición
//     * @return Valor
//     * @param psDatos Datos
//     * @param plPosicion Posicion
//     * @param lsSeparacion Separacion de los datos
//     */
//    public static String gfsExtraerCampo(final String psDatos, final int plPosicion, final char lsSeparacion) {
//        int lLen;
//        int i;
//        int lPosicion;
//        StringBuilder lsDato;
//        lLen = psDatos.length();
//        lPosicion = 0;
//        lsDato = new StringBuilder(25);
//        for (i = 0; i < lLen && lPosicion <= plPosicion; i++) {
//            if (psDatos.charAt(i) == lsSeparacion) {
//                lPosicion++;
//            } else {
//                if (lPosicion == plPosicion) {
//                    lsDato.append(psDatos.charAt(i));
//                } else if (lPosicion > plPosicion) {
//                    break;
//                }
//            }
//        }
//        return lsDato.toString();
//    }

    private static boolean getBoolean(final String psValor) {
        boolean lbResult = false;
        if (psValor != null && psValor.compareTo("") != 0) {
            lbResult = psValor.compareTo("si") == 0 || psValor.compareTo("1") == 0;
        }
        return lbResult;
    }

    private static double getDouble(final String psValor, final String psSiError) {
        double ldResult = 0;
        try {
            ldResult = Double.valueOf(psValor).doubleValue();
        } catch (Exception e) {
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, JxmlLectorInforme.class.getName(), psSiError + psValor);
        }
        return ldResult;

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
            JPDFImprimirInforme.imprimir(loInforme, new FileOutputStream("informe.pdf"));
            
            new JxmlGuardarInformeJasper(loInforme).guardarInforme("informe.jrxml");
            
        } catch (Exception e) {
            JDepuracion.anadirTexto(JxmlLectorInforme.class.getName(), e);
        }
    }
}
