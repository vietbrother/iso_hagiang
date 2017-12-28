/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.EmployeeTypeDAO;
import com.iso.dashboard.dto.EmployeeType;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeTypeMngtService {
    
    private static EmployeeTypeMngtService service;

    public static EmployeeTypeMngtService getInstance() {
        if (service == null) {
            service = new EmployeeTypeMngtService();
        }
        return service;
    }

    public ResultDTO addEmployeeType(EmployeeType et) {
        return EmployeeTypeDAO.getInstance().addEmployeeType(et);
    }

    public ResultDTO updateEmployeeType(EmployeeType et) {
        return EmployeeTypeDAO.getInstance().updateEmployeeType(et);
    }

    public List<EmployeeType> listEmployeeTypes(String employeeType) {
        return EmployeeTypeDAO.getInstance().listEmployeeTypes(employeeType);
    }

    public EmployeeType getEmployeeTypeById(String id) {
        return EmployeeTypeDAO.getInstance().getEmployeeTypeById(String.valueOf(id));
    }

    public ResultDTO removeEmployeeType(String id) {
        return EmployeeTypeDAO.getInstance().removeEmployeeType(id);
    }
}
