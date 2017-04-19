package geo.repository;

import geo.domain.User;
import geo.domain.UserChat;
import geo.domain.UserChatRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ehm on 18.04.2017.
 */
@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {

    List<UserChat> findById_UserAndId_Role(User user, UserChatRole role);
}
