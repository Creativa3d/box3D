/*
 * JListDatosBookMark.java
 *
 * Created on 22 de octubre de 2003, 20:11
 */


package ListDatos;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import java.io.Serializable;
/**
 *Marca de posicion del JListDatos independiente de si cambia el orden del JListDatos 
 */
public final class JListDatosBookMark implements Serializable {
    private static final long serialVersionUID = 3333336L;
    private final JList moList;
    private final int mlIndexInterno;
    private final int mlModo;
    private final IFilaDatos moFilaFieldDefs;
    private final IFilaDatos moFila;

    JListDatosBookMark() {
        this(null, -1, -1, null, null);
    }
    /**
     * Crea una nueva instancia de JListDatosBookMark 
     * @param poList Datos internos del JListDatos, estos nunca varian de posicion cuando filtras u ordenas 
     * @param plIndexInterno indice de los datos internos
     */
    JListDatosBookMark(final JList poList, final int plIndexInterno, final int plModo, final JFieldDefs poCampos, final IFilaDatos poFila) {
        super();
        mlModo = plModo;
        moFilaFieldDefs = poCampos.moFilaDatos();
        moList=poList;
        mlIndexInterno=plIndexInterno;
        moFila=poFila;
    }

    /**
     * @return the moList
     */
    JList getList() {
        return moList;
    }

//    /**
//     * @param moList the moList to set
//     */
//    void setList(JList moList) {
//        this.moList = moList;
//    }

    /**
     * @return the mlIndexInterno
     */
    int getIndexInterno() {
        return mlIndexInterno;
    }

//    /**
//     * @param mlIndexInterno the mlIndexInterno to set
//     */
//    void setIndexInterno(int mlIndexInterno) {
//        this.mlIndexInterno = mlIndexInterno;
//    }

    /**
     * Modo del JListDatos
     * @return the mlModo
     */
    int getModo() {
        return mlModo;
    }

    /**
     * Datos de los campos
     * @return the moFila
     */
    IFilaDatos getFilaFieldDefs() {
        return moFilaFieldDefs;
    }

    IFilaDatos getFila() {
        return moFila;
    }
    
}
