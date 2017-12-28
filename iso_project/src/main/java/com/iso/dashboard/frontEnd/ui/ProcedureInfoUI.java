/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.frontEnd.ui;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcedureInfoUI extends CustomComponent {

    private TextField txtName;
    private TextField txtType;
    private TextField txtOrgName;
    private TextField txtProcessTime;
    private TextField txtMethod;
    private TextField txtCondition;
    private TextField txtResult;
    private TextField txtCostDocument;
    private TextField txtCost;

    public ProcedureInfoUI() {
        setCompositionRoot(buildContent());
    }

    public Component buildContent() {
        txtName = createTextField(BundleUtils.getString("procedureMngt.public.list.name"), "");
        txtName.addStyleName("name-procedure");
        
        txtOrgName = createTextField(BundleUtils.getString("procedureMngt.public.list.orgName"), "");
        txtType = createTextField(BundleUtils.getString("procedureMngt.public.list.type"), "");
        txtProcessTime = createTextField(BundleUtils.getString("procedureMngt.public.list.processTime"), "");
        txtMethod = createTextField(BundleUtils.getString("procedureMngt.public.list.method"), "");
        txtCondition = createTextField(BundleUtils.getString("procedureMngt.public.list.condition"), "");
        txtResult = createTextField(BundleUtils.getString("procedureMngt.public.list.result"), "");
        txtCostDocument = createTextField(BundleUtils.getString("procedureMngt.public.list.costDocument"), "");
        txtCost = createTextField(BundleUtils.getString("procedureMngt.public.list.cost"), "");
        
        FormLayout details = new FormLayout();
        details.setMargin(true);
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        details.addComponent(txtName);
        details.addComponent(txtOrgName);
        details.addComponent(txtProcessTime);
        details.addComponent(txtMethod);
        details.addComponent(txtCondition);
        details.addComponent(txtResult);
        details.addComponent(txtCostDocument);
        details.addComponent(txtCost);
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

    public TextField getTxtName() {
        return txtName;
    }

    public void setTxtName(TextField txtName) {
        this.txtName = txtName;
    }

    
    public TextField getTxtOrgName() {
        return txtOrgName;
    }

    public void setTxtOrgName(TextField txtOrgName) {
        this.txtOrgName = txtOrgName;
    }

    public TextField getTxtProcessTime() {
        return txtProcessTime;
    }

    public void setTxtProcessTime(TextField txtProcessTime) {
        this.txtProcessTime = txtProcessTime;
    }

    public TextField getTxtMethod() {
        return txtMethod;
    }

    public void setTxtMethod(TextField txtMethod) {
        this.txtMethod = txtMethod;
    }

    public TextField getTxtCondition() {
        return txtCondition;
    }

    public void setTxtCondition(TextField txtCondition) {
        this.txtCondition = txtCondition;
    }

    public TextField getTxtResult() {
        return txtResult;
    }

    public void setTxtResult(TextField txtResult) {
        this.txtResult = txtResult;
    }

    public TextField getTxtCostDocument() {
        return txtCostDocument;
    }

    public void setTxtCostDocument(TextField txtCostDocument) {
        this.txtCostDocument = txtCostDocument;
    }

    public TextField getTxtCost() {
        return txtCost;
    }

    public void setTxtCost(TextField txtCost) {
        this.txtCost = txtCost;
    }

    public TextField getTxtType() {
        return txtType;
    }

    public void setTxtType(TextField txtType) {
        this.txtType = txtType;
    }

    
    
}
