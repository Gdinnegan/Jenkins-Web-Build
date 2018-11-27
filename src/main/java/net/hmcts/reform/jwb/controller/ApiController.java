package net.hmcts.reform.jwb.controller;

import net.hmcts.reform.jwb.model.JenkinsBuild;
import net.hmcts.reform.jwb.model.JenkinsJob;
import net.hmcts.reform.jwb.services.DatabaseService;
import net.hmcts.reform.jwb.services.JenkinsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping(value = "jobs", method = RequestMethod.GET)
     public List<JenkinsJob> listJobs() {
        List<JenkinsJob> jobs =  databaseService.getJobs();
        return jobs;

    }


    @RequestMapping(value = "builds/{job_id}", method = RequestMethod.GET)
    public List<JenkinsBuild> listBuildsByJenkinsJob(@PathVariable long job_id) {
        List<JenkinsBuild> builds =  databaseService.getBuildsByJenkinsJob(job_id);
        return builds;

    }

}

