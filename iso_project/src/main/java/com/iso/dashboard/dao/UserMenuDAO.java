/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.MUserMenu;
import com.iso.dashboard.dto.ResultDTO;
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
public class UserMenuDAO extends BaseDAO {

    private static UserMenuDAO dao;

    public static UserMenuDAO getInstance() {
        if (dao == null) {
            dao = new UserMenuDAO();
        }
        return dao;
    }

    public List<MUserMenu> getUserMenu(String menuId, String userId) {
        List<MUserMenu> lstMenu = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List<String> params = new ArrayList<>();
            String sql = "FROM MUserMenu where 1=1 ";
            if (!DataUtil.isNullOrEmpty(menuId)) {
                sql += " and menu.id = ? ";
                params.add(menuId);
            }
            if (!DataUtil.isNullOrEmpty(userId)) {
                sql += " and userId = ? ";
                params.add(userId);
            }

            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.valueOf(params.get(i)));
            }
            lstMenu = (List<MUserMenu>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return lstMenu;
    }

    public ResultDTO addUserMenu(MUserMenu p) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            session.save(p);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO updateUserMenu(MUserMenu newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MUserMenu u = (MUserMenu) session.get(MUserMenu.class, newData.getId());
            u.setMenu(newData.getMenu());
            u.setUserId(newData.getUserId());
            session.update(u);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeUserMenu(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            MUserMenu u = (MUserMenu) session.get(MUserMenu.class, Integer.valueOf(id));
            session.delete(u);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public MUserMenu getUserMenuById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        MUserMenu assignee = null;
        try {
            session = getSession();
            assignee = (MUserMenu) session.get(MUserMenu.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return assignee;
    }

    public void removeUserByMenuId(String menuId) {
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "delete from MUserMenu where 1=1 ";
            if (!DataUtil.isNullOrEmpty(menuId)) {
                sql += "and menu.id = ? ";
                params.add(menuId);
            }
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.parseInt(params.get(i).toString()));
            }
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public void removeMenuByUserId(String userId) {
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "delete from MUserMenu where 1=1 ";
            if (!DataUtil.isNullOrEmpty(userId)) {
                sql += "and userId = ? ";
                params.add(userId);
            }
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.parseInt(params.get(i).toString()));
            }
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}
