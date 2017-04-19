package geo.service.impl;

import geo.domain.User;
import geo.domain.UserRole;
import geo.repository.UserRepository;
import geo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ehm on 18.04.2017.
 */
@Service
@Transactional
public class DBInitializer {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    List<UserRole> roles;
    List<User> users;
    @PostConstruct
    private void init() {
        initRoles();
        initUsers();
    }

    private void initUsers() {
        users = new ArrayList<>();

        users.add(User.builder()
                .uid("nickUser")
                .username("nick")
                .password("1234")
                .enabled(true)
                .email("elenaem26@gmail.com")
                .userRoles(Arrays.asList(roles.get(0)))
                .build());
        users.add(User.builder()
                .uid("adminAdmin")
                .username("admin")
                .password("admin_password")
                .enabled(true)
                .email("admin26@gmail.com")
                .userRoles(Arrays.asList(roles.get(1)))
                .build());
        userRepository.save(users);
    }

    private void initRoles() {
        roles = new ArrayList<>();
        roles.add(UserRole.builder()
                .name("ROLE_USER")
                .build());
        roles.add(UserRole.builder()
                .name("ROLE_ADMIN")
                .build());
        userRoleRepository.save(roles);
    }
}
