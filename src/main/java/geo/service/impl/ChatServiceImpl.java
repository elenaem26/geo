package geo.service.impl;

import geo.domain.*;
import geo.repository.*;
import geo.service.ChatService;
import geo.xdto.XChat;
import geo.xdto.XMessage;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        if (xchat.getDisposeDays() != null && xchat.getDisposeDays() >=0 ) {
            //TODO check dispose date with user role
            //dispose chat by tomorrow
            Date disposeDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(disposeDate);
            c.add(Calendar.DATE, xchat.getDisposeDays());
            disposeDate = c.getTime();
            chat.setDisposeDate(disposeDate);
        }
        if (xchat.getChatGroupId() != null) {
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
            //TODO converter
            XChat xchat = mapper.map(chat, XChat.class);
            xchat.setAmountOfPeople(getAmountOfPeople(chat));
            xchat.setLastActivity(getLastActivity(chat));
            xchats.add(xchat);
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
    public void joinChat(String userName, Long chatId, Double latitude, Double longitude) {
        Chat chat = chatRepository.findOne(chatId);
        if (chat == null) {
            throw new IllegalArgumentException("Chat with id = " + chatId + " not found");
        }
        if (calculateDistance(latitude, longitude, chat.getLocation().getLatitude(), chat.getLocation().getLongitude()) > chat.getRadius()) {
            throw new AccessDeniedException("You are not in the chat area");
        }
        User currentUser = userRepository.findByUsername(userName);
        if (currentUser == null) {
            throw new AccessDeniedException("Access denied for current user");
        }
//        if (currentUser.getUserChats() == null
//                || currentUser.getUserChats().stream()
//                    .anyMatch(c -> c.getId().getRole().equals(UserChatRole.VISITOR) && c.getId().getChat().getId().equals(chatId))) {
//            throw new IllegalArgumentException("User with id = " + currentUser.getId() + " already joined chat with id = " + chat.getId());
//        } else {
            UserChat userChat = UserChat.builder()
                    .id(UserChatPK.builder()
                            .user(currentUser)
                            .chat(chat)
                            .role(UserChatRole.VISITOR)
                            .build())
                    .build();
            userChat = userChatRepository.save(userChat);
        //}
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

    @Override
    public List<XChat> getChats(Double latitude, Double longitude) {
        Double latitude1 = BigDecimal.valueOf(latitude).setScale(2, RoundingMode.DOWN).doubleValue() - 0.01;
        Double latitude2 = BigDecimal.valueOf(latitude).setScale(2, RoundingMode.DOWN).doubleValue() + 0.02;
        Double longitude1 = BigDecimal.valueOf(longitude).setScale(2, RoundingMode.DOWN).doubleValue() - 0.01;
        Double longitude2 = BigDecimal.valueOf(longitude).setScale(2, RoundingMode.DOWN).doubleValue() + 0.02;
        List<Chat> chats = chatRepository.findByLocation_LatitudeBetweenAndLocation_LongitudeBetween(latitude1, latitude2, longitude1, longitude2);
        TreeSet<Chat> sortedSet = new TreeSet<>(
                (c1, c2) -> (c1.getDistance() == null || c2.getDistance() == null)
                ? -1
                : c1.getDistance().compareTo(c2.getDistance()));
        chats.forEach(
                chat -> {
                    if (chat.getLocation() != null && chat.getLocation().getLatitude() != null && chat.getLocation().getLongitude() != null) {
                        chat.setDistance(calculateDistance(latitude, longitude, chat.getLocation().getLatitude(), chat.getLocation().getLongitude()));
                        if (chat.getDistance() <= chat.getRadius()) {
                            sortedSet.add(chat);
                        }
                    }
                }
        );
        List<XChat> xchats = new ArrayList<>();
        for (Chat chat : sortedSet) {
            XChat xchat = mapper.map(chat, XChat.class);
            xchat.setAmountOfPeople(getAmountOfPeople(chat));
            xchat.setLastActivity(getLastActivity(chat));
            xchats.add(xchat);
        }
        return xchats;
    }

    @Override
    public XChat getChat(Long chatId) {
        Chat chat = chatRepository.findOne(chatId);
        if (chat == null) {
            throw new IllegalArgumentException("Chat with id = " + chatId + " not found");
        }
        return mapper.map(chat, XChat.class);
    }

    public static double calculateDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    private Date getLastActivity(Chat chat) {
        Message lastMessage = chat.getMessages().stream().sorted((m1, m2) -> m2.getDate().compareTo(m1.getDate())).findFirst().orElse(null);
        return lastMessage != null ? lastMessage.getDate() : null;
    }

    private Integer getAmountOfPeople(Chat chat) {
        return Math.toIntExact(chat.getChatUsers().stream().filter(u -> u.getId().getRole().equals(UserChatRole.VISITOR)).count());
    }

}
