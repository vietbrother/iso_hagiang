/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.FileDAO;
import com.iso.dashboard.dao.UserDAO;
import com.iso.dashboard.dto.FileLevel;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Employee;
import java.util.List;

/**
 *
 * @author 
 */
public class FileMngService {

    private static FileMngService service;

    public static FileMngService getInstance() {
        if (service == null) {
            service = new FileMngService();
        }
        return service;
    }

    public ResultDTO addUser(FileLevel p) {
        return FileDAO.getInstance().addUsers(p);
    }

    public ResultDTO updateUser(FileLevel p) {
        return FileDAO.getInstance().updateUsers(p);
    }

    public List<FileLevel> listUsers(String fileName) {
        return FileDAO.getInstance().listUsers(fileName);
    }

    public FileLevel getUserById(String id) {
        return FileDAO.getInstance().getUsersById(String.valueOf(id));
    }

    public ResultDTO removeUser(String id) {
        return FileDAO.getInstance().removeUsers(id);
    }

}
