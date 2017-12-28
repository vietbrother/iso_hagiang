/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.FlowProcedureDAO;
import com.iso.dashboard.dto.MFlowProcedure;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class FlowProcedureService {

    private static FlowProcedureService service;

    public static FlowProcedureService getInstance() {
        if (service == null) {
            service = new FlowProcedureService();
        }
        return service;
    }

    public ResultDTO addFlowProcedure(MFlowProcedure p) {
        return FlowProcedureDAO.getInstance().addFlowProcedure(p);
    }

    public ResultDTO updateFlowProcedure(MFlowProcedure p) {
        return FlowProcedureDAO.getInstance().updateFlowProcedure(p);
    }

    public List<MFlowProcedure> listFlowProcedure(String taskId, String assignedId) {
        return FlowProcedureDAO.getInstance().getFlowProcedure(taskId, assignedId);
    }

    public MFlowProcedure getFlowProcedureById(String id) {
        return FlowProcedureDAO.getInstance().getFlowProcedureById(id);
    }

    public ResultDTO removeFlowProcedure(String id) {
        return FlowProcedureDAO.getInstance().removeFlowProcedure(id);
    }
    
    public void removeProcedureByFlowId(String flowId){
        FlowProcedureDAO.getInstance().removeProcedureByFlow(flowId);
    }
    public void removeProcedureByProcedureId(String procedureId){
        FlowProcedureDAO.getInstance().removeProcedureByProcedure(procedureId);
    }

}
