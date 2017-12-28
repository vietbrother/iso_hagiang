/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.FlowStepDAO;
import com.iso.dashboard.dto.MFlowStep;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class FlowStepService {

    private static FlowStepService service;

    public static FlowStepService getInstance() {
        if (service == null) {
            service = new FlowStepService();
        }
        return service;
    }

    public ResultDTO addTaskAssignee(MFlowStep p) {
        return FlowStepDAO.getInstance().addAssignee(p);
    }

    public ResultDTO updateTaskAssignee(MFlowStep p) {
        return FlowStepDAO.getInstance().updateAssignee(p);
    }

    public List<MFlowStep> listTaskAssignee(String taskId, String assignedId) {
        return FlowStepDAO.getInstance().getAssignee(taskId, assignedId);
    }

    public MFlowStep getTaskAssigneeById(String id) {
        return FlowStepDAO.getInstance().getAssigneeById(id);
    }

    public ResultDTO removeTaskAssignee(String id) {
        return FlowStepDAO.getInstance().removeAssignee(id);
    }

}
