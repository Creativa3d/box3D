/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JListaElementos;



public class JCuadernoOrdenante {

    private String msNifOrden;
    private String msSufijoOrden;
    private JDateEdu moFechaConcepcion;
    private String msNombreOrdenante;
    private String msCCC;
    private String msCodigoIne;
    private JDateEdu moFechaCargo;

    private String msDomicilio;
    private String msMunicipio;
    private String msCP;
    private String msLocalidad;
    private String msProvincia;
    private String msPAIS;
    private String msCodProvincia;

    private IListaElementos moListaIndividuales = new JListaElementos();
    private JCuadernoTotalOrdenante moTotal = new JCuadernoTotalOrdenante();
    private int mlTipoCuaderno;

    JCuadernoOrdenante(JDateEdu poDate) {
        moFechaConcepcion=poDate;
    }


    public JCuadernoIndividual addIndividual(){
        JCuadernoIndividual loIndi = new JCuadernoIndividual();
        moListaIndividuales.add(loIndi);
        return loIndi;
    }
    public IListaElementos getIndividuales(){
        return moListaIndividuales;
    }

//    /**
//     * @return the msCodReg
//     */
//    public int getCodReg() {
//        return 53;
//    }

//    /**
//     * @param msCodReg the msCodReg to set
//     */
//    public void setCodReg(int msCodReg) {
//        this.mlCodReg = msCodReg;
//    }
//
//    public int  getCodDato() {
//        if(mb58){
//            return 70;
//        }else{
//            return 80;
//        }
//    }

//    /**
//     * @param msCodDato the msCodDato to set
//     */
//    public void setCodDato(int msCodDato) {
//        this.mlCodDato = msCodDato;
//    }

    /**
     * @return the msNif
     */
    public String getNifOrden() {
        return msNifOrden;
    }

    /**
     * @param msNif the msNif to set
     */
    public void setNifOrden(String msNif) {
        this.msNifOrden = msNif;
    }

    /**
     * @return the msSufijo
     */
    public String getSufijoOrden() {
        return msSufijoOrden;
    }

