/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ProcedureDocumentDAO;
import com.iso.dashboard.dto.MProcedureDocument;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class ProcedureDocumentService {
    
    private static ProcedureDocumentService service;

    public static ProcedureDocumentService getInstance() {
        if (service == null) {
            service = new ProcedureDocumentService();
        }
        return service;
    }

    public ResultDTO addProcedureDocument(MProcedureDocument et) {
        return ProcedureDocumentDAO.getInstance().addProcedureDocument(et);
    }

    public ResultDTO updateProcedureDocument(MProcedureDocument et) {
        return ProcedureDocumentDAO.getInstance().updateProcedureDocument(et);
    }

    public List<MProcedureDocument> listProcedureDocuments(String key) {
        return ProcedureDocumentDAO.getInstance().listProcedureDocuments(key);
    }

    public MProcedureDocument getProcedureDocumentById(String id) {
        return ProcedureDocumentDAO.getInstance().getProcedureDocumentById(String.valueOf(id));
    }

    public ResultDTO removeProcedureDocument(String id) {
        return ProcedureDocumentDAO.getInstance().removeProcedureDocument(id);
    }
    public ResultDTO removeDocumentByProcedureId(String id) {
        return ProcedureDocumentDAO.getInstance().removeDocumentByProcedureId(id);
    }
}
