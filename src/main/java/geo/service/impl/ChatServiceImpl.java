package geo.service.impl;

import geo.domain.*;
import geo.repository.*;
import geo.service.ChatService;
import geo.xdto.XChat;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ehm on 16.04.2017.
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ChatGroupRepository chatGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserChatRepository userChatRepository;

    @Override
    public void createChat(String userName, XChat xchat) {
        User currentUser =  userRepository.findByUsername(userName);
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied for current user");
        }
        Chat chat = mapper.map(xchat, Chat.class);
        //TODO move to the mapper
        Location location = locationRepository.findByLatitudeAndLongitude(xchat.getLatitude(), xchat.getLongitude());
        if (location == null) {
            location = Location.builder()
                    .latitude(xchat.getLatitude())
                    .longitude(xchat.getLongitude())
                    .build();
        }
        chat.setLocation(location);
        //TODO location set chat??
        if (xchat.getDisposeDate() == null) {
            //TODO check dispose date with user role
            //dispose chat by tomorrow
            Date disposeDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(disposeDate);
            c.add(Calendar.DATE, 1);
            disposeDate = c.getTime();
            chat.setDisposeDate(disposeDate);
        }
        if ( xchat.getChatGroupId() != null) {
            //TODO checkUsers and their roles to create chat in this chatgroup
            ChatGroup chatGroup = chatGroupRepository.findOne(xchat.getChatGroupId());
            if (chatGroup == null) {
                //TODO custom exceptions
                throw new IllegalArgumentException("Chat group with id " + xchat.getChatGroupId() + " not found");
            }
            chat.setChatGroup(chatGroup);
        }

        chat = chatRepository.save(chat);
        //create UserChat
        UserChat userChat = UserChat.builder()
                .id(UserChatPK.builder()
                    .user(currentUser)
                    .chat(chat)
                    .role(UserChatRole.ADMIN)
                    .build())
                .build();
        userChat = userChatRepository.save(userChat);
    }

    @Override
    public List<XChat> getChatsByUser(String userName, UserChatRole role) {
        User currentUser =  userRepository.findByUsername(userName);
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied for current user");
        }
        List<UserChat> userChats = userChatRepository.findById_UserAndId_Role(currentUser, role);
        List<Chat> chats = userChats.stream().filter(c -> c.getId() != null).map(c -> c.getId().getChat()).collect(Collectors.toList());
        List<XChat> xchats = new ArrayList<>();
        for (Chat chat : chats) {
            xchats.add(mapper.map(chat, XChat.class));
        }
        return xchats;
    }

    @Override
    public void removeUserChat(String userName, Long chatId) {
        Chat chat = chatRepository.findOne(chatId);
        if (chat == null) {
            throw new IllegalArgumentException("Chat with id = " + chatId + " not found");
        }
        User currentUser = userRepository.findByUsername(userName);
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied for current user");
        }
        if (chat.getChatUsers() != null
                && chat.getChatUsers().stream()
                    .anyMatch(c -> c.getId().getRole().equals(UserChatRole.ADMIN) && c.getId().getChat().getId().equals(chatId))) {
            chatRepository.delete(chat);
        } else {
            throw new IllegalArgumentException("User with id = " + currentUser.getId() + " is not allowed to delete this chat with id = " + chat.getId());
        }
    }

    @Override
    public void joinChat(String userName, Long chatId) {
        Chat chat = chatRepository.findOne(chatId);
        if (chat == null) {
            throw new IllegalArgumentException("Chat with id = " + chatId + " not found");
        }
        User currentUser = userRepository.findByUsername(userName);
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied for current user");
        }
        if (currentUser.getUserChats() == null
                || currentUser.getUserChats().stream()
                    .anyMatch(c -> c.getId().getRole().equals(UserChatRole.VISITOR) && c.getId().getChat().getId().equals(chatId))) {
            throw new IllegalArgumentException("User with id = " + currentUser.getId() + " already joined chat with id = " + chat.getId());
        } else {
            UserChat userChat = UserChat.builder()
                    .id(UserChatPK.builder()
                            .user(currentUser)
                            .chat(chat)
                            .role(UserChatRole.VISITOR)
                            .build())
                    .build();
            userChat = userChatRepository.save(userChat);
        }
    }

    @Override
    public void leaveChat(String userName, Long chatId) {
        Chat chat = chatRepository.findOne(chatId);
        if (chat == null) {
            throw new IllegalArgumentException("Chat with id = " + chatId + " not found");
        }
        User currentUser = userRepository.findByUsername(userName);
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied for current user");
        }
        UserChat userChat = currentUser.getUserChats() != null
                ? currentUser.getUserChats().stream()
                    .filter(c -> c.getId().getRole().equals(UserChatRole.VISITOR))
                    .filter(c -> c.getId().getChat().getId().equals(chatId)).findFirst().orElse(null)
                : null;
        if (userChat != null) {
            userChatRepository.delete(userChat);
        } else {
            throw new IllegalArgumentException("User with id = " + currentUser.getId() + " already left chat with id = " + chat.getId());
        }
    }
}
