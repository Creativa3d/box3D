
package utiles.xml.dom;

import java.io.IOException;
import java.io.InputStream;

import utiles.IListaElementos;
import utiles.xml.sax.JAtributos;

/**
 * Pulls WMS objects out of the XML
 * @author Chris Hodgson chodgson@refractions.net
 */
public class Prueba {
  
  /** 
   * Creates a Parser for dealing with WMS XML.
   */
  public Prueba() {
  }
  
  public void procesar() throws IOException{
      String title = null;
      Document doc;
      InputStream inStream = getClass().getResourceAsStream("/utiles/xml/dom/wms_capabilities.xml");
      try {
        SAXBuilder builder = new SAXBuilder();
        doc = builder.build(inStream );
      } catch(Exception saxe ) {
        throw new IOException( saxe.toString() );
      }
      Element itr = doc.getRootElement();
      // get the title
      try {
            
          System.out.println("Titulo = " + itr.simpleXPath(itr, "WMT_MS_Capabilities/Service/Title").getText());
      } catch (Exception e) {
        // possible NullPointerException if there is no firstChild()
        // also possible miscast causing an Exception
      }
      
      Element formatNode = itr.simpleXPath(itr, "WMT_MS_Capabilities/Capability/Request/GetMap");

      IListaElementos nl = formatNode.getChildren();
      for( int i=0; i < nl.size(); i++ ) {
        Element n = (Element)nl.get(i );
        if(itr.hasChildren() && "Format".equals( n.getName() )) {
            System.out.println("Formato = " + n.getText());
        }
      }

      wmsLayerFromNode( itr.simpleXPath(itr, "WMT_MS_Capabilities/Capability/Layer"));
      
  }
  
  public static void main(String args[]) {
        try {
            (new Prueba()).procesar();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
  }
  
  /**
   * Traverses the DOM tree underneath the specified Node and generates
   * a corresponding WMSLayer object tree. The returned WMSLayer will be set to 
   * have the specified parent.
   * @param layerNode a DOM Node which is a <layer> XML element
   * @return a WMSLayer with complete subLayer tree that corresponds
   *         to the DOM Node provided
   */
  public void wmsLayerFromNode( Element layerNode ) {
    String name = null;
    String title = null;
//    LinkedList srsList = new LinkedList();
//    LinkedList subLayers = new LinkedList();
    
    IListaElementos nl = layerNode.getChildren();
    for( int i = 0; i< nl.size(); i++ ) {
      Element n = (Element)nl.get( i );
      try {
          if( n.getName().equals( "Name" ) ) {
            System.out.println("Nombre = " + n.getText());
          } else if( n.getName().equals( "Title" ) ) {
            System.out.println("titulo = " + n.getText());
          } else if( n.getName().equals( "SRS" ) ) {
            System.out.println("SRS = " + n.getText());
          } else if( n.getName().equals( "BoundingBox" ) ) {
            System.out.println("BoundingBox");
            boundingBoxFromNode( n );
          } else if( n.getName().equals( "Layer" ) ) {
              System.out.println("Nueva SubCapa ");
              wmsLayerFromNode( n );
          }
      } catch( Exception e ) {
        System.out.println( "Exception caught in wmsLayerFromNode(): " + e.toString() );
      }
    }
  }
  public void boundingBoxFromNode( Element n ) throws Exception {
    try {
      JAtributos nm = n.getAttributes();
      if( n.getName().equals( "LatLonBoundingBox" ) ) {
          System.out.println("LatLonBoundingBox=LatLong");
      } else if( n.getName().equals( "BoundingBox" ) ) {
          System.out.println("BoundingBox=" + n.atributoNode(nm,"SRS"));
      } else {
        throw new Exception( "Not a (LatLon)BoundingBox Element" );
      }
      
      double minx;
      if (n.atributoNode(nm,"minx").equals("inf"))
			minx = Double.valueOf("-Infinity").doubleValue();
      else
			minx = Double.valueOf(n.atributoNode(nm,"minx")).doubleValue(); 
		
		double miny;
		if (n.atributoNode(nm,"miny").equals("inf"))
			miny = Double.valueOf("-Infinity").doubleValue();
		else
			miny = Double.valueOf(n.atributoNode(nm,"miny")).doubleValue(); 
		
		double maxx;
		if (n.atributoNode(nm,"maxx").equals("inf"))
			maxx = Double.valueOf("+Infinity").doubleValue();
		else
			maxx = Double.valueOf(n.atributoNode(nm,"maxx")).doubleValue();
		
		double maxy;
		if (n.atributoNode(nm,"maxy").equals("inf"))
			maxy = Double.valueOf("+Infinity").doubleValue();
		else
			maxy = Double.valueOf(n.atributoNode(nm,"maxy")).doubleValue(); 
      System.out.println("BoundingBox="+
              String.valueOf(minx)+"," +
              String.valueOf(miny)+","+ 
              String.valueOf(maxx)+","+ 
              String.valueOf(maxy));
    } catch( Exception e ) {
      // possible NullPointerException from getNamedItem returning a null
      // also possible NumberFormatException
      throw new Exception( "Invalid bounding box element node: " + e.toString() );
    }    
  }
  
}
