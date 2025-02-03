package eu.sparfeld.trianel.spot.requirements;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class CustomerSpotRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    private LocalDate day;

    private Long amount;

    private String customer;

    @ElementCollection
    private List<SpotVolume> volumes;

    @UpdateTimestamp
    private Instant updatedAt;

    @CreationTimestamp
    private Instant createdAt;

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Long getAmount() {
        return amount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<SpotVolume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<SpotVolume> volumes) {
        this.volumes = volumes;
        this.amount = volumes.stream().map(SpotVolume::getVolume).reduce(0L, Long::sum);
    }
}

