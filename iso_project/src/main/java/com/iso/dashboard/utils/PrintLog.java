/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import org.apache.log4j.Logger;

/**
 *
 * @author VIET_BROTHER
 */
public class PrintLog {

    static Logger logger = Logger.getLogger(PrintLog.class);
    public static String printLog(Exception e) {
        String str;
        try {
            str = new StringBuilder().append(":").append(e.toString()).toString();
            if ((e.getCause() != null) && (e.getCause().getMessage() != null)) {
                str = new StringBuilder().append(str).append(" - Caused by ").append(e.getCause().getMessage()).toString();
            }
            str = new StringBuilder().append(str).append("\n").toString();
            StackTraceElement[] traceList = e.getStackTrace();
            for (StackTraceElement trace : traceList) {
                if (trace.getClassName().contains("com.viettel.gnoc")) {
                    str = new StringBuilder().append(str).append(" [").append(trace.getClassName()).append(".class][").append(trace.getMethodName()).append("][").append(trace.getLineNumber()).append("]\n").toString();
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            str = new StringBuilder().append(":").append(e.toString()).toString();
            logger.error(str,ex);
        }
        return str;
    }
}
