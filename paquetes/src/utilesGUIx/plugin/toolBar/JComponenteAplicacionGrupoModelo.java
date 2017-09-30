/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.plugin.toolBar;


import java.util.AbstractList;
import java.util.HashMap;
import java.util.Iterator;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;


public class JComponenteAplicacionGrupoModelo extends AbstractList implements IComponenteAplicacion, IListaElementos {
    private static final long serialVersionUID = 1L;

    public enum Distribucion { Rejilla, Libre };

 
    private int x=10;
    private int y=30;
    private Rectangulo moDimension=new Rectangulo(150, 300);
    private boolean closeable=true;
    private boolean iconificable=true;
    private boolean resizable=true;
    private boolean maximizable=false;
    
    private String msCaption;
    private String msGrupoBase=mcsGRUPOBASEINTERNO;
    private Object moIcon;
    private String msNombre;
    private boolean mbActivo = true;
    private IListaElementos moListaBotones = new JListaElementos();
    private transient HashMap moPropiedades = new HashMap();
    
    public JComponenteAplicacionGrupoModelo(){
        setColumnasDeBotones(2);
    }
    public JComponenteAplicacionGrupoModelo(String psGrupoBase, String psNombre){
        this(psGrupoBase, psNombre, 0, 0, new Rectangulo(0, 0));
    }
    public JComponenteAplicacionGrupoModelo(String psGrupoBase, String psNombre, int plx,int plY, Rectangulo poDimension){
        x=plx;
        y=plY;
        moDimension=poDimension;
        msGrupoBase=psGrupoBase;
        msNombre=psNombre;
        msGrupoBase=psGrupoBase;
        msCaption =psNombre;
        if(msNombre==null || msNombre.equalsIgnoreCase("")){
            msNombre="grupo" + new JDateEdu().msFormatear("ddMMyyyyhhmmss");
        }
    }
    public void setDistribucion(Distribucion pltipo){
        getPropiedades().put("Distribucion", pltipo);
//    private int mlColumnasDeBotones = 2;
    }
    public Distribucion getDistribucion(){
        return (Distribucion) getPropiedades().get("Distribucion");
    }
    public String getTipo() {
        return null;
    }
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public Rectangulo getDimension(){
        return moDimension;
    }
    public void setDimension(final Rectangulo poDimension){
        moDimension = poDimension;
    }
    

    /**
     * @return the closeable
     */
    public boolean isCloseable() {
        return closeable;
    }

    /**
     * @param closeable the closeable to set
     */
    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    /**
     * @return the iconificable
     */
    public boolean isIconificable() {
        return iconificable;
    }

    /**
     * @param iconificable the iconificable to set
     */
    public void setIconificable(boolean iconificable) {
        this.iconificable = iconificable;
    }

    /**
     * @return the resizable
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * @param resizable the resizable to set
     */
    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    /**
     * @return the maximizable
     */
    public boolean isMaximizable() {
        return maximizable;
    }

    /**
     * @param maximizable the maximizable to set
     */
    public void setMaximizable(boolean maximizable) {
        this.maximizable = maximizable;
    }

    /**
     * @return the msTitulo
     */
    public String getCaption() {
        return msCaption;
    }

    /**
     * @param psCap
     */
    public void setCaption(String psCap) {
        this.msCaption = psCap;
    }

    /**
     * @return the msNombre
     */
    public String getNombre() {
        return msNombre;
    }

    /**
     * @param msNombre the msNombre to set
     */
    public void setNombre(String msNombre) {
        this.msNombre = msNombre;
    }
    public void setIcon(Object poimageIcon) {
        moIcon = poimageIcon;
    }
    public Object getIcono() {
        return moIcon;
    }

    /**
     * @return the mbActivo
     */
    public boolean isActivo() {
        return mbActivo;
    }

