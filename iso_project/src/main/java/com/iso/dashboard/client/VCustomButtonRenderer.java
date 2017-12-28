/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.client;

/**
 *
 * @author TUYENNV1
 */
public class VCustomButtonRenderer extends VCustomButtonValueRenderer {

    public VCustomButtonRenderer() {
        super(VCustomButtonValueRenderer.ASSIGN_BITM | VCustomButtonValueRenderer.DELETE_BITM | VCustomButtonValueRenderer.EDIT_BITM
                | VCustomButtonValueRenderer.REPORT_BITM | VCustomButtonValueRenderer.VIEW_BITM);
    }

}
