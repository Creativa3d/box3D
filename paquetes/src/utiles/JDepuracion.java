/*
 * JDepuracion.java
 *
 * Created on 27 de febrero de 2004, 13:27
 */

package utiles;

/**
 * clase de ayuda para presentar mensajes por la salida estandar en funcion de 
 * una variable static booleana
 */
public class JDepuracion {
    public static final int mclINFORMACION=0;
    public static final int mclWARNING=10;
    public static final int mclCRITICO=20;

    public static final String mcsINFORMACION="INFORMACION";
    public static final String mcsWARNING="WARNING";
    public static final String mcsCRITICO="CRITICO";

    /**Si activas esta variable todos los mensajes saldran por la salida estandar*/
    public static boolean mbDepuracion = true;
    /**A mas alta esta variable menos mensajes salen, es decir, cada mensaje tiene un numero, si este numero es menor que esta variable el mensaje no sale*/
    public static int mlNivelDepuracion = mclWARNING;

    public static IDepuracionIMPL moIMPL = new JDepuracionIMPL();

    /** Constructor */
    private JDepuracion() {
    }
    
    /**
     * anade texto de depuracion a la salida
     * @param psGrupo grupo
     * @param psTexto texto
     */
    public static void anadirTexto(String psGrupo,String psTexto){
        try{
            if(mbDepuracion){
                moIMPL.anadirTexto(mclINFORMACION, psGrupo, psTexto,null);
            }
        }catch(Throwable e1){
            System.out.println("Error en el LOG");
            e1.printStackTrace();
        }
    }
    /**
     * anade texto de depuracion a la salida, de informacion
     * @param psGrupo grupo
     * @param e la exception a mostrar
     * 
     */
    public static void anadirTexto(String psGrupo, Exception e){
        anadirTexto(psGrupo, (Throwable)e);
    }
    /**
     * anade texto de depuracion a la salida, de informacion
     * @param psGrupo grupo
     * @param e la exception a mostrar
     *
     */
    public static void anadirTexto(String psGrupo, Throwable e){
        anadirTexto(mclWARNING, psGrupo, e);
    }
    /**
     * anade texto de depuracion a la salida
     * @param psGrupo grupo
     * @param e la exception a mostrar
     * @param plNivel a mas nivel mas posibilidades tiene de salir, ver mlNivelDepuracion
     */
    public static void anadirTexto(int plNivel, String psGrupo, Exception e){
        anadirTexto(plNivel, psGrupo, (Throwable)e);
    }
    /**
     * anade texto de depuracion a la salida
     * @param psGrupo grupo
     * @param e la exception a mostrar
     * @param plNivel a mas nivel mas posibilidades tiene de salir, ver mlNivelDepuracion
     */
    public static void anadirTexto(int plNivel, String psGrupo, Throwable e, Object poExtra){
        try{
            if(mbDepuracion){
                if(plNivel>=JDepuracion.mlNivelDepuracion){
                    moIMPL.anadirTexto(plNivel, psGrupo, e, poExtra);
                }
            }
        }catch(Throwable e1){
            System.out.println("Error en el LOG");
            e1.printStackTrace();
        }
    }
    /**
     * anade texto de depuracion a la salida
     * @param psGrupo grupo
     * @param e la exception a mostrar
     * @param plNivel a mas nivel mas posibilidades tiene de salir, ver mlNivelDepuracion
     */
    public static void anadirTexto(int plNivel, String psGrupo, Throwable e){
        anadirTexto(plNivel, psGrupo, e, null);
    }
    /**
     * anade texto de depuracion a la salida
     * @param psGrupo grupo
     * @param psTexto texto
     * @param plNivel a mas nivel mas posibilidades tiene de salir, ver mlNivelDepuracion
     */
    public static void anadirTexto(int plNivel, String psGrupo,String psTexto){
        anadirTexto(plNivel, psGrupo, psTexto, null);
    }
    
    /**
     * anade texto de depuracion a la salida
     * @param psGrupo grupo
     * @param psTexto texto
     * @param plNivel a mas nivel mas posibilidades tiene de salir, ver mlNivelDepuracion
     */
    public static void anadirTexto(int plNivel, String psGrupo,String psTexto, Object poExtra){
        try{
            if(mbDepuracion){
                if(plNivel>=JDepuracion.mlNivelDepuracion){
                    moIMPL.anadirTexto(plNivel, psGrupo, psTexto, poExtra);
                }
            }
        }catch(Throwable e1){
            System.out.println("Error en el LOG");
            e1.printStackTrace();
        }
    }

    public static String getNivel(int plNivel){
        String lsResult = "";
        switch(plNivel){
            case mclCRITICO:
                lsResult = mcsCRITICO;
                break;
            case mclWARNING:
                lsResult = mcsWARNING;
                break;
            case mclINFORMACION:
                lsResult = mcsINFORMACION;
                break;
            default:
        }
        return lsResult;
    }
    public static void severe(String psTexto){
        anadirTexto(mclCRITICO,"", psTexto);
    }
    public static void warning(String psTexto){
        anadirTexto(mclWARNING,"", psTexto);
    }
    public static void info(String psTexto){
        anadirTexto(mclWARNING,"", psTexto);
    }
    
        
}
