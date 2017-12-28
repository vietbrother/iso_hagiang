/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CTaskAssignee;
import com.iso.dashboard.dto.CTaskAssigneePK;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskAssigneeDAO extends BaseDAO {

    private static TaskAssigneeDAO dao;

    public static TaskAssigneeDAO getInstance() {
        if (dao == null) {
            dao = new TaskAssigneeDAO();
        }
        return dao;
    }

    public List<CTaskAssignee> getAssignee(String taskId, String assignId) {
        List<CTaskAssignee> assignees = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List<String> params = new ArrayList<>();
            String sql = "FROM CTaskAssignee where 1=1 ";
            if (!DataUtil.isNullOrEmpty(taskId)) {
                sql += " and id.taskId = ? ";
                params.add(taskId);
            }
            if (!DataUtil.isNullOrEmpty(assignId)) {
                sql += " and assignedId = ? ";
                params.add(assignId);
            }

            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.valueOf(params.get(i)));
            }
            assignees = (List<CTaskAssignee>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return assignees;
    }

    public ResultDTO addAssignee(CTaskAssignee p) {
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

    public ResultDTO updateAssignee(CTaskAssignee newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskAssignee u = (CTaskAssignee) session.get(CTaskAssignee.class, newData.getId());
            u.setAssignedId(newData.getAssignedId());
            u.setIsMain(newData.getIsMain());
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

    public ResultDTO removeAssignee(CTaskAssigneePK id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskAssignee u = (CTaskAssignee) session.get(CTaskAssignee.class, id);
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

    public CTaskAssignee getAssigneeById(CTaskAssigneePK id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CTaskAssignee assignee = null;
        try {
            session = getSession();
            assignee = (CTaskAssignee) session.get(CTaskAssignee.class, id);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return assignee;
    }
}
