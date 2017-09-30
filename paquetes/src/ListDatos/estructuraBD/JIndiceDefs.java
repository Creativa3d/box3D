/*
 * JTableDefs.java
 *
 * Created on 15 de abril de 2005, 12:06
 */

package ListDatos.estructuraBD;

import utiles.*;

public class JIndiceDefs  implements java.io.Serializable, Cloneable {
  private static final long serialVersionUID = 33333335L;
    private IListaElementos moIndices;

    /** Constructor */
    public JIndiceDefs(){
        moIndices = new JListaElementos();
    }
    
    public void addIndice(final JIndiceDef poItem) {
        moIndices.add(poItem);
    }
    
    public JIndiceDef getIndice(final int plIndex) {
        return (JIndiceDef) moIndices.get(plIndex);
    }
    
    public JIndiceDef getIndice(final String psNombre) {
        int lnCont;
        JIndiceDef loResult=null;
        
        lnCont = 0;
        while(lnCont<moIndices.size() && loResult == null){
            JIndiceDef loIndiceAux=(JIndiceDef) moIndices.get(lnCont);
            if(loIndiceAux.getNombreIndice().equalsIgnoreCase(psNombre)) {
                loResult = loIndiceAux;
            }
            lnCont++;
        }
        return loResult;
    }
    
    public int getCountIndices() {
        return moIndices.size();
    }
    
    public IListaElementos getListaIndices(){
        return moIndices;
    }
    public Object clone() throws CloneNotSupportedException {
        JIndiceDefs loIndices = (JIndiceDefs) super.clone();
        loIndices.moIndices= new JListaElementos();
        for(int i = 0 ; i < moIndices.size(); i++){
            loIndices.moIndices.add(getIndice(i).clone());
        }
        return loIndices;
    }    
}
