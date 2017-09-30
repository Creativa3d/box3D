package utilesGUIxAvisos.avisos.forms;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*; 

import es.xilon.semApi.*;
import es.xilon.semApi.beans.*;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import utilesGUIx.msgbox.JMsgBox;

public class ApiExample extends JFrame implements ActionListener {
	
	private JTextField userTextField, passTextField, licenseTextField, serverTextField, 
	   portTextField, proxyUserTextField, proxyPassTextField, senderTextField,
	   phoneCodeTextField, phoneTextField, servidormensario;
	private JTextArea textTextArea;
    private JTextField puertoTextField;
	
//	public static void main(String [] args) {
//		
//		new ApiExample();
//	}
//	
	public ApiExample() {
		
		// Set frame position
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension t = tk.getScreenSize();
		setBounds((int) ((t.getWidth() / 2) - (650 / 2)), (int) ((t.getHeight() / 2) - (490 / 2)), 650, 490);
		
		// Load frame components
		setLayout(null);
		initComponents();
		
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            try {
                todoBien();
            } catch (Exception ex) {
                JMsgBox.mensajeError(rootPane, ex);
            }
	}
	
	private void initComponents() {
		
		setTitle("Mensario");
		
		// Identification panel
		JPanel identificationPanel = new JPanel();
		identificationPanel.setBorder(new TitledBorder(BorderFactory.createTitledBorder("Identificaci√≥n")));
		identificationPanel.setLayout(null);
		identificationPanel.setBounds(10, 10, 300, 170);
		add(identificationPanel);
		
		JLabel userLabel = new JLabel("Usuario:");
		userLabel.setBounds(20, 30, 80, 20);
		identificationPanel.add(userLabel);
		
		userTextField = new JTextField("");
		userTextField.setBounds(110, 30, 175, 20);
		identificationPanel.add(userTextField);
		
		JLabel passLabel = new JLabel("ContraseÒa:");
		passLabel.setBounds(20, 60, 80, 20);
		identificationPanel.add(passLabel);
		
		passTextField = new JTextField("");
		passTextField.setBounds(110, 60, 175, 20);
		identificationPanel.add(passTextField);
		
		JLabel licenseLabel = new JLabel("Licencia:");
		licenseLabel.setBounds(20, 90, 80, 20);
		identificationPanel.add(licenseLabel);
		
		licenseTextField = new JTextField("");
		licenseTextField.setBounds(110, 90, 175, 20);
		identificationPanel.add(licenseTextField);
		
		JLabel nadaLabel = new JLabel("Puerto mensario:");
		nadaLabel.setBounds(20, 110, 80, 20);
		identificationPanel.add(nadaLabel);
		
		puertoTextField = new JTextField("443");
		puertoTextField.setBounds(110, 110, 175, 20);
		identificationPanel.add(puertoTextField);
                
		nadaLabel = new JLabel("servidor mensario:");
		nadaLabel.setBounds(20, 130, 80, 20);
		identificationPanel.add(nadaLabel);

                servidormensario = new JTextField("servicios.mensario.com");
		servidormensario.setBounds(110, 130, 175, 20);
		identificationPanel.add(servidormensario);
                
		
		// Proxy panel
		JPanel proxyPanel = new JPanel();
		proxyPanel.setBorder(new TitledBorder(BorderFactory.createTitledBorder("Datos del proxy")));
		proxyPanel.setLayout(null);
		proxyPanel.setBounds(330, 10, 300, 170);
		add(proxyPanel);
		
		JLabel serverLabel = new JLabel("Servidor:");
		serverLabel.setBounds(20, 30, 80, 20);
		proxyPanel.add(serverLabel);
		
		serverTextField = new JTextField();
		serverTextField.setBounds(110, 30, 175, 20);
		proxyPanel.add(serverTextField);
		
		JLabel portLabel = new JLabel("Puerto:");
		portLabel.setBounds(20, 60, 80, 20);
		proxyPanel.add(portLabel);
		
		portTextField = new JTextField();
		portTextField.setBounds(110, 60, 175, 20);
		proxyPanel.add(portTextField);
		
		JLabel proxyUserLabel = new JLabel("Usuario:");
		proxyUserLabel.setBounds(20, 90, 80, 20);
		proxyPanel.add(proxyUserLabel);
		
		proxyUserTextField = new JTextField();
		proxyUserTextField.setBounds(110, 90, 175, 20);
		proxyPanel.add(proxyUserTextField);
		
		JLabel proxyPassLabel = new JLabel("ContraseÒa:");
		proxyPassLabel.setBounds(20, 120, 80, 20);
		proxyPanel.add(proxyPassLabel);
		
		proxyPassTextField = new JTextField();
		proxyPassTextField.setBounds(110, 120, 175, 20);
		proxyPanel.add(proxyPassTextField);
		
		// Message panel
		JPanel messagePanel = new JPanel();
		messagePanel.setBorder(new TitledBorder(BorderFactory.createTitledBorder("Datos del mensaje")));
		messagePanel.setLayout(null);
		messagePanel.setBounds(10, 190, 620, 180);
		add(messagePanel);
		
		JLabel senderLabel = new JLabel("Remitente:");
		senderLabel.setBounds(20, 30, 80, 20);
		messagePanel.add(senderLabel);
		
		senderTextField = new JTextField();
		senderTextField.setBounds(110, 30, 100, 20);
		messagePanel.add(senderTextField);
		
		JLabel phoneCodeLabel = new JLabel("Cod. PaÌs:");
		phoneCodeLabel.setBounds(270, 30, 80, 20);
		messagePanel.add(phoneCodeLabel);
		
		phoneCodeTextField = new JTextField();
		phoneCodeTextField.setBounds(360, 30, 30, 20);
		messagePanel.add(phoneCodeTextField);
		
		JLabel phoneLabel = new JLabel("TelÈfono:");
		phoneLabel.setBounds(410, 30, 80, 20);
		messagePanel.add(phoneLabel);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(500, 30, 100, 20);
		messagePanel.add(phoneTextField);
		
		JLabel textLabel = new JLabel("Texto:");
		textLabel.setBounds(20, 60, 80, 20);
		messagePanel.add(textLabel);
		
		textTextArea = new JTextArea();
		textTextArea.setBounds(110, 60, 490, 100);
		messagePanel.add(textTextArea);
		
		// Synchronizacion button
		JButton synchronizationButton = new JButton("SincronizaciÛn");
		synchronizationButton.setBounds(10, 390, 150, 50);
		synchronizationButton.setName("synchronization");
		synchronizationButton.addActionListener(this);
		add(synchronizationButton);
		
		// Credit request button
		JButton creaditRequestButton = new JButton("Consulta de saldo");
		creaditRequestButton.setBounds(180, 390, 150, 50);
		creaditRequestButton.setName("creditRequest");
		creaditRequestButton.addActionListener(this);
		add(creaditRequestButton);
		
		// Send button
		JButton sendButton = new JButton("EnvÌo");
		sendButton.setBounds(350, 390, 150, 50);
		sendButton.setName("sending");
		sendButton.addActionListener(this);
		add(sendButton);
		
		// Exit button
		JButton exitButton = new JButton("Salir");
		exitButton.setBounds(555, 415, 75, 25);
		exitButton.setName("exit");
		exitButton.addActionListener(this);
		add(exitButton);
		
	}

