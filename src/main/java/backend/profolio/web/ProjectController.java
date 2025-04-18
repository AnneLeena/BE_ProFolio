package backend.profolio.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;
import backend.profolio.domain.Status;
import backend.profolio.domain.StatusRepository;
import backend.profolio.domain.Type;
import backend.profolio.domain.TypeRepository;
import jakarta.validation.Valid;

@Controller

public class ProjectController {

    private final ProjectRepository repository;
    
    public ProjectController (ProjectRepository prepository) {
        this.repository = prepository;
    }

        @Autowired
        private ProjectRepository prepository;

        @Autowired
        private StatusRepository srepository;

        @Autowired
        private TypeRepository trepository;

    @RequestMapping(value="/login")
    public String login() {	
    return "login";
    }	
    
    @GetMapping({ "/", "/projectlist" })
    public String projectlist(Model model) {
        model.addAttribute("projects", prepository.findAll());
        return "projectlist";
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("statuses", srepository.findAll());
        model.addAttribute("types", trepository.findAll());
        return "addproject";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute ("project") Project project, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Errors errors " + project);
            model.addAttribute("project", project);
            model.addAttribute("statuses", srepository.findAll());
            model.addAttribute("types", trepository.findAll());

            return "editproject";
        }
        
        repository.save(project);
        return "redirect:/projectlist";
    }

    @GetMapping("/projectbytypelist")
    public String getProjectsByType(Model model) {
    
        List<Type> types = new ArrayList<>();
        trepository.findAll().forEach(types::add); // Muunnetaan Iterable listaksi
    
        Set<Project> uniqueProjects = new HashSet<>();
    
        for (Type type : types) {
            uniqueProjects.addAll(prepository.findByTypes_TypeNameIgnoreCase(type.getTypeName()));
        }
    
        model.addAttribute("projects", new ArrayList<>(uniqueProjects));  
        model.addAttribute("types", types);
    
        return "projectbytypelist";
    }

    @GetMapping("/projectbystatuslist")
    public String getProjectsByStatus(Model model) {
    
        List<Status> statuses = new ArrayList<>();
        srepository.findAll().forEach(statuses::add);

        Map<Status, List<Project>> projectsByStatus = new LinkedHashMap<>();

        for (Status status : statuses) {
            List<Project> projects = prepository.findByStatus_StatusNameIgnoreCase(status.getStatusName());
            projectsByStatus.put(status, projects); 
        }

        model.addAttribute("projectsByStatus", projectsByStatus);

        return "projectbystatuslist";
    }
    


    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Long projectId, Model model) {
        Project project = prepository.findById(projectId).orElse(null);
        model.addAttribute("project", project);
        model.addAttribute("statuses", srepository.findAll());
        model.addAttribute("types", trepository.findAll());
        return "editproject";
    }
    
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId) {
        prepository.deleteById(projectId);
        return "redirect:/projectlist";
    }

}