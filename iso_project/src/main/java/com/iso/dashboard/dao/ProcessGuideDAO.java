/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CProcessGuide;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class ProcessGuideDAO extends BaseDAO {

    private static ProcessGuideDAO dao;

    public static ProcessGuideDAO getInstance() {
        if (dao == null) {
            dao = new ProcessGuideDAO();
        }
        return dao;
    }

    public List<CProcessGuide> getProcessGuides() {
        List<CProcessGuide> calendars = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CProcessGuide cal "
                    + "ORDER BY cal.processGuideName ASC";
            Query query = session.createQuery(sql);
            calendars = (List<CProcessGuide>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return calendars;
    }

    public ResultDTO addProcessGuide(CProcessGuide p) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(p);
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO updateProcessGuide(CProcessGuide newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CProcessGuide updateObj = (CProcessGuide) session.get(CProcessGuide.class, newData.getProcessGuideId());
            updateObj.setProcessGuideName(newData.getProcessGuideName());
            updateObj.setDescription(newData.getDescription());
            updateObj.setStatus(newData.getStatus());
            updateObj.setPosition(newData.getPosition());
            session.update(updateObj);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO removeProcessGuide(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CProcessGuide removeObj = (CProcessGuide) session.get(CProcessGuide.class, Integer.valueOf(id));
            session.delete(removeObj);
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            res.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public CProcessGuide getProcessGuideById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CProcessGuide objGet = null;
        try {
            session = getSession();
            objGet = (CProcessGuide) session.get(CProcessGuide.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return objGet;
    }

}
