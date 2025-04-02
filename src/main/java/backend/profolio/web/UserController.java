package backend.profolio.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import backend.profolio.domain.AppUser;
import backend.profolio.domain.AppUserRepository;
import backend.profolio.domain.SignupForm;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private AppUserRepository urepository;

    public UserController(AppUserRepository ureporitory) {
		this.urepository = ureporitory; 
	}

    @RequestMapping(value = "/signup")
    public String addUser(Model model) {
        model.addAttribute("signupform", new SignupForm());
        return "signup";
    }

    @PostMapping("/saveuser")
    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Jos lomakkeessa on validointivirheitä, palautetaan "signup" sivulle
            return "signup";
        }

        // Tarkistetaan, että salasana ja vahvistus salasana ovat samat
        if (!signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");
            return "signup";
        }

        // Tarkistetaan, onko käyttäjänimi jo olemassa
        if (urepository.findByUsername(signupForm.getUsername()) != null) {
            bindingResult.rejectValue("username", "err.username", "Username already exists");
            return "signup";
        }

        // Kryptataan salasana ja luodaan uusi käyttäjä
        String hashedPassword = new BCryptPasswordEncoder().encode(signupForm.getPassword());
        AppUser newUser = new AppUser();
        newUser.setUsername(signupForm.getUsername());
        newUser.setPasswordHash(hashedPassword);
        newUser.setRole("USER");

        urepository.save(newUser);

        return "redirect:/login"; // Ohjataan kirjautumissivulle rekisteröinnin jälkeen
    }
}
