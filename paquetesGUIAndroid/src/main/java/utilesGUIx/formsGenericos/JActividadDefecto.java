/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import utilesAndroid.util.R;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;


/**
 *
 * @author eduardo
 */
public class JActividadDefecto extends Activity implements IPlugInFrame , ISalir, IContainer {

    private JFormEdicionParametros moParam=new JFormEdicionParametros();
    private LinearLayout moLay;
    private ViewGroup moPanel;
    private JMostrarPantallaParam moParamEntrada;
    private Menu mMenu;
    private boolean mbCancelado=false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.jformactividaddefecto);
        Bundle bun = getIntent().getExtras();
        String param1 = bun.getString(JMostrarPantalla.mcsNumeroForm);
        try {
            moLay = (LinearLayout) findViewById(R.id.root);
            moParamEntrada = JGUIxConfigGlobal.getInstancia().getMostrarPantalla().getParam(param1);
            moPanel = (ViewGroup) moParamEntrada.getCrear().getPanel(this, moParamEntrada);
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().setActividad(this);
            moLay.addView(
                    moPanel
                    , new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().setActividad(this);    
            setTitle(moParamEntrada.getTitulo());
            
        	if(moPanel instanceof JPanelGenericoAbstract){
        		((JPanelGenericoAbstract)moPanel).setActividad(this);
    		}                
        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
        
    }
    public ViewGroup getContenedor() {
        return moPanel;
    }
    
    public Object getMenu() {
        return null;
    }

    public String getIdentificador() {
        return this.getClass().getName();
    }
    public IContainer getContenedorI() {
        return this;
    }

    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }


    public JFormEdicionParametros getParametros() {
        return moParam;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    	mMenu = menu;
    	if(moPanel instanceof JPanelGenericoAbstract){
			((JPanelGenericoAbstract)moPanel).onCreateOptionsMenu(menu);
		}    	
//        
//        // Inflate the currently selected menu XML resource.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.jformiformedicionenu, menu);
        
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(moPanel instanceof JPanelGenericoAbstract){
			return ((JPanelGenericoAbstract)moPanel).onOptionsItemSelected(item);
		}    	    	
        return false;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    	if(moPanel instanceof JPanelGenericoAbstract){
			((JPanelGenericoAbstract)moPanel).onCreateContextMenu(menu, v, menuInfo);
		}    	
    }    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	if(moPanel instanceof JPanelGenericoAbstract){
			return ((JPanelGenericoAbstract)moPanel).onContextItemSelected(item);
		}    	
    	return super.onContextItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
	@Override
	protected void onDestroy() {
		limpiar();
		super.onDestroy();
	}
    
    private void limpiar(){
    	if(moPanel instanceof ISalir){
			((ISalir)moPanel).salir();
		}
    	if(moPanel instanceof JPanelGenericoAbstract){
			((JPanelGenericoAbstract)moPanel).liberar();
		}        
        moLay.removeAllViews();
        
    }
    public void salir() {

    	limpiar();
        finish();
    }

    public void setTitle(String psTitulo) {
        this.setTitle((CharSequence)psTitulo);
    }
 
}
