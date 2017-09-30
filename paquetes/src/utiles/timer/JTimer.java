package utiles.timer;

import java.util.*;

public class JTimer implements Runnable {
    public Date inicio,parada;
    private Thread thread = null;
    private int duracion = 0;
    private int duracionAnt = 0;
    private int intervalo = 1000;
    private boolean repeticion = false;
    private boolean enEjecucion = false;
    private ITemporizador handler = null;
    
    // Contructor basico, usado por defecto
    public JTimer() { }
    

    /**
    // Los siguientes constructores son de conveniencia, porque todas
    // las caracteristicas del timer se pueden ver y modificar a
    // traves de los metodos get y set
    // Constructor de conveniencia que acepta un tiempo de duracion milisegundos
     **/
    public JTimer(int tiempo) { 
        setDuracion( tiempo ); 
        }
    

    /**
     *  Constructor de conveniencia que acepta un JTemporizador
     */
    public JTimer(ITemporizador Handler) { 
        setHandler( Handler ); 
        }
    

    /**
     * Constructor de conveniencia que acepta un JTemporizador y una
     *  duracion en milisegundos
     */
    public JTimer(int tiempo, ITemporizador Handler) {
        setDuracion( tiempo );
        setHandler( Handler );
        }
     

    /**
     * Fija el numero se segundos que correra el timer
     */
    public void setDuracion( int tiempo ) { 
        duracion = tiempo; 
        }
    

    // Fija el objeto que ha de ser notificado de los eventos que
    // sucedan al timer
    public void setHandler( ITemporizador Handler ) { 
        handler = Handler; 
        }
    

    /**
     *  Fija el numero de milisegundos entre pulsos del timer
     */
    public void setIntervalo( int Intervalo ) { 
        intervalo = Intervalo; 
        }
    

    // Funciones "get" para recoger los datos de las caracteristicas
    // que se han fijado antes
    public int getDuration() { 
        return( duracion ); 
        }
 
    public ITemporizador getHandler() { 
        return( handler ); 
        }
 
    public int getIntervalo() { 
        return( intervalo ); 
        }   
    

    // Devuelve el numero de segundos que han transcurrido desde que
    // se arranco el timer
    public int getElapsed() {
         return( calculaLapso( new Date() ) ); 
        }   
 

    // Este metodo permite resetear el timer antes de relanzarlo. Se
    // podria usar el metodo setDuracion, pero este es mas corto
    // y elegante
    public void resetDuracion() { 
        duracion = duracionAnt; 
        }   
       

    // Aqui creamos un nuevo thread para correr el Timer. Lo incializamos
    // con "this" de forma que el metodo run() se llame inmediatamente
    // como comience la ejecucion del thread
    public void start() {
        thread = new Thread( this );
        thread.start();
        }
 

    // Aqui almacenamos el momento en que se llama a este metodo.
    // Tambien comprobamos si hay algun JTemporizador asociado al Timer
    // que estamos parando, en cuyo caso, notificamos a los observadores
    // de este Timer que lo hemos detenido (para eso esta la interface
    // JTemporizador, que debera estar implementada en las clases que
    // miren a este Timer)
    public void stop() {
        enEjecucion = false;
//        notifyAll();
        parada = new Date();
        if ( handler != null ){
            handler.timerParado( this );
        }
    }
    

    public void run() {
        enEjecucion = true;
        duracionAnt = duracion;
 
        // Arrancamos el Timer y lo notificamos a las clases que esten
        // poendientes
        inicio = new Date();
        if( handler != null ){
            handler.timerArrancado( this );
        }
 
        while( enEjecucion )
            {
            // Esperamos el tiempo que nos hayan dicho en la configuracion
            // del intervalo
            try {
                esperar( intervalo );
            } catch( InterruptedException e ) {
                return;
                }
             
            // Cuando se cumple el intervalo, avisamos a las clases que
            // esten pendientes. Si esas clases no quieren hacer nada
            // con este evento periodico, es suficiente con que no lo
            // sobrecarguen, que se quede vacio
            if( handler != null ){
                handler.timerIntervalo( this );
            }
            // Si no indicamos una duracion para el Timer, estara
            // corriendo indefinidamente
            if( duracion > 0 )
                {
                // Comprobamos si el Timer esta muerto ya, para no
                // tener que matarlo
                if( estaMuerto() )
                    {
                    // Si esta muerto, lo propagamos
                    if( handler != null ){
                        handler.timerMuerto( this );               
                    }
                    // Aqui comprobamos si se quiere una repeticion
                    // automatica, en cuyo caso, volvemos a arrancar
                    // el Timer
                    if( repeticion ) {
                        enEjecucion = true;
                        inicio = new Date();
                        if( handler != null ){
                            handler.timerArrancado( this );   
                        }
                    }
                    else
                        {
                        enEjecucion = false;
                        }
                    }
                }
            }
        }    
    
    // Metodos que nos informan del estado del Timer
    public boolean estaCorriendo() { 
        return( enEjecucion ); 
        }
    
    public boolean estaParado() { 
        return( !enEjecucion ); 
        }
    

    public boolean estaMuerto() {
        int segundos = 0;
 
        // Calculamos el intervalo de tiempo que ha transcurrido desde
        // que se ha arrancado el Timer
        segundos = calculaLapso( new Date() );
 
        return( segundos >= duracion );
    }
          

    private int calculaLapso( Date actual ) {
        Date dfinal;
        int  segundos = 0;
 
        if( enEjecucion )
            dfinal = actual;
        else
            dfinal = parada;
 
        // Si se quiere mas precision, en vez de Date(), se puede
        // utilizar System.currentTimeMillis(), que proporciona
        // muchisima mas resolucion
        Calendar loFinal = Calendar.getInstance();
        Calendar loInicio = Calendar.getInstance();
        loFinal.setTime(dfinal);
        loInicio.setTime(inicio);

        segundos += ( loFinal.get(Calendar.HOUR_OF_DAY) - loInicio.get(Calendar.HOUR_OF_DAY) ) * 3600;
        segundos += ( loFinal.get(Calendar.MINUTE) - loInicio.get(Calendar.MINUTE) ) * 60;
        segundos += ( loFinal.get(Calendar.SECOND) - loInicio.get(Calendar.SECOND) );
        return( segundos );
        }
    

    // Aqui implementamos la espera. El lapso en milisegundos se lo
    // pasamos al metodo wait() del thread
    // Este metodo es sincronizado porque sino salta una excepcion 
    // interna en el interprete de Java. No esta muy bien documentado
    // el porque, pero parece ser que no se puede llamar al metodo
    // wait() de un thread desde otro que no sea sincronizado
    private synchronized void esperar( int lapso ) 
        throws InterruptedException {
        this.wait( lapso );
    }   
}
   
   
