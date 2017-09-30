/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conversor.plugin;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utiles.JArchivo;

/**
 *
 * @author eduardo
 */
public class JConversorPlugIn20130913 {
    public String msCodificacion = "ISO-8859-1";
    
    public void recorrer(File poFile) throws Exception{
        File[] loFiles = poFile.listFiles();
        
        for(int i = 0 ; i < loFiles.length;i++){
            File loFileAux = loFiles[i];
            
            if(loFileAux.isDirectory()){
                recorrer(loFileAux);
            }else{
                if(loFileAux.getName().endsWith("java")){
                    System.out.println(loFileAux.getAbsolutePath());
                    procesar(loFileAux);
                }
            }
            
        }
        
    }

    private void procesar(File loFileAux) throws Exception {
        String lsCadena = new String(JArchivo.getArchivoEnBytes(loFileAux.getAbsolutePath()), msCodificacion);
        boolean lbImpor = false;
        boolean lbAlgo = false;
        Pattern patron;
        Pattern patron2;
        Pattern patron3;
        Pattern patron4;
        Matcher loMat;
        patron = Pattern.compile("extends\\s*JPanelGENERALBASE");
        patron2 = Pattern.compile("extends\\s*utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE");
        patron3 = Pattern.compile("extends\\s*utilesGUIx.formsGenericos.edicion.JPanelEdicionAbstract");
        patron4 = Pattern.compile("extends\\s*JPanelEdicionAbstract");
        if(patron.matcher(lsCadena).find() || patron2.matcher(lsCadena).find() 
                || patron3.matcher(lsCadena).find() || patron4.matcher(lsCadena).find()){
            lbImpor = true;
        }        
        patron = Pattern.compile("extends\\s*[A-Za-z0-9.]*JT2GENERALBASE");
        patron2 = Pattern.compile("implements\\s*IEjecutar");
        patron3 = Pattern.compile("new\\s*IEjecutarExtend");
        patron4 = Pattern.compile("implements\\s*IPanelControlador");
        Pattern patron5 = Pattern.compile("new\\s*JT2GENERALBASE");
        
        if(patron.matcher(lsCadena).find() || patron2.matcher(lsCadena).find() 
                || patron3.matcher(lsCadena).find() || patron4.matcher(lsCadena).find()
                || patron5.matcher(lsCadena).find()
                ){        
            lbImpor = true;
        }
        patron = Pattern.compile("implements\\s*IPlugIn");
        if(patron.matcher(lsCadena).find()){        
            lbImpor = true;  
        }

//        if(lbImpor){
//            lbAlgo=true;
//            //priemro borramos, luegoo añadimos
//            patron = Pattern.compile("import\\s*utilesGUIx.plugin.toolBar.IComponenteAplicacion;");
//            loMat = patron.matcher(lsCadena);
//            lsCadena = loMat.replaceAll("");
//            //priemro borramos, luegoo añadimos
//            patron = Pattern.compile("import\\s*utilesGUIx.Rectangulo;");
//            loMat = patron.matcher(lsCadena);
//            lsCadena = loMat.replaceAll("");
//            //priemro borramos, luegoo añadimos
//            patron = Pattern.compile("import\\s*utilesGUIx.ActionEventCZ;");
//            loMat = patron.matcher(lsCadena);
//            lsCadena = loMat.replaceAll("");
//
//            //import
//            int l = lsCadena.indexOf("import ");
//            if(l>=0){
//                lsCadena = lsCadena.substring(0, l)
//                        + "import utilesGUIx.plugin.toolBar.IComponenteAplicacion;\n"
//                        +  lsCadena.substring(l);
//
//                //import
//                lsCadena = lsCadena.substring(0, l)
//                        + "import utilesGUIx.ActionEventCZ;\n"
//                        +  lsCadena.substring(l);
//
//                //import
//                lsCadena = lsCadena.substring(0, l)
//                        + "import utilesGUIx.Rectangulo;\n"
//                        +  lsCadena.substring(l);
//            }
//        }
        patron = Pattern.compile("\\s*import\\s*utilesGUIx.plugin.IPlugInFramePrincipal\\s*;\\s*");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("");

        patron = Pattern.compile("\\(\\s*IPlugInFramePrincipal\\s*\\)\\s*");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("");


        patron = Pattern.compile("JPlugInUtilidades.addMenus\\s*\\(\\s*poContexto.getFormPrincipal");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("JPlugInUtilidades.addMenusFrame(poContexto.getFormPrincipal");

        patron = Pattern.compile("JPlugInUtilidades.getMenu\\s*\\(\\s*poContexto.getFormPrincipal\\s*\\(\\s*\\).getMenu\\s*\\(\\s*\\)\\s*,");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("poContexto.getFormPrincipal().getListaComponentesAplicacion().getComponente(IComponenteAplicacion.mcsGRUPOMENU,");

        patron = Pattern.compile("public\\s*void\\s*actionPerformed\\s*\\(\\s*ActionEvent\\s*e\\s*,\\s*int\\s*\\[\\s*\\]");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("public void actionPerformed(ActionEventCZ e, int[]");

        patron = Pattern.compile("public\\s*void\\s*actionPerformed\\s*\\(\\s*final\\s*ActionEvent\\s*e\\s*,\\s*final\\s*int\\s*\\[\\s*\\]");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("public void actionPerformed(ActionEventCZ e, int[]");
        
        
        patron = Pattern.compile("public\\s*void\\s*actionPerformed\\s*\\(\\s*ActionEvent\\s*e\\s*,\\s*int");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("public void actionPerformed(ActionEventCZ e, int");
        
        patron = Pattern.compile("public\\s*Rectangle\\s*getTanano");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("public Rectangulo getTanano");

        patron = Pattern.compile("return\\s*new\\s*Rectangle");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("return new Rectangulo");
        
        patron = Pattern.compile("mbMaximizado\\s*=\\s*true");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("setMaximizado(true)");

        patron = Pattern.compile("mbMaximizado\\s*=\\s*false");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("setMaximizado(false)");
        
        patron = Pattern.compile("mbMaximizado\\s*=\\s*false");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("setMaximizado(false)");
        
        patron = Pattern.compile("mbSiempreDelante\\s*=\\s*false");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("setSiempreDelante(false)");
        
        patron = Pattern.compile("mbSiempreDelante\\s*=\\s*true");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("setSiempreDelante(true)");
        
        patron = Pattern.compile("\\s*import\\s*utiles.IListaElementosIterator\\s*;\\s*");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("import java.util.Iterator");

        patron = Pattern.compile("\\s*import\\s*utiles.IListDatosOrdenacion\\s*;\\s*");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("import java.util.Comparator");

        patron = Pattern.compile("IListaElementosIterator");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("Iterator");
        
        patron = Pattern.compile("IListDatosOrdenacion");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("Comparator<IFilaDatos>");
        
        patron = Pattern.compile("setIncrementoMemoria");
        loMat = patron.matcher(lsCadena);
        lbAlgo |= loMat.find();
        lsCadena = loMat.replaceAll("ensureCapacity");
        
        
        patron = Pattern.compile("implements\\s*IPlugIn\\s*[\\{\\,]");
        patron2 = Pattern.compile("void\\s*procesarFinal");
        
        if(patron.matcher(lsCadena).find() && !patron2.matcher(lsCadena).find()){        
            //priemro borramos, luegoo añadimos
            patron = Pattern.compile("\\s*public\\s*void\\s*procesarControlador");
            loMat = patron.matcher(lsCadena);
            if(loMat.find()){
                lbAlgo = true;
                lsCadena = loMat.replaceAll(
                        "\n    public void procesarFinal(IPlugInContexto poContexto) {}\n"
                        + loMat.group());
            }
        }
        
        if(lbAlgo){
            JArchivo.guardarArchivo(lsCadena, loFileAux);
        }
//            new JConversorPlugIn20130913().recorrer(new File("/home/d/programas/java/otros/palas"));
        
        
        
        
    }
    public static void main(String args[]) {
        try {
            new JConversorPlugIn20130913().recorrer(new File("/home/d/programas/java/itv/ITVReformas/clienteITVReformas/"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
        
        
}
