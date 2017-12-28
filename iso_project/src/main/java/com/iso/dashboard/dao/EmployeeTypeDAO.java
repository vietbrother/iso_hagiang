/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.EmployeeType;
import com.iso.dashboard.dto.Job;
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
public class EmployeeTypeDAO extends BaseDAO {

    private static EmployeeTypeDAO dao;

    public static EmployeeTypeDAO getInstance() {
        if (dao == null) {
            dao = new EmployeeTypeDAO();
        }
        return dao;
    }

    public List<EmployeeType> listEmployeeTypes(String employeeType) {
        List<EmployeeType> listEmployeeTypes = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM EmployeeType et "
                    + (DataUtil.isNullOrEmpty(employeeType) ? "" : ("where LOWER(et.employeeType) like ? "))
                    + "ORDER BY et.employeeType ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(employeeType)) {
                query.setParameter(0, "%" + employeeType.toLowerCase() + "%");
            }
            listEmployeeTypes = (List<EmployeeType>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployeeTypes;
    }

    public ResultDTO addEmployeeType(EmployeeType et) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(et);
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

    public ResultDTO updateEmployeeType(EmployeeType newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            EmployeeType et = (EmployeeType) session.get(EmployeeType.class, newData.getId());
            et.setEmployeeType(newData.getEmployeeType());
            session.update(et);
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

    public ResultDTO removeEmployeeType(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            EmployeeType et = (EmployeeType) session.get(Job.class, Integer.valueOf(id));
            session.delete(et);
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

    public EmployeeType getEmployeeTypeById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        EmployeeType employeeTypes = null;
        try {
            session = getSession();
            employeeTypes = (EmployeeType) session.get(EmployeeType.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return employeeTypes;
    }
}
