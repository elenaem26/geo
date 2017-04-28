package geo.listener;

import geo.event.MessageAddedEvent;
import geo.xdto.XMessage;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by ehm on 28.04.2017.
 */
@Slf4j
@Component
public class MessageListener {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private Mapper mapper;

    @EventListener(condition = "#event.transactional == false")
    public void handle(MessageAddedEvent event) {
        log.info("Received message added event {}", event.getMessage());
        template.convertAndSend("/topic/" + event.getMessage().getChat().getId(), mapper.map(event.getMessage(), XMessage.class));
    }

    @TransactionalEventListener(condition = "#event.transactional")
    public void handleAfterCommit(MessageAddedEvent event) {
        log.info("Received message added event after commit {}", event.getMessage());
        template.convertAndSend("/topic/" + event.getMessage().getChat().getId(), mapper.map(event.getMessage(), XMessage.class));
    }
}
