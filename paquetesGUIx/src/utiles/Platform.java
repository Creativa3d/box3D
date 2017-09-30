/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */



import java.io.File;
import java.lang.reflect.Field;


/** Clase para la identificaci&oacute;n de la plataforma Cliente y
 * extracci&oacute;n de datos relativos a la misma. */
public final class Platform {

    /** Sistema operativo. */
    public enum OS {
        /** Microsoft Windows. */
        WINDOWS,
        /** Linux. */
        LINUX,
        /** Sun Solaris / Open Solaris. */
        SOLARIS,
        /** Apple Mac OS X. */
        MACOSX,
        /** Google Android. */
        ANDROID,
        /** Sistema operativo no identificado. */
        OTHER
    }

    /** Navegador Web. */
    public enum BROWSER {
        /** Microsoft internet Explorer. */
        INTERNET_EXPLORER,
        /** Mozilla Firefox. */
        FIREFOX,
        /** Google Chrome. */
        CHROME,
        /** Apple Safari. */
        SAFARI,
        /** Opera. */
        OPERA,
        /** Navegador Web no identificado. */
        OTHER
    }

    /** Version del entorno de ejecuci&oacute;n de Java. */
    public enum JREVER {
        /** Java 4 y anteriores. */
        J4,
        /** Java 5. */
        J5,
        /** Java 6. */
        J6,
        /** Java 7. */
        J7
    }

    /** Sistema operativo. */
    private static OS os = null;

    /** Arquitectura de la m&eacute;quina virtual java. */
    private static String javaArch = null;

    /** Directorio de Java. */
    private static String javaHome = null;

    /** Directorio de bibliotecas de Java. */
    private static String javaLibraryPath = null;

    /** Directorio del usuario. */
    private static String userHome = null;

    /** Constructor bloqueado. */
    private Platform() {
        // No permitimos la instanciacion
    }

    private static OS recoverOsName() {

        final String osName = System.getProperty("os.name"); //$NON-NLS-1$

        if(JCadenas.isVacio(osName)){
            JDepuracion.warning("No se ha podido determinar el sistema operativo"); //$NON-NLS-1$
            return OS.OTHER;
        }else if (osName.contains("indows")) { //$NON-NLS-1$
            return OS.WINDOWS;
        } else if (osName.contains("inux")) { //$NON-NLS-1$
            if ("Dalvik".equals(System.getProperty("java.vm.name"))) { //$NON-NLS-1$ //$NON-NLS-2$
                return OS.ANDROID;
            }
            return OS.LINUX;
        } else if (osName.contains("SunOS") || osName.contains("olaris")) { //$NON-NLS-1$ //$NON-NLS-2$
            return OS.SOLARIS;
        } else if (osName.startsWith("Mac OS X")) { //$NON-NLS-1$
            return OS.MACOSX;
        } else {
            JDepuracion.warning("No se ha podido determinar el sistema operativo"); //$NON-NLS-1$
            return OS.OTHER;
        }

    }

    /** Recupera el navegador web actual.
     * @param userAgent <i>UserAgent</i> del navegador Web
     * @return Navegador web correspondiente al UserAgent indicado. */
    public static BROWSER getBrowser(final String userAgent) {
        if (userAgent == null) {
            return BROWSER.OTHER;
        }
        else if (userAgent.toLowerCase().contains("msie")) { //$NON-NLS-1$
            return BROWSER.INTERNET_EXPLORER;
        }
        else if (userAgent.toLowerCase().contains("firefox")) { //$NON-NLS-1$
            return BROWSER.FIREFOX;
        }
        else if (userAgent.toLowerCase().contains("chrome")) { //$NON-NLS-1$
            return BROWSER.CHROME;
        }
        else if (userAgent.toLowerCase().contains("safari")) { //$NON-NLS-1$
            // CUIDADO: Chrome incluye la cadena "safari" como parte de su
            // UserAgent
            return BROWSER.SAFARI;
        }
        else if (userAgent.toLowerCase().contains("opera")) { //$NON-NLS-1$
            return BROWSER.OPERA;
        }
        else { // Cualquier otro navegador
            return BROWSER.OTHER;
        }
    }

