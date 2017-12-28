/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.FlowDAO;
import com.iso.dashboard.dto.CFlow;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class FlowMngtService {
    
    private static FlowMngtService service;

    public static FlowMngtService getInstance() {
        if (service == null) {
            service = new FlowMngtService();
        }
        return service;
    }

    public ResultDTO addCFlow(CFlow et) {
        return FlowDAO.getInstance().addCFlow(et);
    }

    public ResultDTO updateCFlow(CFlow et) {
        return FlowDAO.getInstance().updateCFlow(et);
    }

    public List<CFlow> listCFlows(String key) {
        return FlowDAO.getInstance().listCFlows(key);
    }

    public CFlow getCFlowById(String id) {
        return FlowDAO.getInstance().getCFlowById(String.valueOf(id));
    }

    public ResultDTO removeCFlow(String id) {
        return FlowDAO.getInstance().removeCFlow(id);
    }
}