    /**
     * @param msSufijo the msSufijo to set
     */
    public void setSufijo(String msSufijo) {
        this.msSufijoOrden = msSufijo;
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
    public String getNombreOrdenante() {
        return msNombreOrdenante;
    }

    /**
     * @param msNombrePresentador the msNombrePresentador to set
     */
    public void setNombreOrdenante(String msNombrePresentador) {
        this.msNombreOrdenante = msNombrePresentador;
    }

    /**
     * @return the CCC
     */
    public String getCCC() {
        return msCCC;
    }

    /**CCC */
    public void setCCC(String CCC) {
        this.msCCC = CCC;
    }

    /**
     * @param moFechaConcepcion the moFechaConcepcion to set
     */
    void setFechaConcepcion(JDateEdu moFechaConcepcion) {
        this.moFechaConcepcion = moFechaConcepcion;
    }

//    /**
//     * @return the msProcedimiento
//     */
//    public int getProcedimiento1() {
//        if(mb58){
//            return 6;
//        }else{
//            return 1;
//        }
//    }
//    /**
//     * @param msProcedimiento the msProcedimiento to set
//     */
//    void setProcedimiento(int msProcedimiento) {
//        this.mlProcedimiento = msProcedimiento;
//    }

    /**
     * El campo codigo INE es necesario solo para remesas de la Norma 58 y se lo facilitara el banco. Es un numero de 9 cifras que indica la plaza de emision segun el instituto nacional de estadistica.
     * @return the msCodigoIne
     */
    public String getCodigoIne() {
        return msCodigoIne;
    }

    /**
     * El campo codigo INE es necesario solo para remesas de la Norma 58 y se lo facilitara el banco. Es un numero de 9 cifras que indica la plaza de emision segun el instituto nacional de estadistica.
     * @param msCodigoIne the msCodigoIne to set
     */
    public void setCodigoIne(String msCodigoIne) {
        this.msCodigoIne = msCodigoIne;
    }

    public JCuadernoTotalOrdenante getTotalDomicilioSeparado(){
        moTotal.setNumeroIndividuales(moListaIndividuales.size());
        moTotal.setSuma(0);
        moTotal.setNumeroRegistros(2 + moListaIndividuales.size());
        for(int i = 0 ; i<moListaIndividuales.size(); i++){
             JCuadernoIndividual loIndi = (JCuadernoIndividual) moListaIndividuales.get(i);
             moTotal.setSuma(moTotal.getSuma() + loIndi.getImporte());
             if(loIndi.hayRegistroDomicilio()){
                 moTotal.setNumeroRegistros(moTotal.getNumeroRegistros()+1);
             }
        }
        return moTotal;
    }
    public JCuadernoTotalOrdenante getTotalDomicilioJUNTO(){
        moTotal.setNumeroIndividuales(moListaIndividuales.size());
        moTotal.setSuma(0);
        moTotal.setNumeroRegistros(2 + moListaIndividuales.size());
        for(int i = 0 ; i<moListaIndividuales.size(); i++){
             JCuadernoIndividual loIndi = (JCuadernoIndividual) moListaIndividuales.get(i);
             moTotal.setSuma(moTotal.getSuma() + loIndi.getImporte());
        }
        return moTotal;
    }
    public JCuadernoTotalOrdenante getTotalDomicilioJUNTO2(){
        moTotal.setNumeroIndividuales(moListaIndividuales.size());
        moTotal.setSuma(0);
        moTotal.setNumeroRegistros(3 + moListaIndividuales.size());
        for(int i = 0 ; i<moListaIndividuales.size(); i++){
             JCuadernoIndividual loIndi = (JCuadernoIndividual) moListaIndividuales.get(i);
             moTotal.setSuma(moTotal.getSuma() + loIndi.getImporte());
        }
        return moTotal;
    }
    
    public int getListaIndividuales(){
        return moListaIndividuales.size();
    }

    /**
     * @return the moFechaCargo
     */
    public JDateEdu getFechaCargo() {
        return moFechaCargo;
    }

    /**
     * @param moFechaCargo the moFechaCargo to set
     */
    public void setFechaCargo(JDateEdu moFechaCargo) {
        this.moFechaCargo = moFechaCargo;
    }

    void setTipoCuaderno(int plTipoCuaderno) {
        mlTipoCuaderno=plTipoCuaderno;
        for(int i = 0 ; i<moListaIndividuales.size(); i++){
             JCuadernoIndividual loIndi = (JCuadernoIndividual) moListaIndividuales.get(i);
             loIndi.setTipoCuaderno(plTipoCuaderno);
        }
        moTotal.setTipoCuaderno(plTipoCuaderno);
    }

    /**
     * @return the msDomicilio
     */
    public String getDomicilio() {
        return msDomicilio;
    }

    /**
     * @param msDomicilio the msDomicilio to set
     */
    public void setDomicilio(String msDomicilio) {
        this.msDomicilio = msDomicilio;
    }



    /**
     * @return the msCP
     */
    public String getCP() {
        return msCP;
    }

    /**
     * @param msCP the msCP to set
     */
    public void setCP(String msCP) {
        this.msCP = msCP;
    }

    /**
     * @return the msLocalidad
     */
    public String getLocalidad() {
        return msLocalidad;
    }

    /**
     * @param msLocalidad the msLocalidad to set
     */
    public void setLocalidad(String msLocalidad) {
        this.msLocalidad = msLocalidad;
    }

    /**
     * @return the msProvincia
     */
    public String getProvincia() {
        return msProvincia;
    }

    /**
     * @param msProvincia the msProvincia to set
     */
    public void setProvincia(String msProvincia) {
        this.msProvincia = msProvincia;
    }

    /**
     * @return the msPAIS
     */
    public String getPAIS() {
        return msPAIS;
    }

    /**
     * @param msPAIS the msPAIS to set
     */
    public void setPAIS(String msPAIS) {
        this.msPAIS = msPAIS;
    }


    public boolean hayRegistroDomicilio(){
        return getCP()!=null && !getCP().equalsIgnoreCase("") ;
    }

    /**
     * @return the msMunicipio
     */
    public String getMunicipio() {
        return msMunicipio;
    }

    /**
     * @param msMunicipio the msMunicipio to set
     */
    public void setMunicipio(String msMunicipio) {
        this.msMunicipio = msMunicipio;
    }

    /**
     * @return the msCodProvincia
     */
    public String getCodProvincia() {
        return msCodProvincia;
    }

    /**
     * @param msCodProvincia the msCodProvincia to set
     */
    public void setCodProvincia(String msCodProvincia) {
        this.msCodProvincia = msCodProvincia;
    }



}
