/*
 * JAccionesListados.java
 *
 * Created on 9 de febrero de 2008, 12:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionJasper.plugin;

import utilesGUIx.ActionEventCZ;
import impresionJasper.JInfGeneralJasper;
import impresionJasper.JMotorInformes;
import java.io.OutputStream;

import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JTablaConfigTablaConfig;
import utilesGUIx.formsGenericos.boton.IEjecutarExtend;
import utilesGUIx.plugin.IPlugInContexto;

public class JAccionesListados implements IEjecutarExtend {
    public static final String mcsListado = "Listado";
    public static final String mcsGrupoInformes = "Informes";

    private IPanelControlador moControlador;
    public String msImageBanner;
    public boolean mbPantallaSeleccion = false;
    public boolean mbSeleccionA3=false;
    public int mlDestino = JMotorInformes.mclPrevisualizar;
    public String msImpresora = "";
    public OutputStream moDestino = null;
    private final IPlugInContexto moContexto;

    /**
     * Creates a new instance of JAccionesExportImport
     */
    public JAccionesListados(final IPanelControlador poControlador, IPlugInContexto poContexto) {
        moControlador = poControlador;
        moContexto=poContexto;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if (e.getActionCommand().equals(mcsListado)){
            moContexto.getThreadGroup().addProcesoYEjecutar(new JProcesoAccionAbstracX() {

                public String getTitulo() {
                    return "Listado " + moControlador.getParametros().getTitulo();
                }

                public int getNumeroRegistros() {
                    return 2;
                }

                public void procesar() throws Throwable {
                    mlRegistroActual=1;
                    JInfGeneralJasper loInforme = new JInfGeneralJasper();
                    loInforme.setImagenHeader(msImageBanner);
                    loInforme.setPantallaSeleccion(mbPantallaSeleccion);
                    loInforme.setSeleccionarA3(mbSeleccionA3);

                    loInforme.setList(moControlador.getConsulta().getList());
                    JTablaConfigTablaConfig loConfig = (JTablaConfigTablaConfig) moControlador.getPanel().getTablaConfig().getConfigTablaConcreta().clone();
                    for(int i = 0 ; i < loConfig.size(); i++){
                        loConfig.getColumna(i).setLong((int) (loConfig.getColumna(i).getLong() * JInfGeneralJasper.mcnCoefPantallaImpreso));
                    }
                    loInforme.setConfig(loConfig);
                    loInforme.setTitulo(moControlador.getParametros().getTitulo());
                    loInforme.getMotor().setTipoListado(mlDestino);
                    if(mlDestino==JMotorInformes.mclImpresionDirecta){
                        loInforme.getMotor().setImpresora(msImpresora);
                    }
                    if(mlDestino!=JMotorInformes.mclImpresionDirecta && mlDestino!=JMotorInformes.mclPrevisualizar){
                        loInforme.getMotor().setSalida(moDestino);
                    }
                    loInforme.generarListado();
                    mbFin=true;
                    mlRegistroActual=2;
                }

                public String getTituloRegistroActual() {
                    return "";
                }

                public void mostrarMensaje(String psMensaje) {
                }
            });
            
        }
    }
    
}
