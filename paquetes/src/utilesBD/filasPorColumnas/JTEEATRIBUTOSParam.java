/*
 * JTEEATRIBUTOSParam.java
 *
 * Created on 6 de agosto de 2008, 16:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesBD.filasPorColumnas;

import ListDatos.JListDatos;
import java.io.Serializable;

public class JTEEATRIBUTOSParam implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    public JListDatos moAtribDef;
    public JListDatos moAtrib;

    public int mlPosiCodAtribDef;
    public int mlPosiTipoDef;
    public int mlPosiNombreDef;
    public int mlPosiCodAtrib;
    public int[] malPosiCodigos;
    public int mlPosiValor;

    public int mlPosiTamanoDef;

    private int[] malCodigos=null;
    
    /** Creates a new instance of JTEEATRIBUTOSParam */
    public JTEEATRIBUTOSParam(final JListDatos poAtribDef, final int plPosiCodAtribDef, final int plPosiTipoDef, final int plPosiNombreDef,final int plPosiTamanoDef, final JListDatos poAtrib, final int[] palPosiCodigos, final int plPosiCodAtrib, final int plPosiValor){
        moAtribDef = poAtribDef;
        moAtrib = poAtrib;
        
        mlPosiCodAtribDef=plPosiCodAtribDef;
        mlPosiTipoDef=plPosiTipoDef;
        mlPosiNombreDef=plPosiNombreDef;
        malPosiCodigos=palPosiCodigos;
        mlPosiCodAtrib=plPosiCodAtrib;
        mlPosiValor=plPosiValor;
        mlPosiTamanoDef=plPosiTamanoDef;
    }
    
    public int[] getCodigosAtributosCampos(){
        if(malCodigos==null){
            malCodigos = new int[malPosiCodigos.length+1];
            for(int i = 0 ; i < malPosiCodigos.length; i++){
                malCodigos[i] = malPosiCodigos[i];
            }
            malCodigos[malCodigos.length-1] = mlPosiCodAtrib;
        }
        return malCodigos;
    }
    public String[] getCodigosAtributosValores(){
        int[] lalCodigos = getCodigosAtributosCampos();
        String[] lsResult = new String[lalCodigos.length];
        for(int i = 0 ; i < lalCodigos.length; i++){
            
        }
        return lsResult;
    }

    protected Object clone() throws CloneNotSupportedException {
        JTEEATRIBUTOSParam loAtrib = null;
        try {
            loAtrib = (JTEEATRIBUTOSParam) super.clone();
            loAtrib.moAtrib = moAtrib.Clone();
            loAtrib.moAtribDef = moAtribDef.Clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return loAtrib;
    }
    
    
}
