/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.DocumentDTO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Employee;
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
public class DocumentDAO extends BaseDAO {

    private static DocumentDAO dao;

    public static DocumentDAO getInstance() {
        if (dao == null) {
            dao = new DocumentDAO();
        }
        return dao;
    }

    public List<DocumentDTO> listDocuments(String fileName) {
        List<DocumentDTO> listUsers = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM DocumentDTO u "
                    + (DataUtil.isNullOrEmpty(fileName) ? "" : ("where LOWER(u.fileName) like ? "))
                    + "ORDER BY u.fileName ASC";
            Query query = session.createQuery(sql);            
            if (!DataUtil.isNullOrEmpty(fileName)) {
                query.setParameter(0, "%" + fileName.toLowerCase() + "%");
            }
            listUsers = (List<DocumentDTO>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listUsers;
    }

    public ResultDTO addDocument(DocumentDTO p) {
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

    public ResultDTO updateDocument(DocumentDTO newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            DocumentDTO u = (DocumentDTO) session.get(DocumentDTO.class, Integer.valueOf(newData.getId()));
            u.setFileCode(newData.getFileCode());
            u.setFileName(newData.getFileName());
            u.setFileType(newData.getFileType());
            u.setSecurityLevel(newData.getSecurityLevel());
            u.setStatus(newData.getStatus());
            u.setPartStorageTime(newData.getPartStorageTime());
            u.setDepartmentStorageTime(newData.getDepartmentStorageTime());
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

    public ResultDTO removeDocument(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            DocumentDTO u = (DocumentDTO) session.get(DocumentDTO.class, Integer.valueOf(id));
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

    public DocumentDTO getDocumentById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        DocumentDTO users = null;
        try {
            session = getSession();
            users = (DocumentDTO) session.get(DocumentDTO.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return users;
    }

}
