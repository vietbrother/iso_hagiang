/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import com.vaadin.ui.Window;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class DataUtil {

    public static boolean isNullOrEmpty(Object obj1) {
        return (obj1 == null || "".equals(obj1.toString()));
    }

    public static boolean isNullOrEmpty(String obj1) {
        return (obj1 == null || "".equals(obj1.trim()));
    }

    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || obj1.toString().trim().isEmpty();
    }

    public static boolean isListNullOrEmpty(List<?> lst) {
        return lst == null || lst.isEmpty();
    }

    public static void reloadWindow(Window subWindow) {

        subWindow.addWindowModeChangeListener(new Window.WindowModeChangeListener() {
            @Override
            public void windowModeChanged(Window.WindowModeChangeEvent event) {

                try {
                    Thread.sleep(500L);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }


}
