/*
 * JInfDataBase.java
 *
 * Created on 23 de enero de 2006, 23:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos.estructuraBD;

/**
 *
 * @author Administrador
 */
public class JInfDataBase  implements java.io.Serializable {
  private static final long serialVersionUID = 33333336L;
    
    //Can all the procedures returned by getProcedures be called by the current user?
    public boolean allProceduresAreCallable; 
    //Can all the tables returned by getTable be SELECTed by the current user?
    public boolean allTablesAreSelectable;
    //Does a data definition statement within a transaction force the transaction to commit?
    public boolean dataDefinitionCausesTransactionCommit;
    //Is a data definition statement within a transaction ignored?
    public boolean dataDefinitionIgnoredInTransactions;
    //....
    
    
//    /** Creates a new instance of JInfDataBase */
//    public JInfDataBase() {
//        super();
//        
//    }
    
}
