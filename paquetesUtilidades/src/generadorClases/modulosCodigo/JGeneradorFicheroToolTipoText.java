/*
 * JGeneradorCaption.java
 *
 * Created on 19 de mayo de 2006, 14:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import generadorClases.*;
import utiles.*;

public class JGeneradorFicheroToolTipoText  implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyect;
    
    /** Creates a new instance of JGeneradorCaption */
    public JGeneradorFicheroToolTipoText(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyect=poProyec;
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        boolean lbMay = moProyect.getOpciones().isMayusculas();
        JTableDefs loTablas = moConex.getTablasBD();
        IListaElementos loLista = loTablas.getListaTablas();
        for(int i = 0; i < loLista.size(); i++){
            JTableDef loTabla = (JTableDef)loLista.get(i);
            JFieldDefs loCampos = moConex.getCamposBD(loTabla.getNombre());
            lsText.append(( lbMay ? loTabla.getNombre().replace(' ', '-').toUpperCase() : loTabla.getNombre().replace(' ', '-').toUpperCase()));
            lsText.append('=');
            lsText.append(JUtiles.msRetornoCarro);
            for(int ii = 0; ii < loCampos.count(); ii++ ){
                lsText.append(( lbMay ? loTabla.getNombre().replace(' ', '-').toUpperCase() : loTabla.getNombre().replace(' ', '-').toUpperCase()));
                lsText.append('_');
                lsText.append(( lbMay ? loCampos.get(ii).getNombre().replace(' ', '-').toUpperCase(): loCampos.get(ii).getNombre().replace(' ', '-')));
                lsText.append('=');
                lsText.append(JUtiles.msRetornoCarro);
            }
        }
        
        return lsText.toString();        
    }
    public String getRutaRelativa() {
        return "./..";
    }

    public String getNombre() {
        return "ToolTipTextTablas"+moConex.getDirPadre()+".properties";
    }

    public boolean isGeneral() {
        return true;
    }
    public String getNombreModulo() {
        return getNombre();
    }
    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }

}
