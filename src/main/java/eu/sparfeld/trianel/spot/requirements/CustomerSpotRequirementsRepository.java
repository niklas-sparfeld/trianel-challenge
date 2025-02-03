package eu.sparfeld.trianel.spot.requirements;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerSpotRequirementsRepository extends JpaRepository<CustomerSpotRequirements, UUID> {
    @Query("""
    SELECT csr FROM CustomerSpotRequirements csr
    LEFT JOIN FETCH csr.volumes
    WHERE csr.day = :day
      AND csr.createdAt = (
            SELECT MAX(csr2.createdAt)
            FROM CustomerSpotRequirements csr2
            WHERE csr2.customer = csr.customer
              AND csr2.day = :day
      )
    """)
    List<CustomerSpotRequirements> findLatestRequirementsByDay(@Param("day") LocalDate day);

}


