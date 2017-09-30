/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX.formsGenericos;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilesGUIx.formsGenericos.ISalir;

/**
 *
 * @author eduardo
 */
public class JFrameFX extends BorderPane implements ISalir  {
    
    private JFrameCabezera moCabezera;
    
    public JFrameFX (){
        setStyle("-fx-border-color: #0060a0; -fx-border-width: 4; -fx-border-radius: 4;");
    }        
    public void setStage(Stage poStage){
        moCabezera = new JFrameCabezera(new JFrameFXCabStage(poStage));
        this.setTop(getCabezera());
        //drag only by title
        getCabezera().makeResizable(this);
        getCabezera().makeFocusable();
        
        this.requestFocus();
    }

    public void setIcono(Image poIm){
        getCabezera().setIcono(poIm);
    }


    public String getTitle(){
        return getCabezera().getTitle();
    }
    
    public String getName(){
        if(getId()!=null){
            return getId().toString();
        }else{
            return getTitle();
        }
    }
    
            
    @Override
    public void salir() {
        getCabezera().close();
    }

    @Override
    public void setTitle(String psTitulo) {
        getCabezera().setTitle(psTitulo);
    }

    /**
     * @return the moCabezera
     */
    public JFrameCabezera getCabezera() {
        return moCabezera;
    }

    
}
