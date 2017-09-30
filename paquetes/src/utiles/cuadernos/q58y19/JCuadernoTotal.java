/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;


public class JCuadernoTotal {

    private int mlCodReg = 59;
//    private int mlCodDato = 70;

    private double mdSuma;
    private int mlNumeroOrdenantesDiferentes;
    private int mlNumeroIndividuales;
    private int mlNumeroRegistros;
    private int mlTipoCuaderno;
    JCuadernoTotal(){
        
    }

    /**
     * suma  de todos los importes
     * @return the mdSuma
     */
    public double getSuma() {
        return mdSuma;
    }

    /**
     * suma  de todos los importes
     * @param mdSuma the mdSuma to set
     */
    void setSuma(double mdSuma) {
        this.mdSuma = mdSuma;
    }

    /**
     * Numero de ordenes
     * @return the mlNumeroIndividuales
     */
    public int getNumeroIndividuales() {
        return mlNumeroIndividuales;
    }

    /**
     * Numero de ordenes
     * @param mlNumeroIndividuales the mlNumeroIndividuales to set
     */
    void setNumeroIndividuales(int mlNumeroIndividuales) {
        this.mlNumeroIndividuales = mlNumeroIndividuales;
    }


    /**
     * Numero de registros que contiene el fichero, incluidos los
de cabecera, los de total ordenante y el propio de total
general.

     * @return the mlNumeroRegistros
     */
    public int getNumeroRegistros() {
        return mlNumeroRegistros;
    }

    /**
     * Numero de registros que contiene el fichero, incluidos los
de cabecera, los de total ordenante y el propio de total
general.
     * @param mlNumeroRegistros the mlNumeroRegistros to set
     */
    void setNumeroRegistros(int mlNumeroRegistros) {
        this.mlNumeroRegistros = mlNumeroRegistros;
    }

    /**
     * @return the mlCodReg
     */
    public int getCodReg() {
        return mlCodReg;
    }

    public int  getCodDato() {
        int lResult=0;
        switch(mlTipoCuaderno){
            case JCuadernos.mclCuarderno19:
                lResult = 80;
                break;
            case JCuadernos.mclCuarderno58:
                lResult = 70;
                break;
                
        }
        
        return lResult;
    }
    void setTipoCuaderno(int plTipoCuaderno) {
        mlTipoCuaderno=plTipoCuaderno;
    }

    /**
     * @return the mlNumeroOrdenantesDiferentes
     */
    public int getNumeroOrdenantesDiferentes() {
        return mlNumeroOrdenantesDiferentes;
    }

    /**
     * @param mlNumeroOrdenantesDiferentes the mlNumeroOrdenantesDiferentes to set
     */
    void setNumeroOrdenantesDiferentes(int mlNumeroOrdenantesDiferentes) {
        this.mlNumeroOrdenantesDiferentes = mlNumeroOrdenantesDiferentes;
    }
}