    /**
     * @param mbActivo the mbActivo to set
     */
    public void setActivo(boolean mbActivo) {
        this.mbActivo = mbActivo;
    }
    /**
     * @return the mlColumnasDeBotones
     */
    public int getColumnasDeBotones() {
        try{
            return ((Integer)getPropiedades().get("ColumnasDeBotones")).intValue();
        }catch(Exception e){
            return 2;
        }
    }

    /**
     * @param mlColumnasDeBotones the mlColumnasDeBotones to set
     */
    public void setColumnasDeBotones(int mlColumnasDeBotones) {
        getPropiedades().put("ColumnasDeBotones", new Integer(mlColumnasDeBotones));
    }

    /**
     * @return the moListaBotones
     */
    public IListaElementos getListaBotones() {
        if(moListaBotones==null){
            moListaBotones=new JListaElementos();
        }
        return moListaBotones;
    }

    /**
     * @param moListaBotones the moListaBotones to set
     */
    public void setListaBotones(IListaElementos moListaBotones) {
        this.moListaBotones = moListaBotones;
    }

    public String getGrupoBase() {
        return msGrupoBase;
    }
    public void setGrupoBase(String psGrupo){
        msGrupoBase=psGrupo;
    }
    
    public void add(IComponenteAplicacion poComp){
        moListaBotones.add(poComp);
    }
    public  IFormEdicion getEdicion() {
        return null;
    }
    public utilesGUIx.ActionListenerCZ getAccion() {
        return null;
    }

    public void setAccion(utilesGUIx.ActionListenerCZ poEjecutar) {
    }
    public HashMap getPropiedades(){
        if(moPropiedades==null){
            moPropiedades=new HashMap();
        }
        return moPropiedades;
    }
    public void setPropiedades(HashMap po){
        moPropiedades = po;
    }
    public IComponenteAplicacion getComponente(String psGrupoBase, String psName_ActionComand){
        return getComponente(psGrupoBase, psName_ActionComand, true);
    }
    public IComponenteAplicacion getComponente(String psGrupoBase, String psName_ActionComand, boolean pbRecursivo) {
        IComponenteAplicacion loResult = null;
        for (int i = 0; getListaBotones() != null && i < getListaBotones().size() && loResult == null; i++) {
            IComponenteAplicacion loObj = (IComponenteAplicacion) getListaBotones().get(i);
            if ((loObj.getNombre() != null && loObj.getNombre().equalsIgnoreCase(psName_ActionComand))
                    && (psGrupoBase == null || psGrupoBase.equalsIgnoreCase(loObj.getGrupoBase()))) {
                loResult = loObj;
            } else if (loObj instanceof JComponenteAplicacionGrupoModelo && pbRecursivo) {
                loResult = ((JComponenteAplicacionGrupoModelo) loObj).getComponente(psGrupoBase, psName_ActionComand);
            }
        }
        return loResult;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        JComponenteAplicacionGrupoModelo loComp=null;
        try {
            loComp = (JComponenteAplicacionGrupoModelo) super.clone();
        } catch (CloneNotSupportedException ex) {
            JDepuracion.anadirTexto(JComponenteAplicacionGrupoModelo.class.getName(), ex);
        }
        loComp.moListaBotones=new JListaElementos();
        for(int i = 0 ; moListaBotones!=null && i <moListaBotones.size(); i++){
            IComponenteAplicacion loAux = (IComponenteAplicacion) moListaBotones.get(i);
            loComp.moListaBotones.add(loAux.clone());
        }
        loComp.moDimension=(Rectangulo) moDimension.clone();
        return loComp;

    }
    @Override
    public boolean add(Object poFilaDatos) {
        return moListaBotones.add(poFilaDatos);
    }

    public int size() {
        return moListaBotones.size();
    }

    public Object get(int i) {
        return moListaBotones.get(i);
    }

    @Override
    public Object remove(int index) {
        return moListaBotones.remove(index);
    }

    @Override
    public boolean remove(Object poObject) {
        return moListaBotones.remove(poObject);
    }

    @Override
    public void clear() {
        moListaBotones.clear();
    }

    @Override
    public Iterator iterator() {
        return moListaBotones.iterator();
    }
    
}
