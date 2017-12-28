/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.TaskPriorityDAO;
import com.iso.dashboard.dto.CTaskPriority;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskPriorityMgntService {

    private static TaskPriorityMgntService service;

    public static TaskPriorityMgntService getInstance() {
        if (service == null) {
            service = new TaskPriorityMgntService();
        }
        return service;
    }

    public ResultDTO addTaskPriority(CTaskPriority p) {
        return TaskPriorityDAO.getInstance().addTaskPriority(p);
    }

    public ResultDTO updateTaskPriority(CTaskPriority p) {
        return TaskPriorityDAO.getInstance().updateTaskPriority(p);
    }

    public List<CTaskPriority> getTaskPrioritys() {
        return TaskPriorityDAO.getInstance().getTaskPriorities();
    }

    public CTaskPriority getTaskPriorityById(String id) {
        return TaskPriorityDAO.getInstance().getTaskPriorityById(String.valueOf(id));
    }

    public ResultDTO removeTaskPriority(String id) {
        return TaskPriorityDAO.getInstance().removeTaskPriority(id);
    }

}
