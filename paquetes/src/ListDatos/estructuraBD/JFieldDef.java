/*
 * JFieldDef.java
 *
 * Created on 12 de septiembre de 2003, 9:46
 */
package ListDatos.estructuraBD;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import ListDatos.JSelect;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import utiles.FechaMalException;
import utiles.JArchivo;
import utiles.JCadenas;
import utiles.JConversiones;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.tipos.Moneda;
import utiles.tipos.Moneda3Decimales;
import utiles.tipos.Porcentual;
import utiles.tipos.Porcentual3Decimales;

/**
 * Objeto campo de JFieldDefs, hay un JFieldDef por cada columna 
 */
public class JFieldDef implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = 33333332L;
    /**
     * Constantes de tipo actualizar campo en caso de nulo, no hacer nada
     */
    public static final int mclActualizarNada = -1;
    /**
     * Constantes de tipo actualizar campo en caso de nulo, act. ult. mas uno
     */
    public static final int mclActualizarUltMasUno = 0;
    /**
     * Constantes de tipo actualizar campo en caso de nulo, hacer la select moSelect
     */
    public static final int mclActualizarSelect = 1;
    /**
     * Constantes de tipo actualizar campo en caso de nulo, poner el valor msValorPorDefecto
     */
    public static final int mclActualizarValor = 2;
    
    private static final int mclTamanoIndeterminado=-100;
    private int mlTipo;
    private String msNombre = null;
    private String msCaption = null;
    private String msFormatoPresentacion = null;
    private String msFormatoEdicion = null;
    private String msValor = null;
    private boolean mbEsPrincipal;
    private int mlTamano = mclTamanoIndeterminado;
    private String msNombreTipo;
    private boolean mbNullable = true;
    private String msDescripcion;
    private int mlActualizarValorSiNulo = -1;
    private JSelect moSelect = null;
    private String msValorPorDefecto = null;
    private boolean mbEsEditable = true;
    private boolean mbEsCalculo = false;
    private String msTabla = null;
    private HashMap moAtributos;

    private transient List<IFieldDefChangeListener> moListeners;
    /**
     * Contructor
     *@param plTipo tipo del campo valores posibles:
     *    JListDatos.mclTipoBoolean,JListDatos.mclTipoCadena,
     *    JListDatos.mclTipoFecha, JListDatos.mclTipoNumero, JListDatos.mclTipoNumeroDoble
     *@param psNombre Nombre del campo
     *@param psCaption Caption del campo
     *@param pbEsPrincipal si es parte de la clave primaria
     */
    public JFieldDef(final int plTipo, final String psNombre, final String psCaption, final boolean pbEsPrincipal) {
        this(plTipo, psNombre, psCaption, pbEsPrincipal, mclTamanoIndeterminado, null, true, null);
    }

    /**
     * Contructor
     *@param plTipo tipo del campo valores posibles:
     *    JListDatos.mclTipoBoolean,JListDatos.mclTipoCadena,
     *    JListDatos.mclTipoFecha, JListDatos.mclTipoNumero, JListDatos.mclTipoNumeroDoble
     *@param psNombre Nombre del campo
     *@param psCaption Caption del campo
     *@param pbEsPrincipal si es parte de la clave primaria
     *@param poValor valor del campo
     */
    public JFieldDef(final int plTipo, final String psNombre, final String psCaption, final boolean pbEsPrincipal, final Object poValor) throws ECampoError {
        this(plTipo, psNombre, psCaption, pbEsPrincipal, mclTamanoIndeterminado, null, true, null);
        setValue(poValor);
    }
    /**
     * Contructor
     *@param psNombre Nombre del campo
     */
    public JFieldDef(final String psNombre) {
        this(JListDatos.mclTipoCadena, psNombre, psNombre, false, mclTamanoIndeterminado, null, true, null);
    }
    /**
     * Contructor
     *@param plTipo tipo del campo valores posibles:
     *    JListDatos.mclTipoBoolean,JListDatos.mclTipoCadena,
     *    JListDatos.mclTipoFecha, JListDatos.mclTipoNumero, JListDatos.mclTipoNumeroDoble
     *@param psNombre Nombre del campo
     */
    public JFieldDef(final int plTipo, final String psNombre) {
        this(plTipo, psNombre, psNombre, false, mclTamanoIndeterminado, null, true, null);
    }

    /**
     *Contructor
     *@param plTipo Tipo del campo valores posibles:
     *    JListDatos.mclTipoBoolean,JListDatos.mclTipoCadena,
     *    JListDatos.mclTipoFecha, JListDatos.mclTipoNumero, JListDatos.mclTipoNumeroDoble
     *@param psNombre Nombre del campo
     *@param psCaption Caption del campo
     *@param pbEsPrincipal Si es parte de la clave primaria
     *@param plTamano Tamano del campo
     */
    public JFieldDef(final int plTipo, final String psNombre, final String psCaption, final boolean pbEsPrincipal, final int plTamano) {
        this(plTipo, psNombre, psCaption, pbEsPrincipal, plTamano, null, true, null);
    }

    /**
     * Contructor
     * @param plTipo Tipo del campo valores posibles:
     *    JListDatos.mclTipoBoolean,JListDatos.mclTipoCadena,
     *    JListDatos.mclTipoFecha, JListDatos.mclTipoNumero, JListDatos.mclTipoNumeroDoble
     * @param psNombre Nombre del campo
     * @param psCaption Caption del campo
     * @param pbEsPrincipal Si es parte de la clave primaria
     * @param plTamano Tamano del campo
     * @param psNombreTipo
     * @param pbNullable
     * @param psDescripcion
     */
    public JFieldDef(final int plTipo, final String psNombre, final String psCaption, final boolean pbEsPrincipal, final int plTamano, final String psNombreTipo, final boolean pbNullable, final String psDescripcion) {
        mlTipo = plTipo;
        msNombre = psNombre;
        setCaption(psCaption);
        mlTamano = plTamano;
        msNombreTipo = psNombreTipo;
        mbNullable = pbNullable;
        msDescripcion = psDescripcion;
        setPrincipalSN(pbEsPrincipal);
        moListeners = new ArrayList<IFieldDefChangeListener>();
    }

    /**
     * Caption del campo
     * @param psCaption Caption a establecer
     */
    public void setCaption(final String psCaption) {
        if (psCaption == null) {
            msCaption = null;
        } else {
            if (msNombre != null && psCaption.equals(msNombre)) {
                msCaption = null;
            } else {
                msCaption = psCaption;
            }
        }
    }

    public boolean isEditable() {
        return mbEsEditable;
    }

    public void setEditable(boolean pbEsEditable) {
        mbEsEditable = pbEsEditable;
    }

    public boolean isCalculado() {
        return mbEsCalculo;
    }

    protected void setCalculado(boolean pbEsCalculo) {
        mbEsCalculo = pbEsCalculo;
    }

    /**
     * Devuelve el tipo real de la base de datos
     * @return Nombre del tipo
     */
    public String getNombreTipo() {
        return msNombreTipo;
    }

    /**
     * Devuelve si en la base de datos puede ser null
     * @return si puede ser nulo
     */
    public boolean getNullable() {
        return mbNullable && !getPrincipalSN();
    }

    /**
     * Devuelve la descripcion del campo de la base de datos
     * @return La descripcion
     */
    public String getDescripcion() {
        return msDescripcion;
    }

    /**
     * Caption del campo
     * @return El caption
     */
    public String getCaption() {
        String lsCaption = msCaption;
        if (lsCaption == null) {
            lsCaption = msNombre;
        }

        return lsCaption;
    }

    /**
     * Formato de visualizacion del campo
     * @param psFormato Formato a establecer
     */
    public void setFormatoPresentacion(final String psFormato) {
        msFormatoPresentacion = psFormato;
    }

    /**
     * Formato de visualizacion del campo
     * @return El formato
     */
    public String getFormatoPresentacion() {
        String lsFormato = msFormatoPresentacion;
        if (lsFormato == null) {
            // Hay que buscar el formato de visualizacion por defecto
        }

        return lsFormato;
    }

    /**
     * Formato de edicion del campo
     * @param psFormato Formato a establecer
     */
    public void setFormatoEdicion(final String psFormato) {
        msFormatoEdicion = psFormato;
    }

    /**
     * Formato de edicion del campo
     * @return El formato
     */
    public String getFormatoEdicion() {
        String lsFormato = msFormatoEdicion;
        if (lsFormato == null) {
            // Hay que buscar el formato de edicion por defecto
        }

        return lsFormato;
    }

    /**
     * Devuelve el tipo de actualizacion si nulo
     * @return El tipo de actualizacion
     */
    public int getActualizarValorSiNulo() {
        return mlActualizarValorSiNulo;
    }

    /**
     * Devuelve la select en caso de campo nulo
     * @return Select
     */
    public JSelect getSelect() {
        return moSelect;
    }

    /**
     * Devuelve el valor por defecto en caso de nulo
     * @return Valor por defecto
     */
    public String getValorPorDefecto() {
        return msValorPorDefecto;
    }

    /**
     * Establecemos el tipo de actualizacion si nulo
     * @param plValor Tipo de actualizacion
     */
    public void setActualizarValorSiNulo(final int plValor) {
        switch (plValor) {
            case mclActualizarUltMasUno:
            case mclActualizarSelect:
            case mclActualizarValor:
            case mclActualizarNada:
                break;
            default:
        }

        mlActualizarValorSiNulo = plValor;
    }

    /**
     * Establecemos la select si nulo
     * @param poValor Select
     */
    public void setSelect(final JSelect poValor) {
        moSelect = poValor;
    }

    /**
     * Establecemos el valor por defecto si nulo
     * @param psValor Valor por defecto
     */
    public void setValorPorDefecto(final String psValor) {
        mlActualizarValorSiNulo = mclActualizarValor;
        msValorPorDefecto = psValor;
    }

    /**
     * Devuelve si en la base de datos puede ser null
     * @return si puede ser nulo
     */
    public void setNullable(final boolean pbNullable) {
        mbNullable = pbNullable;
    }

    /**
     * Devuelve el nombre del campo
     * @return Nombre
     */
    public String getNombre() {
        return msNombre;
    }

    /**Establece el nombre del campo
     * @param psNombre*/
    public void setNombre(String psNombre) {
        msNombre = psNombre;
    }

    /**
     * Devuelve el tipo del campo
     * @return Tipo
     */
    public int getTipo() {
        return mlTipo;
    }

    public void setTipo(int plTipo) {
        mlTipo = plTipo;
    }

    /**
     * Devuelve si el campo es clave primaria
     * @return Si es principal
     */
    public boolean getPrincipalSN() {
        return mbEsPrincipal;
    }

    /**
     * Establece si el campo es clave primaria
     * @param pbValor
     */
    public void setPrincipalSN(final boolean pbValor) {
        mbEsPrincipal = pbValor;
        if (mbEsPrincipal) {
            mbNullable = false;
        }
    }

    public boolean isMemo() {
        return (((mlTamano <= 0) && (mlTamano != mclTamanoIndeterminado)) || (mlTamano > 3999));
    }

    /**
     * Devuelve el tamano del campo
     * @return Tamano
     */
    public int getTamano() {
        return mlTamano;
    }

    public void setTamano(int plTamano) {
        mlTamano = plTamano;
    }

    /**
     * Devuelve el tipo normal a partir del tipo SQL
     * @return El tipo
     * @param plTipo Tipo sql
     */
    public static int getTipoNormalDeTipoSQL(final int plTipo) {
        int lTipo;
        switch (plTipo) {
            case Types.DOUBLE:
            case Types.DECIMAL:
            case Types.FLOAT:
            case Types.NUMERIC:
            case Types.REAL:
                lTipo = JListDatos.mclTipoNumeroDoble;
                break;
            case Types.BIGINT:
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.TINYINT:
                lTipo = JListDatos.mclTipoNumero;
                break;
            case Types.BIT:
//            case Types.BOOLEAN:
            case 16:
                lTipo = JListDatos.mclTipoBoolean;
                break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                lTipo = JListDatos.mclTipoFecha;
                break;
            default:
                lTipo = JListDatos.mclTipoCadena;
        }
        return lTipo;
    }

    /**
     * Establece el tipo normal a partir del tipoSQL
     * @param plTipo Tipo sql
     */
    public void setTipoSQL(final int plTipo) {
        mlTipo = getTipoNormalDeTipoSQL(plTipo);
    }

    /**
     * Devuelve el tipo SQL, sirve para paso de parametros en actualizaciones de base de datos
     * @return Tipo sql
     */
    public int getTipoSQL() {
        int lTipo;
        switch (mlTipo) {
            case JListDatos.mclTipoNumero:
                lTipo = Types.INTEGER;
                break;
            case JListDatos.mclTipoNumeroDoble:
            case JListDatos.mclTipoMoneda3Decimales:
            case JListDatos.mclTipoMoneda:
            case JListDatos.mclTipoPorcentual3Decimales:
            case JListDatos.mclTipoPorcentual:
                lTipo = Types.DOUBLE;
                break;
            case JListDatos.mclTipoFecha:
                lTipo = Types.TIMESTAMP;
                break;
            case JListDatos.mclTipoBoolean:
                lTipo = Types.BIT;
                break;
            default:
                if (isMemo()) {
                    lTipo = Types.LONGVARCHAR;
                } else {
                    lTipo = Types.VARCHAR;
                }
        }
        return lTipo;
    }

    /**
     * Devuelve la clase segun el tipo, sirve para presentar datos en componentes como JTable
     * @return Clase segun el tipo
     */
    public Class getClase() {
        Class loClase;
        switch (mlTipo) {
            case JListDatos.mclTipoNumero:
                loClase = Integer.class;
                break;
            case JListDatos.mclTipoNumeroDoble:
                loClase = Double.class;
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                loClase = Moneda3Decimales.class;
                break;
            case JListDatos.mclTipoMoneda:
                loClase = Moneda.class;
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                loClase = Porcentual3Decimales.class;
                break;
            case JListDatos.mclTipoPorcentual:
                loClase = Porcentual.class;
                break;
            case JListDatos.mclTipoFecha:
                loClase = JDateEdu.class;
                break;
            case JListDatos.mclTipoBoolean:
                loClase = Boolean.class;
                break;
            case JListDatos.mclTipoInputStream:
                loClase = File.class;
                break;                
            default:
                loClase = String.class;

        }
        return loClase;
    }

    /**
     * Devuelve la clase segun el tipo, sirve para presentar datos en componentes como JTable
     * @return Clase segun el tipo
     */
    public Class getClaseStandar() {
        Class loClase = getClase();
        switch (mlTipo) {
            case JListDatos.mclTipoFecha:
                loClase = Date.class;
                break;
            case JListDatos.mclTipoMoneda3Decimales:
            case JListDatos.mclTipoMoneda:
            case JListDatos.mclTipoPorcentual3Decimales:
            case JListDatos.mclTipoPorcentual:
                loClase = Double.class;
                break;
        }
        return loClase;
    }
    /**
     * Devuelve si el campo esta vacio, es decir , si hay valor
     * @return Si es vacio
     */
    public boolean isVacio() {
        boolean lbVacio = true;
        if (msValor != null) {
            lbVacio = msValor.equals("");
        }
        return lbVacio;
    }

    /**
     * Establece un valor
     * @param poObject El objeto
     * @throws ECampoError Error
     */
    public void setValue(final Object poObject) throws ECampoError {
        if (poObject == null) {
            msValor = null;
        } else {
            switch (mlTipo) {
                case JListDatos.mclTipoBoolean:
                    if (poObject.getClass() == Boolean.class) {
                        msValor = (((Boolean) poObject).booleanValue() ? JListDatos.mcsTrue : JListDatos.mcsFalse);
                    } else {
                        msValor = poObject.toString().trim().toUpperCase();
                        if (msValor.equals("TRUE") || msValor.equals("-1") || msValor.equals("1")
                                || msValor.equals("S")  || msValor.equals("SI")) {
                            msValor = JListDatos.mcsTrue;
                        } else {
                            if (msValor.equals("FALSE") || msValor.equals("0") 
                                    || msValor.equals("N") | msValor.equals("NO")) {
                                msValor = JListDatos.mcsFalse;
                            }
                        }
                    }   break;
                case JListDatos.mclTipoInputStream:
                    if(poObject instanceof InputStream){
                        try {
                            byte[] array = JArchivo.toByteArray((InputStream)poObject);
                            msValor =new String(new org.apache.commons.codec.binary.Base64().encodeBase64(array));
                        } catch (IOException ex) {
                            throw new ECampoError(this, " Error al codificar inputStrem ");
                        }
                    }else {
                        msValor = poObject.toString();
                    }   
                    break;
                case JListDatos.mclTipoNumeroDoble:
                case JListDatos.mclTipoNumero:
                case JListDatos.mclTipoMoneda3Decimales:
                case JListDatos.mclTipoMoneda:
                case JListDatos.mclTipoPorcentual3Decimales:
                case JListDatos.mclTipoPorcentual:   
                    if (poObject.getClass() == Boolean.class) {
                        if(((Boolean)poObject).booleanValue()){
                            msValor="1";
                        } else {
                            msValor="0";
                        }
                    }else{
                        msValor=poObject.toString().replace(',', '.');
                    }
                    break;
                default:
                    if ((poObject.getClass() == String.class)) {
                        msValor = (String) poObject;
                    } else {
                        msValor = poObject.toString();
                    }   break;
            }
            if (msValor.compareTo("") == 0) {
                msValor = null;
            } else {
                //comprobaciones
                if ((mlTipo == JListDatos.mclTipoNumeroDoble)
                        || (mlTipo == JListDatos.mclTipoNumero)
                        || (mlTipo == JListDatos.mclTipoMoneda3Decimales)
                        || (mlTipo == JListDatos.mclTipoMoneda)
                        || (mlTipo == JListDatos.mclTipoPorcentual3Decimales)
                        || (mlTipo == JListDatos.mclTipoPorcentual)) {
                    //se comprueba si es numero
                    try {
                        Double.valueOf(msValor);
                    } catch (Exception e) {
                        throw new ECampoError(this, " valor: " + msValor + " no es numérico");
                    }
                }
                if (mlTipo == JListDatos.mclTipoFecha) {
                    if (!JDateEdu.isDate(msValor)) {
                        throw new ECampoError(this, " valor: " + msValor + " no es fecha");
                    }
                }
                if (mlTipo == JListDatos.mclTipoBoolean) {
                    if (!((msValor.equalsIgnoreCase(JListDatos.mcsFalse))
                            || (msValor.equalsIgnoreCase(JListDatos.mcsTrue)))) {
                        throw new ECampoError(this, " valor: " + msValor + " no es booleano");
                    }
                }
            }
        }
        doChange();
    }

    /**
     * Establece un valor entero
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final int valor) throws ECampoError {
        setValue(new Integer(valor));
    }

    /**
     * Establece un valor double
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final double valor) throws ECampoError {
        setValue(new Double(valor));
    }

    /**
     * Establece un valor byte
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final byte valor) throws ECampoError {
        setValue(new Byte(valor));
    }

    /**
     * Establece un valor char
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final char valor) throws ECampoError {
        setValue(new Character(valor));
    }

    /**
     * Establece un valor long
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final long valor) throws ECampoError {
        setValue(new Long(valor));
    }

    /**
     * Establece un valor short
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final short valor) throws ECampoError {
        setValue(new Short(valor));
    }

    /**
     * Establece un valor float
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final float valor) throws ECampoError {
        setValue(new Float(valor));
    }

    /**
     * Establece un valor boolean
     * @param valor Valor
     * @throws ECampoError Error
     */
    public void setValue(final boolean valor) throws ECampoError {
        setValue((valor ? Boolean.TRUE : Boolean.FALSE));
    }

    /**
     * Devuelve el valor, como lo requieren los parametros de base de datos
     * @return Objeto para base de datos
     */
    public Object getValueSQL() {
        Object loValor = null;
        if (!isVacio()) {
            switch (mlTipo) {
                case JListDatos.mclTipoFecha:
                    loValor = ((JDateEdu) getValue()).moDateSQL();
                    break;
                case JListDatos.mclTipoCadena:
                    String lsCadena = getString();
                    if ((mlTamano > 0) && (lsCadena.length() > mlTamano)) {
                        lsCadena = lsCadena.substring(0, mlTamano);
                    }
                    loValor = lsCadena;
                    break;
                default:
                    loValor = getValue();
            }
        }
        return loValor;

    }

    /**
     * Devuelve el valor
     * @return Objeto valor
     */
    public Object getValue() {
        Object loObject;
        try {
            switch (mlTipo) {
                case JListDatos.mclTipoNumero:
                    loObject = getIntegerConNull();
                    break;
                case JListDatos.mclTipoNumeroDoble:
                case JListDatos.mclTipoMoneda3Decimales:
                case JListDatos.mclTipoMoneda:
                case JListDatos.mclTipoPorcentual3Decimales:
                case JListDatos.mclTipoPorcentual:
                    loObject = getDoubleConNull();
                    break;
                case JListDatos.mclTipoFecha:
                    loObject = getDateEduConNull();
                    break;
                case JListDatos.mclTipoBoolean:
                    loObject = getBooleanConNull();
                    break;                    
                case JListDatos.mclTipoInputStream:
                    loObject = getInputStreamConNull();
                    break;
                default:
                    loObject = getStringConNull();
            }
        } catch (Throwable e) {
            JDepuracion.anadirTexto(this.getClass().getName(), e);
            loObject = null;
        }
        return loObject;
    }

    /**
     * Devuelve un entero
     * @return Valor
     */
    public int getInteger() {
        return (int) JConversiones.cdbl(msValor);
    }

    /**
     * Devuelve un long
     * @return Valor
     */
    public long getLong() {
        long lLong;
        try {
            lLong = (long) Long.parseLong(msValor);
        } catch (Throwable e) {
            lLong = 0;
        }
        return lLong;
    }

    /**
     * Devuelve un entero Objeto
     * @return Valor
     */
    public Integer getIntegerConNull() {
        Integer loValor = null;
        if (!isVacio()) {
            loValor = new Integer(getInteger());
        }
        return loValor;
    }

    /**
     * Devuelve un double
     * @return Valor
     */
    public double getDouble() {
        return JConversiones.cdbl(msValor);
    }

    /**
     * Devuelve un double Objeto
     * @return Valor
     */
    public Double getDoubleConNull() {
        Double loValor = null;
        try {
            loValor = Double.valueOf(msValor.replace(',','.'));
        } catch (Throwable e) {
        }
        return loValor;
    }

    /**
     * Devuelve un String, si el campo es vacio devuelve ""
     * @return Valor
     */
    public String getString() {
        String lsValor = msValor;
        if (lsValor == null) {
            lsValor = "";
        }
        return lsValor;
    }

    /**
     * Devuelve un String, si el campo es vacio devuelve null
     * @return Valor
     */
    public String getStringConNull() {
        return msValor;
    }

    /**
     * Devuelve un booleano
     * @return Valor
     */
    public boolean getBoolean() {
        boolean lbValor = false;
        if (!isVacio()) {
            lbValor = msValor.equals(JListDatos.mcsTrue)
                    || msValor.equalsIgnoreCase("true")
                    || msValor.equals("-1")
                    || msValor.equalsIgnoreCase("S")
                    || msValor.equalsIgnoreCase("SI");
        }
        return lbValor;
    }

    /**
     * Devuelve un booleano Objeto
     * @return Valor
     */
    public Boolean getBooleanConNull() {
        Boolean loValor = null;
        if (!isVacio()) {
            loValor = (getBoolean() ? Boolean.TRUE : Boolean.FALSE);
        }
        return loValor;
    }

    /**
     * Devuelve un Objeto Fecha
     * @return Valor
     * @throws FechaMalException Error
     */
    public JDateEdu getDateEdu() throws FechaMalException {
        JDateEdu loDate;
        if (isVacio()) {
            loDate = (new JDateEdu(0.0));
        } else {
            loDate = (new JDateEdu(msValor));
        }
        return loDate;
    }

    /**
     * Devuelve un Objeto Fecha, si el campo es vacio devuelve null
     * @return Valor
     * @throws FechaMalException Error
     */
    public JDateEdu getDateEduConNull() throws FechaMalException {
        JDateEdu loDate = null;
        if (!isVacio()) {
            loDate = (new JDateEdu(msValor));
        }
        return loDate;
    }
    
    public InputStream getInputStreamConNull() throws IOException{
        InputStream input = null;
        if(!isVacio()){
            byte [] bytesInput = new org.apache.commons.codec.binary.Base64().decodeBase64(msValor.getBytes());
            input = new ByteArrayInputStream(bytesInput);
         
        }
        
        return input;
    }
        
    //al heredar de clone debe devolver object

    /**
     * Devuelve una clonacion de este objeto
     * @return Objeto clonado
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        JFieldDef loField = (JFieldDef) super.clone();
//        loField.setSelect(moSelect);
//        loField.setValorPorDefecto(msValorPorDefecto);
//        loField.setCalculado(mbEsCalculo);
//        loField.setEditable(mbEsEditable);
//        loField.setTabla(msTabla);
//        loField.setActualizarValorSiNulo(mlActualizarValorSiNulo);
//
//        //no es posible que si se han cargado los datos no se pueda en el clonado
//        try {
//            loField.setValue(msValor);
//        } catch (Exception e) {
//        }
        return loField;
    }
    //al heredar de clone debe devolver object, por eso esta este con mayusculas

    /**
     * Devuelve una clonacion de este objeto, con tipo JFileDef
     * @return Objeto clonado
     * @throws java.lang.CloneNotSupportedException
     */
    public JFieldDef Clone() throws CloneNotSupportedException {
        return (JFieldDef) clone();
    }

    @Override
    public String toString() {
        String lsResult = "";
        if (!isVacio()) {
            //primero transformamos en Objeto y luego toString
            //(esto se origino para un parche para las fechas,
            //q incluyen la hora:minuto:segundo aunque sea 0:0:0)
            lsResult = getValue().toString();
        }
        return lsResult;
    }

    /**
     * @return the msTabla
     */
    public String getTabla() {
        return msTabla;
    }

    /**
     * @param psTabla the msTabla to set
     */
    public void setTabla(String psTabla) {
        msTabla = psTabla;
    }

    /**
     * Comprobamos que la estruc. campo pasada por parametro sea igual a esta estruc. de campo
     * @param poCampo estruc. de campos a comparar
     * @return si son iguales
     */
    public boolean isEstructuraIgual(JFieldDef poCampo) {
        return mlTipo == poCampo.mlTipo
                && JCadenas.isEquals(msNombre, poCampo.msNombre)
                && //            JCadenas.isEquals(msCaption , poCampo.msCaption) && 
                //            JCadenas.isEquals(msValor , poCampo.msValor) && 
                mbEsPrincipal == poCampo.mbEsPrincipal
                && mlTamano == poCampo.mlTamano
                && JCadenas.isEquals(msNombreTipo, poCampo.msNombreTipo)
                && mbNullable == poCampo.mbNullable
                && //            JCadenas.isEquals(msDescripcion , poCampo.msDescripcion) && 
                mlActualizarValorSiNulo == poCampo.mlActualizarValorSiNulo
                && JCadenas.isEquals(msValorPorDefecto, poCampo.msValorPorDefecto)
                && mbEsEditable == poCampo.mbEsEditable
                && mbEsCalculo == poCampo.mbEsCalculo
                && JCadenas.isEquals(msTabla, poCampo.msTabla)
                && (moSelect == null && poCampo.moSelect == null
                || moSelect.toString().equals(poCampo.moSelect.toString()));

    }

    public boolean isNumerico() {
        return mlTipo==JListDatos.mclTipoMoneda
                || mlTipo==JListDatos.mclTipoMoneda3Decimales
                || mlTipo==JListDatos.mclTipoNumero
                || mlTipo==JListDatos.mclTipoNumeroDoble
                || mlTipo==JListDatos.mclTipoPorcentual
                || mlTipo==JListDatos.mclTipoPorcentual3Decimales;
   
    }

    /**
     * @return the moAtributos
     */
    public synchronized HashMap getAtributos() {
        if(moAtributos==null){
            moAtributos=new HashMap();
        }
        return moAtributos;
    }

    /**
     * @param moAtributos the moAtributos to set
     */
    public void setAtributos(HashMap moAtributos) {
        this.moAtributos = moAtributos;
    }
    public void doChange(){
        for(IFieldDefChangeListener loC : getListenersChange()){
            loC.change(this);
        }
    }
    public void addListenerChange(IFieldDefChangeListener poL){
        getListenersChange().add(poL);
    }
    
    public void removeListenerChange(IFieldDefChangeListener poL){
        getListenersChange().remove(poL);
    }
    public synchronized List<IFieldDefChangeListener> getListenersChange(){
        if(moListeners==null){
            moListeners = new ArrayList<IFieldDefChangeListener>();
        }
        return moListeners;
    }
}
