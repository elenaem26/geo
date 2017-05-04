package geo.service;

import geo.xdto.XMessage;

import java.util.List;

/**
 * Created by ehm on 28.04.2017.
 */
public interface MessageService {
    void saveMessage(String userName, XMessage message);

    List<XMessage> getMessages(Long chatId);
}
