/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.camposRapido;

import ListDatos.JSelectCampo;

public class JArbolCampo {
    private JSelectCampo moSelectCampo;

    
    public JArbolCampo(String psCampo, String psCaption, String psTablaOALias){
        moSelectCampo = new JSelectCampo(JSelectCampo.mclFuncionNada,psTablaOALias, psCampo);
        moSelectCampo.setCaption(psCaption);
    }

    public String toString(){
        return moSelectCampo.getCaption();
    }

    public JSelectCampo getDatos(){
        return moSelectCampo;
    }
}
