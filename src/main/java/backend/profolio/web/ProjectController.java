package backend.profolio.web;

import org.springframework.beans.factory.annotation.Autowired;
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
import backend.profolio.domain.StatusRepository;
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

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Long projectId, Model model) {
        Project project = prepository.findById(projectId).orElse(null);
        model.addAttribute("project", project);
        model.addAttribute("statuses", srepository.findAll());
        model.addAttribute("types", trepository.findAll());
        return "editproject";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId) {
        prepository.deleteById(projectId);
        return "redirect:/projectlist";
    }

}
