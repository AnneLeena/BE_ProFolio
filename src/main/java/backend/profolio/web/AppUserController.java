package backend.profolio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import backend.profolio.domain.AppUserRepository;


@Controller
public class AppUserController {

     @Autowired
    private AppUserRepository urepository;


    @GetMapping("/appuserlist")
    public String appuserlist(Model model) {
        model.addAttribute("appUsers", urepository.findAll());
        return "appuserlist";
    }
   
}
