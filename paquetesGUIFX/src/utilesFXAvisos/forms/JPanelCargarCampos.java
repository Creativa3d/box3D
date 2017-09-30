/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFXAvisos.forms;

import ListDatos.JSTabla;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import utilesFX.JCMBLinea;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.Rectangulo;

/**
 *
 * @author eduardo
 */
public class JPanelCargarCampos  extends JPanelGENERALBASE {

    @FXML
    private ComboBox<JCMBLinea> cmbCampos;
    private Node moComponentFocused;
    private int mlCaretPosition;
    private HashMap<String, Object> moCampos;
    private JFieldConComboBox mocmbCamposField;

    public JPanelCargarCampos() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFXAvisos/forms/JPanelCargarCampos.fxml"));
        loader.setController(this);
        try {
            JFXConfigGlobal.getInstancia().inicializarFX();
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void setDatos(HashMap<String, Object> poCampos, Node poComponentFocused, int plCaretPosition) throws Exception {
        moCampos = poCampos;
        moComponentFocused = poComponentFocused;
        mlCaretPosition = plCaretPosition;
    }

    @Override
    public JSTabla getTabla() {
        return null;
    }

    @Override
    public void rellenarPantalla() throws Exception {
        mocmbCamposField = new JFieldConComboBox(cmbCampos);
        

        for(Map.Entry<String, Object> entry : moCampos.entrySet()) {
            String lsNombreCampo = entry.getKey();
            mocmbCamposField.addLinea(lsNombreCampo, lsNombreCampo);
        }
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
    }

    @Override
    public void mostrarDatos() throws Exception {
    }

    @Override
    public void establecerDatos() throws Exception {
    }

    @Override
    public void aceptar() throws Exception {
        String lsCampo = mocmbCamposField.getFilaActual().msCampo(0);
        if (moComponentFocused instanceof TextField) {
            TextField loText = (TextField) moComponentFocused;
            StringBuffer lsTexto = new StringBuffer(loText.getText());
            lsTexto.insert(mlCaretPosition, "[" + lsCampo + "]");
            loText.setText(lsTexto.toString());
        } else if (moComponentFocused instanceof HTMLEditor) {
            HTMLEditor loText = (HTMLEditor) moComponentFocused;
            String jsCodeInsertHtml = "function insertHtmlAtCursor(html) {\n" +
                    "    var range, node;\n" +
                    "    if (window.getSelection && window.getSelection().getRangeAt) {\n" +
                    "        range = window.getSelection().getRangeAt(0);\n" +
                    "        node = range.createContextualFragment(html);\n" +
                    "        range.insertNode(node);\n" +
                    "    } else if (document.selection && document.selection.createRange) {\n" +
                    "        document.selection.createRange().pasteHTML(html);\n" +
                    "    }\n" +
                    "}insertHtmlAtCursor('####html####')";
            WebView webNode = (WebView)loText.lookup(".web-view");
            webNode.getEngine().executeScript(jsCodeInsertHtml.
                            replace("####html####",
            escapeJavaStyleString("[" + lsCampo + "]", true, true)));
            
//            
//            loText.setHtmlText(  
//                     loText.getHtmlText().substring(0, mlCaretPosition)
//                    + "[" + lsCampo + "]"
//                    + loText.getHtmlText().substring(mlCaretPosition)
//            );
            
        }
    }

    private static String hex(int i) {
        return Integer.toHexString(i);
    }

    //a method to convert to a javas/js style string 
    //https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/src-html/org/apache/commons/lang/StringEscapeUtils.html

    private static String escapeJavaStyleString(String str,
            boolean escapeSingleQuote, boolean escapeForwardSlash) {
        StringBuilder out = new StringBuilder("");
        if (str == null) {
            return null;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.append('\\');
                        }
                        out.append('\'');
                        break;
                    case '"':
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.append('\\');
                        }
                        out.append('/');
                        break;
                    default:
                        out.append(ch);
                        break;
                }
            }
        }
        return out.toString();
}
    @Override
    public void cancelar() throws Exception {
    }

    @Override
    public Rectangulo getTanano() {
        return new Rectangulo(0, 0, 450, 250);
    }

    @Override
    public String getTitulo() {
        return "Cargar campos";
    }

    @Override
    public boolean validarDatos() throws Exception {
        return super.validarDatos();
    }
}