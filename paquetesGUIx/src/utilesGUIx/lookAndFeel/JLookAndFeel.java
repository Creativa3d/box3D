/*
 * JLookAndFeel.java
 *
 * Created on 3 de enero de 2006, 11:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.lookAndFeel;

import ListDatos.IFilaDatos;
import java.awt.Component;
import java.lang.reflect.Method;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;


public class JLookAndFeel {
    
    private JLookAndFeelEstilo moEstilo=null;
    private IFilaDatos moTema=null;
    private Component moComponenteBase=null;
    private final IListaElementos moEstilos;

    /** Creates a new instance of JLookAndFeel */
    public JLookAndFeel() {
        super();
        moEstilos = new JListaElementos();
        try{
            LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels(); 
            for (int i = 0 ; i < looks.length;i++) {
                LookAndFeelInfo look = looks[i];
                addEstilo(new JLookAndFeelEstilo(look.getClassName(), look.getName()));
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }
    
    public void setComponenteBase(Component c){
        moComponenteBase = c;
    }
    public Component getComponenteBase(){
        return moComponenteBase;
    }


    /**
     * Devuelve los estilos que hay
     */
    public IListaElementos getEstilos(){
        return moEstilos;
    }
    /**
     * añadimos un estilo
     */
    public void addEstilo(JLookAndFeelEstilo poEstilo){
        moEstilos.add(poEstilo);
    }
    /**Devuelve el estilo actual*/
    public JLookAndFeelEstilo getEstilo() {
        return moEstilo;
    }
    /**Establece un estilo*/
    public void setEstilo(JLookAndFeelEstilo poEstilo) throws Exception {
        moEstilo = poEstilo;
        try{
    //        UIManager.setLookAndFeel(poEstilo.msEstilo);

    //OJO: NO USAR        UIManager.setLookAndFeel((LookAndFeel)this.getClass().getClassLoader().loadClass(moEstilo.msEstilo).newInstance() );
    //        UIManager.setLookAndFeel(poEstilo.msEstilo);
    //        UIManager.getLookAndFeelDefaults().put("ClassLoader", getClass().getClassLoader());
            //OJO PROBAR MAS VECES ANTES DE DESCOMENTAR
            Class clazz = this.getClass().getClassLoader().loadClass(moEstilo.msEstilo);
            ClassLoader clazzLoader = clazz.getClassLoader();
            UIManager.getDefaults().put("ClassLoader", clazzLoader);
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(clazzLoader);
                UIManager.setLookAndFeel(clazz.getName());
                if(moComponenteBase!=null){
                    SwingUtilities.updateComponentTreeUI(moComponenteBase);
                }
            } finally {
                Thread.currentThread().setContextClassLoader(contextClassLoader);
            }
        }catch(Throwable e){
            e.printStackTrace();
        }

        
    }
    public void setEstilo(String psEstilo) throws Exception {
        String lsLayoutExistente = "";
        try{
        if(UIManager.getLookAndFeel()!=null &&
           UIManager.getLookAndFeel().getLayoutStyle()!=null ){
            lsLayoutExistente=UIManager.getLookAndFeel().getLayoutStyle().getClass().getName();
        }
        }catch(NoSuchMethodError ex){
        }catch(Throwable ex){}

        for(int i = 0; i< moEstilos.size() && !lsLayoutExistente.equals(psEstilo);i++){
            JLookAndFeelEstilo loEstilo = (JLookAndFeelEstilo)moEstilos.get(i);
            if (loEstilo.msEstilo.compareTo(psEstilo)==0){
                setEstilo(loEstilo);
            }
        }
    }
    /**Devuelve el tema actual*/
    public IFilaDatos getTema() {
        return moTema;
    }
    /**Establece el tema actual*/
    public void setTema(IFilaDatos poTema) throws Exception {
        moTema = poTema;
        if(moTema!=null){
            Class loEstilo = Class.forName(moEstilo.msEstilo);
            Method loMetodo = loEstilo.getDeclaredMethod(moTema.msCampo(JLookAndFeelEstilo.mclMetodo), new Class[]{String.class});
            loMetodo.invoke(null,new Object[]{moTema.msCampo(JLookAndFeelEstilo.mclTema)});
        }
    }
    /**Establece el tema actual*/
    public void setTema(String psTema) throws Exception {
        if(psTema.compareTo("")==0){
            setTema((IFilaDatos)null);
        }else{
            for(int i = 0; i< moEstilo.moTemas.size();i++){
                IFilaDatos loTema = (IFilaDatos)moEstilo.moTemas.get(i);
                if (loTema.msCampo(JLookAndFeelEstilo.mclTema).compareTo(psTema)==0){
                    setTema(loTema);
                }
            }
        }
    }
    /**Mostramos el formulario*/
    public void mostrar(){
        JFormLookAndFeel loForm = new JFormLookAndFeel(new java.awt.Frame(), true, this);
        loForm.setVisible(true);
    }
    
    public static void rellenarLookAndFeel(){
        try{
            UIManager.installLookAndFeel("Plastic 3d", "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("Líquido", "com.birosoft.liquid.LiquidLookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("NimROD", "com.nilo.plaf.nimrod.NimRODLookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("Defecto SO", UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("Defecto", UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("InfoNode", "net.infonode.gui.laf.InfoNodeLookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("Office2003", "org.fife.plaf.Office2003.Office2003LookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("OfficeXP", "org.fife.plaf.OfficeXP.OfficeXPLookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("VisualStudio2005", "org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("PagoSoft", "com.pagosoft.plaf.PgsLookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("Tiny", "de.muntjak.tinylookandfeel.TinyLookAndFeel");
        }catch(Exception e){}
        try{
            UIManager.installLookAndFeel("Tonic", "com.digitprop.tonic.TonicLookAndFeel");
        }catch(Exception e){}
        
        //vemos el tipo substabce
        boolean lbSubstanceAntig=false;
        boolean lbSubstanceModer=false;
        
        try{
            Class.forName("org.pushingpixels.substance.api.skin.AutumnSkin");
            lbSubstanceModer=true;
        }catch(ClassNotFoundException e){
        }catch(Throwable e){
        }
        try{
            Class.forName("org.jvnet.substance.skin.AutumnSkin");
            lbSubstanceAntig=true;
        }catch(ClassNotFoundException e){
        }catch(Throwable e){
        }
        if(lbSubstanceAntig){
            try{
            UIManager.installLookAndFeel("SubstanceAutumnLookAndFeel","org.jvnet.substance.skin.SubstanceAutumnLookAndFeel");
            UIManager.installLookAndFeel("SubstanceBusinessBlackSteelLookAndFeel","org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
            UIManager.installLookAndFeel("SubstanceBusinessBlueSteelLookAndFeel","org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel");
            UIManager.installLookAndFeel("SubstanceBusinessLookAndFeel","org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
            UIManager.installLookAndFeel("SubstanceChallengerDeepLookAndFeel","org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel");
            UIManager.installLookAndFeel("SubstanceCremeCoffeeLookAndFeel","org.jvnet.substance.skin.SubstanceCremeCoffeeLookAndFeel");
            UIManager.installLookAndFeel("SubstanceCremeLookAndFeel","org.jvnet.substance.skin.SubstanceCremeLookAndFeel");
            UIManager.installLookAndFeel("SubstanceEmeraldDuskLookAndFeel","org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel");
            UIManager.installLookAndFeel("SubstanceFieldOfWheatLookAndFeel","org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel");
            UIManager.installLookAndFeel("SubstanceFindingNemoLookAndFeel","org.jvnet.substance.skin.SubstanceFindingNemoLookAndFeel");
            UIManager.installLookAndFeel("SubstanceGreenMagicLookAndFeel","org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMagmaLookAndFeel","org.jvnet.substance.skin.SubstanceMagmaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMangoLookAndFeel","org.jvnet.substance.skin.SubstanceMangoLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMistAquaLookAndFeel","org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMistSilverLookAndFeel","org.jvnet.substance.skin.SubstanceMistSilverLookAndFeel");
            UIManager.installLookAndFeel("SubstanceModerateLookAndFeel","org.jvnet.substance.skin.SubstanceModerateLookAndFeel");
            UIManager.installLookAndFeel("SubstanceNebulaBrickWallLookAndFeel","org.jvnet.substance.skin.SubstanceNebulaBrickWallLookAndFeel");
            UIManager.installLookAndFeel("SubstanceNebulaLookAndFeel","org.jvnet.substance.skin.SubstanceNebulaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceOfficeBlue2007LookAndFeel","org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel");
            UIManager.installLookAndFeel("SubstanceOfficeSilver2007LookAndFeel","org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel");
            UIManager.installLookAndFeel("SubstanceRavenGraphiteGlassLookAndFeel","org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel");
            UIManager.installLookAndFeel("SubstanceRavenGraphiteLookAndFeel","org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
            UIManager.installLookAndFeel("SubstanceRavenLookAndFeel","org.jvnet.substance.skin.SubstanceRavenLookAndFeel");
            UIManager.installLookAndFeel("SubstanceSaharaLookAndFeel","org.jvnet.substance.skin.SubstanceSaharaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceSkin","org.jvnet.substance.skin.SubstanceSkin");
            }catch(Exception e){
                
            }
        }
        if(lbSubstanceModer){
            try{
            UIManager.installLookAndFeel("SubstanceAutumnLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel");
            UIManager.installLookAndFeel("SubstanceBusinessBlackSteelLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
            UIManager.installLookAndFeel("SubstanceBusinessBlueSteelLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel");
            UIManager.installLookAndFeel("SubstanceBusinessLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel");
            UIManager.installLookAndFeel("SubstanceChallengerDeepLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel");
            UIManager.installLookAndFeel("SubstanceCremeCoffeeLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel");
            UIManager.installLookAndFeel("SubstanceCremeLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel");
            UIManager.installLookAndFeel("SubstanceDustCoffeeLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel");
            UIManager.installLookAndFeel("SubstanceDustLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel");
            UIManager.installLookAndFeel("SubstanceEmeraldDuskLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel");
            UIManager.installLookAndFeel("SubstanceGeminiLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel");
            UIManager.installLookAndFeel("SubstanceGraphiteAquaLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceGraphiteGlassLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel");
            UIManager.installLookAndFeel("SubstanceGraphiteLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMagellanLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMarinerLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMistAquaLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceMistSilverLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
            UIManager.installLookAndFeel("SubstanceModerateLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel");
            UIManager.installLookAndFeel("SubstanceNebulaBrickWallLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel");
            UIManager.installLookAndFeel("SubstanceNebulaLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceOfficeBlack2007LookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel");
            UIManager.installLookAndFeel("SubstanceOfficeBlue2007LookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel");
            UIManager.installLookAndFeel("SubstanceOfficeSilver2007LookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel");
            UIManager.installLookAndFeel("SubstanceRavenLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel");
            UIManager.installLookAndFeel("SubstanceSaharaLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel");
            UIManager.installLookAndFeel("SubstanceTwilightLookAndFeelM","org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel");
            UIManager.installLookAndFeel("TwilightSkinM","org.pushingpixels.substance.api.skin.TwilightSkin");
            
            }catch(Exception e){
                
            }
        }
        try {
            UIManager.installLookAndFeel("SyntheticaStandardLookAndFeel", "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
        } catch (Exception e) {}

    }
    
}
