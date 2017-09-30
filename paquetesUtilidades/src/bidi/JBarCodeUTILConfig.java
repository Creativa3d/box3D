/*
 * JBarCodeUtilConfig.java
 *
 * Created on 11 de agosto de 2008, 12:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bidi;


public class JBarCodeUTILConfig {
    //PDFConfig
    public static final String mcsPDFConfig = "PDFConfig";
    public static final String mcsPDFConfigMaxCols=mcsPDFConfig+"MaxCols";
    public static final String mcsPDFConfigMinCols=mcsPDFConfig+"MinCols";
    public static final String mcsPDFConfigMinRows=mcsPDFConfig+"MinRows";
    public static final String mcsPDFConfigMaxRows=mcsPDFConfig+"MaxRows";
    
//    public static final String mcsPDFConfigHeight=mcsPDFConfig+"Height";
    public static final String mcsPDFConfigBarHeight=mcsPDFConfig+"BarHeight";
    public static final String mcsPDFConfigErrorCorrectionLevel=mcsPDFConfig+"ErrorCorrectionLevel";
    
    public static final String mcsPDFConfigModuleWidth=mcsPDFConfig+"ModuleWidth"; //makes the narrow bar
    public static final String mcsPDFConfigWidthToHeightRatio=mcsPDFConfig+"WidthToHeightRatio";
    
    public static final String mcsPDFConfigdpi =mcsPDFConfig+"dpi";
    
    
    /** Creates a new instance of JBarCodeUtilConfig */
    public JBarCodeUTILConfig() {
    }
    
    
    public int getPDFConfigMaxCols(){
        int lResult = 30;
//        try{
//
//            lResult = Integer.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigMaxCols)).intValue();
//        }catch(Exception e){
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigMaxCols,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
        
        
    public int getPDFConfigMinCols(){
        int lResult = 1;
//        try{
//            lResult = Integer.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigMinCols)).intValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigMinCols,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
        
    public int getPDFConfigMinRows(){
        int lResult = 3;
//        try{
//            lResult = Integer.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigMinRows)).intValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigMinRows,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
   
        
    public int getPDFConfigMaxRows(){
        int lResult = 90;
//        try{
//            lResult = Integer.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigMaxRows)).intValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigMaxRows,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
        
    public int getPDFConfigErrorCorrectionLevel(){
        int lResult = 3;
//        try{
//            lResult = Integer.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigErrorCorrectionLevel)).intValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigErrorCorrectionLevel,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
        
//    public double getPDFConfigHeight(){
//        double lResult = 1.4;
//        try{
//            lResult = Double.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigHeight)).doubleValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig,mcsPDFConfigHeight,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardar();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
//        return lResult;
//    }
    public double getPDFConfigBarHeight(){
        double lResult = 1.0583333333333331;
//        try{
//            lResult = Double.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigBarHeight)).doubleValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigBarHeight,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }

    public double getPDFConfigModuleWidth(){
        double lResult = 0.35277777777777775;
//        try{
//            lResult = Double.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigModuleWidth)).doubleValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigModuleWidth,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
////                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
    public double getPDFConfigWidthToHeightRatio(){
        double lResult = 1.7;
//        try{
//            lResult = Double.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigWidthToHeightRatio)).doubleValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigWidthToHeightRatio,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
        
    public int getPDFConfigdpi(){
        int lResult = 72;
//        try{
//            lResult = Integer.valueOf(JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().getPropiedad(mcsPDFConfigdpi)).intValue();
//        }catch(Exception e){
//
//            try {
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().setPropiedad(
//                        mcsPDFConfig+"/"+mcsPDFConfigdpi,
//                        String.valueOf(lResult));
//                JDatosGeneralesP.getDatosGenerales().getDatosGeneralesXML().guardarFichero();
//            } catch (Exception ex) {
//                JDepuracion.anadirTexto(getClass().getName(), ex);
//            }
//        }
        return lResult;
    }
    
    
}
