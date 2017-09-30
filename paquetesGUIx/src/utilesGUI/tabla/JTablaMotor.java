/*
 * JTablaMotor.java
 *
 * Created on 27 de septiembre de 2003, 9:12
 */

package utilesGUI.tabla;

import utiles.tipos.Moneda3Decimales;
import utiles.tipos.Moneda;
import ListDatos.*;
import utiles.*;
import utiles.tipos.Porcentual;
import utiles.tipos.Porcentual3Decimales;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;

/**
 *Tabla motor para visualizar en una JListDatos
 */
public class JTablaMotor implements ITablaAntig {
  /**
   *Objeto fuente de datos
   */
  public JListDatos moList;
  /**
   *Si es editable
   */
  public boolean mbEditable = false;
  /**
   *Si se actualiza en el servidor
   */
  public boolean mbActualizarServidor = false;
  /**
   *Lista de celdas actualizadas
   */
  public ElementosActualizados moListaActualizados = new ElementosActualizados();
  /**Si guardamos las celdas que se van actualizando*/
  public boolean mbLlevarCeldasActualizadas = false;

  /**
   * Contructor
   * @param poList Datos
   */
  public JTablaMotor(JListDatos poList){
    moList = poList;
  }

  public int getColumnCount() {
      return moList.getFields().count();
  }

  public int getRowCount() {
      return moList.size();
  }


  public String getColumnName(int col) {
      return moList.getFields().get(col).getCaption();
  }


  public Object getValueAt(int row, int col) {
    if(row != moList.getIndex()) {
        moList.setIndex(row);
    }
    return moList.getFields().get(col).getValue();
  }

 
  public Class getColumnClass(int c) {
    return moList.getFields().get(c).getClase();
  }

  public void sortByColumn(int plColumn, boolean pbAscendente){
    moList.ordenar(new int[]{plColumn}, pbAscendente);
  }


   public boolean isCellEditable(int row, int col) {
      return mbEditable;
  }


   public void setValueAt(Object value, int row, int col) {
      //nos posicionamos 
      moList.setIndex(row);
      try{
          //establecemos el valor
          moList.getFields().get(col).setValue(value);
          //actualizamos el registro
          IResultado loResul = moList.update(mbActualizarServidor);
          //si no se ha actualizado bien se presenta un mensaje
          //en caso contrario se añade a la lista de elementos actualizados
          if (loResul.getBien()) {
              if(mbLlevarCeldasActualizadas){
                  moListaActualizados.add(new ElementoActualizado(row, col, value));
              }
          }else {
              utilesGUI.msgbox.JDialogo.showDialog(null, loResul.getMensaje());
          }
      }catch(Exception e){
        utilesGUI.msgbox.JDialogo.showDialog(null, e.toString());
      }
  }


    public java.awt.Component getComponent(int row, int col) {
        utilesGUI.TextFieldCZ loText  = null;
        utilesGUI.CheckBoxCZ loCheck = null;
        //segun la clase de componente se crea un u otro
        if(getColumnClass(col)==Integer.class){
            loText  = new utilesGUI.TextFieldCZ();
            loText.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
        }
        if(getColumnClass(col)==Float.class){
            loText  = new utilesGUI.TextFieldCZ();
            loText.setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
        }
        if(getColumnClass(col)==Moneda3Decimales.class){
            loText  = new utilesGUI.TextFieldCZ();
            loText.setTipo(JTipoTextoEstandar.mclTextMoneda3Decimales);
        }
        if(getColumnClass(col)==Moneda.class){
            loText  = new utilesGUI.TextFieldCZ();
            loText.setTipo(JTipoTextoEstandar.mclTextMoneda);
        }
        if(getColumnClass(col)==Porcentual3Decimales.class){
            loText  = new utilesGUI.TextFieldCZ();
            loText.setTipo(JTipoTextoEstandar.mclTextPorcentual3Decimales);
        }
        if(getColumnClass(col)==Porcentual.class){
            loText  = new utilesGUI.TextFieldCZ();
            loText.setTipo(JTipoTextoEstandar.mclTextPorcentual);
        }
        if(getColumnClass(col)==utiles.JDateEdu.class){
            loText  = new utilesGUI.TextFieldCZ();
            loText.setTipo(JTipoTextoEstandar.mclTextFecha);
        }
        if(getColumnClass(col)==Boolean.class){
            loCheck= new utilesGUI.CheckBoxCZ();
        }
        if(getColumnClass(col)==String.class){
            loText = new utilesGUI.TextFieldCZ();
        }
        java.awt.Component loComp;
        if(loCheck==null){
            loComp = loText;
        }else{
            loComp = loCheck;
        }
        return loComp;
    }
    
}
