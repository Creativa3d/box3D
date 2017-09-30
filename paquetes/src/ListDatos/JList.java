/*
 *
 */
package ListDatos;

import java.util.Iterator;
import utiles.*;

/**
 *Lista de filas de datos
 */
final class JList extends JListaElementos<IFilaDatos> {
  private static final long serialVersionUID = 3333334L;
  private JListaElementos moListeners = new JListaElementos();
  /**
   *si anula los listener, para que no se llamen al andir/borrar datos
   */
  public boolean mbAnularListeners;

  public JList(){
      super();
      mbAnularListeners = false;
  }
  ////////////////////////////////////7
  ///gestion de listeners
  ////////////////////////////////////
  /**
   * Borra todos los listeners
   */
  public void removeAllListener(){
   moListeners.clear();
  }
   /**
    * Anade un listener
    * @param poListener listener
    */
  public void addListener(final IListDatosGest poListener){
   moListeners.add(poListener);
  }
  /**
   * Borra un listener
   * @param poListener listener
   */
  public void removeListener(final IListDatosGest poListener){
   moListeners.remove(poListener);
  }

  /**
   * llama a todos los listener, con la operacion y fila afectada
   * @param plIndex indice de la fila afectada
   * @param poFila fila en cuestion
   * @param pbEsadd si se ha anadir la fila
   */
  public void llamarListeners(final int plIndex, final IFilaDatos poFila,final  boolean pbEsadd){
      try{
        if (!mbAnularListeners) {
          //llamamos a los listener que ha terminado la select correspondiente
          IListDatosGest loListener;
          Iterator loEnum = moListeners.iterator();
          for (; loEnum.hasNext(); ) {
            loListener = (IListDatosGest) loEnum.next();
            if (pbEsadd){
              loListener.addFilaDatos(plIndex, poFila);
            }else{
              loListener.removeFilaDatos(plIndex, poFila);
            }
          }
          loListener=null;
          loEnum=null;
        }
      }catch(Exception e){
          JDepuracion.anadirTexto(getClass().getName(), e);
          throw new InternalError(e.toString());
      }
  }
  /**
   * anade un dato y lo notifica
   */
  @Override
  public boolean add(final IFilaDatos o) {
    boolean lb = super.add(o);
    llamarListeners(super.size()-1,(IFilaDatos)o, true);
    return lb;
  }

  /**
   * borra un dato y lo notifica
   */
  @Override
  public IFilaDatos remove(final int index) {
    IFilaDatos loObj = super.remove(index);
    llamarListeners(index, (IFilaDatos)loObj, false);
    return loObj;
  }

  @Override
  public boolean remove(Object o) {
      int lResult = -1;
      for(int i = 0 ; i < size() && lResult<0;i++){
          if(get(i)==o){
              lResult = i;
          }
      }
      if(lResult<0){
          return false;
      }else{
          remove(lResult);
          return true;
      }
  }

  /**
   * borra todo y lo notifica
   */
  @Override
  public void clear() {
    super.clear();
    llamarListeners(-1, null, false);
  }

    @Override
    public Object clone() {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

}
