/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.formsGenericos;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesFX.plugin.JPlugInUtilidadesFX;
import utilesGUIx.formsGenericos.IPanelGenerico;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;

public class JPanelGeneralPopUpMenu {
    protected ContextMenu jPopupMenu1;

    private MenuItem jMenuAceptar;
    private MenuItem jMenuBorrar;
    private Menu jMenuCampos;
    private MenuItem jMenuCancelar;
    private MenuItem jMenuCopiarTabla;
    private MenuItem jMenuEditar;
    private MenuItem jMenuNuevo;
    private Menu jMenuOtrasAcciones;
    private MenuItem jMenuRefrescar;
    
    private IListaElementos moListaCamposCheck = new JListaElementos();
    protected IPanelGenerico moPanel;
    
    private boolean mbEventosCampos = true;
    private boolean[] mabVisibles;
    private IListaElementos<JPanelGenericoBotonGenerico> moBotones;
    private JPanelGeneralBotones moBotonesGenerales;
    
    public JPanelGeneralPopUpMenu(final IPanelGenerico poPanel, final TableView jTableDatos,
            Button poAceptar, Button poBorrar, Button poCancelar, 
            Button poCopiarTabla, Button poEditar, Button poNuevo, 
            Button poRefrescar){
        mbEventosCampos = false;
        moPanel = poPanel;

        jPopupMenu1 = new ContextMenu();
        jMenuNuevo = new MenuItem();
        jMenuEditar = new MenuItem();
        jMenuBorrar = new MenuItem();
        jMenuRefrescar = new MenuItem();
        jMenuAceptar = new MenuItem();
        jMenuCancelar = new MenuItem();
        jMenuCopiarTabla = new MenuItem();
        jMenuCampos = new Menu();
        jMenuOtrasAcciones = new Menu();
        
        jPopupMenu1.setOnShown((WindowEvent event) -> {
            setCamposCheck();
        });
        jTableDatos.setContextMenu(jPopupMenu1);
//        jTableDatos.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                new EventHandler<MouseEvent>() {
//                    @Override public void handle(MouseEvent e) {
//                        if (e.getButton() == MouseButton.SECONDARY){  
//                            setCamposCheck();
//                            jPopupMenu1.show(jTableDatos, e.getScreenX(), e.getScreenY());
//                        }
//                    }
//            });


        if(poNuevo!=null){
            asignarBotonAMenu( poNuevo, jMenuNuevo);
            jPopupMenu1.getItems().add(jMenuNuevo);
        }
        if(poAceptar!=null){
            asignarBotonAMenu( poAceptar, jMenuAceptar);
            jPopupMenu1.getItems().add(jMenuAceptar);
        }
        if(poBorrar!=null){
            asignarBotonAMenu( poBorrar, jMenuBorrar);
            jPopupMenu1.getItems().add(jMenuBorrar);
        }
        if(poCancelar!=null){
            asignarBotonAMenu( poCancelar, jMenuCancelar);
            jPopupMenu1.getItems().add(jMenuCancelar);
        }
        if(poCopiarTabla!=null){
            asignarBotonAMenu( poCopiarTabla, jMenuCopiarTabla);
            jPopupMenu1.getItems().add(jMenuCopiarTabla);
        }
        if(poEditar!=null){
            asignarBotonAMenu( poEditar, jMenuEditar);
            jPopupMenu1.getItems().add(jMenuEditar);
        }
        if(poRefrescar!=null){
            asignarBotonAMenu( poRefrescar, jMenuRefrescar);
            jPopupMenu1.getItems().add(jMenuRefrescar);
        }


        jMenuCampos.setText("Campos");
        jPopupMenu1.getItems().add(jMenuCampos);

        jMenuOtrasAcciones.setText("Otras acciones");
        jPopupMenu1.getItems().add(jMenuOtrasAcciones);        
        mbEventosCampos = true;
    }
    public void aplicarBoton(final IBotonRelacionado poBoton){
        MenuItem loComp = getBoton(poBoton);
        if(loComp!=null){
            aplicarBoton(loComp, poBoton);
        }
    }    
    public MenuItem getBoton(final IBotonRelacionado poBoton){
        MenuItem loBoton = null;
        if(moBotonesGenerales!=null){
            if(moBotonesGenerales.getAceptar()==poBoton){
                loBoton = jMenuAceptar;
            }
            if(moBotonesGenerales.getCancelar()==poBoton){
                loBoton = jMenuCancelar;
            }
            if(moBotonesGenerales.getBorrar()==poBoton){
                loBoton = jMenuBorrar;
            }
            if(moBotonesGenerales.getEditar()==poBoton){
                loBoton = jMenuEditar;
            }
            if(moBotonesGenerales.getNuevo()==poBoton){
                loBoton = jMenuNuevo;
            }
            if(moBotonesGenerales.getRefrescar()==poBoton){
                loBoton = jMenuRefrescar;
            }
            if(moBotonesGenerales.getCopiarTabla()==poBoton){
                loBoton = jMenuCopiarTabla;
            }
            for(int i = 0 ; i < moBotones.size() && loBoton==null; i++){
                if(moBotones.get(i) == poBoton){
                    loBoton=getBotonPrivado(i);
                }
            }
        }        
        
        return loBoton;
    }
    private MenuItem getBotonPrivado(int plIndex){
        MenuItem loBoton = null;
        loBoton = (MenuItem) JPlugInUtilidadesFX.getMenu(
                jMenuOtrasAcciones
                , String.valueOf(plIndex));
        return loBoton;
    }
    private void asignarBotonAMenu(final Button poBoton, final MenuItem poMenu){
        try{
            if(poBoton.getGraphic()!=null){
                if(ImageView.class.isAssignableFrom(poBoton.getGraphic().getClass())){
                    poMenu.setGraphic(new ImageView(((ImageView)poBoton.getGraphic()).getImage()));
                }
            }
            poMenu.setText(poBoton.getText());
            if(poBoton.getText().equals("")){
                poMenu.setText(poBoton.getText());
            }
            poMenu.setId(poBoton.getId());
            poMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    poBoton.getOnAction().handle(t);
                }
            });
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }
    
    private void visibleCampo(int i, boolean pbValor) {
        if(mbEventosCampos ){
            mabVisibles[i] = pbValor;
            if(pbValor){
                moPanel.getTablaConfig().getTablaConfigColumnaDeCampoReal(i).setLong(80);
            }else{
                moPanel.getTablaConfig().getTablaConfigColumnaDeCampoReal(i).setLong(0);
            }
            moPanel.getTablaConfig().aplicar();
        }
    }
    
    private void establecerValoresMenu(final MenuItem poBoton, final IBotonRelacionado poProp){
        poBoton.setVisible(poProp.isActivo());
        if(poProp.getCaption() != null){
            poBoton.setText(poProp.getCaption());
        }
    }

    public void configurarPopup(
            IListaElementos<JPanelGenericoBotonGenerico> poBotones, JPanelGeneralBotones poBotonesGenerales, 
            EventHandler<ActionEvent> poAccion, JListDatos poDatos,
            boolean[] pabVisibleConfig){
        moBotones=poBotones;
        moBotonesGenerales=poBotonesGenerales;
        mbEventosCampos = false;
        //botones generales
        establecerValoresMenu(jMenuAceptar, poBotonesGenerales.getAceptar());
        establecerValoresMenu(jMenuBorrar, poBotonesGenerales.getBorrar());
        establecerValoresMenu(jMenuCancelar, poBotonesGenerales.getCancelar());
        establecerValoresMenu(jMenuCopiarTabla, poBotonesGenerales.getCopiarTabla());
        establecerValoresMenu(jMenuEditar, poBotonesGenerales.getEditar());
        establecerValoresMenu(jMenuNuevo, poBotonesGenerales.getNuevo());
        establecerValoresMenu(jMenuRefrescar, poBotonesGenerales.getRefrescar());
        
        if(moBotones==null || moBotones.size()==0){
            jMenuOtrasAcciones.setVisible(false);
        }else{
            jMenuOtrasAcciones.setVisible(true);
            //limpiamos las acciones previas
            for(int i = 0 ; i < jMenuOtrasAcciones.getItems().size(); i++){
                jMenuOtrasAcciones.getItems().get(i).setOnAction(null);
            }
            jMenuOtrasAcciones.getItems().clear();
            //añadimos las acciones
            for(int i = 0 ; i < moBotones.size(); i++){
                IBotonRelacionado loBoton = moBotones.get(i).moBotonRelac;
                MenuItem jMenuAux = new MenuItem(loBoton.getCaption());
                jMenuAux.setOnAction(poAccion);
                jMenuAux.setId(String.valueOf(i));
                aplicarBoton(jMenuAux, loBoton);
                jMenuOtrasAcciones.getItems().add(jMenuAux);
            }
        }
        //limpiamos los campos previos
        for(int i = 0 ; i < moListaCamposCheck.size(); i++){
            CheckMenuItem jMenuAux = (CheckMenuItem) moListaCamposCheck.get(i);
            jMenuAux.setOnAction(poAccion);
        }
        jMenuCampos.getItems().clear();
        moListaCamposCheck.clear();
        //añadimos los campos
        ChangeListener<Boolean> loAccion = new ChangeListener<Boolean>() {
                public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
                    BooleanProperty loO =  (BooleanProperty) ov;
                    CheckMenuItem loI = (CheckMenuItem)loO.getBean();
                    visibleCampo(
                            Integer.valueOf(loI.getId()).intValue(),
                            new_val.booleanValue()
                        );                    
                }

        };
        Menu loMenuCamposAux = jMenuCampos;
        int lAdd = 0;
        int lCamposGrupo = 1;
        for(int i = 0 ;  poDatos!=null && i < poDatos.getFields().size(); i++){
            JFieldDef loCampo = poDatos.getFields(i);
            CheckMenuItem jMenuAux = new CheckMenuItem(loCampo.getCaption());
            jMenuAux.selectedProperty().addListener(loAccion);
            jMenuAux.setId(String.valueOf(i));
            moListaCamposCheck.add(jMenuAux);
            if(pabVisibleConfig!=null && !pabVisibleConfig[i]){
                jMenuAux.setVisible(false);
            }else{
                if(lAdd % 10 == 0 && poDatos.getFields().size() > 15){
                    loMenuCamposAux = new Menu("Grupo "+String.valueOf(lCamposGrupo));
                    jMenuCampos.getItems().add(loMenuCamposAux);
                    lCamposGrupo++;
                }
                lAdd++;
            }
            loMenuCamposAux.getItems().add(jMenuAux);
        }
        mbEventosCampos = true;

    }
    protected void aplicarBoton(MenuItem loBotonReal,  IBotonRelacionado poBoton){
        loBotonReal.setText(
                poBoton.getCaption().replace("<html>", "").replace("</html>", "").replace("<br>", "\n")
        );
        if(poBoton.getIcono()!=null){
            if(Node.class.isAssignableFrom(poBoton.getIcono().getClass())){
                loBotonReal.setGraphic((Node) poBoton.getIcono());
            }else if(Image.class.isAssignableFrom(poBoton.getIcono().getClass())){
                loBotonReal.setGraphic(new ImageView((Image)poBoton.getIcono()));
            } else {
                JDepuracion.anadirTexto(getClass().getName(), "Icono no stablecido en botón " +  poBoton.getCaption() + "("+poBoton.getNombre()+")");
            }
        }
        loBotonReal.setVisible(poBoton.isActivo());
    }
    
    private void setCamposCheck(){
        try{
            mbEventosCampos = false;
            for(int i = 0 ; i < mabVisibles.length; i++){
                CheckMenuItem jMenuAux = (CheckMenuItem)moListaCamposCheck.get(i);
                jMenuAux.setSelected(mabVisibles[i]);
            }
        }catch(Exception e){
            
        }finally{
            mbEventosCampos = true;
        }
        
    }
    public void setVisibleCampos(boolean[] pabVisibles){
        mabVisibles = pabVisibles;
    }
    

}
