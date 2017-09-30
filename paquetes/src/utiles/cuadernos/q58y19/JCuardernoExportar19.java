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

public class JCuardernoExportar19 {
    private JCuadernos moCuaderno;
    private String msFichero;

    public JCuardernoExportar19(){
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
            int lTotal = 0;
            exportar(loPrint, moCuaderno.getPresentador());
            lTotal++;
            for(int i = 0 ; i < moCuaderno.getOrdenantes().size();i++){
                int lSubTotal=0;
                JCuadernoOrdenante loORD = (JCuadernoOrdenante) moCuaderno.getOrdenantes().get(i);
                exportar(loPrint, loORD);
                lTotal++;
                lSubTotal++;
                for(int l = 0 ; l < loORD.getIndividuales().size();l++){
                    JCuadernoIndividual loIND = (JCuadernoIndividual) loORD.getIndividuales().get(l);
                    exportar(loPrint, loORD, loIND);
                    lTotal++;
                    lSubTotal++;
                    if(!JCadenas.isVacio(loIND.getMANDATOANTERIOR()) 
                            || !JCadenas.isVacio(loIND.getCCCANTERIOR()) 
                            || !JCadenas.isVacio(loIND.getNIFANTERIOR()) 
                            || !JCadenas.isVacio(loIND.getNombreTitularCreditoANTERIOR()) 
                            ){
                        exportarMandatoCambio(loPrint, loORD, loIND);
                        lTotal++;
                        lSubTotal++;
                    }
                }
                exportar(loPrint,loORD, loORD.getTotalDomicilioJUNTO(), lSubTotal+1);
                lTotal++;
                lSubTotal++;
                exportar2(loPrint,loORD, loORD.getTotalDomicilioJUNTO2(), lSubTotal+1);
                lTotal++;
                lSubTotal++;
            }
            exportar(loPrint, moCuaderno.getPresentador(), moCuaderno.getTotalJUNTO(), lTotal+1);
        }finally{
            if(loPrint!=null){
                loPrint.close();
            }
        }

    }

    private void exportar(PrintWriter poPrint, JCuadernoPresentador poPresentador) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);
        JCuentaBancaria loCuentaPresentador=JCuadernos.validarCCC(poPresentador.getCCC(), poPresentador.getNifPresen(), "del presentador", ""); 
        
        loBuffer.append("01");
        loBuffer.append("19143");
        loBuffer.append( "001");
        loBuffer.append(JFormat.msRellenarDer(
                new Identificador(poPresentador.getNifPresen().toUpperCase(), poPresentador.getSufijoPresen())
                        .getIdentificador(), " ", 35));
        loBuffer.append(JFormat.msRellenarDer(poPresentador.getNombrePresentador().toUpperCase(), " " , 70));
        loBuffer.append(poPresentador.getFechaConcepcion().msFormatear("yyyyMMdd"));
        
        //identificacion fichero
        //esta referencia se estructurará de la siguiente manera , tomando los datos generados por
        //el ordenador del presentador en el momento de la creación del fichero
        //Indicador del tipo de mensaje (3 caracteres)
        //AAAAMMDD (año, mes y día) =(8 caracteres)
        //HHMMSSmmmmm (hora minuto segundo y 5 posiciones de milisegundos = 11 caracteres)
        //Referencia identificativa que asigne el presentador (13 caracteres)

        loBuffer.append(
                "PRE" 
                + poPresentador.getFechaConcepcion().msFormatear("yyyyMMddHHmmss")+"00000"
                +JFormat.msRellenarDer(poPresentador.getSufijoPresen()+poPresentador.getNifPresen().toUpperCase(), " " , 13));

        loBuffer.append(JFormat.msRellenarIzq(loCuentaPresentador.getCCC().toUpperCase(),"0",8));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 434));

        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.toString().length()!=600){
            throw new Exception("Long. del registro del presentador " + String.valueOf(loBuffer.length()) + " y debería ser 600 de NIF " + poPresentador.getNifPresen().toUpperCase());
        }
    }
 
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);
        JCuentaBancaria loCuentaOrdenante=JCuadernos.validarCCC(poOrdenante.getCCC(), poOrdenante.getNifOrden(), "del ordenante", ""); 
        if(poOrdenante.getNombreOrdenante()==null || poOrdenante.getNombreOrdenante().equals("")){
            throw new Exception("Nombre del ordenante vacío");
        }
        loBuffer.append("02");
        loBuffer.append("19143");
        loBuffer.append("002");
        loBuffer.append(
            JFormat.msRellenarDer(
                new Identificador(poOrdenante.getNifOrden().toUpperCase(), poOrdenante.getSufijoOrden())
                        .getIdentificador(), " ", 35)                
        );
        loBuffer.append(poOrdenante.getFechaCargo().msFormatear("yyyyMMdd"));
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
        loBuffer.append(JFormat.msRellenarDer(loCuentaOrdenante.getIBAN()," ",34));
        loBuffer.append(JFormat.msRellenarDer("", " ", 301));     
