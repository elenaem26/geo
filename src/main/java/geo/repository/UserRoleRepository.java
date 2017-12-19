package geo.repository;

import geo.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ehm on 18.04.2017.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByName(String name);
}
