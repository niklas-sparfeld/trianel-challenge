package eu.sparfeld.trianel.spot.requirements;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class CustomerSpotRequirementsService {
    static final int VOLUMES_PER_DAY = 4;
    static final Duration PERIOD_LENGTH = Duration.ofHours(24).dividedBy(CustomerSpotRequirementsService.VOLUMES_PER_DAY);

    private final CustomerSpotRequirementsRepository repository;

    public CustomerSpotRequirementsService(CustomerSpotRequirementsRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CustomerSpotRequirementsResponse> findAll() {
        return this.repository.findAll().stream().map(Mapper::toDto).toList();
    }

    @Transactional
    public CustomerSpotRequirementsResponse create(CustomerSpotRequirementsCreateRequest request) {

        if (request.volumes().size() != VOLUMES_PER_DAY) {
            throw new IllegalArgumentException("Amount of volumes must be " + VOLUMES_PER_DAY + " but was " + request.volumes().size());
        }

        var volumes = IntStream.range(0, VOLUMES_PER_DAY)
                .mapToObj(i -> new SpotVolume(
                        request.volumes().get(i).amount(),
                        LocalTime.MIDNIGHT.plus(PERIOD_LENGTH.multipliedBy(i))
                ))
                .toList();

        var customerSpotRequirements = new CustomerSpotRequirements();
        customerSpotRequirements.setCustomer(request.customer());
        customerSpotRequirements.setVolumes(volumes);
        customerSpotRequirements.setDay(request.day());
        return Mapper.toDto(repository.save(customerSpotRequirements));
    }

}
