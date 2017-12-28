/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.EmployeeReward;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeRewardDAO extends BaseDAO {

    private static EmployeeRewardDAO dao;

    public static EmployeeRewardDAO getInstance() {
        if (dao == null) {
            dao = new EmployeeRewardDAO();
        }
        return dao;
    }

    public List<EmployeeReward> listEmployeeRewards(String key) {
        List<EmployeeReward> listEmployeeRewards = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM EmployeeReward er "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(er.content) like ? OR "))
                    + "ORDER BY er.employee.id ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listEmployeeRewards = (List<EmployeeReward>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployeeRewards;
    }

    public ResultDTO addEmployeeReward(EmployeeReward ep) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(ep);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setMessage(ex.getMessage());
        }
        return res;
    }

    public ResultDTO updateEmployeeReward(EmployeeReward newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            EmployeeReward ep = (EmployeeReward) session.get(EmployeeReward.class, newData.getId());
            ep.setEmployee(newData.getEmployee());
            session.update(ep);
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

    public ResultDTO removeEmployeeReward(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            EmployeeReward ep = (EmployeeReward) session.get(EmployeeReward.class, Integer.valueOf(id));
            session.delete(ep);
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

    public EmployeeReward getEmployeeRewardById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        EmployeeReward employeeProcess = null;
        try {
            session = getSession();
            employeeProcess = (EmployeeReward) session.get(EmployeeReward.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return employeeProcess;
    }

    public List<EmployeeReward> getEmployeeRewardByEmployeeId(int employeeId) {
        List<EmployeeReward> listEmployeeReward = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM EmployeeReward er "
                    + (DataUtil.isNullOrEmpty(employeeId) ? "" : ("where LOWER(er.employee.id) = ? "))
                    + "ORDER BY er.id ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(employeeId)) {
                query.setParameter(0, employeeId);
            }
            listEmployeeReward = (List<EmployeeReward>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployeeReward;
    }

}
