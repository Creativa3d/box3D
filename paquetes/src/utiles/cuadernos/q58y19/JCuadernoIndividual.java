/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

import utiles.JCadenas;
import utiles.JDateEdu;



public class JCuadernoIndividual {

    private String msCodigoReferenciaClienteOMANDATO;
    private String msNombreTitularCredito;
    private String msCCC;
    private double mdImporte;
    private String msCodigoParaDevoluciones;
    private String msCodigoParaDevolucionesReferenciaInterna;
    private String msConcepto1;
    private JDateEdu moFechaVencimiento;

    private String msDomicilio;
    private String msMunicipio;
    private String msCP;
    private String msLocalidad;
    private String msProvincia;
    private String msPAIS;
    private JDateEdu moFechaOrigen;
    private int mlTipoCuaderno;
    private String msBIC;
    private String msNIF;
    private boolean mbPrimeraVez=false;


    private String msCCCANTERIOR="";
    private String msNombreTitularCreditoANTERIOR="";
    private String msNIFANTERIOR="";
    private String msMANDATOANTERIOR="";
    private boolean mbNomina=false;
    
/**   
 * Antiguo
 * El "Codigo de referencia" que identifica univocamente al deudor en la organizacion del cliente 
 * ordenante; dicho codigo debera ser invariable de una facturacion a otra para un mismo titular y servicio
* (puede ser el numero de contrato, poliza, etc.)
*  Actual
* Referencia única del mandato (AT-01).
*/
    public String getCodigoReferenciaClienteOMANDATO() {
        return msCodigoReferenciaClienteOMANDATO;
    }

/**   
 * Antiguo
 * El "Codigo de referencia" que identifica univocamente al deudor en la organizacion del cliente 
 * ordenante; dicho codigo debera ser invariable de una facturacion a otra para un mismo titular y servicio
* (puede ser el numero de contrato, poliza, etc.)
*  Actual
* Referencia única del mandato (AT-01).
*/
    public void setCodigoReferenciaClienteOMANDATO(String msCodigoReferencia) {
        this.msCodigoReferenciaClienteOMANDATO = msCodigoReferencia;
    }

    /**
     * supongo q el nombre al q se le carga la deuda
     * @return the msNombreTitularCredito
     */
    public String getNombreTitularCredito() {
        return msNombreTitularCredito;
    }

    /**
     * supongo q el nombre al q se le carga la deuda
     * @param msNombreTitularCredito the msNombreTitularCredito to set
     */
    public void setNombreTitularCredito(String msNombreTitularCredito) {
        this.msNombreTitularCredito = msNombreTitularCredito;
    }

    /**
     * @return the msCCCAdeudo
     */
    public String getCCC() {
        return msCCC;
    }

    /**
     * @param msCCCAdeudo the msCCCAdeudo to set
     */
    public void setCCC(String msCCCAdeudo) {
        this.msCCC = msCCCAdeudo;
    }

    /**
     * @return the mdImporte
     */
    public double getImporte() {
        return mdImporte;
    }

    /**
     * @param mdImporte the mdImporte to set
     */
    public void setImporte(double mdImporte) {
        this.mdImporte = mdImporte;
    }

    /**
     * Codigo para devoluciones. Codigo para identificar la devolucion.
     * @return the msCodigoParaDevoluciones
     */
    public String getCodigoParaDevoluciones() {
        return msCodigoParaDevoluciones;
    }

    /**
     * Codigo para devoluciones. Codigo para identificar la devolucion.
     * @param msCodigoParaDevoluciones the msCodigoParaDevoluciones to set
     */
    public void setCodigoParaDevoluciones(String msCodigoParaDevoluciones) {
        this.msCodigoParaDevoluciones = msCodigoParaDevoluciones;
    }

    /**
     * Codigo de referencia interna. Igualmente de uso por el cliente ordenante para identificacion, en caso de devolucion. Ambos campos no apareceran en el "Adeudo por domiciliaciones" pero se facilitaran en el fichero de devoluciones, en su caso.
     * @return the msCodigoReferenciaInterna
     */
    public String getCodigoParaDevolucionesReferenciaInterna() {
        return msCodigoParaDevolucionesReferenciaInterna;
    }

    /**
     * Codigo de referencia interna. Igualmente de uso por el cliente ordenante para identificacion, en caso de devolucion. Ambos campos no apareceran en el "Adeudo por domiciliaciones" pero se facilitaran en el fichero de devoluciones, en su caso.
     * @param msCodigoReferenciaInterna the msCodigoReferenciaInterna to set
     */
    public void setCodigoParaDevolucionesReferenciaInterna(String msCodigoReferenciaInterna) {
        this.msCodigoParaDevolucionesReferenciaInterna = msCodigoReferenciaInterna;
    }

    /**
     * @return the msConcepto1
     */
    public String getConcepto1() {
        return msConcepto1;
    }

    /**
     * @param msConcepto1 the msConcepto1 to set
     */
    public void setConcepto1(String msConcepto1) {
        this.msConcepto1 = msConcepto1;
    }

