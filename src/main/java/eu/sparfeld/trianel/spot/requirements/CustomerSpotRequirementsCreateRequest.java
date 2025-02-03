package eu.sparfeld.trianel.spot.requirements;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record CustomerSpotRequirementsCreateRequest(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate day,
        @Schema(example = "DEW21")
        String customer,
        List<SpotVolumeCreateRequest> volumes) {
}
