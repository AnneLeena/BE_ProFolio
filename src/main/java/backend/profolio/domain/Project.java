package backend.profolio.domain;

import java.time.LocalDate;

public class Project {

    String name;
    LocalDate startDate;
    LocalDate endDate;
   
    public Project(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Project [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
       

}
