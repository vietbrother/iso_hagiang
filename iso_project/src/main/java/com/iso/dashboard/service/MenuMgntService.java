/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.MenuDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.CMenu;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class MenuMgntService {

    private static MenuMgntService service;

    public static MenuMgntService getInstance() {
        if (service == null) {
            service = new MenuMgntService();
        }
        return service;
    }

    public ResultDTO addMenu(CMenu p) {
        return MenuDAO.getInstance().addCMenu(p);
    }

    public ResultDTO updateMenu(CMenu p) {
        return MenuDAO.getInstance().updateCMenu(p);
    }

    public List<CMenu> listCMenu(String menuName) {
        return MenuDAO.getInstance().listCMenu(menuName);
    }
    public List<CMenu> listParentMenu(String menuName) {
        return MenuDAO.getInstance().listParentMenu(menuName);
    }

    public CMenu getMenuById(String id) {
        return MenuDAO.getInstance().getCMenuById(String.valueOf(id));
    }

    public ResultDTO removeMenu(String id) {
        return MenuDAO.getInstance().removeCMenu(id);
    }

}
