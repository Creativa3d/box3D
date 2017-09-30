/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario.gmail;

import ListDatos.IResultado;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar.Events.List;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import utiles.CadenaLarga;
import utiles.FechaMalException;
import utiles.JDateEdu;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;


public class JCalendarGMAIL {

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final JsonFactory JSON_FACTORY = new JacksonFactory();
    private com.google.api.services.calendar.Calendar moClient;

    public Credential authorize(String psID, String psSecret, String psRutaTrabajo) throws Exception {
        // load client secrets

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new CadenaLarga(
                "{\n"
                + "  \"installed\": {\n"
                + "    \"client_id\": \"" + psID + "\",\n"
                + "    \"client_secret\": \"" + psSecret + "\"\n"
                + "  }\n"
                + "}"));
        // set up file credential store
        FileCredentialStore credentialStore = new FileCredentialStore(
                new File(psRutaTrabajo, ".credentials/calendar"+psID+".json"), JSON_FACTORY);
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR)).setCredentialStore(credentialStore).build();
        // authorize
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        moClient = new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
                "Google-CalendarSample/1.0").build();

        return credential;
    }

    public CalendarList getCalendars() throws IOException {
        View.header("Show Calendars");
        CalendarList feed = moClient.calendarList().list().execute();
        View.display(feed);
        return feed;
    }

    public CalendarListEntry getCalendar(JTEEGUIXCALENDARIO poCalendario) throws IOException {
        CalendarListEntry loResult = null;
        if(poCalendario.getIDENTIFICADOREXTERNO().isVacio()){
            View.header("Show Calendars");
            CalendarList feed = moClient.calendarList().list().execute();
            View.display(feed);
            Iterator<CalendarListEntry> loIter = feed.getItems().iterator();
            while (loIter.hasNext() && loResult==null) {
                CalendarListEntry loC = loIter.next();
                if (loC.getSummary().equalsIgnoreCase(poCalendario.getCALENDARIO().getString())) {
                    loResult = loC;
                }
            }
        }else{
            loResult = moClient.calendarList().get(poCalendario.getIDENTIFICADOREXTERNO().getString()).execute();
        }
        return loResult;
    }
    public Calendar newCalendar(JTEEGUIXCALENDARIO poCalendario) throws IOException {
        Calendar entry = new Calendar();
        entry.setSummary(poCalendario.getNOMBRE().getString());
        return entry;
    }

    public Calendar addoUpdateCalendar(JTEEGUIXCALENDARIO loCalendar) throws Exception {
        Calendar loResult = null;
        CalendarListEntry loAux = getCalendar(loCalendar);
        if(loAux==null){
            loResult=addCalendar(loCalendar);
        }else{
            loResult=updateCalendar(loCalendar);
        }
        return loResult;
    }
    public Calendar addCalendar(JTEEGUIXCALENDARIO poCalendario) throws Exception {
        View.header("Add Calendar");
        Calendar entry = newCalendar(poCalendario);
        Calendar result = moClient.calendars().insert(entry).execute();
        View.display(result);
        
        //actualizamos la tabla
        poCalendario.getIDENTIFICADOREXTERNO().setValue(result.getId());
        IResultado loResult = poCalendario.update(true);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        
        return result;
    }

    public Calendar updateCalendar(JTEEGUIXCALENDARIO poCalendario) throws IOException {
        View.header("Update Calendar");
        CalendarListEntry loEntry = getCalendar(poCalendario);
        Calendar entry;
        if(loEntry==null){
            entry = newCalendar(poCalendario);
        }else{
            entry = getCalendarEntry(loEntry);
        }
        Calendar result = moClient.calendars().patch(entry.getId(), entry).execute();
        View.display(result);
        return result;
    }
    private Calendar getCalendarEntry(CalendarListEntry loEntry) {
        if(loEntry==null){
            return null;
        }else{
            Calendar entry = new Calendar();
            entry.setSummary(loEntry.getSummary());
            entry.setEtag(loEntry.getEtag());
            entry.setDescription(loEntry.getDescription());
            entry.setId(loEntry.getId());
            entry.setKind(loEntry.getKind());
            entry.setLocation(loEntry.getLocation());
            entry.setResponseHeaders(loEntry.getResponseHeaders());
            entry.setTimeZone(loEntry.getTimeZone());
            return entry;
        }
    }
    public Event addoUpdateEvent(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento) throws IOException, FechaMalException, Exception {
        Event loE =getEvent(poCalendario, poEvento);
        if(loE==null){
            addEvent(poCalendario, poEvento);
        }else{
            updateEvent(poCalendario, poEvento, loE);
        }
        return loE;
    }
    public Event updateEvent(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento, Event loE) throws IOException, FechaMalException, Exception {
        View.header("update Event");
        //consigue calendario
        Calendar calendario = getCalendarEntry(getCalendar(poCalendario));
        //acutializamos datos eventos
        Event event = updateEvent(poEvento, calendario.getTimeZone(), loE);
        //ejecutamos cambios
        Event result = moClient.events().patch(calendario.getId(), event.getId(), event).execute();
        //comprobamos errores
        if(result.getStatus()!=null && result.getStatus().equalsIgnoreCase("cancelled")){
            throw new Exception("Cancelado " + poEvento.getNOMBRE().getString());
        }
        //actualizamos la tabla
        poEvento.getIDENTIFICADOREXTERNO().setValue(result.getId());
        IResultado loResult = poEvento.update(true);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        View.display(result);
        return result;
    }

    public Event updateEvent(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento) throws IOException, FechaMalException, Exception {
        View.header("update Event");
        //consigue calendario
        Calendar calendario = getCalendarEntry(getCalendar(poCalendario));
        //consigue evento
        Event event = getEvent(poCalendario, poEvento);
        if(event == null){
            //si no lo encuentra lo añade
            event = newEvent(poEvento, calendario.getTimeZone());
        }else{
            //acutializamos datos eventos
            event = updateEvent(poEvento, calendario.getTimeZone(), event);
        }
        return updateEvent(poCalendario, poEvento, event);
//        //ejecutamos cambios
//        Event result = client.events().patch(calendario.getId(), event.getId(), event).execute();
//        //actualizamos la tabla
//        poEvento.getIDENTIFICADOREXTERNO().setValue(result.getId());
//        IResultado loResult = poEvento.update(true);
//        if(!loResult.getBien()){
//            throw new Exception(loResult.getMensaje());
//        }
//        View.display(result);
//        return result;
    }
    public Event addEvent(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento) throws IOException, FechaMalException, Exception {
        View.header("Add Event");
        //consigue calendario
        Calendar calendario = getCalendarEntry(getCalendar(poCalendario));
        //crea evento
        Event event = newEvent(poEvento, calendario.getTimeZone());
        event.setId(null);
        //ejecutamos cambios
        Event result = moClient.events().insert(calendario.getId(), event).execute();
        //comprobamos errores
        if(result.getStatus()!=null && result.getStatus().equalsIgnoreCase("cancelled")){
            throw new Exception("Cancelado " + poEvento.getNOMBRE().getString());
        }
        //actualizamos la tabla
        poEvento.getIDENTIFICADOREXTERNO().setValue(result.getId());
        IResultado loResult = poEvento.update(true);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        View.display(result);
        return result;
    }

    private void newEvent(Event loE, JTEEGUIXEVENTOS loTabla, String psCalendario) throws FechaMalException, Exception {
        loTabla.addNew();
        loTabla.valoresDefecto();
        loTabla.getCALENDARIO().setValue(psCalendario);
        loTabla.getNOMBRE().setValue(loE.getSummary());
        loTabla.getTEXTO().setValue(loE.getDescription());
        loTabla.getIDENTIFICADOREXTERNO().setValue(loE.getId());
        
        DateTime loDateGoogle = loE.getStart().getDateTime();
        if (loDateGoogle == null) {
            loDateGoogle = loE.getStart().getDate();
        }
        loTabla.getFECHADESDE().setValue(new JDateEdu( new Date(loDateGoogle.getValue())));
        loDateGoogle = loE.getEnd().getDateTime();
        if (loDateGoogle == null) {
            loDateGoogle = loE.getEnd().getDate();
        }
        loTabla.getFECHAHASTA().setValue(new JDateEdu( new Date(loDateGoogle.getValue())));


//        loTabla.getFECHAMODIFICACION().setValue(loE.getUpdated());

        loTabla.update(false);

    }

    private Event updateEvent(JTEEGUIXEVENTOS poEvento, String pstimeZone, Event event) throws FechaMalException {
        event.setSummary(poEvento.getNOMBRE().getString());

        Date startDate = poEvento.getFECHADESDE().getDateEdu().getDate();
        Date endDate = poEvento.getFECHAHASTA().getDateEdu().getDate();

        DateTime start = new DateTime(startDate, TimeZone.getTimeZone(pstimeZone));
        EventDateTime loEventTiem = new EventDateTime().setDateTime(start);
        loEventTiem.setTimeZone(pstimeZone);
        event.setStart(loEventTiem);
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone(pstimeZone));
        loEventTiem = new EventDateTime().setDateTime(end);
        loEventTiem.setTimeZone(pstimeZone);
        event.setEnd(loEventTiem);

        event.setDescription(poEvento.getTEXTO().getString());

        return event;
    }
    private Event newEvent(JTEEGUIXEVENTOS poEvento, String pstimeZone) throws FechaMalException {
        Event event = new Event();
        if(!poEvento.getIDENTIFICADOREXTERNO().isVacio()){
            event.setId(poEvento.getIDENTIFICADOREXTERNO().getString());
        }
        return updateEvent(poEvento, pstimeZone, event);
    }

    private Events getEventsI(JTEEGUIXCALENDARIO poCalendario, JDateEdu poUltModifAPartir) throws IOException {
        View.header("Show Events");
        CalendarListEntry loC = getCalendar(poCalendario);
        
        List loList = moClient.events().list(loC.getId())
                .setOrderBy("startTime")
                .setSingleEvents(true);
        
        if(poUltModifAPartir==null){
            loList.setMaxResults(20);
            JDateEdu loDate = new JDateEdu();
            loDate.add(JDateEdu.mclHoras, -1);
            DateTime start = new DateTime(loDate.getDate(), TimeZone.getTimeZone(loC.getTimeZone()));
            loList.setTimeMin(start);
        } else {
            DateTime start = new DateTime(poUltModifAPartir.getDate(), TimeZone.getTimeZone(loC.getTimeZone()));
            loList.setUpdatedMin(start);
        }     
        
        Events feed = loList.execute();
        View.display(feed);
        return feed;
    }

    public JTEEGUIXEVENTOS getEvents(JTEEGUIXCALENDARIO poCalendario, JDateEdu poUltModifAPartir) throws IOException, Exception {
        JTEEGUIXEVENTOS loTabla = new JTEEGUIXEVENTOS(poCalendario.moList.moServidor);
        Events loEvents = getEventsI(poCalendario, poUltModifAPartir);
        Iterator<Event> loIte = loEvents.getItems().iterator();
        while (loIte.hasNext()) {
            Event loE = loIte.next();
            newEvent(loE, loTabla, poCalendario.getCALENDARIO().getString());
        }
        return loTabla;
    }
    public Event getEvent(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento) throws IOException, Exception {
        if(poEvento.getIDENTIFICADOREXTERNO().isVacio()){
            return null;
        }else{
            CalendarListEntry loC = getCalendar(poCalendario);
            Event feed = moClient.events().get(loC.getId(), poEvento.getIDENTIFICADOREXTERNO().getString()).execute();
            if(feed.getStatus()!=null && feed.getStatus().equalsIgnoreCase("cancelled")){
                feed=null;
            }
            return feed;
        }
    }
    public void deleteEvent(JTEEGUIXCALENDARIO poCalendario, JTEEGUIXEVENTOS poEvento) throws IOException {
        CalendarListEntry loC = getCalendar(poCalendario);
        if(!poEvento.getIDENTIFICADOREXTERNO().isVacio()){
            moClient.events().delete(loC.getId(), poEvento.getIDENTIFICADOREXTERNO().getString()).execute();
        }
    }

    public void deleteCalendar(JTEEGUIXCALENDARIO poCalendario) throws IOException {
        View.header("Delete Calendar");
        CalendarListEntry loC = getCalendar(poCalendario);
        moClient.calendars().delete(loC.getId()).execute();
    }

    public static void main(String[] args) {
        try {
            try {
                JTEEGUIXCALENDARIO loCalendar = new JTEEGUIXCALENDARIO(null);
                loCalendar.addNew();
                loCalendar.getCALENDARIO().setValue("1");
                loCalendar.getNOMBRE().setValue("Nombre");
                loCalendar.update(false);
                JTEEGUIXEVENTOS loEventos = new JTEEGUIXEVENTOS(null);
                loEventos.addNew();
                loEventos.getCODIGO().setValue("1");
                loEventos.getCALENDARIO().setValue(loCalendar.getCALENDARIO().getString());
                loEventos.getNOMBRE().setValue("prueba");
                loEventos.getTEXTO().setValue("t4exto ");
                loEventos.getFECHADESDE().setValue(new JDateEdu());
                loEventos.getFECHAHASTA().setValue(new JDateEdu());
                loEventos.update(false);

                JCalendarGMAIL loCalen = new JCalendarGMAIL();
                loCalen.authorize("709867355219.apps.googleusercontent.com", "yTL5XIYOPYeFCYbao8noLHvB", ".");
                loCalen.addoUpdateCalendar(loCalendar);
                loCalen.addEvent(loCalendar, loEventos);
        
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);
    }


}
