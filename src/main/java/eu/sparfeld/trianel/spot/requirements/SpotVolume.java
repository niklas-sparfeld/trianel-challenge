package eu.sparfeld.trianel.spot.requirements;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public class SpotVolume {
    private Long volume;
    private LocalTime periodStart;

    public SpotVolume() {
    }

    public SpotVolume(Long volume, LocalTime periodStart) {
        this.volume = volume;
        this.periodStart = periodStart;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public LocalTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalTime periodStart) {
        this.periodStart = periodStart;
    }
}
