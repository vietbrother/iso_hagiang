/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ProcessDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.PProcess;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcessService {

    private static ProcessService service;

    public static ProcessService getInstance() {
        if (service == null) {
            service = new ProcessService();
        }
        return service;
    }

    public ResultDTO addProcess(PProcess p) {
        return ProcessDAO.getInstance().addProcesss(p);
    }

    public ResultDTO updateProcess(PProcess p) {
        return ProcessDAO.getInstance().updateProcesss(p);
    }

    public List<PProcess> listPProcess(PProcess dto) {
        return ProcessDAO.getInstance().listProcesss(dto);
    }

    public PProcess getProcessById(String id) {
        return ProcessDAO.getInstance().getProcesssById(String.valueOf(id));
    }

    public ResultDTO removeProcess(String id) {
        return ProcessDAO.getInstance().removeProcesss(id);
    }

}
