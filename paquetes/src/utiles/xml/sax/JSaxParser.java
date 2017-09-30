/*
 * JSaxParser.java
 *
 * Created on 12 de enero de 2005, 11:12
 */

package utiles.xml.sax;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import utiles.*;

/**Parseador del xml*/
public class JSaxParser {
    
    public static final char mccMenor = '<';
    public static final String mcsMenor = "&lt;";
    
    public static final char mccMayor = '>';
    public static final String mcsMayor = "&gt;";
    
    public static final char mccAmpersan = '&';
    public static final String mcsAmpersan = "&amp;";
    
    public static final char mccComillaDoble = '"';
    public static final String mcsComillaDoble = "&quot;";
    
    public static final char mccApostrofe = '\'';
    public static final String mcsApostrofe = "&apos;";
    
    public static final char mccaAcento = 'á';
    public static final String mcsaAcento = "&aacute;";
    
    public static final char mcceAcento = 'é';
    public static final String mcseAcento = "&eacute;";
    
    public static final char mcciAcento = 'í';
    public static final String mcsiAcento = "&iacute;";
    
    public static final char mccoAcento = 'ó';
    public static final String mcsoAcento = "&oacute;";
    
    public static final char mccuAcento = 'ú';
    public static final String mcsuAcento = "&uacute;";
    
    public static final char mccAAcento = 'Á';
    public static final String mcsAAcento = "&Aacute;";
    
    public static final char mccEAcento = 'É';
    public static final String mcsEAcento = "&Eacute;";
    
    public static final char mccIAcento = 'Í';
    public static final String mcsIAcento = "&Iacute;";
    
    public static final char mccOAcento = 'Ó';
    public static final String mcsOAcento = "&Oacute;";
    
    public static final char mccUAcento = 'Ú';
    public static final String mcsUAcento = "&Uacute;";
    
    public static final char mccene = 'ñ';
    public static final String mcsene = "&ntilde;";
     	
    public static final char mccENE = 'Ñ';
    public static final String mcsENE = "&Ntilde;";
     	
    

    private static final char mcRetornoCarro = System.getProperty("line.separator").charAt(0);

    //Estados
    private static final int mclNada = 0;
    private static final int mclMenorEncontrado = 1;
    private static final int mclMenorConInterrogacion = 2;
    private static final int mclMenorConInterrogacionYInterr = 3;
    private static final int mclEtiqueta = 4;
    private static final int mclEtiquetaBusqAtr = 5;
    private static final int mclEtiquetaAutocontenida = 6;
    private static final int mclEtiquetaValor = 7;
    private static final int mclEtiquetaFinInicio = 8;
    private static final int mclEtiquetaFinLeer = 9;
    private static final int mclAtributoNombre = 10;
    private static final int mclAtributoIgual = 11;
    private static final int mclAtributoValor = 12;
    private static final int mclEtiquetaIniIgnorar = 13;
    private static final int mclEtiquetaFinIgnorar = 14;
    
    private int mlIndex;
    private JListaElementos moEtiquetas=null;
    private int mlEstado;
    private byte[] mabBuffer = new byte[1024];
    private int mlBufferSize=-1;
    private int mlBufferIndex=-1;
    private String msBuffer;
    
