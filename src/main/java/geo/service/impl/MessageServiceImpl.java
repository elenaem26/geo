package geo.service.impl;

import geo.domain.Chat;
import geo.domain.Message;
import geo.domain.User;
import geo.domain.UserChat;
import geo.event.MessageAddedEvent;
import geo.repository.ChatRepository;
import geo.repository.MessageRepository;
import geo.repository.UserRepository;
import geo.service.MessageService;
import geo.xdto.XMessage;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //TODO периодическая проверка на то что человек в зоне и может отправлять и просмартирвать сообщения
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
        boolean isMember = false;
        for(UserChat userChat : chat.getChatUsers()) {
            isMember = userChat.getId().getUser().equals(currentUser);
            if (isMember) {
                break;
            }
        }
        if (!isMember) {
            throw new AccessDeniedException("Access denied for current user");
        }
        Message messageToSave = mapper.map(message, Message.class);
        messageToSave.setUser(currentUser);
        messageToSave.setChat(chat);
        //TODO maybe client?
        messageToSave.setDate(new Date());
        messageToSave = messageRepository.save(messageToSave);
        publisher.publishEvent(new MessageAddedEvent(messageToSave));
    }

    @Override
    public List<XMessage> getMessages(Long chatId) {
        Chat chat = chatRepository.findOne(chatId);
        if (chat == null) {
            throw new IllegalArgumentException("Chat with id = " + chatId + " not found");
        }
        List<XMessage> messages = new ArrayList<>();
        for (Message message : chat.getMessages()) {
            messages.add(mapper.map(message, XMessage.class));
        }
        return messages;
    }
}
