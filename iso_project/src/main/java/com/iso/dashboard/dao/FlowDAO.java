/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CFlow;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class FlowDAO extends BaseDAO{
    
    private static FlowDAO dao;

    public static FlowDAO getInstance() {
        if (dao == null) {
            dao = new FlowDAO();
        }
        return dao;
    }

    public List<CFlow> listCFlows(String key) {
        List<CFlow> listCSteps = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CFlow cs "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(cs.name) like ? "))
                    + "ORDER BY cs.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listCSteps = (List<CFlow>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCSteps;
    }

    public ResultDTO addCFlow(CFlow cs) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(cs);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setMessage(ex.getMessage());
        }
        return res;
    }

    public ResultDTO updateCFlow(CFlow newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CFlow cs = (CFlow) session.get(CFlow.class, newData.getFlowId());
            cs.setCode(newData.getCode());
            cs.setName(newData.getName());
            cs.setDescription(newData.getDescription());
            cs.setStatus(newData.getStatus());
            session.update(cs);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeCFlow(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CFlow cs = (CFlow) session.get(CFlow.class, Integer.valueOf(id));
            session.delete(cs);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public CFlow getCFlowById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CFlow cs = null;
        try {
            session = getSession();
            cs = (CFlow) session.get(CFlow.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return cs;
    }
}
