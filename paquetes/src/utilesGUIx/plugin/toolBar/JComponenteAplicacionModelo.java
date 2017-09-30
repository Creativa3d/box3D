/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.toolBar;


import java.io.File;
import java.util.HashMap;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JDepuracion;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JComponenteAplicacionModelo implements IComponenteAplicacion{
    private static final long serialVersionUID = 1L;


    private String msTipo;
    private String msNombre;
    private String msCaption;
    private Object moIcon;
    private transient utilesGUIx.ActionListenerCZ moEjecutar;
    private Rectangulo moDimension;
    private transient HashMap moPropiedades = new HashMap();
    private boolean mbActivo = true;
    private int mlHorizontalTextAlignment = RIGHT;
    private int mlVerticalTextAlignment = -1;
    private int y;
    private int x;
    private String msGrupoBase=mcsGRUPOBASEINTERNO;
    
    public JComponenteAplicacionModelo() {
    }
    
    public JComponenteAplicacionModelo(String psTipo, String psNombre, String psCaption, String psImageCamino, ActionListenerCZ poEjecutar, Rectangulo poRectangulo) {
        Object loIcono=null;
        if(JGUIxConfigGlobalModelo.getInstancia().getCargarIcono()==null){
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "No esta establecido JGUIxConfigGlobalModelo.getInstancia().getCargarIcono(), por lo que no se veran los iconos");
        }else{
            if(!JCadenas.isVacio(psImageCamino)){
                loIcono = JGUIxConfigGlobalModelo.getInstancia().getCargarIcono().cargarIcono(psImageCamino, poEjecutar, null);
            }
        }
        setDatos(psTipo, psNombre, psCaption, poEjecutar, poRectangulo, loIcono, 4);
    }
    public JComponenteAplicacionModelo(
            String psTipo, String psNombre, String psCaption,
            ActionListenerCZ poEjecutar, Rectangulo poRectangulo,
            Object poIcon,
            int plHorizontalTextAlignment) {
        setDatos(psTipo, psNombre, psCaption, poEjecutar, poRectangulo, poIcon, plHorizontalTextAlignment);
    }
    
    /**
     * Creates a new instance of JComponenteAplicacion
     * @param psNombre Nombre
     * @param psCaption Caption
     * @param psImageCamino Camino de la imagen
     * @param poEjecutar Objeto que ejecuta la acción sobre una fila
     * @param poEjecutarExtend Objeto que ejecuta la acción sobre multiples filas
     * @param poRectangulo Dimesion del boton
     * @param psGrupo Grupo del Boton
     */
    public void setDatos(
            String psTipo, String psNombre, String psCaption,
            ActionListenerCZ poEjecutar, Rectangulo poRectangulo,
            Object poIcon,
            int plHorizontalTextAlignment) {
        msTipo = psTipo;
        if(msTipo.equalsIgnoreCase(mcsTipoMENU) ){
            msGrupoBase=mcsGRUPOMENU;
        }
        moIcon = poIcon;
        msNombre = psNombre;
        msCaption = psCaption;
        moEjecutar = poEjecutar;
        moDimension = poRectangulo;
        mlHorizontalTextAlignment = plHorizontalTextAlignment;
    }

    @Override
    public utilesGUIx.ActionListenerCZ getAccion(){
        return moEjecutar;
    }
    @Override
    public void setAccion(utilesGUIx.ActionListenerCZ poEjecutar){
        moEjecutar = poEjecutar;
    }
    @Override
    public String getCaption() {
        return msCaption;
    }
    @Override
    public void setCaption(String ps) {
        msCaption = ps; 
    }
    public void setIcono(Object poimageIcon) {
        moIcon = poimageIcon;
    }
    public Object getIcono() {
        return moIcon;
    }
    public void setDimension(final Rectangulo poDimension){
        moDimension = poDimension;
    }
    @Override
    public Rectangulo getDimension(){
        return moDimension;
    }
    public void setGrupoBase(String psGrupo){
        msGrupoBase=psGrupo;
    }

    /**
     * @return the x
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }
    public void setBounds(int plx, int ply, int width, int height){
        x=plx;
        y=ply;
        moDimension = new Rectangulo(width, height);
    }
    
//
//    public String getGrupo(){
//        return msGrupo;
//    }

//    public boolean isEsPrincipal() {
//        return msGrupo.equals(mcsGrupoPRINCIPAL);
//    }
//
//    public void setEsPrincipal(boolean mbEsPrincipal) {
//        if(mbEsPrincipal){
//            msGrupo = mcsGrupoPRINCIPAL;
//        }else{
//            msGrupo = "";
//        }
//    }
    
    public void setHorizontalTextAlignment(int plValor){
        mlHorizontalTextAlignment= plValor;
    }
    public int getHorizontalTextAlignment(){
        return mlHorizontalTextAlignment;
    }
    public void setVerticalTextAlignment(int plValor){
            mlVerticalTextAlignment=plValor;
    }
    public int getVerticalTextAlignment(){
        return mlVerticalTextAlignment;
    }

    /**
     * @return the msNombre
     */
    @Override
    public String getNombre() {
        return msNombre;
    }

    /**
     * @return the msTipo
     */
    public String getTipo() {
        return msTipo;
    }
    public void setTipo(String psTipo){
        msTipo=psTipo;
    }

    public HashMap getPropiedades(){
        return moPropiedades;
    }
    public void setPropiedades(HashMap po){
        moPropiedades = po;
    }

    /**
     * @return the mbActivo
     */
    @Override
    public boolean isActivo() {
        return mbActivo;
    }

    /**
     * @param mbActivo the mbActivo to set
     */
    @Override
    public void setActivo(boolean mbActivo) {
        this.mbActivo = mbActivo;
    }

    @Override
    public String getGrupoBase() {
        return msGrupoBase;
    }

    @Override
    public IListaElementos getListaBotones() {
        return null;
    }

    @Override
    public IFormEdicion getEdicion() {
        return null;
    }

    @Override
    public Object clone(){
        JComponenteAplicacionModelo loComp=null;
        try {
            loComp = (JComponenteAplicacionModelo) super.clone();
            if(moDimension!=null){
                loComp.setDimension((Rectangulo) moDimension.clone());
            }
            if(moPropiedades!=null){
                loComp.setPropiedades((HashMap) moPropiedades.clone());
            }else{
                loComp.setPropiedades(new HashMap());
            }
        } catch (CloneNotSupportedException ex) {
            JDepuracion.anadirTexto(JComponenteAplicacionModelo.class.getName(), ex);
        }
        return loComp;

    }
    @Override
    public IComponenteAplicacion getComponente(String psGrupoBase, String psName_ActionComand){
        return null;
    }
    @Override
    public IComponenteAplicacion getComponente(String psGrupoBase, String psName_ActionComand, boolean pbRecursivo){
        IComponenteAplicacion loResult = null;
        return loResult;
    }
    @Override
    public void add(IComponenteAplicacion poComp){
        
    }
}
