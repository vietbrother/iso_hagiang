/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.ProviderDAO;
import com.iso.dashboard.dto.Provider;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class ProviderMngtService {

    private static ProviderMngtService service;

    public static ProviderMngtService getInstance() {
        if (service == null) {
            service = new ProviderMngtService();
        }
        return service;
    }

    public ResultDTO addProvider(Provider et) {
        return ProviderDAO.getInstance().addProvider(et);
    }

    public ResultDTO updateProvider(Provider et) {
        return ProviderDAO.getInstance().updateProvider(et);
    }

    public List<Provider> listProviders(String key) {
        return ProviderDAO.getInstance().listProviders(key);
    }

    public Provider getProviderById(String id) {
        return ProviderDAO.getInstance().getProviderById(String.valueOf(id));
    }

    public ResultDTO removeProvider(String id) {
        return ProviderDAO.getInstance().removeProvider(id);
    }
}
