/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun;

import java.util.HashSet;
import java.util.Set;

    public class JAppManager {
        Set mListeners = new HashSet();
        Set mApps = new HashSet();
        public void addAppListener(JAppListener listener) {
            synchronized (mListeners) {
                mListeners.add(listener);
            }
        }
        public void removeAppListener(JAppListener listener) {
            synchronized (mListeners) {
                mListeners.remove(listener);
            }
        }
        public void addApp(JApp app) {
            mApps.add(app);
            fireAppAdded(app);
        }
        public void removeApp(JApp app) {
            mApps.remove(app);
            fireAppRemoved(app);
        }
        void fireAppRemoved(JApp app) {
            final JAppListener[] arr = new JAppListener[mListeners.size()];
            synchronized (mListeners) {
                mListeners.toArray(arr);
            }
            for (int i = 0; i < arr.length; i++) {
                arr[i].applicationRemoved(app);
            }
        }
        void fireAppAdded(JApp app) {
            JAppListener[] arr = new JAppListener[mListeners.size()];
            synchronized (mListeners) {
                mListeners.toArray(arr);
            }
            for (int i = 0; i < arr.length; i++) {
                arr[i].applicationAdded(app);
            }
        }
    }
