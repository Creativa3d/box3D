package utilesAndroid.util;

import java.util.ArrayList;
import java.util.List;

import utiles.JDepuracion;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class JListDatosAdapter extends BaseAdapter implements Filterable{
	private final Context moContext;
	private JListDatos moList;
	private int[] malColumns;
	private int mlCampoBusqueda;
	private final Object mLock = new Object();
	private JListDatosFilter mFilter;
	
	public JListDatosAdapter(Context poTabla, JListDatos values, int[] palColumns) {
	    super();
	    this.moContext = poTabla;
	    this.moList = values;
	    malColumns=palColumns;
	    mlCampoBusqueda=palColumns[0];
	  }
	@Override
	public int getCount() {
		return moList.size();
	}

	@Override
	public Object getItem(int position) {
		moList.setIndex(position);
		StringBuffer ls = new StringBuffer();
		for (int lCol = 0; lCol < malColumns.length; lCol++) {
			ls.append(moList.getFields(malColumns[lCol]).getString());
			if(lCol < (malColumns.length-1)){
				ls.append( ' ' );
			}
        }			
		return ls.toString().trim();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int lFila, View convertView, ViewGroup parent) {
        TextView row=null;
        try {
	        if(convertView==null){
	        	row = new TextView(moContext);
	        	row.setTextSize(20);
	        	
	        }else{
	            row = (TextView) convertView;
	        }
	         
			row.setTag(String.valueOf(lFila) + JFilaDatosDefecto.mcsSeparacion1);
			
			row.setText((String)getItem(lFila));
		} catch (Throwable e) {
			JDepuracion.anadirTexto(getClass().getName(), e);
		}
		
		return row;
	}
	@Override
	public Filter getFilter() {
		if (mFilter == null) {
            mFilter = new JListDatosFilter();
        }
        return mFilter;
	}	
	private class JListDatosFilter extends Filter {
        private String msUltCadena="";

		@Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {
                	if(moList.getFiltro().mbAlgunaCond()){
	                	moList.getFiltro().clear();
	                	moList.filtrarNulo();
                	}
                }
//                results.values = moList;
                results.count = moList.size();
                msUltCadena="";
            } else {
                String prefixString = prefix.toString().toLowerCase();
                synchronized (mLock) {
                	if(prefixString.length()<msUltCadena.length()){
                    	if(moList.getFiltro().mbAlgunaCond()){
    	                	moList.getFiltro().clear();
    	                	moList.filtrarNulo();
                    	}
                	}
                	moList.getFiltro().addCondicionAND(JListDatos.mclTMayorIgual, mlCampoBusqueda, prefixString);
                	moList.getFiltro().addCondicionAND(JListDatos.mclTMenorIgual, mlCampoBusqueda, prefixString+"zzzzzzz");
                	moList.filtrar();
                }
                msUltCadena=prefixString;


//                results.values = moList;
                results.count = moList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
//            moList = (JListDatos) results.values;
            if (moList.size() > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }	
}
