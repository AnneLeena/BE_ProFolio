package backend.profolio.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity

public class Status {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long statusId;

    @NotBlank
    private String statusName;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "status")
    @JsonIgnore
    private List<Project> projects;
    
    public Status() {

    }

    public Status (String statusName) {
        super();
        this.statusName = statusName;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Status [statusId=" + statusId + ", statusName=" + statusName + "]";
    }

}