/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.Employee;
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
public class EmployeeDAO extends BaseDAO {

    private static EmployeeDAO dao;

    public static EmployeeDAO getInstance() {
        if (dao == null) {
            dao = new EmployeeDAO();
        }
        return dao;
    }

    public List<Employee> listEmployees(String key) {
        List<Employee> listEmployees = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM Employee ep "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(ep.firstName) like ? "
                            + "OR LOWER(ep.lastName) like ? OR LOWER(ep.employeeCode) like ? "))
                    + "ORDER BY ep.lastName ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
                query.setParameter(1, "%" + key.toLowerCase() + "%");
                query.setParameter(2, "%" + key.toLowerCase() + "%");
            }
            listEmployees = (List<Employee>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployees;
    }

    public ResultDTO addEmployee(Employee ep) {
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

    public ResultDTO updateEmployee(Employee newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            Employee ep = (Employee) session.get(Employee.class, newData.getId());
            ep.setEmployeeCode(newData.getEmployeeCode());
            ep.setCommistionPct(newData.getCommistionPct());
            ep.setDepartment(newData.getDepartment());
            ep.setJob(newData.getJob());
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

    public ResultDTO removeEmployee(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            Employee ep = (Employee) session.get(Employee.class, Integer.valueOf(id));
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

    public Employee getEmployeeById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        Employee Employees = null;
        try {
            session = getSession();
            Employees = (Employee) session.get(Employee.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return Employees;
    }

    public List<Employee> getEmployeeByOrganizationId(String orgId) {
        List<Employee> listEmployees = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM Employee ep "
                    + (DataUtil.isNullOrEmpty(orgId) ? "" : ("where ep.department.id = ?"))
                    + "ORDER BY ep.lastName ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(orgId)) {
                query.setParameter(0, Integer.valueOf(orgId));
            }
            listEmployees = (List<Employee>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployees;
    }

    public Employee getLoginInfo(String username, String password) {
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM Employee ep "
                    + ("where ep.userName = ? "
                    + " and ep.password = ? and ep.isActive = '1' ")
                    + "ORDER BY ep.lastName ";
            Query query = session.createQuery(sql);
            query.setParameter(0, username);
            query.setParameter(1, password);
            List<Employee> listEmployees = (List<Employee>) query.list();
            session.getTransaction().commit();
            if(listEmployees != null && !listEmployees.isEmpty()){
                return listEmployees.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
