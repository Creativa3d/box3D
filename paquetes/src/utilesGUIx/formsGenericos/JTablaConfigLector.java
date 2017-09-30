/*
 * JTablaConfigLector.java
 *
 * Created on 19 de julio de 2007, 12:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import ListDatos.JListDatosFiltroConj;
import ListDatos.JServerServidorDatosFichero;
import ListDatos.JUtilTabla;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import utiles.CadenaLarga;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JDepuracion;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMGuardar;
import utiles.xml.dom.SAXBuilder;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;

public class JTablaConfigLector {

    private static final String msRetornoCarro = System.getProperty("line.separator");
    private static final int mclMaxConfig = 2000;
    private static final String mcsTabla = "tabla";
    private static final String mcsConfiguracion = "configuracion";
    private static final String mcsNombre = "nombre";
    private static final String mcsCaption = "caption";
    private static final String mcsColumna = "columna";
    private static final String mcsOrden = "orden";
    private static final String mcsLong = "long";
    private static final String mcsCampoBusq = "campobusqueda";

    private static final String mcsFiltros = "filtros";
    private static final String mcsFiltroDefecto = "filtrodefecto";
    private static final String mcsFiltro = "filtro";

    /**
     * Creates a new instance of JTablaConfigLector
     */
    private JTablaConfigLector() {
    }

    public static void guardar(final JTablaConfigTablas poTablas, final String msFichero) throws FileNotFoundException, IOException {
        int lLen = 0;
        Document loDoc = new Document(new Element("root"));
        Element loRoot = loDoc.getRootElement();
        for (int i = 0; i < poTablas.size() && lLen < mclMaxConfig; i++) {
            JTablaConfigTabla loTabla = poTablas.getTabla(i);
            Element loTablaXML = new Element(mcsTabla);
            loRoot.addContent(loTablaXML);

            if (!JCadenas.isVacio(loTabla.getNombre())) {
                loTablaXML.getAttributes().addAtributo(mcsNombre, loTabla.getNombre());
                loTablaXML.getAttributes().addAtributo(mcsCampoBusq, loTabla.getCampoBusqueda());

                addConfigTabla(loTablaXML, loTabla, lLen);
                addFiltrosTabla(loTablaXML, loTabla.getFiltros(), loTabla.getFiltroDefecto());

            }
        }
        try {
            JDOMGuardar.guardar(loDoc, msFichero);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(JTablaConfigLector.class.getName(), ex);
            throw new IOException(ex.toString());
        }
    }

    private static void addFiltrosTabla(Element poTablaXML, HashMap<String, JTFiltro> filtros, String psDefecto) {
        if(!filtros.isEmpty()){
            Element loFiltros = new Element(mcsFiltros);
            loFiltros.getAttributes().addAtributo(mcsFiltroDefecto, psDefecto);
            poTablaXML.addContent(loFiltros);
            for (Map.Entry<String, JTFiltro> entry : filtros.entrySet()) {
                String lsNombre = entry.getKey();
                JTFiltro loFiltro = entry.getValue();
                Element loFiltroXML = new Element(mcsFiltro);
                loFiltros.addContent(loFiltroXML);

                loFiltroXML.getAttributes().addAtributo(mcsNombre, loFiltro.moList.msTabla);
                loFiltroXML.setValor(JUtilTabla.getListDatos2String(loFiltro.getList(), false));
            }
        }
    }

    private static void addConfigTabla(Element poTablaXML, JTablaConfigTabla poTabla, int plLen) {
        for (int ii = 0; ii < poTabla.size() && plLen < mclMaxConfig; ii++) {
            JTablaConfigTablaConfig loConfig = poTabla.getConfig(ii);
            Element loConfigXML = new Element(mcsConfiguracion);
            poTablaXML.addContent(loConfigXML);

            loConfigXML.getAttributes().addAtributo(mcsNombre, loConfig.getNombre());

            for (int iii = 0; iii < loConfig.size(); iii++) {
                JTablaConfigColumna loColumn = loConfig.getColumna(iii);
                Element loColumnXML = new Element(mcsColumna);
                loConfigXML.addContent(loColumnXML);

                loColumnXML.getAttributes().addAtributo(mcsNombre, loColumn.getNombre());
                loColumnXML.getAttributes().addAtributo(mcsOrden, String.valueOf(loColumn.getOrden()));
                loColumnXML.getAttributes().addAtributo(mcsLong, String.valueOf(loColumn.getLong()));
                loColumnXML.getAttributes().addAtributo(mcsCaption, loColumn.getCaption());

            }
        }
    }

    private static String getStringNormal(String psParam) {
        String lsCaracValidos = "áéíóúÁÉÍÓÚABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz ,.;:-_'¡?¿()=/%$·\"!º\\ª|@#*+[]{}0123456789";
        StringBuilder lsResul = new StringBuilder(psParam.length());
        for (int i = 0; i < psParam.length(); i++) {
            if (lsCaracValidos.indexOf(psParam.charAt(i)) >= 0) {
                lsResul.append(psParam.charAt(i));
            }
        }
        return lsResul.toString();
    }

    public static JTablaConfigTablas getTablaConfigTablas(final String psCamino) throws Exception {
        Document doc;
        JTablaConfigTablas loResult = new JTablaConfigTablas();

        //inicializacion
        File loFile = new File(psCamino);
        if (loFile.length() < 8 * 1024 * 1024) {
            InputStream inStream = new FileInputStream(psCamino);
            try {
                SAXBuilder builder = new SAXBuilder();
                doc = builder.build(inStream);
            } finally {
                inStream.close();
            }
            //proceso

            Element itr = doc.getRootElement();
            IListaElementos loHijos = itr.getChildren();
            JTablaConfigTabla loTabla;
            for (int i = 0; i < loHijos.size(); i++) {
                itr = (Element) loHijos.get(i);
                if (itr.getName().toLowerCase().equals(mcsTabla)) {
                    loTabla = new JTablaConfigTabla();
                    JAtributos loAtributos = itr.getAttributes();
                    if (loAtributos != null) {
                        for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                            JAtributo loAtributo = loAtributos.getAtributo(lAtrib);
                            if (loAtributo.getName().toLowerCase().equals(mcsNombre)) {
                                loTabla.setNombre(loAtributo.getValor());
                            }
                            if (loAtributo.getName().toLowerCase().equals(mcsCampoBusq)) {
                                loTabla.setCampoBusqueda(loAtributo.getValor());
                            }
                        }
                    }
                    rellenarTabla(itr, loTabla);
                    loResult.addTabla(loTabla);
                }
            }
        } else {
            throw new Exception("Fichero long. " + psCamino + " tiene mas de 8mb");
        }
        return loResult;
    }

    private static void rellenarTabla(final Element poitr, final JTablaConfigTabla loTabla) {
        IListaElementos loHijos = poitr.getChildren();
        JTablaConfigTablaConfig loConfig;
        Element itr;
        for (int i = 0; i < loHijos.size(); i++) {
            itr = (Element) loHijos.get(i);
            if (itr.getName().toLowerCase().equals(mcsConfiguracion)) {
                loConfig = new JTablaConfigTablaConfig();
                JAtributos loAtributos = itr.getAttributes();
                if (loAtributos != null) {
                    for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                        JAtributo loAtributo = loAtributos.getAtributo(lAtrib);
                        if (loAtributo.getName().toLowerCase().equals(mcsNombre)) {
                            loConfig.setNombre(loAtributo.getValor());
                        }
                    }
                }
                rellenarConfig(itr, loConfig);
                loTabla.addConfig(loConfig);
            }
            if (itr.getName().toLowerCase().equals(mcsFiltros)) {
                IListaElementos loFiltros = itr.getChildren();
                JAtributos loAtributos = itr.getAttributes();
                if (loAtributos != null) {
                    for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                        JAtributo loAtributo = loAtributos.getAtributo(lAtrib);
                        if (loAtributo.getName().toLowerCase().equals(mcsFiltroDefecto)) {
                            loTabla.setFiltroDefecto(loAtributo.getValor());
                        }
                    }
                }                    
                for (Object loFiltroA : loFiltros) {
                    Element loFiltro = (Element) loFiltroA;
                    addFiltro(loFiltro, loTabla);
                }
            }

        }
    }

    private static void addFiltro(final Element poitr, final JTablaConfigTabla poTabla) {
        try {
            String lsNombre = poitr.getAttribute(mcsNombre).getValor();
            String lsFiltroTabla = poitr.getText();

            JTFiltro loFiltro = new JTFiltro();
            loFiltro.moList.msTabla = lsNombre;
            JServerServidorDatosFichero loF = new JServerServidorDatosFichero("", '\t', true);
            loF.recuperarDatosFichero(loFiltro.getList(), new CadenaLarga(lsFiltroTabla), new JListDatosFiltroConj(), "");

            poTabla.getFiltros().put(lsNombre, loFiltro);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(JTablaConfigLector.class.getName(), ex);
        }
    }

    private static void rellenarConfig(final Element poitr, final JTablaConfigTablaConfig loConfig) {
        IListaElementos loHijos = poitr.getChildren();
        JTablaConfigColumna loColumna;
        Element itr;
        for (int i = 0; i < loHijos.size(); i++) {
            itr = (Element) loHijos.get(i);
            if (itr.getName().toLowerCase().equals(mcsColumna)) {
                loColumna = new JTablaConfigColumna();
                JAtributos loAtributos = itr.getAttributes();
                if (loAtributos != null) {
                    for (int lAtrib = 0; lAtrib < loAtributos.size(); lAtrib++) {
                        JAtributo loAtributo = loAtributos.getAtributo(lAtrib);
                        if (loAtributo.getName().toLowerCase().equals(mcsNombre)) {
                            loColumna.setNombre(loAtributo.getValor());
                        }
                        if (loAtributo.getName().toLowerCase().equals(mcsOrden)) {
                            loColumna.setOrden(Integer.valueOf(loAtributo.getValor()).intValue());
                        }
                        if (loAtributo.getName().toLowerCase().equals(mcsLong)) {
                            loColumna.setLong(Integer.valueOf(loAtributo.getValor()).intValue());
                        }
                        if (loAtributo.getName().toLowerCase().equals(mcsCaption)) {
                            loColumna.setCaption(getStringNormal(loAtributo.getValor()));
                        }
                    }
                }
                loConfig.addColumna(loColumna);
            }
        }
    }
}
