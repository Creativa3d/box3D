package utilesx;

import java.io.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.*;

import sun.security.util.*;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;
import sun.security.pkcs.*;


/*
P7TOOL is a sample utility which demonstrates the very useful (but un-documented)
PKSC#7 classes available in most versions of Sun's Java SDK (at least since 1.4.x).

This utility will perform simple file signing to produce both "attached" and
"detached" PKCS#7 signature files, as well as simple PKCS#7 signature validation.

This code has been compiled and run with some testing using JDK 1.5.0 Beta2,
and it seems suitable for use with the new inbuilt PKCS#11 based keystore providers
(good work Sun for putting this in!!).

Please feel free to distribute, or improve on this existing software as you see fit.
I just ask if you find this source code useful, or have suggestions on how to improve it
please let me know (or send me a version with your own improvements!).

DISCLAIMER:
This source/software is provided purely as a learning tool with NO WARRANTIES expressed or implied.

@version 1.0, 08/12/04
@author Joseph Leslie Granieri (j_granieri@yahoo.com)
 */
public class p7tool {

//    private String storetype = null;
//    private String storepath = null;
//    private char[] pwd = null;
//    private String alias = null;
    private InputStream moDatafile = null;
    private InputStream moSiginfile = null;
    private OutputStream moSigoutfile = null;
    private OutputStream moExtractfile = null;
    private boolean mbAttachFile = false;
    private boolean mbVerboseMode = false;
    private KeyStore moKeystore = null;
    private X509Certificate moX509 = null;
    private Certificate[] moCerts = null;
    private PrivateKey moPriv = null;
// Signature Algorithms Supported
    private String msDigestAlgorithm = "SHA1";
    private String msSigningAlgorithm = "SHA1withRSA";

