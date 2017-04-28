package geo.service.impl;

import geo.domain.Chat;
import geo.domain.Message;
import geo.domain.User;
import geo.event.MessageAddedEvent;
import geo.repository.ChatRepository;
import geo.repository.MessageRepository;
import geo.repository.UserRepository;
import geo.service.MessageService;
import geo.xdto.XMessage;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by ehm on 28.04.2017.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void saveMessage(String userName, XMessage message) {
        User currentUser =  userRepository.findByUsername(userName);
        if (currentUser == null) {
            throw new UsernameNotFoundException("Current user not found");
        }
        Chat chat = chatRepository.findOne(message.getChatId());
        if (chat == null) {
            throw new IllegalArgumentException("Chat with id = " + message.getChatId() + " not found");
        }
        Message messageToSave = mapper.map(message, Message.class);
        messageToSave.setUser(currentUser);
        messageToSave.setChat(chat);
        messageToSave = messageRepository.save(messageToSave);
        publisher.publishEvent(new MessageAddedEvent(messageToSave));
    }
}
