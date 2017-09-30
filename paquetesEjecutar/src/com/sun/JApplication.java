package com.sun;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.reflect.Method;

public class JApplication {

    static Map mClassLoaderCache = new HashMap();

    static JAppManager mManager = new JAppManager();

    static JThreadGroup getJThreadGroup() {
        ThreadGroup g = Thread.currentThread().getThreadGroup();
        while (g != null) {
            if (g instanceof JThreadGroup) {
                return (JThreadGroup)g;
            }
            g = g.getParent();
        }
        return null;
    }

//    static class SysOut {
//
//        PrintStream mSysOut;
//        PrintStream mSysErr;
//        OutputStream mOut;
//        OutputStream mErr;
//
//
//        public SysOut() {
//            mSysOut = System.out;
//            mSysErr = System.err;
//            mOut = new OutputStream() {
//                    public void write(int b) throws IOException {
//                        JThreadGroup tg = getJThreadGroup();
//                        if (tg != null) {
//                            tg.getOut().write(b);
//                        } else {
//                            mSysOut.write(b);
//                        }
//                    }
//                    public void flush() throws IOException {
//                	JThreadGroup tg = getJThreadGroup();
//                        if (tg != null) {
//                            tg.getOut().flush();
//                        } else {
//                            mSysOut.flush();
//                        }
//                    }
//                };
//            mErr = new OutputStream() {
//                    public void write(int b) throws IOException {
//                        JThreadGroup tg = getJThreadGroup();
//                        if (tg != null) {
//                            tg.getErr().write(b);
//                        } else {
//                            mSysErr.write(b);
//                        }
//                    }
//                    public void flush() throws IOException {
//                	JThreadGroup tg = getJThreadGroup();
//                        if (tg != null) {
//                            tg.getErr().flush();
//                        } else {
//                            mSysErr.flush();
//                        }
//                    }
//                };
//            System.setOut(new PrintStream(mOut));
//            System.setErr(new PrintStream(mErr));
//        }
//
//    }


