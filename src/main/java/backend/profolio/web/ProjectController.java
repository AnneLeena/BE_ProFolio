package backend.profolio.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import backend.profolio.domain.Project;
import backend.profolio.domain.ProjectRepository;

@Controller
public class ProjectController {

    private ProjectRepository pRepository;

    public ProjectController (ProjectRepository pRepository) {
        this.pRepository = pRepository;
    }

    @GetMapping({ "/", "/projectlist" })
    public String projectlist(Model model) {
        model.addAttribute("projects", pRepository.findAll());
        return "projectlist";
    }

    @GetMapping("/add")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        // lisää tähän statukset
        return "addproject";
    }

    @PostMapping("/save")
    public String save(Project project) {
        pRepository.save(project);
        return "redirect:/projectlist";
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable("id") Long projectId, Model model) {
        Project project = pRepository.findById(projectId).orElse(null);
        model.addAttribute("project", project);
        return "editproject";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long projectId) {
        pRepository.deleteById(projectId);
        return "redirect:/projectlist";
    }

}
