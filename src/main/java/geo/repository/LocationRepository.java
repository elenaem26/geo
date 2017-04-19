package geo.repository;

import geo.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by ehm on 18.04.2017.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
