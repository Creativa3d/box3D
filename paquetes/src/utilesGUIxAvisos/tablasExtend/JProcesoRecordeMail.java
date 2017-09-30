/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxAvisos.tablasExtend;

import ListDatos.JListDatos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.controlProcesos.JProcesoAccionAbstracX;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIxAvisos.avisos.JGUIxAvisosDatosGenerales;
import utilesGUIxAvisos.tablas.JTGUIXAVISOS;

public class JProcesoRecordeMail extends JProcesoAccionAbstracX {
    private final JListDatos moList;
    private StringBuffer moErrores = new StringBuffer();
    private final JGUIxAvisosDatosGenerales moDatos;
    

    public JProcesoRecordeMail(JListDatos poList, JGUIxAvisosDatosGenerales poDatos){
        moList=poList;
        moDatos=poDatos;
    }

    @Override
    public String getTitulo() {
        return "Envio Email";
    }

    @Override
    public int getNumeroRegistros() {
        return moList.size();
    }

    @Override
    public void procesar() throws Throwable {
        mlRegistroActual=0;
        if(moList.moveFirst()) {
           moDatos.inicializarEmail();   
            do{
                mlRegistroActual=moList.getIndex();
                try{
//                    moDatos.enviarEmail(
//                            moList.getFields(JTGUIXAVISOS.lPosiEMAIL).toString(), 
//                            moList.getFields(JTGUIXAVISOS.lPosiASUNTO).toString(), 
//                            moList.getFields(JTGUIXAVISOS.lPosiTEXTO).toString());

                }catch(Exception e){
                   moErrores.append(moList.getFields(JTGUIXAVISOS.lPosiEMAIL).getString());
                   moErrores.append('=');
                   moErrores.append(e.toString());
                   moErrores.append('\n');
                }
                try{
//                        moDatos.enviarSMS(
//                            moList.getFields(JTGUIXAVISOS.lPosiTELF).getString(), 
//                            moList.getFields(JTGUIXAVISOS.lPosiSENDER).getString(),
//                            moList.getFields(JTGUIXAVISOS.lPosiTEXTO).getString()
//                            );
                }catch(Exception e){
                   moErrores.append(moList.getFields(JTGUIXAVISOS.lPosiTELF).getString());
                   moErrores.append('=');
                   moErrores.append(e.toString());
                   moErrores.append('\n');
                }
                
                
            }while(moList.moveNext() && !mbCancelado);
        }
        mbFin=true;
    }
    @Override
    public String getTituloRegistroActual() {
        return moList.getFields(JTGUIXAVISOS.lPosiEMAIL).getString() 
                + " " + moList.getFields(JTGUIXAVISOS.lPosiTELF).getString();
    }
    @Override
    public void mostrarMensaje(String psMensaje) {
        if(moErrores.length()>0){
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, moErrores.toString(), IMostrarPantalla.mclMensajeInformacion, null);
        }else{
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, psMensaje, IMostrarPantalla.mclMensajeInformacion, null);
        }
    }

}
