/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003-2005</p>
 * <p>Company: </p>
 * @author sin atribuir 
 * @version 1.0
 */
package ListDatos;

import java.io.Serializable;

/**
 * Union de dos tablas del from
 */
public final class JSelectUnionTablas implements Serializable , Cloneable {
    private static final long serialVersionUID = 33333320L;
  /** Constante se anaden las tablas al from separados por comas */
  public static final int mclNada = -1;
  /** Constante se anaden las tablas al from en formato left join */
  public static final int mclLeft = 0;
  /** Constante se anaden las tablas al from en formato inner join */
  public static final int mclInner = 1;
  /** Constante se anaden las tablas al from en formato right join */
  public static final int mclRight = 2;

  /** Campos de la tabla 1, por los que se unen */
  private String[] masCampos1 = null;;
  /** alias de la tabla 2*/
  private String[] masCampos2 = null;
    private int mlTipo = -1;
    private String msTablaPrefijoCampos1 = null;
    private String msTabla2 = null;
    private String msTabla2Alias = null;

  /**
   * Constructor
   * @param plTipo tipo de union
   * @param psTabla tabla 1
   * @param psTabla2 tabla 2
   * @param pasCampos1 lista de campos de tabla 1
   * @param pasCampos2 lista de campos de tabla 2
   */
  public JSelectUnionTablas(final int plTipo, final String psTablaPrefijoCampos1, final String psTabla2 ,final String[] pasCampos1, final String[] pasCampos2) {
    this(plTipo, psTablaPrefijoCampos1, psTabla2, null, pasCampos1, pasCampos2);
  }
  /**
   * Constructor
   * @param plTipo tipo de union
   * @param psTabla tabla 1
   * @param psTabla2 tabla 2
   * @param psTabla2Alias Alias tabla 2
   * @param pasCampos1 lista de campos de tabla 1
   * @param pasCampos2 lista de campos de tabla 2
   */
  public JSelectUnionTablas(final int plTipo, final String psTablaPrefijoCampos1, final String psTabla2 ,final String psTabla2Alias ,final String[] pasCampos1, final String[] pasCampos2) {
    mlTipo = plTipo;
    msTablaPrefijoCampos1 = psTablaPrefijoCampos1;
    msTabla2 = psTabla2;
    msTabla2Alias = psTabla2Alias;

    if(pasCampos1!=null){
        masCampos1 = new String[pasCampos1.length];
        System.arraycopy(pasCampos1, 0,masCampos1, 0, masCampos1.length);
    }
    if(pasCampos2!=null){
        masCampos2 = new String[pasCampos2.length];
        System.arraycopy(pasCampos2, 0,masCampos2, 0, masCampos2.length);
    }

    if(msTabla2Alias!=null && msTabla2!=null &&
       msTabla2Alias.equalsIgnoreCase(msTabla2)
            ){
        msTabla2Alias=null;
    }

  }
  /**
   * Constructor
   * @param psTabla2 una sola tabla
   */
  public JSelectUnionTablas(final String psTabla2) {
    msTabla2 = psTabla2;
  }
  /**
   * Constructor
   * @param psTabla2 una sola tabla
   * @param psTabla2Alias Alias una sola tabla
   */
  public JSelectUnionTablas(final String psTabla2, final String psTabla2Alias) {
    msTabla2 = psTabla2;
    msTabla2Alias=psTabla2Alias;
  }
  public Object clone() throws CloneNotSupportedException {
        return new JSelectUnionTablas(getTipo(), getTablaPrefijoCampos1(), getTabla2(), getTabla2Alias(), getCampos1(), getCampos2());
  }

    /**
     * @return the masCampos1
     */
    public String[] getCampos1() {
        return masCampos1;
    }

    /**
     * @param masCampos1 the masCampos1 to set
     */
    public void setCampos1(String[] masCampos1) {
        this.masCampos1 = masCampos1;
    }

    /**
     * @return the masCampos2
     */
    public String[] getCampos2() {
        return masCampos2;
    }

    /**
     * @param masCampos2 the masCampos2 to set
     */
    public void setCampos2(String[] masCampos2) {
        this.masCampos2 = masCampos2;
    }

    /**
     * @return the mlTipo
     */
    public int getTipo() {
        return mlTipo;
    }

    /**
     * @param mlTipo the mlTipo to set
     */
    public void setTipo(int mlTipo) {
        this.mlTipo = mlTipo;
    }

    /**
     * @return the msTablaPrefijoCampos1
     */
    public String getTablaPrefijoCampos1() {
        return msTablaPrefijoCampos1;
    }

    /**
     * @param msTablaPrefijoCampos1 the msTablaPrefijoCampos1 to set
     */
    public void setTablaPrefijoCampos1(String msTablaPrefijoCampos1) {
        this.msTablaPrefijoCampos1 = msTablaPrefijoCampos1;
    }

    /**
     * @return the msTabla2
     */
    public String getTabla2() {
        return msTabla2;
    }

    /**
     * @param msTabla2 the msTabla2 to set
     */
    public void setTabla2(String msTabla2) {
        this.msTabla2 = msTabla2;
    }

    /**
     * @return the msTabla2Alias
     */
    public String getTabla2Alias() {
        return msTabla2Alias;
    }

    /**
     * @param msTabla2Alias the msTabla2Alias to set
     */
    public void setTabla2Alias(String msTabla2Alias) {
        this.msTabla2Alias = msTabla2Alias;
    }
}