package eu.sparfeld.trianel.spot.requirements;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record ConsolidatedSpotRequirementsResponse(
        LocalDate day,
        @Schema(example = "800")
        Long amount,
        List<SpotVolumeResponse> volumes
) {
}

