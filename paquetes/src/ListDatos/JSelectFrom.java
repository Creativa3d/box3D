/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003-2005</p>
 * <p>Company: </p>
 * @author sin atribuir
 * @version 1.0
 */
package ListDatos;

import utiles.*;
import java.io.Serializable;

/**
 * Objeto from de la select
 */
public final class JSelectFrom implements Serializable, Cloneable {

    private static final long serialVersionUID = 33333317L;
//    /** tabla inicial*/
//    public String msTabla = null;
//    public String msTablaAlias = null;
    private IListaElementos moTablasUnion = new JListaElementos();

    public JSelectFrom() {
    }
    /**
     * Constructor
     * @param psTabla tabla inicial
     */
    public JSelectFrom(final String psTabla) {
        if(psTabla!= null && !psTabla.equals("")){
            moTablasUnion.add(new JSelectUnionTablas(psTabla));
        }
    }

    public JSelectFrom(final String psTabla, final String psTablaAlias) {
        if(psTabla!= null && !psTabla.equals("")){
            moTablasUnion.add(new JSelectUnionTablas(psTabla, psTablaAlias));
        }
    }
    public JSelectUnionTablas getTablaUnion(int i ){
        return (JSelectUnionTablas) moTablasUnion.get(i);
    }
    public IListaElementos getTablasUnion() {
        return moTablasUnion;
    }

    /**
     * Anade una union de tablas
     * @param poUnion union de tablas
     */
    public void addTabla(final JSelectUnionTablas poUnion) {
        if (moTablasUnion == null) {
            moTablasUnion = new JListaElementos();
        }
        moTablasUnion.add(poUnion);
    }

    /**
     * devulve el from en formato SQL en funcion del poSelect
     * @param poSelect motor de sql segun tipo de base de datos
     * @return objeto form parte
     */
    public JSelectFromParte msSQL(final ISelectMotor poSelect) {
        JSelectFromParte loFrom = new JSelectFromParte(
                poSelect.msTabla(
                    ((JSelectUnionTablas) moTablasUnion.get(0)).getTabla2(), ((JSelectUnionTablas) moTablasUnion.get(0)).getTabla2Alias()),
                null);
        for(int i = 1 ; i < moTablasUnion.size(); i++){
            loFrom = poSelect.msFromUnion(
                        loFrom,
                        (JSelectUnionTablas) moTablasUnion.get(i));
        }
        return loFrom;
    }

    public String getNombreTablaDeAlias(String psAlias) {
        String lsResult = null;
        for (int i = 0; i < moTablasUnion.size(); i++) {
            JSelectUnionTablas loUnion = (JSelectUnionTablas) moTablasUnion.get(i);
            if ((loUnion.getTabla2Alias() != null && loUnion.getTabla2Alias().equalsIgnoreCase(psAlias)) ||
                    (loUnion.getTabla2() != null && loUnion.getTabla2().equalsIgnoreCase(psAlias))) {
                lsResult = loUnion.getTabla2();
            }
        }

        return lsResult;
    }

    public boolean estaLaTablaoAliasSN(String psTablaoAlias){
        boolean lbResult = false;
        for (int i = 0; i < moTablasUnion.size(); i++) {
            JSelectUnionTablas loUnion = (JSelectUnionTablas) moTablasUnion.get(i);
            if ((loUnion.getTabla2Alias() != null && loUnion.getTabla2Alias().equalsIgnoreCase(psTablaoAlias)) ||
                (loUnion.getTabla2() != null && loUnion.getTabla2().equalsIgnoreCase(psTablaoAlias))) {
                lbResult = true;
            }
        }
        return lbResult;
    }

    public Object clone() throws CloneNotSupportedException {
        JSelectFrom loFrom = new JSelectFrom();
        for (int i = 0; i < moTablasUnion.size(); i++) {
            JSelectUnionTablas loUnion = (JSelectUnionTablas) moTablasUnion.get(i);
            loFrom.addTabla((JSelectUnionTablas) loUnion.clone());
        }
        return loFrom;
    }
}