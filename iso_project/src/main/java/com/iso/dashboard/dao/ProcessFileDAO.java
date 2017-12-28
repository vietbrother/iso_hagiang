/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.PProcessFile;
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
public class ProcessFileDAO extends BaseDAO {

    private static ProcessFileDAO dao;

    public static ProcessFileDAO getInstance() {
        if (dao == null) {
            dao = new ProcessFileDAO();
        }
        return dao;
    }

    public List<PProcessFile> listProcessFiles(PProcessFile dto) {
        List<PProcessFile> listProcessFiles = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM PProcessFile u where 1=1 ";
            if (!DataUtil.isNullOrEmpty(dto)) {
                sql += (DataUtil.isNullOrEmpty(dto.getFileName()) ? "" : (" AND LOWER(u.fileName) like ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getProcessId()) ? "" : (" AND u.processId = ? "));
                sql += (DataUtil.isNullOrEmpty(dto.getProcedureDocumentId()) ? "" : (" AND u.procedureDocumentId = ? "));
            }

            sql += "ORDER BY u.fileName ";
            Query query = session.createQuery(sql);
            int i = 0;
            if (!DataUtil.isNullOrEmpty(dto)) {
                if (!DataUtil.isNullOrEmpty(dto.getFileName())) {
                    query.setParameter(i, "%" + dto.getFileName().toLowerCase() + "%");
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getProcessId())) {
                    query.setParameter(i, dto.getProcessId());
                    i++;
                }
                if (!DataUtil.isNullOrEmpty(dto.getProcedureDocumentId())) {
                    query.setParameter(i, dto.getProcedureDocumentId());
                    i++;
                }
            }

            listProcessFiles = (List<PProcessFile>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listProcessFiles;
    }

    public ResultDTO addProcessFiles(PProcessFile p) {
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

    public ResultDTO updateProcessFiles(PProcessFile newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            PProcessFile u = (PProcessFile) session.get(PProcessFile.class, Integer.valueOf(newData.getId()));
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

    public ResultDTO removeProcessFiles(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            PProcessFile u = (PProcessFile) session.get(PProcessFile.class, Integer.valueOf(id));
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

    public PProcessFile getProcessFilesById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        PProcessFile processFiles = null;
        try {
            session = getSession();
            processFiles = (PProcessFile) session.get(PProcessFile.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return processFiles;
    }

}
