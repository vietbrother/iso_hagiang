package com.iso.dashboard.view;

import com.iso.dashboard.view.dashboard.DashboardView;
import com.iso.view.reports.ReportsView;
import com.iso.dashboard.view.sales.SalesView;
import com.vaadin.icons.VaadinIcons;
//import com.iso.dashboard.view.transactions.TransactionsView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DashboardViewType {

    DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true, true, false),
    //USER_MNGT("userMngt", UserMngtView.class, FontAwesome.USERS, true, true, false),
    ORGANIZATION_MNGT("organizationMngt", OrganizationMngtView.class, FontAwesome.SITEMAP, true, true, false),
    
    STAFF_MNGT("staffMngt", DashboardView.class, VaadinIcons.CALENDAR_USER, true, true, true),
    STAFF_EMPLOYEE_MNGT("employeeMngt", EmployeeMngtView.class, FontAwesome.NEWSPAPER_O, true, false, false),
    STAFF_JOB_MNGT("jobMngt", JobMngtView.class, FontAwesome.NEWSPAPER_O, true, false, false),
    STAFF_EMPLOYEE_TYPE_MNGT("employeeTypeMngt", EmployeeTypeMngtView.class, FontAwesome.NEWSPAPER_O, true, false, false),
    
    TASK_MNGT("taskMngt", DashboardView.class, VaadinIcons.CALENDAR_CLOCK, true, true, true),
    TASK_GROUP_MNGT("taskGroupMngt", CataTaskGroupMngtView.class, FontAwesome.TASKS, true, false, false),
    TASK_PRIORITY_MNGT("taskPriorityMngt", CataTaskPriorityMngtView.class, FontAwesome.TASKS, true, false, false),
    TASK_ORG_MNGT("taskOrgMngt", TaskOrgMngtView.class, FontAwesome.TASKS, true, false, false),
    TASK_PERSONAL_MNGT("taskPersonalMngt", TaskPersonalMngtView.class, FontAwesome.TASKS, true, false, false),
    //TASK_ASIGN_MNGT("taskAsignMngt", TaskAsignMngtView.class, FontAwesome.TASKS, true, false, false),
    
    ARCHIVES_MNGT("archivesMngt", DashboardView.class, VaadinIcons.CALENDAR_BRIEFCASE, true, true, true),
    ARCHIVES_FILE_MNGT("fileMngt", FileMngtView.class, FontAwesome.FILE_O, true, false, false),
    ARCHIVES_DOCUMENT_MNGT("documentMngt", DocumentMngtView.class, FontAwesome.FILE_O, true, false, false),
    
    PERSONAL_FILE_MNGT("personalFilesMngt", DashboardView.class, VaadinIcons.FILE_TEXT, true, true, true),
    PERSONAL_SEND_FILE_MNGT("sendFileMngt", PersonalSendFileView.class, FontAwesome.FILE_TEXT, true, false, false),
    PERSONAL_FILE_INFO_MNGT("fileInfoMngt", PersonalFileInfoView.class, FontAwesome.FILE_TEXT, true, false, false),
    PERSONAL_SEARCH_FILE_MNGT("searchFileMngt", PersonalFileSearchView.class, FontAwesome.FILE_TEXT, true, false, false),
    
    PROCESS_MNGT("processMngt", DashboardView.class, VaadinIcons.FILE_TREE, true, true, true),
//    GUIDE_MNGT("guideMngt", CataGuideMngtView.class, VaadinIcons.BOOKMARK, true, false, false),
    STEP_MNGT("cataStepMngt", StepMngtView.class, VaadinIcons.BOOKMARK, true, false, false),
    FLOW_MNGT("cataFlowMngt", FlowMngtView.class, VaadinIcons.SHARE, true, false, false),
    PROCEDURE_MNGT("cataProcedureMngt", ProcedureMngtView.class, VaadinIcons.BOOKMARK, true, false, false),
    
    ASSET_MNGT("assetMngt", DashboardView.class, FontAwesome.ARCHIVE, true, true, true),
    ASSET_LIST_MNGT("assetListMngt", AssetMngtView.class, FontAwesome.WRENCH, true, false, false),
    C_UNIT_MNGT("unitMngt", UnitMngtView.class, FontAwesome.WRENCH, true, false, false),
    PROVIDER_MNGT("providerMngt", ProviderMngtView.class, FontAwesome.WRENCH, true, false, false),
    ASSET_TYPE_MNGT("assetTypeMngt", AssetTypeMngtView.class, FontAwesome.WRENCH, true, false, false),
    ASSET_GROUP_MNGT("assetGroupMngt", AssetGroupMngtView.class, FontAwesome.WRENCH, true, false, false),
    ASSET_CLASS_MNGT("assetClassMngt", AssetClassMngtView.class, FontAwesome.WRENCH, true, false, false);
    
