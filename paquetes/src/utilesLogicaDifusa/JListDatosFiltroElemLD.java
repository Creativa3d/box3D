/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesLogicaDifusa;

import ListDatos.IFilaDatos;
import ListDatos.IListDatosFiltro;
import ListDatos.ISelectMotor;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatosFiltroConj;
//import ListDatos.JSelectMotor;
import ListDatos.JSelectMotorFactory;
import utiles.JConversiones;

public final class JListDatosFiltroElemLD implements IListDatosFiltro {

    public static final int mclIgualAlMenos_5 = 5;
    public static final int mclIgualAlMenos_10 = 10;
    public static final int mclIgualAlMenos_20 = 20;
    public static final int mclIgualAlMenos_30 = 30;
    public static final int mclIgualAlMenos_40 = 40;
    public static final int mclIgualAlMenos_50 = 50;
    public static final int mclIgualAlMenos_60 = 60;
    public static final int mclIgualAlMenos_70 = 70;
    public static final int mclIgualAlMenos_80 = 80;
    public static final int mclIgualAlMenos_90 = 90;
    public static final int mclIgualAlMenos_95 = 95;
    public static final int mclIgualAlMenos_100 = 100;


    private static final long serialVersionUID = 1L;
    private int mlCampo = -1;
    private String msCampo = null;
    private String msValor = null;
    private int mlTipo = -1;
    private String msTabla = null;
    private JOrdenacionLD moOrden = null;
    private IFilaDatos moFilaDatosCom = null;
    private boolean mbIgnoreCASE = true;

    private double mdComparaMargenInferior;
    private double mdComparaMargenSuperior=1000000;
    private boolean mbPorPalabra;


    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCompara tipo de comparacion
     * @param plCampo campo
     * @param psValor valor
     */
    public JListDatosFiltroElemLD(
            final double pdPorcentajeMinimo,
            final boolean pbPorPalabras,
            final int plCampo,
            final String psValor) {
        setFiltroElem(pdPorcentajeMinimo, pbPorPalabras, plCampo, psValor);
    }
    public JListDatosFiltroElemLD(
            final double plComparaMargenInferior,
            final double plComparaMargenSuperior,
            final boolean pbPorPalabras,
            final int plCampo,
            final String psValor) {
        setFiltroElem(plComparaMargenInferior,plComparaMargenSuperior,pbPorPalabras,null, plCampo,null, psValor,-1);
    }
    public void inicializar(final String psTabla, final int[] palTodosTipos,
            final String[] pasTodosCampos) {
        //inicializamos los tipos de campos
        if (mlCampo != -1) {
            mlTipo = palTodosTipos[mlCampo];
            //inicializamos los nombres de campos
            msCampo = pasTodosCampos[mlCampo];
        }else if (msCampo != null) {
            for(int ii = 0 ; ii < pasTodosCampos.length; ii++){
                if(pasTodosCampos[ii].equalsIgnoreCase(msCampo)){
                    mlTipo = palTodosTipos[ii];
                }
            }
        }
        //tabla
        msTabla = psTabla;
        //moOrden = null; @@ Comentado por Chema
        moFilaDatosCom = null;
    }

    /**
     * Constructor para condiciones de JListDatos/SQL(si luego lo inicializas)
     * @param plCompara tipo de comparacion
     * @param plCampo campo
     * @param psValor valor
     */
    public void setFiltroElem(
            final double pdPorcentajeMinimo,
            final boolean pbPorPalabras,
            final int plCampo,
            final String psValor) {
        setFiltroElem(
                pdPorcentajeMinimo, 10000000, 
                pbPorPalabras,null,
                plCampo, null, psValor, -1);
    }

    /**
     * Constructor para condiciones de JListDatos/SQL
     * @param palCampos lista de posiciones de campos
     * @param plCompara tipo de comparacion
     * @param pasValores valores
     * @param pasCampos campos
     * @param palTipos tipos de los campos
     */
    public void setFiltroElem(
            final double plComparaMargenInferior,
            final double plComparaMargenSuperior,
            final boolean pbPorPalabra,
            final String psTabla, final int plCampo, final String psCampo,
            final String psValor, final int plTipo) {
        mdComparaMargenInferior = plComparaMargenInferior;
        mdComparaMargenSuperior = plComparaMargenSuperior;
        mbPorPalabra = pbPorPalabra;
        msTabla = psTabla;
        mlCampo=plCampo;
        msCampo=psCampo;
        msValor=psValor;
        mlTipo=plTipo;
        //moOrden = null; comentado por CHEMA
        moFilaDatosCom = null;
    }

