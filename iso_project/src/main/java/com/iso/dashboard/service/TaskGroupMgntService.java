/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.TaskGroupDAO;
import com.iso.dashboard.dto.CTaskGroup;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskGroupMgntService {

    private static TaskGroupMgntService service;

    public static TaskGroupMgntService getInstance() {
        if (service == null) {
            service = new TaskGroupMgntService();
        }
        return service;
    }

    public ResultDTO addTaskGroup(CTaskGroup p) {
        return TaskGroupDAO.getInstance().addTaskGroup(p);
    }

    public ResultDTO updateTaskGroup(CTaskGroup p) {
        return TaskGroupDAO.getInstance().updateTaskGroup(p);
    }

    public List<CTaskGroup> getTaskGroups() {
        return TaskGroupDAO.getInstance().getTaskGroups();
    }

    public CTaskGroup getTaskGroupById(String id) {
        return TaskGroupDAO.getInstance().getTaskGroupById(String.valueOf(id));
    }

    public ResultDTO removeTaskGroup(String id) {
        return TaskGroupDAO.getInstance().removeTaskGroup(id);
    }

}
