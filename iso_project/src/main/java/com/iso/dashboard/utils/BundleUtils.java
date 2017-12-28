/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

//import com.iso.dashboard.component.CustomTable;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
//import com.vaadin.ui.CustomTable;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 *
 * @author VIET_BROTHER
 */
public class BundleUtils {

    public static final Locale locale = (Locale) VaadinSession.getCurrent().getSession().getAttribute("locale");
    private static final String BUSINESS_CONFIG = "businessconfig";
    private static final String CONTRAINT = "AppParamsContrants";
    //contraints table type --pricefactor--
    private static final String CONTRAINT_TABLE = "DataTypeContrants";
    private static final String SEPARATOR = "#";
    private static volatile ResourceBundle rsConfigLanguage = null;
    private static volatile ResourceBundle rsConfigConstraintTable = null;
    private volatile static ResourceBundle rsConfigConstraint = null;
    private volatile static ResourceBundle rsConfigBussiness = null;
    private volatile static ResourceBundle rsConfigCas = null;
    private volatile static ResourceBundle rsConfigMenu = null;
    private volatile static ResourceBundle rsConfigTrouble = null;
    private volatile static ResourceBundle rsConfigSp = null;
    private volatile static ResourceBundle rsConfigChangeManagement = null;
    private static Map<Integer, Table.Align> mapAlign;
//    private static Map<Integer, CustomTable.Align> mapAligns;
    static final Logger log = Logger.getLogger(BundleUtils.class);

    //mapAlign giup thuc hien set align cho cac cot trong bang
    //voi gia tri tuong ung: 1: can trai, 2: can phai, 3: can giua
//    static {
//        mapAligns = new HashMap<Integer, CustomTable.Align>();
//        mapAligns.put(1, CustomTable.Align.LEFT);
//        mapAligns.put(2, CustomTable.Align.RIGHT);
//        mapAligns.put(3, CustomTable.Align.CENTER);
//        mapAlign = new HashMap<Integer, Table.Align>();
//        mapAlign.put(1, Table.Align.LEFT);
//        mapAlign.put(2, Table.Align.RIGHT);
//        mapAlign.put(3, Table.Align.CENTER);
//    }

    public static String getString(String key, Locale... locale) {
        try {
            Locale vi = new Locale("vi");
            Locale mlocale = vi;
            try {
                mlocale = (Locale) VaadinSession.getCurrent().getSession().getAttribute("locale");
                if (mlocale == null) {
                    VaadinSession.getCurrent().getSession().setAttribute("locale", vi);
                    mlocale = vi;
                }
            } catch (Exception e) {
                log.info("VaadionSession is null");
                log.error(e.getMessage());
            }

            if (locale != null) {
                if (locale.length == 0) {
                    rsConfigLanguage = ResourceBundle.getBundle(Constants.LANGUAGE, mlocale);
                } else {
                    rsConfigLanguage = ResourceBundle.getBundle(Constants.LANGUAGE, locale[0]);
                }
            } else {
                rsConfigLanguage = ResourceBundle.getBundle(Constants.LANGUAGE, new Locale("vi"));
            }

            return rsConfigLanguage.getString(key);
        } catch (Exception e) {
            PrintLog.printLog(e);
            return key;
        }
    }

    public static String getString(String key, String defaultvalue, Locale... locale) {
        //Locale mlocale = (Locale) VaadinSession.getCurrent().getSession().getAttribute("locale");
        try {
            Locale mlocale = null;
            try {
                mlocale = (Locale) VaadinSession.getCurrent().getSession().getAttribute("locale");
            } catch (Exception e) {
                log.info("VaadionSession is null");
                log.error(e.getMessage());
            }
            if (locale != null) {
                if (locale.length == 0) {
                    rsConfigLanguage = ResourceBundle.getBundle(Constants.LANGUAGE, mlocale);
                } else {
                    rsConfigLanguage = ResourceBundle.getBundle(Constants.LANGUAGE, locale[0]);
                }
            } else {
                rsConfigLanguage = ResourceBundle.getBundle(Constants.LANGUAGE, new Locale("vi"));
            }
            return rsConfigLanguage.getString(key);
        } catch (Exception e) {
            PrintLog.printLog(e);
            return defaultvalue;
        }
    }

    public static String getMenuString(String key, String defaultvalue, Locale... locale) {
        try {
            //Locale mlocale = (Locale) VaadinSession.getCurrent().getSession().getAttribute("locale");
            Locale mlocale = null;
            try {
                mlocale = (Locale) VaadinSession.getCurrent().getSession().getAttribute("locale");
            } catch (Exception e) {
                log.info("VaadionSession is null");
                log.error(e.getMessage());
            }

            if (locale != null) {
                if (locale.length == 0) {
                    rsConfigMenu = ResourceBundle.getBundle(Constants.MENU, mlocale);
                } else {
                    rsConfigMenu = ResourceBundle.getBundle(Constants.MENU, locale[0]);
                }
            } else {
                rsConfigMenu = ResourceBundle.getBundle(Constants.MENU, new Locale("vi"));
            }
            return rsConfigMenu.getString(key);
        } catch (Exception e) {
            log.info(e);
            //PrintLog.printLog(e);
            return defaultvalue;
        }
    }

