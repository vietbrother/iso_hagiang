/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.client;

import com.iso.dashboard.component.render.CustomButtonValueRenderer;
import com.vaadin.shared.ui.Connect;

/**
 *
 * @author TUYENNV1
 */
@Connect(CustomButtonValueRenderer.class)
public class VCustomButtonValueRendererConnector extends AbstractButtonValueConnector<VCustomButtonRenderer> {
    private static final long serialVersionUID = 2809348195368552095L;
}
