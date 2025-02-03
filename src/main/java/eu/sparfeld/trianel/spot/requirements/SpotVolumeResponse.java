package eu.sparfeld.trianel.spot.requirements;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

public record SpotVolumeResponse(
        @Schema(example = "100")
        Long amount,

        @Schema(example = "06:00")
        @JsonFormat(pattern="HH:mm")
        LocalTime periodStart) {
}
