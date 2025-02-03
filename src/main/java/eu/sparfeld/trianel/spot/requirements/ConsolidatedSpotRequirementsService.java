package eu.sparfeld.trianel.spot.requirements;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.IntStream;

import static eu.sparfeld.trianel.spot.requirements.CustomerSpotRequirementsService.PERIOD_LENGTH;


@Service
public class ConsolidatedSpotRequirementsService {
    private final CustomerSpotRequirementsRepository repository;

    public ConsolidatedSpotRequirementsService(CustomerSpotRequirementsRepository repository) {
        this.repository = repository;
    }

    public ConsolidatedSpotRequirementsResponse consolidateSpotRequirements(LocalDate day) {
        var requirements = repository.findLatestRequirementsByDay(day);

        var volumes = IntStream.range(0, CustomerSpotRequirementsService.VOLUMES_PER_DAY)
                .mapToObj(i -> {
                    var time = LocalTime.MIDNIGHT.plus(PERIOD_LENGTH.multipliedBy(i));
                    var amount = requirements.stream()
                            .mapToLong(requirement -> requirement.getVolumes().get(i).getVolume())
                            .sum();
                    return new SpotVolumeResponse(amount, time);
                })
                .toList();

        var totalAmount = volumes.stream()
                .mapToLong(SpotVolumeResponse::amount)
                .sum();

        return new ConsolidatedSpotRequirementsResponse(day, totalAmount, volumes);
    }
}