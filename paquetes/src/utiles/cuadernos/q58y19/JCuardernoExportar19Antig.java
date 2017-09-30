/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import utiles.JCuentaBancaria;
import utiles.JFormat;

public class JCuardernoExportar19Antig {
    private JCuadernos moCuaderno;
    private String msFichero;

    public JCuardernoExportar19Antig(){
    }

    public void setFichero(String psFichero){
        msFichero=psFichero;
    }
    public void setCuaderno(JCuadernos poCuaderno){
        moCuaderno=poCuaderno;
    }

    public void exportar() throws  Throwable {
        PrintWriter loPrint =new PrintWriter(new FileOutputStream(msFichero));
        try{
            exportar(loPrint, moCuaderno.getPresentador());
            for(int i = 0 ; i < moCuaderno.getOrdenantes().size();i++){
                JCuadernoOrdenante loORD = (JCuadernoOrdenante) moCuaderno.getOrdenantes().get(i);
                exportar(loPrint, loORD);
                for(int l = 0 ; l < loORD.getIndividuales().size();l++){
                    JCuadernoIndividual loIND = (JCuadernoIndividual) loORD.getIndividuales().get(l);
                    exportar(loPrint, loORD, loIND);
                }
                exportar(loPrint,loORD, loORD.getTotalDomicilioSeparado());
            }
            exportar(loPrint, moCuaderno.getPresentador(), moCuaderno.getTotalSeparado());
        }finally{
            if(loPrint!=null){
                loPrint.close();
            }
        }

    }

