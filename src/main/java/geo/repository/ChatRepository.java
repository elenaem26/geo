package geo.repository;

import geo.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ehm on 18.04.2017.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByLocation_LatitudeBetweenAndLocation_LongitudeBetween(Double lat1, Double lat2, Double long1, Double long2);
}
