/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;

import utiles.IListaElementos;
import utiles.JListaElementos;

public class JSelectMotorFactory {
    private static final JSelectMotorFactory moFactory;
    
    static {
        moFactory = new JSelectMotorFactory();
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclAccess), 
                new JSelectMotor(JSelectMotor.mclAccess), "Microsoft Access");
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclDerby), 
                new JSelectMotor(JSelectMotor.mclDerby), "Derby");
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclFireBird), 
                new JSelectMotorFirebird(), "Firebird");
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclMySQL), 
                new JSelectMotor(JSelectMotor.mclMySQL), "MySQL");
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclOracle), 
                new JSelectMotor(JSelectMotor.mclOracle), "Oracle");
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclPostGreSQL), 
                new JSelectMotor(JSelectMotor.mclPostGreSQL), "PostGreSQL");
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclSqlServer), 
                new JSelectMotor(JSelectMotor.mclSqlServer), "SQL Server");
        moFactory.registrarMotor(String.valueOf(JSelectMotor.mclSQLLite), 
                new JSelectMotor(JSelectMotor.mclSQLLite), "SQLite");
    }
    /**Patron singleObject*/
    public static JSelectMotorFactory getInstance(){
        return moFactory;
    }

    public static class JSelectMotorFactElem {
        private final String msTipo;
        private final ISelectMotor moSelect;
        private final String msCaption;

        public JSelectMotorFactElem(String psTipo, ISelectMotor poSelect, String psCaption){
            msTipo = psTipo;
            moSelect = poSelect;
            msCaption = psCaption;
        }
        public String getCaption(){
            return msCaption;
        }

        /**
         * @return the msTipo
         */
        public String getTipo() {
            return msTipo;
        }

        /**
         * @return the moSelect
         */
        public ISelectMotor getSelectMotor() {
            return moSelect;
        }
    }
    
    private IListaElementos moLista = new JListaElementos();
    private ISelectMotor moSelectMotorDefecto;
    
    
    
    private JSelectMotorFactory(){}
    
    /**DESRegistramos un motor de sql*/
    public synchronized void DESregistrarMotor(String psTipo){
        JSelectMotorFactElem loMotorElem = getSelectMotorElem(psTipo);
        if(loMotorElem!=null){
            moLista.remove(loMotorElem);
        }
    }
    /**Registramos un motor de sql*/
    public synchronized void registrarMotor(String psTipo, ISelectMotor poSelectMotor, String psCaption){
        //si existe previamente lo borramos
        DESregistrarMotor(psTipo);
        //add el mnuevo jselecmotor
        moLista.add(new JSelectMotorFactElem(psTipo, poSelectMotor, psCaption));
        //si no hay ningun ISelectMotor por defecto lo establecemos
        if(moSelectMotorDefecto==null){
            moSelectMotorDefecto=poSelectMotor;
        }
    }
    /**Devolvemos un motor de SQLc en funcion de la clave*/
    public ISelectMotor getSelectMotor(String psTipo){
        JSelectMotorFactElem loResult = getSelectMotorElem(psTipo);
        if(loResult!=null){
            return loResult.getSelectMotor();
        }else{
            return null;
        }
    }
    private JSelectMotorFactElem getSelectMotorElem(String psTipo){
        JSelectMotorFactElem loResult = null;
        for(int i =0; i < moLista.size() && loResult == null; i++){
            JSelectMotorFactElem loElem = (JSelectMotorFactElem) moLista.get(i);
            if(loElem.getTipo().equalsIgnoreCase(psTipo)){
                loResult = loElem;
            }
        }
        return loResult;
    }
    /**Select motor por defecto*/
    public ISelectMotor getSelectMotorDefecto() {
        return moSelectMotorDefecto;
    }
    /**Devuelve la lista de JSelectMotorFactElem*/
    public IListaElementos getListaMotoresSQL(){
        return moLista;
    }

    
}

