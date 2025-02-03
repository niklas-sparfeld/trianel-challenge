package eu.sparfeld.trianel.spot.requirements;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/spot-requirements")
public class CustomerSpotRequirementsController {
    private final CustomerSpotRequirementsService service;

    public CustomerSpotRequirementsController(CustomerSpotRequirementsService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerSpotRequirementsResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    public CustomerSpotRequirementsResponse create(@RequestBody CustomerSpotRequirementsCreateRequest request) {
        return service.create(request);
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleException(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Invalid input data");
        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }
}
