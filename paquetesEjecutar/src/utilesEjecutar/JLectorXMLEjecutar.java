

package utilesEjecutar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import utiles.JDepuracion;
import utiles.JFormat;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.SAXBuilder;
import utiles.xml.sax.JAtributo;
import utiles.xml.sax.JAtributos;
import utilesEjecutar.ejecutar.IEjecutarInstruccion;
import utilesEjecutar.ejecutar.JEjecucionesControlador;
import utilesEjecutar.fuentes.IFuente;


public class JLectorXMLEjecutar {
    public static final String mcsFuentes = "fuentes";
    public static final String mcsEjecuciones = "ejecuciones";
    public static final String mcsConfiguracion = "configuracion";
    public static final String mcsParametros = "parametros";
    public static final String mcsAgrupar = "Agrupar";

    private String msFichero;

    public JLectorXMLEjecutar(final String psFichero){
        msFichero = psFichero;
    }

    private static String sustituirVariable(String psValor, String psVariableNombre, String psVariableValor){
        return JFormat.replace2(psValor,"%"+psVariableNombre+"%", psVariableValor);
    }
    private static void sustituirVariable(Element poElem , JAtributo poVariable){
        poElem.setValor(
                sustituirVariable(
                        poElem.getValue(),
                        poVariable.getName(),
                        poVariable.getValor()
                        ));
        for(int i = 0 ; i < poElem.getAttributes().size(); i++){
            JAtributo loAtrib = poElem.getAttributes().getAtributo(i);
            loAtrib.setValor(
                    sustituirVariable(
                        loAtrib.getValor() ,
                        poVariable.getName(),
                        poVariable.getValor())
                    );
        }
    }
    private static void sustituirVariables(Element poRoot, JAtributos poVariables){
        for(int ii = 0 ; ii < poVariables.size(); ii++){
            sustituirVariable(
                    poRoot,
                    poVariables.getAtributo(ii)
                    );
        }
        for(int i = 0; i < poRoot.getChildren().size(); i++){
            Element loElem = (Element) poRoot.getChildren().get(i);
            sustituirVariables(loElem, poVariables);
            for(int ii = 0 ; ii < poVariables.size(); ii++){
                sustituirVariable(
                        loElem,
                        poVariables.getAtributo(ii)
                        );
            }
        }

    }
    private static void leer(Element poRoot, JControladorCoordinadorEjecutar poControlador) throws Throwable{

        for(int i = 0; i < poRoot.getChildren().size() && !poControlador.isCancelado(); i++){
            Element loElem = (Element) poRoot.getChildren().get(i);
            sustituirVariables(loElem, poControlador.getVariables());
            if(loElem.getNombre().equalsIgnoreCase(mcsParametros)){
                for(int ii = 0; ii < loElem.getChildren().size() && !poControlador.isCancelado(); ii++){
                    Element loElemAux = (Element) loElem.getChildren().get(ii);
                    poControlador.addVariable(
                            loElemAux.getNombre(),
                            loElemAux.getValor());
                }
            }
            if(loElem.getNombre().equalsIgnoreCase(mcsConfiguracion)){
                poControlador.setParametros(loElem.getAttributes());
            }
            if(loElem.getNombre().equalsIgnoreCase(mcsFuentes)){
                for(int lAux = 0 ; lAux < loElem.getChildren().size();lAux++){
                    try{
                        Element loAux = (Element) loElem.getChildren().get(lAux);
                        IFuente loFuente =poControlador.getAbstractFactory().getFuente(loAux.getName().trim(), poControlador);
                        loFuente.setParametros(loAux.getAttributes(), loAux.getChildren());
                        poControlador.getFuentesControlador().getFuentes().add(loFuente);
                    }catch(Throwable e){
                        poControlador.addError(JLectorXMLEjecutar.class.getName(), e);
                    }
                }

            }
            if(loElem.getNombre().equalsIgnoreCase(mcsEjecuciones)){
                leerEjecuciones(loElem, poControlador, poControlador.getEjecucionesControlador());
            }
        }
    }
    private static void leerEjecuciones(Element loElem, JControladorCoordinadorEjecutar poControlador, JEjecucionesControlador poEjecuciones) throws Throwable{
        poEjecuciones.setParametros(loElem.getAttributes(), loElem.getChildren());
        for(int lAux = 0 ; lAux < loElem.getChildren().size() && !poControlador.isCancelado();lAux++){
            try{
                Element loAux = (Element) loElem.getChildren().get(lAux);
                if(loAux.getName().equalsIgnoreCase(mcsAgrupar)){
                    JEjecucionesControlador loAgru = new JEjecucionesControlador(poControlador);
                    loAgru.setParametros(loAux.getAttributes(), loAux.getChildren());
                    poEjecuciones.getEjecuciones().add(loAgru);
                    leerEjecuciones(loAux, poControlador, loAgru);
                }else{
                    IEjecutarInstruccion loEje = poControlador.getAbstractFactory().getEjecutar(loAux.getNombre(), poControlador);
                    loEje.setParametros(loAux.getAttributes(), loAux.getChildren());
                    poEjecuciones.getEjecuciones().add(loEje);
                }
            }catch(Throwable e){
                poControlador.addError(JLectorXMLEjecutar.class.getName() ,e);
            }
        }
    }

    /**Lee el xml de configuracion*/
    public void leer(JControladorCoordinadorEjecutar poControlador) throws Throwable {
        SAXBuilder loSax = new SAXBuilder();
        //copiamos el ejecutar en local y usamos el local
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,"", "antes leer ejecucion.xml");
        String lsFichero = getFicheroPath(msFichero);
        JDepuracion.anadirTexto(JDepuracion.mclINFORMACION,"", "Despues leer ejecucion.xml");
        Document loD = loSax.build(lsFichero);
        leer(loD.getRootElement(), poControlador);
    }

    private String getFicheroPath(String psFichero) {
        String lsR = psFichero;
        //si es un archivo local no hace falta sacar una copia
        if(!new File(psFichero).exists()){
            StringBuffer lsNombrenuevo = new StringBuffer(psFichero.length());
            int i = 0;
            while(i<psFichero.length()){
                if((psFichero.charAt(i)>='a' && psFichero.charAt(i)<='z') ||
                   (psFichero.charAt(i)>='A' && psFichero.charAt(i)<='Z') ||
                   (psFichero.charAt(i)>='0' && psFichero.charAt(i)<='9')){
                    lsNombrenuevo.append(psFichero.charAt(i));
                }
                i++;
            }

            //creamos los directorios previos
            File loFile1 = new File(
                        System.getProperty("user.home"),
                        "listDatos");
            loFile1.mkdirs();
            loFile1 = new File(loFile1, "paquetesEjecutar");
            loFile1.mkdirs();
            //fichero real el fichero
            File loFile = new File(
                    loFile1.getAbsolutePath(),
                    lsNombrenuevo.toString()
                    );
            try{
                //copiamos el archivo
                InputStream loIn = SAXBuilder.getInputStream(msFichero);
                FileOutputStream loOut = new FileOutputStream(loFile);
                try{
                    byte[] buffer = new byte[255];
                    int lLen = 0;
                    while( (lLen = loIn.read(buffer)) != -1 ){
                        loOut.write(buffer, 0 , lLen);
                    }
                }finally{
                    loOut.close();
                    loIn.close();
                }
            }catch(Throwable e){
            }
            lsR = loFile.getAbsolutePath();
        }
        return lsR;
    }
    


}
