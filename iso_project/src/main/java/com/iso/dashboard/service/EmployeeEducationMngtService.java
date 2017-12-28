/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.EmployeeEducationDAO;
import com.iso.dashboard.dto.EmployeeEducation;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeEducationMngtService {

    private static EmployeeEducationMngtService service;

    public static EmployeeEducationMngtService getInstance() {
        if (service == null) {
            service = new EmployeeEducationMngtService();
        }
        return service;
    }

    public ResultDTO addEmployeeEducation(EmployeeEducation et) {
        return EmployeeEducationDAO.getInstance().addEmployeeEducation(et);
    }

    public ResultDTO updateEmployeeEducation(EmployeeEducation et) {
        return EmployeeEducationDAO.getInstance().updateEmployeeEducation(et);
    }

    public List<EmployeeEducation> listEmployeeEducations(String key) {
        return EmployeeEducationDAO.getInstance().listEmployeeEducations(key);
    }

    public EmployeeEducation getEmployeeEducationById(String id) {
        return EmployeeEducationDAO.getInstance().getEmployeeEducationById(String.valueOf(id));
    }

    public ResultDTO removeEmployeeEducation(String id) {
        return EmployeeEducationDAO.getInstance().removeEmployeeEducation(id);
    }

    public List<EmployeeEducation> getEmployeeEducationsByEmployeeId(int employeeId) {
        return EmployeeEducationDAO.getInstance().getEmployeeEducationsByEmployeeId(employeeId);
    }
}
