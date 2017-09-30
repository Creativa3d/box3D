/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.msgbox;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utiles.JDepuracion;
import utilesFX.JFXConfigGlobal;

/**
 *
 * @author eduardo
 */
public class JOptionPaneFX {
    public static final int  ERROR_MESSAGE = 1;
    /** Used for information messages. */
    public static final int  INFORMATION_MESSAGE = 0;
    /** Used for warning messages. */
    public static final int  WARNING_MESSAGE = 2;
    /** Used for questions. */
    public static final int  QUESTION_MESSAGE = 3;

    

    /**
     * Type meaning Look and Feel should not supply any options -- only
     * use the options from the <code>JOptionPane</code>.
     */
    public static final int         DEFAULT_OPTION = -1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_OPTION = 0;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         YES_NO_CANCEL_OPTION = 1;
    /** Type used for <code>showConfirmDialog</code>. */
    public static final int         OK_CANCEL_OPTION = 2;
    public static final int         OK_OPTION = 3;
    
    public static final Image imageAccess = new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/accept.gif"));
    public static final Image imageCancel = new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/cancel.gif"));

    public static final Image imageINFORMATION = new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/dialog-information.png"));
    public static final Image imageWARNING = new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/dialog-warning.png"));
    public static final Image imageERROR = new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/dialog-error.png"));
    public static final Image imageQUESTION = new Image(JOptionPaneFX.class.getResourceAsStream("/utilesFX/images/dialog-question.png"));
    
    
    public static void showMessageDialog(Object poPadre, String psTexto){
        showMessageDialog(poPadre, psTexto, null);
    }
    public static void showMessageDialog(Object poPadre, String psTexto, Runnable poOk){
        showConfirmDialog(poPadre, psTexto, "", OK_OPTION, INFORMATION_MESSAGE, poOk, null, null);
    }
    public static void showMessageDialog(Object poPadre, String psTexto, String psTitulo, int plTipoMensaje, Runnable poOk){
        showConfirmDialog(poPadre, psTexto, psTitulo, OK_OPTION, plTipoMensaje, poOk, null, null);
    }
    public static void showConfirmDialog(Object poPadre, String psTexto, Runnable poOk, Runnable poNo){
        showConfirmDialog(poPadre, psTexto, "",  YES_NO_OPTION, QUESTION_MESSAGE, poOk, poNo, null);
    }
    public static void showConfirmDialog(Object poPadre, String psTexto, String psTitulo, int plTipoOpcion, int plTipoMensaje, final Runnable poOk, final Runnable poNo, final Runnable poCancel){
        final Stage dialog = new Stage();
        dialog.setTitle(psTitulo);
        dialog.setResizable(false);
        if(poPadre!=null && Node.class.isAssignableFrom(poPadre.getClass()) ){
            Node loP = (Node) poPadre;
            dialog.initOwner(loP.getScene().getWindow());
        }
        
        dialog.initModality(Modality.NONE);

        try{
            dialog.setAlwaysOnTop(true);
        }catch(Throwable e){
            JDepuracion.anadirTexto(JMsgBox.class.getName(), e);
        }
        FlowPane buttons = new FlowPane(10,10);
        buttons.setAlignment(Pos.CENTER);
        Button ok = new Button("Ok", new ImageView(imageAccess));
        Button cancel = new Button("Cancelar", new ImageView(imageCancel));
        Button yes = new Button("Sí", new ImageView(imageAccess));
        yes.setDefaultButton(true);
        Button no = new Button("No", new ImageView(imageCancel));
        
        switch(plTipoOpcion){
            case YES_NO_OPTION:
                buttons.getChildren().addAll(yes, no);
                yes.setDefaultButton(true);
                no.setCancelButton(true);
                break;
            case OK_CANCEL_OPTION:
                buttons.getChildren().addAll(ok, cancel);
                ok.setDefaultButton(true);
                cancel.setCancelButton(true);
                break;
            case YES_NO_CANCEL_OPTION:
                buttons.getChildren().addAll(yes, no, cancel);
                yes.setDefaultButton(true);
                cancel.setCancelButton(true);
                break;
            default:
                buttons.getChildren().addAll(ok);
        }        
        BorderPane box = new BorderPane();
        Label loLabel = new Label(psTexto);
        loLabel.setWrapText(true);
        loLabel.setMaxWidth(400);
        box.setCenter(loLabel);
        box.setBottom(buttons);
        switch(plTipoMensaje){
            case ERROR_MESSAGE:
                box.setLeft(new ImageView(imageERROR));
                break;
            case QUESTION_MESSAGE:
                box.setLeft(new ImageView(imageQUESTION));
                break;
            case INFORMATION_MESSAGE:
                box.setLeft(new ImageView(imageINFORMATION));
                break;
            case WARNING_MESSAGE:
                box.setLeft(new ImageView(imageWARNING));
                break;
        }


        yes.setOnAction((ActionEvent t) -> {
            if(poOk!=null){
                poOk.run();
            }
            dialog.close();
        });
        no.setOnAction((ActionEvent t) -> {
            if(poNo!=null){
                poNo.run();
            }
            dialog.close();
        });
        ok.setOnAction((ActionEvent t) -> {
            if(poOk!=null){
                poOk.run();
            }
            dialog.close();
        });
        cancel.setOnAction((ActionEvent t) -> {
            if(poCancel!=null){
                poCancel.run();
            }
            dialog.close();
        });
        

        Scene s = new Scene(box);
        s.getStylesheets().add(
                JFXConfigGlobal.getInstancia().getEstilo()
                    );
        dialog.setScene(s);
        dialog.show();        
        dialog.setAlwaysOnTop(true);
        dialog.toFront();
    }
    public  static void showInputDialog(String psTexto, EventHandler<EventInput> poEvent){
        showInputDialog(null, psTexto, "", "", poEvent);
    }
    public  static void showInputDialog(Object poPadre, String psTexto, EventHandler<EventInput> poEvent){
        showInputDialog(poPadre, psTexto, "", "", poEvent);
    }
    public static void showInputDialog(Object poPadre, String psTexto, String psTitulo, String psValorInicial, final EventHandler<EventInput> poEvent){
        showInputDialog(poPadre, psTexto, psTitulo, psValorInicial, false, poEvent);
    }
    public static void showInputDialog(Object poPadre, String psTexto, String psTitulo, String psValorInicial, boolean pbPassword, final EventHandler<EventInput> poEvent){

        final Stage dialog = new Stage();
        dialog.setTitle(psTitulo);
        dialog.setResizable(false);
        dialog.initModality(Modality.NONE);
        if(poPadre!=null && Node.class.isAssignableFrom(poPadre.getClass()) ){
            Node loP = (Node) poPadre;
            dialog.initOwner(loP.getScene().getWindow());
        }

        FlowPane buttons = new FlowPane(10,10);
        buttons.setAlignment(Pos.CENTER);
        Button ok = new Button("Ok", new ImageView(imageAccess));
        ok.setDefaultButton(true);
        Button cancel = new Button("Cancelar", new ImageView(imageCancel));
        cancel.setCancelButton(true);
        final TextField loTextField;
        if(pbPassword){
            loTextField = new PasswordField();
            loTextField.setText(psValorInicial==null ? "" : psValorInicial);
        } else {
            loTextField = new TextField(psValorInicial==null ? "" : psValorInicial);
        }
        buttons.getChildren().addAll(ok, cancel);
        
        HBox loTe4xtLayout = new HBox();
        loTe4xtLayout.getChildren().add(loTextField);
        HBox.setHgrow(loTextField, Priority.ALWAYS);
        
        BorderPane box = new BorderPane();
        box.setTop(new Label(psTexto));
        box.setCenter(loTe4xtLayout);
        box.setBottom(buttons);
        box.setLeft(new ImageView(imageQUESTION));


        ok.setOnAction((ActionEvent t) -> {
            poEvent.handle(new EventInput(null, loTextField.getText()));
            dialog.close();
        });
        cancel.setOnAction((ActionEvent t) -> {
            poEvent.handle(new EventInput(null, ""));
            dialog.close();
        });
        

        Scene s = new Scene(box);
        s.getStylesheets().add(
                JFXConfigGlobal.getInstancia().getEstilo()
                    );        
        dialog.setScene(s);
        dialog.show();        
    }
    public static class EventInput extends Event {
        private String msInput;

        public EventInput(EventType<? extends Event> et, String ps) {
            super(et);
            setInput(ps);
        }
        
        public void setInput(String ps){
            msInput = ps;
        }
        public String getInput(){
            return msInput;
        }
    
    }
}
