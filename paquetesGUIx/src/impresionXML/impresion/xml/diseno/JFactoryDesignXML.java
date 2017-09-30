/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno;


import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlBanda;
import impresionXML.impresion.xml.JxmlCuadrado;
import impresionXML.impresion.xml.JxmlFuente;
import impresionXML.impresion.xml.JxmlImagen;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlLinea;
import impresionXML.impresion.xml.JxmlTexto;
import impresionXML.impresion.xml.diseno.componentes.JxmlBandaDesign;
import impresionXML.impresion.xml.diseno.componentes.JxmlCuadradoDesign;
import impresionXML.impresion.xml.diseno.componentes.JxmlFuenteDesign;
import impresionXML.impresion.xml.diseno.componentes.JxmlImagenDesign;
import impresionXML.impresion.xml.diseno.componentes.JxmlLineaDesign;
import impresionXML.impresion.xml.diseno.componentes.JxmlTextoDesign;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;

/**
 *
 * @author eduardo
 */
public class JFactoryDesignXML {
    private static final JFactoryDesignXML moFactory;
    
    static {
        moFactory = new JFactoryDesignXML();
//        moFactory.registrarMotor(JxmlBanda.mcsNombreXml, JxmlBanda.class, true,
//                new JxmlBandaDesign(), JxmlBanda.mcsNombreXml);
        moFactory.registrarMotor(JxmlCuadrado.mcsNombreXml, JxmlCuadrado.class,true,
                new JxmlCuadradoDesign(), JxmlCuadrado.mcsNombreXml);
        moFactory.registrarMotor(JxmlFuente.mcsNombreXml, JxmlFuente.class,false,
                new JxmlFuenteDesign(), JxmlFuente.mcsNombreXml);
        moFactory.registrarMotor(JxmlImagen.mcsNombreXml, JxmlImagen.class,true,
                new JxmlImagenDesign(), JxmlImagen.mcsNombreXml);
        moFactory.registrarMotor(JxmlLinea.mcsNombreXml, JxmlLinea.class, true,
                new JxmlLineaDesign(), JxmlLinea.mcsNombreXml);
        moFactory.registrarMotor(JxmlTexto.mcsNombreXml, JxmlTexto.class,true,
                new JxmlTextoDesign(), JxmlTexto.mcsNombreXml);
    }
    /**Patron singleObject*/
    public static JFactoryDesignXML getInstance(){
        return moFactory;
    }

    public static class JFactoryDesignXMLElem {
        private final String msTipo;
        private final IXMLDesign moSelect;
        private final String msCaption;
        private final Class moClassxml;
        private final boolean mbImpresion;

        public JFactoryDesignXMLElem(String psTipo, Class poClassXml,boolean pbImpresion, IXMLDesign poSelect, String psCaption){
            msTipo = psTipo;
            moClassxml=poClassXml;
            moSelect = poSelect;
            msCaption = psCaption;
            mbImpresion=pbImpresion;
        }
        public String getCaption(){
            return msCaption;
        }

        /**
         * @return the msTipo
         */
        public String getTipo() {
            return msTipo;
        }

        /**
         * @return the moSelect
         */
        public IXMLDesign getDesignXML() {
            return moSelect;
        }
        
        public Class getClassXml(){
            return moClassxml;
        }
        public boolean  isImpresion(){
            return mbImpresion;
        }
    }
    
    private IListaElementos moLista = new JListaElementos();

    
    
    private JFactoryDesignXML(){}
    
    /**DESRegistramos un motor de sql*/
    public synchronized void DESregistrarMotor(String psTipo){
        JFactoryDesignXMLElem loMotorElem = getDesignXMLElem(psTipo);
        if(loMotorElem!=null){
            moLista.remove(loMotorElem);
        }
    }
    public synchronized void registrarMotor(String psTipo, Class poClassXml, boolean pbImpresion, IXMLDesign poSelectMotor, String psCaption){
        //si existe previamente lo borramos
        DESregistrarMotor(psTipo);
        //add el mnuevo jselecmotor
        moLista.add(new JFactoryDesignXMLElem(psTipo, poClassXml, pbImpresion, poSelectMotor, psCaption));

    }
    public IXMLDesign getDesignXML(IxmlObjetos poObj, JxmlInforme poInforme) throws Exception{
        JFactoryDesignXMLElem loResult = getDesignXMLElem(poObj.getNombreXML());
        if(loResult!=null){

            IXMLDesign loDesig = loResult.getDesignXML().Clone();
            loDesig.setDatos(poObj, poInforme);
            return loDesig;

 
        }else{
            return null;
        }
    }

    public IxmlObjetos getXML(String psNombre, JxmlInforme poInforme) throws Exception{
        JFactoryDesignXMLElem loResult = getDesignXMLElem(psNombre);
        if(loResult!=null){
            return (IxmlObjetos) loResult.getClassXml().newInstance();
        }else{
            return null;
        }        
    }
    
    public JFactoryDesignXMLElem getDesignXMLElem(String psTipo){
        JFactoryDesignXMLElem loResult = null;
        for(int i =0; i < moLista.size() && loResult == null; i++){
            JFactoryDesignXMLElem loElem = (JFactoryDesignXMLElem) moLista.get(i);
            if(loElem.getTipo().equalsIgnoreCase(psTipo)){
                loResult = loElem;
            }
        }
        return loResult;
    }
   
    /**Devuelve la lista de JSelectMotorFactElem*/
    public IListaElementos getListaDesignXML(){
        return moLista;
    }

    
}

