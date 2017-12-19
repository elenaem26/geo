package geo.service.impl;

import geo.domain.User;
import geo.domain.UserRole;
import geo.repository.UserRepository;
import geo.repository.UserRoleRepository;
import geo.service.UserService;
import geo.xdto.XUser;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehm on 15.04.2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                ? (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : null;
    }

    @Override
    public List<XUser> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<XUser> result = new ArrayList<>();
        for (User user : users) {
            result.add(mapper.map(user, XUser.class));
        }
        return result;
    }

    @Override
    public void blockUser(Long id) {
        User user = userRepository.findOne(id);
        user.setEnabled(false);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void unblockUser(Long id) {
        User user = userRepository.findOne(id);
        user.setEnabled(true);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void makeUserAdmin(Long id) {
        User user = userRepository.findOne(id);
        if (user.getUserRoles() == null) {
            user.setUserRoles(new ArrayList<>());
        }
        //TODO enum
        UserRole userRole = userRoleRepository.findByName("ROLE_ADMIN");
        if (userRole != null) {
            user.getUserRoles().add(userRole);
            userRepository.saveAndFlush(user);
        }
    }
}
