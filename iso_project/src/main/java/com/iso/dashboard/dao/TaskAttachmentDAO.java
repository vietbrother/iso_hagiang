/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CTaskAttachment;
import com.iso.dashboard.dto.CTaskAttachmentPK;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskAttachmentDAO extends BaseDAO {

    private static TaskAttachmentDAO dao;

    public static TaskAttachmentDAO getInstance() {
        if (dao == null) {
            dao = new TaskAttachmentDAO();
        }
        return dao;
    }

    public List<CTaskAttachment> getAttachment(String taskId, String attachmentType) {
        List<CTaskAttachment> attachments = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List<String> params = new ArrayList<>();
            String sql = "FROM CTaskAttachment where 1=1";
            if (!DataUtil.isNullOrEmpty(taskId)) {
                sql += "and id.taskId = ? ";
                params.add(taskId);
            }
            if (!DataUtil.isNullOrEmpty(attachmentType)) {
                sql += "and id.attachmentType = ? ";
                params.add(attachmentType);
            }
            sql += "ORDER BY fileName ASC";

            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.parseInt(params.get(i)));
            }
            attachments = (List<CTaskAttachment>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return attachments;
    }

    public ResultDTO addAttachment(CTaskAttachment p) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            session.save(p);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO updateAttachment(CTaskAttachment newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskAttachment u = (CTaskAttachment) session.get(CTaskAttachment.class, newData.getId());
            u.setAttachmentUrl(newData.getAttachmentUrl());
            u.setFileName(newData.getFileName());
            session.update(u);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeAttachment(CTaskAttachmentPK id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskAttachment u = (CTaskAttachment) session.get(CTaskAttachment.class, id);
            session.delete(u);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public CTaskAttachment getAttachmentById(CTaskAttachmentPK id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CTaskAttachment attachment = null;
        try {
            session = getSession();
            attachment = (CTaskAttachment) session.get(CTaskAttachment.class, id);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return attachment;
    }
    
    public void deleteAttachmentByTask(String taskId, String attachmentType, String histSeq){
        Session session = null;
        CTaskAttachment attachment = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "delete from CTaskAttachment where 1=1 ";
            if (!DataUtil.isNullOrEmpty(taskId)) {
                sql += "and id.taskId = ? ";
                params.add(taskId);
            }
            if (!DataUtil.isNullOrEmpty(attachmentType)) {
                sql += "and id.attachmentType = ? ";
                params.add(attachmentType);
            }
            if (!DataUtil.isNullOrEmpty(histSeq)) {
                sql += "and histSeq = ? ";
                params.add(histSeq);
            }
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.parseInt(params.get(i).toString()));
            }
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<CTaskAttachment> getByCondition(Map<Object, Object> map) {
        List<CTaskAttachment> listTaskOrg = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "FROM CTaskAttachment org where 1 = 1 ";
            Set<Object> keySet = map.keySet();
            for (Object key : keySet) {
                sql += " and " + key + " = ?";
                params.add(map.get(key));
            }
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.parseInt(params.get(i).toString()));
            }
            listTaskOrg = (List<CTaskAttachment>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return listTaskOrg;
    }
}
