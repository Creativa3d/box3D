/*
 * ISelect.java
 *
 * Created on 16 de julio de 2002, 20:14
 */
package ListDatos;

import ListDatos.estructuraBD.*;
import java.io.Serializable;

import utiles.*;
import java.sql.PreparedStatement;

/**
 * Interfaz para generar SQL, todas las SQL se crean a traves de este interfaz, si cambia de base de datos, solo hay que implementar este interfaz 
 */
public interface ISelectMotor extends  Serializable {

    /**
     * Creacion de campo 
     * @param plFuncion Funcion sql
     * @param psTabla Tabla del campo
     * @param psCampo Campo 
     * @return Sql del campo
     */
    public String msCampo(int plFuncion, String psTabla, String psCampo);

    /**
     * Union de cond. And
     * @param psCond1 Sql cond1
     * @param psCond2 Sql cond2
     * @return Sql union
     */
    public String msAnd(String psCond1, String psCond2);

    /**
     * Union de cond. or
     * @param psCond1 Sql cond1
     * @param psCond2 Sql cond2
     * @return Sql union
     */
    public String msOr(String psCond1, String psCond2);
    /**
     * Union de cond. or
     * @param psCond1 Sql cond1
     * @return Sql union
     */
    public String msNot(String psCond1);

    /**
     * Devuelve una Cond.
     * @param psCampo Campo
     * @param plCond Comparacion
     * @param psValor Valor
     * @param psCampo2 Campo 2 si este relleno se ignora psValor
     * @param plTipo Tipo del campo
     * @return Sql de la cond.
     */
    public String msCondicion(String psCampo, int plCond, String psValor, String psCampo2, int plTipo);

    /**
     * Devuelve la union del from
     * @param poParte1 Parte anterior del from
     * @param poUnion Parte siguiente
     * @return JSelectFromParte Union de ambas
     */
    public JSelectFromParte msFromUnion(JSelectFromParte poParte1, JSelectUnionTablas poUnion);

    /**
     * Devuelve el nombre de la tabla formateada
     * @param psTabla tabla
     * @param psTablaAlias el alias de la tabla
     * @return tabla sql
     */
    public String msTabla(String psTabla, String psTablaAlias);

    /**
     * DDevuelve una lista de campos en formato SQL
     * @param plTipo Propiedad de las lista de campos(Distinct)
     * @param poCampos Lista de campos
     * @return Lista de campos sql
     */
    public String msListaCampos(int plTipo, JListaElementos poCampos);

    /**
     * Devuelve la lista de campos del group
     * @param poCampos Lista de campos
     * @return Lista de campos sql
     */
    public String msListaCamposGroup(JListaElementos poCampos);

    /**
     * Devuelve la lista de campos del order
     * @param poCampos Lista de campos
     * @return Lista de campos sql
     */
    public String msListaCamposOrder(JListaElementos poCampos);

    /**
     * Devuelve consulta de actualizacion
     * @param psTabla Tabla
     * @param poCampos Lista de campos, con sus valores y tipos
     * @param plTipoModif Tipo de modif.
     * @return Sql act.
     */
    public String msActualizacion(String psTabla, JFieldDefs poCampos, int plTipoModif);

    /**
     * Devuelve la select montada
     * @param psCampos Campos select
     * @param psFrom From
     * @param psWhere Where
     * @param psGroup Group
     * @param psHaving Having
     * @param psOrder Order
     * @return Sql de la select
     */
    public String msSelect(String psCampos, String psFrom, String psWhere, String psGroup, String psHaving, String psOrder);

    /**
     * Pasamos los parametros
     * OJO: no me gusta q aqui este el parametro PreparedStatement, ahi q quitarlo
     * @param psTabla Tabla
     * @param poCampos Lista de campos
     * @param plTipoModif Tipo modif
     * @param loSent Sentencia sql
     * @throws SQLException Error
     */
    public void pasarParametros(String psTabla, JFieldDefs poCampos, int plTipoModif, PreparedStatement loSent) throws java.sql.SQLException;

    public String getTabla(int plTipo, JTableDef poTabla) throws ExceptionNoImplementado;

    public String getCampo(int plTipo, JFieldDef poCampo, JTableDef poTabla) throws ExceptionNoImplementado;

    public String getIndice(int plTipo, JIndiceDef poIndice, JTableDef poTabla) throws ExceptionNoImplementado;

    public String getRelacion(int plTipo, JRelacionesDef poRelacion, JTableDef poTabla1, JTableDef poTabla2) throws ExceptionNoImplementado;
}