    private void exportar(PrintWriter poPrint, JCuadernoPresentador poPresentador) throws Exception{
        StringBuffer loBuffer = new StringBuffer(162);
        JCuentaBancaria loCuentaPresentador; 
        if(JCuentaBancaria.validarCuenta(poPresentador.getCCC())){
            loCuentaPresentador = new JCuentaBancaria(poPresentador.getCCC());
        }else{
            throw new Exception("CCC del presentador incorrecta");
        }
        loBuffer.append(JFormat.msFormatearDouble(51, "00"));
        loBuffer.append(JFormat.msFormatearDouble(80, "00"));
        loBuffer.append(JFormat.msRellenarIzq(poPresentador.getNifPresen().toUpperCase(), "0", 9));
        loBuffer.append(JFormat.msRellenarIzq(poPresentador.getSufijoPresen().toUpperCase(), "0", 3));
        loBuffer.append(poPresentador.getFechaConcepcion().msFormatear("ddMMyy"));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 6));
        loBuffer.append(JFormat.msRellenarDer(poPresentador.getNombrePresentador().toUpperCase(), " " , 40));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 20));
        loBuffer.append(JFormat.msRellenarIzq(loCuentaPresentador.getCCC().toUpperCase(),"0",8));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 12));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 40));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 14));

        poPrint.println(loBuffer.toString());
        if(loBuffer.toString().length()!=162){
            throw new Exception("Long. del registro del presentador " + String.valueOf(loBuffer.length()) + " y debería ser 162");
        }
    }
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante) throws Exception{
        StringBuffer loBuffer = new StringBuffer(162);
        JCuentaBancaria loCuentaOrdenante; 
        if(JCuentaBancaria.validarCuenta(poOrdenante.getCCC())){
            loCuentaOrdenante = new JCuentaBancaria(poOrdenante.getCCC());
        }else{
            throw new Exception("CCC del ordenante incorrecta");
        }
        if(poOrdenante.getNombreOrdenante()==null || poOrdenante.getNombreOrdenante().equals("")){
            throw new Exception("Nombre del ordenante vacío");
        }
        loBuffer.append(JFormat.msFormatearDouble(53, "00"));
        loBuffer.append(JFormat.msFormatearDouble(80, "00"));
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getNifOrden().toUpperCase(), "0", 9));
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getSufijoOrden().toUpperCase(), "0", 3));
        loBuffer.append(poOrdenante.getFechaConcepcion().msFormatear("ddMMyy"));
        if(moCuaderno.getTipoCuaderno()==moCuaderno.mclCuarderno58){
            loBuffer.append(JFormat.msRellenarIzq("", " ", 6));
        }else{
            loBuffer.append(poOrdenante.getFechaCargo().msFormatear("ddMMyy"));
        }

        loBuffer.append(JFormat.msRellenarDer(poOrdenante.getNombreOrdenante().toUpperCase(), " " , 40));
        loBuffer.append(JFormat.msRellenarIzq(loCuentaOrdenante.getCCC(),"0",20));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 8));
        loBuffer.append(JFormat.msFormatearDouble(1, "00"));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 40));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 2));
        if(moCuaderno.getTipoCuaderno()==moCuaderno.mclCuarderno58){
            loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getCodigoIne(), "0", 9));
        }else{
            loBuffer.append(JFormat.msRellenarIzq("", " ", 9));
        }
        loBuffer.append(JFormat.msRellenarIzq("", " ", 3));


        poPrint.println(loBuffer.toString());
        if(loBuffer.length()!=162){
            throw new Exception("Long. del registro del ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 162");
        }
    }
    private String getQuitarCaracterPuntoOComa(String psCadena){
        StringBuffer ls = new StringBuffer(psCadena.length()-1);
        for(int i = 0 ; i < psCadena.length() ; i++){
            if(psCadena.charAt(i)!='.' && psCadena.charAt(i)!=',' ){
                ls.append(psCadena.charAt(i));
            }
        }
        return ls.toString();
    }
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoIndividual poIndividual) throws Exception{
        StringBuffer loBuffer = new StringBuffer(162);
        JCuentaBancaria loCCCAdeudo; 
        if(JCuentaBancaria.validarCuenta(poIndividual.getCCC())){
            loCCCAdeudo = new JCuentaBancaria(poIndividual.getCCC());
        }else{
            throw new Exception("CCC del registro individual incorrecta");
        }
        
        if(poIndividual.getNombreTitularCredito()==null || poIndividual.getNombreTitularCredito().equals("")){
            throw new Exception("Nombre del titular del registro individual vacío");
        }
        loBuffer.append(JFormat.msFormatearDouble(56, "00"));
        loBuffer.append(JFormat.msFormatearDouble(80, "00"));
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getNifOrden().toUpperCase(), "0", 9));
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getSufijoOrden().toUpperCase(), "0", 3));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoReferenciaClienteOMANDATO().toUpperCase(), " ", 12));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getNombreTitularCredito().toUpperCase(), " ", 40) );
        loBuffer.append(JFormat.msRellenarIzq(loCCCAdeudo.getCCC(),"0",20));
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poIndividual.getImporte(), "00000000.00")));
//        loBuffer.deleteCharAt(loBuffer.length()-3);//borramos el punto o coma
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoParaDevoluciones()," ",6));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoParaDevolucionesReferenciaInterna()," ",10));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getConcepto1().toUpperCase(), " ", 40));
        if(moCuaderno.getTipoCuaderno()==moCuaderno.mclCuarderno58){
            loBuffer.append(poIndividual.getFechaVencimiento().msFormatear("ddMMyy"));
        }else{
            loBuffer.append(JFormat.msRellenarIzq("", " ", 6));
        }
        loBuffer.append(JFormat.msRellenarIzq("", " ", 2));


        poPrint.println(loBuffer.toString());
        if(loBuffer.length()!=162){
            throw new Exception("Long. del registro del individual " + String.valueOf(loBuffer.length()) + " y debería ser 162 de referencia " + poIndividual.getCodigoParaDevoluciones());
        }

        if(poIndividual.hayRegistroDomicilio()){
            loBuffer = new StringBuffer(162);

            loBuffer.append(JFormat.msFormatearDouble(56, "00"));
            loBuffer.append(JFormat.msFormatearDouble(86, "00"));
            loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getNifOrden().toUpperCase(), "0", 9));
            loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getSufijoOrden().toUpperCase(), "0", 3));
            loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoReferenciaClienteOMANDATO().toUpperCase(), " ", 12));
            if(moCuaderno.getTipoCuaderno()==moCuaderno.mclCuarderno58){
                loBuffer.append(JFormat.msRellenarDer(poIndividual.getDomicilio().toUpperCase(), " ", 40) );
                loBuffer.append(JFormat.msRellenarIzq(poIndividual.getPlazaDomicilio().toUpperCase()," ",35));
                loBuffer.append(JFormat.msRellenarIzq(poIndividual.getCP(),"0",5));
                loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getLocalidad().toUpperCase()," ",38));
                loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getCodProvincia().toUpperCase(),"0",2));
                if(poIndividual.getFechaOrigen()!=null){
                    loBuffer.append(poIndividual.getFechaOrigen().msFormatear("ddMMyy") );
                }else{
                    loBuffer.append(poOrdenante.getFechaConcepcion().msFormatear("ddMMyy") );
                }
                loBuffer.append(JFormat.msRellenarIzq("", " ", 8));
            }else{
                loBuffer.append(JFormat.msRellenarDer(poIndividual.getNombreTitularCredito().toUpperCase(), " ", 40) );
                loBuffer.append(JFormat.msRellenarDer(poIndividual.getDomicilio().toUpperCase(), " ", 40) );
                loBuffer.append(JFormat.msRellenarIzq(poIndividual.getPlazaDomicilio().toUpperCase()," ",35));
                loBuffer.append(JFormat.msRellenarIzq(poIndividual.getCP(),"0",5));
                loBuffer.append(JFormat.msRellenarIzq("", " ", 14));
            }


            poPrint.println(loBuffer.toString());
            if(loBuffer.length()!=162){
                throw new Exception("Long. del registro del individual opcional dirección " + String.valueOf(loBuffer.length()) + " y debería ser 162");
            }
            
        }
    }
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoTotalOrdenante poTotalOrd) throws Exception{
        StringBuffer loBuffer = new StringBuffer(162);

        loBuffer.append(JFormat.msFormatearDouble(poTotalOrd.getCodReg(), "00"));
        loBuffer.append(JFormat.msFormatearDouble(poTotalOrd.getCodDato(), "00"));
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getNifOrden().toUpperCase(), "0", 9));
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getSufijoOrden().toUpperCase(), "0", 3));
        loBuffer.append(JFormat.msRellenarDer("", " ", 12));
        loBuffer.append(JFormat.msRellenarDer("", " ", 40) );
        loBuffer.append(JFormat.msRellenarIzq(""," ",20));
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poTotalOrd.getSuma(), "00000000.00")));
//        loBuffer.deleteCharAt(loBuffer.length()-3);//borramos el punto o coma
        loBuffer.append(JFormat.msRellenarDer("", " ", 6) );
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotalOrd.getNumeroIndividuales()),"0",10));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotalOrd.getNumeroRegistros()),"0",10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 20));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 18));


        poPrint.println(loBuffer.toString());
        if(loBuffer.length()!=162){
            throw new Exception("Long. del registro del total ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 162");
        }
    }

    private void exportar(PrintWriter poPrint, JCuadernoPresentador poPresen, JCuadernoTotal poTotal) throws Exception{
        StringBuffer loBuffer = new StringBuffer(162);

        loBuffer.append(JFormat.msFormatearDouble(poTotal.getCodReg(), "00"));
        loBuffer.append(JFormat.msFormatearDouble(poTotal.getCodDato(), "00"));
        loBuffer.append(JFormat.msRellenarIzq(poPresen.getNifPresen().toUpperCase(), "0", 9));
        loBuffer.append(JFormat.msRellenarIzq(poPresen.getSufijoPresen().toUpperCase(), "0", 3));
        loBuffer.append(JFormat.msRellenarDer("", " ", 12));
        loBuffer.append(JFormat.msRellenarDer("", " ", 40) );
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotal.getNumeroOrdenantesDiferentes()),"0",4));
        loBuffer.append(JFormat.msRellenarIzq(""," ",16));
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poTotal.getSuma(), "00000000.00")));
//        loBuffer.deleteCharAt(loBuffer.length()-3);//borramos el punto o coma
        loBuffer.append(JFormat.msRellenarDer("", " ", 6) );
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotal.getNumeroIndividuales()),"0",10));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotal.getNumeroRegistros()),"0",10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 20));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 18));


        poPrint.println(loBuffer.toString());
        if(loBuffer.length()!=162){
            throw new Exception("Long. del registro del total presentador " + String.valueOf(loBuffer.length()) + " y debería ser 162");
        }
    }

}
