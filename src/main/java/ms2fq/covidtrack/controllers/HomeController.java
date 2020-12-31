package ms2fq.covidtrack.controllers;

import ms2fq.covidtrack.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService cvds;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("locationStats", cvds.getStatsList());
        return "home";
    }
}
