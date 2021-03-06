package geo.controller;

import geo.service.MessageService;
import geo.service.UserService;
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
 * Created by ehm on 28.04.2017.
 */
@Controller
@RequestMapping("/api/message")
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @RequestMapping(consumes = APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@Valid @RequestBody XMessage message) {
        log.debug("createMessage request received");
        messageService.saveMessage(userService.getCurrentUsername(), message);
    }

    @RequestMapping(value = "/{chatId}/messages", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<XMessage> getMessages(@PathVariable("chatId") Long chatId) {
        log.debug("getMessages request received");
        return messageService.getMessages(chatId);
    }
}
