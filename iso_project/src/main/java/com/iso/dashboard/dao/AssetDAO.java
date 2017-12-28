/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.Asset;
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
public class AssetDAO extends BaseDAO{
    
    private static AssetDAO dao;

    public static AssetDAO getInstance() {
        if (dao == null) {
            dao = new AssetDAO();
        }
        return dao;
    }

    public List<Asset> listAssets(String key) {
        List<Asset> listAssets = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM Asset ast "
                    + (DataUtil.isNullOrEmpty(key) ? "" : ("where LOWER(ast.name) like ? "))
                    + "ORDER BY ast.name ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(key)) {
                query.setParameter(0, "%" + key.toLowerCase() + "%");
            }
            listAssets = (List<Asset>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listAssets;
    }

    public ResultDTO addAsset(Asset ast) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(ast);
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

    public ResultDTO updateAsset(Asset newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            Asset ast = (Asset) session.get(Asset.class, newData.getId());
            ast.setName(newData.getName());
            session.update(ast);
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

    public ResultDTO removeAsset(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            Asset ast = (Asset) session.get(Asset.class, Integer.valueOf(id));
            session.delete(ast);
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

    public Asset getAssetById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        Asset ast = null;
        try {
            session = getSession();
            ast = (Asset) session.get(Asset.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return ast;
    }
}
