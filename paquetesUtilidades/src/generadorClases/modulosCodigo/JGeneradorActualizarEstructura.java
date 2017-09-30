/*
 * JGeneradorActualizarEstructura.java
 *
 * Created on 20 de marzo de 2007, 13:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import generadorClases.*;
import utiles.IListaElementos;

public class JGeneradorActualizarEstructura   implements IModuloProyecto {

    private JUtiles moUtiles;

    private JConexionGeneradorClass moConex;
    private final JProyecto moProyecto;
    
    /** Creates a new instance of JGeneradorActualizarEstructura */
    public JGeneradorActualizarEstructura(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }
    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* JActualizarEstr.java");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("* Creado el 2/7/2008");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("*/");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("package "+moConex.getDirPadre()+"."+moUtiles.getPaqueteExtend()+";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.IServerServidorDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.estructuraBD.JTableDef;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.estructuraBD.JTableDefs;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import "+moConex.getDirPadre()+".tablas.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.actualizarEstruc.IActualizarEstruc;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JActualizarEstruc implements IActualizarEstruc {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    /** Creates a new instance of JActualizarEstr */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JActualizarEstruc() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JTableDefs getTablasOrigen() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JTableDefs loTablasOrigen = new JTableDefs();");lsText.append(JUtiles.msRetornoCarro);
        
        JTableDefs loTablas = moConex.getTablasBD();
        IListaElementos loLista = loTablas.getListaTablas();
        for(int i = 0; i < loLista.size(); i++){
            JTableDef loTabla = (JTableDef)loLista.get(i);
            String lsTablaClase = "JT" + moUtiles.msSustituirRaros(loTabla.getNombre());
            lsText.append("        loTablasOrigen.add(new JTableDef(");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                "+lsTablaClase+".msCTabla, JTableDef.mclTipoTabla, ");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                "+lsTablaClase+".masNombres, "+lsTablaClase+".malCamposPrincipales, ");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                "+lsTablaClase+".malTipos, "+lsTablaClase+".malTamanos)");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("                );");lsText.append(JUtiles.msRetornoCarro);
        }
        
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loTablasOrigen;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
    
        lsText.append("    public void postProceso(IServerServidorDatos poServer) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);
        
        return lsText.toString();        
    }



    public String getRutaRelativa() {
        return moUtiles.getPaqueteExtend();
    }

    public String getNombre() {
        return "JActualizarEstruc.java";
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
