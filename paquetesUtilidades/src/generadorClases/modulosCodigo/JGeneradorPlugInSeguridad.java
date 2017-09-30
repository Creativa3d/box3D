/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorClases.modulosCodigo;

import generadorClases.*;

public class JGeneradorPlugInSeguridad implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades

    public JGeneradorPlugInSeguridad(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * To change this template, choose Tools | Templates");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * and open the template in the editor.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.IServerServidorDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.JListDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.JListDatosFiltroElem;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import impresionJasper.plugin.JAccionesListados;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import paquetesGeneradorInf.plugin.JAccionesGeneradorConsultas;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.usuarios.tablasExtend.JPlugInSeguridadRWModelo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.seguridad.JPlugInSeguridadPermisosBase;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.plugin.seguridad.JTPlugInListaPermisos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JPlugInSeguridadRW extends JPlugInSeguridadRWModelo {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JPlugInSeguridadRW() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void aplicarPermisosServidor(IServerServidorDatos poServer, String psCodUsuario) throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        super.aplicarPermisosServidor(poServer, psCodUsuario);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JTPlugInListaPermisos getListaPermisosBasePersonalizado() throws Exception {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JPlugInSeguridadPermisosBase loPermisosBaseCreador = new JPlugInSeguridadPermisosBase();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        loPermisosBaseCreador.addFrame(JDatosGeneralesP.getDatosGenerales().getFormPrincipal());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                loPermisosBaseCreador.addControlador(new JT2CLIENTES(null, null));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//                loPermisosBaseCreador.addFrame(new JPanelACU());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        return loPermisosBaseCreador.getListaPermisosBase();    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }
    
    @Override
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        return loParam;
    }

    @Override
    public String getRutaRelativa() {
        return ".";
    }

    @Override
    public String getNombre() {
        return "JPlugInSeguridadRW.java";
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    public String getNombreModulo() {
        return getNombre();
    }
}
