/*
 * Variant.java
 *
 * Created on 28 de octubre de 2004, 17:27
 */

package utilesBD.buscarHuecos;

/**objeto variant almacena cualquier tipo*/
public final class Variant extends Object {
    private Object moValor;
    private String msValor;
    private int miValor;
    private char mcValor;
    private double mdValor;
    private long mlValor;
    private float mfValor;
    private boolean mbValor;
    /**
     * Constructor
     * @param poValor valor
     */
    public Variant(Object poValor) {
        moValor = poValor;
    }
    /**
     * Constructor
     * @param psValor valor
     */
    public Variant(String psValor) {
        msValor = psValor;
        miValor = new Double(msValor).intValue();
        mcValor = msValor.charAt(0); //Caracter de la posicion 0
        mdValor = new Double(msValor).doubleValue();
        mlValor = new Double(msValor).longValue();
        mfValor = new Double(msValor).floatValue();
        mbValor = new Boolean(msValor).booleanValue();
    }
    /**
     * Constructor
     * @param piValor valor
     */
    public Variant(int piValor) {
        miValor = piValor;
        msValor = Double.toString(miValor);
        mcValor = Double.toString(miValor).charAt(0);
        mdValor = miValor;
        mlValor = miValor;
        mfValor = miValor;
        mbValor = new Boolean(msValor).booleanValue();
    }
    /**
     * Constructor
     * @param pcValor valor
     */
    public Variant(char pcValor) {
        mcValor = pcValor;
        msValor = Double.toString(mcValor);
        miValor = new Double(mcValor).intValue();
        mdValor = new Double(mcValor).doubleValue();
        mlValor = new Double(mcValor).longValue();
        mfValor = new Double(mcValor).floatValue();
        mbValor = new Boolean(msValor).booleanValue();
    }
    /**
     * Constructor
     * @param pdValor valor
     */
    public Variant(double pdValor) {
        mdValor = pdValor;
        msValor = Double.toString(mdValor);
        miValor = new Double(mdValor).intValue();
        mcValor = msValor.charAt(0); //Caracter de la posicion 0
        mlValor = new Double(mdValor).longValue();
        mfValor = new Double(mdValor).floatValue();
        mbValor = new Boolean(msValor).booleanValue();
    }
    /**
     * Constructor
     * @param plValor valor
     */
    public Variant(long plValor) {
        mlValor = plValor;
        miValor = new Double(mlValor).intValue();
        msValor = Integer.toString(miValor);
        mcValor = msValor.charAt(0); //Caracter de la posicion 0
        mdValor = new Double(mlValor).doubleValue();
        mfValor = new Double(mlValor).floatValue();
        mbValor = new Boolean(msValor).booleanValue();
    }
    /**
     * Constructor
     * @param pfValor valor
     */
    public Variant(float pfValor) {
        mfValor = pfValor;
        msValor = Double.toString(mfValor);
        miValor = new Double(mfValor).intValue();
        mcValor = msValor.charAt(0); //Caracter de la posicion 0
        mdValor = new Double(mfValor).doubleValue();
        mlValor = new Double(mfValor).longValue();
        mbValor = new Boolean(msValor).booleanValue();
    }
    /**
     * Constructor
     * @param pbValor valor
     */
    public Variant(boolean pbValor) {
        mbValor = pbValor;
        msValor = new Boolean(mbValor).toString();
        mcValor = msValor.charAt(0); //Caracter de la posicion 0
    }
    /**Devuelve el objeto*/
    public Object getObject() {
        return(moValor);
    }
    /**Devuelve el valor cadena*/
    public String getString() {
        return(msValor);
    }
    /**Devuelve el valor int*/
    public int getInt() {
        return(miValor);
    }
    /**Devuelve el valor char*/
    public char getChar() {
        return(mcValor);
    }
    /**Devuelve el valor double*/
    public double getDouble() {
        return(mdValor);
    }
    /**Devuelve el valor long*/
    public long getLong() {
        return(mlValor);
    }
    /**Devuelve el valor float*/
    public float getFloat() {
        return(mfValor);
    }
    /**Devuelve el valor boolean*/
    public boolean getBoolean() {
        return(mbValor);
    }
}
