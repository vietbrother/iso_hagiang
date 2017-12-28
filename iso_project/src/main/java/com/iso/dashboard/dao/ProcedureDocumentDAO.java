/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.MProcedureDocument;
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
public class ProcedureDocumentDAO extends BaseDAO {

    private static ProcedureDocumentDAO dao;

    public static ProcedureDocumentDAO getInstance() {
        if (dao == null) {
            dao = new ProcedureDocumentDAO();
        }
        return dao;
    }

    public List<MProcedureDocument> listProcedureDocuments(String procedureId) {
        List<MProcedureDocument> cProcedureDocuments = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM MProcedureDocument cp "
                    //                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(cp.name) like ? "))
                    + (DataUtil.isNullOrEmpty(procedureId) ? "" : ("where cp.procedure.id = ? "))
                    + "ORDER BY cp.id ";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(procedureId)) {
                query.setParameter(0, Integer.valueOf(procedureId));
            }
            cProcedureDocuments = (List<MProcedureDocument>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cProcedureDocuments;
    }

    public ResultDTO addProcedureDocument(MProcedureDocument et) {
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

    public ResultDTO updateProcedureDocument(MProcedureDocument newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MProcedureDocument et = (MProcedureDocument) session.get(MProcedureDocument.class, newData.getId());
            //et.setName(newData.get());
            session.update(et);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeProcedureDocument(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MProcedureDocument et = (MProcedureDocument) session.get(MProcedureDocument.class, Integer.valueOf(id));
            session.delete(et);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public MProcedureDocument getProcedureDocumentById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        MProcedureDocument cProcedureDocument = null;
        try {
            session = getSession();
            cProcedureDocument = (MProcedureDocument) session.get(MProcedureDocument.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return cProcedureDocument;
    }

    public ResultDTO removeDocumentByProcedureId(String procedureId) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "delete from MProcedureDocument where 1=1 ";
            if (!DataUtil.isNullOrEmpty(procedureId)) {
                sql += "and procedure.id = ? ";
                params.add(procedureId);
            }
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.parseInt(params.get(i).toString()));
            }
            query.executeUpdate();
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return res;
    }

}