    /** Recupera el sistema operativo de ejecuci&oacute;n.
     * @return Sistema operativo actual. */
    public static Platform.OS getOS() {
        if (os == null) {
            os = recoverOsName();
        }
        return os;
    }

    /** Recupera la arquitectura de la JVM en ejecuci&oacute;n seg&uacute;n las
     * propiedades de Java.
     * @return Arquitectura de la JVM. */
    public static String getJavaArch() {
        if (javaArch == null) {
            javaArch = System.getProperty("sun.arch.data.model"); //$NON-NLS-1$
            if (javaArch == null) {
                javaArch = System.getProperty("com.ibm.vm.bitmode"); //$NON-NLS-1$
            }
        }
        return javaArch;
    }

    /** Recupera la ruta del directorio de instalaci&oacute;n de Java.
     * @return Ruta del directorio de instalaci&oacute;n de Java. */
    public static String getJavaHome() {
        if (javaHome == null) {
            javaHome = recoverJavaHome();
        }
        return javaHome;
    }

    /** Recupera la propiedad Path de Java.
     * @return Propiedad en el Path de Java. */
    public static String getJavaLibraryPath() {
        if (javaLibraryPath == null) {
            javaLibraryPath = System.getProperty("java.library.path"); //$NON-NLS-1$
        }
        return javaLibraryPath;
    }

    /** Recupera la ruta del directorio personal del usuario del sistema
     * operativo.
     * @return Ruta del directorio del usuario. */
    public static String getUserHome() {
        if (userHome == null) {
            userHome = System.getProperty("user.home"); //$NON-NLS-1$
        }
        return userHome;
    }

    /** Obtiene el directorio de instalaci&oacute;n del entorno de
     * ejecuci&oacute;n de Java actualmente en uso. Si no se puede obtener, se
     * devolver&aacute; {@code null}. Copiado de com.sun.deploy.config.Config.
     * @return Directorio del entorno de ejecuci&oacute;n de Java. */
    private static String recoverJavaHome() {
        String ret = null;
        try {
            ret = System.getProperty("jnlpx.home"); //$NON-NLS-1$
        }
        catch (final Exception e) {
            // Se ignora, puede que no haya permisos para leerla
        }
        if (ret != null) {
            return ret.substring(0, ret.lastIndexOf(File.separator));
        }

        try {
            return System.getProperty("java.home"); //$NON-NLS-1$
        }
        catch (final Exception e) {
            JDepuracion.warning("No se ha podido identificar el directorio de java"); //$NON-NLS-1$
        }

        return null;
    }


    /** Obtiene el directorio principal del sistema operativo del sistema.
     * @return Directorio principal del sistema operativo */
    private static String getSystemRoot() {
        if (!Platform.getOS().equals(Platform.OS.WINDOWS)) {
            return File.separator;
        }
        String systemRoot = null;
        final String defaultSystemRoot = "C:\\WINDOWS"; //$NON-NLS-1$
        try {
            systemRoot =
                WinRegistryWrapper.getString(WinRegistryWrapper.HKEY_LOCAL_MACHINE,
                        "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", //$NON-NLS-1$
                "SystemRoot"); //$NON-NLS-1$
        }
        catch (final Exception e) {
            JDepuracion
            .severe("No se ha podido obtener el directorio principal de Windows accediendo al registro, " + "se probara con 'C:\\WINDOWS': " //$NON-NLS-1$ //$NON-NLS-2$
                    + e);
        }
        if (systemRoot == null) {
            final File winSys32 = new File(defaultSystemRoot + "\\SYSTEM32"); //$NON-NLS-1$
            if (winSys32.exists() && winSys32.isDirectory()) {
                return defaultSystemRoot;
            }
        }
        if (systemRoot == null) {
            JDepuracion
            .warning("No se ha encontrado el directorio ra&iacute;z del sistema, se devolver&aacute;: " + File.separator); //$NON-NLS-1$
            systemRoot = File.separator;
        }
        return systemRoot;
    }

