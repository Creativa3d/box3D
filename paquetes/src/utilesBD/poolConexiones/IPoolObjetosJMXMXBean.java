package utilesBD.poolConexiones;

import java.util.List;

public interface IPoolObjetosJMXMXBean {

    public void cerrarTodasConex();
    public String getConexion();
    public String getSQLActivas();
}
