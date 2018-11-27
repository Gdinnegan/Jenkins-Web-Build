package net.hmcts.reform.jwb.services;

import com.google.common.base.Strings;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import com.sun.net.httpserver.Authenticator;
import net.hmcts.reform.jwb.model.JenkinsBuild;
import net.hmcts.reform.jwb.model.JenkinsJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.transform.Result;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Service
public class JenkinsApiService {

    private JenkinsServer jenkinsServer;

    @Autowired
    private JenkinsJobRepository jobRepository;

    @Autowired
    private JenkinsBuildRepository buildRepository;

    @Value("${app.config.jenkins.url}")
    private String jenkinsUrl;

    @Value("${app.config.jenkins.username}")
    private String jenkinsUsername;

    @Value("${app.config.jenkins.password}")
    private String jenkinsPassword;


    @PostConstruct
    public void init() throws URISyntaxException {
        //jenkinsServer = new JenkinsServer(new URI(jenkinsUrl), jenkinsUsername, jenkinsPassword);
        jenkinsServer = new JenkinsServer(new URI(jenkinsUrl));

    }

    @Scheduled(fixedDelayString = "${app.config.jenkins.job.poll.intervalMilliseconds}")
    public void pollJenkinsApi() {
        List<String> jobNames = new ArrayList<String>();

        try {
            // Use Jenkins API to get all Jobs
            Map<String, Job> jobMap = jenkinsServer.getJobs();

            // For each Job returned from the API
            for (Map.Entry<String, Job> entry : jobMap.entrySet())
            {
                String apiDisplayName = entry.getKey();
                JenkinsJob jenkinsJob = populateJenkinsJobs(apiDisplayName);

                Job apiJob = entry.getValue();
                JobWithDetails apiJobDetails = apiJob.details();
                List<Build> apiBuilds = apiJobDetails.getAllBuilds();
                populateBuilds(jenkinsJob, apiBuilds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JenkinsJob populateJenkinsJobs(String displayName){
        boolean exists = jobRepository.existsByDisplayName(displayName);
        // If it exists, get the existing job from DB
        JenkinsJob jenkinsJob = null;
        if (!exists) {
            // Otherwise, create a new job and save it to DB
            jenkinsJob = new JenkinsJob();
            jenkinsJob.setDisplayName(displayName);
            jenkinsJob.setBuilds(new ArrayList<JenkinsBuild>());
            jobRepository.save(jenkinsJob);
        } else {
            List<JenkinsJob> jenkinsJobList = jobRepository.findByDisplayName(displayName);
            jenkinsJob = jenkinsJobList.get(0);
        }

        return jenkinsJob;
    }

    private void populateBuilds(JenkinsJob jenkinsJob, List<Build> builds) {
        // For each Build in the list
        List<JenkinsBuild> jenkinsBuilds = new ArrayList<JenkinsBuild>();
        for(Build build : builds){
            JenkinsBuild jenkinsBuild = new JenkinsBuild();
            jenkinsBuild.setNumber(build.getNumber());
            try {
                BuildWithDetails buildDetails = build.details();
                BuildResult result = buildDetails.getResult();
                jenkinsBuild.setResult(Integer.toString(result.ordinal()));
                jenkinsBuild.setDisplayName(buildDetails.getDisplayName());
                jenkinsBuilds.add(jenkinsBuild);
                jenkinsBuild.setJenkinsJob(jenkinsJob);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        jenkinsJob.setBuilds(jenkinsBuilds);
        jobRepository.save(jenkinsJob);
        }
    }

    /*

    public List<JenkinsBuild> getBuild(){ return (List) buildRepository.findAll(); }

    @Scheduled(fixedDelayString = "${app.config.jenkins.job.poll.intervalMilliseconds}")
    public List<String> pollJenkinsForbuilds() {
        List<String> buildResult = new ArrayList<String>();

        try {
            QueueItem qi = new QueueItem();

            qi.se
            Map<String, Build> buildMap = jenkinsServer.get

            for (Map.Entry<String, Build> entry : buildMap.entrySet())
            {
                // Check if the job exists in the DB
                String result = entry.getKey();
                boolean exists = buildRepository.existsByResult(result);
                // If it exists, get the existing job from DB
                JenkinsBuild jenkinsBuild = null;
                if (exists) {
                    List<JenkinsBuild> existingBuild = buildRepository.findByResult(result);
                    if (existingBuild != null){
                        jenkinsBuild = existingBuild.get(0);
                    }
                }else{
                    // Otherwise, create a new job and save it to DB
                    jenkinsBuild = new JenkinsBuild();
                    jenkinsBuild.setResult(result);
                    buildRepository.save(jenkinsBuild);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return buildResult;
    }
*/




