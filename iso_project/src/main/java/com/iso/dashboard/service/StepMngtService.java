/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.StepDAO;
import com.iso.dashboard.dto.CStep;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class StepMngtService {
    
    private static StepMngtService service;

    public static StepMngtService getInstance() {
        if (service == null) {
            service = new StepMngtService();
        }
        return service;
    }

    public ResultDTO addCStep(CStep et) {
        return StepDAO.getInstance().addCStep(et);
    }

    public ResultDTO updateCStep(CStep et) {
        return StepDAO.getInstance().updateCStep(et);
    }

    public List<CStep> listCSteps(String key) {
        return StepDAO.getInstance().listCSteps(key);
    }

    public CStep getCStepById(String id) {
        return StepDAO.getInstance().getCStepById(String.valueOf(id));
    }

    public ResultDTO removeCStep(String id) {
        return StepDAO.getInstance().removeCStep(id);
    }
}
