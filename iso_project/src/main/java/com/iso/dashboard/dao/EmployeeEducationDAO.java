/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.EmployeeEducation;
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
public class EmployeeEducationDAO extends BaseDAO {

    private static EmployeeEducationDAO dao;

    public static EmployeeEducationDAO getInstance() {
        if (dao == null) {
            dao = new EmployeeEducationDAO();
        }
        return dao;
    }

    public List<EmployeeEducation> listEmployeeEducations(String key) {
        List<EmployeeEducation> listEmployeeEducations = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM EmployeeEducation ee "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(ee.educationPlace) like ? "))
                    + "ORDER BY ee.fromDate, ee.toDate ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listEmployeeEducations = (List<EmployeeEducation>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployeeEducations;
    }

    public ResultDTO addEmployeeEducation(EmployeeEducation ee) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(ee);
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

    public ResultDTO updateEmployeeEducation(EmployeeEducation newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            EmployeeEducation ee = (EmployeeEducation) session.get(EmployeeEducation.class, newData.getId());
            ee.setEmployee(newData.getEmployee());
            ee.setDegreeType(newData.getDegreeType());
            ee.setEducationType(newData.getEducationType());
            ee.setEducationPlace(newData.getEducationPlace());
            session.update(ee);
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

    public ResultDTO removeEmployeeEducation(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
            EmployeeEducation ee = (EmployeeEducation) session.get(EmployeeEducation.class, Integer.valueOf(id));
            session.delete(ee);
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

    public EmployeeEducation getEmployeeEducationById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        EmployeeEducation employeeEducation = null;
        try {
            session = getSession();
            session.beginTransaction();
            employeeEducation = (EmployeeEducation) session.get(EmployeeEducation.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return employeeEducation;
    }

    public List<EmployeeEducation> getEmployeeEducationsByEmployeeId(int employeeId) {
        List<EmployeeEducation> listEmployeeEducations = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM EmployeeEducation ee "
                    + (DataUtil.isNullOrEmpty(employeeId) ? "" : ("where LOWER(ee.employee.id) = ? "))
                    + "ORDER BY ee.fromDate, ee.toDate ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(employeeId)) {
                query.setParameter(0, employeeId);
            }
            listEmployeeEducations = (List<EmployeeEducation>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listEmployeeEducations;
    }
}
