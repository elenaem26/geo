package geo.repository;

import geo.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ehm on 28.04.2017.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
