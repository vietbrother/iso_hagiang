/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.AssetGroup;
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
public class AssetGroupDAO extends BaseDAO{
    
    private static AssetGroupDAO dao;

    public static AssetGroupDAO getInstance() {
        if (dao == null) {
            dao = new AssetGroupDAO();
        }
        return dao;
    }

    public List<AssetGroup> listAssetGroups(String key) {
        List<AssetGroup> listAssetGroups = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM AssetGroup eg "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(eg.name) like ? "))
                    + "ORDER BY eg.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listAssetGroups = (List<AssetGroup>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listAssetGroups;
    }

    public ResultDTO addAssetGroup(AssetGroup eg) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(eg);
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

    public ResultDTO updateAssetGroup(AssetGroup newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            AssetGroup eg = (AssetGroup) session.get(AssetGroup.class, newData.getId());
            eg.setName(newData.getName());
            session.update(eg);
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

    public ResultDTO removeAssetGroup(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            AssetGroup et = (AssetGroup) session.get(AssetGroup.class, Integer.valueOf(id));
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

    public AssetGroup getAssetGroupById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        AssetGroup assetGroup = null;
        try {
            session = getSession();
            assetGroup = (AssetGroup) session.get(AssetGroup.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return assetGroup;
    }
}
