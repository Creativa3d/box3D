/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;



public class JCuadernoTotalOrdenante {

    private int mlCodReg = 58;
//    private int mlCodDato = 70;

    private double mdSuma;
    private int mlNumeroIndividuales;
    private int mlNumeroRegistros;
    private int mlTipoCuaderno=JCuadernos.mclCuarderno58;

    JCuadernoTotalOrdenante(){

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
    int getNumeroIndividuales() {
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
     * F2. Numero de registros del mismo cliente ordenante,
    incluidos el de cabecera de ordenante y el propio de total

     * @return the mlNumeroRegistros
     */
    public int getNumeroRegistros() {
        return mlNumeroRegistros;
    }

    /**
     * F2. Numero de registros del mismo cliente ordenante,
    incluidos el de cabecera de ordenante y el propio de total

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

}