    static void installManagerApp() throws Exception {
        final JFrame frame = new JFrame("Applications");
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        final DefaultListModel model = new DefaultListModel();
        final JList appList = new JList();
        appList.setModel(model);
        JScrollPane sp = new JScrollPane(appList);
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        p3.add(sp, BorderLayout.CENTER);
        p3.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 4));
        p3.add(sp, BorderLayout.CENTER);
        p.add(p3, BorderLayout.CENTER);
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.TRAILING,  0, 0));
        final JButton b = new JButton("End Process");
        b.setMnemonic('E');
        b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int i = appList.getSelectedIndex();
                    if (i >= 0 && i < model.getSize()) {
                        JApp app = (JApp)model.elementAt(i);
                        app.destroy();
                    }
                }
            });
        p1.setBorder(BorderFactory.createEmptyBorder(0, 6, 6, 6));
        p1.add(b);
        p.add(p1, BorderLayout.SOUTH);
        JTabbedPane t = new JTabbedPane();
        t.addTab("Applications", p);
        t.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        frame.getContentPane().add(t);
        frame.pack();
        frame.setSize(500, 300);
        appList.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    b.setEnabled(appList.getSelectedIndex() >= 0);
                }
            });
        mManager.addAppListener(new JAppListener() {
                public void applicationAdded(final JApp app) {
                    SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                model.addElement(app);
                                if (model.getSize() == 1) {
                                    appList.setSelectedIndex(0);
                                }
                            }
                        });
                }
                public void applicationRemoved(final JApp app) {
                    SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                int i = model.indexOf(app);
                                if (i >= 0 && i < model.getSize()) {
                                    model.removeElementAt(i);
                                }
                                if (i > model.getSize()) {
                                    i = model.getSize()-1;
                                }
                                if (i >= 0) {
                                    appList.setSelectedIndex(i);
                                }
                            }
                        });
                }
            });
        try {
            installTrayIcon(frame);
            frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
        } catch (LinkageError e) {
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }


    static void installTrayIcon(final JFrame frame) {

        //JButton button = new JButton("
        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();
            URL u = JApplication.class.getResource("resources/java32.png");
            Image image = Toolkit.getDefaultToolkit().getImage(u);

            MouseListener mouseListener = new MouseListener() {

                    public void mouseClicked(MouseEvent e) {
                        System.out.println("Tray Icon - Mouse clicked!");
                    }

                    public void mouseEntered(MouseEvent e) {
                        System.out.println("Tray Icon - Mouse entered!");
                    }

                    public void mouseExited(MouseEvent e) {
                        System.out.println("Tray Icon - Mouse exited!");
                    }

                    public void mousePressed(MouseEvent e) {
                        System.out.println("Tray Icon - Mouse pressed!");
                    }

                    public void mouseReleased(MouseEvent e) {
                        System.out.println("Tray Icon - Mouse released!");
                    }
                };

            ActionListener exitListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                };
            ActionListener frameAction = new ActionListener() {
                    boolean firstTime = true;
                    public void actionPerformed(ActionEvent e) {
                        if (firstTime) {
                            firstTime = false;
                            Dimension screen =
                                Toolkit.getDefaultToolkit().getScreenSize();
                            Dimension size = frame.getSize();
                            frame.setLocation(screen.width/2 - size.width/2,
                                              screen.height/2 - size.height/2);
                        }
                        frame.setVisible(true);
                    }
                };

            final PopupMenu popup = new PopupMenu();
            MenuItem appItem = new MenuItem("Applications...");
            popup.add(appItem);
            appItem.addActionListener(frameAction);
            MenuItem gcItem = new MenuItem("Garbage Collect");
            popup.add(gcItem);
            gcItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        synchronized (mClassLoaderCache) {
                            mClassLoaderCache.clear();
                        }
                        System.gc();
                        System.runFinalization();
                        try {
                            Thread.sleep(200);
                        } catch (Exception ignored) {
                        }
                        System.gc();
                        int amount = (int)Runtime.getRuntime().totalMemory();
                        byte[] data = null;
                        try {
                            data = new byte[amount];
                        } catch (OutOfMemoryError err) {
                            System.out.println("forced out of memory error");
                        }
                        data = null;
                        System.gc();
                        System.runFinalization();
                        try {
                            Thread.sleep(200);
                        } catch (Exception ignored) {
                        }
                        System.gc();
                    }
                });
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);
            TrayIcon trayIcon = new TrayIcon(image, "Java", popup);


            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(frameAction);
            //trayIcon.addMouseListener(mouseListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("TrayIcon could not be added.");
            }

        } else {

            //  System Tray is not supported

        }
    }

    public static void main(final String argv[])
        throws Exception
    {
        try {
            OutputStream os;
            InputStream is = null;
            try {
                Socket sock;
                sock = new Socket("localhost", 0xCAFE);
                os = sock.getOutputStream();
                is = sock.getInputStream();
                //escrimos los parametros
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                for (int i = 0; i < argv.length; i++) {
                    writer.write(argv[i]);
                    writer.write("\n");
                    System.out.println("wrote: " + argv[i]);
                }
                writer.write("\n");
                writer.flush();
//                //leemos la respuesta, he quitado la respuesta del servidor
//                try {
//                    BufferedReader b = new BufferedReader(new InputStreamReader(is));
//                    while (true) {
//                        String line = b.readLine();
//                        if (line == null) {
//                            break;
//                        }
//                        System.out.println(line);
//                    }
//                } finally {
//                    System.exit(0);
//                }
            } catch (IOException e) {
                iniciarServidor(argv);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void start(final String[] argv,
                      OutputStream out,
                      OutputStream err) throws Exception {
        String mainClassName = null;
        String [] appArgs = new String[0];
        ClassLoader classLoader = null;
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-classpath")) {
                String cp = argv[i+1];
                StringTokenizer izer = new StringTokenizer(cp, ";");
                List urls = new LinkedList();
                while (izer.hasMoreTokens()) {
                    String urlString = izer.nextToken();
                    URL u;
                    if (!urlString.startsWith("http:") &&
                        !urlString.startsWith("file:") &&
                        !urlString.startsWith("jar:")) {
                        File f = new File(urlString).getCanonicalFile();
                        u = f.toURI().toURL();
                    } else {
                        u = new URL(urlString);
                    }
                    urls.add(u);
                }
                URL[] urlArray = new URL[urls.size()];
                urls.toArray(urlArray);
                URLClassLoader loader;
                synchronized (mClassLoaderCache) {
                    //no se para q sirve cachear los classloader
                    //ya q para la misma aplicacion crea otro classloader
//                    loader = (URLClassLoader)mClassLoaderCache.get(urlArray);
//                    if (loader == null) {
                        loader = new URLClassLoader(urlArray, JApplication.class.getClassLoader());
//                        mClassLoaderCache.put(urlArray, loader);
//                    }
                }
                classLoader = loader;
                i++;
            } else if (!argv[i].startsWith("-")) {
                mainClassName = argv[i];
                if (i + 1 < argv.length) {
                    appArgs = new String[argv.length-i+1];
                    int k = 0;
                    for (int j = i + 1; j < argv.length; j++, k++) {
                        appArgs[k] = argv[j];
                    }
                }
                break;
            }
        }
        System.out.println("main class: " + mainClassName);
        System.out.println("classLoader: " + classLoader);
        final Runnable[] runner = new Runnable[1];
        final Thread disposer = new Thread(new Runnable() {
                public void run() {
                    Runnable r = runner[0];
                    runner[0] = null;
                    System.out.println("diposer running: " + r);
                    if (r != null) {
                        r.run();
                    }
                }
            });
        final ClassLoader finalClassLoader = classLoader;
        final String[] finalArgs = appArgs;
        final JThreadGroup threadGroup = new JThreadGroup(out, err);
        threadGroup.setDisposer(disposer);
        final String finalClassName = mainClassName;
        final JAppImpl app = new JAppImpl(mManager,
                                          threadGroup,
                                          finalClassName);
        mManager.addApp(app);
        Thread thread = new Thread(threadGroup, new Runnable() {

                public void run() {
                    final sun.awt.AppContext appContext = sun.awt.SunToolkit.createNewAppContext();
                    runner[0] = new Runnable() {
                            public void run() {
                                mManager.removeApp(app);
                                System.out.println("disposing app context: ");
                                appContext.dispose();
                            }
                        };
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                                public void run() {
                                    try {
                                        Method main = finalClassLoader.loadClass(finalClassName).getMethod("main", new Class[]{String[].class});
                                        main.invoke(null, new Object[] {finalArgs});
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        threadGroup.close();
                                    }
                                }
                            });
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        thread.start();
    }

    public static void comunicar(final String argv[]) throws Exception{
        OutputStream os;
        InputStream is = null;
        try {
            Socket sock;
            sock = new Socket("localhost", 0xCAFE);
            os = sock.getOutputStream();
            is = sock.getInputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            for (int i = 0; i < argv.length; i++) {
                writer.write(argv[i]);
                writer.write("\n");
                System.out.println("wrote: " + argv[i]);
            }
            writer.write("\n");
            writer.flush();
            writer.close();
            os.close();
            is.close();
            sock.close();

        } catch (IOException e) {
            iniciarServidor(argv);
        }
    }
    private static void iniciarServidor(final String[] argv) throws IOException {
        final ServerSocket ss  = new ServerSocket(0xCAFE);
        new Thread() {
            {
                setDaemon(false);
            }
            public void run() {
                try {
                    BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
                    OutputStream sysout = System.out;
                    OutputStream syserr = System.err;
                    SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                try {
                                    installManagerApp();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

//                    SysOut sysOut = new SysOut();

                    System.setSecurityManager(new SecurityManager() {

                            public void checkExit (int status) {
                                Throwable t = new Throwable();
                                StackTraceElement[] trace =
                                    t.getStackTrace();
                                for (int i = 0; i < trace.length; i++) {
                                    if (trace[i].getClassName().equals("java.lang.System") && trace[i].getMethodName().equals("exit")) {
                                        JThreadGroup tg =
                                            getJThreadGroup();
                                        if (tg != null) {
                                            tg.close();
                                            throw new RuntimeException("exit");
                                        }
                                        break;
                                    }
                                }
                            }

                            public void checkPermission(java.security.Permission p) {
                            }
                        });

                    if (argv.length > 0) {
                        JApplication.start(argv, new OutStreamWrapper(System.out), new OutStreamWrapper(System.err));
                    }
                    while (true) {
                        OutputStream out = null;
                        OutputStream err = null;
                        Socket sock = ss.accept();
                        b = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        List list = new LinkedList();
                        String line = null;
                        while ((line = b.readLine()) != null) {
                            if (line.length() == 0) {
                                break;
                            }
                            list.add(line);
                        }
                        String[] args = new String[list.size()];
                        list.toArray(args);
                        System.out.println("starting: " + list);
                        out = new OutStreamWrapper(System.out);
                        err = new OutStreamWrapper(System.err);
                        try {
                            JApplication.start(args, out, err);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sock.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return;
    }
}

class OutStreamWrapper extends OutputStream {
    OutputStream moOut;
    public OutStreamWrapper(OutputStream poOut){
        moOut=poOut;
    }
    public void write(int b) throws IOException {
        moOut.write(b);
    }
}