    /*
    @param args the command line arguments
     */
    public static void main(String[] args) {
        int idx = 0;

        char[] pwd = new char[0];
        String storepath = null, storetype = null, alias = null;
        String datafilename = null, sigfilename = null, extractfilename = null;
        boolean doVerify = false;
        boolean attachFile = false;
        boolean extractFile = false;
        boolean verboseMode = false;
        boolean noBanner = false;

// this is a bit ugly, but the banner is the first thing that should appear
// even before the command arguments are passed...
        for (idx = 0; idx < args.length; idx++) {
            if (args[idx].equalsIgnoreCase("-nobanner")) {
                noBanner = true;
            }
        }

        if (!noBanner) {
            printBanner();
        }

        try {
            if (args.length == 0) {
                printUsage();
                return;
            }

            for (idx = 0; idx < args.length; idx++) {
                if (args[idx].equalsIgnoreCase("-help") || args[idx].equalsIgnoreCase("-?")) {
                    printUsage();
                    return;
                } else if (args[idx].equalsIgnoreCase("-sign")) {
                    doVerify = false;
                } else if (args[idx].equalsIgnoreCase("-verify")) {
                    doVerify = true;
                } else if (args[idx].equalsIgnoreCase("-datafile")) {
                    if (args.length == idx + 1) {
                        System.err.println("p7tool: Missing datafile file name parm");
                        printUsage();
                        return;
                    }
                    datafilename = args[++idx];
                } else if (args[idx].equalsIgnoreCase("-sigfile")) {
                    if (args.length == idx + 1) {
                        System.err.println("p7tool: Missing sigfile file name parm");
                        printUsage();
                        return;
                    }
                    sigfilename = args[+idx];
                } else if (args[idx].equalsIgnoreCase("-extract")) {
                    if (args.length == idx + 1) {
                        System.err.println("p7tool: Missing extract file name parm");
                        printUsage();
                        return;
                    }
                    extractfilename = args[+idx];
                    extractFile = true;
                } else if (args[idx].equalsIgnoreCase("-keystore")) {
                    if (args.length == idx + 1) {
                        System.err.println("p7tool: Missing keystore file name parm");
                        printUsage();
                        return;
                    }
                    storepath = args[+idx];
                } else if (args[idx].equalsIgnoreCase("-storetype")) {
                    if (args.length == idx + 1) {
                        System.err.println("p7tool: Missing storetype parm");
                        printUsage();
                        return;
                    }
                    storetype = args[+idx];
                } else if (args[idx].equalsIgnoreCase("-storepass")) {
                    if (args.length == idx + 1) {
                        System.err.println("p7tool: Missing storepass parm");
                        printUsage();
                        return;
                    }
                    pwd = args[+idx].toCharArray();
                } else if (args[idx].equalsIgnoreCase("-alias")) {
                    if (args.length == idx + 1) {
                        System.err.println("p7tool: Missing alias parm");
                        printUsage();
                        return;
                    }
                    alias = args[+idx];
                } else if (args[idx].equalsIgnoreCase("-attach")) {
                    attachFile = true;
                } else if (args[idx].equalsIgnoreCase("-verbose")) {
                    verboseMode = true;
                } else if (args[idx].equalsIgnoreCase("-nobanner")) {
                    noBanner = true;
                } else {
                    System.err.println("p7tool: Unknown command-line option '" + args[idx] + "'");
                    printUsage();
                    return;
                }
            }

// set the keystore defaults
            if (storetype == null) {
                storetype = KeyStore.getDefaultType();
            }
            if (storepath == null) {
                storepath = System.getProperty("user.home") + File.separator + ".keystore";
            }
            if (alias == null) {
                alias = "mykey";
            }

            p7tool p7Tool = new p7tool(verboseMode);

            if (!doVerify) {
// DO THE PKCS#7 SIGNING OPERATION
                InputStream datafile;
                OutputStream sigfile;
                if (datafilename != null) {
                    datafile = new FileInputStream(datafilename);
                } else {
                    datafile = System.in;
                }

                if (sigfilename != null) {
                    sigfile = new FileOutputStream(sigfilename);
                } else {
                    sigfile = System.out;
                }

                p7Tool.initSign(datafile, sigfile, attachFile, storetype, storepath, pwd, alias);
                p7Tool.sign();
            } else {
// DO THE PKCS#7 VERIFICATION ONLY
                InputStream datafile = null;
                InputStream sigfile = null;
                OutputStream extractfile = null;

                if (datafilename != null) {
                    datafile = new FileInputStream(datafilename);
                }

// signature file defaults to system.in
                if (sigfilename != null) {
                    sigfile = new FileInputStream(sigfilename);
                } else {
                    sigfile = System.in;
                }

// extract file defaults to system.out
                if (extractfilename != null) {
                    extractfile = new FileOutputStream(extractfilename);
                } else {
                    extractfile = System.out;
                }

                p7Tool.initVerify(datafile, sigfile, extractfile);
                p7Tool.verify();
            }

            /*
            if (idx == args.length) {
            System.err.println("p7tool: missing path to PDF file");
            printUsage(); return;
            }
             */
        } catch (Throwable tossed) {
            tossed.printStackTrace();
            printUsage();
            return;
        }

    }
    public static void printBanner() {
        System.err.println("==============================================================================");
        System.err.println("P7TOOL - PKCS#7 JAVA UTILITY (Written in 100% Pure Java)");
        System.err.println("");
        System.err.println("Original design and coding by Joseph Leslie Granieri (August 2004)");
        System.err.println("");
        System.err.println("DISCLAIMER:");
        System.err.println("This software is provided 'as is' with NO WARRANTIES expressed or implied.");
        System.err.println("==============================================================================");
        System.err.println("");
    }

    public static void printUsage() {
        System.err.println("p7tool usage:");
        System.err.println("");
        System.err.println("-help");
        System.err.println("");
        System.err.println("-sign [-datafile <inputfile>] [-sigfile <outputfile>] [-attach]");
        System.err.println(" [-keystore <filename>] [-storetype <storetype>] [-storepass <password>]");
        System.err.println(" [-alias <alias>]");
        System.err.println(" [-verbose] [-nobanner]");
        System.err.println("");
        System.err.println("-verify [-datafile <inputfile>] [-sigfile <inputfile>] [-extract <outputfile>]");
        System.err.println(" [-keystore <filename>] [-storetype <storetype>] [-storepass <password>]");
        System.err.println(" [-verbose] [-nobanner]");
    }

    /* Creates a new instance of p7tool */
    public p7tool(boolean verboseMode) {
        this.mbVerboseMode = verboseMode;
    }

