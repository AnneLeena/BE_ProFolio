package backend.profolio.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//Entity määrittää, että relaatiotietokannassa on Project-niminen taulu

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name cannot be blank")
    @Size(min = 2, max = 40, message = "Project name must be between 2 and 40 characters")
    @Pattern(regexp = "^[A-Za-z0-9 _-]+$", message = "Project name must contain only alphanumeric characters and/or (space)/-/_")
    private String projectName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private Status status;

    public Project() {

    }

    public Project(
            @NotBlank(message = "Project name cannot be blank") @Size(min = 2, max = 40, message = "Project name must be between 2 and 40 characters") @Pattern(regexp = "^[A-Za-z0-9 _-]+$", message = "Project name must contain only alphanumeric characters and/or (space)/-/_") String projectName,
            LocalDate startDate, LocalDate endDate, Status status) {
        super();
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // endDate on suurempi kuin startDate
    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        return endDate != null && startDate != null && endDate.isAfter(startDate);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", projectName=" + projectName + ", startDate=" + startDate + ", endDate="
                + endDate + ", status=" + this.getStatus() + "]";
    }


}
