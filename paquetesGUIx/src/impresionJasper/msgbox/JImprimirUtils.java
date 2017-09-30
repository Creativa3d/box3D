/*
 * JImprimirUtils.java
 *
 * Created on 1 de septiembre de 2006, 18:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionJasper.msgbox;

import ListDatos.JListDatos;
import impresionJasper.JMotorInformes;
import java.io.InputStream;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import utiles.JDepuracion;

public class JImprimirUtils {
    public static String msRetornoCarro = System.getProperty("line.separator");
    
    /** Creates a new instance of JImprimirUtils */
    private JImprimirUtils() {
    }


    public static void imprimirTexto(String psTitulo,String psHTML) throws Throwable {
        JListDatos loList = new JListDatos(null, "", new String[]{"titulo", "valor"}, new int[]{JListDatos.mclTipoCadena, JListDatos.mclTipoCadena}, new int[]{});
        loList.addNew();
        loList.getFields(0).setValue(psTitulo);
        loList.getFields(1).setValue(psHTML);
        loList.update(false);

        JMotorInformes loMotor = new JMotorInformes();
        loMotor.setTipoListado(loMotor.mclPrevisualizar);
        loMotor.setList(loList);
        loMotor.getParametros().put("prueba", "prueba");

        
        try{
            loMotor.lanzarInformeCompilado(
               (JasperReport) JRLoader.loadObject(JImprimirUtils.class.getResourceAsStream("/impresionJasper/msgbox/texto.jasper"))
                );

        }catch(Throwable e){
            JDepuracion.anadirTexto(JImprimirUtils.class.getName(), e);

            loMotor = new JMotorInformes();
            loMotor.setTipoListado(loMotor.mclPrevisualizar);
            loMotor.setList(loList);
            loMotor.getParametros().put("prueba", "prueba");
            InputStream loIn = JImprimirUtils.class.getResourceAsStream("/impresionJasper/msgbox/texto.jrxml");
//            JasperReport loInformeCompilado = JasperCompileManager.compileReport(loIn);
//            JRSaver.saveObject(loInformeCompilado, "texto.jasper");
//            loIn = JImprimirUtils.class.getResourceAsStream("/impresionJasper/msgbox/texto.jrxml");
            loMotor.lanzarInforme(loIn);
        }
    }
    
}