    /**Consigue el privatekey y x509certificate, es decir, el certificado y la clave privada asociada*/
    public void initSign(
            InputStream datafile,
            OutputStream sigfile,
            boolean attachFile,
            String storetype,
            String storepath,
            char[] pwd,
            String alias) throws Exception {
        this.moDatafile = datafile;
        this.moSigoutfile = sigfile;
        this.mbAttachFile = attachFile;

        if (mbVerboseMode) {
            System.err.println("KeyStore Type: " + storetype);
            System.err.println("KeyStore Path: " + storepath);
            System.err.println("Key Alias: " + alias);
        }

        // carga el keystore (almacen de claves, puede tener varias)
        //Almacen de claves estandar
        KeyStore keystore = KeyStore.getInstance(storetype);
        if (storepath.compareToIgnoreCase("NONE") == 0) {
            //carga el almacen de claves por defecto del sistema
            keystore.load(null, pwd);
        } else {
            //carga el almacen de claves de un fichero
            keystore.load(new FileInputStream(storepath), pwd);
        }
        //si tiene un certificado q tenga el alias pasado por parametro
        if (keystore.isKeyEntry(alias)) {
            //devuelve los certificados q tienen ese alias
            moCerts = keystore.getCertificateChain(alias);
            //consigue el q cumple x509Certificate q nos sirve para acceder a todos los atributos
            if (moCerts[0] instanceof X509Certificate) {
                moX509 = (X509Certificate) moCerts[0];
            }
            if (moCerts[moCerts.length - 1] instanceof X509Certificate) {
                moX509 = (X509Certificate) moCerts[moCerts.length - 1];
            }
        //si tiene un certificado x509 q tenga el alias pasado por parametro
        } else if (keystore.isCertificateEntry(alias)) {
            java.security.cert.Certificate cert = keystore.getCertificate(alias);
            //consigue el q cumple x509Certificate q nos sirve para acceder a todos los atributos
            if (cert instanceof X509Certificate) {
                moX509 = (X509Certificate) cert;
                moCerts = new Certificate[]{moX509};
            }

        } else {
            throw new Exception(alias + " is unknown to this keystore");
        }

        // Consigue la clave privada
        moPriv = (PrivateKey) keystore.getKey(alias, pwd);

        if (moPriv == null) {
            throw new Exception(alias + " could not be accessed");
        }
    }

    public void sign() throws Exception {
// Create the PKCS7 Blob here
        //algoritmo digest, es decir, el algoritmo q coge un fichero y crea un clave unica para ese fichero
        //esta clave es la q se firmara, ya q firmar un fichero entero puede ser muy costoso
        AlgorithmId[] digestAlgorithmIds = {
            AlgorithmId.getAlgorithmId(msDigestAlgorithm)
        };

        // leemos todo el fichero
        byte[] labDataIN = new byte[moDatafile.available()];
        moDatafile.read(labDataIN);

        //algoritmo digest, es decir, el algoritmo q coge un fichero y crea un clave unica para ese fichero
        //esta clave es la q se firmara, ya q firmar un fichero entero puede ser muy costoso
        MessageDigest md = MessageDigest.getInstance(msDigestAlgorithm);
        md.update(labDataIN);
        byte[] labDigestedContent = md.digest();

// Atributos de autentificacion (construct authenticated attributes...), tipo datos, fecha, contenido a firmar
        PKCS9Attribute[] authenticatedAttributeList = {
            new PKCS9Attribute(PKCS9Attribute.CONTENT_TYPE_OID, ContentInfo.DATA_OID),
            new PKCS9Attribute(PKCS9Attribute.SIGNING_TIME_OID, new java.util.Date()),
            new PKCS9Attribute(PKCS9Attribute.MESSAGE_DIGEST_OID, labDigestedContent)
        };

        PKCS9Attributes authenticatedAttributes = new PKCS9Attributes(authenticatedAttributeList);

// digitally sign the DER encoding of the authenticated attributes with Private Key
        Signature signer = Signature.getInstance(msSigningAlgorithm);
        signer.initSign(moPriv);
        signer.update(authenticatedAttributes.getDerEncoding());
        byte[] signedAttributes = signer.sign();

        ContentInfo contentInfo = null;

// We can attach the data here, or not
        if (mbAttachFile) {
            if (mbVerboseMode) {
                System.err.println("PKCS#7 Data: Data is being 'attached' to signature");
            }
            contentInfo = new ContentInfo(ContentInfo.DATA_OID, new DerValue(DerValue.tag_OctetString, labDataIN));
        } else {
            if (mbVerboseMode) {
                System.err.println("PKCS#7 Data: No data 'attached' to signature");
            }
            contentInfo = new ContentInfo(ContentInfo.DATA_OID, null);
        }

        X509Certificate[] certificates = {moX509};

// uncomment for compatibility with 1.4.x SignerInfo
// sun.security.util.BigInt serial = new sun.security.util.BigInt(x509.getSerialNumber());

// for compatibility with 1.5.x SignerInfo
        java.math.BigInteger serial = moX509.getSerialNumber();

        SignerInfo si = new SignerInfo(
                new X500Name(moX509.getIssuerDN().getName()), // X500Name issuerName,
                serial, //x509.getSerialNumber(), // BigInteger serial,
                AlgorithmId.getAlgorithmId(msDigestAlgorithm), // AlgorithmId digestAlgorithmId,
                authenticatedAttributes,// PKCS9Attributes authenticatedAttributes,
                new AlgorithmId(AlgorithmId.RSAEncryption_oid), // AlgorithmId digestEncryptionAlgorithmId,
                signedAttributes, // byte[] encryptedDigest,
                null); // PKCS9Attributes unauthenticatedAttributes) {

        SignerInfo[] signerInfos = {si};

        PKCS7 p7 = new PKCS7(
                digestAlgorithmIds,
                contentInfo,
                certificates,
                signerInfos);

// printout the p7 contents
        if (mbVerboseMode) {
            System.err.println("PKCS#7 produced, dumping PKCS#7 contents...");
            System.err.println(p7.toString());
        }

        p7.encodeSignedData(moSigoutfile);
        moSigoutfile.close();
    }

