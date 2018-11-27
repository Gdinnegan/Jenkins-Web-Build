package net.hmcts.reform.jwb.services;

import net.hmcts.reform.jwb.model.JenkinsBuild;
import net.hmcts.reform.jwb.model.JenkinsJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private JenkinsJobRepository jobRepository;

    @Autowired
    private JenkinsBuildRepository buildRepository;

    public List<JenkinsJob> getJobs(){ return (List) jobRepository.findAll(); }

    public List<JenkinsBuild> getBuilds(){ return (List) buildRepository.findAll(); }


    public JenkinsJob getJob(String displayName){
        JenkinsJob jenkinsJob = null;
        List<JenkinsJob> jenkinsJobs = jobRepository.findByDisplayName(displayName);
        if (jenkinsJobs != null){
            jenkinsJob = jenkinsJobs.get(0);
        }
        return jenkinsJob;
    }

    public List<JenkinsBuild> getBuildsByJenkinsJob(long job_id){
        JenkinsJob jenkinsJob = jobRepository.findOne(job_id);
        return buildRepository.findByJenkinsJob(jenkinsJob);
    }
}
