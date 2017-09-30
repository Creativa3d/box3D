package ListDatos;

import java.io.Serializable;

public class JSelectMeta implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    //meta para pagina actual
    static public final String pagina_actual = "pagina_actual";
    //meta para indicar registro por pagina
    static public final String pagina_size = "pagina_size";
    //meta para quedan mas paginas
    static public final String pagina_more_pages = "pagina_more_pages";
    //meta para version del motor de datos (para token de ultima linea para ServidorInternet)
    static public final String version_motor = "version_motor";
    
    protected String name;
    protected String value;
    
    public JSelectMeta(String name, String value) {
        this.name = name;
        this.value = value;
    }

    //getter's
    public String getName() {return name;}
    public String getValue() {return value;}

    //setter's
    public void setName(String name) {this.name = name;}
    public void setValue(String value) {this.value = value;}
    
    //metodos de utiliza para convertir el value
    public Integer getValueAsInteger() {
        try{
            return  Integer.valueOf(getValue() );
        }
        catch(Exception e) {
            return null;
        }
    }
    public void setValueAsInteger(Integer valor) {
        setValue( valor == null ? null : valor.toString() );
    }
    
    public int getValueAsIntDef(int default_value) {
        Integer val = getValueAsInteger();
        return val==null ? default_value : val.intValue();
    }
    
    public boolean getValueAsBoolean() {
        return  "S".equalsIgnoreCase(getValue()) ||
                "SI".equalsIgnoreCase(getValue()) ||
                "1".equalsIgnoreCase(getValue()) ||
                "Y".equalsIgnoreCase(getValue()) ||
                "YES".equalsIgnoreCase(getValue()) ||
                "T".equalsIgnoreCase(getValue()) ||
                "TRUE".equalsIgnoreCase(getValue()) ||
                "V".equalsIgnoreCase(getValue()) ||
                "VERDAD".equalsIgnoreCase(getValue());
    }
    public void setValueAsBoolean(boolean valor) {
        setValue(  valor ? "S" : "N" );
    }
    public Object clone() throws CloneNotSupportedException {
       return super.clone();
    }
    
}


