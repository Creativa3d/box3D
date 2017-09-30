/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

import utiles.JDateEdu;

public class JCuadernoPresentador {

    private String msNifPresent;
    private String msSufijoPresen;
    private JDateEdu moFechaConcepcion;
    private String msNombrePresentador;
    private String msCCC;
    private int mlTipoCuaderno;
    private String msCodigoRemesa;

    JCuadernoPresentador(JDateEdu poDate) {
        moFechaConcepcion=poDate;
    }
//
//    /**
//     * @return the msCodReg
//     */
//    public int getCodReg() {
//        return 51;
//    }

//    /**
//     * @param msCodReg the msCodReg to set
//     */
//    public void setCodReg(int msCodReg) {
//        this.mlCodReg = msCodReg;
//    }

//    /**
//     * @return the msCodDato
//     */
//    public int  getCodDato() {
//        return (mb58 ? 70 : 80);
//    }

//    /**
//     * @param msCodDato the msCodDato to set
//     */
//    public void setCodDato(int  msCodDato) {
//        this.mlCodDato = msCodDato;
//    }

    /**
     * @return the msNif
     */
    public String getNifPresen() {
        return msNifPresent;
    }

    /**
     * @param msNif the msNif to set
     */
    public void setNifPresen(String msNif) {
        this.msNifPresent = msNif;
    }

    /**
     * @return the msSufijo
     */
    public String getSufijoPresen() {
        return msSufijoPresen;
    }

    /**
     * @param msSufijo the msSufijo to set
     */
    public void setSufijoPresen(String msSufijo) {
        this.msSufijoPresen = msSufijo;
    }

    /**
     * @return the moFechaConcepcion
     */
    public JDateEdu getFechaConcepcion() {
        return moFechaConcepcion;
    }

    /**
     * @return the msNombrePresentador
     */
    public String getNombrePresentador() {
        return msNombrePresentador;
    }

    /**
     * @param msNombrePresentador the msNombrePresentador to set
     */
    public void setNombrePresentador(String msNombrePresentador) {
        this.msNombrePresentador = msNombrePresentador;
    }

    /**
     * @return the CCC
     */
    public String getCCC() {
        return msCCC;
    }

    /**CCC solo hacen falta 8 primeros digitos, Entidad receptora, y oficina*/
    public void setCCC(String CCC) {
        this.msCCC = CCC;
    }

    /**
     * @param moFechaConcepcion the moFechaConcepcion to set
     */
    void setFechaConcepcion(JDateEdu moFechaConcepcion) {
        this.moFechaConcepcion = moFechaConcepcion;
    }

    void setTipoCuaderno(int plTipo) {
        mlTipoCuaderno = plTipo;
    }
    /**
     * @return the mlCodigoRemesa
     */
    public String getCodigoRemesa() {
        return msCodigoRemesa;
    }

    /**
     * @param mlCodigoRemesa the mlCodigoRemesa to set
     */
    public void setCodigoRemesa(String mlCodigoRemesa) {
        this.msCodigoRemesa = mlCodigoRemesa;
    }


}
