
package utilesEjecutar.fuentes.archivos;

import utiles.JDateEdu;


public interface IArchivo {
    /**Copia la fuente a la ruta especificada*/
    public void copiarFuenteA(String psRuta, boolean pbConBarra) throws Throwable;
    /**Fecha modificacion*/
    public JDateEdu getFechaModificacion() throws Throwable;
    /**Tamaño en bytes*/
    public long getTamano() throws Throwable;
    /**Ruta relativa al codebase*/
    public String getRutaRelativa() throws Throwable;
    /**nombre del archivo*/
    public String getNombre() throws Throwable;

}