    /** Devuelve el directorio principal de bibliotecas del sistema. Importante:
     * No funciona correctamente en Windows de 64 bits //FIXME
     * @return Directorio principal de bibliotecas
     */
    public static String getSystemLibDir() {
        if (Platform.getOS().equals(Platform.OS.WINDOWS)) {
            String systemRoot = getSystemRoot();
            if (systemRoot == null) {
                JDepuracion .warning("No se ha podido determinar el directorio de Windows accediendo al registro, se usara 'C:\\WINDOWS\\'"); //$NON-NLS-1$
                systemRoot = "c:\\windows\\"; //$NON-NLS-1$
            }
            if (!systemRoot.endsWith("\\")) { //$NON-NLS-1$
                systemRoot += "\\"; //$NON-NLS-1$
            }
            return systemRoot + "System32"; //$NON-NLS-1$
        }
        return "/usr/lib"; //$NON-NLS-1$
    }

    /** Obtiene la versi&oacute;n de BouncyCastle en uso.
     * @return Versi&oacute;n del BouncyCastle encontrado primero en el BootClassPath
     * o en el ClassPath. Si no se puede recuperar se devuelve {@code null} */
    public static String getBouncyCastleVersion() {

        try {
            final Class<?> bouncyCastleProviderClass = AOUtil.classForName("org.bouncycastle.jce.provider.BouncyCastleProvider"); //$NON-NLS-1$
            final Field info = bouncyCastleProviderClass.getDeclaredField("info"); //$NON-NLS-1$
            info.setAccessible(true);
            return info.get("").toString().replace("BouncyCastle Security Provider v", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        catch (final Exception e) {
        	return null;
        }
    }

    /** Obtiene el directorio de extensiones del entorno de ejecuci&oacute;n de Java en uso.
     * @return Directorio de extensiones del JRE o {@code null} si no se pudo identificar */
    public static String getJavaExtDir() {
        final File extDir = new File(getJavaHome() + (getJavaHome().endsWith(File.separator) ? "" : File.separator) + "lib" + File.separator + "ext"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        if (extDir.exists() && extDir.isDirectory()) {
            return extDir.getAbsolutePath();
        }
        return null;
    }

    /** Obtiene el directorio global de extensiones de Java.
     * @return Directorio de extensiones Java del sistema o {@code null} si no se pudo identificar o no existe */
    public static String getSystemJavaExtDir() {
        final File systemExtDir;
        switch (getOS()) {
            case WINDOWS:
                systemExtDir = new File(getSystemRoot() + (getSystemRoot().endsWith("\\") ? "" : "\\") + "Sun\\Java\\lib\\ext"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                break;
            case SOLARIS:
                systemExtDir = new File("/usr/jdk/packages/lib/ext"); //$NON-NLS-1$
                break;
            case LINUX:
                systemExtDir = new File("/usr/java/packages/lib/ext"); //$NON-NLS-1$
                break;
            case MACOSX:
                systemExtDir = new File("/Library/Java/Extensions"); //$NON-NLS-1$
                break;
            default:
                JDepuracion.warning("No se soporta el sistema operativo '" + getOS() + "' para la obtencion del directorio global de extensiones Java, se devolvera null"); //$NON-NLS-1$ //$NON-NLS-2$
                return null;
        }
        if (systemExtDir.exists() && systemExtDir.isDirectory()) {
            return systemExtDir.getAbsolutePath();
        }
        JDepuracion.info("El directorio global de extensiones Java no esta creado, se devolvera null"); //$NON-NLS-1$
        return null;
    }

}
