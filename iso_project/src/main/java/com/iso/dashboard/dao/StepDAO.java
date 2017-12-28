/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CStep;
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
public class StepDAO extends BaseDAO{
    
    private static StepDAO dao;

    public static StepDAO getInstance() {
        if (dao == null) {
            dao = new StepDAO();
        }
        return dao;
    }

    public List<CStep> listCSteps(String key) {
        List<CStep> listCSteps = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CStep cs "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(cs.name) like ? "))
                    + "ORDER BY cs.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listCSteps = (List<CStep>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCSteps;
    }

    public ResultDTO addCStep(CStep cs) {
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

    public ResultDTO updateCStep(CStep newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CStep cs = (CStep) session.get(CStep.class, newData.getId());
            cs.setName(newData.getName());
            cs.setDescription(newData.getDescription());
            session.update(cs);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeCStep(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CStep cs = (CStep) session.get(CStep.class, Integer.valueOf(id));
            session.delete(cs);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public CStep getCStepById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CStep cs = null;
        try {
            session = getSession();
            cs = (CStep) session.get(CStep.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return cs;
    }
}