    private void todoBien() throws NoSuchAlgorithmException, KeyManagementException {
//        // Create a trust manager that does not validate certificate chains
//        TrustManager[] trustAllCerts = new TrustManager[]{
//            new X509TrustManager() {
//                public X509Certificate[] getAcceptedIssuers() {
//                    System.out.println("getAcceptedIssuers");
//                    return null;
//                }
//
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                    System.out.println("checkClientTrusted");
//                }
//
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                    System.out.println("checkServerTrusted");
//                }
//            }
//        };
//        // Install the all-trusting trust manager
//        SSLContext sc = SSLContext.getInstance("TLSv1");
//        // Create empty HostnameVerifier
//        HostnameVerifier hv = new HostnameVerifier() {
//            public boolean verify(String arg0, SSLSession arg1) {
//                System.out.println("HostnameVerifier.verify");
//                return true;
//            }
//        };
//        
//        sc.init(null, trustAllCerts, null);
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        HttpsURLConnection.setDefaultHostnameVerifier(hv);
//        HttpURLConnection.setFollowRedirects(true);
//        

        System.setProperty("https.protocols", "TLSv1");
        
        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        SSLContext.setDefault(sslContext);
        
        
    }
    
	public void actionPerformed(ActionEvent e) {
		
		try {
                    todoBien();
			// Create api object
			SemApi api;
			if ((serverTextField.getText().length() > 0) && (portTextField.getText().length() > 0) && (proxyUserTextField.getText().length() > 0) && (proxyPassTextField.getText().length() > 0)) {
				
				// With proxy
				api = new SemApi(licenseTextField.getText(), userTextField.getText(), passTextField.getText(), servidormensario.getText(), Integer.valueOf(puertoTextField.getText()).intValue(), serverTextField.getText(), Integer.valueOf(portTextField.getText()), proxyUserTextField.getText(), proxyPassTextField.getText());
			} else {
			
				// Without proxy
				api = new SemApi(licenseTextField.getText(), userTextField.getText(), passTextField.getText(), servidormensario.getText(), Integer.valueOf(puertoTextField.getText()).intValue());
			}
			
			ApiResponseBean apiResponse = null;
			JButton button = (JButton) e.getSource();
			
			if (button.getName().equals("synchronization")) {
				
				try {
				
					// Synchronization method call
					apiResponse = api.executeSynchronization();
					// Show response
					JOptionPane.showMessageDialog(this, "Fecha y hora del servidor: " + apiResponse.getTimestamp(), "Sincronizaci√≥n", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					showErrorMessage(ex.getMessage());
				}
			} else if (button.getName().equals("creditRequest")) {
				
				try {
				
					// Credit request method call
					apiResponse = api.executeBalanceEnquiry();
					//Show response
					JOptionPane.showMessageDialog(this, "Mensajes: " + apiResponse.getQuantity(), "Consulta de saldo", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					
					showErrorMessage(ex.getMessage());
				}
			} else if (button.getName().equals("sending")) {
				
				// Set recipients
				ArrayList<ApiRecipientBean> recipients = new ArrayList<ApiRecipientBean>();
				ApiRecipientBean recipient = new ApiRecipientBean();
				recipient.setCode(phoneCodeTextField.getText());
				recipient.setPhone(phoneTextField.getText());
				recipients.add(recipient);
				
				// Set message data
				ArrayList<ApiMessageBean> sendings = new ArrayList<ApiMessageBean>();
				ApiMessageBean message = new ApiMessageBean();
				message.setRecipients(recipients);
				message.setSender(senderTextField.getText());
				message.setText(textTextArea.getText());
				message.setDate("00000000000000");				
				sendings.add(message);
				
				apiResponse = api.executeSending(sendings);
				
				if (apiResponse.getResult().equals("OK")) {
				
					JOptionPane.showMessageDialog(this, "Id de peticiÛn: " + apiResponse.getRequest() + "\n" + "Id de mensaje: " + apiResponse.getIds().get(0), "Env√≠o", JOptionPane.INFORMATION_MESSAGE);
				} else {
					
					showErrorMessage(apiResponse.getResult());
				}

			} else if (button.getName().equals("exit")) {
				dispose();
			}
		} catch (Exception ex) {
			
			showErrorMessage(ex.getMessage());
		}
	}
	
	private void showErrorMessage(String errorMessage) {
		
		JOptionPane.showMessageDialog(this, errorMessage, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

}
