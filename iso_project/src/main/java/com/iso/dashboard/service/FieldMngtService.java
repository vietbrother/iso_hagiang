/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.FieldDAO;
import com.iso.dashboard.dto.CField;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class FieldMngtService {
    
    private static FieldMngtService service;

    public static FieldMngtService getInstance() {
        if (service == null) {
            service = new FieldMngtService();
        }
        return service;
    }

    public ResultDTO addField(CField et) {
        return FieldDAO.getInstance().addField(et);
    }

    public ResultDTO updateField(CField et) {
        return FieldDAO.getInstance().updateField(et);
    }

    public List<CField> listFields(String key) {
        return FieldDAO.getInstance().listFields(key);
    }

    public CField getFieldById(String id) {
        return FieldDAO.getInstance().getFieldById(String.valueOf(id));
    }

    public ResultDTO removeField(String id) {
        return FieldDAO.getInstance().removeField(id);
    }
}
