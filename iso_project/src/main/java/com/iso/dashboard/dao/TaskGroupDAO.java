/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CTaskGroup;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskGroupDAO extends BaseDAO {

    private static TaskGroupDAO dao;

    public static TaskGroupDAO getInstance() {
        if (dao == null) {
            dao = new TaskGroupDAO();
        }
        return dao;
    }

    public List<CTaskGroup> getTaskGroups() {
        List<CTaskGroup> calendars = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CTaskGroup cal "
                    + "ORDER BY cal.taskGroupName ASC";
            Query query = session.createQuery(sql);
            calendars = (List<CTaskGroup>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return calendars;
    }

    public ResultDTO addTaskGroup(CTaskGroup p) {
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

    public ResultDTO updateTaskGroup(CTaskGroup newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskGroup updateObj = (CTaskGroup) session.get(CTaskGroup.class, newData.getTaskGroupId());
            updateObj.setTaskGroupName(newData.getTaskGroupName());
            updateObj.setDescription(newData.getDescription());
            updateObj.setStatus(newData.getStatus());
            updateObj.setColor(newData.getColor().toLowerCase());
            session.update(updateObj);
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

    public ResultDTO removeTaskGroup(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskGroup removeObj = (CTaskGroup) session.get(CTaskGroup.class, Integer.valueOf(id));
            session.delete(removeObj);
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

    public CTaskGroup getTaskGroupById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CTaskGroup objGet = null;
        try {
            session = getSession();
            objGet = (CTaskGroup) session.get(CTaskGroup.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return objGet;
    }

}
