package geo.service.impl;

import geo.domain.Chat;
import geo.domain.Location;
import geo.domain.User;
import geo.domain.UserRole;
import geo.repository.ChatRepository;
import geo.repository.UserRepository;
import geo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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
    @Autowired
    ChatRepository chatRepository;
    List<UserRole> roles;
    List<User> users;
    List<Chat> chats;
    @PostConstruct
    private void init() {
        initRoles();
        initUsers();
        initChats();
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

    private void initChats() {
        chats = new ArrayList<>();
        chats.add(Chat.builder()
                .name("chatCircus")
                .location(Location.builder()
                    .latitude(51.655815)
                    .longitude(39.185268)
                    .build())
                .radius(300.)
                .build());
        chats.add(Chat.builder()
                .name("chatSCEurope")
                .location(Location.builder()
                        .latitude(51.657026)
                        .longitude(39.185386)
                        .build())
                .radius(300.)
                .build());
        chats.add(Chat.builder()
                .name("chatSunParadise")
                .location(Location.builder()
                        .latitude(51.656011)
                        .longitude(39.188897)
                        .build())
                .radius(300.)
                .build());
        chats.add(Chat.builder()
                .name("chatGranat")
                .location(Location.builder()
                        .latitude(51.659134)
                        .longitude(39.194248)
                        .build())
                .radius(300.)
                .build());
        chats.add(Chat.builder()
                .name("chatDiagnosticCenter")
                .location(Location.builder()
                        .latitude(51.660471)
                        .longitude(39.195860)
                        .build())
                .radius(300.)
                .build());
        chats.add(Chat.builder()
                .name("chatSpartak")
                .location(Location.builder()
                        .latitude(51.661752)
                        .longitude(39.204637)
                        .build())
                .radius(300.)
                .build());
        chatRepository.save(chats);
    }
}
