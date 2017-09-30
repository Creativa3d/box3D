/*
 * JListDatosXSLColumna.java
 *
 * Created on 30 de enero de 2007, 20:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.tools;

import java.awt.Color;

public class JListDatosXSLColumna {

  
    public static final int mclBordeHidden=0;
    public static final int mclBordeDotted=1;
    public static final int mclBordeDashed=2;
    public static final int mclBordeSolid=3;
    public static final int mclBordeDouble=4;
    public static final int mclBordeGroove=5;
    public static final int mclBordeRidge=6;
    public static final int mclBordeInset=7;
    public static final int mclBordeOutset=8;
    
    private double mdLong = 2.0;
    private int mlAlineacion = JListDatosXSL.mclAlineacionIzq;
    private java.awt.Font moFuente;
    private String msTexto;
    
    private int mlBordeIzquierda=0;
    private int mlBordeDerecha=0;
    private int mlBordeArriba=0;
    private int mlBordeAbajo=0;
    
    private int mlBordeEstilo;
    private Color moBordeColor;
    
    private int mlColSpan=1;
    
    /** Creates a new instance of JListDatosXSLColumna */
    public JListDatosXSLColumna() {
        super();
        msTexto="";
        moFuente=null;
        moBordeColor=Color.black;
    }

    public double getLong() {
        return mdLong;
    }

    public void setLong(final double mdLong) {
        this.mdLong = mdLong;
    }

    public int getAlineacion() {
        return mlAlineacion;
    }

    public void setAlineacion(final int mlAlineacion) {
        this.mlAlineacion = mlAlineacion;
    }

    public java.awt.Font getFuente() {
        return moFuente;
    }

    public void setFuente(final java.awt.Font moFuente) {
        this.moFuente = moFuente;
    }

    public String getTexto() {
        return msTexto;
    }

    public void setTexto(final String msTexto) {
        this.msTexto = msTexto;
    }

    public int getBordeIzquierda() {
        return mlBordeIzquierda;
    }

    public void setBordeIzquierda(final int mlBordeIzquierda) {
        this.mlBordeIzquierda = mlBordeIzquierda;
    }

    public int getBordeDerecha() {
        return mlBordeDerecha;
    }

    public void setBordeDerecha(final int mlBordeDerecha) {
        this.mlBordeDerecha = mlBordeDerecha;
    }

    public int getBordeArriba() {
        return mlBordeArriba;
    }

    public void setBordeArriba(final int mlBordeArriba) {
        this.mlBordeArriba = mlBordeArriba;
    }

    public int getBordeAbajo() {
        return mlBordeAbajo;
    }

    public void setBordeAbajo(final int mlBordeAbajo) {
        this.mlBordeAbajo = mlBordeAbajo;
    }

    public int getBordeEstilo() {
        return mlBordeEstilo;
    }

    public void setBordeEstilo(final int mlBordeEstilo) {
        this.mlBordeEstilo = mlBordeEstilo;
    }

    public Color getBordeColor() {
        return moBordeColor;
    }

    public void setBordeColor(final Color moBordeColor) {
        this.moBordeColor = moBordeColor;
    }

    public int getColSpan() {
        return mlColSpan;
    }

    public void setColSpan(final int mlColSpan) {
        this.mlColSpan = mlColSpan;
    }
}
