/*
 * JFicheroFiltroConj.java
 *
 * Created on 19 de septiembre de 2006, 10:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CopiaSeguridad.file;

import java.io.File;
import java.io.FilenameFilter;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JFicheroFiltroConj  implements FilenameFilter {
    /**constante AND*/
    public static final int mclAND = 0;
    /**constante OR*/
    public static final int mclOR  = 1;
    
    private IListaElementos moFiltros = new JListaElementos();
    private IListaElementos moUniones = new JListaElementos();
    
    /** Creates a new instance of JFicheroFiltroConj */
    public JFicheroFiltroConj() {
    }
    
    public void add(int plUnion, FilenameFilter loFiltro){
        moFiltros.add(loFiltro);
        moUniones.add(new Integer(plUnion));
    }

    public boolean accept(File dir, String name) {
        boolean lbResult = true;
        for(int i = 0; i < moFiltros.size(); i++){
            FilenameFilter loElemento = (FilenameFilter)moFiltros.get(i);
            int lUnion = ((Integer)moUniones.get(i)).intValue();
            if(i==0){
                lbResult = loElemento.accept(dir, name);
            }else{
                switch (lUnion){
                    case mclAND:
                        lbResult &= loElemento.accept(dir, name);
                        break;
                    case mclOR:
                        lbResult |= loElemento.accept(dir, name);
                        break;
                }
            }
        }
        return lbResult;
    }
    
}
