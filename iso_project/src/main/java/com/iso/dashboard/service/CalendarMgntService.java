/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.CalendarDAO;
import com.iso.dashboard.dto.CCalendar;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author VIET_BROTHER
 */
public class CalendarMgntService {

    private static CalendarMgntService service;

    public static CalendarMgntService getInstance() {
        if (service == null) {
            service = new CalendarMgntService();
        }
        return service;
    }

    public ResultDTO addCalendar(CCalendar p) {
        return CalendarDAO.getInstance().addCalendar(p);
    }

    public ResultDTO updateCalendar(CCalendar p) {
        return CalendarDAO.getInstance().updateCalendar(p);
    }

    public List<CCalendar> getCalendars() {
        return CalendarDAO.getInstance().getCalendars();
    }

    public CCalendar getCalendarById(String id) {
        return CalendarDAO.getInstance().getCalendarById(String.valueOf(id));
    }

    public ResultDTO removeCalendar(String id) {
        return CalendarDAO.getInstance().removeCalendar(id);
    }

}
