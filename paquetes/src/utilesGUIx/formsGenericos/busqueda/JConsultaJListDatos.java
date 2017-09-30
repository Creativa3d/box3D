/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos.busqueda;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;

/**
 * Iconsulta simple para un JListDatos
 * @author eduardo
 */
public class JConsultaJListDatos implements IConsulta {
    private final JListDatos moList;
    public JConsultaJListDatos(JListDatos poList){
        moList = poList;
    }

    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
    }

    public JListDatos getList() {
        return moList;
    }

    public void addFilaPorClave(IFilaDatos poFila) throws Exception {
    }

    public boolean getPasarCache() {
        return false;
    }
}
