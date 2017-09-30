/*
* JDatosGenerales.java
*
* Creado el 29/7/2008
*/

package utilesGUIx.aplicacion;


import ListDatos.*;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.controlProcesos.JProcesoElementoFactoryMethod;
import utilesGUIx.controlProcesos.JProcesoManejador;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;

public class JDatosGeneralesAplicacion extends JDatosGeneralesAplicacionModelo {
    

    public JDatosGeneralesAplicacion() {
    }
    public JParametrosAplicacion getParametrosApl(){
    	return (JParametrosAplicacion)getParametrosAplicacion();
    }
         
    @Override
    public synchronized IProcesoThreadGroup getThreadGroup() {
        if(super.getThreadGroup()==null){
            setThreadGroup(new JProcesoManejador(new JProcesoElementoFactoryMethod()));
        }
        return super.getThreadGroup(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public IFilaDatos mostrarBusqueda(IConsulta loConsulta, JSTabla loTabla) throws Exception{
        IFilaDatos loResult = null;
        JBusqueda loBusq = new JBusqueda( 
                loConsulta,
                loConsulta.getList().msTabla);
        loBusq.mostrarFormPrinci();

        if(loBusq.getIndex()>=0){
            loTabla.recuperarFiltradosNormal(
                    new JListDatosFiltroElem(
                        JListDatos.mclTIgual,
                        loTabla.getList().getFields().malCamposPrincipales(),
                        loConsulta.getList().getFields().masCamposPrincipales())
                    , false);

            loResult = loTabla.moList.moFila();
        }

        return loResult;
        
    }

    @Override
    public void mostrarPropiedadesConexionBD() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarLogin() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void autentificar(String string, String string1, String string2) throws Throwable {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarFormPrincipal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
