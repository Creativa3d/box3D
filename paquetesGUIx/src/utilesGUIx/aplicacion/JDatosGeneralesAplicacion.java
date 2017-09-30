/*
* JDatosGenerales.java
*
* Creado el 29/7/2008
*/

package utilesGUIx.aplicacion;


import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import utiles.*;
import utiles.xml.dom.Element;
import utilesBD.relaciones.*;
import utilesGUIx.*;
import utilesGUIx.borradoRelaciones.*;
import utilesGUIx.configForm.JT2CONEXIONES;
import utilesGUIx.controlProcesos.IProcesoThreadGroup;
import utilesGUIx.controlProcesos.JProcesoThreadGroup;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;
import utilesGUIx.lookAndFeel.JLookAndFeel;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtension;
import utilesGUIxAvisos.tablasControladoras.JT2GUIXEVENTOS;

public class JDatosGeneralesAplicacion extends JDatosGeneralesAplicacionModelo {

    protected JLookAndFeel moLook;
    private JMenuBar moMenuPrincipal;
    private JT2GUIXEVENTOS moGUIxEventos;
    

    public JDatosGeneralesAplicacion() {
        setAplicacion(new IAplicacion() {
            @Override
            public void mostrarFormPrincipal() {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try{
                            JFormPrincipal loF=new JFormPrincipal(JDatosGeneralesAplicacion.this);
                            loF.setVisible(true);
                            loF.inicializar();
                        }catch(Throwable e){
                            JMsgBox.mensajeErrorYLog(null, e, JDatosGeneralesAplicacion.class.getName());
                        }
                    }
                });
            }
            
            public void mostrarPropiedadesConexionBD() throws Exception{

                JT2CONEXIONES loConexiones = new JT2CONEXIONES(moDatosGeneralesXML, (moMostrarPantalla == null ? JGUIxConfigGlobal.getInstancia().getMostrarPantalla():moMostrarPantalla));
                loConexiones.mostrarFormPrinci();

            }

