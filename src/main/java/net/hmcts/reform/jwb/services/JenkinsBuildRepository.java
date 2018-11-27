package net.hmcts.reform.jwb.services;

import net.hmcts.reform.jwb.model.JenkinsBuild;
import net.hmcts.reform.jwb.model.JenkinsJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JenkinsBuildRepository extends CrudRepository<JenkinsBuild, Long> {

    boolean existsByResult(String result);

    List<JenkinsBuild> findByResult(String result);

    boolean existsByNumber(int number);

    List<JenkinsBuild> findByNumber(int number);

    List<JenkinsBuild> findByJenkinsJob(JenkinsJob jenkinsJob);
}