/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.controller;

import com.iso.dashboard.component.CustomGrid;
import com.iso.dashboard.dto.CMenu;
import com.iso.dashboard.dto.CatItemDTO;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.dto.EmployeeType;
import com.iso.dashboard.dto.Job;
import com.iso.dashboard.dto.MUserMenu;
import com.iso.dashboard.dto.Organization;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.service.EmployeeMngtService;
import com.iso.dashboard.service.EmployeeTypeMngtService;
import com.iso.dashboard.service.JobMngtService;
import com.iso.dashboard.service.MenuMgntService;
import com.iso.dashboard.service.UserMenuService;
import com.iso.dashboard.ui.EmployeeMngtUI;
import com.iso.dashboard.ui.OrgTreeSearchUI;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.CommonExport;
import com.iso.dashboard.utils.ComponentUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.DateUtil;
import com.iso.dashboard.utils.ISOIcons;
import com.iso.dashboard.view.EmployeeMngtView;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeMngtController {

    EmployeeMngtView view;
    EmployeeMngtService service;

    CustomGrid pagedTable;
    Tree orgTree;

//    String[] headerName = new String[]{"Id", "Username", "Email", "Phone", ""};
    String prefix = "employeeMngt.list";//tien to trong file language
    // header.jobMngt=id#employeeCode#employeeName#birthday#email#mobile#jobTitle#department#action
    String headerKey = "header.employeeMngt";//lay trong file cas 
    String[] headerName = BundleUtils.getHeaderColumnName(prefix, headerKey);
    String[] headerColumn = BundleUtils.getHeaderColumn(headerKey);
    String employeeListLabel = "employeeMngt.list";
    Resource resource;

    Organization orgSelected = null;

    public EmployeeMngtController(EmployeeMngtView view) {
        this.view = view;
        this.pagedTable = view.getPagedTable();
        this.orgTree = view.getOrgTree();
        //initTable(EmployeeMngtService.getInstance().listEmployees(orgSelected == null ? null : orgSelected.getId()));
        initTable(EmployeeMngtService.getInstance().getEmployeeByOrganizationId(orgSelected == null ? null : String.valueOf(orgSelected.getId())));
        OrgTreeSearchUI.initTree(orgTree);
        doAction();
    }

    public void initTable(List<Employee> lstEmployees) {
//        pagedTable.addGeneratedColumn("btnAction", new Table.ColumnGenerator() {
//
//            public Component generateCell(Table source, Object itemId, Object columnId) {
//                Item item = source.getItem(itemId);
//                
//                // Edit Action
//                Button btnEdit = new Button();
//                btnEdit.setIcon(FontAwesome.EDIT);
//                btnEdit.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnEdit.setDescription(BundleUtils.getString("common.button.edit"));
//                btnEdit.addClickListener(new Button.ClickListener() {
//                    
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        
//                        String employeeId = (String) item.getItemProperty("id").getValue();
//                        Notification.show("Edit " + employeeId);
//                        Employee dto = EmployeeMngtService.getInstance().getEmployeeById(employeeId);
//                        onUpdate(dto);
//                        view.getBtnSearch().click();
//                    }
//                });
//                
//                // Delete Action
//                Button btnDelete = new Button();
//                btnDelete.setIcon(ISOIcons.DELETE);
//                btnDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
//                btnDelete.setDescription(BundleUtils.getString("common.button.delete"));
//                btnDelete.addClickListener(new Button.ClickListener() {
//                    
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        ConfirmDialog d = ConfirmDialog.show(
//                                UI.getCurrent(),
//                                BundleUtils.getString("message.warning.title"),
//                                BundleUtils.getString("message.warning.content"),
//                                BundleUtils.getString("common.confirmDelete.yes"),
//                                BundleUtils.getString("common.confirmDelete.no"),
//                                new ConfirmDialog.Listener() {
//                            
//                            public void onClose(ConfirmDialog dialog) {
//                                if (dialog.isConfirmed()) {
//                                    // Confirmed to continue
//                                    String employeeId = (String) item.getItemProperty("id").getValue();
//                                    ResultDTO res = JobMngtService.getInstance().removeJob(employeeId);
//                                    ComponentUtils.showNotification("Delete id : " + employeeId + " " + res.getKey() + " " + res.getMessage());
//                                    view.getBtnSearch().click();
//                                }
//                            }
//                        });
//                        d.setStyleName(Reindeer.WINDOW_LIGHT);
//                        d.setContentMode(ConfirmDialog.ContentMode.HTML);
//                        d.getOkButton().setIcon(ISOIcons.SAVE);
//                        d.getCancelButton().setIcon(ISOIcons.CANCEL);
//                    }
//                });
//                
//                HorizontalLayout hori = new HorizontalLayout();
//                hori.addComponent(btnEdit);
//                hori.addComponent(btnDelete);
//                return hori;
//            }
//        });
//        reloadData(lstEmployees);
//        pagedTable.setSizeFull();
//        //pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
////        pagedTable.setWidth("1000px");
//        pagedTable.setPageLength(10);
//        pagedTable.setImmediate(true);
//        pagedTable.setSelectable(true);
//        pagedTable.setAlwaysRecalculateColumnWidths(true);
//        pagedTable.setResponsive(true);
//        pagedTable.setColumnHeaders(headerName);
        IndexedContainer container = createContainer(lstEmployees);
        pagedTable.genGrid(container, prefix, headerColumn, null, new HandlerButtonActionGrid() {

            @Override
            public void actionEdit(Object obj) {
                Employee item = (Employee) obj;
                String employeeId = String.valueOf(item.getId());
                Notification.show("Edit " + employeeId);
                Employee dto = EmployeeMngtService.getInstance().getEmployeeById(employeeId);
                onUpdate(dto);
                view.getBtnSearch().click();
            }

            @Override
            public void actionDelete(Object obj) {
                ConfirmDialog d = ConfirmDialog.show(UI.getCurrent(),
                        BundleUtils.getString("message.warning.title"),
                        BundleUtils.getString("message.warning.content"),
                        BundleUtils.getString("common.confirmDelete.yes"),
                        BundleUtils.getString("common.confirmDelete.no"),
                        new ConfirmDialog.Listener() {

                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    // Confirmed to continue
                                    Employee item = (Employee) obj;
                                    String employeeId = String.valueOf(item.getId());
                                    ResultDTO res = JobMngtService.getInstance().removeJob(employeeId);
                                    ComponentUtils.showNotification("Delete id : " + employeeId + " " + res.getKey() + " " + res.getMessage());
                                    view.getBtnSearch().click();
                                }
                            }
                        });
                d.setStyleName(Reindeer.WINDOW_LIGHT);
                d.setContentMode(ConfirmDialog.ContentMode.HTML);
                d.getOkButton().setIcon(ISOIcons.SAVE);
                d.getCancelButton().setIcon(ISOIcons.CANCEL);
            }

            @Override
            public void actionSelect(Object obj) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        pagedTable.setColumnResizeMode(ColumnResizeMode.SIMPLE);
    }

    public void reloadData(List<Employee> lstEmployees) {
        pagedTable.setContainerDataSource(pagedTable.createWrapContainer(createContainer(lstEmployees)));
    }

    public IndexedContainer createContainer(List<Employee> lstEmployees) {
        IndexedContainer container = new IndexedContainer();
        // Table title
        container.addContainerProperty("btnAction", String.class, null);
        container.addContainerProperty("id", String.class, null);
        container.addContainerProperty("employeeCode", String.class, null);
        container.addContainerProperty("employeeName", String.class, null);
        container.addContainerProperty("birthday", String.class, null);
        container.addContainerProperty("email", String.class, null);
        container.addContainerProperty("mobile", String.class, null);
        container.addContainerProperty("jobTitle", String.class, null);
        container.addContainerProperty("department", String.class, null);
        // Table data grid
        for (Employee j : lstEmployees) {
            Item item = container.addItem(j);
            item.getItemProperty("id").setValue(String.valueOf(j.getId()));
            item.getItemProperty("employeeCode").setValue(j.getEmployeeCode());
            item.getItemProperty("employeeName").setValue(j.getFirstName());
            item.getItemProperty("birthday").setValue(j.getBirthday().toString());
            item.getItemProperty("email").setValue(j.getEmail());
            item.getItemProperty("mobile").setValue(j.getMobile());
            item.getItemProperty("jobTitle").setValue(j.getJob().getJobTitle());
            item.getItemProperty("department").setValue(j.getDepartment().getName());
        }
        container.sort(new Object[]{"id"}, new boolean[]{true});
        return container;
    }

    private void doAction() {
        view.getBtnSearch().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onSearch();
            }
        });

        view.getBtnAdd().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onInsert();
            }
        });

        view.getBtnExport().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                onExport();
            }
        });

        orgTree.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                Organization org = (Organization) event.getItemId();
                orgSelected = org;
                Integer orgId = org.getId();
                List<Employee> lstEmployees = EmployeeMngtService.getInstance().getEmployeeByOrganizationId(orgId == -1 ? null : String.valueOf(orgId));
                Notification.show("lstEmployees : " + lstEmployees.size());
                reloadData(lstEmployees);
            }
        });

    }

    public boolean validateData(EmployeeMngtUI ui) {
        if (DataUtil.isNullOrEmpty(ui.getTxtEmployeeCode().getValue())) {
            Notification.show(BundleUtils.getString("employeeMngt.list.employeeCode") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            ui.getTxtEmployeeCode().focus();
            return false;
        }
        if (ui.getTxtEmployeeCode().getValue().length() > 20) {
            ui.getTxtEmployeeCode().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.employeeCode") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.20"));
            return false;
        }
        if (DataUtil.isNullOrEmpty(ui.getTxtEmployeeName().getValue())) {
            ui.getTxtEmployeeName().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.employeeName") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtEmployeeName().getValue().length() > 100) {
            ui.getTxtEmployeeName().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.employeeName") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.100"));
            return false;
        }
        if (DataUtil.isNullOrEmpty(ui.getTxtEmail().getValue())) {
            ui.getTxtEmail().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.email") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getTxtEmail().getValue().length() > 100) {
            ui.getTxtEmail().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.email") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.100"));
            return false;
        }
        if (DataUtil.isNullOrEmpty(ui.getTxtMobile().getValue())) {
            ui.getTxtMobile().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.mobile") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }

        if (ui.getTxtMobile().getValue().length() > 100) {
            ui.getTxtMobile().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.mobile") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.100"));
            return false;
        }

        if (DataUtil.isNullOrEmpty(ui.getPdBirthday().getValue())) {
            ui.getPdBirthday().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.birthday") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (DataUtil.isNullOrEmpty(ui.getUsername().getValue())) {
            ui.getUsername().focus();
            Notification.show(BundleUtils.getString("login.username") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getUsername().getValue().length() > 100) {
            ui.getUsername().focus();
            Notification.show(BundleUtils.getString("login.username") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.100"));
            return false;
        }
        if (DataUtil.isNullOrEmpty(ui.getPassword().getValue())) {
            ui.getPassword().focus();
            Notification.show(BundleUtils.getString("login.password") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        if (ui.getPassword().getValue().length() > 100) {
            ui.getPassword().focus();
            Notification.show(BundleUtils.getString("login.password") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.maxlength.100"));
            return false;
        }
        Job job = (Job) ui.getCmbJobTitle().getValue();
        if (job == null || job.getId() == 0) {
            ui.getCmbJobTitle().focus();
            Notification.show(BundleUtils.getString("employeeMngt.list.jobTitle") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        EmployeeType employeeType = (EmployeeType) ui.getCmbEmployeeType().getValue();
        if (employeeType == null || employeeType.getId() == 0) {
            ui.getCmbEmployeeType().focus();
            Notification.show(BundleUtils.getString("employeeProcessMngt.list.employeeType") + Constants.SPACE_CHARACTER + BundleUtils.getString("common.notnull"));
            return false;
        }
        return true;
    }

    private void onInsert() {
        if (orgSelected == null) {
            Notification.show(BundleUtils.getString("orgMngt.validate.nodeIsEmpty"));
            return;
        }
        createDialog(true, new Employee());
    }

    private void onUpdate(Employee dto) {
        createDialog(false, dto);
    }

    private void onSearch() {
        List<Employee> lstEmployees = EmployeeMngtService.getInstance().listEmployees(view.getTxtEmployeeType().getValue());
        Notification.show("lstEmployees : " + lstEmployees.size());
        reloadData(lstEmployees);
    }

    private void onExport() {

        try {
            List<Employee> lstEmployeeTypes = EmployeeMngtService.getInstance().listEmployees(view.getTxtEmployeeType().getValue());
            String[] header = new String[]{"export_01", "export_02", "export_03"};
            String[] align = new String[]{"LEFT", "LEFT", "LEFT"};
            List<AbstractMap.SimpleEntry<String, String>> headerAlign = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
            for (int i = 0; i < header.length; i++) {
                headerAlign.add(new AbstractMap.SimpleEntry(header[i], align[i]));
            }
            String fileTemplate = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                    //+ File.separator + "WEB-INF"
                    //+ File.separator + "templates"
                    //+ File.separator + "incident"
                    //+ File.separator + "TEMPLATE_EXPORT.xls"
                    + Constants.FILE_CONF.PATH_EXPORT_TEMPLATE_XLSX;

            String subTitle = Constants.EMPTY_CHARACTER;

            File fileExport = CommonExport.exportFile(lstEmployeeTypes,//list du lieu
                    headerAlign,//header
                    //"userMngt.list",//header prefix
                    employeeListLabel,//header prefix
                    fileTemplate,//path template
                    BundleUtils.getString("userMngt.fileName.export"),//fileName out
                    7,//start row
                    subTitle,//sub title
                    4,//cell title Index
                    BundleUtils.getString("userMngt.report")//title
            );
            resource = new FileResource(fileExport);

            Page.getCurrent().open(resource, null, false);
        } catch (Exception e) {
        }
    }

    private void initDataDialog(EmployeeMngtUI ui, boolean isInsert, Employee dto) {

        List<CatItemDTO> lstSex = new ArrayList<>();
        lstSex.add(new CatItemDTO(Constants.ACTIVE, BundleUtils.getString("userMngt.list.sex.male")));
        lstSex.add(new CatItemDTO(Constants.DEACTIVE, BundleUtils.getString("userMngt.list.sex.female")));
        List<CatItemDTO> lstStatus = new ArrayList<>();
        lstStatus.add(new CatItemDTO(Constants.ACTIVE, BundleUtils.getString("common.active")));
        lstStatus.add(new CatItemDTO(Constants.DEACTIVE, BundleUtils.getString("common.inActive")));

        //filling the combobox with User_'s by BeanContainer
        BeanItemContainer<CMenu> beanContainerFunction = new BeanItemContainer<CMenu>(CMenu.class);
        List<CMenu> lstMenu = MenuMgntService.getInstance().listCMenu(null);
        beanContainerFunction.addAll(lstMenu);
        ui.getViewFunction().getOgMain().setContainerDataSource(beanContainerFunction);
        ui.getViewFunction().getOgMain().setItemCaptionPropertyId("name");
        ui.getViewFunction().getOgMain().setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);

        List<Job> lstJob = JobMngtService.getInstance().listJobs(null);
        List<EmployeeType> lstEmployeeTypes = EmployeeTypeMngtService.getInstance().listEmployeeTypes(null);

        if (isInsert) {
            ComponentUtils.fillDataComboNoDefault(ui.getCmbSex(), Constants.EMPTY_CHARACTER, lstSex);
            ComponentUtils.fillDataComboNoDefault(ui.getCmbIsActive(), Constants.EMPTY_CHARACTER, lstStatus);
            ComponentUtils.fillDataObjectCombo(ui.getCmbJobTitle(), "-1", "", lstJob,
                    Job.class, "id", "jobTitle");
            ComponentUtils.fillDataObjectCombo(ui.getCmbEmployeeType(), "-1", "", lstEmployeeTypes,
                    EmployeeType.class, "id", "employeeType");
            ui.getDetailsWrapper().setVisible(false);
        } else {
            ui.getDetailsWrapper().setVisible(true);
            ui.getTxtEmployeeCode().setValue(dto.getEmployeeCode() == null ? "" : dto.getEmployeeCode());
            ui.getTxtEmployeeCode().setReadOnly(true);
            ui.getTxtEmployeeName().setValue(dto.getFirstName() == null ? "" : (dto.getFirstName()));
            ui.getTxtEmployeeName().setReadOnly(true);
            ui.getPdBirthday().setValue(dto.getBirthday());
            ui.getTxtEmail().setValue(dto.getEmail() == null ? "" : dto.getEmail());
            ui.getTxtMobile().setValue(dto.getMobile() == null ? "" : dto.getMobile());
            ui.getTxtAddress().setValue(dto.getAddress() == null ? "" : dto.getAddress());
            ui.getTxtDepartment().setValue(dto.getDepartment().getName() == null ? "" : dto.getDepartment().getName());
            ui.getUsername().setValue(dto.getUserName() == null ? "" : dto.getUserName());
            ui.getPassword().setValue(dto.getPassword() == null ? "" : dto.getPassword());
//            ui.getTxtJobTitle().setValue(dto.getJob().getJobTitle() == null ? "" : dto.getJob().getJobTitle());
            //ComponentUtils.fillDataCombo(ui.getCmbSex(), Constants.EMPTY_CHARACTER, String.valueOf(dto.isMale()), lstSex);
            ComponentUtils.fillDataObjectCombo(ui.getCmbSex(), "-1", String.valueOf(dto.isMale()), lstSex,
                    CatItemDTO.class, "itemId", "itemName");
            ComponentUtils.fillDataObjectCombo(ui.getCmbIsActive(), "-1", String.valueOf(dto.getIsActive()), lstStatus,
                    CatItemDTO.class, "itemId", "itemName");
            ComponentUtils.fillDataObjectCombo(ui.getCmbJobTitle(), "-1", String.valueOf(dto.getJob().getId()), lstJob,
                    Job.class, "id", "jobTitle");
            ComponentUtils.fillDataObjectCombo(ui.getCmbEmployeeType(), "-1", String.valueOf(dto.getEmployeeType().getId()), lstEmployeeTypes,
                    EmployeeType.class, "id", "employeeType");
        }
    }

    public void createDialog(boolean isInsert, Employee dto) {
        EmployeeMngtUI ui = new EmployeeMngtUI(isInsert ? BundleUtils.getString("common.button.add") : BundleUtils.getString("common.button.edit"));
        Window window = new Window(
                "",
                ui);
        //window.setWidth("700px");
        float height = UI.getCurrent().getWidth() * 5 / 6;
        window.setWidth(String.valueOf(height) + "%");
        window.setHeight(isInsert ? "-1" : "92%");
//        window.setIcon(VaadinIcons.CALENDAR_USER);
        initDataDialog(ui, isInsert, dto);
        ui.getBtnSave().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean validate = validateData(ui);
                if (validate) {
                    ConfirmDialog d = ConfirmDialog.show(
                            UI.getCurrent(),
                            BundleUtils.getString("message.warning.title"),
                            BundleUtils.getString("message.warning.content"),
                            BundleUtils.getString("common.confirmDelete.yes"),
                            BundleUtils.getString("common.confirmDelete.no"),
                            new ConfirmDialog.Listener() {

                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        // Confirmed to continue
                                        ResultDTO res = null;
                                        getDataFromUI(ui, dto);

                                        if (isInsert) {
                                            dto.setDepartment(orgSelected);
                                            res = EmployeeMngtService.getInstance().addEmployee(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.add") + " " + res.getKey() + " " + res.getMessage());
                                            if (Constants.SUCCESS.equals(res.getKey())) {
                                                for (CMenu menuId : (Set<CMenu>) ui.getViewFunction().getOgMain().getValue()) {
                                                    UserMenuService.getInstance().addUserMenu(new MUserMenu(Integer.valueOf(res.getId()), menuId));
                                                }
                                            }

                                        } else {
                                            res = EmployeeMngtService.getInstance().updateEmployee(dto);
                                            ComponentUtils.showNotification(BundleUtils.getString("common.button.update") + " "
                                                    + res.getKey() + " " + res.getMessage());
                                            if (Constants.SUCCESS.equals(res.getKey())) {
                                                UserMenuService.getInstance().removeMenuByUserId(String.valueOf(dto.getId()));
                                                for (CMenu menuId : (Set<CMenu>) ui.getViewFunction().getOgMain().getValue()) {
                                                    UserMenuService.getInstance().addUserMenu(new MUserMenu(Integer.valueOf(dto.getId()), menuId));
                                                }
                                            }

                                        }
                                        window.close();
                                        view.getBtnSearch().click();
                                    } else {
                                        // User did not confirm
                                        Notification.show("nok");
                                        window.close();
                                    }
                                }
                            });
                    d.setStyleName(Reindeer.LAYOUT_BLUE);
                    d.setContentMode(ConfirmDialog.ContentMode.HTML);
                    d.getOkButton().setIcon(ISOIcons.SAVE);
                    d.getOkButton().focus();
                    d.getCancelButton().setIcon(ISOIcons.CANCEL);
                }
            }

        });
        ui.getBtnCancel().addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                window.close();
            }
        });

        ui.setWidth("100%");
        ui.setHeight(Constants.STYLE_CONF.AUTO_VALUE);

        window.setModal(true);
        DataUtil.reloadWindow(window);
        UI.getCurrent().addWindow(window);
        ui.getTxtEmployeeCode().focus();
    }

    private void getDataFromUI(EmployeeMngtUI ui, Employee dto) {
//        dto.setEmployeeCode(ui.getTxtEmployeeCode().getValue().trim());
        ui.getUsername().setReadOnly(false);
        dto.setUserName(ui.getUsername().getValue());
        ui.getUsername().setReadOnly(true);

        ui.getTxtEmployeeCode().setReadOnly(false);
        dto.setEmployeeCode(ui.getTxtEmployeeCode().getValue().trim());
        ui.getTxtEmployeeCode().setReadOnly(true);

        ui.getTxtEmployeeName().setReadOnly(false);
        dto.setFirstName(ui.getTxtEmployeeName().getValue().trim());
        dto.setLastName(ui.getTxtEmployeeName().getValue().trim());
        ui.getTxtEmployeeName().setReadOnly(true);

        Job job = (Job) ui.getCmbJobTitle().getValue();
        if (job != null && !DataUtil.isStringNullOrEmpty(job.getId()) && !Constants.DEFAULT_VALUE.equals(job.getId())) {
            dto.setJob(job);
        }
        EmployeeType employeeType = (EmployeeType) ui.getCmbEmployeeType().getValue();
        if (employeeType != null && !DataUtil.isStringNullOrEmpty(employeeType.getId()) && !Constants.DEFAULT_VALUE.equals(employeeType.getId())) {
            dto.setEmployeeType(employeeType);
        }
        dto.setBirthday(ui.getPdBirthday().getValue());
        dto.setEmail(ui.getTxtEmail().getValue().trim());
        dto.setMobile(ui.getTxtMobile().getValue().trim());
        dto.setAddress(ui.getTxtAddress().getValue());
        dto.setPassword(ui.getPassword().getValue());
        CatItemDTO sex = (CatItemDTO) ui.getCmbSex().getValue();
        if (sex != null && !DataUtil.isStringNullOrEmpty(sex.getItemId()) && !Constants.DEFAULT_VALUE.equals(sex.getItemId())) {
            dto.setMale(Boolean.getBoolean(sex.getItemId()));
        }
        CatItemDTO active = (CatItemDTO) ui.getCmbIsActive().getValue();
        if (active != null && !DataUtil.isStringNullOrEmpty(active.getItemId()) && !Constants.DEFAULT_VALUE.equals(sex.getItemId())) {
            dto.setIsActive(Integer.valueOf(sex.getItemId()));
        }
    }

}
