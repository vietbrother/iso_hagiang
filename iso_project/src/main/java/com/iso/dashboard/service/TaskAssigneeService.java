/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.TaskAssigneeDAO;
import com.iso.dashboard.dto.CTaskAssignee;
import com.iso.dashboard.dto.CTaskAssigneePK;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskAssigneeService {

    private static TaskAssigneeService service;

    public static TaskAssigneeService getInstance() {
        if (service == null) {
            service = new TaskAssigneeService();
        }
        return service;
    }

    public ResultDTO addTaskAssignee(CTaskAssignee p) {
        return TaskAssigneeDAO.getInstance().addAssignee(p);
    }

    public ResultDTO updateTaskAssignee(CTaskAssignee p) {
        return TaskAssigneeDAO.getInstance().updateAssignee(p);
    }

    public List<CTaskAssignee> listTaskAssignee(String taskId, String assignedId) {
        return TaskAssigneeDAO.getInstance().getAssignee(taskId, assignedId);
    }

    public CTaskAssignee getTaskAssigneeById(CTaskAssigneePK id) {
        return TaskAssigneeDAO.getInstance().getAssigneeById(id);
    }

    public ResultDTO removeTaskAssignee(CTaskAssigneePK id) {
        return TaskAssigneeDAO.getInstance().removeAssignee(id);
    }

}
