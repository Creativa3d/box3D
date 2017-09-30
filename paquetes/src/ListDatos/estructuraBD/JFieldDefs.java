/*
 * JFieldDef.java
 *
 * Created on 12 de septiembre de 2003, 9:46
 */
package ListDatos.estructuraBD;

import ListDatos.*;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;

/**
 * Lista de campos(con estructura) 
 */
public class JFieldDefs implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = 33333333L;
    private IListaElementos<JFieldDef> moFields;
    private String msTabla = null;
    private boolean mbAlgunoCalculado = false;

    /**
     *Constructor
     */
    public JFieldDefs() {
        super();
        moFields = new JListaElementos();
    }

    /**
     *Constructor
     *@param plNumeroCampos Numero de campos
     */
    public JFieldDefs(final int plNumeroCampos) {
        super();
        moFields = new JListaElementos(plNumeroCampos);
    }

    /**
     *Constructor
     *@param psNombres Array de los nombres de los campos
     *@param palCamposPrincipales  Array de las posiciones  campos principales
     *@param psNombresCaption array de los caption de los campos
     *@param palTipos Array de los tipos de los campos (JListDatos.mclTipo...)
     *@param palTamanos Array de los tamanos de los campos
     */
    public JFieldDefs(final String[] psNombres, final int[] palCamposPrincipales, final String[] psNombresCaption, final int[] palTipos, final int[] palTamanos) {
        super();
        try {

            if (palTamanos != null && psNombres.length != palTamanos.length) {
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Array Nombres y tamanos de long. diferente");
            }
            if (psNombres.length != palTipos.length) {
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Array Nombres y tipos de long. diferente");
            }
            if (psNombres.length != psNombresCaption.length) {
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Array Nombres y caption de long. diferente");
            }
            if(psNombres.length>0){
                moFields = new JListaElementos(psNombres.length);
            }else{
                moFields = new JListaElementos();
            }
            boolean lbPrincipal = false;
            for (int i = 0; i < psNombres.length; i++) {
                //vemos si el campo pertenece a los principales
                lbPrincipal = false;
                for (int ii = 0; palCamposPrincipales != null && ii < palCamposPrincipales.length; ii++) {
                    lbPrincipal = lbPrincipal || (palCamposPrincipales[ii] == i);
                }
                JFieldDef loCampo;
                if (palTamanos == null) {
                    //creamos el Field
                    loCampo = (new JFieldDef(
                            (int) (palTipos[i]),
                            psNombres[i],
                            psNombresCaption[i],
                            lbPrincipal));
                } else {
                    //creamos el Field
                    loCampo = (new JFieldDef(
                            (int) (palTipos[i]),
                            psNombres[i],
                            psNombresCaption[i],
                            lbPrincipal, palTamanos[i]));
                }
                //si es clave primeria NO PUEDE SER NULO
                loCampo.setNullable(!lbPrincipal);
                addField(loCampo);
            }
        } catch (Exception e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }

    public void setTabla(final String psTabla) {
        msTabla = psTabla;
        for(int i = 0 ; i < moFields.size(); i++){
            JFieldDef loCampo = (JFieldDef) moFields.get(i);
            loCampo.setTabla(psTabla);
        }
    }
    public String getTabla(){
        return msTabla;
    }

    /**
     * Devuelve el objeto IFilaDatos con los valores de los campos
     * @return Fila de datos
     */
    public IFilaDatos moFilaDatos() {
        int i;
        IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(msTabla);
        String[] lasDatos = new String[count()];
        for (i = 0; i < moFields.size(); i++) {
            lasDatos[i] = ((JFieldDef) moFields.get(i)).getString();
        }
        loFila.setArray(lasDatos);
        return loFila;
    }

    /**
     * Devuelve el objeto JFieldDef de la posicion i
     * @return Campo
     * @param i Posicion del campo
     */
    public JFieldDef get(final int i) {
        return ((JFieldDef) moFields.get(i));
    }

    /**
     * Devuelve el objeto JFieldDef de nombre de campo psNombre1
     * @return Campo
     * @param psNombre1 Nombre del campo
     */
    public JFieldDef get(final String psNombre1) {
        int i;
        JFieldDef loField = null;

        String lsNombre = psNombre1.toUpperCase();
        for (i = 0; (i < moFields.size()) && (loField == null); i++) {
            if (((JFieldDef) moFields.get(i)).getNombre().equalsIgnoreCase(lsNombre)) {
                loField = ((JFieldDef) moFields.get(i));
            }
        }

        return loField;
    }

    /**
     *Devuelve el la posicion del campo correspondiente
     */
    public int getIndiceDeCampo(final String psNombre1) {
        int i;
        int lResult = -1;

        String lsNombre = psNombre1.toUpperCase();
        for (i = 0; (i < moFields.size()) && (lResult == -1); i++) {
            if (((JFieldDef) moFields.get(i)).getNombre().equalsIgnoreCase(lsNombre)) {
                lResult = i;
            }
        }

        return lResult;
    }

//  /**
//   * Establece un campo en un indice concreto 
//   * @param plIndex indice
//   * @param poField campo
//   */
//
//  public void setField(final int plIndex, final JFieldDef poField){
//      maoFields[plIndex] = poField;
//  }
    /**
     * Anadimos un campo a la coleccion de campos
     * @param poField campo
     */
    public void addField(final JFieldDef poField) {
        if(msTabla!=null){
            poField.setTabla(msTabla);
        }
        moFields.add(poField);
        mbAlgunoCalculado |= poField.isCalculado();
    }
    /**Borrar un campo*/
    public boolean remove(final JFieldDef poField){
        return moFields.remove(poField);
    }
    /**Borrar un campo*/
    public JFieldDef remove(final int plIndex){
        return (JFieldDef) moFields.remove(plIndex);
    }

    public IListaElementos<JFieldDef> getListaCampos() {
        return moFields;
    }

    /**
     * Carga los valores de los campos a traves de la fila pasada por parametro
     * @param poFila fila de valores
     * @throws ECampoError error
     */
    public void cargar(final IFilaDatos poFila) throws ECampoError {

        //cargamos los campso con los valores de la fila
        int lCamposFila = poFila.mlNumeroCampos();
        for (int i = 0; i < moFields.size() && i < lCamposFila; i++) {
            ((JFieldDef) moFields.get(i)).setValue(poFila.msCampo(i));
        }
        //si hay mas campos que valores, terminamos de rellenar con vacio
        for (int i = lCamposFila; i < moFields.size(); i++) {
            ((JFieldDef) moFields.get(i)).setValue("");
        }
        
    }

    public void limpiar() throws ECampoError {

        for (int i = 0; i < moFields.size(); i++) {
            ((JFieldDef) moFields.get(i)).setValue("");
        }
    }

    /**
     * Numero de campos
     * @return numero de campos
     */
    public int size() {
        return count();
    }

    /**
     * Numero de campos
     * @return numero de campos
     */
    public int count() {
        return moFields.size();
    }

    /**
     * Devuelve un array de los nombres de los campos
     * @return Array de nombres
     */
    public String[] msNombres() {
        String[] lsStrings = new String[count()];

        for (int i = 0; i < count(); i++) {
            lsStrings[i] = ((JFieldDef) moFields.get(i)).getNombre();
        }

        return lsStrings;
    }
    /**
     * Devuelve un array de los tamanos de los campos
     * @return Array de nombres
     */
    public int[] malTamanos() {
        int[] lal = new int[count()];

        for (int i = 0; i < count(); i++) {
            lal[i] = ((JFieldDef) moFields.get(i)).getTamano();
        }

        return lal;
    }

    /**
     * Devuelve un array de los captions de los campos
     * @return Array de caption
     */
    public String[] msNombresCaption() {
        String[] lsStrings = new String[count()];

        for (int i = 0; i < count(); i++) {
            lsStrings[i] = ((JFieldDef) moFields.get(i)).getCaption();
        }

        return lsStrings;
    }
    /**
     * Establece los caption de todos los campos
     * @param pasCaption
     */
    public void setCaptions(String[] pasCaption) {
        for (int i = 0; i < moFields.size(); i++) {
            ((JFieldDef) moFields.get(i)).setCaption(pasCaption[i]);
        }
    }

    /**
     * Devuelve un array de los tipos de los campos
     * @return Array de tipos
     */
    public int[] malTipos() {
        int[] lalTipos = new int[count()];

        for (int i = 0; i < count(); i++) {
            lalTipos[i] = ((JFieldDef) moFields.get(i)).getTipo();
        }

        return lalTipos;
    }

    /**
     * Devuelve un array de los indices de los campos principales
     * @return Array de posiciones de campos principales
     */
    public int[] malCamposPrincipales() {
        int[] lalCamposPrincipales = new int[count()];
        int lIndex = 0;
        int[] lalCampos = null;

        for (int i = 0; i < count(); i++) {
            if (((JFieldDef) moFields.get(i)).getPrincipalSN()) {
                lalCamposPrincipales[lIndex] = i;
                lIndex++;
            }
        }

        if (lIndex != 0) {
            lalCampos = new int[lIndex];
            System.arraycopy(lalCamposPrincipales, 0, lalCampos, 0, lIndex);
//      for(int i = 0; i< lIndex; i++) {
//          lalCampos[i] = lalCamposPrincipales[i];
//      }
        }

        lalCamposPrincipales = null;

        return lalCampos;
    }
    public int[] malCamposNOT_NULL() {
        int[] lalCamposPrincipales = new int[count()];
        int lIndex = 0;
        int[] lalCampos = null;

        for (int i = 0; i < count(); i++) {
            if (!((JFieldDef) moFields.get(i)).getNullable()) {
                lalCamposPrincipales[lIndex] = i;
                lIndex++;
            }
        }

        if (lIndex != 0) {
            lalCampos = new int[lIndex];
            System.arraycopy(lalCamposPrincipales, 0, lalCampos, 0, lIndex);
//      for(int i = 0; i< lIndex; i++) {
//          lalCampos[i] = lalCamposPrincipales[i];
//      }
        }

        lalCamposPrincipales = null;

        return lalCampos;
    }

    /**
     * Devuelve un array de valores de los campos principales
     * @return Array de nombres
     */
    public String[] masCamposPrincipales() {
        int[] lalCamposPrincipales = malCamposPrincipales();

        String[] lasCadenas = new String[lalCamposPrincipales.length];

        for (int i = 0; i < lalCamposPrincipales.length; i++) {
            lasCadenas[i] = get(lalCamposPrincipales[i]).getString();
        }

        lalCamposPrincipales = null;

        return lasCadenas;
    }

    /**Devuelve una cadena con los campos principales, separados por JFilaDatosDefecto.mcsSeparacion
     * @param plNumeroCampos
     * @return */
    public String getCamposPrincipales(final int plNumeroCampos) {
        int[] lalCamposPrincipales = malCamposPrincipales();

        StringBuilder lsCadenas = new StringBuilder();

        for (int i = 0; i < plNumeroCampos; i++) {
            lsCadenas.append(get(lalCamposPrincipales[i]).getString());
            lsCadenas.append(JFilaDatosDefecto.mcsSeparacion1);
        }

        lalCamposPrincipales = null;

        return lsCadenas.toString();
    }
    public String getCamposPrincipales() {
        return getCamposPrincipales(malCamposPrincipales().length);
    }

    /**calcula para la fila los campos calculados**/
    public void cargarCamposCalculados(final IFilaDatos poFila) {
        if (mbAlgunoCalculado) {
            int lCamposFila = poFila.mlNumeroCampos();
            String[] lasDatos = new String[moFields.size()];
            for (int i = 0; i < moFields.size(); i++) {
                if(moFields.get(i) instanceof JFieldDefCalculado){
                    lasDatos[i] = ((JFieldDefCalculado)moFields.get(i)).getCalculado().getValorCalculado(poFila);
                }else{
                    if(i < lCamposFila){
                        lasDatos[i] = poFila.msCampo(i);
                    }else{
                        lasDatos[i] = "";
                    }
                }
            }
            poFila.setArray(lasDatos);
        }
    }

    public void comprobarNulos() throws Exception {
        for (int i = 0; i < moFields.size(); i++) {
            if (!((JFieldDef) moFields.get(i)).getNullable() && ((JFieldDef) moFields.get(i)).isVacio()) {
                throw new Exception("Campo " + ((JFieldDef) moFields.get(i)).getCaption() + " no puede ser vacío");
            }
        }
    }

    public Object clone() throws CloneNotSupportedException {
        JFieldDefs loFields = (JFieldDefs) super.clone();

        //se borran todos los campos
        loFields.moFields=new JListaElementos(moFields.size());
        //se vuelven ha anadir
        for (int i = 0; i < moFields.size(); i++) {
            loFields.addField(get(i).Clone());
        }

        return loFields;
    }

    /**
     * Clonacion del objeto con un tipo dado, porque al heredar de clone debe devolver object, por eso esta este ?Clone? con mayusculas
     * @return  objeto clonado
     */
    public JFieldDefs Clone() throws CloneNotSupportedException {
        return (JFieldDefs) clone();
    }

    public String toString() {
        StringBuilder lsCadena = new StringBuilder(moFields.size() * 10);
        for (int i = 0; i < moFields.size(); i++) {
            lsCadena.append(((JFieldDef) moFields.get(i)).getNombre());
            lsCadena.append('=');
            lsCadena.append(((JFieldDef) moFields.get(i)).getString());
            lsCadena.append(JFilaDatosDefecto.mccSeparacion1);
        }
        //transparentamos los cambios de linea
        String lsAux = lsCadena.toString();
        lsAux = lsAux.replace((char) 10, JFilaDatosDefecto.mccTransparentacionCambioLinea10);
        lsAux = lsAux.replace((char) 13, JFilaDatosDefecto.mccTransparentacionCambioLinea13);

        return lsAux;
    }

    /**
     * Comprobamos que la estruc. de campos pasada por parametro sea igual a esta estruc. de campos
     * @param poCampos estruc. de campos a comparar
     * @return si son iguales
     */
    public boolean isEstructuraIgual(JFieldDefs poCampos) {
        boolean lbResult = poCampos.size()==size();
        for(int i = 0 ; i < size() && lbResult; i++){
            if(!get(i).isEstructuraIgual(poCampos.get(i)) ){
                lbResult = false;
            }
        }
        return lbResult;
    }
}