    public JSaxParser(){
        super();
    }
    /**
     * parsea el texto pasado por parametro haciendo llamadas a la fuente de las etiq.
     *        que se cumplen
     * @param psCadena cadena a parsear
     * @param poFuente donde se lanazan los eventos
     * @throws Exception error
     */
    public void parser(final String psCadena, final ISax poFuente) throws Exception {
        parser(new CadenaLarga(psCadena), poFuente);
    }
    /**
     * parsea el texto pasado por parametro haciendo llamadas a la fuente de las etiq.
     *        que se cumplen
     * @param poEntrada flujo de entrada
     * @param poFuente donde se lanzan los eventos
     * @throws Exception error
     */
    public void parser(final InputStream poEntrada, final ISax poFuente) throws Exception {
        moEtiquetas = new JListaElementos();
        mlIndex = 0;
        mlEstado = mclNada;

        poFuente.startDocument();
        
        StringBuilder lsValor=new StringBuilder();
        StringBuilder lsEtiq=new StringBuilder();
        StringBuilder lsAtrib=new StringBuilder();
        StringBuilder lsAtribValor=new StringBuilder();
        JEtiqueta loEtiqueta=null;
        int lCaracter = getRead(poEntrada); 
        char lcCaracter;
        while(lCaracter!=-1){
            lcCaracter = (char)lCaracter;
            switch(mlEstado){
                case mclNada:
                    if (!isCaracterNeutro(lcCaracter)){
                        if(lcCaracter=='<'){
                            mlEstado = mclMenorEncontrado;
                        }else{
                            throw new Exception("Inicio de etiqueta incorrecta (Caracter="+String.valueOf(mlIndex)+")");
                        }
                    }
                    break;
                case mclEtiquetaIniIgnorar:
                    switch(lcCaracter){
                        case '-':
                            mlEstado = mclEtiquetaFinIgnorar;
                            break;
                        default:
                            //ignorar
                    }
                    break;
                case mclEtiquetaFinIgnorar:
                    switch(lcCaracter){
                        case '-':
                            break;
                        case '>':
                            mlEstado = mclNada;
                            break;
                        default:
                            mlEstado = mclEtiquetaIniIgnorar;
                    }
                    break;
                case mclMenorEncontrado:
                    switch(lcCaracter){
                        case '?':
                            mlEstado = mclMenorConInterrogacion;
                            break;
                        case '/':
                            mlEstado = mclEtiquetaFinLeer;
                            break;
                        case '!':
                            mlEstado = mclEtiquetaIniIgnorar;
                            break;
                        default:
                            lsEtiq.setLength(0);
                            lsEtiq.append(lcCaracter);
                            mlEstado = mclEtiqueta;
                            loEtiqueta = new JEtiqueta();
                            moEtiquetas.add(loEtiqueta);
                    }
                    break;
                case mclMenorConInterrogacion:
                    if(lcCaracter == '?'){
                        mlEstado = mclMenorConInterrogacionYInterr;
                    }
                    break;
                case mclMenorConInterrogacionYInterr:
                    if(lcCaracter == '>'){
                        mlEstado = mclNada;
                    }else{
                        throw new Exception("Error despues de segunda interrogación debe haber un >(Caracter"+String.valueOf(mlIndex)+")");
                    }
                    break;
                case mclEtiqueta:
                    if (isCaracterNeutro(lcCaracter)){
                        loEtiqueta.setNombre(lsEtiq.toString());
                        mlEstado = mclEtiquetaBusqAtr;
                    }else{
                        switch(lcCaracter){
                            case '/':
                                mlEstado = mclEtiquetaAutocontenida;
                                loEtiqueta.setNombre(lsEtiq.toString());
                                poFuente.startElement(loEtiqueta.getNombre(),loEtiqueta.getAtributos());
                                lsValor.setLength(0);
                                break;
                            case '>':
                                mlEstado = mclEtiquetaValor;
                                loEtiqueta.setNombre(lsEtiq.toString());
                                poFuente.startElement(loEtiqueta.getNombre(), loEtiqueta.getAtributos());
                                lsValor.setLength(0);
                                break;
                            default:
                                lsEtiq.append(lcCaracter);
                        }
                    }
                    break;
                case mclEtiquetaBusqAtr:
                    if (!isCaracterNeutro(lcCaracter)){
                        switch(lcCaracter){
                            case '>':
                                poFuente.startElement(loEtiqueta.getNombre(), loEtiqueta.getAtributos());
                                mlEstado = mclEtiquetaValor;
                                break;
                            case '/':
                                poFuente.startElement(loEtiqueta.getNombre(), loEtiqueta.getAtributos());
                                mlEstado = mclEtiquetaAutocontenida;
                                break;
                            default:
                                lsAtrib.setLength(0);
                                lsAtrib.append(lcCaracter);
                                mlEstado = mclAtributoNombre;
                        }
                    }
                    break;
                case mclEtiquetaAutocontenida:
                    if (!isCaracterNeutro(lcCaracter)){
                        switch(lcCaracter){
                            case '>':
                                lsEtiq.setLength(0);
                                loEtiqueta = finElemento(loEtiqueta, poFuente);
                                mlEstado = mclNada;
                                break;
                            default:
                                throw new Exception("Error despues de / debe haber un >(Caracter"+String.valueOf(mlIndex)+")");
                        }
                    }
                    break;
                case mclEtiquetaValor:
                    switch(lcCaracter){
                        case '<':
                            loEtiqueta.setValor(lsValor.toString());
                            lsValor.setLength(0);
                            mlEstado = mclEtiquetaFinInicio;
                            break;
                        default:
                            lsValor.append(lcCaracter);
                    }
                    break;
                case mclEtiquetaFinInicio:
                    if (!isCaracterNeutro(lcCaracter)){
                        switch(lcCaracter){
                            case '!':
                                mlEstado = mclEtiquetaIniIgnorar;
                                break;
                            case '/':
                                mlEstado = mclEtiquetaFinLeer;
                                lsEtiq.setLength(0);
                                break;
                            default:
                                //Comienzo de etiqueta hija
                                lsEtiq.setLength(0);
                                lsEtiq.append(lcCaracter);
                                mlEstado = mclEtiqueta;
                                loEtiqueta = new JEtiqueta();
                                moEtiquetas.add(loEtiqueta);
                        }
                    }
                    break;
                case mclEtiquetaFinLeer:
                    if (!isCaracterNeutro(lcCaracter)){
                        switch(lcCaracter){
                            case '>':
                                mlEstado = mclNada;
                                if(lsEtiq.toString().compareTo(loEtiqueta.getNombre())!=0){
                                    throw new Exception("Error el nombre de la etiqueta "+lsEtiq.toString()+" no coincide con el comienzo "+loEtiqueta.getNombre()+"(Caracter"+String.valueOf(mlIndex)+")");
                                }
                                lsEtiq.setLength(0);
                                loEtiqueta = finElemento(loEtiqueta, poFuente);
                                break;
                            default:
                                lsEtiq.append(lcCaracter);
                        }
                    }
                    break;
                case mclAtributoNombre:
                    if (!isCaracterNeutro(lcCaracter)){
                        switch(lcCaracter){
                            case '=':
                                mlEstado = mclAtributoIgual;
                                break;
                            default:
                                lsAtrib.append(lcCaracter);
                        }
                    }
                    
                    break;
                case mclAtributoIgual:
                    if (!isCaracterNeutro(lcCaracter)){
                        switch(lcCaracter){
                            case '"':
                            case '\'':
                                mlEstado = mclAtributoValor;
                                lsAtribValor.setLength(0);
                                break;
                            default:
                                throw new Exception("Error despues de = debe haber una \" (Caracter"+String.valueOf(mlIndex)+")");
                        }
                    }
                    break;
                case mclAtributoValor:
                    switch(lcCaracter){
                        case '"':
                        case '\'':
                            mlEstado = mclEtiquetaBusqAtr;
                            loEtiqueta.getAtributos().addAtributo(lsAtrib.toString(), lsAtribValor.toString());
                            lsAtrib.setLength(0);
                            lsAtribValor.setLength(0);
                            break;
                        default:
                            lsAtribValor.append(lcCaracter);
                    }
                    break;
                default:
                    throw new Exception("Estado incorrecto");
            }
            lCaracter = getRead(poEntrada);
            mlIndex++;
        }
        poFuente.endDocument();
    }
    private int getRead(final InputStream poEntrada) throws IOException{
        if(mlBufferIndex>=mlBufferSize || mlBufferSize==-1){
            mlBufferSize=poEntrada.read(mabBuffer);
            mlBufferIndex=0;
            msBuffer = new String(mabBuffer,("ISO-8859-1"));
        }
        if(mlBufferSize>0){
            int lCarat=msBuffer.charAt(mlBufferIndex);
            mlBufferIndex++;
            return lCarat;
        }else{
            return -1;
        }
//        return poEntrada.read();
        
    }
    private JEtiqueta finElemento(final JEtiqueta poEtiqueta, final ISax poFuente) throws Exception {
        JEtiqueta loEtiq = null;
        poFuente.endElement(poEtiqueta);
//        System.out.println(poEtiqueta.getNombre() + "=" + poEtiqueta.getValor());
        moEtiquetas.remove(moEtiquetas.size()-1);
        if(moEtiquetas.size()>0){
            loEtiq = (JEtiqueta)moEtiquetas.get(moEtiquetas.size()-1);
        }
        return loEtiq;
        
    }
    private boolean isCaracterNeutro(final char pcCaracter){
        return (
                (Character.isWhitespace(pcCaracter))||
                (pcCaracter==' ')||
                (pcCaracter==mcRetornoCarro));
    }
    public static String reemplazarCaracRarosAEncode(final String psXml, final boolean pnIncluirAcentosYenes){
        return reemplazarCaracRarosAEncode(psXml, pnIncluirAcentosYenes, true);
    }

