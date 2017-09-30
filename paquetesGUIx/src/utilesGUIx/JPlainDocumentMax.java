/*
 * FixedLengthTextField.java
 *
 * Created on 10 de febrero de 2007, 11:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

 public class JPlainDocumentMax extends PlainDocument { 

    private final int maxLength; 

    public JPlainDocumentMax(final int maxLength) { 
        this.maxLength = maxLength; 
    } 
    public void insertString(final int offset, final String str, final AttributeSet a)  throws BadLocationException { 
        if(getLength() + str.length() > maxLength) {
            Toolkit.getDefaultToolkit().beep(); 
            super.insertString(offset, str.substring(0, maxLength-getLength()), a);
        } else { 
            super.insertString(offset, str, a);
        }
    }               
} 
