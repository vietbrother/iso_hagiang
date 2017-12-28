/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CTaskHistory;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskHistDAO extends BaseDAO {

    private static TaskHistDAO dao;

    public static TaskHistDAO getInstance() {
        if (dao == null) {
            dao = new TaskHistDAO();
        }
        return dao;
    }

    public List<CTaskHistory> getHist(String taskId) {
        List<CTaskHistory> taskHistorys = new ArrayList<>();
        Session session = null;
        try {
            List<String> params = new ArrayList<>();
            session = getSession();
            String sql = "FROM CTaskHistory hist where 1=1";
            if (!DataUtil.isNullOrEmpty(taskId)) {
                sql += "and id.taskId = ? ";
                params.add(taskId);
            }
            sql += "ORDER BY id.insertedTime ASC";
            Query query = session.createQuery(sql);
            for(int i=0;i<params.size();i++){
                query.setParameter(i, Integer.parseInt(params.get(i)));
            }
            taskHistorys = (List<CTaskHistory>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return taskHistorys;
    }

    public ResultDTO addHist(CTaskHistory p) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(p);
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
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

    public ResultDTO updateHist(CTaskHistory newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskHistory u = (CTaskHistory) session.get(CTaskHistory.class, newData.getSeq());
            u.setTaskId(newData.getTaskId());
            u.setReportContent(newData.getReportContent());
            u.setUpdatedTime(new Date());
            u.setInsertedBy(newData.getInsertedBy());
            u.setUpdatedBy(newData.getUpdatedBy());
            u.setReportRate(newData.getReportRate());
            u.setCheckResult(newData.getCheckResult());
            u.setCheckRate(newData.getCheckRate());
            u.setCheckTime(newData.getCheckTime());
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

    public ResultDTO removeHist(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskHistory u = (CTaskHistory) session.get(CTaskHistory.class, id);
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

    public CTaskHistory getHistById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CTaskHistory cTaskHistory = null;
        try {
            session = getSession();
            cTaskHistory = (CTaskHistory) session.get(CTaskHistory.class, id);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return cTaskHistory;
    }
}
