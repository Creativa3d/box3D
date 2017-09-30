
package ListDatos;

import utiles.*;
/**
 * Objeto estandar para devolver el resultado de una/unas actualizaciones 
 */
public class JResultado implements IResultado {
    private static final long serialVersionUID = 33333313L;
    private JListaElementos moListDatos = new JListaElementos();

    /**mensaje*/
    private String msMensaje;
    /**si esta bien*/
    private boolean mbBien;

    /**
     * Constructor
     * @param psMensaje mensaje
     * @param pbBien si bien
     */
    public JResultado(final String psMensaje, final boolean pbBien){
        super();
        msMensaje = psMensaje;
        mbBien = pbBien;
    }
    /**
     * Constructor con un registro actualizado
     * @param poFilaDatos fila datos
     * @param psTabla tabla
     * @param psMensaje mensaje
     * @param pbBien si esta bien
     * @param plOperacion tipo operacion
     */
    public JResultado(final IFilaDatos poFilaDatos,final String psTabla, final String psMensaje, final boolean pbBien, final int plOperacion) {
        super();
        addDatos(poFilaDatos, psTabla, plOperacion);
        msMensaje = psMensaje;
        mbBien = pbBien;
    }
    
    /**
     * Anade una dato de actualizacion 
     * @param poFilaDatos fila datos
     * @param psTabla tabla
     * @param plOperacion operacion
     */
    public void addDatos(final IFilaDatos poFilaDatos, final String psTabla, final int plOperacion){
        JListDatos loListDatos = new JListDatos();
//        loListDatos.setIncrementoMemoria(2);
        loListDatos.add(poFilaDatos);
        loListDatos.msTabla = psTabla;
        poFilaDatos.setTipoModif (plOperacion);

        moListDatos.add(loListDatos);

    }
    /**
     * Devuelve el numero de actualizaciones 
     * @return el numero de actualizaciones
     */
    public int getSize(){
        return moListDatos.size();
    }
    /**
     * Anade el resultado a esta clase
     * @param poResult anade un objeto resultado
     */
    public void addResultado(final IResultado poResult){
        IListaElementos loList = poResult.getListDatos();
        if(loList!=null) {
            for(int i = 0 ; i<loList.size(); i++){
                JListDatos loListDato = (JListDatos)loList.get(i);
                moListDatos.add(loListDato);
            }
            loList = null;
        }

        msMensaje = msMensaje + poResult.getMensaje();
        mbBien = mbBien  && poResult.getBien();
    }
    public JListDatos getListDatos(String psTabla) throws CloneNotSupportedException{
        return getListDatosDeTabla(moListDatos, psTabla);
    }
    public IListaElementos getListDatos(){
        return moListDatos;
    }
    /**
     * devuelve el objeto en cadena
     * @return cadena
     */
    public String toString(){
        return msMensaje;
    }
    /**
     * lanza un error si no esta bien
     * @throws Exception error
     */
    public void lanzarErrorSiNecesario() throws Exception{
        if(!mbBien) {
            throw new Exception(msMensaje);
        }
    }
    /** Indica si la operacion ha tenido exito*/
    public boolean getBien() {
        return mbBien;
    }

    public String getMensaje() {
        return msMensaje;
    }
    /**Establece si el resultado es correcto o no
     *@param pbBien si es correcto
     */
    public void setBien(final boolean pbBien) {
        mbBien = pbBien;
    }

    /**
     * Establece el mensaje
     * @param psMensaje mensaje 
     */
    public void setMensaje(final String psMensaje) {
        msMensaje = psMensaje;
    }

    public String getXML() {
        return JXMLSelectMotor.getXMLResultado(this);
    }

    /**Metodo de ayuda a getListDatos(), devuelve todas las filas q pertenecen a la tabla dada por parametro*/
    public static JListDatos getListDatosDeTabla(IListaElementos poList, String psTabla) throws CloneNotSupportedException{
        JListDatos loResult = null;
        //recorremos las lista de JListDatos
        for(int i = 0 ; i<poList.size(); i++){
            JListDatos loListDato = (JListDatos)poList.get(i);
            //comprobamos si es la misma tabla q la pasada por parametro
            if(loListDato.msTabla.equalsIgnoreCase(psTabla)){
                //si no se ha creado el jlistdatos resultado se crea ahora clonado la estruc. del q coincida
                if(loResult==null){
                    loResult = new JListDatos(loListDato, false);
                }
                //anadimos las filas al resultado, clonando la fila
                if(loListDato.moveFirst()){
                    do{
                        loResult.add((IFilaDatos) loListDato.moFila().clone());
                    }while(loListDato.moveNext());
                }
            }
        }
        return loResult;

    }
    
}
