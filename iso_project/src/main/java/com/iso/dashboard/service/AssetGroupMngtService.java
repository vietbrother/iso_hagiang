/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.AssetGroupDAO;
import com.iso.dashboard.dto.AssetGroup;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class AssetGroupMngtService {
    
    private static AssetGroupMngtService service;

    public static AssetGroupMngtService getInstance() {
        if (service == null) {
            service = new AssetGroupMngtService();
        }
        return service;
    }

    public ResultDTO addAssetGroup(AssetGroup et) {
        return AssetGroupDAO.getInstance().addAssetGroup(et);
    }

    public ResultDTO updateAssetGroup(AssetGroup et) {
        return AssetGroupDAO.getInstance().updateAssetGroup(et);
    }

    public List<AssetGroup> listAssetGroups(String key) {
        return AssetGroupDAO.getInstance().listAssetGroups(key);
    }

    public AssetGroup getAssetGroupById(String id) {
        return AssetGroupDAO.getInstance().getAssetGroupById(String.valueOf(id));
    }

    public ResultDTO removeAssetGroup(String id) {
        return AssetGroupDAO.getInstance().removeAssetGroup(id);
    }
}
