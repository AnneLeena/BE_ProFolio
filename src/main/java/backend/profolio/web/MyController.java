package backend.profolio.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @RequestMapping("/main")
    @ResponseBody
    public String returnMessage() {
        return "Tästä se alkaa";
    }

}