//        
//        
//        loBuffer.append(JFormat.msRellenarIzq(poOrdenante.getSufijoOrden().toUpperCase(), "0", 3));
//
//        loBuffer.append(JFormat.msFormatearDouble(poOrdenante.getProcedimiento(), "00"));
//        

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
    private void exportarMandatoCambio(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoIndividual poIndividual) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);
        

        loBuffer.append("03");
        loBuffer.append("19143");
        loBuffer.append("006");
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoParaDevoluciones().toUpperCase(), " ", 35));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoReferenciaClienteOMANDATO().toUpperCase(), " ", 35));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getMANDATOANTERIOR().toUpperCase(), " ", 35));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getNombreTitularCreditoANTERIOR().toUpperCase(), " ", 70));
        
        
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getNIFANTERIOR(), " ", 35));     

        JCuentaBancaria loCCCAdeudo; 
        JCuentaBancaria loCCCAnterior;
        
        if(JCuentaBancaria.validarCuenta(poIndividual.getCCCANTERIOR())
                && JCuentaBancaria.validarCuenta(poIndividual.getCCC())
                ){
            loCCCAdeudo = new JCuentaBancaria(poIndividual.getCCC());
            loCCCAnterior = new JCuentaBancaria(poIndividual.getCCCANTERIOR());
            
            if(loCCCAnterior.getEntidad().equalsIgnoreCase(loCCCAdeudo.getEntidad())
                    && !loCCCAnterior.getIBAN().equalsIgnoreCase(loCCCAdeudo.getIBAN())){
                loBuffer.append(JFormat.msRellenarDer(loCCCAnterior.getIBAN()," ",34));
            }else{
                loBuffer.append(JFormat.msRellenarDer("", " ", 34 ));
            }
            
            if(poIndividual.isPrimeraVez()
                    && !loCCCAnterior.getEntidad().equalsIgnoreCase(loCCCAdeudo.getEntidad())){
                loBuffer.append(JFormat.msRellenarDer("SMNDA", " ", 5 ));
            }else{
                loBuffer.append(JFormat.msRellenarDer("", " ", 5 ));
            }
        }else{
            loBuffer.append(JFormat.msRellenarDer("", " ", 34 ));
            loBuffer.append(JFormat.msRellenarDer("", " ", 5 ));            
        }
        
        loBuffer.append(JFormat.msRellenarDer("", " ", 341 ));
        

        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del individual (MANDATO) " + String.valueOf(loBuffer.length()) + " y debería ser 600 de referencia " + poIndividual.getCodigoParaDevoluciones());
        }
    }
    private void exportar(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoIndividual poIndividual) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);
        JCuentaBancaria loCCCAdeudo=JCuadernos.validarCCC(poIndividual.getCCC(), poIndividual.getNIF(), "del registro individual", " y referencia " + poIndividual.getCodigoParaDevoluciones()); 
                
        
        if(poIndividual.getNombreTitularCredito()==null || poIndividual.getNombreTitularCredito().equals("")){
            throw new Exception("Nombre del titular del registro individual vacío");
        }
        loBuffer.append("03");
        loBuffer.append("19143");
        loBuffer.append("003");
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoParaDevoluciones().toUpperCase(), " ", 35));
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getCodigoReferenciaClienteOMANDATO().toUpperCase(), " ", 35));
        if(poIndividual.isPrimeraVez()){
            loBuffer.append(JFormat.msRellenarDer("FRST", " ", 4)); 
        }else{
            loBuffer.append(JFormat.msRellenarDer("RCUR", " ", 4)); 
        }
        loBuffer.append(JFormat.msRellenarDer("TRAD", " ", 4)); 
        
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poIndividual.getImporte(), "000000000.00")));
       
        if(poIndividual.getFechaOrigen() == null){
            loBuffer.append("20091031");
        }else{
            loBuffer.append(poIndividual.getFechaOrigen().msFormatear("yyyyMMdd"));
        }
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getBIC(), " ", 11));//BIC deudor obligatorio para operaciones transfronterizas
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
        if(CIF_NIF.isCIF(poIndividual.getNIF())){
            loBuffer.append("1");     
            loBuffer.append(JFormat.msRellenarDer("I"+poIndividual.getNIF(), " ", 36));     
//            loBuffer.append(JFormat.msRellenarDer(poIndividual.getNIF(), " ", 36));     
        }else{
            loBuffer.append("2");     
            loBuffer.append(JFormat.msRellenarDer("J"+poIndividual.getNIF(), " ", 36));     
//            loBuffer.append(JFormat.msRellenarDer(poIndividual.getNIF(), " ", 36));     
        }
        loBuffer.append(JFormat.msRellenarDer("", " ", 35));     
        loBuffer.append("A");     
        loBuffer.append(JFormat.msRellenarDer(loCCCAdeudo.getIBAN()," ",34));
        loBuffer.append(JFormat.msRellenarDer("GDDS", " ", 4)); 
        loBuffer.append(JFormat.msRellenarDer(poIndividual.getConcepto1().toUpperCase(), " ", 140));
        loBuffer.append(JFormat.msRellenarDer("", " ", 19));
        

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
        
        loBuffer.append(
            JFormat.msRellenarDer(
                new Identificador(poOrdenante.getNifOrden().toUpperCase(), poOrdenante.getSufijoOrden())
                        .getIdentificador(), " ", 35)                
        );
        loBuffer.append(poOrdenante.getFechaCargo().msFormatear("yyyyMMdd"));
        
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poTotalOrd.getSuma(), "000000000000000.00")));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotalOrd.getNumeroIndividuales()),"0",8));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(plSubTotal),"0",10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 520));


        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del total ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 600");
        }
    }
    private void exportar2(PrintWriter poPrint, JCuadernoOrdenante poOrdenante, JCuadernoTotalOrdenante poTotalOrd, int plSubTotal) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);

        loBuffer.append("05");
        
        loBuffer.append(
            JFormat.msRellenarDer(
                new Identificador(poOrdenante.getNifOrden().toUpperCase(), poOrdenante.getSufijoOrden())
                        .getIdentificador(), " ", 35)                
        );
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poTotalOrd.getSuma(), "000000000000000.00")));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotalOrd.getNumeroIndividuales()),"0",8));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(plSubTotal),"0",10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 528));


        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del total ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 600");
        }
    }

    private void exportar(PrintWriter poPrint, JCuadernoPresentador poPresen, JCuadernoTotal poTotal, int plTotalRegistros) throws Exception{
        StringBuilder loBuffer = new StringBuilder(600);

        loBuffer.append("99");
        loBuffer.append(getQuitarCaracterPuntoOComa(JFormat.msFormatearDouble(poTotal.getSuma(), "000000000000000.00")));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(poTotal.getNumeroIndividuales()),"0",8));
        loBuffer.append(JFormat.msRellenarIzq(String.valueOf(plTotalRegistros),"0",10));
        loBuffer.append(JFormat.msRellenarIzq("", " ", 563));


        poPrint.println(sustituirCaracteresRaros(loBuffer.toString()));
        if(loBuffer.length()!=600){
            throw new Exception("Long. del registro del total ordenante " + String.valueOf(loBuffer.length()) + " y debería ser 600");
        }
        
        
    }

}
