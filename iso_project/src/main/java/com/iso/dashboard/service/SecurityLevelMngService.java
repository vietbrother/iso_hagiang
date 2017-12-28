/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.SecurityLevelDAO;
import com.iso.dashboard.dao.UserDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.SecurityLevel;
import com.iso.dashboard.dto.Employee;
import java.util.List;

/**
 *
 * @author 
 */
public class SecurityLevelMngService {

    private static SecurityLevelMngService service;

    public static SecurityLevelMngService getInstance() {
        if (service == null) {
            service = new SecurityLevelMngService();
        }
        return service;
    }

    public ResultDTO addUser(Employee p) {
        return new ResultDTO();//UserDAO.getInstance().addUsers(p);
    }

    public ResultDTO updateUser(Employee p) {
        return new ResultDTO();//return UserDAO.getInstance().updateUsers(p);
    }

    public List<SecurityLevel> listUsers(String username) {
        return SecurityLevelDAO.getInstance().listUsers(username);
    }

    public Employee getUserById(String id) {
        return new Employee();//return UserDAO.getInstance().getUsersById(String.valueOf(id));
    }

    public ResultDTO removeUser(String id) {
        return UserDAO.getInstance().removeUsers(id);
    }

}
