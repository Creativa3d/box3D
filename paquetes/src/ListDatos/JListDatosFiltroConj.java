package ListDatos;

import ListDatos.estructuraBD.JFieldDefs;
import java.util.Iterator;
import utiles.*;

/**
 * Objeto que contiene las condiciones para filtrar un JListDatos
 */
public final class JListDatosFiltroConj implements IListDatosFiltro {

    private static final long serialVersionUID = 3333338L;
    /**
     * constante AND
     */
    public static final int mclAND = 0;
    /**
     * constante OR
     */
    public static final int mclOR = 1;
    private final JListaElementos moList = new JListaElementos();
    private final JListaElementos moListUniones = new JListaElementos();

//  public JListDatosFiltroConj(){
//      super();
//  }
    /**
     * Anade una condicion elemental OR
     *
     * @return Elemento condicion creado
     * @param palCampos lista de campos
     * @param pasValores lista de valores
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElem addCondicionOR(
            final int plCompara,
            final int[] palCampos, final String[] pasValores) {
        return addCondicion(JListDatosFiltroConj.mclOR, plCompara, palCampos, pasValores);
    }

    /**
     * Anade una condicion elemental OR
     *
     * @return Elemento condicion creado
     * @param plCampo indice campo
     * @param psValor valor
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElem addCondicionOR(
            final int plCompara,
            final int plCampo, final String psValor) {
        return addCondicion(JListDatosFiltroConj.mclOR, plCompara, plCampo, psValor);
    }

    /**
     * Anade una condicion elemental AND
     *
     * @return Elemento condicion creado
     * @param palCampos lista de campos
     * @param pasValores lista de valores
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElem addCondicionAND(
            final int plCompara,
            final int[] palCampos, final String[] pasValores) {
        return addCondicion(JListDatosFiltroConj.mclAND, plCompara, palCampos, pasValores);
    }

    /**
     * Anade una condicion elemental AND
     *
     * @return Elemento condicion creado
     * @param plCampo indice campo
     * @param psValor valor
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElem addCondicionAND(
            final int plCompara,
            final int plCampo, final String psValor) {
        return addCondicion(JListDatosFiltroConj.mclAND, plCompara, plCampo, psValor);
    }

    /**
     * Anade una condicion elemental AND
     *
     * @return Elemento condicion creado
     * @param plCampo indice campo
     * @param plCampo2 indice campo2
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElemCampos addCondicionAND(
            final int plCompara,
            final int plCampo, final int plCampo2) {
        JListDatosFiltroElemCampos lo = new JListDatosFiltroElemCampos(plCompara, plCampo, plCampo2);
        addCondicion(JListDatosFiltroConj.mclAND, lo);
        return lo;
    }

    /**
     * Anade una condicion elemental OR
     *
     * @return Elemento condicion creado
     * @param plCampo indice campo
     * @param plCampo2 indice campo2
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElemCampos addCondicionOR(
            final int plCompara,
            final int plCampo, final int plCampo2) {
        JListDatosFiltroElemCampos lo = new JListDatosFiltroElemCampos(plCompara, plCampo, plCampo2);
        addCondicion(JListDatosFiltroConj.mclOR, lo);
        return lo;
    }

    /**
     * Anade una condicion elemental
     *
     * @return Elemento condicion creado
     * @param palCampos lista de campos
     * @param pasValores lista de valores
     * @param plAND_OR tipo union
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElem addCondicion(
            final int plAND_OR, final int plCompara,
            final int[] palCampos, final String[] pasValores) {
        JListDatosFiltroElem loCond = new JListDatosFiltroElem(plCompara, palCampos, pasValores);
        moList.add(loCond);
        if (moList.size() > 0) {
            moListUniones.add(new Integer(plAND_OR));
        } else {
            moListUniones.add(new Integer(mclAND));
        }
        return loCond;
    }

    /**
     * Anade una condicion elemental
     *
     * @return Elemento condicion creado
     * @param plCampo Indice del campo
     * @param psValor Valor
     * @param plAND_OR Tipo union
     * @param plCompara Tipo comparacion
     */
    public JListDatosFiltroElem addCondicion(
            final int plAND_OR, final int plCompara,
            final int plCampo, final String psValor) {
        return addCondicion(plAND_OR, plCompara, new int[]{plCampo}, new String[]{psValor});
    }

