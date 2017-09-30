package utilesBD.buscarHuecos;

/**
 *OBJETIVOS: Interfaz abstracta de manejo de una lista abstracta de cualquier
 *tipo de elementos
 *
 *PROCEDIMIENTOS:
 *inicializar -> le pasa por parametro la tabla y el campo de busqueda.
 *primero -> Devuelve el primer elemento de la lista
 *siguiente -> Devuelve el siguiente elemento de la lista
 *siguienteSecuencia -> Devuelve el siguiente elemento que corresponderia en riguroso orden.
 *haTerminado -> Indica si ha terminado la lectura secuencial de la lista
 *
 */
public interface IListaIterator {
    public Variant primero();
    public Variant siguiente();
    public Variant siguienteSecuencia();
    public boolean haTerminado();
    public Variant ultimo();
    public Variant ultimoMasUno();
    public boolean esHuecoLibre(Variant pv);
    public long numElementos();
    public boolean compara(Variant pv1,Variant pv2);
}
