/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import utiles.FechaMalException;
import utiles.JCadenas;
import utiles.JDateEdu;


/**
 *
 * @author cristian
 */
public class JGUIxAvisosSMSMensamundi extends JGUIxAvisosSMS {

    public JGUIxAvisosSMSMensamundi() {
        super.inicializar();
        msSMSUsuario = "";
        msSMSClave = "";
        mlSMSPuerto = -1;
    }
    
    @Override
    public void inicializarSMS() {}

    @Override
    public void enviarSMS(String psTelef, String psSender, String psTexto, String psDate) throws Exception {
        HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        PostMethod request = new PostMethod("https://api.gateway360.com/api/3.0/sms/send");

        if(psTelef.length() <= 9)
            psTelef = "34"+psTelef;
        
        String strReq = createRequest(getSMSLicencia(), psTelef, psSender, psTexto, psDate);
        RequestEntity entity = new StringRequestEntity(strReq, "application/json", "UTF-8");

        request.addRequestHeader("content-type", "application/json");
        request.addRequestHeader("Accept", "application/json");
        request.setRequestEntity(entity);
        
        int statusCode = httpClient.executeMethod(request);
        String resStr = request.getResponseBodyAsString();
        
        showStatusCode(statusCode, resStr);
    }
    
    private String createRequest(String psApiKey, String psTelef, String psSender, String psTexto, String psDate) throws FechaMalException {
            String retVal = "{\"api_key\":\""+psApiKey+"\",";
            //retVal += "\"fake\":1,"; // Usar para realizar pruebas (no gasta creditos ni se envia el mensaje)
            if (!JCadenas.isVacio(psSender) && !JCadenas.isVacio(psTelef) && !JCadenas.isVacio(psTexto)) {
                retVal += "\"messages\":[{";
                retVal += "\"from\":\""+psSender.replace("\"", "'")+"\",";
                retVal += "\"to\":\""+psTelef.replace("\"", "'")+"\",";
                retVal += "\"text\":\""+psTexto.replace("\"", "'")+"\"";
                if (JDateEdu.isDate(psDate)) 
                    retVal += ",\"send_at\":\""+new JDateEdu(psDate).msFormatear("yyyy-MM-dd HH:mm:ss")+"\"";
                retVal += "}]}";
            }   
            return retVal;
    }
    
    private void showStatusCode(int statusCode, String resStr) throws Exception {
        String errorID = "";
        JSONObject jsonResponse = new JSONObject(resStr);
        if (statusCode == 200) {
            JSONArray jsonArr = jsonResponse.getJSONArray("result");
            if (JCadenas.isEquals(jsonArr.getJSONObject(0).getString("status"), "error")) {
                for (int i = 0; i < jsonArr.length(); i++)
                    errorID = jsonArr.getJSONObject(i).getString("error_id");
                throwErrors(errorID);
            }
        }
        else if (statusCode - 400 < 100)
            throwErrors(jsonResponse.getString("error_id"));
        else if (statusCode - 500 < 100)
            throw new Exception("ERROR: Mensamundi server error");
    }
    
    private void throwErrors(String errorID) throws Exception {
        if( "INVALID_CONTENT_TYPE".equalsIgnoreCase(errorID))
            throw new Exception("El tipo de contenido debe ser: Content-Type: application/json");
        if( "JSON_PARSE_ERROR".equalsIgnoreCase(errorID))
            throw new Exception("El formato de la peticion JSON es incorrecto o incompleto");
        if( "MISSING_PARAMS".equalsIgnoreCase(errorID))
            throw new Exception("Tu peticion esta incompleta y faltan parametros obligatorios");
        if( "BAD_PARAMS".equalsIgnoreCase(errorID))
            throw new Exception("Uno o mas de tus parametros tiene formato o valor incorrecto");
        if( "UNAUTHORIZED".equalsIgnoreCase(errorID))
            throw new Exception("Tu API key es invalida o tu direccion IP esta bloqueada");
        if( "INVALID_SENDER".equalsIgnoreCase(errorID))
            throw new Exception("El ID de envio del emisor no esta permitida para este mensaje o es incorrecto, maximo 11 caracteres");
        if( "INVALID_DESTINATION".equalsIgnoreCase(errorID))
            throw new Exception("Numero del receptor incorrecto, debe estar en formato MSISDN");
        if( "INVALID_TEXT".equalsIgnoreCase(errorID))
            throw new Exception("El campo de texto esta vacio o es corrupto");
        if( "INVALID_DATETIME".equalsIgnoreCase(errorID))
            throw new Exception("El formato de fecha esperado es.equalsIgnoreCase(errorID)) YYYY-MM-DD HH:MM:SS");
        if( "NOT_ENOUGH_BALANCE".equalsIgnoreCase(errorID))
            throw new Exception("Tu cuenta no tiene credito suficiente para procesar esta peticion");
        if( "LIMIT_EXCEEDED".equalsIgnoreCase(errorID))
            throw new Exception("Tu peticion ha excedido el maximo de 1000 mensajes por peticion");
        
       throw new Exception("Ocurrio un error al procesar tu peticion");
    }
    
}