    /**
     * Anade una condicion elemental SQL
     *
     * @return Elemento condicion creado
     * @param pasCampos lista de nombres de campos
     * @param pasValores lista de valores de campos
     * @param palTipos lista de tipos de campos
     * @param plAND_OR tipo union tipo de union
     * @param plCompara tipo comparacion
     */
    public JListDatosFiltroElem addCondicionSQL(
            final int plAND_OR, final int plCompara,
            final String[] pasCampos, final String[] pasValores,
            final int[] palTipos) {
        JListDatosFiltroElem loCond = new JListDatosFiltroElem(plCompara, pasCampos, pasValores, palTipos);
        moList.add(loCond);
        if (moList.size() > 0) {
            moListUniones.add(new Integer(plAND_OR));
        } else {
            moListUniones.add(new Integer(mclAND));
        }
        return loCond;
    }

    /**
     * Anade una condicion objeto(coleccion o elemento)
     *
     * @param plAND_OR tipo union
     * @param poCond objeto condicion
     */
    public JListDatosFiltroConj addCondicion(final int plAND_OR, IListDatosFiltro poCond) {
        moList.add(poCond);
        if (moList.size() > 0) {
            moListUniones.add(new Integer(plAND_OR));
        } else {
            moListUniones.add(new Integer(mclAND));
        }
        return this;
    }

    /**
     * Anade una condicion objeto(coleccion o elemento) AND
     *
     * @param poCond objeto condicion
     */
    public JListDatosFiltroConj addCondicionAND(IListDatosFiltro poCond) {
        return addCondicion(mclAND, poCond);
    }
    /**
     * Anade una condicion objeto(coleccion o elemento) AND NOT (poCond)
     *
     * @param poCond objeto condicion
     */
    public JListDatosFiltroConj addCondicionANDNOT(IListDatosFiltro poCond) {
        return addCondicionAND(new JListDatosFiltroNOT(poCond));
    }

    /**
     * Anade una condicion objeto(coleccion o elemento) OR
     *
     * @param poCond objeto condicion
     */
    public JListDatosFiltroConj addCondicionOR(IListDatosFiltro poCond) {
        return addCondicion(mclOR, poCond);
    }

    /**
     * Anade una condicion objeto(coleccion o elemento) AND
     *
     * @param poCond objeto condicion
     */
    public JListDatosFiltroConj addCondicion(IListDatosFiltro poCond) {
        return addCondicion(mclAND, poCond);
    }

    /**
     * Borra las condiciones idem Clear
     */
    public void clear() {
        moList.clear();
        moListUniones.clear();
    }

    /**
     * Borra las condiciones idem clear
     */
    public void Clear() {
        clear();
    }

    public void inicializar(final String psTabla, final int[] palTodosTipos, final String[] pasTodosCampos) {
        Iterator loEnum = moList.iterator();
        while (loEnum.hasNext()) {
            ((IListDatosFiltro) loEnum.next()).inicializar(psTabla, palTodosTipos, pasTodosCampos);
        }
        loEnum = null;
    }

    public void inicializar(JSTabla poTabla) {
        inicializar(poTabla.getList());
    }
    public void inicializar(JListDatos poList) {
        inicializar(poList.getTablaOAlias(), poList.getFields().malTipos(), poList.getFields().msNombres());
    }

    public void inicializar(String psTabla,JFieldDefs poCampos){
      inicializar(psTabla, poCampos.malTipos(), poCampos.msNombres());
    }

