/*
 * JTableRender.java
 *
 * Created on 2 de septiembre de 2006, 11:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx;

import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTable;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.busqueda.JBusqueda;
import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;

public class JTableRender extends JTableRenderConColor implements KeyListener {
    private final JPanelBusquedaParametros moParam;
    private JTable moTable;
    private int mlColumna = -1;
    private int mlFilaAntesBuscar=-1;
    
    public JTableRender(JPanelBusquedaParametros poParam){
        this(poParam, null);
    }
    public JTableRender(JPanelBusquedaParametros poParam, final JTableCZ poTable){
        super(String.class, poTable);
        moParam = poParam;
    }
    private void buscar(){
        try{
            if(moParam.moTabla != null){
                JBusqueda loBusq = new JBusqueda(new JConsulta(moParam.moTabla, moParam.mbConDatos), moParam.moTabla.moList.msTabla);
                loBusq.mlAlto = moParam.mlAlto;
                loBusq.mlAncho = moParam.mlAncho;
                loBusq.mbFiltro = moParam.mbFiltro;
                loBusq.mostrarBusq(new CallBack<IPanelControlador>() {
                    public void callBack(IPanelControlador poControlador) {
                        busquedaIndice(poControlador.getIndex());
                    }
                });
                
                
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }
    private void busquedaIndice(int plIndex){
        String msValorCodigo;
        
        if(plIndex>=0){
            moParam.moTabla.moList.setIndex(plIndex);
            if(moParam.mlCamposPrincipales.length==1) {
                msValorCodigo = moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[0]).getString();
            }
            else{
                StringBuilder lsCad = new StringBuilder();
                for(int i = 0; i<moParam.mlCamposPrincipales.length;i++) {
                    lsCad.append(moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[i]).getString());
                    lsCad.append(JFilaDatosDefecto.mcsSeparacion1);
                }
                msValorCodigo = lsCad.toString();
            }
            if(moTable!=null && mlColumna!=-1){
                moTable.setValueAt(msValorCodigo, mlFilaAntesBuscar, mlColumna);
                updateUI();
            }
        }
        mlFilaAntesBuscar=-1;
    }

    private void setTable(JTable table){
        if(moTable==null && table!=null){
            table.addKeyListener(this);
            moTable = table;
            for(int i = 0; i<moTable.getColumnModel().getColumnCount();i++) {
                if(moTable.getColumnModel().getColumn(i).getCellRenderer()==this){
                    mlColumna = i;
                }
            }
        }
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String msValorCodigo;
        setTable(table);
        if(value == null) {
            this.setText("");
        }else{
            JListDatosFiltroConj loFiltroAux=null;
            try{
                msValorCodigo=value.toString();
                //si existe una tabla asociada
                if(moParam.moTabla != null){
                    String lsCodigo ="";
                    for(int i = 0; i< moParam.mlCamposPrincipales.length;i++){
                        lsCodigo+=JFilaDatosDefecto.mcsSeparacion1;
                    }
                    //si el código es nulo ponemos la descripcion a ""
                    if(msValorCodigo==null||(msValorCodigo.compareTo("")==0)||(msValorCodigo.compareTo(lsCodigo)==0)){
                        this.setText("");
                    }else{
                        //buscamos el codigo
                        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                        if(moParam.mlCamposPrincipales.length==1){
                            loFiltro.addCondicion(JListDatosFiltroConj.mclAND, 
                                JListDatos.mclTIgual, moParam.mlCamposPrincipales[0], 
                                msValorCodigo);
                        } else {
                            for(int i = 0; i< moParam.mlCamposPrincipales.length;i++){
                                loFiltro.addCondicion(JListDatosFiltroConj.mclAND, 
                                    JListDatos.mclTIgual, moParam.mlCamposPrincipales[i], 
                                    (new JFilaDatosDefecto(msValorCodigo).msCampo(i)));
                            }
                        }

                        moParam.moTabla.moList.getFiltro().Clear();
                        moParam.moTabla.moList.filtrarNulo();
                        moParam.moTabla.moList.getFiltro().addCondicion(
                            JListDatosFiltroConj.mclAND, loFiltro);

                        moParam.moTabla.moList.filtrar();
                        if(!moParam.moTabla.moList.moveFirst()){
                            if(!moParam.mbConDatos){
                                if(moParam.moTabla.getSelect().getWhere().mbAlgunaCond()){
                                    loFiltroAux = moParam.moTabla.getSelect().getWhere().Clone();
                                }
                                moParam.moTabla.recuperarFiltradosNormal(loFiltro, false);
                            }
                        }
                        if(moParam.moTabla.moList.moveFirst()){
                            //si existe un registro, creamos la descripcion 
                            mostrarDescripcion();
                        }else{
                            //si no existre presentamos un mensaje en caso de que el prog. lo quiera
                            if(moParam.mbMensajeSiNoExiste){
                                utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this, "El código no existe en la tabla relacionada", true);
                            }
                            //descripcion al codigo si no existe
                            this.setText(msValorCodigo);
                        }

                    }
                }
            }catch(Exception e){
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e.getMessage(), getClass().getName());
            }finally{
                if(moParam.moTabla!=null){
                    moParam.moTabla.moList.getFiltro().Clear();
                    moParam.moTabla.moList.filtrarNulo();
                    moParam.moTabla.getSelect().getWhere().Clear();
                    if(loFiltroAux!=null){
                        moParam.moTabla.getSelect().getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltroAux);
                    }
                }
            }
        }
        return super.getTableCellRendererComponent(table, this.getText(), isSelected, hasFocus, row, column);
    }
    private void mostrarDescripcion(){
        StringBuilder lsCadena= new StringBuilder(30);
        for(int i = 0; i< moParam.malDescripciones.length;i++){
            if(moParam.masTextosDescripciones!=null){
                lsCadena.append(moParam.masTextosDescripciones[i]);
                lsCadena.append('=');
            }
            lsCadena.append(moParam.moTabla.moList.getFields().get(moParam.malDescripciones[i]).getString());
            lsCadena.append(' ');
        }
        //establecemos la descripcion
        this.setText(lsCadena.toString());
    }

    public void keyTyped(KeyEvent e) {
        //vacio
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_F3){
            if((moTable!=null) && (mlColumna == moTable.getSelectedColumn())){
                mlFilaAntesBuscar = moTable.getSelectedRow();
                buscar();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        //vacio
    }
}   

