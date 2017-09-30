/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun;

public class JAppImpl implements JApp {
        JAppManager mManager;
        String mMainClass;
        JThreadGroup mThreadGroup;
        public JAppImpl(JAppManager manager,
                        JThreadGroup tg,
                        String className) {
            mManager = manager;
            mMainClass = className;
            mThreadGroup = tg;
        }

        public String getMainClassName() {
            return mMainClass;
        }
        public void destroy() {
            mThreadGroup.close();
        }
        public String toString() {
            return mMainClass;
        }
    }

