package net.hmcts.reform.jwb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="jobs")
public class JenkinsJob {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "job_id")
    @JsonProperty(value = "job_id")
    private Long id;

    @OneToMany(mappedBy = "jenkinsJob", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<JenkinsBuild> builds;

    @JsonProperty(value = "display_name")
    private String displayName;

    public List<JenkinsBuild> getBuilds() {return this.builds;}

    public void setBuilds(List<JenkinsBuild> builds) {this.builds = builds;}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
