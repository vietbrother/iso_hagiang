/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.OrganizationDAO;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Organization;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class OrganizationMngService {

    private static OrganizationMngService service;

    public static OrganizationMngService getInstance() {
        if (service == null) {
            service = new OrganizationMngService();
        }
        return service;
    }

    public ResultDTO addOrganization(Organization p) {
        return OrganizationDAO.getInstance().addOrganization(p);
    }

    public ResultDTO updateOrganization(Organization p) {
        return OrganizationDAO.getInstance().updateOrganization(p);
    }

    public List<Organization> listOrganization(String id, String parentId) {
        return OrganizationDAO.getInstance().listOrganization(id, parentId);
    }

    public Organization getOrganizationById(String id) {
        return OrganizationDAO.getInstance().getOrganizationById(String.valueOf(id));
    }

    public ResultDTO removeOrganization(String id) {
        return OrganizationDAO.getInstance().removeOrganization(id);
    }

}
