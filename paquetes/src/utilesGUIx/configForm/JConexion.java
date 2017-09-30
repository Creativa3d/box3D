/*
 * JConexion.java
 *
 * Created on 19 de febrero de 2007, 10:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.configForm;

import ListDatos.JFilaDatosDefecto;
import ListDatos.JSelectMotor;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosConexion;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import utiles.JCadenas;

public class JConexion implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;    
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclAccessODBC = 0;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclPostGreSQL = 1;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclOracleODBC = 2;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclSQLSERVER = 3;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclSQLSERVERMicrosoft = 4;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclAccessConRuta = 5;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclmySQL = 6;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclInternet = 7;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclInternetComprimido = 8;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclFireBird = 9;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclInternetComprimidoIO = 10;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclDBASE = -11;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclAccessConRutaJava = 12;
    /**
     * Tipo de conexion que reconoce la pantalla
     */
    public static final int mclSQLLITE = 13;



    /**
     * Indice del control visual: nombre
     */
    public static final int mclCampoNombre = 0;
    /**
     * Indice del control visual:
     */
    public static final int mclCampoIP = 1;
    /**
     * Indice del control visual:
     */
    public static final int mclCampoUSUARIO = 2;
    /**
     * Indice del control visual:
     */
    public static final int mclCampoPassWord = 3;
    /**
     * Indice del control visual:
     */
    public static final int mclCampoDominio = 4;
    /**
     * Indice del control visual:
     */
    public static final int mclCampoInstancia = 5;
    /**
     * Indice del control visual:
     */
    public static final int mclCampoRuta = 6;
    /**
     * Indice del control direccion internet
     */
    public static final int mclCampoURL = 7;
    /**
     * Indice del control codificacion caracteres
     */
    public static final int mclCampoCODIFICACION = 8;


    private JServerServidorDatosConexion moConexion = new JServerServidorDatosConexion();

    /**
     * Indica si se ha cancelado la pantalla
     */
    public boolean mbCancelado = false;
    
    /**
     * Pantalla: tipo conexion
     */
    public int mlPantTipoConexion = mclSQLSERVER;
    /**
     * Pantalla: nombre de la bd
     */
    public String msPantNombreBD="";
    /**
     * Pantalla: ip de la bd
     */
    public String msPantIP="";
    /**
     * Pantalla: usuario de la bd
     */
    public String msPantUSUARIO="";
    /**
     * Pantalla: password de la bd
     */
    public String msPantPASSWORD="";
    /**
     * Pantalla: dominio de la maquina de la bd(Generalemente SQL SERVER)
     */
    public String msPantDominio="";
    /**
     * Pantalla: instancia de la bd (Generalemente SQL SERVER)
     */
    public String msPantInstancia="";
    /**
     * Pantalla: ruta de la bd (Generalemente access)
     */
    public String msPantRuta="";
    /**
     * Pantalla: URL de la BD (Generalemente conexion de internet)
     */
    public String msPantURL="";
    /**
     * Variable de codigicacion de caracteres
     */
    public String msPANTCODIFICACION="";


    private String msNombre;
    
    private static HashMap<String, Integer> moListaPantallas;
    
    static {
        moListaPantallas = new HashMap<String, Integer>();
        
        moListaPantallas.put("Access ODBC", JConexion.mclAccessODBC);
        moListaPantallas.put("Access Con Ruta", (JConexion.mclAccessConRuta)  );
        moListaPantallas.put("Access Con Ruta Java", (JConexion.mclAccessConRutaJava)  );
        moListaPantallas.put("PostGreSql", (JConexion.mclPostGreSQL) );
        moListaPantallas.put("Oracle ODBC", (JConexion.mclOracleODBC) );
        moListaPantallas.put("SqlServer", (JConexion.mclSQLSERVER) );
        moListaPantallas.put("SqlServer Microsoft", (JConexion.mclSQLSERVERMicrosoft) );
        moListaPantallas.put("mySQL", (JConexion.mclmySQL) );
        moListaPantallas.put("FireBird", (JConexion.mclFireBird) );
        moListaPantallas.put("Internet", (JConexion.mclInternet) );
        moListaPantallas.put("Internet Comprimido", (JConexion.mclInternetComprimido) );
        moListaPantallas.put("Internet Comprimido IO", (JConexion.mclInternetComprimidoIO) );
        moListaPantallas.put("DBASE", (JConexion.mclDBASE) );
        moListaPantallas.put("SQLLite", (JConexion.mclSQLLITE) );

    }
    
    /** Creates a new instance of JConexion */
    public JConexion() {
    }
    
    public static HashMap<String, Integer> getListaPantallas(){
        return moListaPantallas;
    }

    /**
     * procesa la config. de variables de conexion(leida del fichero) y la guarda en variables de pantalla
     */
    public void leerConfig() throws Exception {
        if(moConexion.getTipoConexion() == JServerServidorDatos.mclTipoBD){
            switch(moConexion.getTipoBD()){
                case JSelectMotor.mclAccess:
                    mlPantTipoConexion = mclAccessConRuta;
                    if(moConexion.getURL().indexOf("jdbc:odbc:Driver")<0 
                            && moConexion.getURL().indexOf("DBQ=")<0){
                        mlPantTipoConexion = mclAccessODBC;
                    }
                    if(moConexion.getURL().indexOf("jdbc:ucanaccess:")>=0){
                        mlPantTipoConexion = mclAccessConRutaJava;
                    }
                    break;
                case JSelectMotor.mclSQLLite:
                    mlPantTipoConexion = mclSQLLITE;
                    break;
                case mclDBASE:
                    mlPantTipoConexion=mclDBASE;
                    break;
                case JSelectMotor.mclOracle:
                    mlPantTipoConexion = mclOracleODBC;
                    break;
                case JSelectMotor.mclPostGreSQL:
                    mlPantTipoConexion = mclPostGreSQL;
                    break;
                case JSelectMotor.mclMySQL:
                    mlPantTipoConexion = mclmySQL;
                    break;
                case JSelectMotor.mclFireBird:
                    mlPantTipoConexion = mclFireBird;
                    break;
                case JSelectMotor.mclSqlServer:
                    mlPantTipoConexion = mclSQLSERVER;
                    if("com.microsoft.sqlserver.jdbc.SQLServerDriver".compareTo(moConexion.getClase())==0){
                        mlPantTipoConexion = mclSQLSERVERMicrosoft;
                    }
                    break;
                default:
                    mlPantTipoConexion = mclSQLSERVER;
            }
        }else{
            switch(moConexion.getTipoConexion()){
                case JServerServidorDatos.mclTipoInternet:
                    mlPantTipoConexion= mclInternet;
                    break;
                case JServerServidorDatos.mclTipoInternetComprimido:
                    mlPantTipoConexion= mclInternetComprimido;
                    break;
                case JServerServidorDatos.mclTipoInternetComprimido_I_O:
                    mlPantTipoConexion= mclInternetComprimidoIO;
                    break;
            }
        }
        msPantPASSWORD = moConexion.getPASSWORD();
        msPantUSUARIO = moConexion.getUSUARIO();
        msPantURL = moConexion.getURL();
        if(moConexion.getURL().compareTo("")!=0){
            int lPrincipio;
            int lIndex;
            int lIndexPuntocoma;
            switch(mlPantTipoConexion){
                case mclAccessODBC:
                    msPantNombreBD = moConexion.getURL().substring("jdbc:odbc:".length());
                    break;
                case mclAccessConRuta:
                    msPantRuta = getPropiedad(moConexion.getURL(), "DBQ");
                    break;
                case mclAccessConRutaJava:
                    if(moConexion.getURL().indexOf(';', "jdbc:ucanaccess://".length())>0){
                        msPantRuta = moConexion.getURL().trim().substring(
                            "jdbc:ucanaccess://".length()
                            , moConexion.getURL().indexOf(';', "jdbc:ucanaccess://".length()));
                    }else{
                        msPantRuta = moConexion.getURL().trim().substring("jdbc:ucanaccess://".length());
                    }
                    break; 
                case mclSQLLITE:
                    msPantRuta = moConexion.getURL().substring("jdbc:sqlite:".length());
                    break;                    
                case mclDBASE:
                    msPantRuta = moConexion.getURL();
                    break;
                case mclOracleODBC:
                    msPantNombreBD = moConexion.getURL().substring("jdbc:odbc:".length());
                    break;
                case mclPostGreSQL:
                    lPrincipio = "jdbc:postgresql://".length();
                    lIndex = moConexion.getURL().indexOf('/',lPrincipio+1);
                    lIndexPuntocoma = moConexion.getURL().indexOf('?',lIndex);
                    msPantIP = moConexion.getURL().substring(lPrincipio, lIndex);
                    msPantNombreBD = moConexion.getURL().substring(lIndex+1, lIndexPuntocoma);
                    msPANTCODIFICACION = "";
                    String lsResto = moConexion.getURL().substring(lIndexPuntocoma+1);
                    JFilaDatosDefecto loFila = new JFilaDatosDefecto(JFilaDatosDefecto.moArrayDatos(lsResto+'&', '&'));
                    for(int i = 0 ; i < loFila.mlNumeroCampos(); i++){
                        if(loFila.msCampo(i).indexOf("user")<0 && 
                           loFila.msCampo(i).indexOf("password")<0){
                            msPANTCODIFICACION = "&" + loFila.msCampo(i);
                        }
                    }
                    break;
                case mclmySQL:
                    lPrincipio = "jdbc:mysql://".length();
                    lIndex = moConexion.getURL().indexOf('/',lPrincipio+1);
                    lIndexPuntocoma = moConexion.getURL().indexOf('?',lIndex);
                    msPantIP = moConexion.getURL().substring(lPrincipio, lIndex);
                    msPantNombreBD = moConexion.getURL().substring(lIndex+1, lIndexPuntocoma);
                    break;
                case mclFireBird:
                    lPrincipio = "jdbc:firebirdsql:".length();
                    lIndex = moConexion.getURL().indexOf(':',lPrincipio+1);
//                    lIndexPuntocoma = moConexion.getURL().indexOf("?lc_ctype=ISO8859_1", lIndex);
//                    if(lIndexPuntocoma<0){
                        lIndexPuntocoma=moConexion.getURL().length();
//                    }
                    msPantIP = moConexion.getURL().substring(lPrincipio, lIndex);
                    msPantNombreBD = moConexion.getURL().substring(lIndex+1, lIndexPuntocoma);
                    break;

                case mclSQLSERVER:
                    lPrincipio = "jdbc:jtds:sqlserver://".length();
                    lIndex = moConexion.getURL().indexOf('/',lPrincipio+1);
                    lIndexPuntocoma = moConexion.getURL().indexOf(';',lIndex);
                    msPantIP = moConexion.getURL().substring(lPrincipio, lIndex);
                    msPantNombreBD = moConexion.getURL().substring(lIndex+1, lIndexPuntocoma);
                    msPantDominio = getPropiedad(moConexion.getURL(), "domain");
                    msPantInstancia =getPropiedad(moConexion.getURL(), "instance");
                    break;
                case mclSQLSERVERMicrosoft:
                    lPrincipio = "jdbc:sqlserver://".length();
                    lIndex = moConexion.getURL().indexOf(';',lPrincipio+1);
                    lIndexPuntocoma = moConexion.getURL().indexOf(';',lIndex+1);
                    msPantIP = moConexion.getURL().substring(lPrincipio, lIndex);
                    msPantNombreBD = moConexion.getURL().substring(lIndex+";databaseName=".length(), lIndexPuntocoma);
                    break;
                default:
            }
        }
    }
    
    /**
     * guarda la config de variables de pantalla a variables de conexion
     */
    public void guardarConfig(){
        moConexion.setPASSWORD(msPantPASSWORD);
        moConexion.setUSUARIO(msPantUSUARIO);
        moConexion.setTipoConexion(JServerServidorDatos.mclTipoBD);
        switch(mlPantTipoConexion){
            case mclAccessODBC:
                moConexion.setTipoBD(JSelectMotor.mclAccess);
                moConexion.setClase("sun.jdbc.odbc.JdbcOdbcDriver");
                moConexion.setURL("jdbc:odbc:" + msPantNombreBD);
                break;
            case mclAccessConRuta:
                moConexion.setTipoBD(JSelectMotor.mclAccess);
                moConexion.setClase("sun.jdbc.odbc.JdbcOdbcDriver");
                moConexion.setURL("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + msPantRuta + ";PWD="+msPantPASSWORD);
                break;
            case mclSQLLITE:
                moConexion.setTipoBD(JSelectMotor.mclSQLLite);
                moConexion.setClase("org.sqlite.JDBC");
                moConexion.setURL("jdbc:sqlite:" + msPantRuta);
                break;
            case mclAccessConRutaJava:
                moConexion.setTipoBD(JSelectMotor.mclAccess);
                moConexion.setClase("net.ucanaccess.jdbc.UcanaccessDriver");
                moConexion.setURL("jdbc:ucanaccess://" + msPantRuta +  ( JCadenas.isVacio(msPantPASSWORD) ? "" : ";password="+msPantPASSWORD  ));
                break;
            case mclDBASE:
                moConexion.setTipoBD(mclDBASE);
                moConexion.setClase("");
                moConexion.setURL(msPantRuta);
                break;
            case mclOracleODBC:
                moConexion.setTipoBD(JSelectMotor.mclOracle);
                moConexion.setClase("sun.jdbc.odbc.JdbcOdbcDriver");
                moConexion.setURL("jdbc:odbc:" + msPantNombreBD);
                break;
            case mclPostGreSQL:
                moConexion.setTipoBD(JSelectMotor.mclPostGreSQL);
                moConexion.setClase("org.postgresql.Driver");
                moConexion.setURL("jdbc:postgresql://" + msPantIP + "/" + msPantNombreBD +
                        "?user=" + msPantUSUARIO +
                        "&password=" + msPantPASSWORD +
                        msPANTCODIFICACION);
                break;
            case mclmySQL:
                moConexion.setTipoBD(JSelectMotor.mclMySQL);
                moConexion.setClase("com.mysql.jdbc.Driver");
                moConexion.setURL("jdbc:mysql://" + msPantIP + "/" + msPantNombreBD +
                        "?zeroDateTimeBehavior=convertToNull"
                        + "&user=" + msPantUSUARIO 
                        + "&password=" + msPantPASSWORD);
                break;
            case mclFireBird:
                moConexion.setTipoBD(JSelectMotor.mclFireBird);
                moConexion.setClase("org.firebirdsql.jdbc.FBDriver");
                moConexion.setURL("jdbc:firebirdsql:"+msPantIP+":" + msPantNombreBD);//+"?lc_ctype=ISO8859_1";
//                +
//                        "?user=" + msPantUSUARIO +
//                        "&password=" + msPantPASSWORD;
                break;
            case mclSQLSERVER:
                moConexion.setTipoBD(JSelectMotor.mclSqlServer);
                moConexion.setClase("net.sourceforge.jtds.jdbc.Driver");
                moConexion.setURL("jdbc:jtds:sqlserver://" + msPantIP + "/" + msPantNombreBD + 
                        ";user=" + msPantUSUARIO + 
                        ";password=" + msPantPASSWORD + 
                        ";instance=" + msPantInstancia + 
                        ";domain=" + msPantDominio);
                break;
            case mclSQLSERVERMicrosoft:
                moConexion.setTipoBD(JSelectMotor.mclSqlServer);
                moConexion.setClase("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                moConexion.setURL("jdbc:sqlserver://" + msPantIP +
                        ";databaseName=" + msPantNombreBD +
                        ";user=" + msPantUSUARIO +
                        ";password=" + msPantPASSWORD);
                break;
            case mclInternetComprimidoIO:
            case mclInternetComprimido:
            case mclInternet:
                moConexion.setTipoBD(0);
                moConexion.setClase("");
                moConexion.setURL(msPantURL);
                if(mlPantTipoConexion==mclInternet){
                    moConexion.setTipoConexion(JServerServidorDatos.mclTipoInternet);
                }else if(mlPantTipoConexion==mclInternetComprimido){
                    moConexion.setTipoConexion(JServerServidorDatos.mclTipoInternetComprimido);
                } else{
                    moConexion.setTipoConexion(JServerServidorDatos.mclTipoInternetComprimido_I_O);
                }
                break;
            default:
                
        }

    }
    /***
     * devuelve los campos visibles en funcion del tipo de conexion
     * @param plTipoConexion tipo de conexion
     * @return lista de controles q se hacen visibles
     */
    public boolean[] getVisibles(int plTipoConexion){
        boolean[] labResult = new boolean[9];
        for(int i = 0 ; i < labResult.length ; i++){
            labResult[i] = false;
        }
        labResult[mclCampoPassWord] = true;
        labResult[mclCampoUSUARIO] = true;
        labResult[mclCampoURL] = false;
        labResult[mclCampoCODIFICACION] = false;
        
        switch(plTipoConexion){
            case mclAccessODBC:
                labResult[mclCampoNombre] = true;
                break;
            case mclAccessConRuta:
                labResult[mclCampoRuta] = true;
                break;
            case mclSQLLITE:
                labResult[mclCampoRuta] = true;
                break;
            case mclAccessConRutaJava:
                labResult[mclCampoRuta] = true;
                break;
            case mclDBASE:
                labResult[mclCampoRuta] = true;
                break;
            case mclOracleODBC:
                labResult[mclCampoNombre] = true;
                break;
            case mclPostGreSQL:
                labResult[mclCampoNombre] = true;
                labResult[mclCampoIP] = true;
                labResult[mclCampoCODIFICACION] = true;
                break;
            case mclFireBird:
                labResult[mclCampoNombre] = true;
                labResult[mclCampoIP] = true;
                break;
            case mclmySQL:
                labResult[mclCampoNombre] = true;
                labResult[mclCampoIP] = true;
                break;
            case mclSQLSERVER:
                labResult[mclCampoNombre] = true;
                labResult[mclCampoIP] = true;
                labResult[mclCampoDominio] = true;
                labResult[mclCampoInstancia] = true;
                break;
            case mclSQLSERVERMicrosoft:
                labResult[mclCampoNombre] = true;
                labResult[mclCampoIP] = true;
                break;
            case mclInternet:
            case mclInternetComprimido:
            case mclInternetComprimidoIO:
                labResult[mclCampoDominio] = false;
                labResult[mclCampoInstancia] = false;
                labResult[mclCampoNombre] = false;
                labResult[mclCampoIP] = false;
                labResult[mclCampoPassWord] = false;
                labResult[mclCampoUSUARIO] = false;
                labResult[mclCampoURL] = true;
                break;
            default:
        }
        return labResult;
    }
    
    
    private String getPropiedad(String psCadena, String psPropiedad){

        int lPosicion = psCadena.indexOf(psPropiedad);
        if(lPosicion>=0){
            char cCaracter = ' ';
            lPosicion+=psPropiedad.length()+1;
            String lsDominio = "";
            while(cCaracter != ';' && lPosicion < psCadena.length()){
                cCaracter = psCadena.charAt(lPosicion);
                lsDominio  = lsDominio + cCaracter;
                lPosicion++;
            }
            if(cCaracter==';'){
                return (lsDominio.substring(0, lsDominio.length()-1));
            }else{
                return(lsDominio);
            }
        }else{
             return "";
        }
    }

    /**
     * @return the msNombre
     */
    public String getNombre() {
        return msNombre;
    }

    /**
     * @param msNombre the msNombre to set
     */
    public void setNombre(String msNombre) {
        this.msNombre = msNombre;
    }

    public Connection probar() throws Exception {
        if(moConexion.getTipoConexion()==JServerServidorDatos.mclTipoBD){
            Class.forName(moConexion.getClase());
            Connection loConex =  DriverManager.getConnection(
                    moConexion.getURL(),
                    moConexion.getUSUARIO(),
                    moConexion.getPASSWORD()
                    );
            return loConex;
        }else{
            throw new Exception("No se puede probar este tipo de conexión");
        }

    }

    public Object clone() throws CloneNotSupportedException {
        JConexion loResult = (JConexion) super.clone();
        loResult.moConexion = (JServerServidorDatosConexion) moConexion.clone();
        return loResult;
    }

    /**
     * @return the moConexion
     */
    public JServerServidorDatosConexion getConexion() {
        return moConexion;
    }

    /**
     * @param moConexion the moConexion to set
     */
    public void setConexion(JServerServidorDatosConexion moConexion) {
        this.moConexion = moConexion;
    }
    
    
    
}
