/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesAndroid.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author eduardo
 */
public class JMostrarFoto  extends Activity {
 private ImageView imageView;
    public static final String mcsImagen="Imagen";
    public static final String mcsAceptar="Aceptar";
    public static final String mcsCancelar="Cancelar";
    public static final int mclOK=1;
    public static final int mclCancel=0;

    public static Bitmap moParametro;
    
    private Bitmap loadedImage;
    private Button btnAceptar;
    private Button btnCancelar;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jmostrarfoto);
        try {
            initComponents();
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null){
                String lsAceptar = bundle.getString(mcsAceptar);
                String lsCancelar = bundle.getString(mcsCancelar);
                if(lsAceptar!=null && lsAceptar.equals("0")){
                    btnAceptar.setVisibility(btnAceptar.GONE);
                }
                if(lsCancelar!=null && lsCancelar.equals("0")){
                    btnCancelar.setVisibility(btnCancelar.GONE);
                }
            }
        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }        
 
    }
     @Override
    protected void onResume() {
        super.onResume();
        try {
            if(moParametro!=null){
                loadedImage=moParametro;
                moParametro=null;
            }else{
                Bundle bun = getIntent().getExtras();
                String param1 = bun.getString(mcsImagen);
                loadedImage=JGUIAndroid.getImagenCargada(param1);
            }
            imageView.setImageBitmap(loadedImage);
            
        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
    }

    private void initComponents() {
        imageView = (ImageView) findViewById(R.id.image_view);      
        
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(mclOK);
                finish();
            }
        });
        
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(mclCancel);
                finish();
            }
        });        
    }


}
