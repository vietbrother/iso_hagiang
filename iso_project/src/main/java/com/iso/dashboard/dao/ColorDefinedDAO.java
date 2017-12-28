/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CColorDefined;
import com.iso.dashboard.dto.ResultDTO;
import com.iso.dashboard.dto.Employee;
import com.iso.dashboard.utils.Constants;
import com.iso.dashboard.utils.DataUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author VIET_BROTHER
 */
public class ColorDefinedDAO extends BaseDAO {

    private static ColorDefinedDAO dao;

    public static ColorDefinedDAO getInstance() {
        if (dao == null) {
            dao = new ColorDefinedDAO();
        }
        return dao;
    }

    public List<CColorDefined> listColor() {
        List<CColorDefined> list = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CColorDefined u "
                    + "ORDER BY u.colorName ASC";
            Query query = session.createQuery(sql);
            list = (List<CColorDefined>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return list;
    }

}
