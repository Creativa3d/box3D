/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.config;

import java.io.File;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMGuardar;
import utiles.xml.dom.SAXBuilder;

public class JDatosGeneralesXML implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String mcsParametroRemoto = "Remoto";
    public static final String mcsParametroLocal  = "Local";
    public static final String mcsParametroLocalPruebas = "LocalPruebas";
    public static final String mcsCONEXIONDIRECTA= "CONEXIONDIRECTA";
    public static final String mcsCONEXIONSERVIDOR = "CONEXIONSERVIDOR";
    public static final String mcsCONEXIONES = "CONEXIONES";
    public static final String mcsCONEXION = "conexion";
    
    public static final String PARAMETRO_TipoSQL = "SimpleDataSource_TipoSQL";
    public static final String PARAMETRO_TipoConexion = "SimpleDataSource_TipoConexion";
    public static final String PARAMETRO_DRIVER_CLASS_NAME ="SimpleDataSource_driverClassName";
    public static final String PARAMETRO_Conexion ="SimpleDataSource_url";
    public static final String PARAMETRO_Usuario = "SimpleDataSource_user";
    public static final String PARAMETRO_Password = "SimpleDataSource_password";
    public static final String PARAMETRO_ConexionesMaximasSelect = "SimpleDataSource_conexionesMaximasSelect";
    public static final String PARAMETRO_ConexionesMaximasEdicion = "SimpleDataSource_conexionesMaximasEdicion";
    public static final String mcsLoginPBKDF2 = "mcsLoginPBKDF2";
    
    private String msNombreFicheroSinExtension;
    private Document moDocumento;
    
    public JDatosGeneralesXML(final String psNombreFicheroSinExtension){
        msNombreFicheroSinExtension=psNombreFicheroSinExtension;
    }
    public JDatosGeneralesXML(final Document poDocumento){
        moDocumento=poDocumento;
    }
    public String getNombreFicheroSinExtension(){
        return msNombreFicheroSinExtension;
    }
    private File moFile(String psFichero) throws Exception {
        File moFile=null;
        Class configurationParametersManagerClass = ConfigurationParametersManager.class;
        ClassLoader classLoader = configurationParametersManagerClass.getClassLoader();
        try {
            moFile=new java.io.File(classLoader.getResource(psFichero).getFile());
        } catch(Exception e1) {
            moFile = new java.io.File(psFichero);
        }
        return moFile;
    }    
    public void leer() throws Exception{
        File loFile = moFile(msNombreFicheroSinExtension + ".xml");
        if(!loFile.exists()){
            File loFileP = moFile(msNombreFicheroSinExtension + ".properties");
            convertirXML(loFileP, loFile);
//            System.out.println("Despues convertir XML");
        }
        SAXBuilder loSax = new SAXBuilder();
        //moDocumento = loSax.build(msNombreFicheroSinExtension + ".xml");
        moDocumento = loSax.build(loFile.toString());
//        System.out.println("Despues loSax.build");
    }
    
    public static void convertirXML(final File poFileProperties, final File poFileXML) throws Exception{
            Element loRoot = new  Element("root");
            Document loDoc = new Document(loRoot);
            if(poFileProperties.exists()){
                convertirXML(poFileProperties, loDoc);
            }
            JDOMGuardar.guardar(loDoc, poFileXML.getAbsolutePath());
    }
    public static void convertirXML(File loFile, Document poDoc) throws Exception{
        JLectorFicherosParametros loLector = new JLectorFicherosParametros(loFile.getAbsolutePath());
        Element loRoot = poDoc.getRootElement();

        Properties loPropiedades = loLector.getTodasPropiedades();
        for (Enumeration e = loPropiedades.propertyNames(); e.hasMoreElements();) {
            String key = (String)e.nextElement();
            String val = (String)loPropiedades.get(key);
            if(key.equals(PARAMETRO_Conexion.replace('_', '/')) ||
               key.equals(PARAMETRO_ConexionesMaximasEdicion.replace('_', '/')) ||
               key.equals(PARAMETRO_ConexionesMaximasSelect.replace('_', '/')) ||
               key.equals(PARAMETRO_DRIVER_CLASS_NAME.replace('_', '/')) ||
               key.equals(PARAMETRO_Password.replace('_', '/')) ||
               key.equals(PARAMETRO_TipoSQL.replace('_', '/')) ||
               key.equals(PARAMETRO_Usuario.replace('_', '/')) ) {
                Element loAux = Element.simpleXPath(loRoot,
                        loRoot.getName() + "/" + mcsCONEXIONDIRECTA);
                if(loAux == null){
                    loAux = new Element(mcsCONEXIONDIRECTA);
                    loRoot.addContent(loAux);
                }
                loAux.addContent(new Element(getSinRaros(key), val));
            }else{
                if(key.equals(mcsParametroLocal) ||
                   key.equals(mcsParametroLocalPruebas) ||
                   key.equals(mcsParametroRemoto)
                   ) {
                    Element loAux = Element.simpleXPath(loRoot, 
                            loRoot.getName() + "/" + mcsCONEXIONSERVIDOR);
                    if(loAux == null){
                        loAux = new Element(mcsCONEXIONSERVIDOR);
                        loRoot.addContent(loAux);
                    }
                    loAux.addContent(new Element(getSinRaros(key), val));
                }else{
                    loRoot.addContent(new Element(getSinRaros(key), val));
                }
            }        
        }
    }
    
    public String getPropiedad(final String psNombre, final String psValorDefecto){
        String lsResult = moDocumento.getPropiedad(psNombre, psValorDefecto);
        return lsResult;
    }
    public  Document getDocumento(){
        return moDocumento;
    }
    public Element getElemento(final String psNombre){
        return Element.simpleXPath(moDocumento.getRootElement(), moDocumento.getRootElement().getName() + "/" + moDocumento.getSinRarosConBarra(psNombre));
    }
    public String getPropiedad(final String psNombre){
        return getPropiedad(psNombre, "");
    }
    
    /**
     * Si existe la propiedad no hace nada, si no existe pone el valor pasado por
     * parametro
     */
    public void setPropiedadDefecto(final String psName, final String psValor){
        String lsValor = getPropiedad(psName, psValor);
        setPropiedad(psName, lsValor);
    }
    
    /**
     * Establece el valor en la propiedad
     * psnombre simpleXpath
     */
    public void setPropiedad(final String psNombre, final String psValor){
        moDocumento.setPropiedad(psNombre, psValor);
    }
    
    public void guardarFichero() throws Exception{
        JDOMGuardar.guardar(moDocumento, msNombreFicheroSinExtension + ".xml");
    }
    
    private static String getSinRaros(String psCadena){
        StringBuilder loBuffer = new StringBuilder(psCadena.length());
        for(int i = 0 ; i < psCadena.length(); i++){
            char lc = psCadena.charAt(i);
            if( (lc >=0 && lc <= 9 ) ||
                (lc >='a' && lc <= 'z' ) ||
                (lc >='A' && lc <= 'Z' )
              ){
                loBuffer.append(lc);
            }else{
                loBuffer.append('_');
            }
        }
        return loBuffer.toString();
        
    }
    
}
