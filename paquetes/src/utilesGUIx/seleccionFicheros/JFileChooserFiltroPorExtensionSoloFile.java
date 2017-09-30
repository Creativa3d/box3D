/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.seleccionFicheros;

import java.io.File;

public class JFileChooserFiltroPorExtensionSoloFile implements java.io.FileFilter {

    private String description;
    private String extensions[];
    private boolean mbDirectoriosValidos=true;

    public JFileChooserFiltroPorExtensionSoloFile(String description, String extension) {
        this(description, new String[]{extension});
    }

    public JFileChooserFiltroPorExtensionSoloFile(String description, String extensions[]) {
        if (description == null) {
            this.description = extensions[0];
        } else {
            this.description = description;
        }
        this.extensions = (String[]) extensions.clone();
        toLower(this.extensions);
    }
    
    public void setDirectoriosValidos(boolean pbValor){
        mbDirectoriosValidos = pbValor;
    }

    private void toLower(String array[]) {
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = array[i].toLowerCase();
        }
    }

    public String getDescription() {
        return description;
    }

    public boolean accept(File file) {
        if (file.isDirectory() && mbDirectoriosValidos) {
            return true;
        } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = getExtensions().length; i < n; i++) {
                String extension = getExtensions()[i];
                if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the extensions
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * @param extensions the extensions to set
     */
    public void setExtensions(String[] extensions) {
        this.extensions = extensions;
    }
}
