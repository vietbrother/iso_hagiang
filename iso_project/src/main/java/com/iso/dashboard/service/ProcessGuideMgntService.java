/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ProcessGuideDAO;
import com.iso.dashboard.dto.CProcessGuide;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcessGuideMgntService {

    private static ProcessGuideMgntService service;

    public static ProcessGuideMgntService getInstance() {
        if (service == null) {
            service = new ProcessGuideMgntService();
        }
        return service;
    }

    public ResultDTO addProcessGuide(CProcessGuide p) {
        return ProcessGuideDAO.getInstance().addProcessGuide(p);
    }

    public ResultDTO updateProcessGuide(CProcessGuide p) {
        return ProcessGuideDAO.getInstance().updateProcessGuide(p);
    }

    public List<CProcessGuide> getProcessGuides() {
        return ProcessGuideDAO.getInstance().getProcessGuides();
    }

    public CProcessGuide getProcessGuideById(String id) {
        return ProcessGuideDAO.getInstance().getProcessGuideById(String.valueOf(id));
    }

    public ResultDTO removeProcessGuide(String id) {
        return ProcessGuideDAO.getInstance().removeProcessGuide(id);
    }

}