//    SALES("sales", SalesView.class, FontAwesome.BAR_CHART_O, false, true, false),
//    REPORTS("reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true, true, false),
//    SCHEDULE("calendar", CalendarMngtView.class, FontAwesome.CALENDAR_O, false, true, false);

    public static Map<String, List<DashboardViewType>> mapSubMenu = new HashMap<>();

    static {
        List<DashboardViewType> lstWorkMngtSubMenu = new ArrayList<>();
        lstWorkMngtSubMenu.add(TASK_GROUP_MNGT);
        lstWorkMngtSubMenu.add(TASK_PRIORITY_MNGT);
        lstWorkMngtSubMenu.add(TASK_ORG_MNGT);
        lstWorkMngtSubMenu.add(TASK_PERSONAL_MNGT);
//        lstWorkMngtSubMenu.add(TASK_ASIGN_MNGT);
        mapSubMenu.put(TASK_MNGT.getViewName(), lstWorkMngtSubMenu);

        List<DashboardViewType> lstArchivesMngtSubMenu = new ArrayList<>();
        lstArchivesMngtSubMenu.add(ARCHIVES_FILE_MNGT);
        lstArchivesMngtSubMenu.add(ARCHIVES_DOCUMENT_MNGT);
        mapSubMenu.put(ARCHIVES_MNGT.getViewName(), lstArchivesMngtSubMenu);

        List<DashboardViewType> lstPersonalFileMngtSubMenu = new ArrayList<>();
        lstPersonalFileMngtSubMenu.add(PERSONAL_SEND_FILE_MNGT);
        lstPersonalFileMngtSubMenu.add(PERSONAL_FILE_INFO_MNGT);
        lstPersonalFileMngtSubMenu.add(PERSONAL_SEARCH_FILE_MNGT);
        mapSubMenu.put(PERSONAL_FILE_MNGT.getViewName(), lstPersonalFileMngtSubMenu);

        List<DashboardViewType> lstStaffMngtSubMenu = new ArrayList<>();
        lstStaffMngtSubMenu.add(STAFF_EMPLOYEE_MNGT);
        lstStaffMngtSubMenu.add(STAFF_JOB_MNGT);
        lstStaffMngtSubMenu.add(STAFF_EMPLOYEE_TYPE_MNGT);
        mapSubMenu.put(STAFF_MNGT.getViewName(), lstStaffMngtSubMenu);

        List<DashboardViewType> lstProcessMngtSubMenu = new ArrayList<>();
        //lstProcessMngtSubMenu.add(GUIDE_MNGT);
        lstProcessMngtSubMenu.add(STEP_MNGT);
        lstProcessMngtSubMenu.add(FLOW_MNGT);
        lstProcessMngtSubMenu.add(PROCEDURE_MNGT);
        mapSubMenu.put(PROCESS_MNGT.getViewName(), lstProcessMngtSubMenu);

        List<DashboardViewType> lstAssetMngtSubMenu = new ArrayList<>();
        lstAssetMngtSubMenu.add(ASSET_LIST_MNGT);
        lstAssetMngtSubMenu.add(C_UNIT_MNGT);
        lstAssetMngtSubMenu.add(PROVIDER_MNGT);
        lstAssetMngtSubMenu.add(ASSET_TYPE_MNGT);
        lstAssetMngtSubMenu.add(ASSET_GROUP_MNGT);
        lstAssetMngtSubMenu.add(ASSET_CLASS_MNGT);
        mapSubMenu.put(ASSET_MNGT.getViewName(), lstAssetMngtSubMenu);

    }

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;
    private final boolean isParentView;
    private final boolean isContainChild;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful,
            boolean isParentView, boolean isContainChild) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
        this.isParentView = isParentView;
        this.isContainChild = isContainChild;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public boolean isParentView() {
        return isParentView;
    }

    public boolean isContainChild() {
        return isContainChild;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
