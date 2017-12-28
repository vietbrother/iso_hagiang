/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.UserDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.User_;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class UserMngService {

    private static UserMngService service;

    public static UserMngService getInstance() {
        if (service == null) {
            service = new UserMngService();
        }
        return service;
    }

    public ResultDTO addUser(User_ p) {
        return UserDAO.getInstance().addUsers(p);
    }

    public ResultDTO updateUser(User_ p) {
        return UserDAO.getInstance().updateUsers(p);
    }

    public List<User_> listUsers(String username) {
        return UserDAO.getInstance().listUsers(username);
    }

    public User_ getUserById(String id) {
        return UserDAO.getInstance().getUsersById(String.valueOf(id));
    }

    public ResultDTO removeUser(String id) {
        return UserDAO.getInstance().removeUsers(id);
    }

}
