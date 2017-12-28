/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CMenu;
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
public class MenuDAO extends BaseDAO {

    private static MenuDAO dao;

    public static MenuDAO getInstance() {
        if (dao == null) {
            dao = new MenuDAO();
        }
        return dao;
    }

    public List<CMenu> listParentMenu(String username) {
        List<CMenu> listCMenu = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CMenu u where isParent = '1' "
                    + (DataUtil.isNullOrEmpty(username) ? "" : (" and LOWER(u.name) like ? "))
                    + "ORDER BY u.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(username)) {
                query.setParameter(0, "%" + username.toLowerCase() + "%");
            }
            listCMenu = (List<CMenu>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return listCMenu;
    }
    public List<CMenu> listCMenu(String username) {
        List<CMenu> listCMenu = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CMenu u "
                    + (DataUtil.isNullOrEmpty(username) ? "" : ("where LOWER(u.name) like ? "))
                    + "ORDER BY u.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(username)) {
                query.setParameter(0, "%" + username.toLowerCase() + "%");
            }
            listCMenu = (List<CMenu>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return listCMenu;
    }

    public ResultDTO addCMenu(CMenu p) {
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

    public ResultDTO updateCMenu(CMenu newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CMenu u = (CMenu) session.get(CMenu.class, Integer.valueOf(newData.getId()));
            u.setName(newData.getName());
            u.setCode(newData.getCode());
            u.setCreateTime(newData.getCreateTime());
            u.setIsActive(newData.getIsActive());
            u.setIsContainChild(newData.getIsContainChild());
            u.setIsParent(newData.getIsParent());
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

    public ResultDTO removeCMenu(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CMenu u = (CMenu) session.get(CMenu.class, Integer.valueOf(id));
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

    public CMenu getCMenuById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CMenu users = null;
        try {
            session = getSession();
            users = (CMenu) session.get(CMenu.class, Integer.valueOf(id));
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
