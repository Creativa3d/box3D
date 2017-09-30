/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import utiles.JConversiones;
import utilesAndroid.util.R;
import utilesGUI.tabla.IComponentParaTabla;


public class JCheckBoxRender extends ImageView implements IComponentParaTabla{
    private boolean mbOriginal=false;
    private static Bitmap moBMPCheckSI;
    private static Bitmap moBMPCheckNO;

    public JCheckBoxRender(Context context) {
        super(context);
        if(moBMPCheckSI==null){
            moBMPCheckSI = BitmapFactory.decodeResource(getResources(), R.drawable.checksi);
            moBMPCheckNO = BitmapFactory.decodeResource(getResources(), R.drawable.checkno);
        }
        setScaleType(ScaleType.CENTER);
    }
    
    public void setText(CharSequence arg0){
        if(arg0!=null && JConversiones.cBool(arg0.toString())){
            setSelected(true);
        }else{
            setSelected(false);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected); //To change body of generated methods, choose Tools | Templates.
        if(selected){
            setImageBitmap(moBMPCheckSI);
        }else{
            setImageBitmap(moBMPCheckNO);
        }
    }

    public void setValueTabla(Object poValor) throws Exception {
        if(poValor==null){
            setSelected(false);
        }else{
            setText(poValor.toString());
        }
        mbOriginal=isSelected();
    }

    public Object getValueTabla() {
        return new Boolean(isSelected());
    }

    public boolean getTextoCambiado() {
        return mbOriginal != isSelected();
    }

    public void setValue(Object poValor) throws Exception {
        if(poValor==null){
            setText("");
        }else{
            setText(poValor.toString());
        }
    }
    
    
    
}
