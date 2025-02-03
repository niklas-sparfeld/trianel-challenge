package eu.sparfeld.trianel.spot.requirements;

import io.swagger.v3.oas.annotations.media.Schema;

public record SpotVolumeCreateRequest(
        @Schema(example = "100")
        Long amount) {
}

