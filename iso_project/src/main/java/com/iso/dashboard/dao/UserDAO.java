/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.User_;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class UserDAO extends BaseDAO {

    private static UserDAO dao;

    public static UserDAO getInstance() {
        if (dao == null) {
            dao = new UserDAO();
        }
        return dao;
    }

    public List<User_> listUsers(String username) {
        List<User_> listUsers = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM Users u "
                    + (DataUtil.isNullOrEmpty(username) ? "" : ("where LOWER(u.username) like ? "))
                    + "ORDER BY u.username ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(username)) {
                query.setParameter(0, "%" + username.toLowerCase() + "%");
            }
            listUsers = (List<User_>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return listUsers;
    }

    public ResultDTO addUsers(User_ p) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(p);
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO updateUsers(User_ newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            User_ u = (User_) session.get(User_.class, Integer.valueOf(newData.getId()));
            u.setUserName(newData.getUserName());
            u.setFirstName(newData.getFirstName());
            u.setLastName(newData.getLastName());
            u.setEmail(newData.getEmail());
            u.setBirthDay(newData.getBirthDay());
            u.setPhone(newData.getPhone());
            u.setRole(newData.getRole());
            session.update(u);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
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
            User_ u = (User_) session.get(User_.class, Integer.valueOf(id));
            session.delete(u);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public User_ getUsersById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        User_ users = null;
        try {
            session = getSession();
            users = (User_) session.get(User_.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return users;
    }

}
