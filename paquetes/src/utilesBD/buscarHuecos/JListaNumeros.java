/*
 * JListaNumeros.java
 *
 * Created on 28 de octubre de 2004, 16:56
 */

package utilesBD.buscarHuecos;

/**
 *Lista de numeros tipo long
 */
import ListDatos.*;

/**Implementacion concreta de busqueda nuevos numero de una tabla, devuelve el ult.+1 o si no puede ser un hueco*/
public class JListaNumeros implements IListaIterator{
    
    private JListDatos moList;
    private int mlCampoBus;
    private int mlTamanoBus;
    
    /**
     * Creates a new instance of JListaNumeros
     */
    public JListaNumeros(JListDatos poList,int plCampoBus,int[] palCampos,String[] pasValores) {
        JListDatosFiltroConj loFiltro;
        
        moList = poList;
        mlCampoBus = plCampoBus;
        mlTamanoBus = moList.getFields().get(mlCampoBus).getTamano();
        loFiltro = moList.getFiltro();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual,palCampos,pasValores);
        moList.filtrar();
        moList.ordenar(plCampoBus);
    }
    /** Creates a new instance of JListaNumeros */
    public JListaNumeros(JSTabla poTabla,int plCampoBus,int[] palCampos,String[] pasValores) throws Exception {
        JSelect loSelect = new JSelect(poTabla.moList.msTabla);
        int[] lalTipos = new int[palCampos.length+1];
        String[] lasCampos = new String[palCampos.length+1];

        mlTamanoBus = poTabla.moList.getFields().get(plCampoBus).getTamano();
        mlCampoBus = lasCampos.length-1;
        
        for(int i = 0; i< palCampos.length;i++){
            loSelect.addCampo(poTabla.moList.msTabla, poTabla.moList.getFields().get(palCampos[i]).getNombre());
            lalTipos[i] =poTabla.moList.getFields().get(palCampos[i]).getTipo();
            lasCampos[i] =poTabla.moList.getFields().get(palCampos[i]).getNombre();
        }
        loSelect.addCampo(poTabla.moList.msTabla, poTabla.moList.getFields().get(plCampoBus).getNombre());
        lalTipos[lalTipos.length-1] =poTabla.moList.getFields().get(plCampoBus).getTipo();
        lasCampos[lalTipos.length-1] =poTabla.moList.getFields().get(plCampoBus).getNombre();
        
        loSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, JListDatos.mclTIgual,palCampos, pasValores);
        loSelect.getWhere().inicializar(poTabla.moList.msTabla, poTabla.moList.getFields().malTipos(), poTabla.moList.getFields().msNombres());
        
        moList = new JListDatos(poTabla.moList.moServidor, poTabla.moList.msTabla, lasCampos,lalTipos,new int[]{0});
        
        moList.recuperarDatosNoCacheNormal(loSelect);
        moList.ordenar(plCampoBus);
    }
    //Devuelve true si es el ultimo elemento de la lista
    public boolean haTerminado() {
        return(moList.mbEsElUltimo());
    }
    //Devuelve el numero de elementos de la lista
    public long numElementos() {
        return((long)moList.size());
    }
    //Devuelve el primer elemento de la lista
    public Variant primero() {
        moList.moveFirst();
        return(new Variant(moList.getFields().get(mlCampoBus).getLong()));
    }
    //Siguente real
    public Variant siguiente() {
        moList.moveNext();
        return(new Variant(moList.getFields().get(mlCampoBus).getLong()));
    }
    //Siguente + 1
    public Variant siguienteSecuencia() {
        return(new Variant(moList.getFields().get(mlCampoBus).getLong() + 1));
    }
    //Ultimo elemento 
    public Variant ultimo() {
        moList.moveLast();
        return(new Variant(moList.getFields().get(mlCampoBus).getLong()));
    }
    //Detecta un hueco libre
    public boolean esHuecoLibre(Variant pv) {
        boolean lbHueco = false;
        int tam1,tam2; //Tamanos de los campos
        
        tam1 = mlTamanoBus; //Tamano del campo de busqueda
        tam2 = pv.getString().length(); //Tamano del valor actual
        if (tam1>=tam2) {
            lbHueco = (!moList.buscar(JListDatos.mclTIgual,mlCampoBus,pv.getString()));
        }else{
            lbHueco = false;
        }
        return lbHueco;
    }
    
    //Ultimo mas uno
    public Variant ultimoMasUno() {
        return(new Variant(ultimo().getLong() + 1));
    }
    
    public boolean compara(Variant pv1, Variant pv2) {
        return(pv1.getLong() == pv2.getLong());
    }
}
