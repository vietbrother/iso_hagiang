/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.AssetDAO;
import com.iso.dashboard.dto.Asset;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class AssetMngtService {
    
    private static AssetMngtService service;

    public static AssetMngtService getInstance() {
        if (service == null) {
            service = new AssetMngtService();
        }
        return service;
    }

    public ResultDTO addAsset(Asset et) {
        return AssetDAO.getInstance().addAsset(et);
    }

    public ResultDTO updateAsset(Asset et) {
        return AssetDAO.getInstance().updateAsset(et);
    }

    public List<Asset> listAssets(String key) {
        return AssetDAO.getInstance().listAssets(key);
    }

    public Asset getAssetById(String id) {
        return AssetDAO.getInstance().getAssetById(String.valueOf(id));
    }

    public ResultDTO removeAsset(String id) {
        return AssetDAO.getInstance().removeAsset(id);
    }
}
