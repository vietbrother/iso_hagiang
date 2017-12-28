/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dao;

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
public class JobDAO extends BaseDAO {

    private static JobDAO dao;

    public static JobDAO getInstance() {
        if (dao == null) {
            dao = new JobDAO();
        }
        return dao;
    }

    public List<Job> listJobs(String jobTitle) {
        List<Job> listJobs = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            String sql = "FROM Job jb "
                    + (DataUtil.isNullOrEmpty(jobTitle) ? "" : ("where LOWER(jb.jobTitle) like ? "))
                    + "ORDER BY jb.jobTitle ASC";
            Query query = session.createQuery(sql);
            if (!DataUtil.isNullOrEmpty(jobTitle)) {
                query.setParameter(0, "%" + jobTitle.toLowerCase() + "%");
            }
            listJobs = (List<Job>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listJobs;
    }

    public ResultDTO addJob(Job j) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            int id = (Integer) session.save(j);
            //session.flush();
//            getTransaction().commit();
            session.getTransaction().commit();
            session.getTransaction().commit();
            res.setId(String.valueOf(id));
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public ResultDTO updateJob(Job newData) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            Job j = (Job) session.get(Job.class, newData.getId());
            j.setJobTitle(newData.getJobTitle());
            j.setSalaryGlone(newData.getSalaryGlone());
            j.setSalaryWage(newData.getSalaryWage());
            session.update(j);
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

    public ResultDTO removeJob(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        try {
            session = getSession();
            Job j = (Job) session.get(Job.class, Integer.valueOf(id));
            session.delete(j);
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

    public Job getJobById(String id) {
        ResultDTO res = new ResultDTO(Constants.FAIL, "");
        Session session = null;
        Job jobs = null;
        try {
            session = getSession();
            jobs = (Job) session.get(Job.class, Integer.valueOf(id));
            session.getTransaction().commit();
            res.setKey(Constants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setMessage(e.getMessage());
        }
        return jobs;
    }
}