    public static String getStringCas(String key) {
        try {
            rsConfigCas = ResourceBundle.getBundle(Constants.CAS);
            if (DataUtil.isStringNullOrEmpty(rsConfigCas)) {
                return key;
            }
            return rsConfigCas.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Can't find resource for bundle java.util.PropertyResourceBundle, key : " + key);
            return key;
        }
    }

    public static String getStringCas(String key, String defaultVal) {
        try {
            rsConfigCas = ResourceBundle.getBundle(Constants.CAS);
            if (DataUtil.isStringNullOrEmpty(rsConfigCas)) {
                return defaultVal;
            }
            return rsConfigCas.getString(key);
        } catch (Exception e) {
            //PrintLog.printLog(e);
            log.info("Can't find resource for bundle java.util.PropertyResourceBundle, key : " + key);
            return defaultVal;
        }

    }

    public static String getConfigString(String key, Locale... locale) {
        if (locale == null) {
            rsConfigBussiness = ResourceBundle.getBundle(BUSINESS_CONFIG, locale[0]);
        } else {
            rsConfigBussiness = ResourceBundle.getBundle(BUSINESS_CONFIG, Locale.ENGLISH);
        }
        return rsConfigBussiness.getString(key);
    }

    public static String getConfigContraint(String key, Locale... locale) {
        if (locale == null) {
            rsConfigConstraint = ResourceBundle.getBundle(CONTRAINT, locale[0]);
        } else {
            rsConfigConstraint = ResourceBundle.getBundle(CONTRAINT, Locale.ENGLISH);
        }
        return rsConfigConstraint.getString(key);
    }

    //contraints data type
    public static String getConfigTableContraint(String key, Locale... locale) {
        if (locale == null) {
            rsConfigConstraintTable = ResourceBundle.getBundle(CONTRAINT_TABLE, locale[0]);
        } else {
            rsConfigConstraintTable = ResourceBundle.getBundle(CONTRAINT_TABLE, Locale.ENGLISH);
        }
        return rsConfigConstraintTable.getString(key);
    }

    public static LinkedHashMap<String, Table.Align> getHeaders(String key) {
        String headerList = BundleUtils.getConfigString(key);
        String[] headers = headerList.split(",");
        LinkedHashMap<String, Table.Align> headerData = new LinkedHashMap<String, Table.Align>();
        for (int i = 0; i < headers.length; i++) {
            String[] headerDataType = headers[i].split(SEPARATOR);
            headerData.put(headerDataType[0], mapAlign.get(Integer.parseInt(headerDataType[1])));
        }
        return headerData;
    }

//    public static LinkedHashMap<String, CustomTable.Align> getHeadersFilter(String key) {
//        String headerList = BundleUtils.getConfigString(key);
//        String[] headers = headerList.split(",");
//        LinkedHashMap<String, CustomTable.Align> headerData = new LinkedHashMap<String, CustomTable.Align>();
//        for (int i = 0; i < headers.length; i++) {
//            String[] headerDataType = headers[i].split(SEPARATOR);
//            headerData.put(headerDataType[0], mapAligns.get(Integer.parseInt(headerDataType[1])));
//        }
//        return headerData;
//    }
//
//    public static LinkedHashMap<String, CustomTable.Align> getHeadersFilterFromDB(String headerList) {
//        String[] headers = headerList.split(",");
//        LinkedHashMap<String, CustomTable.Align> headerData = new LinkedHashMap<String, CustomTable.Align>();
//        for (int i = 0; i < headers.length; i++) {
//            String[] headerDataType = headers[i].split(SEPARATOR);
//            headerData.put(headerDataType[0], mapAligns.get(Integer.parseInt(headerDataType[1])));
//        }
//        return headerData;
//    }
//
//    public static LinkedHashMap<String, CustomTable.Align> getHeadersFilterConfig(String headerList) {
//        String[] headers = headerList.split(",");
//        LinkedHashMap<String, CustomTable.Align> headerData = new LinkedHashMap<String, CustomTable.Align>();
//        for (int i = 0; i < headers.length; i++) {
//            String[] headerDataType = headers[i].split(SEPARATOR);
//            headerData.put(headerDataType[0], mapAligns.get(Integer.parseInt(headerDataType[1])));
//        }
//        return headerData;
//    }

    static Page getString(String string, Notification.Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public static String getStringDecrypt(String key, String defaultStr) {
        try {
            String res = EncodeDecodeUtils.decrypt(getStringCas(key));
            if (res == null || "".equals(res)) {
                return defaultStr;
            }
            return res;
        } catch (Exception e) {
            PrintLog.printLog(e);
            return defaultStr;
        }
    }

        
    public static String[] getHeaderColumnName(String prefix, String key){
        String lstColumnsVal = BundleUtils.getStringCas(key);
        String[] lstColumnsKey = lstColumnsVal.split(SEPARATOR);
        String[] lstColumns = new String[lstColumnsKey.length];
        for(int i = 0; i < lstColumnsKey.length; i++){
            lstColumns[i] = BundleUtils.getString(prefix + "." + lstColumnsKey[i]);
        }
        return lstColumns;
    }
    public static String[] getHeaderColumn(String key){
        String lstColumnsVal = BundleUtils.getStringCas(key);
        String[] lstColumnsKey = lstColumnsVal.split(SEPARATOR);
        return lstColumnsKey;
    }
        
    public static String[] getHeaderColumnVisible(String key){
        String lstColumnsVal = BundleUtils.getStringCas(key);
        return lstColumnsVal.split(SEPARATOR);
    }
}
