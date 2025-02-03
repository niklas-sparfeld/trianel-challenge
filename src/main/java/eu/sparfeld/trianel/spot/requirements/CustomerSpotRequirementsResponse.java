package eu.sparfeld.trianel.spot.requirements;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record CustomerSpotRequirementsResponse(
    LocalDate day,
    @Schema(example = "800")
    Long amount,
    @Schema(example = "DEW21")
    String customer,
    List<SpotVolumeResponse> volumes
) {}
