package backend.profolio.web;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;
import backend.profolio.domain.StatusRepository;
import backend.profolio.domain.TypeRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

//rest-rajapintaan liittyvää, responsebody palauttaa tiedot json-muodossa
//kun käytetään restController annotaatiota, niin responseBodya ei joka paikassa tarvitse käyttää
//sillä se määrittää kaiken palautuvan tiedon jsoniksi

@RestController
public class ProjectRESTController {

    private static final Logger log = LoggerFactory.getLogger(ProjectRESTController.class);

    private final ProjectRepository prepository;
    private StatusRepository srepository;
    private TypeRepository trepository;

    public ProjectRESTController(ProjectRepository prepository, StatusRepository srepository,
            TypeRepository trepository) {
        this.prepository = prepository;
        this.srepository = srepository;
        this.trepository = trepository;
    }

    // return list of projects
    @GetMapping("/projects")
    public Iterable<Project> getProjects() {
        log.info("fetch & return projects");
        return prepository.findAll();
    }

    // add a new project
    @PostMapping("/projects")
    Project newProject(@RequestBody Project newProject) {
        log.info("save a new project " + newProject);
        return prepository.save(newProject);
    }

    // find a specific project
    @GetMapping("/projects/{id}")
    Optional<Project> getProject(@PathVariable Long id) {
        log.info("find project, id = " + id);
        return prepository.findById(id);
    }

    // edit project
    @PutMapping("/projects/{id}")
    Project editProject(@RequestBody Project editedProject, @PathVariable Long id) {
        log.info("edit project " + editedProject);
        editedProject.setId(id);
        return prepository.save(editedProject);
    }

    // delete project
    @DeleteMapping("/projects/{id}")
    public Iterable<Project> deleteProject(@PathVariable Long id) {
        log.info("delete project, id = " + id);
        prepository.deleteById(id);
        return prepository.findAll();
    }

    // find project by name
    @GetMapping("/projects/name/{projectName}")
    List<Project> getProjectByName(@PathVariable String projectName) {
        log.info("find projects = " + projectName);
        return prepository.findByProjectName(projectName);
    }

    // find projects by status
    @GetMapping("/projects/status/{statusName}")
    List<Project> getProjectsByStatus(@PathVariable String statusName) {
        log.info("find projects, status = " + statusName);
        return prepository.findByStatus_StatusName(statusName);
    }

    // find projects by type
    @GetMapping("/projects/type/{typeName}")
    List<Project> getProjectsByType(@PathVariable String typeName) {
        log.info("find projects, types = " + typeName);
        return prepository.findByTypes_TypeName(typeName);
    }

}
