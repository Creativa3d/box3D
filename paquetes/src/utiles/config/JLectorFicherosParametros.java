/*
 * ConfigurationParametersManager.java
 *
 * Created on 25 de agosto de 2003, 9:30
 */

package utiles.config;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import utiles.JDepuracion;
import utiles.red.*;
/** Lee parametros de configuracion*/
public final class JLectorFicherosParametros {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    /**nombre del fichero de configuracion*/
    public String msFichero ="ConfigurationParameters.properties";
    private Properties moProperties=null;

    //solo para guardar
    private static final String specialSaveChars = "=: \t\r\n\f#!";
    //solo para guardar
    private static final char[] hexDigit = {
	'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
    private boolean mbCargado = false;
    public boolean mbDepuracion = true;

    private void cargar(){
        if(!mbCargado){
            mbCargado = true;
            recargar();
        }
    }
    
    public JLectorFicherosParametros() {
        super();
    }
    public JLectorFicherosParametros(String psFichero) {
        super();
        msFichero=psFichero;
    }
    private static InputStream getInputStream(String psFile) throws IOException {
        InputStream loInput=null;
        if(psFile.indexOf("file:")>-1 || 
           psFile.indexOf("http:")>-1 ||
           psFile.indexOf("ftp:") >-1
           ){
            try{
                loInput = msoOpenConnection.getConnection(new URL(psFile)).getInputStream();
//                loInput = (new URL(psFile)).openConnection(ProxyConfig.getProxy()).getInputStream();
            }catch(Exception e){
                throw new  IOException(e.toString());
            }
        }else{
            Class configurationParametersManagerClass = ConfigurationParametersManager.class;
            ClassLoader classLoader = configurationParametersManagerClass.getClassLoader();
            try {
                loInput=classLoader.getResourceAsStream(psFile);
            } catch(Exception e1) {
            }
            if(loInput==null){
                File loFile = new java.io.File(psFile);
                if (!loFile.exists()) {
                     PrintStream loPrint = new PrintStream(new FileOutputStream(loFile));
                     loPrint.println("valor=propiedad");
                     loPrint.flush();
                     loPrint.close();
                }
                loInput = new FileInputStream(loFile);
            }
        }
        return loInput;

    }
    
    public void recargar(){
        try {
            /* Leemos el fichero de propiedades (si existe).*/
//                Class configurationParametersManagerClass = ConfigurationParametersManager.class;
//                ClassLoader classLoader = configurationParametersManagerClass.getClassLoader();
            java.io.InputStream inputStream = getInputStream(msFichero);
            moProperties = new Properties();
//            int l=inputStream.read();
//            while(l!=-1) {
//                System.out.print((char)l);
//                l = inputStream.read();
//            }
            moProperties.load(inputStream);
            inputStream.close();
            JDepuracion.anadirTexto (JDepuracion.mclINFORMACION, getClass().getName(), "*** Usando fichero "+msFichero+" para configuracion ***");
            /*
            * Usamos "HashMap" en vez de "HashTable" porque
            * los metodos de HashMap son NO synchronized (asi ellos son 
            * mas rapidos), y los parametros son de solo lectura
            */
            setTodasPropiedades(moProperties);
        } catch (Exception e) {
            if(mbDepuracion){
                JDepuracion.anadirTexto (JDepuracion.mclINFORMACION, getClass().getName(), "Error  en fichero "+msFichero);
                JDepuracion.anadirTexto (getClass().getName(),e);
            }
        }
        
    }
    /**
     * devuelve el paramentro
     * @return valor
     * @param psNombre nombre
     * @throws FaltaConfiguracionParametroException error
     */
    public String getParametro(String psNombre) throws FaltaConfiguracionParametroException {
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
    public void setParametro(String psNombre, String psValor){
        cargar();
        moProperties.remove(psNombre);
        moProperties.put(psNombre, psValor);
    }
    /**
     * Guarda las propiedades en el fichero
     * @throws Exception error
     */
    public void guardarPropiedades() throws Exception {
        cargar();
        OutputStream OStream = new FileOutputStream(msFichero);
        Properties properties = getTodasPropiedades();
        store(properties,OStream, null);
        OStream.close();
    }
    /**
     * obtener todas las propiedades
     * @return objeto properties con todas las propiedades
     */
    public Properties getTodasPropiedades(){
        cargar();
        return moProperties;
    }
    /**
     * establecer todas las propiedades
     * @param poProperties objeto properties con todos los nuevos valores
     * @throws Exception error
     */
    public void setTodasPropiedades(Properties poProperties) throws Exception {
        cargar();
        moProperties = poProperties;
    }
    //Guadar las propiedades
    private void store(Properties properties, OutputStream out, String header) throws IOException    {
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
    private void writeln(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }
    /*
     * Converts unicodes to encoded &#92;uxxxx
     * and writes out any of the characters in specialSaveChars
     * with a preceding slash
     */
    private String saveConvert(String theString, boolean escapeSpace) {
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
    private char toHex(int nibble) {
	return hexDigit[(nibble & 0xF)];
    }
    
} // class
