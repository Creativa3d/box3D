/*
 * JxmlBanda.java
 *
 * Created on 25 de enero de 2007, 9:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import impresionXML.impresion.estructura.IBanda;
import impresionXML.impresion.estructura.IImagen;
import impresionXML.impresion.estructura.ILinea;
import impresionXML.impresion.estructura.ITextoLibre;
import impresionXML.impresion.motorImpresion.JBanda;
import java.awt.geom.Rectangle2D;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JxmlBanda extends JxmlAbstract implements IxmlObjetos {
    public static final String mcsNombreXml = "banda";
    private static final long serialVersionUID = 1L;
    
    private String msNombre;
    private Rectangle2D moPosicionDestino = new Rectangle2D.Float(0,0,1,1);
    private boolean mbExtensible = true;
    
    private IListaElementos moObjetos = new JListaElementos();
    
    private IBanda moBanda;
    private ITextoLibre moTexto;
    private ILinea moLinea;
    private IImagen moImagen;

    /**
     * Creates a new instance of JxmlBanda
     */
    public JxmlBanda() {
        super();
    }
    
    public void crearColecciones(){
        moObjetos = new JListaElementos();
    }
    
    public void inicializarImpresion(){
        moBanda = new JBanda((float)getPosicionDestino().getHeight(), isMbExtensible());
        moImagen = getBanda().insertarImagen();
        moTexto = getBanda().insertarTexto();
        moLinea  = getBanda().insertarLinea();
    }
    
    public String toString() {
        return getNombre();
    }
    
    private IListaElementos getObjetoNombre(final String psNombre, final Class poTipo){
        IListaElementos loLista = new JListaElementos(); 
        for(int i = 0 ; i < moObjetos.size();i++ ){
            IxmlObjetos loObj = (IxmlObjetos)moObjetos.get(i);
            if(loObj.getClass() == poTipo && 
               ((loObj.getNombre()!=null &&
                 loObj.getNombre().equalsIgnoreCase(psNombre)) ||
                psNombre.equalsIgnoreCase(""))
              ){
                loLista.add(loObj);
            }
        }
        return loLista;
    }
    
    public IListaElementos getCuadradoNombre(final String psNombre) {
        return getObjetoNombre(psNombre, JxmlCuadrado.class);
    }
    public IListaElementos getLineaNombre(final String psNombre) {
        return getObjetoNombre(psNombre, JxmlLinea.class);
    }
    public IListaElementos getImagenNombre(final String psNombre) {
        return getObjetoNombre(psNombre, JxmlImagen.class);
    }
    public IListaElementos getTextoNombre(final String psNombre) {
        return getObjetoNombre(psNombre, JxmlTexto.class);
    }

    public IListaElementos getCuadradosNombre() {
        return getObjetoNombre("", JxmlCuadrado.class);
    }
    public IListaElementos getLineasNombre() {
        return getObjetoNombre("", JxmlLinea.class);
    }
    public IListaElementos getImagenesNombre() {
        return getObjetoNombre("", JxmlImagen.class);
    }
    public IListaElementos getTextosNombre() {
        return getObjetoNombre("", JxmlTexto.class);
    }
    
    public IxmlObjetos get(final int i) {
        return (IxmlObjetos)moObjetos.get(i);
    }
    public void add(final IxmlObjetos loObjeto) {
        moObjetos.add(loObjeto);
        firePropertyChange("add", null, loObjeto);
    }
    public int size(){
        return moObjetos.size();
    }
    public void remove(Object poObjeto){
        moObjetos.remove(poObjeto);
        firePropertyChange("remove", poObjeto, null);
    }
    public void remove(int plIndex){
        Object lo= moObjetos.remove(plIndex);
        firePropertyChange("remove", lo, null);
    }
    
    IBanda getBanda() {
        return moBanda;
    }

    ITextoLibre getTexto() {
        return moTexto;
    }

    ILinea getLinea() {
        return moLinea;
    }

    IImagen getImagen() {
        return moImagen;
    }
    
    public void imprimir(JxmlBanda poBanda, JxmlInforme poInforme) {
        imprimir(poInforme);
    }
    public void imprimir(final JxmlInforme poInforme){
        for(int i = 0; i<moObjetos.size();i++){
            IxmlObjetos loObj = (IxmlObjetos)moObjetos.get(i);
            loObj.imprimir(this, poInforme);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        JxmlBanda retValue;
        
        retValue = (JxmlBanda)super.clone();
        
        retValue.crearColecciones();
        
        retValue.setPosicionDestino((Rectangle2D.Float) getPosicionDestino().clone());
        
        for(int i = 0 ; i < moObjetos.size();i++){
            retValue.add((IxmlObjetos)((IxmlObjetos)moObjetos.get(i)).clone() );
        }
        
        
        return retValue;
    }

    public String getNombre() {
        return msNombre;
    }

    public void setNombre(final String msNombre) {
        this.msNombre = msNombre;
    }

    public Rectangle2D getPosicionDestino() {
        return moPosicionDestino;
    }

    public void setPosicionDestino(final Rectangle2D moPosicionDestino) {
        this.moPosicionDestino = moPosicionDestino;
    }

    public boolean isMbExtensible() {
        return mbExtensible;
    }

    public void setExtensible(final boolean mbExtensible) {
        this.mbExtensible = mbExtensible;
    }
    public String getNombreXML(){
        return mcsNombreXml;
    }

    public void visitar(IVisitorOperacion poOperador)  throws Throwable {
        poOperador.operar(this);
        for(int i = 0 ; i < moObjetos.size();i++){
            IxmlObjetos loObj = (IxmlObjetos) moObjetos.get(i);
            loObj.visitar(poOperador);
        }
    }

    
}
