/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CTaskPriority;
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
public class TaskPriorityDAO extends BaseDAO {

    private static TaskPriorityDAO dao;

    public static TaskPriorityDAO getInstance() {
        if (dao == null) {
            dao = new TaskPriorityDAO();
        }
        return dao;
    }

    public List<CTaskPriority> getTaskPriorities() {
        List<CTaskPriority> calendars = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CTaskPriority cal "
                    + "ORDER BY cal.taskPiorityName ASC";
            Query query = session.createQuery(sql);
            calendars = (List<CTaskPriority>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return calendars;
    }

    public ResultDTO addTaskPriority(CTaskPriority p) {
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

    public ResultDTO updateTaskPriority(CTaskPriority newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskPriority updateObj = (CTaskPriority) session.get(CTaskPriority.class, newData.getTaskPiorityId());
            updateObj.setTaskPiorityName(newData.getTaskPiorityName());
            updateObj.setDescription(newData.getDescription());
            updateObj.setStatus(newData.getStatus());
            updateObj.setColor(newData.getColor());
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

    public ResultDTO removeTaskPriority(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTaskPriority removeObj = (CTaskPriority) session.get(CTaskPriority.class, Integer.valueOf(id));
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

    public CTaskPriority getTaskPriorityById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CTaskPriority objGet = null;
        try {
            session = getSession();
            objGet = (CTaskPriority) session.get(CTaskPriority.class, Integer.valueOf(id));
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
