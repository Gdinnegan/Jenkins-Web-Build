package net.hmcts.reform.jwb.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="builds")
public class JenkinsBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "build_id")
    @JsonProperty(value = "build_id")
    private Long id;

    private int number;

    private String displayName;

    @JsonProperty(value = "build_result")
    private String result;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id", nullable = false)
    JenkinsJob jenkinsJob;

    public JenkinsJob getJenkinsJob() {
        return jenkinsJob;
    }

    public void setJenkinsJob(JenkinsJob jenkinsJob) {
        this.jenkinsJob = jenkinsJob;
    }

    public String getDisplayName() {return displayName;}

    public void setDisplayName(String displayName) {this.displayName = displayName;}

    public String getResult() {
        return result;
    }

    public void setResult(String result) { this.result = result; }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JenkinsBuild that = (JenkinsBuild) o;
        return number == that.number &&
                Objects.equals(id, that.id) &&
                Objects.equals(displayName, that.displayName) &&
                Objects.equals(result, that.result) &&
                Objects.equals(jenkinsJob, that.jenkinsJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, displayName, result, jenkinsJob);
    }
}
