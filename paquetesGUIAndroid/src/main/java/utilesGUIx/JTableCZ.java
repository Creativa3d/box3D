/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import ListDatos.JFilaDatosDefecto;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JListaElementos;
import utilesAndroid.util.JGUIAndroid;
import utilesGUI.tabla.IComponentParaTabla;
import utilesGUI.tabla.ITabla;
import utilesGUIx.msgbox.JMsgBox;


/**
 *
 * @author eduardo
 */
public class JTableCZ extends LinearLayout implements ITableCZ {

    public static final int mclSeleccionPorCelda = 0;
    public static final int mclSeleccionPorFila = 1;
    public static final int mclSeleccionPorColumna = 2;
    private ITabla moTabla;
    private ITableCZColores moColores;
    private int mlColorBack = Color.WHITE;
    private int mlColorFore = Color.BLACK;
    private HashMap moListaClases = new HashMap();
    private TableLayout moCabezera;
    private ListView moCuerpo;
    private TableRow.LayoutParams moLayoutParamCuerpo[];
    private int[] malLong;
    private IListaElementos moListenersCELLClick = new JListaElementos();
    private IListaElementos moListenersCELLLongClick = new JListaElementos();
    private IListaElementos moSeleccionadasFilas = new JListaElementos();
    private IListaElementos moSeleccionadasColum = new JListaElementos();
    private int mlTipoSeleccion = mclSeleccionPorFila;
    private int mlColorSeleccionBack = Color.BLUE;
    private int mlColorSeleccionFore = Color.WHITE;
    private boolean mbSeguirRefresco = true;
    private int mlRefrescandoDatos = 0;
    private int mlHeightFila=50;
    private OnClickListener moOnClickListenerCelda = new OnClickListener() {
        public void onClick(View arg0) {
            try {
                setSelectedCelda(arg0.getTag());
                for (int i = 0; i < moListenersCELLClick.size(); i++) {
                    OnClickListener lo = (OnClickListener) moListenersCELLClick.get(i);
                    lo.onClick(arg0);
                }
            } catch (Throwable e) {
                JMsgBox.mensajeFlotante(JTableCZ.this.getContext(), e.toString());
            }
        }
    };
    private OnLongClickListener moOnLongClickListenerCelda = new OnLongClickListener() {
        public boolean onLongClick(View arg0) {
            try {
                setSelectedCelda(arg0.getTag());
                for (int i = 0; i < moListenersCELLLongClick.size(); i++) {
                    OnLongClickListener lo = (OnLongClickListener) moListenersCELLLongClick.get(i);
                    lo.onLongClick(arg0);
                }
                return true;
            } catch (Throwable e) {
                JMsgBox.mensajeFlotante(JTableCZ.this.getContext(), e.toString());
            }
            return false;
        }
    };
    private TableRow moRowCabezera;
    private JTableCZAdapter moTableCZAdapter;
    private static final ColeccionesMemoria moColeccionesMemoria = new ColeccionesMemoria();
    private boolean mbCabezera = true;
    public JTableCZ(Context context, AttributeSet attrs) {
        super(context, attrs);
        mlHeightFila = JGUIAndroid.getPixelIndepDensidad (getContext(), 50);

        setOrientation(VERTICAL);
        moCabezera = new TableLayout(this.getContext());

        addView(moCabezera, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        moCuerpo = new ListView(this.getContext());
        LayoutParams loParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addView(moCuerpo, loParams);



    }

    private void crearColeccionesBasicas() {
        moListaClases.put(Boolean.class, JCheckBoxRender.class);
    }

    public JTableCZ(Context context) {
        this(context, null);
    }

    public void setSelectedCelda(Object poTag) throws Exception {
        if (poTag instanceof View) {
            poTag = ((View) poTag).getTag();
        }
        if (poTag == null) {
            throw new Exception("TAG nulo");
        }
        String[] lasArray = JFilaDatosDefecto.moArrayDatos(poTag.toString(), JFilaDatosDefecto.mccSeparacion1);
        int lFila = (int) JConversiones.cdbl(lasArray[0]);
        int lCol = (int) JConversiones.cdbl(lasArray[1]);
        setSelectedRow(lFila);
        setSelectedColumn(lCol);
    }

    public void setSelectedRow(int plRow) {
        pintarSelecciones(false);
        moSeleccionadasFilas.clear();
        moSeleccionadasFilas.add(new Integer(plRow));
        pintarSelecciones(true);
    }

    public void setSelectedColumn(int plCol) {
        pintarSelecciones(false);
        moSeleccionadasColum.clear();
        moSeleccionadasColum.add(new Integer(plCol));
        pintarSelecciones(true);
    }

    public int getSelectedRow() {
        int lResult = -1;
        comprobarFilas();
        if (moSeleccionadasFilas.size() > 0) {
            lResult = ((Integer) moSeleccionadasFilas.get(0)).intValue();
        }
        return lResult;
    }

    public int getSelectedCol() {
        int lResult = -1;
        comprobarColumns();
        if (moSeleccionadasColum.size() > 0) {
            lResult = ((Integer) moSeleccionadasColum.get(0)).intValue();
        }
        return lResult;
    }

    public void addSelectedRow(int plRow) {
        moSeleccionadasFilas.add(new Integer(plRow));
        pintarSelecciones(true);
    }

    public int[] getSelectedRows() {
        comprobarFilas();
        int[] lal = new int[moSeleccionadasFilas.size()];
        for (int i = 0; i < moSeleccionadasFilas.size(); i++) {
            lal[i] = ((Integer) moSeleccionadasFilas.get(i)).intValue();
        }
        return lal;
    }

    public void addSelectedCol(int plCol) {
        moSeleccionadasColum.add(new Integer(plCol));
        pintarSelecciones(true);
    }

    public int[] getSelectedCols() {
        comprobarColumns();
        int[] lal = new int[moSeleccionadasColum.size()];
        for (int i = 0; i < moSeleccionadasColum.size(); i++) {
            lal[i] = ((Integer) moSeleccionadasColum.get(i)).intValue();
        }
        return lal;
    }

    public boolean isSelectedRow(int plRow) {
        boolean lbResult = false;
        for (int i = 0; i < moSeleccionadasFilas.size() && !lbResult; i++) {
            int lFila = ((Integer) moSeleccionadasFilas.get(i)).intValue();
            lbResult = (lFila == plRow);
        }
        return lbResult;
    }

    public boolean isSelectedCol(int plcol) {
        boolean lbResult = false;
        for (int i = 0; i < moSeleccionadasColum.size() && !lbResult; i++) {
            int lCol = ((Integer) moSeleccionadasColum.get(i)).intValue();
            lbResult = (lCol == plcol);
        }
        return lbResult;
    }

    private boolean mbComprobarFila(int plFila) {
        if (plFila < 0 || plFila >= moTabla.getRowCount()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean mbComprobarCol(int plFila) {
        if (plFila < 0 || plFila >= moTabla.getColumnCount()) {
            return false;
        } else {
            return true;
        }
    }

    private void comprobarFilas() {
        for (int i = 0; i < moSeleccionadasFilas.size(); i++) {
            int lFila = ((Integer) moSeleccionadasFilas.get(i)).intValue();
            if (!mbComprobarFila(lFila)) {
                moSeleccionadasFilas.remove(i);
                i--;
            }
        }
    }

    private void comprobarColumns() {
        for (int i = 0; i < moSeleccionadasColum.size(); i++) {
            int lCol = ((Integer) moSeleccionadasColum.get(i)).intValue();
            if (!mbComprobarCol(lCol)) {
                moSeleccionadasColum.remove(i);
                i--;
            }
        }
    }

    public void setCabezera(boolean pbCabezera){
        mbCabezera=pbCabezera;
    }

    public void setModel(ITabla poTabla) throws Exception {
        moTabla = poTabla;
        malLong = new int[moTabla.getColumnCount()];
        for (int i = 0; i < malLong.length; i++) {
            malLong[i] = 80;
        }
        crearEstructura();
        moTableCZAdapter = new JTableCZAdapter(this, moTabla);
        moCuerpo.setAdapter(moTableCZAdapter);
    }

    public void setColumnLong(int plColumn, int pl) {
        malLong[plColumn] = pl;
        modificarLayout(plColumn);
    }

    public int getColumnLong(int plColumn) {
        return malLong[plColumn];
    }

    public ITabla getModel() {
        return moTabla;
    }

    public void setTableCZColores(final ITableCZColores poColores) {
        moColores = poColores;
    }

    public ITableCZColores getTableCZColores() {
        return moColores;
    }

    public void addOnClickListenerCELL(OnClickListener poList) {
        moListenersCELLClick.add(poList);
    }

    public void addOnLongClickListenerCELL(OnLongClickListener poList) {
        moListenersCELLLongClick.add(poList);
    }

    private void borrarLinea(TableRow row) {
        for (int j = 0; j < row.getChildCount(); j++) {
            addViewCuerpo(row.getChildAt(j));
        }
        row.removeAllViews();
        addRowCuerpo(row);
    }
    public View getView(int plRow, int plCol) {
        TableRow loRow = (TableRow) moCuerpo.getChildAt(plRow);
        if(loRow==null){
            return null;
        }else{
            return loRow.getChildAt(plCol);
        }
    }

    void pintarSelecciones(boolean pbPintarSeleccion) {
        int[] lalSel = getSelectedRows();
        for (int i = 0; i < lalSel.length && mlTipoSeleccion == mclSeleccionPorFila; i++) {
            int lrow = lalSel[i];
            for (int lcol = 0; lcol < moTabla.getColumnCount(); lcol++) {
                int lColorBack = Color.TRANSPARENT;
                int lColorFore = Color.TRANSPARENT;
                if (moColores != null) {
                    ColorCZ loColorBack = moColores.getColorBackground(moTabla.getValueAt(lrow, lcol), pbPintarSeleccion, false, lrow, lcol);
                    ColorCZ loColorFore = moColores.getColorForeground(moTabla.getValueAt(lrow, lcol), pbPintarSeleccion, false, lrow, lcol);
                    if(loColorBack!=null){
                        lColorBack = loColorBack.getColor();
                    }
                    if(loColorFore!=null){
                        lColorFore = loColorFore.getColor();
                    }
                }
                if (pbPintarSeleccion) {
                    if (lColorBack == Color.TRANSPARENT) {
                        lColorBack = mlColorSeleccionBack;
                    }
                    if (lColorFore == Color.TRANSPARENT) {
                        lColorFore = mlColorSeleccionFore;
                    }
                } else {
                    if (lColorBack == Color.TRANSPARENT) {
                        lColorBack = mlColorBack;
                    }
                    if (lColorFore == Color.TRANSPARENT) {
                        lColorFore = mlColorFore;
                    }
                }
                View loView = getView(lrow, lcol);
                if(loView!=null){
                    setColorView(loView, lColorBack, lColorFore);
                }
            }
        }
        lalSel = getSelectedCols();
        for (int i = 0; i < lalSel.length && mlTipoSeleccion == mclSeleccionPorColumna; i++) {
            int lcol = lalSel[i];
            for (int lrow = 0; lrow < moTabla.getRowCount(); lrow++) {
                int lColorBack = Color.TRANSPARENT;
                int lColorFore = Color.TRANSPARENT;
                if (moColores != null) {
                    ColorCZ loColorBack = moColores.getColorBackground(moTabla.getValueAt(lrow, lcol), true, false, lrow, lcol);
                    ColorCZ loColorFore = moColores.getColorBackground(moTabla.getValueAt(lrow, lcol), true, false, lrow, lcol);
                    if(loColorBack!=null){
                        lColorBack = loColorBack.getColor();
                    }
                    if(loColorFore!=null){
                        lColorFore = loColorFore.getColor();
                    }
                }
                if (lColorBack == Color.TRANSPARENT) {
                    lColorBack = mlColorSeleccionBack;
                }
                if (lColorFore == Color.TRANSPARENT) {
                    lColorFore = mlColorSeleccionFore;
                }
                View loView = getView(lrow, lcol);
                if(loView!=null){
                    setColorView(loView, lColorBack, lColorFore);
                }
            }
        }
    }

    private void setColorView(View poView, int plColorBack, int plColorFore) {

        if (moColores != null) {
            if (plColorBack != 0) {
                poView.setBackgroundColor(plColorBack);
            }
            if (TextView.class.isAssignableFrom(poView.getClass())) {
                if (plColorFore != 0) {
                    ((TextView) poView).setTextColor(plColorFore);
                }
            }
        }
    }

    public void limpiar() {
        //eliminamos completo
        for (int i = 0; i < moCuerpo.getChildCount(); i++) {
            TableRow row = (TableRow) moCuerpo.getChildAt(i);
            borrarLinea(row);
        }
        moCuerpo.removeHeaderView(moRowCabezera);
//        moCuerpo.removeAllViews();
        moCabezera.removeAllViews();
    }

    private void modificarLayout(int plColumn) {
        if (moLayoutParamCuerpo != null && moLayoutParamCuerpo.length > plColumn) {
            TableRow.LayoutParams loLayoutParamCuerpo = moLayoutParamCuerpo[plColumn];
            if (loLayoutParamCuerpo != null) {
                loLayoutParamCuerpo.width = malLong[plColumn];
            }
            if(malLong[plColumn]==0){
                loLayoutParamCuerpo.height = mlHeightFila;
            }
        }

    }

    private void modificarLayout() {
        if (moLayoutParamCuerpo == null || moLayoutParamCuerpo.length != moTabla.getColumnCount()) {
            moLayoutParamCuerpo = new TableRow.LayoutParams[moTabla.getColumnCount()];
        }
        for (int i = 0; i < moLayoutParamCuerpo.length; i++) {
            int lLong = 0;
            if (malLong.length > i) {
                lLong = malLong[i];
            }
            TableRow.LayoutParams loLayoutParamCuerpo = moLayoutParamCuerpo[i];
            if (loLayoutParamCuerpo == null) {
                loLayoutParamCuerpo = new TableRow.LayoutParams(
                        lLong, TableRow.LayoutParams.MATCH_PARENT);
            } else {
                loLayoutParamCuerpo.width = lLong;
            }
            if(lLong==0){

                loLayoutParamCuerpo.height = mlHeightFila;
            }

            loLayoutParamCuerpo.setMargins(1, 1, 1, 1);
            moLayoutParamCuerpo[i] = loLayoutParamCuerpo;
        }
    }

    private void crearEstructura() throws Exception {
        limpiar();

        modificarLayout();


        //cabezera
        if(moRowCabezera!=null){
            moCuerpo.removeHeaderView(moRowCabezera);
        }
        if(mbCabezera){
            moRowCabezera = new TableRow(getContext());
            for (int lCol = 0; lCol < moTabla.getColumnCount(); lCol++) {
                TextView loView = new TextView(getContext());
                ShapeDrawable loDra = new ShapeDrawable(new RectShape());
                //            Shader shader1 = new LinearGradient(0, 0, 0, 50, new int[] {
                //                        0xFFBAF706, 0xFF4CD52F  }, null, Shader.TileMode.CLAMP);
                //            loDra.getPaint().setShader(shader1);
                loDra.getPaint().setStrokeWidth(1);
                loDra.getPaint().setStrokeCap(Paint.Cap.SQUARE);;
                loDra.getPaint().setColor(0xFFAEE4E8);
                loDra.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
                loView.setBackgroundDrawable(loDra);
                loView.setBackgroundColor(0xFFAEE4E8);
                loView.setTextColor(Color.BLACK);

                if(moLayoutParamCuerpo[lCol].width>0){
                    loView.setText(moTabla.getColumnName(lCol));
                }
                loView.setPadding(2, 2, 2, 2);

                moRowCabezera.addView(loView, moLayoutParamCuerpo[lCol]);
            }
            moCabezera.addView(moRowCabezera);
//	        moCuerpo.addHeaderView(moRowCabezera);
        }
    }


    public void refrescarDatos() throws Exception {
        try {
            synchronized (this) {
                while(mlRefrescandoDatos>0){
                    mbSeguirRefresco=false;
                    try{
                        wait(100);
                    }catch(Exception e){}
                }
                mbSeguirRefresco=true;
                mlRefrescandoDatos++;
            }

            moTableCZAdapter.notifyDataSetChanged();
//            
//            //add filas
//            while (moTabla.getRowCount() > moCuerpo.getChildCount() && mbSeguirRefresco) {
//                TableRow row = getRowCuerpo();
//                moCuerpo.addView(row);
//                addViewsAFila(row, moTabla.getColumnCount());
//            }
//            //borramos sobrantes
//            while (moTabla.getRowCount() < moCuerpo.getChildCount() && mbSeguirRefresco) {
//                TableRow row = (TableRow) moCuerpo.getChildAt(moCuerpo.getChildCount() - 1);
//                borrarLinea(row);
//                moCuerpo.removeViewAt(moCuerpo.getChildCount() - 1);
//            }
//            //refrescar datos
//            for (int lFila = 0; lFila < moTabla.getRowCount() && mbSeguirRefresco; lFila++) {
//                TableRow row = (TableRow) moCuerpo.getChildAt(lFila);
//                row.setTag(String.valueOf(lFila) + JFilaDatosDefecto.mcsSeparacion1);
//                for (int lCol = 0; lCol < moTabla.getColumnCount() && mbSeguirRefresco; lCol++) {
//                    setValorCelda(lFila, lCol, row.getChildAt(lCol));
//                }
//            }
            if (mbSeguirRefresco) {
                pintarSelecciones(true);
            }
        } finally {
            synchronized (this) {
                mlRefrescandoDatos--;
                notifyAll();
            }
        }
    }


    public void refrescar() throws Exception {
//        crearEstructura();
        refrescarDatos();

    }

    private void addViewsAFila(TableRow poRow, int plColumns) throws Exception {
        for (; plColumns > poRow.getChildCount();) {
            View loV = getViewCuerpo(moTabla.getColumnClass(poRow.getChildCount()));
            poRow.addView(loV, moLayoutParamCuerpo[poRow.getChildCount()]);
        }
    }

    TableRow.LayoutParams[] getLayoutParamCuerpo(){
        return moLayoutParamCuerpo;
    }

    synchronized View getViewCuerpo(Class poClass) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        View loView = null;
        IListaElementos loLista = null;

        Class loVistaClass = (Class) moListaClases.get(poClass);
        if (loVistaClass == null) {
            loVistaClass = TextView.class;
        }
        loView = getMemoriaSingle().getViewCuerpo(loVistaClass);

        if (loView == null) {
            loView = (View) loVistaClass.getConstructor(Context.class).newInstance(getContext());
            loView.setPadding(2, 2, 2, 2);
            loView.setOnClickListener(moOnClickListenerCelda);
            loView.setOnLongClickListener(moOnLongClickListenerCelda);

        } else {
            loView.setBackgroundColor(Color.WHITE);
            if (loView instanceof TextView) {
                TextView loT = (TextView) loView;
                loT.setTextColor(Color.BLACK);
            }
        }
        return loView;
    }

    synchronized void addViewCuerpo(View poView) {
        getMemoriaSingle().addViewCuerpo(poView);
    }

    synchronized TableRow getRowCuerpo() {
        TableRow loRow = null;

        loRow = getMemoriaSingle().getRowCuerpo();

        if (loRow == null) {
            loRow = new TableRow(getContext());
            loRow.setBackgroundColor(Color.BLUE);
            loRow.setClickable(true);
        }
        return loRow;

    }

    synchronized void addRowCuerpo(TableRow row) {
        getMemoriaSingle().addRowCuerpo(row);
    }

    static ColeccionesMemoria getMemoriaSingle() {
        return moColeccionesMemoria;
    }

    //hacer un interfaz tablerender para cada tipo componente, por ahora asi
    void setValorCelda(int plFila, int plCol, View poView) throws Exception {
        Object loValor = moTabla.getValueAt(plFila, plCol);
        if (IComponentParaTabla.class.isAssignableFrom(poView.getClass())) {
            ((IComponentParaTabla) poView).setValueTabla(
                    loValor);
        } else if (TextView.class.isAssignableFrom(poView.getClass())) {
            ((TextView) poView).setText(
                    loValor == null
                            ? ""
                            : loValor.toString());
        }
        setColorView(poView, mlColorBack, mlColorFore);
        if (moColores != null) {
            int lColorBack = Color.TRANSPARENT;
            int lColorFore = Color.TRANSPARENT;
            ColorCZ loColorBack = moColores.getColorBackground(moTabla.getValueAt(plFila, plCol), false, false, plFila, plCol);
            ColorCZ loColorFore = moColores.getColorForeground(moTabla.getValueAt(plFila, plCol), false, false, plFila, plCol);
            if(loColorBack!=null){
                lColorBack = loColorBack.getColor();
            }
            if(loColorFore!=null){
                lColorFore = loColorFore.getColor();
            }
            setColorView(poView, lColorBack, lColorFore);
        }

        poView.setTag(String.valueOf(plFila) + JFilaDatosDefecto.mcsSeparacion1
                + String.valueOf(plCol) + JFilaDatosDefecto.mcsSeparacion1);
        poView.setFocusable(moTabla.isCellEditable(plFila, plCol));

    }
}