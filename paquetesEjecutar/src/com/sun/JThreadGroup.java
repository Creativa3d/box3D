/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun;

import java.io.IOException;
import java.io.OutputStream;

public class JThreadGroup extends ThreadGroup {
        OutputStream mOut;
        OutputStream mErr;
        Thread mDisposer;

        public void setDisposer(Thread disposer) {
            mDisposer = disposer;
        }

        public OutputStream getOut() {
            return mOut;
        }

        public OutputStream getErr() {
            return mErr;
        }

        public void close() {
            try {
                mOut.close();
            } catch (IOException e) {
            }
            if (mErr != mOut) {
                try {
                    mErr.close();
                } catch (IOException e) {
                }
            }
            mErr = null;
            Thread t = mDisposer;
            mDisposer = null;
            if (t != null) {
                System.out.println("disposing...");
                t.start();
            }
        }

        public JThreadGroup(OutputStream out, OutputStream err) {
            super("JApplication."+System.nanoTime());
            mOut = out;
            mErr = err;
        }
    }

