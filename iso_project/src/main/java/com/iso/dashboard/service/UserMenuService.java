/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.UserMenuDAO;
import com.iso.dashboard.dto.MUserMenu;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class UserMenuService {

    private static UserMenuService service;

    public static UserMenuService getInstance() {
        if (service == null) {
            service = new UserMenuService();
        }
        return service;
    }

    public ResultDTO addUserMenu(MUserMenu p) {
        return UserMenuDAO.getInstance().addUserMenu(p);
    }

    public ResultDTO updateUserMenu(MUserMenu p) {
        return UserMenuDAO.getInstance().updateUserMenu(p);
    }

    public List<MUserMenu> listUserMenu(String menuId, String userId) {
        return UserMenuDAO.getInstance().getUserMenu(menuId, userId);
    }

    public MUserMenu getUserMenuById(String id) {
        return UserMenuDAO.getInstance().getUserMenuById(id);
    }

    public ResultDTO removeUserMenu(String id) {
        return UserMenuDAO.getInstance().removeUserMenu(id);
    }
    
    public void removeUserByMenuId(String menuId){
        UserMenuDAO.getInstance().removeUserByMenuId(menuId);
    }
    public void removeMenuByUserId(String userId){
        UserMenuDAO.getInstance().removeMenuByUserId(userId);
    }

}
