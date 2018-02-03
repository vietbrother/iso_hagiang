/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.PProcess;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author
 */
public class ProcessDAO extends BaseDAO {

    private static ProcessDAO dao;

    public static ProcessDAO getInstance() {
        if (dao == null) {
            dao = new ProcessDAO();
        }
        return dao;
    }

    public List<PProcess> listProcesss(PProcess dto) {
        List<PProcess> listProcesss = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM PProcess u where 1=1 ";
            if (!DataUtil.isNullOrEmpty(dto)) {
                sql += (DataUtil.isNullOrEmpty(dto.getName()) ? "" : (" AND LOWER(u.name) like ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getProcedureId()) ? "" : (" AND u.procedureId = ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getProcedureName()) ? "" : (" AND u.procedureName = ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getState()) ? "" : (" AND u.state = ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getFlowId()) ? "" : (" AND u.flowId = ? "));
            }

            sql += "ORDER BY u.createdTime desc, u.code, u.procedureName ";
            Query query = session.createQuery(sql);
            int i = 0;
            if (!DataUtil.isNullOrEmpty(dto)) {
                if (!DataUtil.isNullOrEmpty(dto.getName())) {
                    query.setParameter(i, "%" + dto.getName().toLowerCase() + "%");
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getProcedureId())) {
                    query.setParameter(i, dto.getProcedureId());
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getProcedureName())) {
                    query.setParameter(i, dto.getProcedureName());
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getState())) {
                    query.setParameter(i, dto.getState());
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getFlowId())) {
                    query.setParameter(i, dto.getFlowId());
                    i++;
                }
            }

            listProcesss = (List<PProcess>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listProcesss;
    }

    public ResultDTO addProcesss(PProcess p) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(p);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO updateProcesss(PProcess newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            PProcess u = (PProcess) session.get(PProcess.class, Integer.valueOf(newData.getId()));
            u.setState(newData.getState());
            u.setStateName(newData.getStateName());
            u.setDescription(newData.getDescription());
            session.update(u);
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

    public ResultDTO removeProcesss(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            PProcess u = (PProcess) session.get(PProcess.class, Integer.valueOf(id));
            session.delete(u);
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

    public PProcess getProcesssById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        PProcess processFiles = null;
        try {
            session = getSession();
            processFiles = (PProcess) session.get(PProcess.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return processFiles;
    }

}
