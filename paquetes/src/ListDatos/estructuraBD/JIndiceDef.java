/*
 * JTableDef.java
 *
 * Created on 15 de abril de 2005, 12:08
 */

package ListDatos.estructuraBD;

import utiles.*;

public class JIndiceDef  implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 33333334L;

    //Propiedades del indice
    private String msNombreIndice; //Nombre del indice
    private IListaElementos moCamposIndice; //Campos del indice
    
    private boolean mbEsPrimario;
    private boolean mbEsUnico;
    

    public String getNombreIndice() {
        return msNombreIndice;
    }
    public void setNombreIndice(final String psNombreIndice) {
        msNombreIndice = psNombreIndice;
    }
    
    /** Constructor */
    public JIndiceDef(final String psNombreIndice) {
        msNombreIndice = psNombreIndice;
        moCamposIndice = new JListaElementos();
    }
    
    /**add un campos*/
    public void addCampo(final String psNombreCampo) {
        moCamposIndice.add(psNombreCampo);
    }
    /**add un campos*/
    public void addCampoIndice(final String psNombreCampo) {
        moCamposIndice.add(psNombreCampo);
    }
    
    public String getCampoIndice(final int plcod) {
        return (String) moCamposIndice.get(plcod);
    }
    
    
    
//    public JFieldDef getCampoIndice(String psNombre){
//        int i;
//        
//        JFieldDef loField=null;
//        String lsNombre = psNombre.toUpperCase();
//        for(i=0;(i<moCamposIndice.size())&&(loField==null);i++){
//            if(getCampoIndice(i).getNombre().toUpperCase().compareTo(lsNombre) == 0) {
//                loField = getCampoIndice(i);
//            }
//        }
//        lsNombre=null;
//        return loField;
//    }   
    
    public int getCountCamposIndice() {
        return moCamposIndice.size();
    }    
    
//    public IListaElementos getListaCamposIndice() {
//        return moCamposIndice;
//    }
    /**Recuperar los campos*/
    public IListaElementos getListaCampos() {
        return moCamposIndice;
    }

    
    public void setListaCamposIndice(final IListaElementos poCamposIndice) {
        moCamposIndice = poCamposIndice;
    }
    public void setEsPrimario(final boolean pbEsPrimario){
        mbEsPrimario=pbEsPrimario;
    }
    public boolean getEsPrimario(){
        return mbEsPrimario;
    }
    public void setEsUnico(final boolean pbEsUnico){
        mbEsUnico=pbEsUnico;
    }
    public boolean getEsUnico(){
        return mbEsUnico;
    }

    public Object clone() throws CloneNotSupportedException {
        JIndiceDef loIndice = (JIndiceDef) super.clone();
        loIndice.moCamposIndice=new JListaElementos(moCamposIndice.size());
        for(int i = 0 ; i < moCamposIndice.size();i++){
            loIndice.moCamposIndice.add(moCamposIndice.get(i));
        }
                
        return loIndice;
    }
    
}
