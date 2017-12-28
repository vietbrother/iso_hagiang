/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.EmployeeProcess;
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
public class EmployeeProcessDAO extends BaseDAO {

    private static EmployeeProcessDAO dao;

    public static EmployeeProcessDAO getInstance() {
        if (dao == null) {
            dao = new EmployeeProcessDAO();
        }
        return dao;
    }

    public List<EmployeeProcess> listEmployeeProcess(String key) {
        List<EmployeeProcess> listEmployeeProcesss = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM EmployeeProcess ep "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(ep.employee.firstName) like ? OR "
                    + "LOWER(ep.employee.lastName) like ? "))
                    + "ORDER BY ep.fromDate, ep.toDate ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
                query.setParameter(1, "%" + key.toLowerCase() + "%");
            }
            listEmployeeProcesss = (List<EmployeeProcess>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployeeProcesss;
    }

    public ResultDTO addEmployeeProcess(EmployeeProcess ep) {
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

    public ResultDTO updateEmployeeProcess(EmployeeProcess newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            EmployeeProcess ep = (EmployeeProcess) session.get(EmployeeProcess.class, newData.getId());
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

    public ResultDTO removeEmployeeProcess(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            EmployeeProcess ep = (EmployeeProcess) session.get(EmployeeProcess.class, Integer.valueOf(id));
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

    public EmployeeProcess getEmployeeProcessById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        EmployeeProcess employeeProcess = null;
        try {
            session = getSession();
            employeeProcess = (EmployeeProcess) session.get(EmployeeProcess.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return employeeProcess;
    }

    public List<EmployeeProcess> getEmployeeProcessByEmployeeId(int employeeId) {
        List<EmployeeProcess> listEmployeeProcess = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM EmployeeProcess ep "
                    + (DataUtil.isNullOrEmpty(employeeId) ? "" : ("where LOWER(ep.employee.id) = ? "))
                    + "ORDER BY ep.fromDate, ep.toDate ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(employeeId)) {
                query.setParameter(0, employeeId);
            }
            listEmployeeProcess = (List<EmployeeProcess>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployeeProcess;
    }

}
