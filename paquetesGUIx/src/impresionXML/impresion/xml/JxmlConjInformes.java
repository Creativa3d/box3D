/*
 * JxmlConjInformes.java
 *
 * Created on 30 de enero de 2007, 17:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import java.io.Serializable;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utiles.xml.dom.JDOMException;
/**Gestor/Cache de informes xml*/
public class JxmlConjInformes extends JxmlAbstract {
    private static final long serialVersionUID = 1L;
    
    private final IListaElementos moInformes;
    private boolean mbCachear = true;
    /**
     * Creates a new instance of JxmlConjInformes
     */
    public JxmlConjInformes() {
        super();
        moInformes = new JListaElementos();
    }
    public void setCachear(boolean pb){
        mbCachear=pb;
    }
    public boolean isCachear(){
        return mbCachear;
    }
    public synchronized void add(final JxmlInforme poInforme, final String psNombre){
        moInformes.add(new JInforme(poInforme, psNombre));
    }
    
    public synchronized void leerInforme(final String psRuta) throws JDOMException{
        add(JxmlLectorInforme.leerInforme(psRuta), psRuta);
    }
    public synchronized JxmlInforme getInforme(final String psRutaNombre) throws CloneNotSupportedException{
        JxmlInforme loInforme = null;
        for(int i = 0; i < moInformes.size() && loInforme == null; i++){
            JInforme loInformeAux = (JInforme)moInformes.get(i);
            if(loInformeAux.msNombre.compareToIgnoreCase(psRutaNombre)==0){
                loInforme = (JxmlInforme)loInformeAux.moInforme.clone();
            }
        }
        return loInforme;
    }
    public synchronized JxmlInforme getInformeYLeer(final String psRutaNombre) throws JDOMException, CloneNotSupportedException{
        JxmlInforme loInforme = getInforme(psRutaNombre);
        if(loInforme==null){
            leerInforme(psRutaNombre);
            loInforme = getInforme(psRutaNombre);
        }
        if(!mbCachear){
            moInformes.clear();
        }
        return loInforme;
    }
}

class JInforme{
    public JxmlInforme moInforme;
    public String msNombre;
    
    public JInforme(final JxmlInforme poInforme, final String psNombre){
        moInforme=poInforme;
        msNombre=psNombre;
    }
}