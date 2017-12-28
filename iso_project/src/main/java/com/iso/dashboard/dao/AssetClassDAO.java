/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.AssetClass;
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
public class AssetClassDAO extends BaseDAO{
    
    private static AssetClassDAO dao;

    public static AssetClassDAO getInstance() {
        if (dao == null) {
            dao = new AssetClassDAO();
        }
        return dao;
    }

    public List<AssetClass> listAssetClass(String key) {
        List<AssetClass> listAssetClasss = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM AssetClass eg "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(eg.name) like ? "))
                    + "ORDER BY eg.id ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listAssetClasss = (List<AssetClass>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listAssetClasss;
    }

    public ResultDTO addAssetClass(AssetClass ec) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(ec);
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

    public ResultDTO updateAssetClass(AssetClass newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            AssetClass ec = (AssetClass) session.get(AssetClass.class, newData.getId());
            ec.setName(newData.getName());
            ec.setDescription(newData.getDescription());
            session.update(ec);
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

    public ResultDTO removeAssetClass(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            AssetClass ec = (AssetClass) session.get(AssetClass.class, Integer.valueOf(id));
            session.delete(ec);
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

    public AssetClass getAssetClassById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        AssetClass assetClass = null;
        try {
            session = getSession();
            assetClass = (AssetClass) session.get(AssetClass.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return assetClass;
    }
}