    /**el algoritmo de las operacioens necesarias para igualar cadenas,
    TIENE en cuenta la TRANSPOSICION*/
    public void setAlgoritDamerauLevenshtein(){
        moOrden = new JOrdenacionLDAlgoritDamerauLevenshtein();
        moOrden.setDatos(mlCampo, mlTipo, mbPorPalabra, mbIgnoreCASE);
    }
    /**el algoritmo de las operacioens necesarias para igualar cadenas, NO TIENE
     en cuenta la TRANSPOSICION*/
    public void setAlgoritLevenshtein(){
        moOrden = new JOrdenacionLDAlgoritLevenshtein();
        moOrden.setDatos(mlCampo, mlTipo, mbPorPalabra, mbIgnoreCASE);
    }
    /**el algoritmo propio, el porcentaje a los acentos y ? es muy bajo*/
    public void setAlgoritPropio(){
        moOrden = new JOrdenacionLDAlgoritPropio();
        moOrden.setDatos(mlCampo, mlTipo, mbPorPalabra, mbIgnoreCASE);
    }

    private void crearFilaCompararYOrden() {
        if (moOrden == null) {
            //creamos el objeto que nos comparara filas, por defecto
            //el algitmo de las operacioens necesarias para igualr cadenas,
            //teniendo en cuenta la trasposicion
            setAlgoritDamerauLevenshtein();
        }

        //
        //creamos la fila a comparar
        //
        int i;

        String[] lsCadenas = new String[mlCampo+1];

        lsCadenas[mlCampo] = msValor;
        StringBuilder lsCadena = new StringBuilder();
        for (i = 0; i <= mlCampo; i++) {
            if (lsCadenas[i] == null) {
                lsCadena.append(JFilaDatosDefecto.mcsSeparacion1);
            } else {
                lsCadena.append(lsCadenas[i]);
                lsCadena.append(JFilaDatosDefecto.mcsSeparacion1);
            }
        }

        if (mbIgnoreCASE) {
            moFilaDatosCom = new JFilaDatosDefecto(lsCadena.toString().toLowerCase());
        } else {
            moFilaDatosCom = new JFilaDatosDefecto(lsCadena.toString());
        }
        lsCadena = null;
        lsCadenas = null;
    }

    public boolean mbCumpleFiltro(final IFilaDatos poFila) {
        boolean lbEncontrado = false;
        crearFilaCompararYOrden();

        //
        //realizamos el filtrado
        //
        double ldPorcentaje = moOrden.compare(poFila, moFilaDatosCom);
        lbEncontrado =
                JConversiones.mlComparaDoubles(ldPorcentaje, mdComparaMargenInferior, 0.0001)>=0  &&
                JConversiones.mlComparaDoubles(ldPorcentaje, mdComparaMargenSuperior, 0.0001)<=0;
        return lbEncontrado;
    }

    public String msSQL(final ISelectMotor poSelect) {
        throw new InternalError("No hay equivalencia en SQL");
    }
    public boolean isMismoCampo(){
        boolean lbResult = true;
        return lbResult;
    }
    public String getCampo(){
        return msCampo;
    }
    public String getValor(){
        return msValor;
    }
    public String getTabla(){
        return msTabla;
    }
    public double getComparacionPorcenMargenInferior(){
        return mdComparaMargenInferior;
    }
    public void setComparacionPorcenMargenInferior(double pdMargen){
        mdComparaMargenInferior = pdMargen;
    }
    public double getComparacionPorcenMargenSuperior(){
        return mdComparaMargenSuperior;
    }
    public void setComparacionPorcenMargenSuperior(double pdMargen){
        mdComparaMargenSuperior = pdMargen;
    }
    public JListDatosFiltroConj getFiltroConj(){
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();

        loFiltro.addCondicion(
                loFiltro.mclAND,
                (IListDatosFiltro) this.clone()
                );
        return loFiltro;
    }

    public Object clone() {
        JListDatosFiltroElemLD loCon=null;
        try {
            loCon = (JListDatosFiltroElemLD) super.clone();
        } catch (CloneNotSupportedException ex) {
        }

        return loCon;
    }

    /**
     * clonacion del objeto con un tipo dado
     * @return objeto clonado
     */
    public JListDatosFiltroElemLD Clone() {
        return (JListDatosFiltroElemLD) clone();
    }

    /**
     * devuelve el objeto en string
     * @return cadena
     */
    public String toString() {
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
}
