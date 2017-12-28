/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CTask;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import com.iso.dashboard.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class TaskOrgDAO extends BaseDAO {

    private static TaskOrgDAO dao;

    public static TaskOrgDAO getInstance() {
        if (dao == null) {
            dao = new TaskOrgDAO();
        }
        return dao;
    }

    public List<CTask> listTaskOrg(String id, String parentTaskId) {
        List<CTask> listTaskOrg = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "FROM CTask org where 1 = 1 ";
            if (!DataUtil.isNullOrEmpty(id)) {
                sql += " and org.taskId = ?";
                params.add(id);
            }
            if (!DataUtil.isNullOrEmpty(parentTaskId)) {
                sql += " and org.taskParentId = ?";
                params.add(parentTaskId);
            }
            sql += "ORDER BY org.taskId ASC";
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            listTaskOrg = (List<CTask>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return listTaskOrg;
    }

    public ResultDTO addTaskOrg(CTask addObj) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(addObj);
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
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

    public ResultDTO updateTaskOrg(CTask newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTask updateObj = (CTask) session.get(CTask.class, newData.getTaskId());
            updateObj.setBudget(newData.getBudget());
            updateObj.setCurrency(newData.getCurrency());
            updateObj.setDepartmentId(newData.getDepartmentId());
            updateObj.setEndTime(newData.getEndTime());
            updateObj.setTaskName(newData.getTaskName());
            updateObj.setTaskContent(newData.getTaskContent());
            updateObj.setAssignedBy(newData.getAssignedBy());
            updateObj.setCreatedBy(newData.getCreatedBy());
            updateObj.setInsertTime(newData.getInsertTime());
            updateObj.setReviewedBy(newData.getReviewedBy());
            updateObj.setReviewedContent(newData.getReviewedContent());
            updateObj.setReviewedResult(newData.getReviewedResult());
            updateObj.setReviewedTime(newData.getReviewedTime());
            updateObj.setStartTime(newData.getStartTime());
            updateObj.setStatus(newData.getStatus());
            updateObj.setTaskGroupId(newData.getTaskGroupId());
            updateObj.setTaskParentId(newData.getTaskParentId());
            updateObj.setTaskPiorityId(newData.getTaskPiorityId());
            updateObj.setUpdateTime(newData.getUpdateTime());
            updateObj.setUpdatedBy(newData.getUpdatedBy());
            session.update(updateObj);
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

    public ResultDTO removeTaskOrg(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CTask org = (CTask) session.get(CTask.class, Integer.valueOf(id));
            session.delete(org);
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

    public CTask getTaskOrgById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CTask org = null;
        try {
            session = getSession();
            org = (CTask) session.get(CTask.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return org;
    }

    public List<CTask> getByCondition(Map<Object, Object> map) {
        List<CTask> listTaskOrg = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "FROM CTask org where 1 = 1 ";
            Set<Object> keySet = map.keySet();
            for (Object key : keySet) {
                sql += " and " + key + " = ?";
                params.add(map.get(key));
            }
            sql += "ORDER BY org.taskId ASC";
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, Integer.parseInt(params.get(i).toString()));
            }
            listTaskOrg = (List<CTask>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return listTaskOrg;
    }

    public List<CTask> listTaskOrg(CTask task) {
        List<CTask> listTaskOrg = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
//            session.beginTransaction();
            String sql = "FROM CTask org where 1 = 1 "
                    + (task == null || DataUtil.isNullOrEmpty(task.getTaskId()) ? "" : ("and org.taskId = ? "))
                    + (task == null || DataUtil.isNullOrEmpty(task.getDepartmentId()) ? "" : ("and org.departmentId = ? "))
                    + "ORDER BY org.id ASC";
            Query query = session.createQuery(sql);
            int i = 0;
            if (task != null && !DataUtil.isNullOrEmpty(task.getTaskId())) {
                query.setParameter(i, Integer.valueOf(task.getTaskId()));
                i++;
            }
            if (task != null && !DataUtil.isNullOrEmpty(task.getDepartmentId())) {
                query.setParameter(i, Integer.valueOf(task.getDepartmentId()));
                i++;
            }
            listTaskOrg = (List<CTask>) query.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTaskOrg;
    }
    
    public List<CTask> listTaskByName(String taskName, String departmentId) {
        List<CTask> listTaskOrg = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            List params = new ArrayList();
            String sql = "FROM CTask org where 1 = 1 ";
            if (!DataUtil.isNullOrEmpty(taskName)) {
                sql += " and lower(org.taskName) like ? ";
                params.add("%" + taskName.toLowerCase() + "%");
            }
            if (!DataUtil.isNullOrEmpty(departmentId)) {
                sql += " and org.departmentId = ? ";
                params.add(Integer.parseInt(departmentId));
            }
            sql += " ORDER BY org.taskId ASC";
            Query query = session.createQuery(sql);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            listTaskOrg = (List<CTask>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.isOpen()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return listTaskOrg;
    }
}
