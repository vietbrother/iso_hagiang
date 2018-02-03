/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.TaskOrgDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.dto.CTaskAssignee;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskOrgMngService {

    private static TaskOrgMngService service;

    public static TaskOrgMngService getInstance() {
        if (service == null) {
            service = new TaskOrgMngService();
        }
        return service;
    }

    public ResultDTO addTaskOrg(CTask p) {
        return TaskOrgDAO.getInstance().addTaskOrg(p);
    }

    public ResultDTO updateTaskOrg(CTask p) {
        return TaskOrgDAO.getInstance().updateTaskOrg(p);
    }

    public List<CTask> listTaskOrg(String id, String parentTaskId) {
        return TaskOrgDAO.getInstance().listTaskOrg(id, parentTaskId);
    }

    public CTask getTaskOrgById(String id) {
        return TaskOrgDAO.getInstance().getTaskOrgById(String.valueOf(id));
    }

    public ResultDTO removeTaskOrg(String id) {
        return TaskOrgDAO.getInstance().removeTaskOrg(id);
    }

    public List<CTask> listTaskByCondition(Map<Object, Object> map) {
        return TaskOrgDAO.getInstance().getByCondition(map);
    }

    public List<CTask> listTaskOrg(CTask task) {
        return TaskOrgDAO.getInstance().listTaskOrg(task);
    }

    public List<CTask> getTasksByName(String taskName, String departmentId) {
        return TaskOrgDAO.getInstance().listTaskByName(taskName, departmentId);
    }

    public List<CTask> getListTaskPersonal(String userId, String departmentId) {
        List<Integer> lstTaskId = new ArrayList<>();
        List<CTaskAssignee> lstAssignees = TaskAssigneeService.getInstance().listTaskAssignee(null, userId);
        if (lstAssignees != null && !lstAssignees.isEmpty()) {
            lstAssignees.stream().forEach((assignee) -> {
                lstTaskId.add(assignee.getId().getTaskId());
            });
        }

        List<CTask> cTasks = TaskOrgDAO.getInstance().listTaskByName("", departmentId);
        List<CTask> cTasksPersonal = new ArrayList<>();
        for (CTask u : cTasks) {
            if (lstTaskId.contains(u.getTaskId())) {
                cTasksPersonal.add(u);
            }
        }
        return cTasksPersonal;
    }
}
