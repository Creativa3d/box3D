/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import ListDatos.JListDatos;
import java.util.HashMap;
import java.util.Map;
import utiles.CadenaLarga;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JListaElementos;
import utiles.xml.dom.Document;
import utiles.xml.dom.Element;
import utiles.xml.dom.JDOMException;
import utiles.xml.dom.SAXBuilder;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;

/**
 *
 * @author eduardo
 */
public class JMensaje  implements java.io.Serializable, Cloneable {
    public static final String mcsTextoHTML="text/html";
    public static final String mcsTextoPlano="text/plain";
    
    private JListaElementos moEmailTO=new JListaElementos();
    private JListaElementos moEmailCC=new JListaElementos();
    private JListaElementos moEmailBCC=new JListaElementos();
    
    
    private String msAsunto;
    private String msTexto;
    private String msTextoTipoContenido;
    
    private JListaElementos moFicheroAdjunto=new JListaElementos();    

    private String msUsuario="";
    private String msGrupo="";
    private boolean mbBCC=false;
    private String msEmailFrom="";
    private String msIdentificadorEnvio="";
    
    
    private JDateEdu moFecha;
    private String msIdMensaje;
    
    
    private HashMap<String, Object> moCampos;

    private HashMap<String, Object> moAtributos = new HashMap<String, Object>();

    
    public JMensaje(){
    }

        
    public JMensaje(String[] psEmailTO, String psAsunto, String psTexto, String[] psFicheroAdjunto){
        msAsunto=psAsunto;
        for(int i = 0; psEmailTO!=null && i < psEmailTO.length; i++){
            moEmailTO.add(psEmailTO[i]);
        }        
        for(int i = 0; psFicheroAdjunto!=null && i < psFicheroAdjunto.length; i++){
            moFicheroAdjunto.add(psFicheroAdjunto[i]);
        }        
        msTexto=psTexto;
    }
    /**
     * @return the psEmailTO
     */
    public IListaElementos getEmailTO() {
        return moEmailTO;
    }

    /**
     * @param psEmailTO the psEmailTO to set
     */
    public void addEmailTO(String psEmailTO) {
        moEmailTO.add(psEmailTO);
    }

    /**
     * @return the psAsunto
     */
    public String getAsunto() {
        return msAsunto;
    }

    /**
     * @param psAsunto the psAsunto to set
     */
    public void setAsunto(String psAsunto) {
        this.msAsunto = psAsunto;
    }

    /**
     * @return the psTexto
     */
    public String getTexto() {
        return msTexto;
    }

    /**
     * @param psTexto the psTexto to set
     */
    public void setTexto(String psTexto) {
        this.msTexto = psTexto;
    }
    public String getAtributosEnvio(){
        StringBuilder loAtrib = new StringBuilder();
        loAtrib.append("<Grupo>"+getGrupo()+"</Grupo>");
        for(Map.Entry<String, Object> lo : getAtributos().entrySet()){
            if(lo.getValue() instanceof String) {
                loAtrib.append(
                        "<"+lo.getKey()+">"+lo.getValue().toString()+"</"+lo.getKey()+">"
                        );
            }
        }
        return  "<!--<ATRIBUTOS>"+loAtrib.toString()+"</ATRIBUTOS>-->";
    }
    public void procesarAtributosEnvio() throws JDOMException{
        int lPosi = msTexto.indexOf("<!--<ATRIBUTOS>");
        int lPosi2 = msTexto.indexOf("</ATRIBUTOS>-->");
        if(lPosi>=0 && lPosi2>=0){
            String lsXML = msTexto.substring(lPosi + 4, lPosi2+12);
            utiles.xml.dom.SAXBuilder loLector = new SAXBuilder();
            Document loDoc = loLector.build(new CadenaLarga(lsXML));
            for (Element loEl : (IListaElementos<Element>)loDoc.getRootElement().getChildren()){
                if(loEl.getNombre().equalsIgnoreCase("Grupo")){
                    setGrupo(loEl.getValor());
                } else {
                    getAtributos().put(loEl.getNombre(), loEl.getValor());
                }
            }
        }
        
    }

    /**
     * @return the psFicheroAdjunto
     */
    public IListaElementos getFicheroAdjunto() {
        return moFicheroAdjunto;
    }

    /**
     * @param psFicheroAdjunto the psFicheroAdjunto to set
     */
    public void addFicheroAdjunto(String psFicheroAdjunto) {
        moFicheroAdjunto.add(psFicheroAdjunto);
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return msUsuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.msUsuario = usuario;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return msGrupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.msGrupo = grupo;
    }

    /**
     * @return the mbBCC
     */
    public boolean isBCC() {
        return mbBCC;
    }

