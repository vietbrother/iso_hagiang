/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.TaskHistDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CTaskHistory;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskHistoryService {

    private static TaskHistoryService service;

    public static TaskHistoryService getInstance() {
        if (service == null) {
            service = new TaskHistoryService();
        }
        return service;
    }

    public ResultDTO addTaskHist(CTaskHistory p) {
        return TaskHistDAO.getInstance().addHist(p);
    }

    public ResultDTO updateTaskHist(CTaskHistory p) {
        return TaskHistDAO.getInstance().updateHist(p);
    }

    public List<CTaskHistory> listTaskHist(String taskId) {
        return TaskHistDAO.getInstance().getHist(taskId);
    }

    public CTaskHistory getTaskHistById(String id) {
        return TaskHistDAO.getInstance().getHistById(id);
    }

    public ResultDTO removeTaskHist(String id) {
        return TaskHistDAO.getInstance().removeHist(id);
    }

}