            public void mostrarLogin() throws Exception{
                if(SwingUtilities.isEventDispatchThread()){
                    JFormLogin loLogin = new JFormLogin(new Frame(), true,JDatosGeneralesAplicacion.this);
                    loLogin.setVisible(true);        
                }else{
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            try{
                                JFormLogin loLogin = new JFormLogin(new Frame(), true,JDatosGeneralesAplicacion.this);
                                loLogin.setVisible(true);        
                            }catch(Throwable e){
                                JMsgBox.mensajeErrorYLog(null, e, JDatosGeneralesAplicacion.class.getName());
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void inicializar(JParametrosAplicacionModelo poParam) {
        super.inicializar(poParam); 
    }
    

    public void borrarTodo(JFieldDefs poFields, String psTabla) throws Exception {
        JBorradoCompleto loBorradoCompleto = new JBorradoCompleto(getServer(), new JRelacionesFormBorrado());
        IResultado loResul = loBorradoCompleto.borrarTodo(poFields, psTabla, true);
        if(!loResul.getBien())
            throw new Exception(loResul.getMensaje());
    }

    
    public JMenuBar getMenu(){
        return moMenuPrincipal;
    }
    public void setMenu(JMenuBar poMenu){
        moMenuPrincipal=poMenu;
    }
    
    public void setDesktopPane1(IMostrarPantalla poMostrar, IPlugInFrame poFramePrinci, IProcesoThreadGroup poThreadGroup) {
        moProcesoGroup = poThreadGroup;
        setFormPrincipal(poFramePrinci);
        setMostrarPantalla(poMostrar);
    }
    public void setDesktopPane1(final JDesktopPane poDesktopPane1, IPlugInFrame poFramePrinci, IProcesoThreadGroup poThreadGroup) {
        moProcesoGroup = poThreadGroup;

        setFormPrincipal(poFramePrinci);
        setThreadGroup(poThreadGroup);
        JMostrarPantalla loMostrar = new JMostrarPantalla(
                poDesktopPane1,
                JMostrarPantalla.mclEdicionFrame,
                JMostrarPantalla.mclEdicionInternal
                );
        if(moParametrosAplicacion.getImagenIcono()!=null){
            loMostrar.setImagenIcono(moParametrosAplicacion.getImagenIcono());
        }

//        loMostrar.setPlugInFactoria(JDatosGeneralesPAplicacion.getDatosGeneralesPlugIn());

        loMostrar.setTipoPrincipal(
                Integer.valueOf(moDatosGeneralesXML.getPropiedad(mcsTIPOFORMSPRINCIPALES_TIPO, String.valueOf(JPanelGenerico2.mclTipo))).intValue()
                );
        loMostrar.setTipoEdicion(
                Integer.valueOf(moDatosGeneralesXML.getPropiedad(mcsTIPOFORMS_EDICIONLAYOUT, String.valueOf(JMostrarPantalla.mclEdicionFrame))).intValue()
                );
        loMostrar.setTipoPrincipalMostrar(
                Integer.valueOf(moDatosGeneralesXML.getPropiedad(mcsTIPOFORMS_PRINCIPALLAYOUT, String.valueOf(JMostrarPantalla.mclEdicionInternal))).intValue()
                );

        loMostrar.setGrupoThreads(getThreadGroup());
        setMostrarPantalla(loMostrar);
    }

    public synchronized JLookAndFeel getLookAndFeelObjeto(){
        if(moLook == null){
            JFormPrincipal.rellenarLookAndFeel();
            moLook = new JLookAndFeel();
        }
        return moLook;
    }
    
    @Override
    public synchronized IProcesoThreadGroup getThreadGroup() {
        if(moProcesoGroup==null){
            moProcesoGroup=new JProcesoThreadGroup();
        }
        return moProcesoGroup;
    }
    
    public Element getImagenFondoElem(){
        Element loElem = moDatosGeneralesXML.getElemento(mcsImagenFondo);
        if(loElem == null){
            loElem = new Element(mcsImagenFondo);
            getDatosGeneralesXML().getDocumento().getRootElement().add(loElem);
        }
        return loElem;
    }
    public String getImagenFondoQueToque(){
        String lsResult = "";
        if(getImagenFondoElem().getValor()!=null && !getImagenFondoElem().getValor().equals("")){
            File loDir = new File(getImagenFondoElem().getValor());
            if(loDir.exists()){
                if(loDir.isDirectory()){
                    File[] loFiles = loDir.listFiles(
                            new JFileChooserFiltroPorExtension("Imágenes",new String[]{"gif","jpeg", "jpg", "png"})
                            );
                    if(loFiles.length>1){
                        lsResult = loFiles [(int)((double)loFiles.length*Math.random())].getAbsolutePath();
                    }else if (loFiles.length==1){
                        lsResult = loFiles[0].getAbsolutePath();
                    } else {
                        lsResult = "";
                    }
                }else{
                    lsResult = loDir.getAbsolutePath();
                }
            }
        }
        return lsResult;
    }
    public ImageIcon getImagenFondoQueToqueIcon() {
        ImageIcon loResult = null;
        String lsImg = getImagenFondoQueToque();
        if(!lsImg.equals("")){
            File loFile = new File(lsImg);
            if(loFile.exists()){
                try{
                    loResult = new ImageIcon(loFile.getAbsolutePath());
                }catch(Throwable e){
                    JDepuracion.anadirTexto(getClass().getName(), "Imagen " + loFile.getAbsolutePath() + " no se ha podido cargar ("+e.toString()+")");
                }
            }
        }
        if(loResult == null && getParametrosAplicacion().getImagenFondo()!=null){
            loResult = (ImageIcon) getParametrosAplicacion().getImagenFondo();
        }
        return loResult;
    }
    public void restaurarBotonesOriginales() throws Exception {
        if(getFormPrincipal() instanceof JFormPrincipal){
            ((JFormPrincipal)getFormPrincipal()).restaurarBotonesOriginales();
        }
    }
    public void ponerLookAndFeel(Component c){
        Component loBase = getLookAndFeelObjeto().getComponenteBase();
        try{
            getLookAndFeelObjeto().setComponenteBase(c);
            JFormPrincipal.ponerLookAndFeel(
                    getLookAndFeelObjeto(),
                    getLookAndFeel(),
                    getLookAndFeelTema()
                    );
        }finally{
            getLookAndFeelObjeto().setComponenteBase(loBase);
        }
        
    }
 
    public JT2GUIXEVENTOS getEventos() throws Exception{
        if(moGUIxEventos==null){
            moGUIxEventos = new JT2GUIXEVENTOS(getTareasAvisos1()
                , "", getCalendarioDefecto(getServer()), ""
                , getCodUsuario() );
            moGUIxEventos.getParametros().setPlugInPasados(true);
            
        }
        return moGUIxEventos;
    }

}
