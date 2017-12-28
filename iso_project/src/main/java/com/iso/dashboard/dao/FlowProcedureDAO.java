/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.MFlowProcedure;
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
public class FlowProcedureDAO extends BaseDAO {

    private static FlowProcedureDAO dao;

    public static FlowProcedureDAO getInstance() {
        if (dao == null) {
            dao = new FlowProcedureDAO();
        }
        return dao;
    }

    public List<MFlowProcedure> getFlowProcedure(String flowId, String proId) {
        List<MFlowProcedure> assignees = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List<String> params = new ArrayList<>();
            String sql = "FROM MFlowProcedure where 1=1 ";
            if (!DataUtil.isNullOrEmpty(flowId)) {
                sql += " and flowId = ? ";
                params.add(flowId);
            }
            if (!DataUtil.isNullOrEmpty(proId)) {
                sql += " and procedureId = ? ";
                params.add(proId);
            }

            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.valueOf(params.get(i)));
            }
            assignees = (List<MFlowProcedure>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return assignees;
    }

    public ResultDTO addFlowProcedure(MFlowProcedure p) {
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

    public ResultDTO updateFlowProcedure(MFlowProcedure newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MFlowProcedure u = (MFlowProcedure) session.get(MFlowProcedure.class, newData.getId());
            u.setFlowId(newData.getFlowId());
            u.setProcedureId(newData.getProcedureId());
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

    public ResultDTO removeFlowProcedure(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MFlowProcedure u = (MFlowProcedure) session.get(MFlowProcedure.class, Integer.valueOf(id));
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

    public MFlowProcedure getFlowProcedureById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        MFlowProcedure assignee = null;
        try {
            session = getSession();
            assignee = (MFlowProcedure) session.get(MFlowProcedure.class, Integer.valueOf(id));
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

    public void removeProcedureByFlow(String flowId) {
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "delete from MFlowProcedure where 1=1 ";
            if (!DataUtil.isNullOrEmpty(flowId)) {
                sql += "and flowId = ? ";
                params.add(flowId);
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

    public void removeProcedureByProcedure(String procedureId) {
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "delete from MFlowProcedure where 1=1 ";
            if (!DataUtil.isNullOrEmpty(procedureId)) {
                sql += "and procedureId = ? ";
                params.add(procedureId);
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
}
