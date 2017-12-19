package geo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehm on 25.03.2017.
 */
@Controller
@Scope(value = "session")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/sessionexpired", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String sessionExpired() throws Exception {
        log.debug("User session expired");
        throw new Exception("Session expired");
    }
}
