/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CField;
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
public class FieldDAO extends BaseDAO{
    
    private static FieldDAO dao;

    public static FieldDAO getInstance() {
        if (dao == null) {
            dao = new FieldDAO();
        }
        return dao;
    }

    public List<CField> listFields(String key) {
        List<CField> cFields = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CField cf "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(cf.name) like ? "))
                    + "ORDER BY cf.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            cFields = (List<CField>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cFields;
    }

    public ResultDTO addField(CField et) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(et);
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

    public ResultDTO updateField(CField newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CField et = (CField) session.get(CField.class, newData.getId());
            et.setName(newData.getName());
            session.update(et);
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

    public ResultDTO removeField(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CField et = (CField) session.get(CField.class, Integer.valueOf(id));
            session.delete(et);
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

    public CField getFieldById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CField cField = null;
        try {
            session = getSession();
            cField = (CField) session.get(CField.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return cField;
    }
}
