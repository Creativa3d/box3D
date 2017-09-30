/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.servletAcciones;

import ListDatos.IResultado;
import ListDatos.ISelectEjecutarComprimido;
import ListDatos.IServerEjecutar;
import ListDatos.IServerServidorDatos;
import ListDatos.JResultado;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utiles.JDepuracion;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.JGUIxConfigGlobalModelo;

/**
 *
 * @author eduardo
 */
public class ALeerCorreo extends JAccionAbstract  {
    public static final String mcsAccion="leerCorreo";
    private JDatosGeneralesXML moDatosXML;
    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response, ServletContext poServletContext, IServerServidorDatos poServer) throws Exception {
        JResultado loResult = new JResultado("", true);
        moDatosXML = (JDatosGeneralesXML) poServletContext.getAttribute(JDatosGeneralesXML.class.getName());
        boolean lbEntradaComprimida = AEntradaComprimida.getEntradaComprimida(request, moDatosXML);
        IServerEjecutar loEntrada = (IServerEjecutar) getUpdateWeb(request, lbEntradaComprimida);
        utilesGUIxAvisos.calendario.JDatosGenerales loDatosGene
                = JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesCalendario();
        
        if(loDatosGene!=null 
                && loDatosGene.getPlanificador()!=null 
                && loDatosGene.getPlanificador().getCorreoLector()!=null){
            try {
                IResultado loResut = loEntrada.ejecutar(poServer);
                loDatosGene.getPlanificador().getCorreoLector().ejecutar(loResut.getMensaje(), poServer);
                loResult.setBien(true);
            } catch (Throwable ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
                loResult.setBien(false);
                loResult.setMensaje("EN SERVIDOR: " + ex.toString());
            }
        } else {
            loResult.setBien(false);
            loResult.setMensaje("Servidor no inicializado");
        }
        devolverResultado(response, loEntrada.getComprimido(), loResult);
        return null;
        
    }
    
}
