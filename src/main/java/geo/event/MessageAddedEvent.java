package geo.event;

import geo.domain.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by ehm on 28.04.2017.
 */
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=true)
public class MessageAddedEvent extends Event{
    private static final long serialVersionUID = -8585132958606174742L;
    Message message;
    boolean transactional;
    public MessageAddedEvent(Message message) {
        this(message, false);
    }

    public MessageAddedEvent(Message message, boolean transactional) {
        this.message = message;
        this.transactional = transactional;
    }
}
