package geo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ehm on 25.03.2017.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Builder
@Entity
@Table(name = "USER_CHAT")
@AssociationOverrides({
        @AssociationOverride(name = "id.user",
                joinColumns = @JoinColumn(name = "USER_ID")),
        @AssociationOverride(name = "id.chat",
                joinColumns = @JoinColumn(name = "CHAT_ID")) })
public class UserChat implements Serializable {

    private static final long serialVersionUID = 7939665595407828801L;

    @EmbeddedId
    private UserChatPK id;

}
