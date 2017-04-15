package geo.controller;

import geo.domain.User;
import geo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehm on 25.03.2017.
 */
@Controller
@Scope(value = "session")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/sessionexpired", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String sessionExpired() throws Exception {
        log.debug("User session expired");
        throw new Exception("Session expired");
    }

    @RequestMapping(value = "/test", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String test() throws Exception {
        log.debug("User session expired");
        User user = User.builder()
                .uid("new User" + new Date().toString())
                .username("nick")
                .password("heeey")
                .email("elenaem26@gmail.com")
                .build();
        userRepository.save(user);
        return "test";
    }

    @RequestMapping(value = "/api/test", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String apitest() throws Exception {
        log.debug("User session expired");
        return "api test";
    }

    @RequestMapping(value = "/greeting", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity get() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok("G’day, " + user.getUsername() + "!");
    }
}