    /**
     * @param mbBCC the mbBCC to set
     */
    public void setBCC(boolean mbBCC) {
        this.mbBCC = mbBCC;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        JMensaje obj=null;
        obj=(JMensaje) super.clone();
        obj.moEmailTO = (JListaElementos) moEmailTO.clone();
        obj.moEmailBCC = (JListaElementos) moEmailBCC.clone();
        obj.moEmailCC = (JListaElementos) moEmailCC.clone();
        obj.moFicheroAdjunto = (JListaElementos) moFicheroAdjunto.clone();
        obj.moAtributos = (HashMap<String, Object>) moAtributos.clone();
        if(moFecha!=null){
            obj.moFecha=(JDateEdu) moFecha.clone();
        }
        return obj;
    } 
    
    public String getEmailFrom() {
        return msEmailFrom;
    }

    public void setEmailFrom(String psEmailFrom) {
        this.msEmailFrom = psEmailFrom;
    }

    /**
     * @return the moEmailCC
     */
    public JListaElementos getEmailCC() {
        return moEmailCC;
    }

    /**
     * @param moEmailCC the moEmailCC to set
     */
    public void setEmailCC(JListaElementos moEmailCC) {
        this.moEmailCC = moEmailCC;
    }

    /**
     * @return the moEmailBCC
     */
    public JListaElementos getEmailBCC() {
        return moEmailBCC;
    }

    /**
     * @param moEmailBCC the moEmailBCC to set
     */
    public void setEmailBCC(JListaElementos moEmailBCC) {
        this.moEmailBCC = moEmailBCC;
    }

    public void addEmailCC(String psEmail) {
            moEmailCC.add(psEmail);
    }

    public void addEmailBCC(String psEmail) {
            moEmailBCC.add(psEmail);
    }
    
    
    

    /**
     * @return the msIdMensaje
     */
    public String getIdMensaje() {
        return msIdMensaje;
    }

    /**
     * @param msIdMensaje the msIdMensaje to set
     */
    public void setIdMensaje(String msIdMensaje) {
        this.msIdMensaje = msIdMensaje;
    }

    /**
     * @return the moFecha
     */
    public JDateEdu getFecha() {
        return moFecha;
    }

    /**
     * @param moFecha the moFecha to set
     */
    public void setFecha(JDateEdu moFecha) {
        this.moFecha = moFecha;
    }

    /**
     * @return the msTextoTipoContenido
     */
    public String getTextoTipoContenido() {
        return msTextoTipoContenido;
    }

    /**
     * @param msTextoTipoContenido the msTextoTipoContenido to set
     */
    public void setTextoTipoContenido(String msTextoTipoContenido) {
        this.msTextoTipoContenido = msTextoTipoContenido;
    }

    /**
     * @return the moCampos
     */
    public HashMap<String, Object> getCampos() {
        return moCampos;
    }

    /**
     * @param moCampos the moCampos to set
     */
    public void setCampos(HashMap<String, Object> moCampos) {
        this.moCampos = moCampos;
    }

    public static HashMap<String, Object> getDatos(JListDatos poLista) {
        HashMap<String, Object> loDatos = new HashMap<String, Object>();

        for (int i = 0; i < poLista.getFields().size(); i++) {
            loDatos.put(poLista.getFields(i).getNombre(), poLista.getFields(i).getValue());
        }

        return loDatos;
    }   

    /**
     * @return the msIdentificadorEnvio
     */
    public String getIdentificadorEnvio() {
        return msIdentificadorEnvio;
    }

    /**
     * @param msIdentificadorEnvio the msIdentificadorEnvio to set
     */
    public void setIdentificadorEnvio(String msIdentificadorEnvio) {
        this.msIdentificadorEnvio = msIdentificadorEnvio;
    }


    /**
     * @return the moAtributos
     */
    public HashMap<String, Object> getAtributos() {
        return moAtributos;
    }

    /**
     * @param moAtributos the moAtributos to set
     */
    public void setAtributos(HashMap<String, Object> moAtributos) {
        this.moAtributos = moAtributos;
    }

    public JMensaje getResponder() throws CloneNotSupportedException {
        JMensaje loResult = (JMensaje) clone();
        loResult.setIdMensaje("");
        loResult.getEmailTO().clear();
        loResult.getEmailTO().add(msEmailFrom);
        loResult.setAsunto("Re: " + loResult.getAsunto());
        loResult.getAtributos().put(JTEEGUIXMENSAJESBD.msCTabla, "");
        return loResult;
    }
    public JMensaje getReenviar() throws CloneNotSupportedException {
        JMensaje loResult = (JMensaje) clone();
        loResult.setIdMensaje("");
        loResult.setAsunto("Fwd: " + loResult.getAsunto());
        loResult.getAtributos().put(JTEEGUIXMENSAJESBD.msCTabla, "");
        
        return loResult;
    }
}
