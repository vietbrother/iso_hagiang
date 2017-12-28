/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ProcessFileDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.PProcessFile;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcessFileService {

    private static ProcessFileService service;

    public static ProcessFileService getInstance() {
        if (service == null) {
            service = new ProcessFileService();
        }
        return service;
    }

    public ResultDTO addProcessFile(PProcessFile p) {
        return ProcessFileDAO.getInstance().addProcessFiles(p);
    }

    public ResultDTO updateProcessFile(PProcessFile p) {
        return ProcessFileDAO.getInstance().updateProcessFiles(p);
    }

    public List<PProcessFile> listPProcessFile(PProcessFile dto) {
        return ProcessFileDAO.getInstance().listProcessFiles(dto);
    }

    public PProcessFile getProcessFileById(String id) {
        return ProcessFileDAO.getInstance().getProcessFilesById(String.valueOf(id));
    }

    public ResultDTO removeProcessFile(String id) {
        return ProcessFileDAO.getInstance().removeProcessFiles(id);
    }

}
