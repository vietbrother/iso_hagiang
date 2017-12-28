/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.EmployeeRewardDAO;
import com.iso.dashboard.dto.EmployeeReward;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class EmployeeRewardMngtService {
    
    private static EmployeeRewardMngtService service;

    public static EmployeeRewardMngtService getInstance() {
        if (service == null) {
            service = new EmployeeRewardMngtService();
        }
        return service;
    }

    public ResultDTO addEmployeeReward(EmployeeReward er) {
        return EmployeeRewardDAO.getInstance().addEmployeeReward(er);
    }

    public ResultDTO updateEmployeeReward(EmployeeReward er) {
        return EmployeeRewardDAO.getInstance().updateEmployeeReward(er);
    }

    public List<EmployeeReward> listEmployeeReward(String key) {
        return EmployeeRewardDAO.getInstance().listEmployeeRewards(key);
    }

    public EmployeeReward getEmployeeRewardById(String id) {
        return EmployeeRewardDAO.getInstance().getEmployeeRewardById(String.valueOf(id));
    }

    public ResultDTO removeEmployeeReward(String id) {
        return EmployeeRewardDAO.getInstance().removeEmployeeReward(id);
    }

    public List<EmployeeReward> getEmployeeRewardByEmployeeId(int employeeId) {
        return EmployeeRewardDAO.getInstance().getEmployeeRewardByEmployeeId(employeeId);
    }
}
