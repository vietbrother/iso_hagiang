/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.ui.process;

import com.iso.dashboard.utils.BundleUtils;
import com.iso.dashboard.utils.Constants;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author VIET_BROTHER
 */
public class ActionFactoryUI extends CustomComponent {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    
    private Component mainView;

    public ActionFactoryUI() {
    }
    
    
    public Component buildComponent(){
        mainView = new VerticalLayout();
        
        return mainView;
    }

    public HorizontalLayout buildButtonsForm() {
        HorizontalLayout buttonForm = new HorizontalLayout();
        buttonForm.setHeight(Constants.STYLE_CONF.AUTO_VALUE);
        buttonForm.setSpacing(true);
        buttonForm.addStyleName("toolbar");
        Responsive.makeResponsive(buttonForm);

        btn1 = new Button(BundleUtils.getString("flowStep.function1"));
        btn2 = new Button(BundleUtils.getString("flowStep.function2"));
        btn3 = new Button(BundleUtils.getString("flowStep.function3"));
        btn4 = new Button(BundleUtils.getString("flowStep.function4"));
        btn5 = new Button(BundleUtils.getString("flowStep.function5"));
        btn6 = new Button(BundleUtils.getString("flowStep.function6"));
        btn7 = new Button(BundleUtils.getString("flowStep.function7"));
        btn8 = new Button(BundleUtils.getString("flowStep.function8"));
        btn9 = new Button(BundleUtils.getString("flowStep.function9"));
        btn10 = new Button(BundleUtils.getString("flowStep.function10"));
        btn11 = new Button(BundleUtils.getString("flowStep.function11"));
        btn12 = new Button(BundleUtils.getString("flowStep.function12"));
        
        buttonForm.addComponents(btn1, btn2,
                btn3, btn4,
                btn5, btn6,
                btn7, btn8,
                btn9, btn10,
                btn11, btn12);

        return buttonForm;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }

    public Button getBtn2() {
        return btn2;
    }

    public void setBtn2(Button btn2) {
        this.btn2 = btn2;
    }

    public Button getBtn3() {
        return btn3;
    }

    public void setBtn3(Button btn3) {
        this.btn3 = btn3;
    }

    public Button getBtn4() {
        return btn4;
    }

    public void setBtn4(Button btn4) {
        this.btn4 = btn4;
    }

    public Button getBtn5() {
        return btn5;
    }

    public void setBtn5(Button btn5) {
        this.btn5 = btn5;
    }

    public Button getBtn6() {
        return btn6;
    }

    public void setBtn6(Button btn6) {
        this.btn6 = btn6;
    }

    public Button getBtn7() {
        return btn7;
    }

    public void setBtn7(Button btn7) {
        this.btn7 = btn7;
    }

    public Button getBtn8() {
        return btn8;
    }

    public void setBtn8(Button btn8) {
        this.btn8 = btn8;
    }

    public Button getBtn9() {
        return btn9;
    }

    public void setBtn9(Button btn9) {
        this.btn9 = btn9;
    }

    public Button getBtn10() {
        return btn10;
    }

    public void setBtn10(Button btn10) {
        this.btn10 = btn10;
    }

    public Button getBtn11() {
        return btn11;
    }

    public void setBtn11(Button btn11) {
        this.btn11 = btn11;
    }

    public Button getBtn12() {
        return btn12;
    }

    public void setBtn12(Button btn12) {
        this.btn12 = btn12;
    }

}
