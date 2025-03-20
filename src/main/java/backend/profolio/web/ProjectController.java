package backend.profolio.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import backend.profolio.domain.Project;

@Controller
public class ProjectController {

    public static List<Project> projects = new ArrayList<>();

    static {
        projects.add(new Project("Project A", LocalDate.of(2024, 10, 05), LocalDate.of(2025, 04, 30)));
        projects.add(new Project("Project H", LocalDate.of(2023, 11, 27), LocalDate.of(2024, 9, 17)));
        projects.add(new Project("Project M", LocalDate.of(2025, 3, 16), LocalDate.of(2025, 6, 5)));

    }

    @GetMapping({ "/", "/projectlist" })
    public String projectlist(Model model) {
        model.addAttribute("projects", projects);
        return "projectlist";
    }

    @GetMapping("/add")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        // lisää tähän statukset
        return "addproject";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("project") Project project) {
        projects.add(project);

        return "redirect:/projectlist";
    }

    //lisää delete + edit-ominaisuus, kun id-tehty

}
