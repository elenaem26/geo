package geo.service;

import geo.domain.UserChatRole;
import geo.xdto.XChat;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ehm on 16.04.2017.
 */
public interface ChatService {

    void createChat(String userName, XChat xchat);

    List<XChat> getChatsByUser(String userName, UserChatRole role);

    void removeUserChat(String userName, Long chatId);

    void joinChat(String userName, Long chatId);

    void leaveChat(String userName, Long chatId);

    List<XChat> getChats(Double latitude, Double longitude);
}