    /**
     * @return the moFechaVencimiento
     */
    public JDateEdu getFechaVencimiento() {
        return moFechaVencimiento;
    }

    /**
     * @param moFechaVencimiento the moFechaVencimiento to set
     */
    public void setFechaVencimiento(JDateEdu moFechaVencimiento) {
        this.moFechaVencimiento = moFechaVencimiento;
    }

    /**
     * Registro obligatorio de domicilio, para no domiciliados
     * calle
     * @return the msDomicilio
     */
    public String getDomicilio() {
        return msDomicilio;
    }

    /**
     * Registro obligatorio de domicilio, para no domiciliados
     * calle
     * @param msDomicilio the msDomicilio to set
     */
    public void setDomicilio(String msDomicilio) {
        this.msDomicilio = msDomicilio;
    }

    /**
     * Registro obligatorio de domicilio, para no domiciliados
     * Codigo postal del domicilio del deudor. Cuando no se
     * conozca el codigo completo, se cumplimentara, al menos,
     * las dos primeras posiciones que identifican la provincia,
     * dejando el resto de posiciones a cero.

     * @return the msCP
     */
    public String getCP() {
        return msCP;
    }

    /**
     * Registro obligatorio de domicilio, para no domiciliados
     * Codigo postal del domicilio del deudor. Cuando no se conozca el codigo completo, se cumplimentara, al menos,
     * las dos primeras posiciones que identifican la provincia, dejando el resto de posiciones a cero.
     * @param msCP the msCP to set
     */
    public void setCP(String msCP) {
        this.msCP = msCP;
    }

    /**
     * Registro obligatorio de domicilio, para no domiciliados
     * Fecha de origen en que se formalizo el credito anticipado
     */
    public JDateEdu getFechaOrigen() {
        return moFechaOrigen;
    }

    /**
     * Registro obligatorio de domicilio, para no domiciliados
     * Fecha de origen en que se formalizo el credito anticipado, en formato DDMMAA.
     * Fecha de origen en que se formalizo el credito anticipado
     */
    public void setFechaOrigen(JDateEdu moFechaOrigen) {
        this.moFechaOrigen = moFechaOrigen;
    }


    public boolean hayRegistroDomicilio(){
        return getCP()!=null && !getCP().equalsIgnoreCase("") ;
    }

    void setTipoCuaderno(int plTipoCuaderno) {
        mlTipoCuaderno=plTipoCuaderno;
    }

    public String getBIC() {
        return msBIC;
    }
    public void setBIC(String ps){
        msBIC=ps;
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

    public String  getPlazaDomicilio() {
            return (JCadenas.isVacio(msLocalidad) ?
                            msMunicipio :
                            msLocalidad);
    }

    public void setNIF(String string) {
        msNIF = string;
    }
    public String getNIF() {
        return msNIF;
    }

    /**
     * @return the mbPrimeraVez
     */
    public boolean isPrimeraVez() {
        return mbPrimeraVez;
    }

    /**
     * @param mbPrimeraVez the mbPrimeraVez to set
     */
    public void setPrimeraVez(boolean mbPrimeraVez) {
        this.mbPrimeraVez = mbPrimeraVez;
    }

    /**
     * @return the msCCCANTERIOR
     */
    public String getCCCANTERIOR() {
        return msCCCANTERIOR;
    }

    /**
     * @param msCCCANTERIOR the msCCCANTERIOR to set
     */
    public void setCCCANTERIOR(String msCCCANTERIOR) {
        this.msCCCANTERIOR = msCCCANTERIOR;
    }

    /**
     * @return the msNombreTitularCreditoANTERIOR
     */
    public String getNombreTitularCreditoANTERIOR() {
        return msNombreTitularCreditoANTERIOR;
    }

    /**
     * @param msNombreTitularCreditoANTERIOR the msNombreTitularCreditoANTERIOR to set
     */
    public void setNombreTitularCreditoANTERIOR(String msNombreTitularCreditoANTERIOR) {
        this.msNombreTitularCreditoANTERIOR = msNombreTitularCreditoANTERIOR;
    }

    /**
     * @return the msNIFANTERIOR
     */
    public String getNIFANTERIOR() {
        return msNIFANTERIOR;
    }

    /**
     * @param msNIFANTERIOR the msNIFANTERIOR to set
     */
    public void setNIFANTERIOR(String msNIFANTERIOR) {
        this.msNIFANTERIOR = msNIFANTERIOR;
    }

    /**
     * @return the msMANDATOANTERIOR
     */
    public String getMANDATOANTERIOR() {
        return msMANDATOANTERIOR;
    }

    /**
     * @param msMANDATOANTERIOR the msMANDATOANTERIOR to set
     */
    public void setMANDATOANTERIOR(String msMANDATOANTERIOR) {
        this.msMANDATOANTERIOR = msMANDATOANTERIOR;
    }

    /**
     * @return the mbNomina
     */
    public boolean isNomina() {
        return mbNomina;
    }

    /**
     * @param mbNomina the mbNomina to set
     */
    public void setNomina(boolean mbNomina) {
        this.mbNomina = mbNomina;
    }

}