    /* datafile, if null it is assumed the data is attached to the signature blob
    sigfile, mandatory
    extractfile, if null no data file is extracted from the signature
     */
    public void initVerify(
            InputStream datafile,
            InputStream sigfile,
            OutputStream extractfile) throws Exception {
        if (sigfile == null) {
            throw new Exception("Signature file must be supplied");
        }

        this.moDatafile = datafile;
        this.moSiginfile = sigfile;
        this.moExtractfile = extractfile;
    }

    public void verify() throws Exception {
        if (moSiginfile == null) {
            throw new Exception("Signature file must be supplied");
        }

// parse the PKCS7 input file...
        PKCS7 p7 = new PKCS7(moSiginfile);

        SignerInfo[] si = null;

// check if data is "attached" to this P7 blob
        if (p7.getContentInfo().getContentBytes() == null) {
            if (mbVerboseMode) {
                System.err.println("PKCS#7 Data: No data 'attached' to signature (reading from data file)");
            }

// this is a "detached" signature
// the original data must be provided to complete verification...
            if (moDatafile == null) {
                throw new Exception("Data file must be supplied for this 'detached' signature");
            }

            byte[] dataIN = new byte[moDatafile.available()];
// infile.reset();
            moDatafile.read(dataIN);

// do the verification on the data provided
            si = p7.verify(dataIN);
        } else {
            if (mbVerboseMode) {
                System.err.println("PKCS#7 Data: Data is 'attached' to signature");
            }

// original data is embedded or "attached" to this P7,
// implicit verification will do...
            si = p7.verify();
        }

// check the results of the verification
        if (si == null) {
            throw new Exception("Signature failed verification, data has been tampered");
        }

        System.err.println("[VERIFY OK]");

// printout the p7 contents
        if (mbVerboseMode) {
            System.err.println("PKCS#7 Validated, dumping PKCS#7 contents...");
            System.err.println(p7.toString());
        }

// extract the file
        if ((moExtractfile != null) && (p7.getContentInfo().getContentBytes() != null)) {
            if (moExtractfile == System.out) // always display this banner, even if not in verbose mode (very confusing otherwise)
            {
                System.err.println("====================== EXTRACTING DATA TO CONSOLE ============================");
            } else if (mbVerboseMode) {
                System.err.println("======================= EXTRACTING DATA TO FILE ==============================");
            }

            moExtractfile.write(p7.getContentInfo().getContentBytes());
            moExtractfile.close();
        }
    }
}
