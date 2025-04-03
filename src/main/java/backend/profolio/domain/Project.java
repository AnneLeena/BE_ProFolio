package backend.profolio.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //jos json data/rest api käytössä niin tämä tarvitaan @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //jos json data/rest api käytössä niin tämä tarvitaan @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Status cannot be null")
    @ManyToOne
    @JoinColumn(name = "statusId")
    private Status status;

    @NotNull(message = "Types cannot be null")
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
        name = "projectType", // Taulun nimi, jossa yhdistetään projektit ja tyypit
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "typeId")
    )
    private Set<Type> types = new HashSet<>(); // useampi tyyppi mahdollista


    public Project() {

    }

    public Project(
            @NotBlank(message = "Project name cannot be blank") @Size(min = 2, max = 40, message = "Project name must be between 2 and 40 characters") @Pattern(regexp = "^[A-Za-z0-9 _-]+$", message = "Project name must contain only alphanumeric characters and/or (space)/-/_") String projectName,
            LocalDate startDate, LocalDate endDate, Status status, Type... types) {
        super();
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.types = new HashSet<>(Arrays.asList(types));
        //this.types.addAll(Arrays.asList(types));  // Lisää kaikki annetut tyypit
   
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

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", projectName=" + projectName + ", startDate=" + startDate + ", endDate="
                + endDate + ", status=" + this.getStatus() + ", types=" + this.getTypes() + "]";
    }

    }




