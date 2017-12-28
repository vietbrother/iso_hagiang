/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.EmployeeProcessDAO;
import com.iso.dashboard.dto.EmployeeProcess;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeProcessMngtService {
    
    private static EmployeeProcessMngtService service;

    public static EmployeeProcessMngtService getInstance() {
        if (service == null) {
            service = new EmployeeProcessMngtService();
        }
        return service;
    }

    public ResultDTO addEmployeeProcess(EmployeeProcess et) {
        return EmployeeProcessDAO.getInstance().addEmployeeProcess(et);
    }

    public ResultDTO updateEmployeeProcess(EmployeeProcess et) {
        return EmployeeProcessDAO.getInstance().updateEmployeeProcess(et);
    }

    public List<EmployeeProcess> listEmployeeProcess(String key) {
        return EmployeeProcessDAO.getInstance().listEmployeeProcess(key);
    }

    public EmployeeProcess getEmployeeProcessById(String id) {
        return EmployeeProcessDAO.getInstance().getEmployeeProcessById(String.valueOf(id));
    }

    public ResultDTO removeEmployeeProcess(String id) {
        return EmployeeProcessDAO.getInstance().removeEmployeeProcess(id);
    }

    public List<EmployeeProcess> getEmployeeProcessByEmployeeId(int employeeId) {
        return EmployeeProcessDAO.getInstance().getEmployeeProcessByEmployeeId(employeeId);
    }
}
