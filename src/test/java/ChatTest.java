import geo.domain.UserChatRole;
import geo.service.ChatService;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ehm on 23.04.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@Transactional
@Ignore
public class ChatTest extends TestCase {

    @Autowired
    ChatService chatService;

    @Test
    public void test() {
        chatService.getChatsByUser("adf", UserChatRole.ADMIN);
    }
}
