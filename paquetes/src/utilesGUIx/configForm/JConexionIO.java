/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.configForm;

import ListDatos.JSelect;
import ListDatos.JSelectMotor;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosConexion;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.config.ConfigurationParametersManager;
import utiles.config.JDatosGeneralesXML;
import utiles.config.JLectorFicherosParametros;
import utiles.xml.dom.Element;
import utiles.xml.sax.JAtributo;
import utilesBD.poolConexiones.PoolObjetos;

public class JConexionIO {
    private final String mcsNombre = "nombre";

    private JLectorFicherosParametros moLector=null;
    private JDatosGeneralesXML moLectorXML = null;

    private String msPrefijo = "";

    /***
     * Prefijo de las variables cuando se guarda en el fichero
     * @param psPrefijo
     */
    public void setPrefijo(String psPrefijo){
        msPrefijo = psPrefijo;
    }
    /**
     * Establece el lector
     * @param poLector
     */
    public void setLector(JLectorFicherosParametros poLector){
        moLector=poLector;
        msPrefijo = "";
    }
    /**
     * Establece el lector
     * @param poLector
     */
    public void setLector(JDatosGeneralesXML poLector){
        moLectorXML=poLector;
        msPrefijo = JDatosGeneralesXML.mcsCONEXIONDIRECTA;
    }
    /**
     * Lee las propiedades del fichero
     */
    public void leerPropiedades(JConexion poConex){
        leerPropiedades(poConex.getConexion());
    }
    /**
     * Lee las propiedades del fichero
     */
    public void leerPropiedades(JServerServidorDatosConexion poConex){
        try {
            if(moLector!=null){
                poConex.setClase( moLector.getParametro(msPrefijo+PoolObjetos.PARAMETRO_DRIVER_CLASS_NAME));
                poConex.setURL( moLector.getParametro(msPrefijo+PoolObjetos.PARAMETRO_Conexion));
                poConex.setPASSWORD( moLector.getParametro(msPrefijo+PoolObjetos.PARAMETRO_Password));
                poConex.setUSUARIO( moLector.getParametro(msPrefijo+PoolObjetos.PARAMETRO_Usuario));
                poConex.setTipoBD( Integer.valueOf(moLector.getParametro(msPrefijo+PoolObjetos.PARAMETRO_TipoSQL)).intValue());
                try{
                    poConex.setTipoConexion( Integer.valueOf(ConfigurationParametersManager.getParametro(msPrefijo+JDatosGeneralesXML.PARAMETRO_TipoConexion)).intValue());
                }catch(Exception e){
                    poConex.setTipoConexion( JServerServidorDatos.mclTipoBD);
                }
            }else{
                if(moLectorXML!=null){
                    poConex.setClase( moLectorXML.getPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME));
                    poConex.setURL( moLectorXML.getPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_Conexion));
                    poConex.setPASSWORD( moLectorXML.getPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_Password));
                    poConex.setUSUARIO( moLectorXML.getPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_Usuario));
                    try{
                        poConex.setTipoConexion( Integer.valueOf(moLectorXML.getPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_TipoConexion)).intValue());
                    }catch(Exception e){
                        poConex.setTipoConexion( JServerServidorDatos.mclTipoBD);
                    }
                    try{
                        poConex.setTipoBD( Integer.valueOf(moLectorXML.getPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_TipoSQL)).intValue());
                    }catch(Exception e){
                        poConex.setTipoBD( JSelectMotor.mclSqlServer);
                    }

                }else{
                    poConex.setClase( ConfigurationParametersManager.getParametro(msPrefijo+PoolObjetos.PARAMETRO_DRIVER_CLASS_NAME));
                    poConex.setURL( ConfigurationParametersManager.getParametro(msPrefijo+PoolObjetos.PARAMETRO_Conexion));
                    poConex.setPASSWORD( ConfigurationParametersManager.getParametro(msPrefijo+PoolObjetos.PARAMETRO_Password));
                    poConex.setUSUARIO( ConfigurationParametersManager.getParametro(msPrefijo+PoolObjetos.PARAMETRO_Usuario));
                    poConex.setTipoBD( Integer.valueOf(ConfigurationParametersManager.getParametro(msPrefijo+PoolObjetos.PARAMETRO_TipoSQL)).intValue());
                    try{
                        poConex.setTipoConexion( Integer.valueOf(ConfigurationParametersManager.getParametro(msPrefijo+JDatosGeneralesXML.PARAMETRO_TipoConexion)).intValue());
                    }catch(Exception e){
                        poConex.setTipoConexion( JServerServidorDatos.mclTipoBD);
                    }
                }
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }
    /**
     * Guarda las propiedades en el fichero
     * @throws java.lang.Exception
     */
    public void guardarPropiedades(JConexion poConex) throws Exception{
        guardarPropiedades(poConex.getConexion());
    }
    /**
     * Guarda las propiedades en el fichero
     * @throws java.lang.Exception
     */
    public void guardarPropiedades(JServerServidorDatosConexion poConex) throws Exception{
        if(moLector!=null){
            moLector.setParametro(msPrefijo + PoolObjetos.PARAMETRO_DRIVER_CLASS_NAME, poConex.getClase() );
            moLector.setParametro(msPrefijo + PoolObjetos.PARAMETRO_Conexion, poConex.getURL() );
            moLector.setParametro(msPrefijo + PoolObjetos.PARAMETRO_Password, poConex.getPASSWORD());
            moLector.setParametro(msPrefijo + PoolObjetos.PARAMETRO_Usuario, poConex.getUSUARIO());
            moLector.setParametro(msPrefijo + PoolObjetos.PARAMETRO_TipoSQL, String.valueOf(poConex.getTipoBD()));
            moLector.setParametro(msPrefijo + JDatosGeneralesXML.PARAMETRO_TipoConexion, String.valueOf(poConex.getTipoConexion()));
            moLector.guardarPropiedades();
        }else{
            if(moLectorXML!=null){
                moLectorXML.setPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME, poConex.getClase() );
                moLectorXML.setPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_Conexion, poConex.getURL() );
                moLectorXML.setPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_Password, poConex.getPASSWORD());
                moLectorXML.setPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_Usuario, poConex.getUSUARIO());
                moLectorXML.setPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_TipoSQL, String.valueOf(poConex.getTipoBD()));
                moLectorXML.setPropiedad(msPrefijo + "/" + JDatosGeneralesXML.PARAMETRO_TipoConexion, String.valueOf(poConex.getTipoConexion()));
                moLectorXML.guardarFichero();
            }else{
                ConfigurationParametersManager.setParametro(msPrefijo + PoolObjetos.PARAMETRO_DRIVER_CLASS_NAME, poConex.getClase() );
                ConfigurationParametersManager.setParametro(msPrefijo + PoolObjetos.PARAMETRO_Conexion, poConex.getURL() );
                ConfigurationParametersManager.setParametro(msPrefijo + PoolObjetos.PARAMETRO_Password, poConex.getPASSWORD());
                ConfigurationParametersManager.setParametro(msPrefijo + PoolObjetos.PARAMETRO_Usuario, poConex.getUSUARIO());
                ConfigurationParametersManager.setParametro(msPrefijo + PoolObjetos.PARAMETRO_TipoSQL, String.valueOf(poConex.getTipoBD()));
                ConfigurationParametersManager.setParametro(msPrefijo + JDatosGeneralesXML.PARAMETRO_TipoConexion, String.valueOf(poConex.getTipoConexion()));
                ConfigurationParametersManager.guardarPropiedades();

            }
        }
    }
    public void guardarPropiedadesConexion(JConexion poConex, Element poConexXML) throws Exception{
        guardarPropiedadesConexion(poConex.getConexion(), poConexXML);
    }
    public void guardarPropiedadesConexion(JServerServidorDatosConexion poConex, Element poConexXML){
        poConexXML.addContent(new Element(
                    JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME,
                    poConex.getClase()
                    ));
        poConexXML.addContent(new Element(
                    JDatosGeneralesXML.PARAMETRO_Conexion,
                    poConex.getURL()
                    ));
        poConexXML.addContent(new Element(
                    JDatosGeneralesXML.PARAMETRO_Usuario,
                    poConex.getUSUARIO()
                    ));
        poConexXML.addContent(new Element(
                    JDatosGeneralesXML.PARAMETRO_Password,
                    poConex.getPASSWORD()
                    ));
        poConexXML.addContent(new Element(
                    JDatosGeneralesXML.PARAMETRO_TipoConexion,
                    String.valueOf(poConex.getTipoConexion())
                    ));
        poConexXML.addContent(new Element(
                    JDatosGeneralesXML.PARAMETRO_TipoSQL,
                    String.valueOf(poConex.getTipoBD()
                    )));
    }
    public void leerPropiedadesConexion(JConexion poConex, Element poConexXML) throws Exception{
        leerPropiedadesConexion(poConex.getConexion(), poConexXML);
        try{
            poConex.leerConfig();
        }catch(Exception e){
        }
    }
    public void leerPropiedadesConexion(JServerServidorDatosConexion poConex, Element poConexXML) throws Exception{

        if(moLectorXML!=null){
            try{
                poConex.setClase( poConexXML.getChild(JDatosGeneralesXML.PARAMETRO_DRIVER_CLASS_NAME).getText());
            }catch(Exception e){
                poConex.setClase( "");
            }
            try{
                poConex.setURL( poConexXML.getChild(JDatosGeneralesXML.PARAMETRO_Conexion).getText());
            }catch(Exception e){
                poConex.setURL( "");
            }
            try{
                poConex.setPASSWORD( poConexXML.getChild(JDatosGeneralesXML.PARAMETRO_Password).getText());
            }catch(Exception e){
                poConex.setPASSWORD( "");
            }
            try{
                poConex.setUSUARIO( poConexXML.getChild(JDatosGeneralesXML.PARAMETRO_Usuario).getText());
            }catch(Exception e){
                poConex.setUSUARIO( "");
            }
            try{
                poConex.setTipoConexion( Integer.valueOf(poConexXML.getChild(JDatosGeneralesXML.PARAMETRO_TipoConexion).getText()).intValue());
            }catch(Exception e){
                poConex.setTipoConexion( JServerServidorDatos.mclTipoBD);
            }
            try{
                poConex.setTipoBD( Integer.valueOf(poConexXML.getChild(JDatosGeneralesXML.PARAMETRO_TipoSQL).getText()).intValue());
            }catch(Exception e){
                poConex.setTipoBD( JSelectMotor.mclSqlServer);
            }

        }
    }
    public JConexionLista leerListaConexiones() throws Exception{
        JConexionLista loLista = new JConexionLista();
        Element loServidores = moLectorXML.getElemento(JDatosGeneralesXML.mcsCONEXIONES);
        if(loServidores!=null){
            IListaElementos loElem =  loServidores.getChildren();
            for(int i = 0 ; i < loElem.size(); i++){
                Element loAux = (Element)loElem.get(i);
                JConexion loConex = new JConexion();
                JAtributo loAtrib =  loAux.getAttribute("nombre");
                if(loAtrib!=null){
                    loConex.setNombre(loAtrib.getValor());
                }
                leerPropiedadesConexion(loConex, loAux);
                loLista.add(loConex);
            }
        }
        //antigua conexion
        String lsAux = msPrefijo;
        try{
            msPrefijo = JDatosGeneralesXML.mcsCONEXIONDIRECTA;
            JConexion loConex = new JConexion();
            leerPropiedades(loConex);
            if(loConex.getConexion().getClase()!=null && !loConex.getConexion().getClase().equals("")){
                loConex.setNombre(JDatosGeneralesXML.mcsCONEXIONDIRECTA);
                loConex.leerConfig();
                loLista.add(loConex);
            }
            loServidores = moLectorXML.getDocumento().getRootElement().getChild(JDatosGeneralesXML.mcsCONEXIONSERVIDOR);
            if(loServidores!=null){
                for(int i = 0 ; i < loServidores.getChildren().size(); i++){
                    Element loAux = (Element) loServidores.getChildren().get(i);
                    loConex = new JConexion();
                    loConex.getConexion().setTipoConexion (JServerServidorDatos.mclTipoInternetComprimido);
                    loConex.getConexion().setURL (loAux.getText());
                    loConex.setNombre(loAux.getName());
                    loLista.add(loConex);
                }
            }
        }finally{
            msPrefijo=lsAux;
        }
        return loLista;
        
    }
    public void guardarListaConexiones(JConexionLista poLista) throws Exception{
        //recuepramos las conexiones
        Element loServidores = moLectorXML.getElemento(JDatosGeneralesXML.mcsCONEXIONES);
        if(loServidores==null){
            loServidores = new Element(JDatosGeneralesXML.mcsCONEXIONES);
            moLectorXML.getDocumento().getRootElement().add(loServidores);
        }
        //borramos las conmexiones
        loServidores.clear();
        //guardamos las conexiones en elementos xml
        for(int i = 0 ; i < poLista.size() ; i++){
            JConexion loConex = poLista.get(i);
            Element loConexXML = new Element(JDatosGeneralesXML.mcsCONEXION);
            loConexXML.getAttributes().addAtributo(mcsNombre, loConex.getNombre());
            guardarPropiedadesConexion(loConex, loConexXML);
            loServidores.add(loConexXML);
        }
        //borramos la conexion directa y servidores q ya se ha leido anteriormente
        Element loRoot = moLectorXML.getDocumento().getRootElement();
        for(int i = 0 ; i < loRoot.getChildren().size();i++ ){
            Element loAux = (Element) loRoot.getChildren().get(i);
            if(loAux.getNombre().equalsIgnoreCase(JDatosGeneralesXML.mcsCONEXIONDIRECTA)){
                loRoot.getChildren().remove(i);
                i--;
            }
            if(loAux.getNombre().equalsIgnoreCase(JDatosGeneralesXML.mcsCONEXIONSERVIDOR)){
                loRoot.getChildren().remove(i);
                i--;
            }
        }
        //guardamos en el fichero
        moLectorXML.guardarFichero();
    }


}
