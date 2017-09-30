/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gest1;

import impresionJasper.plugin.JAccionesListados;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import paquetesGeneradorInf.gest1.tablas.JTSQLGENERADOR;
import paquetesGeneradorInf.gest1.tablasControladoras.JT2SQLGENERADOR;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import utiles.IListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.JFormPrincipal;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.JPlugInUtilidades;

public class JPlugInPrincipal implements IPlugIn {
    public String msImageBanner;
    public String msGrupoForm = JAccionesListados.mcsGrupoInformes;
    public boolean mbEsPrincipal = true;
    public JGuiConsultaDatos moDatos = new JGuiConsultaDatos(null);
    
    public void procesarInicial(IPlugInContexto poContexto) {
        JMenuBar jMenuBar1 = new javax.swing.JMenuBar();
        JMenu jMenuArchivo = new javax.swing.JMenu();
        JMenuItem jMenuMSQLGENERADOR = new javax.swing.JMenuItem();
        jMenuMSQLGENERADOR.setText(JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTSQLGENERADOR.msCTabla) );

        jMenuArchivo.setText("Archivo");

        jMenuMSQLGENERADOR.setText("SQLGENERADOR");
        jMenuMSQLGENERADOR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMSQLGENERADORActionPerformed(evt);
            }
        });
        jMenuArchivo.add(jMenuMSQLGENERADOR);

        jMenuBar1.add(jMenuArchivo);

        jMenuArchivo.setActionCommand(JFormPrincipal.mcsArchivo);
        

        JPlugInUtilidades.addMenusFrame(poContexto.getFormPrincipal(), jMenuBar1);
        JDatosGeneralesP.getDatosGenerales().setPlugInContexto(poContexto);
    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
        JDatosGeneralesP.getDatosGenerales().setPlugInContexto(poContexto);
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
        JDatosGeneralesP.getDatosGenerales().setPlugInContexto(poContexto);
    }

    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
        JDatosGeneralesP.getDatosGenerales().setPlugInContexto(poContexto);
        IListaElementos loElem = poControlador.getParametros().getBotonesGenerales().getListaBotones();
        
        String lsIcon;
        ImageIcon loIcon = null;
        try{
            
            lsIcon="/paquetesGeneradorInf/gest1/images/Print16.gif";
            loIcon = new ImageIcon(getClass().getResource(lsIcon));
        }catch(Throwable e){
            try{
                loIcon = null;
                lsIcon="/paquetesGeneradorInf/images/Print16.gif";
                loIcon = new ImageIcon(getClass().getResource(lsIcon));
            }catch(Throwable e2){
                try{
                    loIcon = null;
                    lsIcon="/images/Print16.gif";
                    loIcon = new ImageIcon(getClass().getResource(lsIcon));
                }catch(Throwable e1){
                    lsIcon="";
                }                
            }
            
        }
        JAccionesGenConsGest loAcciones = new JAccionesGenConsGest(
                poControlador,
                poContexto.getThreadGroup());
        loAcciones.moDatos = moDatos;
        JBotonRelacionado loBoton = new JBotonRelacionado(
                JAccionesGenConsGest.mcsGenerador,
                JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(getClass().getSimpleName() ,  JAccionesGenConsGest.mcsGenerador),
                loIcon != null ? loIcon : "",
                null,
                (IEjecutarExtend)loAcciones,
                null,
                msGrupoForm);
        loBoton.setEsPrincipal(mbEsPrincipal);
        loElem.add(loBoton);
        
        loBoton = new JBotonRelacionado(
                JAccionesGenConsGest.mcsGestorConsultas,
                JAccionesGenConsGest.mcsGestorConsultas,
                loIcon != null ? loIcon : "",
                null,
                (IEjecutarExtend)loAcciones,
                null,
                msGrupoForm);
        loBoton.setEsPrincipal(mbEsPrincipal);
        loElem.add(loBoton);
        
        
    }
    private void jMenuMSQLGENERADORActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        try{
            JT2SQLGENERADOR loSQLGENERADOR = new JT2SQLGENERADOR(JDatosGeneralesP.getDatosGenerales().getServer(), JDatosGeneralesP.getDatosGenerales().getMostrarPantalla());
            loSQLGENERADOR.mostrarFormPrinciMenu();
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(null, e, getClass().getName());
        }
    }     
    public void procesarFinal(IPlugInContexto poContexto){
        
    }
}
