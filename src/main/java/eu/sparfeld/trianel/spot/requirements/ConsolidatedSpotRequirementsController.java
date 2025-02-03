package eu.sparfeld.trianel.spot.requirements;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/consolidated-spot-requirements")
public class ConsolidatedSpotRequirementsController {
    private final ConsolidatedSpotRequirementsService service;

    public ConsolidatedSpotRequirementsController(ConsolidatedSpotRequirementsService service) {
        this.service = service;
    }

    @GetMapping("/{day}")
    public ConsolidatedSpotRequirementsResponse consolidatedSpotRequirements(@PathVariable LocalDate day) {
        return service.consolidateSpotRequirements(day);
    }
}

