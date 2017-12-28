/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.UnitDAO;
import com.iso.dashboard.dto.CUnit;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class CUnitMngtService {
    
    private static CUnitMngtService service;

    public static CUnitMngtService getInstance() {
        if (service == null) {
            service = new CUnitMngtService();
        }
        return service;
    }

    public ResultDTO addUnit(CUnit et) {
        return UnitDAO.getInstance().addUnit(et);
    }

    public ResultDTO updateUnit(CUnit et) {
        return UnitDAO.getInstance().updateUnit(et);
    }

    public List<CUnit> listUnits(String key) {
        return UnitDAO.getInstance().listUnits(key);
    }

    public CUnit getUnitById(String id) {
        return UnitDAO.getInstance().getUnitById(String.valueOf(id));
    }

    public ResultDTO removeUnit(String id) {
        return UnitDAO.getInstance().removeUnit(id);
    }
}
