/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ProcessHisDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.PProcessHis;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcessHisService {

    private static ProcessHisService service;

    public static ProcessHisService getInstance() {
        if (service == null) {
            service = new ProcessHisService();
        }
        return service;
    }

    public ResultDTO addProcessHis(PProcessHis p) {
        return ProcessHisDAO.getInstance().addProcessHis(p);
    }

    public ResultDTO updateProcessHis(PProcessHis p) {
        return ProcessHisDAO.getInstance().updateProcessHis(p);
    }

    public List<PProcessHis> listPProcessHis(PProcessHis dto) {
        return ProcessHisDAO.getInstance().listProcessHis(dto);
    }

    public PProcessHis getProcessHisById(String id) {
        return ProcessHisDAO.getInstance().getProcessHisById(String.valueOf(id));
    }

    public ResultDTO removeProcessHis(String id) {
        return ProcessHisDAO.getInstance().removeProcessHis(id);
    }

}
