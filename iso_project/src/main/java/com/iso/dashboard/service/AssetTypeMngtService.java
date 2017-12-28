/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.AssetTypeDAO;
import com.iso.dashboard.dto.AssetType;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class AssetTypeMngtService {
    
    private static AssetTypeMngtService service;

    public static AssetTypeMngtService getInstance() {
        if (service == null) {
            service = new AssetTypeMngtService();
        }
        return service;
    }

    public ResultDTO addAssetType(AssetType et) {
        return AssetTypeDAO.getInstance().addAssetType(et);
    }

    public ResultDTO updateAssetType(AssetType et) {
        return AssetTypeDAO.getInstance().updateAssetType(et);
    }

    public List<AssetType> listAssetTypes(String key) {
        return AssetTypeDAO.getInstance().listAssetTypes(key);
    }

    public AssetType getAssetTypeById(String id) {
        return AssetTypeDAO.getInstance().getAssetTypeById(String.valueOf(id));
    }

    public ResultDTO removeAssetType(String id) {
        return AssetTypeDAO.getInstance().removeAssetType(id);
    }
}
