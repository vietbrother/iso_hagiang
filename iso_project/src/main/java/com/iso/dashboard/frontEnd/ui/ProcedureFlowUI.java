/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.frontEnd.ui;

import com.iso.dashboard.dto.CStep;
import com.iso.dashboard.dto.MFlowProcedure;
import com.iso.dashboard.dto.MFlowStep;
import com.iso.dashboard.service.FlowProcedureService;
import com.iso.dashboard.service.FlowStepService;
import com.iso.dashboard.service.StepMngtService;
import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcedureFlowUI extends CustomComponent {

    private Label name;
    private TextField orgName;
    private TextField processTime;
    private TextField method;
    private TextField condition;
    private TextField result;
    private TextField costDocument;
    private TextField cost;

    private Component content;

    public ProcedureFlowUI() {
        content = new VerticalLayout();//buildContent(null, "1");
        setCompositionRoot(content);
    }
    public ProcedureFlowUI(String procedureId) {
        content = buildContent(null, procedureId);
        setCompositionRoot(content);
    }
    public ProcedureFlowUI(String flowId, String procedureId) {
        content = buildContent(flowId, procedureId);
        setCompositionRoot(content);
    }

    public Component buildContent() {
        name = new Label(BundleUtils.getString("procedureMngt.public.list.name"), ContentMode.HTML);
        name.setCaption(BundleUtils.getString("procedureMngt.public.list.name"));
        name.addStyleName(ValoTheme.LABEL_BOLD);
        name.addStyleName(ValoTheme.LABEL_COLORED);
        name.addStyleName(ValoTheme.LABEL_H4);
        orgName = createTextField(BundleUtils.getString("procedureMngt.public.list.name"), "");
        processTime = createTextField(BundleUtils.getString("procedureMngt.public.list.processTime"), "");
        method = createTextField(BundleUtils.getString("procedureMngt.public.list.method"), "");
        condition = createTextField(BundleUtils.getString("procedureMngt.public.list.condition"), "");
        result = createTextField(BundleUtils.getString("procedureMngt.public.list.result"), "");
        costDocument = createTextField(BundleUtils.getString("procedureMngt.public.list.costDocument"), "");
        cost = createTextField(BundleUtils.getString("procedureMngt.public.list.cost"), "");

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(name);
        details.addComponent(orgName);
        details.addComponent(processTime);
        details.addComponent(method);
        details.addComponent(condition);
        details.addComponent(result);
        details.addComponent(costDocument);
        details.addComponent(cost);
        return details;
    }

    public Component buildContent(String flowId,String procedureId) {
        List<MFlowProcedure> lstFlow = FlowProcedureService.getInstance().listFlowProcedure(flowId, procedureId);
        List<MFlowStep> flowSteps = new ArrayList<>();
        for (MFlowProcedure map : lstFlow) {
            List<MFlowStep> temp = FlowStepService.getInstance().listTaskAssignee(String.valueOf(map.getFlowId()), null);
            if (temp != null && !temp.isEmpty()) {
                flowSteps.addAll(temp);
            }
        }
        List<CStep> cSteps = new ArrayList<>();
        for (MFlowStep flowStep : flowSteps) {
            CStep cStep = StepMngtService.getInstance().getCStepById(String.valueOf(flowStep.getStepId()));
            cStep.setPosition(String.valueOf(flowStep.getStepIndex()));
            cStep.setBanchId(flowStep.getStepBranch());
            cSteps.add(cStep);
        }
        Collections.sort(cSteps, new Comparator<CStep>() {
            @Override
            public int compare(CStep o1, CStep o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });

        VerticalLayout details = new VerticalLayout();
        details.setCaption("Sơ đồ luồng xử lý");
        details.setMargin(true);
        details.setWidth("100%");
        if (cSteps == null || cSteps.isEmpty()) {
            Notification.show("Chưa có bước thục hiện");
        } else {

            for (int i = 0; i < cSteps.size(); i++) {
                HorizontalLayout hori = new HorizontalLayout();
                hori.setWidth("100%");
                
                hori.addComponent(new Label(""
                        + "<div style=\"position: relative; padding-left: 100px; display: block;box-sizing: border-box;\">\n"
                        + "	<div style=\"position: absolute;\n"
                        + "			top: 0;\n"
                        + "			left: 0;\n"
                        + "			bottom: 0;\n"
                        + "			margin-left: 30px;\n"
                        + "			width: 1px;\n"
                        + "			border-left: rgba(0,0,0,0.1) 4px dashed;\n"
                        + "			height: 100%;\"></div>\n"
                        + "			<div style=\"display: block;\n"
                        + "			margin-bottom: 9px;\n"
                        + "			padding-bottom: 38px;\n"
                        + "			border-bottom: #eee 3px solid;\n"
                        + "			position: relative;\">\n"
                        + "	    <div style=\"border: rgba(0,0,0,0.1) 3px solid;\n"
                        + "			background-color: #fff;\n"
                        + "			padding-top: 24px;\n"
                        + "			top: 20px;\n"
                        + "			z-index: 10;\n"
                        + "			position: absolute;\n"
                        + "			left: -102px;\n"
                        + "			right: auto;\n"
                        + "			width: 64px;\n"
                        + "			height: 47px;\n"
                        + "			font-size: 26px;\n"
                        + "			text-align: center;\n"
                        + "			line-height: 1;\n"
                        + "			color: #ccc;\n"
                        + "			font-weight: bold;\n"
                        + "			font-family: 'Lato','Open Sans',Arial,Helvetica,sans-serif; color:blue;\">\n"
                        + "		    " + (i + 1)
                        + "                 <span style=\"display: block;\n"
                        + "				font-size: 12px;\n"
                        + "				text-transform: uppercase;\n"
                        + "				font-weight: 300;\n"
                        + "				font-family: 'Open Sans',Arial,Helvetica,sans-serif;line-height: 16px !important;\"></span>\n"
                        + "		    <div style=\"position: absolute;\n"
                        + "				top: 50%;\n"
                        + "				margin-top: -1px;\n"
                        + "				right: -40px;\n"
                        + "				width: 40px;\n"
                        + "				height: 0;\n"
                        + "				border-top: 1px dashed #CCC; box-sizing: border-box;\"></div>\n"
                        + "		</div>\n"
                        + "		<h4 style=\"font-family: 'Raleway','Open Sans',Arial,Helvetica,sans-serif;\n"
                        + "				font-weight: 600;\n"
                        + "				color: #414141;\n"
                        + "				font-size: 18px;\n"
                        + "				letter-spacing: normal;\n"
                        + "				margin: 0 0 14px 0;\n"
                        + "				line-height: 1.5;\n"
                        + "				-webkit-font-smoothing: antialiased;\">"
                        + "                 <label>"
                        + "                         "+ cSteps.get(i).getName()
                        + "                 </label></h4>\n"
                        + "		<ul style=\"display: block; padding-bottom: 20px;     padding-left: 0;\n"
                        + "				margin-left: 5px; color: #888;margin-right: 5px;\n"
                        + "				list-style: none; margin-bottom: 10px !important;     border-bottom: 0 !important;\">\n"
                        + "		    <li>\n"
                        + "			<i style=\"display: inline-block;\n"
                        + "				font: normal normal normal 14px/1 FontAwesome;\n"
                        + "				font-size: inherit;\n"
                        + "				text-rendering: auto;\n"
                        + "				-webkit-font-smoothing: antialiased; content: \\f017;\"></i>\n"
//                        + "                     <i class=\"fa fa-clock-o\"></i>\n"
                        + "			<span>Thời gian thực hiện: \n"
                        + "			<span style=\"background-color: #5bc0de; color: #fff;\n"
                        + "					text-align: center;\n"
                        + "					padding: .4em .6em .4em;\n"
                        + "					white-space: nowrap;\n"
                        + "					vertical-align: baseline;\n"
                        + "					border-radius: .25em;     display: inline-block !important; font-size: 11px !important;\n"
                        + "					line-height: 15px !important;\">10 ngày 0 giờ \n"
                        + "			</span>\n"
                        + "				</span>\n"
                        + "		    </li>\n"
                        + "		</ul>\n"
                        + "		<div style=\"margin-bottom-10 margin-top-10 justify\">\n"
                        + "		    \n"
                        + "		</div>\n"
                        + "		</div>\n"
                        + "		\n"
                        + "		\n"
                        + "		\n"
                        + "		\n"
                        + "	</div>", ContentMode.HTML));

                details.addComponent(hori);
            }
        }

        return details;
    }

    public TextField createTextField(String caption, String value) {
        TextField textField = new TextField();
        textField.setImmediate(true);
        textField.setWidth("100.0%");
        textField.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        textField.setDescription(caption);
        textField.setCaption(caption);
        textField.setInputPrompt(caption);
        textField.setValue(DataUtil.isNullOrEmpty(value) ? "undefined" : value);
        return textField;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public TextField getOrgName() {
        return orgName;
    }

    public void setOrgName(TextField orgName) {
        this.orgName = orgName;
    }

    public TextField getProcessTime() {
        return processTime;
    }

    public void setProcessTime(TextField processTime) {
        this.processTime = processTime;
    }

    public TextField getMethod() {
        return method;
    }

    public void setMethod(TextField method) {
        this.method = method;
    }

    public TextField getCondition() {
        return condition;
    }

    public void setCondition(TextField condition) {
        this.condition = condition;
    }

    public TextField getResult() {
        return result;
    }

    public void setResult(TextField result) {
        this.result = result;
    }

    public TextField getCostDocument() {
        return costDocument;
    }

    public void setCostDocument(TextField costDocument) {
        this.costDocument = costDocument;
    }

    public TextField getCost() {
        return cost;
    }

    public void setCost(TextField cost) {
        this.cost = cost;
    }

    public Component getContent() {
        return content;
    }

    public void setContent(Component content) {
        this.content = content;
    }

}
