/*
 * JFilaDatos.java
 *
 * Created on 11 de julio de 2002, 18:27
 */
package ListDatos;

import utiles.JCadenas;

/**
 *Almacena los valores de una fila de datos en un String separados por un caracter raro 
 */
public final class JFilaDatosDefecto implements IFilaDatos, Cloneable {
    private static final long serialVersionUID = 3333333L;
    /**Lista de campos de datos*/
    private String[] masDatos=null;
    /**Tipo de modificacion*/
    private int mlTipoModif=JListDatos.mclNada;
    /**Separacion de campos*/
    public static final String mcsSeparacion1 = String.valueOf((char)6);
    /**Separacion de campos*/
    public static final char mccSeparacion1 = (char)6;
    /**Para la transferencia por intenert, para transparentar caracter 10*/
    public static final char mccTransparentacionCambioLinea10 = (char)7;
    /**Para la transferencia por intenert, para transparentar caracter 13*/
    public static final char mccTransparentacionCambioLinea13 = (char)8;

    public JFilaDatosDefecto() {
        super();
        masDatos=new String[0];
    }
    /**
     * Crea una nueva instancia de JFilaDatos 
     * @param psDatos Datos iniciales
     */
    public JFilaDatosDefecto(final String psDatos) {
        setCadena(psDatos);
    }
    public JFilaDatosDefecto(final String[] pasDatos) {
        setArray(pasDatos);
    }
    public void setCadena(final String psDatos){
        String lsDatos = psDatos;
        if(!JCadenas.isVacio(psDatos) &&
           lsDatos.substring(lsDatos.length()-1).compareTo(mcsSeparacion1)!=0) {
                lsDatos += mcsSeparacion1;
        }
        setArray(moArrayDatos(lsDatos, mccSeparacion1));
        
    }
    public void setArray(final String[] pasDatos){
        masDatos = new String[pasDatos.length];
        System.arraycopy(pasDatos, 0,masDatos, 0, pasDatos.length);
    }
    /**
     * Anadimos un valor del campo al final 
     * @param psCampo Campo
     */
    public void addCampo(final String psCampo){
        addCampos(new String[]{psCampo});
    }
    /**
     * Anadimos un array de valores de campos al final 
     * @param psCampos Lista de campos
     */
    public void addCampos(final String[] psCampos){
        String[] laAux = new String[masDatos.length+psCampos.length];
        System.arraycopy(masDatos, 0, laAux, 0, masDatos.length);
        System.arraycopy(psCampos, 0, laAux, masDatos.length, psCampos.length);
        masDatos = laAux;
    }
    /**
     * Devuelve el valor de la posicion
     * @return valor Valor
     * @param plPosicion Posicion
     */
    public String msCampo(final int plPosicion){
        String lsResult;
        try{
            lsResult= masDatos[plPosicion];
        }catch(Exception e){
            lsResult= "";
        }
        return lsResult;
    }
    
