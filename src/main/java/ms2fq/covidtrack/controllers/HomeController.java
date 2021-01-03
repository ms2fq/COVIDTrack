package ms2fq.covidtrack.controllers;

import ms2fq.covidtrack.models.LocationStats;
import ms2fq.covidtrack.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService cvds;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> statsList = cvds.getStatsList();
        int trc = statsList.stream().mapToInt(stat -> stat.getLatestTotal()).sum();
        int tnc = statsList.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", statsList);
        model.addAttribute("totalReportedCases", trc);
        model.addAttribute("totalNewCases", tnc);
        return "home";
    }
}
