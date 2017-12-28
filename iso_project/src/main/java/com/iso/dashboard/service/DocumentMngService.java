/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.DocumentDAO;
import com.iso.dashboard.dao.FileDAO;
import com.iso.dashboard.dao.UserDAO;
import com.iso.dashboard.dto.DocumentDTO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Employee;
import java.util.List;

/**
 *
 * @author 
 */
public class DocumentMngService {

    private static DocumentMngService service;

    public static DocumentMngService getInstance() {
        if (service == null) {
            service = new DocumentMngService();
        }
        return service;
    }

    public ResultDTO addDocument(DocumentDTO p) {
        return DocumentDAO.getInstance().addDocument(p);
    }

    public ResultDTO updateDocument(DocumentDTO p) {
        return DocumentDAO.getInstance().updateDocument(p);
    }

    public List<DocumentDTO> listDocuments(String fileName) {
        return DocumentDAO.getInstance().listDocuments(fileName);
    }

    public DocumentDTO getDocumentById(String id) {
        return DocumentDAO.getInstance().getDocumentById(String.valueOf(id));
    }

    public ResultDTO removeDocument(String id) {
        return DocumentDAO.getInstance().removeDocument(id);
    }

}
