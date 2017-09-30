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
 * Indica un elemento del from (tabla simple o union de una tabla con otra union/tabla anterior)
 */
public final class JSelectFromParte implements Serializable{
    private static final long serialVersionUID = 33333318L;
  /** where procesado*/  
  private String msFrom = null;
    private String msWhere = null;
  /**
   * Contructor
   * @param psFrom from
   * @param psWhere where
   */
  public JSelectFromParte(final String psFrom,final String psWhere ) {
    msFrom=psFrom;
    msWhere=psWhere;
  }

    /**
     * @return the msFrom
     */
    public String getFrom() {
        return msFrom;
    }

    /**
     * @param msFrom the msFrom to set
     */
    public void setFrom(String msFrom) {
        this.msFrom = msFrom;
    }

    /**
     * @return the msWhere
     */
    public String getWhere() {
        return msWhere;
    }

    /**
     * @param msWhere the msWhere to set
     */
    public void setWhere(String msWhere) {
        this.msWhere = msWhere;
    }

}
