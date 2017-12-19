package geo.service;

import geo.xdto.XUser;

import java.util.List;

/**
 * Created by ehm on 15.04.2017.
 */
public interface UserService {
    String getCurrentUsername();

    List<XUser> getAllUsers();

    void blockUser(Long id);

    void unblockUser(Long id);

    void makeUserAdmin(Long id);
}
