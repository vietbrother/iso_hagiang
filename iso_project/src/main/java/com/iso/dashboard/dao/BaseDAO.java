/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.utils.HibernateUtil;
import java.util.Date;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;

/**
 *
 * @author TUYENNV1
 */
public abstract class BaseDAO {

    public Session getSession() {
        return HibernateUtil.getSessionAndBeginTransaction();
    }

    public Date getSysdate()
            throws Exception {
        String strQuery = "SELECT SYSDATE as system_datetime ";
        SQLQuery queryObject = getSession().createSQLQuery(strQuery);
        queryObject.addScalar("system_datetime", StandardBasicTypes.TIMESTAMP);
        Date sysdate = (Date) queryObject.uniqueResult();
        return sysdate;
    }

}
