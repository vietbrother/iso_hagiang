/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

import com.iso.dashboard.dto.CCalendar;
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
public class CalendarDAO extends BaseDAO {

    private static CalendarDAO dao;

    public static CalendarDAO getInstance() {
        if (dao == null) {
            dao = new CalendarDAO();
        }
        return dao;
    }

    public List<CCalendar> getCalendars() {
        List<CCalendar> calendars = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM CCalendar cal "
                    + "ORDER BY cal.calendarName ASC";
            Query query = session.createQuery(sql);
            calendars = (List<CCalendar>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return calendars;
    }

    public ResultDTO addCalendar(CCalendar p) {
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

    public ResultDTO updateCalendar(CCalendar newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CCalendar cal = (CCalendar) session.get(CCalendar.class, newData.getCalendarId());
            cal.setCalendarName(newData.getCalendarName());
            cal.setColor(newData.getColor());
            cal.setDescription(newData.getDescription());
            cal.setStatus(newData.getStatus());
            cal.setWorkingDate(newData.getWorkingDate());
            session.update(cal);
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

    public ResultDTO removeCalendar(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            CCalendar u = (CCalendar) session.get(CCalendar.class, Integer.valueOf(id));
            session.delete(u);
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

    public CCalendar getCalendarById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        CCalendar users = null;
        try {
            session = getSession();
            users = (CCalendar) session.get(CCalendar.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            if(session != null && session.isOpen()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return users;
    }

}
