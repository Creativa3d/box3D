package utiles.timer;

public interface ITemporizador {
    public void timerArrancado( JTimer JTimer );   
    public void timerParado( JTimer JTimer );   
    public void timerMuerto( JTimer JTimer );
    public void timerIntervalo( JTimer JTimer );
}

