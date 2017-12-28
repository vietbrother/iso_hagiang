/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.AssetType;
import com.iso.dashboard.dto.Job;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class AssetTypeDAO extends BaseDAO{
    
    private static AssetTypeDAO dao;

    public static AssetTypeDAO getInstance() {
        if (dao == null) {
            dao = new AssetTypeDAO();
        }
        return dao;
    }

    public List<AssetType> listAssetTypes(String key) {
        List<AssetType> listAssetTypes = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM AssetType et "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(et.name) like ? "))
                    + "ORDER BY et.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listAssetTypes = (List<AssetType>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listAssetTypes;
    }

    public ResultDTO addAssetType(AssetType et) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(et);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.setMessage(ex.getMessage());
        }
        return res;
    }

    public ResultDTO updateAssetType(AssetType newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            AssetType et = (AssetType) session.get(AssetType.class, newData.getId());
            et.setName(newData.getName());
            session.update(et);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeAssetType(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            AssetType et = (AssetType) session.get(AssetType.class, Integer.valueOf(id));
            session.delete(et);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public AssetType getAssetTypeById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        AssetType assetTypes = null;
        try {
            session = getSession();
            assetTypes = (AssetType) session.get(AssetType.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return assetTypes;
    }
}
