/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUI.tiposTextos;

/**
 *
 * @author eduardo
 */
public class KeyEventCZ {
    private char mcChar;

    public KeyEventCZ (){
        
    }
    public KeyEventCZ (char pcChar){
        setKeyChar(pcChar);
    }
    
    public char getKeyChar() {
        return mcChar;
    }
    public void setKeyChar(char pcChar){
        mcChar=pcChar;
    }
    
}
