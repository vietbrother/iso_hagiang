/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;


public class MakeURL {

    public static String makeURLForGrid(String caption) {

        String URL = "<button class=\"caption\" onclick=\"myFunction(this)\">"
                + "<img src=\"VAADIN/themes/dashboard/img/navigate_close.png\">  "
                + caption
                + "  </button>";
        return URL;
    }
    public static String makeURLForVerticalLayout(String caption) {

        String URL = "<button class=\"caption\" onclick=\"myFunctionVerticalLayout(this)\">"
                + "<img src=\"VAADIN/themes/dashboard/img/navigate_close.png\">  "
                + caption
                + "  </button>";
        return URL;
    }

    public static String makeURLForGrid(String caption, String reloadFunction) {

        String URL = "<button class=\"caption\" onclick=\"myFunctionReload(this," + reloadFunction + ")\">"
                + "<img src=\"VAADIN/themes/dashboard/img/navigate_close.png\">  "
                + caption
                + "  </button>";
        return URL;
    }
    public static String makeURLForCssLayout(String caption) {

        String URL = "<button class=\"caption\" onclick=\"myFunctionCssLayout(this)\">"
                + "<img src=\"VAADIN/themes/dashboard/img/navigate_close.png\">  "
                + caption
                + "  </button>";
        return URL;
    }

    public static String makeURLForCssLayout(String caption, String reloadFunction) {

        String URL = "<button class=\"caption\" onclick=\"myFunctionReloadCssLayout(this," + reloadFunction + ")\">"
                + "<img src=\"VAADIN/themes/dashboard/img/navigate_close.png\">  "
                + caption
                + "  </button>";
        return URL;
    }


    public static String makeURLForTable(String caption) {
        String URL = "<button class=\"caption\" onclick=\"myFunctionTable(this)\">"
                + "<img src=\"VAADIN/themes/dashboard/img/navigate_close.png\">   "
                + caption
                + " </button>";
        return URL;
    }
    public static String makeURLForTable(String caption, String reloadFuction) {
        String URL = "<button class=\"caption\" onclick=\"myFunctionReloadTable(this," + reloadFuction + ")\">"
                + "<img src=\"VAADIN/themes/dashboard/img/navigate_close.png\">   "
                + caption
                + " </button>";
        return URL;
    }

}
