package geo.repository;

import geo.domain.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ehm on 18.04.2017.
 */
@Repository
public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
}
