package geo.controller;

import geo.domain.UserChatRole;
import geo.service.ChatService;
import geo.service.UserService;
import geo.xdto.XChat;
import geo.xdto.XMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * Created by ehm on 18.04.2017.
 */
@Controller
@RequestMapping("/api/chat")
public class ChatController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ChatService chatService;
    @Autowired
    UserService userService;

    @RequestMapping(consumes = APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createChat(@Valid @RequestBody XChat xChat) {
        log.debug("createChat request received");
        chatService.createChat(userService.getCurrentUsername(), xChat);
    }

    @RequestMapping(consumes = APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeChat(@RequestParam("chatId") Long chatId) {
        log.debug("removeChat request received");
        chatService.removeUserChat(userService.getCurrentUsername(), chatId);
    }

    @RequestMapping(value = "/{chatId}/join", consumes = APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void joinChat(@PathVariable("chatId") Long chatId, @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {
        log.debug("joinChat request received");
        chatService.joinChat(userService.getCurrentUsername(), chatId, latitude, longitude);
    }

    @RequestMapping(value = "/{chatId}/leave", consumes = APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void leaveChat(@PathVariable("chatId") Long chatId) {
        log.debug("leaveChat userService received");
        chatService.leaveChat(userService.getCurrentUsername(), chatId);
    }

    @RequestMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<XChat> getUserChatsAdmin() {
        log.debug("getUserChatsAdmin request received");
        return chatService.getChatsByUser(userService.getCurrentUsername(), UserChatRole.ADMIN);
    }

    @RequestMapping(value="/by_location", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<XChat> getUserChats(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {
        log.debug("getUserChats request received");
        return chatService.getChats(latitude, longitude);
    }

    @RequestMapping(value = "/{chatId}", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public XChat getChat(@PathVariable("chatId") Long chatId) {
        log.debug("getChat request received");
        return chatService.getChat(chatId);
    }
}
