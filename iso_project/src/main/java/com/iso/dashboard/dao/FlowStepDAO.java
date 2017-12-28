/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.MFlowStep;
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
public class FlowStepDAO extends BaseDAO {

    private static FlowStepDAO dao;

    public static FlowStepDAO getInstance() {
        if (dao == null) {
            dao = new FlowStepDAO();
        }
        return dao;
    }

    public List<MFlowStep> getAssignee(String flowId, String stepId) {
        List<MFlowStep> assignees = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List<String> params = new ArrayList<>();
            String sql = "FROM MFlowStep where 1=1 ";
            if (!DataUtil.isNullOrEmpty(flowId)) {
                sql += " and flowId = ? ";
                params.add(flowId);
            }
            if (!DataUtil.isNullOrEmpty(stepId)) {
                sql += " and stepId = ? ";
                params.add(stepId);
            }

            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.valueOf(params.get(i)));
            }
            assignees = (List<MFlowStep>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return assignees;
    }

    public ResultDTO addAssignee(MFlowStep p) {
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

    public ResultDTO updateAssignee(MFlowStep newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MFlowStep u = (MFlowStep) session.get(MFlowStep.class, newData.getId());
            u.setFlowId(newData.getFlowId());
            u.setStepId(newData.getStepId());
            u.setStepBranch(newData.getStepBranch());
            u.setAllowAction(newData.getAllowAction());
            u.setStepIndex(newData.getStepIndex()); 
            u.setBackEmp(newData.getBackEmp());
            u.setDefEmp(newData.getDefEmp());
            u.setSubEmp(newData.getSubEmp());
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

    public ResultDTO removeAssignee(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MFlowStep u = (MFlowStep) session.get(MFlowStep.class, Integer.valueOf(id));
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

    public MFlowStep getAssigneeById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        MFlowStep assignee = null;
        try {
            session = getSession();
            assignee = (MFlowStep) session.get(MFlowStep.class, Integer.valueOf(id));
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
