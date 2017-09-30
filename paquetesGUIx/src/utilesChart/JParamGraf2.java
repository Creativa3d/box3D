package utilesChart;

import java.awt.Image;
import utiles.FechaMalException;
import utiles.IListaElementos;
import utiles.JDateEdu;
import utiles.JListaElementos;
import utilesChart.util.JPanelGrafico;

public class JParamGraf2 {
    public static final int mclTipoBarras2D = JPanelGrafico.mclTipoBarras2D;
    public static final int mclTipoXYValores = JPanelGrafico.mclTipoXYValores;
    public static final int mclTipoBarras3D = JPanelGrafico.mclTipoBarras3D;
    public static final int mclTipoXYTiempo = JPanelGrafico.mclTipoXYTiempo;

    /**collecion de JParamGraf2Y para el eje 1*/
    public IListaElementos<JParamGraf2Y> moCollecEje1 = new JListaElementos();
    /**collecion de JParamGraf2Y para el eje 2*/
    public IListaElementos<JParamGraf2Y> moCollecEje2 = new JListaElementos();
    /**Nombre del eje X que es comun a los 2 ejes Y*/
    public String msNombreEjeX;
    /**Titulo*/
    public String msTitulo;
    /**Label parametro eje1*/
    public String msCaption1;
    /**Label parametro eje2*/
    public String msCaption2;
    /**indica el tipo del eje X(numerico, Fecha, Valores)*/
    public int mlTipoEjeX=mclTipoXYTiempo;
    /**formato de los datos del eje X, ejem: dd/MM/yyyy*/
    public String msFormat="d/M/yy";
    /**imagen interior de la cabeza normal*/
    public Image moImagenCabeza;
    /**angulo de las letras*/
    public int mlAngulo=0;
    /**parametros de impresion: Ruta logo*/
    public Image moImpRutaLogo;
    /**parametros de impresion: texto logo*/
    public String msImpTextoLogo;
    /**parametros de impresion: entidad*/
    public String msImpEntidad;
    /**imagen interior de la cabeza impresion*/
    public Image moImpImagenCabeza;

    public JParamGraf2() {
    }
    
    public String getFechaMinima1() throws FechaMalException{
        String lsInicio="";
        for(int i = 0 ; i < moCollecEje1.size(); i++){
            JParamGraf2Y loY = (JParamGraf2Y) moCollecEje1.get(i);
            if(JDateEdu.isDate(loY.msXInicio)){
                if(lsInicio.equals("") || new JDateEdu(lsInicio).compareTo(new JDateEdu(loY.msXInicio)) == JDateEdu.mclFechaMayor){
                    lsInicio=loY.msXInicio;
                }
            }

        }
        return lsInicio;
    }    
    public String getFechaMaxima1() throws FechaMalException{
        String lsTerminar="";
        for(int i = 0 ; i < moCollecEje1.size(); i++){
            JParamGraf2Y loY = (JParamGraf2Y) moCollecEje1.get(i);
            if(JDateEdu.isDate(loY.msXFin)){
                if(lsTerminar.equals("") || new JDateEdu(lsTerminar).compareTo(new JDateEdu(loY.msXFin)) == JDateEdu.mclFechaMenor){
                    lsTerminar=loY.msXFin;
                }
            }
        }
        return lsTerminar;
    }
}