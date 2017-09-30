/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesAndroid.util;

import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eduardo
 */
public class JGUIAndroid {

    public static final int mclSpinnerSimple = android.R.layout.simple_spinner_dropdown_item;
    public static final int mclSpinnerDoble = R.layout.multiline_spinner_dropdown_item;
    
    
    public static String cmbSelectedItem(Spinner s1) {
        Object loValor = s1.getSelectedItem();
        if (loValor == null) {
            return "";
        } else {
            return loValor.toString();
        }
    }

    public static void cmbAsignar(Spinner s1, JListDatos poDatos, int plDescrip, int plClav) {
        cmbAsignar(s1, poDatos, new int[]{plDescrip}, new int[]{plClav}, mclSpinnerSimple);
    }
    
    public static void cmbAsignar(Spinner s1, JListDatos poDatos, int[] palDescrip, int[] palClaves) {
        cmbAsignar(s1, poDatos, palDescrip, palClaves, mclSpinnerSimple);
    }
    public static void cmbAsignar(Spinner s1, JListDatos poDatos, int[] palDescrip, int[] palClaves, int plLayout) {
    	cmbAsignar(s1, poDatos, palDescrip, palClaves, plLayout, false);
    }
    public static void cmbAsignar(Spinner s1, JListDatos poDatos, int[] palDescrip, int[] palClaves, int plLayout, boolean pbConBlanco) {
            List loServidor = new ArrayList();
            if(pbConBlanco){
            	loServidor.add(new JCMBLinea("", ""));
            }
            if(poDatos.moveFirst()){
                do{
                    StringBuffer loBuffer = new StringBuffer(palDescrip.length * 10);
                    for(int i = 0 ; i < palDescrip.length;i++){
                        loBuffer.append(poDatos.getFields(palDescrip[i]).getString() );
                        if(i < palDescrip.length-1){
                            loBuffer.append("-");
                        }
                    }

                    StringBuffer loClave = new StringBuffer(palClaves.length * 10);
                    for(int i = 0 ; i < palClaves.length;i++){
                        loClave.append(poDatos.getFields(palClaves[i]).getString());
                        loClave.append(JFilaDatosDefecto.mccSeparacion1);
                    }
                    loServidor.add(
                        new JCMBLinea(
                            loBuffer.toString()
                            , loClave.toString()));
                    
                }while(poDatos.moveNext());
            }
            cmbAsignar(s1, loServidor, plLayout);
        
    }
    public static void cmbAsignar(Spinner s1, List poDatos) {
        cmbAsignar(s1, poDatos, mclSpinnerSimple);
    }
    public static void cmbAsignar(Spinner s1, List poDatos, int plLayout) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(s1.getContext(),
                android.R.layout.simple_spinner_item, poDatos);
        adapter.setDropDownViewResource(plLayout);
        s1.setAdapter(adapter);
    }

    public static void cmbAsignar(ListView s1, List poDatos, final int plColor) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(s1.getContext(),
                android.R.layout.simple_list_item_1, poDatos) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(plColor);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        s1.setAdapter(adapter);
    }

    public static void cmbAsignar(ListView s1, List poDatos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(s1.getContext(),
                android.R.layout.simple_list_item_1, poDatos);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        s1.setAdapter(adapter);
    }

    public static boolean cmbSeleccionar(Spinner s1, String psID) {
        boolean lbResult = false;
        int lLinea = -1;
        if (s1 != null && s1.getAdapter() != null) {
            for (int i = 0; i < s1.getAdapter().getCount() && !lbResult; i++) {
                Object loLinea = s1.getAdapter().getItem(i);
                String ls;
                if (loLinea instanceof JCMBLinea) {
                    JCMBLinea loLin = (JCMBLinea) loLinea;
                    ls = loLin.getclave();
                } else {
                    ls = loLinea.toString();
                }
                if (ls.equals(psID)) {
                    lbResult = true;
                    lLinea = i;
                }
            }
        }
        if (lbResult) {
            s1.setSelection(lLinea);
        }
        return lbResult;
    }

    public static String cmbClave(Spinner s1) {
        Object loLinea = s1.getSelectedItem();
        String ls;
        if (loLinea == null) {
            ls = "";
        } else if (loLinea instanceof JCMBLinea) {
            JCMBLinea loLin = (JCMBLinea) loLinea;
            ls = loLin.getclave();
        } else {
            ls = loLinea.toString();
        }
        return ls;
    }
    public static IFilaDatos cmbGetFilaActual(Spinner s1) {
        Object loLinea = s1.getSelectedItem();
        String ls;
        if (loLinea == null) {
            ls = "";
        } else if (loLinea instanceof JCMBLinea) {
            JCMBLinea loLin = (JCMBLinea) loLinea;
            ls = loLin.getclave();
        } else {
            ls = loLinea.toString();
        }
        return new JFilaDatosDefecto(ls);
    }
    
    public static Bitmap getImagenCargada(String psCamino) throws Exception{
        Bitmap loadedImage;
        File loFile = new File(psCamino);
        try{
            //usamos 1º el imaeio para evitar la cache de java
            if(loFile.exists()){
                loadedImage = BitmapFactory.decodeStream(new FileInputStream(loFile));
            }else{
                URL imageUrl = new URL(psCamino);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            }
        } catch (Exception e) {
            loadedImage = BitmapFactory.decodeStream(JGUIAndroid.class.getResourceAsStream(psCamino));
        }        
        return loadedImage;
    }    
    
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height;
	        final int halfWidth = width;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize += 2;
	        }
	    }
	
	    return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromFile(String psCamino, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(psCamino, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(psCamino, options);
    }

    public static int getPixelIndepDensidad(Context poContext, int plPixel){
        final float scale = poContext.getResources().getDisplayMetrics().density;

        return (int) (plPixel * scale + 0.5f);

    }


}
