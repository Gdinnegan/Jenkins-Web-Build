package net.hmcts.reform.jwb.services;

import net.hmcts.reform.jwb.model.JenkinsJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JenkinsJobRepository extends CrudRepository<JenkinsJob, Long> {

    boolean existsByDisplayName(String displayName);

    List<JenkinsJob> findByDisplayName(String displayName);

}