/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion.componentes.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.codec.binary.Base64;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupoModelo;

public class JXMLDesktop {

    public static String guardarXML(JComponenteAplicacionGrupoModelo poListComp) throws ClassNotFoundException, Throwable {
//la lista de botones y grupos no se guarda en profundidad
//        XStream xstream = new XStream(new StaxDriver());
//        return xstream.toXML(poListComp);
//        
//        com.thoughtworks.xstream.XStream lo = new com.thoughtworks.xstream.XStream();
//        JListaElementos loLista = new JListaElementos();
//        for(int i = 0 ; i < poListComp.size(); i++){
//            IComponenteAplicacion loComp = (IComponenteAplicacion) poListComp.get(i);
//            if(loComp.getGrupoBase().equals(IComponenteAplicacion.mcsGRUPOBASEDESKTOP)){
//                loLista.add(loComp);
//            }
//        }
//        String lsCadena = lo.toXML(loLista);
//        System.out.println(lsCadena);
//        IListaElementos loLista1 = leerXML(lsCadena);
//        return lsCadena;
//        
//        
//        
//        Base64 moBase64Encoder = new Base64();
//
//        ByteArrayOutputStream fos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        oos.writeObject(poListComp);
//        oos.close();
//
//        return new String( moBase64Encoder.encodeBase64(fos.toByteArray()));
        return "";
    }

    public static JComponenteAplicacionGrupoModelo leerXML(String psLector) throws ClassNotFoundException, Throwable {
//        Base64 moBase64Decoder = new Base64();
//        byte[] loByte = moBase64Decoder.decodeBase64(psLector.getBytes());
//        ByteArrayInputStream loArr = new ByteArrayInputStream(loByte);
//        ObjectInputStream ois = new ObjectInputStream(loArr);
//        return (JComponenteAplicacionGrupoModelo) ois.readObject();

        return null;

////        System.out.println(psLector);
////        com.thoughtworks.xstream.XStream lo = new com.thoughtworks.xstream.XStream(new com.thoughtworks.xstream.io.xml.DomDriver("ISO-8859-1"));
////        return (IListaElementos) lo.fromXML(psLector);
//        // Create input streams.
//        XStream xstream = new XStream(new DomDriver());
//        return (JComponenteAplicacionGrupoModelo) xstream.fromXML(new StringReader(psLector));
    }
}
