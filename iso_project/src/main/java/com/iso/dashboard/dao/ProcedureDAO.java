/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CProcedure;
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
public class ProcedureDAO extends BaseDAO{
    
    private static ProcedureDAO dao;

    public static ProcedureDAO getInstance() {
        if (dao == null) {
            dao = new ProcedureDAO();
        }
        return dao;
    }

    public List<CProcedure> listProcedures(String key) {
        List<CProcedure> cProcedures = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CProcedure cp "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(cp.name) like ? "))
                    + "ORDER BY cp.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            cProcedures = (List<CProcedure>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cProcedures;
    }

    public ResultDTO addProcedure(CProcedure et) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(et);
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setMessage(ex.getMessage());
        }
        return res;
    }

    public ResultDTO updateProcedure(CProcedure newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CProcedure et = (CProcedure) session.get(CProcedure.class, newData.getId());
            et.setName(newData.getName());
            et.setType(newData.getType());
            et.setDescription(newData.getDescription());
            et.setCode(newData.getCode());
            et.setContent(newData.getContent());
            et.setLevel(newData.getLevel());
            et.setOrgId(newData.getOrgId());
            et.setProcessTime(newData.getProcessTime());
            session.update(et);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeProcedure(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CProcedure et = (CProcedure) session.get(CProcedure.class, Integer.valueOf(id));
            session.delete(et);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public CProcedure getProcedureById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CProcedure cProcedure = null;
        try {
            session = getSession();
            cProcedure = (CProcedure) session.get(CProcedure.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return cProcedure;
    }
}
