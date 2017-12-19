package geo.controller;

import geo.service.ChatService;
import geo.service.UserService;
import geo.xdto.XChat;
import geo.xdto.XUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by ehm on 19.06.2017.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;
    @Autowired
    ChatService chatService;

    @RequestMapping(value = "/allusers", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<XUser> getAllUsers() throws Exception {
        log.debug("getAllUsers request received");
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/allchats", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<XChat> getAllChats() throws Exception {
        log.debug("getAllChats request received");
        return chatService.getAllChats();
    }

    @RequestMapping(value = "/block/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(@PathVariable Long id) throws Exception {
        log.debug("blockUser request received");
        userService.blockUser(id);
    }

    @RequestMapping(value = "/unblock/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void unblockUser(@PathVariable Long id) throws Exception {
        log.debug("unblock request received");
        userService.unblockUser(id);
    }

    @RequestMapping(value = "/makeadmin/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void makeUserAdmin(@PathVariable Long id) throws Exception {
        log.debug("makeUserAdmin request received");
        userService.makeUserAdmin(id);
    }
}
