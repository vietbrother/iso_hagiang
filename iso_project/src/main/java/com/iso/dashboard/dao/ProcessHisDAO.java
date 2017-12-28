/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.PProcessHis;
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
public class ProcessHisDAO extends BaseDAO {

    private static ProcessHisDAO dao;

    public static ProcessHisDAO getInstance() {
        if (dao == null) {
            dao = new ProcessHisDAO();
        }
        return dao;
    }

    public List<PProcessHis> listProcessHis(PProcessHis dto) {
        List<PProcessHis> listProcessHis = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM PProcessHis u where 1=1 ";
            if (!DataUtil.isNullOrEmpty(dto)) {
                sql += (DataUtil.isNullOrEmpty(dto.getActionName()) ? "" : (" AND LOWER(u.actionName) like ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getpProcessId()) ? "" : (" AND u.pProcessId = ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getCreateBy()) ? "" : (" AND u.createBy = ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getProcessStateId()) ? "" : (" AND u.processStateId = ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getFlowId()) ? "" : (" AND u.flowId = ? "));
            }

            sql += "ORDER BY u.createdTime ";
            Query query = session.createQuery(sql);
            int i = 0;
            if (!DataUtil.isNullOrEmpty(dto)) {
                if (!DataUtil.isNullOrEmpty(dto.getActionName())) {
                    query.setParameter(i, "%" + dto.getActionName().toLowerCase() + "%");
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getpProcessId())) {
                    query.setParameter(i, dto.getpProcessId());
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getCreateBy())) {
                    query.setParameter(i, dto.getCreateBy());
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getProcessStateId())) {
                    query.setParameter(i, dto.getProcessStateId());
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getFlowId())) {
                    query.setParameter(i, dto.getFlowId());
                    i++;
                }
            }

            listProcessHis = (List<PProcessHis>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listProcessHis;
    }

    public ResultDTO addProcessHis(PProcessHis p) {
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

    public ResultDTO updateProcessHis(PProcessHis newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            PProcessHis u = (PProcessHis) session.get(PProcessHis.class, Integer.valueOf(newData.getId()));
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

    public ResultDTO removeProcessHis(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            PProcessHis u = (PProcessHis) session.get(PProcessHis.class, Integer.valueOf(id));
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

    public PProcessHis getProcessHisById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        PProcessHis processFiles = null;
        try {
            session = getSession();
            processFiles = (PProcessHis) session.get(PProcessHis.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return processFiles;
    }

}
