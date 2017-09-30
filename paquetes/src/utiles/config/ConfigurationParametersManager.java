/*
 * ConfigurationParametersManager.java
 *
 * Created  on 25 de agosto de 2003, 9:30
 */ 

package utiles.config;
import java.io.*;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Date;
import utiles.JDepuracion;
/** Lee parametros de configuracion*/
public final class ConfigurationParametersManager {
    /**nombre del fichero de configuracion*/
    public static String msFichero ="ConfigurationParameters.properties";
    private static Properties moProperties=null;

    //solo para guardar
    private static final String specialSaveChars = "=: \t\r\n\f#!";
    //solo para guardar
    private static final char[] hexDigit = {
	'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
    private static boolean mbCargado = false;
    private static File moFile=null;

    private static void cargar(){
        if(!mbCargado){
            mbCargado = true;
            recargar();
        }
    }
    
    private ConfigurationParametersManager() {}

    public static void recargar(){
        try {
            /* Leemos el fichero de propiedades (si existe).*/
//                Class configurationParametersManagerClass = ConfigurationParametersManager.class;
//                ClassLoader classLoader = configurationParametersManagerClass.getClassLoader();
            java.io.File loFile = moFile();
            java.io.InputStream inputStream = new FileInputStream(loFile);
            moProperties = new Properties();
            moProperties.load(inputStream);
            inputStream.close();
            JDepuracion.anadirTexto (JDepuracion.mclINFORMACION, "ConfigurationParametersManager","*** Usando fichero "+loFile.getAbsolutePath()+" para configuracion ***");
            /*
            * Usamos "HashMap" en vez de "HashTable" porque
            * los metodos de HashMap son NO synchronized (asi ellos son 
            * mas rapidos), y los parametros son de solo lectura
            */
            setTodasPropiedades(moProperties);
        } catch (Exception e) {
            JDepuracion.anadirTexto ( JDepuracion.mclINFORMACION, "ConfigurationParametersManager","Error  en fichero "+msFichero);
            JDepuracion.anadirTexto ("ConfigurationParametersManager",e);
        }
        
    }
    /**
     * devuelve el paramentro
     * @return valor
     * @param psNombre nombre
     * @throws FaltaConfiguracionParametroException error
     */
    public static String getParametro(String psNombre) throws FaltaConfiguracionParametroException {
        cargar();
        String lsValor = (String) moProperties.get(psNombre);
        if (lsValor == null) {
            throw new FaltaConfiguracionParametroException(psNombre);
        }
        return lsValor;
    }

    /**
     * Establece el parametro
     * @param psNombre nombre
     * @param psValor valor
     */
    public static void setParametro(String psNombre, String psValor){
        cargar();
        moProperties.remove(psNombre);
        moProperties.put(psNombre, psValor);
    }
    private static java.io.File moFile() throws Exception {
        if(moFile==null){
            ClassLoader classLoader = ConfigurationParametersManager.class.getClassLoader();
            try {
                moFile=new java.io.File(classLoader.getResource(msFichero).getFile());
            } catch(Exception e1) {
                moFile = new java.io.File(msFichero);
                if (!moFile.exists()) {
                     PrintStream loPrint = new PrintStream(new FileOutputStream(moFile));
                     loPrint.println("propiedad=valorPropiedad");
                     loPrint.flush();
                     loPrint.close();
                }
            }
        }
        return moFile;
    }
    /**
     * Guarda las propiedades en el fichero
     * @throws Exception error
     */
    public static void guardarPropiedades() throws Exception {
        cargar();
        OutputStream OStream = new FileOutputStream(moFile());
        Properties properties = getTodasPropiedades();
        store(properties,OStream, null);
        OStream.close();
    }
    /**
     * obtener todas las propiedades
     * @return objeto properties con todas las propiedades
     */
    public static Properties getTodasPropiedades(){
        cargar();
        return moProperties;
    }
    /**
     * establecer todas las propiedades
     * @param poProperties objeto properties con todos los nuevos valores
     * @throws Exception error
     */
    public static void setTodasPropiedades(Properties poProperties) throws Exception {
        cargar();
        moProperties = poProperties;
    }
    //Guadar las propiedades
    private static void store(Properties properties, OutputStream out, String header) throws IOException    {
        BufferedWriter awriter;
        awriter = new BufferedWriter(new OutputStreamWriter(out, "8859_1"));
        if (header != null) {
            writeln(awriter, "#" + header);
        }
        writeln(awriter, "#" + (new Date()).toString());
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements();) {
            String key = (String)e.nextElement();
            String val = (String)properties.get(key);
            key = saveConvert(key, true);

	    /* No need to escape embedded and trailing spaces for value, hence
	     * pass false to flag.
	     */
            val = saveConvert(val, false);
            writeln(awriter, key + "=" + val);
        }
        awriter.flush();
    }
    //escribir en fichero
    private static void writeln(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }
    /*
     * Converts unicodes to encoded &#92;uxxxx
     * and writes out any of the characters in specialSaveChars
     * with a preceding slash
     */
    private static String saveConvert(String theString, boolean escapeSpace) {
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len*2);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            switch(aChar) {
		case ' ':
		    if (x == 0 || escapeSpace) {
			outBuffer.append('\\');
                    }

		    outBuffer.append(' ');
		    break;
                case '\\':outBuffer.append('\\'); outBuffer.append('\\');
                          break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >>  8) & 0xF));
                        outBuffer.append(toHex((aChar >>  4) & 0xF));
                        outBuffer.append(toHex( aChar        & 0xF));
                    } else {
                        if (specialSaveChars.indexOf(aChar) != -1){
                            outBuffer.append('\\');
                        }
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }
    /**
     * Convert a nibble to a hex character
     * @param	nibble	the nibble to convert.
     */
    private static char toHex(int nibble) {
	return hexDigit[(nibble & 0xF)];
    }
    
} // class