    public static String reemplazarCaracRarosAEncode(final String psXml, final boolean pnIncluirAcentosYenes, final boolean pnIncluirMayorMenorYDemas){
        if(psXml==null){
            return "";
        }else{
            String lsXml = psXml;
            if(pnIncluirMayorMenorYDemas){
                lsXml = msReemplazarCaracAEncode(psXml,mcsMayor,mccMayor);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsMenor,mccMenor);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsAmpersan,mccAmpersan);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsApostrofe,mccApostrofe);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsComillaDoble,mccComillaDoble);
            }
            if(pnIncluirAcentosYenes){
                lsXml = msReemplazarCaracAEncode(lsXml,mcsAAcento,mccAAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsEAcento,mccEAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsIAcento,mccIAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsOAcento,mccOAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsUAcento,mccUAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsaAcento,mccaAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcseAcento,mcceAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsiAcento,mcciAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsoAcento,mccoAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsuAcento,mccuAcento);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsENE,mccENE);
                lsXml = msReemplazarCaracAEncode(lsXml,mcsene,mccene);
            }
            
            return lsXml;
        }
    }
    public static String reemplazarCaracRaros(final String psXml){
        if(psXml==null){
            return "";
        }else{
            String lsXml = msReemplazarCarac(psXml,mcsMayor,mccMayor);
            lsXml = msReemplazarCarac(lsXml,mcsMenor,mccMenor);
            lsXml = msReemplazarCarac(lsXml,mcsAmpersan,mccAmpersan);
            lsXml = msReemplazarCarac(lsXml,mcsApostrofe,mccApostrofe);
            lsXml = msReemplazarCarac(lsXml,mcsComillaDoble,mccComillaDoble);
            
            lsXml = msReemplazarCarac(lsXml,mcsAAcento,mccAAcento);
            lsXml = msReemplazarCarac(lsXml,mcsEAcento,mccEAcento);
            lsXml = msReemplazarCarac(lsXml,mcsIAcento,mccIAcento);
            lsXml = msReemplazarCarac(lsXml,mcsOAcento,mccOAcento);
            lsXml = msReemplazarCarac(lsXml,mcsUAcento,mccUAcento);
            lsXml = msReemplazarCarac(lsXml,mcsaAcento,mccaAcento);
            lsXml = msReemplazarCarac(lsXml,mcseAcento,mcceAcento);
            lsXml = msReemplazarCarac(lsXml,mcsiAcento,mcciAcento);
            lsXml = msReemplazarCarac(lsXml,mcsoAcento,mccoAcento);
            lsXml = msReemplazarCarac(lsXml,mcsuAcento,mccuAcento);
            
            lsXml = msReemplazarCarac(lsXml,mcsENE,mccENE);
            lsXml = msReemplazarCarac(lsXml,mcsene,mccene);
            return lsXml;
        }
    }
    private static String msReemplazarCaracAEncode(final String psXml, final String psCad, final char pcCarac){
        if(psXml==null){
            return "";
        }else{
            int lPosi=0;
            int lPosiAnt=0;
            StringBuilder lsXml=new StringBuilder(psXml.length());
            lPosi=psXml.indexOf(pcCarac);
            while(lPosi!=-1){
                lsXml.append(psXml.substring(lPosiAnt, lPosi));
                lsXml.append(psCad);
                lPosiAnt = lPosi + 1;
                lPosi=psXml.indexOf(pcCarac,lPosiAnt);
            }
            lsXml.append(psXml.substring(lPosiAnt));
            return lsXml.toString();
        }
    }
    
    private static String msReemplazarCarac(final String psXml, final String psCad, final char pcCarac ){
        if(psXml==null){
            return "";
        }else{
            int lPosi=0;
            int lPosiAnt=0;
            StringBuilder lsXml=new StringBuilder(psXml.length());
            lPosi=psXml.indexOf(psCad);
            while(lPosi!=-1){
                lsXml.append(psXml.substring(lPosiAnt, lPosi));
                lsXml.append(pcCarac);
                lPosiAnt = lPosi + psCad.length();
                lPosi=psXml.indexOf(psCad,lPosiAnt);
            }
            lsXml.append(psXml.substring(lPosiAnt));
            return lsXml.toString();
        }
    }
}
