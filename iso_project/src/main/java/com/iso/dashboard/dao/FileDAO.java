/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.FileLevel;
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
public class FileDAO extends BaseDAO {

    private static FileDAO dao;

    public static FileDAO getInstance() {
        if (dao == null) {
            dao = new FileDAO();
        }
        return dao;
    }

    public List<FileLevel> listUsers(String fileName) {
        List<FileLevel> listUsers = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM FileLevel u "
                    + (DataUtil.isNullOrEmpty(fileName) ? "" : ("where LOWER(u.fileName) like ? "))
                    + "ORDER BY u.fileName ASC";
            Query query = session.createQuery(sql);            
            if (!DataUtil.isNullOrEmpty(fileName)) {
                query.setParameter(0, "%" + fileName.toLowerCase() + "%");
            }
            listUsers = (List<FileLevel>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listUsers;
    }

    public ResultDTO addUsers(FileLevel p) {
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

    public ResultDTO updateUsers(FileLevel newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            FileLevel u = (FileLevel) session.get(FileLevel.class, Integer.valueOf(newData.getId()));
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

    public ResultDTO removeUsers(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            FileLevel u = (FileLevel) session.get(FileLevel.class, Integer.valueOf(id));
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

    public FileLevel getUsersById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        FileLevel users = null;
        try {
            session = getSession();
            users = (FileLevel) session.get(FileLevel.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return users;
    }

}
