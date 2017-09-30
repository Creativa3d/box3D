/*
h*-nge this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos.edicion;


import ListDatos.ECampoError;
import ListDatos.JListDatos;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesAndroid.util.R;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;


/**
 *
 * @author eduardo
 */
public class JActividadEdicion extends AppCompatActivity implements IPlugInFrame, IFormEdicionLista, IContainer, android.support.v7.app.ActionBar.TabListener {
	 
    private IListaElementos<IFormEdicion> moListaEdiciones = new JListaElementos<IFormEdicion>();
    private IListaElementos<Fragment> moListaFragmentos = new JListaElementos<Fragment>();
    private JFormEdicionParametros moParam=new JFormEdicionParametros();
    private LinearLayout moLay;
    private JMostrarPantallaParam moParamEntrada;
    private Menu mMenu;
    private boolean mbCancelado=false;
    private boolean mbInicializado=false;
	private JActividadEdicionAdap adapter;
	private ViewPager viewpager;
	private boolean mbNuevoRapido=false;
	private boolean mbCrearOtro=false;
	

    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Bundle bun = getIntent().getExtras();
        String param1 = bun.getString(JMostrarPantalla.mcsNumeroForm);
        try {
            moParamEntrada = JGUIxConfigGlobal.getInstancia().getMostrarPantalla().getParam(param1);
            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().setActividad(this);
            
            Object loPaneles = moParamEntrada.getCrear().getPanel(this, moParamEntrada);
            setTitle(moParamEntrada.getTitulo());
            if(IListaElementos.class.isAssignableFrom(loPaneles.getClass())){
            	pestanas((IListaElementos) loPaneles);
            }else{
            	componente((ViewGroup) loPaneles);
            }
            if(moListaEdiciones.get(0).getTabla()!=null
        		&& moListaEdiciones.get(0).getTabla().getList().getModoTabla() == JListDatos.mclNuevo){
            	mbCrearOtro=true;
            }

        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }

        
    }
    private void componente(ViewGroup poComp){
        setContentView(R.layout.edicion);
        moLay = (LinearLayout) findViewById(R.id.root);
        moLay.addView(
        		poComp
                , new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().setActividad(this);
        if(
                JCadenas.isVacio(moParamEntrada.getTitulo())
                &&  IFormEdicion.class.isAssignableFrom(poComp.getClass())
                && !JCadenas.isVacio(((IFormEdicion)poComp).getTitulo())

                ){
            setTitle(((IFormEdicion)poComp).getTitulo());
        }
        moListaEdiciones.add((IFormEdicion)poComp);         
    }
        
    private void pestanas(IListaElementos<IFormEdicion> poLista){
        setContentView(R.layout.ediciontab);
    	for(IFormEdicion loEdit : poLista){
    		moListaEdiciones.add(loEdit);
    		if(Fragment.class.isAssignableFrom(loEdit.getClass())){
    			moListaFragmentos.add((Fragment) loEdit);
    		}else{
                JFragmentoActividad loFrag = new JFragmentoActividad();
                loFrag.setVista((View) loEdit);
    			moListaFragmentos.add(loFrag);
    		}
    	}
        // Obtener instancia de la Action Bar
        final ActionBar actionBar = getSupportActionBar();

        // Activar el modo de navegación con tabs en la Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        adapter = new JActividadEdicionAdap(getSupportFragmentManager());
        // Obtener el ViewPager y setear el adaptador y la escucha
        viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Coordinar el item del pager con la pestaña
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // Añadir 3 pestañas y asignarles un título y escucha
        for (int i = 0; i < adapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            // Habilita el titulo si lo prefieres
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this));
        }
        if(poLista.size()>0
                && !JCadenas.isVacio(poLista.get(0).getTitulo())
                && JCadenas.isVacio(moParamEntrada.getTitulo())){
            setTitle(poLista.get(0).getTitulo());
        }
            	
    }

    @Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Coordinar la pestaña seleccionada con el item del viewpager
    	JDepuracion.anadirTexto("JFormDatosObjTab", "onTabSelected");   
        viewpager.setCurrentItem(tab.getPosition());
    	JDepuracion.anadirTexto("JFormDatosObjTab", "setCurrentItem");   
	}    
	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}
    public ViewGroup getContenedor() {
        return null;
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
    private void fin(){
        JGUIxConfigGlobal.getInstancia().getMostrarPantalla().setActividad(null);    

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            Object loPanel = moListaEdiciones.get(i);
            if(IFormEdicionAndroid.class.isAssignableFrom(loPanel.getClass())){
            	((IFormEdicionAndroid)loPanel).onActivityResult(requestCode, resultCode, data);
            }
        }    	
        super.onActivityResult(requestCode, resultCode, data);
        
    }    
 // Set the check state of an actionbar item that has its actionLayout set to a layout
    // containing a checkbox with the ID action_item_checkbox.
    private void setActionBarCheckboxChecked(MenuItem it, boolean checked)
    {
        if (it == null)
            return;

        it.setChecked(checked);

        if(it.getActionView()!=null) {
            // Since it is shown as an action, and not in the sub-menu we have to manually set the icon too.
            CheckBox cb = (CheckBox) it.getActionView().findViewById(R.id.action_item_checkbox);
            if (cb != null)
                cb.setChecked(checked);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hold on to this
        mMenu = menu;
        
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jformiformedicionrapidomenu, menu);
        

        // Restore the check state e.g. if the device has been rotated.
        final MenuItem loMenuCrearOtro = menu.findItem(R.id.menuCrearOtro);
        setActionBarCheckboxChecked(loMenuCrearOtro, mbNuevoRapido);

        if(loMenuCrearOtro.getActionView()!=null) {
            CheckBox cb = (CheckBox) menu.findItem(R.id.menuCrearOtro).getActionView();
            if (cb != null) {
                // Set the text to match the item.
                cb.setText(loMenuCrearOtro.getTitle());
                // Add the onClickListener because the CheckBox doesn't automatically trigger onOptionsItemSelected.
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOptionsItemSelected(loMenuCrearOtro);
                    }
                });
            }
        }
        return true;
    }
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem menuCrearOtro = menu.findItem(R.id.menuCrearOtro);      
        menuCrearOtro.setVisible(mbCrearOtro);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuAceptar){
            btnAceptarActionPerformed();
            return true;
        }
        if(item.getItemId()==R.id.menuCancelar){
            btnCancelarActionPerformed();
            return true;
        }
        if (item.getItemId()==R.id.menuCrearOtro){
        	// Toggle the checkbox.
            setActionBarCheckboxChecked(item, !item.isChecked());

            // Do whatever you want to do when the checkbox is changed.
            mbNuevoRapido=item.isChecked();
            
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }    
            
    @Override
    protected void onResume() {
        super.onResume();
        try {
        	if(!mbInicializado){
	        	mbInicializado=true;
	        	for(int i = 0 ; i < moListaEdiciones.size(); i++){
	                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
		            initEdicion(loPanel);
	        	}	        	
        	}
        	for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                if(IFormEdicionAndroid.class.isAssignableFrom(loPanel.getClass())){
                	((IFormEdicionAndroid)loPanel).onResume();
                } 
        	}	        	
        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//          if(mbCancelado){
//              fin();
//          }else{
//              if(btnAceptarActionPerformed()){
//                  fin();
//              }else{
//              }
//          }
//          // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
//          return true;
//        }
//      //para las demas cosas, se reenvia el evento al listener habitual
//        return super.onKeyDown(keyCode, event);
//      } 
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    
	  if (keyCode == KeyEvent.KEYCODE_BACK) {
		  mbNuevoRapido=false;
	    // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
	    return btnAceptarActionPerformed();
	  }
	//para las demas cosas, se reenvia el evento al listener habitual
	  return super.onKeyDown(keyCode, event);
	}     
    //inicializamos un panel
    private void initEdicion(final IFormEdicion poPanel) throws Exception{
    	if(IFormEdicionAndroid.class.isAssignableFrom(poPanel.getClass())){
        	((IFormEdicionAndroid)poPanel).setActivity(this);
        }    	
        poPanel.rellenarPantalla();
        poPanel.ponerTipoTextos();
        if(View.class.isAssignableFrom(poPanel.getClass())){
            mostrarDatosBD((View)poPanel);
        }        
        poPanel.mostrarDatos();
        poPanel.habilitarSegunEdicion();
    }
    /**
     * Anadimos una edicion y la inicializamos
     */
    public void addEdicion(final IFormEdicion poPanel) throws Exception{
        moListaEdiciones.add(poPanel);
        initEdicion(poPanel);
    }
    /**
     * @return devolvemos la lista de ediciones
     */
    public IListaElementos getListaEdiciones() {
        return moListaEdiciones;
    }    
    public static void mostrarDatosBD(final View poComponente){
        if( ViewGroup.class.isAssignableFrom(poComponente.getClass())){
            ViewGroup loGroup = (ViewGroup) poComponente;
            for(int i = 0; i < loGroup.getChildCount(); i++){
                View loAux = loGroup.getChildAt(i);
                //establecemos el bloque al componente concreto
                if(ITextBD.class.isAssignableFrom(loAux.getClass())){
                    ITextBD loText = (ITextBD)loAux;
                    loText.mostrarDatosBD();
                }
                //llamada recursiva para los contenedores
                if(ViewGroup.class.isAssignableFrom(loAux.getClass()) &&
                   poComponente != loAux
                  ){
                    mostrarDatosBD(loAux);
                }
            }
        }else{
            if(ITextBD.class.isAssignableFrom(poComponente.getClass())){
                ITextBD loText = (ITextBD)poComponente;
                loText.mostrarDatosBD();
            }
        }
    }
    public static void establecerDatosBD(final View  poComponente) throws ECampoError{
        if( ViewGroup.class.isAssignableFrom(poComponente.getClass())){
            ViewGroup loGroup = (ViewGroup) poComponente;
            for(int i = 0; i < loGroup.getChildCount(); i++){
                View loAux = loGroup.getChildAt(i);
                //establecemos el bloque al componente concreto
                if(ITextBD.class.isAssignableFrom(loAux.getClass())){
                    ITextBD loText = (ITextBD)loAux;
                    loText.establecerDatosBD();
                }
                //llamada recursiva para los contenedores
                if(ViewGroup.class.isAssignableFrom(loAux.getClass()) &&
                   poComponente != loAux
                  ){
                    mostrarDatosBD(loAux);
                }
            }
        }else{
            if(ITextBD.class.isAssignableFrom(poComponente.getClass())){
                ITextBD loText = (ITextBD)poComponente;
                loText.mostrarDatosBD();
            }
        }
    }
    /**
     * Indica si todos los IFormEdicion son editables
     */
    public boolean isEditable() {
        boolean lbSoloLectura = true;
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
            lbSoloLectura &= loPanel.getParametros() != null && loPanel.getParametros().isSoloLectura();
        }
        return !lbSoloLectura;
    }

    private void btnCancelarActionPerformed() {                                            
        try{
            mbCancelado = true;
            //para cada uno cancelamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                loPanel.cancelar();
            }
            moListaEdiciones.clear();
            fin();
            
        }catch(Exception e){
            JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }        
        
    }                                           

    private boolean btnAceptarActionPerformed() {                                           
        boolean lbContinuar = true;
        try{
            if(!isEditable()){
                throw new Exception(JGUIxConfigGlobal.getInstancia().getEdicionNavegadorMensajeSoloLectura() );
            }
            //para cada uno establecemos datos y  validamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                if(View.class.isAssignableFrom(loPanel.getClass())){
                    establecerDatosBD((View)loPanel);
                }        
                loPanel.establecerDatos();
                lbContinuar &= loPanel.validarDatos();
            }
            //para cada uno aceptamos
            for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                loPanel.aceptar(); 
            }
            if(mbNuevoRapido){
	            //para cada uno nuevo
	            for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
	                IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
	                loPanel.nuevo();
	                if(View.class.isAssignableFrom(loPanel.getClass())){
	                    JActividadEdicion.mostrarDatosBD((View)loPanel);
	                }        
	                loPanel.mostrarDatos();
	                loPanel.habilitarSegunEdicion();
	            }              
            }
            if(lbContinuar && !mbNuevoRapido){
            	JMsgBox.mensajeFlotante(getBaseContext(), getString( R.string.guardado) );
                fin();
            }
        }catch(Exception e){
            lbContinuar=false;
            JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }              
        return lbContinuar;
    }                                          

    public void salir() {
        btnCancelarActionPerformed();
    }

    public void setTitle(String psTitulo) {
        super.setTitle(psTitulo);
    }    

    /*
    Adaptador de fragmentos para el ViewPager
     */
    public class JActividadEdicionAdap extends FragmentPagerAdapter {


    	public JActividadEdicionAdap(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
        	if(moListaEdiciones==null){
        		return 0;
        	}
            return moListaEdiciones.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	if(moListaEdiciones==null){
        		return "";
        	}
        	if(position<moListaEdiciones.size()){
        		return moListaEdiciones.get(position).getTitulo();
        	}else{
        		return "";
        	}
        }
        
        @Override
        public Fragment getItem(int position) {
        	if(moListaEdiciones==null){
        		return null;
        	}
        	if(position<moListaFragmentos.size()){
        		return (Fragment) moListaFragmentos.get(position);
        	}else{
                return null; 
        	}
        }
    }

}
