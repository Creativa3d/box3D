/*
 * JListDatosXSLCabe.java
 *
 * Created on 30 de enero de 2007, 20:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.tools;

import java.awt.Font;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JListDatosXSLCabe {
    String msLogotipoImagen=null;
    String msLogotipoImagen2=null;
    String[] masLogotipoTextos = new String[0];
    String[] masLogotipoTextos2 = new String[0];
    int mlAlineacionCabe = JListDatosXSL.mclAlineacionCen;
    
    String msTitulo = "";
    
    Font moFuenteCabezera;
    Font moFuenteTitulo;
    
    private final IListaElementos moFilas;
    /** Creates a new instance of JListDatosXSLCabe */
    public JListDatosXSLCabe() {
        super();
        moFuenteCabezera = new Font(null, java.awt.Font.BOLD, 10);
        moFuenteTitulo = new Font(null, java.awt.Font.BOLD, 12);
        moFilas=new JListaElementos();
    }
    
    public void addFila(){
        moFilas.add(new JListaElementos());
    }
    public void borrarFila(final int i){
        moFilas.remove(i);
    }
    public void addColumna(final int plFila){
        IListaElementos loFila = (IListaElementos)moFilas.get(plFila);
        loFila.add(new JListDatosXSLColumna());
    }
    public void borrarColumna(final int plFila, final int plColumna){
        IListaElementos loFila = (IListaElementos)moFilas.get(plFila);
        loFila.remove(plColumna);
    }
    
    public JListDatosXSLColumna getColumna(final int plFila, final int plColumna){
        IListaElementos loFila = (IListaElementos)moFilas.get(plFila);
        return (JListDatosXSLColumna)loFila.get(plColumna);
    }
    
    public int getFilas(){
        return moFilas.size();
    }

    public int getColumnas(final int plFila){
        return ((IListaElementos)moFilas.get(plFila)).size();
    }
    
    
    
    
    
    
}
