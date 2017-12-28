/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.AssetClassDAO;
import com.iso.dashboard.dto.AssetClass;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class AssetClassMngtService {
    
    private static AssetClassMngtService service;

    public static AssetClassMngtService getInstance() {
        if (service == null) {
            service = new AssetClassMngtService();
        }
        return service;
    }

    public ResultDTO addAssetClass(AssetClass et) {
        return AssetClassDAO.getInstance().addAssetClass(et);
    }

    public ResultDTO updateAssetClass(AssetClass et) {
        return AssetClassDAO.getInstance().updateAssetClass(et);
    }

    public List<AssetClass> listAssetClass(String key) {
        return AssetClassDAO.getInstance().listAssetClass(key);
    }

    public AssetClass getAssetClassById(String id) {
        return AssetClassDAO.getInstance().getAssetClassById(String.valueOf(id));
    }

    public ResultDTO removeAssetClass(String id) {
        return AssetClassDAO.getInstance().removeAssetClass(id);
    }
}
