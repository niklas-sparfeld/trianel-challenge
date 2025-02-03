package eu.sparfeld.trianel;

import eu.sparfeld.trianel.spot.requirements.ConsolidatedSpotRequirementsResponse;
import eu.sparfeld.trianel.spot.requirements.CustomerSpotRequirementsCreateRequest;
import eu.sparfeld.trianel.spot.requirements.CustomerSpotRequirementsResponse;
import eu.sparfeld.trianel.spot.requirements.SpotVolumeCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
class IntegrationTest {

    private static final LocalDate TEST_DAY = LocalDate.of(2025, 2, 3);
    private static final String SPOT_REQS_ENDPOINT = "/spot-requirements";
    private static final String CONSOLIDATED_ENDPOINT = "/consolidated-spot-requirements/{day}";

    @Autowired
    private WebTestClient client;

    @Test
    void testSpotRequirementsFlow() {
        // 1. Get empty requirements
        client.get()
                .uri(SPOT_REQS_ENDPOINT)
                .exchange()
                .expectStatus().isOk()
                // assuming the API returns a JSON array; adjust the type as needed.
                .expectBodyList(CustomerSpotRequirementsResponse.class)
                .hasSize(0);

        // 2. Add DEW21 requirements
        var dew21Request = new CustomerSpotRequirementsCreateRequest(
                TEST_DAY,
                "DEW21",
                List.of(
                        new SpotVolumeCreateRequest(100L),
                        new SpotVolumeCreateRequest(200L),
                        new SpotVolumeCreateRequest(300L),
                        new SpotVolumeCreateRequest(400L)
                )
        );

        client.post()
                .uri(SPOT_REQS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dew21Request)
                .exchange()
                .expectStatus().isOk();

        // 3. Add Gelsenwasser requirements
        var gelsenwasserRequest = new CustomerSpotRequirementsCreateRequest(
                TEST_DAY,
                "Gelsenwasser",
                List.of(
                        new SpotVolumeCreateRequest(10L),
                        new SpotVolumeCreateRequest(20L),
                        new SpotVolumeCreateRequest(30L),
                        new SpotVolumeCreateRequest(40L)
                )
        );

        client.post()
                .uri(SPOT_REQS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(gelsenwasserRequest)
                .exchange()
                .expectStatus().isOk();

        // 4. Show added requirements (expecting two entries now)
        client.get()
                .uri(SPOT_REQS_ENDPOINT)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerSpotRequirementsResponse.class)
                .value(list -> assertThat(list).hasSize(2));

        // 5. Show consolidated requirements
        var consolidated = client.get()
                .uri(CONSOLIDATED_ENDPOINT, TEST_DAY)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ConsolidatedSpotRequirementsResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(consolidated)
                .withFailMessage("Consolidated response must not be null")
                .isNotNull();
        // Assert the consolidated day and total amount
        assertThat(consolidated.day()).isEqualTo(TEST_DAY);
        assertThat(consolidated.amount()).isEqualTo(1100);

        // Assert the consolidated volumes details
        var volumes = consolidated.volumes();
        assertThat(volumes).hasSize(4);

        assertThat(volumes.get(0).periodStart()).isEqualTo(LocalTime.of(0, 0));
        assertThat(volumes.get(0).amount()).isEqualTo(110);

        assertThat(volumes.get(1).periodStart()).isEqualTo(LocalTime.of(6, 0));
        assertThat(volumes.get(1).amount()).isEqualTo(220);

        assertThat(volumes.get(2).periodStart()).isEqualTo(LocalTime.of(12, 0));
        assertThat(volumes.get(2).amount()).isEqualTo(330);

        assertThat(volumes.get(3).periodStart()).isEqualTo(LocalTime.of(18, 0));
        assertThat(volumes.get(3).amount()).isEqualTo(440);

        // 6. Append (replace) Gelsenwasser requirements
        var newGelsenwasserRequest = new CustomerSpotRequirementsCreateRequest(
                TEST_DAY,
                "Gelsenwasser",
                List.of(
                        new SpotVolumeCreateRequest(1L),
                        new SpotVolumeCreateRequest(2L),
                        new SpotVolumeCreateRequest(3L),
                        new SpotVolumeCreateRequest(4L)
                )
        );

        client.post()
                .uri(SPOT_REQS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newGelsenwasserRequest)
                .exchange()
                .expectStatus().isOk();

        // 7. Show new consolidated requirements
        var newConsolidated = client.get()
                .uri(CONSOLIDATED_ENDPOINT, TEST_DAY)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ConsolidatedSpotRequirementsResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(newConsolidated)
                .withFailMessage("New consolidated response must not be null")
                .isNotNull();

        // Assert the consolidated day and total amount
        assertThat(newConsolidated.day()).isEqualTo(TEST_DAY);
        assertThat(newConsolidated.amount()).isEqualTo(1010);

        // Assert the consolidated volumes details
        var newVolumes = newConsolidated.volumes();
        assertThat(newVolumes).hasSize(4);

        assertThat(newVolumes.get(0).periodStart()).isEqualTo(LocalTime.of(0, 0));
        assertThat(newVolumes.get(0).amount()).isEqualTo(101);

        assertThat(newVolumes.get(1).periodStart()).isEqualTo(LocalTime.of(6, 0));
        assertThat(newVolumes.get(1).amount()).isEqualTo(202);

        assertThat(newVolumes.get(2).periodStart()).isEqualTo(LocalTime.of(12, 0));
        assertThat(newVolumes.get(2).amount()).isEqualTo(303);

        assertThat(newVolumes.get(3).periodStart()).isEqualTo(LocalTime.of(18, 0));
        assertThat(newVolumes.get(3).amount()).isEqualTo(404);
    }
}