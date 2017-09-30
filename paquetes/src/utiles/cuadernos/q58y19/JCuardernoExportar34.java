/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import utiles.CIF_NIF;
import utiles.JCadenas;
import utiles.JCuentaBancaria;
import utiles.JFormat;

public class JCuardernoExportar34 {
    private JCuadernos moCuaderno;
    private String msFichero;

    public JCuardernoExportar34(){
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
            for(int i = 0 ; i < moCuaderno.getOrdenantes().size();i++){
                int lSubTotal=0;
                JCuadernoOrdenante loORD = (JCuadernoOrdenante) moCuaderno.getOrdenantes().get(i);
                exportar(loPrint, loORD);
                lSubTotal++;
                exportar2(loPrint, loORD);
                lSubTotal++;
                for(int l = 0 ; l < loORD.getIndividuales().size();l++){
                    JCuadernoIndividual loIND = (JCuadernoIndividual) loORD.getIndividuales().get(l);
                    exportar(loPrint, loORD, loIND);
                    lSubTotal++;
                    
                }
                exportar(loPrint,loORD, loORD.getTotalDomicilioJUNTO(), lSubTotal);
                lSubTotal++;
                exportar99(loPrint,loORD, loORD.getTotalDomicilioJUNTO(), lSubTotal+1);
            }
        }finally{
            if(loPrint!=null){
                loPrint.close();
            }
        }

    }
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);
        JCuentaBancaria loCuentaOrdenante=JCuadernos.validarCCC(poOrdenante.getCCC(), poOrdenante.getNifOrden(), "del ordenante", ""); 
        if(poOrdenante.getNombreOrdenante()==null || poOrdenante.getNombreOrdenante().equals("")){
            throw new Exception("Nombre del ordenante vacío");
        }
        loBuffer.append("01");
        loBuffer.append("ORD");
        loBuffer.append("34145");
        loBuffer.append("001");
        
        loBuffer.append(JFormat.msRellenarDer(poOrdenante.getNifOrden().toUpperCase(), " ", 9)                );
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getSufijoOrden().toUpperCase(), "0", 3) );
        loBuffer.append(poOrdenante.getFechaConcepcion().msFormatear("yyyyMMdd"));
        loBuffer.append(poOrdenante.getFechaCargo().msFormatear("yyyyMMdd"));
        loBuffer.append("A");
        loBuffer.append(JFormat.msRellenarDer(loCuentaOrdenante.getIBAN()," ",34));
        loBuffer.append("1");
        
        loBuffer.append(JFormat.msRellenarDer(poOrdenante.getNombreOrdenante().toUpperCase(), " " , 70));

        if(poOrdenante.hayRegistroDomicilio()){
            loBuffer.append(JFormat.msRellenarDer(poOrdenante.getDomicilio().toUpperCase(), " ", 50) );
            loBuffer.append(
                    JFormat.msRellenarIzq(poOrdenante.getCP(),"0",5)  + " " 
                    + JFormat.msRellenarDer(poOrdenante.getLocalidad().toUpperCase()," ",44)  );
            loBuffer.append(JFormat.msRellenarDer(poOrdenante.getProvincia().toUpperCase()," ",40));
            if(JCadenas.isVacio(poOrdenante.getPAIS())){
                loBuffer.append(JFormat.msRellenarDer("ES"," ",2));
            }else{
                loBuffer.append(JFormat.msRellenarDer(poOrdenante.getPAIS().toUpperCase()," ",2));
            }
        }else{
            loBuffer.append(JFormat.msRellenarDer("", " ", 50));
            loBuffer.append(JFormat.msRellenarDer("", " ", 50));
            loBuffer.append(JFormat.msRellenarDer("", " ", 40));     
            loBuffer.append(JFormat.msRellenarDer("", " ", 2));     
        }
        loBuffer.append(JFormat.msRellenarDer("", " ", 311));     

        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 600");
        }
    }
    private void exportar2(PrintWriter poPrint, JCuadernoOrdenante poOrdenante) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);
        if(poOrdenante.getNombreOrdenante()==null || poOrdenante.getNombreOrdenante().equals("")){
            throw new Exception("Nombre del ordenante vacío");
        }
        loBuffer.append("02");
        loBuffer.append("SCT");
        loBuffer.append("34145");
        loBuffer.append(JFormat.msRellenarDer(poOrdenante.getNifOrden().toUpperCase(), " ", 9)                );
        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getSufijoOrden().toUpperCase(), "0", 3) );
        loBuffer.append(JFormat.msRellenarDer("", " ", 578));     

        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 600");
        }
    }
    private String getQuitarCaracterPuntoOComa(String psCadena){
        StringBuilder ls = new StringBuilder(psCadena.length()-1);
        for(int i = 0 ; i < psCadena.length() ; i++){
            if(psCadena.charAt(i)!='.' && psCadena.charAt(i)!=',' ){
                ls.append(psCadena.charAt(i));
            }
        }
        return ls.toString();
    }
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoIndividual poIndividual) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);
        JCuentaBancaria loCCCIngreso=JCuadernos.validarCCC(poIndividual.getCCC(), poIndividual.getNIF(), "del registro individual", " y referencia " + poIndividual.getCodigoParaDevoluciones()); 
                
        
        if(poIndividual.getNombreTitularCredito()==null || poIndividual.getNombreTitularCredito().equals("")){
            throw new Exception("Nombre del titular del registro individual vacío");
        }
        loBuffer.append("03");
        loBuffer.append("SCT");
        loBuffer.append("34145");
        loBuffer.append("002");
        
        
        loBuffer.append(
            JFormat.msRellenarDer(
                new Identificador(poOrdenante.getNifOrden().toUpperCase(), poOrdenante.getSufijoOrden())
                        .getIdentificador(), " ", 35)                
        );
        loBuffer.append("A");
        loBuffer.append(JFormat.msRellenarDer(loCCCIngreso.getIBAN()," ",34));
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poIndividual.getImporte(), "000000000.00")));
       
        loBuffer.append("3");
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getBIC(), " ", 11));//BIC obligatorio para operaciones transfronterizas
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getNombreTitularCredito().toUpperCase(), " ", 70) );
        
        
        if(poOrdenante.hayRegistroDomicilio()){
            loBuffer.append(JFormat.msRellenarDer(poIndividual.getDomicilio().toUpperCase(), " ", 50) );
            loBuffer.append(
                    JFormat.msRellenarIzq(poIndividual.getCP(),"0",5)  + " " 
                    + JFormat.msRellenarDer(poIndividual.getLocalidad().toUpperCase()," ",44)  );
            loBuffer.append(JFormat.msRellenarDer(poIndividual.getProvincia().toUpperCase()," ",40));
            if(JCadenas.isVacio(poIndividual.getPAIS())){
                loBuffer.append(JFormat.msRellenarDer("ES"," ",2));
            }else{
                loBuffer.append(JFormat.msRellenarDer(poIndividual.getPAIS().toUpperCase()," ",2));
            }
        }else{
            loBuffer.append(JFormat.msRellenarDer("", " ", 50));
            loBuffer.append(JFormat.msRellenarDer("", " ", 50));
            loBuffer.append(JFormat.msRellenarDer("", " ", 40));     
            loBuffer.append(JFormat.msRellenarDer("", " ", 2));     
        }

        loBuffer.append(JFormat.msRellenarDer(poIndividual.getConcepto1().toUpperCase(), " ", 140));
        
        loBuffer.append(JFormat.msRellenarDer("", " ", 35));   
        
        if(poIndividual.isNomina()){
            loBuffer.append(JFormat.msRellenarDer("SEPA", " ", 4));   
        } else {
            loBuffer.append(JFormat.msRellenarDer("", " ", 4));   
        }
        loBuffer.append(JFormat.msRellenarDer("", " ", 4));   
        loBuffer.append(JFormat.msRellenarDer("", " ", 99));   
        

        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del individual " + String.valueOf(loBuffer.length()) + " y debería ser 600 de referencia " + poIndividual.getCodigoParaDevoluciones());
        }
    }
    private String sustituirCaracteresRaros(String psCADENA){
        String lsCadena = psCADENA
                .replaceAll("Á", "A").replaceAll("É", "E").replaceAll("Í", "I").replaceAll("Ó", "O").replaceAll("Ú", "U").replaceAll("Ñ", "N")
                .replaceAll("á", "a").replaceAll("é", "e").replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u").replaceAll("ñ", "n")
                .replace('\\', '?')
                ;
        String lsCaracValidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/?-:().,+ ";
        StringBuilder lsResult = new StringBuilder();
        
        for(int i = 0 ; i < lsCadena.length();i++){
            char c = lsCadena.charAt(i);
            if(lsCaracValidos.indexOf(c)>=0){
                lsResult.append(c);
            }else{
                lsResult.append(' ');
            }
        }
        return lsResult.toString();
    }
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoTotalOrdenante poTotalOrd, int plSubTotal) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);

        loBuffer.append("04");
        loBuffer.append("SCT");
        
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poTotalOrd.getSuma(), "000000000000000.00")));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotalOrd.getNumeroIndividuales()),"0",8));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(plSubTotal),"0",10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 560));


        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del total ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 600");
        }
    }
    
    private void exportar99(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoTotalOrdenante poTotalOrd, int plSubTotal) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);

        loBuffer.append("99");
        loBuffer.append("ORD");
        
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poTotalOrd.getSuma(), "000000000000000.00")));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotalOrd.getNumeroIndividuales()),"0",8));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(plSubTotal),"0",10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 560));


        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del total ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 600");
        }
    }

}