    public boolean mbCumpleFiltro(final IFilaDatos poFila) {
        boolean lbResult = true;
        IListDatosFiltro loFiltro;
        for (int i = 0; (i < moList.size()); i++) {
            loFiltro = (IListDatosFiltro) moList.get(i);
            if (i == 0) {
                lbResult = loFiltro.mbCumpleFiltro(poFila);
            } else {
                switch (((Integer) moListUniones.get(i)).intValue()) {
                    case mclAND:
                        lbResult = lbResult && loFiltro.mbCumpleFiltro(poFila);
                        break;
                    case mclOR:
                        lbResult = lbResult || loFiltro.mbCumpleFiltro(poFila);
                        break;
                    default:
                }
            }
        }
        loFiltro = null;
        return lbResult;
    }

    void duplicarDatos(final JListDatosFiltroConj poDatos) {
        int i = 0;
        Integer lAndOr;
        IListDatosFiltro loElem;
        poDatos.Clear();
        for (; i < moList.size(); i++) {
            loElem = (IListDatosFiltro) moList.get(i);
            lAndOr = (Integer) moListUniones.get(i);
            poDatos.addCondicion(lAndOr.intValue(), (IListDatosFiltro) loElem.clone());
        }
        loElem = null;
    }

    public Object clone() {
        JListDatosFiltroConj loDatos = new JListDatosFiltroConj();
        int i = 0;
        Integer lAndOr;
        for (; i < moList.size(); i++) {
            lAndOr = (Integer) moListUniones.get(i);
            IListDatosFiltro loListFiltro = (IListDatosFiltro) moList.get(i);
            loDatos.addCondicion(lAndOr.intValue(), (IListDatosFiltro) loListFiltro.clone());
        }
        return loDatos;
    }

    /**
     * clonacion del objeto con un tipo concreto
     *
     * @return objeto clonado
     */
    public JListDatosFiltroConj Clone() {
        return (JListDatosFiltroConj) clone();
    }

    public String msSQL(final ISelectMotor poSelect) {
        String lsResult = "";
        IListDatosFiltro loFiltro;
        for (int i = 0; (i < moList.size()); i++) {
            loFiltro = (IListDatosFiltro) moList.get(i);
            if (loFiltro != null) {
                String lsIzq = loFiltro.msSQL(poSelect);
                if (i > 0 && lsResult != null) {
                    if (lsIzq != null) {
                        switch (((Integer) moListUniones.get(i)).intValue()) {
                            case mclAND:
                                lsResult = poSelect.msAnd(lsResult, lsIzq);
                                break;
                            case mclOR:
                                lsResult = poSelect.msOr(lsResult, lsIzq);
                                break;
                            default:
                        }
                    }
                } else {
                    lsResult = lsIzq;
                }
            }
        }
        loFiltro = null;
        return ((lsResult == null || lsResult.compareTo("") == 0) ? null : lsResult);

    }

    /**
     * Indica si hay alguna condicion
     *
     * @return si hay condiciones
     */
    public boolean mbAlgunaCond() {
        boolean lbResult = false;
        for (int i = 0; (i < moList.size()) && !lbResult; i++) {
            if (moList.get(i) != null) {
                if (moList.get(i).getClass() == JListDatosFiltroConj.class) {
                    lbResult = ((JListDatosFiltroConj) moList.get(i)).mbAlgunaCond();
                } else {
                    lbResult = true;
                }
            }
        }
        return lbResult;
    }

    /**
     * Lista de filtros IListDatosFiltro
     */
    public IListaElementos getConjFiltros() {
        return moList;
    }

    /**
     * Lista de uniones, la primera union debe ser ignorada, a partir de la 2?
     * union es la union de la 1? condicion con la 2? condicion
     */
    public IListaElementos getConjUniones() {
        return moListUniones;
    }

    /**
     * Devuelve el objeto en cadena
     */
    public String toString() {
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
}
