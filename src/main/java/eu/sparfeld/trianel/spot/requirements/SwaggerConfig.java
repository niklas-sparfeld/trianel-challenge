package eu.sparfeld.trianel.spot.requirements;

import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
class SwaggerConfig {
    SwaggerConfig() {
        Schema<LocalTime> schema = new Schema<>();
        schema.example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, schema);
    }
}