    /**
     * Numero de campos
     * @return Numero de campos
     */
    public int mlNumeroCampos(){
        return masDatos.length;
    }
    /**
     *
     * Devuelve el numero de campos en funcion de la separacion
     * @return Numero de campos
     * @param psDatos Datos
     * @param psSeparacion separacion de los datos
     */
    public static int mlNumeroCampos(final String psDatos, final char psSeparacion){
        int lLen;
        int i;
        int lPosiciones;
        lLen=psDatos.length();
        lPosiciones=0;
        for(i = 0;i<lLen ;i++){
            if(psDatos.charAt(i)==psSeparacion){
                lPosiciones++;
            }
        }
        return lPosiciones;
    }
    /**
     *
     * Devuelve el numero de campos en funcion de la separacion
     * @return Numero de campos
     * @param psDatos Datos
     * @param psSeparacion separacion de los datos
     */
    public static int mlNumeroCampos(final StringBuffer psDatos, final char psSeparacion){
        int lLen;
        int i;
        int lPosiciones;
        lLen=psDatos.length();
        lPosiciones=0;
        for(i = 0;i<lLen ;i++){
            if(psDatos.charAt(i)==psSeparacion){
                lPosiciones++;
            }
        }
        return lPosiciones;
    }
    /**
     * Devuelve el valor de la posicion 
     * @return Valor 
     * @param psDatos Datos
     * @param plPosicion Posicion del campo a devolver
     */
    public static String gfsExtraerCampo(final String psDatos,final int plPosicion){
      return gfsExtraerCampo(psDatos,plPosicion,mccSeparacion1);
    }
    /**
     * Devuelve el valor de la posicion
     * @return Valor
     * @param psDatos Datos
     * @param plPosicion Posicion
     * @param lsSeparacion Separacion de los datos
     */
    public static String gfsExtraerCampo(final String psDatos,final int plPosicion, final char lsSeparacion){
        int lLen;
        int i;
        int lPosicion;
        StringBuilder lsDato;
        lLen=psDatos.length();
        lPosicion=0;
        lsDato=new StringBuilder(25);
        for(i = 0;i<lLen && lPosicion <= plPosicion;i++){
            if(psDatos.charAt(i)==lsSeparacion){
                lPosicion++;
            }else {
                if (lPosicion == plPosicion){
                    lsDato.append(psDatos.charAt(i));
                }else if (lPosicion > plPosicion) {
                    break;
                }
            }
        }
        return lsDato.toString();
    }
    /**
     * Devuelve el valor de la posicion
     * @return Valor
     * @param psDatos Datos
     * @param plPosicion Posicion
     * @param lsSeparacion Separacion de los datos
     */
    public static String gfsExtraerCampo(final StringBuffer psDatos,final int plPosicion, final char lsSeparacion){
        int lLen;
        int i;
        int lPosicion;
        StringBuilder lsDato;
        lLen=psDatos.length();
        lPosicion=0;
        lsDato=new StringBuilder(25);
        for(i = 0;i<lLen && lPosicion <= plPosicion;i++){
            if(psDatos.charAt(i)==lsSeparacion){
                lPosicion++;
            }else {
                if (lPosicion == plPosicion){
                    lsDato.append(psDatos.charAt(i));
                }else if (lPosicion > plPosicion) {
                    break;
                }
            }
        }
        return lsDato.toString();
    }
    /**
     * Devuelve el array de valores
     * @return Array de valores
     */
    public String[] moArrayDatos(){
        String[] laAux = new String[masDatos.length];
        System.arraycopy(masDatos, 0, laAux, 0, masDatos.length);
        return laAux;
    }
//    /**
//     *
//     * Devuelve el array de valores
//     * @return Array de valores
//     * @param psSeparacion Separacion de los campos
//     */
//    public String[] moArrayDatos(final char psSeparacion){
//        return moArrayDatos(msDatos, psSeparacion);
//    }
    /**
     *
     * Devuelve el array de valores
     * @return Array de valores
     * @param psDatos Datos
     * @param psSeparacion Separacion de los datos
     */
    public static String[] moArrayDatos(final String psDatos, final char psSeparacion){
        int lCampos;
        lCampos=mlNumeroCampos(psDatos, psSeparacion);
        String[] aDatos=new String[lCampos];
      
        int lLen;
        int i;
        int lPosicion;
        StringBuilder lsDato;
        lLen=psDatos.length();
        lPosicion=0;
        lsDato=new StringBuilder(25);
        for(i = 0;i<lLen && lPosicion <= lCampos;i++){
            if(psDatos.charAt(i)==psSeparacion){
                aDatos[lPosicion]=lsDato.toString();
                lsDato.setLength(0);
                lPosicion++;
            }else {
                lsDato.append(psDatos.charAt(i));
            }
        }
        if(lsDato.length()>0 && lPosicion<aDatos.length){
            aDatos[lPosicion]=lsDato.toString();
        }

        return aDatos;
    }
    /**
     *
     * Devuelve el array de valores
     * @return Array de valores
     * @param psDatos Datos
     * @param psSeparacion Separacion de los datos
     */
    public static String[] moArrayDatos(final StringBuffer psDatos, final char psSeparacion){
      int i;
      int lCampos;
      lCampos=mlNumeroCampos(psDatos, psSeparacion);
      String[] aDatos=new String[lCampos];
      for(i=0;i<lCampos;i++){
        aDatos[i]=gfsExtraerCampo(psDatos, i, psSeparacion);
      }
      if(aDatos.length==0 && psDatos.length()>0){
        aDatos = new String[]{psDatos.toString()};
      }
      return aDatos;
    }
    /**Devuelve el objeto en cadena*/
    public String toString(){
        return toString(mccSeparacion1);
    }
    public String toString(char pcSepa){
        StringBuilder lsAux = new StringBuilder();
        for(int i = 0; i < masDatos.length;i++){
            lsAux.append(masDatos[i]);
            lsAux.append(pcSepa);
        }
        return lsAux.toString();
   }
    public static String toString(IFilaDatos poFila, char pcSepa){
        StringBuilder lsAux = new StringBuilder();
        for(int i = 0; i < poFila.mlNumeroCampos();i++){
            lsAux.append(poFila.msCampo(i));
            lsAux.append(pcSepa);
        }
        return lsAux.toString();
    }

    /**Devuelve el objeto clonado*/
    public Object clone(){
        JFilaDatosDefecto loFila = new JFilaDatosDefecto(moArrayDatos());
        loFila.mlTipoModif=mlTipoModif;
        return loFila;
    }

    public int getTipoModif() {
        return mlTipoModif;
    }

    public void setTipoModif(final int plTipoModif) {
        mlTipoModif = plTipoModif;
    }
    
}
