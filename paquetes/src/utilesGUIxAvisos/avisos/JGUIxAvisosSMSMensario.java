/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import es.xilon.semApi.SemApi;
import es.xilon.semApi.beans.ApiMessageBean;
import es.xilon.semApi.beans.ApiRecipientBean;
import es.xilon.semApi.beans.ApiResponseBean;
import java.util.ArrayList;

/**
 *
 * @author eduardo
 */
public class JGUIxAvisosSMSMensario extends JGUIxAvisosSMS {
    private SemApi api;
    public JGUIxAvisosSMSMensario() {
        super.inicializar();
        mlSMSPuerto = 443;
    }
    
    @Override
    public void inicializarSMS() {
        api = new SemApi(
                getSMSLicencia(),
                getSMSUsuario(),
                getSMSClave(),
                "servicios.mensario.com", getSMSPuerto());
    }

    @Override
    public void enviarSMS(String psTelef, String psSender, String psTexto, String pdDate) throws Exception{
        if (api == null) {
            inicializarSMS();
        }
        ArrayList<ApiMessageBean> sendings = new ArrayList();
        // Set recipients
        ArrayList<ApiRecipientBean> recipients = new ArrayList();
        ApiRecipientBean recipient = new ApiRecipientBean();
        recipient.setCode("34");
        recipient.setPhone(psTelef);
        recipients.add(recipient);

        // Set message data
        ApiMessageBean message = new ApiMessageBean();
        message.setRecipients(recipients);
        message.setSender(psSender);
        message.setText(psTexto);
        message.setDate("00000000000000");


        sendings.add(message);
        ApiResponseBean apiResponse = ((SemApi) api).executeSending(sendings);
        if (!apiResponse.getResult().equals("OK")) {
            throw new Exception(apiResponse.getResult());
        }
    }
    
}
