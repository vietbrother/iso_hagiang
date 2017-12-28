/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ProcedureDAO;
import com.iso.dashboard.dto.CProcedure;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class ProcedureMngtService {
    
    private static ProcedureMngtService service;

    public static ProcedureMngtService getInstance() {
        if (service == null) {
            service = new ProcedureMngtService();
        }
        return service;
    }

    public ResultDTO addProcedure(CProcedure et) {
        return ProcedureDAO.getInstance().addProcedure(et);
    }

    public ResultDTO updateProcedure(CProcedure et) {
        return ProcedureDAO.getInstance().updateProcedure(et);
    }

    public List<CProcedure> listProcedures(String key) {
        return ProcedureDAO.getInstance().listProcedures(key);
    }

    public CProcedure getProcedureById(String id) {
        return ProcedureDAO.getInstance().getProcedureById(String.valueOf(id));
    }

    public ResultDTO removeProcedure(String id) {
        return ProcedureDAO.getInstance().removeProcedure(id);
    }
}
