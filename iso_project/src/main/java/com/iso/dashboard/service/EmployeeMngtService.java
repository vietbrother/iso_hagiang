/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.EmployeeDAO;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeMngtService {

    private static EmployeeMngtService service;
    
    public static EmployeeMngtService getInstance() {
        if (service == null) {
            service = new EmployeeMngtService();
        }
        return service;
    }
    
    public ResultDTO addEmployee(Employee ep) {
        return EmployeeDAO.getInstance().addEmployee(ep);
    }
    
    public ResultDTO updateEmployee(Employee ep) {
        return EmployeeDAO.getInstance().updateEmployee(ep);
    }
    
    public List<Employee> listEmployees(String key) {
        return EmployeeDAO.getInstance().listEmployees(key);
    }
    
    public Employee getEmployeeById(String id) {
        return EmployeeDAO.getInstance().getEmployeeById(String.valueOf(id));
    }
    
    public ResultDTO removeEmployee(String id) {
        return EmployeeDAO.getInstance().removeEmployee(id);
    }
    
    public List<Employee> getEmployeeByOrganizationId(String orgId) {
        return EmployeeDAO.getInstance().getEmployeeByOrganizationId(orgId);
    }
    
    public Employee getLoginInfo(String user, String pass) {
        return EmployeeDAO.getInstance().getLoginInfo(user, pass);
    }
    
}
