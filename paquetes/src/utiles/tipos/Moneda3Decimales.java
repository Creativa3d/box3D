/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles.tipos;
/**
 *
 * @author daniel
 */
public final class Moneda3Decimales extends Number implements Comparable {
    public static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
    public static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
    public static final double NaN = Double.NaN;
    public static final double MAX_VALUE = Double.MAX_VALUE;
    public static final double MIN_NORMAL = 2.2250738585072014E-308;
    public static final double MIN_VALUE = Double.MIN_VALUE;
    public static final int MAX_EXPONENT = 1023;
    public static final int MIN_EXPONENT =-1022;
    public static final int SIZE = Double.SIZE;
//    public static final Class<Moneda> TYPE = new Class<Moneda>();
    private final Double value;
    private static final long serialVersionUID = -5555774392245257468L;

    public Moneda3Decimales(double d ) {
        value = new Double(d);
    }

    public Moneda3Decimales(String string) throws NumberFormatException {
        value = new Double(string);
    }

    public static String toString(double d) {
        return Double.toString(d);
    }

    public static String toHexString(double d) {
        return Double.toHexString(d);
    }

    public static Double valueOf(String string) throws NumberFormatException {
        return Double.valueOf(string);
    }

    public static Double valueOf(double d) {
        return Double.valueOf(d);
    }

    public static double parseDouble(String string) throws NumberFormatException {
        return Double.parseDouble(string);
    }

    public static boolean isNaN(double d) {
        return Double.isNaN(d);
    }

    public static boolean isInfinite(double d) {
        return Double.isInfinite(d);
    }

    public boolean isNaN() {
        return value.isNaN();
    }

    public boolean isInfinite() {
        return value.isInfinite();
    }

    
    public String toString() {
        return value.toString();
    }

    
    public byte byteValue() {
        return value.byteValue();
    }

    
    public short shortValue() {
        return value.shortValue();
    }

    public int intValue() {
        return value.intValue();
    }

    public long longValue() {
        return value.longValue();
    }

    public float floatValue() {
        return value.floatValue();
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    
    public int hashCode() {
        return value.hashCode();
    }

    
    public boolean equals(Object o) {
        return value.equals(o);
    }

    public static long doubleToLongBits(double d) {
        return Double.doubleToLongBits(d);
    }

    public static long doubleToRawLongBits(double d){
        return Double.doubleToRawLongBits(d);
    };

    public static double longBitsToDouble(long l){
        return Double.longBitsToDouble(l);
    };

    public static int compare(double d, double d1) {
        return Double.compare(d, d1);
    }

//    public int compareTo(Moneda3Decimales t) {
//        return value.compareTo(t.doubleValue());
//    }

    public int compareTo(Object t) {
        return value.compareTo(new Double(t.toString()));
    }
    
}
