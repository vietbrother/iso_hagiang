/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.TaskAttachmentDAO;
import com.iso.dashboard.dto.CTaskAttachment;
import com.iso.dashboard.dto.CTaskAttachmentPK;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskAttachmentService {

    private static TaskAttachmentService service;

    public static TaskAttachmentService getInstance() {
        if (service == null) {
            service = new TaskAttachmentService();
        }
        return service;
    }

    public ResultDTO addTaskAttachment(CTaskAttachment p) {
        return TaskAttachmentDAO.getInstance().addAttachment(p);
    }

    public ResultDTO updateTaskAttachment(CTaskAttachment p) {
        return TaskAttachmentDAO.getInstance().updateAttachment(p);
    }

    public List<CTaskAttachment> listTaskAttachment(String taskId, String attachmentType) {
        return TaskAttachmentDAO.getInstance().getAttachment(taskId, attachmentType);
    }

    public CTaskAttachment getTaskAttachmentById(CTaskAttachmentPK id) {
        return TaskAttachmentDAO.getInstance().getAttachmentById(id);
    }

    public ResultDTO removeTaskAttachment(CTaskAttachmentPK id) {
        return TaskAttachmentDAO.getInstance().removeAttachment(id);
    }

    public void deleteAttachmentsByTask(String taskId, String attachType, String histSeq) {
        TaskAttachmentDAO.getInstance().deleteAttachmentByTask(taskId, attachType, histSeq);
    }

    public List<CTaskAttachment> listTaskByCondition(Map<Object, Object> map) {
        return TaskAttachmentDAO.getInstance().getByCondition(map);
    }

}
