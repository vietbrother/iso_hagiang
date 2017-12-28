/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.service;

import com.iso.dashboard.dao.JobDAO;
import com.iso.dashboard.dto.Job;
import com.iso.dashboard.dto.ResultDTO;
import java.util.List;

/**
 *
 * @author Thuclt-VNPTTech
 */
public class JobMngtService {

    private static JobMngtService service;

    public static JobMngtService getInstance() {
        if (service == null) {
            service = new JobMngtService();
        }
        return service;
    }

    public ResultDTO addJob(Job j) {
        return JobDAO.getInstance().addJob(j);
    }

    public ResultDTO updateJob(Job j) {
        return JobDAO.getInstance().updateJob(j);
    }

    public List<Job> listJobs(String title) {
        return JobDAO.getInstance().listJobs(title);
    }

    public Job getJobById(String id) {
        return JobDAO.getInstance().getJobById(String.valueOf(id));
    }

    public ResultDTO removeJob(String id) {
        return JobDAO.getInstance().removeJob(id);
    }
}
